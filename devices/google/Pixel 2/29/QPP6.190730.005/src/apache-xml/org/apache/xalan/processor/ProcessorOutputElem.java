/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.util.Properties;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.SystemIDResolver;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class ProcessorOutputElem
extends XSLTElementProcessor {
    static final long serialVersionUID = 3513742319582547590L;
    private OutputProperties m_outputProperties;

    ProcessorOutputElem() {
    }

    public void addLiteralResultAttribute(String object, String string, String string2, String string3) {
        object = new QName((String)object, string);
        this.m_outputProperties.setProperty((QName)object, string3);
    }

    public void setCdataSectionElements(Vector vector) {
        this.m_outputProperties.setQNameProperties("cdata-section-elements", vector);
    }

    public void setDoctypePublic(String string) {
        this.m_outputProperties.setProperty("doctype-public", string);
    }

    public void setDoctypeSystem(String string) {
        this.m_outputProperties.setProperty("doctype-system", string);
    }

    public void setEncoding(String string) {
        this.m_outputProperties.setProperty("encoding", string);
    }

    public void setForeignAttr(String object, String string, String string2, String string3) {
        object = new QName((String)object, string);
        this.m_outputProperties.setProperty((QName)object, string3);
    }

    public void setIndent(boolean bl) {
        this.m_outputProperties.setBooleanProperty("indent", bl);
    }

    public void setMediaType(String string) {
        this.m_outputProperties.setProperty("media-type", string);
    }

    public void setMethod(QName qName) {
        this.m_outputProperties.setQNameProperty("method", qName);
    }

    public void setOmitXmlDeclaration(boolean bl) {
        this.m_outputProperties.setBooleanProperty("omit-xml-declaration", bl);
    }

    public void setStandalone(boolean bl) {
        this.m_outputProperties.setBooleanProperty("standalone", bl);
    }

    public void setVersion(String string) {
        this.m_outputProperties.setProperty("version", string);
    }

    @Override
    public void startElement(StylesheetHandler stylesheetHandler, String string, String string2, String string3, Attributes attributes) throws SAXException {
        this.m_outputProperties = new OutputProperties();
        this.m_outputProperties.setDOMBackPointer(stylesheetHandler.getOriginatingNode());
        this.m_outputProperties.setLocaterInfo(stylesheetHandler.getLocator());
        this.m_outputProperties.setUid(stylesheetHandler.nextUid());
        this.setPropertiesFromAttributes(stylesheetHandler, string3, attributes, this);
        string = (String)this.m_outputProperties.getProperties().get("{http://xml.apache.org/xalan}entities");
        if (string != null) {
            try {
                string = SystemIDResolver.getAbsoluteURI(string, stylesheetHandler.getBaseIdentifier());
                this.m_outputProperties.getProperties().put("{http://xml.apache.org/xalan}entities", string);
            }
            catch (TransformerException transformerException) {
                stylesheetHandler.error(transformerException.getMessage(), transformerException);
            }
        }
        stylesheetHandler.getStylesheet().setOutput(this.m_outputProperties);
        stylesheetHandler.getElemTemplateElement().appendChild(this.m_outputProperties);
        this.m_outputProperties = null;
    }
}

