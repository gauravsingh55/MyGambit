/**
* Represents all of the data and methods associated with a Rook
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/

package com.example.mg.mygambit.src.piece;

import com.example.mg.mygambit.src.board.Board;

import java.io.Serializable;

public class Rook extends Piece implements Serializable {
	private  final long serialVersionUID = 1;

	/**
	   * Creates the piece, sets default values and the color
	   * 
	   * @param c A letter representing the color of the piece
	   */
	public Rook(char c) {
		super(c);
		type = 'R';
	}
	
	/**
	   * Checks if a move is valid for a Rook
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   * @return boolean True if the Rook is capable of this movement, False otherwise
	   */
	public boolean validMove(Board b, int x1, int y1, int x2, int y2) {
		if((x1 == x2 && y1 == y2) || (x1 != x2 && y1 != y2)) // If it didn't move or tried to move 'non-carindally'
			return false;
		
		if(x1 != x2) { // Horizontal movement
			if(x1 > x2) {
				for(int i = x1 - 1; i > x2; i--) {
					if(b.board[i][y1] != null)
						return false;
				}
			} else { // x1 < x2
				for(int i = x1 + 1; i < x2; i++) {
					if(b.board[i][y1] != null)
						return false;
				}
			}
			
		} else { // Vertical movement
			if(y1 > y2) {
				for(int i = y1 - 1; i > y2; i--) {
					if(b.board[x1][i] != null)
						return false;
				}
			} else { // y1 < y2
				for(int i = y1 + 1; i < y2; i++) {
					if(b.board[x1][i] != null)
						return false;
				}
			}
		}
		
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
		
		b.board[x2][y2] = b.board[x1][y1];
		b.board[x1][y1] = null;
		
		// TEST FOR CHECK/CHECKMATE
		boolean result = b.check();
		
		b.board[x1][y1] = b.board[x2][y2];
		b.board[x2][y2] = captured;
		
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
	}
}
