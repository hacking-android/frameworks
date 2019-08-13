/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExtensionHandler;
import org.apache.xalan.extensions.ExtensionNamespacesManager;
import org.apache.xalan.extensions.ExtensionsTable;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemExtensionDecl;
import org.apache.xalan.templates.ElemFallback;
import org.apache.xalan.templates.ElemLiteralResult;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPathContext;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ElemExtensionCall
extends ElemLiteralResult {
    static final long serialVersionUID = 3171339708500216920L;
    ElemExtensionDecl m_decl = null;
    String m_extns;
    String m_lang;
    String m_scriptSrc;
    String m_srcURL;

    private void executeFallbacks(TransformerImpl transformerImpl) throws TransformerException {
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        while (elemTemplateElement != null) {
            if (elemTemplateElement.getXSLToken() == 57) {
                try {
                    transformerImpl.pushElemTemplateElement(elemTemplateElement);
                    ((ElemFallback)elemTemplateElement).executeFallback(transformerImpl);
                }
                finally {
                    transformerImpl.popElemTemplateElement();
                }
            }
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
    }

    private ElemExtensionDecl getElemExtensionDecl(StylesheetRoot stylesheetRoot, String string) {
        int n = stylesheetRoot.getGlobalImportCount();
        for (int i = 0; i < n; ++i) {
            for (ElemTemplateElement elemTemplateElement = stylesheetRoot.getGlobalImport((int)i).getFirstChildElem(); elemTemplateElement != null; elemTemplateElement = elemTemplateElement.getNextSiblingElem()) {
                ElemExtensionDecl elemExtensionDecl;
                if (85 != elemTemplateElement.getXSLToken() || !string.equals(elemTemplateElement.getNamespaceForPrefix((elemExtensionDecl = (ElemExtensionDecl)elemTemplateElement).getPrefix()))) continue;
                return elemExtensionDecl;
            }
        }
        return null;
    }

    private boolean hasFallbackChildren() {
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        while (elemTemplateElement != null) {
            if (elemTemplateElement.getXSLToken() == 57) {
                return true;
            }
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
        return false;
    }

    @Override
    protected boolean accept(XSLTVisitor xSLTVisitor) {
        return xSLTVisitor.visitExtensionElement(this);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        this.m_extns = this.getNamespace();
        this.m_decl = this.getElemExtensionDecl(stylesheetRoot, this.m_extns);
        if (this.m_decl == null) {
            stylesheetRoot.getExtensionNamespacesManager().registerExtension(this.m_extns);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        if (transformerImpl.getStylesheet().isSecureProcessing()) {
            throw new TransformerException(XSLMessages.createMessage("ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", new Object[]{this.getRawName()}));
        }
        try {
            transformerImpl.getResultTreeHandler().flushPending();
            Object object = transformerImpl.getExtensionsTable().get(this.m_extns);
            if (object == null) {
                if (this.hasFallbackChildren()) {
                    this.executeFallbacks(transformerImpl);
                    return;
                }
                object = new TransformerException(XSLMessages.createMessage("ER_CALL_TO_EXT_FAILED", new Object[]{this.getNodeName()}));
                transformerImpl.getErrorListener().fatalError((TransformerException)object);
                return;
            }
            try {
                ((ExtensionHandler)object).processElement(this.getLocalName(), this, transformerImpl, this.getStylesheet(), this);
                return;
            }
            catch (Exception exception) {
                if (this.hasFallbackChildren()) {
                    this.executeFallbacks(transformerImpl);
                    return;
                }
                if (exception instanceof TransformerException) {
                    TransformerException transformerException = (TransformerException)exception;
                    if (transformerException.getLocator() == null) {
                        transformerException.setLocator(this);
                    }
                    transformerImpl.getErrorListener().fatalError(transformerException);
                    return;
                }
                if (exception instanceof RuntimeException) {
                    ErrorListener errorListener = transformerImpl.getErrorListener();
                    TransformerException transformerException = new TransformerException(exception);
                    errorListener.fatalError(transformerException);
                    return;
                }
                ErrorListener errorListener = transformerImpl.getErrorListener();
                TransformerException transformerException = new TransformerException(exception);
                errorListener.warning(transformerException);
                return;
            }
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
        catch (TransformerException transformerException) {
            transformerImpl.getErrorListener().fatalError(transformerException);
        }
    }

    public String getAttribute(String object, Node node, TransformerImpl transformerImpl) throws TransformerException {
        AVT aVT = this.getLiteralResultAttribute((String)object);
        if (aVT != null && aVT.getRawName().equals(object)) {
            object = transformerImpl.getXPathContext();
            return aVT.evaluate((XPathContext)object, ((XPathContext)object).getDTMHandleFromNode(node), this);
        }
        return null;
    }

    @Override
    public int getXSLToken() {
        return 79;
    }
}

