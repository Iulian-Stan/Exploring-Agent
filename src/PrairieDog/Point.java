package PrairieDog;

public class Point {
	private int _x, _y;

	public Point(int x, int y)
	{
		_x = x;
		_y = y;
	}

	public Point(Point point)
	{
		_x = point._x;
		_y = point._y;
	}

	public boolean isNeighbour(Point point)
	{
		return point._x == _x && (point._y - 1 == _y || point._y + 1 == _y) ||
				point._y == _y && (point._x - 1 == _x || point._x + 1 == _x);
	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

	public void setXY(int x, int y)
	{
		_x = x;
		_y = y;
	}

	public void setXY(Point point)
	{
		_x = point._x;
		_y = point._y;
	}

	public int Manhatten(int x, int y)
	{
		return Math.abs(_x - x) + Math.abs(_y - y);
	}

	public int Manhatten(Point dest)
	{
		return Manhatten(dest._x, dest._y);
	}

	public boolean isOnLine(Point point1, Point point2)
	{
		return _x == point1._x && _x == point2._x ||
				_y == point1._y && _y == point2._y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point point = (Point) obj;
		return _x == point._x && _y == point._y;
	}

}
