
/** Things to add/do:
 * 	Improve clarity of comments
 * 	A system for easy input for making initial boards
 * 	A system for incrementing the board manually or automatically
 * 	A system for saving and loading boards
 *  A system for hexagonal boards**/

public class ConwayGame {
	
	public double verNum = 0.20;
	
	// Runs the game. Not to be confused with run()
	public void runGame()
	{
		System.out.println("Welcome to GoL v. " + verNum + "!");
		
		// Main menu determines the board. newGame and loadGame may be void later
		boolean[][] b = null;
		int menuOption = ConGameUtil.promptInt("\n1. New game\n2. Load game (test)", 1, 2);
		switch(menuOption)
		{
			case 1: b = newGame();
			break;
			case 2: b = loadGame();
			break;
		}
		
		//Prints the first few steps on the board
		ConGameUtil.makeSpace(4);
		printBoard(b);
		for(int i=0;i<=5;i++)
		{
			b = nextBoard(b);
			ConGameUtil.makeSpace(4);
			printBoard(b);
		}
		
	}
	
	// Creates a new board
	private boolean[][] newGame()
	{
		// Prompts the length and height of the board and stores the dimensions in b
		boolean[][] bNew = new boolean[ConGameUtil.promptInt("Length of the board? (In cells)", 1, 10)][ConGameUtil.promptInt("Height of the board? (In cells)", 1, 10)];
		
		boolean doneEditingBoard = false;
		while (!doneEditingBoard)
		{
			ConGameUtil.makeSpace(0);
			printBoard(bNew);
			
			int rowOption = ConGameUtil.promptInt("Enter the # of a row (from the bottom) to edit it.\nEnter 0 once you're done.", 0, bNew[0].length);
			if(rowOption == 0)
			{
				doneEditingBoard = true;
			}
			else
			{
				boolean doneEditingRow = false;
				while(!doneEditingRow)
				{
					ConGameUtil.makeSpace(0);
					printRow(bNew, rowOption-1);
					int cellOption = ConGameUtil.promptInt("Enter the # of a cell (from the left) to change it.\nEnter 0 once you're done.", 0, bNew.length);
					if(cellOption == 0)
					{
						doneEditingRow = true;
					}
					else
					{
						bNew[cellOption - 1][rowOption - 1] = !bNew[cellOption - 1][rowOption - 1];
					}
				}
			}
		}
		
		return bNew;
	}
	
	// Loads test boards, will later read files and load them
	private boolean[][] loadGame()
	{
		// test boards are hard-coded below, as they would appear in the console
		boolean[][] test1 = {{false, false, true, false, false},
 		 					{false, false, true, false, false},
 		 					{false, false, true, false, false},
 		 					{false, false, true, false, false},
 		 					{false, false, true, false, false},};
		
		boolean[][] test2 = {{false, false, true, false, false},
 		 					{false, false, false, false, false},
 		 					{false, false, false, false, false},
 		 					{false, false, true, false, false},
 		 					{false, true, true, true, false},};
		
		ConGameUtil.makeSpace(0);
		int loadMenu = ConGameUtil.promptInt("1. Load test1 \n2. Load test2", 1, 2);
		switch(loadMenu)
		{
			case 1: return ConGameUtil.SwapDim(test1);
			case 2: return ConGameUtil.SwapDim(test2);
			default: return new boolean[1][1];
		}
	}
	
	// Calculates and returns the next "step" of the board.
	private boolean[][] nextBoard(boolean[][] b)
	{
		//The array to be returned, the next increment of the board
		boolean[][] bNext = new boolean[b.length][b[0].length];
		
		//The first set of nested loops goes through the entire board (same order as drawing- Left-right up-down)
		for(int j=b[0].length-1;j>=0;j--)
		{
			for(int i=0;i<b.length;i++)
			{
				// The # of neighbors counted
				int n = 0;
				
				//Second set of nested loops used to count neighbors of selected cell
				for(int k = (i-1);k<=(i+1);k++)
				{
					for(int l = (j-1);l<=(j+1);l++)
					{   
						try
						{
							if(b[k][l])
							{
								n++;
							}
						}
						catch(ArrayIndexOutOfBoundsException e)
						{
							// Does nothing if exception is caught
						}
					}
				} //End of second set of nested for loops
				
				//Subtracts itself from # of neighbors if alive
				if(b[i][j])
				{
					n--;
				} 
			
				/* Determines if this cell is alive or dead next step, such that:
				 * If the cell is alive and has 2 or 3 neighbors, it survives
				 * If the cell is alive and has less than 2 or more than 3 neighbors, it survives
				 * If the cell is dead and has exactly 3 neighbors it becomes alive
				 */
				if(b[i][j])
				{
					if(n>=2 && n<=3)
					{
						bNext[i][j] = true;
					}
				}
				else
				{
					if(n==3)
					{
						bNext[i][j] = true;
					}
				}
			}
		} //End of first set of nested for loops
		return bNext;
	}
	
	/* NOTE! The order of j and i are swapped around to accommodate for
	 * printing in console, such that each cell is printed left-right, up-down.
	 * j is still Y and i is still X though...
	 * NOTE! printBoard() and printRow() are candidates for migration to ConGameUtil*/
	
	// Prints the board passed to it. 1 is alive and 0 is dead.
	// Candidate for migration to ConGameUtil. 
	private void printBoard(boolean[][] b)
	{
		for(int j=b[0].length-1;j>=0;j--)
		{
			printRow(b, j);
			System.out.println();
		}
	}

	// Prints the row (All values of i for j) of the board passed
	private void printRow(boolean[][] b, int j)
	{
		for(int i = 0; i<b.length; i++)
		{
			if(b[i][j])
			{
				System.out.print("1");
			}
			else
			{
				System.out.print("0");
			}
			
			System.out.print(" ");
		}
	}
}