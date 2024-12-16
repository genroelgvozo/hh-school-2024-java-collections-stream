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
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    // Преобразуем Set<Person> в Map<Integer, Person> для быстрого доступа
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personMap = persons.stream()
            .collect(Collectors.toMap(Person::id, person -> person));

    // Формируем результирующий список, сохраняя порядок из personIds
    return personIds.stream()
            .map(personMap::get) // Получаем персону по ID
            .filter(Objects::nonNull) // Убираем null-значения, если ID не найден
            .collect(Collectors.toList());
  }
}