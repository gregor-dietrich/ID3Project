package main;

import java.util.Objects;

public class DecisionTree {
    private Node root;
    private Node currentNode;

    public DecisionTree() {
        this(null);
    }

    public DecisionTree(final Node root) {
        setRoot(root);
    }

    public void printPreOrder() {
        root.printPreOrder();
    }

    public void printPostOrder() {
        root.printPostOrder();
    }

    public void printInOrder() {
        root.printInOrder();
    }

    public void print() {
        if (root != null) root.print();
        else System.out.println("Tree is empty.");
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(final Node node) {
        if (node == null) return 0;
        int count = 1;

        return count;
    }

    public boolean search(final String data) {
        return search(root, data);
    }

    private boolean search(final Node node, final String data) {
        if (Objects.equals(node.getData(), data)) return true;

        return false;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(final Node root) {
        this.root = root;
    }

    public Node getCurrent() {
        return currentNode;
    }

    public void setCurrent(final Node current) {
        this.currentNode = current;
    }
}
