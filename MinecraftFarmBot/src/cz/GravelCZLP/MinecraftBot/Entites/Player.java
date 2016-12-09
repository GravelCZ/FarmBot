package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.HashMap;
import java.util.UUID;

import org.spacehq.mc.protocol.data.game.entity.EquipmentSlot;
import org.spacehq.mc.protocol.data.game.entity.metadata.EntityMetadata;
import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;

public class Player extends Entity {

    private UUID uuid;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private EntityMetadata metadata[];
    
    private float health;
    private HashMap<EquipmentSlot, ItemStack> armor = new HashMap<>();
    private ItemStack currentItemInhand;
    
	public Player(int entityId, UUID uuid, double x, double y, double z, float yaw, float pitch,
			EntityMetadata[] metadata) {
		super(entityId);
		this.entityId = entityId;
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
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
	public float getHealth() {
		return health;
	}
	public void setHealth(float health) {
		this.health = health;
	}
	public ItemStack getCurrentItemInhand() {
		return currentItemInhand;
	}
	public void setCurrentItemInhand(ItemStack currentItemInhand) {
		this.currentItemInhand = currentItemInhand;
	}
	public HashMap<EquipmentSlot, ItemStack> getArmor() {
		return armor;
	}
}
