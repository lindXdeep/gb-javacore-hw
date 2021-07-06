
public class Console extends Game {

  public Console(int fieldfieldSize, int winfieldSize) {

    super(fieldfieldSize, winfieldSize);

    gameLoop();
  }

  protected void gameLoop() {

    printConsoleField();
  }

  private void printConsoleField() {

    int i = 0;
    for (; i < fieldSize; i++) {

      if (i == 0) {
        System.out.printf("%3s", " ");
        int x = 0;
        do {
          System.out.printf("%4s", ++x);
        } while (x < fieldSize);
        System.out.println("\n");
      }

      for (int j = 0; j < fieldSize; j++) {

        if (j == 0)
          System.out.printf("%3s  ", i + 1);

        System.out.printf("%2s %s", field[i][j], (j < fieldSize - 1) ? "|" : "");
      }
      System.out.println();

      if (i < fieldSize - 1) {
        for (int j = 0; j < fieldSize; j++) {
          System.out.printf("%s", (j == 0) ? "     ——— " : "——— ");
        }
      }

      System.out.println();
    }
    System.out.println();
  }
}
