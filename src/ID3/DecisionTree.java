package ID3;

public final class DecisionTree {
    private Node root;
    private Node currentNode;

    public DecisionTree() {
        this(null);
    }

    public DecisionTree(final Node root) {
        setRoot(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void print() {
        System.out.println("Tree Structure:\n--------------------------------------------------");
        root.print("", true);
        System.out.println("--------------------------------------------------");
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
