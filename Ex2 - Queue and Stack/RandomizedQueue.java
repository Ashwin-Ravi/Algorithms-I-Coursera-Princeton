import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private int size;
    private Item[] randomQueue;
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       size = 0;
       randomQueue = (Item[]) new Object[1];
   }
   
   public boolean isEmpty()                 // is the queue empty?
   {
       return (size == 0);
   }
   
   public int size()                        // return the number of items on the queue
   {
       return size;
   }
   
   public void enqueue(Item item)           // add the item
   {
       if (item == null) 
       {
           throw new IllegalArgumentException();
       }
       randomQueue[size] = item;
       size++;
       if (size == randomQueue.length)
       {
           Item[] newQueue = (Item[]) new Object[size*2];
           for (int i = 0; i < size; i++)
           {
               newQueue[i] = randomQueue[i];
           }
           randomQueue = newQueue;
       }
   }
   
   public Item dequeue()                    // remove and return a random item
   {
       if (size == 0) 
       {
           throw new java.util.NoSuchElementException();
       }
       int randomNumber = StdRandom.uniform(size);
       Item returnItem = randomQueue[randomNumber];
       randomQueue[randomNumber] = randomQueue[size-1];
       randomQueue[size-1] = null;
       size--;
       if (randomQueue.length > 4 && size <= randomQueue.length / 4)
       {
           Item[] newQueue = (Item[]) new Object[randomQueue.length/2];
           for(int i = 0; i < size; i++)
           {
               newQueue[i] = randomQueue[i];
           }
           randomQueue = newQueue;
       }
       return returnItem;
   }
   
   public Item sample()                     // return (but do not remove) a random item
   {
        if (size == 0) 
       {
           throw new java.util.NoSuchElementException();
       }
        int randomNumber = StdRandom.uniform(size);
        return randomQueue[randomNumber];
   }
   
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedQueueIterator();
   }
   private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int i = size;
        final private int[] order;
        
        public RandomizedQueueIterator()
        {
            order = new int[i];
            for (int j = 0; j < i; ++j) 
            {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }
        
        public boolean hasNext() 
        { 
            return i > 0; 
        }
        public void remove() 
        { 
            throw new java.lang.UnsupportedOperationException(); 
        }
        public Item next() 
        { 
            if (!hasNext()) 
            {
                throw new java.util.NoSuchElementException();
            }
            return randomQueue[order[--i]]; 
        }
    }
   
}