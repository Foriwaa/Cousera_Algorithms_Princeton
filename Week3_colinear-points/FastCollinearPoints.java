import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	
	private ArrayList <LineSegment> lineSeg;
	
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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
	
	   
	   for(int i = 0; i < n; i++)
	   {
		   Arrays.sort(pointsC);  //sort array
		 Point origin = pointsC[i];  
	  //sort array by slope order
	   Arrays.sort(pointsC, origin.slopeOrder());
	   int count = 1;
	   int j = 0;
	   boolean naturalOrder = true;
	   while(j < n-1 )
	   {
		 if (origin.slopeTo(pointsC[j]) == origin.slopeTo(pointsC[j+1]))
	   //p,r,s,t , p->r == p->s
		 {
			count++; 
			if (!(origin.compareTo(pointsC[j]) < 0 && origin.compareTo(pointsC[j+1]) < 0))
				//not sorted in natural order
				{naturalOrder = false;}
		 }
			else
			{
				if (count >= 3 && naturalOrder )
				{   //
					LineSegment line = new LineSegment(origin, pointsC[j]);
					lineSeg.add(line);
				}
				count = 1; //new segment added so set count to 0
				naturalOrder = true;
			} 
			j++; //increment j for while loop
			
			//if pointsC[j] is last point
			if ((j == n-1) && count >= 3 && naturalOrder)
			{
				LineSegment line = new LineSegment(origin, pointsC[j]);
				lineSeg.add(line);
				count = 1;
				naturalOrder = true;
			}
		 }
	   }
	   
	   }
	   
 	   
	   
   public           int numberOfSegments()        // the number of line segments
   {
	   return lineSeg.size();
   }
   public LineSegment[] segments()                // the line segments
   {
	   LineSegment[] segments = new LineSegment[lineSeg.size()];
	   return lineSeg.toArray(segments);
   }
}