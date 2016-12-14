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
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

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
			EntityLocation loc = new EntityLocation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
			Object obj = new Object(packet.getEntityId(), packet.getUUID(), packet.getType(), loc, packet.getData(), packet.getMotionX(), packet.getMotionY(), packet.getMotionZ());
			bot.nearbyObjects.add(obj);
			bot.allEntities.add(obj);
		} else if (p instanceof ServerSpawnPaintingPacket) {
			ServerSpawnPaintingPacket packet = (ServerSpawnPaintingPacket) p;
			Painting painting = new Painting(packet.getEntityId(), packet.getUUID(), packet.getPaintingType(), packet.getDirection(), packet.getPosition());
			bot.nearbyPaintings.add(painting);
			bot.allEntities.add(painting);
		} else if (p instanceof ServerSpawnPlayerPacket) {
			ServerSpawnPlayerPacket packet = (ServerSpawnPlayerPacket) p;
			EntityLocation loc = new EntityLocation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
			Player player = new Player(packet.getEntityId(), packet.getUUID(), loc, packet.getMetadata());
			bot.nearbyPlayers.add(player);
			bot.allEntities.add(player);
		} else if (p instanceof ServerSpawnMobPacket) {
			ServerSpawnMobPacket packet = (ServerSpawnMobPacket) p;
			EntityLocation loc = new EntityLocation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
			Mob mob = new Mob(packet.getEntityId(), packet.getUUID(), packet.getType(), loc, packet.getYaw(), packet.getMotionX(), packet.getMotionY(), packet.getMotionZ(), packet.getMetadata());
			bot.nearbyMobs.add(mob);
			bot.allEntities.add(mob);
		} else if (p instanceof ServerSpawnExpOrbPacket) {
			ServerSpawnExpOrbPacket packet = (ServerSpawnExpOrbPacket) p;
			EntityLocation loc = new EntityLocation(packet.getX(), packet.getY(), packet.getZ());
			Exporb orb = new Exporb(packet.getEntityId(), loc, packet.getExp());
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
						switch (en.getIdentifier()) {
						case EXPERIENCE:
							bot.nerbyXPs.remove(en);
							break;
						case MOB:
							bot.nearbyMobs.remove(en);
							break;
						case OBJECT:
							bot.nearbyObjects.remove(en);
							break;
						case PAINTING:
							bot.nearbyPaintings.remove(en);
							break;
						case PLAYER:
							bot.nearbyPlayers.remove(en);
							break;
						default:
							break;
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
					switch (ent.getIdentifier()) {
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == id) {
								player.getArmor().put(es, newItem);
								break;
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == id) {
								mob.getArmor().put(es, newItem);
								break;
							}
						}
						break;
					default:
						break;
					}
				}
			}
		} else if (p instanceof ServerEntityHeadLookPacket) {
			ServerEntityHeadLookPacket packet = (ServerEntityHeadLookPacket) p;
			int id = packet.getEntityId();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == id) {
					switch (ent.getIdentifier()) {
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == id) {
								player.getLocation().setYaw(packet.getHeadYaw());
								break;
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == id) {
								mob.getLocation().setYaw(packet.getHeadYaw());
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == id) {
								obj.getLocation().setYaw(packet.getHeadYaw());
								break;
							}
						}
						break;
					case EXPERIENCE:
						for (Exporb exp : bot.nerbyXPs) {
							if (exp.getEntityId() == id) {
								exp.getLocation().setYaw(packet.getHeadYaw());
								break;
							}
						}
						break;
					default:
						break;
					}
				}
			}
		} else if (p instanceof ServerEntityMetadataPacket) {
			ServerEntityMetadataPacket packet = (ServerEntityMetadataPacket) p;
			int id = packet.getEntityId();
			EntityMetadata[] data = packet.getMetadata();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == id) {
					switch (ent.getIdentifier()) {
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == id) {
								player.setMetadata(data);
								break;
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == id) {
								mob.setMetadata(data);
								break;
							}
						}
						break;
					default:
						break;
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
					switch (ent.getIdentifier()) {
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.setOnGround(onGround);
								player.getLocation().setX(moveX);
								player.getLocation().setY(moveY);
								player.getLocation().setZ(moveZ);
								break;
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.setOnGround(onGround);
								mob.getLocation().setX(moveX);
								mob.getLocation().setY(moveY);
								mob.getLocation().setZ(moveZ);
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.setOnGround(onGround);
								obj.getLocation().setX(moveX);
								obj.getLocation().setY(moveY);
								obj.getLocation().setZ(moveZ);
								break;
							}
						}
						break;
					case EXPERIENCE:
						for (Exporb exp : bot.nerbyXPs) {
							if (exp.getEntityId() == entityId) {
								exp.getLocation().setX(moveX);
								exp.getLocation().setY(moveY);
								exp.getLocation().setZ(moveZ);
								break;
							}
						}
						break;
					case PAINTING:
						for (Painting painting : bot.nearbyPaintings) {
							if (painting.getEntityId() == entityId) {
								painting.getLocation().setX(moveX);
								painting.getLocation().setY(moveY);
								painting.getLocation().setZ(moveZ);
							}
						}
					default:
						break;
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
					switch (ent.getIdentifier()) {
					case EXPERIENCE:
						for (Exporb exp : bot.nerbyXPs) {
							if (exp.getEntityId() == entityId) {
								exp.getLocation().setX(moveX);
								exp.getLocation().setY(moveY);
								exp.getLocation().setZ(moveZ);
								exp.getLocation().setYaw(yaw);
								exp.getLocation().setPitch(pitch);
								break;
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.getLocation().setX(moveX);
								mob.getLocation().setY(moveY);
								mob.getLocation().setZ(moveZ);
								mob.getLocation().setYaw(yaw);
								mob.getLocation().setPitch(pitch);
								mob.setOnGround(onGround);
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.getLocation().setX(moveX);
								obj.getLocation().setY(moveY);
								obj.getLocation().setZ(moveZ);
								obj.getLocation().setYaw(yaw);
								obj.getLocation().setPitch(pitch);
								obj.setOnGround(onGround);
								break;
							}
						}
						break;
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.getLocation().setX(moveX);
								player.getLocation().setY(moveY);
								player.getLocation().setZ(moveZ);
								player.getLocation().setYaw(yaw);
								player.getLocation().setPitch(pitch);
								player.setOnGround(onGround);
								break;
							}
						}
						break;
					case PAINTING:
						for (Painting painting : bot.nearbyPaintings) {
							if (painting.getEntityId() == entityId) {
								painting.getLocation().setX(moveX);
								painting.getLocation().setY(moveY);
								painting.getLocation().setZ(moveZ);
								painting.getLocation().setYaw(yaw);
								painting.getLocation().setPitch(pitch);
							}
						}
					default:
						break;
					}
				}
			}
		} else if (p instanceof ServerEntityPropertiesPacket) {
			ServerEntityPropertiesPacket packet = (ServerEntityPropertiesPacket) p;
			int entityId = packet.getEntityId();
			List<Attribute> attributes = packet.getAttributes();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					switch (ent.getIdentifier()) {
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.attributes = attributes;
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.attributes = attributes;
								break;
							}
						}
						break;
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.setAttributes(attributes);
								break;
							}
						}
						break;
					default:
						break;
					}
				}
			}
		} else if (p instanceof ServerEntityRemoveEffectPacket) {
			ServerEntityRemoveEffectPacket packet = (ServerEntityRemoveEffectPacket) p;
			int entityId = packet.getEntityId();
			Effect effectToRemove = packet.getEffect();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					switch (ent.getIdentifier()) {
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.effects.remove(effectToRemove);
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.effects.remove(effectToRemove);
								break;
							}
						}
						break;
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.effects.remove(effectToRemove);
								break;
							}
						}
						break;
					default:
						break;
						
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
					switch (ent.getIdentifier()) {
					case EXPERIENCE:
						for (Exporb exp : bot.nerbyXPs) {
							if (exp.getEntityId() == entityId) {
								exp.getLocation().setPitch(pitch);
								exp.getLocation().setYaw(yaw);
								break;
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.getLocation().setPitch(pitch);
								mob.getLocation().setYaw(yaw);
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.getLocation().setPitch(pitch);
								obj.getLocation().setYaw(yaw);
								break;
							}
						}
						break;
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.getLocation().setPitch(pitch);
								player.getLocation().setYaw(yaw);
								break;
							}
						}
						break;
					default:
						break;
					}
				}
			}
		} else if (p instanceof ServerEntityStatusPacket) {
			ServerEntityStatusPacket packet = (ServerEntityStatusPacket) p;
			int entityId = packet.getEntityId();
			EntityStatus status = packet.getStatus();
			for (Entity ent : bot.allEntities) {
				if (ent.getEntityId() == entityId) {
					switch (ent.getIdentifier()) {
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.setStatus(status);
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.setStatus(status);
								break;
							}
						}
						break;
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.setStatus(status);
								break;
							}
						}
						break;
					default:
						break;
					
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
					switch (ent.getIdentifier()) {
					case EXPERIENCE:
						for (Exporb exp : bot.nerbyXPs) {
							if (exp.getEntityId() == entityId) {
								exp.setLocation(new EntityLocation(X, Y, Z, yaw, pitch));
							}
						}
						break;
					case MOB:
						for (Mob mob : bot.nearbyMobs) {
							if (mob.getEntityId() == entityId) {
								mob.setLocation(new EntityLocation(X, Y, Z, yaw, pitch));
								mob.setOnGround(onGround);
								break;
							}
						}
						break;
					case OBJECT:
						for (Object obj : bot.nearbyObjects) {
							if (obj.getEntityId() == entityId) {
								obj.setLocation(new EntityLocation(X, Y, Z, yaw, pitch));
								obj.setOnGround(onGround);
								break;
							}
						}
						break;
					case PAINTING:
						for (Painting obj : bot.nearbyPaintings) {
							if (obj.getEntityId() == entityId) {
								obj.setLocation(new EntityLocation(X, Y, Z, yaw, pitch));
								break;
							}
						}
						break;
					case PLAYER:
						for (Player player : bot.nearbyPlayers) {
							if (player.getEntityId() == entityId) {
								player.setLocation(new EntityLocation(X, Y, Z, yaw, pitch));
								player.setOnGround(onGround);
								break;
							}
						}
						break;
					default:
						break;
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
}
