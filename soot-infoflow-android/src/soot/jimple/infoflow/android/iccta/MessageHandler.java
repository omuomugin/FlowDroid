package soot.jimple.infoflow.android.iccta;

import soot.Scene;
import soot.SootClass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MessageHandler {
    private static MessageHandler instance = new MessageHandler();

    private MessageHandler() {
    }

    ;

    public static MessageHandler v() {
        return instance;
    }

    private Set<SootClass> handlerImpls = null;

    public Set<SootClass> getAllHandlers() {
        if (null == handlerImpls) {
            handlerImpls = new HashSet<SootClass>();

            SootClass handler = Scene.v().getSootClass("android.os.Handler");

            for (Iterator<SootClass> iter = Scene.v().getApplicationClasses().snapshotIterator(); iter.hasNext(); ) {
                SootClass sootClass = iter.next();

                SootClass tmpClass = sootClass;

                while (sootClass.hasSuperclass()) {
                    sootClass = sootClass.getSuperclass();

                    if (sootClass.getName().equals(handler.getName())) {
                        handlerImpls.add(tmpClass);
                        break;
                    }
                }
            }
        }

        return handlerImpls;
    }
}
