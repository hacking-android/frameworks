/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.util.Vector;
import org.apache.xalan.processor.ProcessorPreserveSpace;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.WhitespaceInfoPaths;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.WhiteSpaceInfo;
import org.apache.xpath.XPath;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class ProcessorStripSpace
extends ProcessorPreserveSpace {
    static final long serialVersionUID = -5594493198637899591L;

    ProcessorStripSpace() {
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String object, String object2, String object3, Attributes object4) throws SAXException {
        object2 = stylesheetHandler.getStylesheet();
        object = new WhitespaceInfoPaths((Stylesheet)object2);
        this.setPropertiesFromAttributes(stylesheetHandler, (String)object3, (Attributes)object4, (ElemTemplateElement)object);
        object3 = ((WhitespaceInfoPaths)object).getElements();
        for (int i = 0; i < ((Vector)object3).size(); ++i) {
            object4 = new WhiteSpaceInfo((XPath)((Vector)object3).elementAt(i), true, (Stylesheet)object2);
            ((ElemTemplateElement)object4).setUid(stylesheetHandler.nextUid());
            ((Stylesheet)object2).setStripSpaces((WhiteSpaceInfo)object4);
        }
        ((WhitespaceInfoPaths)object).clearElements();
    }
}

