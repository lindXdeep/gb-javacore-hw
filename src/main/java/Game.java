/**
 * Game
 */
abstract class Game {

  protected boolean isGame = true;
  protected int fieldSize;
  protected int winSize;

  protected char[][] field;

  private final char DOT_EMPTY = ' ';

  public Game(int fieldSize, int winSize) {
    this.fieldSize = fieldSize;
    this.winSize = winSize;
    field = new char[fieldSize][fieldSize];

    initField();
  }

  private void initField() {
    for (int i = 0; i < fieldSize * fieldSize; i++) {
      field[i / fieldSize][i % fieldSize] = DOT_EMPTY;
    }
  }

  protected abstract void gameLoop();
}