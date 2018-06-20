import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
//import java.lang.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   
	private Node first, last;    // beginning of queue
    //private Node<Item> last;     // end of queue
    private int size;
	
 // helper linked list class
    private class Node 
    {
        private Item item;
        private Node next, prev;

        public Node(Item item) 
        {
            this.item = item;
        }
    }
	
    
    
    //initialise empty deque
    public Deque()                           // construct an empty deque
    {
        first = null;
        last = first;
        size = 0;
    }
    
   public boolean isEmpty()                 // is the deque empty?
   {
	   return size == 0;
   }
   
   public int size()                        // return the number of items on the deque
   {
	   return size;
   }
   
   public void addFirst(Item item)          // add the item to the front
   {
	   if (item == null) 
       {
           throw new IllegalArgumentException();
       }
       
       Node node = new Node(item);
       if (isEmpty())
       {
           last = node;
           first = node;
       } 
       else 
       {
           node.next = first;
           first.prev = node;
           first = node;
       }
       size++;   
   }
   
   public void addLast(Item item)           // add the item to the end
   {
	   if (item == null) 
       {
           throw new IllegalArgumentException();
       }
       
       Node node = new Node(item);
       if (isEmpty()) 
       {
           last = node;
           first = node;
       } 
       else 
       {
           last.next = node;
           node.prev = last;
           last = node;
       }
       size++; 
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
	   if (isEmpty())
       {
           throw new NoSuchElementException();
       }
       Item item = first.item;
        //if there are just one node in the Linkedlist
       if (size == 1) 
       {
           first = null;
           last = null;
       } 
       else 
       {
           first = first.next;
           first.prev = null;
       }
       size--;
       return item;
   }
   
   public Item removeLast()                 // remove and return the item from the end
   {
	   if (isEmpty())
       {
           throw new NoSuchElementException();
       }
       Item item = last.item;
       if (size == 1) 
       {
           first = null;
           last = null;
       } 
       else 
       {
           last = last.prev;
           last.next = null;
       }
       size--;
       return item;
   }
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
	   return new ItemIterator();
   }
   
   private class ItemIterator implements Iterator<Item> 
   {

       private Node current = first;

       public boolean hasNext() 
       {
           return current != null;
       }

       public Item next() 
       {
           // check whether the current is null
           if (current == null)
           {
               throw new NoSuchElementException();
           }

           Item item = current.item;
           current = current.next;
           return item;
       }

       
       // doesn't implement remove()
       public void remove() 
       {
           throw new UnsupportedOperationException();
       }
   }

   
   public static void main(String[] args)   // unit testing (optional)
   {
	   
   }
}

