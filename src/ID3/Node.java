package ID3;

import java.util.HashMap;

public final class Node {
    private final HashMap<String, Node> children = new HashMap<>();
    private String data;
    private Node parent;
    private NodeType type;

    public Node(final String data) {
        setData(data);
    }

    public NodeType getType() {
        return type;
    }

    public void setType(final NodeType type) {
        this.type = type;
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

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public HashMap<String, Node> getChildren() {
        return children;
    }

    public void addChild(final String name) {
        children.put(name, new Node(name));
    }

    public void removeChild(final String name) {
        children.remove(name);
    }

    public void removeChildren() {
        children.clear();
    }

    public NodeType getNodeType() {
        return type;
    }

    public void setNodeType(final NodeType type) {
        this.type = type;
    }

    public void print(final String prefix, final boolean isTail) {
        System.out.println(prefix + (isTail ? "\\--" : "|--") + data);
        final var childrenList = children.keySet().stream().toList();
        for (int i = 0; i < childrenList.size() - 1; i++)
            children.get(childrenList.get(i)).print(prefix + (isTail ? "  " : "| "), false);
        if (children.size() > 0)
            children.get(childrenList.get(childrenList.size() - 1)).print(prefix + (isTail ? "  " : "| "), true);
    }

    public enum NodeType {
        ROOTNODE,
        LEAFNODE,
        BRANCH
    }
}
