package ID3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class ID3 {
    final static DecisionTree tree = new DecisionTree();

    public static void main(String[] args) {
        try {
            final var csv = loadCSV("data.csv");
            printArrayList(csv);
            buildTree(csv);
        } catch (FileNotFoundException e) {
            System.out.println("Hmm... something went wrong:\n" + e);
        }
        tree.print();
    }

    private static void buildTree(final List<ArrayList<String>> data) {
        final var dt = new DataTable(data);

        final var node = new Node(dt.findMaxGainAttribute());
        if (tree.isEmpty()) {
            tree.setRoot(node);
            node.setNodeType(Node.NodeType.ROOT);
        } else {
            tree.getCurrent().addChild(node);
            node.setParent(tree.getCurrent());
        }

        for (final var value : dt.getAttributeValues(dt.findMaxGainAttribute())) {
            final var childNode = new Node(value);
            node.addChild(childNode);
            tree.setCurrent(childNode);
            final var childData = dt.getWhere(dt.findMaxGainAttribute(), value);
            final var childTable = new DataTable(childData);
            if (childTable.getWhere("Result", "Yes").size() == 1)
                childNode.addChild(new Node("No"));
            else if (childTable.getWhere("Result", "No").size() == 1)
                childNode.addChild(new Node("Yes"));
            else
                buildTree(childData);
        }

        if (node.getChildren().size() > 0 && node.getNodeType() != Node.NodeType.ROOT)
            node.setNodeType(Node.NodeType.BRANCH);
    }

    private static List<ArrayList<String>> loadCSV(final String filePath) throws FileNotFoundException {
        final var data = new ArrayList<ArrayList<String>>();
        final var scan = new Scanner(new File(filePath));
        while (scan.hasNextLine())
            data.add(new ArrayList<>(Arrays.asList(scan.nextLine().split(";"))));
        return data;
    }

    private static void printArrayList(final List<ArrayList<String>> data) {
        var col = 0;
        final var maxCols = new ArrayList<Integer>();
        for (final var row : data) {
            for (final var item : row) {
                if (maxCols.size() <= col)
                    maxCols.add(item.length());
                else if (item.length() > maxCols.get(col))
                    maxCols.set(col, item.length());
                col++;
            }
            col = 0;
        }

        System.out.println("Parsing data:\n--------------------------------------------------");
        for (final var row : data) {
            for (final var item : row) {
                final var format = "| %-" + maxCols.get(col) + "s";
                System.out.printf(format, item);
                col++;
            }
            col = 0;
            System.out.println();
        }
        System.out.println("--------------------------------------------------\n");
    }
}