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

    public boolean isEmpty()
    {
	    return (list.isEmpty());
    }
       
    public void enqueue(Object value)
    {
        list.insertLast(value);
    }

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

    public Iterator iterator()
    {
        return list.iterator();
    }
}
