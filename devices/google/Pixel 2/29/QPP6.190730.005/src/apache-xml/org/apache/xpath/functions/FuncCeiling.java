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

public class FuncCeiling
extends FunctionOneArg {
    static final long serialVersionUID = -1275988936390464739L;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return new XNumber(Math.ceil(this.m_arg0.execute(xPathContext).num()));
    }
}

