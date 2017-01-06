package cz.GravelCZLP.MinecraftBot.Utils;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.world.block.BlockFace;

public class EntityLocation implements Cloneable {

	private double x;
	private double y;
	private double z;
	
	private float yaw;
	private float pitch;
	
	public EntityLocation(Position pos) {
		double x = (double) pos.getX();
		double y = (double) pos.getY();
		double z = (double) pos.getZ();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
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
	public EntityLocation north() {
		return new EntityLocation(getX(), getY() ,( getZ() - 1));
	}

	public EntityLocation east() {
		return new EntityLocation((getX() + 1), getY() , getZ());
	}

	public EntityLocation south() {
		return new EntityLocation(getX(), getY() ,( getZ() + 1));
	}

	public EntityLocation west() {
		return new EntityLocation((getX() - 1), getY() , getZ());
	}
	
	public EntityLocation up() {
		return new EntityLocation(getX(), (getY() + 1), getZ());
	}
	
	public EntityLocation down() {
		return new EntityLocation(getX(), (getY() - 1), getZ());
	}
	public EntityLocation offset(BlockFace dir) {
		switch (dir) {
		case DOWN:
			return down();
		case EAST:
			return east();
		case NORTH:
			return north();
		case SOUTH:
			return south();
		case UP:
			return up();
		case WEST:
			return west();
		default:
			return null;
		}
	}
	
	@Override
	public EntityLocation clone() {
		try {
			return (EntityLocation) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Position toPosition() {
		return new Position((int)x, (int)y, (int)z);
	}
	
	@Override
	public String toString() {
		return new 
				StringBuffer()
				.append("EntityLocation[X=")
				.append(x)
				.append(",Y=")
				.append(y)
				.append(",Z=")
				.append(z)
				.append(",Yaw=")
				.append(yaw)
				.append(",Pitch=")
				.append(pitch)
				.toString();
	}
}
