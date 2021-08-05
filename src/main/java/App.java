import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * App
 */
public class App {

  private static String[] arrStr = {

      "Sale", "Mandamus", "abhorreant", "deseruisse", "mea",

      "at", "mea", "elit", "at", "persequeris",

      "Sale", "fuisset", "mea", "dicant", "cetero",

      "phaedrum", "opulo", "at", "mea", "nostrum" };

  public static void main(String[] args) {

    /**
     * Создать массив с набором слов (10-20 слов, должны встречаться
     * повторяющиеся). Найти и вывести список уникальных слов, из которых состоит
     * массив (дубликаты не считаем).
     */

    show("Before:", Arrays.asList(arrStr));

    Set<String> sets = new HashSet<>(20);
    sets.addAll(Arrays.asList(arrStr));

    show("After:", sets);
  }

  private static <E> void show(final String comment, final Collection<E> c) {

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