/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xml.utils.SAXSourceLocator;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.NamespaceSupport;

class ProcessorAttributeSet
extends XSLTElementProcessor {
    static final long serialVersionUID = -6473739251316787552L;

    ProcessorAttributeSet() {
    }

    @Override
    public void endElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3) throws SAXException {
        stylesheetHandler.popElemTemplateElement();
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String object, String string, String string2, Attributes attributes) throws SAXException {
        object = new ElemAttributeSet();
        ((ElemTemplateElement)object).setLocaterInfo(stylesheetHandler.getLocator());
        try {
            ((ElemTemplateElement)object).setPrefixes(stylesheetHandler.getNamespaceSupport());
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
        ((ElemTemplateElement)object).setDOMBackPointer(stylesheetHandler.getOriginatingNode());
        this.setPropertiesFromAttributes(stylesheetHandler, string2, attributes, (ElemTemplateElement)object);
        stylesheetHandler.getStylesheet().setAttributeSet((ElemAttributeSet)object);
        stylesheetHandler.getElemTemplateElement().appendChild((ElemTemplateElement)object);
        stylesheetHandler.pushElemTemplateElement((ElemTemplateElement)object);
    }
}

