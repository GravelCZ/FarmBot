package cz.GravelCZLP.MinecraftBot.World;

import java.util.HashMap;

import org.spacehq.mc.protocol.data.game.chunk.Chunk;
import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.world.WorldType;
import org.spacehq.mc.protocol.data.game.world.block.BlockState;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class World {

	private int dimension;
	
	private boolean hardcore;
	private Difficulty difficulty;
	private WorldType worldType;

	private long age;
	private long time;
	
	private HashMap<ChunkCoordinates, Chunk> chunks = new HashMap<>();
	
	public int getDimension() {
		return dimension;
	}

	public void addChunk(Chunk chunk, ChunkCoordinates coords) {
		chunks.put(coords, chunk);
	}
	
	public void unloadChunk(ChunkCoordinates chunk) {
		chunks.remove(chunk);
	}
	
	public Chunk getChunkAt(Position pos) {
		int chunkZ = pos.getZ() / 16;
		int chunkX = pos.getX() / 16;
		ChunkCoordinates coords = new ChunkCoordinates(chunkX, chunkZ);
		return chunks.get(coords);
	}
	
	public BlockState getBlock(Position pos) {
		ChunkCoordinates coords = new ChunkCoordinates(pos.getX() / 16, pos.getZ() / 16);
		return chunks.get(coords).getBlocks().get(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public BlockState getBlock(EntityLocation loc) {
		Position pos = new Position((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
		ChunkCoordinates coords = new ChunkCoordinates(pos.getX() / 16, pos.getZ() / 16);
		return chunks.get(coords).getBlocks().get(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public void updateBlock(Position pos, BlockState state) {
		ChunkCoordinates coords = new ChunkCoordinates(pos.getX() / 16, pos.getZ() / 16);
		chunks.get(coords).getBlocks().set(pos.getX(), pos.getY(), pos.getZ(), state);
	}
	
	public BlockState setBlock(Position pos, BlockState state) {
		ChunkCoordinates coords = new ChunkCoordinates(pos.getX() / 16, pos.getZ() / 16);
		chunks.get(coords).getBlocks().set(pos.getX(), pos.getY(), pos.getZ(), state);
		return chunks.get(coords).getBlocks().get(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public boolean isBlockLoaded(Position b) {
		int chunkX = b.getX() / 16;
		int chunkZ = b.getZ() / 16;
		ChunkCoordinates coords = new ChunkCoordinates(chunkX, chunkZ);
		return chunks.containsKey(coords);
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
		
	}
	
}
