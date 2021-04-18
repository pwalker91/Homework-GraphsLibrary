package com.peterlibs.graphs;

import java.io.Serializable;
import java.util.ArrayList;

class Vertex implements Serializable {

    /*
     * Instance variables, for recording the vertices and edges in this graph.
     * Constructor, for creating a new graph.
     */
    private ArrayList<Edge> edges;

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
}
