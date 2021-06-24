public class Dog extends Animal {

  private int run;

  public Dog(final int run) {
    this.run = run;
  }

  @Override
  public void run(int val) {
    System.out.println("run: " + (val <= run));
  }

  @Override
  public void jump(float val) {
    System.out.println("jump: " + (val <= 0.5f));
  }

  @Override
  public void swim(int val) {
    System.out.println("swim: " + (val < 10));
  }
}