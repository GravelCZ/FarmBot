package cz.GravelCZLP.MinecraftBot.Bots.Farmer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.BlockPosition;

public class FarmerBot extends Bot {

	private BlockPosition firstPositionOfBox;
	private BlockPosition secondPositionOfBox;
	
	private HashMap<BlockState, BlockPosition> blocks = new HashMap<>();
	private HashMap<BlockState, BlockPosition> farmlands = new HashMap<>();
	private List<BlockPosition> placeSeeds = new LinkedList<>();
	
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
					blocks.put(getCurrentWorld().getBlock(new Position(x, y, z)), new BlockPosition(x, y, z));
				}
			}
		}
		setupFarmLands();
	}
	private void setupFarmLands() {
		for (Entry<BlockState, BlockPosition> entry : blocks.entrySet()) {
			if (entry.getKey().getId() == 60) {
				farmlands.put(entry.getKey(), entry.getValue());
			}
		}
	}
}
