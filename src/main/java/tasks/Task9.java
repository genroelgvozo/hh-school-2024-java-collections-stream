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
    // Есть специальный метод для проверки на пустоту
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    persons.remove(0);
    return persons.stream().map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // distinct не нужен, так как всё преобразуется в Set, где и так нет дубликатов
    // И вообще можно короче без потока записать это
    // (Спасибо IDE за рекомендации)
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // Так короче и производительнее, так как строка каждый раз не создаётся заново
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Так будет короче
    return persons
            .stream()
            .collect(Collectors.toMap(Person::id, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Так короче и выполняется не за квадрат, но выделяется память для всех элементов из второй коллекции

    var secondCollectionSet = new HashSet<>(persons2);
    return persons1.stream().anyMatch(secondCollectionSet::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Есть специальный метод для подсчёта элементов
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке

  // HashSet основан на HashMap
  // HashMap основан на массиве
  // При добавлении элементов в HashMap, их место в массиве определяется как остаток от деления hashcode на размер массива
  // При создании классов исполняют контракт, согласно которому равные объекты (.equals()) будут иметь равный hashcode, а
  // результат исполнения equals может менять только, если изменилось внутреннее состояние класса, но Integer неизменяемый,
  // поэтому у каждого Integer будет равный hashcode во время исполнения программы
  // Ответ на вопрос из чата:
  // Не гарантировано, что результат выполнения hashcode между разными запусками программы будет одним и тем же,
  // поэтому в зависимости от запуска программы элементы Set'а могут лежать в разном порядке в массиве

  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
