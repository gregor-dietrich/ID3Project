package ID3;

import java.util.HashMap;

public final class Node {
    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public enum NodeType {
        ROOTNODE,
        LEAFNODE,
        BRANCH
    }
    private String data;
    private Node parent;
    private NodeType type;
    private final HashMap<String, Node> children = new HashMap<>();

    public Node() {
        this("");
    }

    public Node(final String data) {
        setData(data);
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

    public void addChild(final String name, final Node node) {
        children.put(name, node);
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

    public void print(final String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "\\--" : "|--") + data);
        var childrenList = children.keySet().stream().toList();
        for (int i = 0; i < childrenList.size() - 1; i++)
            children.get(childrenList.get(i)).print(prefix + (isTail ? "  " : "| "), false);
        if (children.size() > 0)
            children.get(childrenList.get(childrenList.size() - 1)).print(prefix + (isTail ? "  " : "| "), true);
    }
}
