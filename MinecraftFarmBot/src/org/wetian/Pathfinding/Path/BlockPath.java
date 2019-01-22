package org.wetian.Pathfinding.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wetian.Pathfinding.Node.BlockPathNode;

public class BlockPath {

	private List<BlockPathNode> nodes = new ArrayList<BlockPathNode>();

	public BlockPath(BlockPathNode endNode) {
		generatePath(endNode);
	}

	private void generatePath(BlockPathNode endNode) {
		BlockPathNode currentNode = endNode;
		
		while (currentNode != null) {
			nodes.add(currentNode);
			
			currentNode = currentNode.getParent();
		}

		Collections.reverse(nodes);
	}

	public List<BlockPathNode> getNodes() {
		return nodes;
	}
}
