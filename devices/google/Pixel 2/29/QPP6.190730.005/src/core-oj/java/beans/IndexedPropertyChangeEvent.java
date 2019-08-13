/*
 * Decompiled with CFR 0.145.
 */
package java.beans;

import java.beans.PropertyChangeEvent;

public class IndexedPropertyChangeEvent
extends PropertyChangeEvent {
    private static final long serialVersionUID = -320227448495806870L;
    private int index;

    public IndexedPropertyChangeEvent(Object object, String string, Object object2, Object object3, int n) {
        super(object, string, object2, object3);
        this.index = n;
    }

    @Override
    void appendTo(StringBuilder stringBuilder) {
        stringBuilder.append("; index=");
        stringBuilder.append(this.getIndex());
    }

    public int getIndex() {
        return this.index;
    }
}

