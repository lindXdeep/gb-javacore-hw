/**
 * HomeWorkApp
 */
public class HomeWorkApp {

  private static String[] words = { "Orange", "Banana", "Apple" };

  public static void main(String[] args) {
    printThreeWords();
    checkSumSign();
    printColor();
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

  public static void printColor() {
    int value = 100;

    if (value <= 0) {
      System.out.println("Красный");
    }else if(value > 0 & value <= 100 ){
      System.out.println("Желтый");
    }else{
      System.out.println("Зеленый");
    }
  }
}
