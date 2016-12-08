package cz.GravelCZLP.MinecraftBot.Bots.Farmer;

import org.spacehq.mc.protocol.MinecraftProtocol;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;

public class FarmerBot extends Bot {

	public FarmerBot(String host, int port, MinecraftProtocol p) {
		super(host, port, p);
	}

	@Override
	public BotType getType() {
		return BotType.FARMER;
	}

}
