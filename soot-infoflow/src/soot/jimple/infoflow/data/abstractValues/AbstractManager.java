package soot.jimple.infoflow.data.abstractValues;

import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.jimple.infoflow.data.SootMethodAndClass;

import java.util.HashMap;
import java.util.Map;

public class AbstractManager {

    private Map<SootClass, AbstractClass> abstractClasses;

    public AbstractManager(Scene scene) {
        this.abstractClasses = new HashMap<>();
        for (SootClass sootClass : scene.getClasses()) {
            this.abstractClasses.put(sootClass, new AbstractClass(sootClass));
        }
    }

    public void updateMethodStatus(SootClass clazz, SootMethodAndClass sootMethodAndClass, Status status) {
        AbstractClass abstractClass = this.abstractClasses.get(clazz);
        if (abstractClass != null) {
            abstractClass.updateMethodStatus(sootMethodAndClass, status);
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
