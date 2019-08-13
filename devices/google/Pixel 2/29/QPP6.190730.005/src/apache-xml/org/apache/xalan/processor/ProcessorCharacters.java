/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemText;
import org.apache.xalan.templates.ElemTextLiteral;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.XMLCharacterRecognizer;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.NamespaceSupport;

public class ProcessorCharacters
extends XSLTElementProcessor {
    static final long serialVersionUID = 8632900007814162650L;
    private StringBuffer m_accumulator = new StringBuffer();
    protected Node m_firstBackPointer = null;
    private ElemText m_xslTextElement;

    @Override
    public void characters(StylesheetHandler stylesheetHandler, char[] arrc, int n, int n2) throws SAXException {
        this.m_accumulator.append(arrc, n, n2);
        if (this.m_firstBackPointer == null) {
            this.m_firstBackPointer = stylesheetHandler.getOriginatingNode();
        }
        if (this != stylesheetHandler.getCurrentProcessor()) {
            stylesheetHandler.pushProcessor(this);
        }
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        this.startNonText(stylesheetHandler);
        stylesheetHandler.getCurrentProcessor().endElement(stylesheetHandler, string, string2, string3);
        stylesheetHandler.popProcessor();
    }

    void setXslTextElement(ElemText elemText) {
        this.m_xslTextElement = elemText;
    }

    @Override
    public void startNonText(StylesheetHandler stylesheetHandler) throws SAXException {
        int n;
        if (this == stylesheetHandler.getCurrentProcessor()) {
            stylesheetHandler.popProcessor();
        }
        if ((n = this.m_accumulator.length()) > 0 && (this.m_xslTextElement != null || !XMLCharacterRecognizer.isWhiteSpace(this.m_accumulator)) || stylesheetHandler.isSpacePreserve()) {
            char[] arrc;
            ElemTextLiteral elemTextLiteral = new ElemTextLiteral();
            elemTextLiteral.setDOMBackPointer(this.m_firstBackPointer);
            elemTextLiteral.setLocaterInfo(stylesheetHandler.getLocator());
            try {
                elemTextLiteral.setPrefixes(stylesheetHandler.getNamespaceSupport());
                arrc = this.m_xslTextElement;
                boolean bl = arrc != null ? arrc.getDisableOutputEscaping() : false;
                elemTextLiteral.setDisableOutputEscaping(bl);
                elemTextLiteral.setPreserveSpace(true);
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
            arrc = new char[n];
            this.m_accumulator.getChars(0, n, arrc, 0);
            elemTextLiteral.setChars(arrc);
            stylesheetHandler.getElemTemplateElement().appendChild(elemTextLiteral);
        }
        this.m_accumulator.setLength(0);
        this.m_firstBackPointer = null;
    }
}

