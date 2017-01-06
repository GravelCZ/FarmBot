package cz.GravelCZLP.MinecraftBot.Utils;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;

public class BlockPosition extends Position {
	
	public BlockPosition(int x, int y, int z) {
		super(x, y, z);
	}

	public BlockPosition up() {
		return new BlockPosition(getX(), (getY() + 1), getZ());
	}
	
	public BlockPosition down() {
		return new BlockPosition(getX(), (getY() - 1), getZ());
	}
}
