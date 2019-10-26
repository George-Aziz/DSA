/*****************************************************************************
*  Author: George Aziz
*  Date Created: 
*  Date Last Modified: 26/10/2019 
*  Purpose: A Class responsible for Linked List
******************************************************************************/
/* This File has been taken from my DSA Practical 3 work and added a RemoveNode() function */
/* Aziz, George. (2019). DSA LinkedList/Stack/Queue Prac P03. */

import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable
{
    //Private Classfield
    private DSAListNode head;
    private DSAListNode tail;
    
    DSALinkedList()
    {
        head = null;
        tail = null;
    }

    /******************************************************************
    * SUBMODULE: insertFirst()
    * IMPORTS: newValue (Object)
    * EXPORTS: none
    * ASSERTION: adds a new node at the head position of the linked list
    * *****************************************************************/
    public void insertFirst(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);

        if (isEmpty())
        {
            tail = newNd;
        }
        else
        {
            head.setPrev(newNd);        
        }
            newNd.setNext(head); //Moves the existing head to the right one spot to allow for the new head
            head = newNd; //New Node becomes the new head
    }

    /******************************************************************
    * SUBMODULE: insertLast()
    * IMPORTS: newValue (Object)
    * EXPORTS: none
    * ASSERTION: adds a new node at the tail position of the linked list
    * *****************************************************************/
    public void insertLast(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);

        if (isEmpty())
        {
            head = newNd;
        }
        else
        {
            tail.setNext(newNd); //New node goes to the right of the tail
            newNd.setPrev(tail);                      
        }
            tail = newNd; //New Node that has entered becomes the new tail
    }

    /******************************************************************
    * SUBMODULE: isEmpty()
    * IMPORTS: none
    * EXPORTS: true/false (Boolean)
    * ASSERTION: Checks if linkedlist is empty
    * *****************************************************************/
    public boolean isEmpty()
    {
	    return head == null;
    }

    /******************************************************************
    * SUBMODULE: peekFirst()
    * IMPORTS: none
    * EXPORTS: nodeValue (Object)
    * ASSERTION: Checks the head of the linkedlist
    * *****************************************************************/
    public Object peekFirst()
    {
        Object nodeValue;
        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else
        {
            nodeValue = head.getValue();
        }
        return nodeValue;
    }

    /******************************************************************
    * SUBMODULE: peekFirst()
    * IMPORTS: none
    * EXPORTS: nodeValue (Object)
    * ASSERTION: Checks the tail of the linkedlist
    * *****************************************************************/
    public Object peekLast()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else
        {
            nodeValue = tail.getValue();
            //System.out.println(nodeValue);
        }
        return nodeValue;
    }

    /******************************************************************
    * SUBMODULE: removeFirst()
    * IMPORTS: none
    * EXPORTS: nodeValue (Object)
    * ASSERTION: Removes the head node of the linked list
    * *****************************************************************/
    public Object removeFirst()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else if (head == tail)
        {
            nodeValue = head.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = head.getValue();
            head.getNext().setPrev(null);
            head = head.getNext();
        }
        return nodeValue;
    }

    /******************************************************************
    * SUBMODULE: removeLast()
    * IMPORTS: none
    * EXPORTS: nodeValue (Object)
    * ASSERTION: Removes the tail node of the linked list
    * *****************************************************************/
    public Object removeLast()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is Empty");
        }
        else if (head == tail)
        {
            nodeValue = tail.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = tail.getValue();
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
        }
        return nodeValue;
    }

    /******************************************************************
    * SUBMODULE: removeNode()
    * IMPORTS: value (Object)
    * EXPORTS: none
    * ASSERTION: Removes a speceifc node no matter where it is
    * *****************************************************************/
    public void removeNode(Object value)
    {
        if(head != null)
        {
            if(head.getValue() == value)
            {
                removeFirst(); // if the value is the head then the first node is removed
            }
            else if(tail.getValue() == value)
            {
                removeLast(); // if the value is at the tail, the last node is removed
            }
            else
            { 
                DSAListNode node, nextNode;
    
                node = head;
                if (node == null)
                {
                    throw new IllegalArgumentException("Node not found!");
                }
                else 
                {
                    while(node != null) 
                    {
                        nextNode = node.getNext();

                        if (node.getValue() == value)
                        {
                            node.getNext().setPrev(node.getPrev()); // Makes the deleted node cut off from the references
                            node.getPrev().setNext(node.getNext());
                        }
                        node = nextNode;
                    }
                }
            }  
        }
    }

    public Iterator iterator() //Return a new Iterator of internal type DSALinkedListIterator
    {
        return new DSALinkedListIterator(this); //Hooks the iterator to this DSALinkedList object
    }
    
    /*********************************************************
    * PRIVATE INNER CLASS: DSALinkedListIterator
    * Class that helps iterate over the whole Linked List
    * *******************************************************/
    private class DSALinkedListIterator implements Iterator
    {
        private DSAListNode iterNext;
        public DSALinkedListIterator(DSALinkedList theList)
        {
            iterNext = theList.head;
        }

        public boolean hasNext() 
        {
            return (iterNext != null);
        }
        public Object next() 
        {
            Object value;
            if (iterNext == null)
            {
                value = null;
            }
            else 
            {
                value = iterNext.getValue(); //Get the value in the node
                iterNext = iterNext.getNext(); //Ready for subsequent calls to next()
            }
            return value;
        }

        public void remove(Object value)
        {
            throw new UnsupportedOperationException("Not Supported");
        }
    }
    
    /*****************************************************************************************************
    * PRIVATE INNER CLASS: DSAListNode
    * Class that contains all data for a list node including its value, the next and prev references
    * ***************************************************************************************************/
    private class DSAListNode 
    {
        //Class Fields
        public Object value;
        public DSAListNode next;
        public DSAListNode prev;

        DSAListNode(Object inValue)
        {
            value = inValue;
            next = null;
            prev = null;
        }

        //ACCESSOR
        public Object getValue()
        {
            return value;
        }

        public DSAListNode getNext()
        {
            return next;
        }

        public DSAListNode getPrev()
        {
            return prev;
        }
        //MUTATORS
        public void setValue(Object inValue)
        {
            value = inValue;
        }

        public void setNext (DSAListNode newNext)
        {
            next = newNext;
        }

        public void setPrev (DSAListNode newPrev)
        {
            prev = newPrev;
        }
    }
}