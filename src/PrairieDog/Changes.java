package PrairieDog;

public class Changes {
	private Point _previousPoint;
	private Point _currentPoint;
	private Position _position;

	public Changes()
	{
		_previousPoint = new Point(0, 0);
		_currentPoint = new Point(0, 0);
		_position = Position.Free;
	}
	
	public Point getCurrentPoint()
	{
		return _currentPoint;
	}
	
	public void setCurrentPoint(int x, int y)
	{
		_currentPoint.setXY(x, y);
	}
	
	public void setCurrentPoint(Point point)
	{
		setCurrentPoint(point.getX(), point.getY());
	}
	
	public Point getPreviousPoint()
	{
		return _previousPoint;
	}
	
	public Position getPosition()
	{
		return _position;
	}
	
	public void setPosition(Position position)
	{
		_position = position;
		_previousPoint.setXY(_currentPoint.getX(), _currentPoint.getY());
	}
}
