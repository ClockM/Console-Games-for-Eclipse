import java.util.InputMismatchException;
import java.util.Scanner;

/** Things to add/do:
 * 	Clean up the code, especially comments
 * 	A system for easy input for making initial boards
 * 	A system for incrementing the board manually or automatically
 * 	A system for saving and loading boards
 *  A system for hexagonal boards**/

public class ConwayGame {
	
	public double verNum = 0.02;
	
	public void runGame()
	{
		System.out.println("Welcome to GoL v. " + verNum + "!");
		System.out.println();
		
		Scanner userInput = new Scanner(System.in);
		int bX;
		int bY;
		
		System.out.println("Length of board? (In cells)");
		bX = userInput.nextInt();
		System.out.println("Height of board? (In cells)");
		bY = userInput.nextInt();
		
		userInput.close(); //Note to self: Be sure to close input stream when done
		
		/*For testing purposes:
		boolean[][] b = {{false, false, true, false, false},
				 		 {false, false, true, false, false},
				 		 {false, false, true, false, false},
				 		 {false, false, true, false, false},
				 		 {false, false, true, false, false},}; //The "board"- True is alive, false is dead
				 		 b = bSwapDim(b);
		*/
		boolean[][] b = new boolean[bX][bY];
		
		
		printBoard(b);
		for(int i=0;i<=10;i++)
		{
			b = bNext(b);
			printBoard(b);
		}
		
	}
	
	private boolean[][] bNext(boolean[][] b) //Returns the next step of the board
	{
		boolean[][] bNext = new boolean[b.length][b[0].length]; //The array to be returned, the next increment of the board
		
		//The first set of nested loops goes through the entire board, same order as drawing
		for(int j=b[0].length-1;j>=0;j--){for(int i=0;i<b.length;i++){
				int n = 0; // Used to count the # of neighbors
				
				//Second set of nested loops used to count neighbors of selected cell
				for(int k = (i-1);k<=(i+1);k++){for(int l = (j-1);l<=(j+1);l++){   
				//Doesn't do anything if b[k][l] doesn't actually exist
				try{if(b[k][l]){n++;}}catch(ArrayIndexOutOfBoundsException e){}
				}}
				
				if(b[i][j]){n--;} //Subtracts itself from # of neighbors if alive
			
				//Determines if this cell is alive or dead next step
				if(b[i][j])
				{if(n>=2 && n<=3){bNext[i][j] = true;}}
				else
				{if(n==3){bNext[i][j] = true;}}
		}}
		
		return bNext;
	}
	
	//Returns a 2D boolean array such that b[i][j] becomes b[j][i] for all values of i and j
	private boolean[][] bSwapDim(boolean[][] b)
	{
		boolean[][] bInv = new boolean[b[0].length][b.length];
		
		for(int j=b[0].length-1;j>=0;j--){for(int i=0;i<b.length;i++){
			bInv[j][i] = b[i][j];
		}}
		
		return bInv;
	}
	
	private void printBoard(boolean[][] b)
	{
		System.out.print("----------------------------");
		makeSpace(5);
		/* NOTE! The order of j and i are swapped around to accommodate for
		 * printing in console, such that each cell is printed left-right, up-down.
		 * j is still Y and i is still X though... */
		for(int j=b[0].length-1;j>=0;j--){for(int i=0;i<b.length;i++){
			if(b[i][j]){System.out.print("1");}else{System.out.print("0");}
			if(i==b.length-1){System.out.println();}else{System.out.print(" ");}
		}}
	}
	
	private void makeSpace(int n) //It's a shame I can't "clear" the console
	{for(int i=0;i<n;i++){System.out.println();}}

}
