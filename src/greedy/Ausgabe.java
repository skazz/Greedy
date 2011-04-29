package greedy;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.*;

public class Ausgabe  {
	JFrame f = new JFrame("Greedy");
	GridBagLayout gbl = new GridBagLayout();
	JTextArea console;
	JTable table;
	MyTableModel tableModel;
	JScrollPane scrollPane;

	ActionListener actionListener;
	ItemListener checkboxListener;
	ActionListener toggleButtonListener;


	class MyTableModel extends AbstractTableModel {

		private String[] columnNames = {
				"Amount", "Item", "Prize", "Recreate"
		};

		private Object[][] data = new Object[10][4];

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			if (c == 3)
				return new Boolean(true).getClass();

			return new String().getClass();
		}

		/*
		 * Don't need to implement this method unless your table's
		 * editable.
		 */
		public boolean isCellEditable(int row, int col) {
			//Note that the data/cell address is constant,
			//no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Don't need to implement this method unless your table's
		 * data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	};




	public Ausgabe() {

		console = new JTextArea("Welcome to Greedy\n");
		console.setBorder(new javax.swing.border.BevelBorder(1));
		console.setBackground(Color.LIGHT_GRAY);
		console.setForeground(Color.BLACK);

		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		table.setBorder(new javax.swing.border.BevelBorder(1));
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		
	}

	public void addListener(ActionListener actionListener,ItemListener checkboxListener,ActionListener toggleButtonListener) {
		this.actionListener = actionListener;
		this.checkboxListener = checkboxListener;
		this.toggleButtonListener = toggleButtonListener;
	}

	public void print(String s) {
		console.append(date () + " : " + s);
	}

	public void println(String s) {
		console.append(date () + " : " + s + "\n");
	}

	public void setTable(Vector<Angebot> a) {
		int i = 0;
		for (Angebot temp : a) {
			tableModel.setValueAt(temp.getAmount(), i, 0);
			tableModel.setValueAt(temp.getItem(), i, 1);
			tableModel.setValueAt(temp.getPrize(), i, 2);
			tableModel.setValueAt(false, i, 3);
			i++;
		}
	}

	public JTextArea getConsole() {
		return console;
	}

	public JTable getTable() {
		return table;
	}

	private static String date() {
		SimpleDateFormat f = new SimpleDateFormat("hh:mm:ss");
		return f.format(new java.util.Date());
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

	public void createWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
				Container c = f.getContentPane();
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
				addComponent( c, gbl, table , 2, 2, 6, 10, 0  , 0   );


				//textarea zur ausgabe
				addComponent( c, gbl, new JLabel(" ")  , 0, 9, 0, 1, 0  , 0   );
				addComponent( c, gbl, new JLabel(" ")  , 0, 10, 0, 1, 0  , 0   );
				addComponent( c, gbl, new JLabel(" ")  , 0, 12, 0, 1, 0  , 0   );
				addComponent( c, gbl, console , 0, 13, 0, 0, 0  , 1   );

				f.setSize( 640, 300 );
				f.setVisible( true );
			}
		});
	}
}
