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
        .flatMap(person -> {
          Set<Integer> areaIds = personAreaIds.getOrDefault(person.id(), Collections.emptySet());
          return areaIds.stream()
              .map(areaMap::get) // получаем арейки
              /*
                   .filter(Objects::nonNull) вообще по условию у всех есть хотя бы одна арейка. т е не имеет смысла это проверять.
                   я же их отфильтровала по инерции... хотя с точки зрения информации о пользователе отсутствие у него арейки
                   еще не значит, что про него надо забыть, а скорее что нужно получить эту информацию(
               */
              .map(area -> formatPersonAreaDescription(person, area));
        })
        .collect(Collectors.toSet());
  }

  private static String formatPersonAreaDescription(Person person, Area area) {
    return person.firstName() + " - " + area.getName();
    /* возможно, приватным сделано для того, чтобы потом кто-либо в классах потомках не поменял местами например
    имя и арейку тем самым поломав наш начальный класс и те функции что уже были выстроены исходя из этого порядка
     */
  }
}
