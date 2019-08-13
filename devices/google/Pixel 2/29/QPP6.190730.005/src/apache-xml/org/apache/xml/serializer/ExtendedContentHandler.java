/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import javax.xml.transform.SourceLocator;
import org.apache.xml.serializer.NamespaceMappings;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface ExtendedContentHandler
extends ContentHandler {
    public static final int HTML_ATTREMPTY = 2;
    public static final int HTML_ATTRURL = 4;
    public static final int NO_BAD_CHARS = 1;

    public void addAttribute(String var1, String var2);

    public void addAttribute(String var1, String var2, String var3, String var4, String var5) throws SAXException;

    public void addAttribute(String var1, String var2, String var3, String var4, String var5, boolean var6) throws SAXException;

    public void addAttributes(Attributes var1) throws SAXException;

    public void addUniqueAttribute(String var1, String var2, int var3) throws SAXException;

    public void addXSLAttribute(String var1, String var2, String var3);

    public void characters(String var1) throws SAXException;

    public void characters(Node var1) throws SAXException;

    public void endElement(String var1) throws SAXException;

    public void entityReference(String var1) throws SAXException;

    public NamespaceMappings getNamespaceMappings();

    public String getNamespaceURI(String var1, boolean var2);

    public String getNamespaceURIFromPrefix(String var1);

    public String getPrefix(String var1);

    public void namespaceAfterStartElement(String var1, String var2) throws SAXException;

    public void setSourceLocator(SourceLocator var1);

    public void startElement(String var1) throws SAXException;

    public void startElement(String var1, String var2, String var3) throws SAXException;

    public boolean startPrefixMapping(String var1, String var2, boolean var3) throws SAXException;
}

