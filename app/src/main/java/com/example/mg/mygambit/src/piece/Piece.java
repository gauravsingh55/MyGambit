/**
* Represents all of the data and methods associated with a Piece (abstract)
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/

package com.example.mg.mygambit.src.piece;

import com.example.mg.mygambit.src.board.Board;

import java.io.Serializable;

public abstract class Piece implements Serializable {
	private  final long serialVersionUID = 1;

	public char color;
	public char type;
	public boolean hasMoved;
	
	/**
	   * Creates the piece, sets default values and the color
	   * 
	   * @param c A letter representing the color of the piece
	   */
	public Piece(char c) {
		color = c;
		hasMoved = false;
	}
	
	/**
	   * Checks if a move is valid for a given piece
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   * @return boolean True if the piece is capable of this movement, False otherwise
	   */
	public abstract boolean validMove(Board b, int x1, int y1, int x2, int y2);
	
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
	public abstract boolean checkTest(Board b, int x1, int y1, int x2, int y2);
	
	/**
	   * Moves the piece at the given location to the new one and handles any capturing involved
	   * 
	   * @param b The board on which the move is taking place
	   * @param x1 The x coordinate of the piece to be moved
	   * @param y1 The y coordinate of the piece to be moved
	   * @param x2 The x coordinate of where its moving to
	   * @param y2 The y coordinate of where its moving to
	   */
	public abstract void makeMove(Board b, int x1, int y1, int x2, int y2);
	
	/**
	   * Gets a string representing the piece
	   * 
	   * @return String A string representing the piece
	   */
	public String toString() {
		return "" + color + type;
	}




}
