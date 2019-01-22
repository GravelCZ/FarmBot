package org.wetian.Pathfinding.Block;

import java.util.HashSet;
import java.util.Set;

import org.wetian.Pathfinding.Eluvator.MaterialEvaluator;
import org.wetian.Pathfinding.Eluvator.StairEvaluator;
import org.wetian.Pathfinding.Node.BlockPathNode;
import org.wetian.Pathfinding.Node.TransitionType;
import org.wetian.Pathfinding.Path.BlockPath;
import org.wetian.Pathfinding.Utils.MathUtil;
import org.wetian.Pathfinding.Weighted.SortedWeightedNodeList;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.World.Material;

public class BlockAStar {

	BlockPath path;
	
	private static final double CLIMBING_EXPENSE = 2;

	private double heuristicImportance = 1.0;
	private int maxNodeVisits = 500;

	private boolean canUseDiagonalMovement = true;
	private boolean canUseLadders = true;

	private EntityLocation startLocation;
	private EntityLocation endLocation;

	private boolean moveDiagonally = false;
	private boolean useLadders = true;

	protected BlockPathNode endNode;

	private SortedWeightedNodeList<BlockPathNode> unvisitedNodes = new SortedWeightedNodeList<BlockPathNode>(this.maxNodeVisits * 3);
	private Set<BlockPathNode> visitedNodes = new HashSet<BlockPathNode>(this.maxNodeVisits);

	private long pathfindingStartNano;
	private long pathfindingEndNano;

	private String failure;

	public BlockAStar(EntityLocation startLocation, EntityLocation endLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}


	public boolean pathFound() {
		return this.path != null;
	}

	 public BlockPath getPath()
	{
		return this.path;
	}


	public String getFailure() {
		return this.failure;
	}

	private long getNanoDuration() {
		return pathfindingEndNano - pathfindingStartNano;
	}

	public double getMsDuration() {
		return MathUtil.round(getNanoDuration() / 1000d /1000, 2);
	}
	
	public void setHeuristicImportance(double heuristicImportance) {
		this.heuristicImportance = heuristicImportance;
	}

	public void setCanUseDiagonalMovement(boolean canUseDiagonalMovement) {
		this.canUseDiagonalMovement = canUseDiagonalMovement;
	}

	public void setCanUseLadders(boolean canUseLadders) {
		this.canUseLadders = canUseLadders;
	}

	public void findPath() {
		if (pathfindingStartNano == 0) {
			pathfindingStartNano = System.nanoTime();
		}
			
		BlockPathNode startNode = new BlockPathNode(startLocation.toPosition().getX(), startLocation.toPosition().getY(), startLocation.toPosition().getZ());
		 startNode.setParent(null, TransitionType.WALK, 0);

		endNode = new BlockPathNode(endLocation.toPosition().getX(), endLocation.toPosition().getY(), endLocation.toPosition().getZ());

		unvisitedNodes.addSorted(startNode);

		visitNodes();

		if (endNode.getParent() != null || startNode.equals(endNode)) {
			path = new BlockPath(endNode);
		} else {
			retry();
		}

		pathfindingEndNano = System.nanoTime();
	}

	protected void visitNodes() {
		while (true) {
			if (unvisitedNodes.getSize() == 0) {
				failure = "No unvisted nodes left";
				
				break;
			}

			if (visitedNodes.size() >= maxNodeVisits) {
				failure = "Number of nodes visited exceeds maximum";
				
				break;
			}

			BlockPathNode nodeToVisit = unvisitedNodes.getAndRemoveFirst();
			 visitedNodes.add(nodeToVisit);
			 
			if (isTargetReached(nodeToVisit)) {
				endNode = nodeToVisit;
				
				break;
			}

			visitNode(nodeToVisit);
		}
	}

	protected boolean isTargetReached(BlockPathNode nodeToVisit) {
		return nodeToVisit.equals(endNode);
	}


	protected void visitNode(BlockPathNode node) {
		lookForWalkableNodes(node);

		if(useLadders) {
			lookForLadderNodes(node);
		}
	}

	protected void lookForWalkableNodes(BlockPathNode node) {
		for (int dX = -1; dX <= 1; dX++) {
			for (int dZ = -1; dZ <= 1; dZ++) {
				for (int dY = -1; dY <= 1; dY++) {
 					validateNodeOffset(node, dX, dY, dZ);
				}
			}
		}
	}

