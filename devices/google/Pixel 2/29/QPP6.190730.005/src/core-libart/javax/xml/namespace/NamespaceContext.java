/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.namespace;

import java.util.Iterator;

public interface NamespaceContext {
    public String getNamespaceURI(String var1);

    public String getPrefix(String var1);

    public Iterator getPrefixes(String var1);
}

