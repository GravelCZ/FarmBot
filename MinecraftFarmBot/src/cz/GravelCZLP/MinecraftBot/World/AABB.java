package cz.GravelCZLP.MinecraftBot.World;

public class AABB {

	private double minX, minY, minZ, maxX, maxY, maxZ;
	
	public AABB(Vector3D min, Vector3D max) {
		this.minX = min.getX();
		this.minY = min.getY();
		this.minZ = min.getZ();
		
		this.maxX = max.getX();
		this.maxY = max.getY();
		this.maxZ = max.getZ();
	}
	
	public AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		
		this.maxX = maxZ;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}
	
	public AABB grow(double x, double y, double z) {
		this.minX *= x;
		this.minY *= y;
		this.minZ *= z;
		
		this.maxX *= x;
		this.maxY *= y;
		this.maxZ *= z;
		
		return this;
	}
	
	public boolean isInAABB(Vector3D vec) {
		return (vec.x >= minX && vec.x <= maxX) &&
				(vec.y >= minY && vec.y <= maxY) &&
				(vec.z >= minZ && vec.y <= maxZ);
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMaxZ() {
		return maxZ;
	}
	
}
