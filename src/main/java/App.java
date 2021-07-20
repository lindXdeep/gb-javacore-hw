import entities.Cat;
import entities.Entity;
import entities.Human;
import entities.Robot;
import obstacles.Obstacle;
import obstacles.RunningTrack;
import obstacles.Wall;

public class App {

  static AthleticField field = new AthleticField();

  static Obstacle[] obstacles = {
    new RunningTrack(200),
    new RunningTrack(100),
    new RunningTrack(80),
    new RunningTrack(50),
    new RunningTrack(20),
    new Wall(10),
    new Wall(7),
    new Wall(6),
    new Wall(4),
    new Wall(2),
    new Wall(1)
  };

  static Entity[] entities = {
    new Human(100, 2, "Человек"),
    new Cat(200, 4, "Кот"),
    new Robot(1000, 8, "Робот"),
  };

  public static void main(String[] args) {

    field.test(obstacles, entities);

  }
}