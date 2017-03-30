package cz.GravelCZLP.MinecraftBot.Bots.Guard;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
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
			Player nearestPlayer = GuardUtils.getNearestPlayer(bot.nearbyPlayers, bot.getCurrentLoc());
			if (FriendUtils.willAttack(nearestPlayer)) {
				GuardUtils.faceEntity(nearestPlayer, bot);
				GuardUtils.attackEntity(nearestPlayer, bot);
			}
		} else {
			
		}
	}
	
}
