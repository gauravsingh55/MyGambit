/**
* Represents all of the data and methods associated with a King
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/

package com.example.mg.mygambit.src.piece;

import com.example.mg.mygambit.src.board.Board;

import java.io.Serializable;

public class King extends Piece implements Serializable {
	private  final long serialVersionUID = 1;

	public int x, y;
	
	/**
	   * Creates the piece, sets default values and the color
	   * 
	   * @param c A letter representing the color of the piece
	   */
	public King(char c) {
		super(c);
		type = 'K';
	}

	public King(King k, char c) {
		super(c);
		type = 'K';
		this.x = k.x;
		this.y = k.y;
	}

	/**
	   * Sets the x and y coordinates for the King
	   * 
	   * @param x An int representing the x coordinate of the king
	   * @param y An int representing the y coordinate of the king
	   */
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	   * Checks if a move is valid for a King
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   * @return boolean True if the King is capable of this movement, False otherwise
	   */
	public boolean validMove(Board b, int x1, int y1, int x2, int y2) {
		if((x1 == x2 && y1 == y2) || Math.abs(y2 - y1) > 1) // If it didn't move, or tried to move > 1 vertically
			return false;
		
		if(Math.abs(x1 - x2) == 2) { // Trying to do castling
			if(y1 != y2) // Trying to move vertically as well
				return false;
			
			if(hasMoved) // If king has moved
				return false;
			
			if(x1 > x2) { // - left rook
				if(b.board[0][y1] == null) // If there is no piece there
					return false;
				
				if(!(b.board[0][y1] instanceof Rook) || b.board[0][y1].hasMoved) //If the piece isn't a rook or has moved
					return false;
				
				if(b.board[1][y1] != null || b.board[2][y1] != null || b.board[3][y1] != null) // If all spaces aren't clear
					return false;
				
				if(checkTest(b, x1, y1, x2+1, y2)) // If the king can't pass through that spot
					return false;
				
			} else { //x1 < x2 - right rook
				if(b.board[7][y1] == null) // If there is no piece there
					return false;
				
				if(!(b.board[7][y1] instanceof Rook) || b.board[7][y1].hasMoved) //If the piece isn't a rook or has moved
					return false;
				
				if(b.board[5][y1] != null || b.board[6][y1] != null) // If all spaces aren't clear
					return false;
				
				if(checkTest(b, x1, y1, x2-1, y2)) // If the king can't pass through that spot
					return false;
			}
			
			return true;
		}
		
		if(Math.abs(x1 - x2) > 1) // Trying to move > 1 horizontally
			return false;
		
		if(b.board[x2][y2] != null) // If the spot is occupied
			if(b.board[x2][y2].color == color) // If its the same color
				return false;
		
		return true;
	}
	
	/**
	   * Checks if this movement would put this player's king in check
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   * @return boolean True if this movement wouldn't put this player's king in check, False otherwise
	   */
	public boolean checkTest(Board b, int x1, int y1, int x2, int y2) {
		Piece captured = b.board[x2][y2];
		boolean cast = false;
		
		b.board[x2][y2] = b.board[x1][y1];
		b.board[x1][y1] = null;
		setXY(x2, y2);



		if(Math.abs(x1 - x2) == 2) { // Trying to do castling
			cast = true;
			if(x1 > x2) { // - left rook
				b.board[x2+1][y1] = b.board[0][y1];
				b.board[0][y1] = null;
			} else { // (x1 < x2) - right rook
				b.board[x2-1][y1] = b.board[7][y1];
				b.board[7][y1] = null;
			}
		}

		// TEST FOR CHECK/CHECKMATE
		boolean result = b.check();
		
		if(cast) { // Reset castling
			if(x1 > x2) { // - left rook
				b.board[0][y1] = b.board[x2+1][y1];
				b.board[x2+1][y1] = null;
			} else { // (x1 < x2) - right rook
				b.board[7][y1] = b.board[x2-1][y1];
				b.board[x2-1][y1] = null;
			}
		}
		
		b.board[x1][y1] = b.board[x2][y2];
		b.board[x2][y2] = captured;
		setXY(x1, y1);
		
		return result;
	}
	
	/**
	   * Moves the piece at the given location to the new one and handles any capturing involved
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   */
	public void makeMove(Board b, int x1, int y1, int x2, int y2) {
		if(Math.abs(x1 - x2) == 2) { // Castling
			b.board[x2][y2] = b.board[x1][y1];
			b.board[x1][y1] = null;
			
			if(x1 > x2) { // - left rook
				b.board[x2+1][y1] = b.board[0][y1];
				b.board[0][y1] = null;
				
				b.board[x2+1][y1].hasMoved = true;
			} else { // (x1 < x2) - right rook
				b.board[x2-1][y1] = b.board[7][y1];
				b.board[7][y1] = null;
				
				b.board[x2-1][y1].hasMoved = true;
			}
			
			hasMoved = true;
			return;
		}
		
		if(b.board[x2][y2] != null) { // Capturing
			if(color == 'w')
				b.black.kill(b.board[x2][y2]);
			
			if(color == 'b')
				b.white.kill(b.board[x2][y2]);
		}
		
		// Move Piece
		b.board[x2][y2] = b.board[x1][y1];
		b.board[x1][y1] = null;
		
		hasMoved = true;
		setXY(x2, y2);
	}
	
}
