package PrairieDog;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {
	private Point _destination;
	
	public PointComparator(Point destination)
	{
		super();
		_destination = destination;
	}
	
	@Override
	public int compare(Point o1, Point o2) 
	{
		return _destination.Manhatten(o1) - _destination.Manhatten(o2);
	}

}
