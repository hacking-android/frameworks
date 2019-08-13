/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.XMLString;

public abstract class XMLStringFactory {
    public abstract XMLString emptystr();

    public abstract XMLString newstr(String var1);

    public abstract XMLString newstr(FastStringBuffer var1, int var2, int var3);

    public abstract XMLString newstr(char[] var1, int var2, int var3);
}

