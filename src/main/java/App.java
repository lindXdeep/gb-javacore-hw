import Server.Server;

/**
 * App
 */
public class App {

  private static final int PORT = 8181;
  public static void main(String[] args) {
    Server server = new Server(PORT);
    server.start();
  }
}