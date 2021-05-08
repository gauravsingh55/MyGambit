/**
* Hosts all game data and deals with piece interaction.
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/
package com.example.mg.mygambit.src.board;

import java.io.Serializable;
import java.util.Random;

import java.util.List;
import java.util.ArrayList;

import com.example.mg.mygambit.src.piece.*;
import com.example.mg.mygambit.src.player.Player;


public class Board implements Serializable {
	private  final long serialVersionUID = 1;

	private  boolean test = false;
    private  final String TAG = "Board";

    /**
	   * A printing suppressor for the real build.
	   * 
	   * @param s Prints a string if the test boolean is true
	   */
	public void testPrint(String s) {
		if(test == true)
			System.out.print(s);
	}
	
	public Piece[][] board = new Piece[8][8];
	public List<String> moveList = new ArrayList<String>();
	public Player white = new Player('w');
	public Player black =  new Player('b');
	public char turn =  'w';

    //Yeah, I know this is ugly
    public Board(Board newBoard) {
        Piece[][] newP = new Piece[8][8];
        Piece[][] oldP = newBoard.getBoard();
        King bk = null, wk = null;

        for(int i=0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if(oldP[i][j] != null){
//                    newP[i][j] = oldP[i][j];
                    String piece = oldP[i][j].toString();
                    if(piece.charAt(0) == 'w'){
                        //White Pieces...
                        if(piece.charAt(1) == 'B'){
                            newP[i][j] = new Bishop('w');
                        } else if(piece.charAt(1) == 'K'){
                            wk = new King('w');
                            wk.setXY(i,j);
                            newP[i][j] = wk;
                        } else if(piece.charAt(1) == 'N'){
                            newP[i][j] = new Knight('w');
                        } else if(piece.charAt(1) == 'p'){
                            newP[i][j] = new Pawn('w');
                        } else if(piece.charAt(1) == 'Q'){
                            newP[i][j] = new Queen('w');
                        } else if(piece.charAt(1) == 'R'){
                            newP[i][j] = new Rook('w');
                        } else {
                            newP[i][j] = null;
                        }

                    } else {
                        //Black Pieces...
                        if(piece.charAt(1) == 'B'){
                            newP[i][j] = new Bishop('b');
                        } else if(piece.charAt(1) == 'K'){
                            bk = new King('b');
                            bk.setXY(i,j);
                            newP[i][j] = bk;
                        } else if(piece.charAt(1) == 'N'){
                            newP[i][j] = new Knight('b');
                        } else if(piece.charAt(1) == 'p'){
                            newP[i][j] = new Pawn('b');
                        } else if(piece.charAt(1) == 'Q'){
                            newP[i][j] = new Queen('b');
                        } else if(piece.charAt(1) == 'R'){
                            newP[i][j] = new Rook('b');
                        } else {
                            newP[i][j] = null;
                        }
                    }
                } else {
                    newP[i][j] = null;
                }
            }
        }

        this.board = newP;
        this.moveList = new ArrayList<String>(newBoard.moveList);

        Player w = new Player(newBoard.white.color, newBoard.white.dead, newBoard.white.living);
        w.k = new King(newBoard.white.k, 'w');
        Player b = new Player(newBoard.black.color, newBoard.black.dead, newBoard.black.living);
        b.k = new King(newBoard.black.k, 'b');

        this.white = w;
        this.black = b;

        Character newC = new Character(newBoard.getTurn());
