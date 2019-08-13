/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import javax.xml.transform.SourceLocator;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.DecimalFormatProperties;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xml.utils.SAXSourceLocator;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class ProcessorDecimalFormat
extends XSLTElementProcessor {
    static final long serialVersionUID = -5052904382662921627L;

    ProcessorDecimalFormat() {
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String object, String string, String string2, Attributes attributes) throws SAXException {
        object = new DecimalFormatProperties(stylesheetHandler.nextUid());
        ((ElemTemplateElement)object).setDOMBackPointer(stylesheetHandler.getOriginatingNode());
        ((ElemTemplateElement)object).setLocaterInfo(stylesheetHandler.getLocator());
        this.setPropertiesFromAttributes(stylesheetHandler, string2, attributes, (ElemTemplateElement)object);
        stylesheetHandler.getStylesheet().setDecimalFormat((DecimalFormatProperties)object);
        stylesheetHandler.getStylesheet().appendChild((ElemTemplateElement)object);
    }
}

