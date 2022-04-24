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
            buildTree(loadCSV("data.csv"));
        }
        catch (FileNotFoundException e) {
            System.out.println("Hmm... something went wrong:\n" + e);
        }
    }

    private static void buildTree(final List<ArrayList<String>> data) {
        printArrayList(data);
        final var dt = new DataTable(data);
        /*
        System.out.println(dt.calcGain("Outlook"));
        System.out.println(dt.calcGain("Temperature"));
        System.out.println(dt.calcGain("Humidity"));
        System.out.println(dt.calcGain("Wind"));
        System.out.println(dt.findMaxGainAttribute());
        System.out.println(dt.calcMaxGain());
        */

        final var node = new Node(dt.findMaxGainAttribute());
        if (tree.isEmpty()) {
            tree.setRoot(node);
            node.setNodeType(Node.NodeType.ROOT);
        }
        else {
            tree.getCurrent().addChild(node);
            node.setParent(tree.getCurrent());
        }
        tree.setCurrent(node);

        for (final var value : dt.getAttributeValues(dt.findMaxGainAttribute())) {
            var childNode = new Node(value);
            node.addChild(childNode);
            tree.setCurrent(childNode);
            var childData = dt.getWhere(dt.findMaxGainAttribute(), value);
            buildTree(childData);
        }

        if (node.getChildren().size() > 0 && node.getNodeType() != Node.NodeType.ROOT)
            node.setNodeType(Node.NodeType.BRANCH);
        tree.setCurrent(node);

        tree.print();
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

        System.out.println("--------------------------------------------------");
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