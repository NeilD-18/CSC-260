package edu.union.adt.graph.tests.dateraon; 

import java.util.*; 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.union.adt.graph.*;

@RunWith(JUnit4.class)
public class NewGraphTests 
{
    private Graph<String> g; 

    @Before
    public void setUp()
    {
        g = GraphFactory.<String>createGraph();
    }

    @After
    public void tearDown()
    {
        g = null;
    }

   

    @Test 
    public void testIsEmpty()
    {
        assertEquals("Graph must have 0 vertices",0, g.numVertices()); 
        assertEquals("Graph must have 0 edges",0, g.numEdges()); 

        g.addEdge("A", "B"); 
        assertFalse("Graph has an edge from A to B, no longer empty", g.isEmpty());
    }
   
    
    private <T> boolean iteratorContains(Iterable<T> container, T x)
    {
        for (Object s: container) {
            if (s.equals(x)) {
                return true;
            }
        }

        return false;
    }
    
    
    @Test
    public void testRemoveVertex() 
    {   
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addVertex("Fizz"); 
        g.addEdge("Foo", "Bar"); 
        g.addEdge("Fizz", "Bar"); 
       
        int priorToRemoval = g.numVertices();
        
        g.removeVertex("Bar");

        assertEquals("After removal, one vertex should be gone", priorToRemoval-1, g.numVertices()); 
        assertFalse("Bar should no longer be in the graph" ,g.contains("Bar")); 
        assertFalse("Bar should not be adjacent to Foo anymore", iteratorContains(g.adjacentTo("Foo"), "Bar")); 
        assertFalse("Bar should not be adjacent to Fizz anymore", iteratorContains(g.adjacentTo("Fizz"), "Bar"));
                
    }

    @Test
    public void testRemoveVertexNotPresent() 
    {   
        g.addEdge("Foo", "Bar");
        g.addEdge("Fizz", "Buzz"); 

        int priorToRemoval = g.numVertices(); 
        
        g.removeVertex("CSC-260"); 

        assertEquals("Vertex not in graph, nothing should be removed",priorToRemoval, g.numVertices()); 
       
    }

    @Test
    public void testRemoveEdge()
    { 
        g.addEdge("A", "B"); 
        g.addEdge("C", "D"); 

        int priorToRemoval = g.numEdges(); 
        g.removeEdge("A", "B"); 

        assertEquals("Number of edges should be 1 less", priorToRemoval - 1, g.numEdges()); 
        assertFalse("Edge from A to B should no longer exist", iteratorContains(g.adjacentTo("A"), "B"));

    }

    @Test 
    public void testRemoveEdgeInvalidEdge()
    {
        g.addEdge("A", "B"); 
        g.addEdge("C", "D"); 

        int priorToRemoval = g.numEdges();

        g.removeEdge("A", "D"); 
        g.removeEdge("E", "F"); 

        assertEquals("Amount of edges should not change when removing invalid edge", priorToRemoval, g.numEdges()); 

    }

    @Test
    public void testHasPath() 
    {   
        g.addEdge("A", "C"); 
        g.addEdge("B", "C");
        g.addEdge("C", "D");
        g.addEdge("D", "E"); 

        assertTrue("Has Path from A to D", g.hasPath("A", "D"));
        assertTrue("Has Path from A to E", g.hasPath("A", "E"));
        assertTrue("Has Path from B to C", g.hasPath("B", "C"));

        assertFalse("No path from C to B", g.hasPath("C", "B"));
        assertFalse("No path from D to A", g.hasPath("D", "A"));
        assertFalse("No path from C to F (vertex not in graph)", g.hasPath("C", "F"));
        assertFalse("No path from Q to E (vertex not in graph)", g.hasPath("Q", "E"));


        assertTrue("Path from A to A", g.hasPath("A", "A"));
        assertTrue("Path from E to E", g.hasPath("E", "E"));

    }

    @Test 
    public void testPathLength()
    {
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "D");
        g.addEdge("D", "E");
        g.addVertex("F");

        assertEquals("A -> B -> C, shortest path length is 2", 2, g.pathLength("A", "C")); 
        assertEquals("A -> B -> C -> D, shortest path length is 3", 3, g.pathLength("A", "D")); 
        assertEquals("Direct path from B to C",1, g.pathLength("B", "C"));

        g.addEdge("A", "C"); 

        assertEquals("A -> C, shortest path length is 1", 1, g.pathLength("A", "C")); 

        assertEquals("Path doesn't exist, return Integer.MAX_VALUE", Integer.MAX_VALUE, g.pathLength("A", "F")); 
        assertEquals("Path doesn't exist, return Integer.MAX_VALUE", Integer.MAX_VALUE, g.pathLength("B", "A"));

        assertEquals("Shorteset path from a vertex to itself is 0", 0, g.pathLength("A", "A")); 
        assertEquals("Shorteset path from a vertex to itself is 0", 0, g.pathLength("B", "B"));


    }

    @Test
    public void testGetPath()
    {
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "D");
        g.addEdge("D", "E");

        Iterable<String> pathAC = g.getPath("A", "C");
        Iterable<String> pathAD = g.getPath("A", "D");
        Iterable<String> pathBD = g.getPath("B", "D");
        Iterable<String> pathAA = g.getPath("A", "A");
        Iterable<String> pathBB = g.getPath("B", "B");

        List<String> expectedPathAC = List.of("A", "B", "C");
        List<String> expectedPathAD = List.of("A", "B", "C", "D");
        List<String> expectedPathBD = List.of("B", "C", "D");
        List<String> expectedPathAA = List.of("A");
        List<String> expectedPathBB = List.of("B");

        assertEquals("Path from A to C", expectedPathAC, pathAC);
        assertEquals("Path from A to D", expectedPathAD, pathAD);
        assertEquals("Path from B to D", expectedPathBD, pathBD);
        assertEquals("Path from Vertex to Itself", expectedPathAA, pathAA); 
        assertEquals("Path from Vertex to Itself", expectedPathBB, pathBB);

        g.addEdge("A", "C"); 
        
        Iterable<String> newPathAC = g.getPath("A", "C");
        List<String> newExpectedPathAC = List.of("A", "C");

        assertEquals("Shorter Path from A to C", newExpectedPathAC, newPathAC);


    }

    @Test
    public void testGetPathInvalid() { 
        
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("D", "E");
        
        Iterable<String> pathAE = g.getPath("A", "E");
        Iterable<String> pathDB = g.getPath("D", "B");

        List<String> expectedEmptyPath = Collections.emptyList(); 

        assertEquals("No path from A to E", expectedEmptyPath, pathAE); 
        assertEquals("No path from D to B", expectedEmptyPath, pathDB); 
    }

    





    
   
}
