package cz.GravelCZLP.MinecraftBot.Bots.Guard;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Utils.FriendUtils;

public class EntityCheckThread extends Thread {
	
	private GuardBot bot;
	
	public EntityCheckThread(Bot bot) {
		if (!(bot instanceof GuardBot)) {
			throw new IllegalArgumentException("Bot needs to be instance of GuardBot");
		}
		this.bot = (GuardBot) bot;
	}
	
	@Override
	public void run() {
		if (FriendUtils.isAggressive) {
			Entity lastEntityDamagedby = bot.getLastEntityDamagedBy();
		} else {
			
		}
	}
	
}
