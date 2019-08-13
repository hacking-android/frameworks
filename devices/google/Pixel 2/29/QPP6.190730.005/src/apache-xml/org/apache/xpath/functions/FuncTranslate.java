/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function3Args;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncTranslate
extends Function3Args {
    static final long serialVersionUID = -1672834340026116482L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        String string = this.m_arg0.execute((XPathContext)object).str();
        String string2 = this.m_arg1.execute((XPathContext)object).str();
        String string3 = this.m_arg2.execute((XPathContext)object).str();
        int n = string.length();
        int n2 = string3.length();
        object = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            int n3 = string2.indexOf(c);
            if (n3 < 0) {
                ((StringBuffer)object).append(c);
                continue;
            }
            if (n3 >= n2) continue;
            ((StringBuffer)object).append(string3.charAt(n3));
        }
        return new XString(((StringBuffer)object).toString());
    }
}

