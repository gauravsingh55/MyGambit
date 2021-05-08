/**
* Engine for the Chess game.
*
* @author  Liam Davies, Kevin Lee
* @version 1.0
* @since   2018-04-12 
*/

package com.example.mg.mygambit.src.chess;

import com.example.mg.mygambit.src.board.Board;

import java.util.Scanner;

public class Chess {
	
	/**
	   * Runs the game of chess and handles draws, resigns and check(mate)s
	   * 
	   * @param args N/A
	   */
	public static void main(String[] args) {
		Board b = new Board();
		Scanner s = new Scanner(System.in);
		
		boolean cont = false;
		boolean draw = false;
		
		while(true) {
			b.printBoard();
			// Handle check(mate)
			if(b.check()) {
				System.out.println("\nCheck");
			}
			if(b.checkmate()) {
				System.out.println("\nCheckmate\n\nBlack wins");
				break;
			}
			
			while(!cont) {
				System.out.print("\nWhite's Turn: ");
				String input = s.nextLine();
				
				if(input.trim().equals("resign")) {
					System.out.println("\nBlack wins");
					System.exit(0);
				}
				
				if(draw)
					if(input.trim().equals("draw"))
						System.exit(0);
				
				draw = false;
				
				if(input.length() > 6)
					if(input.trim().substring(input.trim().length() - 5, input.trim().length()).equals("draw?"))
						draw = true;
					
			
				if(!b.takeTurn(input))
					System.out.print("Illegal move, try again");
				else
					cont = true;
			}
			System.out.println();
			
			cont = false;
			
			b.printBoard();
			// Handle check(mate)
			if(b.check()) {
				System.out.println("\nCheck");
			}
			if(b.checkmate()) {
				System.out.println("\nCheckmate\n\\nWhite wins");
				break;
			}
			
			
			while(!cont) {
				System.out.print("\nBlack's Turn: ");
				String input = s.nextLine();
				
				if(input.trim().equals("resign")) {
					System.out.println("\nWhite wins");
					System.exit(0);
				}
				
				if(draw)
					if(input.trim().equals("draw"))
						System.exit(0);
				
				draw = false;
				
				if(input.length() > 6)
					if(input.trim().substring(input.trim().length() - 5, input.trim().length()).equals("draw?"))
						draw = true;
			
				if(!b.takeTurn(input))
					System.out.print("Illegal move, try again");
				else
					cont = true;
			}
			System.out.println();
			
			cont = false;
		}
	}
}
