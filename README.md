# Практическое задание

1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив. 

   Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки. 

   Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.

> Написать набор тестов для этого метода (по 3-4 варианта входных данных).

```
Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
```

2. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы, то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).

```
[ 1 1 1 4 4 1 4 4 ] -> true
[ 1 1 1 1 1 1 ] -> false
[ 4 4 4 4 ] -> false
[ 1 4 4 1 1 4 3 ] -> false
```

```
import java.util.HashMap;
import java.util.Map;

public class App {
  public static void main(String[] args) {

    int[] arr = new int[] { 1, 2, 4, 4, 2, 3, 4, 1, 7 };

    App app = new App();

    System.out.println("--------");
    int[] a = app.newAfterFore(arr);
    for (int i : a) {
      System.out.print(i + " ");
    }

  }

  public int[] newAfterFore(final int[] arr) {

    boolean startFill = false;
    int i = 0;
    int s = 0;
    int j = 0;

    while (i++ < arr.length) {
      if (arr[i] == 4) {
        
        s = arr.length - i;
        j = arr.length - s;

        if (s != 0) {

          int[] newArr = new int[s];

          while (i < arr.length) {
            newArr[i - j] = arr[i++];
          }
          return newArr;
        }
      }
    }

    throw new RuntimeException();
  }
}
```

