/**
* Represents all of the data and methods associated with a Bishop
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/

package com.example.mg.mygambit.src.piece;

import com.example.mg.mygambit.src.board.Board;

import java.io.Serializable;

public class Bishop extends Piece implements Serializable {
	private  final long serialVersionUID = 1;

	/**
	   * Creates the piece, sets default values and the color
	   * 
	   * @param c A letter representing the color of the piece
	   */
	public Bishop(char c) {
		super(c);
		type = 'B';
	}
	
	/**
	   * Checks if a move is valid for a Bishop
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   * @return boolean True if the Bishop is capable of this movement, False otherwise
	   */
	public boolean validMove(Board b, int x1, int y1, int x2, int y2) {
		if((x1 == x2 && y1 == y2) || Math.abs(x1 - x2) != Math.abs(y1 - y2)) // If invalid move type
			return false;

		int tx = x1;
		int ty = y1;

		// Is something in the way?
		if(x1 > x2) {
			if(y1 > y2) { // - down left
				tx--;
				ty--;
				while(tx != x2 && ty != y2) {
					if(b.board[tx][ty] != null)
						return false;
					tx--;
					ty--;
				}

			} else { // y1 < y2 - up left
				tx--;
				ty++;
				while(tx != x2 && ty != y2) {
					if(b.board[tx][ty] != null)
						return false;
					tx--;
					ty++;
				}
			}

		} else { // x2 > x1
			if(y1 > y2) { // - down right
				tx++;
				ty--;
				while(tx != x2 && ty != y2) {
					if(b.board[tx][ty] != null)
						return false;
					tx++;
					ty--;
				}
			} else { // y1 < y2 - up right
				tx++;
				ty++;
				while(tx != x2 && ty != y2) {
					if(b.board[tx][ty] != null)
						return false;
					tx++;
					ty++;
				}
			}
		}

		if(b.board[x2][y2] != null) // If capturing
			if(b.board[x2][y2].color == color) // If same color
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
