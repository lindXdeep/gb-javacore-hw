import entities.Entity;
import entities.Human;
import obstacles.RunningTrack;
import obstacles.Wall;
public class App {

  public static void main(String[] args) {

    AthleticField field = new AthleticField();

    RunningTrack track = new RunningTrack(100);
    Wall wall = new Wall(2);
    
    Entity human1 = new Human(100, 1);
    
   field.test(track, human1);
   field.test(wall, human1);

  }
}