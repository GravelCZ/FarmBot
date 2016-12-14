package cz.GravelCZLP.MinecraftBot.AI;

import java.util.Iterator;
import java.util.PriorityQueue;

public class PathQuete {

	private final PriorityQueue<PathQuete.Entry> quete = new PriorityQueue<>((e1, e2) -> {
		return Float.compare(e1.priority, e2.priority);
	});
	
	private class Entry {
		private PathPos pos;
		private float priority;
		
		public Entry(PathPos pos, float priority) {
			this.pos = pos;
			this.priority = priority;
		}
	}
	
	public boolean isEmpty() {
		return quete.isEmpty();
	}
	
	public boolean add(PathPos pos, float priority) {
		return quete.add(new Entry(pos, priority));
	}
	public PathPos[] toArray() {
		PathPos[] array = new PathPos[size()];
		Iterator<Entry> itr = quete.iterator();
		
		for (int i = 0; i < size() && itr.hasNext(); i++) {
			array[i] = itr.next().pos;
		}
		return array;
	}
	public int size() {
		return quete.size();
	}
	public void clear() {
		quete.clear();
	}
	public PathPos pool() {
		return quete.poll().pos;
	}
}
