package soot.jimple.infoflow.data.abstractValues;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.infoflow.data.SootMethodAndClass;

import java.util.HashMap;
import java.util.Map;

public class AbstractClass {
    private SootClass clazz;
    private Map<SootMethodAndClass, AbstractMethod> methods;
    private Map<SootField, AbstractField> fields;

    public AbstractClass(SootClass clazz) {
        this.clazz = clazz;

        this.methods = new HashMap<>();
        for (SootMethod method : clazz.getMethods()) {
            SootMethodAndClass sootMethodAndClass = new SootMethodAndClass(method);
            methods.put(sootMethodAndClass, new AbstractMethod(sootMethodAndClass));
        }

        this.fields = new HashMap<>();
        for (SootField sootField : clazz.getFields()) {
            fields.put(sootField, new AbstractField(sootField));
        }
    }

    public void updateMethodStatus(SootMethodAndClass method, Status status) {
        if (!this.methods.containsKey(method))
            return;

        AbstractMethod abstractMethod = this.methods.get(method);
        for (AbstractParams abstractParams : abstractMethod.params) {
            abstractParams.updateStatus(status);
        }
    }

    public void updateFieldStatus(SootField sootField, Status status) {
        if (!this.fields.containsKey(sootField))
            return;

        this.fields.get(sootField).updateStatus(status);
    }

    @Override
    public String toString() {
        return "\n===========================================\n" +
                "[class] : \n" +
                clazz.getName() + "\n" +
                "[methods] : \n" +
                methods2String() + "\n" +
                "[fields] : \n" +
                fields2String() + "\n" +
                "===========================================\n";
    }

    private String methods2String() {
        String str = "";

        for (SootMethodAndClass sootMethodAndClass : methods.keySet()) {
            str += methods.get(sootMethodAndClass).toString();
        }

        return str;
    }

    private String fields2String() {
        String str = "";

        for (SootField field : fields.keySet()) {
            str += fields.get(field).toString();
        }

        return str;
    }
}
