package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.UUID;

import org.spacehq.mc.protocol.data.game.entity.metadata.EntityMetadata;
import org.spacehq.mc.protocol.data.game.entity.type.MobType;

public class Mob {

	private int entityId;
	private UUID uuid;
	private MobType type;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private float headYaw;
	private double motX;
	private double motY;
	private double motZ;
	private EntityMetadata metadata[];
	public Mob(int entityId, UUID uuid, MobType type, double x, double y, double z, float pitch, float yaw,
			float headYaw, double motX, double motY, double motZ, EntityMetadata[] metadata) {
		super();
		this.entityId = entityId;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.headYaw = headYaw;
		this.motX = motX;
		this.motY = motY;
		this.motZ = motZ;
		this.metadata = metadata;
	}
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public MobType getType() {
		return type;
	}
	public void setType(MobType type) {
		this.type = type;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public float getHeadYaw() {
		return headYaw;
	}
	public void setHeadYaw(float headYaw) {
		this.headYaw = headYaw;
	}
	public double getMotX() {
		return motX;
	}
	public void setMotX(double motX) {
		this.motX = motX;
	}
	public double getMotY() {
		return motY;
	}
	public void setMotY(double motY) {
		this.motY = motY;
	}
	public double getMotZ() {
		return motZ;
	}
	public void setMotZ(double motZ) {
		this.motZ = motZ;
	}
	public EntityMetadata[] getMetadata() {
		return metadata;
	}
	public void setMetadata(EntityMetadata[] metadata) {
		this.metadata = metadata;
	}

}
