import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable {

  private static int CARS_COUNT;

  static {
    CARS_COUNT = 0;
  }

  private Race race;
  private int speed;
  private String name;

  public Car(Race race, int speed) {
    this.race = race;
    this.speed = speed;
    this.name = "Участник #" + ++CARS_COUNT;
  }

  public int getSpeed() {
    return this.speed;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public void run() {
    try {
      System.out.println(this.name + " готовится");
      Thread.sleep(500 + (int) (Math.random() * 800));
      System.out.println(this.name + " готов");

      try {
        App.CD_BARRIER.await();
      } catch (BrokenBarrierException | InterruptedException e) {
        e.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    App.CDL_START.countDown();

    for (int i = 0; i < race.getStages().size(); i++) {
      race.getStages().get(i).go(this);
    }

    if (win() == 0) {
      System.out.println(this.getName() + " - WIN");
    }

    App.CDL_FINISH.countDown();
  }

  private synchronized int win() {
    return App.WINNER--;
  }
}
