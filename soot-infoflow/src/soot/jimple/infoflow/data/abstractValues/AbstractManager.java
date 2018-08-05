package soot.jimple.infoflow.data.abstractValues;

import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.Value;
import soot.jimple.NullConstant;
import soot.jimple.infoflow.results.ResultSinkInfo;
import soot.jimple.infoflow.results.ResultSourceInfo;
import soot.jimple.infoflow.sourcesSinks.definitions.MethodSourceSinkDefinition;
import soot.jimple.internal.JAssignStmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AbstractManager {

    private Map<SootClass, AbstractClass> abstractClasses;

    public AbstractManager(Scene scene) {
        this.abstractClasses = new HashMap<>();
        for (SootClass sootClass : scene.getClasses()) {
            this.abstractClasses.put(sootClass, new AbstractClass(sootClass));
        }
    }

    public void updateMethodStatus(SootClass clazz, ResultSinkInfo sink, Set<ResultSourceInfo> source, Status status) {
        AbstractClass abstractClass = this.abstractClasses.get(clazz);
        if (abstractClass != null) {
            for (ResultSourceInfo sourceInfo : source) {
                Value sourceValue = ((JAssignStmt) sourceInfo.getStmt()).leftBox.getValue();
                List<Value> sinkValues = sink.getStmt().getInvokeExpr().getArgs();

                for (int i = 0; i < sinkValues.size(); i++) {
                    // this is when constant value `null` is passed directly to the method ( e.g hoge(null) )
                    if (sinkValues.get(i) instanceof NullConstant) {
                        abstractClass.updateMethodStatus(((MethodSourceSinkDefinition) sink.getDefinition()).getMethod(), i, Status.Nullable);
                    }

                    // this is when null is passed indirectly to the method ( e.g val a = null; hoge(a) )
                    else if (sourceValue.equals(sinkValues.get(i))) {
                        abstractClass.updateMethodStatus(((MethodSourceSinkDefinition) sink.getDefinition()).getMethod(), i, status);
                    }
                }
            }
        }
    }

    public void updateFieldStatus(SootClass clazz, SootField sootField, Status status) {
        AbstractClass abstractClass = this.abstractClasses.get(clazz);
        if (abstractClass != null) {
            abstractClass.updateFieldStatus(sootField, status);
        }
    }

    public Map<SootClass, AbstractClass> getAbstractClasses() {
        return abstractClasses;
    }
}
