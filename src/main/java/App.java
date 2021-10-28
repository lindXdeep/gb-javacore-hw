public class App {
  public static void main(String[] args) {
    System.out.println("main");
  }

  public int[] newAfterFore(final int[] arr) {

    int i = arr.length;

    while (i-- > 0) {
      if (arr[i] == 4) {
        int j = i++ + 1;
        int[] newArr = new int[arr.length - j];
        while (i < arr.length)
          newArr[i - j] = arr[i++];
        return newArr;
      }
    }
    throw new RuntimeException();
  }
}