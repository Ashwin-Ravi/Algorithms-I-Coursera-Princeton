import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    
    private ArrayList<LineSegment> lineSegmentList = new ArrayList<>();
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       
       if (points == null) 
           throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) 
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        
        Point[] pointsDup = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(pointsDup);
            Arrays.sort(pointsDup, p.slopeOrder());

            int min = 0;
            while (min < pointsDup.length && p.slopeTo(pointsDup[min]) == Double.NEGATIVE_INFINITY) min++;
            if (min != 1) throw new IllegalArgumentException();// check duplicate points
            int max = min;
            while (min < pointsDup.length) {
                while (max < pointsDup.length && p.slopeTo(pointsDup[max]) == p.slopeTo(pointsDup[min])) max++;
                if (max - min >= 3) {
                    Point pMin = pointsDup[min].compareTo(p) < 0 ? pointsDup[min] : p;
                    Point pMax = pointsDup[max - 1].compareTo(p) > 0 ? pointsDup[max - 1] : p;
                    if (p == pMin)
                        lineSegmentList.add(new LineSegment(pMin, pMax));
                }
                min = max;
            }
        }
        
   }
   
   public int numberOfSegments()        // the number of line segments
   {
       return lineSegmentList.size();
   }
   
   public LineSegment[] segments()                // the line segments
   {
       LineSegment[] segments = new LineSegment[lineSegmentList.size()];
       return lineSegmentList.toArray(segments);
   }
}