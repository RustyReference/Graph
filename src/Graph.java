package src;

/**
 * 
 */

public interface Graph<T> {
    void add(T first, T second) throws IllegalArgumentException;
    void delete(T vertex);
}