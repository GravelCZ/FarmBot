package cz.GravelCZLP.MinecraftBot.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.JsonConfig;

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
	
	private static List<Bot> bots;
	
	public Main(String host, int port) {
		List<Bot> bots = BotManager.loadAllBots(host, port);
		Main.bots = bots;
		bots.stream().forEach(Bot::connect);
		
		try {
			startCommandListener(System.in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startCommandListener(InputStream in) throws IOException {		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			while (true) {
				String command = br.readLine();
				String[] args = command.split(" ");
				if (args[0].equalsIgnoreCase("newBot")) {
					if (args.length < 4) {
						System.out.println("Usage: newBot type[FARMER, GUARD, MINER] name password isRegistered(true, false");
					} else {
						BotType typeToCreate = BotType.valueOf(args[1].toUpperCase());
						String name = args[2];
						String password = args[3];
						boolean isRegistered = Boolean.valueOf(args[4]);
						JsonConfig config = new JsonConfig();
						config.isRegistered = isRegistered;
						config.name = name;
						config.password = password;
						config.type = typeToCreate;
						BotManager.saveToFile(config);
						String fileName = config.type.name() + "_" + config.name;
						BotManager.addFromFile(fileName,h, p);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
	
	public static void addBot(Bot bot) {
		bots.add(bot);
	}
}
