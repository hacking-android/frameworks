/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

public interface URIResolver {
    public Source resolve(String var1, String var2) throws TransformerException;
}

