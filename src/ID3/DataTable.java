package ID3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class DataTable {
    private final ArrayList<String> labels;
    private final HashMap<String, HashMap<String, Integer>> yesCounts;
    private final HashMap<String, HashMap<String, Integer>> noCounts;

    public DataTable(final List<ArrayList<String>> trainingData) {
        labels = new ArrayList<>();
        yesCounts = new HashMap<>();
        noCounts = new HashMap<>();

        var parseErrors = 0;
        for (var i = 0; i < trainingData.size(); i++) {
            var row = trainingData.get(i);
            if (i == 0)
                for (final var label : row) {
                    labels.add(label);
                    yesCounts.put(label, new HashMap<>());
                    noCounts.put(label, new HashMap<>());
                }
            else {
                var decision = row.get(row.size() - 1);
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

    public double calcEntropy(final String attribute, final String value) {
        if (!yesCounts.get(attribute).containsKey(value)) yesCounts.get(attribute).put(value, 0);
        if (!noCounts.get(attribute).containsKey(value)) noCounts.get(attribute).put(value, 0);
        if (Objects.equals(attribute, "Result"))
            return calcEntropy(yesCounts.get(attribute).get("Yes"), noCounts.get(attribute).get("No"));
        final var result = calcEntropy(yesCounts.get(attribute).get(value), noCounts.get(attribute).get(value));
        return Double.isNaN(result) ? 0 : result;
    }

    public double calcResultEntropy() {
        return calcEntropy("Result", "");
    }

    public double calcCaseCount(final String attribute, final String value) {
        if (!yesCounts.get(attribute).containsKey(value)) yesCounts.get(attribute).put(value, 0);
        if (!noCounts.get(attribute).containsKey(value)) noCounts.get(attribute).put(value, 0);
        return yesCounts.get(attribute).get(value) + noCounts.get(attribute).get(value);
    }

    public double calcGain(final String attribute) {
        final var totalCases = yesCounts.get("Result").get("Yes") + noCounts.get("Result").get("No");
        var result = calcResultEntropy();
        for (final var key : yesCounts.get(attribute).keySet())
            result -= (calcCaseCount(attribute, key) / totalCases) * calcEntropy(attribute, key);
        return result;
    }

    public double calcMaxGain() {
        var maxGain = 0.0;
        for (final var label : labels)
            if (calcGain(label) > maxGain && !Objects.equals(label, "Result")) {
                maxGain = calcGain(label);
            }
        return maxGain;
    }

    public String findMaxGainAttribute() {
        for (final var label : labels)
            if (calcGain(label) == calcMaxGain())
                return label;
        return "";
    }
}