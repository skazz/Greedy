package greedy;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

public class Greedy {
	
	URL url;
	Haendler haendler;
	//JTextArea t = new JTextArea("Welcome to Greedy\n");
	Thread haendlerThread;
	String name, password;
	int delay;
	Ausgabe ausgabe;

	private Greedy() {
		try {
			url = new URL("http://www.worldofminecraft.eu");
			ausgabe = new Ausgabe();
			haendler = new Haendler(url, ausgabe);
			delay = 1800;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	static void addComponent( Container cont,
			GridBagLayout gbl,
			Component c,
			int x, int y,
			int width, int height,
			double weightx, double weighty )
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbl.setConstraints( c, gbc );
		cont.add( c );
	}

	
	ActionListener actionListener = new ActionListener() {
		  @Override public void actionPerformed( ActionEvent e ) {
			  String title = ((JTextField)e.getSource()).getName();
			  String text = ((JTextField)e.getSource()).getText();
			  
		    if (title.equals("loginInput"))
		    	name = text;
		    else if (title.equals("passwordInput"))
		    	password = text;
		    else if (title.equals("intervallInput")) {
		    	try {
		    		delay = Integer.valueOf(text);
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
		
		
		ActionListener toggleButtonListener = new ActionListener() {
			@Override public void actionPerformed( ActionEvent e ) {
				if (((JToggleButton)e.getSource()).getName().equals("start"))
					if (((JToggleButton)e.getSource()).getText().equals("start")) {
						((JToggleButton)e.getSource()).setText("stop");

						haendler.register(name, password);
						haendler.setDelay(delay);
						haendlerThread = new Thread (haendler);
						haendlerThread.start();
					} else {
						((JToggleButton)e.getSource()).setText("start");
						haendlerThread.stop();
					}
			}
		};
		
		ItemListener checkboxListener = new ItemListener() {
			  @Override public void itemStateChanged( ItemEvent e ) {
				  String title = ((JCheckBox) e.getItem()).getName();
				  boolean state = (e.getStateChange() == ItemEvent.SELECTED);
				  
			    if( title.contains("sell") )
			    	haendler.setSell(title.toLowerCase().substring(4), state);
			  }
			};

	
	private void createWindow() {
		JFrame f = new JFrame("Greedy");
	    f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    Container c = f.getContentPane();

	    GridBagLayout gbl = new GridBagLayout();
	    c.setLayout( gbl );

	    //erste Zeile, name, pw, delay, startbutton
	    JLabel login = new JLabel("Login : ");
	    JLabel password = new JLabel("  Password : ");
	    JLabel intervall = new JLabel("  Intervall : ");
	    JLabel seconds = new JLabel(" sec   ");
	    
	    JTextField loginInput = new JTextField( 10 );
	    loginInput.setName("loginInput");
	    loginInput.addActionListener(actionListener);
	    JPasswordField passwordInput = new JPasswordField( 10 );
	    passwordInput.setName("passwordInput");
	    passwordInput.addActionListener(actionListener);
	    
	    JTextField intervallInput = new JTextField( 6 );
	    intervallInput.setName("intervallInput");
	    intervallInput.setHorizontalAlignment(JTextField.RIGHT);
	    intervallInput.addActionListener(actionListener);
	    
	    JToggleButton start = new JToggleButton("start");
	    start.setName("start");
	    start.addActionListener(toggleButtonListener);
	    
	    //                                      x  y  w  h  wx   wy

	    addComponent( c, gbl, login           , 0, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, loginInput      , 1, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, password        , 2, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, passwordInput   , 3, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, intervall       , 4, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, intervallInput  , 5, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, seconds         , 6, 0, 1, 1, 0  , 0   );
	    addComponent( c, gbl, start           , 7, 0, 1, 1, 0  , 0   );
	    
	    
	    
	    addComponent( c, gbl, new JLabel(" ")  , 2, 1, 1, 1, 0  , 0   );
	    
	    
	    
	    JCheckBox sellCobble = new JCheckBox("sellCobble");
	    sellCobble.setName("sellCobble");
	    sellCobble.addItemListener(checkboxListener);
	    JTextField minCobble = new JTextField();
	    minCobble.setName("minCobble");
	    minCobble.addActionListener(actionListener);
	    
	    JCheckBox sellSapling = new JCheckBox("sellSapling");
	    sellSapling.setName("sellSapling");
	    sellSapling.addItemListener(checkboxListener);
	    JTextField minSapling = new JTextField();
	    minSapling.setName("minSapling");
	    minSapling.addActionListener(actionListener);
	    
	    JCheckBox sellWood = new JCheckBox("sellWoodenplanks");
	    sellWood.setName("sellWoodenplanks");
	    sellWood.addItemListener(checkboxListener);
	    JTextField minWood = new JTextField();
	    minWood.setName("minWoodenplanks");
	    minWood.addActionListener(actionListener);
	    
	    JCheckBox sellTorch = new JCheckBox("sellTorch");
	    sellTorch.setName("sellTorch");
	    sellTorch.addItemListener(checkboxListener);
	    JTextField minTorch = new JTextField();
	    minTorch.setName("minTorch");
	    minTorch.addActionListener(actionListener);
	    
	    JCheckBox sellGlass = new JCheckBox("sellGlass");
	    sellGlass.setName("sellGlass");
	    sellGlass.addItemListener(checkboxListener);
	    JTextField minGlass = new JTextField();
	    minGlass.setName("minGlass");
	    minGlass.addActionListener(actionListener);
	    
	    JCheckBox sellSand = new JCheckBox("sellSand");
	    sellSand.setName("sellSand");
	    sellSand.addItemListener(checkboxListener);
	    JTextField minSand = new JTextField();
	    minSand.setName("minSand");
	    minSand.addActionListener(actionListener);
	    
	    JCheckBox sellDirt = new JCheckBox("sellDirt");
	    sellDirt.setName("sellDirt");
	    sellDirt.addItemListener(checkboxListener);
	    JTextField minDirt = new JTextField();
	    minDirt.setName("minDirt");
	    minDirt.addActionListener(actionListener);
	    
	    
	    addComponent( c, gbl, sellCobble      , 0, 2, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minCobble       , 1, 2, 1, 1, 0  , 0   );
	    addComponent( c, gbl, sellSapling     , 0, 3, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minSapling      , 1, 3, 1, 1, 0  , 0   );
	    addComponent( c, gbl, sellWood        , 0, 4, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minWood         , 1, 4, 1, 1, 0  , 0   );
	    addComponent( c, gbl, sellTorch       , 0, 5, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minTorch        , 1, 5, 1, 1, 0  , 0   );
	    addComponent( c, gbl, sellGlass       , 0, 6, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minGlass        , 1, 6, 1, 1, 0  , 0   );
	    addComponent( c, gbl, sellSand        , 0, 7, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minSand         , 1, 7, 1, 1, 0  , 0   );
	    addComponent( c, gbl, sellDirt        , 0, 8, 1, 1, 0  , 0   );
	    addComponent( c, gbl, minDirt         , 1, 8, 1, 1, 0  , 0   );

	    
	    //tabelle mit eigenen angeboten
	    addComponent( c, gbl, ausgabe.getTable() , 2, 2, 6, 10, 0  , 0   );
	    
	    
	    //textarea zur ausgabe
	    addComponent( c, gbl, new JLabel(" ")  , 0, 9, 0, 1, 0  , 0   );
	    addComponent( c, gbl, new JLabel(" ")  , 0, 10, 0, 1, 0  , 0   );
	    addComponent( c, gbl, new JLabel(" ")  , 0, 12, 0, 1, 0  , 0   );
	    addComponent( c, gbl, ausgabe.getConsole() , 0, 13, 0, 0, 0  , 1   );
	    
	    f.setSize( 640, 300 );
	    f.setVisible( true );

	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Greedy greedy = new Greedy();
		
		greedy.createWindow();
		
		//greedy.init();
		//greedy.mainLoop();
	}
}
