package cz.GravelCZLP.MinecraftBot.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Bots.Farmer.FarmerUtils;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.JsonConfig;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

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
		System.out.println("Started");
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
				} else if (args[0].equalsIgnoreCase("placeblock")) {
					double x = Double.valueOf(args[1]);
					double y = Double.valueOf(args[2]);
					double z = Double.valueOf(args[3]);
					Bot bot = getBotByName(args[0]);
					
					if (bot == null) {
						continue;
					}
					FarmerUtils.faceBlock(new Position((int) x, (int) y, (int) z), bot);
					ClientPlayerPlaceBlockPacket b = new ClientPlayerPlaceBlockPacket(new EntityLocation(x, y, z, bot.getCurrentWorld()).toPosition(), BlockFace.UP, Hand.OFF_HAND, 0.5f, 0.5f, 0.5f);
					bot.getSession().send(b);
				} else if (args[0].equalsIgnoreCase("drop")) {
					Bot bot = getBotByName(args[0]);
					
					if (bot == null) {
						continue;
					}
					bot.drop(true, true);
				} else if (args[0].equalsIgnoreCase("listinv")) {
					Bot bot = getBotByName(args[0]);
					
					if (bot == null) {
						continue;
					}
					ItemStack[] inv = bot.getPlayerInventory().getInventory();
					ItemStack[] hotbar = bot.getPlayerInventory().getHotbar();
					for (int i = 0; i < hotbar.length; i++) {
						if (hotbar[i] != null) {
							System.out.println("Hotbar: " + i + " " + hotbar[i].getId() + ":" + hotbar[i].getData() + " a: " + hotbar[i].getAmount());	
						}
					}
					for (int i = 0; i < inv.length; i++) {
						if (inv[i] != null) {
							System.out.println("Hotbar: " + i + " " + inv[i].getId() + ":" + inv[i].getData() + " a: " + inv[i].getAmount());	
						}
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
	
	public Bot getBotByName(String name) {
		List<Bot> bots0 = bots.stream().filter(bot -> !bot.getName().equals(name)).collect(Collectors.toList());
		
		if (bots0.isEmpty()) {
			System.out.println("That bot does not exist.");
			return null;
		}
		return bots0.get(0);
	}
	
	public static void addBot(Bot bot) {
		bots.add(bot);
	}
}
