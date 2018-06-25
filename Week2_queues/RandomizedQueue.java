import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	 private Item[] items; // array of items
	 private int size;  // number of elements
	 
   public RandomizedQueue()                 // construct an empty randomized queue
   {
	   items = (Item[]) new Object[1];
       size = 0;
   }
   
   public boolean isEmpty()                 // is the randomized queue empty?
   {
	   return size == 0;
   }
   
   public int size()                        // return the number of items on the randomized queue
   {
	   return size;
   }
   
   private void resize(int N) {
       Item[] temp = (Item[]) new Object[N];
       for (int i = 0; i < size; i++) {
           temp[i] = items[i];
       }
       items = temp;
   }
   
   public void enqueue(Item item)           // add the item
   {
	   if (item == null) {
           throw new IllegalArgumentException();
       }
       
       
       if (size == items.length) {
           resize(2*size);
       }
       items[size] = item;
       size++; 
   }
   
   public Item dequeue()                    // remove and return a random item
   {
	   if (isEmpty()) {
           throw new NoSuchElementException();
       }
       //get random i
       int i = (int) (StdRandom.uniform()*size);
       Item item = items[i];
       items[i] = items[--size];
       items[size] = null;
       if (size <= items.length/4) {
           resize(items.length/2);
       }
       return item;  
   }
   
   public Item sample()                     // return a random item (but do not remove it)
   {
	   if (isEmpty()) {
           throw new NoSuchElementException();
       }
       int i = (int) (StdRandom.uniform()*size);
       return items[i]; 
   }
   
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedQueueIterator();
   }
   
   private class RandomizedQueueIterator implements Iterator<Item> {
       private int N = 0;
       private Item[] temp;

       public RandomizedQueueIterator() {
           temp = (Item[]) new Object[size];

           for (int i = 0; i < size; i++) {
               temp[i] = items[i];
           }
       }

       public boolean hasNext() { 
           return N < size; 
       }

       public Item next() {
           if (!hasNext()) { 
               throw new NoSuchElementException(); 
           }
           int j = (int) (StdRandom.uniform()*(size-N));
           Item item = temp[j];
           temp[j] = temp[size-(++N)];
           temp[size-N] = null;
           return item;
       }

       public void remove() { 
           throw new UnsupportedOperationException();
       }
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
	   
   }
}
