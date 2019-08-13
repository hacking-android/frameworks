/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;
import javax.xml.transform.Source;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMException;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMManagerDefault;
import org.apache.xml.dtm.ref.DTMNodeProxy;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.BoolStack;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public abstract class DTMDefaultBase
implements DTM {
    public static final int DEFAULT_BLOCKSIZE = 512;
    public static final int DEFAULT_NUMBLOCKS = 32;
    public static final int DEFAULT_NUMBLOCKS_SMALL = 4;
    static final boolean JJK_DEBUG = false;
    protected static final int NOTPROCESSED = -2;
    public static final int ROOTNODE = 0;
    protected String m_documentBaseURI;
    protected SuballocatedIntVector m_dtmIdent;
    protected int[][][] m_elemIndexes;
    protected ExpandedNameTable m_expandedNameTable;
    protected SuballocatedIntVector m_exptype;
    protected SuballocatedIntVector m_firstch;
    protected boolean m_indexing;
    public DTMManager m_mgr;
    protected DTMManagerDefault m_mgrDefault;
    protected SuballocatedIntVector m_namespaceDeclSetElements;
    protected Vector m_namespaceDeclSets;
    private Vector m_namespaceLists;
    protected SuballocatedIntVector m_nextsib;
    protected SuballocatedIntVector m_parent;
    protected SuballocatedIntVector m_prevsib;
    protected boolean m_shouldStripWS;
    protected BoolStack m_shouldStripWhitespaceStack;
    protected int m_size = 0;
    protected DTMAxisTraverser[] m_traversers;
    protected DTMWSFilter m_wsfilter;
    protected XMLStringFactory m_xstrf;

    public DTMDefaultBase(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        this(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl, 512, true, false);
    }

    public DTMDefaultBase(DTMManager object, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl, int n2, boolean bl2, boolean bl3) {
        int n3;
        Object var10_10 = null;
        this.m_namespaceDeclSets = null;
        this.m_namespaceDeclSetElements = null;
        this.m_mgrDefault = null;
        this.m_shouldStripWS = false;
        this.m_namespaceLists = null;
        if (n2 <= 64) {
            n3 = 4;
            this.m_dtmIdent = new SuballocatedIntVector(4, 1);
        } else {
            n3 = 32;
            this.m_dtmIdent = new SuballocatedIntVector(32);
        }
        this.m_exptype = new SuballocatedIntVector(n2, n3);
        this.m_firstch = new SuballocatedIntVector(n2, n3);
        this.m_nextsib = new SuballocatedIntVector(n2, n3);
        this.m_parent = new SuballocatedIntVector(n2, n3);
        if (bl2) {
            this.m_prevsib = new SuballocatedIntVector(n2, n3);
        }
        this.m_mgr = object;
        if (object instanceof DTMManagerDefault) {
            this.m_mgrDefault = (DTMManagerDefault)object;
        }
        object = var10_10;
        if (source != null) {
            object = source.getSystemId();
        }
        this.m_documentBaseURI = object;
        this.m_dtmIdent.setElementAt(n, 0);
        this.m_wsfilter = dTMWSFilter;
        this.m_xstrf = xMLStringFactory;
        this.m_indexing = bl;
        this.m_expandedNameTable = bl ? new ExpandedNameTable() : this.m_mgrDefault.getExpandedNameTable(this);
        if (dTMWSFilter != null) {
            this.m_shouldStripWhitespaceStack = new BoolStack();
            this.pushShouldStripWhitespace(false);
        }
    }

    protected int _exptype(int n) {
        if (n == -1) {
            return -1;
        }
        while (n >= this.m_size) {
            if (this.nextNode() || n < this.m_size) continue;
            return -1;
        }
        return this.m_exptype.elementAt(n);
    }

    protected int _firstch(int n) {
        int n2 = n >= this.m_size ? -2 : this.m_firstch.elementAt(n);
        while (n2 == -2) {
            boolean bl = this.nextNode();
            if (n >= this.m_size && !bl) {
                return -1;
            }
            n2 = this.m_firstch.elementAt(n);
            if (n2 != -2 || bl) continue;
            return -1;
        }
        return n2;
    }

    protected int _level(int n) {
        while (n >= this.m_size) {
            if (this.nextNode() || n < this.m_size) continue;
            return -1;
        }
        int n2 = 0;
        int n3 = n;
        n = n2;
        do {
            n3 = n2 = this._parent(n3);
            if (-1 == n2) break;
            ++n;
        } while (true);
        return n;
    }

    protected int _nextsib(int n) {
        int n2 = n >= this.m_size ? -2 : this.m_nextsib.elementAt(n);
        while (n2 == -2) {
            boolean bl = this.nextNode();
            if (n >= this.m_size && !bl) {
                return -1;
            }
            n2 = this.m_nextsib.elementAt(n);
            if (n2 != -2 || bl) continue;
            return -1;
        }
        return n2;
    }

    protected int _parent(int n) {
        if (n < this.m_size) {
            return this.m_parent.elementAt(n);
        }
        do {
            boolean bl = this.nextNode();
            if (n < this.m_size || bl) continue;
            return -1;
        } while (n >= this.m_size);
        return this.m_parent.elementAt(n);
    }

    protected int _prevsib(int n) {
        if (n < this.m_size) {
            return this.m_prevsib.elementAt(n);
        }
        do {
            boolean bl = this.nextNode();
            if (n < this.m_size || bl) continue;
            return -1;
        } while (n >= this.m_size);
        return this.m_prevsib.elementAt(n);
    }

    protected short _type(int n) {
        if (-1 != (n = this._exptype(n))) {
            return this.m_expandedNameTable.getType(n);
        }
        return -1;
    }

    @Override
    public void appendChild(int n, boolean bl, boolean bl2) {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
    }

    @Override
    public void appendTextChild(String string) {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
    }

    protected void declareNamespaceInContext(int n, int n2) {
        int n3;
        SuballocatedIntVector suballocatedIntVector;
        SuballocatedIntVector suballocatedIntVector2 = null;
        if (this.m_namespaceDeclSets == null) {
            this.m_namespaceDeclSetElements = new SuballocatedIntVector(32);
            this.m_namespaceDeclSetElements.addElement(n);
            this.m_namespaceDeclSets = new Vector();
            suballocatedIntVector = new SuballocatedIntVector(32);
            this.m_namespaceDeclSets.addElement(suballocatedIntVector);
        } else {
            n3 = this.m_namespaceDeclSetElements.size() - 1;
            suballocatedIntVector = suballocatedIntVector2;
            if (n3 >= 0) {
                suballocatedIntVector = suballocatedIntVector2;
                if (n == this.m_namespaceDeclSetElements.elementAt(n3)) {
                    suballocatedIntVector = (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(n3);
                }
            }
        }
        suballocatedIntVector2 = suballocatedIntVector;
        if (suballocatedIntVector == null) {
            this.m_namespaceDeclSetElements.addElement(n);
            suballocatedIntVector2 = this.findNamespaceContext(this._parent(n));
            if (suballocatedIntVector2 != null) {
                n3 = suballocatedIntVector2.size();
                suballocatedIntVector = new SuballocatedIntVector(Math.max(Math.min(n3 + 16, 2048), 32));
                for (n = 0; n < n3; ++n) {
                    suballocatedIntVector.addElement(suballocatedIntVector2.elementAt(n));
                }
            } else {
                suballocatedIntVector = new SuballocatedIntVector(32);
            }
            this.m_namespaceDeclSets.addElement(suballocatedIntVector);
            suballocatedIntVector2 = suballocatedIntVector;
        }
        n3 = this._exptype(n2);
        for (n = suballocatedIntVector2.size() - 1; n >= 0; --n) {
            if (n3 != this.getExpandedTypeID(suballocatedIntVector2.elementAt(n))) continue;
            suballocatedIntVector2.setElementAt(this.makeNodeHandle(n2), n);
            return;
        }
        suballocatedIntVector2.addElement(this.makeNodeHandle(n2));
    }

    @Override
    public abstract void dispatchCharactersEvents(int var1, ContentHandler var2, boolean var3) throws SAXException;

    @Override
    public abstract void dispatchToEvents(int var1, ContentHandler var2) throws SAXException;

    @Override
    public void documentRegistration() {
    }

    @Override
    public void documentRelease() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void dumpDTM(OutputStream var1_1) {
        var2_3 = var1_1;
        if (var1_1 != null) ** GOTO lbl20
        try {
            var2_3 = new StringBuilder();
            var2_3.append("DTMDump");
            var2_3.append(this.hashCode());
            var2_3.append(".txt");
            var1_1 = new File(var2_3.toString());
            var2_3 = System.err;
            var3_4 = new StringBuilder();
            var3_4.append("Dumping... ");
            var3_4.append(var1_1.getAbsolutePath());
            var2_3.println(var3_4.toString());
            var2_3 = new FileOutputStream((File)var1_1);
lbl20: // 2 sources:
            var3_4 = new PrintStream((OutputStream)var2_3);
            while (this.nextNode()) {
            }
            var4_5 = this.m_size;
            var1_1 = new StringBuilder();
            var1_1.append("Total nodes: ");
            var1_1.append(var4_5);
            var3_4.println(var1_1.toString());
            for (var5_6 = 0; var5_6 < var4_5; ++var5_6) {
                var6_7 = this.makeNodeHandle(var5_6);
                var1_1 = new StringBuilder();
                var1_1.append("=========== index=");
                var1_1.append(var5_6);
                var1_1.append(" handle=");
                var1_1.append(var6_7);
                var1_1.append(" ===========");
                var3_4.println(var1_1.toString());
                var1_1 = new StringBuilder();
                var1_1.append("NodeName: ");
                var1_1.append(this.getNodeName(var6_7));
                var3_4.println(var1_1.toString());
                var1_1 = new StringBuilder();
                var1_1.append("NodeNameX: ");
                var1_1.append(this.getNodeNameX(var6_7));
                var3_4.println(var1_1.toString());
                var1_1 = new StringBuilder();
                var1_1.append("LocalName: ");
                var1_1.append(this.getLocalName(var6_7));
                var3_4.println(var1_1.toString());
                var1_1 = new StringBuilder();
                var1_1.append("NamespaceURI: ");
                var1_1.append(this.getNamespaceURI(var6_7));
                var3_4.println(var1_1.toString());
                var1_1 = new StringBuilder();
                var1_1.append("Prefix: ");
                var1_1.append(this.getPrefix(var6_7));
                var3_4.println(var1_1.toString());
                var7_8 = this._exptype(var5_6);
                var1_1 = new StringBuilder();
                var1_1.append("Expanded Type ID: ");
                var1_1.append(Integer.toHexString(var7_8));
                var3_4.println(var1_1.toString());
                var7_8 = this._type(var5_6);
                var1_1 = "DOCUMENT_NODE";
            }
        }
        catch (IOException var1_2) {
            var1_2.printStackTrace(System.err);
            throw new RuntimeException(var1_2.getMessage());
        }
        {
            switch (var7_8) {
                default: {
                    var1_1 = "Unknown!";
                    break;
                }
                case 13: {
                    var1_1 = "NAMESPACE_NODE";
                    break;
                }
                case 12: {
                    var1_1 = "NOTATION_NODE";
                    break;
                }
                case 11: {
                    var1_1 = "DOCUMENT_FRAGMENT_NODE";
                    break;
                }
                case 10: {
                    break;
                }
                case 9: {
                    break;
                }
                case 8: {
                    var1_1 = "COMMENT_NODE";
                    break;
                }
                case 7: {
                    var1_1 = "PROCESSING_INSTRUCTION_NODE";
                    break;
                }
                case 6: {
                    var1_1 = "ENTITY_NODE";
                    break;
                }
                case 5: {
                    var1_1 = "ENTITY_REFERENCE_NODE";
                    break;
                }
                case 4: {
                    var1_1 = "CDATA_SECTION_NODE";
                    break;
                }
                case 3: {
                    var1_1 = "TEXT_NODE";
                    break;
                }
                case 2: {
                    var1_1 = "ATTRIBUTE_NODE";
                    break;
                }
                case 1: {
                    var1_1 = "ELEMENT_NODE";
                    break;
                }
                case -1: {
                    var1_1 = "NULL";
                }
            }
            var2_3 = new StringBuilder();
            var2_3.append("Type: ");
            var2_3.append((String)var1_1);
            var3_4.println(var2_3.toString());
            var7_8 = this._firstch(var5_6);
            if (-1 == var7_8) {
                var3_4.println("First child: DTM.NULL");
            } else if (-2 == var7_8) {
                var3_4.println("First child: NOTPROCESSED");
            } else {
                var1_1 = new StringBuilder();
                var1_1.append("First child: ");
                var1_1.append(var7_8);
                var3_4.println(var1_1.toString());
            }
            if (this.m_prevsib != null) {
                var7_8 = this._prevsib(var5_6);
                if (-1 == var7_8) {
                    var3_4.println("Prev sibling: DTM.NULL");
                } else if (-2 == var7_8) {
                    var3_4.println("Prev sibling: NOTPROCESSED");
                } else {
                    var1_1 = new StringBuilder();
                    var1_1.append("Prev sibling: ");
                    var1_1.append(var7_8);
                    var3_4.println(var1_1.toString());
                }
            }
            if (-1 == (var7_8 = this._nextsib(var5_6))) {
                var3_4.println("Next sibling: DTM.NULL");
            } else if (-2 == var7_8) {
                var3_4.println("Next sibling: NOTPROCESSED");
            } else {
                var1_1 = new StringBuilder();
                var1_1.append("Next sibling: ");
                var1_1.append(var7_8);
                var3_4.println(var1_1.toString());
            }
            var7_8 = this._parent(var5_6);
            if (-1 == var7_8) {
                var3_4.println("Parent: DTM.NULL");
            } else if (-2 == var7_8) {
                var3_4.println("Parent: NOTPROCESSED");
            } else {
                var1_1 = new StringBuilder();
                var1_1.append("Parent: ");
                var1_1.append(var7_8);
                var3_4.println(var1_1.toString());
            }
            var7_8 = this._level(var5_6);
            var1_1 = new StringBuilder();
            var1_1.append("Level: ");
            var1_1.append(var7_8);
            var3_4.println(var1_1.toString());
            var1_1 = new StringBuilder();
            var1_1.append("Node Value: ");
            var1_1.append(this.getNodeValue(var6_7));
            var3_4.println(var1_1.toString());
            var1_1 = new StringBuilder();
            var1_1.append("String Value: ");
            var1_1.append(this.getStringValue(var6_7));
            var3_4.println(var1_1.toString());
            continue;
        }
    }

    public String dumpNode(int n) {
        String string;
        if (n == -1) {
            return "[null]";
        }
        switch (this.getNodeType(n)) {
            default: {
                string = "Unknown!";
                break;
            }
            case 13: {
                string = "NAMESPACE";
                break;
            }
            case 12: {
                string = "NOTATION";
                break;
            }
            case 11: {
                string = "DOC_FRAG";
                break;
            }
            case 10: {
                string = "DOC_TYPE";
                break;
            }
            case 9: {
                string = "DOC";
                break;
            }
            case 8: {
                string = "COMMENT";
                break;
            }
            case 7: {
                string = "PI";
                break;
            }
            case 6: {
                string = "ENTITY";
                break;
            }
            case 5: {
                string = "ENT_REF";
                break;
            }
            case 4: {
                string = "CDATA";
                break;
            }
            case 3: {
                string = "TEXT";
                break;
            }
            case 2: {
                string = "ATTR";
                break;
            }
            case 1: {
                string = "ELEMENT";
                break;
            }
            case -1: {
                string = "null";
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(n);
        stringBuilder.append(": ");
        stringBuilder.append(string);
        stringBuilder.append("(0x");
        stringBuilder.append(Integer.toHexString(this.getExpandedTypeID(n)));
        stringBuilder.append(") ");
        stringBuilder.append(this.getNodeNameX(n));
        stringBuilder.append(" {");
        stringBuilder.append(this.getNamespaceURI(n));
        stringBuilder.append("}=\"");
        stringBuilder.append(this.getNodeValue(n));
        stringBuilder.append("\"]");
        stringBuffer.append(stringBuilder.toString());
        return stringBuffer.toString();
    }

    protected void ensureSizeOfIndex(int n, int n2) {
        int[][][] arrn = this.m_elemIndexes;
        if (arrn == null) {
            this.m_elemIndexes = new int[n + 20][][];
        } else if (arrn.length <= n) {
            arrn = this.m_elemIndexes;
            this.m_elemIndexes = new int[n + 20][][];
            System.arraycopy(arrn, 0, this.m_elemIndexes, 0, arrn.length);
        }
        int[][][] arrn2 = this.m_elemIndexes;
        int[][] arrn3 = arrn2[n];
        if (arrn3 == null) {
            arrn = new int[n2 + 100][];
            arrn2[n] = arrn;
        } else {
            arrn = arrn3;
            if (arrn3.length <= n2) {
                arrn = new int[n2 + 100][];
                System.arraycopy(arrn3, 0, arrn, 0, arrn3.length);
                this.m_elemIndexes[n] = arrn;
            }
        }
        arrn2 = arrn[n2];
        if (arrn2 == null) {
            arrn3 = new int[128];
            arrn[n2] = arrn3;
            arrn3[0] = (int[])true;
        } else if (arrn2.length <= arrn2[0] + true) {
            arrn3 = new int[arrn2[0] + 1024];
            System.arraycopy(arrn2, 0, arrn3, 0, arrn2.length);
            arrn[n2] = arrn3;
        }
    }

    protected void error(String string) {
        throw new DTMException(string);
    }

    int findElementFromIndex(int n, int n2, int n3) {
        int[][][] arrn = this.m_elemIndexes;
        if (arrn != null && n < arrn.length && (arrn = arrn[n]) != null && n2 < arrn.length && (arrn = arrn[n2]) != null && (n = this.findGTE((int[])arrn, 1, (int)arrn[0], n3)) > -1) {
            return (int)arrn[n];
        }
        return -2;
    }

    protected int findGTE(int[] arrn, int n, int n2, int n3) {
        int n4;
        int n5 = n;
        n2 = n4 = n2 - 1 + n;
        n = n5;
        while (n <= n2) {
            int n6 = (n + n2) / 2;
            n5 = arrn[n6];
            if (n5 > n3) {
                n2 = n6 - 1;
                continue;
            }
            if (n5 < n3) {
                n = n6 + 1;
                continue;
            }
            return n6;
        }
        if (n > n4 || arrn[n] <= n3) {
            n = -1;
        }
        return n;
    }

    protected int findInSortedSuballocatedIntVector(SuballocatedIntVector suballocatedIntVector, int n) {
        int n2 = 0;
        int n3 = 0;
        if (suballocatedIntVector != null) {
            int n4 = 0;
            n2 = suballocatedIntVector.size() - 1;
            while (n4 <= n2) {
                n3 = (n4 + n2) / 2;
                int n5 = n - suballocatedIntVector.elementAt(n3);
                if (n5 == 0) {
                    return n3;
                }
                if (n5 < 0) {
                    n2 = n3 - 1;
                    continue;
                }
                n4 = n3 + 1;
            }
            n2 = n3;
            if (n4 > n3) {
                n2 = n4;
            }
        }
        return -1 - n2;
    }

    protected SuballocatedIntVector findNamespaceContext(int n) {
        SuballocatedIntVector suballocatedIntVector = this.m_namespaceDeclSetElements;
        if (suballocatedIntVector != null) {
            int n2 = this.findInSortedSuballocatedIntVector(suballocatedIntVector, n);
            if (n2 >= 0) {
                return (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(n2);
            }
            if (n2 == -1) {
                return null;
            }
            suballocatedIntVector = this.m_namespaceDeclSetElements;
            int n3 = -1 - n2 - 1;
            int n4 = suballocatedIntVector.elementAt(n3);
            int n5 = this._parent(n);
            int n6 = n3;
            int n7 = n4;
            n2 = n5;
            if (n3 == 0) {
                n6 = n3;
                n7 = n4;
                n2 = n5;
                if (n4 < n5) {
                    n2 = this.getDocumentRoot(this.makeNodeHandle(n));
                    n = this.makeNodeIdentity(n2);
                    if (this.getNodeType(n2) == 9 && (n2 = this._firstch(n)) != -1) {
                        n = n2;
                    }
                    n6 = n3;
                    n7 = n4;
                    n2 = n5;
                    if (n4 == n) {
                        return (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(n3);
                    }
                }
            }
            while (n6 >= 0 && n2 > 0) {
                if (n7 == n2) {
                    return (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(n6);
                }
                if (n7 < n2) {
                    n = n2;
                    do {
                        n = n2 = this._parent(n);
                    } while (n7 < n2);
                    continue;
                }
                if (n6 <= 0) break;
                suballocatedIntVector = this.m_namespaceDeclSetElements;
                n7 = suballocatedIntVector.elementAt(--n6);
            }
        }
        return null;
    }

    @Override
    public abstract int getAttributeNode(int var1, String var2, String var3);

    public SuballocatedIntVector getDTMIDs() {
        if (this.m_mgr == null) {
            return null;
        }
        return this.m_dtmIdent;
    }

    @Override
    public int getDocument() {
        return this.m_dtmIdent.elementAt(0);
    }

    @Override
    public boolean getDocumentAllDeclarationsProcessed() {
        return true;
    }

    @Override
    public String getDocumentBaseURI() {
        return this.m_documentBaseURI;
    }

    @Override
    public String getDocumentEncoding(int n) {
        return "UTF-8";
    }

    @Override
    public int getDocumentRoot(int n) {
        return this.getManager().getDTM(n).getDocument();
    }

    @Override
    public String getDocumentStandalone(int n) {
        return null;
    }

    @Override
    public String getDocumentSystemIdentifier(int n) {
        return this.m_documentBaseURI;
    }

    @Override
    public abstract String getDocumentTypeDeclarationPublicIdentifier();

    @Override
    public abstract String getDocumentTypeDeclarationSystemIdentifier();

    @Override
    public String getDocumentVersion(int n) {
        return null;
    }

    @Override
    public abstract int getElementById(String var1);

    @Override
    public int getExpandedTypeID(int n) {
        if ((n = this.makeNodeIdentity(n)) == -1) {
            return -1;
        }
        return this._exptype(n);
    }

    @Override
    public int getExpandedTypeID(String string, String string2, int n) {
        return this.m_expandedNameTable.getExpandedTypeID(string, string2, n);
    }

    @Override
    public int getFirstAttribute(int n) {
        return this.makeNodeHandle(this.getFirstAttributeIdentity(this.makeNodeIdentity(n)));
    }

    protected int getFirstAttributeIdentity(int n) {
        if (1 == this._type(n)) {
            int n2;
            do {
                n = n2 = this.getNextNodeIdentity(n);
                if (-1 == n2) break;
                n2 = this._type(n);
                if (n2 != 2) continue;
                return n;
            } while (13 == n2);
        }
        return -1;
    }

    @Override
    public int getFirstChild(int n) {
        return this.makeNodeHandle(this._firstch(this.makeNodeIdentity(n)));
    }

    @Override
    public int getFirstNamespaceNode(int n, boolean bl) {
        if (bl) {
            if (this._type(n = this.makeNodeIdentity(n)) == 1) {
                SuballocatedIntVector suballocatedIntVector = this.findNamespaceContext(n);
                if (suballocatedIntVector != null && suballocatedIntVector.size() >= 1) {
                    return suballocatedIntVector.elementAt(0);
                }
                return -1;
            }
            return -1;
        }
        if (this._type(n = this.makeNodeIdentity(n)) == 1) {
            int n2;
            do {
                n = n2 = this.getNextNodeIdentity(n);
                if (-1 == n2) break;
                n2 = this._type(n);
                if (n2 != 13) continue;
                return this.makeNodeHandle(n);
            } while (2 == n2);
            return -1;
        }
        return -1;
    }

    @Override
    public int getLastChild(int n) {
        n = this._firstch(this.makeNodeIdentity(n));
        int n2 = -1;
        while (n != -1) {
            n2 = n;
            n = this._nextsib(n);
        }
        return this.makeNodeHandle(n2);
    }

    @Override
    public short getLevel(int n) {
        return (short)(this._level(this.makeNodeIdentity(n)) + 1);
    }

    @Override
    public abstract String getLocalName(int var1);

    @Override
    public String getLocalNameFromExpandedNameID(int n) {
        return this.m_expandedNameTable.getLocalName(n);
    }

    public DTMManager getManager() {
        return this.m_mgr;
    }

    @Override
    public String getNamespaceFromExpandedNameID(int n) {
        return this.m_expandedNameTable.getNamespace(n);
    }

    public int getNamespaceType(int n) {
        n = this._exptype(this.makeNodeIdentity(n));
        return this.m_expandedNameTable.getNamespaceID(n);
    }

    @Override
    public abstract String getNamespaceURI(int var1);

    @Override
    public int getNextAttribute(int n) {
        if (this._type(n = this.makeNodeIdentity(n)) == 2) {
            return this.makeNodeHandle(this.getNextAttributeIdentity(n));
        }
        return -1;
    }

    protected int getNextAttributeIdentity(int n) {
        int n2;
        do {
            n = n2 = this.getNextNodeIdentity(n);
            if (-1 == n2) break;
            n2 = this._type(n);
            if (n2 != 2) continue;
            return n;
        } while (n2 == 13);
        return -1;
    }

    @Override
    public int getNextNamespaceNode(int n, int n2, boolean bl) {
        if (bl) {
            SuballocatedIntVector suballocatedIntVector = this.findNamespaceContext(this.makeNodeIdentity(n));
            if (suballocatedIntVector == null) {
                return -1;
            }
            n = suballocatedIntVector.indexOf(n2) + 1;
            if (n > 0 && n != suballocatedIntVector.size()) {
                return suballocatedIntVector.elementAt(n);
            }
            return -1;
        }
        n = this.makeNodeIdentity(n2);
        do {
            n = n2 = this.getNextNodeIdentity(n);
            if (-1 == n2) break;
            n2 = this._type(n);
            if (n2 != 13) continue;
            return this.makeNodeHandle(n);
        } while (n2 == 2);
        return -1;
    }

    protected abstract int getNextNodeIdentity(int var1);

    @Override
    public int getNextSibling(int n) {
        if (n == -1) {
            return -1;
        }
        return this.makeNodeHandle(this._nextsib(this.makeNodeIdentity(n)));
    }

    @Override
    public Node getNode(int n) {
        return new DTMNodeProxy(this, n);
    }

    public int getNodeHandle(int n) {
        return this.makeNodeHandle(n);
    }

    public int getNodeIdent(int n) {
        return this.makeNodeIdentity(n);
    }

    @Override
    public abstract String getNodeName(int var1);

    @Override
    public String getNodeNameX(int n) {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
        return null;
    }

    @Override
    public short getNodeType(int n) {
        if (n == -1) {
            return -1;
        }
        return this.m_expandedNameTable.getType(this._exptype(this.makeNodeIdentity(n)));
    }

    @Override
    public abstract String getNodeValue(int var1);

    protected abstract int getNumberOfNodes();

    @Override
    public int getOwnerDocument(int n) {
        if (9 == this.getNodeType(n)) {
            return -1;
        }
        return this.getDocumentRoot(n);
    }

    @Override
    public int getParent(int n) {
        if ((n = this.makeNodeIdentity(n)) > 0) {
            return this.makeNodeHandle(this._parent(n));
        }
        return -1;
    }

    @Override
    public abstract String getPrefix(int var1);

    @Override
    public int getPreviousSibling(int n) {
        if (n == -1) {
            return -1;
        }
        if (this.m_prevsib != null) {
            return this.makeNodeHandle(this._prevsib(this.makeNodeIdentity(n)));
        }
        int n2 = this.makeNodeIdentity(n);
        n = this._firstch(this._parent(n2));
        int n3 = -1;
        while (n != n2) {
            n3 = n;
            n = this._nextsib(n);
        }
        return this.makeNodeHandle(n3);
    }

    protected boolean getShouldStripWhitespace() {
        return this.m_shouldStripWS;
    }

    @Override
    public abstract XMLString getStringValue(int var1);

    @Override
    public char[] getStringValueChunk(int n, int n2, int[] arrn) {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
        return null;
    }

    @Override
    public int getStringValueChunkCount(int n) {
        this.error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
        return 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected int getTypedAttribute(int var1_1, int var2_2) {
        if (1 != this.getNodeType(var1_1)) return -1;
        var3_3 = this.makeNodeIdentity(var1_1);
        do lbl-1000: // 3 sources:
        {
            block1 : {
                var1_1 = var3_3 = this.getNextNodeIdentity(var3_3);
                if (-1 == var3_3) return -1;
                var4_4 = this._type(var1_1);
                if (var4_4 != 2) break block1;
                var3_3 = var1_1;
                if (this._exptype(var1_1) != var2_2) ** GOTO lbl-1000
                return this.makeNodeHandle(var1_1);
            }
            var3_3 = var1_1;
        } while (13 == var4_4);
        return -1;
    }

    public int getTypedFirstChild(int n, int n2) {
        if (n2 < 14) {
            n = this._firstch(this.makeNodeIdentity(n));
            while (n != -1) {
                int n3 = this._exptype(n);
                if (n3 != n2 && (n3 < 14 || this.m_expandedNameTable.getType(n3) != n2)) {
                    n = this._nextsib(n);
                    continue;
                }
                return this.makeNodeHandle(n);
            }
        } else {
            n = this._firstch(this.makeNodeIdentity(n));
            while (n != -1) {
                if (this._exptype(n) == n2) {
                    return this.makeNodeHandle(n);
                }
                n = this._nextsib(n);
            }
        }
        return -1;
    }

    public int getTypedNextSibling(int n, int n2) {
        int n3;
        int n4 = -1;
        if (n == -1) {
            return -1;
        }
        n = this.makeNodeIdentity(n);
        do {
            n = n3 = this._nextsib(n);
        } while (n3 != -1 && (n3 = this._exptype(n)) != n2 && this.m_expandedNameTable.getType(n3) != n2);
        n = n == -1 ? n4 : this.makeNodeHandle(n);
        return n;
    }

    @Override
    public abstract String getUnparsedEntityURI(String var1);

    @Override
    public boolean hasChildNodes(int n) {
        boolean bl = this._firstch(this.makeNodeIdentity(n)) != -1;
        return bl;
    }

    protected void indexNode(int n, int n2) {
        int[] arrn = this.m_expandedNameTable;
        if (1 == arrn.getType(n)) {
            int n3 = arrn.getNamespaceID(n);
            n = arrn.getLocalNameID(n);
            this.ensureSizeOfIndex(n3, n);
            arrn = this.m_elemIndexes[n3][n];
            arrn[arrn[0]] = n2;
            arrn[0] = arrn[0] + 1;
        }
    }

    @Override
    public abstract boolean isAttributeSpecified(int var1);

    @Override
    public boolean isCharacterElementContentWhitespace(int n) {
        return false;
    }

    @Override
    public boolean isDocumentAllDeclarationsProcessed(int n) {
        return true;
    }

    @Override
    public boolean isNodeAfter(int n, int n2) {
        n = this.makeNodeIdentity(n);
        n2 = this.makeNodeIdentity(n2);
        boolean bl = n != -1 && n2 != -1 && n <= n2;
        return bl;
    }

    @Override
    public boolean isSupported(String string, String string2) {
        return false;
    }

    public final int makeNodeHandle(int n) {
        if (-1 == n) {
            return -1;
        }
        return this.m_dtmIdent.elementAt(n >>> 16) + (65535 & n);
    }

    public final int makeNodeIdentity(int n) {
        int n2 = -1;
        if (-1 == n) {
            return -1;
        }
        DTMManagerDefault dTMManagerDefault = this.m_mgrDefault;
        if (dTMManagerDefault != null) {
            n2 = n >>> 16;
            if (dTMManagerDefault.m_dtms[n2] != this) {
                return -1;
            }
            return this.m_mgrDefault.m_dtm_offsets[n2] | n & 65535;
        }
        int n3 = this.m_dtmIdent.indexOf(-65536 & n);
        n = n3 == -1 ? n2 : (n3 << 16) + (65535 & n);
        return n;
    }

    @Override
    public void migrateTo(DTMManager dTMManager) {
        this.m_mgr = dTMManager;
        if (dTMManager instanceof DTMManagerDefault) {
            this.m_mgrDefault = (DTMManagerDefault)dTMManager;
        }
    }

    protected abstract boolean nextNode();

    protected void popShouldStripWhitespace() {
        BoolStack boolStack = this.m_shouldStripWhitespaceStack;
        if (boolStack != null) {
            this.m_shouldStripWS = boolStack.popAndTop();
        }
    }

    protected void pushShouldStripWhitespace(boolean bl) {
        this.m_shouldStripWS = bl;
        BoolStack boolStack = this.m_shouldStripWhitespaceStack;
        if (boolStack != null) {
            boolStack.push(bl);
        }
    }

    @Override
    public void setDocumentBaseURI(String string) {
        this.m_documentBaseURI = string;
    }

    @Override
    public void setFeature(String string, boolean bl) {
    }

    protected void setShouldStripWhitespace(boolean bl) {
        this.m_shouldStripWS = bl;
        BoolStack boolStack = this.m_shouldStripWhitespaceStack;
        if (boolStack != null) {
            boolStack.setTop(bl);
        }
    }

    @Override
    public boolean supportsPreStripping() {
        return true;
    }
}

