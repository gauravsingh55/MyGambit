/**
* Represents all of the data and methods associated with a Pawn
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/

package com.example.mg.mygambit.src.piece;

import com.example.mg.mygambit.src.board.Board;

import java.io.Serializable;

public class Pawn extends Piece implements Serializable {
	private  final long serialVersionUID = 1;

	public boolean passAble = false;
	
	/**
	   * Creates the piece, sets default values and the color
	   * 
	   * @param c A letter representing the color of the piece
	   */
	public Pawn(char c) {
		super(c);
		type = 'p';
	}
		
	/**
	   * Checks if a move is valid for a Pawn
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   * @return boolean True if the Pawn is capable of this movement, False otherwise
	   */
	public boolean validMove(Board b, int x1, int y1, int x2, int y2) {
		// Must be trying to capture
		if(x1 != x2){
			if(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) // Valid distance
				return false;
			
			if(color == 'w' && y2 > y1) { // Check against color: white
				if(b.board[x2][y2] != null)
					if(b.board[x2][y2].color != color)
						return true;
				if(b.board[x2][y2-1] != null)
					if(b.board[x2][y2-1] instanceof Pawn && b.board[x2][y2-1].color != color)
						return ((Pawn) b.board[x2][y2-1]).passAble;
				
			} else if(color == 'b' && y2 < y1){ // Check against color: black
				if(b.board[x2][y2] != null)
					if(b.board[x2][y2].color != color)
						return true;
				if(b.board[x2][y2+1] != null)
					if(b.board[x2][y2+1] instanceof Pawn && b.board[x2][y2+1].color != color)
						return ((Pawn) b.board[x2][y2+1]).passAble;	
			}
			
			return false;	
		}
		
		// No capture
		if(Math.abs(y1 - y2) > 2 || (Math.abs(y1 - y2) > 1 && hasMoved)) // Invalid distance
			return false;
		
		if(b.board[x2][y2] != null) // Can't capture this way
			return false;
		
		if((color == 'w' && y2 < y1) || (color == 'b' && y2 > y1)) // Invalid direction
			return false;
		
		if(Math.abs(y1 - y2) == 2) { // If attempting to move multiple spaces, make sure travel is possible
			if(color == 'w' && b.board[x1][y1+1] != null)
				return false;
			if(color == 'b' && b.board[x1][y1-1] != null)
				return false;
		}
		
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
		// Simulate capture
		int takenX = x2, takenY = y2;
		Piece captured = b.board[x2][y2];
		if(captured == null && x1 != x2) {
			if(color == 'w') // Check against color: white
				if(b.board[x2][y2-1] != null)
					if(b.board[x2][y2-1] instanceof Pawn)
						if(((Pawn) b.board[x2][y2-1]).passAble){
							captured = b.board[x2][y2-1];
							takenX = x2;
							takenY = y2-1;
						}
			if(color == 'b') // Check against color: black
				if(b.board[x2][y2+1] != null)
					if(b.board[x2][y2+1] instanceof Pawn)
						if(((Pawn) b.board[x2][y2+1]).passAble){
							captured = b.board[x2][y2+1];
							takenX = x2;
							takenY = y2+1;
						}
		}
			
		b.board[x2][y2] = b.board[x1][y1];
		b.board[x1][y1] = null;
		
		// TEST FOR CHECK/CHECKMATE
		boolean result = b.check();
		
		// Reset board
		b.board[x1][y1] = b.board[x2][y2];
		b.board[x2][y2] = null;
		b.board[takenX][takenY] = captured;
		
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
		// Handle any capturing
		if(b.board[x2][y2] != null) {
			if(color == 'w')
				b.black.kill(b.board[x2][y2]);
			
			if(color == 'b')
				b.white.kill(b.board[x2][y2]);
			
			
		} else if(x1 != x2) {
			if(color == 'w') // Check against color: white
				if(b.board[x2][y2-1] != null)
					if(b.board[x2][y2-1] instanceof Pawn)
						if(((Pawn) b.board[x2][y2-1]).passAble) {
							b.black.kill(b.board[x2][y2-1]);
							b.board[x2][y2-1] = null;
						}

			if(color == 'b') // Check against color: black
				if(b.board[x2][y2+1] != null)
					if(b.board[x2][y2+1] instanceof Pawn)
						if(((Pawn) b.board[x2][y2+1]).passAble) {
							b.white.kill(b.board[x2][y2+1]);
							b.board[x2][y2+1] = null;
						}
		}
			
		b.board[x2][y2] = b.board[x1][y1];
		b.board[x1][y1] = null;
		
		if(Math.abs(y2 - y1) == 2)
			passAble = true;
		
		hasMoved = true;
	}
	
}