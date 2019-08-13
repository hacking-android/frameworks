/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.ProcessorCharacters;
import org.apache.xalan.processor.ProcessorTemplateElem;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemText;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ProcessorText
extends ProcessorTemplateElem {
    static final long serialVersionUID = 5170229307201307523L;

    @Override
    protected void appendAndPush(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        ((ProcessorCharacters)stylesheetHandler.getProcessorFor(null, "text()", "text")).setXslTextElement((ElemText)elemTemplateElement);
        stylesheetHandler.getElemTemplateElement().appendChild(elemTemplateElement);
        elemTemplateElement.setDOMBackPointer(stylesheetHandler.getOriginatingNode());
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        ((ProcessorCharacters)stylesheetHandler.getProcessorFor(null, "text()", "text")).setXslTextElement(null);
    }
}

