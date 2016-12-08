package cz.GravelCZLP.MinecraftBot.Bots.Guard;

import org.spacehq.mc.protocol.MinecraftProtocol;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;

public class GuardBot extends Bot {

	public GuardBot(String host, int port, MinecraftProtocol p) {
		super(host, port, p);
	}

	@Override
	public BotType getType() {
		return BotType.GUARD;
	}

}
