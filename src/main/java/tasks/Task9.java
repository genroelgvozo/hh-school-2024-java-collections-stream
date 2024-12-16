package tasks;

import common.Person;

import java.util.*;
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
    // Проверка на пустоту
    if (persons == null || persons.isEmpty()) {
      return Collections.emptyList();
    }
    // Используем Skip чтобы не изменять изначальную коллекцию
    return persons.stream()
            .skip(1) // Пропускаем первый элемент (фальшивую персону)
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // Напрямую конвертируем в Set
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    if (person == null) {
      return "";
    }
    // Используем String.join для склейки строк, для улучшения читаемости
    return String.join(" ",
            Optional.ofNullable(person.secondName()).orElse(""),
            Optional.ofNullable(person.firstName()).orElse(""),
            Optional.ofNullable(person.middleName()).orElse("")).trim();
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(1);
    for (Person person : persons) {
      if (!map.containsKey(person.id())) {
        map.put(person.id(), convertPersonToString(person));
      }
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    if (persons1 == null || persons2 == null) {
      return false;
    }

    // Преобразуем одну из коллекций в множество для ускорения проверки
    Set<Person> personSet = new HashSet<>(persons1);
    return persons2.stream().anyMatch(personSet::contains);
  }

  // Посчитать число четных чисел
  //убрана лишняя переменная
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    // Утверждение всегда истинно, так как snapshot.toString() создаёт строку с числами в порядке их добавления,
    // а toString() для HashSet у Integer возвращает числа в их натуральном порядке.
    assert new TreeSet<>(snapshot).toString().equals(new TreeSet<>(set).toString());
  }
}
