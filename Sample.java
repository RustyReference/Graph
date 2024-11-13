import src.UndirectedGraph;

public class Sample {
    public static void main(String[] args) {
        UndirectedGraph<String> g = new UndirectedGraph<>();
        g.add("A", "B");
        g.add("c", "d");
        g.add("d", "e");
        g.add("A", "c");

        g.DFS("A");
    }
}
