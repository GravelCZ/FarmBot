package cz.GravelCZLP.MinecraftBot.Bots.Farmer;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Utils.MathHelp;

public class FarmerUtils {

	public static void faceBlock(Position pos, Bot bot) {
		double diffX = pos.getX() + 0.5 - bot.getCurrentLoc().getX();
		double diffY = pos.getY() + 0.5 - (bot.getCurrentLoc().getY() + bot.getCurrentLoc().getYaw());
		double diffZ = pos.getZ() + 0.5 - bot.getCurrentLoc().getZ();
		double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) - (Math.atan2(diffY, dist) * 180.0D / Math.PI);
		bot.getCurrentLoc().setYaw(bot.getCurrentLoc().getYaw() + (float) MathHelp.warpDegrees(yaw - bot.getCurrentLoc().getYaw()));
		bot.getCurrentLoc().setPitch(bot.getCurrentLoc().getPitch() + (float) MathHelp.warpDegrees(pitch - bot.getCurrentLoc().getPitch()));
		ClientPlayerRotationPacket move = new ClientPlayerRotationPacket(true, bot.getCurrentLoc().getYaw(), bot.getCurrentLoc().getPitch());
		bot.getSession().send(move);
	}
	
}
