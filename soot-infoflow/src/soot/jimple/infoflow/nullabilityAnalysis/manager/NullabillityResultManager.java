package soot.jimple.infoflow.nullabilityAnalysis.manager;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.infoflow.nullabilityAnalysis.Status;
import soot.jimple.infoflow.nullabilityAnalysis.data.AbstractClass;
import soot.jimple.infoflow.nullabilityAnalysis.util.ResultWriter;
import soot.jimple.infoflow.sourcesSinks.definitions.SourceSinkDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullabillityResultManager {
    private static NullabillityResultManager INSTANCE = new NullabillityResultManager();

    public static NullabillityResultManager getIntance() {
        return INSTANCE;
    }

    private NullabillityResultManager() {
        // do nothing
    }

    private Map<String, AbstractClass> abstractClassMap = new HashMap<>();

    public void initializeWithCallGraph(Scene scene) {
        for (SootClass sootClass : scene.getClasses()) {
            this.abstractClassMap.put(sootClass.getName(), new AbstractClass(sootClass));
        }

        ResultWriter.clear();
    }

    public void addSourceInfo(Map<SootMethod, SourceSinkDefinition> sourceMethods) {
        for (SootMethod sootMethod : sourceMethods.keySet()) {
            this.abstractClassMap.get(sootMethod.getDeclaringClass().getName()).updateMethodReturnStatus(sootMethod.getSignature(), Status.Nullable);
        }
    }

    public void writeMethodParams(SootMethod method, List<Status> statusList) {
        // logger
        ResultWriter.writeMethodParams(method.getDeclaringClass().getName(), method.getSignature(), statusList);

        if (!abstractClassMap.containsKey(method.getDeclaringClass().getName())) return;

        for (int i = 0; i < statusList.size(); i++) {
            abstractClassMap.get(method.getDeclaringClass().getName()).updateMethodParamsStatus(method.getSignature(), i, statusList.get(i));
        }
    }

    public void writeMethodReturn(SootMethod method) {
        // logger
        ResultWriter.writeMethodReturn(method.getSignature());

        if (!abstractClassMap.containsKey(method.getDeclaringClass().getName())) return;

        abstractClassMap.get(method.getDeclaringClass().getName()).updateMethodReturnStatus(method.getSignature(), Status.Nullable);
    }

    public void writeFields(SootClass clazz, String fieldName) {
        // logger
        ResultWriter.writeFields(clazz.getName(), fieldName);

        if (!abstractClassMap.containsKey(clazz.getName())) return;

        abstractClassMap.get(clazz.getName()).updateFieldStatus(fieldName, Status.Nullable);
    }

    public void writeResult() {
        for (String className : abstractClassMap.keySet()) {
            ResultWriter.writeResult(abstractClassMap.get(className).toString());
        }
    }
}
