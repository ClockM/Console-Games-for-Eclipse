import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * @author Will Brown (AKA ClockM)
 * 
 * This public class is a collection of useful methods for making console games
 * 
 * List of methods:
 * makeSpace()
 * promptInt()
 * swapDim()
 */

public class ConGameUtil
{
	// Creates a break between messages and an additional n line breaks
	public static void makeSpace(int n)
	{
		System.out.print("----------------------------");
		for(int i=0;i<=n;i++)
		{
			System.out.println();
		}
	}
		
	// Prints the string s and prompts recursively for an integer value
	public static int promptInt(String s)
	{
		Scanner userInput = null;
		try
		{
			System.out.println(s);
			userInput = new Scanner(System.in);
			int n = userInput.nextInt();
			return n;
		}
		catch(InputMismatchException e)
		{
			makeSpace(0);
			System.out.println("*Please input an integer*");
			return promptInt(s);
		}
	}
	
	// Prints the string s and prompts recursively for an integer value
	// Also checks to make sure the integer is between the min. and the max. values specified
	public static int promptInt(String s, int min, int max)
	{
		Scanner userInput = null;
		try
		{
			System.out.println(s);
			userInput = new Scanner(System.in);
			int n = userInput.nextInt();
			if(n<min || n>max)
			{
				throw new InputMismatchException();
			}
			return n;
		}
		catch(InputMismatchException e)
		{
			makeSpace(0);
			System.out.println("*Please input an integer between " + min + " and " + max + "*");
			return promptInt(s, min, max);
		}
	}
		
	//Returns a 2D boolean array such that b[i][j] becomes b[j][i] for all values of i and j
	public static boolean[][] SwapDim(boolean[][] b)
	{
		boolean[][] bInv = new boolean[b[0].length][b.length];
		
		for(int j=b[0].length-1;j>=0;j--)
		{
			for(int i=0;i<b.length;i++)
			{
				bInv[j][i] = b[i][j];
			}
		}
		return bInv;
	}
}
