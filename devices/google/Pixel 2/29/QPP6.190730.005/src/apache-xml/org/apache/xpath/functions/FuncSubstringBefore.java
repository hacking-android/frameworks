/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncSubstringBefore
extends Function2Args {
    static final long serialVersionUID = 4110547161672431775L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        String string = this.m_arg0.execute((XPathContext)object).str();
        int n = string.indexOf(this.m_arg1.execute((XPathContext)object).str());
        object = -1 == n ? XString.EMPTYSTRING : new XString(string.substring(0, n));
        return object;
    }
}

