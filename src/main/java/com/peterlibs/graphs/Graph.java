package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for a Graph, which will store a map of Nodes connected by Edges
 */
public class Graph implements Serializable {

    static final Logger classLogger = LogManager.getLogger(Graph.class);
    //Static variables for some of the stuff I don't want to keep typing
    static final int EDGE_DEFAULT_WEIGHT = 0;
    static final String EDGE_DEFAULT_LABEL = "An Edge";
    static final String VERTEX_DEFAULT_LABEL = "label";
    //Instance variables, for recording the vertices in this graph.
    // We do not need to maintain a list of edges, since that will exist within
    // each Vertex object.
    private final ArrayList<Vertex> vertices;

    /**
     * Constructor for a new, empty Graph
     */
    public Graph () {
        this.vertices = new ArrayList<>();
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for building the Graph
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     * Attempts to find the Edge that exists between the two given Vertices
     * @param vertexStartName : The name of the vertex that this edge starts at
     * @param vertexEndName : The name of the vertex that this edge ends at
     * @return Edge object, a reference to the Edge in the graph.
     *          Returns null if the Edge is not found
     */
    public Edge getEdge(String vertexStartName, String vertexEndName) {
        classLogger.debug(
            "Will attempt to find an Edge between '{}' and '{}'",
            vertexStartName,
            vertexEndName
        );
        Vertex vertexStart = this.getVertex(vertexStartName);
        classLogger.debug("Starting Vertex: {}", vertexStart);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        classLogger.debug("Ending Vertex: {}", vertexEnd);
        return this.getEdge(vertexStart, vertexEnd);
    }
    private Edge getEdge(Vertex vertexStart, Vertex vertexEnd) {
        if (vertexStart == null || vertexEnd == null) {
            classLogger.debug("One of the given vertices was null. Cannot create an Edge to null. Returning null.");
            return null;
        }
        classLogger.debug("Asking Starting Vertex to search for Edge to Ending Vertex");
        return vertexStart.getEdgeToVertex(vertexEnd);
    }
    /**
     * Gets the number of edges in the Graph, in total
     * @return Integer, the number of edges that have been added to this Graph
     */
    public int getNumberOfEdges() {
        int totalEdges = 0;
        for (Vertex aVertex: this.vertices) {
            totalEdges += aVertex.getEdges().size();
        }
        return totalEdges;
    }
    /**
     * Adds an Edge to the Graph, linking the two specified Vertex objects.
     * @param vertexStartName : The starting point of the Edge being created
     * @param vertexEndName : The end point of the Edge being created
     * @param label : [optional] The label for the Edge. Defaults to "An Edge"
     * @param weight : [optional] The weight of the Edge. Default to '0'
     * @return The Edge object that was created
     */
    public Edge addEdge(String vertexStartName, String vertexEndName, int weight, String label) {
        classLogger.debug(
            "Will add/update Edge between '{}' and '{}' | weight = '{}', label = '{}'",
            vertexStartName,
            vertexEndName,
            weight,
            label
        );
        Vertex vertexStart = this.getVertex(vertexStartName);
        if (vertexStart == null)
            throw new IllegalArgumentException("The Vertex "+vertexStartName+" does not exist in this Graph.");
        Vertex vertexEnd = this.getVertex(vertexEndName);
        if (vertexEnd == null)
            throw new IllegalArgumentException("The Vertex "+vertexEndName+" does not exist in this Graph.");
        Edge theEdge = this.getEdge(vertexStart, vertexEnd);
        if (theEdge != null) {
            classLogger.debug("Found the Edge. Updating weight and label");
            theEdge.setWeight(weight);
            theEdge.setLabel(label);
        }
        else {
            classLogger.debug("Edge did not exist. Creating...");
            theEdge = new Edge(vertexStart, vertexEnd, weight, label);
            classLogger.debug("Edge created. Adding to Vertex's store of Edges that originate from it");
            vertexStart.addEdge(theEdge);
        }
        return theEdge;
    }
    public Edge addEdge(String vertexStartName, String vertexEndName, String label) {
        return this.addEdge(vertexStartName, vertexEndName, EDGE_DEFAULT_WEIGHT, label);
    }
    public Edge addEdge(String vertexStartName, String vertexEndName, int weight) {
        return this.addEdge(vertexStartName, vertexEndName, weight, EDGE_DEFAULT_LABEL);
    }
    public Edge addEdge(String vertexStartName, String vertexEndName) {
        return this.addEdge(vertexStartName, vertexEndName, EDGE_DEFAULT_WEIGHT, EDGE_DEFAULT_LABEL);
    }
    /**
     * Safely removes the Edge from the Graph, if it exists.
     * @param vertexStartName : The name of the Vertex the Edge needs to start at
     * @param vertexEndName : The name of the Vertex the Edge needs to end at
     */
    public void removeEdge(String vertexStartName, String vertexEndName) {
        classLogger.debug(
            "Will remove Edge between '{}' and '{}', if it exists",
            vertexStartName,
            vertexEndName
        );
        Vertex vertexStart = this.getVertex(vertexStartName);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        Edge theEdge = vertexStart.getEdgeToVertex(vertexEnd);
        if (theEdge == null) {
            throw new IllegalArgumentException("This edge does not exist in the Graph.");
        }
        this.removeEdge(theEdge);
    }
    private void removeEdge(Edge theEdge) {
        //To safely remove the Edge from the graph, we need to make sure that no vertices
        // have any references to the Edge and that the Edge has no references to any vertices.
        // Then we can safely remove the Edge from our Graph and the garbage collector should
        // clean up our memory.
        theEdge.getVertexStart().getEdges().remove(theEdge);
        theEdge.getVertexEnd().getEdges().remove(theEdge);
        theEdge.setVertexStart(null);
        theEdge.setVertexEnd(null);
    }

    /**
     * Gets a Vertex by its name.
     * @param vertexName : The name of the Vertex to search for
     * @return A Vertex object if it exists, otherwise, null
     */
    public Vertex getVertex(String vertexName) {
        classLogger.debug(
            "Looking for Vertex labeled '{}' in graph of {} vertices",
            vertexName,
            this.vertices.size()
        );
        for (Vertex aVertex: this.vertices) {
            classLogger.debug(
                "Processing | Label: '{}', Edges: {}",
                aVertex.getLabel(),
                aVertex.getEdges()
            );
            if (aVertex.getLabel().equals(vertexName)) {
                classLogger.debug(
                    "{} is a match | Label: '{}', Edges: {}",
                    aVertex,
                    aVertex.getLabel(),
                    aVertex.getEdges()
                );
                return aVertex;
            }
        }
        return null;
    }
    /**
     * Gets the number of edges in the Graph, in total
     * @return Integer, the number of edges that have been added to this Graph
     */
    public int getNumberOfVertices() {
        return this.vertices.size();
    }
    /**
     * Creates a new Vertex in the graph with the given Name.
     * @param newVertexName : The name of the Vertex. Must be UNIQUE
     * @return The Vertex object that was created
     * @throws IllegalArgumentException : The given Name for the new Vertex already exists
     */
    public Vertex addVertex(String newVertexName) {
        classLogger.debug("Will add new vertex '{}' if it does not already exist", newVertexName);
        Vertex newVertex = this.getVertex(newVertexName);
        if (newVertex != null) {
            classLogger.debug("Found a Vertex with the same label, which is not allowed");
            throw new IllegalArgumentException("A Vertex with this name already exists.");
        }
        newVertex = new Vertex(newVertexName);
        this.vertices.add(newVertex);
        classLogger.debug("New Vertex '"+newVertexName+"' added");
        return newVertex;
    }
    /**
     * Remove a Vertex from the Graph by providing the Vertex object.
     * @param vertexName : The Name of the Vertex to remove from the Graph.
     * @throws IllegalArgumentException : The given Vertex does not exist in the Graph
     */
    public void removeVertex(String vertexName) {
        classLogger.debug("Looking for Vertex labeled '{}' and removing it if it exists.", vertexName);
        Vertex foundVertex = this.getVertex(vertexName);
        if (foundVertex == null) {
            throw new IllegalArgumentException("This vertex does not exist in the Graph.");
        }
        for (Vertex aVertex: this.vertices) {
            classLogger.debug(
                "Checking if Vertex '{}' has any Edges that are linked to the Vertex we want to remove, '{}'",
                aVertex.getLabel(),
                vertexName
            );
            for (Edge anEdge: aVertex.getEdges()) {
                if (anEdge.getVertexStart() == foundVertex || anEdge.getVertexEnd() == foundVertex) {
                    this.removeEdge(anEdge);
                }
            }
        }
        classLogger.info("All adjacent edges removed. Removing Vertex");
        this.vertices.remove(foundVertex);
    }
    //What I'd like to do here is have the return of removing a Vertex also return a
    // Hashmap that could be used to re-insert the Vertex (if it was removed incorrectly).
    // I don't quite have the time to really flesh this out, so I'll leave it here as a stub
    // to get to later.
//    public HashMap<String, HashMap<String,Object>> removeVertex(Vertex vertex) {}


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Getting Paths between Vertices
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     * Find all possible paths from the Vertex A to Vertex B, regardless of cost and
     * without using the same Vertex twice.
     * @param vertexStart : The starting point of our path search
     * @param vertexEnd : The Vertex we wish to reach
     * @return An ArrayList of ArrayList of Vertex objects.
     *         Each embedded ArrayList represents a path, where the first Vertex is our
     *         starting point, and the final Vertex is our destination.
     * @throws RuntimeException, when no paths could be found
     */
    public ArrayList<ArrayList<Vertex>> findAllPaths(Vertex vertexStart, Vertex vertexEnd) {
        //TODO: implement
        return null;
    }

    /**
     *
     * @param vertexStart : The starting point of our path search
     * @param vertexEnd : The Vertex we wish to reach
     * @return An ArrayList of Vertex objects, the shortest path from Vertex A to Vertex B
     */
    public ArrayList<Vertex> findShortestPath(Vertex vertexStart, Vertex vertexEnd) {
        //TODO: implement
        return null;
    }

    /**
     *
     * @param vertexStart : The starting point of our path search
     * @param vertexEnd : The Vertex we wish to reach
     * @return An ArrayList of Vertex objects, the longest path from Vertex A to Vertex B
     */
    public ArrayList<Vertex> findLongestPath(Vertex vertexStart, Vertex vertexEnd) {
        //TODO: implement
        return null;
    }

    /*
    What wikipedia says a graph aught to be able to tell about itself. Let's see if
    I can implement all of these!
        adjacent(G, x, y): tests whether there is an edge from the vertex x to the vertex y;
        neighbors(G, x): lists all vertices y such that there is an edge from the vertex x to the vertex y;
        add_vertex(G, x): adds the vertex x, if it is not there;
        remove_vertex(G, x): removes the vertex x, if it is there;
        add_edge(G, x, y): adds the edge from the vertex x to the vertex y, if it is not there;
        remove_edge(G, x, y): removes the edge from the vertex x to the vertex y, if it is there;
        get_vertex_value(G, x): returns the value associated with the vertex x;
        set_vertex_value(G, x, v): sets the value associated with the vertex x to v.
        get_edge_value(G, x, y): returns the value associated with the edge (x, y);
        set_edge_value(G, x, y, v): sets the value associated with the edge (x, y) to v.
     */

}
