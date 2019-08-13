/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExtensionNamespaceSupport;
import org.apache.xalan.extensions.ExtensionNamespacesManager;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemExtensionScript;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemTextLiteral;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.StringVector;

public class ElemExtensionDecl
extends ElemTemplateElement {
    static final long serialVersionUID = -4692738885172766789L;
    private StringVector m_elements = null;
    private StringVector m_functions = new StringVector();
    private String m_prefix = null;

    @Override
    public void compose(StylesheetRoot object) throws TransformerException {
        super.compose((StylesheetRoot)object);
        Object object2 = this.getPrefix();
        String string = this.getNamespaceForPrefix((String)object2);
        String string2 = null;
        String string3 = null;
        Object object3 = null;
        if (string != null) {
            Object object4;
            for (object4 = this.getFirstChildElem(); object4 != null; object4 = object4.getNextSiblingElem()) {
                object2 = object3;
                if (86 == ((ElemTemplateElement)object4).getXSLToken()) {
                    object2 = (ElemExtensionScript)object4;
                    String string4 = ((ElemExtensionScript)object2).getLang();
                    String string5 = ((ElemExtensionScript)object2).getSrc();
                    ElemTemplateElement elemTemplateElement = ((ElemTemplateElement)object2).getFirstChildElem();
                    string2 = string4;
                    string3 = string5;
                    object2 = object3;
                    if (elemTemplateElement != null) {
                        string2 = string4;
                        string3 = string5;
                        object2 = object3;
                        if (78 == elemTemplateElement.getXSLToken()) {
                            object3 = new String(((ElemTextLiteral)elemTemplateElement).getChars());
                            string2 = string4;
                            string3 = string5;
                            object2 = object3;
                            if (((String)object3).trim().length() == 0) {
                                object2 = null;
                                string3 = string5;
                                string2 = string4;
                            }
                        }
                    }
                }
                object3 = object2;
            }
            object2 = string2;
            if (string2 == null) {
                object2 = "javaclass";
            }
            if (((String)object2).equals("javaclass") && object3 != null) {
                throw new TransformerException(XSLMessages.createMessage("ER_ELEM_CONTENT_NOT_ALLOWED", new Object[]{object3}));
            }
            string2 = null;
            object4 = ((StylesheetRoot)object).getExtensionNamespacesManager();
            object = string2;
            if (((ExtensionNamespacesManager)object4).namespaceIndex(string, ((ExtensionNamespacesManager)object4).getExtensions()) == -1) {
                if (((String)object2).equals("javaclass")) {
                    if (string3 == null) {
                        object = ((ExtensionNamespacesManager)object4).defineJavaNamespace(string);
                    } else {
                        object = string2;
                        if (((ExtensionNamespacesManager)object4).namespaceIndex(string3, ((ExtensionNamespacesManager)object4).getExtensions()) == -1) {
                            object = ((ExtensionNamespacesManager)object4).defineJavaNamespace(string, string3);
                        }
                    }
                } else {
                    object = new ExtensionNamespaceSupport(string, "org.apache.xalan.extensions.ExtensionHandlerGeneral", new Object[]{string, this.m_elements, this.m_functions, object2, string3, object3, this.getSystemId()});
                }
            }
            if (object != null) {
                ((ExtensionNamespacesManager)object4).registerExtension((ExtensionNamespaceSupport)object);
            }
            return;
        }
        throw new TransformerException(XSLMessages.createMessage("ER_NO_NAMESPACE_DECL", new Object[]{object2}));
    }

    public String getElement(int n) throws ArrayIndexOutOfBoundsException {
        StringVector stringVector = this.m_elements;
        if (stringVector != null) {
            return stringVector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getElementCount() {
        StringVector stringVector = this.m_elements;
        int n = stringVector != null ? stringVector.size() : 0;
        return n;
    }

    public StringVector getElements() {
        return this.m_elements;
    }

    public String getFunction(int n) throws ArrayIndexOutOfBoundsException {
        StringVector stringVector = this.m_functions;
        if (stringVector != null) {
            return stringVector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getFunctionCount() {
        StringVector stringVector = this.m_functions;
        int n = stringVector != null ? stringVector.size() : 0;
        return n;
    }

    public StringVector getFunctions() {
        return this.m_functions;
    }

    @Override
    public String getPrefix() {
        return this.m_prefix;
    }

    @Override
    public int getXSLToken() {
        return 85;
    }

    @Override
    public void runtimeInit(TransformerImpl transformerImpl) throws TransformerException {
    }

    public void setElements(StringVector stringVector) {
        this.m_elements = stringVector;
    }

    public void setFunctions(StringVector stringVector) {
        this.m_functions = stringVector;
    }

    @Override
    public void setPrefix(String string) {
        this.m_prefix = string;
    }
}

