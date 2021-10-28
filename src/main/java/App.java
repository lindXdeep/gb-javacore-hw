import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class App {
  
  public static final int CARS_COUNT = 4;

  public static int WINNER = 1;
  public static final CyclicBarrier CD_BARRIER = new CyclicBarrier(CARS_COUNT);
  public static final CountDownLatch CDL_START = new CountDownLatch(CARS_COUNT);
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

    for (int i = 0; i < cars.length; i++) {
      new Thread(cars[i]).start();
    }

    CDL_START.await();
    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

    CDL_FINISH.await();
    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
  }
}