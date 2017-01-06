package cz.GravelCZLP.MinecraftBot.Bots.Farmer;

import java.util.ArrayList;
import java.util.List;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.world.block.BlockState;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.BlockPosition;

public class FarmerBot extends Bot {

	private BlockPosition firstPositionOfBox;
	private BlockPosition secondPositionOfBox;
	
	private List<BlockState> blocks = new ArrayList<>();
	
	private List<BlockState> farmlands = new ArrayList<>();
	
	public FarmerBot(String host, int port, MinecraftProtocol p) {
		super(host, port, p);
	}

	@Override
	public BotType getType() {
		return BotType.FARMER;
	}

	
	public void setupArrayOfBlocks() {
		if (firstPositionOfBox == null|| secondPositionOfBox == null) {
			throw new IllegalArgumentException("Positions of bot needed to be setted before this method is called");
		}
		for (int x = firstPositionOfBox.getX(); x < secondPositionOfBox.getX(); x++) {
			for (int z = firstPositionOfBox.getZ(); z < secondPositionOfBox.getZ(); z++) {
				for (int y = firstPositionOfBox.getY(); y < secondPositionOfBox.getY(); y++) {
					blocks.add(super.getCurrentWorld().getBlock(new Position(x, y, z)));
				}
			}
		}
		setupFarmLands();
	}
	private void setupFarmLands() {
		for (BlockState state : blocks) {
			if (state.getId() == 60) {
				farmlands.add(state);
			}
		}
	}
}
