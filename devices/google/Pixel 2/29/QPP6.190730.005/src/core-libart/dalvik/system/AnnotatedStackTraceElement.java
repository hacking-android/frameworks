/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

public class AnnotatedStackTraceElement {
    private Object blockedOn;
    private Object[] heldLocks;
    private StackTraceElement stackTraceElement;

    private AnnotatedStackTraceElement() {
    }

    public Object getBlockedOn() {
        return this.blockedOn;
    }

    public Object[] getHeldLocks() {
        return this.heldLocks;
    }

    public StackTraceElement getStackTraceElement() {
        return this.stackTraceElement;
    }
}

