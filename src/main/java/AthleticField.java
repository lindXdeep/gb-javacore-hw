import entities.Entity;
import obstacles.Obstacle;
import obstacles.RunningTrack;
import obstacles.Wall;

public class AthleticField {
  public void test(final Obstacle obstacle, final Entity entity) {

    if (obstacle instanceof RunningTrack) {

      if (entity.run((RunningTrack) obstacle)) {
        System.out.println("Могу пробежать");
      } else {
        System.out.println("Не могу пробежать");
      }

    } else {

      if (entity.jump((Wall) obstacle)) {
        System.out.println("Могу перепрыгнуть");
      } else {
        System.out.println("Не могу перепрыгнуть");
      }
      
    }
  }
}
