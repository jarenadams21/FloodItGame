// Represents the WorldState of a FloodIt game.
class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<Cell> board;

  // Represents the constant of a board size.
  int boardSize = 22;

  // Tracks the number of times clicked on the board
  int presses;

  // Max number of presses to win the game.
  int numToWin;

  // Proportions for WorldState
  int width;
  int height;

  // Number of colors in the game.
  int colors;

  // Scale of cells
  int scale;

  // FloodItWorld constructor
  FloodItWorld(int boardSize, int colors) {

    this.boardSize = boardSize;
    this.colors = colors;
    initializeCells();

    // Changes the number of presses to win based on board size.
    if (this.boardSize > 14) {
      numToWin = this.boardSize + 17;
    }
    else if (this.boardSize < 4) {
      numToWin = this.boardSize + 2;
    }
    else {
      numToWin = this.boardSize + 6;
    }

    // Sets scale of cells based on board size.
    if (this.boardSize > 40) {

      this.scale = 10;
    }

    else if (this.boardSize < 15) {

      this.scale = 35;
    }

    else {

      this.scale = 20;
    }

  }

  // Convenience FloodItWorld Constructor
  // Takes in an Arraylist of cells
  FloodItWorld(int boardSize, int colors, ArrayList<Cell> cells) {

    this.boardSize = boardSize;
    this.colors = colors;
    this.board = cells;

    // Changes the number of presses to win based on board size.
    if (this.boardSize > 14) {
      numToWin = this.boardSize + 17;
    }
    else if (this.boardSize < 4) {
      numToWin = this.boardSize + 2;
    }
    else {
      numToWin = this.boardSize + 6;
    }

    assignNeighbors();

  }

  // Makes a visual representation of the world state
  // for a player to act on.
  public WorldScene makeScene() {

    WorldScene scene = new WorldScene(1200, 800);

    for (Cell cell : this.board) {
      scene.placeImageXY(cell.draw(this.scale), 12 + this.scale * cell.x, 12 + this.scale * cell.y);
    }

    scene.placeImageXY(
        new TextImage("Current Presses: " + Integer.toString(presses), 20, Color.BLACK), 1100, 20);

    scene.placeImageXY(
        new TextImage("Target Presses: " + Integer.toString(numToWin), 20, Color.BLACK), 1100, 50);

    return scene;
  }

  // Initializes the cells of the FloodIt game
  // given the board size of the game.
  void initializeCells() {

    this.board = new ArrayList<Cell>();

    for (int i = 0; i < boardSize; i++) {

      for (int j = 0; j < boardSize; j++) {

        if (i == j && j == 0) {
          Cell origin = new Cell(0, 0, this.colors, true);
          this.board.add(origin);
        }

        else {
          Cell current = new Cell(i, j, this.colors, false);
          this.board.add(current);
        }
      }
    }

    assignNeighbors();

  }

  // Helper designed to properly assign neighbors of cells in the board.
  void assignNeighbors() {

    // Assign correct neighbors.
    for (int k = 0; k < board.size(); k++) {

      // Current cell
      Cell current = board.get(k);

      // X-variable Assignments

      // Left
      if (board.get(k).x == 0) {
        current.left = null;
      }
      else {
        current.left = board.get(k - this.boardSize);
      }

      // Right
      if (board.get(k).x == this.boardSize - 1) {
        current.right = null;
      }
      else {
        current.right = board.get(k + this.boardSize);
      }

      // Y-Variable Assignments

      // Top
      if (board.get(k).y == 0) {
        current.top = null;
      }
      else {
        current.top = board.get(k - 1);
      }

      // Bottom
      if (board.get(k).y == this.boardSize - 1) {
        current.bottom = null;
      }
      else {
        current.bottom = board.get(k + 1);
      }
    }

  }

  // Resets the game after the user presses r.
  public void onKeyEvent(String key) {

    if (key.equals("r")) {
      presses = 0;
      initializeCells();
    }
  }

  // Starts the game of FloodIt.
  public void initializeGame(int boardSize) {

    FloodItWorld fl = new FloodItWorld(boardSize, this.colors);
    this.boardSize = boardSize;
    fl.bigBang(1200, 800, 0.1);
  }

}
