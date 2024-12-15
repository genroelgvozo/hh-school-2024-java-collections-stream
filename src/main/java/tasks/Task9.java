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


  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) { //метод isEmpty() чуть-чуть быстрее, чем size>0
      return Collections.emptyList();
    }
    return persons.stream()
        .skip(1)                    //пропускаем первую позицию
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); //используем getNames и ложим в HashSet уникальных имен
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return Stream.of(person.firstName(),person.secondName(),person.middleName()) //сокращаем всю эту грамозткость до 1 StreamApi(кстати, в оригинале 2 раза secondName, ошибка???)
        .filter(Objects::nonNull)                                                //Изящно простой способ убрать null
        .collect(Collectors.joining(" "));                               //Склеиваем с разделителем " "
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(
        Person::id,
        this::convertPersonToString //сократили в 1 StreamApi, разве это не прекрастно?)
    ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains); //используем StreamAPI для нахождения совпадения
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    count = numbers.filter(num -> num % 2 == 0).count(); //используем StreamAPI для подсчёта
    return count;
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);       //т.к. у нас числа от  до 1000, хеши у этих чисел будут распологаться соответствующим образом
    assert snapshot.toString().equals(set.toString());//у HashSet.toString() реализация происходит по его элементам, которые упорядочены по хешу
                                                      //т.е. в порядчке от 1 до 1000.
                                                      //чтд
  }
}
