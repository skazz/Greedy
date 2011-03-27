package greedy;

import java.io.*;
import java.util.*;

public class Markt {
	String name;
	Vector<Angebot> angebot, meinAngebot;

	public Markt(String name) {
		this.name = name;
	}
	
	public void update(BufferedReader r) {
		try {
			Vector<Angebot> aktuell = new Vector<Angebot>();
			for ( String line; (line = r.readLine()) != null; )
			{
				if (line.contains("		  <td style=\"border-width: 1px; border-color:#000000; border-style:solid; border-collapse:collapse;\">"))
					aktuell.add(new Angebot(line));
			}
			
			//neue angebote, entfernte angebote; test
			
			angebot = aktuell;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tradeWithCPU() {
		int cobble = 20;
		
		System.out.println("Trading...");
		
		for (Angebot a : angebot) {
			if (a.getName().equals("World of Minecraft H&auml;ndler e.V")) {
				if (a.getItem().equals("cobble") && a.getPrize() > cobble)
					for(int i = 0; i < a.getPrize() - cobble; i++)
						a.trade();
			}
		}
		
		System.out.println("...trading done");
		
	}
	
	public void print() {
		for (Angebot a : angebot) {
			System.out.println(a.toString());
		}
	}
}
