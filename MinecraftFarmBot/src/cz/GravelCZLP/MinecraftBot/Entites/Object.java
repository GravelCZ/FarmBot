package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.UUID;

import org.spacehq.mc.protocol.data.game.entity.type.object.ObjectData;
import org.spacehq.mc.protocol.data.game.entity.type.object.ObjectType;

public class Object {

	private int entityId;
	private UUID uuid;
	private ObjectType type;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private ObjectData data;
	private double motX;
	private double motY;
	private double motZ;
	public Object(int entityId, UUID uuid, ObjectType type, double x, double y, double z, float pitch, float yaw,
			ObjectData data, double motX, double motY, double motZ) {
		super();
		this.entityId = entityId;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.data = data;
		this.motX = motX;
		this.motY = motY;
		this.motZ = motZ;
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
	public ObjectType getType() {
		return type;
	}
	public void setType(ObjectType type) {
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
	public ObjectData getData() {
		return data;
	}
	public void setData(ObjectData data) {
		this.data = data;
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
}
