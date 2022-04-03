// Examples and Tests for the FloodIt World and Cells
// (Does not test non-working/in-progress methods)
class ExampleFloodIt {

  // Example Cells
  Cell redCell;
  Cell greenCell;
  Cell yellowCell;
  Cell orangeCell;
  Cell blueCell;
  Cell cyanCell;
  Cell grayCell;
  Cell pinkCell;
  Cell pinkCellTwo;

  // Example FloodItWorld state.
  FloodItWorld flood1;
  FloodItWorld flood2;
  FloodItWorld flood3;
  FloodItWorld flood4;
  FloodItWorld makeGame;

  // Example list of cells in a board of FloodIt.
  ArrayList<Cell> exCellList;

  // Initial World State
  void initializeFlood() {

    redCell = new Cell(0, 0, true, Color.RED);
    greenCell = new Cell(0, 1, false, Color.GREEN);
    yellowCell = new Cell(0, 2, false, Color.YELLOW);
    orangeCell = new Cell(1, 0, false, Color.ORANGE);
    blueCell = new Cell(1, 1, false, Color.BLUE);
    cyanCell = new Cell(1, 2, false, Color.CYAN);
    grayCell = new Cell(2, 0, false, Color.GRAY);
    pinkCell = new Cell(2, 1, false, Color.PINK);
    pinkCellTwo = new Cell(2, 2, false, Color.PINK);

    exCellList = new ArrayList<Cell>();

    exCellList.add(redCell);
    exCellList.add(greenCell);
    exCellList.add(yellowCell);
    exCellList.add(orangeCell);
    exCellList.add(blueCell);
    exCellList.add(grayCell);
    exCellList.add(grayCell);
    exCellList.add(pinkCell);
    exCellList.add(pinkCellTwo);

    flood1 = new FloodItWorld(3, 3);
    flood2 = new FloodItWorld(3, 3, exCellList);
    flood3 = new FloodItWorld(2, 2);
    makeGame = new FloodItWorld(20,8);

  }

  // CELL METHOD TESTS

  // Tests the method to change a cell's color
  void testChangeColor(Tester t) {

    t.checkExpect(redCell.color, Color.RED);
    t.checkExpect(orangeCell.color, Color.ORANGE);
    t.checkExpect(yellowCell.color, Color.YELLOW);

    initializeFlood();
    redCell.changeColor(Color.GREEN);
    t.checkExpect(redCell.color, Color.GREEN);
    orangeCell.changeColor(Color.BLUE);
    t.checkExpect(orangeCell.color, Color.BLUE);
    yellowCell.changeColor(Color.CYAN);
    t.checkExpect(yellowCell.color, Color.CYAN);

  }

  // Tests the draw method of a cell.
  void testDraw(Tester t) {

    initializeFlood();

    t.checkExpect(redCell.draw(20), new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED));
    t.checkExpect(blueCell.draw(10), new RectangleImage(10, 10, OutlineMode.SOLID, Color.BLUE));
    t.checkExpect(pinkCell.draw(35), new RectangleImage(35, 35, OutlineMode.SOLID, Color.PINK));

  }

  // FLOODITWORLD METHOD TESTS

  // Tests the initialization of cells into the game.
  void testInitializeCells(Tester t) {

    initializeFlood();
    // Is my origin flooded.
    t.checkExpect(flood1.board.get(0).flooded, true);

    // Is every other cell not flooded.
    for (int i = 1; i < flood1.board.size(); i++) {

      t.checkExpect(flood1.board.get(i).flooded, false);
    }

    // Checks if each cell has a valid color in the list of colors
    // As well as if each x and y value are valid given the boardSize.
    for (int i = 0; i < flood1.board.size(); i++) {
      t.checkExpect(flood1.board.get(i).colors.contains(flood1.board.get(i).color), true);
      t.checkExpect(flood1.board.get(i).x >= 0 && flood1.board.get(i).x <= flood1.boardSize, true);
      t.checkExpect(flood1.board.get(i).y >= 0 && flood1.board.get(i).y <= flood1.boardSize, true);
    }

    // Checks if neighbors are properly assigned for example cells.

    // (assignNeighbors() Testing)
    t.checkExpect(flood2.board.get(0).left, null);
    t.checkExpect(flood2.board.get(3).left, flood2.board.get(0));
    t.checkExpect(flood2.board.get(0).right, flood2.board.get(3));
    t.checkExpect(flood2.board.get(7).right, null);
    t.checkExpect(flood2.board.get(0).top, null);
    t.checkExpect(flood2.board.get(1).top, flood2.board.get(0));
    t.checkExpect(flood2.board.get(2).bottom, null);
    t.checkExpect(flood2.board.get(4).bottom, flood2.board.get(5));

  }

  // Tests the reset of the board.
  void testOnKey(Tester t) {

    initializeFlood();
    ArrayList<Cell> oldBoard = new ArrayList<Cell>();
    oldBoard = flood3.board;

    t.checkExpect(flood3.colors == 2, true);
    t.checkExpect(flood3.boardSize, 2);
    t.checkExpect(flood3.board.equals(oldBoard), true);

    flood3.onKeyEvent("q");

    t.checkExpect(flood3.colors == 2, true);
    t.checkExpect(flood3.boardSize, 2);
    t.checkExpect(flood3.board.equals(oldBoard), true);

    flood3.onKeyEvent("r");

    t.checkExpect(flood3.colors == 2, true);
    t.checkExpect(flood3.boardSize, 2);
    t.checkExpect(flood3.board.equals(oldBoard), false);

  }

  // Tests the visualization of the world state
  // with the makeScene() method.
  void testMakeScene(Tester t) {

    flood4 = new FloodItWorld(1, 1);

    WorldScene scene = new WorldScene(1200, 800);

    scene.placeImageXY(redCell.draw(35), 12, 12);

    scene.placeImageXY(new TextImage("Current Presses: " + Integer.toString(0), 20, Color.BLACK),
        1100, 20);

    scene.placeImageXY(new TextImage("Target Presses: " + Integer.toString(3), 20, Color.BLACK),
        1100, 50);

    t.checkExpect(flood4.makeScene(), scene);
  }

  // Testing the actual world state visualization.
  // Available colors cannot exceed 8!
  void testWorld(Tester t) {
    
    initializeFlood();

    t.checkExpect(makeGame.boardSize, 20);
    t.checkExpect(makeGame.colors, 8);
    makeGame.initializeGame(20);

  }
}
