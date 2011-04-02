package greedy;

import java.io.*;
import java.util.*;

public class Markt {
	Vector<Angebot> ankauf, verkauf;

	public Markt(BufferedReader a, BufferedReader v) {
		ankauf = parseHTML(a);
		verkauf = parseHTML(v);
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
		
		try {
		for ( String line; (line = r.readLine()) != null; )
		{
			if (line.contains("<td style=\"border-width: 1px; border-color:#000000; border-style:solid; border-collapse:collapse;\">"))
				angebot.add(new Angebot(line));
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return angebot;
	}
}
