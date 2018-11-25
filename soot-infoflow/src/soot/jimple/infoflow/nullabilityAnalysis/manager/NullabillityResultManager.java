package soot.jimple.infoflow.nullabilityAnalysis.manager;

import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.infoflow.nullabilityAnalysis.Status;
import soot.jimple.infoflow.nullabilityAnalysis.data.AbstractClass;
import soot.jimple.infoflow.nullabilityAnalysis.data.AbstractField;
import soot.jimple.infoflow.nullabilityAnalysis.data.AbstractMethod;
import soot.jimple.infoflow.nullabilityAnalysis.data.AbstractParams;
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

    private int nullCount = 0;
    private int nullCountPrev = -1;

    public void initializeWithCallGraph(Scene scene) {
        for (SootClass sootClass : scene.getClasses()) {
            String className = sootClass.getName();

            // ignore framework packages
            if (isIgnoredPackage(sootClass)) continue;

            this.abstractClassMap.put(className, new AbstractClass(sootClass));
        }

        ResultWriter.clear();
    }

    public boolean isIgnoredPackage(SootClass sootClass) {
        String className = sootClass.getName();

        return className.startsWith("java.") ||
                className.startsWith("javax.") ||
                className.startsWith("kotlin.") ||
                className.startsWith("dalvik.") ||
                className.startsWith("android.") ||
                className.startsWith("androidx.") ||
                className.startsWith("org.intellij.") ||
                className.startsWith("org.jetbrains.") ||
                className.startsWith("com.google.android.") ||
                className.startsWith("org.xmlpull.") ||
                className.startsWith("org.xml.") ||
                className.startsWith("org.json") ||
                // android frame work
                className.contains("android.support.") ||
                className.contains("android.view.") ||
                className.contains("BuildConfig") ||
                className.contains("R$");
    }

    public int getNullCount() {
        return nullCount;
    }

    public void addSourceMethodInfo(Map<SootMethod, SourceSinkDefinition> sourceMethods) {
        for (SootMethod sootMethod : sourceMethods.keySet()) {

            if (!this.abstractClassMap.containsKey(sootMethod.getDeclaringClass().getName()))
                return;

            this.abstractClassMap.get(sootMethod.getDeclaringClass().getName()).updateMethodReturnStatus(sootMethod.getSignature(), Status.Nullable);
        }
    }

    public void addSourceFieldInfo(Map<SootField, SourceSinkDefinition> sourceFields) {
        for(SootField sootField : sourceFields.keySet()){

            if (!this.abstractClassMap.containsKey(sootField.getDeclaringClass().getName()))
                return;

            this.abstractClassMap.get(sootField.getDeclaringClass().getName()).updateFieldStatus(sootField.getName(), Status.Nullable);
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
            AbstractClass abstractClass = abstractClassMap.get(className);

            if (abstractClass != null && abstractClass.hasMethod() && abstractClass.hasFiled())
                ResultWriter.writeResult(abstractClassMap.get(className).toString());
        }
    }

    public void updateNullCount() {
        nullCountPrev = nullCount;
        nullCount = countNull();
    }

    public boolean isNullConvergenced() {
        return (nullCountPrev == nullCount);
    }

    public int countNull() {
        int nullableFields = 0;
        int nullableMethods = 0;
        int nullableParams = 0;

        for (String className : abstractClassMap.keySet()) {
            AbstractClass abstractClass = abstractClassMap.get(className);
            Map<String, AbstractField> fieldMap = abstractClass.getFieldMap();
            Map<String, AbstractMethod> methodMap = abstractClass.getMethodMap();

            for (String fieldName : fieldMap.keySet()) {
                if (fieldMap.get(fieldName).getStatus() == Status.Nullable) nullableFields++;
            }

            for (String methodName : methodMap.keySet()) {
                AbstractMethod abstractMethod = methodMap.get(methodName);

                if (abstractMethod.getReturnStatus() == Status.Nullable) nullableMethods++;

                for (AbstractParams abstractParams : abstractMethod.params) {
                    if (abstractParams.getStatus() == Status.Nullable) nullableParams++;
                }
            }
        }

        ResultWriter.log("field : " + String.valueOf(nullableFields) + "\n" +
                "methods : " + String.valueOf(nullableMethods) + "\n" +
                "params : " + String.valueOf(nullableParams) + "\n");

        return nullableFields + nullableMethods + nullableParams;
    }
}
