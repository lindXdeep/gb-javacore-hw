import entities.Entity;
import obstacles.Obstacle;
import obstacles.RunningTrack;
import obstacles.Wall;

public class AthleticField {
  public void test(final Obstacle obstacle, final Entity entity) {

    if (obstacle instanceof RunningTrack) {

      if (entity.run((RunningTrack) obstacle)) {
        System.out.println("Успешно пробежал");
      } else {
        System.out.println("Не смог пробежать");
      }

    } else {

      if (entity.jump((Wall) obstacle)) {
        System.out.println("Успешно перепрыгнул");
      } else {
        System.out.println("не смог перепрыгнуть");
      }

    }
  }

  public void test(final Obstacle[] obstacles, final Entity[] entities) {

    for (int i = 0; i < entities.length; i++) {

      for (int j = 0; j < obstacles.length; j++) {

        if (obstacles[j] instanceof RunningTrack) {

          if (entities[i].run((RunningTrack) obstacles[j])) {
            System.out.println(entities[i].getName() + ": " +   "Успешно пробежал");
          } else {
            System.out.println(entities[i].getName() + ": " + "Не смог пробежать");
            break;
          }

        } else {
          if (entities[i].jump((Wall) obstacles[j])) {
            System.out.println(entities[i].getName() + ": " + "Успешно перепрыгнул");
          } else {
            System.out.println(entities[i].getName() + ": " + "не смог перепрыгнуть");
            break;
          }
        }
      }
    }
  }
}
