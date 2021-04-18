package com.peterlibs.graphs;

import java.io.Serializable;
import java.util.*;

/**
 * Class for a Graph, which will store a map of Nodes connected by Edges
 */
public class Graph implements Serializable {

    //Static variables for some of the stuff I don't want to keep typing
    private static final int EDGE_DEFAULT_WEIGHT = 0;
    private static final String EDGE_DEFAULT_LABEL = "An Edge";
    //Instance variables, for recording the vertices and edges in this graph
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;

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
     * Gets a Vertex by its name.
     * @param vertexName : The name of the Vertex to search for
     * @return A Vertex object if it exists, otherwise, null
     */
    public Vertex getVertex(String vertexName) {
        /**/
        return null;
    }
    /**
     * Creates a new Vertex in the graph with the given Name.
     * @param vertexName : The name of the Vertex. Must be UNIQUE
     * @return The Vertex object that was created
     * @throws IllegalArgumentException : The given Name for the new Vertex already exists
     */
    public Vertex addVertex(String vertexName) {
        //test is vertex name is unique
            //if not, throw exception
        if (false) {
            throw new IllegalArgumentException("A Vertex with this name already exists.");
        }
        return null;
    }
    /**
     * Remove a Vertex from the Graph by providing the Vertex object.
     * @param vertex : The Vertex object to remove from the Graph
     * @throws IllegalArgumentException : The given Vertex does not exist in the Graph
     */
    public void removeVertex(Vertex vertex) {
        /**/
        throw new IllegalArgumentException("This vertex does not exist in the Graph.");
    }
    //What I'd like to do here is have the return of removing a Vertex also return a
    //Hashmap that could be used to re-insert the Vertex (if it was removed incorrectly).
    //I don't quite have the time to really flesh this out, so I'll leave it here as a stub
    //to get to later.
//    public HashMap<String, HashMap<String,Object>> removeVertex(Vertex vertex) {}

    /**
     *
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
     *
     * @param edge
     * @return
     */
    public Vertex removeEdge(Edge edge) {
        /**/
        return null;
    }
    /**
     *
     * @param edgeLabel
     * @return
     */
    public Vertex removeEdge(String edgeLabel) {
        /**/
        return null;
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Getting Paths between Vertices
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @return
     */
    public ArrayList<ArrayList<Vertex>> findAllPaths(Vertex vertexStart, Vertex vertexEnd) {
        return null;
    }

    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @return
     */
    public ArrayList<Vertex> findShortestPath(Vertex vertexStart, Vertex vertexEnd) {
        return null;
    }

    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @return
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
