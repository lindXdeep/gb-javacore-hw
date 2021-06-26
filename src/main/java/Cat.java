public class Cat extends Animal {

  private final int FEDUP;

  
  public Cat(final String name, int run, int swim, int fedup) {
    super(name, run, swim);
    this.FEDUP = fedup;
  }
}
