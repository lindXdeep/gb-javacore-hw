import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * App
 */
public class App {

  public static Plate plate = new Plate(100);

  public static Cat[] cats = {
    new Cat("Cat1", 130),
    new Cat("Cat2", 20),
    new Cat("Cat3", 115),
    new Cat("Cat4", 45),
    new Cat("Cat5", 110),
  };

  public static void main(String[] args) {

    plate.setEat(350);

    for (Cat cat : cats) {
      System.out.printf("%s %s\n", cat.getName(), cat.isFedUp() ? " сыт" : " голоден" );
    }

    System.out.println("-----");

    for (int i = 0; i < cats.length; i++) {
      cats[i].eat(plate);
    }

    for (Cat cat : cats) {
      System.out.printf("%s %s\n", cat.getName(), cat.isFedUp() ? " сыт" : " голоден" );
    }
  }
}