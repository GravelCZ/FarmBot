package cz.GravelCZLP.MinecraftBot.World;

public class Border {

	private double radius;
	
	private double oldRadius;
	private double newRadius;
	
	private long speed;
	
	private double centerX;
	private double centerY;
	
	private int portalTeleportBoundary;
	
	private int warningTime;
	
	private int warningBlocks;

	
	
	public Border(double radius, double oldRadius, double newRadius,long speed, double centerX, double centerY,
			int portalTeleportBoundary, int warningTime, int warningBlocks) {
		this.radius = radius;
		this.oldRadius = oldRadius;
		this.speed = speed;
		this.centerX = centerX;
		this.centerY = centerY;
		this.portalTeleportBoundary = portalTeleportBoundary;
		this.warningTime = warningTime;
		this.warningBlocks = warningBlocks;
		this.newRadius = newRadius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getOldRadius() {
		return oldRadius;
	}

	public void setOldRadius(double oldRadius) {
		this.oldRadius = oldRadius;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public int getPortalTeleportBoundary() {
		return portalTeleportBoundary;
	}

	public void setPortalTeleportBoundary(int portalTeleportBoundary) {
		this.portalTeleportBoundary = portalTeleportBoundary;
	}

	public int getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(int warningTime) {
		this.warningTime = warningTime;
	}

	public int getWarningBlocks() {
		return warningBlocks;
	}

	public void setWarningBlocks(int warningBlocks) {
		this.warningBlocks = warningBlocks;
	}

	public double getNewRadius() {
		return newRadius;
	}

	public void setNewRadius(double newRadius) {
		this.newRadius = newRadius;
	}
	
	
}
