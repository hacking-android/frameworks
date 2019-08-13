/*
 * Decompiled with CFR 0.145.
 */
package java.text;

public class Annotation {
    private Object value;

    public Annotation(Object object) {
        this.value = object;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[value=");
        stringBuilder.append(this.value);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

