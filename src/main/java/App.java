public class App {

  public static final int SIZE = 4;

  public static String array[][] = new String[SIZE][SIZE];

  public static void main(String[] args) {

    fillArray(array);
    showArray(array);

    try {
      convertArrayToInt(array);
    } catch (MyArraySizeException | MyArrayDataException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void convertArrayToInt(final String[][] arr) throws MyArraySizeException, MyArrayDataException {

    if (arr.length > 4) {
      throw new MyArraySizeException("Exception: Array" + arr.getClass().getSimpleName() + " out of index");
    }

    for (int i = 0; i < arr.length * arr.length; i++) {
      if (arr[i / arr.length].length > 4) {
        throw new MyArraySizeException("Execption: Array" + arr.getClass().getCanonicalName() + " out of index");
      }
    }

    int sum = 0;

    for (String[] i : arr) {
      for (String j : i) {

        for (char ch : j.toCharArray()) {
          if (!Character.isDigit(ch)) {
            throw new MyArrayDataException("Exeption: in string '" + j + "' symbol: '" + ch + "' is not digit");
          }
        }

        sum += Integer.valueOf(j);
      }
    }

    System.out.println("sum: " + sum);
  }

  public static void showArray(String[][] arr) {
    for (String[] s1 : arr) {
      for (String s2 : s1) {
        System.out.print(s2 + " ");
      }
      System.out.println();
    }
  }

  public static void fillArray(String[][] arr) {

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = String.valueOf(i + j);
      }
    }
    // arr[2][2] = "not digit";
  }
}