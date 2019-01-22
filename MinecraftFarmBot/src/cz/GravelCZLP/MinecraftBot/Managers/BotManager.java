package cz.GravelCZLP.MinecraftBot.Managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.jsonbeans.Json;
import com.github.steveice10.mc.protocol.MinecraftProtocol;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Bots.Farmer.FarmerBot;
import cz.GravelCZLP.MinecraftBot.Bots.Guard.GuardBot;
import cz.GravelCZLP.MinecraftBot.Bots.Miner.MinerBot;
import cz.GravelCZLP.MinecraftBot.Main.Main;

public class BotManager {

	public static List<Bot> loadAllBots(String host, int port) {
		File dataFolder = new File("./botData");
		if(dataFolder.exists()) {
			if (!dataFolder.isDirectory()) {
				dataFolder.delete();
				dataFolder.mkdir();
			}
		} else {
			dataFolder.mkdir();
		}
		ArrayList<File> configFiles = new ArrayList<File>();
		if (dataFolder.listFiles() == null) {
			return new ArrayList<Bot>();
		}
		for (File f : dataFolder.listFiles()) {
			if(f.getName().endsWith(".json")) {
				configFiles.add(f);
			}
		}
		if (configFiles.isEmpty()) {
			return new ArrayList<Bot>();
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
				bots.add(guardBot);
				break;
			case MINER:
				MinerBot minerBot = new MinerBot(host, port, new MinecraftProtocol(config.name));
				minerBot.setRegistered(config.isRegistered);
				if (minerBot.isRegistered()) {
					minerBot.setPassword(config.password);
				}
				bots.add(minerBot);
				break;
			default:
				break;
			}
		}
		return bots;
	}

	public static void saveBots(List<Bot> bots) {
		if (bots.isEmpty()) {
			return;
		}
		for (Bot bot : bots) {
			String password = bot.getPassword();
			boolean registered = bot.isRegistered();
			BotType type = bot.getType();
			String name = bot.getName();
			File folder = new File("./botData");
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
				File jsonFile = new File(folder.getParentFile() + "/" + fileName + ".json");
				if (!jsonFile.exists()) {
					jsonFile.createNewFile();
				}
				FileWriter fw = new FileWriter(jsonFile);
				fw.write(jsonText);
				fw.flush();
				fw.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveToFile(JsonConfig config) {
		BotType type = config.type;
		String name = config.name;
		File folder = new File("./botData");
		String fileName = type.name() + "_" + name;
		Json json = new Json();
		String jsonText = json.toJson(config, JsonConfig.class);
		jsonText = json.prettyPrint(jsonText);
		try {
			File jsonFile = new File(folder.getAbsolutePath() + "/" + fileName + ".json");
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
			}
			FileWriter fw = new FileWriter(jsonFile);
			fw.write(jsonText);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addFromFile(String name, String host, int port) {
		Json json = new Json();
		File folder = new File("./botData");
		File configFile = new File(folder.getAbsolutePath() + "/" + name + ".json");
		JsonConfig config = json.fromJson(JsonConfig.class, configFile);
		switch (config.type) {
		case FARMER:
			FarmerBot fb = new FarmerBot(host, port, new MinecraftProtocol(config.name));
			fb.setPassword(config.password);
			fb.setRegistered(config.isRegistered);
			Main.addBot(fb);
			fb.connect();
			break;
		case GUARD:
			GuardBot gb = new GuardBot(host, port, new MinecraftProtocol(config.name));
			gb.setPassword(config.password);
			gb.setRegistered(config.isRegistered);
			Main.addBot(gb);
			gb.connect();
			break;
		case MINER:
			MinerBot mb = new MinerBot(host, port, new MinecraftProtocol(config.name));
			mb.setPassword(config.password);
			mb.setRegistered(config.isRegistered);
			Main.addBot(mb);
			mb.connect();
			break;
		default:
			break;
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
