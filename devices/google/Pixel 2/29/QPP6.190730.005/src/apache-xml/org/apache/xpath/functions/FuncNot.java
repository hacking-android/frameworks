/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncNot
extends FunctionOneArg {
    static final long serialVersionUID = 7299699961076329790L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        object = this.m_arg0.execute((XPathContext)object).bool() ? XBoolean.S_FALSE : XBoolean.S_TRUE;
        return object;
    }
}

