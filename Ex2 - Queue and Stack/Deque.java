import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> 
{
    private Node first, last;
    private int size;
    
    private class Node
    {
        private Item item;
        private Node next;
        private Node previous;
    }
    
   public Deque()                           // construct an empty deque
   {
       first = null;
       last = null;
       size = 0;
   }
   
   public boolean isEmpty()                 // is the deque empty?
   {
       return (size == 0);
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
       Node newNode = new Node();
       newNode.item = item;
       newNode.next = first;
       newNode.previous = null;
       if (size == 0)
       {
           first = newNode;
           last = first;
       }
       else
       {
           first.previous = newNode;
           first = newNode;
       }
       size++;
   }
   
   public void addLast(Item item)           // add the item to the end
   {
       if (item == null)
       {
           throw new IllegalArgumentException();
       }
       Node newNode = new Node();
       newNode.item = item;
       newNode.next = null;
       newNode.previous = last;
       if (size == 0)
       {
           last = newNode;
           first = last;
       }
       else
       {
           last.next = newNode;
           last = newNode;
       }
       size++;
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
       if (isEmpty()) 
       {
           throw new java.util.NoSuchElementException();
       }
       Item returnItem = first.item;
       first = first.next;
       if (size == 1)
       {
           last = null;
       }
       else
       {
           first.previous = null;
       }
       size--;
       return returnItem;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if (isEmpty()) 
       {
           throw new java.util.NoSuchElementException();
       }
       Item returnItem = last.item;
       last = last.previous;
       if (size == 1)
       {
           first = null;
       }
       else
       {
           last.next = null;
       }
       size--;
       return returnItem;
   }
   private class MyIterator implements Iterator<Item>         
   {
       private Node current = first;
       
       public boolean hasNext() 
       { 
           return (current != null); 
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
           
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
   
 public Iterator<Item> iterator()            // return an iterator over items in order from front to end
 {
     return new MyIterator();
 }
   
 
}