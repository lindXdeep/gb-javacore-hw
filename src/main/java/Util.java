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

  public static <T extends Fruit> void fillBox(final Box<T> box, T fruit, int n) {
    for (int i = 0; i < n; i++) {
      box.addFruit(fruit);
    }
  }

  public static <T extends Fruit> void showBox(Box<T> box) {

    if (box.getFruits().size() != 0) {
      System.out.print(box.getFruits().get(0).getClass().getName() + "'s: ");
    } else {
      System.out.println("box empty");
      return;
    }

    Iterator<?> it = box.getFruits().iterator();
    while (it.hasNext())
      System.out.print(((Fruit) it.next()).getWeight() + " ");
    System.out.println();
  }
}
