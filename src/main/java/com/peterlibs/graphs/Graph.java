package com.peterlibs.graphs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for a Graph, which will store a map of Nodes connected by Edges
 */
public class Graph implements Serializable {

    static final Logger classLogger = LogManager.getLogger(Graph.class);
    //Static variables for some of the stuff I don't want to keep typing
    static final int EDGE_DEFAULT_WEIGHT = 0;
    static final String EDGE_DEFAULT_LABEL = "An Edge";
    static final String VERTEX_DEFAULT_LABEL = "label";
    //Instance variables, for recording the vertices in this graph.
    // We do not need to maintain a list of edges, since that will exist within
    // each Vertex object.
    private final ArrayList<Vertex> vertices;

    /**
     * Constructor for a new, empty Graph
     */
    public Graph () {
        this.vertices = new ArrayList<>();
    }


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Simple Getters and Setters for building the Graph
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     * Attempts to find the Edge that exists between the two given Vertices
     * @param vertexStartName : The name of the vertex that this edge starts at
     * @param vertexEndName : The name of the vertex that this edge ends at
     * @return Edge object, a reference to the Edge in the graph.
     *          Returns null if the Edge is not found
     */
    public Edge getEdge(String vertexStartName, String vertexEndName) {
        classLogger.debug(
            "Will attempt to find an Edge between '{}' and '{}'",
            vertexStartName,
            vertexEndName
        );
        Vertex vertexStart = this.getVertex(vertexStartName);
        classLogger.debug("Starting Vertex: {}", vertexStart);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        classLogger.debug("Ending Vertex: {}", vertexEnd);
        return this.getEdge(vertexStart, vertexEnd);
    }
    private Edge getEdge(Vertex vertexStart, Vertex vertexEnd) {
        if (vertexStart == null || vertexEnd == null) {
            classLogger.debug("One of the given vertices was null. Cannot create an Edge to null. Returning null.");
            return null;
        }
        classLogger.debug("Asking Starting Vertex to search for Edge to Ending Vertex");
        return vertexStart.getEdgeToVertex(vertexEnd);
    }
    /**
     * Gets the number of edges in the Graph, in total
     * @return Integer, the number of edges that have been added to this Graph
     */
    public int getNumberOfEdges() {
        int totalEdges = 0;
        for (Vertex aVertex: this.vertices) {
            totalEdges += aVertex.getEdges().size();
        }
        return totalEdges;
    }
    /**
     * Adds an Edge to the Graph, linking the two specified Vertex objects.
     * @param vertexStartName : The starting point of the Edge being created
     * @param vertexEndName : The end point of the Edge being created
     * @param label : [optional] The label for the Edge. Defaults to "An Edge"
     * @param weight : [optional] The weight of the Edge. Default to '0'
     * @return The Edge object that was created
     */
    public Edge addEdge(String vertexStartName, String vertexEndName, int weight, String label) {
        classLogger.debug(
            "Will add/update Edge between '{}' and '{}' | weight = '{}', label = '{}'",
            vertexStartName,
            vertexEndName,
            weight,
            label
        );
        Vertex vertexStart = this.getVertex(vertexStartName);
        if (vertexStart == null)
            throw new IllegalArgumentException(
                "The Vertex %s does not exist in this Graph.".formatted( vertexStartName )
            );
        Vertex vertexEnd = this.getVertex(vertexEndName);
        if (vertexEnd == null)
            throw new IllegalArgumentException(
                "The Vertex %s does not exist in this Graph.".formatted( vertexEndName )
            );
        Edge theEdge = this.getEdge(vertexStart, vertexEnd);
        if (theEdge != null) {
            classLogger.debug("Found the Edge. Updating weight and label");
            theEdge.setWeight(weight);
            theEdge.setLabel(label);
        }
        else {
            classLogger.debug("Edge did not exist. Creating...");
            theEdge = new Edge(vertexStart, vertexEnd, weight, label);
            classLogger.debug("Edge created. Adding to Vertex's store of Edges that originate from it");
            vertexStart.addEdge(theEdge);
        }
        return theEdge;
    }
    public Edge addEdge(String vertexStartName, String vertexEndName, String label) {
        return this.addEdge(vertexStartName, vertexEndName, EDGE_DEFAULT_WEIGHT, label);
    }
    public Edge addEdge(String vertexStartName, String vertexEndName, int weight) {
        return this.addEdge(vertexStartName, vertexEndName, weight, EDGE_DEFAULT_LABEL);
    }
    public Edge addEdge(String vertexStartName, String vertexEndName) {
        return this.addEdge(vertexStartName, vertexEndName, EDGE_DEFAULT_WEIGHT, EDGE_DEFAULT_LABEL);
    }
    /**
     * Safely removes the Edge from the Graph, if it exists.
     * @param vertexStartName : The name of the Vertex the Edge needs to start at
     * @param vertexEndName : The name of the Vertex the Edge needs to end at
     */
    public void removeEdge(String vertexStartName, String vertexEndName) {
        classLogger.debug(
            "Will remove Edge between '{}' and '{}', if it exists",
            vertexStartName,
            vertexEndName
        );
        Vertex vertexStart = this.getVertex(vertexStartName);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        Edge theEdge = vertexStart.getEdgeToVertex(vertexEnd);
        if (theEdge == null) {
            throw new IllegalArgumentException("This edge does not exist in the Graph.");
        }
        this.removeEdge(theEdge);
    }
    private void removeEdge(Edge theEdge) {
        //To safely remove the Edge from the graph, we need to make sure that no vertices
        // have any references to the Edge and that the Edge has no references to any vertices.
        // Then we can safely remove the Edge from our Graph and the garbage collector should
        // clean up our memory.
        theEdge.getVertexStart().getEdgesInternal().remove(theEdge);
        theEdge.getVertexEnd().getEdgesInternal().remove(theEdge);
        theEdge.setVertexStart(null);
        theEdge.setVertexEnd(null);
    }

