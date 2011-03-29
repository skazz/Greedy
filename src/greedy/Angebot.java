package greedy;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Angebot {
	int amount, prize, id;
	URL url;
	String name, item, value;	
	
	static HashMap<String,String> m;
	
	public Angebot(String row) {
		
		if (m == null) {
			try {
				m = new HashMap<String,String>();
				String[] s = new String[2];
				BufferedReader r = new BufferedReader(new FileReader("itemHash.txt"));
				for(String line; (line = r.readLine()) != null;) {
					s = line.split(" ", 2);
					m.put(s[0], s[1]);
				}
			} catch (FileNotFoundException e) {
				System.err.print("File not found");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String[] s;
		String u;
		int offset = 0;
		
		s = row.split("\\<.*?\\>");
		
		try {
		u = new String(row.substring(row.lastIndexOf("a href=") + 8, row.length() - 20));
		url = new URL( "http://www.worldofminecraft.eu/" + u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		id = Integer.valueOf(row.substring(row.lastIndexOf("&id=") + 4, row.lastIndexOf("&id=") + 9));
		
		//anpassung fuer stadt (+1 neuer string vor name)
		if (row.contains("<font color=\"orange\">"))
			offset = offset + 1;
		name = s[1 + offset];
		
		//anpassung fuer stadt (nochmal +1 string nach name
		if (row.contains("<font color=\"orange\">"))
			offset = offset + 1;
		
		item = s[6 + offset];
		
		//anpassung fuer item mit durability (+2 strings nach item)
		//entfernen des leerzeichens zwischen item (durability)
		if (row.contains("Durability")) {
			item = item.replaceAll(" ", "");
			offset = offset + 2;
		}
		
		amount = Integer.valueOf(s[8 + offset]);
		prize = Integer.valueOf(s[10 + offset].split(" ")[0]);
		
		value = m.get(item);
	}
	
	
	public void trade() {
		try {
			System.out.println(tradeMessage());
			url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String tradeMessage() {
		String s = (new java.util.Date()).toString();
		if (url.toString().contains("verkaufen")) {
			s = s + " : Sold ";
		} else {
			s = s + " : Bought ";
		}
		
		s = s + toString();;
		
		return s;
	}
	
	public String getName() {
		return name;
	}
	
	public String getItem() {
		return item;
	}
	
	public int getPrize() {
		return prize;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getID() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override public String toString() {
		return String.valueOf(id) + ". " + String.valueOf(amount) + " " + item + " " + String.valueOf(prize) + " " + name + " " + value;
	}
	
	@Override public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( !(o instanceof Angebot) ) return false;
		
		Angebot a = (Angebot) o;
		return id == a.getID();
	}
}
