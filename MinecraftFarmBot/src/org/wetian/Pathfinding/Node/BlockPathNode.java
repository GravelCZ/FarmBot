package org.wetian.Pathfinding.Node;

import org.wetian.Pathfinding.Weighted.WeightedNode;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.World.World;

public class BlockPathNode implements WeightedNode {

	BlockPathNode parent;
	
	public final int x;
	public final int y;
	public final int z;

	private int transitionType = 0;

	private double weightFromParent;
	private double heuristicWeight;

	public BlockPathNode(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override 
	public boolean equals(Object other) {
		if(!(other instanceof BlockPathNode)) {
			return false;
		}

		BlockPathNode o = (BlockPathNode) other;

		return o.x == x && o.y == y && o.z == z;
	}

	public BlockPathNode getParent() {
		return parent;
	}

	public int getTransitionType() {
		return transitionType;
	}


	@Override 
	public double getGValue() {
		if (parent == null) {
			return weightFromParent;
		}
			
		return parent.getGValue() + weightFromParent;
	}

	@Override 
	public double getHValue() {
		return heuristicWeight;
	}

	@Override 
	public double getFValue() {
		return getGValue() + getHValue();
	}

	public EntityLocation getLocation(World w) {
		return new EntityLocation(x, y, z, w);
	}

	public void setParent(BlockPathNode parent, int transitionType, double additionalWeight) {
		this.parent = parent;
		this.transitionType = transitionType;
		this.weightFromParent = additionalWeight;
	}

	public void setHeuristicWeight(double heuristicWeight) {
		this.heuristicWeight = heuristicWeight;
	}
}
