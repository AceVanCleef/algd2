package ch.fhnw.algd2.graphedit;

import java.util.*;

public final class AdjListGraph<K> extends AbstractGraph<K> {
	private static class Vertex<K> {
		K data;
		List<Vertex<K>> adjList = new LinkedList<Vertex<K>>();

		Vertex(K vertex) {
			data = vertex;
		}

		boolean addEdgeTo(Vertex<K> to) {
			return (adjList.contains(to)) ? false : adjList.add(to);
		}
	}


	private Map<K, Vertex<K>> vertices;

	private int noOfVertices = 0;
	private int noOfEdges = 0;

	public AdjListGraph() { // default constructor
		this(false);
	}

	public AdjListGraph(boolean directed) {
		super(directed);
		vertices = new HashMap<K, Vertex<K>>();
	}

	public AdjListGraph(AdjListGraph<K> orig) { // copy
		//Deep copy in Java? https://stackoverflow.com/questions/16436591/deep-copy-of-a-generic-type-in-java

		// TODO Loeschen Sie folgende Zeile und programmieren Sie
		// einen Konstruktor, der eine Kopie von orig erstellt.
		this(orig.isDirected());
		//recreate vertices
		for (K key : orig.vertices.keySet()) {
			addVertex(key);
		}
		//recreate edges
		for (K key : orig.vertices.keySet()) {
			List<Vertex<K>> origAdjList = orig.vertices.get(key).adjList;
			Vertex<K> vf = vertices.get(key);
			for (Vertex<K> vt : origAdjList) {
				addEdge(vf.data, vt.data);
			}
		}

	}



	@Override
	public boolean addVertex(K vertex) {
		if (vertex != null && !vertices.containsKey(vertex)) {
			vertices.put(vertex, new Vertex<K>(vertex));
			noOfVertices++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addEdge(K from, K to) {
		Vertex<K> vf = vertices.get(from);
		Vertex<K> vt = vertices.get(to);
		if (vf != null && vt != null && !vf.adjList.contains(vt)) {
			// Done Kante einfuegen, es muss dabei unterschieden werden, ob der
			// Graph gerichtet ist oder nicht.
			vf.addEdgeTo(vt);
			if (!this.isDirected()) {
				vt.addEdgeTo(vf); // == adjList.add(vf)
			}
			noOfEdges++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeEdge(K from, K to) {
		Vertex<K> vf = vertices.get(from);
		Vertex<K> vt = vertices.get(to);
		if (vf != null && vt != null && vf.adjList.contains(vt)) {
			// TODO Kante loeschen, es muss dabei unterschieden werden, ob der
			// Graph gerichtet ist oder nicht.
			vf.adjList.remove(vt);
			if (!this.isDirected())
				vt.adjList.remove(vf);
			noOfEdges--;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getNofVertices() {
		// Done Ersetzen Sie die folgende Zeile durch eine effizientere
		// Implementation
		return noOfVertices;
	}

	@Override
	public int getNofEdges() {
		// Done Ersetzen Sie die folgende Zeile durch eine effizientere
		// Implementation
		return noOfEdges;
	}

	@Override
	public Set<K> getVertices() {
		return new HashSet<K>(vertices.keySet());
	}

	@Override
	public Set<K> getAdjacentVertices(K vertex) {
		Set<K> set = new HashSet<K>();
		// Done Alle data-Objekte, die in den benachbarten
		// Knoten gespeichert sind, in set einf�gen

		Vertex<K> v = vertices.get(vertex);
		for (Vertex<K> to : v.adjList) {
			set.add(to.data);
		}
		return set;
	}

	@Override
	public Object clone() {
		return new AdjListGraph<K>(this);
	}
}
