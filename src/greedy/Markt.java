package greedy;

import java.io.*;
import java.net.*;
import java.util.*;

public class Markt {
	String name;
	Vector<Angebot> ankauf_alt, verkauf_alt, meinAngebot_alt;
	Vector<Angebot> ankauf, verkauf, meinAngebot;
	CookieManager manager;

	public Markt(String name, CookieManager manager) {
		this.name = name;
		this.manager = manager;
		ankauf = new Vector<Angebot>();
		verkauf = new Vector<Angebot>();
		meinAngebot = new Vector<Angebot>();
	}

	public void update(BufferedReader a, BufferedReader v) {
		ankauf_alt = ankauf;
		ankauf = parseHTML(a);

		verkauf_alt = verkauf;
		verkauf = parseHTML(v);
		
		meinAngebot_alt = meinAngebot;
		meinAngebot = new Vector<Angebot>();
		for (Angebot angebot : ankauf) {
			if (angebot.getName().equals(name))
				meinAngebot.add(angebot);
		}
	}
	
	private static Vector<Angebot> terminatedOffers(Vector<Angebot> alt, Vector<Angebot> neu) {
		Vector<Angebot> o = new Vector<Angebot>();
		
		if (alt == null || neu == null)
			return null;
		
		for (Angebot a : alt) {
			if (!neu.contains(a))
				o.add(a);
		}
		return o;
	}

	public void tradeWithCPU() {
		int cobble = 20;
		
		System.out.println("Trading...");
		
		for (Angebot a : verkauf) {
			if (a.getName().equals("World of Minecraft H&auml;ndler e.V")) {
				if (a.getItem().equals("cobble") && a.getPrize() > cobble)
					for(int i = 0; i < a.getPrize() - cobble; i++)
						a.trade();
			}
		}
		
		System.out.println("...trading done");
		
	}
	
	public void recreateOffers() {
		Vector<Angebot> o;
		o = terminatedOffers(meinAngebot_alt, meinAngebot);
		for (Angebot a : o) {
			createOffer(a);
		}
	}
	
	public void createOffer(Angebot a) {
		URL url;
		HttpURLConnection connection;
		OutputStreamWriter writer;
		BufferedReader reader;
		
		try {
			String content = "kaufen_ex=0&ware=" + a.getValue() + "&anzahl=" + a.getAmount() + "&preis=" + a.getPrize() + "&button=Angebot+erstellen";
			
			String cookie = manager.getCookieStore().getCookies().get(0).toString();
			url = new URL("http://worldofminecraft.eu/?p=marktplatz&s=angebot_erstellen");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(true);
			connection.setRequestProperty("Cookie", cookie);
			connection.setRequestProperty("Referer", "http://worldofminecraft.eu/?p=marktplatz&s=angebot_erstellen");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", Integer.toString(content.length()));

			writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(content);
			writer.flush();

			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			System.out.println((new java.util.Date()).toString() + " : Recreated " + a.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print() {
		for (Angebot a : ankauf) {
			System.out.println(a.toString());
		}
		
		System.out.println("\n\n");
		
		for (Angebot a : verkauf) {
			System.out.println(a.toString());
		}
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
