package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is for storing a "path", a series of Vertices and Edges
 * that connect a starting Vertex and ending Vertex
 */
class Path implements Serializable {

    static final Logger classLogger = LogManager.getLogger(Path.class);
    //Instance variables, for recording the vertices in this graph.
    // We do not need to maintain a list of edges, since that will exist within
    // each Vertex object.
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Edge> edges;
    private int totalCost;

    /**
     * Constructor for a new, empty Path that will be added to
     */
    Path () {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.totalCost = 0;
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for the created Edge
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    public List<Vertex> getVertices() { return Collections.unmodifiableList(this.vertices); }
    public List<Edge> getEdges() { return Collections.unmodifiableList(this.edges); }
    public int getCost() { return this.totalCost; }

    /**
     * Using the given edge, updates the ArrayList of Vertex object, the ArrayList
     * of Edge objects, and the total cost of this Path.
     * @param anEdge : The Edge object with the info we need to add
     * @param isFinalEdge : True if this Edge is the final Edge in the path
     */
    void addStep(Edge anEdge, boolean isFinalEdge) {
        this.totalCost += anEdge.getWeight();
        this.edges.add(anEdge);
        this.vertices.add(anEdge.getVertexStart());
        if (isFinalEdge)
            this.vertices.add(anEdge.getVertexEnd());
    }

    //I'm not yet implementing this because I'm not entirely sure I'll need it.
    // At this point, I haven't implemented my `Graph.findShortestPath`, and maybe
    // I'll run into a situation where I need it.
//    void removeStep(Edge anEdge) { }

}
