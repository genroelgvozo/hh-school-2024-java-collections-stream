package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    List<Person> sortedPerson = new ArrayList<>(persons);
    sortedPerson.sort(Comparator.comparing(Person::secondName)
        .thenComparing(Person::firstName)
        .thenComparing(Person::createdAt));
    return sortedPerson;
  }
}
