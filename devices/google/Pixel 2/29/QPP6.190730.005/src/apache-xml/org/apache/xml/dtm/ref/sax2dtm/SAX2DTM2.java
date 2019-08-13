/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref.sax2dtm;

import java.util.Vector;
import javax.xml.transform.Source;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMException;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
import org.apache.xml.dtm.ref.DTMStringPool;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.dtm.ref.ExtendedType;
import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringDefault;
import org.apache.xml.utils.XMLStringFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SAX2DTM2
extends SAX2DTM {
    private static final String EMPTY_STR = "";
    private static final XMLString EMPTY_XML_STR = new XMLStringDefault("");
    protected static final int TEXT_LENGTH_BITS = 10;
    protected static final int TEXT_LENGTH_MAX = 1023;
    protected static final int TEXT_OFFSET_BITS = 21;
    protected static final int TEXT_OFFSET_MAX = 2097151;
    protected int m_MASK;
    protected int m_SHIFT;
    protected int m_blocksize;
    protected boolean m_buildIdIndex = true;
    private int[][] m_exptype_map;
    private int[] m_exptype_map0;
    protected ExtendedType[] m_extendedTypes;
    private int[][] m_firstch_map;
    private int[] m_firstch_map0;
    private int m_maxNodeIndex;
    private int[][] m_nextsib_map;
    private int[] m_nextsib_map0;
    private int[][] m_parent_map;
    private int[] m_parent_map0;
    private int m_valueIndex = 0;
    protected Vector m_values;

    public SAX2DTM2(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        this(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl, 512, true, true, false);
    }

    public SAX2DTM2(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl, int n2, boolean bl2, boolean bl3, boolean bl4) {
        super(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl, n2, bl2, bl4);
        n = 0;
        do {
            int n3;
            n2 = n3 = n2 >>> 1;
            if (n3 == 0) break;
            ++n;
        } while (true);
        this.m_blocksize = 1 << n;
        this.m_SHIFT = n;
        this.m_MASK = this.m_blocksize - 1;
        this.m_buildIdIndex = bl3;
        this.m_values = new Vector(32, 512);
        this.m_maxNodeIndex = 65536;
        this.m_exptype_map0 = this.m_exptype.getMap0();
        this.m_nextsib_map0 = this.m_nextsib.getMap0();
        this.m_firstch_map0 = this.m_firstch.getMap0();
        this.m_parent_map0 = this.m_parent.getMap0();
    }

    @Override
    public final int _exptype(int n) {
        return this.m_exptype.elementAt(n);
    }

    public final int _exptype2(int n) {
        if (n < this.m_blocksize) {
            return this.m_exptype_map0[n];
        }
        return this.m_exptype_map[n >>> this.m_SHIFT][this.m_MASK & n];
    }

    public final int _exptype2Type(int n) {
        if (-1 != n) {
            return this.m_extendedTypes[n].getNodeType();
        }
        return -1;
    }

    public final int _firstch2(int n) {
        if (n < this.m_blocksize) {
            return this.m_firstch_map0[n];
        }
        return this.m_firstch_map[n >>> this.m_SHIFT][this.m_MASK & n];
    }

    public final int _nextsib2(int n) {
        if (n < this.m_blocksize) {
            return this.m_nextsib_map0[n];
        }
        return this.m_nextsib_map[n >>> this.m_SHIFT][this.m_MASK & n];
    }

    public final int _parent2(int n) {
        if (n < this.m_blocksize) {
            return this.m_parent_map0[n];
        }
        return this.m_parent_map[n >>> this.m_SHIFT][this.m_MASK & n];
    }

    public final int _type2(int n) {
        if (-1 != (n = n < this.m_blocksize ? this.m_exptype_map0[n] : this.m_exptype_map[n >>> this.m_SHIFT][this.m_MASK & n])) {
            return this.m_extendedTypes[n].getNodeType();
        }
        return -1;
    }

    @Override
    protected final int addNode(int n, int n2, int n3, int n4, int n5, boolean bl) {
        int n6;
        block3 : {
            block4 : {
                block5 : {
                    n6 = this.m_size;
                    this.m_size = n6 + 1;
                    if (n6 == this.m_maxNodeIndex) {
                        this.addNewDTMID(n6);
                        this.m_maxNodeIndex += 65536;
                    }
                    this.m_firstch.addElement(-1);
                    this.m_nextsib.addElement(-1);
                    this.m_parent.addElement(n3);
                    this.m_exptype.addElement(n2);
                    this.m_dataOrQName.addElement(n5);
                    if (this.m_prevsib != null) {
                        this.m_prevsib.addElement(n4);
                    }
                    if (this.m_locator != null && this.m_useSourceLocationProperty) {
                        this.setSourceLocation();
                    }
                    if (n == 2) break block3;
                    if (n == 13) break block4;
                    if (-1 == n4) break block5;
                    this.m_nextsib.setElementAt(n6, n4);
                    break block3;
                }
                if (-1 == n3) break block3;
                this.m_firstch.setElementAt(n6, n3);
                break block3;
            }
            this.declareNamespaceInContext(n3, n6);
        }
        return n6;
    }

    @Override
    protected final void charactersFlush() {
        if (this.m_textPendingStart >= 0) {
            int n = this.m_chars.size() - this.m_textPendingStart;
            boolean bl = false;
            if (this.getShouldStripWhitespace()) {
                bl = this.m_chars.isWhitespace(this.m_textPendingStart, n);
            }
            if (bl) {
                this.m_chars.setLength(this.m_textPendingStart);
            } else if (n > 0) {
                if (n <= 1023 && this.m_textPendingStart <= 2097151) {
                    this.m_previous = this.addNode(this.m_coalescedTextType, 3, this.m_parents.peek(), this.m_previous, n + (this.m_textPendingStart << 10), false);
                } else {
                    int n2 = this.m_data.size();
                    this.m_previous = this.addNode(this.m_coalescedTextType, 3, this.m_parents.peek(), this.m_previous, -n2, false);
                    this.m_data.addElement(this.m_textPendingStart);
                    this.m_data.addElement(n);
                }
            }
            this.m_textPendingStart = -1;
            this.m_coalescedTextType = 3;
            this.m_textType = 3;
        }
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_insideDTD) {
            return;
        }
        this.charactersFlush();
        this.m_values.addElement(new String(arrc, n, n2));
        n = this.m_valueIndex;
        this.m_valueIndex = n + 1;
        this.m_previous = this.addNode(8, 8, this.m_parents.peek(), this.m_previous, n, false);
    }

    protected final void copyAttribute(int n, int n2, SerializationHandler serializationHandler) throws SAXException {
        Object object = this.m_extendedTypes[n2];
        String string = ((ExtendedType)object).getNamespace();
        String string2 = ((ExtendedType)object).getLocalName();
        String string3 = null;
        object = null;
        n = n2 = this._dataOrQName(n);
        String string4 = string3;
        if (n2 <= 0) {
            n = this.m_data.elementAt(-n2);
            n2 = this.m_data.elementAt(-n2 + 1);
            String string5 = this.m_valuesOrPrefixes.indexToString(n);
            int n3 = string5.indexOf(58);
            string4 = string3;
            object = string5;
            n = n2;
            if (n3 > 0) {
                string4 = string5.substring(0, n3);
                n = n2;
                object = string5;
            }
        }
        if (string.length() != 0) {
            serializationHandler.namespaceAfterStartElement(string4, string);
        }
        if (string4 == null) {
            object = string2;
        }
        serializationHandler.addAttribute((String)object, (String)this.m_values.elementAt(n));
    }

    protected final void copyAttributes(int n, SerializationHandler serializationHandler) throws SAXException {
        n = this.getFirstAttributeIdentity(n);
        while (n != -1) {
            this.copyAttribute(n, this._exptype2(n), serializationHandler);
            n = this.getNextAttributeIdentity(n);
        }
    }

    protected final String copyElement(int n, int n2, SerializationHandler serializationHandler) throws SAXException {
        Object object = this.m_extendedTypes[n2];
        String string = ((ExtendedType)object).getNamespace();
        object = ((ExtendedType)object).getLocalName();
        if (string.length() == 0) {
            serializationHandler.startElement((String)object);
            return object;
        }
        n2 = this.m_dataOrQName.elementAt(n);
        if (n2 == 0) {
            serializationHandler.startElement((String)object);
            serializationHandler.namespaceAfterStartElement(EMPTY_STR, string);
            return object;
        }
        n = n2;
        if (n2 < 0) {
            n = -n2;
            n = this.m_data.elementAt(n);
        }
        String string2 = this.m_valuesOrPrefixes.indexToString(n);
        serializationHandler.startElement(string2);
        n = string2.indexOf(58);
        object = n > 0 ? string2.substring(0, n) : null;
        serializationHandler.namespaceAfterStartElement((String)object, string);
        return string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final void copyNS(int n, SerializationHandler serializationHandler, boolean bl) throws SAXException {
        if (this.m_namespaceDeclSetElements != null && this.m_namespaceDeclSetElements.size() == 1 && this.m_namespaceDeclSets != null && ((SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(0)).size() == 1) {
            return;
        }
        SuballocatedIntVector suballocatedIntVector = null;
        if (bl) {
            suballocatedIntVector = this.findNamespaceContext(n);
            if (suballocatedIntVector == null || suballocatedIntVector.size() < 1) return;
            n = this.makeNodeIdentity(suballocatedIntVector.elementAt(0));
        } else {
            n = this.getNextNamespaceNode2(n);
        }
        int n2 = 1;
        while (n != -1) {
            int n3;
            int n4 = this._exptype2(n);
            String string = this.m_extendedTypes[n4].getLocalName();
            n4 = n3 = this.m_dataOrQName.elementAt(n);
            if (n3 < 0) {
                n4 = -n3;
                n4 = this.m_data.elementAt(n4 + 1);
            }
            serializationHandler.namespaceAfterStartElement(string, (String)this.m_values.elementAt(n4));
            if (bl) {
                if (n2 >= suballocatedIntVector.size()) return;
                n = this.makeNodeIdentity(suballocatedIntVector.elementAt(n2));
                ++n2;
                continue;
            }
            n = this.getNextNamespaceNode2(n);
        }
    }

    protected final void copyTextNode(int n, SerializationHandler serializationHandler) throws SAXException {
        if (n != -1) {
            if ((n = this.m_dataOrQName.elementAt(n)) >= 0) {
                this.m_chars.sendSAXcharacters(serializationHandler, n >>> 10, n & 1023);
            } else {
                this.m_chars.sendSAXcharacters(serializationHandler, this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
            }
        }
    }

    @Override
    public final void dispatchCharactersEvents(int n, ContentHandler contentHandler, boolean bl) throws SAXException {
        block24 : {
            int n2;
            int n3;
            int n4;
            int n5;
            block23 : {
                n5 = this.makeNodeIdentity(n);
                if (n5 == -1) {
                    return;
                }
                n = this._type2(n5);
                if (n == 1 || n == 9) break block23;
                if (3 != n && 4 != n) {
                    int n6;
                    n = n6 = this.m_dataOrQName.elementAt(n5);
                    if (n6 < 0) {
                        n = -n6;
                        n = this.m_data.elementAt(n + 1);
                    }
                    String string = (String)this.m_values.elementAt(n);
                    if (bl) {
                        FastStringBuffer.sendNormalizedSAXcharacters(string.toCharArray(), 0, string.length(), contentHandler);
                    } else {
                        contentHandler.characters(string.toCharArray(), 0, string.length());
                    }
                } else {
                    n = this.m_dataOrQName.elementAt(n5);
                    if (n >= 0) {
                        if (bl) {
                            this.m_chars.sendNormalizedSAXcharacters(contentHandler, n >>> 10, n & 1023);
                        } else {
                            this.m_chars.sendSAXcharacters(contentHandler, n >>> 10, n & 1023);
                        }
                    } else if (bl) {
                        this.m_chars.sendNormalizedSAXcharacters(contentHandler, this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
                    } else {
                        this.m_chars.sendSAXcharacters(contentHandler, this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
                    }
                }
                break block24;
            }
            int n7 = this._firstch2(n5);
            if (-1 == n7) break block24;
            n = -1;
            int n8 = 0;
            do {
                block26 : {
                    block25 : {
                        if ((n2 = this._exptype2(n7)) == 3) break block25;
                        n3 = n;
                        n4 = n8;
                        if (n2 != 4) break block26;
                    }
                    if ((n4 = this.m_dataOrQName.elementAt(n7)) >= 0) {
                        n3 = n;
                        if (-1 == n) {
                            n3 = n4 >>> 10;
                        }
                        n4 = n8 + (n4 & 1023);
                    } else {
                        n3 = n;
                        if (-1 == n) {
                            n3 = this.m_data.elementAt(-n4);
                        }
                        n4 = n8 + this.m_data.elementAt(-n4 + 1);
                    }
                }
                n7 = n2 = n7 + 1;
                n = n3;
                n8 = n4;
            } while (this._parent2(n2) >= n5);
            if (n4 > 0) {
                if (bl) {
                    this.m_chars.sendNormalizedSAXcharacters(contentHandler, n3, n4);
                } else {
                    this.m_chars.sendSAXcharacters(contentHandler, n3, n4);
                }
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        this.m_exptype.addElement(-1);
        this.m_parent.addElement(-1);
        this.m_nextsib.addElement(-1);
        this.m_firstch.addElement(-1);
        this.m_extendedTypes = this.m_expandedNameTable.getExtendedTypes();
        this.m_exptype_map = this.m_exptype.getMap();
        this.m_nextsib_map = this.m_nextsib.getMap();
        this.m_firstch_map = this.m_firstch.getMap();
        this.m_parent_map = this.m_parent.getMap();
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        this.charactersFlush();
        this.m_contextIndexes.quickPop(1);
        int n = this.m_contextIndexes.peek();
        if (n != this.m_prefixMappings.size()) {
            this.m_prefixMappings.setSize(n);
        }
        this.m_previous = this.m_parents.pop();
        this.popShouldStripWhitespace();
    }

    public final int getExpandedTypeID2(int n) {
        if ((n = this.makeNodeIdentity(n)) != -1) {
            if (n < this.m_blocksize) {
                return this.m_exptype_map0[n];
            }
            return this.m_exptype_map[n >>> this.m_SHIFT][this.m_MASK & n];
        }
        return -1;
    }

    @Override
    public final int getFirstAttribute(int n) {
        if ((n = this.makeNodeIdentity(n)) == -1) {
            return -1;
        }
        if (1 == this._type2(n)) {
            int n2;
            do {
                if ((n2 = this._type2(++n)) != 2) continue;
                return this.makeNodeHandle(n);
            } while (13 == n2);
        }
        return -1;
    }

    @Override
    protected int getFirstAttributeIdentity(int n) {
        if (n == -1) {
            return -1;
        }
        if (1 == this._type2(n)) {
            int n2;
            do {
                if ((n2 = this._type2(++n)) != 2) continue;
                return n;
            } while (13 == n2);
        }
        return -1;
    }

    @Override
    public int getIdForNamespace(String string) {
        int n = this.m_values.indexOf(string);
        if (n < 0) {
            this.m_values.addElement(string);
            n = this.m_valueIndex;
            this.m_valueIndex = n + 1;
            return n;
        }
        return n;
    }

    @Override
    public String getLocalName(int n) {
        int n2 = this._exptype(this.makeNodeIdentity(n));
        if (n2 == 7) {
            n = this._dataOrQName(this.makeNodeIdentity(n));
            n = this.m_data.elementAt(-n);
            return this.m_valuesOrPrefixes.indexToString(n);
        }
        return this.m_expandedNameTable.getLocalName(n2);
    }

    @Override
    protected int getNextAttributeIdentity(int n) {
        int n2;
        do {
            if ((n2 = this._type2(++n)) != 2) continue;
            return n;
        } while (n2 == 13);
        return -1;
    }

    protected final int getNextNamespaceNode2(int n) {
        int n2;
        while ((n2 = this._type2(++n)) == 2) {
        }
        if (n2 == 13) {
            return n;
        }
        return -1;
    }

    @Override
    public String getNodeName(int n) {
        int n2 = this._exptype2(n = this.makeNodeIdentity(n));
        Object object = this.m_extendedTypes[n2];
        if (((ExtendedType)object).getNamespace().length() == 0) {
            n2 = ((ExtendedType)object).getNodeType();
            String string = ((ExtendedType)object).getLocalName();
            if (n2 == 13) {
                if (string.length() == 0) {
                    return "xmlns";
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("xmlns:");
                ((StringBuilder)object).append(string);
                return ((StringBuilder)object).toString();
            }
            if (n2 == 7) {
                n = this._dataOrQName(n);
                n = this.m_data.elementAt(-n);
                return this.m_valuesOrPrefixes.indexToString(n);
            }
            if (string.length() == 0) {
                return this.getFixedNames(n2);
            }
            return string;
        }
        n2 = this.m_dataOrQName.elementAt(n);
        if (n2 == 0) {
            return ((ExtendedType)object).getLocalName();
        }
        n = n2;
        if (n2 < 0) {
            n = -n2;
            n = this.m_data.elementAt(n);
        }
        return this.m_valuesOrPrefixes.indexToString(n);
    }

    @Override
    public final String getNodeNameX(int n) {
        int n2 = this.makeNodeIdentity(n);
        n = this._exptype2(n2);
        if (n == 7) {
            n = this._dataOrQName(n2);
            n = this.m_data.elementAt(-n);
            return this.m_valuesOrPrefixes.indexToString(n);
        }
        ExtendedType extendedType = this.m_extendedTypes[n];
        if (extendedType.getNamespace().length() == 0) {
            return extendedType.getLocalName();
        }
        if ((n2 = this.m_dataOrQName.elementAt(n2)) == 0) {
            return extendedType.getLocalName();
        }
        n = n2;
        if (n2 < 0) {
            n = -n2;
            n = this.m_data.elementAt(n);
        }
        return this.m_valuesOrPrefixes.indexToString(n);
    }

    @Override
    public String getNodeValue(int n) {
        int n2 = this.makeNodeIdentity(n);
        n = this._type2(n2);
        if (n != 3 && n != 4) {
            if (1 != n && 11 != n && 9 != n) {
                n = n2 = this.m_dataOrQName.elementAt(n2);
                if (n2 < 0) {
                    n = -n2;
                    n = this.m_data.elementAt(n + 1);
                }
                return (String)this.m_values.elementAt(n);
            }
            return null;
        }
        n = this._dataOrQName(n2);
        if (n > 0) {
            return this.m_chars.getString(n >>> 10, n & 1023);
        }
        return this.m_chars.getString(this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
    }

    public String getStringValue() {
        int n = this._firstch2(0);
        if (n == -1) {
            return EMPTY_STR;
        }
        if (this._exptype2(n) == 3 && this._nextsib2(n) == -1) {
            if ((n = this.m_dataOrQName.elementAt(n)) >= 0) {
                return this.m_chars.getString(n >>> 10, n & 1023);
            }
            return this.m_chars.getString(this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
        }
        return this.getStringValueX(this.getDocument());
    }

    @Override
    public XMLString getStringValue(int n) {
        block18 : {
            int n2;
            int n3;
            int n4;
            int n5 = this.makeNodeIdentity(n);
            if (n5 == -1) {
                return EMPTY_XML_STR;
            }
            n = this._type2(n5);
            if (n != 1 && n != 9) {
                if (3 != n && 4 != n) {
                    int n6;
                    n = n6 = this.m_dataOrQName.elementAt(n5);
                    if (n6 < 0) {
                        n = -n6;
                        n = this.m_data.elementAt(n + 1);
                    }
                    if (this.m_xstrf != null) {
                        return this.m_xstrf.newstr((String)this.m_values.elementAt(n));
                    }
                    return new XMLStringDefault((String)this.m_values.elementAt(n));
                }
                n = this.m_dataOrQName.elementAt(n5);
                if (n >= 0) {
                    if (this.m_xstrf != null) {
                        return this.m_xstrf.newstr(this.m_chars, n >>> 10, n & 1023);
                    }
                    return new XMLStringDefault(this.m_chars.getString(n >>> 10, n & 1023));
                }
                if (this.m_xstrf != null) {
                    return this.m_xstrf.newstr(this.m_chars, this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
                }
                return new XMLStringDefault(this.m_chars.getString(this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1)));
            }
            int n7 = this._firstch2(n5);
            if (-1 == n7) break block18;
            n = -1;
            int n8 = 0;
            do {
                block20 : {
                    block19 : {
                        if ((n4 = this._exptype2(n7)) == 3) break block19;
                        n3 = n;
                        n2 = n8;
                        if (n4 != 4) break block20;
                    }
                    if ((n2 = this.m_dataOrQName.elementAt(n7)) >= 0) {
                        n3 = n;
                        if (-1 == n) {
                            n3 = n2 >>> 10;
                        }
                        n2 = n8 + (n2 & 1023);
                    } else {
                        n3 = n;
                        if (-1 == n) {
                            n3 = this.m_data.elementAt(-n2);
                        }
                        n2 = n8 + this.m_data.elementAt(-n2 + 1);
                    }
                }
                n7 = n4 = n7 + 1;
                n = n3;
                n8 = n2;
            } while (this._parent2(n4) >= n5);
            if (n2 > 0) {
                if (this.m_xstrf != null) {
                    return this.m_xstrf.newstr(this.m_chars, n3, n2);
                }
                return new XMLStringDefault(this.m_chars.getString(n3, n2));
            }
            return EMPTY_XML_STR;
        }
        return EMPTY_XML_STR;
    }

    public final String getStringValueX(int n) {
        block14 : {
            int n2;
            int n3;
            int n4;
            int n5 = this.makeNodeIdentity(n);
            if (n5 == -1) {
                return EMPTY_STR;
            }
            n = this._type2(n5);
            if (n != 1 && n != 9) {
                if (3 != n && 4 != n) {
                    int n6;
                    n = n6 = this.m_dataOrQName.elementAt(n5);
                    if (n6 < 0) {
                        n = -n6;
                        n = this.m_data.elementAt(n + 1);
                    }
                    return (String)this.m_values.elementAt(n);
                }
                n = this.m_dataOrQName.elementAt(n5);
                if (n >= 0) {
                    return this.m_chars.getString(n >>> 10, n & 1023);
                }
                return this.m_chars.getString(this.m_data.elementAt(-n), this.m_data.elementAt(-n + 1));
            }
            int n7 = this._firstch2(n5);
            if (-1 == n7) break block14;
            n = -1;
            int n8 = 0;
            do {
                block16 : {
                    block15 : {
                        if ((n4 = this._exptype2(n7)) == 3) break block15;
                        n3 = n;
                        n2 = n8;
                        if (n4 != 4) break block16;
                    }
                    if ((n2 = this.m_dataOrQName.elementAt(n7)) >= 0) {
                        n3 = n;
                        if (-1 == n) {
                            n3 = n2 >>> 10;
                        }
                        n2 = n8 + (n2 & 1023);
                    } else {
                        n3 = n;
                        if (-1 == n) {
                            n3 = this.m_data.elementAt(-n2);
                        }
                        n2 = n8 + this.m_data.elementAt(-n2 + 1);
                    }
                }
                n7 = n4 = n7 + 1;
                n = n3;
                n8 = n2;
            } while (this._parent2(n4) >= n5);
            if (n2 > 0) {
                return this.m_chars.getString(n3, n2);
            }
            return EMPTY_STR;
        }
        return EMPTY_STR;
    }

    @Override
    protected final int getTypedAttribute(int n, int n2) {
        block4 : {
            if ((n = this.makeNodeIdentity(n)) == -1) {
                return -1;
            }
            if (1 == this._type2(n)) {
                int n3;
                int n4;
                while ((n4 = this._exptype2(n3 = n + 1)) != -1) {
                    int n5 = this.m_extendedTypes[n4].getNodeType();
                    if (n5 == 2) {
                        n = n3;
                        if (n4 != n2) continue;
                        return this.makeNodeHandle(n3);
                    }
                    n = n3;
                    if (13 == n5) continue;
                    break block4;
                }
                return -1;
            }
        }
        return -1;
    }

    @Override
    public void processingInstruction(String object, String string) throws SAXException {
        this.charactersFlush();
        int n = this.m_data.size();
        this.m_previous = this.addNode(7, 7, this.m_parents.peek(), this.m_previous, -n, false);
        this.m_data.addElement(this.m_valuesOrPrefixes.stringToIndex((String)object));
        this.m_values.addElement(string);
        object = this.m_data;
        n = this.m_valueIndex;
        this.m_valueIndex = n + 1;
        ((SuballocatedIntVector)object).addElement(n);
    }

    @Override
    public void startDocument() throws SAXException {
        int n = this.addNode(9, 9, -1, -1, 0, true);
        this.m_parents.push(n);
        this.m_previous = -1;
        this.m_contextIndexes.push(this.m_prefixMappings.size());
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        int n;
        int n2;
        this.charactersFlush();
        int n3 = this.m_expandedNameTable.getExpandedTypeID(string, string2, 1);
        int n4 = string3.length();
        int n5 = string2.length();
        boolean bl = false;
        n4 = n4 != n5 ? this.m_valuesOrPrefixes.stringToIndex(string3) : 0;
        n5 = this.addNode(1, n3, this.m_parents.peek(), this.m_previous, n4, true);
        if (this.m_indexing) {
            this.indexNode(n3, n5);
        }
        this.m_parents.push(n5);
        n3 = this.m_contextIndexes.peek();
        int n6 = this.m_prefixMappings.size();
        if (!this.m_pastFirstElement) {
            n2 = this.m_expandedNameTable.getExpandedTypeID(null, "xml", 13);
            this.m_values.addElement("http://www.w3.org/XML/1998/namespace");
            n = this.m_valueIndex;
            this.m_valueIndex = n + 1;
            this.addNode(13, n2, n5, -1, n, false);
            this.m_pastFirstElement = true;
        }
        while (n3 < n6) {
            string2 = (String)this.m_prefixMappings.elementAt(n3);
            if (string2 != null) {
                string = (String)this.m_prefixMappings.elementAt(n3 + 1);
                n2 = this.m_expandedNameTable.getExpandedTypeID(null, string2, 13);
                this.m_values.addElement(string);
                n = this.m_valueIndex;
                this.m_valueIndex = n + 1;
                this.addNode(13, n2, n5, -1, n, false);
            }
            n3 += 2;
        }
        int n7 = attributes.getLength();
        for (n3 = 0; n3 < n7; ++n3) {
            string3 = attributes.getURI(n3);
            String string4 = attributes.getQName(n3);
            string = attributes.getValue(n3);
            string2 = attributes.getLocalName(n3);
            if (string4 != null && (string4.equals("xmlns") || string4.startsWith("xmlns:"))) {
                if (this.declAlreadyDeclared(this.getPrefix(string4, string3))) continue;
                n = 13;
            } else {
                if (this.m_buildIdIndex && attributes.getType(n3).equalsIgnoreCase("ID")) {
                    this.setIDAttribute(string, n5);
                }
                n = 2;
            }
            if (string == null) {
                string = EMPTY_STR;
            }
            this.m_values.addElement(string);
            n2 = this.m_valueIndex;
            this.m_valueIndex = n2 + 1;
            if (string2.length() != string4.length()) {
                n4 = this.m_valuesOrPrefixes.stringToIndex(string4);
                int n8 = this.m_data.size();
                this.m_data.addElement(n4);
                this.m_data.addElement(n2);
                n2 = -n8;
            }
            this.addNode(n, this.m_expandedNameTable.getExpandedTypeID(string3, string2, n), n5, -1, n2, false);
        }
        if (this.m_wsfilter != null) {
            n4 = this.m_wsfilter.getShouldStripSpace(this.makeNodeHandle(n5), this);
            if (3 == n4) {
                bl = this.getShouldStripWhitespace();
            } else if (2 == n4) {
                bl = true;
            }
            this.pushShouldStripWhitespace(bl);
        }
        this.m_previous = -1;
        this.m_contextIndexes.push(this.m_prefixMappings.size());
    }

    public class AncestorIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        private static final int m_blocksize = 32;
        int[] m_ancestors;
        int m_ancestorsPos;
        int m_markedPos;
        int m_realStartNode;
        int m_size;

        public AncestorIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
            this.m_ancestors = new int[32];
            this.m_size = 0;
        }

        @Override
        public DTMAxisIterator cloneIterator() {
            this._isRestartable = false;
            try {
                AncestorIterator ancestorIterator = (AncestorIterator)Object.super.clone();
                ancestorIterator._startNode = this._startNode;
                return ancestorIterator;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
            }
        }

        @Override
        public int getStartNode() {
            return this.m_realStartNode;
        }

        @Override
        public void gotoMark() {
            this.m_ancestorsPos = this.m_markedPos;
            int n = this.m_ancestorsPos;
            n = n >= 0 ? this.m_ancestors[n] : -1;
            this._currentNode = n;
        }

        @Override
        public final boolean isReverse() {
            return true;
        }

        @Override
        public int next() {
            int n;
            int n2 = this._currentNode;
            this.m_ancestorsPos = n = this.m_ancestorsPos - 1;
            n = n >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1;
            this._currentNode = n;
            return this.returnNode(n2);
        }

        @Override
        public DTMAxisIterator reset() {
            this.m_ancestorsPos = this.m_size - 1;
            int n = this.m_ancestorsPos;
            n = n >= 0 ? this.m_ancestors[n] : -1;
            this._currentNode = n;
            return this.resetPosition();
        }

        @Override
        public void setMark() {
            this.m_markedPos = this.m_ancestorsPos;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            this.m_realStartNode = n2;
            if (this._isRestartable) {
                int n3 = SAX2DTM2.this.makeNodeIdentity(n2);
                this.m_size = 0;
                int n4 = -1;
                if (n3 == -1) {
                    this._currentNode = -1;
                    this.m_ancestorsPos = 0;
                    return this;
                }
                n = n3;
                if (!this._includeSelf) {
                    n = SAX2DTM2.this._parent2(n3);
                    n2 = SAX2DTM2.this.makeNodeHandle(n);
                }
                this._startNode = n2;
                while (n != -1) {
                    int[] arrn;
                    n3 = this.m_size;
                    int[] arrn2 = this.m_ancestors;
                    if (n3 >= arrn2.length) {
                        arrn = new int[n3 * 2];
                        System.arraycopy(arrn2, 0, arrn, 0, arrn2.length);
                        this.m_ancestors = arrn;
                    }
                    arrn = this.m_ancestors;
                    n3 = this.m_size;
                    this.m_size = n3 + 1;
                    arrn[n3] = n2;
                    n = SAX2DTM2.this._parent2(n);
                    n2 = SAX2DTM2.this.makeNodeHandle(n);
                }
                this.m_ancestorsPos = this.m_size - 1;
                n = this.m_ancestorsPos;
                n = n >= 0 ? this.m_ancestors[n] : n4;
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class AttributeIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        public AttributeIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
        }

        @Override
        public int next() {
            int n = this._currentNode;
            if (n != -1) {
                this._currentNode = SAX2DTM2.this.getNextAttributeIdentity(n);
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                SAX2DTM2 sAX2DTM2 = SAX2DTM2.this;
                this._currentNode = sAX2DTM2.getFirstAttributeIdentity(sAX2DTM2.makeNodeIdentity(n2));
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class ChildrenIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        public ChildrenIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
        }

        @Override
        public int next() {
            if (this._currentNode != -1) {
                int n = this._currentNode;
                this._currentNode = SAX2DTM2.this._nextsib2(n);
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                n = -1;
                if (n2 != -1) {
                    SAX2DTM2 sAX2DTM2 = SAX2DTM2.this;
                    n = sAX2DTM2._firstch2(sAX2DTM2.makeNodeIdentity(n2));
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class DescendantIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        public DescendantIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
        }

        protected final boolean isDescendant(int n) {
            boolean bl = SAX2DTM2.this._parent2(n) >= this._startNode || this._startNode == n;
            return bl;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public int next() {
            block5 : {
                var1_1 = this._startNode;
                if (var1_1 == -1) {
                    return -1;
                }
                if (this._includeSelf && this._currentNode + 1 == var1_1) {
                    var2_2 = SAX2DTM2.this;
                    this._currentNode = var3_3 = this._currentNode + 1;
                    return this.returnNode(var2_2.makeNodeHandle(var3_3));
                }
                var3_4 = var4_5 = this._currentNode;
                if (var1_1 != 0) break block5;
                do lbl-1000: // 4 sources:
                {
                    if (-1 == (var1_1 = SAX2DTM2.this._exptype2(var3_4 = var4_5 + 1))) {
                        this._currentNode = -1;
                        return -1;
                    }
                    var4_5 = var3_4;
                    if (var1_1 == 3) ** GOTO lbl-1000
                    var1_1 = SAX2DTM2.this.m_extendedTypes[var1_1].getNodeType();
                    var4_5 = var3_4;
                    if (var1_1 == 2) ** GOTO lbl-1000
                    var4_5 = var3_4;
                } while (var1_1 == 13);
                ** GOTO lbl31
            }
            while (-1 != (var1_1 = SAX2DTM2.this._type2(var4_5 = var3_4 + 1)) && this.isDescendant(var4_5)) {
                var3_4 = var4_5;
                if (2 == var1_1) continue;
                var3_4 = var4_5;
                if (3 == var1_1) continue;
                var3_4 = var4_5;
                if (13 == var1_1) continue;
                var3_4 = var4_5;
lbl31: // 2 sources:
                this._currentNode = var3_4;
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(var3_4));
            }
            this._currentNode = -1;
            return -1;
        }

        @Override
        public DTMAxisIterator reset() {
            boolean bl = this._isRestartable;
            this._isRestartable = true;
            this.setStartNode(SAX2DTM2.this.makeNodeHandle(this._startNode));
            this._isRestartable = bl;
            return this;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2 = SAX2DTM2.this.makeNodeIdentity(n2);
                n = n2;
                if (this._includeSelf) {
                    n = n2 - 1;
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class FollowingIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        public FollowingIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
        }

        @Override
        public int next() {
            int n;
            int n2 = this._currentNode;
            int n3 = SAX2DTM2.this.makeNodeIdentity(n2);
            do {
                int n4;
                if (-1 == (n4 = SAX2DTM2.this._type2(n = n3 + 1))) {
                    this._currentNode = -1;
                    return this.returnNode(n2);
                }
                n3 = n;
                if (2 == n4) continue;
                if (13 != n4) break;
                n3 = n;
            } while (true);
            this._currentNode = SAX2DTM2.this.makeNodeHandle(n);
            return this.returnNode(n2);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            block7 : {
                int n2;
                int n3;
                block9 : {
                    block8 : {
                        n3 = n;
                        if (n == 0) {
                            n3 = SAX2DTM2.this.getDocument();
                        }
                        if (!this._isRestartable) break block7;
                        this._startNode = n3;
                        n2 = SAX2DTM2.this._type2(n3 = SAX2DTM2.this.makeNodeIdentity(n3));
                        if (2 == n2) break block8;
                        n = n3;
                        if (13 != n2) break block9;
                    }
                    n = SAX2DTM2.this._parent2(n3);
                    n3 = SAX2DTM2.this._firstch2(n);
                    if (-1 != n3) {
                        this._currentNode = SAX2DTM2.this.makeNodeHandle(n3);
                        return this.resetPosition();
                    }
                }
                do {
                    n2 = SAX2DTM2.this._nextsib2(n);
                    n3 = n;
                    if (-1 == n2) {
                        n3 = SAX2DTM2.this._parent2(n);
                    }
                    if (-1 != n2) break;
                    n = n3;
                } while (-1 != n3);
                this._currentNode = SAX2DTM2.this.makeNodeHandle(n2);
                return this.resetPosition();
            }
            return this;
        }
    }

    public class FollowingSiblingIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        public FollowingSiblingIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
        }

        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = -1;
            if (n != -1) {
                n2 = SAX2DTM2.this._nextsib2(this._currentNode);
            }
            this._currentNode = n2;
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(this._currentNode));
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = SAX2DTM2.this.makeNodeIdentity(n2);
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class ParentIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        private int _nodeType;

        public ParentIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
            this._nodeType = -1;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            if (n == -1) {
                return -1;
            }
            int n2 = this._nodeType;
            if (n2 == -1) {
                this._currentNode = -1;
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
            }
            if (n2 >= 14) {
                if (n2 == SAX2DTM2.this._exptype2(n)) {
                    this._currentNode = -1;
                    return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
                }
            } else if (n2 == SAX2DTM2.this._type2(n)) {
                this._currentNode = -1;
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
            }
            return -1;
        }

        public DTMAxisIterator setNodeType(int n) {
            this._nodeType = n;
            return this;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                if (n2 != -1) {
                    SAX2DTM2 sAX2DTM2 = SAX2DTM2.this;
                    this._currentNode = sAX2DTM2._parent2(sAX2DTM2.makeNodeIdentity(n2));
                } else {
                    this._currentNode = -1;
                }
                return this.resetPosition();
            }
            return this;
        }
    }

    public class PrecedingIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        protected int _markedDescendant;
        protected int _markedNode;
        protected int _markedsp;
        private final int _maxAncestors;
        protected int _oldsp;
        protected int _sp;
        protected int[] _stack;

        public PrecedingIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
            this._maxAncestors = 8;
            this._stack = new int[8];
        }

        @Override
        public DTMAxisIterator cloneIterator() {
            this._isRestartable = false;
            try {
                PrecedingIterator precedingIterator = (PrecedingIterator)Object.super.clone();
                int[] arrn = new int[this._stack.length];
                System.arraycopy(this._stack, 0, arrn, 0, this._stack.length);
                precedingIterator._stack = arrn;
                return precedingIterator;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
            }
        }

        @Override
        public void gotoMark() {
            this._sp = this._markedsp;
            this._currentNode = this._markedNode;
        }

        @Override
        public boolean isReverse() {
            return true;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            do {
                this._currentNode = n + 1;
                if (this._sp < 0) break;
                n = this._currentNode;
                int[] arrn = this._stack;
                int n2 = this._sp;
                if (n < arrn[n2]) {
                    n = SAX2DTM2.this._type2(this._currentNode);
                    if (n != 2 && n != 13) {
                        return this.returnNode(SAX2DTM2.this.makeNodeHandle(this._currentNode));
                    }
                } else {
                    this._sp = n2 - 1;
                }
                n = this._currentNode;
            } while (true);
            return -1;
        }

        @Override
        public DTMAxisIterator reset() {
            this._sp = this._oldsp;
            return this.resetPosition();
        }

        @Override
        public void setMark() {
            this._markedsp = this._sp;
            this._markedNode = this._currentNode;
            this._markedDescendant = this._stack[0];
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                n = n2 = SAX2DTM2.this.makeNodeIdentity(n2);
                if (SAX2DTM2.this._type2(n2) == 2) {
                    n = SAX2DTM2.this._parent2(n2);
                }
                this._startNode = n;
                int[] arrn = this._stack;
                int n3 = 0;
                arrn[0] = n;
                n2 = n;
                n = n3;
                do {
                    n2 = n3 = SAX2DTM2.this._parent2(n2);
                    if (n3 == -1) break;
                    arrn = this._stack;
                    if (++n == arrn.length) {
                        int[] arrn2 = new int[n * 2];
                        System.arraycopy(arrn, 0, arrn2, 0, n);
                        this._stack = arrn2;
                    }
                    this._stack[n] = n2;
                } while (true);
                n2 = n;
                if (n > 0) {
                    n2 = n - 1;
                }
                this._currentNode = this._stack[n2];
                this._sp = n2;
                this._oldsp = n2;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class PrecedingSiblingIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        protected int _startNodeID;

        public PrecedingSiblingIterator() {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
        }

        @Override
        public boolean isReverse() {
            return true;
        }

        @Override
        public int next() {
            if (this._currentNode != this._startNodeID && this._currentNode != -1) {
                int n = this._currentNode;
                this._currentNode = SAX2DTM2.this._nextsib2(n);
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._startNodeID = n2 = SAX2DTM2.this.makeNodeIdentity(n2);
                if (n2 == -1) {
                    this._currentNode = n2;
                    return this.resetPosition();
                }
                n = SAX2DTM2.this._type2(n2);
                if (2 != n && 13 != n) {
                    this._currentNode = SAX2DTM2.this._parent2(n2);
                    this._currentNode = -1 != this._currentNode ? SAX2DTM2.this._firstch2(this._currentNode) : n2;
                } else {
                    this._currentNode = n2;
                }
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedAncestorIterator
    extends AncestorIterator {
        private final int _nodeType;

        public TypedAncestorIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int getLast() {
            return this.m_size;
        }

        @Override
        public int getNodeByPosition(int n) {
            if (n > 0 && n <= this.m_size) {
                return this.m_ancestors[n - 1];
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            this.m_realStartNode = n2;
            if (this._isRestartable) {
                int n3 = SAX2DTM2.this.makeNodeIdentity(n2);
                this.m_size = 0;
                int n4 = -1;
                if (n3 == -1) {
                    this._currentNode = -1;
                    this.m_ancestorsPos = 0;
                    return this;
                }
                int n5 = this._nodeType;
                n = n3;
                if (!this._includeSelf) {
                    n = SAX2DTM2.this._parent2(n3);
                    n2 = SAX2DTM2.this.makeNodeHandle(n);
                }
                this._startNode = n2;
                n2 = n;
                if (n5 >= 14) {
                    while (n != -1) {
                        if (SAX2DTM2.this._exptype2(n) == n5) {
                            int[] arrn;
                            if (this.m_size >= this.m_ancestors.length) {
                                arrn = new int[this.m_size * 2];
                                System.arraycopy(this.m_ancestors, 0, arrn, 0, this.m_ancestors.length);
                                this.m_ancestors = arrn;
                            }
                            arrn = this.m_ancestors;
                            n2 = this.m_size;
                            this.m_size = n2 + 1;
                            arrn[n2] = SAX2DTM2.this.makeNodeHandle(n);
                        }
                        n = SAX2DTM2.this._parent2(n);
                    }
                } else {
                    while (n2 != -1) {
                        n = SAX2DTM2.this._exptype2(n2);
                        if (n < 14 && n == n5 || n >= 14 && SAX2DTM2.this.m_extendedTypes[n].getNodeType() == n5) {
                            int[] arrn;
                            if (this.m_size >= this.m_ancestors.length) {
                                arrn = new int[this.m_size * 2];
                                System.arraycopy(this.m_ancestors, 0, arrn, 0, this.m_ancestors.length);
                                this.m_ancestors = arrn;
                            }
                            arrn = this.m_ancestors;
                            n = this.m_size;
                            this.m_size = n + 1;
                            arrn[n] = SAX2DTM2.this.makeNodeHandle(n2);
                        }
                        n2 = SAX2DTM2.this._parent2(n2);
                    }
                }
                this.m_ancestorsPos = this.m_size - 1;
                n = this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : n4;
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedAttributeIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        private final int _nodeType;

        public TypedAttributeIterator(int n) {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            this._currentNode = -1;
            return this.returnNode(n);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            if (this._isRestartable) {
                this._startNode = n;
                this._currentNode = SAX2DTM2.this.getTypedAttribute(n, this._nodeType);
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedChildrenIterator
    extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
        private final int _nodeType;

        public TypedChildrenIterator(int n) {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
            this._nodeType = n;
        }

        @Override
        public int getNodeByPosition(int n) {
            if (n <= 0) {
                return -1;
            }
            int n2 = this._currentNode;
            int n3 = 0;
            int n4 = 0;
            int n5 = this._nodeType;
            int n6 = n2;
            if (n5 != 1) {
                n3 = n4;
                while (n2 != -1) {
                    n6 = n3++;
                    if (SAX2DTM2.this._exptype2(n2) == n5) {
                        n6 = n3;
                        if (n3 == n) {
                            return SAX2DTM2.this.makeNodeHandle(n2);
                        }
                    }
                    n2 = SAX2DTM2.this._nextsib2(n2);
                    n3 = n6;
                }
                return -1;
            }
            while (n6 != -1) {
                n2 = n3++;
                if (SAX2DTM2.this._exptype2(n6) >= 14) {
                    n2 = n3;
                    if (n3 == n) {
                        return SAX2DTM2.this.makeNodeHandle(n6);
                    }
                }
                n6 = SAX2DTM2.this._nextsib2(n6);
                n3 = n2;
            }
            return -1;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            if (n == -1) {
                return -1;
            }
            int n2 = this._nodeType;
            int n3 = n;
            if (n2 != 1) {
                n3 = n;
                do {
                    n = n3;
                    if (n3 != -1) {
                        n = n3;
                        if (SAX2DTM2.this._exptype2(n3) != n2) {
                            n3 = SAX2DTM2.this._nextsib2(n3);
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                do {
                    n = n3;
                    if (n3 == -1) break;
                    if (SAX2DTM2.this._exptype2(n3) >= 14) {
                        n = n3;
                        break;
                    }
                    n3 = SAX2DTM2.this._nextsib2(n3);
                } while (true);
            }
            if (n == -1) {
                this._currentNode = -1;
                return -1;
            }
            this._currentNode = SAX2DTM2.this._nextsib2(n);
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = SAX2DTM2.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                n = -1;
                if (n2 != -1) {
                    SAX2DTM2 sAX2DTM2 = SAX2DTM2.this;
                    n = sAX2DTM2._firstch2(sAX2DTM2.makeNodeIdentity(this._startNode));
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedDescendantIterator
    extends DescendantIterator {
        private final int _nodeType;

        public TypedDescendantIterator(int n) {
            this._nodeType = n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public int next() {
            block6 : {
                block7 : {
                    block5 : {
                        var1_1 = this._startNode;
                        if (this._startNode == -1) {
                            return -1;
                        }
                        var2_2 = this._currentNode;
                        var3_3 = this._nodeType;
                        if (var3_3 != 1) break block5;
                        var4_5 = var2_2;
                        if (var1_1 != 0) break block6;
                        break block7;
                    }
                    while (-1 != (var5_6 = SAX2DTM2.this._exptype2(var4_4 = var2_2 + 1)) && (SAX2DTM2.this._parent2(var4_4) >= var1_1 || var1_1 == var4_4)) {
                        var2_2 = var4_4;
                        if (var5_6 != var3_3) continue;
                        var2_2 = var4_4;
                        ** GOTO lbl35
                    }
                    this._currentNode = -1;
                    return -1;
                }
                do lbl-1000: // 3 sources:
                {
                    if (-1 == (var1_1 = SAX2DTM2.this._exptype2(var4_5 = var2_2 + 1))) {
                        this._currentNode = -1;
                        return -1;
                    }
                    var2_2 = var4_5;
                    if (var1_1 < 14) ** GOTO lbl-1000
                    var2_2 = var4_5;
                } while (SAX2DTM2.this.m_extendedTypes[var1_1].getNodeType() != 1);
                var2_2 = var4_5;
                ** GOTO lbl35
            }
            while (-1 != (var3_3 = SAX2DTM2.this._exptype2(var2_2 = var4_5 + 1)) && (SAX2DTM2.this._parent2(var2_2) >= var1_1 || var1_1 == var2_2)) {
                var4_5 = var2_2;
                if (var3_3 < 14) continue;
                var4_5 = var2_2;
                if (SAX2DTM2.this.m_extendedTypes[var3_3].getNodeType() != 1) continue;
lbl35: // 3 sources:
                this._currentNode = var2_2;
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(var2_2));
            }
            this._currentNode = -1;
            return -1;
        }
    }

    public final class TypedFollowingIterator
    extends FollowingIterator {
        private final int _nodeType;

        public TypedFollowingIterator(int n) {
            this._nodeType = n;
        }

        /*
         * Exception decompiling
         */
        @Override
        public int next() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[DOLOOP]], but top level block is 4[SIMPLE_IF_TAKEN]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    }

    public final class TypedFollowingSiblingIterator
    extends FollowingSiblingIterator {
        private final int _nodeType;

        public TypedFollowingSiblingIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = -1;
            if (n == -1) {
                return -1;
            }
            int n3 = this._currentNode;
            int n4 = this._nodeType;
            n = n3;
            if (n4 != 1) {
                n = n3;
                do {
                    int n5;
                    n3 = n = (n5 = SAX2DTM2.this._nextsib2(n));
                    if (n5 != -1) {
                        n3 = n;
                        if (SAX2DTM2.this._exptype2(n) != n4) {
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                do {
                    int n6;
                    n3 = n = (n6 = SAX2DTM2.this._nextsib2(n));
                    if (n6 == -1) break;
                    n3 = n;
                } while (SAX2DTM2.this._exptype2(n) < 14);
            }
            this._currentNode = n3;
            n = n3 == -1 ? n2 : this.returnNode(SAX2DTM2.this.makeNodeHandle(n3));
            return n;
        }
    }

    public final class TypedPrecedingIterator
    extends PrecedingIterator {
        private final int _nodeType;

        public TypedPrecedingIterator(int n) {
            this._nodeType = n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public int next() {
            block4 : {
                var1_1 = this._currentNode;
                var2_2 = this._nodeType;
                var3_3 = var1_1;
                if (var2_2 < 14) break block4;
                var3_3 = var1_1;
                do lbl-1000: // 3 sources:
                {
                    block6 : {
                        block5 : {
                            var1_1 = var3_3 + 1;
                            if (this._sp >= 0) break block5;
                            var3_3 = -1;
                            ** GOTO lbl46
                        }
                        if (var1_1 < this._stack[this._sp]) break block6;
                        this._sp = var4_4 = this._sp - 1;
                        var3_3 = var1_1;
                        if (var4_4 >= 0) ** GOTO lbl-1000
                        var3_3 = -1;
                        ** GOTO lbl46
                    }
                    var3_3 = var1_1;
                } while (SAX2DTM2.this._exptype2(var1_1) != var2_2);
                var3_3 = var1_1;
                ** GOTO lbl46
            }
            do {
                block11 : {
                    block8 : {
                        block10 : {
                            block9 : {
                                block7 : {
                                    var1_1 = var3_3 + 1;
                                    if (this._sp >= 0) break block7;
                                    var3_3 = -1;
                                    break block8;
                                }
                                if (var1_1 < this._stack[this._sp]) break block9;
                                this._sp = var4_5 = this._sp - 1;
                                var3_3 = var1_1;
                                if (var4_5 >= 0) continue;
                                var3_3 = -1;
                                break block8;
                            }
                            var4_5 = SAX2DTM2.this._exptype2(var1_1);
                            if (var4_5 >= 14) break block10;
                            var3_3 = var1_1;
                            if (var4_5 != var2_2) continue;
                            var3_3 = var1_1;
                            break block8;
                        }
                        if (SAX2DTM2.this.m_extendedTypes[var4_5].getNodeType() != var2_2) break block11;
                        var3_3 = var1_1;
                    }
                    this._currentNode = var3_3;
                    var1_1 = -1;
                    if (var3_3 != -1) return this.returnNode(SAX2DTM2.this.makeNodeHandle(var3_3));
                    return var1_1;
                }
                var3_3 = var1_1;
            } while (true);
        }
    }

    public final class TypedPrecedingSiblingIterator
    extends PrecedingSiblingIterator {
        private final int _nodeType;

        public TypedPrecedingSiblingIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int getLast() {
            if (this._last != -1) {
                return this._last;
            }
            this.setMark();
            int n = this._currentNode;
            int n2 = this._nodeType;
            int n3 = this._startNodeID;
            int n4 = 0;
            int n5 = 0;
            int n6 = n;
            if (n2 != 1) {
                n4 = n5;
                n6 = n;
                do {
                    n = n4;
                    if (n6 != -1) {
                        n = n4;
                        if (n6 != n3) {
                            n = n4;
                            if (SAX2DTM2.this._exptype2(n6) == n2) {
                                n = n4 + 1;
                            }
                            n6 = SAX2DTM2.this._nextsib2(n6);
                            n4 = n;
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                do {
                    n = n4;
                    if (n6 == -1) break;
                    n = n4;
                    if (n6 == n3) break;
                    n = n4;
                    if (SAX2DTM2.this._exptype2(n6) >= 14) {
                        n = n4 + 1;
                    }
                    n6 = SAX2DTM2.this._nextsib2(n6);
                    n4 = n;
                } while (true);
            }
            this.gotoMark();
            this._last = n;
            return n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = this._nodeType;
            int n3 = this._startNodeID;
            int n4 = n;
            if (n2 != 1) {
                n4 = n;
                do {
                    n = n4;
                    if (n4 != -1) {
                        n = n4;
                        if (n4 != n3) {
                            n = n4;
                            if (SAX2DTM2.this._exptype2(n4) != n2) {
                                n4 = SAX2DTM2.this._nextsib2(n4);
                                continue;
                            }
                        }
                    }
                    break;
                } while (true);
            } else {
                do {
                    n = n4;
                    if (n4 == -1) break;
                    n = n4;
                    if (n4 == n3) break;
                    n = n4;
                    if (SAX2DTM2.this._exptype2(n4) >= 14) break;
                    n4 = SAX2DTM2.this._nextsib2(n4);
                } while (true);
            }
            if (n != -1 && n != n3) {
                this._currentNode = SAX2DTM2.this._nextsib2(n);
                return this.returnNode(SAX2DTM2.this.makeNodeHandle(n));
            }
            this._currentNode = -1;
            return -1;
        }
    }

    public class TypedRootIterator
    extends DTMDefaultBaseIterators.RootIterator {
        private final int _nodeType;

        public TypedRootIterator(int n) {
            super((DTMDefaultBaseIterators)SAX2DTM2.this);
            this._nodeType = n;
        }

        @Override
        public int next() {
            if (this._startNode == this._currentNode) {
                return -1;
            }
            int n = this._startNode;
            SAX2DTM2 sAX2DTM2 = SAX2DTM2.this;
            int n2 = sAX2DTM2._exptype2(sAX2DTM2.makeNodeIdentity(n));
            this._currentNode = n;
            int n3 = this._nodeType;
            if (n3 >= 14 ? n3 == n2 : (n2 < 14 ? n2 == n3 : SAX2DTM2.this.m_extendedTypes[n2].getNodeType() == this._nodeType)) {
                return this.returnNode(n);
            }
            return -1;
        }
    }

    public final class TypedSingletonIterator
    extends DTMDefaultBaseIterators.SingletonIterator {
        private final int _nodeType;

        public TypedSingletonIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            SAX2DTM2 sAX2DTM2;
            SAX2DTM2 sAX2DTM22;
            int n = this._currentNode;
            if (n == -1) {
                return -1;
            }
            this._currentNode = -1;
            if (this._nodeType >= 14 ? (sAX2DTM22 = SAX2DTM2.this)._exptype2(sAX2DTM22.makeNodeIdentity(n)) == this._nodeType : (sAX2DTM2 = SAX2DTM2.this)._type2(sAX2DTM2.makeNodeIdentity(n)) == this._nodeType) {
                return this.returnNode(n);
            }
            return -1;
        }
    }

}

