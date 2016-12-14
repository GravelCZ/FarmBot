package cz.GravelCZLP.MinecraftBot.AI;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.world.block.BlockFace;

public class PathPos extends Position {

	private final boolean jumping;
	
	public PathPos(int x, int y, int z) {
		this (x, y, z, false);
	}

	public PathPos(int x,int y, int z, boolean jumping)  {
		super(x, y, z);
		this.jumping = jumping;
	}
	
	public PathPos(PathPos pos, boolean jumping) {
		this(pos.getX(), pos.getY(), pos.getZ(), jumping);
	}
	
	public boolean isJumping() {
		return jumping;
	}

	public PathPos north() {
		return new PathPos(getX(), getY() ,( getZ() - 1));
	}

	public PathPos east() {
		return new PathPos((getX() + 1), getY() , getZ());
	}

	public PathPos south() {
		return new PathPos(getX(), getY() ,( getZ() + 1));
	}

	public PathPos west() {
		return new PathPos((getX() - 1), getY() , getZ());
	}
	
	public PathPos up() {
		return new PathPos(getX(), (getY() + 1), getZ());
	}
	
	public PathPos down() {
		return new PathPos(getX(), (getY() - 1), getZ());
	}
	public PathPos offset(BlockFace dir) {
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
}
