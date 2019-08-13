/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Operation;

public class Or
extends Operation {
    static final long serialVersionUID = -644107191353853079L;

    @Override
    public boolean bool(XPathContext xPathContext) throws TransformerException {
        boolean bl = this.m_left.bool(xPathContext) || this.m_right.bool(xPathContext);
        return bl;
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        if (!this.m_left.execute((XPathContext)object).bool()) {
            object = this.m_right.execute((XPathContext)object).bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
            return object;
        }
        return XBoolean.S_TRUE;
    }
}

