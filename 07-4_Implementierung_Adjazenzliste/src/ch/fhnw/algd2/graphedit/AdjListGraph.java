package ch.fhnw.algd2.graphedit;

import java.util.*;

public final class AdjListGraph<K> extends AbstractGraph<K> implements
        GraphAlgorithms.TopSort {
	private static class Vertex<K> {
		K data;
		List<Vertex<K>> adjList = new LinkedList<Vertex<K>>();
		int indegree = 0;
		int topoIndegree;

		Vertex(K vertex) {
			data = vertex;
		}

		boolean addEdgeTo(Vertex<K> to) {
		    if (adjList.contains(to)) return false;
		    to.indegree++;
		    return adjList.add(to);
		}

        boolean removeEdgeTo(Vertex<K> to) {
            if (!adjList.contains(to)) return false;
            to.indegree--;
            return adjList.remove(to);
        }
	}


	private Map<K, Vertex<K>> vertices;
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

		// Done Loeschen Sie folgende Zeile und programmieren Sie
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
			// Done Kante loeschen, es muss dabei unterschieden werden, ob der
			// Graph gerichtet ist oder nicht.
			vf.removeEdgeTo(vt);
			if (!this.isDirected())
				vt.removeEdgeTo(vf);
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
		return vertices.size();
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


	//------------------- TopoSort --------------------------
    public void sort() {
        StringBuffer sb = new StringBuffer();
        if( !isDirected() ) {
            sb.append("Graph is undirected. Topological search impossible");
            System.out.println(sb);
            return;
        }

        //1) Für alle Knoten den Eingangsgrad bestimmen
        //2) Alle Knoten mit Eingangsgrad 0 in einem Set sammeln
        LinkedList<Vertex<K>> queue = new LinkedList<>();
        for (Vertex<K> v : vertices.values()) {
            v.topoIndegree = v.indegree;
            if (v.topoIndegree == 0) queue.add(v);
        }

        while (!queue.isEmpty()) {
            Vertex<K> current = queue.removeFirst();
            sb.append(current.data + " ");
            for (Vertex<K> adjacent : current.adjList) {
                adjacent.topoIndegree--;
                if (adjacent.topoIndegree == 0) queue.add(adjacent);
            }
        }

        if (isCyclic())
            sb.replace(0, sb.length(), "Graph is cyclic. Topological search impossible");

        System.out.println(sb);
    }

    private boolean isCyclic() {
	    //Done: implement algorithm to determine cyclic structure.
	    return vertices.values().stream().anyMatch(v -> v.topoIndegree != 0);
	    // Alternative: int counter
	    //  - in while(!queue.isEmpty() counter++;
        // if(counter != vertices.size()) graph is cyclic
    }
}
