/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.ProcessorTemplateElem;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.templates.ElemParam;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.xml.sax.SAXException;

class ProcessorGlobalParamDecl
extends ProcessorTemplateElem {
    static final long serialVersionUID = 1900450872353587350L;

    ProcessorGlobalParamDecl() {
    }

    @Override
    protected void appendAndPush(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        stylesheetHandler.pushElemTemplateElement(elemTemplateElement);
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        ElemParam elemParam = (ElemParam)stylesheetHandler.getElemTemplateElement();
        stylesheetHandler.getStylesheet().appendChild(elemParam);
        stylesheetHandler.getStylesheet().setParam(elemParam);
        super.endElement(stylesheetHandler, string, string2, string3);
    }
}

