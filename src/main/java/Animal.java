/**
 * Animal
 */
abstract class Animal {

  String m = "метров";

  public void run(final int val) {
    System.out.println("бежать: " + val + m);
  };

  public void swim(final int val) {
    System.out.println("плыть: " + val + m);
  };

  public void jump(final float val) {
    System.out.println("перепрыгивать препятствие: " + val + m);
  };
}