package soot.jimple.infoflow.test.methodSummary.junit;

import org.junit.Test;
import soot.jimple.infoflow.methodSummary.data.summary.MethodFlow;
import soot.jimple.infoflow.methodSummary.data.summary.SourceSinkType;
import soot.jimple.infoflow.methodSummary.generator.SummaryGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataClassTests extends TestHelper {

    static final String className = "soot.jimple.infoflow.test.methodSummary.Data";

    private static final String NEXT_FIELD = "<soot.jimple.infoflow.test.methodSummary.Data: soot.jimple.infoflow.test.methodSummary.Data next>";

    @Test(timeout = 100000)
    public void switchSwitch() {
        String mSig = "<soot.jimple.infoflow.test.methodSummary.Data: void switchSwitch()>";
        Set<MethodFlow> flow = createSummaries(mSig).getAllFlows();

        // Flow from "next" field to gap base object
        assertTrue(containsFlow(flow, SourceSinkType.Field, -1, new String[]{NEXT_FIELD}, "",
                SourceSinkType.GapBaseObject, -1, new String[]{},
                "<soot.jimple.infoflow.test.methodSummary.Data: void switchData()>"));
        // Flow from gap base object back to "next" field
        assertTrue(containsFlow(flow, SourceSinkType.Field, -1, new String[]{},
                "<soot.jimple.infoflow.test.methodSummary.Data: void switchData()>", SourceSinkType.Field, -1,
                new String[]{NEXT_FIELD}, ""));

        assertEquals(2, flow.size());
    }

    @Override
    protected SummaryGenerator getSummary() {
        SummaryGenerator sg = new SummaryGenerator();
        List<String> sub = new LinkedList<String>();
        sub.add("java.util.LinkedList");
        sg.setSubstitutedWith(sub);
        sg.getConfig().setAccessPathLength(3);
        sg.getConfig().setUseRecursiveAccessPaths(false);
        return sg;
    }

}
