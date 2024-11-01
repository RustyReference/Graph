package lib;

import src.UndirectedGraph;

class A {
    private int a = 2;
    public A(int i) {
        a = i;
    }
    public void m(A another) {
        System.out.println("THIS: " + a);
        System.out.println("AND THIS: " + another.a);
    }

}



public class TestUndirectedGraph {
    private static int numPassed;
    public static void testAddVertex() {
        UndirectedGraph<Integer> gph = new UndirectedGraph<>();
        gph.add(1, 2);
        gph.add(2, 3);
        gph.add(3, 4);
        gph.add(5, 6);
        gph.add(5, 7);
        gph.add(6, 7);
        gph.add(6, 5);

        String testDisplay = gph.toString();
        String expectedDisplay = "[1, 2]\n"
                               + "[2, 1, 3]\n"
                               + "[3, 2, 4]\n"
                               + "[4, 3]\n"
                               + "[5, 6, 7]\n"
                               + "[6, 5, 7]\n"
                               + "[7, 5, 6]\n";
        assert testDisplay.equals(expectedDisplay);
        numPassed++;
    }

    public static void testRemoveVertex() {
        UndirectedGraph<Integer> gph = new UndirectedGraph<>();
        gph.add(1, 2);
        gph.add(2, 3);
        gph.add(3, 4);
        gph.add(5, 6);
        gph.add(5, 7);
        gph.add(6, 7);
        gph.add(6, 5);

        gph.deleteVertex(1);
        gph.deleteVertex(5);

        String testDisplay = gph.toString();
        String expectedDisplay = "[2, 3]\n"
                               + "[3, 2, 4]\n"
                               + "[4, 3]\n"
                               + "[6, 7]\n"
                               + "[7, 6]\n";
        assert testDisplay.equals(expectedDisplay);
        numPassed++;
    }

    public static void testRepeatAddVertex() {
        UndirectedGraph<String> gph = new UndirectedGraph<>();
        gph.add("hi", "there");
        gph.add("there", "my");
        gph.add("hi", "world");
        gph.add("hi", "world");
        gph.add("hi", "world");
        gph.add("hi", "world");

        String testDisplay = gph.toString();
        String expectedDisplay = "[hi, there, world]\n"
                               + "[there, hi, my]\n"
                               + "[my, there]\n"
                               + "[world, hi]\n";
        
        assert testDisplay.equals(expectedDisplay);
        numPassed++;
    }

    public static void testAddEdge() {
        UndirectedGraph<String> gph = new UndirectedGraph<>();
        gph.add("zigzag", "jonny");
        gph.add("banana", "boat");
        
        // Adding Edge
        gph.add("jonny", "banana");

        String testDisplay = gph.toString();
        String expectedDisplay = "[zigzag, jonny]\n"
                               + "[jonny, zigzag, banana]\n"
                               + "[banana, boat, jonny]\n"
                               + "[boat, banana]\n";
        assert testDisplay.equals(expectedDisplay);
        numPassed++;
    }

    public static void main(String[] args) {
        //Tests
        /* 
         * 
         */
         System.out.println("--------------------------");
         System.out.println("Running Tests for UndirectedGraph:");
         System.out.println("--------------------------\n");
         
         System.out.println("Testing addVertex();");
         testAddVertex();

         System.out.println("Testing removeVertex();");
         testRemoveVertex();
         
         System.out.println("Testing repetition of addVertex()");
         testRepeatAddVertex();

         System.out.println("Testing adding an edge functionality");
         testRemoveVertex();

         System.out.println("\nSuccessfully Passed " + numPassed + " tests.");

    }
}