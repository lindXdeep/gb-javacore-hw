import java.io.IOException;
import java.util.Scanner;

/**
 * App XO
 */
public class App {

  private static int SIZE = 5;
  private static int WINSIZE = 4;

  private static boolean GAME;
  private static char[][] field;

  private static char DOT_HUMAN = 'X';
  private static char DOT_COMP = 'O';
  private static char DOT_EMPTY = ' ';

  public static void main(String[] args) {

    try {
      start();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    initField(DOT_EMPTY);

    gameLoop();
  }

  static {
    field = new char[SIZE][SIZE];
  }

  private static void initField(final char ch) {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        field[i][j] = ch;
      }
    }
  }

  private static void start() throws IOException, InterruptedException {

    int code;
    System.out.printf(" %s", "\n\n\tPress any key to star game!\n");
    while (-1 != (code = System.in.read())) {
      System.out.println("Start game!\n");
      Thread.sleep(500);
      GAME = true;
      break;
    }
  }

  private static void gameLoop() {
    do {
      printField();
      humanTurn();

      break;
    } while (GAME);
  }

  private static void humanTurn() {

    System.out.println("You turn: ");
    System.out.print("\ninput <y> <x>: ");

    try (Scanner sc = new Scanner(System.in)) {

      String line_x = "";
      String line_y = "";

      do {

        line_y = sc.next();
        line_x = sc.next();

      } while (!isCellValid(line_y, line_x));
    }
  }

  private static boolean isCellValid(final String line_y, final String line_x) {
    return false;
  }

  private static void printField() {

    int i = 0;
    for (; i < SIZE; i++) {

      if (i == 0) {
        System.out.printf("%3s", " ");
        int x = 0;
        do {
          System.out.printf("%4s", ++x );
        } while (x < SIZE);
        System.out.println("\n");
      }


      for (int j = 0; j < SIZE; j++) {

       


        if (j == 0)
          System.out.printf("%3s  ", i+1);

        System.out.printf("%2s %s", field[i][j], (j < SIZE - 1) ? "|" : "");
      }
      System.out.println();

      if (i < SIZE - 1) {
        for (int j = 0; j < SIZE; j++) {
          System.out.printf("%s", (j == 0) ? "     --- " : "--- ");
        }
      }

      System.out.println();
    }
    System.out.println();
  }
}