package cz.GravelCZLP.MinecraftBot.Utils;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;

import cz.GravelCZLP.MinecraftBot.World.World;

public class EntityLocation implements Cloneable {

	private double x;
	private double y;
	private double z;
	
	private float yaw;
	private float pitch;
	
	private World world;
	
	public EntityLocation(Position pos, World w) {
		double x = (double) pos.getX();
		double y = (double) pos.getY();
		double z = (double) pos.getZ();
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = w;
	}
	
	public EntityLocation(double x, double y, double z, World w) {
		this(x,y,z,0,0,w);
	}
	
	public EntityLocation(double x, double y, double z, float yaw, float pitch, World w) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.yaw = yaw;
		this.pitch = pitch;
		
		this.world = w;
	}

	public EntityLocation add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public EntityLocation add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
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
	
	public World getWorld() {
		return world;
	}
	
	public EntityLocation north() {
		return new EntityLocation(getX(), getY() ,( getZ() - 1), world);
	}

	public EntityLocation east() {
		return new EntityLocation((getX() + 1), getY() , getZ(), world);
	}

	public EntityLocation south() {
		return new EntityLocation(getX(), getY() ,( getZ() + 1), world);
	}

	public EntityLocation west() {
		return new EntityLocation((getX() - 1), getY() , getZ(), world);
	}
	
	public EntityLocation up() {
		return new EntityLocation(getX(), (getY() + 1), getZ(), world);
	}
	
	public EntityLocation down() {
		return new EntityLocation(getX(), (getY() - 1), getZ(), world);
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
		return new EntityLocation(x, y, z, yaw, pitch, world);
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

	public BlockState getBlock() {
		return world.getBlock(this);
	}
}
