package entities;
import obstacles.RunningTrack;
import obstacles.Wall;

public interface Entity {

  boolean run(final RunningTrack track);

  boolean jump(final Wall Wall);

  String getName();
}
