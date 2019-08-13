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

public class CachedXPathAPI {
    protected XPathContext xpathSupport;

    public CachedXPathAPI() {
        this.xpathSupport = new XPathContext(false);
    }

    public CachedXPathAPI(CachedXPathAPI cachedXPathAPI) {
        this.xpathSupport = cachedXPathAPI.xpathSupport;
    }

    public XObject eval(Node node, String string) throws TransformerException {
        return this.eval(node, string, node);
    }

    public XObject eval(Node node, String object, PrefixResolver prefixResolver) throws TransformerException {
        XPath xPath = new XPath((String)object, null, prefixResolver, 0, null);
        object = new XPathContext(false);
        return xPath.execute((XPathContext)object, ((XPathContext)object).getDTMHandleFromNode(node), prefixResolver);
    }

    public XObject eval(Node node, String object, Node object2) throws TransformerException {
        if (object2.getNodeType() == 9) {
            object2 = ((Document)object2).getDocumentElement();
        }
        object2 = new PrefixResolverDefault((Node)object2);
        object = new XPath((String)object, null, (PrefixResolver)object2, 0, null);
        int n = this.xpathSupport.getDTMHandleFromNode(node);
        return ((XPath)object).execute(this.xpathSupport, n, (PrefixResolver)object2);
    }

    public XPathContext getXPathContext() {
        return this.xpathSupport;
    }

    public NodeIterator selectNodeIterator(Node node, String string) throws TransformerException {
        return this.selectNodeIterator(node, string, node);
    }

    public NodeIterator selectNodeIterator(Node node, String string, Node node2) throws TransformerException {
        return this.eval(node, string, node2).nodeset();
    }

    public NodeList selectNodeList(Node node, String string) throws TransformerException {
        return this.selectNodeList(node, string, node);
    }

    public NodeList selectNodeList(Node node, String string, Node node2) throws TransformerException {
        return this.eval(node, string, node2).nodelist();
    }

    public Node selectSingleNode(Node node, String string) throws TransformerException {
        return this.selectSingleNode(node, string, node);
    }

    public Node selectSingleNode(Node node, String string, Node node2) throws TransformerException {
        return this.selectNodeIterator(node, string, node2).nextNode();
    }
}