    /**
     * Gets a Vertex by its name.
     * @param vertexName : The name of the Vertex to search for
     * @return A Vertex object if it exists, otherwise, null
     */
    public Vertex getVertex(String vertexName) {
        classLogger.debug(
            "Looking for Vertex labeled '{}' in graph of {} vertices",
            vertexName,
            this.vertices.size()
        );
        for (Vertex aVertex: this.vertices) {
            classLogger.debug(
                "Processing | Label: '{}', Edges: {}",
                aVertex.getLabel(),
                aVertex.getEdges()
            );
            if (aVertex.getLabel().equals(vertexName)) {
                classLogger.debug(
                    "{} is a match | Label: '{}', Edges: {}",
                    aVertex,
                    aVertex.getLabel(),
                    aVertex.getEdges()
                );
                return aVertex;
            }
        }
        return null;
    }
    /**
     * Gets the number of edges in the Graph, in total
     * @return Integer, the number of edges that have been added to this Graph
     */
    public int getNumberOfVertices() {
        return this.vertices.size();
    }
    /**
     * Creates a new Vertex in the graph with the given Name.
     * @param newVertexName : The name of the Vertex. Must be UNIQUE
     * @return The Vertex object that was created
     * @throws IllegalArgumentException : The given Name for the new Vertex already exists
     */
    public Vertex addVertex(String newVertexName) {
        classLogger.debug("Will add new vertex '{}' if it does not already exist", newVertexName);
        Vertex newVertex = this.getVertex(newVertexName);
        if (newVertex != null) {
            classLogger.debug("Found a Vertex with the same label, which is not allowed");
            throw new IllegalArgumentException("A Vertex with this name already exists.");
        }
        newVertex = new Vertex(newVertexName);
        this.vertices.add(newVertex);
        classLogger.debug("New Vertex '{}' added", newVertexName);
        return newVertex;
    }
    /**
     * Remove a Vertex from the Graph by providing the Vertex object.
     * @param vertexName : The Name of the Vertex to remove from the Graph.
     * @throws IllegalArgumentException : The given Vertex does not exist in the Graph
     */
    public void removeVertex(String vertexName) {
        classLogger.debug("Looking for Vertex labeled '{}' and removing it if it exists.", vertexName);
        Vertex foundVertex = this.getVertex(vertexName);
        if (foundVertex == null) {
            throw new IllegalArgumentException("This vertex does not exist in the Graph.");
        }
        for (Vertex aVertex: this.vertices) {
            classLogger.debug(
                "Checking if Vertex '{}' has any Edges that are linked to the Vertex we want to remove, '{}'",
                aVertex.getLabel(),
                vertexName
            );
            ArrayList<Edge> edgesToRemove = new ArrayList<>();
            for (Edge anEdge: aVertex.getEdges()) {
                if (anEdge.getVertexStart() == foundVertex || anEdge.getVertexEnd() == foundVertex) {
                    edgesToRemove.add(anEdge);
                }
            }
            classLogger.info("Will be removing {} Edges from the Vertex", edgesToRemove.size());
            aVertex.getEdgesInternal().removeAll(edgesToRemove);
        }
        classLogger.info("All adjacent edges removed. Removing Vertex");
        this.vertices.remove(foundVertex);
    }
    //What I'd like to do here is have the return of removing a Vertex also return a
    // Hashmap that could be used to re-insert the Vertex (if it was removed incorrectly).
    // I don't quite have the time to really flesh this out, so I'll leave it here as a stub
    // to get to later.
//    public HashMap<String, HashMap<String,Object>> removeVertex(Vertex vertex) {}


    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * Getting Paths between Vertices, or generic info about the Edges/Vertices related to a Vertex
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    private Path makeIntoPath(ArrayList<Edge> aRawPath) {
        Path newPath = new Path();
        for (int i = 0; i < aRawPath.size(); i++) {
            newPath.addStep(
                aRawPath.get(i),
                ((i+1) == aRawPath.size()) //True if is final Edge in ArrayList
            );
        }
        return newPath;
    }

