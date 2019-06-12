/**
 * GameBoard.java
 *
 * Author: Sam Tracy
 * Student ID: A11034980
 * Course: CSE11 Spring 2013
 * Assignment: Program 3
 *
 * Build: javac -classpath="*":"." GameBoard.java
 * Dependencies: objectdraw.jar, java.awt.*
 *
 * Description: Constructs a new GameBoard of specified size. May 
 *		determine if grid coordinates have been selected by mouse 
 *		click and place new pieces at specified locations. May also 
 *		set background color.
 * 
 * GameBoard is intended to be called by other classes.
 * 
 * Public methods:
 *	GameBoard(int, DrawingCanvas)
 *	void boardColor(Color)
 */

package reversi;

import objectdraw.*;
import java.awt.*;

public class GameBoard {

	private FilledRect backGround;
	private FramedRect[][] gridSquares;
	private double gridSize;
	private double canvasSize;
	private double arraySize;
	private Location clicked;
	private DrawingCanvas acanvas;
	private PlayPiece p;

	private boolean clickedSq = false;

	//Constructor
	public GameBoard(int s, Color c, DrawingCanvas canvas ) {

		canvasSize = Math.min(canvas.getHeight()-1,
					canvas.getWidth()-1);
		//board background		
		backGround = new FilledRect(0, 0, canvasSize, 
					canvasSize, canvas);
		backGround.setColor(c);
	
		//size of grid squares relative to canvas
		gridSize = canvasSize/s;

		//grid matrix
		gridSquares = new FramedRect[s][s];
		for(int i = 0; i < s; i++)
		   for(int j = 0; j < s; j++)
		      gridSquares[i][j] = new FramedRect(j*gridSize, 
					i*gridSize, gridSize, gridSize, canvas);
		arraySize = s;
		acanvas = canvas;
	}

	//Change the background color
	public void boardColor(Color c) {
		backGround.setColor(c);
	}

	//Return whether a square has been selected
	public boolean tfSquare(Location point) {
		for(int i = 0; i < arraySize; i++)
		   for(int j = 0; j < arraySize; j++)
			if(gridSquares[i][j].contains(point)) {
				clickedSq = true;
			}
		return clickedSq;
	}	

	//Make clickedSq false (I made this method because doing so within 
	//tfSquare() was an unreachable statement)
	public void setclickedSq() {
		clickedSq = false;
	}

	//Return the row position of a selected square
	public int iCoordinate(Location point) {
		int I=-1;
		int i;
		for( i = 0; i < arraySize; i++)
		   for(int j = 0; j < arraySize; j++)
		      if(gridSquares[i][j].contains(point)) {
			I = i;
		      }
		return I;
	}

	//Return the column position of a selected square
	public int jCoordinate(Location point) {
		int J=-1;
		int j;
		for(int i = 0; i < arraySize; i++)
		   for( j = 0; j < arraySize; j++)
		      if(gridSquares[i][j].contains(point)) {
			J = j;
		      }
		return J;
	}

	//place a PlayPiece given a row and column position
	public void placePiece(int i, int j, boolean side, Color p1, 
					Color p2) {
		double x = j*gridSize+2;
		double y = i*gridSize+2;
		Location l = new Location(x, y);
		p = new PlayPiece(gridSize-4, side, p1, p2, l, 
						acanvas);
	}

	public PlayPiece returnP(){
		return p;
	}
}
