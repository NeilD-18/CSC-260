package edu.union.adt.graph;

import java.util.*; 


/**
 *  Graph implementation 
 *  @author Neil Daterao
 */


public class GraphImpl<V> implements Graph<V> {
   
    private Map<V, LinkedList<V>> graph;  
    
    /**
     * Create an empty graph.
     */
   
    public GraphImpl() 
    {
        graph = new HashMap<>(); 

    }

    /**
     * @return the number of vertices in the graph.
     */
    @Override
     public int numVertices()
    {
        return graph.keySet().size();
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
     public int numEdges()
    {
        int edgesCount = 0;
        for (V vertex : graph.keySet()) { 
            edgesCount += graph.get(vertex).size();
        }
        return edgesCount; 
    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
    @Override
    public int degree(V vertex)
    {
        if (!contains(vertex)) { throw new RuntimeException("Vertex Not In Graph"); }
        
        return graph.get(vertex).size();
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
    @Override
    public void addEdge(V from, V to)
    {
        if (!graph.containsKey(from)) { addVertex(from); }
        if (!graph.containsKey(to)) { addVertex(to); }
        if (!graph.get(from).contains(to)) { graph.get(from).add(to); }

       
    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    @Override
    public void addVertex(V vertex)
    {
        if (!graph.containsKey(vertex)) { graph.put(vertex, new LinkedList<V>()); } 
        
    }


    /**
     * @return the an iterable collection for the set of vertices of
     * the graph.
     */
    @Override
    public Iterable<V> getVertices()
    {
        return graph.keySet(); 
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    @Override
    public Iterable<V> adjacentTo(V from)
    {
        if (!graph.containsKey(from)) { return Collections.emptyList();  }

        return graph.get(from); 
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    @Override
    public boolean contains(V vertex)
    {
        return graph.containsKey(vertex); 
    }

    /**
     * Tells whether an edge exists in the graph.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
    @Override
    public boolean hasEdge(V from, V to)
    {
        if (contains(from)) { return graph.get(from).contains(to); } 
        else { return false; } 
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a list of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A: A, B
     * B:
     * C: A, B
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
     * adjacent vertices.
     *
     * Note: there are no extraneous spaces in the output.  So, if we
     * replace each space with '*', the above representation would be:
     *
     * <PRE>
     * A:*A,*B
     * B:
     * C:*A,*B
     * </PRE>
     *
     * @return the string representation of the graph
     */
    @Override
    public String toString()
    {
        StringBuilder stringRepresentation = new StringBuilder(); 
        for (V vertex : graph.keySet()) { 
            
            stringRepresentation.append(vertex.toString() + ":");
            
            for (V edge : graph.get(vertex)) { 
                stringRepresentation.append(" " + edge.toString() + ",");
            }
            stringRepresentation.append("\n");
        }
        

        return stringRepresentation.toString(); 
    }

    /**
     * Checks if two graph objects are equal. Checks if structure of graphs is identical. 
     * 
     * @param otherObj object you are checking is equal
     * @return true iff graphs are equal. Checks if graphs habe the same vertices and edges. 
     */
    @Override
    public boolean equals(Object otherObj) { 
        if (this == otherObj) { return true; } 
        if (otherObj == null || getClass() != otherObj.getClass()) { return false; }

        Graph otherGraph = (Graph) otherObj; 
        
        if (this.getVertices().equals(otherGraph.getVertices())) { 
            for (V vertex: this.getVertices()) { 
                if (!this.adjacentTo(vertex).equals(otherGraph.adjacentTo(vertex))) { return false; }
            }
            return true; 
        }
        else { return false; }
        
        //return toString().equals(otherObj.toString()); -> Easy way
   
   
    }

    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    @Override
    public boolean isEmpty() 
    {
        return (this.numVertices() == 0 && this.numEdges() == 0);
    }
    

      /**
     * Removes a vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    @Override
    public void removeVertex(V toRemove)
    {
        graph.remove(toRemove); 

        for (V vertex : getVertices()) { 
           Iterable<V> adjacencyList = adjacentTo(vertex); 
           for (V adjVertex : adjacencyList) { 
               if (adjVertex.equals(toRemove)) { graph.get(vertex).remove(toRemove); }
           }
        }
    }

    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then:
     * <ul>
     * <li> numEdges = numEdges' - 1
     * <li> to is no longer in adjacentTo(from)
     * </ul>
     *
     * @param from the source vertex for the edge
     * @param to the target vertex for the edge
     */
    @Override
    public void removeEdge(V from, V to)
    {
        if (hasEdge(from, to)) { graph.get(from).remove(to); }
        
    }

    /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff A and B are in the
     * graph and there exists a sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     *
     * It therefore follows that if vertex A is in the graph, there
     * is a path from A to A.
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    @Override
    public boolean hasPath(V from, V to)
    {   
        Set<V> visited = new HashSet<>(); 
        return hasPathDFS(from, to, visited); 
    }


    /**
     * Private helper method that uses DFS to search graph structure
     */
    private boolean hasPathDFS(V current, V to, Set<V> visited) {
        if (current.equals(to)) { return true; }
        
        visited.add(current);

        for (V neighbor : adjacentTo(current)) { 
            if (!visited.contains(neighbor)) { 
                if (hasPathDFS(neighbor, to, visited)) { 
                    return true; 
                }
            }
        }

        return false; 

    }

    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol> 
     * <li>If from = to, the shortest path has length 0
     * <li>Otherwise, the shortest path length is the length of the shortest
     * possible path connecting from to to.  
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    @Override
    public int pathLength(V from, V to)
    {
        if (from.equals(to)) { return 0; } //from = to

        // Map to store visited vertices and their distances from the source
        Map<V, Integer> distances = new HashMap<>();
        Queue<V> queue = new LinkedList<>();
 
        // Initialize distances and queue
        for (V vertex : getVertices()) {
             distances.put(vertex, Integer.MAX_VALUE);
        }
         
        distances.put(from, 0);
        queue.add(from);
 
        // Perform BFS
        while (!queue.isEmpty()) {
            V current = queue.poll();
            if (current.equals(to)) {
                return distances.get(current); // Return the distance if 'to' is reached
            }
 
            // Explore neighbors
            for (V neighbor : adjacentTo(current)) {
                if (distances.get(neighbor) == Integer.MAX_VALUE) {
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
 
        // 'to' vertex is not reachable from 'from'
        return Integer.MAX_VALUE;
    
    }

    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices are given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     * 
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable includes the source and
     * destination vertices. If there is no path from 'from' to 'to'
     * in the graph (e.g. if the vertices are not in the graph),
     * returns an empty Iterable collection of vertices.
     */
    @Override
    public Iterable<V> getPath(V from, V to)
    {
        Map<V, V> previous = new HashMap<>();
        Queue<V> queue = new LinkedList<>();
        List<V> path = new LinkedList<>();

        // BFS to find the shortest path
        queue.add(from);
        while (!queue.isEmpty()) {
            V current = queue.poll();
            if (current.equals(to)) {
                break; // Found the shortest path
            }

            for (V neighbor : adjacentTo(current)) {
                if (!previous.containsKey(neighbor)) {
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Reconstruct the path
        V current = to;
        while (current != null && !current.equals(from)) {
            path.add(0, current);
            current = previous.get(current);
        }
        if (current != null) {
            path.add(0, from);
        }
        
        //in the event there's an invalid path. 
        if (path.size() == 1 && from != to) { return Collections.emptyList(); }
        else { return path; } 
    }



}
