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
    if (persons.isEmpty()) {     //  тут поправку на спец метод проверки на пустоту даже сама идея предлагает. на мой взгляд проще прочитать слова чем думать почему тут равно-равно стоит
      return Collections.emptyList(); // Подумав еще раз, вроде смысла нет в проверке. так как скип вернет пустой список. мапа от пустого списка тоже пуста => после то лист будет [] буквально то же что и в if
    }
    // persons.remove(0); это можно просто в стрим сразу включить написав skip
    return persons.stream().skip(1) // лучше skip потому что при удалении возникает много лишней работы с памятью. так как лист то будет пересчет заголовка как минимум. да и в целом может когда-нибудь понадобятся эти фальшивки, мало ли
        .map(Person::firstName)          // ну и в целом легче читать если главные пункты
        .collect(Collectors.toList());   // на разных строчкках, но наверно это не очень важно

  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  /*
  тут идея сама пишет, что distinct не нужен:
  Redundant 'distinct()' call: elements will be distinct anyway when collected to a Set
  что логично, действительно, зачем еще дистинкт когда мы все равно потом все запишем в множество
  а без него все в целом упрощается до конструктора нового сета просто
 */

  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  /* поменяла реализацию в соотвествии с пояснениями
   */
  public String BuildFullNameForPerson(Person person) {
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // перепишем через стрим для большей наглядности
  // заменила distinct на то как было на лекции
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
        .collect(Collectors.toMap(
            Person::id,
            this::BuildFullNameForPerson,
            (existing, duplicate) -> existing));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  //через стрим быстрее и нагляднее
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> setForPerson2 = new HashSet<>(persons2);
    return persons1.stream().anyMatch(setForPerson2::contains);
  }

  // Посчитать число четных чисел
  // в целом не вижу смысла в доп переменной count, можно сразу
  /*
    при использовании доп переменной есть несколько проблем:
    во-первых возможна ситуации что далее использует в своей части кода что может привести к ошибкам
    во-вторых с ней в целом снижается читабельность, поскольку для понимания этой части кода нужно посмотреть большее кол-во строчек
    ну и зачем доп переменная если есть спец метод но про это я написала уже (хотя наверно не очень ясно это сделала)
   */
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
    assert snapshot.toString().equals(set.toString());
  }

  /*
   Хеш функция от целого числа это просто это же число. Хеш таблица определяет куда попадет очередной элемент (в какой конкретно бакет)
   через формулу: bucketIndex = (hashcode) % (capacity - 1) , где вместимость хеш таблицы определяется из того, что она расширяется вдвое при достижениее значения
   capacity * loadFactor. причем обычно loadfactor ~0.75
   У нас 10000 элементов => 10000/0.75 = 13333.33... то есть ближайшая степень двойки 16384 т е сильно больше 10000 => каждое число будет в отдельном бакете.
   причем попадут они туда в порядке возрастания, потому что остаток от деления всех чисел при делении на 16384 и будут эти числа
   toString() печатает элементы в том порядке что они приходят к нему от HashSet, а он в свою очередь проходится по бакетам просто в порядке возрастания.
   Т е получается отсортированный вывод.
  */

}
