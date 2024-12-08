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
  // так же пустой лист. удалять запись тоже не нужно (сложность удаления элемента списка O(n), а сложность skip O(1),
  // следовательно пропускаем skip(1)
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
        .skip(1)
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  // более лаконично вернем Set
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }


  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  //не понятно почему secondName+firstName+secondName? если опечатка, то исправил. вместо if используем stream
  public String convertPersonToString(Person person) {
    return Stream.of(person.firstName(), person.middleName(), person.secondName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // лаконичнее возвращаем словарь требуемой конфы (с доработкой merge function)
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
        .collect(Collectors
            .toMap(Person::id,
                this::convertPersonToString,
                (a, b) -> a));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // проверка anyMatch stream'a persons1
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> persons2Set = new HashSet<>(persons2);
    return persons1.stream().anyMatch(persons2Set::contains);
  }

  // Посчитать число четных чисел
  //избавимся от переменной. forEach - O(n), a count() - O(1)
  public long countEven(Stream<Integer> numbers) {
     return numbers.filter(x -> x % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /* псевдосортировка в Set начинается от того, что Hash от Integer равен самому числу. Количество bucket минимум на треть
  превосходит количество элементов множества. Адрес bucket расчитывается (Hash mod buckets) в последовательности
  возрастающих на единицу чисел любого размера, количество bucket будет как минимум на 33% больше величины максимального элемента
  поэтому адрес всегда будет совпадать со значением числа, а поэтому выводиться в возрастающем порядке.
  */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
