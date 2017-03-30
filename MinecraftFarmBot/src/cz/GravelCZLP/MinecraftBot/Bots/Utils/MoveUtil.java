package cz.GravelCZLP.MinecraftBot.Bots.Utils;

import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class MoveUtil {

	private EntityLocation prevLocation;

	public MoveUtil(Bot bot) {
		Thread moveUpdateThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				double x = bot.getCurrentLoc().getX();
				double y = bot.getCurrentLoc().getY();
				double z = bot.getCurrentLoc().getZ();
				float yaw = bot.getCurrentLoc().getYaw();
				float pitch = bot.getCurrentLoc().getPitch();
				
				double prevX = prevLocation.getX();
				double prevY = prevLocation.getY();
				double prevZ = prevLocation.getZ();
				float prevYaw = prevLocation.getYaw();
				float prevPitch = prevLocation.getPitch();
				
				if ((x != prevX) || (y != prevY) || (z != prevZ) || (yaw != prevYaw) || (pitch != prevPitch)) {
					ClientPlayerPositionRotationPacket move = new ClientPlayerPositionRotationPacket(true, x, y, z, yaw, pitch);
					bot.getSession().send(move);
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "Bot Location Update Thread");
		moveUpdateThread.start();
	}
}
