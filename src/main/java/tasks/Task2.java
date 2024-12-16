package tasks;

import common.Person;

import java.util.*;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    List<Person> persons = new ArrayList<>();
    persons.addAll(persons1);
    persons.addAll(persons2);
    persons.sort(new Comparator<Person>() {
      @Override
      public int compare(Person person1, Person person2) {
        if (person1.createdAt().isBefore(person2.createdAt())) {
          return -1;
        }
        return 1;
      }
    });
    if (persons.size() > limit) {
      persons = persons.subList(0, limit);
    }
    return persons;
  }
}
