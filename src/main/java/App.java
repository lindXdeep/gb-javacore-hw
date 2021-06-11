/**
 * App
 */
public class App {

  public static void main(String[] args) {
    System.out.println(ariaSum(15, 10));
    System.out.println(ariaSum(10, 10));
  }

  public static boolean ariaSum(final int a, final int b) {
    return (a + b) >= 10 & (a + b) <= 20;
  }
}