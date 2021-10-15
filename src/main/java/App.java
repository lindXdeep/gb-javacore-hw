public class App {

  private volatile char tmp = 'A';
  private final Object monitor = new Object();

  public static void main(String[] args) {

    App app = new App();

    new Thread(() -> {
      try {
        app.a();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    new Thread(() -> {
      try {
        app.b();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();;

    new Thread(() -> {
      try {
        app.c();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();;
  }

  private synchronized void a() throws InterruptedException {
    for (int i = 0; i < 5; i++) {

      while (tmp != 'A')
        this.wait();

      System.out.print(tmp);
      tmp = 'B';
      this.notifyAll();
    }
  }

  private synchronized void b() throws InterruptedException {
    for (int i = 0; i < 5; i++) {

      while (tmp != 'B')
        this.wait();

      System.out.print(tmp);
      tmp = 'C';
      this.notifyAll();
    }
  }

  private synchronized void c() throws InterruptedException {
    for (int i = 0; i < 5; i++) {

      while (tmp != 'C')
        this.wait();

      System.out.print(tmp);
      tmp = 'A';
      this.notifyAll();
    }
  }
}