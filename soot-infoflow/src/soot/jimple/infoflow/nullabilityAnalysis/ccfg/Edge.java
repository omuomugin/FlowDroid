package soot.jimple.infoflow.nullabilityAnalysis.ccfg;

public class Edge {

    public static EdgeType convertToEdgeType(String type) {
        if (type.equals("event")) {
            return EdgeType.EVENT;
        } else if (type.equals("lifecycle")) {
            return EdgeType.LIFECYCLE_EVENT;
        } else {
            return EdgeType.UNKNOWN;
        }
    }

    private String sourceMethodName;
    private EdgeType type;
    private String eventType;
    private String declaringClassName;

    public Edge(String sourceMethodName, EdgeType type, String eventType, String declaringClassName) {
        this.sourceMethodName = "<" + sourceMethodName + ">";
        this.type = type;
        this.eventType = eventType;
        this.declaringClassName = declaringClassName;
    }

    public String getSourceMethodName() {
        return sourceMethodName;
    }

    public EdgeType getType() {
        return type;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDeclaringClassName() {
        return declaringClassName;
    }
}
