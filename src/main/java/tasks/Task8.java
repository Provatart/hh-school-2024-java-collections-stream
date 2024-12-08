package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

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
    Set<Resume> resumes = personService.findResumes(persons.stream().map(Person::id).collect(Collectors.toSet()));
    Map<Integer, Set<Resume>> resumeMap = resumes.stream().collect(Collectors.toMap(Resume::personId,
        resume -> new HashSet<>(Set.of(resume)),
        (a, b) -> {
          a.addAll(b);
          return a;
        }));
    return persons.stream()
        .map(person -> new PersonWithResumes(person, resumeMap.getOrDefault(person.id(), Collections.emptySet())))
        .collect(Collectors.toSet());
  }
}
