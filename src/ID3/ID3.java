package ID3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class ID3 {
    public static void main(String[] args) throws FileNotFoundException {
        final var dt = new DataTable(loadCSV("data.csv"));

        System.out.println(dt.calcGain("Outlook"));
        System.out.println(dt.calcGain("Temperature"));
        System.out.println(dt.calcGain("Humidity"));
        System.out.println(dt.calcGain("Wind"));
        System.out.println(dt.findMaxGainAttribute());
        System.out.println(dt.calcMaxGain());

        final var tree = new DecisionTree();
    }

    private static List<ArrayList<String>> loadCSV(final String filePath) throws FileNotFoundException {
        var data = new ArrayList<ArrayList<String>>();
        var scan = new Scanner(new File(filePath));
        while (scan.hasNextLine()) {
            var line = scan.nextLine();
            var lineArrayList = new ArrayList<>(Arrays.asList(line.split(";")));
            data.add(lineArrayList);
        }
        return data;
    }

    public static void printArrayList(final List<ArrayList<String>> data) {
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

        for (final var row : data) {
            for (final var item : row) {
                final var format = "| %-" + maxCols.get(col) + "s";
                System.out.printf(format, item);
                col++;
            }
            col = 0;
            System.out.println();
        }
    }
}