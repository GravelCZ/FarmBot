package org.wetian.Pathfinding.Path;

import java.util.List;

public class Path {

	private List<PathWaypoint> pathWaypoints;

	public Path(List<PathWaypoint> pathWaypoints) {
		this.pathWaypoints = pathWaypoints;
	}

	public int getNumberOfWaypoints() {
		return pathWaypoints.size();
	}

	public PathWaypoint getWaypoint(int index) {
		if (index >= pathWaypoints.size() || index < 0) {
			return null;
		}

		return pathWaypoints.get(index);
	}
}
