/**
 * App
 */
public class App {

  private static int[] array = { 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 };

  public static void main(String[] args) {
    
    for (int i = 0; i < args.length; i++) {
      array[i] = array[i] ^ 1;
    }

    for (int i : array) {
      System.out.println(i + " ");
    }
  }
}