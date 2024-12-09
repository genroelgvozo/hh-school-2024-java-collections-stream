package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
    if (persons.isEmpty()) {     //  тут поправку на спец метод проверки на пустоту даже сама идея предлагает. на мой взгляд проще прочитать слова чем думать почему тут равно-равно стоит
      return Collections.emptyList();
    }
    // persons.remove(0); это можно просто в стрим сразу включить написав skip
    return persons.stream().skip(1)
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
  /* в целом тут просто ошиблись и вместо фио спросили дважды фамилия и ни разу отчество,
     но если честно, я не вижу смысла проверять что фамилия и имя присутсвуют, потому что
     обычно это обязательные поля и пропустить можно только отчество по причине отсутвия его во многих странах
     но оставлю, мало ли в самом коде забыли сделать эти поля обязательными...
     также можно использовать StringBuilder но так как тут всего 3 слова, наверно не имеет смысла
   */
  public String convertPersonToString(Person person) { // не надо людей превращать в строчки. страшна :)
    String result = "";                                // но назание понятное, менять не вижу смысла
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.middleName() != null) {
      result += " " + person.secondName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  // перепишем через стрим для большей наглядности
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
        .distinct()
        .collect(Collectors.toMap(Person::id, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  //через стрим быстрее и нагляднее
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> setForPerson2 = new HashSet<>(persons2);
    return persons1.stream().anyMatch(setForPerson2::contains);
  }

  // Посчитать число четных чисел
  // в целом не вижу смысла в доп переменной count, можно сразу
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
    HashSet хранит только уникальные элементы, а так как изначально в списке не было дубликатов, то множество содержит все числа от 1 до 10,000.
    toString() на HashSet не сохраняет порядок добавления элементов. Вместо этого возвращается строковое представление, которое соответствует элементам,
    упорядоченным по порядку возрастания. Строка, возвращаемая snapshot.toString(), будет содержать числа в порядке их появления, но так как  числа в нем те же,
    что и в set, а все они уникальны и в одном и том же диапазоне, то итоговые строки окажутся одинаковыми по содержимому, что делает assert всегда верным.
*/
}
