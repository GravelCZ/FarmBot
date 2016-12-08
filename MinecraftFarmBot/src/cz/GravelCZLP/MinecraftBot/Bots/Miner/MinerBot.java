package cz.GravelCZLP.MinecraftBot.Bots.Miner;

import org.spacehq.mc.protocol.MinecraftProtocol;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;

public class MinerBot extends Bot {

	public MinerBot(String host, int port, MinecraftProtocol p) {
		super(host, port, p);
	}

	@Override
	public BotType getType() {
		return BotType.MINER;
	}

}
