
/**
 * Task 004
 */
public class App {

  private static int SIZE = 7;
  private static int[][] arr = new int[SIZE][SIZE];

  public static void main(String[] args) {

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        arr[i][i] = 1;
        arr[i][SIZE - i - 1] = 1;
      }
    }

    printAtrray();
  }

  public static void printAtrray() {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        System.out.printf("%2d", arr[i][j]);
      }
      System.out.println();
    }
  }
}