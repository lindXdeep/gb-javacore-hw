public class App {
  public static void main(String[] args) {

    System.out.println("main");

    int[] test4_1 = new int[] { 1, 1, 1, 4, 4, 1, 4, 4 }; // true

    System.out.println(new App().containForeAndOne(test4_1));
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

  public boolean containForeAndOne(final int[] arr) {

    boolean change = false;
    final int BEGIN = arr[0];

    for (int i = 0; i < arr.length; i++) {

      if (arr[i] != BEGIN)
        change = true;

      if (arr[i] != 1 & arr[i] != 4 | i == arr.length - 1 & !change)
        return false;
    }
    return true;
  }
}