package soot.jimple.infoflow.data;

import soot.SootField;

/**
 * Data container which stores the string representation of a SootField and its corresponding class
 */
public class SootFieldAndClass {
    private final String fieldName;
    private final String className;
    private final String type;

    private String subSignature = null;
    private String signature = null;
    private int hashCode = 0;

    public SootFieldAndClass(String fieldName, String type, String className) {
        this.fieldName = fieldName;
        this.className = className;
        this.type = type;
    }

    public SootFieldAndClass(SootField sf) {
        this.fieldName = sf.getName();
        this.className = sf.getDeclaringClass().getName();
        this.type = sf.getType().toString();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getClassName() {
        return className;
    }

    public String getType() {
        return type;
    }

    public String getSubSignature() {
        if (subSignature != null) {
            return subSignature;
        }

        StringBuilder sb = new StringBuilder(10 + this.type.length() + this.fieldName.length());
        if (!this.type.isEmpty()) {
            sb.append(this.type);
            sb.append(" ");
        }
        sb.append(this.fieldName);
        this.subSignature = sb.toString();

        return this.subSignature;
    }

    public String getSignature() {
        if (signature != null)
            return signature;

        StringBuilder sb = new StringBuilder(10 + this.className.length() + this.type.length() + this.fieldName.length());
        sb.append("<");
        sb.append(this.className);
        sb.append(": ");
        if (!this.type.isEmpty()) {
            sb.append(this.type);
            sb.append(" ");
        }
        sb.append(this.fieldName);
        sb.append(">");
        this.signature = sb.toString();

        return this.signature;
    }

    @Override
    public boolean equals(Object another) {
        if (super.equals(another))
            return true;
        if (!(another instanceof SootFieldAndClass))
            return false;
        SootFieldAndClass otherField = (SootFieldAndClass) another;

        if (!this.fieldName.equals(otherField.fieldName))
            return false;
        if (!this.type.equals(otherField.type))
            return false;
        if (!this.className.equals(otherField.className))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        if (this.hashCode == 0)
            this.hashCode = this.fieldName.hashCode() + this.className.hashCode() * 5;
        // The parameter list is available from the outside, so we can't cache it
        return this.hashCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(className);
        sb.append(": ");
        sb.append(type);
        sb.append(" ");
        sb.append(fieldName);
        sb.append(">");
        return sb.toString();
    }
}
