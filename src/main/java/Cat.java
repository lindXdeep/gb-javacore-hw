/**
 * Cat
 */
public class Cat extends Animal {

  private boolean fullness = false;
  private final int loads;
  private int eat;

  public Cat(final int loads) {
    this.loads = loads;
  }

  public int getLoads() {
    return loads;
  }

  @Override
  public void run(int val) {
    System.out.println("run: " + (val <= 200));
  }

  @Override
  public void jump(float val) {
    System.out.println("jump: " + (val < 2.0f));
  }

  @Override
  public void swim(int val) {
    System.out.println("swim: " + (val == 0));
  }

  public void eat(Plate plate) {

    if (!getStatus() && plate.getValue() >= (loads - eat)) {
      eat = plate.getEat(loads - eat);
    }else{
      eat =+ plate.getEat(loads);
    }

    checkFill();
  }

  private void checkFill() {
    fullness = (eat == loads) ? true : false;
  }

  public boolean getStatus() {
    return fullness;
  }

  public int getEat() {
    return eat;
  }
}
