package com.peterlibs.graphs;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class GraphTest {

    @Test
    void addVertex() {
        Graph testGraph = new Graph();

        assertDoesNotThrow( () -> testGraph.addVertex("v1") );
        assertDoesNotThrow( () -> testGraph.addVertex("v2") );
        assertThrows(
            IllegalArgumentException.class,
            () -> testGraph.addVertex("v1")
        );
    }

    @Test
    @Disabled
    void removeVertex() {
    }

    @Test
    void getVertex() {
        Graph testGraph = new Graph();

        String testVertexName = "v1";
        testGraph.addVertex(testVertexName);

        Vertex addedVertex = testGraph.getVertex(testVertexName);
        assertInstanceOf(Vertex.class, addedVertex);

        String vertexLabel = addedVertex.getLabel();
        assertEquals(testVertexName, vertexLabel);
    }

    @Test
    void addEdge() {
        Graph testGraph = new Graph();
        testGraph.addVertex("v1");
        testGraph.addVertex("v2");
        testGraph.addVertex("v3");

        //Adding new edges to graph
        assertDoesNotThrow( () -> testGraph.addEdge("v1", "v2") );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v3", 3) );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v1", "edgy") );
        assertDoesNotThrow(
            () -> testGraph.addEdge(
                "v3", "v1", 7, "anotheredge"
            )
        );

        //Updating existing edges
        assertDoesNotThrow( () -> testGraph.addEdge("v1", "v2", 8) );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v3", 2, "hello") );
        assertDoesNotThrow(
            () -> testGraph.addEdge(
                "v3", "v1", 2, "new-me"
            )
        );

        //bad edges
        assertThrows(
            IllegalArgumentException.class,
            () -> testGraph.addEdge("v1", "v2", -3)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> testGraph.addEdge("v1", "vbad", 3)
        );
    }

    @Test
    void removeEdge() {
    }

    @Test
    void findAllPaths() {
    }

    @Test
    void findShortestPath() {
    }

    @Test
    void findLongestPath() {
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException
    {
        String filename = "graphserialized.txt";
        Graph testGraph = new Graph();
        testGraph.addVertex("v1");
        testGraph.addVertex("v2");
        testGraph.addEdge("v1", "v2", 3, "e1");

        //saving the serialized object to a file
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(testGraph);
        objectOutputStream.flush();
        objectOutputStream.close();

        //reading the file and instantiating the object
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Graph testGraph2 = (Graph) objectInputStream.readObject();
        objectInputStream.close();

        //Getting the same edge from each Graph
        Edge theEdge = testGraph.getEdge("v1","v2");
        Edge theEdge2 = testGraph2.getEdge("v1","v2");

        //Verifying that the objects are essentially the same
        assertEquals(testGraph.getNumberOfEdges(), testGraph2.getNumberOfEdges());
        assertEquals(testGraph.getNumberOfVertices(), testGraph2.getNumberOfVertices());
        assertEquals(theEdge.getLabel(), theEdge2.getLabel());
        assertEquals(theEdge.getWeight(), theEdge2.getWeight());
        assertEquals(theEdge.getVertexStart().getLabel(), theEdge2.getVertexStart().getLabel());
        assertEquals(theEdge.getVertexEnd().getLabel(), theEdge2.getVertexEnd().getLabel());
    }
}