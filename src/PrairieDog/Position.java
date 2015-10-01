package PrairieDog;
public enum Position {
	Free(0), 
	Obstacle(1), 
	Hole(2), 
	Tile(3), 
	FilledHole(4), 
	Agent(5),
	Unknown(6),
	Unexistent(7);

	private final int _index; 

	private Position (int index)
	{
		_index = index;
	}

	public int getIndex()
	{
		return _index;
	}
};