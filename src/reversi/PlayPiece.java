/**
 * PlayPiece.java
 *
 * Author: Sam Tracy
 * Student ID: A11034980
 * Course: CSE11 Spring 2013
 * Assignment: Program 3
 *
 * Build: javac -classpath="*":"." PlayPiece.java
 * Dependencies: objectdraw.jar, java.awt.*
 *
 * Description: Constructs a game pice and places it on the canvas. Can 
 * 		determine if top or bottom of piece is showing, may flip 
 * 		pieces, and sets side colors. May also clear all pieces 
 * 		from the canvas.
 *
 * Public constructor:	PlayPiece(double, boolean, Location, DrawingCanvas)
 *
 * Public methods:	boolean topBottom()
 * 			void flip()
 * 			void clear()
 * 
 */

package reversi;

import objectdraw.*;
import java.awt.*;
import java.util.*;

// Pieces are created with specified colors, size, and position
public class PlayPiece {

	//private variables
	private FilledOval piece;
	private static double Size;
	private static DrawingCanvas canvas;
	private static Location location;
	private boolean player;
	private Color player1;
	private Color player2;

	// Constructor
	public PlayPiece( double size, boolean side, Color top, Color bot,
				 Location given, DrawingCanvas acanvas)
	{
		Size = size;
		location = given;
		canvas = acanvas;
		player = side;
		player1 = top;
		player2 = bot;

		piece = new FilledOval(location, Size, Size, canvas);

		if(player == false) 
		{
			piece.setColor(player1);
		}
		else if(player == true)
		{
			piece.setColor(player2);
		}
	}

	public boolean topbottom(){
		return player;
	}

	public void flip() {
		player = !player;
		if(player == false) {
			piece.setColor(player1);
			//System.out.println("setplayer1");
		}
		else if(player == true) {
			piece.setColor(player2);
			//System.out.println("setplayer2");
		}
	}

	public void clear() {
		piece.removeFromCanvas();
	}
}
