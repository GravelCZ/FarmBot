package cz.GravelCZLP.MinecraftBot.Bots.Miner;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;

import cz.GravelCZLP.MinecraftBot.Bots.Guard.GuardUtils;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.Utils.MathHelp;
import cz.GravelCZLP.MinecraftBot.World.AABB;
import cz.GravelCZLP.MinecraftBot.World.Vector3D;
import cz.GravelCZLP.MinecraftBot.World.World;

public class MinerUtils {

	public static boolean canReach(EntityLocation botLoc, Position blockPos, World w) {
		return GuardUtils.distanceSquared(botLoc, new EntityLocation(blockPos, w)) < 6;
	}

	public static BlockFace blockFaceCollide(Position startPos, Vector3D vec, AABB aabb) {
		double cons = Double.MAX_VALUE;
		BlockFace bf = null;
		
		if (vec.y > 0) {
			double b = aabb.getMinY() - startPos.getY();
			double tc = b / vec.y;
			if (tc > 0 && tc < cons) {
				double xCollide = tc * vec.x + startPos.getX();
				double zCollide = tc * vec.z + startPos.getZ();
				if (MathHelp.between(xCollide, aabb.getMinX(), aabb.getMaxX(), 0) &&
						MathHelp.between(zCollide, aabb.getMinZ(), aabb.getMaxZ(), 0)) {
					cons = tc;
					bf = BlockFace.DOWN;
				}
			}
		} else {
			double b = aabb.getMaxY() - startPos.getY();
			double tc = b / vec.y;
			if (tc > 0 && tc < cons) {
				double xCollide = tc * vec.x + startPos.getX();
				double zCollide = tc * vec.z + startPos.getZ();
				if (MathHelp.between(xCollide, aabb.getMinX(), aabb.getMaxX(), 0) &&
						MathHelp.between(zCollide, aabb.getMinZ(), aabb.getMaxZ(), 0)) {
					cons = tc;
					bf = BlockFace.UP;
				}
			}
		}
		
		if (vec.x < 0) {
			double b = aabb.getMaxX() - startPos.getX();
			double tc = b / vec.x;
			if (tc > 0 && tc < cons) {
				double yCollide = tc * vec.y + startPos.getY();
				double zCollide = tc * vec.z + startPos.getZ();
				if (MathHelp.between(yCollide, aabb.getMinY(), aabb.getMaxY(), 0) &&
						MathHelp.between(zCollide, aabb.getMinZ(), aabb.getMaxZ(), 0)) {
					cons = tc;
					bf = BlockFace.EAST;
				}
			}
		} else {
			double b = aabb.getMinX() - startPos.getX();
			double tc = b / vec.x;
			if (tc > 0 && tc < cons) {
				double yCollide = tc * vec.y + startPos.getY();
				double zCollide = tc * vec.z + startPos.getZ();
				if (MathHelp.between(yCollide, aabb.getMinY(), aabb.getMaxY(), 0) &&
						MathHelp.between(zCollide, aabb.getMinZ(), aabb.getMaxZ(), 0)) {
					cons = tc;
					bf = BlockFace.WEST;
				}
			}
		}
		
		if (vec.z > 0) {
			double b = aabb.getMinZ() - startPos.getZ();
			double tc = b / vec.z;
			if (tc > 0 && tc < cons) {
				double xCollide = tc * vec.x + startPos.getX();
				double yCollide = tc * vec.y + startPos.getY();
				if (MathHelp.between(xCollide, aabb.getMinX(), aabb.getMaxX(), 0) &&
						MathHelp.between(yCollide, aabb.getMinY(), aabb.getMaxY(), 0)) {
					cons = tc;
					bf = BlockFace.NORTH;
				}
			}
		} else {
			double b = aabb.getMaxZ() - startPos.getZ();
			double tc = b / vec.z;
			if (tc > 0 && tc < cons) {
				double xCollide = tc * vec.x + startPos.getX();
				double yCollide = tc * vec.y + startPos.getY();
				if (MathHelp.between(xCollide, aabb.getMinX(), aabb.getMaxX(), 0) &&
						MathHelp.between(yCollide, aabb.getMinY(), aabb.getMaxY(), 0)) {
					cons = tc;
					bf = BlockFace.NORTH;
				}
			}
		}
		
		return bf;
	}
	
}
