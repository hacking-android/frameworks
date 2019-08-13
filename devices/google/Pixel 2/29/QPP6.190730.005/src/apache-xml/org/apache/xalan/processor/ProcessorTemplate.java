/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.ProcessorTemplateElem;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

class ProcessorTemplate
extends ProcessorTemplateElem {
    static final long serialVersionUID = -8457812845473603860L;

    ProcessorTemplate() {
    }

    @Override
    protected void appendAndPush(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        super.appendAndPush(stylesheetHandler, elemTemplateElement);
        elemTemplateElement.setDOMBackPointer(stylesheetHandler.getOriginatingNode());
        stylesheetHandler.getStylesheet().setTemplate((ElemTemplate)elemTemplateElement);
    }
}

