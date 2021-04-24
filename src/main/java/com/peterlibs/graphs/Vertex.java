package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;

class Vertex implements Serializable {

    static final Logger classLogger = LogManager.getLogger(Vertex.class);
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
    protected void setLabel(String label) {
        if (label == null)
            throw new IllegalArgumentException("Vertex cannot have a 'null' label.");
        this.label = label;
    }

    protected ArrayList<Edge> getEdges() { return this.edges; }
    protected void addEdge(Edge newEdge) {
        if (newEdge == null) {
            classLogger.warn("Given a null Edge object");
            throw new IllegalArgumentException("Cannot add a null Edge object.");
        }
        classLogger.trace(
            "start: {} {}",
            newEdge.getVertexStart().getLabel(),
            newEdge.getVertexStart().toString()
        );
        classLogger.trace(
            "this: {} {}",
            this.getLabel(),
            this.toString()
        );
        if (newEdge.getVertexStart() != this) {
            classLogger.warn(
                "This and given Start Vertex are not the same. {}' does not match '{}'",
                newEdge.getVertexStart().getLabel(),
                this.getLabel()
            );
            throw new IllegalArgumentException("The Edge does not have this Vertex as its starting vertex.");
        }
        this.edges.add(newEdge);
    }
    protected void removeEdge(Edge anEdge) { this.edges.remove(anEdge); }

    /**
     * Gets the Edge that connects this Vertex to the specified Vertex (if it exists).
     * @param destinationVertex : Vertex object
     * @return An Edge object if it exists, otherwise, null
     */
    Edge getEdgeToVertex(Vertex destinationVertex) {
        if (destinationVertex == null) {
            classLogger.debug("Given destination Vertex is null. No Edge can exist");
            return null;
        }

        classLogger.debug(
            "Looking for Edge that connects this to Vertex '{}'",
            destinationVertex.getLabel()
        );
        for (Edge anEdge: this.edges) {
            classLogger.trace(
                "Processing Edge: {} |  Ending Vertex [{}] '{}'",
                anEdge.toString(),
                anEdge.getVertexEnd().toString(),
                anEdge.getVertexEnd().getLabel()
            );
            if (anEdge.getVertexEnd() == destinationVertex) {
                return anEdge;
            }
        }
        classLogger.debug("No Edge found. Returning null");
        return null;
    }

    @Override
    public String toString() {
        return "'"+this.getLabel()+"' | "+
            this.getEdges().toString()+" | "+
            super.toString();
    }

}
