/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.ext;

import org.xml.sax.Attributes;

public interface Attributes2
extends Attributes {
    public boolean isDeclared(int var1);

    public boolean isDeclared(String var1);

    public boolean isDeclared(String var1, String var2);

    public boolean isSpecified(int var1);

    public boolean isSpecified(String var1);

    public boolean isSpecified(String var1, String var2);
}

