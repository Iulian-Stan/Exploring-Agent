package PrairieDog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Environment extends JPanel implements ActionListener {

	protected int N;
	
	protected static ImageIcon _holeIcon = new ImageIcon("resources/PrairieDog48.jpg");
	protected static ImageIcon _tileIcon = new ImageIcon("resources/Dynamite48.jpg");
	protected static ImageIcon _filledHoleIcon = new ImageIcon("resources/DeadDog48.jpg");
	protected static ImageIcon _freeIcon = new ImageIcon("resources/Free48.jpg");
	protected static ImageIcon _agentIcon = new ImageIcon("resources/Agent48.jpg");
	protected static ImageIcon _unknownIcon = new ImageIcon("resources/Unknown48.jpg");

	protected List<JLabel> _list = new LinkedList<JLabel>();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Update((Changes) e.getSource());
	}

	protected abstract void Update(Changes changes);	
}
