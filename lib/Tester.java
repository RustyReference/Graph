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



public class Tester {
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

    public static void main(String[] args) {
        //Tests
        /* 
         * 
         System.out.println("--------------------------");
         System.out.println("Running Tests for UndirectedGraph:");
         System.out.println("--------------------------\n");
         
         System.out.println("Testing addVertex();");
         testAddVertex();
         
         System.out.println("Successfully Passed " + numPassed + " tests.");
         */

         A a = new A(1);
         A b = new A(4);
         a.m(b);
    }
}