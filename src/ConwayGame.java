
/**
 *  @author Will Brown (AKA ClockM)
 *  
 *  Things to add/do:
 * 	Improve clarity of comments
 * 	
 * 	Road map:
 * 	ver. 0.3- Incrementing the board manually or automatically
 *  ver. 0.4- Adjustable settings for game rules
 * 	ver. ???- Saving and loading boards
 *  ver. ???- Hexagonal boards
 *  **/

public class ConwayGame {
	
	public double verNum = 0.21;
	
	// Runs the game. Not to be confused with run()!
	public void runGame()
	{
		System.out.println("Welcome to GoL v. " + verNum + "!");
		
		/* mainSelect menu (AKA "Main Menu"):
		 * (n == 1): calls newGame() to prompt the dimensions of the board and then open the "board editor"
		 * (n == 2): calls loadGame() and the loadSelect menu
		*/
		boolean[][] b = null;
		int mainSelect = ConGameUtil.promptInt("\n1. New game\n2. Load game (test)", 1, 2);
		switch(mainSelect)
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
	
	// Prompts the dimensions of the new board and then opens the board editor
	private boolean[][] newGame()
	{
		// Prompts the length and height, then creates bNew with every cell false
		boolean[][] bNew = new boolean[ConGameUtil.promptInt("Length of the board? (In cells)", 1, 10)][ConGameUtil.promptInt("Height of the board? (In cells)", 1, 10)];
		
		// This segment of code is the "board editor", and it contains 2 menus: "rowSelect" and "cellSelect"
		// I may eventually migrate this to its own "boardEditor" method
		
		/* rowSelect menu:
		 * (n == 0): Returns bNew
		 * (1 <= n <= bNew[0].length): Opens up the cellSelect menu for specified row (nth row from the bottom)
		*/
		boolean rowSelectDone = false;
		while (!rowSelectDone)
		{
			ConGameUtil.makeSpace(0);
			printBoard(bNew);
			
			int rowSelect = ConGameUtil.promptInt("Enter the # of a row (from the bottom) to edit it.\nEnter 0 once you're done.", 0, bNew[0].length);
			if(rowSelect == 0)
			{
				rowSelectDone = true;
			}
			else
			{
				/* cellSelect menu:
				 * (n == 0): Sends user back to the rowSelect menu
				 * (1 <= n <= bNew.length): changes the value of the specified cell (nth cell from the left)
				*/
				boolean cellSelectDone = false;
				while(!cellSelectDone)
				{
					ConGameUtil.makeSpace(0);
					printRow(bNew, rowSelect - 1);
					int cellSelect = ConGameUtil.promptInt("Enter the # of a cell (from the left) to change it.\nEnter 0 once you're done.", 0, bNew.length);
					if(cellSelect == 0)
					{
						cellSelectDone = true;
					}
					else
					{
						bNew[cellSelect - 1][rowSelect - 1] = !bNew[cellSelect - 1][rowSelect - 1];
					}
				}
			}
		}
		
		return bNew;
	}
	
	// Loads test boards, will later read files and load them (Hopefully)
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
		
		/* loadSelect menu:
		 * (n == 1): returns test1
		 * (n == 2): returns test2
		*/
		int loadSelect = ConGameUtil.promptInt("1. Load test1 \n2. Load test2", 1, 2);
		switch(loadSelect)
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