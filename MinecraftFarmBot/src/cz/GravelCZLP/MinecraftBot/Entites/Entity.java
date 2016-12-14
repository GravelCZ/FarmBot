package cz.GravelCZLP.MinecraftBot.Entites;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public abstract class Entity {

	protected int entityId;
	
	protected EntityLocation location;
	
	public Entity(int entityId) {
		this.entityId = entityId;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	public EntityLocation getLocation() {
		return location;
	}
	
	public void setLocation(EntityLocation newLoc) {
		this.location = newLoc;
	}
	
	public abstract EntityIdentifier getIdentifier();
}
