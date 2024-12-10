package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {

    /*
     1) В Stream Api есть метод skip который позволяет сразу пропустить элементы коллекции
     2) Можно обернуть в тернарный оператор для упрощения конструкции
     */

    return persons.isEmpty() ? Collections.emptyList() :
        persons
            .stream()
            .skip(1)
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {

    /*
    1) Два раза находили уникальные элементы коллекции, с помощью distinct() и toSet()
    2) toSet() можно сократить, обернув List в new HashSet<>(list)
     */

    return new HashSet<>(this.getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {

    /*
    1) Можно упростить заиспользовав StringBuilder,
    это позволит не создавать новые строки в памяти при конкатенации
    2) в методе 2 использовался person.secondName() -> заменил на person.middleName()
     */
    StringBuilder result = new StringBuilder();

    if (person.secondName() != null) {
      result.append(person.secondName());
    }

    if (person.firstName() != null) {
      if (!result.isEmpty()) {
        result.append(" ");
      }
      result.append(person.firstName());
    }

    if (person.middleName() != null) {
      if (!result.isEmpty()) {
        result.append(" ");
      }
      result.append(person.middleName());
    }

    return result.toString();
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    /*
    1) Зачем то создавали отдельную мапу и выполняли полной перебор персон
    2) Каждую персону проверяли еще и на условие
     */
    return persons.stream()
        .collect(
            Collectors.toMap(
                Person::id,
                this::convertPersonToString,
                (p, w) -> p
        ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    /*
    1) Преобразуем одну коллекцию в Set для ускорения проверки на совпадение
    2) В исходном коде было O(x*y)
     */
    Set<Person> set = new HashSet<>(persons1);
    for (Person person : persons2) {
      if (set.contains(person)) {
        return true;
      }
    }
    return false;
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    /*
    Используем метод count() - возвращает количество элементов в потоке после применения фильтра.
     */
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /*
  1) Повезло, HashSet не гарантирует порядок и может не совпадать с порядком в листе snapshot
  2) Как то переопределили реализацию Set
   */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
