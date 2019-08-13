/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import javax.xml.transform.SourceLocator;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.XMLString;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public interface DTM {
    public static final short ATTRIBUTE_NODE = 2;
    public static final short CDATA_SECTION_NODE = 4;
    public static final short COMMENT_NODE = 8;
    public static final short DOCUMENT_FRAGMENT_NODE = 11;
    public static final short DOCUMENT_NODE = 9;
    public static final short DOCUMENT_TYPE_NODE = 10;
    public static final short ELEMENT_NODE = 1;
    public static final short ENTITY_NODE = 6;
    public static final short ENTITY_REFERENCE_NODE = 5;
    public static final short NAMESPACE_NODE = 13;
    public static final short NOTATION_NODE = 12;
    public static final short NTYPES = 14;
    public static final int NULL = -1;
    public static final short PROCESSING_INSTRUCTION_NODE = 7;
    public static final short ROOT_NODE = 0;
    public static final short TEXT_NODE = 3;

    public void appendChild(int var1, boolean var2, boolean var3);

    public void appendTextChild(String var1);

    public void dispatchCharactersEvents(int var1, ContentHandler var2, boolean var3) throws SAXException;

    public void dispatchToEvents(int var1, ContentHandler var2) throws SAXException;

    public void documentRegistration();

    public void documentRelease();

    public int getAttributeNode(int var1, String var2, String var3);

    public DTMAxisIterator getAxisIterator(int var1);

    public DTMAxisTraverser getAxisTraverser(int var1);

    public ContentHandler getContentHandler();

    public DTDHandler getDTDHandler();

    public DeclHandler getDeclHandler();

    public int getDocument();

    public boolean getDocumentAllDeclarationsProcessed();

    public String getDocumentBaseURI();

    public String getDocumentEncoding(int var1);

    public int getDocumentRoot(int var1);

    public String getDocumentStandalone(int var1);

    public String getDocumentSystemIdentifier(int var1);

    public String getDocumentTypeDeclarationPublicIdentifier();

    public String getDocumentTypeDeclarationSystemIdentifier();

    public String getDocumentVersion(int var1);

    public int getElementById(String var1);

    public EntityResolver getEntityResolver();

    public ErrorHandler getErrorHandler();

    public int getExpandedTypeID(int var1);

    public int getExpandedTypeID(String var1, String var2, int var3);

    public int getFirstAttribute(int var1);

    public int getFirstChild(int var1);

    public int getFirstNamespaceNode(int var1, boolean var2);

    public int getLastChild(int var1);

    public short getLevel(int var1);

    public LexicalHandler getLexicalHandler();

    public String getLocalName(int var1);

    public String getLocalNameFromExpandedNameID(int var1);

    public String getNamespaceFromExpandedNameID(int var1);

    public String getNamespaceURI(int var1);

    public int getNextAttribute(int var1);

    public int getNextNamespaceNode(int var1, int var2, boolean var3);

    public int getNextSibling(int var1);

    public Node getNode(int var1);

    public String getNodeName(int var1);

    public String getNodeNameX(int var1);

    public short getNodeType(int var1);

    public String getNodeValue(int var1);

    public int getOwnerDocument(int var1);

    public int getParent(int var1);

    public String getPrefix(int var1);

    public int getPreviousSibling(int var1);

    public SourceLocator getSourceLocatorFor(int var1);

    public XMLString getStringValue(int var1);

    public char[] getStringValueChunk(int var1, int var2, int[] var3);

    public int getStringValueChunkCount(int var1);

    public DTMAxisIterator getTypedAxisIterator(int var1, int var2);

    public String getUnparsedEntityURI(String var1);

    public boolean hasChildNodes(int var1);

    public boolean isAttributeSpecified(int var1);

    public boolean isCharacterElementContentWhitespace(int var1);

    public boolean isDocumentAllDeclarationsProcessed(int var1);

    public boolean isNodeAfter(int var1, int var2);

    public boolean isSupported(String var1, String var2);

    public void migrateTo(DTMManager var1);

    public boolean needsTwoThreads();

    public void setDocumentBaseURI(String var1);

    public void setFeature(String var1, boolean var2);

    public void setProperty(String var1, Object var2);

    public boolean supportsPreStripping();
}

