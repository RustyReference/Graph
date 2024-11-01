package src;

import java.util.*;
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
    public void DFS(T start) {
        if (adjList.size() == 0) {
            return;
        }

        HashMap<T, Boolean> visited = new HashMap<>();
        StringBuilder compositeMessage = new StringBuilder();
        for (ArrayList<T> list: adjList) {
            visited.put(list.get(0), false);
        }
        
        helperDFS(visited, start, start, compositeMessage);
        System.out.println(compositeMessage.toString());
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
    private void helperDFS(HashMap<T, Boolean> visited, T current, T beginning,
                           StringBuilder message) {
        // Set current vertex as visited
        visited.put(current, true);
        message.append(current.toString()).append(" ");

        ArrayList<T> listToTraverse = new ArrayList<T>();
        for (ArrayList<T> list: adjList) {
            if (list.get(0).equals(current)) {
                listToTraverse = list;
                break;
            }
        }

        if (listToTraverse.size() == 0) {
            throw new DFSException("ArrayList containing vertex to traverse from should not be empty.");
        }

        for (int ind = 1; ind < listToTraverse.size(); ind++) {
            T vertex = listToTraverse.get(ind);
            if (!visited.get(vertex)) {
                helperDFS(visited, vertex, beginning, message);
            }
        }

        if (!current.equals(beginning))
            message.append("backtrack ");
        else message.append("End DFS");
    }

    /**
     * @return The number of verticies in the graph
     */
    public int size() {
        return adjList.size();
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
        
    }
}