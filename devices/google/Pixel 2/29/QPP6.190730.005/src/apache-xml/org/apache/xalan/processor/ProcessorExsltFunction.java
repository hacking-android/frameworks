/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.ProcessorTemplateElem;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.templates.ElemApplyImport;
import org.apache.xalan.templates.ElemApplyTemplates;
import org.apache.xalan.templates.ElemAttribute;
import org.apache.xalan.templates.ElemCallTemplate;
import org.apache.xalan.templates.ElemComment;
import org.apache.xalan.templates.ElemCopy;
import org.apache.xalan.templates.ElemCopyOf;
import org.apache.xalan.templates.ElemElement;
import org.apache.xalan.templates.ElemExsltFuncResult;
import org.apache.xalan.templates.ElemExsltFunction;
import org.apache.xalan.templates.ElemFallback;
import org.apache.xalan.templates.ElemLiteralResult;
import org.apache.xalan.templates.ElemMessage;
import org.apache.xalan.templates.ElemNumber;
import org.apache.xalan.templates.ElemPI;
import org.apache.xalan.templates.ElemParam;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemText;
import org.apache.xalan.templates.ElemTextLiteral;
import org.apache.xalan.templates.ElemValueOf;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.Stylesheet;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ProcessorExsltFunction
extends ProcessorTemplateElem {
    static final long serialVersionUID = 2411427965578315332L;

    boolean ancestorIsOk(ElemTemplateElement elemTemplateElement) {
        while (elemTemplateElement.getParentElem() != null && !(elemTemplateElement.getParentElem() instanceof ElemExsltFunction)) {
            if (!((elemTemplateElement = elemTemplateElement.getParentElem()) instanceof ElemExsltFuncResult) && !(elemTemplateElement instanceof ElemVariable) && !(elemTemplateElement instanceof ElemParam) && !(elemTemplateElement instanceof ElemMessage)) continue;
            return true;
        }
        return false;
    }

    @Override
    protected void appendAndPush(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        super.appendAndPush(stylesheetHandler, elemTemplateElement);
        elemTemplateElement.setDOMBackPointer(stylesheetHandler.getOriginatingNode());
        stylesheetHandler.getStylesheet().setTemplate((ElemTemplate)elemTemplateElement);
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        this.validate(stylesheetHandler.getElemTemplateElement(), stylesheetHandler);
        super.endElement(stylesheetHandler, string, string2, string3);
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3, Attributes attributes) throws SAXException {
        if (!(stylesheetHandler.getElemTemplateElement() instanceof Stylesheet)) {
            stylesheetHandler.error("func:function element must be top level.", new SAXException("func:function element must be top level."));
        }
        super.startElement(stylesheetHandler, string, string2, string3, attributes);
        if (attributes.getValue("name").indexOf(":") <= 0) {
            stylesheetHandler.error("func:function name must have namespace", new SAXException("func:function name must have namespace"));
        }
    }

    public void validate(ElemTemplateElement elemTemplateElement, StylesheetHandler stylesheetHandler) throws SAXException {
        while (elemTemplateElement != null) {
            if (elemTemplateElement instanceof ElemExsltFuncResult && elemTemplateElement.getNextSiblingElem() != null && !(elemTemplateElement.getNextSiblingElem() instanceof ElemFallback)) {
                stylesheetHandler.error("func:result has an illegal following sibling (only xsl:fallback allowed)", new SAXException("func:result has an illegal following sibling (only xsl:fallback allowed)"));
            }
            if ((elemTemplateElement instanceof ElemApplyImport || elemTemplateElement instanceof ElemApplyTemplates || elemTemplateElement instanceof ElemAttribute || elemTemplateElement instanceof ElemCallTemplate || elemTemplateElement instanceof ElemComment || elemTemplateElement instanceof ElemCopy || elemTemplateElement instanceof ElemCopyOf || elemTemplateElement instanceof ElemElement || elemTemplateElement instanceof ElemLiteralResult || elemTemplateElement instanceof ElemNumber || elemTemplateElement instanceof ElemPI || elemTemplateElement instanceof ElemText || elemTemplateElement instanceof ElemTextLiteral || elemTemplateElement instanceof ElemValueOf) && !this.ancestorIsOk(elemTemplateElement)) {
                stylesheetHandler.error("misplaced literal result in a func:function container.", new SAXException("misplaced literal result in a func:function container."));
            }
            ElemTemplateElement elemTemplateElement2 = elemTemplateElement.getFirstChildElem();
            ElemTemplateElement elemTemplateElement3 = elemTemplateElement;
            elemTemplateElement = elemTemplateElement2;
            while (elemTemplateElement == null) {
                elemTemplateElement = elemTemplateElement3.getNextSiblingElem();
                elemTemplateElement2 = elemTemplateElement3;
                if (elemTemplateElement == null) {
                    elemTemplateElement2 = elemTemplateElement3.getParentElem();
                }
                if (elemTemplateElement2 != null) {
                    elemTemplateElement3 = elemTemplateElement2;
                    if (!(elemTemplateElement2 instanceof ElemExsltFunction)) continue;
                }
                return;
            }
        }
    }
}

