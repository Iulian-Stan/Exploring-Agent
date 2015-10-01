package PrairieDog;

import java.util.Observable;

public class Action extends Observable{
	private int _cost;
	private int _stepsTillRandomization;

	public Action(int steps)
	{
		_cost = 0;
		_stepsTillRandomization = steps;
	}

	public int getCost()
	{
		return _cost;
	}

	public int getSteps()
	{
		return _stepsTillRandomization;
	}

	public void setSteps(int steps)
	{
		if (steps > 0)
			_stepsTillRandomization= steps;
		else
			_stepsTillRandomization = -1;
	}

	public void updateCost(int cost)
	{
		_cost = cost;
		if (_stepsTillRandomization > -1)
			--_stepsTillRandomization;
		setChanged();
		notifyObservers();
	}
}
