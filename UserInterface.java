/*****************************************************************************
*  Author: George Aziz
*  Date Created: 04/05/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Handles all user input / output for this program
******************************************************************************/

import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class UserInterface
{	
	//Class Fields 
	private DSAGraph graph;
    private FileManager fileMgr;
	
	/************************************************************
	Default Constructor: 
	IMPORT: none
	EXPORT: none
	ASSERTION: 
    *************************************************************/
	public UserInterface()
	{
		graph = new DSAGraph();
        fileMgr = new FileManager();
	}	

	/************************************************************
	SUBMODULE: mainMenu
	IMPORT: none
	EXPORT: none
	ASSERTION: Main menu that's the first output the user sees and is able to go into other menus 
    *************************************************************/
	public void mainMenu()
	{
		double likeProb = -1;
		double followProb = -1; //Variables for probabilities to be used in time-step
        int option = 0; //Option by default is invalid so the menu keeps looping
		do
		{
			//First output prompt to the user to access all functionalities of the program
			System.out.println("\n\n---===Main Menu===---");           
			System.out.println("1: Load network file");                                    
			System.out.println("2: Load event file");                                    
			System.out.println("3: Set probabilities");
			System.out.println("4: Insert new user");
			System.out.println("5: Find a user");
			System.out.println("6: Remove a user");
			System.out.println("7: Add a follow");                                     
			System.out.println("8: Remove Follow");                                  
			System.out.println("9: Add new post");                                                    
			System.out.println("10: Display network");
			System.out.println("11: Display statistics");
			System.out.println("12: Update(run a time-step)");
			System.out.println("13: Save network");
			System.out.println("14: Exit \n");
			
			option = inputInteger("Please input the number next to the option:","ERROR: That wasn't a choice. Please select one of the 14 options \n \n",1,14); 
			
			switch(option)
			{
				case 1:
					loadFile(graph, fileMgr); 
				break;
				case 2: //Reading in the event file 
					loadFile(graph, fileMgr); 
				break;
				case 3:
					followProb = inputDouble("Please input the probability of following (0-1):","ERROR: Please select a number between 0 and 1 (Can be decimals)", 0, 1);
					likeProb = inputDouble("Please input the probability of liking (0-1):","ERROR: Please select a number between 0 and 1 (Can be decimals)", 0, 1);
				break;	
				case 4:
					insertNewUser(graph);
					break;
				case 5:
					findUser(graph);
					break;
				case 6:
					removeUser(graph);
					break;
				case 7:
					addFollow(graph);
					break;
				case 8:
					removeFollow(graph);
					break;
				case 9:
					insertPostManual(graph);
					break;
				case 10:
					graph.displayAsList();
					break;
				case 11:
					//Statistics
					break;
				case 12: //Run update/time-step
					graph.timeStep(likeProb, followProb);
					break;
				case 13:
					//Save to file
				case 14:
					System.out.println("You have selected to exit. Good Bye"); //If the user wants to exit the program
					break;
			}

		} 
		while (option != 14); //Validation to ensure that this menu keeps being outputted unless the options in the selected range has been chosen 
	}

	/************************************
	SUBMODULE: getFileName
	IMPORT: none 
	EXPORT: fileName (String)
	ASSERTION: Retrieves the file name
	************************************/
	public String getFileName()
	{
		Scanner sc = new Scanner(System.in);
		String fileName;
		System.out.println("\nPlease enter the file name with its extension (.csv): ");

		fileName = sc.nextLine(); //User inputs the file name

		return fileName;
	}
	
	/**************************************************************************
	SUBMODULE: loadFile (loads the file)
	IMPORT: graph (DSAGraph), filemgr (FileManager)
	EXPORT: none
	ASSERTION: Menu for when the user picks to load networks/events from a file
	***************************************************************************/
	public void loadFile(DSAGraph graph, FileManager fileMgr) 
	{
		String fileName;
		int count = 0; //Count is 0 since no line has been processed yet
		
		//Initial input to see if the file exists
		fileName = getFileName();
		count = fileMgr.readFile(fileName, graph);

		//If the file inputted by the user is invalid, the while loop will start with the error message first and then another input
		while (count == 0)		
		{
			System.out.println("Invalid file!\n");
			fileName = getFileName();

			count = fileMgr.readFile(fileName, graph); //The readfile method will return a count value tha represents the amount of valid lines processed
		}
	}
	
	/********************************************************************************
	SUBMODULE: saveShips
	IMPORT: graph (DSAGraph), fileMgr (FileManager)
	EXPORT: none
	ASSERTION: When the user wants to save ships into a file
	*********************************************************************************/
	/* public void saveNetwork(DSAGraph graph, FileManager fileMgr)
	{
		System.out.println("\nNOTE: If you enter a file that already exists, it will overwrite the file!");
		String fileName = getFileName();

		fileMgr.writeFile(fileName, graph);
	} */
	
	/*****************************************************************************************
	SUBMODULE: insertPostManual
	IMPORT: graph (DSAGraph)
	EXPORT: none
	ASSERTION: Inputs a post for a specefied user
	******************************************************************************************/
	public void insertPostManual(DSAGraph graph)
	{	
		String user, post;
		double clickBait;
		
		user = inputUser("\nPlease input the name of the user that wants to add a post:", "ERROR: User not found!"); 
		post = inputPost("\nPlease input what the user will post:", "ERROR: Cannot have empty post!");
		clickBait = inputClickBait("Please input the click bait factor (Above 0 Only!):","ERROR: Please select a number above 0 (Can be decimals)", 0);
		graph.addPost(user, post, clickBait);
	}


	/*****************************************************************************************
	SUBMODULE: insertNewUser
	IMPORT: graph (DSAGraph)
	EXPORT: none
	ASSERTION: Inputs a new user into social network
	******************************************************************************************/
	public void insertNewUser(DSAGraph graph)
	{
		String user;
		user = inputNewUser("\nPlease input the name of the new user:", "ERROR: Can't have empty name for user"); 

		graph.addUser(user);
	}


	/*****************************************************************************************
	SUBMODULE: addFollow
	IMPORT: graph (DSAGraph)
	EXPORT: none
	ASSERTION: Makes the specefied user follow another person
	******************************************************************************************/
	public void addFollow(DSAGraph graph)
	{
		String main, follow;
		main = inputUser("\nPlease input the name of the user that wants to follow:", "ERROR: Can't have empty name for user"); 
		follow = inputUser("\nPlease input the name of the uer that " + main + " wants to follow:", "ERROR: Can't have empty name for user"); 

		graph.addFollow(main, follow); //Follower follows "following"
	}


	/*****************************************************************************************
	SUBMODULE: findUser
	IMPORT: graph (DSAGraph)
	EXPORT: none
	ASSERTION: Finds a user and displays all their information
	******************************************************************************************/
	public void findUser(DSAGraph graph)
	{
		String user;
		user = inputUser("\nPlease input the name of the user you want to find:", "ERROR: User not found!");

		graph.displayUserInfo(user);
	}


	/*****************************************************************************************
	SUBMODULE: removeUser
	IMPORT: graph (DSAGraph)
	EXPORT: none
	ASSERTION: Finds the user specefied and then removes them from the network
	******************************************************************************************/
	public void removeUser(DSAGraph graph)
	{
		String userName;
		userName = inputUser("\nPlease input the name of the user you want to remove:", "ERROR: User not found!");

		graph.removeUser(userName);
	}


	/*****************************************************************************************
	SUBMODULE: removeFollow
	IMPORT: graph (DSAGraph)
	EXPORT: none
	ASSERTION: Finds the user, and then finds the user in their follow list and removes it
	******************************************************************************************/
	public void removeFollow(DSAGraph graph)
	{
		String userName, userName1;
		userName = inputUser("\nPlease input the name of the user you want to remove follow from:", "ERROR: User not found!");
		userName1 = inputUser("\nPlease input the name of the user you want to remove:", "ERROR: User not found!");

		graph.removeFollow(userName, userName1);
	}
	
	/*************************
	SUBMODULE: showMessage
	IMPORT: message (String)
	EXPORT: none
	***************************/
	public static void showMessage(String message)
	{
		System.out.println(message);
	}

/*                                 Methods for inputting with validation                                */
/* VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV */
	/********************************************************************************
	SUBMODULE: inputInteger
	IMPORT: prompt (String), error (String), min (Integer), max (Integer)
	EXPORT: integerInput (Integer)
	ASSERTION: User input submodule to input an integer that is within the specified range
	*********************************************************************************/
	public int inputInteger(String prompt, String error, int min, int max)
	{
		Scanner sc = new Scanner(System.in);
		String outputString;
		int integerInput = min - 1; //By default the input is always invalid to ensure loop keeps looping even if nothing is inputted
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called       

		do
		{
			try
			{
				System.out.println(outputString); //Outputs the prompt for the user
				outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
				integerInput = sc.nextInt(); //User inputs an integer
			}
			catch (InputMismatchException e)
			{
				sc.nextLine(); //advances scanner to the next line
			}
		}
		while ((integerInput < min || integerInput > max)); //Validation boundaries for integers that get imported
		return integerInput;
	}

	/********************************************************************************
	SUBMODULE: inputDouble
	IMPORT: prompt (String), error (String), min (Real), max (Real)
	EXPORT: integerInput (Integer)
	ASSERTION: User input submodule to input a real that is within the specified range
	*********************************************************************************/
	public double inputDouble(String prompt, String error, double min, double max)
	{
		Scanner sc = new Scanner(System.in);
		String outputString;
		double doubleInput = min - 1.0; //By default the input is always invalid to ensure loop keeps looping even if nothing is inputted
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called       
		
		do
		{
			try
			{
				System.out.println(outputString); //Outputs the prompt for the user
				outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
				doubleInput = sc.nextDouble(); //User inputs a double
			}
			catch (InputMismatchException e)
			{
				sc.nextLine(); //advances scanner to the next line
			}
		}
		while ((doubleInput < min) || (doubleInput > max)); //Validation boundaries for double that get imported
		return doubleInput;
	}
	
	/********************************************************************************
	SUBMODULE: inputUser
	IMPORT: prompt (String), error (String)
	EXPORT: inputUserName (String)
	ASSERTION: User input for user names input
	*********************************************************************************/
	public String inputUser(String prompt, String error)
	{
		Scanner sc = new Scanner(System.in);
		String outputString, inputUserName;
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called 
		inputUserName = null;
		
		do
		{
			System.out.println(outputString); //Outputs the prompt for the user
			outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
			inputUserName = sc.nextLine(); //User inputs a string
			
		}
		while ((inputUserName == null) || (inputUserName.isEmpty()) || graph.getUser(inputUserName) == null);
		return inputUserName;
	}

	/********************************************************************************
	SUBMODULE: inputUser
	IMPORT: prompt (String), error (String)
	EXPORT: inputUserName (String)
	ASSERTION: User input for user names input
	*********************************************************************************/
	public String inputNewUser(String prompt, String error)
	{
		Scanner sc = new Scanner(System.in);
		String outputString, inputUserName;
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called 
		inputUserName = null;
		
		do
		{
			System.out.println(outputString); //Outputs the prompt for the user
			outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
			inputUserName = sc.nextLine(); //User inputs a string
			
		}
		while ((inputUserName == null) || (inputUserName.isEmpty()));
		return inputUserName;
	}


	/********************************************************************************
	SUBMODULE: inputPost
	IMPORT: prompt (String), error (String)
	EXPORT: inputPost (String)
	ASSERTION: User input for posts input
	*********************************************************************************/
	public String inputPost(String prompt, String error)
	{
		Scanner sc = new Scanner(System.in);
		String outputString, inputPost;
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called 
		inputPost = null;
		
		do
		{
			System.out.println(outputString); //Outputs the prompt for the user
			outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
			inputPost = sc.nextLine(); //User inputs a string
			
		}
		while ((inputPost == null) || (inputPost.isEmpty()));
		return inputPost;
	}

	/********************************************************************************
	SUBMODULE: inputClickBait
	IMPORT: prompt (String), error (String), min (Real), max (Real)
	EXPORT: doubleInput (Real)
	ASSERTION: User input submodule to input a click bait factor above 0 
	*********************************************************************************/
	public double inputClickBait(String prompt, String error, double min) //No max needed for click bait
	{
		Scanner sc = new Scanner(System.in);
		String outputString;
		double doubleInput = min - 1.0; //By default the input is always invalid to ensure loop keeps looping even if nothing is inputted
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called       
		
		do
		{
			try
			{
				System.out.println(outputString); //Outputs the prompt for the user
				outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
				doubleInput = sc.nextDouble(); //User inputs a double
			}
			catch (InputMismatchException e)
			{
				sc.nextLine(); //advances scanner to the next line
			}
		}
		while (doubleInput < min); //Validation boundaries for double that get imported
		return doubleInput;
	}
}