	protected void validateNodeOffset(BlockPathNode node, int dX, int dY, int dZ) {
		if (dX == 0 && dY == 0 && dZ == 0) {
			return;
		}

		if (!moveDiagonally && dX*dZ != 0) {
			return;
		}

		if (dX*dZ != 0 && dY != 0) {
			return;
		}

		BlockPathNode newNode = new BlockPathNode(node.x + dX, node.y + dY, node.z + dZ);

		if (doesNodeAlreadyExist(newNode)) {
			return;
		}

		if (!isValid(newNode)) {
			return;
		}

		if (dX * dZ != 0 && !isDiagonalMovementPossible(node, dX, dZ)) {
			return;
		}

		if (dY == 1 && !isBlockUnobstructed(node.getLocation(startLocation.getWorld()).add(0, 2, 0))) {
			return;
		}

		if (dY == -1 && !isBlockUnobstructed(newNode.getLocation(startLocation.getWorld()).add(0, 2, 0))) {
			return;
		}
		
		int transitionType = TransitionType.WALK;
		
		if(dY == 1) {
			boolean isStair = StairEvaluator.isStair(node, newNode, startLocation.getWorld());

			if(!isStair) {
				transitionType = TransitionType.JUMP;
			}
		}

		int sumAbs = Math.abs(dX) + Math.abs(dY) + Math.abs(dZ);
		
		double weight = 1;
		
		if (sumAbs == 2) {
			weight = 1.41;
		} else if(sumAbs == 3) {
			weight = 1.73;
		}

		if (transitionType == TransitionType.JUMP) {
			weight += 0.5;
		}

		newNode.setParent(node, transitionType, weight);

		addNode(newNode);
	}

	protected void lookForLadderNodes(BlockPathNode node) {
		EntityLocation feetLocation = node.getLocation(startLocation.getWorld());

		for (int dY = -1; dY <= 1; dY++) {
			EntityLocation location = feetLocation.clone().add(0, dY, 0);
			
			if (location.getBlock().getId() != Material.LADDER.getId()) {//ladder
				continue;
			}

			BlockPathNode newNode = new BlockPathNode(node.x, node.y + dY, node.z);
			 newNode.setParent(node, TransitionType.CLIMB, CLIMBING_EXPENSE);

			if (doesNodeAlreadyExist(newNode)) {
				continue;
			}

			addNode(newNode);
		}
	}


	protected boolean doesNodeAlreadyExist(BlockPathNode node) {
		if (visitedNodes.contains(node)) {
			return true;
		}

		return unvisitedNodes.contains(node);

	}

	protected void addNode(BlockPathNode node) {
		node.setHeuristicWeight(getHeuristicWeight(node) * heuristicImportance);
		
		unvisitedNodes.addSorted(node);
	}


	protected boolean isValid(BlockPathNode node) {
		return canStandAt(node.getLocation(startLocation.getWorld()));
	}

	protected boolean isDiagonalMovementPossible(BlockPathNode node, int dX, int dZ) {
		if (!isUnobstructed(node.getLocation(startLocation.getWorld()).clone().add(dX, 0, 0))) {
			return false;
		}

		return isUnobstructed(node.getLocation(startLocation.getWorld()).clone().add(0, 0, dZ));
	}

	protected boolean canStandAt(EntityLocation feetLocation) {
		if (!isUnobstructed(feetLocation)) {
			return false;
		}

		return MaterialEvaluator.canStandOn(feetLocation.getBlock().getId());
	}

	protected boolean isUnobstructed(EntityLocation feetLocation) {
		if (!isBlockUnobstructed(feetLocation)) {
			return false;
		}

		return isBlockUnobstructed(feetLocation.clone().add(0, 1, 0));
	}


	protected boolean isBlockUnobstructed(EntityLocation location) {
		return MaterialEvaluator.canStandIn(location.getBlock().getId());
	}

	protected double getHeuristicWeight(BlockPathNode node) {
		return getEuclideanDistance(node);
	}

	protected double getManhattanDistance(BlockPathNode node) {
		return Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y) + Math.abs(node.z - endNode.z);
	}	

	protected double getEuclideanDistance(BlockPathNode node) {
		int dX = endNode.x - node.x;
		int dY = endNode.y - node.y;
		int dZ = endNode.z - node.z;

		return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
	}


	protected void retry() {
		lookForReason: {
			if (canUseDiagonalMovement && !moveDiagonally) {
				moveDiagonally = true;
				
				break lookForReason;
			}

			if (canUseLadders && !useLadders) {
				useLadders = true;
				
				break lookForReason;
			}

			return;
		}

		reset();
		findPath();
	}

	protected void reset() {
		endNode = null;

		unvisitedNodes.clear();
		visitedNodes.clear();

		path = null;
		failure = null;
	}
}
