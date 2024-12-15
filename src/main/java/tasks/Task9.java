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
import java.util.Objects;
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
    // Более чистый код
    // Не тратим время на удаление нулевого элемента из Листа
    // List на входе может быть неизменяемым
    return persons.stream()
        .skip(1)
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // Тут подсказывает IDEA
    // Set содержит уникальные элементы, distinct избыточен
    // Заменяем на конструктор HashSet
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // Опечатка, вместо middleName используется secondName
    // Более чистый код
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Более чистый код
    // Т.к. в изначальном коде есть проверка на повторение person.id
    // то используем toMap с мерджем, т.к в противном случае дубликат заменит собой первый вариант
    return persons.stream()
        .collect(Collectors.toMap(Person::id, this::convertPersonToString, (a,b) -> a));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Более чистый код
    return !Collections.disjoint(persons1, persons2);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Переменная count не нужна
    return numbers
        .filter(number -> number % 2 == 0)
        .count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    // Хоть мы и перетасовали числа, но когда оборачиваем их в HashSet, то для данного набора чисел получается так,
    // что они снова выстраиваются в отсортированном порядке.
    // Объяснение кроется в работе хеш-таблицы. Элементы в хеш-таблице хранятся в бакетах.
    // В какой бакет попадет элемент рассчитывается как остаток от деления хеш-функции элемента на емкость хеш-таблицы.
    // Для целого числа хеш-функция равна этому числу. Получается что число 1 попадет в первый бакете, число 2 во второй,
    // число 10000 в бакет с индексом 10000.
    // В нашем случае, независимо от того как числа были перемешаны, в хеш-таблице каждое число попадает
    // в отдельный бакет и окажется в отсортированном порядке.
    // Когда мы проводим сравнение, HashSet сперва выдает элемент из первого бакета - число 1, потом из второго - число 2
    // и так далее. Получается отсортированный вывод.
    // В итоге, для нашего случая, получается что мы сравниваем два одинаковых отсортированных набора чисел.

    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
