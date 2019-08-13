/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref.sax2dtm;

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
import org.apache.xml.dtm.ref.DTMManagerDefault;
import org.apache.xml.dtm.ref.DTMStringPool;
import org.apache.xml.dtm.ref.DTMTreeWalker;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.dtm.ref.IncrementalSAXSource;
import org.apache.xml.dtm.ref.IncrementalSAXSource_Filter;
import org.apache.xml.dtm.ref.NodeLocator;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.IntVector;
import org.apache.xml.utils.StringVector;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.SystemIDResolver;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class SAX2DTM
extends DTMDefaultBaseIterators
implements EntityResolver,
DTDHandler,
ContentHandler,
ErrorHandler,
DeclHandler,
LexicalHandler {
    private static final boolean DEBUG = false;
    private static final int ENTITY_FIELDS_PER = 4;
    private static final int ENTITY_FIELD_NAME = 3;
    private static final int ENTITY_FIELD_NOTATIONNAME = 2;
    private static final int ENTITY_FIELD_PUBLICID = 0;
    private static final int ENTITY_FIELD_SYSTEMID = 1;
    private static final String[] m_fixednames = new String[]{null, null, null, "#text", "#cdata_section", null, null, null, "#comment", "#document", null, "#document-fragment", null};
    protected FastStringBuffer m_chars;
    protected transient int m_coalescedTextType;
    protected transient IntStack m_contextIndexes;
    protected SuballocatedIntVector m_data;
    protected SuballocatedIntVector m_dataOrQName;
    protected boolean m_endDocumentOccured;
    private Vector m_entities;
    protected Hashtable m_idAttributes;
    private IncrementalSAXSource m_incrementalSAXSource;
    protected transient boolean m_insideDTD;
    protected transient Locator m_locator;
    protected transient IntStack m_parents;
    boolean m_pastFirstElement;
    protected transient Vector m_prefixMappings;
    protected transient int m_previous;
    protected IntVector m_sourceColumn;
    protected IntVector m_sourceLine;
    protected StringVector m_sourceSystemId;
    private transient String m_systemId;
    protected int m_textPendingStart;
    protected transient int m_textType;
    protected boolean m_useSourceLocationProperty;
    protected DTMStringPool m_valuesOrPrefixes;
    protected DTMTreeWalker m_walker;

    public SAX2DTM(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        this(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl, 512, true, false);
    }

    public SAX2DTM(DTMManager object, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl, int n2, boolean bl2, boolean bl3) {
        super((DTMManager)object, source, n, dTMWSFilter, xMLStringFactory, bl, n2, bl2, bl3);
        source = null;
        this.m_incrementalSAXSource = null;
        this.m_previous = 0;
        this.m_prefixMappings = new Vector();
        this.m_textType = 3;
        this.m_coalescedTextType = 3;
        this.m_locator = null;
        this.m_systemId = null;
        this.m_insideDTD = false;
        this.m_walker = new DTMTreeWalker();
        this.m_endDocumentOccured = false;
        this.m_idAttributes = new Hashtable();
        this.m_entities = null;
        this.m_textPendingStart = -1;
        this.m_useSourceLocationProperty = false;
        this.m_pastFirstElement = false;
        if (n2 <= 64) {
            this.m_data = new SuballocatedIntVector(n2, 4);
            this.m_dataOrQName = new SuballocatedIntVector(n2, 4);
            this.m_valuesOrPrefixes = new DTMStringPool(16);
            this.m_chars = new FastStringBuffer(7, 10);
            this.m_contextIndexes = new IntStack(4);
            this.m_parents = new IntStack(4);
        } else {
            this.m_data = new SuballocatedIntVector(n2, 32);
            this.m_dataOrQName = new SuballocatedIntVector(n2, 32);
            this.m_valuesOrPrefixes = new DTMStringPool();
            this.m_chars = new FastStringBuffer(10, 13);
            this.m_contextIndexes = new IntStack();
            this.m_parents = new IntStack();
        }
        this.m_data.addElement(0);
        this.m_useSourceLocationProperty = ((DTMManager)object).getSource_location();
        object = this.m_useSourceLocationProperty ? new StringVector() : null;
        this.m_sourceSystemId = object;
        object = this.m_useSourceLocationProperty ? new IntVector() : null;
        this.m_sourceLine = object;
        object = source;
        if (this.m_useSourceLocationProperty) {
            object = new IntVector();
        }
        this.m_sourceColumn = object;
    }

    private final boolean isTextType(int n) {
        boolean bl = 3 == n || 4 == n;
        return bl;
    }

    protected int _dataOrQName(int n) {
        if (n < this.m_size) {
            return this.m_dataOrQName.elementAt(n);
        }
        do {
            if (this.nextNode()) continue;
            return -1;
        } while (n >= this.m_size);
        return this.m_dataOrQName.elementAt(n);
    }

    protected void addNewDTMID(int n) {
        try {
            if (this.m_mgr == null) {
                ClassCastException classCastException = new ClassCastException();
                throw classCastException;
            }
            DTMManagerDefault dTMManagerDefault = (DTMManagerDefault)this.m_mgr;
            int n2 = dTMManagerDefault.getFirstFreeDTMID();
            dTMManagerDefault.addDTM(this, n2, n);
            this.m_dtmIdent.addElement(n2 << 16);
        }
        catch (ClassCastException classCastException) {
            this.error(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
        }
    }

    protected int addNode(int n, int n2, int n3, int n4, int n5, boolean bl) {
        int n6;
        block4 : {
            block5 : {
                n6 = this.m_size;
                this.m_size = n6 + 1;
                if (this.m_dtmIdent.size() == n6 >>> 16) {
                    this.addNewDTMID(n6);
                }
                SuballocatedIntVector suballocatedIntVector = this.m_firstch;
                int n7 = bl ? -2 : -1;
                suballocatedIntVector.addElement(n7);
                this.m_nextsib.addElement(-2);
                this.m_parent.addElement(n3);
                this.m_exptype.addElement(n2);
                this.m_dataOrQName.addElement(n5);
                if (this.m_prevsib != null) {
                    this.m_prevsib.addElement(n4);
                }
                if (-1 != n4) {
                    this.m_nextsib.setElementAt(n6, n4);
                }
                if (this.m_locator != null && this.m_useSourceLocationProperty) {
                    this.setSourceLocation();
                }
                if (n == 2) break block4;
                if (n == 13) break block5;
                if (-1 != n4 || -1 == n3) break block4;
                this.m_firstch.setElementAt(n6, n3);
                break block4;
            }
            this.declareNamespaceInContext(n3, n6);
        }
        return n6;
    }

    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_textPendingStart == -1) {
            this.m_textPendingStart = this.m_chars.size();
            this.m_coalescedTextType = this.m_textType;
        } else if (this.m_textType == 3) {
            this.m_coalescedTextType = 3;
        }
        this.m_chars.append(arrc, n, n2);
    }

    protected void charactersFlush() {
        if (this.m_textPendingStart >= 0) {
            int n = this.m_chars.size() - this.m_textPendingStart;
            boolean bl = false;
            if (this.getShouldStripWhitespace()) {
                bl = this.m_chars.isWhitespace(this.m_textPendingStart, n);
            }
            if (bl) {
                this.m_chars.setLength(this.m_textPendingStart);
            } else if (n > 0) {
                int n2 = this.m_expandedNameTable.getExpandedTypeID(3);
                int n3 = this.m_data.size();
                this.m_previous = this.addNode(this.m_coalescedTextType, n2, this.m_parents.peek(), this.m_previous, n3, false);
                this.m_data.addElement(this.m_textPendingStart);
                this.m_data.addElement(n);
            }
            this.m_textPendingStart = -1;
            this.m_coalescedTextType = 3;
            this.m_textType = 3;
        }
    }

    public void clearCoRoutine() {
        this.clearCoRoutine(true);
    }

    public void clearCoRoutine(boolean bl) {
        IncrementalSAXSource incrementalSAXSource = this.m_incrementalSAXSource;
        if (incrementalSAXSource != null) {
            if (bl) {
                incrementalSAXSource.deliverMoreNodes(false);
            }
            this.m_incrementalSAXSource = null;
        }
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_insideDTD) {
            return;
        }
        this.charactersFlush();
        int n3 = this.m_expandedNameTable.getExpandedTypeID(8);
        n = this.m_valuesOrPrefixes.stringToIndex(new String(arrc, n, n2));
        this.m_previous = this.addNode(8, n3, this.m_parents.peek(), this.m_previous, n, false);
    }

    protected boolean declAlreadyDeclared(String string) {
        Vector vector = this.m_prefixMappings;
        int n = vector.size();
        for (int i = this.m_contextIndexes.peek(); i < n; i += 2) {
            String string2 = (String)vector.elementAt(i);
            if (string2 == null || !string2.equals(string)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void dispatchCharactersEvents(int n, ContentHandler contentHandler, boolean bl) throws SAXException {
        int n2 = this.makeNodeIdentity(n);
        if (n2 == -1) {
            return;
        }
        n = this._type(n2);
        if (this.isTextType(n)) {
            int n3 = this.m_dataOrQName.elementAt(n2);
            n = this.m_data.elementAt(n3);
            n3 = this.m_data.elementAt(n3 + 1);
            if (bl) {
                this.m_chars.sendNormalizedSAXcharacters(contentHandler, n, n3);
            } else {
                this.m_chars.sendSAXcharacters(contentHandler, n, n3);
            }
        } else {
            int n4 = this._firstch(n2);
            if (-1 != n4) {
                int n5;
                int n6;
                int n7;
                n = -1;
                int n8 = 0;
                do {
                    n6 = n;
                    n5 = n8;
                    if (this.isTextType(this._type(n4))) {
                        n5 = this._dataOrQName(n4);
                        n6 = n;
                        if (-1 == n) {
                            n6 = this.m_data.elementAt(n5);
                        }
                        n5 = n8 + this.m_data.elementAt(n5 + 1);
                    }
                    if (-1 == (n7 = this.getNextNodeIdentity(n4))) break;
                    n4 = n7;
                    n = n6;
                    n8 = n5;
                } while (this._parent(n7) >= n2);
                if (n5 > 0) {
                    if (bl) {
                        this.m_chars.sendNormalizedSAXcharacters(contentHandler, n6, n5);
                    } else {
                        this.m_chars.sendSAXcharacters(contentHandler, n6, n5);
                    }
                }
            } else if (n != 1) {
                int n9;
                n = n9 = this._dataOrQName(n2);
                if (n9 < 0) {
                    n = -n9;
                    n = this.m_data.elementAt(n + 1);
                }
                String string = this.m_valuesOrPrefixes.indexToString(n);
                if (bl) {
                    FastStringBuffer.sendNormalizedSAXcharacters(string.toCharArray(), 0, string.length(), contentHandler);
                } else {
                    contentHandler.characters(string.toCharArray(), 0, string.length());
                }
            }
        }
    }

    @Override
    public void dispatchToEvents(int n, ContentHandler contentHandler) throws SAXException {
        DTMTreeWalker dTMTreeWalker;
        DTMTreeWalker dTMTreeWalker2 = dTMTreeWalker = this.m_walker;
        if (dTMTreeWalker.getcontentHandler() != null) {
            dTMTreeWalker2 = new DTMTreeWalker();
        }
        dTMTreeWalker2.setcontentHandler(contentHandler);
        dTMTreeWalker2.setDTM(this);
        try {
            dTMTreeWalker2.traverse(n);
            return;
        }
        finally {
            dTMTreeWalker2.setcontentHandler(null);
        }
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
    }

    @Override
    public void endCDATA() throws SAXException {
        this.m_textType = 3;
    }

    @Override
    public void endDTD() throws SAXException {
        this.m_insideDTD = false;
    }

    @Override
    public void endDocument() throws SAXException {
        this.charactersFlush();
        this.m_nextsib.setElementAt(-1, 0);
        if (this.m_firstch.elementAt(0) == -2) {
            this.m_firstch.setElementAt(-1, 0);
        }
        if (-1 != this.m_previous) {
            this.m_nextsib.setElementAt(-1, this.m_previous);
        }
        this.m_parents = null;
        this.m_prefixMappings = null;
        this.m_contextIndexes = null;
        this.m_endDocumentOccured = true;
        this.m_locator = null;
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        this.charactersFlush();
        this.m_contextIndexes.quickPop(1);
        int n = this.m_contextIndexes.peek();
        if (n != this.m_prefixMappings.size()) {
            this.m_prefixMappings.setSize(n);
        }
        n = this.m_previous;
        this.m_previous = this.m_parents.pop();
        if (-1 == n) {
            this.m_firstch.setElementAt(-1, this.m_previous);
        } else {
            this.m_nextsib.setElementAt(-1, n);
        }
        this.popShouldStripWhitespace();
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
        int n;
        String string2 = string;
        if (string == null) {
            string2 = "";
        }
        int n2 = this.m_contextIndexes.peek() - 1;
        while ((n = this.m_prefixMappings.indexOf(string2, n2 + 1)) >= 0) {
            n2 = n;
            if ((n & 1) == 1) continue;
        }
        if (n > -1) {
            this.m_prefixMappings.setElementAt("%@$#^@#", n);
            this.m_prefixMappings.setElementAt("%@$#^@#", n + 1);
        }
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    @Override
    public int getAttributeNode(int n, String string, String string2) {
        n = this.getFirstAttribute(n);
        while (-1 != n) {
            String string3 = this.getNamespaceURI(n);
            String string4 = this.getLocalName(n);
            boolean bl = string == string3 || string != null && string.equals(string3);
            if (bl && string2.equals(string4)) {
                return n;
            }
            n = this.getNextAttribute(n);
        }
        return -1;
    }

    @Override
    public ContentHandler getContentHandler() {
        IncrementalSAXSource incrementalSAXSource = this.m_incrementalSAXSource;
        if (incrementalSAXSource instanceof IncrementalSAXSource_Filter) {
            return (ContentHandler)((Object)incrementalSAXSource);
        }
        return this;
    }

    @Override
    public DTDHandler getDTDHandler() {
        return this;
    }

    @Override
    public DeclHandler getDeclHandler() {
        return this;
    }

    @Override
    public String getDocumentTypeDeclarationPublicIdentifier() {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
        return null;
    }

    @Override
    public String getDocumentTypeDeclarationSystemIdentifier() {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
        return null;
    }

    @Override
    public int getElementById(String string) {
        Integer n;
        boolean bl = true;
        do {
            if ((n = (Integer)this.m_idAttributes.get(string)) != null) {
                return this.makeNodeHandle(n);
            }
            if (!bl || this.m_endDocumentOccured) break;
            bl = this.nextNode();
        } while (n == null);
        return -1;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return this;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return this;
    }

    public String getFixedNames(int n) {
        return m_fixednames[n];
    }

    public int getIdForNamespace(String string) {
        return this.m_valuesOrPrefixes.stringToIndex(string);
    }

    @Override
    public LexicalHandler getLexicalHandler() {
        IncrementalSAXSource incrementalSAXSource = this.m_incrementalSAXSource;
        if (incrementalSAXSource instanceof IncrementalSAXSource_Filter) {
            return (LexicalHandler)((Object)incrementalSAXSource);
        }
        return this;
    }

    @Override
    public String getLocalName(int n) {
        return this.m_expandedNameTable.getLocalName(this._exptype(this.makeNodeIdentity(n)));
    }

    @Override
    public String getNamespaceURI(int n) {
        return this.m_expandedNameTable.getNamespace(this._exptype(this.makeNodeIdentity(n)));
    }

    public String getNamespaceURI(String string) {
        int n;
        String string2 = "";
        int n2 = n = this.m_contextIndexes.peek() - 1;
        String string3 = string;
        if (string == null) {
            string3 = "";
            n2 = n;
        }
        while ((n = this.m_prefixMappings.indexOf(string3, n2 + 1)) >= 0) {
            n2 = n;
            if ((n & 1) == 1) continue;
        }
        string = string2;
        if (n > -1) {
            string = (String)this.m_prefixMappings.elementAt(n + 1);
        }
        return string;
    }

    @Override
    protected int getNextNodeIdentity(int n) {
        while (++n >= this.m_size) {
            if (this.m_incrementalSAXSource == null) {
                return -1;
            }
            this.nextNode();
        }
        return n;
    }

    @Override
    public String getNodeName(int n) {
        int n2 = this.getExpandedTypeID(n);
        if (this.m_expandedNameTable.getNamespaceID(n2) == 0) {
            if ((n = (int)this.getNodeType(n)) == 13) {
                if (this.m_expandedNameTable.getLocalName(n2) == null) {
                    return "xmlns";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("xmlns:");
                stringBuilder.append(this.m_expandedNameTable.getLocalName(n2));
                return stringBuilder.toString();
            }
            if (this.m_expandedNameTable.getLocalNameID(n2) == 0) {
                return m_fixednames[n];
            }
            return this.m_expandedNameTable.getLocalName(n2);
        }
        n = n2 = this.m_dataOrQName.elementAt(this.makeNodeIdentity(n));
        if (n2 < 0) {
            n = -n2;
            n = this.m_data.elementAt(n);
        }
        return this.m_valuesOrPrefixes.indexToString(n);
    }

    @Override
    public String getNodeNameX(int n) {
        int n2 = this.getExpandedTypeID(n);
        if (this.m_expandedNameTable.getNamespaceID(n2) == 0) {
            String string = this.m_expandedNameTable.getLocalName(n2);
            if (string == null) {
                return "";
            }
            return string;
        }
        n = n2 = this.m_dataOrQName.elementAt(this.makeNodeIdentity(n));
        if (n2 < 0) {
            n = -n2;
            n = this.m_data.elementAt(n);
        }
        return this.m_valuesOrPrefixes.indexToString(n);
    }

    @Override
    public String getNodeValue(int n) {
        int n2 = this.makeNodeIdentity(n);
        n = this._type(n2);
        if (this.isTextType(n)) {
            n2 = this._dataOrQName(n2);
            n = this.m_data.elementAt(n2);
            n2 = this.m_data.elementAt(n2 + 1);
            return this.m_chars.getString(n, n2);
        }
        if (1 != n && 11 != n && 9 != n) {
            n = n2 = this._dataOrQName(n2);
            if (n2 < 0) {
                n = -n2;
                n = this.m_data.elementAt(n + 1);
            }
            return this.m_valuesOrPrefixes.indexToString(n);
        }
        return null;
    }

    @Override
    public int getNumberOfNodes() {
        return this.m_size;
    }

    @Override
    public String getPrefix(int n) {
        short s = this._type(n = this.makeNodeIdentity(n));
        if (1 == s) {
            if ((n = this._dataOrQName(n)) == 0) {
                return "";
            }
            return this.getPrefix(this.m_valuesOrPrefixes.indexToString(n), null);
        }
        if (2 == s && (n = this._dataOrQName(n)) < 0) {
            n = this.m_data.elementAt(-n);
            return this.getPrefix(this.m_valuesOrPrefixes.indexToString(n), null);
        }
        return "";
    }

    public String getPrefix(String string, String string2) {
        int n = -1;
        if (string2 != null && string2.length() > 0) {
            int n2;
            do {
                n = n2 = this.m_prefixMappings.indexOf(string2, n + 1);
            } while ((n2 & 1) == 0);
            if (n2 >= 0) {
                string = (String)this.m_prefixMappings.elementAt(n2 - 1);
            } else if (string != null) {
                n = string.indexOf(58);
                string = string.equals("xmlns") ? "" : (string.startsWith("xmlns:") ? string.substring(n + 1) : (n > 0 ? string.substring(0, n) : null));
            } else {
                string = null;
            }
        } else {
            string = string != null ? ((n = string.indexOf(58)) > 0 ? (string.startsWith("xmlns:") ? string.substring(n + 1) : string.substring(0, n)) : (string.equals("xmlns") ? "" : null)) : null;
        }
        return string;
    }

    @Override
    public SourceLocator getSourceLocatorFor(int n) {
        if (this.m_useSourceLocationProperty) {
            n = this.makeNodeIdentity(n);
            return new NodeLocator(null, this.m_sourceSystemId.elementAt(n), this.m_sourceLine.elementAt(n), this.m_sourceColumn.elementAt(n));
        }
        Object object = this.m_locator;
        if (object != null) {
            return new NodeLocator(null, object.getSystemId(), -1, -1);
        }
        object = this.m_systemId;
        if (object != null) {
            return new NodeLocator(null, (String)object, -1, -1);
        }
        return null;
    }

    @Override
    public XMLString getStringValue(int n) {
        int n2 = this.makeNodeIdentity(n);
        if (this.isTextType(n = n2 == -1 ? -1 : (int)this._type(n2))) {
            int n3 = this._dataOrQName(n2);
            n = this.m_data.elementAt(n3);
            n3 = this.m_data.elementAt(n3 + 1);
            return this.m_xstrf.newstr(this.m_chars, n, n3);
        }
        int n4 = this._firstch(n2);
        if (-1 != n4) {
            int n5;
            int n6;
            int n7;
            n = -1;
            int n8 = 0;
            do {
                n5 = n;
                n7 = n8;
                if (this.isTextType(this._type(n4))) {
                    n7 = this._dataOrQName(n4);
                    n5 = n;
                    if (-1 == n) {
                        n5 = this.m_data.elementAt(n7);
                    }
                    n7 = n8 + this.m_data.elementAt(n7 + 1);
                }
                if (-1 == (n6 = this.getNextNodeIdentity(n4))) break;
                n4 = n6;
                n = n5;
                n8 = n7;
            } while (this._parent(n6) >= n2);
            if (n7 > 0) {
                return this.m_xstrf.newstr(this.m_chars, n5, n7);
            }
        } else if (n != 1) {
            int n9;
            n = n9 = this._dataOrQName(n2);
            if (n9 < 0) {
                n = -n9;
                n = this.m_data.elementAt(n + 1);
            }
            return this.m_xstrf.newstr(this.m_valuesOrPrefixes.indexToString(n));
        }
        return this.m_xstrf.emptystr();
    }

    @Override
    public String getUnparsedEntityURI(String string) {
        String string2 = "";
        Object object = this.m_entities;
        if (object == null) {
            return "";
        }
        int n = ((Vector)object).size();
        int n2 = 0;
        do {
            object = string2;
            if (n2 >= n) break;
            object = (String)this.m_entities.elementAt(n2 + 3);
            if (object != null && ((String)object).equals(string)) {
                object = string2;
                if ((String)this.m_entities.elementAt(n2 + 2) == null) break;
                string = (String)this.m_entities.elementAt(n2 + 1);
                object = string;
                if (string != null) break;
                object = (String)this.m_entities.elementAt(n2 + 0);
                break;
            }
            n2 += 4;
        } while (true);
        return object;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        this.characters(arrc, n, n2);
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
    }

    @Override
    public boolean isAttributeSpecified(int n) {
        return true;
    }

    public boolean isWhitespace(int n) {
        int n2 = this.makeNodeIdentity(n);
        if (this.isTextType(n = n2 == -1 ? -1 : (int)this._type(n2))) {
            n2 = this._dataOrQName(n2);
            n = this.m_data.elementAt(n2);
            n2 = this.m_data.elementAt(n2 + 1);
            return this.m_chars.isWhitespace(n, n2);
        }
        return false;
    }

    @Override
    public void migrateTo(DTMManager dTMManager) {
        super.migrateTo(dTMManager);
        int n = this.m_dtmIdent.size();
        int n2 = this.m_mgrDefault.getFirstFreeDTMID();
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            this.m_dtmIdent.setElementAt(n2 << 16, i);
            this.m_mgrDefault.addDTM(this, n2, n3);
            ++n2;
            n3 += 65536;
        }
    }

    @Override
    public boolean needsTwoThreads() {
        boolean bl = this.m_incrementalSAXSource != null;
        return bl;
    }

    @Override
    protected boolean nextNode() {
        Object object = this.m_incrementalSAXSource;
        if (object == null) {
            return false;
        }
        if (this.m_endDocumentOccured) {
            this.clearCoRoutine();
            return false;
        }
        if (!((object = object.deliverMoreNodes(true)) instanceof Boolean)) {
            if (!(object instanceof RuntimeException)) {
                if (!(object instanceof Exception)) {
                    this.clearCoRoutine();
                    return false;
                }
                throw new WrappedRuntimeException((Exception)object);
            }
            throw (RuntimeException)object;
        }
        if (object != Boolean.TRUE) {
            this.clearCoRoutine();
        }
        return true;
    }

    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.charactersFlush();
        int n = this.m_expandedNameTable.getExpandedTypeID(null, string, 7);
        int n2 = this.m_valuesOrPrefixes.stringToIndex(string2);
        this.m_previous = this.addNode(7, n, this.m_parents.peek(), this.m_previous, n2, false);
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException {
        return null;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.m_locator = locator;
        this.m_systemId = locator.getSystemId();
    }

    public void setIDAttribute(String string, int n) {
        this.m_idAttributes.put(string, new Integer(n));
    }

    public void setIncrementalSAXSource(IncrementalSAXSource incrementalSAXSource) {
        this.m_incrementalSAXSource = incrementalSAXSource;
        incrementalSAXSource.setContentHandler(this);
        incrementalSAXSource.setLexicalHandler(this);
        incrementalSAXSource.setDTDHandler(this);
    }

    @Override
    public void setProperty(String string, Object object) {
    }

    protected void setSourceLocation() {
        this.m_sourceSystemId.addElement(this.m_locator.getSystemId());
        this.m_sourceLine.addElement(this.m_locator.getLineNumber());
        this.m_sourceColumn.addElement(this.m_locator.getColumnNumber());
        if (this.m_sourceSystemId.size() == this.m_size) {
            return;
        }
        CharSequence charSequence = new StringBuilder();
        charSequence.append("CODING ERROR in Source Location: ");
        charSequence.append(this.m_size);
        charSequence.append(" != ");
        charSequence.append(this.m_sourceSystemId.size());
        charSequence = charSequence.toString();
        System.err.println((String)charSequence);
        throw new RuntimeException((String)charSequence);
    }

    public void setUseSourceLocation(boolean bl) {
        this.m_useSourceLocationProperty = bl;
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
        this.m_textType = 4;
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        this.m_insideDTD = true;
    }

    @Override
    public void startDocument() throws SAXException {
        int n = this.addNode(9, this.m_expandedNameTable.getExpandedTypeID(9), -1, -1, 0, true);
        this.m_parents.push(n);
        this.m_previous = -1;
        this.m_contextIndexes.push(this.m_prefixMappings.size());
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        this.charactersFlush();
        int n = this.m_expandedNameTable.getExpandedTypeID(string, string2, 1);
        int n2 = this.getPrefix(string3, string) != null ? this.m_valuesOrPrefixes.stringToIndex(string3) : 0;
        int n3 = this.addNode(1, n, this.m_parents.peek(), this.m_previous, n2, true);
        if (this.m_indexing) {
            this.indexNode(n, n3);
        }
        this.m_parents.push(n3);
        int n4 = this.m_contextIndexes.peek();
        int n5 = this.m_prefixMappings.size();
        n = -1;
        if (!this.m_pastFirstElement) {
            n = this.addNode(13, this.m_expandedNameTable.getExpandedTypeID(null, "xml", 13), n3, -1, this.m_valuesOrPrefixes.stringToIndex("http://www.w3.org/XML/1998/namespace"), false);
            this.m_pastFirstElement = true;
        }
        boolean bl = true;
        while (n4 < n5) {
            string2 = (String)this.m_prefixMappings.elementAt(n4);
            if (string2 != null) {
                string = (String)this.m_prefixMappings.elementAt(n4 + 1);
                n = this.addNode(13, this.m_expandedNameTable.getExpandedTypeID(null, string2, 13), n3, n, this.m_valuesOrPrefixes.stringToIndex(string), false);
            }
            n4 += 2;
        }
        int n6 = attributes.getLength();
        int n7 = n;
        n = n6;
        for (n4 = 0; n4 < n; ++n4) {
            block14 : {
                String string4;
                String string5;
                block15 : {
                    block13 : {
                        string4 = attributes.getURI(n4);
                        string5 = attributes.getQName(n4);
                        string = attributes.getValue(n4);
                        string3 = this.getPrefix(string5, string4);
                        string2 = attributes.getLocalName(n4);
                        if (string5 == null || !string5.equals("xmlns") && !string5.startsWith("xmlns:")) break block13;
                        if (this.declAlreadyDeclared(string3)) break block14;
                        n6 = 13;
                        break block15;
                    }
                    if (attributes.getType(n4).equalsIgnoreCase("ID")) {
                        this.setIDAttribute(string, n3);
                    }
                    n6 = 2;
                }
                if (string == null) {
                    string = "";
                }
                int n8 = this.m_valuesOrPrefixes.stringToIndex(string);
                if (string3 != null) {
                    n2 = this.m_valuesOrPrefixes.stringToIndex(string5);
                    int n9 = this.m_data.size();
                    this.m_data.addElement(n2);
                    this.m_data.addElement(n8);
                    n8 = -n9;
                }
                n7 = this.addNode(n6, this.m_expandedNameTable.getExpandedTypeID(string4, string2, n6), n3, n7, n8, false);
            }
            bl = true;
        }
        if (-1 != n7) {
            this.m_nextsib.setElementAt(-1, n7);
        }
        if (this.m_wsfilter != null) {
            n2 = this.m_wsfilter.getShouldStripSpace(this.makeNodeHandle(n3), this);
            if (3 == n2) {
                bl = this.getShouldStripWhitespace();
            } else if (2 != n2) {
                bl = false;
            }
            this.pushShouldStripWhitespace(bl);
        }
        this.m_previous = -1;
        this.m_contextIndexes.push(this.m_prefixMappings.size());
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        String string3 = string;
        if (string == null) {
            string3 = "";
        }
        this.m_prefixMappings.addElement(string3);
        this.m_prefixMappings.addElement(string2);
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        if (this.m_entities == null) {
            this.m_entities = new Vector();
        }
        try {
            string3 = SystemIDResolver.getAbsoluteURI(string3, this.getDocumentBaseURI());
            this.m_entities.addElement(string2);
            this.m_entities.addElement(string3);
            this.m_entities.addElement(string4);
            this.m_entities.addElement(string);
            return;
        }
        catch (Exception exception) {
            throw new SAXException(exception);
        }
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        System.err.println(sAXParseException.getMessage());
    }
}

