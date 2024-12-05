package tasks;

import common.Person;

import java.util.Collection;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream().sorted((o1, o2) -> {
      if (o1.secondName().compareTo(o2.secondName()) < 0) {
        return -1;
      } else if (o1.secondName().compareTo(o2.secondName()) > 0) {
        return 1;
      } else {
        if (o1.firstName().compareTo(o2.firstName()) < 0) {
          return -1;
        } else if (o1.firstName().compareTo(o2.firstName()) > 0) {
          return 1;
        } else {
          if (o1.createdAt().isBefore(o2.createdAt())) {
            return -1;
          } else if (o1.createdAt().isAfter(o2.createdAt())) {
            return 1;
          } else {
            return 0;
          }
        }
      }
    }).toList();
  }
}
