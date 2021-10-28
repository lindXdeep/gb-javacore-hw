import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AppTest
 */
public class AppTest {

  private static App app;
  private static int[] test1, test2, test3;

  private static Class<RuntimeException> test1_expected;
  private static int[] test2_expected;

  @BeforeAll
  static void init() {
    test1 = new int[] { 1, 2, 3, 5, 2, 3, 6, 1, 7 };
    test2 = new int[] { 1, 2, 4, 4, 2, 3, 4, 1, 7 };
    test3 = new int[] {};

    test1_expected = RuntimeException.class;
    test2_expected = new int[] { 1, 7 };
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

}