    /**
     * Will follow all possible paths to search for any feasible path to the destination.
     * If the vertex we are at does not have a direct path, then it will ask the vertices
     *  it is connected to to similarly search for a path. If any of them find a path, the
     *  starting vertex will add itself to the path and return.
     * @param vertexStart : The vertex we are at
     * @param vertexDestination : The vertex we are searching for
     * @param visitedVertices : ArrayList of Vertex objects, the ones already visited
     * @return ArrayList of ArrayLists of Edge objects. Each sub-ArrayList describe
     *          a possible path to the destination vertex.
     */
    private ArrayList<ArrayList<Edge>> allPathSearchForDestination(Vertex vertexStart, Vertex vertexDestination, ArrayList<Vertex> visitedVertices) {
        ArrayList<ArrayList<Edge>> foundPaths = new ArrayList<>();

        //The `clone` method should be fine in this situation because our values
        // are all going to be references to objects. Although, my more rudimentary
        // experience with Java may had made this a spectacular blunder to learn from.
        ArrayList<Vertex> newVisitedVertices;
        if (visitedVertices == null)
            newVisitedVertices = new ArrayList<>();
        else
            newVisitedVertices = new ArrayList<>(visitedVertices);
        newVisitedVertices.add(vertexStart);

        for (Edge anEdge: vertexStart.getEdges()) {
            //If we have already visited the vertex, skip doing anything more
            if (newVisitedVertices.contains(anEdge.getVertexEnd()))
                continue;

            ArrayList<Edge> itsAPath = new ArrayList<>();
            if (anEdge.getVertexEnd() == vertexDestination) {
                itsAPath.add(anEdge);
                foundPaths.add(itsAPath);
            }
            else {
                //Add all other found paths from the Edge's End Vertex
                ArrayList<ArrayList<Edge>> foundPathsForThisEdge = allPathSearchForDestination(
                    anEdge.getVertexEnd(), vertexDestination, newVisitedVertices
                );
                for (ArrayList<Edge> aPath: foundPathsForThisEdge) {
                    aPath.add(0, anEdge);
                }
                foundPaths.addAll(foundPathsForThisEdge);
            }
        }

        return foundPaths;
    }
    /**
     * Find all possible paths from the Vertex A to Vertex B, regardless of cost.
     * Each path will only use each Vertex once at a maximum.
     * @param vertexStartName : The starting point of our path search
     * @param vertexEndName : The Vertex we wish to reach
     * @return An ArrayList of ArrayList of String or Edge objects.
     *         Each embedded ArrayList represents a path, where the first given Vertex is our
     *         starting point, and the final given Vertex is our destination.
     * @throws RuntimeException, when no paths could be found
     */
    public List<Path> findAllPaths(String vertexStartName, String vertexEndName) {
        ArrayList<Path> allPaths = new ArrayList<>();

        Vertex vertexStart = this.getVertex(vertexStartName);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        if (vertexStart == vertexEnd) {
            classLogger.debug("Was given the same vertex for the start and end.");
            throw new IllegalArgumentException("Starting Vertex and Ending Vertex cannot be the same");
        }

        ArrayList<Vertex> visitedVertices = new ArrayList<>();
        classLogger.debug("Calling recursive helper function to get all paths.");
        ArrayList<ArrayList<Edge>> foundPaths = allPathSearchForDestination(vertexStart, vertexEnd, visitedVertices);

        classLogger.debug("Converting ArrayList of paths (using Edge objects) into ArrayLists of Vertices");
        for (ArrayList<Edge> aRawPath: foundPaths) {
            allPaths.add(
                this.makeIntoPath(aRawPath)
            );
        }
        return Collections.unmodifiableList(allPaths);
    }

