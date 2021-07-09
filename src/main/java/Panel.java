import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class Panel extends JPanel {

  Font fontScreen = new Font("Arial Narrow", Font.BOLD, 40);
	Font fontResult = new Font("Arial Narrow", Font.BOLD, 50);
	Font fontNums = new Font("Arial", Font.BOLD, 25);

  final int SIZE;
  final int WIN;

  Desing color = Desing.inut();

  public Panel(int size, int win, char[][] field) {

    SIZE = size;
    WIN = win;
    this.createGUI();
    this.setBackground(color.getColorBack());
  }

  private void createGUI() {

    
 
    
  
  }

}
