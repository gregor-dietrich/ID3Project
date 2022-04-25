package ID3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class DataTable {
    private final ArrayList<String> labels;
    private final HashMap<String, HashMap<String, Integer>> yesCounts;
    private final HashMap<String, HashMap<String, Integer>> noCounts;
    private final List<ArrayList<String>> data;

    public DataTable(final List<ArrayList<String>> data) {
        this.data = data;
        labels = new ArrayList<>();
        yesCounts = new HashMap<>();
        noCounts = new HashMap<>();

        var parseErrors = 0;
        for (var i = 0; i < data.size(); i++) {
            final var row = data.get(i);
            if (i == 0)
                for (final var label : row) {
                    labels.add(label);
                    yesCounts.put(label, new HashMap<>());
                    noCounts.put(label, new HashMap<>());
                }
            else {
                final var decision = row.get(row.size() - 1);
                for (var j = 0; j < row.size(); j++)
                    if (Objects.equals(decision, "Yes"))
                        yesCounts.get(labels.get(j)).merge(row.get(j), 1, Integer::sum);
                    else if (Objects.equals(decision, "No"))
                        noCounts.get(labels.get(j)).merge(row.get(j), 1, Integer::sum);
                    else
                        parseErrors++;
            }
        }

        if (parseErrors != 0)
            System.out.println("WARNING: I ran into " + parseErrors + " errors while parsing " + "!");
    }

    private static double logBase2(final double n) {
        return Math.log(n) / Math.log(2);
    }

    private static double calcEntropy(final int yes, final int no) {
        final double total = yes + no;
        return (-1 * (yes / total)) * logBase2(yes / total) - (no / total) * logBase2(no / total);
    }

    private double calcEntropy(final String attribute, final String value) {
        if (Objects.equals(attribute, "Result"))
            return calcEntropy(yesCounts.get(attribute).get("Yes"), noCounts.get(attribute).get("No"));
        final var result = calcEntropy(yesCounts.get(attribute).get(value), noCounts.get(attribute).get(value));
        return Double.isNaN(result) ? 0 : result;
    }

    private double calcCaseCount(final String attribute, final String value) {
        if (!yesCounts.get(attribute).containsKey(value)) yesCounts.get(attribute).put(value, 0);
        if (!noCounts.get(attribute).containsKey(value)) noCounts.get(attribute).put(value, 0);
        return yesCounts.get(attribute).get(value) + noCounts.get(attribute).get(value);
    }

    private double calcGain(final String attribute) {
        var result = calcEntropy("Result", "");
        for (final var key : yesCounts.get(attribute).keySet())
            result -= (calcCaseCount(attribute, key) / data.size()) * calcEntropy(attribute, key);
        return result;
    }

    private double calcMaxGain() {
        var maxGain = 0.0;
        for (final var label : labels)
            if (calcGain(label) > maxGain && !Objects.equals(label, "Result"))
                maxGain = calcGain(label);
        return maxGain;
    }

    public String findMaxGainAttribute() {
        for (final var label : labels)
            if (calcGain(label) == calcMaxGain())
                return label;
        return "";
    }

    public ArrayList<String> getAttributeValues(final String attribute) {
        final var keys = new ArrayList<>(yesCounts.get(attribute).keySet());
        for (final var key : noCounts.get(attribute).keySet())
            if (!keys.contains(key))
                keys.add(key);
        return keys;
    }

    public List<ArrayList<String>> getWhere(final String attribute, final String value) {
        final var result = new ArrayList<ArrayList<String>>();
        result.add(labels);
        for (final var row : data) {
            if (Objects.equals(row.get(labels.indexOf(attribute)), value))
                result.add(row);
        }
        return result;
    }
}