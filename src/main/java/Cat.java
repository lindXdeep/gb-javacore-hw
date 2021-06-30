public class Cat extends Animal {

  private boolean fedup;
  private final int FULL_EAT;
  private int eat;

  public Cat(final String name, int run, int swim) {
    super(name, run, swim);
    this.FULL_EAT = 0;
  }

  public Cat(final String name, int eat) {
    super(name, 0, 0);
    this.FULL_EAT = eat;
  }

  public boolean eat(Plate plate) {

    if ((FULL_EAT - eat) > plate.getEat()) {
      return false;
    }

    plate.popEat((FULL_EAT - eat));

    fedup = true;
    return fedup;
  }
}
