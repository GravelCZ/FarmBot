package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.steveice10.mc.protocol.data.game.entity.Effect;
import com.github.steveice10.mc.protocol.data.game.entity.EntityStatus;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.Attribute;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectData;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class Object extends Entity{

	private UUID uuid;
	private ObjectType type;
	private ObjectData data;
	private double motX;
	private double motY;
	private double motZ;
	private boolean onGround;
	public List<Attribute> attributes = new ArrayList<Attribute>();
	public List<Effect> effects = new ArrayList<Effect>();
	
	private EntityStatus status;
	
	public Object(int entityId, UUID uuid, ObjectType type, EntityLocation loc,
			ObjectData data, double motX, double motY, double motZ) {
		super(entityId);
		this.entityId = entityId;
		this.uuid = uuid;
		this.type = type;
		super.location = loc;
		this.data = data;
		this.motX = motX;
		this.motY = motY;
		this.motZ = motZ;
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
		return EntityIdentifier.OBJECT;
	}
	
}
