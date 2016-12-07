package cz.GravelCZLP.FarmBot.Core.Listeners;

import java.util.logging.Level;

import org.spacehq.mc.protocol.data.message.Message;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.login.server.LoginSuccessPacket;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.packet.Packet;

import cz.GravelCZLP.FarmBot.Core.Bot;
import cz.GravelCZLP.FarmBot.Core.EntityLocation;

public class SessionListener1_11 extends SessionListener {

	public SessionListener1_11(Bot bot) {
		super(bot);
	}

	
	@Override
	public void packetReceived(PacketReceivedEvent event) {
		Packet p = event.getPacket();
		if (p instanceof ServerChatPacket) {
			Message msg = event.<ServerChatPacket>getPacket().getMessage();
			String text = msg.getFullText();
			bot.getLogger().log(Level.INFO, text, new Throwable());
		} else if (p instanceof ServerPlayerPositionRotationPacket) {
			ServerPlayerPositionRotationPacket packet = event.<ServerPlayerPositionRotationPacket>getPacket();
			
			double x = packet.getX();
			double y = packet.getY();
			double z = packet.getZ();
			float pitch = packet.getPitch();
			float yaw = packet.getYaw();
			EntityLocation loc = new EntityLocation(x, y, z, yaw, pitch);
			bot.setLocation(loc);
		} else if (p instanceof LoginSuccessPacket) {
            LoginSuccessPacket loginSuccessPacket = event.<LoginSuccessPacket>getPacket();
            super.onJoin(loginSuccessPacket.getProfile());
		}
	}
}
