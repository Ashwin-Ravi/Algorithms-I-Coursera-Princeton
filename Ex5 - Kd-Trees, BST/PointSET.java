import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.List;

public class PointSET {
   private TreeSet<Point2D> set;
   
   public PointSET()                               // construct an empty set of points 
   {
       set = new TreeSet<Point2D>();
   }
   
   public boolean isEmpty()                      // is the set empty? 
   {
       return set.isEmpty();
   }
   
   public int size()                         // number of points in the set 
   {
       return set.size();
   }
   
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       if (p == null) 
           throw new java.lang.IllegalArgumentException();
       set.add(p);
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       if (p == null) 
           throw new java.lang.IllegalArgumentException();
       return set.contains(p);
   }
   
   public void draw()                         // draw all points to standard draw 
   {
       for(Point2D point : set) 
       {
           point.draw();
       }
   }
   
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
   {
       // all points in the set that are inside the rectangle
       List<Point2D> pointList = new LinkedList<Point2D>();
       for(Point2D point : set)
       {
           if(rect.contains(point)) 
               pointList.add(point);
       }
       return pointList;
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       if(set.isEmpty() || set.size() == 0) 
           return null;
       Point2D near = null;
       double dist = Double.POSITIVE_INFINITY;
       for(Point2D point : set) 
       {
           double pointdis = point.distanceTo(point);
           if(pointdis < dist) 
           {
               dist = pointdis; 
               near = point;
           }
  }
  return near;
   }
   

//   public static void main(String[] args)                  // unit testing of the methods (optional) 
}