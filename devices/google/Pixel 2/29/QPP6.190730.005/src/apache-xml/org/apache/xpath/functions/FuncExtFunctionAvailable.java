/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExtensionsProvider;
import org.apache.xpath.XPathContext;
import org.apache.xpath.compiler.FunctionTable;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class FuncExtFunctionAvailable
extends FunctionOneArg {
    static final long serialVersionUID = 5118814314918592241L;
    private transient FunctionTable m_functionTable = null;

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
        if (string.equals("http://www.w3.org/1999/XSL/Transform")) {
            try {
                if (this.m_functionTable == null) {
                    this.m_functionTable = object = new FunctionTable();
                }
                object = this.m_functionTable.functionAvailable(string2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
                return object;
            }
            catch (Exception exception) {
                return XBoolean.S_FALSE;
            }
        }
        object = ((ExtensionsProvider)((XPathContext)object).getOwnerObject()).functionAvailable(string, string2) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
        return object;
    }

    public void setFunctionTable(FunctionTable functionTable) {
        this.m_functionTable = functionTable;
    }
}

