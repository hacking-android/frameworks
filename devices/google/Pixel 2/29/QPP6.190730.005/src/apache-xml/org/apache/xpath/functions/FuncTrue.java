/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncTrue
extends Function {
    static final long serialVersionUID = 5663314547346339447L;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return XBoolean.S_TRUE;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }
}

