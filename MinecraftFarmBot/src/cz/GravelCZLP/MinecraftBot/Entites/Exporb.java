package cz.GravelCZLP.MinecraftBot.Entites;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class Exporb extends Entity {

	private int xp;
	
	public Exporb(int entityId, EntityLocation loc, int xp) {
		super(entityId);
		this.entityId = entityId;
		super.location = loc;
		this.xp = xp;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	@Override
	public EntityIdentifier getIdentifier() {
		return EntityIdentifier.EXPERIENCE;
	}
}
