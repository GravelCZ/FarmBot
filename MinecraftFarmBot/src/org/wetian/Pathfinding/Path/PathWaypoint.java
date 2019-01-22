package org.wetian.Pathfinding.Path;

import cz.GravelCZLP.MinecraftBot.World.Vector3D;

public class PathWaypoint {

	Vector3D position;
	
	private int transitionType;

	public PathWaypoint(Vector3D position, int transitionType) {
		this.position = position;
		this.transitionType = transitionType;
	}

	public Vector3D getPosition() {
		return position;
	}

	public int getTransitionType() {
		return transitionType;
	}
}
