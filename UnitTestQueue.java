/*********************************************************************
*  Author: George Aziz
*  Date Created: 
*  Date Last Modified:  
*  Purpose: Test Harness for a queue, testing enqueing and dequeing
**********************************************************************/
/* This Test Harness has been taken from my DSA Practical 3 work */
/* Aziz, George. (2019). DSA LinkedList/Stack/Queue Prac P03. */

public class UnitTestQueue
{
    public static void main (String [] args)
    {
        DSAQueue queue = new DSAQueue();
        
        try 
        {
            System.out.println("================================\n"); 
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);
            queue.enqueue(40);
            queue.enqueue(50);
            System.out.println("Enqueue Passed!");
        } 
        catch (Exception e) { System.out.println(e.getMessage() + "Enqueue Error");}

        System.out.println("---------------------------------\n"); 

        try
        {
            queue.dequeue();
            queue.dequeue();
            queue.dequeue();
            queue.dequeue();
            queue.dequeue();
            System.out.println("Dequeue Passed!");
            System.out.println("================================"); 
        }
        catch (Exception e) { System.out.println(e.getMessage() + "Dequeue Error");}
    }
}
