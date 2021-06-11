/**
 * HomeWorkApp
 */
public class HomeWorkApp {

  private static String[] words = { "Orange", "Banana", "Apple" };

  public static void main(String[] args) {
    printThreeWords();
    checkSumSign();
  }

  public static void printThreeWords() {
    for (String str : words) {
      System.out.println(str);
    }
  }

  public static void checkSumSign() {
    int a = 1;
    int b = 2;

    int c  = a + b;

    if (c >= 0) {
      System.out.println("Сумма положительная");
    }else{
      System.out.println("Сумма отрицательная");
    }
  }
}
