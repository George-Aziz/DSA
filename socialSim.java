/*****************************************************************************
*  Author: George Aziz
*  Date Created: 13/10/2019
*  Date Last Modified: 
*  Purpose: Calls the UI which leads to the program 
******************************************************************************/

import java.util.*;

public class socialSim
{
	public static void main (String [] args)
	{
		
		UserInterface UI = new UserInterface();
	
	    UI.mainMenu(); //Call the main menu which connects all the methods in userInterface that would call methods from other classes
		
	}
}
