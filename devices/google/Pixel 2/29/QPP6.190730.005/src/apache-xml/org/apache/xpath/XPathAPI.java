/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.PrefixResolverDefault;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

public class XPathAPI {
    public static XObject eval(Node node, String string) throws TransformerException {
        return XPathAPI.eval(node, string, node);
    }

    public static XObject eval(Node node, String object, PrefixResolver prefixResolver) throws TransformerException {
        XPath xPath = new XPath((String)object, null, prefixResolver, 0, null);
        object = new XPathContext(false);
        return xPath.execute((XPathContext)object, ((XPathContext)object).getDTMHandleFromNode(node), prefixResolver);
    }

    public static XObject eval(Node node, String string, Node object) throws TransformerException {
        XPathContext xPathContext = new XPathContext(false);
        if (object.getNodeType() == 9) {
            object = ((Document)object).getDocumentElement();
        }
        object = new PrefixResolverDefault((Node)object);
        return new XPath(string, null, (PrefixResolver)object, 0, null).execute(xPathContext, xPathContext.getDTMHandleFromNode(node), (PrefixResolver)object);
    }

    public static NodeIterator selectNodeIterator(Node node, String string) throws TransformerException {
        return XPathAPI.selectNodeIterator(node, string, node);
    }

    public static NodeIterator selectNodeIterator(Node node, String string, Node node2) throws TransformerException {
        return XPathAPI.eval(node, string, node2).nodeset();
    }

    public static NodeList selectNodeList(Node node, String string) throws TransformerException {
        return XPathAPI.selectNodeList(node, string, node);
    }

    public static NodeList selectNodeList(Node node, String string, Node node2) throws TransformerException {
        return XPathAPI.eval(node, string, node2).nodelist();
    }

    public static Node selectSingleNode(Node node, String string) throws TransformerException {
        return XPathAPI.selectSingleNode(node, string, node);
    }

    public static Node selectSingleNode(Node node, String string, Node node2) throws TransformerException {
        return XPathAPI.selectNodeIterator(node, string, node2).nextNode();
    }
}

