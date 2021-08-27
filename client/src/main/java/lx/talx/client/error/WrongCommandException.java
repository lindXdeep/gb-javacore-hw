package lx.talx.client.error;

public class WrongCommandException extends RuntimeException {
  public WrongCommandException() {
    this("Error: No such command exists");
  }

  public WrongCommandException(String str) {
    super(str);
  }
}
