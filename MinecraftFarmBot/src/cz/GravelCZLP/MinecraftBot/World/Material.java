package cz.GravelCZLP.MinecraftBot.World;

public enum Material { //TODO: boolean isBlock, AABB block AxisAlignedBlockBounding coords.

	AIR(0),
	STONE(1),
	GRANITE(1,1),
	POLISHED_GRANITE(1,2),
	DIORITE(1,3),
	POLISHED_DIORITE(1,4),
	ANDESITE(1,5),
	POLISHED_ANDESITE(1,6),
	GRASS_BLOCK(2),
	DIRT(3),
	CORASE_DIRT(3,1),
	PODZOL(3,2),
	COBLESTONE(4),
	OAK_WOOD_PLANK(5),
	OAK_SAPLING(6),
	OAK_WOOD(16),
	LADDER(65);
	
	private int id;
	private int subId = 0;
	
	private Material(int id, int subId) {
		this.id = id;
		this.subId = subId;
	}
	
	Material(int id) {
		this.id = id;
	}
	
	public int getSubId() {
		return subId;
	}
	
	public int getId() {
		return id;
	}
	
}
