/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.Serializable;
import java.util.Objects;

public final class StackTraceElement
implements Serializable {
    private static final long serialVersionUID = 6992337162326171013L;
    private String declaringClass;
    private String fileName;
    private int lineNumber;
    private String methodName;

    public StackTraceElement(String string, String string2, String string3, int n) {
        this.declaringClass = Objects.requireNonNull(string, "Declaring class is null");
        this.methodName = Objects.requireNonNull(string2, "Method name is null");
        this.fileName = string3;
        this.lineNumber = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof StackTraceElement)) {
            return false;
        }
        object = (StackTraceElement)object;
        if (!(((StackTraceElement)object).declaringClass.equals(this.declaringClass) && ((StackTraceElement)object).lineNumber == this.lineNumber && Objects.equals(this.methodName, ((StackTraceElement)object).methodName) && Objects.equals(this.fileName, ((StackTraceElement)object).fileName))) {
            bl = false;
        }
        return bl;
    }

    public String getClassName() {
        return this.declaringClass;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public int hashCode() {
        return ((this.declaringClass.hashCode() * 31 + this.methodName.hashCode()) * 31 + Objects.hashCode(this.fileName)) * 31 + this.lineNumber;
    }

    public boolean isNativeMethod() {
        boolean bl = this.lineNumber == -2;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClassName());
        stringBuilder.append(".");
        stringBuilder.append(this.methodName);
        if (this.isNativeMethod()) {
            stringBuilder.append("(Native Method)");
        } else if (this.fileName != null) {
            if (this.lineNumber >= 0) {
                stringBuilder.append("(");
                stringBuilder.append(this.fileName);
                stringBuilder.append(":");
                stringBuilder.append(this.lineNumber);
                stringBuilder.append(")");
            } else {
                stringBuilder.append("(");
                stringBuilder.append(this.fileName);
                stringBuilder.append(")");
            }
        } else if (this.lineNumber >= 0) {
            stringBuilder.append("(Unknown Source:");
            stringBuilder.append(this.lineNumber);
            stringBuilder.append(")");
        } else {
            stringBuilder.append("(Unknown Source)");
        }
        return stringBuilder.toString();
    }
}

