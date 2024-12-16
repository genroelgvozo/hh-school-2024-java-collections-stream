package tasks;

import common.Area;
import common.Person;

import java.util.*;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Set<String> descriptions = new HashSet<>();
    for (Person person : persons) {
      for (Integer areaId : personAreaIds.get(person.id())) {
        Optional<Area> area = areas.stream()
                .filter(a -> a.getId().equals(areaId))
                .findFirst();
        String decription = person.firstName() + " - " + area.get().getName();
        descriptions.add(decription);
      }
    }
    System.out.println(descriptions);
    return descriptions;
  }
}
