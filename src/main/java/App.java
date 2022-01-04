/**
 * App
 */
public class App {

  public static void main(String[] args) {

    // 1.
    Class strClass = "Java".getClass();

    // 2.
    Class integerClass = Integer.class;
    Class strClass = String.class;
    Class strClass = int.class;
    Class voidClass = void.class;
    Class charArrayClass = char[].class;

    // 3.
    try {
      Class jdbcClass = Class.forName("org.sqlite.jdbc");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }
}