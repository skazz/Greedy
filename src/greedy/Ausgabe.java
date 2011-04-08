package greedy;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class Ausgabe {
	JTextArea console;
	JTable table;
	
	
	public Ausgabe() {
		Object[][] data = {
			    { "Japan", "245","USA", new Boolean(false) }, { "Italien", "220","Spanien", new Boolean(false) }, {"Türkei", "215","England", new Boolean(false) },
			    { "Frankreich", "190","Griechenland", new Boolean(false) },
			    { "Deutschland", "180","Portugal", new Boolean(false) }
			    };

			    String[] columnNames =  {
			      "Amount", "Item", "Prize", "Recreate"
			    };

		
		
		console = new JTextArea("Welcome to Greedy\n");
		console.setBorder(new javax.swing.border.BevelBorder(1));
		console.setBackground(Color.LIGHT_GRAY);
		console.setForeground(Color.BLACK);
		
		table = new JTable(data, columnNames);
		table.setBorder(new javax.swing.border.BevelBorder(1));
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
	}
	
	public void print(String s) {
		console.append(date () + " : " + s);
	}
	
	public void println(String s) {
		console.append(date () + " : " + s + "\n");
	}
	
	public void setTable(Vector<Angebot> a) {
		
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
}
