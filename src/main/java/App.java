import java.util.concurrent.CountDownLatch;

public class App {
  
  public static final int CARS_COUNT = 4;

  public static final CountDownLatch CDL_FINISH = new CountDownLatch(CARS_COUNT);

  public static void main(String[] args) throws InterruptedException {

    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

    Race race = new Race(

        new Road(60),

        new Tunnel(),

        new Road(40)

    );

    Car[] cars = new Car[CARS_COUNT];

    for (int i = 0; i < cars.length; i++) {
      cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
    }

    System.out.println("Start");

    for (int i = 0; i < cars.length; i++) {
      new Thread(cars[i]).start();
    }

    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");


    CDL_FINISH.await();
    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
  }
}