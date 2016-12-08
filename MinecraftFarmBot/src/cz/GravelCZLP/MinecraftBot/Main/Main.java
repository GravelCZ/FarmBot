package cz.GravelCZLP.MinecraftBot.Main;

import java.util.List;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager;

public class Main {

	private static int p;
	private static String h;
	
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			if (s.equalsIgnoreCase("-p")) {
				p = Integer.parseInt(args[(i + 1)]);
			}
			if (s.equalsIgnoreCase("-ip")) {
				h = args[(i + 1)];
			}
		}
		new Main(h, p);
	}
	
	public List<Bot> bots;
	
	public Main(String host, int port) {
		List<Bot> bots = BotManager.loadAllBots(host, port);
		this.bots = bots;
		bots.stream().forEach(Bot::connect);
		
	}
}
