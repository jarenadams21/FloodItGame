// Represents the WorldState of a FloodIt game.
class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<Cell> board;
  // Represents the constant of a board size.
  int boardSize;
  // Tracks the number of times clicked on the board
  int presses;
  // Max number of presses to win the game.
  int numToWin;
  // Proportions for WorldState
  int width;
  int heigh;
  // Number of colors in the game. (Cannot exceed 8)
  int colors;
  // Scale of cells
  int scale;
  // Time since the game has started.
  int time;
  int currentWinstreak;

  // FloodItWorld constructor
  FloodItWorld(int boardSize, int colors) {
    this.boardSize = boardSize;
    this.colors = colors;
    this.currentWinstreak = 0;
    setScale();
    initializeCells();
  }



  // Convenience FloodItWorld Constructor
  // Takes in an Arraylist of cells
  FloodItWorld(int boardSize, int colors, ArrayList<Cell> cells) {
    this.boardSize = boardSize;
    this.colors = colors;
    this.board = cells;
    setScale();
    assignNeighbors();
  }

  // Makes a visual representation of the world state
  // for a player to act on.
  public WorldScene makeScene() {
   
    //Worldscene
    WorldScene scene = new WorldScene(1200, 800);
   
   
      for (Cell cell : this.board) {
        scene.placeImageXY(cell.draw(this.scale), 12 + this.scale * cell.x, 12 + this.scale * cell.y);
      }
      scene.placeImageXY(
          new TextImage("Flood-it Game!", 60, FontStyle.BOLD_ITALIC, Color.red), 700, 50);
      scene.placeImageXY(
          new TextImage("Can you fill the board in "+ Integer.toString(numToWin) + " presses??", 20, FontStyle.BOLD_ITALIC, Color.red), 700, 100);
      scene.placeImageXY(
          new TextImage("Current Presses: " + Integer.toString(presses), 20, FontStyle.BOLD_ITALIC, Color.BLACK), 700, 200);
      scene.placeImageXY(
          new TextImage("Target Presses: " + Integer.toString(numToWin), 20, FontStyle.BOLD_ITALIC, Color.BLACK), 700, 250);
      scene.placeImageXY(
          new TextImage("Current Winstreak: " + Integer.toString(currentWinstreak), 20, FontStyle.BOLD_ITALIC, Color.BLACK), 700, 300);
   
   
    // Check for game lost (if presses > allowed presses)
    if (this.presses > this.numToWin && (this.gameOver() == false)) {
      scene.placeImageXY(new TextImage("BOOOO!! Game over. You should press r to play again. Redeem yourself.", 25, Color.RED), 600, 700);
      this.currentWinstreak = 0;
    }
   
    // Check for game win (if presses < allowed presses)
    if (this.presses <= this.numToWin && this.gameOver() == true) {
      scene.placeImageXY(new TextImage("Horray!! You win. Press r to continue your winstreak.", 25, Color.GREEN), 600, 600);
    }

    return scene;
  }

 
  // Sets the appropriate size for cells based on the
  // board size.
  void setScale() {

    // Changes the number of presses to win based on board size.
    if (this.boardSize > 14) {
      this.numToWin = this.boardSize * 2 + 15 + this.colors;
    } else if (this.boardSize < 4) {
      this.numToWin = this.boardSize + 2;
    } else {
      this.numToWin = this.boardSize + 10;
    }


    // Sets scale of cells based on board size.
    if (this.boardSize > 40) {
      this.scale = 10;
    } else if (this.boardSize < 15) {
      this.scale = 35;
    } else {
      this.scale = 20;
    }

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
        } else {
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
      } else {
        current.left = board.get(k - this.boardSize);
      }
      // Right
      if (board.get(k).x == this.boardSize - 1) {
        current.right = null;
      } else {
        current.right = board.get(k + this.boardSize);
      }

      // Y-Variable Assignments
      // Top
      if (board.get(k).y == 0) {
        current.top = null;
      } else {
        current.top = board.get(k - 1);
      }
      // Bottom
      if (board.get(k).y == this.boardSize - 1) {
        current.bottom = null;
      } else {
        current.bottom = board.get(k + 1);
      }
    }
  }


  // Resets the game after the user presses r.
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      if (this.gameOver() && presses <= numToWin) {
        this.currentWinstreak ++;
      }
      if (!this.gameOver()) {
        this.currentWinstreak = 0;
      }
      this.presses = 0;
      initializeCells();
    }
  }

 

  // Takes the position received by the onMouseClicked position
  // and returns the cell that is also at this position.
 public Cell cellPos(Posn pos) {
    Cell cellClicked = null;
    for (Cell cell: this.board) {
      if ((cell.x <= ((pos.x - 3) / this.scale)) && (((pos.x - this.scale) / this.scale) <= cell.x )
          && (cell.y <= ((pos.y - 3) / this.scale)) && (((pos.y - this.scale) / this.scale) <= cell.y )) {
        cellClicked = cell;
      }
    }
    return cellClicked;
  }

  // Changes the first cell in the board to the color
  // of the cell that was clicked on.
  public void changeOrigin(Cell cell) {
    if (cell != null && !cell.color.equals(board.get(0).color)) {
      board.get(0).color = cell.color;
      presses++;  
    }
  }



  // Tracks valid mouse clicks on the board and
  // sends the correct position in which it was clicked to
  // relevant methods to update the board accordingly.
  public void onMouseClicked(Posn pos) {
      this.changeOrigin(this.cellPos(pos)); 
  }

 
  // Updates the world every tick to correctly match any changes
  // in the world state.
  public void onTick() {
    floodCells();
  }

 
  // Updates the neighbors of cells to flooded if they
  // match the current cells color and are not flooded.
  public void floodCells() {
    for (int i = 0; i < this.board.size(); i++) {
      Cell current = this.board.get(i);
      if (current.flooded == true) {
        current.color = this.board.get(0).color;
        current.changeNeighbors(this.board.get(0).color);
      }
    }
    makeScene();
  }

 
  // Checks to see if all cells have been flooded.
  public boolean gameOver() {
    return this.checkAllCellsForFlooded(0);
  }


//Continuously goes through all cells to see if
  // all have been flooded and returns the && concatenation
  // of all their flooded values.
  // DOES NOTHING RIGHT NOW
  public boolean checkAllCellsForFlooded(int index) {
    if (index == this.board.size() - 1) {
      return this.board.get(this.board.size() - 1).flooded;
    } else {
      return this.board.get(index).flooded && checkAllCellsForFlooded(index + 1);
    }
  }

 

  // Starts the game of FloodIt.
  public void initializeGame(int boardSize, int amountOfColors) {
    FloodItWorld fl = new FloodItWorld(boardSize, amountOfColors);
    fl.bigBang(1200, 800, 0.1);
  }
}
