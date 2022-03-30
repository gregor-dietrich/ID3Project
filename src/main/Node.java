package main;

import java.util.ArrayList;

public class Node {
    private String data;
    private Node parent;
    private final ArrayList<Node> children = new ArrayList<>();

    public Node() {
        this("");
    }

    public Node(final String data) {
        setData(data);
    }

    public void printPreOrder() {
        System.out.print(data + " ");
    }

    public void printPostOrder() {
        System.out.print(data + " ");
    }

    public void printInOrder() {
        System.out.print(data + " ");
    }

    public void print() {
        print("", true);
    }

    private void print(final String prefix, final boolean isTail) {
        System.out.println(prefix + (isTail ? "\\" : "|") + "-- " + data);
        for (final var child : children)
            child.print(prefix + (isTail ? "    " : "|   "), false);
        if (!children.isEmpty())
            children.get(children.size() - 1).print(prefix + (isTail ? "    " : "|   "), true);
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public boolean addChild(final Node child) { return children.add(child); }

    public boolean removeChild(final Node child) { return children.remove(child); }

    public void removeChildren() { children.clear(); }
}
