package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.List;

import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnExpOrbPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnMobPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnObjectPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPaintingPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.packet.Packet;

import cz.GravelCZLP.MinecraftBot.Entites.Exporb;
import cz.GravelCZLP.MinecraftBot.Entites.Mob;
import cz.GravelCZLP.MinecraftBot.Entites.Object;
import cz.GravelCZLP.MinecraftBot.Entites.Painting;
import cz.GravelCZLP.MinecraftBot.Entites.Player;

public class EntityPacketsListener implements SessionListener {
	private Bot bot;
	
	public EntityPacketsListener(Bot bot) {
		this.bot = bot;
	}

	@Override
	public void connected(ConnectedEvent arg0) {}

	@Override
	public void disconnected(DisconnectedEvent arg0) {}

	@Override
	public void disconnecting(DisconnectingEvent arg0) {}

	@Override
	public void packetReceived(PacketReceivedEvent e) {
		Session s = e.getSession();
		Packet p = e.getPacket();
		// spawn packets !
		if (p instanceof ServerSpawnObjectPacket) {
			ServerSpawnObjectPacket packet = (ServerSpawnObjectPacket) p;
			Object obj = new Object(packet.getEntityId(), packet.getUUID(), packet.getType(), packet.getX(), packet.getY(), packet.getZ(), packet.getPitch(), packet.getYaw(), packet.getData(), packet.getMotionX(), packet.getMotionY(), packet.getMotionZ());
			bot.nearbyObjects.add(obj);
		} else if (p instanceof ServerSpawnPaintingPacket) {
			ServerSpawnPaintingPacket packet = (ServerSpawnPaintingPacket) p;
			Painting painting = new Painting(packet.getEntityId(), packet.getUUID(), packet.getPaintingType(), packet.getDirection(), packet.getPosition());
			bot.nearbyPaintings.add(painting);
		} else if (p instanceof ServerSpawnPlayerPacket) {
			ServerSpawnPlayerPacket packet = (ServerSpawnPlayerPacket) p;
			Player player = new Player(packet.getEntityId(), packet.getUUID(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), packet.getMetadata());
			bot.nearbyPlayers.add(player);
		} else if (p instanceof ServerSpawnMobPacket) {
			ServerSpawnMobPacket packet = (ServerSpawnMobPacket) p;
			Mob mob = new Mob(packet.getEntityId(), packet.getUUID(), packet.getType(), packet.getX(), packet.getY(), packet.getZ(), packet.getPitch(), packet.getYaw(), packet.getHeadYaw(), packet.getMotionX(), packet.getMotionY(), packet.getMotionZ(), packet.getMetadata());
			bot.nearbyMobs.add(mob);
		} else if (p instanceof ServerSpawnExpOrbPacket) {
			ServerSpawnExpOrbPacket packet = (ServerSpawnExpOrbPacket) p;
			Exporb orb = new Exporb(packet.getEntityId(), packet.getX(), packet.getY(), packet.getZ(), packet.getExp());
			bot.nerbyXPs.add(orb);
		}
		
		//entity packets
		if (p instanceof ServerEntityDestroyPacket) {
			ServerEntityDestroyPacket packet = (ServerEntityDestroyPacket) p;
		}
	}

	@Override
	public void packetSent(PacketSentEvent arg0) {}

}
