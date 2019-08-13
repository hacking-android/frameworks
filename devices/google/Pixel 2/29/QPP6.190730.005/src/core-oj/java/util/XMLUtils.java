/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class XMLUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String EXTERNAL_XML_VERSION = "1.0";
    private static final String PROPS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, entry* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry  key CDATA #REQUIRED>";
    private static final String PROPS_DTD_URI = "http://java.sun.com/dtd/properties.dtd";

    XMLUtils() {
    }

    static void emitDocument(Document object, OutputStream object2, String string) throws IOException {
        Object object3 = TransformerFactory.newInstance();
        Object object4 = null;
        object4 = object3 = ((TransformerFactory)object3).newTransformer();
        ((Transformer)object3).setOutputProperty("doctype-system", PROPS_DTD_URI);
        object4 = object3;
        ((Transformer)object3).setOutputProperty("indent", "yes");
        object4 = object3;
        ((Transformer)object3).setOutputProperty("method", "xml");
        object4 = object3;
        try {
            ((Transformer)object3).setOutputProperty("encoding", string);
            object4 = object3;
        }
        catch (TransformerConfigurationException transformerConfigurationException) {
            // empty catch block
        }
        object = new DOMSource((Node)object);
        object2 = new StreamResult((OutputStream)object2);
        try {
            ((Transformer)object4).transform((Source)object, (Result)object2);
            return;
        }
        catch (TransformerException transformerException) {
            object2 = new IOException();
            ((Throwable)object2).initCause(transformerException);
            throw object2;
        }
    }

    static Document getLoadingDoc(InputStream object) throws SAXException, IOException {
        Object object2 = DocumentBuilderFactory.newInstance();
        ((DocumentBuilderFactory)object2).setIgnoringElementContentWhitespace(true);
        ((DocumentBuilderFactory)object2).setCoalescing(true);
        ((DocumentBuilderFactory)object2).setIgnoringComments(true);
        try {
            object2 = ((DocumentBuilderFactory)object2).newDocumentBuilder();
            Object object3 = new Resolver();
            ((DocumentBuilder)object2).setEntityResolver((EntityResolver)object3);
            object3 = new EH();
            ((DocumentBuilder)object2).setErrorHandler((ErrorHandler)object3);
            object3 = new InputSource((InputStream)object);
            object = ((DocumentBuilder)object2).parse((InputSource)object3);
            return object;
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new Error(parserConfigurationException);
        }
    }

    static void importProperties(Properties properties, Element object) {
        NodeList nodeList = object.getChildNodes();
        int n = nodeList.getLength();
        int n2 = 0;
        if (n > 0 && nodeList.item(0).getNodeName().equals("comment")) {
            n2 = 1;
        }
        while (n2 < n) {
            Element element;
            if (nodeList.item(n2) instanceof Element && (element = (Element)nodeList.item(n2)).hasAttribute("key")) {
                object = element.getFirstChild();
                object = object == null ? "" : object.getNodeValue();
                properties.setProperty(element.getAttribute("key"), (String)object);
            }
            ++n2;
        }
    }

    static void load(Properties serializable, InputStream object) throws IOException, InvalidPropertiesFormatException {
        block2 : {
            Element element;
            try {
                object = XMLUtils.getLoadingDoc((InputStream)object);
                element = object.getDocumentElement();
                object = element.getAttribute("version");
                if (((String)object).compareTo(EXTERNAL_XML_VERSION) > 0) break block2;
            }
            catch (SAXException sAXException) {
                throw new InvalidPropertiesFormatException(sAXException);
            }
            XMLUtils.importProperties((Properties)serializable, element);
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Exported Properties file format version ");
        ((StringBuilder)serializable).append((String)object);
        ((StringBuilder)serializable).append(" is not supported. This java installation can read versions ");
        ((StringBuilder)serializable).append(EXTERNAL_XML_VERSION);
        ((StringBuilder)serializable).append(" or older. You may need to install a newer version of JDK.");
        throw new InvalidPropertiesFormatException(((StringBuilder)serializable).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void save(Properties properties, OutputStream outputStream, String string, String string2) throws IOException {
        Object object = DocumentBuilderFactory.newInstance();
        Object object2 = null;
        try {
            object2 = object = ((DocumentBuilderFactory)object).newDocumentBuilder();
        }
        catch (ParserConfigurationException parserConfigurationException) {
            // empty catch block
        }
        object2 = ((DocumentBuilder)object2).newDocument();
        object = (Element)object2.appendChild(object2.createElement("properties"));
        if (string != null) {
            ((Element)object.appendChild(object2.createElement("comment"))).appendChild(object2.createTextNode(string));
        }
        synchronized (properties) {
            Iterator<String> iterator = properties.stringPropertyNames().iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [3, 5, 6] lbl17 : MonitorExitStatement: MONITOREXIT : var0
                    XMLUtils.emitDocument((Document)object2, outputStream, string2);
                    return;
                }
                string = iterator.next();
                Element element = (Element)object.appendChild(object2.createElement("entry"));
                element.setAttribute("key", string);
                element.appendChild(object2.createTextNode(properties.getProperty(string)));
            } while (true);
        }
    }

    private static class EH
    implements ErrorHandler {
        private EH() {
        }

        @Override
        public void error(SAXParseException sAXParseException) throws SAXException {
            throw sAXParseException;
        }

        @Override
        public void fatalError(SAXParseException sAXParseException) throws SAXException {
            throw sAXParseException;
        }

        @Override
        public void warning(SAXParseException sAXParseException) throws SAXException {
            throw sAXParseException;
        }
    }

    private static class Resolver
    implements EntityResolver {
        private Resolver() {
        }

        @Override
        public InputSource resolveEntity(String object, String string) throws SAXException {
            if (string.equals(XMLUtils.PROPS_DTD_URI)) {
                object = new InputSource(new StringReader(XMLUtils.PROPS_DTD));
                ((InputSource)object).setSystemId(XMLUtils.PROPS_DTD_URI);
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid system identifier: ");
            ((StringBuilder)object).append(string);
            throw new SAXException(((StringBuilder)object).toString());
        }
    }

}

