/**
 * HomeWorkApp
 */
public class HomeWorkApp {

  private static String[] words = {
    "Orange",
    "Banana",
    "Apple"
  };

  public static void main(String[] args) {
    printThreeWords();
  }

  public static void printThreeWords(){
    for (String str : words) {
      System.out.println(str);
    }
  }
}
