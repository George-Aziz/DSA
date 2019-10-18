import java.util.*;
public class DSAStack implements Iterable //To support for-each loop
{
    //Class Fields
    private DSALinkedList list;

    public DSAStack()
    {
        list = new DSALinkedList();
    }

    //MUTATORS
    public void push (Object value)
    {
        list.insertLast(value);
    }

    public Object pop ()
    {
        Object topVal = top();
        list.removeLast();
        return topVal;
    }

    public Object top()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("Stack is Empty");
        }
        Object topVal = list .peekLast();
        return topVal;
    }

    public boolean isEmpty()
    {
        return (list.isEmpty());
    }

    public Iterator iterator()
    {
        return list.iterator(); //Expose list's iterator
    } 
}