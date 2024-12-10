package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Person.firstName - Area.name". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Map<Integer, String> areasMap = areas.stream()
            .collect(
                    Collectors.toMap(
                            s->s.getId(), // в качестве ключа - Id
                            s->s.getName() // считаем, что элементы уникальны
                    )
            );

    return persons.stream().
            flatMap( // создаем потоки внутри потока (и преобразуем)
                    person -> personAreaIds.get(person.id()) // получение areaIds для person
                            .stream() // делаем stream для каждого элемента
                            .map(areaId -> String.format("%s - %s", person.firstName(), areasMap.get(areaId)))) // формируем строку
            .collect(Collectors.toSet()); // преобразуем в Set (HashSet)
  }
}
