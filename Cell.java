// Imports
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.*;

//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  // Position based off the given x and y coord.
  Posn position;

  // List of available cell colors to choose from
  ArrayList<Color> colors;

  // Maximum potential colors a cell can be
  int maxColors;

  // Cell constructor to create a singular cell.
  Cell(int x, int y, int maxColors, boolean flooded) {

    this.x = x;
    this.y = y;
    this.position = new Posn(this.x, this.y);
    this.flooded = flooded;
    this.maxColors = maxColors;

    this.colors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW,
        Color.ORANGE, Color.BLUE, Color.CYAN, Color.GRAY, Color.PINK));

    this.color = colors.get((int) (Math.random() * maxColors));

  }

  // Convenience cell constructor that allows color manipulation
  Cell(int x, int y, boolean flooded, Color inp) {

    this.x = x;
    this.y = y;
    this.position = new Posn(this.x, this.y);
    this.flooded = flooded;

    this.colors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW,
        Color.ORANGE, Color.BLUE, Color.CYAN, Color.GRAY, Color.PINK));

    this.color = inp;

  }

  // Changes the color of this cell to the inputted color.
  void changeColor(Color color) {

    this.color = color;
  }

  // Visually represents a cell based on its color and a given scale.
  WorldImage draw(int scale) {

    return new RectangleImage(scale, scale, OutlineMode.SOLID, this.color);

  }
}
