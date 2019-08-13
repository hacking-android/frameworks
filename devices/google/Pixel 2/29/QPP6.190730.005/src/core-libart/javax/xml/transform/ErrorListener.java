/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

import javax.xml.transform.TransformerException;

public interface ErrorListener {
    public void error(TransformerException var1) throws TransformerException;

    public void fatalError(TransformerException var1) throws TransformerException;

    public void warning(TransformerException var1) throws TransformerException;
}

