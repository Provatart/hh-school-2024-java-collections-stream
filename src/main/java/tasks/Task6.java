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
    Map<Integer, Area> areasMap = areas.stream().collect(Collectors.toMap(Area::getId, a->a));
    for (Person person : persons) {
      Set<Integer> setOfPersonRegions = personAreaIds.get(person.id());
      for (Integer areaId : setOfPersonRegions) {
        result.add(getString(areasMap.get(areaId),person));
      }
    }
    return result;
  }
  public static String getString(Area area, Person person) {
    return "%s - %s".formatted(person.firstName(), area.getName());
  }
}
