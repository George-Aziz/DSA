/*****************************************************************************
*  Author: George Aziz
*  Date Created: 25/05/2019
*  Date Last Modified: 
*  Purpose: Handles Simulation Mode for SocialSim
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

	/************************************************************
	Default Constructor: 
	IMPORT: none
	EXPORT: none
	ASSERTION: 
    *************************************************************/
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

	/************************************************************
	SUBMODULE: mainSim
	IMPORT: none
	EXPORT: none
	ASSERTION: Method to run simulation mode
    *************************************************************/
	public void mainSim()
	{
		int option = -1;
		int timeStepCount = 0;
		
		fileMgr.readFile(networkFile, graph); //Reads the networkFile
		fileMgr.readFile(eventFile, graph); //Reads the event file

		do
		{
			option = UI.inputInteger("1: Time-step\n2: Exit","ERROR: That wasn't a choice. Please select one of the 2 options \n",1,2);
			if (option == 1) 
			{
				graph.timeStep(likeProb, followProb); //Runs time step
				timeStepCount++; //Increments count for log saving
				String output = "Time-step: " + timeStepCount; 
				UI.showMessage(output);
				DSAQueue queue = new DSAQueue();
				queue.enqueue(output); 
			 	saveLogs(queue); //saves logs 
			}
		}
		while(option != 2);
	}

	/****************************************************************************************
	SUBMODULE: saveLogs
	IMPORT: queue (DSAQueue)
	EXPORT: none
	ASSERTION: Log saving for simulation mode
	*****************************************************************************************/
	public void saveLogs(DSAQueue queue)
	{
		String fileName = new SimpleDateFormat("dd-HH-mm' Sim.log'").format(new Date()); //Log File file name format
		graph.displayStats(queue); //saves stats first
		graph.displayAsList(queue); //Saves Network's Adjacency list after stats to show the overall state
		fileMgr.saveNetwork(fileName, queue,true); //True since we want to append to file
	}
}
