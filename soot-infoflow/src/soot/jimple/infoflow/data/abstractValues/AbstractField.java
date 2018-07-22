package soot.jimple.infoflow.data.abstractValues;

import soot.SootField;

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
