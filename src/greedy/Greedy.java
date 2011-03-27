package greedy;

import java.io.*;
import java.net.*;
import java.util.*;

public class Greedy {

	CookieManager manager;
	URL loginURL, ankaufURL, verkaufURL;
	HttpURLConnection connection;
	OutputStreamWriter writer;
	BufferedReader lreader, vreader, areader;

	Markt ankauf, verkauf;
	String name, pw;
	int delay;

	private Greedy() {
		try {
			loginURL = new URL("http://www.worldofminecraft.eu/?p=login");
			verkaufURL = new URL(
					"http://www.worldofminecraft.eu/?p=marktplatz&s=waren_verkaufen");
			ankaufURL = new URL(
					"http://www.worldofminecraft.eu/?p=marktplatz&s=waren_kaufen");

			manager = new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(manager);

			delay = 1800000;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		Scanner s = new Scanner(System.in);
		
		System.out.print("WoM-Login    : ");
		name = s.next();
		System.out.print("WoM-Password : ");
		pw = s.next();
		
		try {
			System.out.print("\nIntervall(minutes) : ");
			delay = s.nextInt() * 60000;
		} catch (InputMismatchException e) {
			
		}
	}

	private void login() {
		try {
			String loginContent = "spieler=" + name + "&passwort=" + pw + "&button=Login";
			
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void logout() {
		try {
			writer.close();
			lreader.close();
			vreader.close();
			areader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		try {
			System.out.print("Updating...");

			vreader = new BufferedReader(new InputStreamReader(
					verkaufURL.openStream()));

			areader = new BufferedReader(new InputStreamReader(
					ankaufURL.openStream()));

			if (verkauf == null)
				verkauf = new Markt(name);
			verkauf.update(vreader);

			if (ankauf == null)
				ankauf = new Markt(name);
			ankauf.update(areader);

			System.out.println("done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void trade() {
		verkauf.tradeWithCPU();
	}
	
	private void sleep() {
		java.util.Date now = new java.util.Date();
		System.out.println(now.toString() + " : Sleeping for " + (delay / 60000) + " min");
		
		try {
		Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to GREEDY");

		Greedy greedy = new Greedy();
		greedy.init();
		while (true) {
			try {
				greedy.login();

				greedy.update();

				greedy.trade();

				greedy.logout();
				
				greedy.sleep();

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}
