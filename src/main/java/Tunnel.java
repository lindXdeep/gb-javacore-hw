import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

  private Semaphore limit = new Semaphore(App.CARS_COUNT / 2);

  public Tunnel() {
    this.length = 80;
    this.description = "Тоннель " + length + " метров";
  }

  @Override
  public void go(Car c) {
    try {
      try {

        limit.acquire();

        System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
        System.out.println(c.getName() + " начал этап: " + description);
        Thread.sleep(length / c.getSpeed() * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {

        limit.release();
        
        System.out.println(c.getName() + " закончил этап: " + description);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}