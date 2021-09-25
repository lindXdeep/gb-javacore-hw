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

    printArr(array1);
    printArr(array2);

  }

  public static <T> void swap(T[] arr, int a, int b) {
    T tmp = arr[a];
    arr[a] = arr[b];
    arr[b] = tmp;
  }

  public static <T> void printArr(T[] arr) {
    System.out.println();
    for (T t : arr) {
      System.out.print(t + " ");
    }
    System.out.println();
  }
}