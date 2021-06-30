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

  public static Cat[] cats = {
    new Cat("Cat1", 30),
    new Cat("Cat2", 20),
    new Cat("Cat3", 15),
    new Cat("Cat4", 45),
    new Cat("Cat5", 10),
  };

  public static void main(String[] args) {

    Plate plate = new Plate(100);

    plate.setEat(35);

    System.out.println(plate.getEat());

    cats[0].eat(plate);

    System.out.println(plate.getEat());

  }
}