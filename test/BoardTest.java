package reversi;

import objectdraw.*;
import java.awt.*;
import java.util.*;

public class BoardTest extends WindowController {

	private static int WIN_SIZE = 600;
	private static int boardSize = 10;
	private GameBoard newBoard;
	private Color neo = new Color(0,240, 240);
	private Color classic = new Color(0,255,0);
	private Color p1 = new Color(0,0,0);
	private Color p2 = new Color(255,255,255);
	private boolean color = false;
	private Location clickedSquare;
	private PlayPiece[][] pieces = new PlayPiece[10][10];
	private boolean player= false;

	public void begin() {
		newBoard = new GameBoard( boardSize, classic, canvas);
	}

	public void onMouseExit(Location point){
		color = !color;
		if(!color){
			newBoard.boardColor(classic);
		}
		else if(color){
			newBoard.boardColor(neo);
		}
	}

	public void onMouseClick(Location point) {
		if(newBoard.tfSquare(point)){
			int i, j;
			i = newBoard.iCoordinate(point);
			j = newBoard.jCoordinate(point);
			newBoard.placePiece(i,j,player,p1,p2);
			player=!player;
			newBoard.setclickedSq();
		}
	}

	static public void main(String [] args) {
		new BoardTest().startController(WIN_SIZE, WIN_SIZE);
	}
}
