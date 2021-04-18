package com.peterlibs.graphs;

import java.io.Serializable;

class Edge implements Serializable {

    /*
     * Instance variables, for recording the vertices and edges in this graph.
     * Constructor, for creating a new graph.
     */
    private Vertex vertexStart;
    private Vertex vertexEnd;
    private int weight;
    private String label;

    /**
     * Constructor for a new Edge in a Graph
     * @param vertexStart : The Vertex this Edge starts at.
     * @param vertexEnd : The Vertex this Edge ends at.
     * @param weight : [optional] The weight (or cost) of the edge. Defaults to '0'.
     * @param label : [optional] The pretty name for the Edge. Defaults to 'label'.
     */
    public Edge (Vertex vertexStart, Vertex vertexEnd, int weight, String label) {
        this.vertexStart = vertexStart;
        this.vertexEnd = vertexEnd;
        this.label = label;
        if (weight < 0) {
            throw new IllegalArgumentException("An edge cannot have a weight less than 0.");
        }
        this.weight = weight;
    }
    public Edge (Vertex vertexStart, Vertex vertexEnd, String label) {
        this(vertexStart, vertexEnd, 0, label);
    }
    public Edge (Vertex vertexStart, Vertex vertexEnd, int weight) {
        this(vertexStart, vertexEnd, weight, "label");
    }
    public Edge (Vertex vertexStart, Vertex vertexEnd) {
        this(vertexStart, vertexEnd, 0, "label");
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for the created Edge
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */
    public String getLabel() { return this.label; }
    public void setLabel(String newLabel) { this.label = newLabel; }

    public int getWeight() { return this.weight; }
    public void setWeight(int newWeight) { this.weight = newWeight; }

    public Vertex getVertexStart() { return this.vertexStart; }
    public void setVertexStart(Vertex newVertex) { this.vertexStart = newVertex; }
    public Vertex getVertexEnd() { return this.vertexEnd; }
    public void setVertexEnd(Vertex newVertex) { this.vertexEnd = newVertex; }
    /**
     * Changes the "direction" of this vertex, swapping the Start and End Vertices.
     */
    public void reverseDirection() {
        Vertex tmp = this.vertexStart;
        this.vertexStart = this.vertexEnd;
        this.vertexEnd = tmp;
    }

}
