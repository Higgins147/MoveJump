/**
 * MoveJump Board
 * DCU CA664 GDF Assignment 
 * 
 * This Class contains the methods used to implement a solution for the MoveJump puzzle
 * 
 * @title MoveJump Board
 * @author John Coleman
 * @date 2016-04-21
 * @version 3.1
 * 
 */


package MoveJump;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class MoveJump {
	
	ArrayList<String> moveJumpArray = new ArrayList<String>();		// ArrayList to represent the peg board
	
	int numOfPegs;				// Total number of each colour peg
	int blankPosition;			// The location of the space position on the board
	int numOfMoves;				// Total number of moves required to solve the puzzle
	int [] moves;				// Array that stores integer values(0-3) that represent the 4 possibleMove
	int counter = 0;			// Counts the number of moves taken
	
	/*
	 * The 4 possible moves allowed in the rules of the puzzle
	 */
	
	String [] possibleMoves = {	"Move peg to the immediate left of the space into the empty space\n",
								"Move peg to the immediate right of the space into the empty space\n",
								"Move peg two positions to the left of the space into the empty space\n",
								"Move peg two positions to the right of the space into the empty space\n"};	

	BufferedWriter output;		// Solution output file
	
	/*
	 * Method to initialise variables based on the user inputed number of holes
	 */
	
	void setValues (int numOfHoles){
		
		numOfPegs = numOfHoles/2;
		blankPosition = numOfPegs;
		numOfMoves = (numOfPegs * numOfPegs) + (numOfPegs * 2);
		moves = new int [numOfMoves];
		
	}

	/*
	 * Method to fill the board with initial peg positions
	 */
	
	void fillArray (){
		for(int i = 0; i < numOfPegs; i++){
			moveJumpArray.add("Black");		// adds a String 'Black' to the ArrayList to represent a peg on the board
		}
		
		moveJumpArray.add("_____");			// adds a String '_____' to the ArrayList to represent a space on the board
		
		for(int i = 0; i < numOfPegs; i++){
			moveJumpArray.add("White");		// adds a String 'White' to the ArrayList to represent a peg on the board
		}
	}

	/*
	 * Method to swap pegs
	 */
	
	void useSwapPegs(){
		int direction = -1;			// Initialised to -1. -1 is Left, 1 is Right.
		int singleJump = 1;			// Number of single jumps to be made
		int doubleJump;				// Number of double jumps to be made
		
		/*
		 * 4 different stages were identified in the pattern
		 * Stage 1 	Sequence is double jump(s) followed by one single jump followed by direction change.
		 * 			Double jumps start at 0 and increase by 1 each time the direction is changed.
		 * 			Stage 1 is complete when number of double jumps is one less than the number of each colour peg. 
		 * Stage 2	Sequence is Double jumps followed by direction change
		 * 			Stage 2 is complete when number of double jumps equals number of each colour peg
		 * Stage 3	Sequence is single jump followed by double jump(s) followed by direction change.
		 * 			Double jumps start at one less than number of each colour peg and decrease by 1 each time the direction is changed.
		 * 			Stage 3 is complete when number of double jumps is one.
		 * Stage 4	Sequence is one final single jump
		 */
		
		for(doubleJump = 0; doubleJump < numOfPegs; doubleJump++){				// Stage 1
			swapPegs(direction, 1, singleJump, doubleJump, blankPosition);		
			direction = direction * -1;											// Changes direction
		}
		
		swapPegs(direction, 0, 0, numOfPegs, blankPosition);					// Stage 2
		direction = direction * -1;												// Changes direction
			
		for(doubleJump = numOfPegs - 1; doubleJump > 0; doubleJump--){			// Stage 3
			swapPegs(direction, -1, singleJump, doubleJump, blankPosition);
			direction = direction * -1;											// Changes direction
		}
		
		Collections.swap(moveJumpArray, blankPosition, blankPosition - 1);		// Stage 4
		
		System.out.println("\nStep " + (counter + 1));
		System.out.println(possibleMoves[moves[counter]]);
		System.out.println(moveJumpArray);
		
		/*
		 * Output Stage 4 to solution file
		 */
		
		try{
			output.write("\nStep " + (counter + 1) + ": \r\n");
			output.write(possibleMoves[moves[counter]] + "\r\n\r\n");
			
			for(String s: moveJumpArray){
				output.write(s + " ");
			}
			output.write("\r\n\r\n");
			
		}catch(IOException e){
				System.out.println("Error: " + e);
			}
		}
	
	/*
	 * Method to execute the solution and output steps to the solution file.
	 */
	
	void swapPegs(int direction, int upDown, int singleJump, int doubleJump, int blankPosition){
		
		int swapPosition = blankPosition + direction;							// Identifies the peg to move
		
		/*
		 * Execute single jumps in Stage 3 of the solution
		 */
		
		if(singleJump > 0 && upDown == -1){								
			Collections.swap(moveJumpArray, this.blankPosition, swapPosition);	// Swap the active peg into the empty space
			this.blankPosition = swapPosition;									// Previous active peg position is now assigned to space
			
			if(direction == -1){			// -1 represents Left, 1 represents Right
				moves[counter] = 0;			// Move peg to the immediate left of the space into the empty space
			}else{
				moves[counter] = 1;			// Move peg to the immediate right of the space into the empty space
			}
						
			System.out.println("\nStep " + (counter + 1));
			System.out.println(possibleMoves[moves[counter]]);
			System.out.println(moveJumpArray);
			
			/*
			 * Output step details to solution file
			 */
			
			try{
			output.write("\nStep " + (counter + 1) + ": \r\n");				// Step number
			output.write(possibleMoves[moves[counter]] + "\r\n\r\n");		// Move instruction, 1 of 4 possible moves
			
			for(String s: moveJumpArray){					
				output.write(s + " ");										// Current status of the board
			}
			
			output.write("\r\n\r\n");
			
			}catch(IOException e){
				System.out.println("Error: " + e);
			}
			
			counter++;
		}
		
		/*
		 * Execute double jumps for Stage 1, 2 and 3
		 */
			
		for(int i = 0; i < doubleJump; i++){
			swapPosition = this.blankPosition + (2 * direction);					// Identifies the peg to move
			Collections.swap(moveJumpArray, this.blankPosition, swapPosition);		// Swap the active peg into the empty space
			this.blankPosition = swapPosition;										// Previous active peg position is now assigned to space
			
			if(direction == -1){				// -1 represents Left, 1 represents Right
				moves[counter] = 2;				// Move peg two positions to the left of the space into the empty space
			}else{
				moves[counter] = 3;				// Move peg two positions to the right of the space into the empty space
			}
			
			System.out.println("\nStep " + (counter + 1));
			System.out.println(possibleMoves[moves[counter]]);
			System.out.println(moveJumpArray);
			
			/*
			 * Output step details to solution file
			 */
			
			try{
				output.write("\nStep " + (counter + 1) + ": \r\n");
				output.write(possibleMoves[moves[counter]] + "\r\n\r\n");
				
				for(String s: moveJumpArray){
					output.write(s + " ");
				}
				
				output.write("\r\n\r\n");
				
				}catch(IOException e){
					System.out.println("Error: " + e);
				}
						
			counter++;
		}
		
		swapPosition = this.blankPosition + direction;							// Identifies the peg to move
		
		/*
		 * Execute single jumps in Stage 1 of the solution
		 */
		
		if(singleJump > 0 && upDown == 1){										// 
			Collections.swap(moveJumpArray, this.blankPosition, swapPosition);
			this.blankPosition = swapPosition;
			
			if(direction == -1){
				moves[counter] = 0;
			}else{
				moves[counter] = 1;
			}
						
			System.out.println("\nStep " + (counter + 1));
			System.out.println(possibleMoves[moves[counter]]);
			System.out.println(moveJumpArray);
			
			try{
				output.write("\nStep " + (counter + 1) + ": \r\n");
				output.write(possibleMoves[moves[counter]] + "\r\n\r\n");
				
				for(String s: moveJumpArray){
					output.write(s + " ");
				}
				
				output.write("\r\n\r\n");
				
			}catch(IOException e){
				System.out.println("Error: " + e);
			}
				
			counter++;
		}
		
	}

}
