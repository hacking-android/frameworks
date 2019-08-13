/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import java.io.IOException;
import java.util.Vector;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.utils.SystemIDResolver;
import org.apache.xpath.SourceTree;
import org.apache.xpath.XPathContext;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SourceTreeManager {
    private Vector m_sourceTree = new Vector();
    URIResolver m_uriResolver;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static XMLReader getXMLReader(Source object, SourceLocator sourceLocator) throws TransformerException {
        Object object2;
        block10 : {
            try {
                object = object instanceof SAXSource ? ((SAXSource)object).getXMLReader() : null;
                object2 = object;
                if (object != null) break block10;
                try {
                    object2 = SAXParserFactory.newInstance();
                    ((SAXParserFactory)object2).setNamespaceAware(true);
                    object = object2 = ((SAXParserFactory)object2).newSAXParser().getXMLReader();
                }
                catch (AbstractMethodError abstractMethodError) {
                }
                catch (NoSuchMethodError noSuchMethodError) {}
                object2 = object;
                if (object == null) {
                    object2 = XMLReaderFactory.createXMLReader();
                }
                break block10;
                catch (FactoryConfigurationError factoryConfigurationError) {
                    object2 = new SAXException(factoryConfigurationError.toString());
                    throw object2;
                }
                catch (ParserConfigurationException parserConfigurationException) {
                    object = new SAXException(parserConfigurationException);
                    throw object;
                }
            }
            catch (SAXException sAXException) {
                throw new TransformerException(sAXException.getMessage(), sourceLocator, sAXException);
            }
        }
        try {
            object2.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
            return object2;
        }
        catch (SAXException sAXException) {
            // empty catch block
        }
        return object2;
    }

    public String findURIFromDoc(int n) {
        int n2 = this.m_sourceTree.size();
        for (int i = 0; i < n2; ++i) {
            SourceTree sourceTree = (SourceTree)this.m_sourceTree.elementAt(i);
            if (n != sourceTree.m_root) continue;
            return sourceTree.m_url;
        }
        return null;
    }

    public int getNode(Source object) {
        if ((object = object.getSystemId()) == null) {
            return -1;
        }
        int n = this.m_sourceTree.size();
        for (int i = 0; i < n; ++i) {
            SourceTree sourceTree = (SourceTree)this.m_sourceTree.elementAt(i);
            if (!((String)object).equals(sourceTree.m_url)) continue;
            return sourceTree.m_root;
        }
        return -1;
    }

    public int getSourceTree(String string, String string2, SourceLocator sourceLocator, XPathContext xPathContext) throws TransformerException {
        try {
            int n = this.getSourceTree(this.resolveURI(string, string2, sourceLocator), sourceLocator, xPathContext);
            return n;
        }
        catch (IOException iOException) {
            throw new TransformerException(iOException.getMessage(), sourceLocator, iOException);
        }
    }

    public int getSourceTree(Source source, SourceLocator sourceLocator, XPathContext xPathContext) throws TransformerException {
        int n = this.getNode(source);
        if (-1 != n) {
            return n;
        }
        n = this.parseToNode(source, sourceLocator, xPathContext);
        if (-1 != n) {
            this.putDocumentInCache(n, source);
        }
        return n;
    }

    public URIResolver getURIResolver() {
        return this.m_uriResolver;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int parseToNode(Source object, SourceLocator sourceLocator, XPathContext xPathContext) throws TransformerException {
        try {
            Object object2 = xPathContext.getOwnerObject();
            object = object2 != null && object2 instanceof DTMWSFilter ? xPathContext.getDTM((Source)object, false, (DTMWSFilter)object2, false, true) : xPathContext.getDTM((Source)object, false, null, false, true);
            return object.getDocument();
        }
        catch (Exception exception) {
            throw new TransformerException(exception.getMessage(), sourceLocator, exception);
        }
    }

    public void putDocumentInCache(int n, Source source) {
        int n2 = this.getNode(source);
        if (-1 != n2) {
            if (n2 == n) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Programmer's Error!  putDocumentInCache found reparse of doc: ");
            stringBuilder.append(source.getSystemId());
            throw new RuntimeException(stringBuilder.toString());
        }
        if (source.getSystemId() != null) {
            this.m_sourceTree.addElement(new SourceTree(n, source.getSystemId()));
        }
    }

    public void removeDocumentFromCache(int n) {
        if (-1 == n) {
            return;
        }
        for (int i = this.m_sourceTree.size() - 1; i >= 0; --i) {
            SourceTree sourceTree = (SourceTree)this.m_sourceTree.elementAt(i);
            if (sourceTree == null || sourceTree.m_root != n) continue;
            this.m_sourceTree.removeElementAt(i);
            return;
        }
    }

    public void reset() {
        this.m_sourceTree = new Vector();
    }

    public Source resolveURI(String string, String string2, SourceLocator object) throws TransformerException, IOException {
        object = null;
        Object object2 = this.m_uriResolver;
        if (object2 != null) {
            object = object2.resolve(string2, string);
        }
        object2 = object;
        if (object == null) {
            object2 = new StreamSource(SystemIDResolver.getAbsoluteURI(string2, string));
        }
        return object2;
    }

    public void setURIResolver(URIResolver uRIResolver) {
        this.m_uriResolver = uRIResolver;
    }
}

