/**
 * Plate
 */
public class Plate {

  private final int CAPACITY;
  private int eatCount;

  public Plate(int CAPACITY) {
    this.CAPACITY = CAPACITY;
  }

  public void setEat(int eatCount) {
    this.eatCount = eatCount;
  }

  public int getEat() {
    return this.eatCount;
  }

  public int popEat(int eat) {

    if (eatCount < eat) {
      return 0;
    }

    eatCount -= eat;

    return eat;
  }
}