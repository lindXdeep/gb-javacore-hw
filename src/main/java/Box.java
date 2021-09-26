import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {

  private List<T> fruits;

  public Box() {
    this.fruits = new ArrayList<>();
  }

  public List<T> getFruits() {
    return this.fruits;
  }

  public void addFruit(T fruit) {
    this.fruits.add(fruit);
  }

  public double getWeight() {
    return (fruits.size() > 0) ? (fruits.size() * fruits.get(0).getWeight()) : 0;
  }

  public boolean compare(final Box<?> box) {
    return getWeight() == box.getWeight();
  }

  public void pourAll(Box<? super T> another) {
    if (!this.equals(another))
      for (T t : fruits)
        another.addFruit(t);
    fruits.clear();
  }
}
