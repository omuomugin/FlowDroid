package soot.jimple.infoflow.android.callbacks.filters;

import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.jimple.infoflow.entryPointCreators.AndroidEntryPointConstants;

import java.util.Set;

/**
 * A callback filter that disallows inner classes from being used in other host
 * components than the outer class. If a class is a component on its own, it may
 * not be used as a callback handler in some other component.
 * <p>
 * Note that the rules in this filter are heuristics. They are valid for sensible
 * applications, but may be disregarded on purpose by a malicious developer.
 *
 * @author Steven Arzt
 */
public class AlienHostComponentFilter implements ICallbackFilter {

    private SootClass activityClass;
    private SootClass fragmentClass;
    private final Set<SootClass> components;

    /**
     * Creates a new instance of the {@link AlienHostComponentFilter} class
     *
     * @param components The set of components in the Android app
     */
    public AlienHostComponentFilter(Set<SootClass> components) {
        this.components = components;
    }

    @Override
    public boolean accepts(SootClass component, SootClass callbackHandler) {
        // If the callback class is a fragment, but the hosting component is not
        // an activity, this association must be wrong.
        if (Scene.v().getOrMakeFastHierarchy().canStoreType(
                callbackHandler.getType(), this.fragmentClass.getType()))
            if (!Scene.v().getOrMakeFastHierarchy().canStoreType(
                    component.getType(), this.activityClass.getType()))
                return false;

        // If the callback handler is an inner class, we only accept it if its
        // outer class matches the component
        if (callbackHandler.isInnerClass()) {
            if (components.contains(callbackHandler.getOuterClass())
                    && !Scene.v().getOrMakeFastHierarchy().canStoreType(component.getType(),
                    callbackHandler.getOuterClass().getType())) {
                return false;
            }
        }

        // If the callback handler is a component on its own, we do not accept
        // it to be called in the lifecycle of any other components.
        if (components.contains(callbackHandler) && callbackHandler != component) {
            return false;
        }

        // If the callback class only has constructors that require references
        // to other components, it is not meant to be used by the current
        // component.
        for (SootMethod cons : callbackHandler.getMethods()) {
            if (cons.isConstructor() && !cons.isPrivate()) {
                boolean isConstructorUsable = true;

                // Check if we have at least one other component in the parameter
                // list
                for (int i = 0; i < cons.getParameterCount(); i++) {
                    Type paramType = cons.getParameterType(i);
                    if (paramType != component.getType()
                            && paramType instanceof RefType) {
                        if (components.contains(((RefType) paramType).getSootClass())) {
                            isConstructorUsable = false;
                            break;
                        }
                    }
                }

                // Do we have a usable constructor?
                if (!isConstructorUsable)
                    return false;
            }
        }

        return true;
    }

    @Override
    public void reset() {
        this.activityClass = Scene.v().getSootClassUnsafe(AndroidEntryPointConstants.ACTIVITYCLASS);
        this.fragmentClass = Scene.v().getSootClassUnsafe(AndroidEntryPointConstants.FRAGMENTCLASS);
    }

    @Override
    public boolean accepts(SootClass component, SootMethod callback) {
        // We do not implement this method
        return true;
    }

}
