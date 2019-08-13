/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function3Args;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncSubstring
extends Function3Args {
    static final long serialVersionUID = -5996676095024715502L;

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n < 2) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        int n;
        XMLString xMLString = this.m_arg0.execute((XPathContext)object).xstr();
        double d = this.m_arg1.execute((XPathContext)object).num();
        int n2 = xMLString.length();
        if (n2 <= 0) {
            return XString.EMPTYSTRING;
        }
        if (Double.isNaN(d)) {
            d = -1000000.0;
            n = 0;
        } else {
            n = (d = (double)Math.round(d)) > 0.0 ? (int)d - 1 : 0;
        }
        if (this.m_arg2 != null) {
            int n3;
            int n4 = (int)((double)Math.round(this.m_arg2.num((XPathContext)object)) + d) - 1;
            if (n4 < 0) {
                n3 = 0;
            } else {
                n3 = n4;
                if (n4 > n2) {
                    n3 = n2;
                }
            }
            n4 = n;
            if (n > n2) {
                n4 = n2;
            }
            object = xMLString.substring(n4, n3);
        } else {
            int n5 = n;
            if (n > n2) {
                n5 = n2;
            }
            object = xMLString.substring(n5);
        }
        return (XString)object;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("ER_TWO_OR_THREE", null));
    }
}

