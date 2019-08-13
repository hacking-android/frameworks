/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.Serializable;

public abstract class Number
implements Serializable {
    private static final long serialVersionUID = -8742448824652078965L;

    public byte byteValue() {
        return (byte)this.intValue();
    }

    public abstract double doubleValue();

    public abstract float floatValue();

    public abstract int intValue();

    public abstract long longValue();

    public short shortValue() {
        return (short)this.intValue();
    }
}

