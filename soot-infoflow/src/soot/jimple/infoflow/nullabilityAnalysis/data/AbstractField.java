package soot.jimple.infoflow.nullabilityAnalysis.data;

import soot.SootField;
import soot.jimple.infoflow.nullabilityAnalysis.Status;

public class AbstractField {
    private SootField field;
    private Status status;

    public AbstractField(SootField sootField) {
        this.field = sootField;
        status = Status.UNKNOWN;
    }

    /**
     * Update Status
     *
     * @param value
     */
    public void updateStatus(Status value) {
        this.status = value;
    }

    @Override
    public String toString() {
        return "- " + field.getName() + " : " + Status.toLabel(status) + "\n";
    }
}
