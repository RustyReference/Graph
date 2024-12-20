/**
 * @RustyReference
 * Date: 12-19-2024
 * An implementation of an undirected graph using an adjacencylist, complete
 * with a DFS and BFS traversal and add/remove operations for edges and
 * verticies.
 */

package src;

import java.util.*;

import lib.BFSException;
import lib.DFSException;

public class UndirectedGraph<T> implements Graph<T> {
    private ArrayList<ArrayList<T>> adjList;

    public UndirectedGraph() {
        adjList = new ArrayList<>();
    }

    /**
     * Adds a the verticies 'first' and 'second' to the graph and connects 
     * 'first' and 'second'
     * @param first the first vertex
     * @param second the first vertex
     * @throws IllegalArgumentException when either of the arguments is null
     */
    public void add(T first, T second) throws IllegalArgumentException {
        if (first == null || second == null) {
            throw new IllegalArgumentException(
                "Passed null argument to undirected graph");
        }

        //Checks if first or second exist in the graph
        boolean foundFirst = false;
        boolean foundSecond = false;
        ArrayList<T> firstTemp, secondTemp;
        firstTemp = secondTemp = null;

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

        // Depending on if the graph already contains the 'first' or
        // 'second' values it is updated to make it so that they both
        // are verticies in the graph and have each other in their
        // adjacency lists.
        updateListsAfterAdding(foundFirst, foundSecond,
                               first, second,
                               firstTemp, secondTemp);
    }

    /**
     * Updates the Lists in the graph so that they all have the corresponding
     * edges connecting each node.
     * @param foundFirst whether or not the element in 'first' of 
     *                   add(T first, T second) already exists in 
     *                   the graph.
     * @param foundSecond whether or not the element in 'second' of 
     *                    add(T first, T second) already exists in 
     *                    the graph.
     * @param first the first parameter involved in adding an edge
     *              in the call to add(T first, T second)
     * @param second the second parameter involved in adding an edge
     *               in the call to add(T first, T second)
     * @param firstTemp a temporary holder for the ArrayList in
     *                  adjList that contains the first argument 
     *                  involved in adding an edge, or null if 
     *                  the first argument is not found.
     * @param secondTemp a temporary holder for the ArrayList in
     *                   adjList that contains the second argument 
     *                   involved in adding an edge, or null if 
     *                   the second argument is not found.
     */
    private void updateListsAfterAdding(boolean foundFirst,
                             boolean foundSecond,
                             T first,
                             T second,
                             ArrayList<T> firstTemp,
                             ArrayList<T> secondTemp) {
        if (foundFirst && foundSecond) {
            if (!firstTemp.contains(second))
                firstTemp.add(second);
            
            if (!secondTemp.contains(first)) 
                secondTemp.add(first);
            return;
        }

        if (!foundFirst && !foundSecond) {
            firstTemp = new ArrayList<>();
            firstTemp.add(first);
            firstTemp.add(second);
            adjList.add(firstTemp);

            secondTemp = new ArrayList<>();
            secondTemp.add(second);
            secondTemp.add(first);
            adjList.add(secondTemp);
        }

        if (foundFirst) {
            firstTemp.add(second);
            secondTemp = new ArrayList<>();
            secondTemp.add(second);
            secondTemp.add(first);
            adjList.add(secondTemp);
        } 

        if (foundSecond) {
            secondTemp.add(first);
            firstTemp = new ArrayList<>();
            firstTemp.add(first);
            firstTemp.add(second);
            adjList.add(firstTemp);
        }       
    }

    /**
     * Deletes a vertex from the graph and all associated edges
     * conencting to it
     *      Precondition: The graph is expected to be empty
     * @param vertex the vertex to delete
     */
    public void deleteVertex(T vertex) {
        int size = adjList.size();
        if (size == 0)
            throw new IllegalStateException("Cannot delete from empty graph");
        
        // Remove ArrayList in adjList associated with 'vertex'
        for (int ind = size - 1; ind >= 0; ind--) {
            ArrayList<T> currentList = adjList.get(ind);
            if (currentList.get(0).equals(vertex)) 
                adjList.remove(ind);
            else {
                while (currentList.contains(vertex)) {
                    currentList.remove(vertex);
                }
            }
        }
    }

