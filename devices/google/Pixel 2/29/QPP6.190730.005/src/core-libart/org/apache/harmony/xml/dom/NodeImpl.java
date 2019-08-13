/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.harmony.xml.dom.AttrImpl;
import org.apache.harmony.xml.dom.DOMImplementationImpl;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.DocumentTypeImpl;
import org.apache.harmony.xml.dom.ElementImpl;
import org.apache.harmony.xml.dom.NodeListImpl;
import org.apache.harmony.xml.dom.TextImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public abstract class NodeImpl
implements Node {
    private static final NodeList EMPTY_LIST = new NodeListImpl();
    static final TypeInfo NULL_TYPE_INFO = new TypeInfo(){

        @Override
        public String getTypeName() {
            return null;
        }

        @Override
        public String getTypeNamespace() {
            return null;
        }

        @Override
        public boolean isDerivedFrom(String string, String string2, int n) {
            return false;
        }
    };
    DocumentImpl document;

    NodeImpl(DocumentImpl documentImpl) {
        this.document = documentImpl;
    }

    private static List<Object> createEqualityKey(Node node) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(node.getNodeType());
        arrayList.add(node.getNodeName());
        arrayList.add(node.getLocalName());
        arrayList.add(node.getNamespaceURI());
        arrayList.add(node.getPrefix());
        arrayList.add(node.getNodeValue());
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            arrayList.add(node2);
        }
        short s = node.getNodeType();
        if (s != 1) {
            if (s == 10) {
                node = (DocumentTypeImpl)node;
                arrayList.add(((DocumentTypeImpl)node).getPublicId());
                arrayList.add(((DocumentTypeImpl)node).getSystemId());
                arrayList.add(((DocumentTypeImpl)node).getInternalSubset());
                arrayList.add(((DocumentTypeImpl)node).getEntities());
                arrayList.add(((DocumentTypeImpl)node).getNotations());
            }
        } else {
            arrayList.add(((Element)node).getAttributes());
        }
        return arrayList;
    }

    private NodeImpl getContainingElement() {
        for (Node node = this.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() != 1) continue;
            return (NodeImpl)node;
        }
        return null;
    }

    private NodeImpl getNamespacingElement() {
        switch (this.getNodeType()) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported node type ");
                stringBuilder.append(this.getNodeType());
                throw new DOMException(9, stringBuilder.toString());
            }
            case 9: {
                return (NodeImpl)((Object)((Document)((Object)this)).getDocumentElement());
            }
            case 6: 
            case 10: 
            case 11: 
            case 12: {
                return null;
            }
            case 3: 
            case 4: 
            case 5: 
            case 7: 
            case 8: {
                return this.getContainingElement();
            }
            case 2: {
                return (NodeImpl)((Object)((Attr)((Object)this)).getOwnerElement());
            }
            case 1: 
        }
        return this;
    }

    private String getParentBaseUri() {
        Object object = this.getParentNode();
        object = object != null ? object.getBaseURI() : null;
        return object;
    }

    private boolean namedNodeMapsEqual(NamedNodeMap namedNodeMap, NamedNodeMap namedNodeMap2) {
        if (namedNodeMap.getLength() != namedNodeMap2.getLength()) {
            return false;
        }
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Node node = namedNodeMap.item(i);
            Node node2 = node.getLocalName() == null ? namedNodeMap2.getNamedItem(node.getNodeName()) : namedNodeMap2.getNamedItemNS(node.getNamespaceURI(), node.getLocalName());
            if (node2 != null && node.isEqualNode(node2)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private String sanitizeUri(String string) {
        if (string != null && string.length() != 0) {
            try {
                URI uRI = new URI(string);
                string = uRI.toString();
                return string;
            }
            catch (URISyntaxException uRISyntaxException) {
                return null;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void setName(NodeImpl nodeImpl, String charSequence) {
        int n = ((String)charSequence).lastIndexOf(":");
        if (n != -1) {
            String string = ((String)charSequence).substring(0, n);
            String string2 = ((String)charSequence).substring(n + 1);
            if (!DocumentImpl.isXMLIdentifier(string)) throw new DOMException(5, (String)charSequence);
            if (!DocumentImpl.isXMLIdentifier(string2)) throw new DOMException(5, (String)charSequence);
        } else if (!DocumentImpl.isXMLIdentifier((String)charSequence)) throw new DOMException(5, (String)charSequence);
        n = nodeImpl.getNodeType();
        if (n == 1) {
            nodeImpl = (ElementImpl)nodeImpl;
            ((ElementImpl)nodeImpl).namespaceAware = false;
            ((ElementImpl)nodeImpl).localName = charSequence;
            return;
        }
        if (n == 2) {
            nodeImpl = (AttrImpl)nodeImpl;
            ((AttrImpl)nodeImpl).namespaceAware = false;
            ((AttrImpl)nodeImpl).localName = charSequence;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Cannot rename nodes of type ");
        ((StringBuilder)charSequence).append(nodeImpl.getNodeType());
        throw new DOMException(9, ((StringBuilder)charSequence).toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    static void setNameNS(NodeImpl nodeImpl, String charSequence, String string) {
        if (string == null) throw new DOMException(14, string);
        String string2 = null;
        int n = string.lastIndexOf(":");
        String string3 = string;
        if (n != -1) {
            string2 = NodeImpl.validatePrefix(string.substring(0, n), true, (String)charSequence);
            string3 = string.substring(n + 1);
        }
        if (!DocumentImpl.isXMLIdentifier(string3)) throw new DOMException(5, string3);
        n = nodeImpl.getNodeType();
        if (n == 1) {
            nodeImpl = (ElementImpl)nodeImpl;
            ((ElementImpl)nodeImpl).namespaceAware = true;
            ((ElementImpl)nodeImpl).namespaceURI = charSequence;
            ((ElementImpl)nodeImpl).prefix = string2;
            ((ElementImpl)nodeImpl).localName = string3;
            return;
        }
        if (n != 2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Cannot rename nodes of type ");
            ((StringBuilder)charSequence).append(nodeImpl.getNodeType());
            throw new DOMException(9, ((StringBuilder)charSequence).toString());
        }
        if ("xmlns".equals(string3)) {
            if (!"http://www.w3.org/2000/xmlns/".equals(charSequence)) throw new DOMException(14, string3);
        }
        nodeImpl = (AttrImpl)nodeImpl;
        ((AttrImpl)nodeImpl).namespaceAware = true;
        ((AttrImpl)nodeImpl).namespaceURI = charSequence;
        ((AttrImpl)nodeImpl).prefix = string2;
        ((AttrImpl)nodeImpl).localName = string3;
    }

    static String validatePrefix(String string, boolean bl, String string2) {
        if (bl) {
            if (string != null && (string2 == null || !DocumentImpl.isXMLIdentifier(string) || "xml".equals(string) && !"http://www.w3.org/XML/1998/namespace".equals(string2) || "xmlns".equals(string) && !"http://www.w3.org/2000/xmlns/".equals(string2))) {
                throw new DOMException(14, string);
            }
            return string;
        }
        throw new DOMException(14, string);
    }

    @Override
    public Node appendChild(Node node) throws DOMException {
        throw new DOMException(3, null);
    }

    @Override
    public final Node cloneNode(boolean bl) {
        return this.document.cloneOrImportNode((short)1, this, bl);
    }

    @Override
    public short compareDocumentPosition(Node node) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final String getBaseURI() {
        String string;
        String string2;
        URI uRI;
        block13 : {
            block12 : {
                switch (this.getNodeType()) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported node type ");
                        stringBuilder.append(this.getNodeType());
                        throw new DOMException(9, stringBuilder.toString());
                    }
                    case 9: {
                        return this.sanitizeUri(((Document)((Object)this)).getDocumentURI());
                    }
                    case 7: {
                        return this.getParentBaseUri();
                    }
                    case 6: 
                    case 12: {
                        return null;
                    }
                    case 5: {
                        return null;
                    }
                    case 2: 
                    case 3: 
                    case 4: 
                    case 8: 
                    case 10: 
                    case 11: {
                        return null;
                    }
                    case 1: 
                }
                string2 = ((Element)((Object)this)).getAttributeNS("http://www.w3.org/XML/1998/namespace", "base");
                if (string2 == null) return this.getParentBaseUri();
                try {
                    if (string2.isEmpty()) return this.getParentBaseUri();
                    uRI = new URI(string2);
                    if (!uRI.isAbsolute()) break block12;
                    return string2;
                }
                catch (URISyntaxException uRISyntaxException) {
                    return null;
                }
            }
            string = this.getParentBaseUri();
            if (string != null) break block13;
            return null;
        }
        uRI = new URI(string);
        return uRI.resolve(string2).toString();
    }

    @Override
    public NodeList getChildNodes() {
        return EMPTY_LIST;
    }

    @Override
    public final Object getFeature(String object, String string) {
        object = this.isSupported((String)object, string) ? this : null;
        return object;
    }

    @Override
    public Node getFirstChild() {
        return null;
    }

    @Override
    public Node getLastChild() {
        return null;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getNamespaceURI() {
        return null;
    }

    @Override
    public Node getNextSibling() {
        return null;
    }

    @Override
    public String getNodeName() {
        return null;
    }

    @Override
    public abstract short getNodeType();

    @Override
    public String getNodeValue() throws DOMException {
        return null;
    }

    @Override
    public final Document getOwnerDocument() {
        DocumentImpl documentImpl;
        DocumentImpl documentImpl2 = documentImpl = this.document;
        if (documentImpl == this) {
            documentImpl2 = null;
        }
        return documentImpl2;
    }

    @Override
    public Node getParentNode() {
        return null;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        return null;
    }

    @Override
    public String getTextContent() throws DOMException {
        return this.getNodeValue();
    }

    void getTextContent(StringBuilder stringBuilder) throws DOMException {
        String string = this.getNodeValue();
        if (string != null) {
            stringBuilder.append(string);
        }
    }

    @Override
    public final Object getUserData(String object) {
        if (object != null) {
            object = this.document.getUserDataMapForRead(this).get(object);
            object = object != null ? ((UserData)object).value : null;
            return object;
        }
        throw new NullPointerException("key == null");
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public Node insertBefore(Node node, Node node2) throws DOMException {
        throw new DOMException(3, null);
    }

    @Override
    public final boolean isDefaultNamespace(String string) {
        String string2 = this.lookupNamespaceURI(null);
        boolean bl = string == null ? string2 == null : string.equals(string2);
        return bl;
    }

    @Override
    public final boolean isEqualNode(Node object) {
        if (object == this) {
            return true;
        }
        List<Object> list = NodeImpl.createEqualityKey(this);
        object = NodeImpl.createEqualityKey((Node)object);
        if (list.size() != object.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            E e;
            Object object2 = list.get(i);
            if (object2 == (e = object.get(i))) continue;
            if (object2 != null && e != null) {
                if (!(object2 instanceof String) && !(object2 instanceof Short)) {
                    if (object2 instanceof NamedNodeMap) {
                        if (e instanceof NamedNodeMap && this.namedNodeMapsEqual((NamedNodeMap)object2, (NamedNodeMap)e)) continue;
                        return false;
                    }
                    if (object2 instanceof Node) {
                        if (e instanceof Node && ((Node)object2).isEqualNode((Node)e)) continue;
                        return false;
                    }
                    throw new AssertionError();
                }
                if (object2.equals(e)) continue;
                return false;
            }
            return false;
        }
        return true;
    }

    boolean isPrefixMappedToUri(String string, String string2) {
        if (string == null) {
            return false;
        }
        return string2.equals(this.lookupNamespaceURI(string));
    }

    @Override
    public boolean isSameNode(Node node) {
        boolean bl = this == node;
        return bl;
    }

    @Override
    public boolean isSupported(String string, String string2) {
        return DOMImplementationImpl.getInstance().hasFeature(string, string2);
    }

    @Override
    public final String lookupNamespaceURI(String object) {
        Object object2 = this.getNamespacingElement();
        do {
            Object var3_3 = null;
            if (object2 == null) break;
            Object object3 = ((NodeImpl)object2).getPrefix();
            if (((NodeImpl)object2).getNamespaceURI() != null && (object == null ? object3 == null : ((String)object).equals(object3))) {
                return ((NodeImpl)object2).getNamespaceURI();
            }
            if (((NodeImpl)object2).hasAttributes()) {
                NamedNodeMap namedNodeMap = ((NodeImpl)object2).getAttributes();
                int n = namedNodeMap.getLength();
                for (int i = 0; i < n; ++i) {
                    object3 = namedNodeMap.item(i);
                    if (!"http://www.w3.org/2000/xmlns/".equals(object3.getNamespaceURI()) || !(object == null ? "xmlns".equals(object3.getNodeName()) : "xmlns".equals(object3.getPrefix()) && ((String)object).equals(object3.getLocalName()))) continue;
                    object2 = object3.getNodeValue();
                    object = var3_3;
                    if (((String)object2).length() > 0) {
                        object = object2;
                    }
                    return object;
                }
            }
            object2 = ((NodeImpl)object2).getContainingElement();
        } while (true);
        return null;
    }

    @Override
    public final String lookupPrefix(String string) {
        NodeImpl nodeImpl;
        if (string == null) {
            return null;
        }
        for (NodeImpl nodeImpl2 = nodeImpl = this.getNamespacingElement(); nodeImpl2 != null; nodeImpl2 = nodeImpl2.getContainingElement()) {
            if (string.equals(nodeImpl2.getNamespaceURI()) && nodeImpl.isPrefixMappedToUri(nodeImpl2.getPrefix(), string)) {
                return nodeImpl2.getPrefix();
            }
            if (!nodeImpl2.hasAttributes()) continue;
            NamedNodeMap namedNodeMap = nodeImpl2.getAttributes();
            int n = namedNodeMap.getLength();
            for (int i = 0; i < n; ++i) {
                Node node = namedNodeMap.item(i);
                if (!"http://www.w3.org/2000/xmlns/".equals(node.getNamespaceURI()) || !"xmlns".equals(node.getPrefix()) || !string.equals(node.getNodeValue()) || !nodeImpl.isPrefixMappedToUri(node.getLocalName(), string)) continue;
                return node.getLocalName();
            }
        }
        return null;
    }

    @Override
    public void normalize() {
    }

    @Override
    public Node removeChild(Node node) throws DOMException {
        throw new DOMException(3, null);
    }

    @Override
    public Node replaceChild(Node node, Node node2) throws DOMException {
        throw new DOMException(3, null);
    }

    @Override
    public final void setNodeValue(String charSequence) throws DOMException {
        switch (this.getNodeType()) {
            default: {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unsupported node type ");
                ((StringBuilder)charSequence).append(this.getNodeType());
                throw new DOMException(9, ((StringBuilder)charSequence).toString());
            }
            case 7: {
                ((ProcessingInstruction)((Object)this)).setData((String)charSequence);
                return;
            }
            case 3: 
            case 4: 
            case 8: {
                ((CharacterData)((Object)this)).setData((String)charSequence);
                return;
            }
            case 2: {
                ((Attr)((Object)this)).setValue((String)charSequence);
                return;
            }
            case 1: 
            case 5: 
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
        }
    }

    @Override
    public void setPrefix(String string) throws DOMException {
    }

    @Override
    public final void setTextContent(String charSequence) throws DOMException {
        Node node;
        switch (this.getNodeType()) {
            default: {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unsupported node type ");
                ((StringBuilder)charSequence).append(this.getNodeType());
                throw new DOMException(9, ((StringBuilder)charSequence).toString());
            }
            case 9: 
            case 10: {
                return;
            }
            case 2: 
            case 3: 
            case 4: 
            case 7: 
            case 8: 
            case 12: {
                this.setNodeValue((String)charSequence);
                return;
            }
            case 1: 
            case 5: 
            case 6: 
            case 11: 
        }
        while ((node = this.getFirstChild()) != null) {
            this.removeChild(node);
        }
        if (charSequence != null && ((String)charSequence).length() != 0) {
            this.appendChild(this.document.createTextNode((String)charSequence));
        }
    }

    @Override
    public final Object setUserData(String object, Object object2, UserDataHandler userDataHandler) {
        if (object != null) {
            Map<String, UserData> map = this.document.getUserDataMap(this);
            object = object2 == null ? map.remove(object) : map.put((String)object, new UserData(object2, userDataHandler));
            object = object != null ? ((UserData)object).value : null;
            return object;
        }
        throw new NullPointerException("key == null");
    }

    static class UserData {
        final UserDataHandler handler;
        final Object value;

        UserData(Object object, UserDataHandler userDataHandler) {
            this.value = object;
            this.handler = userDataHandler;
        }
    }

}

