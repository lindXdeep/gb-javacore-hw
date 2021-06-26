/**
 * Plate
 */
public class Plate {

  private final  int CAPACITY; 
  private int zhranina;

  public Plate(int CAPACITY) {
    this.CAPACITY = CAPACITY;
  }

  public int getEat() {
    return this.zhranina;
  }

  public void setEat(int zhranina) {
    this.zhranina = zhranina;
  }
}