package soot.jimple.infoflow.nullabilityAnalysis.data;

import soot.jimple.infoflow.nullabilityAnalysis.Status;

public class AbstractParams {
    private String name;
    private Status status;

    public AbstractParams(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " : " + Status.toLabel(status);
    }
}
