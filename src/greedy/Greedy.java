package greedy;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

public class Greedy {
	
	static URL url;

	private Greedy(Ausgabe ausgabe) {
		try {
			url = new URL("http://www.worldofminecraft.eu");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	class MyActionListener implements ActionListener {
		Haendler h;
		MyActionListener(Haendler h) {
			this.h = h;
		}
		
		@Override public void actionPerformed( ActionEvent e ) {
			String title = ((JTextField)e.getSource()).getName();
			String text = ((JTextField)e.getSource()).getText();

			if (title.equals("loginInput"))
				h.name = text;
			else if (title.equals("passwordInput"))
				h.password = text;
			else if (title.equals("intervallInput")) {
				try {
					h.delay = Integer.valueOf(text);
				} catch (NumberFormatException a) {
					a.printStackTrace();
				}
			}
			else if (title.contains("min")) {
				try {
					h.setMin(title.toLowerCase().substring(3), Integer.valueOf(text));
				} catch (NumberFormatException a) {
					a.printStackTrace();
				}
			}
		} };


		class ToggleButtonListener implements ActionListener {
			
			Haendler h;
			Thread hThread;
			ToggleButtonListener(Haendler h) {
				this.h = h;
				hThread = new Thread(h);
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
			
			Haendler h;
			CheckboxListener(Haendler h) {
				this.h = h;
			}
			
			@Override public void itemStateChanged( ItemEvent e ) {
				String title = ((JCheckBox) e.getItem()).getName();
				boolean state = (e.getStateChange() == ItemEvent.SELECTED);

				if( title.contains("sell") )
					h.setSell(title.toLowerCase().substring(4), state);
			}
		};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Haendler haendler;	
		Ausgabe ausgabe = new Ausgabe();
		Greedy greedy = new Greedy(ausgabe);
		
		haendler = new Haendler(url, ausgabe);
		
		CheckboxListener checkboxListener = greedy.new CheckboxListener(haendler);
		ToggleButtonListener toggleButtonListener = greedy.new ToggleButtonListener(haendler);
		MyActionListener myActionListener = greedy.new MyActionListener(haendler);
		
		ausgabe.addListener(myActionListener, checkboxListener, toggleButtonListener);
		
		ausgabe.createWindow();
		//greedy.init();
		//greedy.mainLoop();
	}
}
