package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    List<ApiPersonDto> apiPersonDtos = new ArrayList<>();
    
    for (Person person : persons) {
      ApiPersonDto dto = personConverter.convert(person);
      Integer areaId = personAreaIds.get(person.id());
      if (areaId != null) {
        dto.setAreaId(areaId);
      }
      apiPersonDtos.add(dto);
    }

    return apiPersonDtos;
  }
}
