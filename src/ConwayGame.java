
/** Things to add/do:
 * 	Improve clarity of comments
 * 	A system for easy input for making initial boards
 * 	A system for incrementing the board manually or automatically
 * 	A system for saving and loading boards
 *  A system for hexagonal boards**/

public class ConwayGame {
	
	public double verNum = 0.11;
	
	public void runGame()
	{
		System.out.println("Welcome to GoL v. " + verNum + "!");
		
		boolean[][] b = null;
		int menuOption = ConGameUtil.promptInt("\n1. New game\n2. Load game (test)", 1, 2);
		System.out.println(menuOption);
		switch(menuOption)
		{
			case 1: b = newGame();
			break;
			case 2: b = loadGame();
			break;
		}
		
		//Prints the first few steps on the board
		printBoard(b);
		for(int i=0;i<=5;i++)
		{
			b = bNext(b);
			printBoard(b);
		}
		
	}
	
	private boolean[][] newGame()
	{
		// Prompts the length and height of the board
		int bLength = ConGameUtil.promptInt("Length of the board? (In cells)", 1, 10);
		int bHeight = ConGameUtil.promptInt("Height of the board? (In cells)", 1, 10);
				
				//The "board"- True is alive, false is dead
				boolean[][] b = new boolean[bLength][bHeight];
				return b;
	}
	
	private boolean[][] loadGame()
	{
		boolean[][] b = {{false, false, true, false, false},
		 		 		{false, false, true, false, false},
		 		 		{false, false, true, false, false},
		 		 		{false, false, true, false, false},
		 		 		{false, false, true, false, false},};
		b = ConGameUtil.SwapDim(b);
		
		return b;
	}
	
	// bNext returns the next "step" of the board.
	private boolean[][] bNext(boolean[][] b)
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
	
	// Prints the board passed to it. 1 is alive and 0 is dead
	private void printBoard(boolean[][] b)
	{
		ConGameUtil.makeSpace(4);
		
		/* NOTE! The order of j and i are swapped around to accommodate for
		 * printing in console, such that each cell is printed left-right, up-down.
		 * j is still Y and i is still X though... */
		for(int j=b[0].length-1;j>=0;j--)
		{
			for(int i=0;i<b.length;i++)
			{
				if(b[i][j])
				{
					System.out.print("1");
				}
				else
				{
					System.out.print("0");
				}
				
				if(i==b.length-1)
				{
					System.out.println();
				}
				else
				{
					System.out.print(" ");
				}
			}
		}
	}

}
