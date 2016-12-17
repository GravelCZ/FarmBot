package cz.GravelCZLP.MinecraftBot.Bots.Guard;

import org.spacehq.mc.protocol.MinecraftProtocol;

import cz.GravelCZLP.MinecraftBot.AI.PathFinder;
import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;

public class GuardBot extends Bot {

	private Entity lastEntityDamagedBy;
	
	private PathFinder currentPathFinder;
	
	public GuardBot(String host, int port, MinecraftProtocol p) {
		super(host, port, p);
	}

	@Override
	public BotType getType() {
		return BotType.GUARD;
	}

	public Entity getLastEntityDamagedBy() {
		return lastEntityDamagedBy;
	}
	
	public void setLastEntityDamagedBy(Entity ent) {
		lastEntityDamagedBy = ent;
	}
	public void setPlayerTarget(Player target) {
		final GuardBot guard = this;
		PathFinder finder = new PathFinder(guard.getCurrentLoc(), target.getLocation(), this);
		Thread attackThread = new Thread(new Runnable() {
			@Override
			public void run() {
				double distance = GuardUtils.distance(getCurrentLoc(), target.getLocation());
				if (distance < 4.0) {
					GuardUtils.faceEntity(target, guard);
					GuardUtils.attackEntity(target, guard);
					
				}
			}
		}, "GuardBot Attack Thread");
	}
}
