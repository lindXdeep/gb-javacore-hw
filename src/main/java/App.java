/**
 * App
 */
public class App {

  private static int FIELD_SIZE = 5;
  private static int WIN_SIZE = 4;

  private static String flag;
  private static boolean isConsoleGame;

  private static Game game;

  private static boolean isSillyMode = true;

  public static void main(String... args) {

    if (args.length != 0 && args.length == 1) {
      isConsoleGame = chkGameMode(args[0]);
    }

    if (isConsoleGame) {
      game = new Console(FIELD_SIZE, WIN_SIZE);
    } else {
      game = new Gui(FIELD_SIZE, WIN_SIZE);
    }
  }

  public static boolean chkGameMode(final String flag) {

    if (flag.equals("--help")) {

      System.out.printf("\n %s \n\t %s \n\t %s \n", "Available Commands:",
          "--help \t Show help for XO commands and flags", "--console \t run console mode game");
      System.exit(0);
    } else if (flag.equals("--console")) {

      return true;

    } else {
      System.out.printf("\n %s \n\n %s \n\n", "Error: unknown flag: " + flag,
          "Use \"xo --help\" for to see the global flags.");
      System.exit(0);
    }
    return false;
  }
}