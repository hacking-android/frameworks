/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExpressionContext;
import org.apache.xalan.extensions.ExtensionHandler;
import org.apache.xalan.extensions.ExtensionNamespaceSupport;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xpath.XPathProcessorException;
import org.apache.xpath.functions.FuncExtFunction;

public class ExtensionsTable {
    public Hashtable m_extensionFunctionNamespaces = new Hashtable();
    private StylesheetRoot m_sroot;

    public ExtensionsTable(StylesheetRoot serializable) throws TransformerException {
        this.m_sroot = serializable;
        serializable = this.m_sroot.getExtensions();
        for (int i = 0; i < ((Vector)serializable).size(); ++i) {
            ExtensionNamespaceSupport extensionNamespaceSupport = (ExtensionNamespaceSupport)((Vector)serializable).get(i);
            ExtensionHandler extensionHandler = extensionNamespaceSupport.launch();
            if (extensionHandler == null) continue;
            this.addExtensionNamespace(extensionNamespaceSupport.getNamespace(), extensionHandler);
        }
    }

    public void addExtensionNamespace(String string, ExtensionHandler extensionHandler) {
        this.m_extensionFunctionNamespaces.put(string, extensionHandler);
    }

    public boolean elementAvailable(String object, String string) throws TransformerException {
        boolean bl;
        boolean bl2 = bl = false;
        if (object != null) {
            object = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(object);
            bl2 = bl;
            if (object != null) {
                bl2 = ((ExtensionHandler)object).isElementAvailable(string);
            }
        }
        return bl2;
    }

    public Object extFunction(String string, String string2, Vector vector, Object object, ExpressionContext expressionContext) throws TransformerException {
        Object object2 = null;
        if (string != null) {
            object2 = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(string);
            if (object2 != null) {
                try {
                    object2 = ((ExtensionHandler)object2).callFunction(string2, vector, object, expressionContext);
                }
                catch (Exception exception) {
                    throw new TransformerException(exception);
                }
                catch (TransformerException transformerException) {
                    throw transformerException;
                }
            } else {
                throw new XPathProcessorException(XSLMessages.createMessage("ER_EXTENSION_FUNC_UNKNOWN", new Object[]{string, string2}));
            }
        }
        return object2;
    }

    public Object extFunction(FuncExtFunction funcExtFunction, Vector vector, ExpressionContext expressionContext) throws TransformerException {
        Object object = null;
        String string = funcExtFunction.getNamespace();
        if (string != null) {
            object = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(string);
            if (object != null) {
                try {
                    object = ((ExtensionHandler)object).callFunction(funcExtFunction, vector, expressionContext);
                }
                catch (Exception exception) {
                    throw new TransformerException(exception);
                }
                catch (TransformerException transformerException) {
                    throw transformerException;
                }
            } else {
                throw new XPathProcessorException(XSLMessages.createMessage("ER_EXTENSION_FUNC_UNKNOWN", new Object[]{string, funcExtFunction.getFunctionName()}));
            }
        }
        return object;
    }

    public boolean functionAvailable(String object, String string) throws TransformerException {
        boolean bl;
        boolean bl2 = bl = false;
        if (object != null) {
            object = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(object);
            bl2 = bl;
            if (object != null) {
                bl2 = ((ExtensionHandler)object).isFunctionAvailable(string);
            }
        }
        return bl2;
    }

    public ExtensionHandler get(String string) {
        return (ExtensionHandler)this.m_extensionFunctionNamespaces.get(string);
    }
}

