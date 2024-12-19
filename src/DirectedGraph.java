package src;
import java.util.*;

public class DirectedGraph<T> implements Graph<T> {
    private ArrayList<ArrayList<T>> adjList;

    /**
     * Constructor that initializes an empty adjacency list
     */
    public DirectedGraph() {
        adjList = new ArrayList<ArrayList<T>>();
    }

    /**
     * Adds a one-way edge from the 'first' argument to the 'second'
     * argument.
     * @param first the vertex that the directed edge is pointing from
     * @param second the vertex that the 'first' edge is going to point to
     */
    public void add(T first, T second) throws IllegalArgumentException {
        if (first == null || second == null) {
            throw new IllegalArgumentException(
                "Passed null argument to directed graph");
        }

        boolean foundFirst = false;
        boolean foundSecond = false;
        ArrayList<T> firstTemp = null;
        ArrayList<T> secondTemp = null;
        for (ArrayList<T> list: adjList) {
            if (list.get(0).equals(first)) {
                foundFirst = true;
                firstTemp = list;
            }

            if (list.get(0).equals(second)) {
                foundSecond = true;
                secondTemp = list;
            }
        }

        updateLists(foundFirst, foundSecond, first, second,
                    firstTemp, secondTemp);
    }

    private void updateLists(boolean foundFirst, boolean foundSecond,
                             T first, T second,
                             ArrayList<T> firstTemp, ArrayList<T> secondTemp) 
                             throws IllegalArgumentException {
/*
        System.out.println("First: " + first + " Second " + second);
        System.out.println("Found first: " + (foundFirst) + " found second " + foundSecond);
 * 
 System.out.println("\n Conditions");
 System.out.println("11: " + (foundFirst && foundSecond));
 System.out.println("00: " + (!foundFirst && !foundSecond));
 System.out.println("10: " + (foundFirst && !foundSecond));
 System.out.println("01: " + (!foundFirst && foundSecond));
 System.out.println("\n");
 */

        if (foundFirst && foundSecond) {
            if (!firstTemp.contains(second)) {
                // If there isn't an edge already from first to second,
                // make one
                firstTemp.add(second);
            }
        }

        if (!foundFirst && !foundSecond) {
            // Add lists for first and second, but only give an edge from first
            // to second
            firstTemp = new ArrayList<>();
            secondTemp = new ArrayList<>();

            // Add values to the new lists
            firstTemp.add(first);
            firstTemp.add(second);
            secondTemp.add(second);

            // Add new lists to the adjList
            adjList.add(firstTemp);
            adjList.add(secondTemp);
        }

        // If only the first is found in the list, then just add the second
        // vertex to the first's list
        if (foundFirst && !foundSecond) {
            // Add adjacency to first
            firstTemp.add(second);

            // Create and add second vertex to adjList
            secondTemp = new ArrayList<>();
            secondTemp.add(second);
            adjList.add(secondTemp);
        }

        if (!foundFirst && foundSecond) {
            // Create list with first value
            firstTemp = new ArrayList<>();

            // Add value to first vertex and adjacency to second vertex
            firstTemp.add(first);
            firstTemp.add(second);
            adjList.add(firstTemp);
        }
    }

    public void deleteVertex(T vertex) {
        int size = adjList.size();
        if (size == 0) {
            throw new IllegalStateException("Cannot delete from empty graph.");
        }

        for (int ind = adjList.size() - 1; ind >= 0; ind--) {
            ArrayList<T> currentList = adjList.get(ind);
            if (currentList.get(0).equals(vertex)) {
                adjList.remove(ind);
            }

            if (currentList.contains(vertex)) {
                currentList.remove(vertex);
            }
        }
    }

    public void deleteEdge(T first, T second) {
        for (ArrayList<T> list: adjList) {
            T vertex = list.get(0);
            if (vertex.equals(first)) {
                list.remove(second);
                return;
            }
        }
    }

    public DirectedGraph<T> DFS(T start) {
        DirectedGraph<T> mst = new DirectedGraph<>();
        HashSet<T> visited = new HashSet<>();
        
        helperDFS(visited, start, mst);

        return mst;
    }

    private void helperDFS(HashSet<T> visited, T curr, DirectedGraph<T> mst) {
        visited.add(curr);

        ArrayList<T> travList = new ArrayList<>();
        for (ArrayList<T> list: adjList) {
            if (list.get(0).equals(curr)) {
                travList = list;
                break;
            }
        }

        // If there are no nodes to visit, then just proceed
        // with other adjacent verticies
        if (travList.size() == 0) {
            return;
        }

        // For every neighbor N of current vertex:
        for (int i = 1; i < travList.size(); i++) {
            T adjVertex = travList.get(i);

            // If N has not been visited, then add it to mst
            // and traverse it's adjacencies
            if (!visited.contains(adjVertex)) {
                mst.add(curr, adjVertex);
                helperDFS(visited, adjVertex, mst);
            }
        }
    }

    public DirectedGraph<T> BFS(T start) {
        DirectedGraph<T> mst = new DirectedGraph<>();
        HashSet<T> visited = new HashSet<>();
        Queue<T> q = new LinkedList<>();
        visited.add(start);
        q.add(start);
        
        while(!q.isEmpty()) {
            T top = q.remove();

            ArrayList<T> trav = new ArrayList<>();
            for (ArrayList<T> list: adjList) {
                if (list.get(0).equals(top)) {
                    trav = list;
                }
            }

            if (trav.size() == 0)
                continue;

            for (T adj: trav) {
                if (!visited.contains(adj)) {
                    // Add to queue
                    q.add(adj);

                    // Add to minimum spanning tree
                    mst.add(top, adj);

                    // Mark as visited
                    visited.add(adj);
                }
            }
        }

        return mst;
    }

    /**
     * @return the adjacency list as a string
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (ArrayList<T> list: adjList) {
            sb.append(list.toString() + "\n");
        }

        return sb.toString();
    }

    public static void main (String... args) {
        DirectedGraph<Integer> g = new DirectedGraph<>();

        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 3);
        g.add(2, 4);
        g.add(2, 5);
        g.add(3, 4);
        g.add(6, 7);
        g.add(6, 8);
        g.add(7, 8);

        System.out.println(g + "\n\n");

        DirectedGraph<Integer> mst = g.BFS(1);
        DirectedGraph<Integer> mst2 = g.BFS(6);
        System.out.println(mst.toString());
        System.out.println(mst2.toString());
        System.out.println("TIME FOR DFS:\n");
        DirectedGraph<Integer> mstd = g.DFS(1);
        DirectedGraph<Integer> mstd2 = g.DFS(6);
        System.out.println(mstd.toString());
        System.out.println(mstd2.toString());
    }
}