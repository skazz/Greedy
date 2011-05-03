package greedy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Controller {

	Ausgabe ausgabe;
	Haendler haendler;
	
	CheckboxListener checkboxListener;
	ToggleButtonListener toggleButtonListener;
	MyActionListener myActionListener;
	MyTableModelListener myTableModelListener;
	
	public Controller() {
		haendler = new Haendler(this);
		ausgabe = new Ausgabe(haendler);
		checkboxListener = new CheckboxListener();
		toggleButtonListener = new ToggleButtonListener();
		myActionListener = new MyActionListener();
		myTableModelListener = new MyTableModelListener();
		
		ausgabe.addListener(myActionListener, checkboxListener, toggleButtonListener, myTableModelListener);
	}
	
	class MyTableModelListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			// TODO Auto-generated method stub
			if (e.getColumn() == 3) {
				int row = e.getFirstRow();
				haendler.setRecreate(row, (Boolean) ausgabe.getTable().getValueAt(row, 3));
			}
		}
		
	}
	
	class MyActionListener implements ActionListener {
		@Override public void actionPerformed( ActionEvent e ) {
			String title = ((JTextField)e.getSource()).getName();
			String text = ((JTextField)e.getSource()).getText();

			if (title.equals("loginInput"))
				haendler.name = text;
			else if (title.equals("passwordInput"))
				haendler.password = text;
			else if (title.equals("intervallInput")) {
				try {
					haendler.delay = Integer.valueOf(text);
				} catch (NumberFormatException a) {
					a.printStackTrace();
				}
			}
			else if (title.contains("min")) {
				try {
					haendler.setMin(title.toLowerCase().substring(3), Integer.valueOf(text));
				} catch (NumberFormatException a) {
					a.printStackTrace();
				}
			}
		} };


		class ToggleButtonListener implements ActionListener {
			
			Thread hThread;
			ToggleButtonListener() {
				hThread = new Thread(haendler);
			}
			
			
			@Override public void actionPerformed( ActionEvent e ) {
				if (((JToggleButton)e.getSource()).getName().equals("start"))
					if (((JToggleButton)e.getSource()).getText().equals("start")) {
						((JToggleButton)e.getSource()).setText("stop");
						
						hThread.start();
					} else {
						((JToggleButton)e.getSource()).setText("start");
						hThread.stop();
					}
			}
		};

		class CheckboxListener implements ItemListener {
			@Override public void itemStateChanged( ItemEvent e ) {
				String title = ((JCheckBox) e.getItem()).getName();
				boolean state = (e.getStateChange() == ItemEvent.SELECTED);

				if( title.contains("sell") )
					haendler.setSell(title.toLowerCase().substring(4), state);
			}
		};
		
		
		public void update() {
			ausgabe.update();
		}


		public void showView() {
			// TODO Auto-generated method stub
			ausgabe.createWindow();
		}
}
