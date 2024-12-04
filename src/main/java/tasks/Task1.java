package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    return persons.stream()
        .sorted(Comparator.comparingInt(person -> personIds.indexOf(person.id())))
        .collect(Collectors.toList());
  }
}
/* Асимптотика: главным сортировочным алгоритмом, который используется в
stream API, является TimSort, worst case которого O(n log n)
 */