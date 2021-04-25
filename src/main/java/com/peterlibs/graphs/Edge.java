package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;

/**
 * (content to be added)
 */
class Edge implements Serializable {

    static final Logger classLogger = LogManager.getLogger(Edge.class);
    //Instance variables, for recording the vertices this Edge is connecting, identifying
    // which vertex is the START and which is the END.
    //An Edge can also have a weight
    private Vertex vertexStart;
    private Vertex vertexEnd;
    private int weight;
    private String label;

    /**
     * Constructor for a new Edge in the Graph
     * @param vertexStart : The Vertex this Edge starts at.
     * @param vertexEnd : The Vertex this Edge ends at.
     * @param weight : [optional] The weight (or cost) of the edge. Defaults to '0'.
     * @param label : [optional] The pretty name for the Edge. Defaults to 'label'.
     */
    Edge (Vertex vertexStart, Vertex vertexEnd, int weight, String label) {
        classLogger.debug(
            "Creating new Edge between '{}' and '{}'. weight = {}, label = '{}'",
            vertexStart.getLabel(),
            vertexEnd.getLabel(),
            weight,
            label
        );
        this.setLabel(label);
        this.setWeight(weight);
        this.setVertexStart(vertexStart);
        this.setVertexEnd(vertexEnd);
    }
    Edge (Vertex vertexStart, Vertex vertexEnd, String label) {
        this(vertexStart, vertexEnd, Graph.EDGE_DEFAULT_WEIGHT, label);
    }
    Edge (Vertex vertexStart, Vertex vertexEnd, int weight) {
        this(vertexStart, vertexEnd, weight, Graph.EDGE_DEFAULT_LABEL);
    }
    Edge (Vertex vertexStart, Vertex vertexEnd) {
        this(vertexStart, vertexEnd, Graph.EDGE_DEFAULT_WEIGHT, Graph.EDGE_DEFAULT_LABEL);
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for the created Edge
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    public String getLabel() { return this.label; }
    void setLabel(String newLabel) { this.label = newLabel; }

    public int getWeight() { return this.weight; }
    void setWeight(int newWeight) {
        if (newWeight < 0) {
            classLogger.warn(
                "Invalid Edge Weight. {} is less than 0",
                newWeight
            );
            throw new IllegalArgumentException("An edge cannot have a weight less than 0.");
        }
        this.weight = newWeight;
    }

    public Vertex getVertexStart() { return this.vertexStart; }
    /**
     * Sets the given Vertex to be the Edge's new Starting Vertex
     * @param newVertex : A Vertex object. `null` is allowed
     */
    void setVertexStart(Vertex newVertex) { this.vertexStart = newVertex; }
    public Vertex getVertexEnd() { return this.vertexEnd; }
    /**
     * Sets the given Vertex to be the Edge's new Ending Vertex
     * @param newVertex : A Vertex object. `null` is allowed
     */
    void setVertexEnd(Vertex newVertex) { this.vertexEnd = newVertex; }

}
