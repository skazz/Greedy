package greedy;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Angebot {
	URL url;
	String name, item, value;
	int amount, prize, id;
	boolean verkaufen;
	boolean recreate = false;
	
	static HashMap<String,String> m;
	
	public Angebot(String name, String item, int amount, int prize, int id, boolean verkaufen) {
		
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
		
		this.id = id;
		this.name = name;
		this.item = item;
		this.amount = amount;
		this.prize = prize;
		value = m.get(item);
		
		try {
			if (verkaufen)
				url = new URL( "http://www.worldofminecraft.eu/?p=marktplatz&s=waren_verkaufen&id=" + this.id);
			else
				url = new URL( "http://www.worldofminecraft.eu/?p=marktplatz&s=waren_kaufen&id=" + this.id);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void trade() {
		try {
			url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String tradeMessage() {
		String s;
		if (url.toString().contains("verkaufen")) {
			s = "Sold ";
		} else {
			s = "Bought ";
		}
		
		s = s + amount + " " + item + " " + prize;
		
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
		return String.valueOf(id) + ". " + String.valueOf(amount) + " " + item + " " + String.valueOf(prize) + " " + name + " " + value + " " + recreate;
	}
	
	@Override public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( !(o instanceof Angebot) ) return false;
		
		Angebot a = (Angebot) o;
		return id == a.getID();
	}
}
