/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xpath.Expression;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class XUnresolvedVariableSimple
extends XObject {
    static final long serialVersionUID = -1224413807443958985L;

    public XUnresolvedVariableSimple(ElemVariable elemVariable) {
        super(elemVariable);
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        object = ((ElemVariable)this.m_obj).getSelect().getExpression().execute((XPathContext)object);
        ((XObject)object).allowDetachToRelease(false);
        return object;
    }

    @Override
    public int getType() {
        return 600;
    }

    @Override
    public String getTypeString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("XUnresolvedVariableSimple (");
        stringBuilder.append(this.object().getClass().getName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

