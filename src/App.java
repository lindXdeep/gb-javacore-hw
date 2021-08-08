import java.util.Arrays;

/**
 * Многопоточность
 */
public class App {

  private static final int SIZE = 1_000_000;

  public static void main(String[] args) {

    new Thread(() -> {

      try {
        System.out.println("Method one:\t" + methodOne(createAndFillArray(SIZE)) + " ms");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }).start();

    new Thread(() -> {

      try {
        System.out.println("Method two:\t" + methodTwo(createAndFillArray(SIZE)) + " ms");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }).start();
  }

  private static long methodOne(float[] arr) throws InterruptedException {

    long startTime = System.nanoTime();

    int i = SIZE;
    while (i-- > 0)
      arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

    long endTime = System.nanoTime();

    return (endTime - startTime) / 1_000_000;
  }

  private static long methodTwo(float[] arr) throws InterruptedException {

    int i = SIZE;
    int half = SIZE / 2;

    long startTime = System.nanoTime();

    Thread th1 = new Thread(() -> {

      float[] tmpLeft = new float[half];
      System.arraycopy(arr, 0, tmpLeft, 0, half);

      int j = half;
      while (j-- > 0)
        tmpLeft[j] = (float) (arr[j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));

      System.arraycopy(tmpLeft, 0, arr, 0, half);
    });

    Thread th2 = new Thread(() -> {

      float[] tmpRigth = new float[half];
      System.arraycopy(arr, half, tmpRigth, 0, half);

      int j = half;
      while (j-- > 0)
        tmpRigth[j] = (float) (arr[j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
      
      System.arraycopy(tmpRigth, 0, arr, half, half);
    });

    th1.start();
    th2.start();

    long endTime = System.nanoTime();

    return (endTime - startTime) / 1_000_000;
  }

  public static float[] createAndFillArray(final int size) {
    float[] arr = new float[size];
    Arrays.fill(arr, 1.f);
    return arr;
  }
}