package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

class GraphTest {

    static final Logger testLogger = LogManager.getLogger(GraphTest.class);

    @Test
    void addVertex() {
        testLogger.info("Creating new Graph");
        Graph testGraph = new Graph();

        testLogger.info("Adding vertices");
        assertDoesNotThrow( () -> testGraph.addVertex("v1") );
        assertDoesNotThrow( () -> testGraph.addVertex("v2") );

        testLogger.info("Adding vertices that will throw an error");
        assertThrows(
            IllegalArgumentException.class,
            () -> testGraph.addVertex(null) );
        assertThrows(
            IllegalArgumentException.class,
            () -> testGraph.addVertex("v1")
        );
        assertEquals(testGraph.getNumberOfVertices(), 2);
    }

    @Test
    void removeVertex() {
        testLogger.info("Creating new Graph");
        Graph testGraph = new Graph();
        String vertexToBeRemoved = "v2";
        String vertexToStay = "v1";

        testLogger.info("Adding vertices");
        assertDoesNotThrow( () -> testGraph.addVertex(vertexToStay) );
        assertDoesNotThrow( () -> testGraph.addVertex(vertexToBeRemoved) );
        assertDoesNotThrow( () -> testGraph.addEdge(vertexToStay, vertexToBeRemoved) );

        testLogger.info("Validating that Vertex '{}' exists", vertexToBeRemoved);
        assertInstanceOf(Vertex.class, testGraph.getVertex(vertexToBeRemoved));
        assertInstanceOf(
            Vertex.class,
            testGraph.getEdge(vertexToStay, vertexToBeRemoved).getVertexEnd()
        );

        testLogger.info("Removing Vertex '{}'", vertexToBeRemoved);
        assertDoesNotThrow( () -> testGraph.removeVertex(vertexToBeRemoved) );

        testLogger.info("Validating that vertex is gone");
        assertEquals(testGraph.getNumberOfVertices(), 1);
        assertNull(testGraph.getVertex(vertexToBeRemoved));
        assertEquals(testGraph.getVertex(vertexToStay).getEdges().size(), 0);
        assertNull(
            testGraph.getVertex(vertexToStay).getEdgeToVertex(
                testGraph.getVertex(vertexToBeRemoved)
            )
        );
        assertNull(testGraph.getEdge(vertexToStay, vertexToBeRemoved));
    }

    @Test
    void getVertex() {
        testLogger.info("Creating new Graph");
        Graph testGraph = new Graph();

        testLogger.info("Adding vertex");
        String testVertexName = "v1";
        testGraph.addVertex(testVertexName);

        testLogger.info("Getting the vertex from the Graph");
        Vertex addedVertex = testGraph.getVertex(testVertexName);
        assertInstanceOf(Vertex.class, addedVertex);
        String vertexLabel = addedVertex.getLabel();
        assertEquals(testVertexName, vertexLabel);

        testLogger.info("Adding another Vertex and an Edge between them");
        String otherVertexName = "v2";
        testGraph.addVertex(otherVertexName);
        testGraph.addEdge(testVertexName, otherVertexName);

        testLogger.info("Testing that Edge actually has reference to Vertices");
        Edge theEdgeBetween = testGraph.getEdge(testVertexName, otherVertexName);
        Vertex otherAddedVertex = testGraph.getVertex(otherVertexName);
        assertEquals(addedVertex, theEdgeBetween.getVertexStart());
        assertEquals(otherAddedVertex, theEdgeBetween.getVertexEnd());
    }

    @Test
    void addEdge() {
        testLogger.info("Creating new Graph");
        Graph testGraph = new Graph();

        testLogger.info("Adding vertices");
        testGraph.addVertex("v1");
        testGraph.addVertex("v2");
        testGraph.addVertex("v3");

        testLogger.info("Adding edges");
        assertDoesNotThrow( () -> testGraph.addEdge("v1", "v2") );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v3", 3) );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v1", "edgy") );
        assertDoesNotThrow(
            () -> testGraph.addEdge(
                "v3", "v1", 7, "anotheredge"
            )
        );

        testLogger.info("Updating edges");
        assertDoesNotThrow( () -> testGraph.addEdge("v1", "v2", 8) );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v3", 2, "hello") );
        assertDoesNotThrow(
            () -> testGraph.addEdge(
                "v3", "v1", 2, "new-me"
            )
        );

        testLogger.info("Adding bad edges");
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
        testLogger.info("Creating new Graph");
        Graph testGraph = new Graph();

        testLogger.info("Adding vertices");
        testGraph.addVertex("v1");
        testGraph.addVertex("v2");
        testGraph.addVertex("v3");

        testLogger.info("Adding edges");
        assertDoesNotThrow( () -> testGraph.addEdge("v1", "v2", 4) );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v3", 3) );
        assertDoesNotThrow( () -> testGraph.addEdge("v2", "v1", 7) );
        assertDoesNotThrow( () -> testGraph.addEdge("v3", "v1", 7) );

        testLogger.info("Verifying correct number of edges");
        assertEquals(testGraph.getVertex("v1").getEdges().size(), 1);
        assertEquals(testGraph.getVertex("v2").getEdges().size(), 2);
        assertEquals(testGraph.getVertex("v3").getEdges().size(), 1);

        testLogger.info("Removing edges");
        assertDoesNotThrow( () -> testGraph.removeEdge("v2", "v1") );
        assertDoesNotThrow( () -> testGraph.removeEdge("v3", "v1") );

        testLogger.info("Verifying correct number of edges");
        assertEquals(testGraph.getVertex("v1").getEdges().size(), 1);
        assertEquals(testGraph.getVertex("v2").getEdges().size(), 1);
        assertEquals(testGraph.getVertex("v3").getEdges().size(), 0);
    }

    @Disabled
    @Test
    void findAllPaths() {
    }

    @Disabled
    @Test
    void findShortestPath() {
    }

    @Disabled
    @Test
    void findLongestPath() {
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        Graph testGraph = new Graph();
        testGraph.addVertex("v1");
        testGraph.addVertex("v2");
        testGraph.addEdge("v1", "v2", 3, "e1");

        //saving the serialized object to a file
        File filename = File.createTempFile("serialized", ".tmp");
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
        Vertex startVertex = theEdge.getVertexStart();
        Edge theEdge2 = testGraph2.getEdge("v1","v2");
        Vertex startVertex2 = theEdge2.getVertexStart();

        //Verifying that the objects are essentially the same
        assertNotEquals(testGraph, testGraph2);
        assertNotEquals(theEdge, theEdge2);
        assertNotEquals(startVertex, startVertex2);
        assertEquals(testGraph.getNumberOfEdges(), testGraph2.getNumberOfEdges());
        assertEquals(testGraph.getNumberOfVertices(), testGraph2.getNumberOfVertices());
        assertEquals(theEdge.getLabel(), theEdge2.getLabel());
        assertEquals(theEdge.getWeight(), theEdge2.getWeight());
        assertEquals(startVertex.getLabel(), startVertex2.getLabel());
        assertEquals(startVertex.getLabel(), startVertex2.getLabel());
    }
}