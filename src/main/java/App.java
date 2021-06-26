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
    new Cat("Cat1", 300, 0),
    new Cat("Cat2", 200, 0),
    new Cat("Cat3", 150, 0),
    new Cat("Cat4", 450, 0),
    new Cat("Cat5", 100, 0),
  };

  public static Dog[] dogs = {
    new Dog("Dog1", 300, 10),
    new Dog("Dog2", 500, 10),
    new Dog("Dog3", 400, 20),
    new Dog("Dog4", 650, 30),
    new Dog("Dog5", 450, 25),
  };

  public static Pool[] pools = {
    new Pool(20),
    new Pool(30),
    new Pool(40),
    new Pool(50),
    new Pool(60),
    new Pool(70)
  };

  public static Road[] roads = {
    new Road(200),
    new Road(300),
    new Road(400),
    new Road(500),
    new Road(600),
    new Road(700)
  };

  public static void main(String[] args) {

    System.out.printf("\n Котов: %s \n", cats.length);
    System.out.printf("\n Собак: %s \n", dogs.length);
    System.out.println("\n----------------------------\n");

    System.out.println("Dog challange: ");
    challange("Забег: ", roads, dogs);

    System.out.println("Cat challange: ");
    challange("Забег: ", roads, cats);

    System.out.println("Dog challange: ");
    challange("Заплыв: ", pools, dogs);

    System.out.println("Cat challange: ");
    challange("Заплыв: ", pools, cats);
  }

  public static void challange(final String challangeName, final Obstacle[] obstacles, Animal[] animals){

    for (int j = 0; j < obstacles.length; j++) {

      if (animals.length == 0) {
        return;
      }else {
        System.out.println("\n\t" + challangeName + (j+1) + ": ");
      }

      List<Animal> animalz = new ArrayList<>(); 

      for (int i = 0; i < animals.length; i++) {

        if (obstacles[0].getClass().getTypeName() == "Road") {
          if (animals[i].run(obstacles[j])) {
            animalz.add(animals[i]);
          }
        }

        if (obstacles[0].getClass().getTypeName() == "Pool") {
          if (animals[i].swim(obstacles[j])) {
            animalz.add(animals[i]);
          }
        }
      }

      animals = new Animal[animalz.size()];
      for (int i = 0; i < animals.length; i++) {
        animals[i] = animalz.get(i);
      }

      System.out.println();
    }
  }
}