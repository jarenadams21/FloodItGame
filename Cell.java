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




//Changes the colors of this cell's neighbors if they
 // are not flooded and have the same color as this cell.
 void changeNeighbors(Color color) {
   if (this.left != null && this.left.flooded == false && this.left.color.equals(color)) {
     this.left.flooded = true;
   }


   if (this.right != null && this.right.flooded == false && this.right.color.equals(color)) {
     this.right.flooded = true;
   }

   if (this.top != null && this.top.flooded == false && this.top.color.equals(color)) {
     this.top.flooded = true;
   }

   if (this.bottom != null && this.bottom.flooded == false && this.bottom.color.equals(color)) {
     this.bottom.flooded = true;
   }

 }


  // Visually represents a cell based on its color and a given scale.
  WorldImage draw(int scale) {
    return new RectangleImage(scale, scale, OutlineMode.SOLID, this.color);
  }
}
