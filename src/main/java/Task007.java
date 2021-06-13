
/**
 * Task 07
 */
public class Task007 {

  private static int[] arr1 = { 2, 2, 2, 1, 2, 2, 10, 1 };
  private static int[] arr2 = { 1, 1, 1, 2, 1 };

  public static void main(String[] args) {

    System.out.println(checkBalance(arr1));
    System.out.println(checkBalance(arr2));

  }

  private static boolean checkBalance(final int[] arr) {

    if (sum(arr) % 2 != 0) {
      return false;
    }

    int SIZE = arr.length;

    int sum_left = 0;
    int sum_right = 0;

    int mediana = SIZE / 2;

    do {

      sum_left = 0;
      sum_right = 0;

      int r = mediana;
      int l = r - 1;

      while (l >= 0)
        sum_left += arr[l--];

      while (r < SIZE)
        sum_right += arr[r++];

      mediana = (sum_left < sum_right) ? mediana + 1 : mediana - 1;

    } while (sum_left != sum_right);

    showMediana(arr, mediana);

    return false;
  }

  private static void showMediana(final int[] arr, final int mediana) {
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%2d", arr[i]);
      if (i == mediana) {
        System.out.print(" ||| ");
      }
    }
    System.out.print(" \t");
  }

  private static int sum(final int[] arr) {

    int sum = 0;

    for (int i : arr) {
      sum += i;
    }
    return sum;
  }
}