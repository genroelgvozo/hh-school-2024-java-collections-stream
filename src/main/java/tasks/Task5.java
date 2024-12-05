package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Задача 5
Расширим предыдущую задачу.
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 {

  private final PersonConverter personConverter;

  public Task5(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {
    Map<Person, Integer> personAreas = new LinkedHashMap<>();

    for (Person curPerson : persons) {
      Integer area = personAreaIds.get(curPerson.id());
      personAreas.put(curPerson, area);
    }

    return personAreas.entrySet().stream().map(x -> personConverter.convert(x.getKey(), x.getValue()))
        .collect(Collectors.toList());
  }
}
