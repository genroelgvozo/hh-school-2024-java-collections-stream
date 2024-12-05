package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  /*
  Ассимптотика O(n):
  - сначала я прохожусь один раз по списку с айдишниками, чтобы создать сет, это O(n);
  - далее я прохожусь один раз по сету с Person, чтобы переложить их в нужном порядке в массив, это тоже O(n);
  - далее я прохожусь один раз по массиву с Person, чтобы собрать финальный список, это тоже O(n).

  Получается финально O(n) + O(n) + O(n) = 3*O(n), и по правилу ассимптотики мы опускаем константу, и остается O(n).
  Все операции внутри циклов (добавление в конец списка, добавление/получение из массива) это О(1).
   */
  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Integer> personIdsMap = new HashMap<>();

    for (int i = 0; i < personIds.size(); i++) {
      personIdsMap.put(personIds.get(i), i);
    }

    Person[] curPersons = new Person[personIds.size()];

    for (Person curPerson : persons) {
      Integer curPersonId = curPerson.id();
      int index = personIdsMap.getOrDefault(curPersonId, -1);

      if (index != -1) {
        curPersons[index] = curPerson;
      }
    }

    List<Person> result = new ArrayList<>();
    for (Person curPerson : curPersons) {
      if (curPerson != null) {
        result.add(curPerson);
      }
    }
    return result;
  }
}
