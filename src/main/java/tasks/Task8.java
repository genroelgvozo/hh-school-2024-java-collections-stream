package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    Map<Integer, Person> personById = persons.stream().collect(Collectors.toMap(Person::id, person -> person));


    Map<Person, Set<Resume>> personsResumeById = personService
            .findResumes(personById.keySet())
            .stream()
            .collect(Collectors.groupingBy(resume -> personById.get(resume.personId()), Collectors.toSet()));


    Map<Person, Set<Resume>> EnrichedPersonsWithoutResumes = persons.stream()
            .filter(person -> !personsResumeById.containsKey(person))
            .collect(Collectors.toMap(person -> person, person -> new HashSet<>()));

    return Stream.concat(personsResumeById.entrySet().stream(), EnrichedPersonsWithoutResumes.entrySet().stream())
            .map(personWithResumes -> new PersonWithResumes(personWithResumes.getKey(), personWithResumes.getValue()))
            .collect(Collectors.toSet());

  }
}
