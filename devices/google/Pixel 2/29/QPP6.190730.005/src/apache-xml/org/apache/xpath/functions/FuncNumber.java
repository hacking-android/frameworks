/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionDef1Arg;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class FuncNumber
extends FunctionDef1Arg {
    static final long serialVersionUID = 7266745342264153076L;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return new XNumber(this.getArg0AsNumber(xPathContext));
    }
}

