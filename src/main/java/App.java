/**
 * App
 */
public class App {

  public static void main(String[] args) {
    System.out.println(ariaSum(15, 10));
    System.out.println(ariaSum(10, 10));

    System.out.printf("%2d - %s \n", 0, !isPositiveOrNegativeNumber(0) ? " положительное" : "отрицательное");
    System.out.printf("%2d - %s \n", -10, !isPositiveOrNegativeNumber(-10) ? " положительное" : "отрицательное");
    System.out.printf("%2d - %s \n", 5, !isPositiveOrNegativeNumber(5) ? " положительное" : "отрицательное");
    
    printPositiveOrNegativeNumber(-5);
    printPositiveOrNegativeNumber(0);
    printPositiveOrNegativeNumber(10);
  }

  public static boolean ariaSum(final int a, final int b) {
    return (a + b) >= 10 & (a + b) <= 20;
  }

  public static boolean isPositiveOrNegativeNumber(final int x) {
    return x < 0;
  }

  public static void printPositiveOrNegativeNumber(final int x) {
    System.out.printf("%2d - %s \n", x, !isPositiveOrNegativeNumber(x) ? " положительное" : "отрицательное");
  }
}