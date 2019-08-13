/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncContains
extends Function2Args {
    static final long serialVersionUID = 5084753781887919723L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        String string = this.m_arg0.execute((XPathContext)object).str();
        object = this.m_arg1.execute((XPathContext)object).str();
        if (string.length() == 0 && ((String)object).length() == 0) {
            return XBoolean.S_TRUE;
        }
        object = string.indexOf((String)object) > -1 ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return object;
    }
}

