/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncStartsWith
extends Function2Args {
    static final long serialVersionUID = 2194585774699567928L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        object = this.m_arg0.execute((XPathContext)object).xstr().startsWith(this.m_arg1.execute((XPathContext)object).xstr()) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return object;
    }
}

