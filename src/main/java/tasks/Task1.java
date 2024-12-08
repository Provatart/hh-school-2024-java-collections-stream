package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
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
    Map<Integer, Person> resultMap=new HashMap<>();
    for (Person person : persons) {
      resultMap.put(person.id(), person);
    }
    List<Person> resultList = new ArrayList<>();
    for (Integer personId : personIds) {
      resultList.add(resultMap.get(personId));
    }
    return resultList;
  }
}
/* Асимптотика: заполнение словаря O(n), заполнение результирующего списка O(n). Итого O(2n) = o(n)
 */
