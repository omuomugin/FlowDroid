package soot.jimple.infoflow.nullabilityAnalysis.rwpair;

public class Edge {
    private String leftMethodName;
    private String rightMethodName;

    public Edge(String leftMethodName, String rightMethodName) {
        this.leftMethodName = "<" + leftMethodName + ">";
        this.rightMethodName = "<" + rightMethodName + ">";
    }

    public String getLefttMethodName() {
        return leftMethodName;
    }

    public String getRightMethodName() {
        return rightMethodName;
    }
}
