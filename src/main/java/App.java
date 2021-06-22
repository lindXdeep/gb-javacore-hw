import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * App XO
 */
public class App {

  private static int SIZE = 5;
  private static int WINSIZE = 4;
  private static boolean SILLY_MODE = true;

  private static char DOT_HUMAN = '╳';
  private static char DOT_COMP = '◯';
  private static char DOT_EMPTY = ' ';

  private static boolean GAME;
  private static char[][] field;

  private static boolean turn_comp = false;
  private static boolean turn_human = false;

  private static int y_x;
  private static int x_y;
  private static int l_r;
  private static int r_l;

  public static void main(String[] args) {

    try {
      start();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    initField(DOT_EMPTY);
    printField();

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
      humanTurn();
      printField();

      if (checkWin(DOT_HUMAN)) {
        printField();
        System.out.println("\t Вы победили!!!\n");
        break;
      }

      checkDeadHeat();

      compTurn();
      printField();

      if (checkWin(DOT_COMP)) {
        printField();
        System.out.println("\n Вы проиграли!");
        break;
      }

    } while (GAME);
  }

  private static boolean checkWin(final char dot) {

    int checkWinCounter;

    for (int i = 0; i < SIZE; i++) {

      checkWinCounter = 0;

      for (int j = 0; j < SIZE; j++) {
        checkWinCounter = (field[i][j] == dot) ? checkWinCounter + 1 : checkWinCounter;
        if (checkWinCounter == WINSIZE) {
          initField(DOT_EMPTY);
          for (int w = WINSIZE; w > 0; w--) {
            field[i][w] = dot;
          }
          return true;
        }
      }
    }

    for (int i = 0; i < SIZE; i++) {

      checkWinCounter = 0;

      for (int j = 0; j < SIZE; j++) {
        checkWinCounter = (field[j][i] == dot) ? checkWinCounter + 1 : checkWinCounter;
        if (checkWinCounter == WINSIZE) {
          initField(DOT_EMPTY);
          for (int w = WINSIZE; w > 0; w--) {
            field[w][i] = dot;
          }
          return true;
        }
      }
    }

    for (int i = 0; i < SIZE; i++) {

      checkWinCounter = 0;

      checkWinCounter = (field[i][i] == dot) ? checkWinCounter + 1 : checkWinCounter;
      if (checkWinCounter == WINSIZE) {
        initField(DOT_EMPTY);
        for (int w = WINSIZE; w > 0; w--) {
          field[i][w] = dot;
        }
        return true;
      }
    }

    for (int i = 0; i < SIZE; i++) {

      checkWinCounter = 0;

      checkWinCounter = (field[i][SIZE - i -1] == dot) ? checkWinCounter + 1 : checkWinCounter;
      if (checkWinCounter == WINSIZE) {
        initField(DOT_EMPTY);
        for (int w = WINSIZE; w > 0; w--) {
          field[i][SIZE - w - 1] = dot;
        }
        return true;
      }
    }

    return false;
  }

  private static boolean checkPreWin(final char dot_me, final char dot_enemy) {

    int countPreWin;

    for (int i = 0; i < SIZE; i++) {

      countPreWin = 0;

      for (int j = 0; j < SIZE; j++) {
        countPreWin = (field[i][j] == dot_enemy) ? countPreWin + 1 : countPreWin;
      }
      if (countPreWin == WINSIZE - 1) {
        for (int j = 0; j < SIZE; j++) {
          if (field[i][j] == DOT_EMPTY) {
            field[i][j] = dot_me;
            return true;
          }
        }
      }
    }

    for (int j = 0; j < SIZE; j++) {

      countPreWin = 0;

      for (int i = 0; i < SIZE; i++) {
        countPreWin = (field[i][j] == dot_enemy) ? countPreWin + 1 : countPreWin;
      }
      if (countPreWin == WINSIZE - 1) {
        for (int i = 0; i < SIZE; i++) {
          if (field[i][j] == DOT_EMPTY) {
            field[i][j] = dot_me;
            return true;
          }
        }
      }
    }

    countPreWin = 0;

    for (int i = 0; i < SIZE; i++) {

      countPreWin = (field[i][i] == dot_enemy) ? countPreWin + 1 : countPreWin;

      if (countPreWin == WINSIZE - 1) {
        for (int j = 0; j < SIZE; j++) {
          if (field[j][j] == DOT_EMPTY) {
            field[j][j] = dot_me;
            return true;
          }
        }
      }
    }

    countPreWin = 0;

    for (int i = 0; i < SIZE; i++) {

      countPreWin = (field[i][SIZE - i - 1] == dot_enemy) ? countPreWin + 1 : countPreWin;

      if (countPreWin == WINSIZE - 1) {
        for (int j = 0; j < SIZE; j++) {
          if (field[j][SIZE - j - 1] == DOT_EMPTY) {
            field[j][SIZE - j - 1] = dot_me;
            return true;
          }
        }
      }
    }
    return false;
  }

