import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * App
 */
public class App {

  public static void main(String[] args) {

    // 1. Написать метод, который меняет два элемента массива местами.
    Integer[] array1 = new Integer[] { 10, 20, 30, 40, 50 };
    String[] array2 = new String[] { "10", "20", "30", "40", "50" };
    swap(array1, 1, 2);
    swap(array2, 0, 2);

    Util.printArr(array1);
    Util.printArr(array2);

    // 2. Написать метод, который преобразует массив в ArrayList;
    List<Integer> listArr = toArrayList(array1);
    Util.printArr(listArr);
  }

  public static <T> void swap(T[] arr, int a, int b) {
    T tmp = arr[a];
    arr[a] = arr[b];
    arr[b] = tmp;
  }

  public static <T> ArrayList<T> toArrayList(T[] arr) {
    ArrayList<T> a = new ArrayList<>();
    for (T t : arr)
      a.add(t);
    return a;
  }
}