    /**
     * Deletes an edge between two verticies by removing their adjacencies
     * to each other
     * 
     * @param first the first vertex involved
     * @param second the second vertex involved
     */
    public void deleteEdge(T first, T second) {
        for (ArrayList<T> list: adjList) {
            T vertex = list.get(0);
            if (vertex.equals(first) && list.contains(second)) {
                list.remove(second);
            }

            if (vertex.equals(second) && list.contains(first)) {
                list.remove(first);
            }
        }
    }

    /**
     * Traverses through the graph using depth-first search and prints each 
     * iteration of the search and each instance where the search back tracks
     * @param start the vertex from which the search begins
     */
    public UndirectedGraph<T> DFS(T start) {
        UndirectedGraph<T> mst = new UndirectedGraph<>();
        HashMap<T, Boolean> visited = new HashMap<>();
        for (ArrayList<T> list: adjList) {
            visited.put(list.get(0), false);
        }
        
        helperDFS(visited, start, mst);

        return mst;
    }

    /**
     * Assists with the DFS traversal by recursively calling itself for each 
     * vertex the current vertex is adjacent to. Backtracks when all adjacent
     * verticies to the current vertex have been completely traversed
     * @param visited a map showing which verticies have been visited
     * @param current the current vertex being visited
     * @param beginning the vertex the DFS traversal began in 
     * @param message the StringBuilder that contains all nodes visited
     *                all instances of backtracking.
     */
    private void helperDFS(HashMap<T, Boolean> visited, T current,
                           UndirectedGraph<T> mst) {
        // Set current vertex as visited
        visited.put(current, true);

        ArrayList<T> listToTraverse = new ArrayList<T>();
        for (ArrayList<T> list: adjList) {
            if (list.get(0).equals(current)) {
                listToTraverse = list;
                break;
            }
        }

        for (int ind = 1; ind < listToTraverse.size(); ind++) {
            T vertex = listToTraverse.get(ind);
            if (!visited.get(vertex)) {
                mst.add(current, vertex);
                helperDFS(visited, vertex, mst);
            }
        }
    }

    /**
     * @return The number of verticies in the graph
     */
    public int size() {
        return adjList.size();
    }

    /**
     * Performs a bread-first-search traversal in the undirected grpah 
     * starting at the specified vertex and returns a minimum spanning tree 
     * containing the node specified. It may not include all the verticies 
     * of the original graph if the original graph is not connected.
     * @param start the specified vertex where the traversal begins
     * @return a minimum spanning tree containing 'start'
     */
    public UndirectedGraph<T> BFS(T start) {
        HashSet<T> visited = new HashSet<>();
        UndirectedGraph<T> mst = new UndirectedGraph<>(); // Minimum spanning tree as adjacency list
        
        Queue<T> q = new LinkedList<>();
        
        q.add(start);
        visited.add(start);
        
        while (!q.isEmpty()) {
            T top = q.remove();
            ArrayList<T> trav = new ArrayList<>();
            for (ArrayList<T> list: adjList) {
                if (list.get(0).equals(top)) {
                    trav = list;
                }
            }
            
            for (T neighbor: trav) {
                if (neighbor.equals(top)) 
                continue;
                
                if (!visited.contains(neighbor)) {
                    q.add(neighbor);
                    
                    // Add edge from current to neighbor to MST
                    mst.add(top, neighbor);
                    
                    // Mark neighbor as visited
                    visited.add(neighbor);
                }
            }
        }
        
        return mst;
    }

    /**
     * @return the graph as an adjacency list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<T> list: adjList) {
            sb.append(list.toString()).append("\n");
        }

        return sb.toString();
    }
    
    public static void main(String[] args) {
        UndirectedGraph<Integer> g = new UndirectedGraph<>();
        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 3);
        g.add(2, 4);
        g.add(2, 5);
        g.add(3, 4);
        g.add(7, 8);
        g.add(7, 6);
        g.add(6, 7);
        g.add(6, 8);

        UndirectedGraph<Integer> mst = g.DFS(1);
        UndirectedGraph<Integer> mstb = g.BFS(1);
        System.out.println(g.toString());
        System.out.println("MINIMUM");
        System.out.println(mst.toString());

        System.out.println("BFS: ");
        System.out.println(mstb.toString() + "\n");

        g.deleteEdge(4, 3);
        System.out.println("DFS: \n" + mst.toString());

        System.out.println(g);

        g.deleteVertex(2);
        System.out.println(g);
    }
}