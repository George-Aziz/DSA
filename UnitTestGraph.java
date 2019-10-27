/*****************************************
*  Author: George Aziz
*  Date Created: 
*  Date Last Modified:  
*  Purpose: Test Harness for DSAGraph
******************************************/
/* This Test Harness has been taken from my DSA Practical 5 work and adjusted with new methods and new names to test */
/* Aziz, George. (2019). DSA Graphs Prac P05. */

import java.util.*;
import java.io.*;


public class UnitTestGraph
{
    public static void main (String [] args)
    {
        DSAGraph graph = new DSAGraph();

        try 
        {
            System.out.println("Adding Users test--------------------");
            System.out.println("Labels being added - UserNames: A B C D E");
            graph.addUser("A");
            graph.addUser("B");
            graph.addUser("C");
            graph.addUser("D");
            graph.addUser("E");
            System.out.println("Adding Users Test Passed!\n");
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Adding User Error!");}

        try 
        {
            System.out.println("Adding Follows--------------------");
            System.out.println("Follows: A-B, B-C, C-A, D-A E-A");

            graph.addFollow("A", "B");
            graph.addFollow("B", "C");
            graph.addFollow("C", "A");
            graph.addFollow("D", "A");
            graph.addFollow("E", "A");
            System.out.println("Adding Follows Test Passed!\n");
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Adding Follows Error!");}

        try 
        {
            System.out.println("Adding Posts--------------------");

            graph.addPost("A", "Hello World!", 2);
            graph.addPost("B", "Hello I am Person B!", 0.5);
            graph.addPost("C", "Testing posts", 1);
            graph.addPost("D", "Greeting! I am person D", 1);
            graph.addPost("E", "Test!", 1);
            System.out.println("Adding Posts Test Passed!\n");
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Adding Posts Error!");}
        try
        {
            System.out.println("Time-step test--------------------");
            System.out.println("With Like and Follow Probability of 1");
            graph.timeStep(1.0 , 1.0);
            System.out.println("Time Step Test Passed!\n");
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Time-Step Error!");}
        try
        {
            System.out.println("\nAdjacency Display Test---------------------");
            DSAQueue listQueue = new DSAQueue();
            graph.displayAsList(listQueue);
            Iterator listQiter = listQueue.iterator();
            while (listQiter.hasNext())
            {
                System.out.print(listQiter.next());
            }
            System.out.println("\nAdjacency List Passed!");

            System.out.println("\nStatistics Display Test---------------------");
            DSAQueue statsQueue = new DSAQueue();
            graph.displayStats(statsQueue);
            Iterator statsQiter = statsQueue.iterator();
            while (statsQiter.hasNext())
            {
                System.out.print(statsQiter.next());
            }
            System.out.println("\nStats Display Passed!");
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Display Error!");}

        try
        {
            System.out.println("\nFind User Test---------------------");
            System.out.println("Finding Person C");
            DSAQueue fQueue = new DSAQueue();
            graph.displayUserInfo("C", fQueue);
            Iterator fQiter = fQueue.iterator();
            while (fQiter.hasNext())
            {
                System.out.print(fQiter.next());
            }
            System.out.println("\nFind User Passed!");
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Display Error!");}
    }
}