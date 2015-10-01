package PrairieDog;

import java.awt.GridLayout;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ClosedEnvironment extends Environment{

	public ClosedEnvironment(int size)
	{
		N = size;
		
		setLayout(new GridLayout(N, N));
		
		for (int row = 0; row < N; ++row) 
			for (int column = 0; column < N; column++) 
			{
				_list.add(new JLabel(_unknownIcon));
			}
		_list.get(0).setIcon(_agentIcon);

		for (JLabel jLabel : _list) 
			add(jLabel);
		validate();
	}
	
	protected void Update (Changes chang)
	{
		int row = chang.getPreviousPoint().getX(), column = chang.getPreviousPoint().getY();
		JLabel label = (JLabel)getComponent(row * N + column);
		switch (chang.getPosition())
		{
		case Free:
			label.setIcon(_freeIcon);
			break;
		case FilledHole:
			label.setIcon(_filledHoleIcon);
			break;
		case Tile:
			label.setIcon(_tileIcon);
			break;
		case Hole:
			label.setIcon(_holeIcon);
			break;
		case Agent:
			label.setIcon(_agentIcon);
			break;
		default:
			label.setIcon(null);
			break;
		}
	}
}