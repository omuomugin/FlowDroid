package soot.jimple.infoflow.data.abstractValues;

import soot.jimple.infoflow.data.SootMethodAndClass;

import java.util.ArrayList;
import java.util.List;

public class AbstractMethod {
    private SootMethodAndClass method;
    public List<AbstractParams> params;

    public AbstractMethod(SootMethodAndClass method) {
        this.method = method;
        this.params = new ArrayList<>();
        for (String p : method.getParameters())
            this.params.add(new AbstractParams(p, Status.UNKNOWN));
    }

    public void updateStatus(int index, Status status) {
        AbstractParams abstractParams = this.params.get(index);
        abstractParams.updateStatus(status);
        this.params.set(index, abstractParams);
    }

    @Override
    public String toString() {
        String str = "method : " + method.getMethodName() + "\n";

        for (AbstractParams param : params) {
            str += "----------> " + param.toString() + "\n";
        }

        return str;
    }
}
