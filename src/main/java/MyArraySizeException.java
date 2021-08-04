public class MyArraySizeException extends ArrayIndexOutOfBoundsException{

  public MyArraySizeException(int index) {
    super("My Array index out of range: " + index);
  }

  public MyArraySizeException(String string) {
    super(string);
  }
}
