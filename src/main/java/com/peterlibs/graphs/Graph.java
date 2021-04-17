package com.peterlibs.graphs;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for a Graph, which will store a map of Nodes connected by Edges
 */
public class Graph implements Serializable {

    /*
     *
     */
    //
    //


    /**
     * Constructor for a new, empty Graph
     */
    public Graph () {
        /**/
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for building the Graph
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     *
     * @return
     */
    public String addVertex() {
        /**/
        return null;
    }
    /**
     *
     * @param vertexName
     * @return
     */
    public String addVertex(String vertexName) {
        /**/
        return null;
    }
    /**
     *
     * @param vertexName
     * @return
     */
    public Vertex removeVertex(String vertexName) {
        /**/
        return null;
    }
    /**
     *
     * @param vertex
     * @return
     */
    public Vertex removeVertex(Vertex vertex) {
        /**/
        return null;
    }

    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @return
     */
    public String addEdge(Vertex vertexStart, Vertex vertexEnd) {
        /**/
        return null;
    }
    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @param label
     * @return
     */
    public String addEdge(Vertex vertexStart, Vertex vertexEnd, String label) {
        /**/
        return null;
    }
    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @param weight
     * @return
     */
    public String addEdge(Vertex vertexStart, Vertex vertexEnd, int weight) {
        /**/
        return null;
    }
    /**
     *
     * @param vertexStart
     * @param vertexEnd
     * @param label
     * @param weight
     * @return
     */
    public String addEdge(Vertex vertexStart, Vertex vertexEnd, String label, int weight) {
        /**/
        return null;
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
     * @param vertexNameStart
     * @param vertexNameEnd
     * @return
     */
    public ArrayList<ArrayList<Vertex>> findAllPaths(String vertexNameStart, String vertexNameEnd) {
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
     * @param vertexNameStart
     * @param vertexNameEnd
     * @return
     */
    public ArrayList<Vertex> findShortestPath(String vertexNameStart, String vertexNameEnd) {
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
    /**
     *
     * @param vertexNameStart
     * @param vertexNameEnd
     * @return
     */
    public ArrayList<Vertex> findLongestPath(String vertexNameStart, String vertexNameEnd) {
        return null;
    }
}
