package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.spacehq.mc.protocol.data.game.entity.Effect;
import org.spacehq.mc.protocol.data.game.entity.EntityStatus;
import org.spacehq.mc.protocol.data.game.entity.EquipmentSlot;
import org.spacehq.mc.protocol.data.game.entity.attribute.Attribute;
import org.spacehq.mc.protocol.data.game.entity.metadata.EntityMetadata;
import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.data.game.entity.type.MobType;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class Mob extends Entity {

	private UUID uuid;
	private MobType type;
	private float yaw;
	private double motX;
	private double motY;
	private double motZ;
	private EntityMetadata metadata[];
	
	public List<Attribute> attributes = new ArrayList<Attribute>();
	public List<Effect> effects = new ArrayList<Effect>();
	
	private boolean onGround;
	
	private HashMap<EquipmentSlot, ItemStack> armor = new HashMap<>();
	private float health;
	private ItemStack currentItemInHand;
	
	private EntityStatus status;
	
	public Mob(int entityId, UUID uuid, MobType type, EntityLocation loc, float yaw,double motX, double motY, double motZ, EntityMetadata[] metadata) {
		super(entityId);
		this.entityId = entityId;
		this.uuid = uuid;
		this.type = type;
		this.yaw = yaw;
		this.motX = motX;
		this.motY = motY;
		this.motZ = motZ;
		this.metadata = metadata;
		super.location = loc;
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
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
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
	public float getHealth() {
		return health;
	}
	public void setHealth(float health) {
		this.health = health;
	}
	public ItemStack getCurrentItemInHand() {
		return currentItemInHand;
	}
	public void setCurrentItemInHand(ItemStack currentItemInHand) {
		this.currentItemInHand = currentItemInHand;
	}
	public HashMap<EquipmentSlot, ItemStack> getArmor() {
		return armor;
	}
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	public boolean isOnGround() {
		return onGround;
	}
	public EntityStatus getStatus() {
		return status;
	}
	public void setStatus(EntityStatus status) {
		this.status = status;
	}
	@Override
	public EntityIdentifier getIdentifier() {
		return EntityIdentifier.MOB
	}
}
