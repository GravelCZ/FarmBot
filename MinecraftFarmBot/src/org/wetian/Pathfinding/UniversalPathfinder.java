package org.wetian.Pathfinding;

import org.wetian.Pathfinding.Block.BlockAStar;
import org.wetian.Pathfinding.Block.BlockPathSmoother;
import org.wetian.Pathfinding.Path.BlockPath;
import org.wetian.Pathfinding.Path.Path;

import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class UniversalPathfinder {

	Path path;
	
	private EntityLocation start = null;
	private EntityLocation target = null;

	private String diagnose;
	private String failure;

	public UniversalPathfinder(EntityLocation start, EntityLocation target) {
		this.start = fixPathfindingLocation(start);
		this.target = fixPathfindingLocation(target);
	}


	public Path getPath() {
		return path;
	}

	public boolean isPathFound() {
		return path != null;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public String getFailure() {
		return failure;
	}

	public void findPath() {
		useWorldAStar();
	}

	private void useWorldAStar() {
		BlockAStar pathfinder = new BlockAStar(start, target);
		 pathfinder.findPath();
		
		if(!pathfinder.pathFound()) {
			failure = pathfinder.getFailure();
			
			return;
		}
		
		BlockPath blockPath = pathfinder.getPath();
		 BlockPathSmoother smoother = new BlockPathSmoother(blockPath);
		
		smoother.convert();
		
		path = smoother.getSmoothPath();
	}

	public static EntityLocation fixPathfindingLocation(EntityLocation location) {
		location.setY(Math.floor(location.getY()));

		/*String materialName = location.getBlock().getType().name();
		
		if (materialName.contains("SLAB") || materialName.contains("STEP")) {
			location.add(0, 1, 0);
		}*/

		return location;
	}
}
