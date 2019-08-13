/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

import org.apache.xalan.extensions.ExtensionNamespacesManager;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.functions.FuncExtFunction;
import org.apache.xpath.functions.FuncExtFunctionAvailable;
import org.apache.xpath.functions.Function;

public class ExpressionVisitor
extends XPathVisitor {
    private StylesheetRoot m_sroot;

    public ExpressionVisitor(StylesheetRoot stylesheetRoot) {
        this.m_sroot = stylesheetRoot;
    }

    @Override
    public boolean visitFunction(ExpressionOwner object, Function function) {
        if (function instanceof FuncExtFunction) {
            object = ((FuncExtFunction)function).getNamespace();
            this.m_sroot.getExtensionNamespacesManager().registerExtension((String)object);
        } else if (function instanceof FuncExtFunctionAvailable && ((String)(object = ((FuncExtFunctionAvailable)function).getArg0().toString())).indexOf(":") > 0) {
            object = ((String)object).substring(0, ((String)object).indexOf(":"));
            object = this.m_sroot.getNamespaceForPrefix((String)object);
            this.m_sroot.getExtensionNamespacesManager().registerExtension((String)object);
        }
        return true;
    }
}

