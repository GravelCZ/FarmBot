package cz.GravelCZLP.MinecraftBot.Bots;

import org.spacehq.mc.protocol.data.game.entity.EquipmentSlot;
import org.spacehq.mc.protocol.data.game.entity.metadata.EntityMetadata;
import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket;
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

import cz.GravelCZLP.MinecraftBot.Entites.Entity;
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
			bot.allEntities.add(obj);
		} else if (p instanceof ServerSpawnPaintingPacket) {
			ServerSpawnPaintingPacket packet = (ServerSpawnPaintingPacket) p;
			Painting painting = new Painting(packet.getEntityId(), packet.getUUID(), packet.getPaintingType(), packet.getDirection(), packet.getPosition());
			bot.nearbyPaintings.add(painting);
			bot.allEntities.add(painting);
		} else if (p instanceof ServerSpawnPlayerPacket) {
			ServerSpawnPlayerPacket packet = (ServerSpawnPlayerPacket) p;
			Player player = new Player(packet.getEntityId(), packet.getUUID(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), packet.getMetadata());
			bot.nearbyPlayers.add(player);
			bot.allEntities.add(player);
		} else if (p instanceof ServerSpawnMobPacket) {
			ServerSpawnMobPacket packet = (ServerSpawnMobPacket) p;
			Mob mob = new Mob(packet.getEntityId(), packet.getUUID(), packet.getType(), packet.getX(), packet.getY(), packet.getZ(), packet.getPitch(), packet.getYaw(), packet.getHeadYaw(), packet.getMotionX(), packet.getMotionY(), packet.getMotionZ(), packet.getMetadata());
			bot.nearbyMobs.add(mob);
			bot.allEntities.add(mob);
		} else if (p instanceof ServerSpawnExpOrbPacket) {
			ServerSpawnExpOrbPacket packet = (ServerSpawnExpOrbPacket) p;
			Exporb orb = new Exporb(packet.getEntityId(), packet.getX(), packet.getY(), packet.getZ(), packet.getExp());
			bot.nerbyXPs.add(orb);
			bot.allEntities.add(orb);
		}
		
		//entity packets
		if (p instanceof ServerEntityDestroyPacket) {
			ServerEntityDestroyPacket packet = (ServerEntityDestroyPacket) p;
			int[] entitiesIds = packet.getEntityIds();
			for(Entity en : bot.allEntities) {
				for (int i : entitiesIds) {
					if (i == en.getEntityId()) {
						bot.allEntities.remove(en);
						if (en instanceof Player) {
							bot.nearbyPlayers.remove((Player) en);
						} else if (en instanceof Mob) {
							bot.nearbyMobs.remove((Mob) en);
						} else if (en instanceof Object) {
							bot.nearbyObjects.remove((Object) en);
						} else if (en instanceof Painting) {
							bot.nearbyPaintings.remove((Painting) en);
						} else if (en instanceof Exporb) {
							bot.nerbyXPs.remove((Exporb) en);
						} else {
							throw new IllegalArgumentException("Invalid Entity");
						}
					}
				}
			}
		} else if (p instanceof ServerEntityEquipmentPacket) {
			ServerEntityEquipmentPacket packet = (ServerEntityEquipmentPacket) p;
			ItemStack newItem = packet.getItem();
			int id = packet.getEntityId();
			EquipmentSlot es = packet.getSlot();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == id) {
					if (ent instanceof Player || ent instanceof Mob) {
						if (ent instanceof Player) {
							Player player = (Player) ent;
							player.getArmor().put(es, newItem);
						} else if (ent instanceof Mob) {
							Mob mob = (Mob) ent;
							mob.getArmor().put(es, newItem);
						}
					}
				}
			}
		} else if (p instanceof ServerEntityHeadLookPacket) {
			ServerEntityHeadLookPacket packet = (ServerEntityHeadLookPacket) p;
			int entityId = packet.getEntityId();
			float headYaw = packet.getHeadYaw();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setYaw(headYaw);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.setHeadYaw(headYaw);
					}
				}
			}
		} else if (p instanceof ServerEntityMetadataPacket) {
			ServerEntityMetadataPacket packet = (ServerEntityMetadataPacket) p;
			int entityId = packet.getEntityId();
			EntityMetadata[] data = packet.getMetadata();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setMetadata(data);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.setMetadata(data);
					}
				}
			}
		} else if (p instanceof ServerEntityPositionPacket) {
			ServerEntityPositionPacket packet = (ServerEntityPositionPacket) p;
			int entityId = packet.getEntityId();
			double moveX = packet.getMovementX();
			double moveY = packet.getMovementY();
			double moveZ = packet.getMovementZ();
			boolean onGround = packet.isOnGround();
		}
	}

	@Override
	public void packetSent(PacketSentEvent arg0) {}

}
