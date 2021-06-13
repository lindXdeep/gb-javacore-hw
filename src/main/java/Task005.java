/**
 * Task 05
 */
public class Task005 {

  public static void main(String[] args) {

    int[] arr = initArray(10, 1);

    for (int i : arr) {
      System.out.print(i + " ");
    }
  }

  public static int[] initArray(int len, final int initialValue){
    
    int[] arr = new int[len];

    do {
      arr[--len] = initialValue;
    } while (len > 0);

    return arr;
  }
}