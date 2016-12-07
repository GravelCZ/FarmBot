package cz.GravelCZLP.FarmBot.Core;

public class EntityLocation {

	private double x;
	private double y;
	private double z;
	
	private float yaw;
	private float pitch;
	
	public EntityLocation(double x, double y, double z) {
		this(x,y,z,0,0);
	}
	
	public EntityLocation(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.yaw = yaw;
		this.pitch = pitch;
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
}
