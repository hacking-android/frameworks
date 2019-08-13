/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref.dom2dtm;

import java.util.Vector;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.dom.DOMSource;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
import org.apache.xml.dtm.ref.DTMManagerDefault;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.dtm.ref.IncrementalSAXSource;
import org.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.StringBufferPool;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.TreeWalker;
import org.apache.xml.utils.XMLCharacterRecognizer;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class DOM2DTM
extends DTMDefaultBaseIterators {
    static final boolean JJK_DEBUG = false;
    static final boolean JJK_NEWCODE = true;
    static final String NAMESPACE_DECL_NS = "http://www.w3.org/XML/1998/namespace";
    private int m_last_kid = -1;
    private int m_last_parent = 0;
    protected Vector m_nodes = new Vector();
    private transient boolean m_nodesAreProcessed;
    private transient Node m_pos;
    boolean m_processedFirstElement = false;
    private transient Node m_root;
    TreeWalker m_walker = new TreeWalker(null);

    public DOM2DTM(DTMManager object, DOMSource dOMSource, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        super((DTMManager)object, dOMSource, n, dTMWSFilter, xMLStringFactory, bl);
        this.m_root = object = dOMSource.getNode();
        this.m_pos = object;
        this.m_last_kid = -1;
        this.m_last_parent = -1;
        this.m_last_kid = this.addNode(this.m_root, this.m_last_parent, this.m_last_kid, -1);
        if (1 == this.m_root.getNodeType() && (n = (object = this.m_root.getAttributes()) == null ? 0 : object.getLength()) > 0) {
            int n2 = -1;
            for (int i = 0; i < n; ++i) {
                n2 = this.addNode(object.item(i), 0, n2, -1);
                this.m_firstch.setElementAt(-1, n2);
            }
            this.m_nextsib.setElementAt(-1, n2);
        }
        this.m_nodesAreProcessed = false;
    }

    protected static void dispatchNodeData(Node node, ContentHandler contentHandler, int n) throws SAXException {
        block6 : {
            block3 : {
                block4 : {
                    block5 : {
                        short s = node.getNodeType();
                        if (s == 1) break block3;
                        if (s == 2 || s == 3 || s == 4) break block4;
                        if (s == 7 || s == 8) break block5;
                        if (s == 9 || s == 11) break block3;
                        break block6;
                    }
                    if (n != 0) break block6;
                }
                String string = node.getNodeValue();
                if (contentHandler instanceof CharacterNodeHandler) {
                    ((CharacterNodeHandler)((Object)contentHandler)).characters(node);
                } else {
                    contentHandler.characters(string.toCharArray(), 0, string.length());
                }
                break block6;
            }
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                DOM2DTM.dispatchNodeData(node, contentHandler, n + 1);
            }
        }
    }

    private int getHandleFromNode(Node node) {
        if (node != null) {
            int n = this.m_nodes.size();
            int n2 = 0;
            do {
                int n3;
                if (n2 < n) {
                    if (this.m_nodes.elementAt(n2) == node) {
                        return this.makeNodeHandle(n2);
                    }
                    ++n2;
                    continue;
                }
                boolean bl = this.nextNode();
                n = n3 = this.m_nodes.size();
                if (bl) continue;
                n = n3;
                if (n2 >= n3) break;
            } while (true);
        }
        return -1;
    }

    protected static void getNodeData(Node node, FastStringBuffer fastStringBuffer) {
        block3 : {
            block1 : {
                block2 : {
                    short s = node.getNodeType();
                    if (s == 1) break block1;
                    if (s == 2 || s == 3 || s == 4) break block2;
                    if (s != 7 && (s == 9 || s == 11)) break block1;
                    break block3;
                }
                fastStringBuffer.append(node.getNodeValue());
                break block3;
            }
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                DOM2DTM.getNodeData(node, fastStringBuffer);
            }
        }
    }

    private static boolean isSpace(char c) {
        return XMLCharacterRecognizer.isWhiteSpace(c);
    }

    private Node logicalNextDOMTextNode(Node node) {
        Node node2;
        Node node3 = node2 = node.getNextSibling();
        if (node2 == null) {
            Node node4 = node.getParentNode();
            node = node2;
            do {
                node3 = node;
                if (node4 == null) break;
                node3 = node;
                if (5 != node4.getNodeType()) break;
                node = node4.getNextSibling();
                if (node != null) {
                    node3 = node;
                    break;
                }
                node4 = node4.getParentNode();
            } while (true);
        }
        node = node3;
        while (node != null && 5 == node.getNodeType()) {
            if (node.hasChildNodes()) {
                node = node.getFirstChild();
                continue;
            }
            node = node.getNextSibling();
        }
        node3 = node;
        if (node != null) {
            short s = node.getNodeType();
            node3 = node;
            if (3 != s) {
                node3 = node;
                if (4 != s) {
                    node3 = null;
                }
            }
        }
        return node3;
    }

    protected int addNode(Node node, int n, int n2, int n3) {
        int n4;
        Object object;
        int n5;
        Object object2;
        String string;
        block19 : {
            block18 : {
                block16 : {
                    block17 : {
                        n5 = this.m_nodes.size();
                        if (this.m_dtmIdent.size() == n5 >>> 16) {
                            try {
                                if (this.m_mgr == null) {
                                    ClassCastException classCastException = new ClassCastException();
                                    throw classCastException;
                                }
                                object2 = (DTMManagerDefault)this.m_mgr;
                                n4 = ((DTMManagerDefault)object2).getFirstFreeDTMID();
                                ((DTMManagerDefault)object2).addDTM(this, n4, n5);
                                this.m_dtmIdent.addElement(n4 << 16);
                            }
                            catch (ClassCastException classCastException) {
                                this.error(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
                            }
                        }
                        ++this.m_size;
                        if (-1 == n3) {
                            n3 = node.getNodeType();
                        }
                        n4 = n3;
                        if (2 != n3) break block16;
                        object2 = node.getNodeName();
                        if (((String)object2).startsWith("xmlns:")) break block17;
                        n4 = n3;
                        if (!((String)object2).equals("xmlns")) break block16;
                    }
                    n4 = 13;
                }
                this.m_nodes.addElement(node);
                this.m_firstch.setElementAt(-2, n5);
                this.m_nextsib.setElementAt(-2, n5);
                this.m_prevsib.setElementAt(n2, n5);
                this.m_parent.setElementAt(n, n5);
                if (-1 != n && n4 != 2 && n4 != 13 && -2 == this.m_firstch.elementAt(n)) {
                    this.m_firstch.setElementAt(n5, n);
                }
                string = node.getNamespaceURI();
                object2 = n4 == 7 ? node.getNodeName() : node.getLocalName();
                if (n4 == 1) break block18;
                object = object2;
                if (n4 != 2) break block19;
            }
            object = object2;
            if (object2 == null) {
                object = node.getNodeName();
            }
        }
        object2 = this.m_expandedNameTable;
        if (node.getLocalName() == null) {
            // empty if block
        }
        n3 = object != null ? ((ExpandedNameTable)object2).getExpandedTypeID(string, (String)object, n4) : ((ExpandedNameTable)object2).getExpandedTypeID(n4);
        this.m_exptype.setElementAt(n3, n5);
        this.indexNode(n3, n5);
        if (-1 != n2) {
            this.m_nextsib.setElementAt(n5, n2);
        }
        if (n4 == 13) {
            this.declareNamespaceInContext(n, n5);
        }
        return n5;
    }

    @Override
    public void dispatchCharactersEvents(int n, ContentHandler contentHandler, boolean bl) throws SAXException {
        block4 : {
            Node node;
            Node node2;
            block5 : {
                block3 : {
                    if (!bl) break block3;
                    this.getStringValue(n).fixWhiteSpace(true, true, false).dispatchCharactersEvents(contentHandler);
                    break block4;
                }
                short s = this.getNodeType(n);
                node = this.getNode(n);
                DOM2DTM.dispatchNodeData(node, contentHandler, 0);
                node2 = node;
                if (3 == s) break block5;
                if (4 != s) break block4;
                node2 = node;
            }
            do {
                node2 = node = this.logicalNextDOMTextNode(node2);
                if (node == null) break;
                DOM2DTM.dispatchNodeData(node2, contentHandler, 0);
            } while (true);
        }
    }

    @Override
    public void dispatchToEvents(int n, ContentHandler contentHandler) throws SAXException {
        TreeWalker treeWalker;
        TreeWalker treeWalker2 = treeWalker = this.m_walker;
        if (treeWalker.getContentHandler() != null) {
            treeWalker2 = new TreeWalker(null);
        }
        treeWalker2.setContentHandler(contentHandler);
        try {
            treeWalker2.traverseFragment(this.getNode(n));
            return;
        }
        finally {
            treeWalker2.setContentHandler(null);
        }
    }

    @Override
    public int getAttributeNode(int n, String string, String string2) {
        block4 : {
            String string3 = string;
            if (string == null) {
                string3 = "";
            }
            if (1 == this.getNodeType(n)) {
                String string4;
                n = this.makeNodeIdentity(n);
                do {
                    int n2;
                    n = n2 = this.getNextNodeIdentity(n);
                    if (-1 == n2 || (n2 = (int)this._type(n)) != 2 && n2 != 13) break block4;
                    Node node = this.lookupNode(n);
                    string = string4 = node.getNamespaceURI();
                    if (string4 == null) {
                        string = "";
                    }
                    string4 = node.getLocalName();
                } while (!string.equals(string3) || !string2.equals(string4));
                return this.makeNodeHandle(n);
            }
        }
        return -1;
    }

    @Override
    public ContentHandler getContentHandler() {
        return null;
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
    public String getDocumentTypeDeclarationPublicIdentifier() {
        Node node = this.m_root.getNodeType() == 9 ? (Document)this.m_root : this.m_root.getOwnerDocument();
        if (node != null && (node = node.getDoctype()) != null) {
            return node.getPublicId();
        }
        return null;
    }

    @Override
    public String getDocumentTypeDeclarationSystemIdentifier() {
        Node node = this.m_root.getNodeType() == 9 ? (Document)this.m_root : this.m_root.getOwnerDocument();
        if (node != null && (node = node.getDoctype()) != null) {
            return node.getSystemId();
        }
        return null;
    }

    @Override
    public int getElementById(String object) {
        Document document = this.m_root.getNodeType() == 9 ? (Document)this.m_root : this.m_root.getOwnerDocument();
        if (document != null && (object = document.getElementById((String)object)) != null) {
            int n;
            block3 : {
                int n2;
                n = n2 = this.getHandleFromNode((Node)object);
                if (-1 == n2) {
                    int n3 = this.m_nodes.size() - 1;
                    do {
                        int n4;
                        n3 = n4 = this.getNextNodeIdentity(n3);
                        n = n2;
                        if (-1 == n4) break block3;
                    } while (this.getNode(n3) != object);
                    n = this.getHandleFromNode((Node)object);
                }
            }
            return n;
        }
        return -1;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    public int getHandleOfNode(Node node) {
        Node node2;
        if (node != null && ((node2 = this.m_root) == node || node2.getNodeType() == 9 && this.m_root == node.getOwnerDocument() || this.m_root.getNodeType() != 9 && this.m_root.getOwnerDocument() == node.getOwnerDocument())) {
            node2 = node;
            while (node2 != null) {
                if (node2 == this.m_root) {
                    return this.getHandleFromNode(node);
                }
                if (node2.getNodeType() != 2) {
                    node2 = node2.getParentNode();
                    continue;
                }
                node2 = ((Attr)node2).getOwnerElement();
            }
        }
        return -1;
    }

    @Override
    public LexicalHandler getLexicalHandler() {
        return null;
    }

    @Override
    public String getLocalName(int n) {
        String string;
        if (-1 == (n = this.makeNodeIdentity(n))) {
            return null;
        }
        Node node = (Node)this.m_nodes.elementAt(n);
        String string2 = string = node.getLocalName();
        if (string == null) {
            string2 = node.getNodeName();
            if ('#' == string2.charAt(0)) {
                string2 = "";
            } else {
                n = string2.indexOf(58);
                if (n >= 0) {
                    string2 = string2.substring(n + 1);
                }
            }
        }
        return string2;
    }

    @Override
    public String getNamespaceURI(int n) {
        if ((n = this.makeNodeIdentity(n)) == -1) {
            return null;
        }
        return ((Node)this.m_nodes.elementAt(n)).getNamespaceURI();
    }

    @Override
    protected int getNextNodeIdentity(int n) {
        int n2;
        n = n2 = n + 1;
        if (n2 >= this.m_nodes.size()) {
            n = n2;
            if (!this.nextNode()) {
                n = -1;
            }
        }
        return n;
    }

    @Override
    public Node getNode(int n) {
        n = this.makeNodeIdentity(n);
        return (Node)this.m_nodes.elementAt(n);
    }

    @Override
    public String getNodeName(int n) {
        return this.getNode(n).getNodeName();
    }

    @Override
    public String getNodeNameX(int n) {
        String string;
        short s = this.getNodeType(n);
        if (s != 1 && s != 2 && s != 5 && s != 7) {
            if (s != 13) {
                string = "";
            } else {
                string = this.getNode(n).getNodeName();
                if (string.startsWith("xmlns:")) {
                    string = QName.getLocalPart(string);
                } else if (string.equals("xmlns")) {
                    string = "";
                }
            }
        } else {
            string = this.getNode(n).getNodeName();
        }
        return string;
    }

    @Override
    public String getNodeValue(int n) {
        int n2 = this._exptype(this.makeNodeIdentity(n));
        int n3 = -1;
        if (-1 != n2) {
            n3 = this.getNodeType(n);
        }
        if (3 != n3 && 4 != n3) {
            return this.getNode(n).getNodeValue();
        }
        Node node = this.getNode(n);
        Object object = this.logicalNextDOMTextNode(node);
        if (object == null) {
            return node.getNodeValue();
        }
        FastStringBuffer fastStringBuffer = StringBufferPool.get();
        fastStringBuffer.append(node.getNodeValue());
        while (object != null) {
            fastStringBuffer.append(object.getNodeValue());
            object = this.logicalNextDOMTextNode((Node)object);
        }
        object = fastStringBuffer.length() > 0 ? fastStringBuffer.toString() : "";
        StringBufferPool.free(fastStringBuffer);
        return object;
    }

    @Override
    public int getNumberOfNodes() {
        return this.m_nodes.size();
    }

    @Override
    public String getPrefix(int n) {
        short s = this.getNodeType(n);
        String string = "";
        if (s != 1 && s != 2) {
            if (s != 13) {
                string = "";
            } else {
                String string2 = this.getNode(n).getNodeName();
                n = string2.indexOf(58);
                if (n >= 0) {
                    string = string2.substring(n + 1);
                }
            }
        } else {
            String string3 = this.getNode(n).getNodeName();
            n = string3.indexOf(58);
            if (n >= 0) {
                string = string3.substring(0, n);
            }
        }
        return string;
    }

    @Override
    public SourceLocator getSourceLocatorFor(int n) {
        return null;
    }

    @Override
    public XMLString getStringValue(int n) {
        short s = this.getNodeType(n);
        Node node = this.getNode(n);
        String string = "";
        if (1 != s && 9 != s && 11 != s) {
            if (3 != s && 4 != s) {
                return this.m_xstrf.newstr(node.getNodeValue());
            }
            FastStringBuffer fastStringBuffer = StringBufferPool.get();
            while (node != null) {
                fastStringBuffer.append(node.getNodeValue());
                node = this.logicalNextDOMTextNode(node);
            }
            if (fastStringBuffer.length() > 0) {
                string = fastStringBuffer.toString();
            }
            StringBufferPool.free(fastStringBuffer);
            return this.m_xstrf.newstr(string);
        }
        FastStringBuffer fastStringBuffer = StringBufferPool.get();
        try {
            DOM2DTM.getNodeData(node, fastStringBuffer);
            if (fastStringBuffer.length() > 0) {
                string = fastStringBuffer.toString();
            }
            return this.m_xstrf.newstr(string);
        }
        finally {
            StringBufferPool.free(fastStringBuffer);
        }
    }

    @Override
    public String getUnparsedEntityURI(String string) {
        String string2 = "";
        Node node = this.m_root.getNodeType() == 9 ? (Document)this.m_root : this.m_root.getOwnerDocument();
        Object object = string2;
        if (node != null) {
            node = node.getDoctype();
            object = string2;
            if (node != null) {
                object = node.getEntities();
                if (object == null) {
                    return "";
                }
                node = (Entity)object.getNamedItem(string);
                if (node == null) {
                    return "";
                }
                object = string2;
                if (node.getNotationName() != null) {
                    string = node.getSystemId();
                    object = string;
                    if (string == null) {
                        object = node.getPublicId();
                    }
                }
            }
        }
        return object;
    }

    @Override
    public boolean isAttributeSpecified(int n) {
        if (2 == this.getNodeType(n)) {
            return ((Attr)this.getNode(n)).getSpecified();
        }
        return false;
    }

    public boolean isWhitespace(int n) {
        short s = this.getNodeType(n);
        Node node = this.getNode(n);
        if (3 != s && 4 != s) {
            return false;
        }
        FastStringBuffer fastStringBuffer = StringBufferPool.get();
        while (node != null) {
            fastStringBuffer.append(node.getNodeValue());
            node = this.logicalNextDOMTextNode(node);
        }
        boolean bl = fastStringBuffer.isWhitespace(0, fastStringBuffer.length());
        StringBufferPool.free(fastStringBuffer);
        return bl;
    }

    protected Node lookupNode(int n) {
        return (Node)this.m_nodes.elementAt(n);
    }

    @Override
    public boolean needsTwoThreads() {
        return false;
    }

    @Override
    protected boolean nextNode() {
        boolean bl = this.m_nodesAreProcessed;
        int n = 0;
        if (bl) {
            return false;
        }
        Object object = this.m_pos;
        Object object2 = null;
        int n2 = -1;
        do {
            Object object3;
            int n3;
            Object object4;
            if (object.hasChildNodes()) {
                object4 = object2 = object.getFirstChild();
                if (object2 != null) {
                    object4 = object2;
                    if (10 == object2.getNodeType()) {
                        object4 = object2.getNextSibling();
                    }
                }
                object3 = object;
                object2 = object4;
                if (5 != object.getNodeType()) {
                    this.m_last_parent = this.m_last_kid;
                    this.m_last_kid = -1;
                    object3 = object;
                    object2 = object4;
                    if (this.m_wsfilter != null) {
                        n3 = this.m_wsfilter.getShouldStripSpace(this.makeNodeHandle(this.m_last_parent), this);
                        bl = 3 == n3 ? this.getShouldStripWhitespace() : 2 == n3;
                        this.pushShouldStripWhitespace(bl);
                        object3 = object;
                        object2 = object4;
                    }
                }
            } else {
                object4 = object;
                object3 = object2;
                if (this.m_last_kid != -1) {
                    object4 = object;
                    object3 = object2;
                    if (this.m_firstch.elementAt(this.m_last_kid) == -2) {
                        this.m_firstch.setElementAt(-1, this.m_last_kid);
                        object3 = object2;
                        object4 = object;
                    }
                }
                do {
                    object2 = object3;
                    if (this.m_last_parent == -1) break;
                    object2 = object = object4.getNextSibling();
                    if (object != null) {
                        object2 = object;
                        if (10 == object.getNodeType()) {
                            object2 = object.getNextSibling();
                        }
                    }
                    if (object2 != null) break;
                    if ((object4 = object4.getParentNode()) != null && 5 == object4.getNodeType()) {
                        object3 = object2;
                        continue;
                    }
                    this.popShouldStripWhitespace();
                    if (this.m_last_kid == -1) {
                        this.m_firstch.setElementAt(-1, this.m_last_parent);
                    } else {
                        this.m_nextsib.setElementAt(-1, this.m_last_kid);
                    }
                    object = this.m_parent;
                    this.m_last_kid = n3 = this.m_last_parent;
                    this.m_last_parent = ((SuballocatedIntVector)object).elementAt(n3);
                    object3 = object2;
                } while (true);
                object3 = object4;
                if (this.m_last_parent == -1) {
                    object2 = null;
                    object3 = object4;
                }
            }
            if (object2 != null) {
                n2 = object2.getNodeType();
            }
            if (5 == n2) {
                object3 = object2;
            }
            if (5 != n2) {
                int n4;
                if (object2 == null) {
                    this.m_nextsib.setElementAt(-1, 0);
                    this.m_nodesAreProcessed = true;
                    this.m_pos = null;
                    return false;
                }
                bl = false;
                Object var9_9 = null;
                object4 = null;
                n2 = object2.getNodeType();
                if (3 != n2 && 4 != n2) {
                    n4 = n2;
                    object = var9_9;
                    if (7 == n2) {
                        bl = object3.getNodeName().toLowerCase().equals("xml");
                        n4 = n2;
                        object = var9_9;
                    }
                } else {
                    bl = this.m_wsfilter != null && this.getShouldStripWhitespace();
                    object = object2;
                    while (object != null) {
                        object4 = object;
                        if (3 == object.getNodeType()) {
                            n2 = 3;
                        }
                        bl &= XMLCharacterRecognizer.isWhiteSpace(object.getNodeValue());
                        object = this.logicalNextDOMTextNode((Node)object);
                    }
                    object = object4;
                    n4 = n2;
                }
                if (!bl) {
                    int n5;
                    this.m_last_kid = n5 = this.addNode((Node)object2, this.m_last_parent, this.m_last_kid, n4);
                    if (1 == n4) {
                        n3 = -1;
                        object4 = object2.getAttributes();
                        if (object4 != null) {
                            n = object4.getLength();
                        }
                        n2 = n3;
                        if (n > 0) {
                            int n6 = 0;
                            do {
                                n2 = n3;
                                if (n6 >= n) break;
                                n3 = this.addNode(object4.item(n6), n5, n3, -1);
                                this.m_firstch.setElementAt(-1, n3);
                                if (!this.m_processedFirstElement && "xmlns:xml".equals(object4.item(n6).getNodeName())) {
                                    this.m_processedFirstElement = true;
                                }
                                ++n6;
                            } while (true);
                        }
                        n3 = n2;
                        if (!this.m_processedFirstElement) {
                            object4 = (Element)object2;
                            n3 = n2 == -1 ? n5 : n2;
                            n3 = this.addNode(new DOM2DTMdefaultNamespaceDeclarationNode((Element)object4, "xml", NAMESPACE_DECL_NS, this.makeNodeHandle(n3 + 1)), n5, n2, -1);
                            this.m_firstch.setElementAt(-1, n3);
                            this.m_processedFirstElement = true;
                        }
                        if (n3 != -1) {
                            this.m_nextsib.setElementAt(-1, n3);
                        }
                    }
                }
                if (3 == n4 || 4 == n4) {
                    object2 = object;
                }
                this.m_pos = object2;
                return true;
            }
            object = object3;
        } while (true);
    }

    public void setIncrementalSAXSource(IncrementalSAXSource incrementalSAXSource) {
    }

    @Override
    public void setProperty(String string, Object object) {
    }

    public static interface CharacterNodeHandler {
        public void characters(Node var1) throws SAXException;
    }

}

