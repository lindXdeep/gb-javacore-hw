import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AppTest
 */
public class AppTest {

  private static App app;
  private static int[] test1, test2, test3, test4_1, test4_2, test4_3, test4_4;

  private static Class<RuntimeException> test1_expected;
  private static int[] test2_expected;

  @BeforeAll
  static void init() {
    test1 = new int[] { 1, 2, 3, 5, 2, 3, 6, 1, 7 };
    test2 = new int[] { 1, 2, 4, 4, 2, 3, 4, 1, 7 };
    test3 = new int[] {};

    test1_expected = RuntimeException.class;
    test2_expected = new int[] { 1, 7 };

    test4_1 = new int[] { 1, 1, 1, 4, 4, 1, 4, 4 }; // true
    test4_2 = new int[] { 1, 1, 1, 1, 1, 1 }; // false
    test4_3 = new int[] { 4, 4, 4, 4 }; // false
    test4_4 = new int[] { 1, 4, 4, 1, 1, 4, 3 }; // false
  }

  @BeforeEach
  void prepare() {
    app = new App();
  }

  @Test
  @DisplayName("Тестируем на исключительный случай.")
  void test1() {
    assertThrows(test1_expected, () -> app.newAfterFore(test1));
  }

  @Test
  @DisplayName("Тестируем на вытаскивание элементов, идущих после последней четверки.")
  void test2() {
    assertArrayEquals(test2_expected, app.newAfterFore(test2));
  }

  @Test
  @DisplayName("Тестируем на пустой массив")
  void test3() {
    assertThrows(test1_expected, () -> app.newAfterFore(test3));
  }

  @Test
  @DisplayName("Тестируем в штатном режиме.")
  void test4_1() {
    assertTrue(app.containForeAndOne(test4_1));
  }

  @Test
  @DisplayName("Тестируем на все единицы.")
  void test4_2() {
    assertFalse(app.containForeAndOne(test4_2));

  }

  @Test
  @DisplayName("Тестируем на все четверки.")
  void test4_3() {
    assertFalse(app.containForeAndOne(test4_3));
  }

  @Test
  @DisplayName("Тестируем на лишняя цифра")
  void test4_4() {
    assertFalse(app.containForeAndOne(test4_4));
  }

}