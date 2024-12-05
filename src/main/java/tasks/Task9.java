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
  // Решение: добавила функцию skip, чтобы просто пропустить первый элемент стрима
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  // Решение: заменила на конструктор сета. Нет смысла делать стрим, когда мы можем просто использовать конструктор
  // и передать туда нужную коллекцию
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  // Решение: потерялся middleName, вернула его в логику. Заменила String на StringBuilder, т.к. такой код
  // будет работать быстрее. String это immutable объект, и каждый раз при добавлении слова будет создаваться новый,
  // а StringBuilder можно изменять добавляя новые слова, а уже в конце сконвертировать в строку

  public String convertPersonToString(Person person) {
    StringBuilder builder = new StringBuilder();

    if (person.secondName() != null) {
      builder.append(person.secondName());
      builder.append(" ");
    }

    if (person.firstName() != null) {
      builder.append(person.firstName());
      builder.append(" ");
    }

    if (person.middleName() != null) {
      builder.append(person.middleName());
      builder.append(" ");
    }
    return builder.toString();
  }

  // словарь id персоны -> ее имя
  // Решение: заменила на стрим, который сразу выдает нужную мапу. Id персоны уникально, поэтому мы можем быть уверены,
  // что не перепишем какое-то значение
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(Person::id, Person::firstName));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // Решение: можно было в изначальном коде просто сделать сразу return true, без ввода переменной,
  // такой код работал бы быстрее, но все равно через О(n^2). Чтобы было быстрее, я стримами переложила
  // коллекции в два сета с id персонами и нашла пересечение этих двух сетов
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Integer> persons1Set = persons1.stream().map(Person::id).collect(Collectors.toSet());
    Set<Integer> persons2Set = persons2.stream().map(Person::id).collect(Collectors.toSet());
    persons1Set.retainAll(persons2Set);

    return persons1Set.size() > 0;
  }

  // Посчитать число четных чисел
  // Решение: создавать переменную, которая будет хранить состояние не лучший вараинт, в данном случае
  // в этом нет смысла, и это может привезти к ошибкам в том случае, если к этой переменной будет обращаться другой
  // метод, и ее зануление повлияет на результат.
  // Я заменила выгрузку отфильтрованных цифр в список и return длины списка
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).toList().size();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  // Решение: сет не хранит порядок добавления элементов, поэтому, когда мы добавили зашафленные числа в сет,
  // их перепутанный порядок потерялся. Но при добавлении в сет, хэшкодом целого числа, которое мы добавляем,
  // будет это самое число, поэтому они будут добавляться в память по порядку возрастания, и также выведутся в строку
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
