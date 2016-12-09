package cz.GravelCZLP.MinecraftBot.Entites;

public class Exporb extends Entity {

	private double x;
	private double y;
	private double z;
	private int xp;
	
	public Exporb(int entityId, double x, double y, double z, int xp) {
		super(entityId);
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xp = xp;
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
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
}
