/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class XmlSupport {
    private static final String EXTERNAL_XML_VERSION = "1.0";
    private static final String MAP_XML_VERSION = "1.0";
    private static final String PREFS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for preferences --><!ELEMENT preferences (root) ><!ATTLIST preferences EXTERNAL_XML_VERSION CDATA \"0.0\"  ><!ELEMENT root (map, node*) ><!ATTLIST root          type (system|user) #REQUIRED ><!ELEMENT node (map, node*) ><!ATTLIST node          name CDATA #REQUIRED ><!ELEMENT map (entry*) ><!ATTLIST map  MAP_XML_VERSION CDATA \"0.0\"  ><!ELEMENT entry EMPTY ><!ATTLIST entry          key CDATA #REQUIRED          value CDATA #REQUIRED >";
    private static final String PREFS_DTD_URI = "http://java.sun.com/dtd/preferences.dtd";

    XmlSupport() {
    }

    private static void ImportPrefs(Preferences preferences, Element element) {
        NodeList nodeList = XmlSupport.elementNodesOf(element.getChildNodes());
        int n = nodeList.getLength();
        for (int i = 0; i < n; ++i) {
            element = (Element)nodeList.item(i);
            preferences.put(element.getAttribute("key"), element.getAttribute("value"));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void ImportSubtree(Preferences preferences, Element object) {
        int n;
        Preferences[] arrpreferences;
        NodeList nodeList = XmlSupport.elementNodesOf(object.getChildNodes());
        int n2 = nodeList.getLength();
        object = ((AbstractPreferences)preferences).lock;
        synchronized (object) {
            if (((AbstractPreferences)preferences).isRemoved()) {
                return;
            }
            XmlSupport.ImportPrefs(preferences, (Element)nodeList.item(0));
            arrpreferences = new Preferences[n2 - 1];
            for (n = 1; n < n2; ++n) {
                arrpreferences[n - 1] = preferences.node(((Element)nodeList.item(n)).getAttribute("name"));
            }
        }
        n = 1;
        while (n < n2) {
            XmlSupport.ImportSubtree(arrpreferences[n - 1], (Element)nodeList.item(n));
            ++n;
        }
        return;
    }

    private static Document createPrefsDoc(String object) {
        try {
            DOMImplementation dOMImplementation = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
            object = dOMImplementation.createDocument(null, (String)object, dOMImplementation.createDocumentType((String)object, null, PREFS_DTD_URI));
            return object;
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new AssertionError(parserConfigurationException);
        }
    }

    private static NodeList elementNodesOf(NodeList nodeList) {
        ArrayList<Element> arrayList = new ArrayList<Element>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            if (!(node instanceof Element)) continue;
            arrayList.add((Element)node);
        }
        return new NodeListAdapter(arrayList);
    }

    static void export(OutputStream outputStream, Preferences preferences, boolean bl) throws IOException, BackingStoreException {
        if (!((AbstractPreferences)preferences).isRemoved()) {
            Document document = XmlSupport.createPrefsDoc("preferences");
            Object object = document.getDocumentElement();
            object.setAttribute("EXTERNAL_XML_VERSION", "1.0");
            Element element = (Element)object.appendChild(document.createElement("root"));
            object = preferences.isUserNode() ? "user" : "system";
            element.setAttribute("type", (String)object);
            ArrayList<Preferences> arrayList = new ArrayList<Preferences>();
            Object object2 = preferences;
            object = ((Preferences)object2).parent();
            while (object != null) {
                arrayList.add((Preferences)object2);
                Preferences preferences2 = ((Preferences)object).parent();
                object2 = object;
                object = preferences2;
            }
            object = element;
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                object.appendChild(document.createElement("map"));
                object = (Element)object.appendChild(document.createElement("node"));
                object.setAttribute("name", ((Preferences)arrayList.get(i)).name());
            }
            XmlSupport.putPreferencesInXml((Element)object, document, preferences, bl);
            XmlSupport.writeDoc(document, outputStream);
            return;
        }
        throw new IllegalStateException("Node has been removed");
    }

    static void exportMap(OutputStream outputStream, Map<String, String> object) throws IOException {
        Document document = XmlSupport.createPrefsDoc("map");
        Element element = document.getDocumentElement();
        element.setAttribute("MAP_XML_VERSION", "1.0");
        for (Map.Entry entry : object.entrySet()) {
            Element element2 = (Element)element.appendChild(document.createElement("entry"));
            element2.setAttribute("key", (String)entry.getKey());
            element2.setAttribute("value", (String)entry.getValue());
        }
        XmlSupport.writeDoc(document, outputStream);
    }

    static void importMap(InputStream object, Map<String, String> object2) throws IOException, InvalidPreferencesFormatException {
        Object object3;
        block6 : {
            object3 = XmlSupport.loadPrefsDoc((InputStream)object).getDocumentElement();
            object = object3.getAttribute("MAP_XML_VERSION");
            if (((String)object).compareTo("1.0") > 0) break block6;
            object3 = object3.getChildNodes();
            int n = object3.getLength();
            for (int i = 0; i < n; ++i) {
                if (!(object3.item(i) instanceof Element)) continue;
                object = (Element)object3.item(i);
                object2.put(object.getAttribute("key"), object.getAttribute("value"));
                continue;
            }
            return;
        }
        try {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Preferences map file format version ");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" is not supported. This java installation can read versions ");
            ((StringBuilder)object2).append("1.0");
            ((StringBuilder)object2).append(" or older. You may need to install a newer version of JDK.");
            object3 = new InvalidPreferencesFormatException(((StringBuilder)object2).toString());
            throw object3;
        }
        catch (SAXException sAXException) {
            throw new InvalidPreferencesFormatException(sAXException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void importPreferences(InputStream object) throws IOException, InvalidPreferencesFormatException {
        try {
            Object object2 = XmlSupport.loadPrefsDoc((InputStream)object);
            object = object2.getDocumentElement().getAttribute("EXTERNAL_XML_VERSION");
            if (((String)object).compareTo("1.0") > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exported preferences file format version ");
                stringBuilder.append((String)object);
                stringBuilder.append(" is not supported. This java installation can read versions ");
                stringBuilder.append("1.0");
                stringBuilder.append(" or older. You may need to install a newer version of JDK.");
                object2 = new InvalidPreferencesFormatException(stringBuilder.toString());
                throw object2;
            }
            object = object2.getDocumentElement().getElementsByTagName("root");
            if (object != null && object.getLength() == 1) {
                object2 = (Element)object.item(0);
                object = object2.getAttribute("type").equals("user") ? Preferences.userRoot() : Preferences.systemRoot();
                XmlSupport.ImportSubtree((Preferences)object, (Element)object2);
                return;
            }
            object = new InvalidPreferencesFormatException("invalid root node");
            throw object;
        }
        catch (SAXException sAXException) {
            throw new InvalidPreferencesFormatException(sAXException);
        }
    }

    private static Document loadPrefsDoc(InputStream object) throws SAXException, IOException {
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
            throw new AssertionError(parserConfigurationException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void putPreferencesInXml(Element element, Document document, Preferences object, boolean bl) throws BackingStoreException {
        int n;
        Object object2;
        Object[] arrobject = null;
        Object object3 = null;
        Object object4 = ((AbstractPreferences)object).lock;
        // MONITORENTER : object4
        if (((AbstractPreferences)object).isRemoved()) {
            element.getParentNode().removeChild(element);
            // MONITOREXIT : object4
            return;
        }
        Object[] arrobject2 = ((Preferences)object).keys();
        Element element2 = (Element)element.appendChild(document.createElement("map"));
        int n2 = 0;
        for (n = 0; n < arrobject2.length; ++n) {
            object2 = (Element)element2.appendChild(document.createElement("entry"));
            object2.setAttribute("key", (String)arrobject2[n]);
            object2.setAttribute("value", ((Preferences)object).get((String)arrobject2[n], null));
        }
        if (bl) {
            object2 = ((Preferences)object).childrenNames();
            arrobject2 = new Preferences[((String[])object2).length];
            n = n2;
            do {
                arrobject = arrobject2;
                object3 = object2;
                if (n >= ((Object)object2).length) break;
                arrobject2[n] = ((Preferences)object).node((String)object2[n]);
                ++n;
            } while (true);
        }
        // MONITOREXIT : object4
        if (!bl) return;
        n = 0;
        while (n < (object3).length) {
            object = (Element)element.appendChild(document.createElement("node"));
            object.setAttribute("name", (String)object3[n]);
            XmlSupport.putPreferencesInXml((Element)object, document, (Preferences)arrobject[n], bl);
            ++n;
        }
    }

    private static final void writeDoc(Document object, OutputStream outputStream) throws IOException {
        Object object2;
        Object object3;
        try {
            object3 = TransformerFactory.newInstance();
            try {
                object2 = new Integer(2);
                ((TransformerFactory)object3).setAttribute("indent-number", object2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        catch (TransformerException transformerException) {
            throw new AssertionError(transformerException);
        }
        object2 = ((TransformerFactory)object3).newTransformer();
        ((Transformer)object2).setOutputProperty("doctype-system", object.getDoctype().getSystemId());
        ((Transformer)object2).setOutputProperty("indent", "yes");
        object3 = new DOMSource((Node)object);
        object = new OutputStreamWriter(outputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter((Writer)object);
        StreamResult streamResult = new StreamResult(bufferedWriter);
        ((Transformer)object2).transform((Source)object3, streamResult);
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

    static class NodeListAdapter
    implements NodeList {
        private final List<? extends Node> delegate;

        public NodeListAdapter(List<? extends Node> list) {
            this.delegate = Objects.requireNonNull(list);
        }

        @Override
        public int getLength() {
            return this.delegate.size();
        }

        @Override
        public Node item(int n) {
            if (n >= 0 && n < this.delegate.size()) {
                return this.delegate.get(n);
            }
            return null;
        }
    }

    private static class Resolver
    implements EntityResolver {
        private Resolver() {
        }

        @Override
        public InputSource resolveEntity(String object, String string) throws SAXException {
            if (string.equals(XmlSupport.PREFS_DTD_URI)) {
                object = new InputSource(new StringReader(XmlSupport.PREFS_DTD));
                ((InputSource)object).setSystemId(XmlSupport.PREFS_DTD_URI);
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid system identifier: ");
            ((StringBuilder)object).append(string);
            throw new SAXException(((StringBuilder)object).toString());
        }
    }

}

