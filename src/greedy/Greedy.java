package greedy;

import java.net.*;
import java.util.*;

public class Greedy {
	
	URL url;
	Haendler haendler;
	String name, password;
	int delay;

	private Greedy() {
		try {
			url = new URL("http://www.worldofminecraft.eu");

			haendler = new Haendler(url);
			
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
		password = s.next();
		
		haendler.register(name, password);
		try {
			System.out.print("\nIntervall(minutes) : ");
			delay = s.nextInt() * 60000;
		} catch (InputMismatchException e) {
			
		}
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
	
	private void mainLoop() {
		while (true) {
			try {				
				haendler.trade();
				sleep();

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to GREEDY");

		Greedy greedy = new Greedy();
		
		greedy.init();
		greedy.mainLoop();
	}
}
