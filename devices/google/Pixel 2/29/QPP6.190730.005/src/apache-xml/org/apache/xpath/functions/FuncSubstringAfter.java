/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncSubstringAfter
extends Function2Args {
    static final long serialVersionUID = -8119731889862512194L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        XMLString xMLString = this.m_arg0.execute((XPathContext)object).xstr();
        int n = xMLString.indexOf((XMLString)(object = this.m_arg1.execute((XPathContext)object).xstr()));
        object = -1 == n ? XString.EMPTYSTRING : (XString)xMLString.substring(object.length() + n);
        return object;
    }
}