    /**
     *
     * @param vertexStart
     * @param vertexDestination
     * @param visitedVertices
     * @return
     */
    private ArrayList<Edge> shortestPathSearchForDestination(Vertex vertexStart, Vertex vertexDestination, ArrayList<Vertex> visitedVertices) {
        classLogger.debug(
            "At vertex '{}', looking for path to vertex '{}'",
            vertexStart.getLabel(), vertexDestination.getLabel()
        );
        ArrayList<Edge> foundPath = new ArrayList<>();
        int lowestTotalCost = -1;

        ArrayList<Vertex> newVisitedVertices;
        if (visitedVertices == null)
            newVisitedVertices = new ArrayList<>();
        else
            newVisitedVertices = new ArrayList<>(visitedVertices);
        newVisitedVertices.add(vertexStart);
        classLogger.trace("Already visited vertices | '{}'", newVisitedVertices);

        for (Edge anEdge: vertexStart.getEdges()) {
            classLogger.trace(
                "Processing Edge '{}' ({} -> {})",
                anEdge.getLabel(), anEdge.getVertexStart().getLabel(), anEdge.getVertexEnd().getLabel()
            );
            //If we have already visited the vertex, skip doing anything more
            if (newVisitedVertices.contains(anEdge.getVertexEnd())) {
                classLogger.trace("Edge ends at a Vertex we have already visited. Skipping Edge...");
                continue;
            }

            ArrayList<Edge> itsAPath;
            int thisPathCost = 0;
            if (anEdge.getVertexEnd() == vertexDestination) {
                classLogger.trace("Edge ends at our destination. Building a new possible path.");
                itsAPath = new ArrayList<>();
                itsAPath.add(anEdge);
                thisPathCost = anEdge.getWeight();
            }
            else {
                //Add all other found paths from the Edge's End Vertex
                itsAPath = shortestPathSearchForDestination(
                    anEdge.getVertexEnd(), vertexDestination, newVisitedVertices
                );
                for (Edge pathEdge: itsAPath) { thisPathCost += pathEdge.getWeight(); }
            }

            classLogger.trace("Testing that we have a path to compare with what the current shortest is");
            if (itsAPath.size() == 0) {
                classLogger.trace("No path to destination from here. Continuing to next edge");
                continue;
            }

            classLogger.trace("Comparing the newly constructed path to what is recorded as the shortest.");
            classLogger.trace(
                "Current lowest, size={} cost={} | contender, size={} cost={}",
                foundPath.size(), lowestTotalCost, itsAPath.size(), thisPathCost
            );
            if (
                lowestTotalCost == -1 ||
                    thisPathCost < lowestTotalCost ||
                    (thisPathCost == lowestTotalCost && itsAPath.size() < foundPath.size())
                //The use case that I am not able to cover here (based on my current class design)
                // if when a path of the same weight and number of hops is found. The method will
                // return whichever one it found first, which may not ALWAYS be the same path.
            ) {
                classLogger.trace("We currently do not have a valid shortest path, or the newly found path is shorter");
                foundPath = itsAPath;
                foundPath.add(0, anEdge);
                lowestTotalCost = thisPathCost;
            }
        }
        if (lowestTotalCost == -1) {
            classLogger.debug("No possible path was found to destination");
            return null;
        }
        else {
            classLogger.debug("Path found. size={} cost={}", foundPath.size(), lowestTotalCost);
            return foundPath;
        }
    }
    /**
     * Gets the shortest path between the given start and end vertices.
     * @param vertexStartName : The starting point of our path search
     * @param vertexEndName : The Vertex we wish to reach
     * @return A Path object, the shortest path from Vertex A to Vertex B
     */
    public Path findShortestPath(String vertexStartName, String vertexEndName) {
        Vertex vertexStart = this.getVertex(vertexStartName);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        if (vertexStart == vertexEnd) {
            classLogger.debug("Was given the same vertex for the start and end.");
            throw new IllegalArgumentException("Starting Vertex and Ending Vertex cannot be the same");
        }

        classLogger.debug("Calling recursive helper function to get shortest path.");
        ArrayList<Edge> foundPath = shortestPathSearchForDestination(vertexStart, vertexEnd, null);
        if (foundPath == null) {
            classLogger.debug("No path found from '{}' to '{}'", vertexStartName, vertexEndName);
            throw new RuntimeException(
                "No path could be found from '%s' to '%s'".formatted( vertexStartName, vertexEndName )
            );
        }
        return this.makeIntoPath(foundPath);
    }

