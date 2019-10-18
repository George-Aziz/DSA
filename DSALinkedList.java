import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable , Serializable
{
    //Classfield
    private DSAListNode head;
    private DSAListNode tail;
    
    DSALinkedList()
    {
        head = null;
        tail = null;
    }

    //Mutator
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

    public boolean isEmpty()
    {
	    return head == null;
    }

    //Accessor
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

    public Iterator iterator() //Return a new Iterator of internal type DSALinkedListIterator
    {
        return new DSALinkedListIterator(this); //Hooks the iterator to this DSALinkedList object
    }

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
        public void remove()
        {
            throw new UnsupportedOperationException("Not Supported");
        }
    }
    
    private class DSAListNode implements Serializable
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