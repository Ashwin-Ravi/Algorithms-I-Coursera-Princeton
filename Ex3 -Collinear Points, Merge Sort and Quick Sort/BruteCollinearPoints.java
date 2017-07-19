import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints 
{
    private LineSegment[] lineSegmentArr;
    private ArrayList<LineSegment> foundSegments = new ArrayList<>();
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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
        Arrays.sort(pointsDup);

        for (int p = 0; p < pointsDup.length - 3; p++) {
            for (int q = p + 1; q < pointsDup.length - 2; q++) {
                for (int r = q + 1; r < pointsDup.length - 1; r++) {
                    for (int s = r + 1; s < pointsDup.length; s++) {
                        if (pointsDup[p].slopeTo(pointsDup[q]) == pointsDup[p].slopeTo(pointsDup[r]) &&
                                pointsDup[p].slopeTo(pointsDup[q]) == pointsDup[p].slopeTo(pointsDup[s])) {
                            foundSegments.add(new LineSegment(pointsDup[p], pointsDup[s]));
                        }
                    }
                }
            }
        }

        lineSegmentArr = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
   
   public int numberOfSegments()        // the number of line segments
   {
       return lineSegmentArr.length;
   }
   
   public LineSegment[] segments()                // the line segments
   {
       return Arrays.copyOf(lineSegmentArr, numberOfSegments());
   }
   
}