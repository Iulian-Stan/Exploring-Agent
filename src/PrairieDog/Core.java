package PrairieDog;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Core extends JFrame implements Observer{

	private final Sizes _cont = new Sizes(10, 5, 10, 100, 1000);
	private final Action _act = new Action(_cont.getRandomizeInterval());

	private ControlPanel _control;
	private ClosedEnvironment _closedEnvironement;
	private OpenEnvironment _openEnvironement;

	public Core ()
	{
		_act.addObserver(this);
		_cont.addObserver(this);

		_closedEnvironement = new ClosedEnvironment(_cont.getSize());
		_openEnvironement = new OpenEnvironment(_cont, _act, _closedEnvironement);
		_control = new ControlPanel(_cont);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = getContentPane();
		pane.add(_control, BorderLayout.WEST);
		pane.add(_openEnvironement, BorderLayout.CENTER);
		pane.add(_closedEnvironement, BorderLayout.EAST);
		pack();
		setVisible(true);
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Core();
			}
		});
	}


	@Override
	public void update(Observable observale, Object object) {
		if (observale == _cont)
		{
			switch (_cont.flag)
			{
			case -2 :
				_openEnvironement.setRandomizeInterval(_cont.getRandomizeInterval());
			case -1 :
				_openEnvironement.setAgentWaitInterval(_cont.getDelayInterval());
				break;
			case 0 :
				_openEnvironement.StopAgent();
				remove(_closedEnvironement);
				remove(_openEnvironement);
				_closedEnvironement = new ClosedEnvironment(_cont.getSize());
				_openEnvironement = new OpenEnvironment(_cont, _act, _closedEnvironement);
				add(_openEnvironement, BorderLayout.CENTER);
				add(_closedEnvironement, BorderLayout.EAST);
				pack();
				break;
			case 1 :
				_openEnvironement.StartAgent();
				break;
			case 2 :
				_openEnvironement.InterruptAgent();
				break;
			}
		}
		else
		{
			if (_act.getCost() == 100)
				_cont.HoleFilled();
			_control.UpdateScore(_act.getCost());
			if (_cont.getHolesNumber() == 0)
				_openEnvironement.InterruptAgent();
			if (_act.getSteps() == 0)
			{
				_openEnvironement.Randomize();
				_act.setSteps(_cont.getRandomizeInterval());
			}
		}
	}
}
