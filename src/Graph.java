package src;

/**
 * 
 */
public interface Graph<T> {
    //void add(T vertex) throws IllegalArgumentException;
    void add(T first, T second) throws IllegalArgumentException;
    void deleteVertex(T vertex);
    void deleteEdge(T first, T second);
}