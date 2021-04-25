package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    
    //These are some helper functions for the testing of path logic
    private void logPathAsString(Path aPath) {
        ArrayList<String> aPathAsStrings = new ArrayList<>();
        for (Vertex v: aPath.getVertices()) {
            aPathAsStrings.add(v.getLabel());
        }
        testLogger.info(
            "A path as a list of Vertex labels | {} | Total Cost = {}",
            aPathAsStrings, aPath.getCost()
        );
    }
    private void doTestFindAllPaths(
        Graph aGraph, String vertexStartName, String vertexEndName,
        int expectedSize
    ) {
        if (vertexStartName.equals(vertexEndName)) {
            testLogger.info("Validating that a path from '{}' to itself is not allowed", vertexStartName);
            assertThrows(
                IllegalArgumentException.class,
                () -> aGraph.findAllPaths(vertexStartName, vertexEndName)
            );
        }
        else {
            testLogger.info(
                "Finding and Validating all paths from '{}' to '{}'",
                vertexStartName, vertexEndName
            );
            List<Path> paths = aGraph.findAllPaths(vertexStartName, vertexEndName);
            for (Path aPath : paths) {
                logPathAsString(aPath);
            }
            assertEquals(paths.size(), expectedSize);
        }
    }
    private void doTestFindShortestAllPaths(
        Graph aGraph, String vertexStartName, String vertexEndName,
        boolean shouldHavePath, int expectedTotalCost, int expectedSize
    ) {
        if (vertexStartName.equals(vertexEndName)) {
            testLogger.info("Validating that a path from '{}' to itself is not allowed", vertexStartName);
            assertThrows(
                IllegalArgumentException.class,
                () -> aGraph.findAllPaths(vertexStartName, vertexEndName)
            );
        }
        else {
            testLogger.info(
                "Finding and Validating shortest path from '{}' to '{}'",
                vertexStartName, vertexEndName
            );
            if (shouldHavePath) {
                Path aPath = aGraph.findShortestPath(vertexStartName, vertexEndName);
                logPathAsString(aPath);
                assertEquals(aPath.getCost(), expectedTotalCost);
                assertEquals(aPath.getVertices().size(), expectedSize);
            } else {
                assertThrows(
                    RuntimeException.class,
                    () -> aGraph.findShortestPath(vertexStartName, vertexEndName)
                );
            }
        }
    }

    @Nested
    class DenseGraphPathTest {

        Graph denseGraph;

        @BeforeEach
        public void init() {
            testLogger.info("Creating new dense Graph");
            denseGraph = new Graph();

            testLogger.info("Adding vertices to dense Graph");
            denseGraph.addVertex("v1");
            denseGraph.addVertex("v2");
            denseGraph.addVertex("v3");
            denseGraph.addVertex("v4");
            denseGraph.addVertex("v5");

            testLogger.info("Adding edges to dense Graph");
            denseGraph.addEdge("v1", "v2", 4);
            denseGraph.addEdge("v1", "v3", 3);
            denseGraph.addEdge("v1", "v5", 2);
            denseGraph.addEdge("v2", "v1", 5);
            denseGraph.addEdge("v2", "v3", 6);
            denseGraph.addEdge("v2", "v4", 8);
            denseGraph.addEdge("v2", "v5", 3);
            denseGraph.addEdge("v3", "v2", 4);
            denseGraph.addEdge("v3", "v4", 3);
            denseGraph.addEdge("v4", "v1", 2);
            denseGraph.addEdge("v4", "v2", 5);
            denseGraph.addEdge("v4", "v3", 6);
            denseGraph.addEdge("v5", "v3", 2);
            denseGraph.addEdge("v5", "v4", 1);
        }

        @Test
        void findAllPaths() {
            doTestFindAllPaths(denseGraph, "v1", "v1", -1);
            doTestFindAllPaths(denseGraph, "v1", "v2", 7);
            doTestFindAllPaths(denseGraph, "v1", "v3", 8);
            doTestFindAllPaths(denseGraph, "v1", "v4", 10);
            doTestFindAllPaths(denseGraph, "v1", "v5", 4);

            doTestFindAllPaths(denseGraph, "v2", "v1", 5);
            doTestFindAllPaths(denseGraph, "v2", "v2", -1);
            doTestFindAllPaths(denseGraph, "v2", "v3", 10);
            doTestFindAllPaths(denseGraph, "v2", "v4", 7);
            doTestFindAllPaths(denseGraph, "v2", "v5", 4);

            doTestFindAllPaths(denseGraph, "v3", "v1", 5);
            doTestFindAllPaths(denseGraph, "v3", "v2", 3);
            doTestFindAllPaths(denseGraph, "v3", "v3", -1);
            doTestFindAllPaths(denseGraph, "v3", "v4", 4);
            doTestFindAllPaths(denseGraph, "v3", "v5", 7);

            doTestFindAllPaths(denseGraph, "v4", "v1", 3);
            doTestFindAllPaths(denseGraph, "v4", "v2", 5);
            doTestFindAllPaths(denseGraph, "v4", "v3", 9);
            doTestFindAllPaths(denseGraph, "v4", "v4", -1);
            doTestFindAllPaths(denseGraph, "v4", "v5", 7);

            doTestFindAllPaths(denseGraph, "v5", "v1", 7);
            doTestFindAllPaths(denseGraph, "v5", "v2", 7);
            doTestFindAllPaths(denseGraph, "v5", "v3", 6);
            doTestFindAllPaths(denseGraph, "v5", "v4", 3);
            doTestFindAllPaths(denseGraph, "v5", "v5", -1);
        }

        @Test
        void findShortestPath() {
            doTestFindShortestAllPaths(denseGraph, "v1", "v1", true, -1, -1);
            doTestFindShortestAllPaths(denseGraph, "v1", "v2", true, 4, 2);
            doTestFindShortestAllPaths(denseGraph, "v1", "v3", true, 3, 2);
            doTestFindShortestAllPaths(denseGraph, "v1", "v4", true, 3, 3);
            doTestFindShortestAllPaths(denseGraph, "v1", "v5", true, 2, 2);

            doTestFindShortestAllPaths(denseGraph, "v2", "v1", true, 5, 2);
            doTestFindShortestAllPaths(denseGraph, "v2", "v2", true, -1, -1);
            doTestFindShortestAllPaths(denseGraph, "v2", "v3", true, 5, 3);
            doTestFindShortestAllPaths(denseGraph, "v2", "v4", true, 4, 3);
            doTestFindShortestAllPaths(denseGraph, "v2", "v5", true, 3, 2);

            doTestFindShortestAllPaths(denseGraph, "v3", "v1", true, 5, 3);
            doTestFindShortestAllPaths(denseGraph, "v3", "v2", true, 4, 2);
            doTestFindShortestAllPaths(denseGraph, "v3", "v3", true, -1, -1);
            doTestFindShortestAllPaths(denseGraph, "v3", "v4", true, 3, 2);
            doTestFindShortestAllPaths(denseGraph, "v3", "v5", true, 7, 3);

            doTestFindShortestAllPaths(denseGraph, "v4", "v1", true, 2, 2);
            doTestFindShortestAllPaths(denseGraph, "v4", "v2", true, 5, 2);
            doTestFindShortestAllPaths(denseGraph, "v4", "v3", true, 5, 3);
            doTestFindShortestAllPaths(denseGraph, "v4", "v4", true, -1, -1);
            doTestFindShortestAllPaths(denseGraph, "v4", "v5", true, 4, 3);

            doTestFindShortestAllPaths(denseGraph, "v5", "v1", true, 3, 3);
            doTestFindShortestAllPaths(denseGraph, "v5", "v2", true, 6, 3);
            doTestFindShortestAllPaths(denseGraph, "v5", "v3", true, 2, 2);
            doTestFindShortestAllPaths(denseGraph, "v5", "v4", true, 1, 2);
            doTestFindShortestAllPaths(denseGraph, "v5", "v5", true, -1, -1);
        }

        @Disabled
        @Test
        void findLongestPath() {
        }

    }
    
    @Nested
    class SparseGraphPathTest {

        Graph sparseGraph;

        @BeforeEach
        public void init() {
            testLogger.info("Creating new sparse Graph");
            sparseGraph = new Graph();

            testLogger.info("Adding vertices to dense Graph");
            sparseGraph.addVertex("v1");
            sparseGraph.addVertex("v2");
            sparseGraph.addVertex("v3");
            sparseGraph.addVertex("v4");
            sparseGraph.addVertex("v5");
            sparseGraph.addVertex("v6");
            sparseGraph.addVertex("v7");
            sparseGraph.addVertex("v8");
            sparseGraph.addVertex("v9");

            testLogger.info("Adding edges to dense Graph");
            sparseGraph.addEdge("v1", "v2", 4);
            sparseGraph.addEdge("v1", "v7", 2);
            sparseGraph.addEdge("v1", "v8", 3);
            sparseGraph.addEdge("v2", "v3", 2);
            sparseGraph.addEdge("v2", "v5", 3);
            sparseGraph.addEdge("v2", "v9", 5);
            sparseGraph.addEdge("v3", "v4", 3);
            sparseGraph.addEdge("v3", "v7", 4);
            sparseGraph.addEdge("v4", "v6", 2);
            sparseGraph.addEdge("v4", "v9", 4);
            sparseGraph.addEdge("v5", "v8", 2);
            sparseGraph.addEdge("v5", "v9", 1);
            sparseGraph.addEdge("v8", "v4", 5);
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

    }

}