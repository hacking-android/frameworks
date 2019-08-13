/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.io.Serializable;

public interface Header
extends Cloneable,
Serializable {
    public Object clone();

    public boolean equals(Object var1);

    public String getName();

    public int hashCode();

    public String toString();
}

