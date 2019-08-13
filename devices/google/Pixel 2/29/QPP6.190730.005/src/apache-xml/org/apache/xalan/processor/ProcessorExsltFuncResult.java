/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import org.apache.xalan.processor.ProcessorTemplateElem;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.templates.ElemExsltFuncResult;
import org.apache.xalan.templates.ElemExsltFunction;
import org.apache.xalan.templates.ElemParam;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ProcessorExsltFuncResult
extends ProcessorTemplateElem {
    static final long serialVersionUID = 6451230911473482423L;

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String object, String string, String string2, Attributes attributes) throws SAXException {
        super.startElement(stylesheetHandler, (String)object, string, string2, attributes);
        for (object = stylesheetHandler.getElemTemplateElement().getParentElem(); object != null && !(object instanceof ElemExsltFunction); object = object.getParentElem()) {
            if (!(object instanceof ElemVariable) && !(object instanceof ElemParam) && !(object instanceof ElemExsltFuncResult)) continue;
            stylesheetHandler.error("func:result cannot appear within a variable, parameter, or another func:result.", new SAXException("func:result cannot appear within a variable, parameter, or another func:result."));
        }
        if (object == null) {
            stylesheetHandler.error("func:result must appear in a func:function element", new SAXException("func:result must appear in a func:function element"));
        }
    }
}

