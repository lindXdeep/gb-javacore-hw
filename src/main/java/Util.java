import java.util.Iterator;
import java.util.List;

public class Util {

  public static <T> void printArr(T[] arr) {
    for (T t : arr) {
      System.out.print(t + " ");
    }
    System.out.println();
  }

  public static void printArr(List<Integer> listArr) {

    Iterator<Integer> it = listArr.iterator();

    while (it.hasNext()) {
      System.out.print(it.next() + " ");
    }
  }
}
