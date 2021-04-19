package com.peterlibs.graphs;

import java.io.Serializable;
import java.util.*;

/**
 * Class for a Graph, which will store a map of Nodes connected by Edges
 */
public class Graph implements Serializable {

    //Static variables for some of the stuff I don't want to keep typing
    static final int EDGE_DEFAULT_WEIGHT = 0;
    static final String EDGE_DEFAULT_LABEL = "An Edge";
    static final String VERTEX_DEFAULT_LABEL = "label";
    //Instance variables, for recording the vertices and edges in this graph
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Edge> edges;

    /**
     * Constructor for a new, empty Graph
     */
    public Graph () {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for building the Graph
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     * Adds an Edge to the Graph, linking the two specified Vertex objects.
     * @param vertexStart : The starting point of the Edge being created
     * @param vertexEnd : The end point of the Edge being created
     * @param label : [optional] The label for the Edge. Defaults to "An Edge"
     * @param weight : [optional] The weight of the Edge. Default to '0'
     * @return The Edge object that was created
     */
    public Edge addEdge(Vertex vertexStart, Vertex vertexEnd, int weight, String label) {
        Edge theEdge = vertexStart.getEdgeToVertex(vertexEnd);
        if (theEdge != null) {
            theEdge.setWeight(weight);
            theEdge.setLabel(label);
        }
        else {
            theEdge = new Edge(vertexStart, vertexEnd, weight, label);
            vertexStart.addEdge(theEdge);
        }
        this.edges.add(theEdge);
        return theEdge;
    }
    public Edge addEdge(Vertex vertexStart, Vertex vertexEnd, String label) {
        return this.addEdge(vertexStart, vertexEnd, EDGE_DEFAULT_WEIGHT, label);
    }
    public Edge addEdge(Vertex vertexStart, Vertex vertexEnd, int weight) {
        return this.addEdge(vertexStart, vertexEnd, weight, EDGE_DEFAULT_LABEL);
    }
    public Edge addEdge(Vertex vertexStart, Vertex vertexEnd) {
        return this.addEdge(vertexStart, vertexEnd, EDGE_DEFAULT_WEIGHT, EDGE_DEFAULT_LABEL);
    }
    /**
     * Safely removes the Edge from the Graph.
     * @param theEdge : The Edge object we will remove, if it exists in the graph
     */
    public void removeEdge(Edge theEdge) {
        boolean found = false;
        for (Edge anEdge: this.edges) {
            if (anEdge == theEdge) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("This edge does not exist in the Graph.");
        }
        //To safely remove the Edge from the graph, we need to make sure that no vertices
        // have any references to the Edge and that the Edge has no references to any vertices.
        // Then we can safely remove the Edge from our Graph and the garbage collector should
        // clean up our memory.
        theEdge.getVertexStart().getEdges().remove(theEdge);
        theEdge.getVertexEnd().getEdges().remove(theEdge);
        theEdge.setVertexStart(null);
        theEdge.setVertexEnd(null);
        this.edges.remove(theEdge);
    }

    /**
     * Gets a Vertex by its name.
     * @param vertexName : The name of the Vertex to search for
     * @return A Vertex object if it exists, otherwise, null
     */
    public Vertex getVertex(String vertexName) {
        for (Vertex aVertex: this.vertices) {
            if (aVertex.getLabel().equals(vertexName)) {
                return aVertex;
            }
        }
        return null;
    }
    /**
     * Creates a new Vertex in the graph with the given Name.
     * @param newVertexName : The name of the Vertex. Must be UNIQUE
     * @return The Vertex object that was created
     * @throws IllegalArgumentException : The given Name for the new Vertex already exists
     */
    public Vertex addVertex(String newVertexName) {
        Vertex newVertex = this.getVertex(newVertexName);
        if (newVertex != null) {
            throw new IllegalArgumentException("A Vertex with this name already exists.");
        }
        newVertex = new Vertex();
        this.vertices.add(newVertex);
        return newVertex;
    }
    /**
     * Remove a Vertex from the Graph by providing the Vertex object.
     * @param vertexName : The Name of the Vertex to remove from the Graph.
     * @throws IllegalArgumentException : The given Vertex does not exist in the Graph
     */
    public void removeVertex(String vertexName) {
        Vertex foundVertex = this.getVertex(vertexName);
        if (foundVertex == null) {
            throw new IllegalArgumentException("This vertex does not exist in the Graph.");
        }
        for (Edge anEdge: this.edges) {
            if (anEdge.getVertexStart() == foundVertex || anEdge.getVertexEnd() == foundVertex) {
                this.removeEdge(anEdge);
            }
        }
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
        return null;
    }

    /**
     *
     * @param vertexStart : The starting point of our path search
     * @param vertexEnd : The Vertex we wish to reach
     * @return An ArrayList of Vertex objects, the shortest path from Vertex A to Vertex B
     */
    public ArrayList<Vertex> findShortestPath(Vertex vertexStart, Vertex vertexEnd) {
        return null;
    }

    /**
     *
     * @param vertexStart : The starting point of our path search
     * @param vertexEnd : The Vertex we wish to reach
     * @return An ArrayList of Vertex objects, the longest path from Vertex A to Vertex B
     */
    public ArrayList<Vertex> findLongestPath(Vertex vertexStart, Vertex vertexEnd) {
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
