package cz.GravelCZLP.MinecraftBot.Bots.Farmer;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Utils.MathHelp;

public class FarmerUtils {

	public static void faceBlock(Position pos, Bot bot) {
		double dx = pos.getX() - bot.getCurrentLoc().getX();
		double dy = pos.getY() - bot.getCurrentLoc().getY();
		double dz = pos.getZ() - bot.getCurrentLoc().getZ();
		double dist = Math.sqrt( MathHelp.square(dx) + MathHelp.square(dz) + MathHelp.square(dy));
		
		double yaw = -Math.atan2(dx, dz) / Math.PI * 180.0D;
		if (yaw < 0) {
			yaw = 360 + yaw;
		}
		double pitch = -Math.asin(dy / dist) /  Math.PI * 180.0D;
		if (bot.getCurrentLoc().getYaw() == yaw && bot.getCurrentLoc().getPitch() == pitch) {
			return; // No need to send data.
		}
		bot.getCurrentLoc().setYaw((float) yaw);
		bot.getCurrentLoc().setPitch((float) pitch);
		ClientPlayerRotationPacket move = new ClientPlayerRotationPacket(true, (float) yaw, (float) pitch);
		bot.getSession().send(move);
	}
	
}
