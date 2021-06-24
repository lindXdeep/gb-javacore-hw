public class Plate {

  private int capacity;
  private int eat;

  public Plate(final int capacity) {
    this.capacity = capacity;
  }

  public void setEat(final int eat) {
    if ((this.capacity - this.eat) >= eat) {
      this.eat += eat;
    } else {
      this.eat = capacity;
    }
  }

  public void addEat(final int eat) {
    this.eat =+ eat;
  }

  public int getEat() {
    if (this.eat > 0)
      return this.eat--;
    return 0;
  }

  public int getEat(final int val) {

    if (this.eat > 0 && val <= eat) {
      this.eat = eat - val;
      return val;
    } else if (this.eat > 0 && val > eat) {
      int tmp = eat;
      eat = 0;
      return tmp;
    }
    return 0;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getValue() {
    return eat;
  }
}
