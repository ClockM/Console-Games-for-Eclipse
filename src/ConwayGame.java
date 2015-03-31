
/**
 *  Things to add/do:
 * 	Improve clarity of comments
 * 	
 * 	Road map:
 *  ver. 0.4- Adjustable settings for game rules
 * 	ver. ???- Saving and loading boards
 *  ver. ???- Hexagonal boards
 *  
 *  @author Will Brown (AKA ClockM)
 *  **/

public class ConwayGame extends ConsoleGame{
	
	public ConwayGame()
	{
		super();
		this.version = 0.31;
		this.title = "Conway's Game of Life";
	}
	
	// Runs the game. Not to be confused with run()!
	public void runGame()
	{
		System.out.println("You're playing " + title + " v. " + version + "!");
		
		/* mainSelect menu (AKA "Main Menu"):
		 * (n == 1): Calls editBoard() with prompts for the dimensions of the board as arguments.
		 * (n == 2): Calls loadGame() and the loadSelect menu.
		*/
		boolean[][] b = null;
		int mainSelect = promptInt("\n1: New game\n2: Load game (test)", 1, 2);
		if(mainSelect == 1)
		{
			makeSpace(0);
			b = editBoard(new boolean[promptInt("Length of the board? (In cells)", 1, 50)][promptInt("Height of the board? (In cells)", 1, 50)]);
		}
		else
		{
			b = loadGame();
		}
		
		runBoard:
		while(true)
		{
			makeSpace(1);
			for(int j=b[0].length-1; j >= 0; j--)
			{
				printRow(b, j, 1);
				System.out.println();
			}
			
			/* stepSelect menu:
			 * (n == 0): Breaks the 'while' loop.
			 * (n == 1): Calls nextBoard() to increment b, then loops.
			 * (n == 2): Calls autoBoard() with a prompt for the number of steps to take as the argument, then loops.
			 * (n == 3): Calls editBoard() to edit b, then loops.
			 * (n < 0 || n > 2): Prints a friendly message to the console, then loops without changing the value of b.
			*/
			int stepSelect = promptInt("\n0: Exit | 1: Next | 2: Auto | 3: Edit", 0, 2);
			switch(stepSelect)
			{
				case 0:	break runBoard;
				
				case 1:	b = nextBoard(b);
						break;
				case 2:	b = autoBoard(b, promptInt("0: Cancel | 1-10: Select # of steps", 0, 10));
						break;
				case 3:	b = editBoard(b);
						break;
						
				default:System.out.println("Invalid option for stepSelect!");
			}
		}
		
	}
	
	// Automatically prints the next n steps of the board b passed to it
	// Returns the nth step of b without printing it, so it can be printed later
	// If n == 0 then it returns b without printing anything
	private boolean[][] autoBoard(boolean[][] b, int n)
	{
		boolean[][] bNext = b;
		for(int i = 0; i < n; i++)
		{
			bNext = nextBoard(bNext);
			if(i != n - 1)
			{
				makeSpace(1);
				for(int j = bNext[0].length - 1; j >= 0; j--)
				{
					printRow(bNext, j, 1);
					System.out.println();
				}
			}
		}
		return bNext;
	}
	
	// An interface for editing the board, consisting of 2 menus: "rowSelect" and "cellSelect"
	private boolean[][] editBoard(boolean[][] b)
	{			
		while (true)
		{
			makeSpace(0);
			for(int j=b[0].length-1; j >= 0; j--)
			{
				if(j+1<10)
				{
					System.out.print(" ");
				}
				System.out.print( (j+1) + "|  ");
				printRow(b, j, 2);
				System.out.println();
			}
			
			/* rowSelect menu:
			 * (n == 0): Returns b.
			 * (1 <= n <= b[0].length): Opens up the cellSelect menu for specified row (nth row from the bottom) then loops.
			*/
			int rowSelect = promptInt("\n0: Finish | 1-" + b[0].length + ": Select row", 0, b[0].length);
			if(rowSelect == 0)
			{
				break;
			}
			else
			{
				while(true)
				{
					makeSpace(0);
					if(rowSelect < 10)
					{
						System.out.print(" ");
					}
					System.out.print(rowSelect + "|  ");
					printRow(b, rowSelect - 1, 2);
					System.out.print("\n     ");
					for(int i = 1; i <= b.length; i++)
					{
						System.out.print("_");
						System.out.print("  ");
					}
					System.out.print("\n     ");
					for(int i = 1; i <= b.length; i++)
					{
						System.out.print(i);
						System.out.print(" ");
						if(i<10)
						{
							System.out.print(" ");
						}
					}
					
					/* cellSelect menu:
					 * (n == 0): Sends user back to the rowSelect menu by breaking the nested 'while' loop.
					 * (1 <= n <= b.length): Changes the value of the specified cell (nth cell from the left) then loops.
					*/
					int cellSelect = promptInt("\n\n0: Back | 1-" + b.length + ": Edit cell", 0, b.length);
					if(cellSelect == 0)
					{
						break;
					}
					else
					{
						b[cellSelect - 1][rowSelect - 1] = !b[cellSelect - 1][rowSelect - 1];
					}
				}
			}
		}
		
		return b;
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
		
		makeSpace(0);
		
		/* loadSelect menu:
		 * (n == 1): Returns test1.
		 * (n == 2): Returns test2.
		*/
		int loadSelect = promptInt("1. Load test1 \n2. Load test2", 1, 2);
		switch(loadSelect)
		{
			case 1: return SwapDim(test1);
			case 2: return SwapDim(test2);
			
			default: return new boolean[1][1];
		}
	}
	
	// Calculates and returns the next "step" of the board.
	private boolean[][] nextBoard(boolean[][] b)
	{
		//The array to be returned, the next increment of the board
		boolean[][] bNext = new boolean[b.length][b[0].length];
		
		//The first set of nested loops goes through the entire board (same order as drawing- Left-right up-down)
		for(int j = b[0].length - 1; j >= 0; j--)
		{
			for(int i = 0; i < b.length; i++)
			{
				// The # of neighbors counted
				int n = 0;
				
				//Second set of nested loops used to count neighbors of selected cell
				for(int k = i - 1; k <= i + 1;k++)
				{
					for(int l = (j - 1); l <= (j + 1); l++)
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
				} //End of second set of nested 'for' loops
				
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
				else if(n==3)
				{
					bNext[i][j] = true;
				}
			}
		} //End of first set of nested for loops
		return bNext;
	}
	
	/* NOTE! The values for j and i accommodate for printing in console,
	 * such that each cell is printed left-right, up-down.
	 * 
	 * Thus j corresponds with the Y coordinate and i corresponds with the X coordinate
	 * of each cell from the bottom-left corner of the board.
	*/

	// Prints the row (All values of i for j) of the board passed
	private void printRow(boolean[][] b, int row, int spaces)
	{
		for(int i = 0; i < b.length; i++)
		{
			if(b[i][row])
			{
				System.out.print("1");
			}
			else
			{
				System.out.print("0");
			}
			
			for(int j = 0; j < spaces; j++)
			{
				System.out.print(" ");
			}
		}
	}
}
