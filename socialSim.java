/*****************************************************************************
*  Author: George Aziz
*  Date Created: 13/10/2019
*  Date Last Modified: 
*  Purpose: Main for the program that will either go interactive or sim mode 
******************************************************************************/

import java.util.*;

public class SocialSim
{
	public static void main (String [] args)
	{
		//Makes sure to inform the user of the right usage of the program
		if (args.length == 1 && args[0].equals("-i")) 
		{
			UserInterface UI = new UserInterface();	
			UI.mainMenu(); //Calls the main menu for UserInterface which is also the menu for interactive mode
		}
		else if (args.length == 5 && args[0].equals("-s"))
		{
			String networkFile = args[1];
			String eventFile = args[2];
			double likeProb = Double.parseDouble(args[3]);
			double followProb = Double.parseDouble(args[4]);

			SimulationMode sim = new SimulationMode(networkFile, eventFile, likeProb, followProb);
			sim.mainSim(); //Calls the main sim function
		}
		else if (args.length == 0 || !(args[0].equals("-i")) || !(args[0].equals("-s"))) 
		{
			System.out.println("Usage for interactive mode: ./SocialSim -i");
			System.out.println("Usage for simulation mode: ./SocialSim -s <netFile> <eventFile> <prob_like> <prob_follow>");
		}
		
	}
}
