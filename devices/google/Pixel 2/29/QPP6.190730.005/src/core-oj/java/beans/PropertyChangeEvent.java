/*
 * Decompiled with CFR 0.145.
 */
package java.beans;

import java.util.EventObject;

public class PropertyChangeEvent
extends EventObject {
    private static final long serialVersionUID = 7042693688939648123L;
    private Object newValue;
    private Object oldValue;
    private Object propagationId;
    private String propertyName;

    public PropertyChangeEvent(Object object, String string, Object object2, Object object3) {
        super(object);
        this.propertyName = string;
        this.newValue = object3;
        this.oldValue = object2;
    }

    void appendTo(StringBuilder stringBuilder) {
    }

    public Object getNewValue() {
        return this.newValue;
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public Object getPropagationId() {
        return this.propagationId;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropagationId(Object object) {
        this.propagationId = object;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getName());
        stringBuilder.append("[propertyName=");
        stringBuilder.append(this.getPropertyName());
        this.appendTo(stringBuilder);
        stringBuilder.append("; oldValue=");
        stringBuilder.append(this.getOldValue());
        stringBuilder.append("; newValue=");
        stringBuilder.append(this.getNewValue());
        stringBuilder.append("; propagationId=");
        stringBuilder.append(this.getPropagationId());
        stringBuilder.append("; source=");
        stringBuilder.append(this.getSource());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

