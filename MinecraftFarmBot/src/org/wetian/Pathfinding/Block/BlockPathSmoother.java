package org.wetian.Pathfinding.Block;

import java.util.ArrayList;
import java.util.List;

import org.wetian.Pathfinding.Node.BlockPathNode;
import org.wetian.Pathfinding.Path.BlockPath;
import org.wetian.Pathfinding.Path.Path;
import org.wetian.Pathfinding.Path.PathWaypoint;

import cz.GravelCZLP.MinecraftBot.World.Vector3D;

public class BlockPathSmoother {

	BlockPath blockPath;

	private Path smoothPath;
	
	public BlockPathSmoother(BlockPath blockPath) {
		this.blockPath = blockPath;
	}

	public Path getSmoothPath() {
		return smoothPath;
	}

	public void convert() {
		List<PathWaypoint> pathWaypoints = new ArrayList<PathWaypoint>();
		
		for (BlockPathNode blockPathNode : this.blockPath.getNodes()) {
			pathWaypoints.add(new PathWaypoint(new Vector3D(blockPathNode.x + 0.5, blockPathNode.y, blockPathNode.z + 0.5), blockPathNode.getTransitionType()));
		}

		smoothPath = new Path(pathWaypoints);
	}
}
