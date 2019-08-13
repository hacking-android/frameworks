/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.serialize;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class SerializerUtils {
    public static void addAttribute(SerializationHandler serializationHandler, int n) throws TransformerException {
        String string;
        DTM dTM = ((TransformerImpl)serializationHandler.getTransformer()).getXPathContext().getDTM(n);
        if (SerializerUtils.isDefinedNSDecl(serializationHandler, n, dTM)) {
            return;
        }
        String string2 = string = dTM.getNamespaceURI(n);
        if (string == null) {
            string2 = "";
        }
        try {
            serializationHandler.addAttribute(string2, dTM.getLocalName(n), dTM.getNodeName(n), "CDATA", dTM.getNodeValue(n), false);
        }
        catch (SAXException sAXException) {
            // empty catch block
        }
    }

    public static void addAttributes(SerializationHandler serializationHandler, int n) throws TransformerException {
        DTM dTM = ((TransformerImpl)serializationHandler.getTransformer()).getXPathContext().getDTM(n);
        n = dTM.getFirstAttribute(n);
        while (-1 != n) {
            SerializerUtils.addAttribute(serializationHandler, n);
            n = dTM.getNextAttribute(n);
        }
    }

    public static void ensureNamespaceDeclDeclared(SerializationHandler serializationHandler, DTM object, int n) throws SAXException {
        Object object2;
        String string = object.getNodeValue(n);
        object = object.getNodeNameX(n);
        if (!(string == null || string.length() <= 0 || object == null || (object2 = serializationHandler.getNamespaceMappings()) == null || (object2 = ((NamespaceMappings)object2).lookupNamespace((String)object)) != null && ((String)object2).equals(string))) {
            serializationHandler.startPrefixMapping((String)object, string, false);
        }
    }

    public static boolean isDefinedNSDecl(SerializationHandler object, int n, DTM dTM) {
        return 13 == dTM.getNodeType(n) && (object = object.getNamespaceURIFromPrefix(dTM.getNodeNameX(n))) != null && ((String)object).equals(dTM.getStringValue(n));
    }

    public static void outputResultTreeFragment(SerializationHandler serializationHandler, XObject object, XPathContext xPathContext) throws SAXException {
        int n = ((XObject)object).rtf();
        object = xPathContext.getDTM(n);
        if (object != null) {
            n = object.getFirstChild(n);
            while (-1 != n) {
                serializationHandler.flushPending();
                if (object.getNodeType(n) == 1 && object.getNamespaceURI(n) == null) {
                    serializationHandler.startPrefixMapping("", "");
                }
                object.dispatchToEvents(n, serializationHandler);
                n = object.getNextSibling(n);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void processNSDecls(SerializationHandler serializationHandler, int n, int n2, DTM object) throws TransformerException {
        if (n2 == 1) {
            try {
                n2 = object.getFirstNamespaceNode(n, true);
                while (-1 != n2) {
                    String string = object.getNodeNameX(n2);
                    String string2 = serializationHandler.getNamespaceURIFromPrefix(string);
                    String string3 = object.getNodeValue(n2);
                    if (!string3.equalsIgnoreCase(string2)) {
                        serializationHandler.startPrefixMapping(string, string3, false);
                    }
                    n2 = object.getNextNamespaceNode(n, n2, true);
                }
                return;
            }
            catch (SAXException sAXException) {
                throw new TransformerException(sAXException);
            }
        }
        if (n2 != 13) return;
        String string = object.getNodeNameX(n);
        String string4 = serializationHandler.getNamespaceURIFromPrefix(string);
        if (((String)(object = object.getNodeValue(n))).equalsIgnoreCase(string4)) return;
        serializationHandler.startPrefixMapping(string, (String)object, false);
    }
}

