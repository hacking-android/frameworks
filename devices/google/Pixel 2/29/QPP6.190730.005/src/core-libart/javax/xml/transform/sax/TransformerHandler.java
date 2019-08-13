/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.sax;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ext.LexicalHandler;

public interface TransformerHandler
extends ContentHandler,
LexicalHandler,
DTDHandler {
    public String getSystemId();

    public Transformer getTransformer();

    public void setResult(Result var1) throws IllegalArgumentException;

    public void setSystemId(String var1);
}

