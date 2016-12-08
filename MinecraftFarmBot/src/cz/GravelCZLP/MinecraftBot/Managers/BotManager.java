package cz.GravelCZLP.MinecraftBot.Managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spacehq.mc.protocol.MinecraftProtocol;

import com.esotericsoftware.jsonbeans.Json;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Bots.Farmer.FarmerBot;
import cz.GravelCZLP.MinecraftBot.Bots.Guard.GuardBot;
import cz.GravelCZLP.MinecraftBot.Bots.Miner.MinerBot;

public class BotManager {

	public static List<Bot> loadAllBots(String host, int port) {
		File dataFolder = new File("/botData/");
		ArrayList<File> configFiles = new ArrayList<File>();
		for (File f : dataFolder.listFiles()) {
			if(f.getName().endsWith(".json")) {
				configFiles.add(f);
			}
		}
		Json json = new Json();
		ArrayList<Bot> bots = new ArrayList<Bot>();
		for (File configFile: configFiles) {
			JsonConfig config = json.fromJson(JsonConfig.class, configFile);
			switch (config.type) {
			case FARMER:
				FarmerBot farmerBot = new FarmerBot(host, port, new MinecraftProtocol(config.name));
				farmerBot.setRegistered(config.isRegistered);
				if (farmerBot.isRegistered()) {
					farmerBot.setPassword(config.password);	
				}
				bots.add(farmerBot);
				break;
			case GUARD:
				GuardBot guardBot = new GuardBot(host, port, new MinecraftProtocol(config.name));
				guardBot.setRegistered(config.isRegistered);
				if (guardBot.isRegistered()) {
					guardBot.setPassword(config.password);
				}
				break;
			case MINER:
				MinerBot minerBot = new MinerBot(host, port, new MinecraftProtocol(config.name));
				minerBot.setRegistered(config.isRegistered);
				if (minerBot.isRegistered()) {
					minerBot.setPassword(config.password);
				}
				break;
			default:
				break;
			}
		}
		return bots;
	}

	public static void saveBots(List<Bot> bots) {
		for (Bot bot : bots) {
			String password = bot.getPassword();
			boolean registered = bot.isRegistered();
			BotType type = bot.getType();
			String name = bot.getName();
			File folder = new File("/botdata/");
			for (File f : folder.listFiles()) {
				if (f.getName().endsWith(".json")) {
					f.delete();
				}
			}
			String fileName = type.name() + "_" + name;
			JsonConfig cfg = new JsonConfig();
			cfg.isRegistered = registered;
			cfg.name = name;
			cfg.type = type;
			cfg.password = password;
			Json json = new Json();
			String jsonText = json.toJson(cfg, JsonConfig.class);
			jsonText = json.prettyPrint(jsonText);
			try {
				FileWriter fw = new FileWriter(new File(folder + fileName + ".json"));
				fw.write(jsonText);
				fw.flush();
				fw.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class JsonConfig {
		public String name;
		public String password;
		public boolean isRegistered;
		public BotType type;
	}
	
	public static enum BotType {
		FARMER,
		GUARD,
		MINER;
	}
}
