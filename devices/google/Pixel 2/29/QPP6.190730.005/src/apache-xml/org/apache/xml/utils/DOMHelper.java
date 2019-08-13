/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.Hashtable;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.ref.DTMNodeProxy;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.NSInfo;
import org.apache.xml.utils.StringBufferPool;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class DOMHelper {
    protected static final NSInfo m_NSInfoNullNoAncestorXMLNS;
    protected static final NSInfo m_NSInfoNullWithXMLNS;
    protected static final NSInfo m_NSInfoNullWithoutXMLNS;
    protected static final NSInfo m_NSInfoUnProcNoAncestorXMLNS;
    protected static final NSInfo m_NSInfoUnProcWithXMLNS;
    protected static final NSInfo m_NSInfoUnProcWithoutXMLNS;
    protected Document m_DOMFactory = null;
    Hashtable m_NSInfos = new Hashtable();
    protected Vector m_candidateNoAncestorXMLNS = new Vector();

    static {
        m_NSInfoUnProcWithXMLNS = new NSInfo(false, true);
        m_NSInfoUnProcWithoutXMLNS = new NSInfo(false, false);
        m_NSInfoUnProcNoAncestorXMLNS = new NSInfo(false, false, 2);
        m_NSInfoNullWithXMLNS = new NSInfo(true, true);
        m_NSInfoNullWithoutXMLNS = new NSInfo(true, false);
        m_NSInfoNullNoAncestorXMLNS = new NSInfo(true, false, 2);
    }

    public static Document createDocument() {
        return DOMHelper.createDocument(false);
    }

    public static Document createDocument(boolean bl) {
        try {
            Object object = DocumentBuilderFactory.newInstance();
            ((DocumentBuilderFactory)object).setNamespaceAware(true);
            object = ((DocumentBuilderFactory)object).newDocumentBuilder().newDocument();
            return object;
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new RuntimeException(XMLMessages.createXMLMessage("ER_CREATEDOCUMENT_NOT_SUPPORTED", null));
        }
    }

    public static String getNodeData(Node object) {
        FastStringBuffer fastStringBuffer = StringBufferPool.get();
        try {
            DOMHelper.getNodeData((Node)object, fastStringBuffer);
            object = fastStringBuffer.length() > 0 ? fastStringBuffer.toString() : "";
        }
        catch (Throwable throwable) {
            StringBufferPool.free(fastStringBuffer);
            throw throwable;
        }
        StringBufferPool.free(fastStringBuffer);
        return object;
    }

    public static void getNodeData(Node node, FastStringBuffer fastStringBuffer) {
        block4 : {
            block1 : {
                block2 : {
                    block3 : {
                        short s = node.getNodeType();
                        if (s == 1) break block1;
                        if (s == 2) break block2;
                        if (s == 3 || s == 4) break block3;
                        if (s != 7 && (s == 9 || s == 11)) break block1;
                        break block4;
                    }
                    fastStringBuffer.append(node.getNodeValue());
                    break block4;
                }
                fastStringBuffer.append(node.getNodeValue());
                break block4;
            }
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                DOMHelper.getNodeData(node, fastStringBuffer);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Node getParentOfNode(Node node) throws RuntimeException {
        if (2 != node.getNodeType()) return node.getParentNode();
        Document document = node.getOwnerDocument();
        Object object = document.getImplementation();
        if (object != null && object.hasFeature("Core", "2.0")) {
            return ((Attr)node).getOwnerElement();
        }
        object = document.getDocumentElement();
        if (object == null) throw new RuntimeException(XMLMessages.createXMLMessage("ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", null));
        return DOMHelper.locateAttrParent((Element)object, node);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isNodeAfter(Node node, Node node2) {
        Node node3;
        boolean bl;
        boolean bl2 = true;
        if (node == node2) return true;
        if (DOMHelper.isNodeTheSame(node, node2)) return true;
        boolean bl3 = true;
        Node node4 = DOMHelper.getParentOfNode(node);
        if (node4 != (node3 = DOMHelper.getParentOfNode(node2)) && !DOMHelper.isNodeTheSame(node4, node3)) {
            Node node5;
            int n;
            int n2 = 2;
            int n3 = 2;
            do {
                node5 = node3;
                n = n3;
                if (node4 == null) break;
                ++n2;
                node4 = DOMHelper.getParentOfNode(node4);
            } while (true);
            while (node5 != null) {
                ++n;
                node5 = DOMHelper.getParentOfNode(node5);
            }
            node5 = node;
            node4 = node2;
            if (n2 < n) {
                node2 = node4;
                for (n3 = 0; n3 < n - n2; ++n3) {
                    node2 = DOMHelper.getParentOfNode(node2);
                }
                node = node5;
            } else {
                node = node5;
                node2 = node4;
                if (n2 > n) {
                    n3 = 0;
                    do {
                        node = node5;
                        node2 = node4;
                        if (n3 >= n2 - n) break;
                        node5 = DOMHelper.getParentOfNode(node5);
                        ++n3;
                    } while (true);
                }
            }
            node4 = null;
            node5 = null;
            do {
                bl = bl3;
                if (node == null) return bl;
                if (node == node2 || DOMHelper.isNodeTheSame(node, node2)) break;
                node4 = node;
                node = DOMHelper.getParentOfNode(node);
                node5 = node2;
                node2 = DOMHelper.getParentOfNode(node2);
            } while (true);
            if (node4 != null) return DOMHelper.isNodeAfterSibling(node, node4, node5);
            if (n2 >= n) return false;
            return bl2;
        }
        bl = bl3;
        if (node4 == null) return bl;
        return DOMHelper.isNodeAfterSibling(node4, node, node2);
    }

    private static boolean isNodeAfterSibling(Node object, Node node, Node node2) {
        boolean bl;
        block9 : {
            int n;
            short s;
            boolean bl2;
            block12 : {
                boolean bl3;
                block11 : {
                    block10 : {
                        bl2 = false;
                        bl3 = false;
                        n = node.getNodeType();
                        s = node2.getNodeType();
                        if (2 == n || 2 != s) break block10;
                        bl = false;
                        break block9;
                    }
                    if (2 != n || 2 == s) break block11;
                    bl = true;
                    break block9;
                }
                if (2 != n) break block12;
                object = object.getAttributes();
                int n2 = object.getLength();
                s = 0;
                boolean bl4 = false;
                n = 0;
                do {
                    short s2;
                    block15 : {
                        block13 : {
                            block14 : {
                                bl = bl3;
                                if (n >= n2) break block9;
                                Node node3 = object.item(n);
                                if (node == node3 || DOMHelper.isNodeTheSame(node, node3)) break block13;
                                if (node2 == node3) break block14;
                                s2 = s;
                                if (!DOMHelper.isNodeTheSame(node2, node3)) break block15;
                            }
                            if (s != 0) {
                                bl = true;
                                break block9;
                            }
                            bl4 = true;
                            s2 = s;
                            break block15;
                        }
                        if (bl4) {
                            bl = false;
                            break block9;
                        }
                        s2 = 1;
                    }
                    ++n;
                    s = s2;
                } while (true);
            }
            object = object.getFirstChild();
            s = 0;
            n = 0;
            do {
                short s3;
                block18 : {
                    block16 : {
                        block17 : {
                            bl = bl2;
                            if (object == null) break;
                            if (node == object || DOMHelper.isNodeTheSame(node, (Node)object)) break block16;
                            if (node2 == object) break block17;
                            s3 = s;
                            if (!DOMHelper.isNodeTheSame(node2, (Node)object)) break block18;
                        }
                        if (s != 0) {
                            bl = true;
                            break;
                        }
                        n = 1;
                        s3 = s;
                        break block18;
                    }
                    if (n != 0) {
                        bl = false;
                        break;
                    }
                    s3 = 1;
                }
                object = object.getNextSibling();
                s = s3;
            } while (true);
        }
        return bl;
    }

    public static boolean isNodeTheSame(Node node, Node node2) {
        if (node instanceof DTMNodeProxy && node2 instanceof DTMNodeProxy) {
            return ((DTMNodeProxy)node).equals((DTMNodeProxy)node2);
        }
        boolean bl = node == node2;
        return bl;
    }

    private static Node locateAttrParent(Element node, Node node2) {
        Element element = null;
        if (node.getAttributeNode(node2.getNodeName()) == node2) {
            element = node;
        }
        Node node3 = element;
        if (element == null) {
            node = node.getFirstChild();
            do {
                node3 = element;
                if (node == null) break;
                if (1 == node.getNodeType()) {
                    element = node3 = DOMHelper.locateAttrParent((Element)node, node2);
                    if (node3 != null) break;
                }
                node = node.getNextSibling();
            } while (true);
        }
        return node3;
    }

    public Document getDOMFactory() {
        if (this.m_DOMFactory == null) {
            this.m_DOMFactory = DOMHelper.createDocument();
        }
        return this.m_DOMFactory;
    }

    public Element getElementByID(String string, Document document) {
        return null;
    }

    public String getExpandedAttributeName(Attr object) {
        String string = this.getNamespaceOfNode((Node)object);
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(":");
            stringBuilder.append(this.getLocalNameOfNode((Node)object));
            object = stringBuilder.toString();
        } else {
            object = this.getLocalNameOfNode((Node)object);
        }
        return object;
    }

    public String getExpandedElementName(Element object) {
        String string = this.getNamespaceOfNode((Node)object);
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(":");
            stringBuilder.append(this.getLocalNameOfNode((Node)object));
            object = stringBuilder.toString();
        } else {
            object = this.getLocalNameOfNode((Node)object);
        }
        return object;
    }

    public short getLevel(Node node) {
        short s;
        short s2 = s = 1;
        do {
            Node node2;
            node = node2 = DOMHelper.getParentOfNode(node);
            if (node2 == null) break;
            s2 = s = (short)(s2 + 1);
        } while (true);
        return s2;
    }

    public String getLocalNameOfNode(Node object) {
        int n = ((String)(object = object.getNodeName())).indexOf(58);
        if (n >= 0) {
            object = ((String)object).substring(n + 1);
        }
        return object;
    }

    public String getNamespaceForPrefix(String object, Element object2) {
        Node node = object2;
        Object var4_4 = null;
        if (((String)object).equals("xml")) {
            object = "http://www.w3.org/XML/1998/namespace";
        } else {
            object2 = "xmlns";
            if (((String)object).equals("xmlns")) {
                object = "http://www.w3.org/2000/xmlns/";
            } else {
                if (object != "") {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("xmlns:");
                    ((StringBuilder)object2).append((String)object);
                    object2 = ((StringBuilder)object2).toString();
                }
                do {
                    object = var4_4;
                    if (node == null) break;
                    object = var4_4;
                    if (false) break;
                    short s = node.getNodeType();
                    if (s != 1) {
                        object = var4_4;
                        if (s != 5) break;
                    }
                    if (s == 1 && (object = node.getAttributeNode((String)object2)) != null) {
                        object = object.getNodeValue();
                        break;
                    }
                    node = DOMHelper.getParentOfNode(node);
                } while (true);
            }
        }
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getNamespaceOfNode(Node node) {
        int n;
        void var3_18;
        short s;
        boolean bl;
        String string;
        block30 : {
            int n2;
            int n3;
            void var4_24;
            String string2;
            boolean bl2;
            block29 : {
                boolean bl3;
                s = node.getNodeType();
                if (2 != s) {
                    void var4_21;
                    Object v = this.m_NSInfos.get(node);
                    if (v == null) {
                        Object var4_19 = null;
                    } else {
                        NSInfo nSInfo = (NSInfo)v;
                    }
                    bl3 = var4_21 == null ? false : var4_21.m_hasProcessedNS;
                } else {
                    bl3 = false;
                    Object var4_22 = null;
                }
                if (bl3) {
                    void var4_23;
                    return var4_23.m_namespace;
                }
                string = null;
                String string3 = node.getNodeName();
                n = string3.indexOf(58);
                if (2 == s) {
                    if (n <= 0) return null;
                    string2 = string3.substring(0, n);
                } else {
                    string2 = n >= 0 ? string3.substring(0, n) : "";
                }
                n2 = 0;
                n = 0;
                bl2 = false;
                bl = false;
                if (!string2.equals("xml")) break block29;
                String string4 = "http://www.w3.org/XML/1998/namespace";
                n = n2;
                bl = bl2;
                break block30;
            }
            Node node2 = node;
            while (node2 != null && string == null && (var4_24 == null || var4_24.m_ancestorHasXMLNSAttrs != 2)) {
                Object object;
                void var3_12;
                short s2;
                block32 : {
                    block28 : {
                        void var3_6;
                        block33 : {
                            block31 : {
                                s2 = node2.getNodeType();
                                if (var4_24 == null || var4_24.m_hasXMLNSAttrs) break block31;
                                object = var3_6;
                                String string5 = string;
                                string = object;
                                break block32;
                            }
                            n2 = 0;
                            if (s2 != 1) break block33;
                            object = node2.getAttributes();
                            n2 = 0;
                            for (n3 = 0; n3 < object.getLength(); ++n3) {
                                int n4;
                                int n5;
                                block34 : {
                                    Node node3;
                                    String string6;
                                    boolean bl4;
                                    block35 : {
                                        node3 = object.item(n3);
                                        string6 = node3.getNodeName();
                                        n5 = n2;
                                        n4 = n;
                                        bl2 = bl;
                                        if (string6.charAt(0) != 'x') break block34;
                                        bl4 = string6.startsWith("xmlns:");
                                        if (string6.equals("xmlns")) break block35;
                                        n5 = n2;
                                        n4 = n;
                                        bl2 = bl;
                                        if (!bl4) break block34;
                                    }
                                    if (node == node2) {
                                        bl = true;
                                    }
                                    n = 1;
                                    string6 = bl4 ? string6.substring(6) : "";
                                    if (string6.equals(string2)) {
                                        object = node3.getNodeValue();
                                        n2 = 1;
                                        string = var3_6;
                                        Object object2 = object;
                                        break block28;
                                    }
                                    n4 = 1;
                                    n5 = 1;
                                    bl2 = bl;
                                }
                                n2 = n5;
                                n = n4;
                                bl = bl2;
                            }
                            object = string;
                            string = var3_6;
                            Object object3 = object;
                            break block28;
                        }
                        object = string;
                        string = var3_6;
                        Object object4 = object;
                    }
                    if (2 != s2 && var4_24 == null && node != node2) {
                        void var4_27;
                        if (n2 != 0) {
                            NSInfo nSInfo = m_NSInfoUnProcWithXMLNS;
                        } else {
                            NSInfo nSInfo = m_NSInfoUnProcWithoutXMLNS;
                        }
                        this.m_NSInfos.put(node2, var4_27);
                    }
                }
                if (2 == s2) {
                    node2 = DOMHelper.getParentOfNode(node2);
                } else {
                    this.m_candidateNoAncestorXMLNS.addElement(node2);
                    this.m_candidateNoAncestorXMLNS.addElement(var4_24);
                    node2 = node2.getParentNode();
                }
                if (node2 != null) {
                    Object v = this.m_NSInfos.get(node2);
                    if (v == null) {
                        Object var4_29 = null;
                    } else {
                        NSInfo nSInfo = (NSInfo)v;
                    }
                    object = string;
                    string = var3_12;
                    Object object5 = object;
                    continue;
                }
                object = string;
                string = var3_12;
                Object object6 = object;
            }
            n3 = this.m_candidateNoAncestorXMLNS.size();
            if (n3 > 0) {
                if (n == 0 && node2 == null) {
                    for (n2 = 0; n2 < n3; n2 += 2) {
                        Object e = this.m_candidateNoAncestorXMLNS.elementAt(n2 + 1);
                        if (e == m_NSInfoUnProcWithoutXMLNS) {
                            this.m_NSInfos.put(this.m_candidateNoAncestorXMLNS.elementAt(n2), m_NSInfoUnProcNoAncestorXMLNS);
                            continue;
                        }
                        if (e != m_NSInfoNullWithoutXMLNS) continue;
                        this.m_NSInfos.put(this.m_candidateNoAncestorXMLNS.elementAt(n2), m_NSInfoNullNoAncestorXMLNS);
                    }
                }
                this.m_candidateNoAncestorXMLNS.removeAllElements();
            }
            String string7 = string;
        }
        string = var3_18;
        if (2 == s) return string;
        if (var3_18 == null) {
            if (n != 0) {
                if (bl) {
                    this.m_NSInfos.put(node, m_NSInfoNullWithXMLNS);
                    return var3_18;
                }
                this.m_NSInfos.put(node, m_NSInfoNullWithoutXMLNS);
                return var3_18;
            }
            this.m_NSInfos.put(node, m_NSInfoNullNoAncestorXMLNS);
            return var3_18;
        }
        this.m_NSInfos.put(node, new NSInfo((String)var3_18, bl));
        return var3_18;
    }

    public Node getRoot(Node node) {
        Node node2 = null;
        while (node != null) {
            node2 = node;
            node = DOMHelper.getParentOfNode(node);
        }
        return node2;
    }

    public Node getRootNode(Node node) {
        block0 : {
            short s = node.getNodeType();
            if (9 == s || 11 == s) break block0;
            node = node.getOwnerDocument();
        }
        return node;
    }

    public String getUniqueID(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("N");
        stringBuilder.append(Integer.toHexString(node.hashCode()).toUpperCase());
        return stringBuilder.toString();
    }

    public String getUnparsedEntityURI(String string, Document object) {
        String string2 = "";
        Node node = object.getDoctype();
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
        return object;
    }

    public boolean isIgnorableWhitespace(Text text) {
        return false;
    }

    public boolean isNamespaceNode(Node object) {
        short s = object.getNodeType();
        boolean bl = false;
        if (2 == s) {
            if (((String)(object = object.getNodeName())).startsWith("xmlns:") || ((String)object).equals("xmlns")) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public void setDOMFactory(Document document) {
        this.m_DOMFactory = document;
    }

    public boolean shouldStripSourceNode(Node node) throws TransformerException {
        return false;
    }
}

