abstract class Animal {

  private String name;
  private int MAX_RUN;
  private int MAX_SWIM;

  public Animal(final String name) {
    this.name = name;
  }

  public Animal(String name, int MAX_RUN, int MAX_SWIM) {
    this.name = name;
    this.MAX_RUN = MAX_RUN;
    this.MAX_SWIM = MAX_SWIM;
  }

  public boolean run(final Obstacle road) {

    int val = road.getLength();

    if (val <= MAX_RUN) {
      System.out.println(name + " пробежал " + val + " м.");
      return true;
    } else {
      System.out.printf("%s %2d %s\n", name, val, " м. пробежать не может");
      return false;
    }
  }

  public boolean swim(final Obstacle pool) {

    int val = pool.getLength();

    if (this.MAX_SWIM == 0) {
      System.out.println(name + " плавать не умееет.");
      return false;

    } else if (val <= this.MAX_SWIM) {
      System.out.println(name + " проплыл " + val + " м.");
      return true;

    } else {
      System.out.printf("%s %2d %s\n", name, val, " м. проплыть не может");
      return false;
    }
  }
}
