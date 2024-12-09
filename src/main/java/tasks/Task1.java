package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */

//Преобразование множества в словарь - О(m),
// плюс еще цикл(О(n)) => O(m + n) итоговая асимтотика

public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    Map<Integer, Person> personAndTheirIdMap = persons.stream()
            .collect(Collectors.toMap(Person::id, person -> person));

    List<Person> listOfOrderedPersons = new ArrayList<>();
    for (Integer id: personIds){
      if(personAndTheirIdMap.containsKey(id)){
        listOfOrderedPersons.add(personAndTheirIdMap.get(id));
      }
    }
    return listOfOrderedPersons;
  }
}
