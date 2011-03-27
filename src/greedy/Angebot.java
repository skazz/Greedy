package greedy;

import java.io.IOException;
import java.net.*;

public class Angebot {
	int amount, prize, id;
	URL url;
	String name, item;
	
	
	public Angebot(String row) {
		String[] s;
		String u;

		s = row.split("\\<.*?\\>");
		
		try {
		u = new String(row.substring(row.lastIndexOf("a href=") + 8, row.length() - 20));
		url = new URL( "http://www.worldofminecraft.eu/" + u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		id = Integer.valueOf(row.substring(row.lastIndexOf("&id=") + 4, row.length() - 20));
		name = s[1];
		item = s[6];
		amount = Integer.valueOf(s[8]);
		prize = Integer.valueOf(s[10].split(" ")[0]);
		
		System.out.println(toString());
	}
	
	
	public void trade() {
		try {
			System.out.println(toString());
			url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	@Override public String toString() {
		return String.valueOf(id) + ". " + String.valueOf(prize) + " " + String.valueOf(amount) + " " + item + " " + name + " " + String.valueOf(url);
	}
	
	@Override public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( !(o instanceof Angebot) ) return false;
		
		Angebot a = (Angebot) o;
		return id == a.getID();
	}
}
