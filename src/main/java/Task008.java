
/**
 * Task 08
 */
public class Task008 {

  private static int[] arr = { 10, 20, 30, 40, 50 };

  public static void main(String[] args) {

    System.out.print("source arr: ");
    printArray();

    System.out.print("move right: ");
    moveArray(2002);
    printArray();

    System.out.print("move left:  ");
    moveArray(-2002);
    printArray();
  }

  private static void moveArray(int val) {

    int SIZE = arr.length - 1;
    int tmp;

    int move = (val < 0) ? (val * -1) % SIZE : val % SIZE;

    do {
      if (val > 0) {

        tmp = arr[SIZE];
        for (int i = SIZE; i > 0; i--) {
          arr[i] = arr[i - 1];
        }
        arr[0] = tmp;

      } else {

        tmp = arr[0];
        for (int i = 0; i < SIZE; i++) {
          arr[i] = arr[i + 1];
        }
        arr[SIZE] = tmp;
      }

    } while (--move > 0);
  }

  private static void printArray() {
    for (int idx : arr) {
      System.out.printf(" %2d ", idx);
    }
    System.out.println();
  }
}