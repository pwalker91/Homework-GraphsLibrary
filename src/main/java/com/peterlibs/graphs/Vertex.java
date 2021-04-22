package com.peterlibs.graphs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

class Vertex implements Serializable {

    static final Logger logger = Logger.getLogger(Vertex.class.getName());
    //Instance variables, for recording the edges this Vertex is incident to, specifically
    // where this is the STARTING vertex in the incident pair.
    private final ArrayList<Edge> edges;
    private String label;

    /**
     * Constructor for a new Vertex in the Graph
     * @param label : [optional] The name of the Vertex in the Graph
     */
    Vertex (String label) {
        this.edges = new ArrayList<>();
        this.setLabel(label);
    }
    Vertex () {
        this(Graph.VERTEX_DEFAULT_LABEL);
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for building the Graph
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    String getLabel() { return label; }
    void setLabel(String label) { this.label = label; }

    protected ArrayList<Edge> getEdges() { return this.edges; }
    protected void addEdge(Edge newEdge) {
        if (newEdge == null)
            throw new IllegalArgumentException("Cannot add a null Edge object.");
        this.edges.add(newEdge);
    }
    protected void removeEdge(Edge anEdge) { this.edges.remove(anEdge); }

    /**
     * Gets the Edge that connects this Vertex to the specified Vertex (if it exists).
     * @param destinationVertex : Vertex object
     * @return An Edge object if it exists, otherwise, null
     */
    Edge getEdgeToVertex(Vertex destinationVertex) {
        for (Edge anEdge: this.edges) {
            if (anEdge.getVertexEnd() == destinationVertex) {
                return anEdge;
            }
        }
        return null;
    }

}
