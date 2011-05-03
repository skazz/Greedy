package greedy;

import java.io.*;
import java.util.*;

public class Markt {
	Vector<Angebot> ankauf, verkauf;
	Date date;

	public Markt(BufferedReader a, BufferedReader v) {
		ankauf = parseHTML(a);
		verkauf = parseHTML(v);
		date = new java.util.Date();
	}

	public void update(BufferedReader a, BufferedReader v) {
		ankauf = parseHTML(a);
		verkauf = parseHTML(v);
	}
	
	public Vector<Angebot> getAnkauf() {
		return ankauf;
	}
	
	public Vector<Angebot> getVerkauf() {
		return verkauf;
	}
	
	public Vector<Angebot> getOffersByName(String name) {
		Vector<Angebot> o = new Vector<Angebot>();
		for (Angebot a : ankauf) {
			if (a.getName().equals(name))
				o.add(a);
		}
		
		for (Angebot a : verkauf) {
			if (a.getName().equals(name))
				o.add(a);
		}
		
		return o;
	}
	
	
	private Vector<Angebot> parseHTML(BufferedReader r) {
		Vector<Angebot> angebot = new Vector<Angebot>();
		String s[] = new String[5];
		String name, item;
		int amount, prize, id;
		boolean verkaufen;
		
		try {
			while (!r.readLine().contains("tbody")) {}

				for ( String line; (line = r.readLine()) != null; )
				{
					if (line.contains("<tr>")) {
						for (int i = 0; i < 5; i++) {
							s[i] = r.readLine();
						}

						name = parseLine(s[0]);
						item = parseLine(s[1]);
						amount = Integer.valueOf(parseLine(s[2]));
						prize = Integer.valueOf(parseLine(s[3]));
						id = Integer.valueOf(s[4].substring(s[4].indexOf("id=") + 3, s[4].lastIndexOf("\"")));
						verkaufen = s[4].contains("verkaufen");
						
						angebot.add(new Angebot(name, item, amount, prize, id, verkaufen));
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return angebot;
	}
	
	private String parseLine(String s) {
		String _s;
		int k = 0;
		
		while (k < s.length()) {
			k = s.indexOf(">", k);
			_s = s.substring(k + 1, s.indexOf("<", k));
			if (!_s.equals(""))
				return _s;
			k++;
		}
		
		return new String();
	}
}
