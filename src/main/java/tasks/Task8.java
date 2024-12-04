package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

    Set<PersonWithResumes> personWithResumes = new HashSet<>();

    Set<Resume> personsResumes = new HashSet<>();

    for (Person person : persons) {

      for (Resume resume : resumes) {
        if (resume.personId() == person.id()) {
          personsResumes.add(resume);
        }
      }

      personWithResumes.add(new PersonWithResumes(person, new HashSet<>(personsResumes)));
      personsResumes.clear();
    }
    return personWithResumes;


  }
}
