/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

import java.io.IOException;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExpressionContext;
import org.apache.xalan.extensions.ObjectFactory;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xpath.functions.FuncExtFunction;

public abstract class ExtensionHandler {
    protected String m_namespaceUri;
    protected String m_scriptLang;

    protected ExtensionHandler(String string, String string2) {
        this.m_namespaceUri = string;
        this.m_scriptLang = string2;
    }

    static Class getClassForName(String string) throws ClassNotFoundException {
        String string2 = string;
        if (string.equals("org.apache.xalan.xslt.extensions.Redirect")) {
            string2 = "org.apache.xalan.lib.Redirect";
        }
        return ObjectFactory.findProviderClass(string2, ObjectFactory.findClassLoader(), true);
    }

    public abstract Object callFunction(String var1, Vector var2, Object var3, ExpressionContext var4) throws TransformerException;

    public abstract Object callFunction(FuncExtFunction var1, Vector var2, ExpressionContext var3) throws TransformerException;

    public abstract boolean isElementAvailable(String var1);

    public abstract boolean isFunctionAvailable(String var1);

    public abstract void processElement(String var1, ElemTemplateElement var2, TransformerImpl var3, Stylesheet var4, Object var5) throws TransformerException, IOException;
}

