package soot.jimple.infoflow.nullabilityAnalysis.data;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.infoflow.data.SootMethodAndClass;
import soot.jimple.infoflow.nullabilityAnalysis.Status;

import java.util.HashMap;
import java.util.Map;

public class AbstractClass {

    private String className;
    private Map<String, AbstractMethod> methodMap;
    private Map<String, AbstractField> fieldMap;

    public AbstractClass(SootClass clazz) {
        this.className = clazz.getName();

        this.methodMap = new HashMap<>();
        for (SootMethod method : clazz.getMethods()) {
            SootMethodAndClass sootMethodAndClass = new SootMethodAndClass(method);
            methodMap.put(method.getSignature(), new AbstractMethod(sootMethodAndClass));
        }

        this.fieldMap = new HashMap<>();
        for (SootField sootField : clazz.getFields()) {
            fieldMap.put(sootField.getName(), new AbstractField(sootField));
        }
    }

    public void updateMethodParamsStatus(String methodName, int index, Status status) {
        if (!this.methodMap.containsKey(methodName))
            return;

        AbstractMethod abstractMethod = this.methodMap.get(methodName);
        abstractMethod.updateParamsStatus(index, status);
    }

    public void updateMethodReturnStatus(String methodName, Status status) {
        if (!this.methodMap.containsKey(methodName)) return;

        AbstractMethod abstractMethod = this.methodMap.get(methodName);
        abstractMethod.updateReturnStatus(status);
    }

    public void updateFieldStatus(String fieldName, Status status) {
        if (!this.fieldMap.containsKey(fieldName))
            return;

        this.fieldMap.get(fieldName).updateStatus(status);
    }

    public boolean hasMethod() {
        return !this.methodMap.isEmpty();
    }

    public boolean hasFiled() {
        return !this.fieldMap.isEmpty();
    }

    @Override
    public String toString() {
        return "\n===========================================\n" +
                "[class] : \n" +
                className + "\n" +
                "[methods] : \n" +
                methods2String() + "\n" +
                "[fields] : \n" +
                fields2String() + "\n" +
                "===========================================\n";
    }

    private String methods2String() {
        String str = "";

        for (String methodName : methodMap.keySet()) {
            str += methodMap.get(methodName).toString() + "\n";
        }

        return str;
    }

    private String fields2String() {
        String str = "";

        for (String fieldName : fieldMap.keySet()) {
            str += fieldMap.get(fieldName).toString();
        }

        return str;
    }
}
