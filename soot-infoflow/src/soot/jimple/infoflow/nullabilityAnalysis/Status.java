package soot.jimple.infoflow.nullabilityAnalysis;

public enum Status {
    Nullable,
    NonNull,
    UNKNOWN;

    static public String toLabel(Status status) {
        switch (status) {
            case Nullable: {
                return "Nullable";
            }
            case NonNull: {
                return "NonNull";
            }
            case UNKNOWN: {
                return "Unknown";
            }
        }

        return "NOT FOUND";
    }
}
