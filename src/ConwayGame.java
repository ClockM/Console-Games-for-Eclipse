import java.util.Scanner;

public class ConwayGame {
	int bX;
	int bY;
	double verNum = 0.01;
	public void runGame()
	{
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to GoL v. " + verNum + "!");
		//The line below is for convenience in testing
		this.bX = 5;
		this.bY = 5;
		/*
		System.out.println("Length of board? (In cells)");
		this.bX = userInput.nextInt();
		System.out.println("Height of board? (In cells)");
		this.bY = userInput.nextInt();
		*/
		
		
		boolean[][] bInit = new boolean[bX][bY];
		for(int i=0;i<bX;i++){for(int j=0;j<bY;j++){bInit[i][j]=false;}}
		bInit[0][2]=true;
		
		printBoard(bInit);
		
	}
	
	public void printBoard(boolean[][] b)
	{
		System.out.print("----------------------------");
		makeSpace(5);
		/*NOTE! The roles of j and i are swapped around to accommodate for
		 * printing in console. j is still Y and i is still X though... */
		for(int j=bY-1;j>=0;j--){for(int i=0;i<bX;i++){
			if(b[i][j]){System.out.print("1");}else{System.out.print("0");}
			if(i==bX-1){System.out.println();}else{System.out.print(" ");}
		}}
	}
	
	public void makeSpace(int n) //It's a shame I can't "clear" the console
	{for(int i=0;i<n;i++){System.out.println();}}

}
