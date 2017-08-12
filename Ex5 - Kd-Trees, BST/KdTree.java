import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
    private Node root;
    private RectHV board;
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    private Point2D nearestPoint;
    private double minDistance;
    
    private static class Node 
    {
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean divideBy; // divide by horizontal/vertical
        private int size;       // size of node
        private Point2D point;
        
        public Node(Point2D point, boolean divideBy) 
        {
            this.point = point;
            this.divideBy = divideBy;
            size = 1;
            lb = null;
            rt = null;
        }
    }
    
    
    
    public KdTree() 
    {
        // construct an empty set of points
        root = null;
    }
    
    public boolean isEmpty() 
    {
        // is the set empty?
        return root == null;
    }
    
    public int size() 
    {
        // number of points in the set
        return size(root);
    }
    
    private int size(Node x) 
    {
        if (x == null) return 0;
        return x.size;
    }
    
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        root = insert(root, p, VERTICAL);
    }
    
    private Node insert(Node x, Point2D p, boolean divideBy) {
        if (x == null) return new Node(p, divideBy);
        if (p.compareTo(x.point) == 0) return x;
        
        double cmp;
        if (divideBy == VERTICAL)
            cmp = p.x() - x.point.x();
        else
            cmp = p.y() - x.point.y();
        
        if (cmp < 0)
            x.lb = insert(x.lb, p, !divideBy);
        else
            x.rt = insert(x.rt, p, !divideBy);
        
        x.size = 1 + size(x.lb) + size(x.rt);
        return x;
    }
    
    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new NullPointerException();
        Node x = contains(root, p);
        return x != null;
    }
    
    private Node contains(Node x, Point2D p) {
        if (x == null) return null;
        
        if (p.compareTo(x.point) == 0) return x;
        
        double cmp;
        if (x.divideBy == VERTICAL)
            cmp = p.x() - x.point.x();
        else
            cmp = p.y() - x.point.y();
        
        if (cmp < 0)
            return contains(x.lb, p);
        else
            return contains(x.rt, p);
    }
    
    public void draw() {
        // draw all points to standard draw
        draw(root);
    }
    
    private void draw(Node x) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        x.point.draw();
        if (x.divideBy == VERTICAL) {
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.line(x.point.x(), 0, x.point.x(), 1);
        } else {
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.line(0, x.point.y(), 1, x.point.y());
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        
        ArrayList<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }
    
    private void range(Node x, RectHV rect, ArrayList<Point2D> points) {
        if (x == null) return;
        if (rect.contains(x.point))
            points.add(x.point);
        
        if (x.divideBy == VERTICAL) {
            if (rect.xmax() < x.point.x())
                range(x.lb, rect, points);
            else if (rect.xmin() >= x.point.x())
                range(x.rt, rect, points);
            else {
                range(x.lb, rect, points);
                range(x.rt, rect, points);
            }
        } else {
            if (rect.ymax() < x.point.y())
                range(x.lb, rect, points);
            else if (rect.ymin() >= x.point.y())
                range(x.rt, rect, points);
            else {
                range(x.lb, rect, points);
                range(x.rt, rect, points);
            }
        }
    }
    
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        
        nearestPoint = null;
        minDistance = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return nearestPoint;
    }
    
    private void nearest(Node x, Point2D p) {
        if (x == null) return;
        
        double distance = p.distanceTo(x.point);
        if (distance < minDistance) {
            minDistance = distance;
            nearestPoint = x.point;
        }
        
        if (x.divideBy == VERTICAL) {
            if (p.x() < x.point.x()) {
                nearest(x.lb, p);
                if (minDistance >= x.point.x() - p.x())
                    nearest(x.rt, p);
            } else {
                nearest(x.rt, p);
                if (minDistance >= p.x() - x.point.x())
                    nearest(x.lb, p);
            }
        } else {
            if (p.y() < x.point.y()) {
                nearest(x.lb, p);
                if (minDistance >= x.point.y() - p.y())
                    nearest(x.rt, p);
            } else {
                nearest(x.rt, p);
                if (minDistance >= p.y() - x.point.y())
                    nearest(x.lb, p);
            }
        }
    }
    
    //public static void main(String[] args) {
    // unit testing of the methods (optional)
}

