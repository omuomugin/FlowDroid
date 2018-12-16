package soot.jimple.infoflow.nullabilityAnalysis.data;

import soot.jimple.infoflow.nullabilityAnalysis.Status;

public class AbstractParams {
    private String name;
    private Status status;

    public AbstractParams(String name) {
        this.name = name;
        this.status = Status.UNKNOWN;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        if (this.status != Status.Nullable) {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        return name + " : " + Status.toLabel(status);
    }
}
