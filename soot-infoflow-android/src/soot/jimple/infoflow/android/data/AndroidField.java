package soot.jimple.infoflow.android.data;

import soot.SootField;
import soot.jimple.infoflow.data.SootFieldAndClass;
import soot.jimple.infoflow.sourcesSinks.definitions.SourceSinkType;
import soot.jimple.infoflow.util.SootFieldRepresentationParser;

public class AndroidField extends SootFieldAndClass {

    private SourceSinkType sourceSinkType = SourceSinkType.Undefined;

    public AndroidField(String fieldName, String className, String type) {
        super(fieldName, className, type);
    }

    public AndroidField(SootField sf) {
        super(sf);
    }

    public void setSourceSinkType(SourceSinkType sourceSinkType) {
        this.sourceSinkType = sourceSinkType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSignature());

        if (this.sourceSinkType != SourceSinkType.Undefined)
            sb.append(" ->");
        if (this.sourceSinkType == SourceSinkType.Source)
            sb.append(" _SOURCE_");
        else if (this.sourceSinkType == SourceSinkType.Sink)
            sb.append(" _SINK_ ");
        else if (this.sourceSinkType == SourceSinkType.Neither)
            sb.append(" _NONE_");
        else if (this.sourceSinkType == SourceSinkType.Both)
            sb.append(" _BOTH_");
        return sb.toString();
    }

    public SourceSinkType getSourceSinkType() {
        return sourceSinkType;
    }

    public static AndroidField createFromSignature(String signature) {
        if (!signature.startsWith("<"))
            signature = "<" + signature;
        if (!signature.endsWith(">"))
            signature = signature + ">";

        SootFieldAndClass smac = SootFieldRepresentationParser.v().parseSootFieldString(signature);
        return new AndroidField(smac.getFieldName(), smac.getType(), smac.getClassName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((sourceSinkType == null) ? 0 : sourceSinkType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AndroidField other = (AndroidField) obj;
        if (sourceSinkType != other.sourceSinkType)
            return false;
        return true;
    }
}
