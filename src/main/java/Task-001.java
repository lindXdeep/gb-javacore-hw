/**
 * App
 */
public class App {

  private static int[] array = { 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 };

  public static void main(String[] args) {

    showArray();

    for (int i = 0; i < array.length; i++) {
      array[i] = array[i] ^ 1;
    }

    showArray();
  }

  public static void showArray() {
    for (int i : array) {
      System.out.print(i + " ");
    }
    System.out.println();
  }
}