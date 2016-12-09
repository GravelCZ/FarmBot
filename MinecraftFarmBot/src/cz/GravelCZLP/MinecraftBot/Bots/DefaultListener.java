package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.spacehq.mc.protocol.data.game.ResourcePackStatus;
import org.spacehq.mc.protocol.data.game.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;
import org.spacehq.mc.protocol.data.game.setting.ChatVisibility;
import org.spacehq.mc.protocol.data.game.setting.SkinPart;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientResourcePackStatusPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerResourcePackSendPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.packet.Packet;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class DefaultListener implements SessionListener {

	protected Bot bot;
	
	public DefaultListener(Bot b) {
		this.bot = b;
	}
	
	@Override
	public void connected(ConnectedEvent e) {
		Session s = e.getSession();
		ClientChatPacket p = bot.isRegistered() ? new ClientChatPacket("/login " + bot.getPassword()) : new ClientChatPacket("/register passworld99 passworld99");
		s.send(p);
		
		List<SkinPart> list = new ArrayList<SkinPart>();
		
		for (SkinPart sp : SkinPart.values()) {
			list.add(sp);
		}
		
		ClientSettingsPacket packet = new ClientSettingsPacket("null", 8, ChatVisibility.FULL, true, (SkinPart[])list.toArray(), Hand.MAIN_HAND);
		s.send(packet);
	}

	@Override
	public void disconnected(DisconnectedEvent e) { 
		String why = e.getReason(); 
		bot.getLogger().log(Level.WARNING, "Bot byl Odpojen: " + why);
	}

	@Override
	public void disconnecting(DisconnectingEvent arg0) {}

	@Override
	public void packetReceived(PacketReceivedEvent e) {
		Packet p = e.getPacket();
		Session s = e.getSession();
		if (p instanceof ServerChatPacket) {
			ServerChatPacket packet = (ServerChatPacket) p;
			String msg = packet.getMessage().getFullText();
			bot.getLogger().log(Level.FINE, msg);
		} else if (p instanceof ServerRespawnPacket) {
			ServerRespawnPacket packet = (ServerRespawnPacket) p;
			GameMode mode = packet.getGameMode();
			bot.setGameMode(mode);
		} else if (p instanceof ServerResourcePackSendPacket) {
			ClientResourcePackStatusPacket packet1 = new ClientResourcePackStatusPacket(ResourcePackStatus.ACCEPTED);
			ClientResourcePackStatusPacket packet2 = new ClientResourcePackStatusPacket(ResourcePackStatus.SUCCESSFULLY_LOADED);
			s.send(packet1);
			s.send(packet2);
		} else if (p instanceof ServerPlayerPositionRotationPacket) {
			ServerPlayerPositionRotationPacket packet = (ServerPlayerPositionRotationPacket) p;
			double x = packet.getX();
			double y = packet.getY();
			double z = packet.getZ();
			float yaw = packet.getYaw();
			float pitch = packet.getPitch();
			bot.setCurrentLoc(new EntityLocation(x, y, z, yaw, pitch));
		} else if (p instanceof ServerPlayerHealthPacket) {
			ServerPlayerHealthPacket packet = (ServerPlayerHealthPacket) p;
			float health = packet.getHealth();
			float food = packet.getFood();
			float saturation = packet.getSaturation();
			bot.setHealth(health);
			bot.setFood(food);
			bot.setSaturation(saturation);
		}
	}

	@Override
	public void packetSent(PacketSentEvent arg0) {}

}
