/**
 * Task 06
 */
public class Task006 {

  private static int[] arr = { 3, 5, 6, 8, 4, 7, 2, 5, 2, 1, 8, 4, 6, 2 };

  public static void main(String[] args) {

    int SIZE = arr.length;

    int min = arr[0];
    int max = arr[SIZE - 1];

    int idx = SIZE - 1;

    do {
      min = (arr[SIZE - idx] < min) ? arr[SIZE - idx] : min;
      max = (arr[idx - 1] > max) ? arr[idx - 1] : max;
    } while (--idx > 0);

    System.out.println("min: " + min);
    System.out.println("max: " + max);
  }
}