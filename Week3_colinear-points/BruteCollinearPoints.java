
import java.util.Arrays;
import java.util.ArrayList;


public class BruteCollinearPoints {
	
	//private int size; 
	private ArrayList <LineSegment> lineSeg;
	
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
	    lineSeg = new ArrayList<>(); //array list size unknowm
	    
		  
	   
	   if(points == null )   //argument is null
	   {
		   throw new IllegalArgumentException();
	   }
	   
	  
	 
	   
	   //any point in the array is null
	   for (int i = 0; i < points.length ; i++)
	   {
	   if (points[i] == null)
	   {
		  throw new IllegalArgumentException();
	   }
	   }
	 
	   
	   Point[] pointsC = Arrays.copyOf(points, points.length);
	   Arrays.sort(pointsC);  //sort array
	   int n = pointsC.length; 
	   
	 //the argument to the constructor contains a repeated point.
	   for (int i = 0; i < n - 1; i++)
	   {
	   if (pointsC[i].compareTo(pointsC[i+1]) == 0)
	   {
		  throw new IllegalArgumentException();
	   }
	   }
	

	   
		 for (int i = 0; i < n-3; i++)
			 for (int j = i+1; j < n-2; j++)
				 for (int k = j+1; k < n-1; k++)
					 for (int l = k+1; l < n; l++) 
					if (isColinear(pointsC[i], pointsC[j], pointsC[k], pointsC[l]))	 
					{
						LineSegment line = new LineSegment(pointsC[i], pointsC[l]);
						lineSeg.add(line);
					}
		 
   }
   
   private boolean isColinear(Point a, Point b, Point c, Point d)
   {
	   if (a.slopeTo(b) != a.slopeTo(c) || a.slopeTo(c) != a.slopeTo(d))
	   {
	   return false;
	   }
	   else 
	   {
		   return true;
	   }
   }
   
   public           int numberOfSegments()        // the number of line segments
   {   
	   return lineSeg.size();
   }
   public LineSegment[] segments()                // the line segments
   { 
	  LineSegment[] segments = new LineSegment[lineSeg.size()];
	   for (int i = 0; i < lineSeg.size(); i++)
	   {  
		   segments[i] = lineSeg.get(i);
	   }
	   return segments;
   }
}