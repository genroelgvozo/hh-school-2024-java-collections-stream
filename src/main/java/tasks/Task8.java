package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Map<Integer, Person> personIdMap = persons.stream().collect(Collectors.toMap(Person::id, Function.identity()));
    Set<Resume> resumes = personService.findResumes(personIdMap.keySet());
    Map<Integer, Set<Resume>> resumePersonIdMap = resumes.stream().collect(groupingBy(Resume::personId, toSet()));
    Set<PersonWithResumes> result = new HashSet<>();

    for (Integer curPersonId : personIdMap.keySet()) {
      result.add(new PersonWithResumes(personIdMap.get(curPersonId), resumePersonIdMap.getOrDefault(curPersonId, Set.of())));
    }

    return result;
  }
}
