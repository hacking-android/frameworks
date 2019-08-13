/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.OneStepIterator;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XNodeSetForDOM;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

public class XObjectFactory {
    public static XObject create(Object object) {
        object = object instanceof XObject ? (XObject)object : (object instanceof String ? new XString((String)object) : (object instanceof Boolean ? new XBoolean((Boolean)object) : (object instanceof Double ? new XNumber((Double)object) : new XObject(object))));
        return object;
    }

    public static XObject create(Object object, XPathContext xPathContext) {
        if (object instanceof XObject) {
            object = (XObject)object;
        } else if (object instanceof String) {
            object = new XString((String)object);
        } else if (object instanceof Boolean) {
            object = new XBoolean((Boolean)object);
        } else if (object instanceof Number) {
            object = new XNumber((Number)object);
        } else if (object instanceof DTM) {
            object = (DTM)object;
            try {
                int n = object.getDocument();
                DTMAxisIterator dTMAxisIterator = object.getAxisIterator(13);
                dTMAxisIterator.setStartNode(n);
                object = new OneStepIterator(dTMAxisIterator, 13);
                object.setRoot(n, xPathContext);
                object = new XNodeSet((DTMIterator)object);
            }
            catch (Exception exception) {
                throw new WrappedRuntimeException(exception);
            }
        } else if (object instanceof DTMAxisIterator) {
            object = (DTMAxisIterator)object;
            try {
                OneStepIterator oneStepIterator = new OneStepIterator((DTMAxisIterator)object, 13);
                oneStepIterator.setRoot(object.getStartNode(), xPathContext);
                object = new XNodeSet(oneStepIterator);
            }
            catch (Exception exception) {
                throw new WrappedRuntimeException(exception);
            }
        } else {
            object = object instanceof DTMIterator ? new XNodeSet((DTMIterator)object) : (object instanceof Node ? new XNodeSetForDOM((Node)object, (DTMManager)xPathContext) : (object instanceof NodeList ? new XNodeSetForDOM((NodeList)object, xPathContext) : (object instanceof NodeIterator ? new XNodeSetForDOM((NodeIterator)object, xPathContext) : new XObject(object))));
        }
        return object;
    }
}

