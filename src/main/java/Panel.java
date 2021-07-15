import java.awt.Font;

import javax.swing.JPanel;

public class Panel extends JPanel {

  private Font fontScreen = new Font("Arial Narrow", Font.BOLD, 40);
	private Font fontResult = new Font("Arial Narrow", Font.BOLD, 50);
	private Font fontNums = new Font("Arial", Font.BOLD, 25);

  private char[][] field;

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
