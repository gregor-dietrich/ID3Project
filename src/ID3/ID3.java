package ID3;

public final class ID3 {
    public static void main(String[] args) {
        var tree = new DecisionTree();

        var csv = new CSVReader("data.csv");

        System.out.println(csv.calcGain("Outlook"));
        System.out.println(csv.calcGain("Temperature"));
        System.out.println(csv.calcGain("Humidity"));
        System.out.println(csv.calcGain("Wind"));

        System.out.println(csv.findMaxGainAttribute());
        System.out.println(csv.calcMaxGain());
    }
}