package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.UUID;

import org.spacehq.mc.protocol.data.game.entity.metadata.EntityMetadata;
import org.spacehq.mc.protocol.data.game.entity.type.GlobalEntityType;

public class Player extends Entity {

	private UUID uuid;
	private float yaw;
	private float pitch;
	private EntityMetadata metadata[];
	
	public Player(int id, GlobalEntityType type, double x, double y, double z, UUID uuid, float yaw, float pitch, EntityMetadata[] metadata) {
		super(id, type, x, y, z);
		this.uuid = uuid;
		this.yaw = yaw;
		this.pitch = pitch;
		this.metadata = metadata;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public EntityMetadata[] getMetadata() {
		return metadata;
	}

	public void setMetadata(EntityMetadata[] metadata) {
		this.metadata = metadata;
	}
}
