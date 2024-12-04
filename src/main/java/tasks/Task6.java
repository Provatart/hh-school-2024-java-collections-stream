package tasks;

import common.Area;
import common.Person;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    Set<String> result = new HashSet<>();
    Map<Integer, String> areasMap = areas.stream().collect(Collectors.toMap(o -> o.getId(), o -> o.getName()));
    for (Person person : persons) {
      Set<Integer> setOfPersonRegions = personAreaIds.get(person.id());
      for (Integer areaId : setOfPersonRegions) {
        result.add(person.firstName() + " - " + areasMap.get(areaId));
      }
    }
    return result;
  }
}
