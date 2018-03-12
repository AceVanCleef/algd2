package ch.fhnw.algd2.binsearchtrees.kapitel4;

/**
 * ETH Zürich, Leitprogramm, Binäre Suchbäume ----
 * In dieser Klasse wird ein Binärbaum erzeugt und die Methode insert()
 * aufgerufen. Sie müssen an dieser Klasse nichts verändern, rufen Sie einfach
 * die main-Methode auf. So können Sie Ihre Implementation testen.
 *
 * @author Björn Steffen, Timur Erdag, Christina Class
 */
public class Aufgabe4_8 {

    public static void main( String[] args ) 
    {
        System.out.println( "Aufgabe 4.8\n*****\n" );
        BinSearchTree root = makeTree();
    
        System.out.println( "Einfügen der 27:" );
        System.out.println ("Ihre Ausgabe der besuchten Knoten:");
        root = root.insert(root, 27);
        System.out.println( "\nDie korrekte Ausgabe:" );
        System.out.println( "6 7 10 18 20" );
        System.out.println();
        
        System.out.println( "Einfügen der 3:" );
        System.out.println ("Ihre Ausgabe der besuchten Knoten:");
        root = root.insert(root, 3);
        System.out.println( "\nDie korrekte Ausgabe:" );
        System.out.println( "6 3 4" );
    }

    private static BinSearchTree makeTree() 
    {
        BinSearchTree root = new BinSearchTree(6);
        BinSearchTree node1 = new BinSearchTree(3);
        BinSearchTree node2 = new BinSearchTree(2);
        BinSearchTree node3 = new BinSearchTree(4);
        BinSearchTree node4 = new BinSearchTree(7);
        BinSearchTree node5 = new BinSearchTree(10);
        BinSearchTree node6 = new BinSearchTree(7);
        BinSearchTree node7 = new BinSearchTree(18);
        BinSearchTree node8 = new BinSearchTree(14);
        BinSearchTree node9 = new BinSearchTree(20);

        root.setLeft(node1);
        node1.setLeft(node2);
        node1.setRight(node3);
        root.setRight(node4);
        node4.setRight(node5);
        node5.setLeft(node6);
        node5.setRight(node7);
        node7.setLeft(node8);
        node7.setRight(node9);
        
        return root;
    }
}