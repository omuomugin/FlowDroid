package presto.android.gui.clients;

import presto.android.Logger;
import presto.android.gui.GUIAnalysisClient;
import presto.android.gui.GUIAnalysisOutput;
import presto.android.gui.rep.GUIHierarchy;
import presto.android.gui.rep.StaticGUIHierarchy;

import java.util.List;

public class TestClient implements GUIAnalysisClient {
    @Override
    public void run(GUIAnalysisOutput output) {

        GUIHierarchy hierarchy = new StaticGUIHierarchy(output);
        List<GUIHierarchy.Activity> activities = hierarchy.activities;
        for (GUIHierarchy.Activity activity : activities) {
            dumpWindow(activity, 0);
        }
    }

    private String genIndent(int indent) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            stringBuilder.append("  ");
        }

        return stringBuilder.toString();
    }

    private void dumpWindow(GUIHierarchy.ViewContainer w, int indent) {
        if (w instanceof GUIHierarchy.Window) {
            GUIHierarchy.Window window = (GUIHierarchy.Window) w;
            Logger.verb("DUMPHIER", genIndent(indent) + "Window " + window.getName());
        } else if (w instanceof GUIHierarchy.View) {
            GUIHierarchy.View view = (GUIHierarchy.View) w;
            Logger.verb("DUMPHIER", genIndent(indent) + "View " + view.getType() + " " + view.getIdName() + " " + view.getTitle() + " " + view.getHint());
        }

        for (GUIHierarchy.View view : w.getChildren()) {
            dumpWindow(view, indent + 2);
        }
    }
}