//        newC = newBoard.getTurn();
        this.turn = newC;
    }


	/* STATIC METHODS */
	
	/**
	   * Changes a letter coordinate to an integer for the board
	   * 
	   * @param c A letter representing a coordinate
	   * @return int The number that the letter represented, -1 if invalid
	   */
	public int letterToInt(char c) {
		switch(c) {
			case 'a':
				return 0;
			case 'b':
				return 1;
			case 'c':
				return 2;
			case 'd':
				return 3;
			case 'e':
				return 4;
			case 'f':
				return 5;
			case 'g':
				return 6;
			case 'h':
				return 7;
			default:
				return -1;
		}
	}
	
	/**
	   * Changes a digit coordinate to an integer for the board
	   * 
	   * @param c A digit representing a coordinate
	   * @return int The number that the digit represented, -1 if invalid
	   */
	public int digitToInt(char c) {
		switch(c) {
		case '1':
			return 0;
		case '2':
			return 1;
		case '3':
			return 2;
		case '4':
			return 3;
		case '5':
			return 4;
		case '6':
			return 5;
		case '7':
			return 6;
		case '8':
			return 7;
		default:
			return -1;
		}
	}

	/**
	   * Changes an int coordinate to an digit char
	   * 
	   * @param y An int representing a coordinate
	   * @return char The char digit that the integer represented, '0' if invalid
	   */
	public char intToDigit(int y) {
		switch(y) {
			case 0:
				return '1';
			case 1:
				return '2';
			case 2:
				return '3';
			case 3:
				return '4';
			case 4:
				return '5';
			case 5:
				return '6';
			case 6:
				return '7';
			case 7:
				return '8';
			default:
				return '0';
		}
	}
	
	/**
	   * Changes an int coordinate to a letter char
	   * 
	   * @param x An int representing a coordinate
	   * @return char The char letter that the integer represented, '0' if invalid
	   */
	public char intToLetter(int x) {
		switch(x) {
			case 0:
				return 'a';
			case 1:
				return 'b';
			case 2:
				return 'c';
			case 3:
				return 'd';
			case 4:
				return 'e';
			case 5:
				return 'f';
			case 6:
				return 'g';
			case 7:
				return 'h';
			default:
				return '0';
		}
	}
	
	/**
	   * Given 4 integers, ensures all of them represent a coordinate on the board
	   * 
	   * @param x1 A coordinate
	   * @param y1 A coordinate
	   * @param x2 A coordinate
	   * @param y2 A coordinate
	   * @return boolean True if all are in bounds, False otherwise
	   */
	public boolean inBounds(int x1, int y1, int x2, int y2) {
		if(x1 < 0 || x1 > 7)
			return false;
		if(x2 < 0 || x2 > 7)
			return false;
		if(y1 < 0 || y1 > 7)
			return false;
		if(y2 < 0 || y2 > 7)
			return false;
		return true;
	}

	public String intToRealMove(String s){
        char startX = intToLetter(Integer.parseInt(String.valueOf(s.charAt(0))));
        char startY = intToDigit(Integer.parseInt(String.valueOf(s.charAt(1))));

        char endX = intToLetter(Integer.parseInt(String.valueOf(s.charAt(2))));
        char endY = intToDigit(Integer.parseInt(String.valueOf(s.charAt(3))));

	    return "" + startX + startY + " " + endX + endY;
    }

    public void addMove(String s){
	    moveList.add(s);
    }

    public void deleteLastMove(){
        moveList.remove(moveList.size() - 1);
    }

    public List<String> getMoveList(){
        return moveList;
    }



	/* BOARD-SPECIFIC METHODS */
	
	/**
	   * Sets up the board by creating and placing all the pieces
	   */
	public Board() { // Populate board + Player arraylists
		//Pawns
		for(int x = 0; x < 8; x++) {
			board[x][1] = new Pawn('w');
			white.living.add(board[x][1]);
			
			board[x][6] = new Pawn('b');
			black.living.add(board[x][6]);
		}
		
		//Kings
		board[4][0] = new King('w');
		white.k = (King) board[4][0];
		white.living.add(board[4][0]);
		white.k.setXY(4,0);
		
		board[4][7] = new King('b');
		black.k = (King) board[4][7];
		black.living.add(board[4][7]);
		black.k.setXY(4,7);
		
		//Queens
		board[3][0] = new Queen('w');
		white.living.add(board[3][0]);
		
		board[3][7] = new Queen('b');
		black.living.add(board[3][7]);
		
		//Rooks
		board[0][0] = new Rook('w');
		board[7][0] = new Rook('w');
		white.living.add(board[7][0]);
		white.living.add(board[0][0]);
		
		board[0][7] = new Rook('b');
		board[7][7] = new Rook('b');
		black.living.add(board[7][7]);
		black.living.add(board[0][7]);
		
		//Bishops
		board[2][0] = new Bishop('w');
		board[5][0] = new Bishop('w');
		white.living.add(board[2][0]);
		white.living.add(board[5][0]);
		
		board[2][7] = new Bishop('b');
		board[5][7] = new Bishop('b');
		black.living.add(board[2][7]);
		black.living.add(board[5][7]);
		
		//Knights
		board[1][0] = new Knight('w');
		board[6][0] = new Knight('w');
		white.living.add(board[1][0]);
		white.living.add(board[6][0]);
		
		board[1][7] = new Knight('b');
		board[6][7] = new Knight('b');
		black.living.add(board[1][7]);
		black.living.add(board[6][7]);
	}
	
	/**
	   * Resets the en passant booleans for the given color
	   * 
	   * @param color The color for which the en passant variables should be reset
	   */
	public void resetPass(char color) {
		if(color == 'w') {
			for(int i = 0; i < white.living.size(); i++) {
				Piece p = white.living.get(i);
				if(p instanceof Pawn)
					((Pawn) p).passAble = false;
			}
		} else {
			for(int i = 0; i < black.living.size(); i++) {
				Piece p = black.living.get(i);
				if(p instanceof Pawn)
					((Pawn) p).passAble = false;
			}
		}
	}
	
	/**
	   * Prints the board in the required format
	   *
	   */
	public void printBoard() {
		for(int y = 7; y >= 0; y--) {

			for(int x = 0; x < 8; x++) {
				if(board[x][y] != null) {
					System.out.print(board[x][y]);
				} else if(y % 2 == 0 && x % 2 == 0){
					System.out.print("##");
				} else if(y % 2 == 1 && x % 2 == 1) {
					System.out.print("##");
				} else {
					System.out.print("  ");
				}
				System.out.print(" ");
			}
			System.out.println(intToDigit(y));
		}
		for(int x = 0; x < 8; x++) {
			System.out.print(" " + intToLetter(x) + " ");
		}
		System.out.println();
	}

	/**
	 * Gets the data
	 *
	 *  @return piece[] an arraylist of datas
	 */
	public Piece[][] getBoard() {

		return board;
	}

	/**
	   * Given a player, checks if they are in check
	   * 
	   * @param player A char representing the player in question
	   * @return boolean True if in check, False otherwise
	   */
	public boolean check(char player) {
		if(player == 'w') { // white's turn
			for(int x = 0; x < 8; x++) {
				for(int y = 0; y < 8; y++) {
					if(board[x][y] == null)
						continue;
					
					if(board[x][y].color == 'b') {
						if(board[x][y].validMove(this, x, y, white.k.x, white.k.y))
							return true;
					}
				}
			}
			
		} else { // black's turn
			for(int x = 0; x < 8; x++) {
				for(int y = 0; y < 8; y++) {
					if(board[x][y] == null)
						continue;
					
					if(board[x][y].color == 'w') {
						if(board[x][y].validMove(this, x, y, black.k.x, black.k.y))
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	   * Calls check for the current player
	   * 
	   * @return boolean True if in check, False otherwise
	   */
	public boolean check() {
		return check(turn);
	}
	
	/**
	   * Checks if the current player is in checkmate
	   * 
	   * @return boolean True if in checkmate, False otherwise
	   */
	public boolean checkmate() {
		// For each spot on the board
		for(int x1 = 0; x1 < 8; x1++) {
			for(int y1 = 0; y1 < 8; y1++) {
				if(board[x1][y1] == null)
					continue;
				
				// If the piece belongs to you
				if(board[x1][y1].color == turn) {
					
					// For every location on the board
					for(int x2 = 0; x2 < 8; x2++) {
						for(int y2 = 0; y2 < 8; y2++) {
							if(board[x1][y1].validMove(this, x1, y1, x2, y2)) // If the move is allowed
								if(!board[x1][y1].checkTest(this, x1, y1, x2, y2)) // If you make it would your King not be in check
									return false;
						}
					}
						
				}
			}
		}
		
		return true;
	}
	
	/**
	   * Automatically makes a random move for the player
	   * 
	   * @return boolean True if a move was successfully found, False otherwise (Should never be false)
	   */
	public boolean ai() {
		int[] xCoor = {0, 1, 2, 3, 4, 5, 6, 7};
		int[] yCoor = {0, 1, 2, 3, 4, 5, 6, 7};
		int temp, index;
		
		Random r = new Random();
		
		// Randomize xCoor
		for(int i = 0; i < 8; i++) {
			index = r.nextInt(8 - i) + i;
			temp = xCoor[i];
			xCoor[i] = xCoor[index];
			xCoor[index] = temp;
		}
		
		// Randomize yCoor
		for(int i = 0; i < 8; i++) {
			index = r.nextInt(8 - i) + i;
			temp = yCoor[i];
			yCoor[i] = yCoor[index];
			yCoor[index] = temp;
		}
		
		int x1, x2, y1, y2;
		
		// Try moves
		for(int xt1 = 0; xt1 < 8; xt1++) {
			for(int yt1 = 0; yt1 < 8; yt1++) {
				x1 = xCoor[xt1];
				y1 = yCoor[yt1];
				
				// Make sure spot is occupied
				if(board[x1][y1] == null)
					continue;
				
				// Make sure color matches
				if(board[x1][y1].color != turn)
					continue;
				
				// Set default promotion
				char promote = 'Q';
				
				for(int xt2 = 0; xt2 < 8; xt2++) {
					for(int yt2 = 0; yt2 < 8; yt2++) {
						x2 = yCoor[xt2];
						y2 = xCoor[yt2];
						
						// Is move possible?
						if(!(board[x1][y1].validMove(this, x1, y1, x2, y2)))
							continue;
						
						// Would king be ok?
						if(board[x1][y1].checkTest(this, x1, y1, x2, y2))
							continue;
						
						// Make move
						board[x1][y1].makeMove(this, x1, y1, x2, y2);
						
						// Promote if needed
						if(board[x2][y2] instanceof Pawn) {
							if(turn == 'w' && y2 == 7) // white's turn
								board[x2][y2] = white.promote(board[x2][y2], promote);
							else if(turn == 'b' && y2 == 0) // black's turn
								board[x2][y2] = black.promote(board[x2][y2], promote);	
						}
						
						// Pass turn
						if(turn == 'w')
							turn = 'b';
						else
							turn = 'w';
						
						// Reset passAble
						resetPass(turn);

						moveList.add(intToRealMove("" + x1 + y1 + x2 + y2));

						// Return true
						return true;
						
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Returns who's turn it is
	 *
	 * @return char Turn of who's turn it is
	 */
	public char getTurn(){
		return turn;
	}
	
	/**
	   * Executes or prevents each move a player tries to make by performing all checks necessary.
	   * 
	   * @param s A String representing the move the current player is attempting to make
	   * @return boolean True if the move was successfully completed, False otherwise
	   */
	public boolean takeTurn(String s) {
		s = s.trim();
		
		if(s.equals("ai"))
			return ai();
		
		// If its not long enough
//		if(s.length() < 5)
//			return false;
		
		// Set positions
//		int x1 = Board.letterToInt(s.charAt(0));
//		int y1 = Board.digitToInt(s.charAt(1));
//		int x2 = Board.letterToInt(s.charAt(3));
//		int y2 = Board.digitToInt(s.charAt(4));

		int x1 = Integer.parseInt(String.valueOf(s.charAt(0)));
		int y1 = Integer.parseInt(String.valueOf(s.charAt(1)));
		int x2 = Integer.parseInt(String.valueOf(s.charAt(2)));
		int y2 = Integer.parseInt(String.valueOf(s.charAt(3)));
		
		// Set promotion
		char promote = 'p';
		if(s.length() == 5)
			promote = s.charAt(4);
		if(promote != 'Q' && promote != 'B' && promote != 'N' && promote != 'R' && promote != 'p')
			return false;
		
		// Make sure x's and y's are in bounds
		if(!inBounds(x1, y1, x2, y2))
			return false;
		
		testPrint("In Bounds.");
		
		// Make sure location is not null
		if(board[x1][y1] == null)
			return false;
		
		testPrint("Not null.");
		
		// Make sure this is the right player
		if(board[x1][y1].color != turn)
			return false;
		
		testPrint("Correct Player.");
		
		// Is move possible?
		if(!(board[x1][y1].validMove(this, x1, y1, x2, y2)))
			return false;
		
		testPrint("Move Possible.");
		
		// Would king be ok?
		if(board[x1][y1].checkTest(this, x1, y1, x2, y2))
			return false;
		
		testPrint("Test complete.");
		
		// Make move
		board[x1][y1].makeMove(this, x1, y1, x2, y2);
		
		testPrint("Move Made.");
		
		// Promote if needed
		if(board[x2][y2] instanceof Pawn) {
			if(turn == 'w' && y2 == 7) // white's turn
				board[x2][y2] = white.promote(board[x2][y2], promote);
			else if(turn == 'b' && y2 == 0) // black's turn
				board[x2][y2] = black.promote(board[x2][y2], promote);	
		}
		
		// Pass turn
		if(turn == 'w')
			turn = 'b';
		else
			turn = 'w';
		
		// Reset passAble
		resetPass(turn);
		
		// Return true
		return true;
	}
	
}
