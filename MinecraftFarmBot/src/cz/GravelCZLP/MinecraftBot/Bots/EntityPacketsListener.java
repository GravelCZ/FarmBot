package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.List;

import org.spacehq.mc.protocol.data.game.entity.Effect;
import org.spacehq.mc.protocol.data.game.entity.EntityStatus;
import org.spacehq.mc.protocol.data.game.entity.EquipmentSlot;
import org.spacehq.mc.protocol.data.game.entity.attribute.Attribute;
import org.spacehq.mc.protocol.data.game.entity.metadata.EntityMetadata;
import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityPropertiesPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityRemoveEffectPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityStatusPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerAbilitiesPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerSetExperiencePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnExpOrbPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnMobPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnObjectPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPaintingPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
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
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setX(moveX);
						player.setY(moveY);
						player.setZ(moveZ);
						player.setOnGround(onGround);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.setX(moveX);
						mob.setY(moveY);
						mob.setZ(moveZ);
						mob.setOnGround(onGround);
					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.setX(moveX);
						obj.setY(moveY);
						obj.setZ(moveZ);
						obj.setOnGround(onGround);
					}
				}
			}
		} else if (p instanceof ServerEntityPositionRotationPacket) {
			ServerEntityPositionRotationPacket packet = (ServerEntityPositionRotationPacket) p;
			int entityId = packet.getEntityId();
			double moveX = packet.getMovementX();
			double moveY = packet.getMovementY();
			double moveZ = packet.getMovementZ();
			boolean onGround = packet.isOnGround();
			float yaw = packet.getYaw();
			float pitch = packet.getPitch();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setX(moveX);
						player.setY(moveY);
						player.setZ(moveZ);
						player.setYaw(yaw);
						player.setPitch(pitch);
						player.setOnGround(onGround);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.setX(moveX);
						mob.setY(moveY);
						mob.setZ(moveZ);
						mob.setYaw(yaw);
						mob.setPitch(pitch);
						mob.setOnGround(onGround);
					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.setX(moveX);
						obj.setY(moveY);
						obj.setZ(moveZ);
						obj.setYaw(yaw);
						obj.setPitch(pitch);
						obj.setOnGround(onGround);
					}
				}
			}
		} else if (p instanceof ServerEntityPropertiesPacket) {
			ServerEntityPropertiesPacket packet = (ServerEntityPropertiesPacket) p;
			int entityId = packet.getEntityId();
			List<Attribute> attributes = packet.getAttributes();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setAttributes(attributes);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.attributes = attributes;
					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.attributes = attributes;
					}
				}
			}
		} else if (p instanceof ServerEntityRemoveEffectPacket) {
			ServerEntityRemoveEffectPacket packet = (ServerEntityRemoveEffectPacket) p;
			int entityId = packet.getEntityId();
			Effect effectToRemove = packet.getEffect();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.effects.remove(effectToRemove);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.effects.remove(effectToRemove);
 					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.effects.remove(effectToRemove);
					}
				}
			}
		} else if (p instanceof ServerEntityRotationPacket) {
			ServerEntityRotationPacket packet = (ServerEntityRotationPacket) p;
			int entityId = packet.getEntityId();
			float yaw = packet.getYaw();
			float pitch = packet.getPitch();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setYaw(yaw);
						player.setPitch(pitch);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;
						mob.setYaw(yaw);
						mob.setPitch(pitch);						
 					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.setYaw(yaw);
						obj.setPitch(pitch);
					}
				}
			}
		} else if (p instanceof ServerEntityStatusPacket) {
			ServerEntityStatusPacket packet = (ServerEntityStatusPacket) p;
			int entityId = packet.getEntityId();
			EntityStatus status = packet.getStatus();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setStatus(status);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;	
						mob.setStatus(status);
 					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.setStatus(status);
					}
				}
			}
		} else if (p instanceof ServerEntityTeleportPacket) {
			ServerEntityTeleportPacket packet = (ServerEntityTeleportPacket) p;
			double X = packet.getX();
			double Y = packet.getY();
			double Z = packet.getZ();
			boolean onGround = packet.isOnGround();
			float yaw = packet.getYaw();
			float pitch = packet.getPitch();
			int entityId = packet.getEntityId();
 			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
						player.setX(X);
						player.setY(Y);
						player.setZ(Z);
						player.setYaw(yaw);
						player.setPitch(pitch);
						player.setOnGround(onGround);
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;		
						mob.setX(X);
						mob.setY(Y);
						mob.setZ(Z);
						mob.setYaw(yaw);
						mob.setPitch(pitch);
						mob.setOnGround(onGround);
						} else if (ent instanceof Object) {
						Object obj = (Object) ent;
						obj.setX(X);
						obj.setY(Y);
						obj.setZ(Z);
						obj.setYaw(yaw);
						obj.setPitch(pitch);
						obj.setOnGround(onGround);
					}
				}
 			}
		} else if (p instanceof ServerPlayerAbilitiesPacket) {
			ServerPlayerAbilitiesPacket packet = (ServerPlayerAbilitiesPacket) p;
			bot.setCanFly(packet.getCanFly());
			bot.setInvincible(packet.getInvincible());
			bot.setFlying(packet.getFlying());
			bot.setCreative(packet.getCreative());
			bot.setFlySpeed(packet.getFlySpeed());
			bot.setWalkSpeed(packet.getWalkSpeed());
		} else if (p instanceof ServerPlayerChangeHeldItemPacket) {
			ServerPlayerChangeHeldItemPacket packet = (ServerPlayerChangeHeldItemPacket) p;
			bot.currentSlotInHand = packet.getSlot();
		} else if (p instanceof ServerPlayerSetExperiencePacket) {
			ServerPlayerSetExperiencePacket packet = (ServerPlayerSetExperiencePacket) p;
			bot.setTotalExperience(packet.getTotalExperience());
			bot.setLevel(packet.getLevel());
			bot.setExperience(packet.getSlot());
		}
	}

	@Override
	public void packetSent(PacketSentEvent arg0) {}
	
	/*
	 			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					if (ent instanceof Player) {
						Player player = (Player) ent;
					} else if (ent instanceof Mob) {
						Mob mob = (Mob) ent;						
 					} else if (ent instanceof Object) {
						Object obj = (Object) ent;
					}
				}
			}
	 */
}
