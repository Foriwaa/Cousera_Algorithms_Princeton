import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;



/**
 * Compilation javac PointSET.java
 * Execution PointSET a a a
 * Dependencies: Point2D.java StdDraw.java StdRandom.java
 * Brute-force implementation. A mutable data type that represents a set of
 * points in the unit square. Implemented by using a red-black BST of
 * {@code edu.princeton.cs.algs4.SET}.
 *
 * @author Fori
 */


public class PointSET {
    private SET<Point2D> set2D;
    // construct an empty set of points
    public         PointSET()     {
        set2D = new SET<Point2D>();
    }
    // is the set empty?
    public           boolean isEmpty()   {
        
        return set2D.isEmpty();
    }
    // number of points in the set
    public               int size()   {
        return set2D.size();
    }
    // add the point to the set (if it is not already in the set)
    public              void insert(Point2D p)  {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (!set2D.contains(p)) {
            set2D.add(p);
        }
        
    }
    // does the set contain point p?
    public           boolean contains(Point2D p)  {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        return set2D.contains(p);
    }
    // draw all points to standard draw
    public  void draw()     {
        for (Point2D p : set2D) {
            p.draw();
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        
        Stack<Point2D>  stack2D = new Stack<Point2D>();
        for (Point2D p : set2D) {
            if (rect.contains(p)) {
                stack2D.push(p);
            }
        }
        return stack2D;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p)    {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        if (isEmpty()) return null;
        
        double dist;
        double minDist = Double.MAX_VALUE;
        Point2D near = null;
        
        for (Point2D q : set2D) {
            dist = p.distanceSquaredTo(q);
            
            if (dist < minDist) {
                minDist = dist;
                near = q;
            }
        }
        return near;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args)   {
        int N = Integer.parseInt(args[0]);
        
        StdDraw.setCanvasSize();
        
        StdDraw.setXscale();
        StdDraw.setYscale();
        
        PointSET mySet = new PointSET();
        
        for (int i = 1; i <= N; i++) {
            double x = ((double) StdRandom.uniform(100))/100.0;
            double y = ((double) StdRandom.uniform(100))/100.0;
            
            Point2D point = new Point2D(x, y);
            mySet.insert(point);
        }
        //Draw all the points
        StdDraw.setPenRadius(0.02);
        mySet.draw();
        
        //draw the reference point
        Point2D p = new Point2D(0.5, 0.5);
        StdDraw.setPenColor(StdDraw.RED);
        p.draw();
        
        //draw the nearest neighbor
        System.out.println("The size of the set is " + mySet.size());
        System.out.println("The p point is " + p.toString());
        System.out.println("The closest point to p is " +
                           mySet.nearest(p).toString());
        StdDraw.setPenColor(StdDraw.PINK);
        mySet.nearest(p).draw();
        
        //draw point within a range
        RectHV myRect = new RectHV(0.1, 0.1, 0.4, 0.4);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.005);
        StdDraw.rectangle(0.25, 0.25, 0.15, 0.15);
        
        StdDraw.setPenRadius(0.02);
        Iterable<Point2D> rangeSet = mySet.range(myRect);
        for (Point2D point : rangeSet) {
            point.draw();
            System.out.println("Point inside range is " + point.toString());
        }
        
        
    }
}
