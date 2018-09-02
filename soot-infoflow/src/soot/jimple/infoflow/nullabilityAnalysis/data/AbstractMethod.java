package soot.jimple.infoflow.nullabilityAnalysis.data;

import soot.jimple.infoflow.data.SootMethodAndClass;
import soot.jimple.infoflow.nullabilityAnalysis.Status;

import java.util.ArrayList;
import java.util.List;

public class AbstractMethod {
    private String methodName;
    public List<AbstractParams> params;
    private Status returnStatus;

    public AbstractMethod(SootMethodAndClass method) {
        this.methodName = method.getMethodName();
        this.params = new ArrayList<>();
        for (String p : method.getParameters())
            this.params.add(new AbstractParams(p, Status.UNKNOWN));
        this.returnStatus = Status.UNKNOWN;
    }

    public void updateParamsStatus(int index, Status status) {
        AbstractParams abstractParams = this.params.get(index);
        abstractParams.updateStatus(status);
        this.params.set(index, abstractParams);
    }

    public void updateReturnStatus(Status status) {
        this.returnStatus = status;
    }

    @Override
    public String toString() {
        String str = "method : " + this.methodName + "\n";

        str += "returns " + this.returnStatus.name() + "\n";

        for (AbstractParams param : params) {
            str += "- " + param.toString() + "\n";
        }

        return str;
    }
}
