/*****************************************************************************
*  Author: George Aziz
*  Date Created: 04/05/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Handles all user input / output for this program
******************************************************************************/

import java.util.*;
import java.io.*;
import java.text.*;

public class SimulationMode
{	
	//Class Fields 
	private DSAGraph graph;
    private FileManager fileMgr;
	private UserInterface UI;
	//Class fields for network
	private String networkFile, eventFile;
	private double likeProb, followProb;

	
	public SimulationMode(String inNet, String inEvent, double inLikeProb, double inFollowProb)
	{
		graph = new DSAGraph();
        fileMgr = new FileManager();
		UI = new UserInterface();

		networkFile = inNet;
		eventFile = inEvent;
		likeProb = inLikeProb;
		followProb = inFollowProb;
	}	

	public void mainSim()
	{
		int option = -1;
		int timeStepCount = 0;
		
		fileMgr.readFile(networkFile, graph);			
		fileMgr.readFile(eventFile, graph);

		do
		{
			option = UI.inputInteger("1: Time-step\n2: Exit","ERROR: That wasn't a choice. Please select one of the 2 options \n",1,2);
			if (option == 1) 
			{
				graph.timeStep(likeProb, followProb);
				timeStepCount++;
				String output = "Time-step: " + timeStepCount; 
				UI.showMessage(output);
				DSAQueue queue = new DSAQueue();
				queue.enqueue(output);
			 	saveLogs(queue);
			}
		}
		while(option != 2);
	}

	/****************************************************************************************
	SUBMODULE: saveLogs
	IMPORT: 
	EXPORT: none
	ASSERTION: When the user wants to save the network to a file in its current state in .txt
	*****************************************************************************************/
	public void saveLogs(DSAQueue queue)
	{
		System.out.println("\nNOTE: If you enter a file that already exists, it will overwrite the file!");
		String fileName = new SimpleDateFormat("dd-HH-mm':Sim.log'").format(new Date());;
		graph.displayAsList(queue);
		fileMgr.saveNetwork(fileName, queue,true); //True since we want to append to file
	}
}
