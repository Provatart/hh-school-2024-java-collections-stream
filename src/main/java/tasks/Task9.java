package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй

  // проверка на пустоту множества кажется лишней, так как при пустом входном списке после стрима вернется
  // так же пустой лист. удалять запись тоже не нужно, можно пропустить skip(1)
  public List<String> getNames(List<Person> persons) {
    /*
     if (persons.size() == 0) {
      return Collections.emptyList();
    }
    persons.remove(0);
    return persons.stream().map(Person::firstName).collect(Collectors.toList());
    */
       return persons.stream()
        .skip(1)
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)

  // более лаконично вернем Set
  public Set<String> getDifferentNames(List<Person> persons) {
    //return getNames(persons).stream().distinct().collect(Collectors.toSet());
    return new HashSet<>(getNames(persons));
  }


  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  //не понятно почему secondName+firstName+secondName? если опечатка, то исправил. вместо if используем stream
  public String convertPersonToString(Person person) {
    List<String> personList=List.of((""+person.firstName()), ""+person.middleName(),(""+person.secondName()));
    return personList.stream()
        .filter(x->!(Objects.equals(x, "null")))
        .collect(Collectors.joining(" "));

  /*  String result = "";
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.secondName() != null) {
      result += " " + person.secondName();
    }
    return result;*/
  }

  // словарь id персоны -> ее имя
  // лаконичнее возвращаем словарь требуемой конфы
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
//    Map<Integer, String> map = new HashMap<>(1);
//    for (Person person : persons) {
//      if (!map.containsKey(person.id())) {
//        map.put(person.id(), convertPersonToString(person));
//      }
//    }
    return persons.stream()
        .collect(Collectors
            .toMap(Person::id,
                Person::firstName));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // чтобы посчитать количество дублей, нужно сравнить сумму размеров списков и размер итогового списка (уникальных id)
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Long bothSize = Stream.concat(persons1.stream().map(Person::id),
            persons2.stream().map(Person::id))
        .distinct()
        .count();

//    boolean has = false;
//    for (Person person1 : persons1) {
//      for (Person person2 : persons2) {
//        if (person1.equals(person2)) {
//          has = true;
//        }
//      }
//    }
//    return has;
    return !(bothSize == (persons1.size()+ persons2.size()));
  }

  // Посчитать число четных чисел
  //избавимся от переменной
  public long countEven(Stream<Integer> numbers) {
    //count = 0;
    // numbers.filter(num -> num % 2 == 0).forEach(num -> count++);
    //return count;
    return numbers.filter(x->x%2==0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  // в данном случае snapshot это полностью автономный список сделанный из первоначальной версии integers
  //(не ссылка). изменение integers на snapshot не влияют.
  //если бы было вот так:

  //List<Integer> snapshot = new ArrayList<>();
  //snapshot=integers;
  //Collections.shuffle(integers);

  //то после shuffle snapshot бы тоже поменялся и assert был false
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
