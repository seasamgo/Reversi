package reversi;

import objectdraw.*;
import java.awt.*;
import java.util.*;

public class PieceTest extends WindowController {
	
	private static int WIN_SIZE = 600;
	private PlayPiece pieces[] = new PlayPiece[64];
	private boolean side = true;
	private static int index = 0;
	private Color one = new Color(255,0,255);
	private Color two = new Color(0,255,0);

	public void begin()
	{
		//display instructions
		new Text("Click to create pieces", 10, 10, canvas);
	}

	public void onMouseClick(Location point) {

		//make a new piece
		pieces[index] = new PlayPiece(canvas.getWidth()/10,
				side,one,two,point,canvas);
		index++;
		side = !side;	
		//System.out.println("side: " + side);
	}

	public void onMouseExit(Location point) {
		if(pieces[0] == null) return;

		for(int i= 0; i<index; i++){
			pieces[i].flip();
			//System.out.println("i: " + i);
		}
	}

	public void onMouseEnter(Location point) {
		if (pieces[0] == null) return;

		for(int j=0; j<index; j++){
			pieces[j].flip();
		}
	}

	public void onMouseDrag(Location point) {
		if(pieces[0] == null) return;
	
		for(int k = index -1; k>=0; k--){
			pieces[k].clear();
			pieces[k] = null;
			index = 0;
		}
	}

	static public void main(String [] args) {
		new PieceTest().startController(WIN_SIZE, WIN_SIZE);
	}
}
