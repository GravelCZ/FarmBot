package cz.GravelCZLP.MinecraftBot.World;

import java.util.HashMap;

import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class World {

	private int dimension;
	
	private boolean hardcore;
	private Difficulty difficulty;
	private WorldType worldType;

	private long age;
	private long time;
	
	private HashMap<ChunkCoordinates, Column> columns = new HashMap<>();
	private HashMap<ChunkCoordinates, byte[]> biomeData = new HashMap<>();
	private HashMap<ChunkCoordinates, CompoundTag[]> tileEntities = new HashMap<>();
	
	public int getDimension() {
		return dimension;
	}
	
	public Column getColumnAt(Position pos) {
		int chunkZ = (int) (pos.getZ() / 16.0);
		int chunkX = (int) (pos.getX() / 16.0);
		ChunkCoordinates coords = new ChunkCoordinates(chunkX, chunkZ);
		return columns.get(coords);
	}
	
	public void setBiomeData(ChunkCoordinates coords, byte[] data) {
		biomeData.put(coords, data);
	}
	
	public void setTileEntities(ChunkCoordinates coords, CompoundTag[] tileEnts) {
		tileEntities.put(coords, tileEnts);
	}
	
	public byte[] getBiomeData(ChunkCoordinates coords) {
		return biomeData.get(coords);
	}

	public void unloadColumn(ChunkCoordinates coords) {
		columns.remove(coords);
	}
	
	public void addChunkColumn(ChunkCoordinates coords, Column column) {
		columns.put(coords, column);
	}
	
	public CompoundTag[] getTileEntities(ChunkCoordinates coords) {
		return tileEntities.get(coords);
	}
	
	public BlockState getBlock(Position pos) {
		ChunkCoordinates coords = new ChunkCoordinates((int) Math.floor(pos.getX() / 16.0), (int) Math.floor(pos.getZ() / 16.0));
		Column c = columns.get(coords);
		if (c == null) {
			return null;
		}
		int yPos = (int) Math.floor(pos.getY() / 16.0);
		return c.getChunks()[yPos].getBlocks().get(pos.getX() % 16, pos.getY() % 16, pos.getZ() % 16);
	}
	
	public BlockState getBlock(EntityLocation loc) {
		return getBlock(new Position((int)loc.getX(), (int)loc.getY(), (int)loc.getZ()));
	}
	
	public void setBlock(Position pos, BlockState state) {
		ChunkCoordinates coords = new ChunkCoordinates((int) (pos.getX() / 16.0), (int) (pos.getZ() / 16.0));
		int yPos = (int) Math.floor(pos.getY() / 16.0);
		if (!columns.containsKey(coords)) {
			return;
		}
		columns.get(coords).getChunks()[yPos].getBlocks().set(Math.abs(pos.getX() % 16), Math.abs(pos.getY() % 16), Math.abs(pos.getZ() % 16), state);
	}
	
	public boolean isBlockLoaded(Position b) {
		int chunkX = (int) (b.getX() / 16.0);
		int chunkZ = (int) (b.getZ() / 16.0);
		ChunkCoordinates coords = new ChunkCoordinates(chunkX, chunkZ);
		return columns.containsKey(coords);
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public boolean isHardcore() {
		return hardcore;
	}
	
	public void setHardcore(boolean hardcore) {
		this.hardcore = hardcore;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public WorldType getWorldType() {
		return worldType;
	}


	public void setWorldType(WorldType worldType) {
		this.worldType = worldType;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public static class ChunkCoordinates {
		
		private final int chunkX;
		private final int chunkZ;
		
		public ChunkCoordinates(int chunkX, int chunkZ) {
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
		}

		public int getChunkX() {
			return chunkX;
		}

		public int getChunkZ() {
			return chunkZ;
		}
		
		@Override
		public int hashCode() {
			int hash = 7;
			hash = 31 * hash + chunkX;
			hash = 31 * hash + chunkZ;
			return hash;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ChunkCoordinates)) {
				return false;
			}
			ChunkCoordinates coordsObj = (ChunkCoordinates) obj;
			if (coordsObj.getChunkX() == chunkX && coordsObj.getChunkZ() == chunkZ) {
				return true;
			} else {
				return false;
			}
		}
	}
	
}