  private static void checkDeadHeat() {

    GAME = false;

    for (char[] cs : field)
      for (char c : cs)
        if (c == DOT_EMPTY)
          GAME = true;

    if (GAME == false) {
      System.out.println("\t Ничья!\n");
      Runtime.getRuntime().exit(0);
    }
  }

  private static void compTurn() {

    if (checkPreWin(DOT_COMP, DOT_HUMAN)) {
      return;
    }

    Random rand = new Random();

    int x;
    int y;

    if (SILLY_MODE) {

      do {

        x = rand.nextInt(SIZE);
        y = rand.nextInt(SIZE);

      } while (!isCellValid(x, y));

      System.out.println("\nМой ход: [x = " + (x + 1) + "] [y = " + (y + 1) + "]\n");

      field[y][x] = DOT_COMP;

    } else {

    }
  }

  private static void humanTurn() {

    boolean stepDuration = true;

    System.out.println("You turn: ");
    System.out.print("\ninput <y> <x>: ");

    Scanner sc = new Scanner(System.in);

    String line_x = "";
    String line_y = "";

    do {

      line_y = sc.next();
      line_x = sc.next();

      if (!isCorrectInput(line_x, line_y)) {
        System.out.println("\nКоординаты [" + line_x + "] и [" + line_y + "] неверны!");
        System.out.print("\nВведите координаты через пробел границах от 1 до " + SIZE + ": ");
        stepDuration = true;
      } else {
        if (!isCellValid(Integer.parseInt(line_x) - 1, Integer.parseInt(line_y) - 1)) {
          System.out.println("\nЯчейка уже занята.");
          System.out.print("\ninput <y> <x>: ");
          stepDuration = true;
        } else {
          System.out.println("\nВаш ход: [x = " + line_x + "] [y = " + line_y + "]\n");

          field[Integer.parseInt(line_y) - 1][Integer.parseInt(line_x) - 1] = DOT_HUMAN;

          stepDuration = false;
          break;
        }
      }
    } while (stepDuration);
  }

  private static boolean isCellValid(final int y, final int x) {

    if ((y >= 0 & y < SIZE) && (x >= 0 & x < SIZE)) {
      return field[x][y] == DOT_EMPTY;
    }
    return false;
  }

  public static boolean isCorrectInput(final String y, final String x) {
    for (int i = 0; i < x.length(); i++) {
      if (!Character.isDigit(x.charAt(i))) {
        return false;
      }
    }

    for (int i = 0; i < y.length(); i++) {
      if (!Character.isDigit(y.charAt(i))) {
        return false;
      }
    }

    return (Integer.parseInt(x) >= 1 & Integer.parseInt(x) <= SIZE)
        & (Integer.parseInt(y) >= 1 & Integer.parseInt(y) <= SIZE);
  }

  private static void printField() {

    int i = 0;
    for (; i < SIZE; i++) {

      if (i == 0) {
        System.out.printf("%3s", " ");
        int x = 0;
        do {
          System.out.printf("%4s", ++x);
        } while (x < SIZE);
        System.out.println("\n");
      }

      for (int j = 0; j < SIZE; j++) {

        if (j == 0)
          System.out.printf("%3s  ", i + 1);

        System.out.printf("%2s %s", field[i][j], (j < SIZE - 1) ? "|" : "");
      }
      System.out.println();

      if (i < SIZE - 1) {
        for (int j = 0; j < SIZE; j++) {
          System.out.printf("%s", (j == 0) ? "     ——— " : "——— ");
        }
      }

      System.out.println();
    }
    System.out.println();
  }
}