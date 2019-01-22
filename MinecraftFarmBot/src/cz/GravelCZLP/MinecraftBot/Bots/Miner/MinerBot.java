package cz.GravelCZLP.MinecraftBot.Bots.Miner;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.PlayerAction;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Bots.Farmer.FarmerUtils;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.MathHelp;

public class MinerBot extends Bot {

	private ArrayList<Position> blocksToMine = new ArrayList<>();
	private ArrayList<Position> keepEmpty = new ArrayList<>();
	
	public MinerBot(String host, int port, MinecraftProtocol p) {
		super(host, port, p);
	}

	@Override
	public BotType getType() {
		return BotType.MINER;
	}

	@Override
	public void run() {
		super.run();
		Runnable miningrunnable = new Runnable() {
			
			@Override
			public void run() {
				while (getSession().isConnected()) {
					if (!blocksToMine.isEmpty()) {
						Iterator<Position> iter = blocksToMine.iterator();
						while (iter.hasNext()) {
							Position pos = iter.next();
							if (MinerUtils.canReach(getCurrentLoc(), pos, getCurrentWorld())) {
								FarmerUtils.faceBlock(pos, MinerBot.this);
								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								BlockFace bf = MinerUtils.blockFaceCollide(getCurrentLoc().toPosition(), MathHelp.vectorDirection(getCurrentLoc()), null);
								if (bf == null) {
									System.out.println("You fucked up gravel. fix yo shit!");
									bf = BlockFace.UP;
								}
								ClientPlayerActionPacket a = new ClientPlayerActionPacket(PlayerAction.START_DIGGING, pos, BlockFace.UP);
								getSession().send(a);
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
					if (!keepEmpty.isEmpty()) {
						Iterator<Position> iter = keepEmpty.iterator();
						while (iter.hasNext()) {
							Position pos = iter.next();
							if (MinerUtils.canReach(getCurrentLoc(), pos, getCurrentWorld())) {
								FarmerUtils.faceBlock(pos, MinerBot.this);
								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								BlockFace bf = MinerUtils.blockFaceCollide(getCurrentLoc().toPosition(), MathHelp.vectorDirection(getCurrentLoc()), null);
								if (bf == null) {
									System.out.println("You fucked up gravel. fix yo shit!");
									bf = BlockFace.UP;
								}
								ClientPlayerActionPacket a = new ClientPlayerActionPacket(PlayerAction.START_DIGGING, pos, BlockFace.UP);
								getSession().send(a);
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				
			}
		};
		
		Thread t = new Thread(miningrunnable);
		t.setName("MinerBot: " + getName());
		t.start();
	}
	
	public void addBlocksToKeepEmpty(Position pos) {
		keepEmpty.add(pos);
	}
	
	public void addBlocksToKeepEmpty(int x, int y, int z) {
		addBlocksToKeepEmpty(new Position(x, y, z));;
	}
	
	public void addBlockToMine(Position pos) {
		blocksToMine.add(pos);
	}
	
	public void addBlockToMine(int x, int y, int z) {
		addBlockToMine(new Position(x, y, z));;
	}
	
	public void prepareBounding(int startX, int startY, int startZ, int endX, int endY, int endZ) {
		int minX = Math.min(startX, endX);
		int minY = Math.min(startY, endY);
		int minZ = Math.min(startZ, endZ);
		
		int maxX = Math.max(startX, endX);
		int maxY = Math.max(startY, endY);
		int maxZ = Math.max(startZ, endZ);
		
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				for (int z = minZ; z < maxZ; z++) {
					Position pos = new Position(x, y, z);
					BlockState bs = getCurrentWorld().getBlock(pos);
					if (bs.getId() != 0) {
						blocksToMine.add(pos);
					}
				}	
			}	
		}
	}
}
