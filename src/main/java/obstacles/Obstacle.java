package obstacles;
public abstract class Obstacle {

  private int length;

  public Obstacle(int length) {
    this.length = length;
  }

  public int getLength() {
    return this.length;
  }

  public void setLength(int length) {
    this.length = length;
  }
}
