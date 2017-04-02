/**
 * MoveJump Board
 * DCU CA664 GDF Assignment 
 * 
 * This is a program that will tell you the number of steps required to solve the MoveJump puzzle.
 * The program will ask for the number of holes on the board and will output the results to a txt file.
 * This txt file will give you the number of moves and detail step by step how to solve the puzzle.
 * 
 * @title MoveJump Board
 * @author John Coleman
 * @date 2016-04-21
 * @version 3.1
 * 
 */

package MoveJump;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;


public class Main {

	public static void main(String[] args) {
		
		MoveJump mJ = new MoveJump();
		
		/*
		 * User is asked to enter the number of holes on the playing board.
		 * A check is performed to confirm if the user has entered an ODD number.
		 * When the user correctly enters an ODD number the board starting values are initialised.
		 */
		
		try{
			int numOfHoles = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the number of holes on the board: ","MoveJump Solution",JOptionPane.INFORMATION_MESSAGE));
			
			while (numOfHoles % 2 == 0){
				JOptionPane.showMessageDialog(null,"You must enter an ODD number\n","MoveJump Solution",JOptionPane.INFORMATION_MESSAGE);
				numOfHoles = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the number of holes on the board: ","MoveJump Solution",JOptionPane.INFORMATION_MESSAGE));
			}
			
			mJ.setValues(numOfHoles);
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Run again and input a Number ONLY!\n","MoveJump Solution",JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Invalid Input");
			System.exit(0);
		}
		
		/*
		 * A GUI input dialog box is used to request the solution output filename.
		 * The file will contain the total number of moves needed to solve the puzzle and the steps to achieve this.
		 */
		
		try{	
			String solutionFile = JOptionPane.showInputDialog(null,"Choose a name for the solution file: ","MoveJump Solution",JOptionPane.INFORMATION_MESSAGE);
			
			mJ.output = new BufferedWriter(new FileWriter(solutionFile));		// opens the output file
					
			System.out.println("It will take " + mJ.numOfMoves + " moves");		// output number of moves to the terminal
			mJ.output.write("It will take " + mJ.numOfMoves + " moves\r\n\r\n");
			
			mJ.fillArray();														// initialises the array
			System.out.println(mJ.moveJumpArray);								// output initial array to terminal
			
			mJ.useSwapPegs();													// calls the method to create the steps of the solution
	
			mJ.output.close();													// closes the output file
			
		} catch(NullPointerException e){
			System.out.println("User selected Cancel: Program Closes");
			System.exit(0);
			
		} catch (IOException e){
			System.out.println("Error: " + e);
		}
		
		/*
		 * Output dialog pop-up box to notify user solution file has been created
		 */
		
		JOptionPane.showMessageDialog(null,"Your Solution file has been created\n","MoveJump Solution",JOptionPane.INFORMATION_MESSAGE);
		
		
		/*
		 * Testing the number of moves in the swapPegs algorithm equals the number of moves from the identified pattern formula found using numOfMoves.
		 * Note: variable Counter starts at 0 so we need to add 1 to this to begin.
		 */
		
		if(mJ.counter + 1 == mJ.numOfMoves){
			System.out.println("\nYour algorithm is counting the correct number of steps\nBoth variables are indicating " + mJ.numOfMoves + " steps."); 
		}else{
			System.out.println("There's an error in your algorithm");
		}
		
	}	// End of main method
	
}	// End of Main Class
