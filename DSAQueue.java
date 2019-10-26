/*****************************************************************************
*  Author: George Aziz
*  Date Created: 
*  Date Last Modified: 26/10/2019 
*  Purpose: A Class responsible for Queues ADT
******************************************************************************/

import java.util.*;
import java.io.*;

public class DSAQueue implements Iterable, Serializable
{
    //Class fields
    private DSALinkedList list;

    /****************************************
    * Default Constrcutor: DSAQueue
    * IMPORT: none
    * EXPORT: address of new DSAQueue Object
    ****************************************/
    public DSAQueue()
    {
	    list = new DSALinkedList();    
    }

    /******************************************************************
    * SUBMODULE: isEmpty()
    * IMPORTS: none
    * EXPORTS: true/false (Boolean)
    * ASSERTION: Checks if the queue is empty
    * *****************************************************************/
    public boolean isEmpty()
    {
	    return (list.isEmpty());
    }
       
    /******************************************************************
    * SUBMODULE: enqueue()
    * IMPORTS: value (Object)
    * EXPORTS: none
    * ASSERTION: Adds a new value into the queue at the end always
    * *****************************************************************/
    public void enqueue(Object value)
    {
        list.insertLast(value);
    }

    /******************************************************************
    * SUBMODULE: dequeue()
    * IMPORTS: none
    * EXPORTS: frontVal (Object)
    * ASSERTION: Removes the head node of queue/ linked list
    * *****************************************************************/
    public Object dequeue()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("Queue is Empty");
        }

        Object frontVal;
        frontVal = list.peekFirst();
        list.removeFirst();
        return frontVal;
    }

    /******************************************************************
    * SUBMODULE: iterator()
    * IMPORTS: none
    * EXPORTS: listIterator (Iterator)
    * ASSERTION: To be able to iterate over a queue
    * *****************************************************************/
    public Iterator iterator()
    {
        return list.iterator();
    }
}
