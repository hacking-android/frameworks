/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

import java.io.Serializable;

public interface URI
extends Cloneable,
Serializable {
    public Object clone();

    public String getScheme();

    public boolean isSipURI();

    public String toString();
}

