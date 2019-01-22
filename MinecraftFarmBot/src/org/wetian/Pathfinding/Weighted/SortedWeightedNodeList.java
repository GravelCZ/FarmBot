package org.wetian.Pathfinding.Weighted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SortedWeightedNodeList<T extends WeightedNode> {

	private List<T> nodes;
	private Set<T> nodesContainsTester;

	public SortedWeightedNodeList(int length) {
		this.nodes = new ArrayList<T>(length);
		
		this.nodesContainsTester = new HashSet<T>(length);
	}

	public T getAndRemoveFirst() {
		if (nodes.isEmpty()) {
			return null;
		}

		T firstNode = this.nodes.remove(0);
		 nodesContainsTester.remove(firstNode);

		return firstNode;
	}

	public boolean contains(T node) {
		return nodesContainsTester.contains(node);
	}

	public int getSize() {
		return nodes.size();
	}

	private double getValueToCompare(T node) {
		return node.getFValue();
	}

	@Deprecated 
	public List<T> getNodes() {
		return nodes;
	}

	public void addSorted(T node) {
		nodesContainsTester.add(node);
		
		insertIntoList(node, 0, nodes.size());
	}

	private void insertIntoList(T node, int lowerBound, int upperBound) {
		if (lowerBound == upperBound) {
			nodes.add(lowerBound, node);
			
			return;
		}

		double nodeValue = getValueToCompare(node);

		int dividingIndex = (lowerBound + upperBound) / 2;
		 T dividingNode = nodes.get(dividingIndex);
		 
		double dividingValue = getValueToCompare(dividingNode);

		if (nodeValue > dividingValue) {
			insertIntoList(node, dividingIndex + 1, upperBound);
		} else {
			insertIntoList(node, lowerBound, dividingIndex);
		}
	}

	public void clear() {
		nodes.clear();
		nodesContainsTester.clear();
	}


	public void sort() {
		Collections.sort(nodes, (n1, n2) -> getValueToCompare(n1) > getValueToCompare(n2) ? 1 : -1);
	}
}
