/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.io.PrintStream;
import javax.xml.transform.SourceLocator;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.ChunkedIntArray;
import org.apache.xml.dtm.ref.DTMStringPool;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.dtm.ref.IncrementalSAXSource;
import org.apache.xml.dtm.ref.IncrementalSAXSource_Filter;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class DTMDocumentImpl
implements DTM,
ContentHandler,
LexicalHandler {
    protected static final int DOCHANDLE_MASK = -8388608;
    protected static final byte DOCHANDLE_SHIFT = 22;
    protected static final int NODEHANDLE_MASK = 8388607;
    private static final String[] fixednames = new String[]{null, null, null, "#text", "#cdata_section", null, null, null, "#comment", "#document", null, "#document-fragment", null};
    private final boolean DEBUG;
    int currentParent = 0;
    private boolean done = false;
    int[] gotslot = new int[4];
    private FastStringBuffer m_char = new FastStringBuffer();
    private int m_char_current_start = 0;
    protected int m_currentNode = -1;
    int m_docElement = -1;
    int m_docHandle = -1;
    protected String m_documentBaseURI;
    private ExpandedNameTable m_expandedNames = new ExpandedNameTable();
    private IncrementalSAXSource m_incrSAXSource = null;
    boolean m_isError = false;
    private DTMStringPool m_localNames = new DTMStringPool();
    private DTMStringPool m_nsNames = new DTMStringPool();
    private DTMStringPool m_prefixNames = new DTMStringPool();
    private XMLStringFactory m_xsf;
    ChunkedIntArray nodes = new ChunkedIntArray(4);
    int previousSibling = 0;
    private boolean previousSiblingWasParent = false;

    public DTMDocumentImpl(DTMManager dTMManager, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory) {
        this.DEBUG = false;
        this.initDocument(n);
        this.m_xsf = xMLStringFactory;
    }

    private final int appendNode(int n, int n2, int n3, int n4) {
        n = this.nodes.appendSlot(n, n2, n3, n4);
        if (this.previousSiblingWasParent) {
            this.nodes.writeEntry(this.previousSibling, 2, n);
        }
        this.previousSiblingWasParent = false;
        return n;
    }

    private void processAccumulatedText() {
        int n;
        int n2 = this.m_char.length();
        if (n2 != (n = this.m_char_current_start)) {
            this.appendTextChild(n, n2 - n);
            this.m_char_current_start = n2;
        }
    }

    void appendAttribute(int n, int n2, int n3, boolean bl, int n4, int n5) {
        int n6 = this.currentParent;
        n2 = n3 << 16 | n2;
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("set w3=");
        stringBuilder.append(n2);
        stringBuilder.append(" ");
        stringBuilder.append(n2 >> 16);
        stringBuilder.append("/");
        stringBuilder.append(65535 & n2);
        printStream.println(stringBuilder.toString());
        this.previousSibling = n = this.appendNode(n << 16 | 2, n6, 0, n2);
        this.appendNode(3, n, n4, n5);
        this.previousSiblingWasParent = true;
    }

    @Override
    public void appendChild(int n, boolean bl, boolean bl2) {
        if ((-8388608 & n) != this.m_docHandle) {
            // empty if block
        }
    }

    void appendComment(int n, int n2) {
        this.previousSibling = this.appendNode(8, this.currentParent, n, n2);
    }

    void appendEndDocument() {
        this.done = true;
    }

    void appendEndElement() {
        int n;
        if (this.previousSiblingWasParent) {
            this.nodes.writeEntry(this.previousSibling, 2, -1);
        }
        this.previousSibling = n = this.currentParent;
        this.nodes.readSlot(n, this.gotslot);
        this.currentParent = this.gotslot[1] & 65535;
        this.previousSiblingWasParent = true;
    }

    void appendNSDeclaration(int n, int n2, boolean bl) {
        this.m_nsNames.stringToIndex("http://www.w3.org/2000/xmlns/");
        this.previousSibling = this.appendNode(this.m_nsNames.stringToIndex("http://www.w3.org/2000/xmlns/") << 16 | 13, this.currentParent, 0, n2);
        this.previousSiblingWasParent = false;
    }

    void appendStartDocument() {
        this.m_docElement = -1;
        this.initDocument(0);
    }

    void appendStartElement(int n, int n2, int n3) {
        int n4 = this.currentParent;
        n2 = n3 << 16 | n2;
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("set w3=");
        stringBuilder.append(n2);
        stringBuilder.append(" ");
        stringBuilder.append(n2 >> 16);
        stringBuilder.append("/");
        stringBuilder.append(65535 & n2);
        printStream.println(stringBuilder.toString());
        this.currentParent = n = this.appendNode(n << 16 | 1, n4, 0, n2);
        this.previousSibling = 0;
        if (this.m_docElement == -1) {
            this.m_docElement = n;
        }
    }

    void appendTextChild(int n, int n2) {
        this.previousSibling = this.appendNode(3, this.currentParent, n, n2);
    }

    @Override
    public void appendTextChild(String string) {
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        this.m_char.append(arrc, n, n2);
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        this.processAccumulatedText();
        this.m_char.append(arrc, n, n2);
        this.appendComment(this.m_char_current_start, n2);
        this.m_char_current_start += n2;
    }

    @Override
    public void dispatchCharactersEvents(int n, ContentHandler contentHandler, boolean bl) throws SAXException {
    }

    @Override
    public void dispatchToEvents(int n, ContentHandler contentHandler) throws SAXException {
    }

    @Override
    public void documentRegistration() {
    }

    @Override
    public void documentRelease() {
    }

    @Override
    public void endCDATA() throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
        this.appendEndDocument();
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        this.processAccumulatedText();
        this.appendEndElement();
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
    }

    @Override
    public int getAttributeNode(int n, String arrn, String string) {
        int n2 = this.m_nsNames.stringToIndex((String)arrn);
        int n3 = this.m_localNames.stringToIndex(string);
        int n4 = n & 8388607;
        this.nodes.readSlot(n4, this.gotslot);
        short s = (short)(this.gotslot[0] & 65535);
        n = n4;
        if (s == 1) {
            n = n4 + 1;
        }
        while (s == 2) {
            arrn = this.gotslot;
            if (n2 == arrn[0] << 16 && arrn[3] == n3) {
                return this.m_docHandle | n;
            }
            arrn = this.gotslot;
            n = arrn[2];
            this.nodes.readSlot(n, arrn);
        }
        return -1;
    }

    @Override
    public DTMAxisIterator getAxisIterator(int n) {
        return null;
    }

    @Override
    public DTMAxisTraverser getAxisTraverser(int n) {
        return null;
    }

    FastStringBuffer getContentBuffer() {
        return this.m_char;
    }

    @Override
    public ContentHandler getContentHandler() {
        IncrementalSAXSource incrementalSAXSource = this.m_incrSAXSource;
        if (incrementalSAXSource instanceof IncrementalSAXSource_Filter) {
            return (ContentHandler)((Object)incrementalSAXSource);
        }
        return this;
    }

    @Override
    public DTDHandler getDTDHandler() {
        return null;
    }

    @Override
    public DeclHandler getDeclHandler() {
        return null;
    }

    @Override
    public int getDocument() {
        return this.m_docHandle;
    }

    @Override
    public boolean getDocumentAllDeclarationsProcessed() {
        return false;
    }

    @Override
    public String getDocumentBaseURI() {
        return this.m_documentBaseURI;
    }

    @Override
    public String getDocumentEncoding(int n) {
        return null;
    }

    public int getDocumentRoot() {
        return this.m_docHandle | this.m_docElement;
    }

    @Override
    public int getDocumentRoot(int n) {
        if ((8388607 & n) == 0) {
            return -1;
        }
        return -8388608 & n;
    }

    @Override
    public String getDocumentStandalone(int n) {
        return null;
    }

    @Override
    public String getDocumentSystemIdentifier(int n) {
        return null;
    }

    @Override
    public String getDocumentTypeDeclarationPublicIdentifier() {
        return null;
    }

    @Override
    public String getDocumentTypeDeclarationSystemIdentifier() {
        return null;
    }

    @Override
    public String getDocumentVersion(int n) {
        return null;
    }

    @Override
    public int getElementById(String string) {
        return 0;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public int getExpandedTypeID(int n) {
        this.nodes.readSlot(n, this.gotslot);
        CharSequence charSequence = this.m_localNames.indexToString(this.gotslot[3]);
        String string = ((String)charSequence).substring(((String)charSequence).indexOf(":") + 1);
        String string2 = this.m_nsNames.indexToString(this.gotslot[0] << 16);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(":");
        ((StringBuilder)charSequence).append(string);
        charSequence = ((StringBuilder)charSequence).toString();
        return this.m_nsNames.stringToIndex((String)charSequence);
    }

    @Override
    public int getExpandedTypeID(String string, String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(string2);
        string = stringBuilder.toString();
        return this.m_nsNames.stringToIndex(string);
    }

    @Override
    public int getFirstAttribute(int n) {
        block1 : {
            int n2 = n & 8388607;
            int n3 = this.nodes.readEntry(n2, 0);
            n = -1;
            if (1 != (n3 & 65535)) {
                return -1;
            }
            n3 = n2 + 1;
            if (2 != (this.nodes.readEntry(n3, 0) & 65535)) break block1;
            n = n3 | this.m_docHandle;
        }
        return n;
    }

    @Override
    public int getFirstChild(int n) {
        int n2 = n & 8388607;
        this.nodes.readSlot(n2, this.gotslot);
        n = (short)(this.gotslot[0] & 65535);
        if (n == 1 || n == 9 || n == 5) {
            int[] arrn;
            n = n2 + 1;
            this.nodes.readSlot(n, this.gotslot);
            while (2 == ((arrn = this.gotslot)[0] & 65535)) {
                n = arrn[2];
                if (n == -1) {
                    return -1;
                }
                this.nodes.readSlot(n, arrn);
            }
            if (arrn[1] == n2) {
                return this.m_docHandle | n;
            }
        }
        return -1;
    }

    @Override
    public int getFirstNamespaceNode(int n, boolean bl) {
        return -1;
    }

    @Override
    public int getLastChild(int n) {
        int n2 = -1;
        n = this.getFirstChild(n & 8388607);
        while (n != -1) {
            n2 = n;
            n = this.getNextSibling(n);
        }
        return this.m_docHandle | n2;
    }

    @Override
    public short getLevel(int n) {
        short s;
        short s2 = s = 0;
        while (n != 0) {
            s = (short)(s2 + 1);
            n = this.nodes.readEntry(n, 1);
            s2 = s;
        }
        return s2;
    }

    @Override
    public LexicalHandler getLexicalHandler() {
        IncrementalSAXSource incrementalSAXSource = this.m_incrSAXSource;
        if (incrementalSAXSource instanceof IncrementalSAXSource_Filter) {
            return (LexicalHandler)((Object)incrementalSAXSource);
        }
        return this;
    }

    @Override
    public String getLocalName(int n) {
        this.nodes.readSlot(n, this.gotslot);
        n = (short)(this.gotslot[0] & 65535);
        String string = "";
        if (n == 1 || n == 2) {
            String string2;
            n = this.gotslot[3];
            string = string2 = this.m_localNames.indexToString(65535 & n);
            if (string2 == null) {
                string = "";
            }
        }
        return string;
    }

    @Override
    public String getLocalNameFromExpandedNameID(int n) {
        String string = this.m_localNames.indexToString(n);
        return string.substring(string.indexOf(":") + 1);
    }

    public DTMStringPool getLocalNameTable() {
        return this.m_localNames;
    }

    @Override
    public String getNamespaceFromExpandedNameID(int n) {
        String string = this.m_localNames.indexToString(n);
        return string.substring(0, string.indexOf(":"));
    }

    @Override
    public String getNamespaceURI(int n) {
        return null;
    }

    @Override
    public int getNextAttribute(int n) {
        this.nodes.readSlot(n &= 8388607, this.gotslot);
        int[] arrn = this.gotslot;
        short s = (short)(arrn[0] & 65535);
        if (s == 1) {
            return this.getFirstAttribute(n);
        }
        if (s == 2 && arrn[2] != -1) {
            n = this.m_docHandle;
            return arrn[2] | n;
        }
        return -1;
    }

    public int getNextDescendant(int n, int n2) {
        int n3 = n & 8388607;
        n = n2 &= 8388607;
        if (n2 == 0) {
            return -1;
        }
        while (!(this.m_isError || this.done && n > this.nodes.slotsUsed())) {
            if (n > n3) {
                this.nodes.readSlot(n + 1, this.gotslot);
                int[] arrn = this.gotslot;
                if (arrn[2] != 0) {
                    if ((short)(arrn[0] & 65535) == 2) {
                        n += 2;
                        continue;
                    }
                    if (arrn[1] < n3) break;
                    return this.m_docHandle | n + 1;
                }
                if (this.done) break;
                continue;
            }
            ++n;
        }
        return -1;
    }

    public int getNextFollowing(int n, int n2) {
        return -1;
    }

    @Override
    public int getNextNamespaceNode(int n, int n2, boolean bl) {
        return -1;
    }

    public int getNextPreceding(int n, int n2) {
        n2 &= 8388607;
        while (n2 > 1) {
            if (2 == (this.nodes.readEntry(--n2, 0) & 65535)) continue;
            return this.m_docHandle | this.nodes.specialFind(n, n2);
        }
        return -1;
    }

    @Override
    public int getNextSibling(int n) {
        if ((n &= 8388607) == 0) {
            return -1;
        }
        int n2 = this.nodes.readEntry(n, 0) & 65535;
        if (n2 == 1 || n2 == 2 || n2 == 5) {
            n2 = this.nodes.readEntry(n, 2);
            if (n2 == -1) {
                return -1;
            }
            if (n2 != 0) {
                return this.m_docHandle | n2;
            }
        }
        n2 = this.nodes.readEntry(n, 1);
        ChunkedIntArray chunkedIntArray = this.nodes;
        if (chunkedIntArray.readEntry(++n, 1) == n2) {
            return this.m_docHandle | n;
        }
        return -1;
    }

    @Override
    public Node getNode(int n) {
        return null;
    }

    @Override
    public String getNodeName(int n) {
        this.nodes.readSlot(n, this.gotslot);
        Object object = this.gotslot;
        n = (short)(object[0] & 65535);
        CharSequence charSequence = fixednames[n];
        Object object2 = charSequence;
        if (charSequence == null) {
            n = object[3];
            object2 = System.out;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("got i=");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(n >> 16);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(n & 65535);
            ((PrintStream)object2).println(((StringBuilder)charSequence).toString());
            charSequence = this.m_localNames.indexToString(65535 & n);
            object = this.m_prefixNames.indexToString(n >> 16);
            object2 = charSequence;
            if (object != null) {
                object2 = charSequence;
                if (((String)object).length() > 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(":");
                    ((StringBuilder)object2).append((String)charSequence);
                    object2 = ((StringBuilder)object2).toString();
                }
            }
        }
        return object2;
    }

    @Override
    public String getNodeNameX(int n) {
        return null;
    }

    @Override
    public short getNodeType(int n) {
        return (short)(this.nodes.readEntry(n, 0) & 65535);
    }

    @Override
    public String getNodeValue(int n) {
        Object object;
        block2 : {
            int[] arrn;
            block1 : {
                block0 : {
                    this.nodes.readSlot(n, this.gotslot);
                    arrn = this.gotslot;
                    int n2 = arrn[0] & 255;
                    object = null;
                    if (n2 == 2) break block0;
                    if (n2 == 3 || n2 == 4 || n2 == 8) break block1;
                    break block2;
                }
                this.nodes.readSlot(n + 1, arrn);
            }
            object = this.m_char;
            arrn = this.gotslot;
            object = ((FastStringBuffer)object).getString(arrn[2], arrn[3]);
        }
        return object;
    }

    public DTMStringPool getNsNameTable() {
        return this.m_nsNames;
    }

    @Override
    public int getOwnerDocument(int n) {
        if ((8388607 & n) == 0) {
            return -1;
        }
        return -8388608 & n;
    }

    @Override
    public int getParent(int n) {
        return this.m_docHandle | this.nodes.readEntry(n, 1);
    }

    @Override
    public String getPrefix(int n) {
        this.nodes.readSlot(n, this.gotslot);
        n = (short)(this.gotslot[0] & 65535);
        String string = "";
        if (n == 1 || n == 2) {
            String string2;
            n = this.gotslot[3];
            string = string2 = this.m_prefixNames.indexToString(n >> 16);
            if (string2 == null) {
                string = "";
            }
        }
        return string;
    }

    public DTMStringPool getPrefixNameTable() {
        return this.m_prefixNames;
    }

    @Override
    public int getPreviousSibling(int n) {
        int n2 = n & 8388607;
        if (n2 == 0) {
            return -1;
        }
        n = this.nodes.readEntry(n2, 1);
        int n3 = -1;
        n = this.getFirstChild(n);
        while (n != n2) {
            n3 = n;
            n = this.getNextSibling(n);
        }
        return this.m_docHandle | n3;
    }

    @Override
    public SourceLocator getSourceLocatorFor(int n) {
        return null;
    }

    @Override
    public XMLString getStringValue(int n) {
        this.nodes.readSlot(n, this.gotslot);
        n = this.gotslot[0] & 255;
        Object object = null;
        if (n == 3 || n == 4 || n == 8) {
            FastStringBuffer fastStringBuffer = this.m_char;
            object = this.gotslot;
            object = fastStringBuffer.getString(object[2], object[3]);
        }
        return this.m_xsf.newstr((String)object);
    }

    @Override
    public char[] getStringValueChunk(int n, int n2, int[] arrn) {
        return new char[0];
    }

    @Override
    public int getStringValueChunkCount(int n) {
        return 0;
    }

    @Override
    public DTMAxisIterator getTypedAxisIterator(int n, int n2) {
        return null;
    }

    @Override
    public String getUnparsedEntityURI(String string) {
        return null;
    }

    @Override
    public boolean hasChildNodes(int n) {
        boolean bl = this.getFirstChild(n) != -1;
        return bl;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
    }

    final void initDocument(int n) {
        this.m_docHandle = n << 22;
        this.nodes.writeSlot(0, 9, -1, -1, 0);
        this.done = false;
    }

    @Override
    public boolean isAttributeSpecified(int n) {
        return false;
    }

    @Override
    public boolean isCharacterElementContentWhitespace(int n) {
        return false;
    }

    @Override
    public boolean isDocumentAllDeclarationsProcessed(int n) {
        return false;
    }

    @Override
    public boolean isNodeAfter(int n, int n2) {
        return false;
    }

    @Override
    public boolean isSupported(String string, String string2) {
        return false;
    }

    @Override
    public void migrateTo(DTMManager dTMManager) {
    }

    @Override
    public boolean needsTwoThreads() {
        boolean bl = this.m_incrSAXSource != null;
        return bl;
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.processAccumulatedText();
    }

    void setContentBuffer(FastStringBuffer fastStringBuffer) {
        this.m_char = fastStringBuffer;
    }

    @Override
    public void setDocumentBaseURI(String string) {
        this.m_documentBaseURI = string;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void setFeature(String string, boolean bl) {
    }

    public void setIncrementalSAXSource(IncrementalSAXSource incrementalSAXSource) {
        this.m_incrSAXSource = incrementalSAXSource;
        incrementalSAXSource.setContentHandler(this);
        incrementalSAXSource.setLexicalHandler(this);
    }

    public void setLocalNameTable(DTMStringPool dTMStringPool) {
        this.m_localNames = dTMStringPool;
    }

    public void setNsNameTable(DTMStringPool dTMStringPool) {
        this.m_nsNames = dTMStringPool;
    }

    public void setPrefixNameTable(DTMStringPool dTMStringPool) {
        this.m_prefixNames = dTMStringPool;
    }

    @Override
    public void setProperty(String string, Object object) {
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        this.processAccumulatedText();
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
        this.appendStartDocument();
    }

    @Override
    public void startElement(String string, String object, String object2, Attributes attributes) throws SAXException {
        int n;
        int n2;
        this.processAccumulatedText();
        String string2 = null;
        int n3 = ((String)object2).indexOf(58);
        if (n3 > 0) {
            string2 = ((String)object2).substring(0, n3);
        }
        object2 = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prefix=");
        stringBuilder.append(string2);
        stringBuilder.append(" index=");
        stringBuilder.append(this.m_prefixNames.stringToIndex(string2));
        ((PrintStream)object2).println(stringBuilder.toString());
        this.appendStartElement(this.m_nsNames.stringToIndex(string), this.m_localNames.stringToIndex((String)object), this.m_prefixNames.stringToIndex(string2));
        n3 = attributes == null ? 0 : attributes.getLength();
        for (n = n3 - 1; n >= 0; --n) {
            string = attributes.getQName(n);
            if (!string.startsWith("xmlns:") && !"xmlns".equals(string)) continue;
            n2 = string.indexOf(58);
            string = n2 > 0 ? string.substring(0, n2) : null;
            this.appendNSDeclaration(this.m_prefixNames.stringToIndex(string), this.m_nsNames.stringToIndex(attributes.getValue(n)), attributes.getType(n).equalsIgnoreCase("ID"));
        }
        --n3;
        while (n3 >= 0) {
            object2 = attributes.getQName(n3);
            if (!((String)object2).startsWith("xmlns:") && !"xmlns".equals(object2)) {
                n = ((String)object2).indexOf(58);
                if (n > 0) {
                    string = ((String)object2).substring(0, n);
                    object = ((String)object2).substring(n + 1);
                } else {
                    string = "";
                    object = object2;
                }
                this.m_char.append(attributes.getValue(n3));
                int n4 = this.m_char.length();
                if (!"xmlns".equals(string) && !"xmlns".equals(object2)) {
                    int n5 = this.m_nsNames.stringToIndex(attributes.getURI(n3));
                    n = this.m_localNames.stringToIndex((String)object);
                    n2 = this.m_prefixNames.stringToIndex(string);
                    boolean bl = attributes.getType(n3).equalsIgnoreCase("ID");
                    int n6 = this.m_char_current_start;
                    this.appendAttribute(n5, n, n2, bl, n6, n4 - n6);
                }
                this.m_char_current_start = n4;
            }
            --n3;
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
    }

    @Override
    public boolean supportsPreStripping() {
        return false;
    }
}

