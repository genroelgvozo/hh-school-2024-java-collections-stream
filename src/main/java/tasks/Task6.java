package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    Map<Integer, Area> areaMap = areas.stream()
        .collect(Collectors.toMap(Area::getId, area -> area));
    return persons.stream()
        .flatMap(person -> getPersonDataTogether(person, personAreaIds, areaMap))
        .collect(Collectors.toSet());
  }
   private static Stream<String> getPersonDataTogether(Person person,
                                               Map<Integer, Set<Integer>> personAreaIds,
                                               Map<Integer, Area> areaMap) {

     Set<Integer> areaIds = personAreaIds.getOrDefault(person.id(), Collections.emptySet());
     return areaIds.stream()
         .map(areaMap::get)
         .filter(Objects::nonNull)
         .map(area -> person.firstName() + " - " + area.getName());
   }
}
