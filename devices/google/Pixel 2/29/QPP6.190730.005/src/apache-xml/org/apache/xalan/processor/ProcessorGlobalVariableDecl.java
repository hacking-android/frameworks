/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.ProcessorTemplateElem;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.Stylesheet;
import org.xml.sax.SAXException;

class ProcessorGlobalVariableDecl
extends ProcessorTemplateElem {
    static final long serialVersionUID = -5954332402269819582L;

    ProcessorGlobalVariableDecl() {
    }

    @Override
    protected void appendAndPush(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        stylesheetHandler.pushElemTemplateElement(elemTemplateElement);
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        ElemVariable elemVariable = (ElemVariable)stylesheetHandler.getElemTemplateElement();
        stylesheetHandler.getStylesheet().appendChild(elemVariable);
        stylesheetHandler.getStylesheet().setVariable(elemVariable);
        super.endElement(stylesheetHandler, string, string2, string3);
    }
}

