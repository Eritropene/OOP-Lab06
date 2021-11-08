package it.unibo.oop.lab06.generics1;

import java.util.*;


public class GraphImpl<N> implements Graph<N> {

	Map<N,LinkedList<N>> nodes;
	
	public GraphImpl() {
		nodes = new HashMap<>();
	}

	@Override
	public void addNode(N node) {
		nodes.put(node, new LinkedList<N>());
	}

	@Override
	public void addEdge(N source, N target) {
		if (source == null || target == null) {
			return;
		}
		Map.Entry<N, LinkedList<N>> hasSource = null;
		Map.Entry<N, LinkedList<N>> hasTarget = null;
		for (Map.Entry<N, LinkedList<N>> d : nodes.entrySet()) {
			if (d.getKey() == source) {
				hasSource = d;
			}
			if (d.getKey() == target) {
				hasTarget = d;
			}
		}
		if (hasSource != null && hasTarget != null) {
			hasSource.getValue().add(hasTarget.getKey());
		}
	}

	@Override
	public Set<N> nodeSet() {
		Set<N> ret = new HashSet<N>();
		for (Map.Entry<N, LinkedList<N>> node : nodes.entrySet()) {
			ret.add(node.getKey());
		}
		return ret;
	}

	@Override
	public Set<N> linkedNodes(N node) {
		Set<N> ret = new HashSet<N>();
		for (Map.Entry<N, LinkedList<N>> n : nodes.entrySet()) {
			if (n.getKey() == node) {
				for (N elem : n.getValue()) {
					ret.add(elem);
				}
				return ret;
			}
		}
		return null;
	}

	@Override
	public List<N> getPath(N source, N target) {
		Map<N, Integer> dist = new HashMap<>();
		for (Map.Entry<N, LinkedList<N>> n : nodes.entrySet()) {
			dist.put(n.getKey(), -1);
		}
		dist.replace(source, 1);
		Map<N, N> parent = new HashMap<>();
		for (Map.Entry<N, LinkedList<N>> n : nodes.entrySet()) {
			parent.put(n.getKey(), null);
		}
		Queue<N> q = new PriorityQueue<>();
		q.add(source);
		
		while (!q.isEmpty()) {
			N temp = q.poll();
			for (N linked : nodes.get(temp)) {
				if (dist.get(linked) == -1) {
					dist.replace(linked, 0);
					parent.replace(linked, temp); //set the parent
					q.add(linked);
					if (linked == target) {
						q.clear();
						break;
					}
				}
			}
		}
		List<N> list = new LinkedList<>();
		N temp = target;
		while (temp != source) {
			list.add(0, temp);
			temp = parent.get(temp);
		}
		list.add(0, source);
		return list;
	}

}
