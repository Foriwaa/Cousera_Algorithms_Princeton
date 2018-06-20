import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    /*private static final double xMin = 0.0;
     private static final double yMin = 0.0;
     private static final double xMax = 1.0;
     private static final double yMax = 1.0;*/
    
    private Node root;
    private int size;
    
    private static class Node {
        private Point2D p;
        //the point
        private RectHV rect;
        //the axis-aligned rectangle corresponding to this node
        private Node lb;
        //the left/bottom subtree
        private Node rt;
        //the right/top subtree
        private boolean isVert;
        //node orientation
        //private int m;
        
        public  Node(Point2D p, RectHV rect, boolean isVert) {
            this.rect = rect;
            this.p = p;
            this.isVert = isVert;
            lb = null;
            rt = null;
        }
        
        
        /** get the rectangle associated to point p given parent node n
         * @param p the point to be inserted
         */
        public RectHV getRect(Point2D point) {
            //point = child, this = parent
            if (isVert)  {
                if (Point2D.X_ORDER.compare(point, this.p) < 0) {
                    //child's xMax = parent parent point x
                    return new RectHV(this.rect.xmin(), this.rect.ymin(),
                                      this.p.x(), this.rect.ymax());
                }
                else {
                    //childs xMin = parent point x
                    return new RectHV(this.p.x(), this.rect.ymin(),
                                      this.rect.xmax(), this.rect.ymax());
                }
            }
            else { //is horizontal
                if (Point2D.Y_ORDER.compare(point, this.p) < 0) {
                    //child goes below
                    return new RectHV(this.rect.xmin(), this.rect.ymin(),
                                      this.rect.xmax(), this.p.y());
                }
                else {
                    //child goes above
                    return new RectHV(this.rect.xmin(), this.p.y(),
                                      this.rect.xmax(), this.rect.ymax());
                }
            }
        }
    }
    
    
    
    // construct an empty set of points
    public         KdTree()     {
        root = null;
        size = 0;
    }
    // is the set empty?
    public           boolean isEmpty()   {
        return size == 0;
    }
    // number of points in the set
    public               int size()   {
        return size;
    }
    // add the point to the set (if it is not already in the set)
    public              void insert(Point2D p)  {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        root = insert(root, p, null);
    }
    
    
    /** helper method for insert
     * @param n the node at which a new point will be inserted
     * @param p the point to be inserted
     * @param use parent to determine orientation
     * @return the node after insertion
     */
    private Node insert(Node n, Point2D pn, Node parent) {
        //a new node
        if (n == null) {
            size++;
            if (parent == null) {
                //it is the root , set to vertical
                // size++;
                return new Node(pn, new RectHV(0.0, 0.0, 1.0, 1.0), true);
            }
            
            else {
                //orientation opposite of parent
                return new Node(pn, parent.getRect(pn), !parent.isVert);
            }
        }
        
        //if the point is already on tree
        if (n.p.equals(pn)) {
            return n;
        }
        
        if (n.isVert) {
            if (Point2D.X_ORDER.compare(pn, n.p) < 0) {
                //insert left node
                n.lb = insert(n.lb, pn, n);
            }
            else {
                n.rt = insert(n.rt, pn, n);
            }
        }
        else {
            if (Point2D.Y_ORDER.compare(pn, n.p) < 0) {
                //insert left node
                n.lb = insert(n.lb, pn, n);
            }
            else {
                n.rt = insert(n.rt, pn, n);
            }
        }
        
        return n;
    }
    
    
    
    // does the set contain point p?
    public           boolean contains(Point2D p)  {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p);
    }
    
    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        boolean cmp;
        if (x.isVert) {
            cmp = p.x() < x.p.x();
        } else {
            cmp = p.y() < x.p.y();
        }
        if (cmp) {
            return contains(x.lb, p);
        } else {
            return contains(x.rt, p);
        }
    }
    
    // draw all points to standard draw
    public              void draw()     {
        StdDraw.clear();
        
        drawLine(root, true);
    }
    
    private void drawLine(Node x, boolean orient) {
        if (x != null) {
            drawLine(x.lb, !orient);
            
            StdDraw.setPenRadius();
            if (orient) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }
            
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();
            
            drawLine(x.rt, !orient);
        }
    }
    
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> points = new Queue<Point2D>();
       // Queue<Node> queue = new Queue<Node>();
       // queue.enqueue(root);
        range(root, rect, points);
        return points;
    }
    
    /** helper method for range
     * @param n the root
     * @param rect the query rectangle
     * @param mySet the set of Point2D in the query rectangel
     */
    private void range(Node x, RectHV rec, Queue<Point2D> points){
        if (x == null) {
                return;
            }
        if (!x.rect.intersects(rec)){
            return;
        }
        if (rec.contains(x.p)){
            points.enqueue(x.p);
        }
        
        range(x.lb, rec, points);
        range(x.rt, rec, points);
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    /** a nearest neighbor in the set to point p; return
     * null if the set is empty
     * @param p point
     * @return the nearest point to p in the set. Return null if
     * the set is empty
     * @throws NullPointerException if p is null
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        if (isEmpty()) {
            return null;
        }
        
        return nearest(root, p, root.p);
    }
    
    /** helper method of nearest
     * @param n the starting node of the kdTree
     * @param p the query point
     * @param closestP the nearest neighbor of the query point
     */
    private Point2D nearest(Node n, Point2D p, Point2D closestP) {
        if (n == null) {
            return closestP;
        }
        
        if (n.rect.distanceTo(p) >= p.distanceTo(closestP)) {
            return closestP;
        }
        
        if ((n.p).distanceTo(p) < p.distanceTo(closestP)) {
            closestP = n.p;
        }
        
        //System.out.println("current closest point is: " + closestP.toString());
        
        Point2D subClosestP;
        if (n.lb != null && isSameSide(p, n.lb, n)) {
            subClosestP = nearest(n.lb, p, closestP);
            closestP = nearest(n.rt, p, subClosestP);
        }
        else {
            subClosestP = nearest(n.rt, p, closestP);
            closestP = nearest(n.lb, p, subClosestP);
        }
        
        return closestP;
        
    }
    
    /** test whether the child node and the query point is on the same
     * side of the parent node
     * @param p the query point
     * @param n the current node
     * @param parent the parent node
     */
    private boolean isSameSide(Point2D p, Node n, Node parent) {
        if (parent.isVert) {
            return Point2D.X_ORDER.compare(p, parent.p)
            == Point2D.X_ORDER.compare(n.p, parent.p);
        }
        else {
            return Point2D.Y_ORDER.compare(p, parent.p)
            == Point2D.Y_ORDER.compare(n.p, parent.p);
        }
    }

    
    // unit testing of the methods (optional)
    public static void main(String[] args)   {
        /*KdTree k = new KdTree();
        // k.insert(new Point2D(0,0));
        // k.insert(new Point2D(0,0));
        // k.insert(new Point2D(0,0));
        // k.insert(new Point2D(0,0));
        // k.insert(new Point2D(0,0.1));
        for (Point2D p : k.range(new RectHV(0.0, 0.0, 0.6, 0.6))) {
            System.out.println(p);
        }
        System.out.println(k.size());*/
    }
}
