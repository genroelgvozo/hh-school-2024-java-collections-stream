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

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    // O(x)
    Map<Integer, Person> personMapById = new HashMap<>();
    for (Person person : persons) {
      personMapById.put(person.id(), person);
    }

    // O(y)
    List<Person> orderedPersons = new ArrayList<>();
    for (Integer id : personIds) {
      if (personMapById.containsKey(id)) {
        orderedPersons.add(personMapById.get(id));
      }
    }

    // O(x+y)
    return orderedPersons;
  }
}
