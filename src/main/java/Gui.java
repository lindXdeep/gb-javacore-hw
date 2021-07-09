import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Gui extends Game {

  private JFrame frame;

  public Gui(int fieldSize, int winSize) {
    super(fieldSize, winSize);

    frame = new JFrame();
    frame.setTitle("Tic Tac Toe");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
    frame.add(new Panel(fieldSize, winSize, field));
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);
  }

  @Override
  protected void gameLoop() {
    
  }
}
