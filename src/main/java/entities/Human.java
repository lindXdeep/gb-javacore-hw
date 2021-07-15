package entities;


import obstacles.Obstacle;
import obstacles.RunningTrack;
import obstacles.Wall;

/**
 * Human
 */
public class Human implements Entity {

  private final int MAX_RUN;
  private final int MAX_JUMP;

  public Human(final int MAX_RUN, final int MAX_JUMP) {
    this.MAX_RUN = MAX_RUN;
    this.MAX_JUMP = MAX_JUMP;
  }

  @Override
  public boolean run(final RunningTrack track) {
    return track.getLength() <= MAX_RUN;
  }

  @Override
  public boolean jump(final Wall wall) {
    return wall.getLength() <= MAX_JUMP;
  }
}