import entities.Human;
import obstacles.RunningTrack;
import obstacles.Wall;
public class App {

  public static void main(String[] args) {

    RunningTrack track = new RunningTrack(100);
    Wall wall = new Wall(2);
    
    Human human1 = new Human(100, 1);
    
    if (human1.run(track)) {
      System.out.println("Могу пробежать");
    }else {
      System.out.println("Не могу пробежать");
    }

    if (human1.jump(wall)) {
      System.out.println("Могу перепрыгнуть");
    }else {
      System.out.println("Не могу перепрыгнуть");
    }
  }
}