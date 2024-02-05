package edu.union.adt.graph;


/**
 * Graph Factory. 
 * @author Neil Daterao
 */
public class GraphFactory {
    public static <V> Graph<V> createGraph() { 
        return new GraphImpl<V>(); 
    }
}
