package greedy;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Haendler implements Runnable {
	String name, password;
	int delay;
	
	URL url, ankaufURL, verkaufURL, loginURL;
	CookieManager manager;
	Controller controller;
	Markt marktAlt, marktNeu;
	Vector<Angebot> ownOffers, ownOffersOld, dealsDone;
	HashMap<String, Boolean> zuordnung;
	HashMap<String, Integer> minPrize;

	public Haendler(Controller controller) {		
		delay = 1800;
		this.controller = controller;

		zuordnung = new HashMap<String, Boolean>();

		minPrize = new HashMap<String, Integer>();
		
		dealsDone = new Vector<Angebot>();

		try {
			url = new URL("http://www.worldofminecraft.eu");
			loginURL = new URL(url.toString() + "/?p=login");
			ankaufURL = new URL(url.toString() + "/?p=marktplatz&s=waren_kaufen");
			verkaufURL = new URL(url.toString() + "/?p=marktplatz&s=waren_verkaufen");

			manager = new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(manager);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void register(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public void setDelay(int d) {
		delay = d;
	}
	
	
	private boolean login() {
		HttpURLConnection connection;
		OutputStreamWriter writer;
		BufferedReader lreader;
		try {
			String loginContent = "spieler=" + name + "&passwort=" + password + "&button=Login";
			
			connection = (HttpURLConnection) loginURL.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", Integer.toString(loginContent.length()));

			writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(loginContent);
			writer.flush();
			

			lreader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			for ( String line; (line = lreader.readLine()) != null; )
			{
				if (line.contains("eingelogged"))
					return true;
			}

			lreader.close();
			writer.close();
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private boolean update() {
		try {
			BufferedReader vreader = new BufferedReader(new InputStreamReader(
					verkaufURL.openStream()));

			BufferedReader areader = new BufferedReader(new InputStreamReader(
					ankaufURL.openStream()));

			marktAlt = marktNeu;
			marktNeu = new Markt(areader, vreader);
			
			ownOffersOld = ownOffers;
			ownOffers = marktNeu.getOffersByName(name);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			trade();
			//console.setForeground(Color.BLACK);
			//console.append(date + " : Sleeping for " + (delay) + " sec\n");

			try {
			  TimeUnit.SECONDS.sleep( delay );
			} catch ( InterruptedException e ) { }
		}
	}
	
	public void trade() {
		if (login()) {
			//console.setForeground(Color.BLACK);
			//console.append(date() + " Logged in\n");
			if (update()) {
				Vector<Angebot> ankauf, verkauf, recreate;
				ankauf = marktNeu.getAnkauf();
				verkauf = marktNeu.getVerkauf();
				
				for (Angebot a : verkauf) {
					if (a.getName().equals("World of Minecraft H&auml;ndler e.V")) {
						if (sell(a.getItem())) {
							if (a.getPrize() >= minPrize(a.getItem())) {
								for (int i = 0; i <= a.getPrize() - minPrize(a.getItem()); i++) {
									a.trade();
								}
								dealsDone.add(a);
							}
						}
					}
				}
				
				if ((ownOffersOld != null) && ownOffers != null) {
					recreate = terminatedOffers(ownOffersOld, ownOffers);

					for (Angebot a : recreate) {
						if (a.recreate)
							createOffer(a);
					}
				}
				
				update();
				controller.update();
			}
		}
	}
	
	private boolean createOffer(Angebot a) {
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
			
			//console.setForeground(Color.BLACK);
			
			for ( String line; (line = reader.readLine()) != null; ) {
				if (line.contains("Dein Angebot wurde erfolgreich erstellt.")) {
					return true;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private boolean sell(String item) {
		if (zuordnung.containsKey(item))
			return zuordnung.get(item);
		else
			return false;
	}
	
	private int minPrize(String item) {
		if (minPrize.containsKey(item))
			return minPrize.get(item);
		else
			return -1;
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
	
	public void setMin(String key, int value) {
		minPrize.put(key, value);
	}
	
	public void setSell(String key, boolean value) {
		zuordnung.put(key, value);
	}

	public Vector<Angebot> getOwnOffers() {
		// TODO Auto-generated method stub
		return ownOffers;
	}

	public void setRecreate(int row, boolean value) {
		// TODO Auto-generated method stub
		ownOffers.get(row).recreate = value;
	}
}
