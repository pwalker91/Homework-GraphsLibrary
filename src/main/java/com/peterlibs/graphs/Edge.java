package com.peterlibs.graphs;

import java.io.Serializable;

class Edge implements Serializable {

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
        this.setLabel(label);
        this.setWeight(weight);
        this.setVertexStart(vertexStart);
        this.setVertexStart(vertexEnd);
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

    String getLabel() { return this.label; }
    void setLabel(String newLabel) { this.label = newLabel; }

    int getWeight() { return this.weight; }
    void setWeight(int newWeight) {
        if (weight < 0)
            throw new IllegalArgumentException("An edge cannot have a weight less than 0.");
        this.weight = newWeight;
    }

    Vertex getVertexStart() { return this.vertexStart; }
    protected void setVertexStart(Vertex newVertex) { this.vertexStart = newVertex; }
    Vertex getVertexEnd() { return this.vertexEnd; }
    protected void setVertexEnd(Vertex newVertex) { this.vertexEnd = newVertex; }

}
