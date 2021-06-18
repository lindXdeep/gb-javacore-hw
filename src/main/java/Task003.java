/**
 * Task 03
 */
public class Task003 {

  private static int[] arr = { 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };

  public static void main(String[] args) {

    printArrray();

    for (int i = 0; i < arr.length; i++) {
      int j = arr[i];
      arr[i] = (j % 6 == j) ? j * 2 : j;
    }

    printArrray();
  }

  public static void printArrray() {

    for (int i : arr) {
      System.out.printf("%3d", i);
    }
    System.out.println();
  }
}