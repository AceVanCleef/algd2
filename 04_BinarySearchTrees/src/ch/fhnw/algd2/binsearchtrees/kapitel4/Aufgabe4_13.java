package ch.fhnw.algd2.binsearchtrees.kapitel4;

/**
 * ETH Zürich, Leitprogramm, Binäre Suchbäume ----
 * In dieser Klasse wird ein Binärbaum erzeugt und die Methode delete()
 * aufgerufen. Sie müssen an dieser Klasse nichts verändern, rufen Sie einfach
 * die main-Methode auf. So können Sie Ihre Implementation testen.
 *
 * @author Björn Steffen, Timur Erdag, Christina Class
 */
public class Aufgabe4_13 
{

    public static void main( String[] args ) 
    {
        System.out.println("Aufgabe 4.13\n*****\n");
        BinSearchTree root = makeTree();
    
        System.out.println("Inorder Ausgabe des Baumes: ");
        root.inorder(root);
        System.out.println("\n");
        System.out.println ("Löschen der 20 (Blatt, Fall 1)");
        root = root.delete(root,20);
        System.out.println("Inorder Ausgabe des Baumes nach dem Löschen: ");
        root.inorder(root);
        System.out.println("\n");
        System.out.println ("Löschen der 4 (Fall 2)");
        root = root.delete(root,4);
        System.out.println("Inorder Ausgabe des Baumes nach dem Löschen: ");
        root.inorder(root);
        System.out.println("\n");
        System.out.println ("Löschen der 7 (2 Vorkommen, jeweils Fall 2)");
        root = root.delete(root,7);
        System.out.println("Inorder Ausgabe des Baumes nach dem Löschen: ");
        root.inorder(root);
        System.out.println("\n");
        System.out.println ("Löschen der 6 (Wurzel, Fall 3)");
        root = root.delete(root,6);
        System.out.println("Inorder Ausgabe des Baumes nach dem Löschen: ");
        root.inorder(root);         
        System.out.println("\n");
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