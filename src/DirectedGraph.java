package src;

import java.util.*;

public class DirectedGraph<T> implements Graph<T> {
    private ArrayList<ArrayList<T>> adjList;

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
            firstTemp.add(first);
            firstTemp.add(second);
            adjList.add(firstTemp);

            // Note how no corresponding edge is added from second to first
            secondTemp = new ArrayList<>();
            secondTemp.add(second);
            adjList.add(secondTemp);
        }

        // If only the first is found in the list, then create a new list with 
        // the second value and create an edge from the first to the second.
        if (foundFirst) {
            firstTemp.add(second);
            secondTemp = new ArrayList<>();
            secondTemp.add(second);
            adjList.add(secondTemp);
        }

        if (foundSecond) {
            firstTemp = new ArrayList<>();
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
}