package PrairieDog;

import java.util.Observable;

public class Sizes extends Observable {
	public int flag = 0;

	private int _size;
	private int _holesNumber;
	private int _obstaclesNumber;
	private int _delayInterval;
	private int _randomizeInterval;

	public Sizes(int size, int holesNumber, int obstaclesNumber, int delayInterval, int randomizeInterval)
	{
		super();
		_size = size;
		_holesNumber = holesNumber;
		_obstaclesNumber = obstaclesNumber;
		_delayInterval = delayInterval;
		_randomizeInterval = randomizeInterval;
	}

	public int getSize()
	{
		return _size;
	}

	public void HoleFilled()
	{
		--_holesNumber;
	}

	public int getHolesNumber()
	{
		return _holesNumber;
	}

	public int getObstaclesNumber()
	{
		return _obstaclesNumber;
	}

	public int getDelayInterval()
	{
		return _delayInterval;
	}

	public int getRandomizeInterval()
	{
		return _randomizeInterval;
	}

	public void updateValues(int size, int holesNumber, int obstaclesNumber)
	{
		flag = 0;
		_size = size;
		_holesNumber = holesNumber;
		_obstaclesNumber = obstaclesNumber;
		setChanged();
		notifyObservers();
	}

	public void updateDelayInterval(int delayInterval)
	{
		flag = -1;
		_delayInterval = delayInterval;
		setChanged();
		notifyObservers();
	}

	public void updateRandomizeInterval(int randomizeInterval)
	{
		flag = -2;
		_randomizeInterval = randomizeInterval;
		setChanged();
		notifyObservers();
	}

	public void updateFlag(int newFlag)
	{
		flag = newFlag;
		setChanged();
		notifyObservers();
	}
}
