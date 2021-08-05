import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class App {

  /**
   * 1. Создать массив с набором слов (10-20 слов, должны встречаться
   * повторяющиеся).
   */
  private static String[] arrStr = {

      "Sale", "Mandamus", "abhorreant", "deseruisse", "mea",

      "at", "mea", "elit", "at", "persequeris",

      "Sale", "fuisset", "mea", "dicant", "cetero",

      "phaedrum", "opulo", "at", "mea", "nostrum" };

  public static void main(String[] args) {

    /**
     * 2. Найти и вывести список уникальных слов, из которых состоит массив
     * (дубликаты не считаем).
     */
    distinct(arrStr);

    /**
     * 3. Посчитать сколько раз встречается каждое слово.
     */
    countWords(arrStr);

    /**
     * Написать простой класс ТелефонныйСправочник, который хранит в себе список
     * фамилий и телефонных номеров.
     */
    PhoneBook phoneBook = new PhoneBook();

    phoneBook.add("lastname1", "111-222-333");
    phoneBook.add("lastname1", "111-222-444");
    phoneBook.add("lastname1", "111-222-555");

    phoneBook.add("lastname2", "112-222-333");
    phoneBook.add("lastname2", "113-222-444");
    phoneBook.add("lastname2", "114-222-555");

    phoneBook.add("lastname3", "212-222-333");
    phoneBook.add("lastname4", "313-222-444");
    phoneBook.add("lastname5", "214-222-555");

    System.out.println("lastname1: " + phoneBook.get("lastname1"));
    System.out.println("lastname2: " + phoneBook.get("lastname2"));
    System.out.println("lastname3: " + phoneBook.get("lastname3"));
    System.out.println("lastname4: " + phoneBook.get("lastname4"));
    System.out.println("lastname5: " + phoneBook.get("lastname5"));
  }

  private static void countWords(String[] arr) {

    Map<String, Integer> elms = new HashMap<>(arr.length);

    for (String str : arr) {
      if (!elms.containsKey(str)) {
        elms.put(str, 0);
      }
      elms.put(str, elms.get(str) + 1);
    }

    elms.forEach((k, v) -> System.out.println(k + " = " + v));

  }

  private static void distinct(final String[] arr) {
    showSet("Before:", Arrays.asList(arr));

    Set<String> sets = new HashSet<>(20);
    sets.addAll(Arrays.asList(arr));

    showSet("After:", sets);
  }

  private static <E> void showSet(final String comment, final Collection<E> c) {

    System.out.println("\n" + comment + "\n");

    int length = c.size();

    Iterator<E> iter = c.iterator();

    while (iter.hasNext()) {
      System.out.print(iter.next() + ", ");

      if (length-- % 5 == 0)
        System.out.println();
    }
    System.out.println();
  }
}