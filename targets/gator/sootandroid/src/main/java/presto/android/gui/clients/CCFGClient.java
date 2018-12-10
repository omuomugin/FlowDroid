package presto.android.gui.clients;

import presto.android.gui.GUIAnalysisClient;
import presto.android.gui.GUIAnalysisOutput;
import presto.android.gui.clients.ccfg.CCFG;
import presto.android.gui.clients.energy.VarUtil;
import presto.android.gui.wtg.EventHandler;
import presto.android.gui.wtg.WTGAnalysisOutput;
import presto.android.gui.wtg.WTGBuilder;
import presto.android.gui.wtg.ds.WTG;
import presto.android.gui.wtg.ds.WTGEdge;
import soot.SootMethod;

import java.io.PrintStream;
import java.util.Collection;

public class CCFGClient implements GUIAnalysisClient {

    private PrintStream out = null;

    @Override
    public void run(GUIAnalysisOutput output) {
        VarUtil.v().guiOutput = output;
        WTGBuilder wtgBuilder = new WTGBuilder();
        wtgBuilder.build(output);
        WTGAnalysisOutput wtgAO = new WTGAnalysisOutput(output, wtgBuilder);
        WTG wtg = wtgAO.getWTG();

        // 標準出力
        out = System.out;

        CCFG ccfg = new CCFG();
        Collection<WTGEdge> edges = wtg.getEdges();

        for (WTGEdge e : edges) {

            if (e.getCallbacks().isEmpty() && e.getEventHandlers().isEmpty()) continue;

            // if(!e.getSourceNode().getWindow().toString().equals(e.getTargetNode().getWindow().toString())) continue;

            if(!e.getEventHandlers().isEmpty()){
                for(SootMethod m : e.getEventHandlers()){
                    CCFG.Event event = new CCFG.Event(m.getSignature(), "event", e.getEventType().toString(), m.getDeclaringClass().getName());
                    ccfg.setEvent(event);
                }
            } else if (!e.getCallbacks().isEmpty()){
                for (EventHandler eh : e.getCallbacks()) {
                    CCFG.Event event = new CCFG.Event(eh.getEventHandler().getSignature(), "lifecycle", e.getEventType().toString(), eh.getEventHandler().getDeclaringClass().getName());
                    ccfg.setEvent(event);
                }
            }
        }

        ccfg.dumpXML(out);
    }
}
