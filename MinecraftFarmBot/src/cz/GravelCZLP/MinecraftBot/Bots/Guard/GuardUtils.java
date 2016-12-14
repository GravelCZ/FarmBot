package cz.GravelCZLP.MinecraftBot.Bots.Guard;

import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.Utils.MathHelp;

public class GuardUtils {

	public static float[] getRotationsNeeded(Entity entity, Bot bot) {
		double diffX = entity.getLocation().getX() - bot.getCurrentLoc().getX();
		double diffY = entity.getLocation().getY() + entity.getLocation().getYaw() * 0.9 - (bot.getCurrentLoc().getY() + bot.getCurrentLoc().getYaw()); 
		double diffZ = entity.getLocation().getZ() - bot.getCurrentLoc().getZ();
		double dist = Math.sqrt(diffX * diffX + diffZ + diffZ);
		double yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) - (Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[]{
				(float) (bot.getCurrentLoc().getYaw() + MathHelp.warpDegrees(yaw - bot.getCurrentLoc().getYaw())),
				(float) (bot.getCurrentLoc().getPitch() + MathHelp.warpDegrees(pitch - bot.getCurrentLoc().getPitch()))
		};
	}
	public synchronized static void faceEntity(Entity entity, Bot bot) {
		float[] rotations = getRotationsNeeded(entity, bot);
		if (rotations != null) {
			EntityLocation botLoc = bot.getCurrentLoc().clone();
			botLoc.setYaw(limitAngleChnage(bot.getCurrentLoc().getYaw(), rotations[0], 100));
			botLoc.setPitch(rotations[1]);
			ClientPlayerPositionRotationPacket move = new ClientPlayerPositionRotationPacket(
					true, 
					botLoc.getX(), 
					botLoc.getY(), 
					botLoc.getZ(), 
					botLoc.getYaw(), 
					botLoc.getPitch()
					);
			bot.getSession().send(move);
		}
	}
	public final static float limitAngleChnage(final float current, final float intended, final float maxChange) {
		float change = intended - current;
		if (change > maxChange) {
			change = maxChange;
		}
		else if (change < -maxChange) {
			change = -maxChange;
		}
		return current + change;
	}
	
	public double distanceSquared(EntityLocation loc1, EntityLocation loc2) {
		if (loc1 == null || loc2 == null) {
			throw new IllegalArgumentException("EntityLocation cannot be null");
		}
		double x1 = loc1.getX();
		double y1 = loc1.getY();
		double z1 = loc1.getZ();
		
		double x2 = loc2.getX();
		double y2 = loc2.getY();
		double z2 = loc2.getZ();
		
		double squareX = MathHelp.square(x1 - x2);
		double squareY = MathHelp.square(y1 - y2);
		double squareZ = MathHelp.square(z1 - z2);
		return squareX + squareY + squareZ;
	}
	
	public double distance(EntityLocation loc1, EntityLocation loc2) {
		return Math.sqrt(distanceSquared(loc1, loc2));
	}
	
}
