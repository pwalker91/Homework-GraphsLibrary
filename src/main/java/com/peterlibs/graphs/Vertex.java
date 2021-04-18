package com.peterlibs.graphs;

import java.io.Serializable;
import java.util.ArrayList;

class Vertex implements Serializable {

    /*
     * Instance variables, for recording the edges this Vertex is incident to, specifically
     * where this is the STARTING vertex in the incident pair.
     * Constructor, for creating a new vertex.
     */
    private final ArrayList<Edge> edges;
    private String label;

    /**
     * Constructor for a new, empty Graph
     */
    public Vertex () {
        this.edges = new ArrayList<>();
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for building the Graph
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     * Adds a new Edge to this Vertex. By adding an edge
     * @param newEdge
     */
    public void addEdge(Edge newEdge) {
        this.edges.add(newEdge);
    }

    /**
     * Gets the Edge that connects this Vertex to the specified Vertex (if it exists).
     * @param destinationVertex : Vertex object
     * @return An Edge object if it exists, otherwise, null
     */
    public Edge getEdgeToVertex(Vertex destinationVertex) {
        for (Edge anEdge: this.edges) {
            if (anEdge.getVertexEnd() == destinationVertex) {
                return anEdge;
            }
        }
        return null;
    }
}
