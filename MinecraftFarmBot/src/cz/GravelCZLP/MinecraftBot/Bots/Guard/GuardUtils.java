package cz.GravelCZLP.MinecraftBot.Bots.Guard;

import java.util.ArrayList;
import java.util.List;

import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.entity.player.InteractAction;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerInteractEntityPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Entites.Mob;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class GuardUtils {

	public static float[] getRotationsNeeded(Entity entity, Bot bot) {
		return null;
	}

	public static void faceEntity(Entity entity, Bot bot) {
		
	}

	public static float limitAngleChnage(float current, float intended, float maxChange) {
		float change = intended - current;
		if (change > maxChange) {
			change = maxChange;
		} else if (change < -maxChange) {
			change = -maxChange;
		}
		return current + change;
	}

	public static double distanceSquared(EntityLocation loc1, EntityLocation loc2) {
		if (loc1 == null || loc2 == null) {
			throw new IllegalArgumentException("EntityLocation cannot be null");
		}
		double dx = loc1.getX() - loc2.getX();
		double dy = loc1.getY() - loc2.getY();
		double dz = loc1.getZ() - loc2.getZ();

		return dx * dx + dy * dy + dz * dz;
	}

	public static double distance(EntityLocation loc1, EntityLocation loc2) {
		return Math.sqrt(distanceSquared(loc1, loc2));
	}

	public static List<Player> getNearbyPlayers(EntityLocation currentLoc, List<Player> loadedPlayers, double range) {
		List<Player> players = new ArrayList<>();
		for (Player p : loadedPlayers) {
			double distance = distance(currentLoc, p.getLocation());
			if (distance > range) {
				continue;
			}
			players.add(p);
		}
		return players;
	}

	public static Player getNearestPlayer(List<Player> nearbyPlayers, EntityLocation currentLoc) {
		Player p = null;

		double prevDistance = 20.0;

		for (Player player : nearbyPlayers) {
			double distance = distance(currentLoc, player.getLocation());
			if (distance < prevDistance) {
				prevDistance = distance;
			} else {
				continue;
			}
			p = player;
		}

		return p;
	}

	public static List<Mob> getNearbyDangerousMobs(List<Mob> mobs, EntityLocation currentLoc, double range) {
		List<Mob> nearbyDangerousMobs = new ArrayList<>();

		for (Mob mob : mobs) {
			if (mob.isDangerous()) {
				double distance = distance(currentLoc, mob.getLocation());
				if (distance > range) {
					break;
				}
				nearbyDangerousMobs.add(mob);
			}
		}

		return nearbyDangerousMobs;
	}

	public static Mob getNearestDangrousMob(List<Mob> nearMobs, EntityLocation currentLoc) {
		Mob m = null;
		double prevDistance = 20.0;
		for (Mob mob : nearMobs) {
			if (mob.isDangerous()) {
				double distance = distance(currentLoc, mob.getLocation());
				if (distance < prevDistance) {
					prevDistance = distance;
				} else {
					continue;
				}
				m = mob;
			}
		}
		return m;
	}

	public static void attackEntity(Entity ent, GuardBot bot) {
		ClientPlayerInteractEntityPacket packet = new ClientPlayerInteractEntityPacket(ent.getEntityId(),
				InteractAction.ATTACK, Hand.MAIN_HAND);
		bot.getSession().send(packet);
	}
}
