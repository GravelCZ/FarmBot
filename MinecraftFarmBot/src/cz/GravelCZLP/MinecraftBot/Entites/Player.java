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

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class Player extends Entity {

    private UUID uuid;
    private EntityMetadata metadata[];
    
    private boolean onGround;
    
    private List<Attribute> attributes = new ArrayList<Attribute>();
    public List<Effect> effects = new ArrayList<Effect>();
    
    private float health;
    private HashMap<EquipmentSlot, ItemStack> armor = new HashMap<>();
    private ItemStack currentItemInhand;
    
    private EntityStatus status;;
    
    private boolean isSleeping;
    
	public Player(int entityId, UUID uuid, EntityLocation loc, EntityMetadata[] metadata) {
		super(entityId);
		this.entityId = entityId;
		this.uuid = uuid;
		super.location = loc;
		this.metadata = metadata;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	public boolean isOnGround() {
		return onGround;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public EntityStatus getStatus() {
		return status;
	}
	public void setStatus(EntityStatus status) {
		this.status = status;
	}
	@Override
	public EntityIdentifier getIdentifier() {
		return EntityIdentifier.PLAYER;
	}
	public void setSleeping(boolean b) {
		this.isSleeping = b;
	}
	public boolean isSleeping() {
		return this.isSleeping;
	}
}
