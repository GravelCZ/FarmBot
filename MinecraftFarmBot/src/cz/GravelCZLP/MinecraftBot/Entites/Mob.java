package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.github.steveice10.mc.protocol.data.game.entity.Effect;
import com.github.steveice10.mc.protocol.data.game.entity.EntityStatus;
import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.Attribute;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;

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
		return EntityIdentifier.MOB;
	}
	public boolean isDangerous() {
		switch(type) {
		case BAT:
			return false;
		case BLAZE:
			return true;
		case CAVE_SPIDER:
			return true;
		case CHICKEN:
			return false;
		case COW:
			return false;
		case CREEPER:
			return true;
		case DONKEY:
			return false;
		case ELDER_GUARDIAN:
			return true;
		case ENDERMAN:
			return true;
		case ENDERMITE:
			return true;
		case ENDER_DRAGON:
			return true;
		case EVOCATION_ILLAGER:
			return true;
		case GHAST:
			return true;
		case GIANT_ZOMBIE:
			return true;
		case GUARDIAN:
			return true;
		case HORSE:
			return false;
		case HUSK:
			return true;
		case IRON_GOLEM:
			return false;
		case LLAMA:
			return false;
		case MAGMA_CUBE:
			return true;
		case MOOSHROOM:
			return false;
		case MULE:
			return false;
		case OCELOT:
			return false;
		case PIG:
			return false;
		case POLAR_BEAR:
			return false;
		case RABBIT:
			return false;
		case SHEEP:
			return false;
		case SHULKER:
			return true;
		case SILVERFISH:
			return true;
		case SKELETON:
			return true;
		case SKELETON_HORSE:
			return true;
		case SLIME:
			return true;
		case SNOWMAN:
			return false;
		case SPIDER:
			return true;
		case SQUID:
			return false;
		case STRAY:
			return true;
		case VEX:
			return true;
		case VILLAGER:
			return false;
		case VINDICATION_ILLAGER:
			return true;
		case WITCH:
			return true;
		case WITHER:
			return true;
		case WITHER_SKELETON:
			return true;
		case WOLF:
			return false;
		case ZOMBIE:
			return true;
		case ZOMBIE_HORSE:
			return true;
		case ZOMBIE_PIGMAN:
			return true;
		case ZOMBIE_VILLAGER:
			return true;
		default:
			return true;
		}
	}
}
