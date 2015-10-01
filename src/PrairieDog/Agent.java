package PrairieDog;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Agent implements Runnable 
{
	private boolean life = true;
	private boolean running = false;

	private int _delayInterval;
	private Changes _chang, _auxChang;
	private OpenEnvironment _openEnv;
	private ClosedEnvironment _closedEnv;

	private int N;
	private Position[][] _map;

	private List<Point> _holes = new LinkedList<Point>();
	private List<Point> _tiles = new LinkedList<Point>();

	private List<Point> _destinations = new LinkedList<Point>();
	private Queue<Point> _toVisit = new LinkedList<Point>();
	private Deque<Point> _path = new LinkedList<Point>();

	public Agent(OpenEnvironment openListener, ClosedEnvironment closedListener, int size)
	{
		int row, column;
		Point point; 
		_chang = new Changes();
		_auxChang = new Changes();

		N = size;
		_map = new Position[size][size];

		for (row = 0; row < size; ++row)
			for (column = 0; column < size; ++column)
				_map[row][column] = Position.Unknown;
		point = _chang.getCurrentPoint();
		_map[point.getX()][point.getY()] = Position.Agent;

		_openEnv = openListener;
		_closedEnv = closedListener;
	}

	public void setDelayInterval(int delay)
	{
		_delayInterval = delay;
	}

	public Point getInitialPoint()
	{
		return _chang.getCurrentPoint();
	}

	private void AddPointToList(int row, int column, List<Point> list)
	{		
		for (Point point : list)
		{
			if (point.getX() == row && point.getY() == column)
				return;
		}
		list.add(new Point(row, column));
	}

	private void RemovePointFromList(int row, int column, List<Point> list)
	{		
		for (int index = 0; index < list.size(); ++index)
			if (list.get(index).getX() == row && list.get(index).getY() == column)
			{
				list.remove(index);
				return;
			}
	}

	private boolean Avoid(Set<Point> set, int x, int y)
	{
		if (set != null)
			for(Point point : set)
				if (point.getX() == x && point.getY() == y)
					return true;
		return false;
	}

	private boolean Free(Point point)
	{
		return Free(point.getX(), point.getY());
	}

	private boolean Free(int row, int column)
	{
		return _map[row][column] == Position.Free ||
				_map[row][column] == Position.FilledHole;
	}

	private List<Point> GetNeightbours(Point point, Set<Point> toExclude)
	{
		int row = point.getX(), column = point.getY();
		List<Point> neighbours = new LinkedList<Point>();
		--row;
		if (row >= 0 && Free(row,column) && !Avoid(toExclude, row, column))
			neighbours.add(new Point(row, column));
		++row; --column;
		if (column >= 0 && Free(row,column) && !Avoid(toExclude, row, column))
			neighbours.add(new Point(row, column));
		++row; ++column;
		if (row < N && Free(row,column) && !Avoid(toExclude, row, column))
			neighbours.add(new Point(row, column));
		--row; ++column;
		if (column < N && Free(row,column) && !Avoid(toExclude, row, column))
			neighbours.add(new Point(row, column));
		return neighbours;
	}

	private Point SymetricPoint(Point point, Point origin)
	{
		int row = 2 * origin.getX() - point.getX(),
				column = 2 * origin.getY() - point.getY();
		if (row >= 0 && row < N && column >= 0 && column < N)
			return new Point(row, column);
		else 
			return null;
	}

	private Deque<Point> CreatePushPath()
	{
		int index, minDistance, tile = 0, hole = 0;
		Point point = _chang.getCurrentPoint();
		minDistance = point.Manhatten(_tiles.get(tile));
		for (index = 1; index < _tiles.size(); ++index)
			if (point.Manhatten(_tiles.get(index)) < minDistance)
				tile = index;
		point = _tiles.get(tile);

		minDistance = point.Manhatten(_holes.get(hole));
		for (index = 1; index < _holes.size(); ++index)
			if (point.Manhatten(_holes.get(index)) < minDistance)
				hole = index;

		Point destination = new Point(_holes.get(hole));
		Point source = new Point(_tiles.get(tile));
		Point agent = new Point(_chang.getCurrentPoint());
		Point symetric;

		_map[destination.getX()][destination.getY()] = Position.Free;
		_map[source.getX()][source.getY()] = Position.Free;
		_map[agent.getX()][agent.getY()] = Position.Free;

		PriorityQueue<Point> options = new PriorityQueue<Point>(3, new PointComparator(destination));
		List<Point> neighbours = new LinkedList<Point>();
		Set<Point> notOptions = new HashSet<Point>();
		Deque<Point> path = new LinkedList<Point>();
		path.push(_tiles.get(tile));

		while (!path.isEmpty() && !destination.equals(path.peek()))
		{
			point = path.peek();
			neighbours = GetNeightbours(point, notOptions);
			_map[destination.getX()][destination.getY()] = Position.Hole;
			_map[point.getX()][point.getY()] = Position.Tile;
			for (Point p : neighbours)
			{
				symetric = SymetricPoint(p, point);
				if (symetric != null && Free(symetric))
					if (CreateMovePath(agent, symetric).size() > -1)
						options.add(p);
			}
			_map[point.getX()][point.getY()] = Position.Free;
			_map[destination.getX()][destination.getY()] = Position.Free;

			if (!options.isEmpty())
			{
				notOptions.add(point);
				path.push(options.remove());
				options.clear();
				agent.setXY(point);
			}
			else
			{
				notOptions.add(path.pop());
				if (path.size() == 1)
					agent.setXY(_chang.getCurrentPoint());
				else if (path.size() > 0)
				{
					point = path.pop();
					agent.setXY(path.peek());
					path.push(point);
				}
			}
		}
		_map[destination.getX()][destination.getY()] = Position.Hole;
		_map[source.getX()][source.getY()] = Position.Tile;
		_map[_chang.getCurrentPoint().getX()][_chang.getCurrentPoint().getY()] = Position.Agent;

		path.push(source);

		return path;
	}

	private Deque<Point> CreateMovePath(Point source, Point destination)
	{
		PriorityQueue<Point> options = new PriorityQueue<Point>(3, new PointComparator(destination));
		Set<Point> notOptions = new HashSet<Point>();
		Deque<Point> path = new LinkedList<Point>();
		path.push(source);
		while (!path.isEmpty() && !destination.equals(path.peek()))
		{
			options.addAll(GetNeightbours(path.peek(), notOptions));
			if (!options.isEmpty())
			{
				notOptions.add(path.peek());
				path.push(options.remove());
				options.clear();
			}
			else
				notOptions.add(path.pop());
		}
		return path;
	}

	private int AddPathToMove(Point destination)
	{
		Deque<Point> path = CreateMovePath(_chang.getCurrentPoint(), destination);
		if (path.isEmpty())
			return -1; 
		while(path.size() > 1)
			_path.push(path.pop());
		return path.size();
	}

	private void AddPathToPush()
	{
		Point point1, point2, tile, agent;
		Deque<Point> path = CreatePushPath(), aux = new LinkedList<Point>();

		if (path.size() < 3)
			return;

		agent = _chang.getCurrentPoint();
		tile = path.pop();

		point1 = path.pop();
		_path.push(path.pop());

		_map[agent.getX()][agent.getY()] = Position.Free;
		_map[tile.getX()][tile.getY()] = Position.Free;


		while (!path.isEmpty()) 
		{
			point2 = path.pop();

			if (point2.isOnLine(point1, _path.peek()))
			{
				point1 = _path.peek();
				_path.push(point2);
			}
			else
			{
				_map[_path.peek().getX()][_path.peek().getY()] = Position.Tile;
				aux = CreateMovePath(point2, SymetricPoint(point1, _path.peek()));
				point1 = _path.peek();
				while(aux.size() > 0)
					_path.push(aux.pop());
				_map[point1.getX()][point1.getY()] = Position.Free;
			}
		}

		_map[agent.getX()][agent.getY()] = Position.Agent;
		_map[tile.getX()][tile.getY()] = Position.Tile;

		aux = CreateMovePath(agent, SymetricPoint(point1, _path.peek()));
		while(aux.size() > 1)
			_path.push(aux.pop());
	}

	private void PerformAction()
	{		
		if (!_path.isEmpty())
		{
			Point next = _path.pop();
			Position pos = _map[next.getX()][next.getY()];
			if (pos == Position.Free || pos == Position.FilledHole || pos == Position.Tile)
			{
				_chang.setCurrentPoint(next);
				_openEnv.actionPerformed(new ActionEvent(_chang, 0, "real")); //Two auxiliar parameters
				Explore();
			}
			else
				_path.clear();
		}
	}

	public void Explore()
	{
		int row, column;
		Point point = _chang.getCurrentPoint();
		Position position;
		_toVisit.remove(point);
		_tiles.remove(point);
		for (int dx = -2; dx <= 2; ++dx)
			for (int dy = -2; dy <= 2; ++dy)
			{
				row = point.getX() + dx;
				column = point.getY() + dy;
				position = _openEnv.getPosition(row, column);
				if (position != Position.Unexistent)
				{
					if (_map[row][column] == Position.Unknown && 	//Agent didn't know about it
							position == Position.Free && 			//and it's free
							point.Manhatten(row,column) > 2)		//and it's far enought
						AddPointToList(row, column, _destinations);

					switch (position)
					{
					case Hole :
						AddPointToList(row, column, _holes);		//Add to hole list
						break;
					case Tile :
						AddPointToList(row, column, _tiles);		//Add to tile list
						break;
					case Free :										//Hole may be disapeared
					case FilledHole :								//Hole may be filled
						RemovePointFromList(row, column, _holes);   
						break;
					default :
						break;
					}

					//Update map
					_map[row][column] = position;

					//Update agent view
					_auxChang.setCurrentPoint(row, column);
					_auxChang.setPosition(position);
					_closedEnv.actionPerformed(new ActionEvent(_auxChang, 0, "img")); //Two auxiliar parameters
				}
			}
	}

	public void Stop()
	{
		running = false;
	}

	synchronized public void Run()
	{ 
		running = true;
		notify();
	}

	public void Finish()
	{ 
		life = false;
	}	

	@Override
	public void run() {
		while(life) 
		{ 
			try 
			{ 
				synchronized(this) 
				{ 
					if(!running) 
						wait() ;
				}
				Thread.sleep(_delayInterval);

				if (_path.isEmpty())
				{					
					if (!_holes.isEmpty() && !_tiles.isEmpty())
					{
						AddPathToPush();
					}


					if (_path.isEmpty())
					{
						if (_toVisit.isEmpty() && !_destinations.isEmpty())
						{
							_toVisit = new PriorityQueue<Point>(10, new PointComparator(_chang.getCurrentPoint()));
							_toVisit.addAll(_destinations);
							_destinations.clear();
						}

						while (!_toVisit.isEmpty() && AddPathToMove(_toVisit.remove()) <= 0)
						{} 	
					}
				}
				PerformAction();
			} 
			catch(InterruptedException e){} 
		}
	}

	@SuppressWarnings("unused")
	private static synchronized void playExplosion() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("resources/explosion.wav"));
					clip.open(inputStream);
					clip.start(); 
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
}