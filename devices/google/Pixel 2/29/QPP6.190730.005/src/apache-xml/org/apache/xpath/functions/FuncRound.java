/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class FuncRound
extends FunctionOneArg {
    static final long serialVersionUID = -7970583902573826611L;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        double d = this.m_arg0.execute(xPathContext).num();
        if (d >= -0.5 && d < 0.0) {
            return new XNumber(0.0);
        }
        if (d == 0.0) {
            return new XNumber(d);
        }
        return new XNumber(Math.floor(0.5 + d));
    }
}

