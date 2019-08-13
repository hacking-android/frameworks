/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.HashMap;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xpath.Expression;
import org.apache.xpath.ExtensionsProvider;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncExtElementAvailable
extends FunctionOneArg {
    static final long serialVersionUID = -472533699257968546L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        String string;
        String string2 = this.m_arg0.execute((XPathContext)object).str();
        int n = string2.indexOf(58);
        if (n < 0) {
            string = "http://www.w3.org/1999/XSL/Transform";
        } else {
            string = string2.substring(0, n);
            string = ((XPathContext)object).getNamespaceContext().getNamespaceForPrefix(string);
            if (string == null) {
                return XBoolean.S_FALSE;
            }
            string2 = string2.substring(n + 1);
        }
        if (!string.equals("http://www.w3.org/1999/XSL/Transform") && !string.equals("http://xml.apache.org/xalan")) {
            object = ((ExtensionsProvider)((XPathContext)object).getOwnerObject()).elementAvailable(string, string2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
            return object;
        }
        try {
            HashMap hashMap = ((TransformerImpl)((XPathContext)object).getOwnerObject()).getStylesheet().getAvailableElements();
            object = new QName(string, string2);
            object = hashMap.containsKey(object) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
            return object;
        }
        catch (Exception exception) {
            return XBoolean.S_FALSE;
        }
    }
}

