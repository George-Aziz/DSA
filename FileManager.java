/*****************************************************************************
*  Author: George Aziz
*  Date Created: 13/10/2019
*  Date Last Modified: 
*  Purpose: Managing any file reading/writing including processing every line 
*           of a loaded file
******************************************************************************/
// This class is retrieved from: My OOPD Assignment from last semester 

import java.util.*;
import java.io.*;

public class FileManager
{
 
	/************************************************************
	SUBMODULE: readFile
	IMPORT: fileName (String), graph (DSAGraph)
	EXPORT: none
	ASSERTION: To be able to read the file inputted by the user
	*************************************************************/
	public int readFile(String fileName, DSAGraph graph)
	{	
			FileInputStream fileStrm = null;
			InputStreamReader inRdr;
			BufferedReader bufRdr = null;
			String line;

			int count = 0;

		try
		{
			fileStrm = new FileInputStream(fileName);
			inRdr = new InputStreamReader(fileStrm);
			bufRdr = new BufferedReader(inRdr);

			//Use while loop to make sure the file isn't empty
			line = bufRdr.readLine();
			while (line != null)
			{
				try
				{
					processLine(line, graph); //The line will be processed

					count++; //The count only increases if a line has been processed properly
				}
				catch (IllegalArgumentException e)
				{
					UserInterface.showMessage(e.getMessage() + " " + line); //Any error will be shown to the user
				}
				line = bufRdr.readLine(); //read next line and repeat the process
			}

			fileStrm.close(); //Once all lines are read, the file must be closed
		}
		catch (IOException e) 
		{
			try
			{
				if (fileStrm != null)
				{
					fileStrm.close();
				}
			}
			catch (IOException ex)
			{
				//File won't close, nothing that can be done
			}
		}

		return count; //Count is the amount of lines processed properly
	}
	
	
	/************************************************************************************
	SUBMODULE: processLine
	IMPORT: line (String), graph (DSAGraph)
	EXPORT: none
	ASSERTION: To be able to process a line from a file that should represent a network
    ************************************************************************************/
	public void processLine(String line, DSAGraph graph)
	{
		String [] lineArray = line.split(":"); //After every section a ':' will be used to seperate them
		
		if (lineArray.length == 1) // If only one element on a line means name input
		{
			graph.addUser(lineArray[0]);//Value could be anything, I just do 1 as a placeholder value
		}
		
		if (lineArray.length == 2) // If two elements on a line means a person following another person
		{
			graph.addFollow(lineArray[0], lineArray[1]); //Makes the person follow the other person to the right of the :
		}

		if (lineArray.length == 3)
		{
			if (lineArray[0].equals("F")) //Means that a follow event will be added
			{
				graph.addFollow(lineArray[1], lineArray[2]); 
			}

			if (lineArray[0].equals("P")) //Means that a post event will be added
			{
				graph.addPost(lineArray[1], lineArray[2]);		
			}
		}
	}

	/*****************************************************************
	SUBMODULE: writeFile
	IMPORT: fileName (String), graph (DSAGraph)
	EXPORT: none
	ASSERTION: To be able to write the report of the sim to a file
    *******************************************************************/
	/* public void writeFile(String fileName, DSAGraph graph)
	{

		String message;
		
		FileOutputStream fileStrm = null;
   		PrintWriter pw;

		try 
		{
			fileStrm = new FileOutputStream(fileName); 
			pw = new PrintWriter(fileStrm);
			
			for (int fileIndex = 0; fileIndex < sStore.getShipCount(); fileIndex++) 
			{
				pw.println(shipArray[fileIndex].toFileString()); //Goes through each index of the array and adds the string created from toFileString method
			}
			pw.close(); //Writer must be closed
			message = "Ships succesfully saved!";
			UserInterface.showMessage(message);
		}
		catch (IOException e)
		{
			if (fileStrm != null) 
			{
         		try 
				{ 
					fileStrm.close(); 
				} 
				catch (IOException ex2) { } // We can’t do anything more! 
      		}
			
			message = "Error in writing to file:" + e.getMessage(); //If an error occurs whilst saving to File, a message will appear to the user
			UserInterface.showMessage(message);
		}
		
	}*/
} 