    /**
     *
     * @param vertexStart
     * @param vertexDestination
     * @param visitedVertices
     * @return
     */
    private ArrayList<Edge> longestPathSearchForDestination(Vertex vertexStart, Vertex vertexDestination, ArrayList<Vertex> visitedVertices) {
        ArrayList<Edge> foundPath = new ArrayList<>();

        ArrayList<Vertex> newVisitedVertices;
        if (visitedVertices == null)
            newVisitedVertices = new ArrayList<>();
        else
            newVisitedVertices = new ArrayList<>(visitedVertices);
        newVisitedVertices.add(vertexStart);

        //TODO: implement
        return null;
    }
    /**
     * Finds the longest path between the given start and end vertices.
     * @param vertexStartName : The starting point of our path search
     * @param vertexEndName : The Vertex we wish to reach
     * @return A Path object, the longest path from Vertex A to Vertex B
     */
    public Path findLongestPath(String vertexStartName, String vertexEndName) {
        Vertex vertexStart = this.getVertex(vertexStartName);
        Vertex vertexEnd = this.getVertex(vertexEndName);
        if (vertexStart == vertexEnd) {
            classLogger.debug("Was given the same vertex for the start and end.");
            throw new IllegalArgumentException("Starting Vertex and Ending Vertex cannot be the same");
        }

        classLogger.debug("Calling recursive helper function to get longest path.");
        ArrayList<Edge> foundPath = longestPathSearchForDestination(vertexStart, vertexEnd, null);
        if (foundPath == null) {
            classLogger.debug("No path found from '{}' to '{}'", vertexStartName, vertexEndName);
            throw new RuntimeException(
                "No path could be found from '%s' to '%s'".formatted( vertexStartName, vertexEndName )
            );
        }
        return this.makeIntoPath(foundPath);
    }

}
