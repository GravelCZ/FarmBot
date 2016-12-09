package cz.GravelCZLP.MinecraftBot.Entites;

public class Entity {

	protected int entityId;
	
	public Entity(int entityId) {
		this.entityId = entityId;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
}
