/**
 * Reversi.java
 *
 * Author: Sam Tracy
 * Student ID: A11034980
 * Course: CSE11 Spring 2013
 * Assignment: Program 3
 * 
 * Build:	javac -classpath="*":"." Reversi.java
 * Execute:	java -classpath="*":." Reversi
 *
 * Dependencies:
 * 	objectdraw.jar, java.awt.*, java.awt.event.*, java.swing.*, 
 * 	java.swing.event.*, ActionListener Interface, Gameboard.java, 
 * 	PlayPiece.java, 
 *
 * A graphical program that implements ActionListener. Operates a GameBoard
 * grid and set of PlayPiece objects for the game of Reversi.
 *
 * Public methods:	
 * 	void begin()
 * 	void beginGame(int)
 * 	void schemeColor(Object)
 * 	void ActionPerformed( ActionEvent )
 * 	void scanSides(int,int, int, boolean)
 * 	void scanVertical(int,int, int, boolean)
 * 	void scanDiR(int,int, int, boolean)
 * 	void scanDiL(int,int, int, boolean)	Note: messy but working
 * 	void reversi(int,int, boolean)
 * 	void onMouseClick(Location)
 * 	void static main( String [] args )
 *
 */

package reversi;

import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Reversi extends WindowController implements ActionListener {

	private static final int WIN_X = 600;
	private static final int WIN_Y = 495;

	//Colors	
	private static Color classic = new Color(0,255,0);
	private static Color p1classic = new Color(0,0,0);
	private static Color p2classic = new Color(255,255,255);
	private static Color neo = new Color(49,79,79);
	private static Color p1neo = new Color(255,0,0);
	private static Color p2neo = new Color(0,191,255);
	private static Color pirate = new Color(160,82,45);
	private static Color p1pirate = new Color(255,215,0);
	private static Color p2pirate = new Color(255,255,240);
	private Color p1, p2;


	//Board and player designations
	private GameBoard board;
	private int grid;
	private boolean boardExist;
	private boolean gameStart;
	private boolean player;
	private boolean validMove = false;
	private PlayPiece[][] pieces;

	private JPanel northPanel;	//north
	private JLabel gridLabel, p1Label, p2Label;
	private JTextField gridField;
	private JPanel eastPanel;	//east
	private JButton pass;
	private JLabel playerPass;
	private JPanel westPanel;	//west
	private JComboBox<String> schemeMenu;
	private JPanel southPanel;	//south
	private JButton calculate, newGame;
        private JButton exit;


	public void begin() {

		Container contentPane=getContentPane();

		//grid size and score
		northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout()); // easier
		gridLabel=new JLabel("Grid Size ");
		p1Label=new JLabel("Player 1: 0 ");
		p2Label=new JLabel("Player 2: 0 ");
		gridField=new JTextField(2);
		gridField.addActionListener(this);
		northPanel.add(gridLabel);
		northPanel.add(gridField);
		northPanel.add(p1Label);
		northPanel.add(p2Label);
		contentPane.add(northPanel, BorderLayout.NORTH);

		//game scheme
		westPanel=new JPanel();
		westPanel.setLayout(new GridLayout(1,1));
		schemeMenu = new JComboBox<String>();
		schemeMenu.addItem("Classic");
		schemeMenu.addItem("Neo");
		schemeMenu.addItem("Pirate");
		schemeMenu.addActionListener(this);
		westPanel.add(schemeMenu);
		contentPane.add(westPanel, BorderLayout.WEST);

		//pass
		eastPanel=new JPanel();
		pass = new JButton("Pass");
		playerPass = new JLabel("Player 1");
		pass.addActionListener(this);
		eastPanel.add(pass);
		eastPanel.add(playerPass);
		contentPane.add(eastPanel, BorderLayout.EAST);

		//score and new games
		southPanel=new JPanel();
		southPanel.setLayout(new FlowLayout());
		calculate=new JButton("Calculate Score");
		newGame=new JButton("New Game");
		calculate.addActionListener(this);
		newGame.addActionListener(this);
                exit = new JButton("Exit");
                exit.addActionListener(this);
                eastPanel.add(exit);
		southPanel.add(calculate);
		southPanel.add(newGame);
                southPanel.add(exit);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		contentPane.validate();
	}

	public void beginGame(int grid) {
			//place 4 alternating pieces to start the game
		int g= (grid-1)/2;
		board.placePiece(g,g,player,p1,p2);
		pieces[g][g]=board.returnP();
		board.placePiece(g,g+1,!player,p1,p2);
		pieces[g][g+1]=board.returnP();	
		board.placePiece(g+1,g,!player,p1,p2);
		pieces[g+1][g]=board.returnP();	
		board.placePiece(g+1,g+1,player,p1,p2);
		pieces[g+1][g+1]=board.returnP();		
	}
		//set color scheme by menu choice
	public void schemeColor(Object menu) {
		if(menu.equals("Classic")){
			board.boardColor(classic);
			p1 = p1classic;
			p2 = p2classic;
		}
		else if(menu.equals("Neo")) {
			board.boardColor(neo);
			p1 = p1neo;
			p2 = p2neo;
		}
		else if(menu.equals("Pirate")) {
			board.boardColor(pirate);
			p1 = p1pirate;
			p2 = p2pirate;
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource()== newGame && (gridField.getText()).length()!=0) {
			canvas.clear(); //catch any old games
				//read input and build board and PlayPiece array
			grid = Integer.parseInt(gridField.getText());
			board = new GameBoard( grid, classic, canvas );
			boardExist = true;
			pieces = new PlayPiece[grid][grid];
				//set colors by menu
			Object menu = schemeMenu.getSelectedItem();
			this.schemeColor(menu);
			this.beginGame(grid);
		}
		else if(evt.getSource()== schemeMenu && boardExist) {
				//get menu options
			Object menu = schemeMenu.getSelectedItem();
			this.schemeColor(menu);			
				//flip all colors
			for(int i = 0; i<grid; i++)
			   for(int j= 0; j<grid; j++)
			      if(pieces[i][j]!=null) {	
			         if(!pieces[i][j].topbottom()) {
					boolean p=pieces[i][j].topbottom();
					pieces[i][j].clear();
					board.placePiece(i,j,p,p1,p2);
					pieces[i][j]=board.returnP();	
				 }
				 if(pieces[i][j].topbottom()) {
					boolean p=pieces[i][j].topbottom();
					pieces[i][j].clear();
					board.placePiece(i,j,p,p1,p2);
					pieces[i][j]=board.returnP();	
				 }
			      }
		}
			//calculate scores with topbottom() method
		else if(evt.getSource() == calculate) {
			int score1 = 0; 
			int score2 = 0;
			for(int i = 0; i<grid; i++)
			   for(int j= 0; j<grid; j++)
			      if(pieces[i][j]!=null) {	
			         if(!pieces[i][j].topbottom()) {
					score1++;
				 }
				 if(pieces[i][j].topbottom()) {
					score2++;
				 }
			      }
			p1Label.setText("Player 1: "+score1+" ");
			p2Label.setText("Player 2: "+score2+" ");
		}
		//player passes and label changes, player boolean switches
		else if(evt.getSource() == pass) {
			player = !player;
			if(!player) {
				playerPass.setText("Player 1");
			}
			else if(player) {
				playerPass.setText("Player 2");
			}
		}
                //exit game
                else if(evt.getSource() == exit) {
                    System.exit(0);
                }
	}
		//scan and flip pieces
		//(I realize these 4 methods are messy and repetitious
		//but they work and I ran out of time to figure out how to compact them)
	public void scanSides(int I, int J, int x, boolean b) {
		//array to record pieces to flip
		PlayPiece[] p = new PlayPiece[grid];
		int index = 0;
		boolean rev = false;
		//if x is positive go right
		if(x>0) {
			for(int j = J+x; j < grid; j+=x )
				if(pieces[I][j]==null) {
					break;
				}
				else if (pieces[I][j].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[I][j];
					index++;
				}
		}
		//if x is negative go left
		if(x<0) {
			for(int j = J+x; j > 0; j+=x )
				if(pieces[I][j]==null) {
					break;
				}
				else if (pieces[I][j].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[I][j];
					index++;
				}
		}
		//if we ever scanned across one of our own pieces, flip
		if(rev) {
			for(int a = 0; a < p.length; a++) {
				if(p[a]==null) {
					break;
				}
				else {
					p[a].flip();
					validMove=true;
				}
			}
		}
	}
	public void scanVertical(int I, int J, int y, boolean b) {
		
		PlayPiece[] p = new PlayPiece[grid];
		int index = 0;
		boolean rev = false;

		if(y>0 ) { // if y is positive go up
			for(int i = I+y; i < grid; i+=y )
				if(pieces[i][J]==null) {
					break;
				}
				else if (pieces[i][J].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[i][J];
					index++;
				}
		}
		if(y<0) { //if y is negative go down
			for(int i = I+y; i > 0; i+=y )
				if(pieces[i][J]==null) {
					break;
				}
				else if (pieces[i][J].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[i][J];
					index++;
				}
		}
		if(rev) {
			for(int a = 0; a < p.length; a++) {
				if(p[a]==null) {
					break;
				}
				else {
					p[a].flip();
					validMove = true;
				}
			}
		}

	}

	public void scanDiR(int I, int J, int d, boolean b) {
		PlayPiece[] p = new PlayPiece[grid];
		int index = 0;
		boolean rev = false;

		if(d>0 ) { // go up and right
			int i = I+1;
			int j = J+1;
			while(i < grid && j <grid) {
				if(pieces[i][j]==null) {
					break;
				}
				else if (pieces[i][j].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[i][j];
					index++;
				}
				i++;
				j++;
			}
		}
		if(d<0 ) { // go down and right
			int i = I-1;
			int j = J+1;
			while(i > 0 && j <grid) {
				if(pieces[i][j]==null) {
					break;
				}
				else if (pieces[i][j].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[i][j];
					index++;
				}
				i--;
				j++;
			}
		}
		if(rev) {
			for(int a = 0; a < p.length; a++) {
				if(p[a]==null) {
					break;
				}
				else {
					p[a].flip();
					validMove = true;
				}
			}
		}
	}

	public void scanDiL(int I, int J, int d, boolean b) {
		PlayPiece[] p = new PlayPiece[grid];
		int index = 0;
		boolean rev = false;

		if(d>0 ) { //go up and left
			int i = I+1;
			int j = J-1;
			while(i < grid && j >0) {
				if(pieces[i][j]==null) {
					break;
				}
				else if (pieces[i][j].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[i][j];
					index++;
				}
				i++;
				j--;
			}
		}
		if(d<0 ) { // go down and left
			int i = I-1;
			int j = J-1;
			while(i > 0 && j> 0) {
				if(pieces[i][j]==null) {
					break;
				}
				else if (pieces[i][j].topbottom()==b) {
					rev = true;
					break;
				}
				else {
					p[index]=pieces[i][j];
					index++;
				}
				i--;
				j--;
			}
		}
		if(rev) {
			for(int a = 0; a < p.length; a++) {
				if(p[a]==null) {
					break;
				}
				else {
					p[a].flip();
					validMove = true;
				}
			}
		}
	}

		//invoke scan() method in all directions
	public void reversi(int I, int J, boolean b) {

		this.scanSides(I, J, 1, b);
		this.scanSides(I, J, -1, b);
		this.scanVertical(I, J, 1, b);
		this.scanVertical(I, J, -1, b);
		this.scanDiR(I, J, 1, b);
		this.scanDiR(I, J, -1, b);
		this.scanDiL(I, J, 1, b);
		this.scanDiL(I, J, -1, b);
	}
	
	//place pieces on mouse click
	public void onMouseClick(Location point){
		if(boardExist && board.tfSquare(point)){ //only act if board exists
			int i, j;
			i = board.iCoordinate(point);
			j = board.jCoordinate(point);
			if(pieces[i][j]==null){		//only act if no piece present already
				this.reversi(i,j, player);
				if(validMove) {		//only act on valid moves
					board.placePiece(i,j,player,p1,p2);
					pieces[i][j]= board.returnP();
					player = !player;	//switch player
					board.setclickedSq();
				}
			}
		validMove = false;	//to check after reversi() method on next move
		}
		//change pass labels
		if(!player) {
			playerPass.setText("Player 1");
		}
		else if(player) {
			playerPass.setText("Player 2");
		}
                
	}

	public static void main(String[] args) {
		new Reversi().startController(WIN_X, WIN_Y);
	}
}
