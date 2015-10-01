package PrairieDog;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel
{
	private JPanel _controls;
	private JLabel _delayIntervalLabel;
	private SpinnerNumberModel _delayIntervalSpin;
	private JSpinner _delayIntervalSpinner;
	private JLabel _randomizeIntervalLabel;
	private SpinnerNumberModel _randomizeIntervalSpin;
	private JSpinner _randomizeIntervalSpinner;
	private JLabel _sizeLabel;
	private SpinnerNumberModel _sizeSpin;
	private JSpinner _sizeSpinner;
	private JLabel _holesLabel;
	private SpinnerNumberModel _holesSpin;
	private JSpinner _holesSpinner;
	private JLabel _obstaclesLabel;
	private SpinnerNumberModel _obstaclesSpin;
	private JSpinner _obstaclesSpinner;
	private JButton _generate;
	private JButton _start;
	private JButton _stop;
	private JLabel _scoreLabel;
	private JLabel _scoreValueLabel;

	public ControlPanel(final Sizes cont)
	{		
		int n = cont.getSize();

		_controls = new JPanel();	
		_controls.setLayout(new GridLayout(15, 1));

		_sizeLabel = new JLabel("Size :");

		_sizeSpin = new SpinnerNumberModel(n, 5, 15, 1);
		_sizeSpinner = new JSpinner(_sizeSpin);
		_sizeSpinner.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent arg) {
				_holesSpinner.setValue(1);
				_obstaclesSpin.setValue(1);
			}
		});
		
		_delayIntervalLabel = new JLabel("Delay interval between moves :");

		_delayIntervalSpin = new SpinnerNumberModel(cont.getDelayInterval(), 0, 1000, 10);
		_delayIntervalSpinner = new JSpinner(_delayIntervalSpin);
		_delayIntervalSpinner.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				cont.updateDelayInterval(((Integer)_delayIntervalSpinner.getValue()).intValue());
			}
		});
		
		_randomizeIntervalLabel = new JLabel("Randomize interval :");

		_randomizeIntervalSpin = new SpinnerNumberModel(cont.getRandomizeInterval(), 0, 1000, 1);
		_randomizeIntervalSpinner = new JSpinner(_randomizeIntervalSpin);
		_randomizeIntervalSpinner.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				cont.updateRandomizeInterval(((Integer)_randomizeIntervalSpinner.getValue()).intValue());
			}
		});
		
		_holesLabel = new JLabel("Number of holes/tiles :");

		_holesSpin = new SpinnerNumberModel(cont.getHolesNumber(), 1, n * n / 2, 1);
		_holesSpinner = new JSpinner(_holesSpin);

		_obstaclesLabel = new JLabel("Number of obstacles :");

		_obstaclesSpin = new SpinnerNumberModel(cont.getObstaclesNumber(), 1, 2 * n, 1);
		_obstaclesSpinner = new JSpinner(_obstaclesSpin);

		_generate = new JButton("Generate Map");
		_generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.updateValues(((Integer)_sizeSpinner.getValue()).intValue(),
						((Integer)_holesSpinner.getValue()).intValue(), 
						((Integer)_obstaclesSpinner.getValue()).intValue());
				_scoreValueLabel.setText("0");
			}
		});

		_start = new JButton("Start");
		_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.updateFlag(1);
			}
		});

		_stop = new JButton("Stop");
		_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.updateFlag(2);
			}
		});

		_scoreLabel = new JLabel("SCORE : ");
		_scoreValueLabel = new JLabel("0");

		_controls.add(_delayIntervalLabel);
		_controls.add(_delayIntervalSpinner);
		_controls.add(_randomizeIntervalLabel);
		_controls.add(_randomizeIntervalSpinner);
		_controls.add(_sizeLabel);
		_controls.add(_sizeSpinner);
		_controls.add(_holesLabel);
		_controls.add(_holesSpinner);
		_controls.add(_obstaclesLabel);
		_controls.add(_obstaclesSpinner);
		_controls.add(_generate);
		_controls.add(_start);
		_controls.add(_stop);
		_controls.add(_scoreLabel);
		_controls.add(_scoreValueLabel);

		add(_controls, BorderLayout.NORTH);
	}
	
	public void UpdateScore(int value)
	{
		_scoreValueLabel.setText("" + (Integer.parseInt(_scoreValueLabel.getText()) + value));
	}

}
