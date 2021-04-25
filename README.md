# Overview
 A simple Java project to implement the Graphs data structure as a reusable library.

# Use
To create a new Graph, follow these steps

### 1. Create a new Graph
Every Graph will start out completely empty. You will new need to add vertices and edges.
```java
import com.peterlibs.graphs;

Graph myNewGraph = new Graph();
```

### 2. Add Vertices
Every Vertex in the Graph must be uniquely named. If no name for the Vertex is provided, the default String `"label"` will be used
```java
myNewGraph.addVertex("vertex-1");
myNewGraph.addVertex("vertex-2");
myNewGraph.addVertex("vertex-3");
myNewGraph.addVertex("vertex-4");
```

### 3. Add Edges
Edges in this Graph are directional and weighted. The default weight is 0.
When adding an Edge, you must specify the label of the starting Vertex and ending Vertex. If the Vertex does not exist, an Exception will be thrown
```java
myNewGraph.addEdge("vertex-1", "vertex-2");
myNewGraph.addEdge("vertex-2", "vertex-3", 10);
myNewGraph.addEdge("vertex-3", "vertex-4", 20);
```
To update an Edge's weight, simply re-add the Edge.
```java
myNewGraph.addEdge("vertex-1", "vertex-2", 30);
myNewGraph.addEdge("vertex-2", "vertex-3", 50);
```

### 4. Get Paths (all, shortest, or longest)
With your Graph built, you can now ask it to get the path(s) between two vertices.
Each possible path with be returned as a `com.peterlibs.graph.Path` object.
This object will contain a list of the `com.peterlibs.graph.Vertex` objects that define the path. You can also get the path as a list of `com.peterlibs.graph.Edge` objects. The Path also has a method for easily getting the "total cost" of this path.
```java
Path shortestPath = myNewGraph.findShortestPath("vertex-1", "vertex-3");
List<Vertex> thePath = shortestPath.getVertices();
int totalCost = shortestPath.getCost();
```

# Testing
Unit tests have been written to test the functionality of the methods in the provided classes. The following classes have complete unit tests
 - [x] com.peterlibs.graph.Graph
 - [ ] com.peterlibs.graph.Vertex
 - [ ] com.peterlibs.graph.Edge
 - [ ] com.peterlibs.graph.Path

When testing the `Graph` class, the following graphs were used.
![Dense Graph](https://github.com/pwalker91/Homework-GraphsLibrary/blob/main/src/test/resources/PathsTests-DenseGraph-Diagram.png?raw=true){width=600}
![Sparse Graph](https://github.com/pwalker91/Homework-GraphsLibrary/blob/main/src/test/resources/PathsTests-SparseGraph-Diagram.png?raw=true){width=600}
