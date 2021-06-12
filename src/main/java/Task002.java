/**
 * App
 */
public class Task002 {

  private static int[] arr = new int[100];

  public static void main(String[] args) {

    for (int i = 0; i < arr.length;) {
      arr[i] = ++i;
    }

    printArray();
  }

  public static void printArray() {
    for (int i : arr) {
      System.out.printf(" %3d", i);
      if (i % 10 == 0) {
        System.out.println();
      }
    }
  }
}