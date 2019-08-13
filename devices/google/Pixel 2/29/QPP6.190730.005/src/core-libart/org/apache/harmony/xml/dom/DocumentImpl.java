/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.apache.harmony.xml.dom.AttrImpl;
import org.apache.harmony.xml.dom.CDATASectionImpl;
import org.apache.harmony.xml.dom.CommentImpl;
import org.apache.harmony.xml.dom.DOMConfigurationImpl;
import org.apache.harmony.xml.dom.DOMImplementationImpl;
import org.apache.harmony.xml.dom.DocumentFragmentImpl;
import org.apache.harmony.xml.dom.ElementImpl;
import org.apache.harmony.xml.dom.EntityReferenceImpl;
import org.apache.harmony.xml.dom.InnerNodeImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.apache.harmony.xml.dom.NodeImpl;
import org.apache.harmony.xml.dom.NodeListImpl;
import org.apache.harmony.xml.dom.ProcessingInstructionImpl;
import org.apache.harmony.xml.dom.TextImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public final class DocumentImpl
extends InnerNodeImpl
implements Document {
    private String documentUri;
    private DOMConfigurationImpl domConfiguration;
    private DOMImplementation domImplementation;
    private String inputEncoding;
    private WeakHashMap<NodeImpl, Map<String, NodeImpl.UserData>> nodeToUserData;
    private boolean strictErrorChecking = true;
    private boolean xmlStandalone = false;
    private String xmlVersion = "1.0";

    public DocumentImpl(DOMImplementationImpl dOMImplementationImpl, String string, String string2, DocumentType documentType, String string3) {
        super(null);
        this.document = this;
        this.domImplementation = dOMImplementationImpl;
        this.inputEncoding = string3;
        if (documentType != null) {
            this.appendChild(documentType);
        }
        if (string2 != null) {
            this.appendChild(this.createElementNS(string, string2));
        }
    }

    private void changeDocumentToThis(NodeImpl object) {
        int n;
        Map<String, NodeImpl.UserData> map = ((NodeImpl)object).document.getUserDataMapForRead((NodeImpl)object);
        if (!map.isEmpty()) {
            this.getUserDataMap((NodeImpl)object).putAll(map);
        }
        ((NodeImpl)object).document = this;
        map = ((NodeImpl)object).getChildNodes();
        for (n = 0; n < map.getLength(); ++n) {
            this.changeDocumentToThis((NodeImpl)map.item(n));
        }
        if (((NodeImpl)object).getNodeType() == 1) {
            object = ((NodeImpl)object).getAttributes();
            for (n = 0; n < object.getLength(); ++n) {
                this.changeDocumentToThis((AttrImpl)object.item(n));
            }
        }
    }

    static boolean isXMLIdentifier(String string) {
        if (string.length() == 0) {
            return false;
        }
        if (!DocumentImpl.isXMLIdentifierStart(string.charAt(0))) {
            return false;
        }
        for (int i = 1; i < string.length(); ++i) {
            if (DocumentImpl.isXMLIdentifierPart(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    private static boolean isXMLIdentifierPart(char c) {
        boolean bl = DocumentImpl.isXMLIdentifierStart(c) || c >= '0' && c <= '9' || c == '-' || c == '.';
        return bl;
    }

    private static boolean isXMLIdentifierStart(char c) {
        boolean bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c == '_';
        return bl;
    }

    private static void notifyUserDataHandlers(short s, Node node, NodeImpl nodeImpl) {
        if (!(node instanceof NodeImpl)) {
            return;
        }
        NodeImpl nodeImpl2 = (NodeImpl)node;
        if (nodeImpl2.document == null) {
            return;
        }
        for (Map.Entry entry : nodeImpl2.document.getUserDataMapForRead(nodeImpl2).entrySet()) {
            NodeImpl.UserData userData = (NodeImpl.UserData)entry.getValue();
            if (userData.handler == null) continue;
            userData.handler.handle(s, (String)entry.getKey(), userData.value, node, nodeImpl);
        }
    }

    private NodeImpl shallowCopy(short s, Node node) {
        switch (node.getNodeType()) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported node type ");
                stringBuilder.append(node.getNodeType());
                throw new DOMException(9, stringBuilder.toString());
            }
            case 11: {
                return this.createDocumentFragment();
            }
            case 9: 
            case 10: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot copy node of type ");
                stringBuilder.append(node.getNodeType());
                throw new DOMException(9, stringBuilder.toString());
            }
            case 8: {
                return this.createComment(((Comment)node).getData());
            }
            case 7: {
                node = (ProcessingInstruction)node;
                return this.createProcessingInstruction(node.getTarget(), node.getData());
            }
            case 6: 
            case 12: {
                throw new UnsupportedOperationException();
            }
            case 5: {
                return this.createEntityReference(node.getNodeName());
            }
            case 4: {
                return this.createCDATASection(((CharacterData)node).getData());
            }
            case 3: {
                return this.createTextNode(((Text)node).getData());
            }
            case 2: {
                AttrImpl attrImpl = (AttrImpl)node;
                if (attrImpl.namespaceAware) {
                    node = this.createAttributeNS(attrImpl.getNamespaceURI(), attrImpl.getLocalName());
                    ((AttrImpl)node).setPrefix(attrImpl.getPrefix());
                } else {
                    node = this.createAttribute(attrImpl.getName());
                }
                ((NodeImpl)node).setNodeValue(attrImpl.getValue());
                return node;
            }
            case 1: 
        }
        NodeImpl nodeImpl = (ElementImpl)node;
        if (nodeImpl.namespaceAware) {
            node = this.createElementNS(nodeImpl.getNamespaceURI(), nodeImpl.getLocalName());
            ((ElementImpl)node).setPrefix(nodeImpl.getPrefix());
        } else {
            node = this.createElement(nodeImpl.getTagName());
        }
        NamedNodeMap namedNodeMap = nodeImpl.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            AttrImpl attrImpl = (AttrImpl)namedNodeMap.item(i);
            nodeImpl = (AttrImpl)this.shallowCopy(s, attrImpl);
            DocumentImpl.notifyUserDataHandlers(s, attrImpl, nodeImpl);
            if (attrImpl.namespaceAware) {
                ((ElementImpl)node).setAttributeNodeNS((Attr)((Object)nodeImpl));
                continue;
            }
            ((ElementImpl)node).setAttributeNode((Attr)((Object)nodeImpl));
        }
        return node;
    }

    @Override
    public Node adoptNode(Node object) {
        Node node;
        if (!(object instanceof NodeImpl)) {
            return null;
        }
        Object object2 = (NodeImpl)object;
        switch (((NodeImpl)object2).getNodeType()) {
            default: {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unsupported node type ");
                ((StringBuilder)object2).append(object.getNodeType());
                throw new DOMException(9, ((StringBuilder)object2).toString());
            }
            case 6: 
            case 9: 
            case 10: 
            case 12: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot adopt nodes of type ");
                ((StringBuilder)object).append(((NodeImpl)object2).getNodeType());
                throw new DOMException(9, ((StringBuilder)object).toString());
            }
            case 2: {
                node = (AttrImpl)object;
                if (((AttrImpl)node).ownerElement == null) break;
                ((AttrImpl)node).ownerElement.removeAttributeNode((Attr)node);
            }
            case 1: 
            case 3: 
            case 4: 
            case 5: 
            case 7: 
            case 8: 
            case 11: 
        }
        node = ((NodeImpl)object2).getParentNode();
        if (node != null) {
            node.removeChild((Node)object2);
        }
        this.changeDocumentToThis((NodeImpl)object2);
        DocumentImpl.notifyUserDataHandlers((short)5, (Node)object, null);
        return object2;
    }

    Node cloneOrImportNode(short s, Node node, boolean bl) {
        NodeImpl nodeImpl = this.shallowCopy(s, node);
        if (bl) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                nodeImpl.appendChild(this.cloneOrImportNode(s, nodeList.item(i), bl));
            }
        }
        DocumentImpl.notifyUserDataHandlers(s, node, nodeImpl);
        return nodeImpl;
    }

    @Override
    public AttrImpl createAttribute(String string) {
        return new AttrImpl(this, string);
    }

    @Override
    public AttrImpl createAttributeNS(String string, String string2) {
        return new AttrImpl(this, string, string2);
    }

    @Override
    public CDATASectionImpl createCDATASection(String string) {
        return new CDATASectionImpl(this, string);
    }

    @Override
    public CommentImpl createComment(String string) {
        return new CommentImpl(this, string);
    }

    @Override
    public DocumentFragmentImpl createDocumentFragment() {
        return new DocumentFragmentImpl(this);
    }

    @Override
    public ElementImpl createElement(String string) {
        return new ElementImpl(this, string);
    }

    @Override
    public ElementImpl createElementNS(String string, String string2) {
        return new ElementImpl(this, string, string2);
    }

    @Override
    public EntityReferenceImpl createEntityReference(String string) {
        return new EntityReferenceImpl(this, string);
    }

    @Override
    public ProcessingInstructionImpl createProcessingInstruction(String string, String string2) {
        return new ProcessingInstructionImpl(this, string, string2);
    }

    @Override
    public TextImpl createTextNode(String string) {
        return new TextImpl(this, string);
    }

    @Override
    public DocumentType getDoctype() {
        for (LeafNodeImpl leafNodeImpl : this.children) {
            if (!(leafNodeImpl instanceof DocumentType)) continue;
            return (DocumentType)((Object)leafNodeImpl);
        }
        return null;
    }

    @Override
    public Element getDocumentElement() {
        for (LeafNodeImpl leafNodeImpl : this.children) {
            if (!(leafNodeImpl instanceof Element)) continue;
            return (Element)((Object)leafNodeImpl);
        }
        return null;
    }

    @Override
    public String getDocumentURI() {
        return this.documentUri;
    }

    @Override
    public DOMConfiguration getDomConfig() {
        if (this.domConfiguration == null) {
            this.domConfiguration = new DOMConfigurationImpl();
        }
        return this.domConfiguration;
    }

    @Override
    public Element getElementById(String object) {
        ElementImpl elementImpl = (ElementImpl)this.getDocumentElement();
        object = elementImpl == null ? null : elementImpl.getElementById((String)object);
        return object;
    }

    @Override
    public NodeList getElementsByTagName(String string) {
        NodeListImpl nodeListImpl = new NodeListImpl();
        this.getElementsByTagName(nodeListImpl, string);
        return nodeListImpl;
    }

    @Override
    public NodeList getElementsByTagNameNS(String string, String string2) {
        NodeListImpl nodeListImpl = new NodeListImpl();
        this.getElementsByTagNameNS(nodeListImpl, string, string2);
        return nodeListImpl;
    }

    @Override
    public DOMImplementation getImplementation() {
        return this.domImplementation;
    }

    @Override
    public String getInputEncoding() {
        return this.inputEncoding;
    }

    @Override
    public String getNodeName() {
        return "#document";
    }

    @Override
    public short getNodeType() {
        return 9;
    }

    @Override
    public boolean getStrictErrorChecking() {
        return this.strictErrorChecking;
    }

    @Override
    public String getTextContent() {
        return null;
    }

    Map<String, NodeImpl.UserData> getUserDataMap(NodeImpl nodeImpl) {
        Map<String, NodeImpl.UserData> map;
        if (this.nodeToUserData == null) {
            this.nodeToUserData = new WeakHashMap();
        }
        Map<String, NodeImpl.UserData> map2 = map = this.nodeToUserData.get(nodeImpl);
        if (map == null) {
            map2 = new HashMap<String, NodeImpl.UserData>();
            this.nodeToUserData.put(nodeImpl, map2);
        }
        return map2;
    }

    Map<String, NodeImpl.UserData> getUserDataMapForRead(NodeImpl map) {
        block1 : {
            WeakHashMap<NodeImpl, Map<String, NodeImpl.UserData>> weakHashMap = this.nodeToUserData;
            if (weakHashMap == null) {
                return Collections.emptyMap();
            }
            if ((map = weakHashMap.get(map)) != null) break block1;
            map = Collections.emptyMap();
        }
        return map;
    }

    @Override
    public String getXmlEncoding() {
        return null;
    }

    @Override
    public boolean getXmlStandalone() {
        return this.xmlStandalone;
    }

    @Override
    public String getXmlVersion() {
        return this.xmlVersion;
    }

    @Override
    public Node importNode(Node node, boolean bl) {
        return this.cloneOrImportNode((short)2, node, bl);
    }

    @Override
    public Node insertChildAt(Node node, int n) {
        if (node instanceof Element && this.getDocumentElement() != null) {
            throw new DOMException(3, "Only one root element allowed");
        }
        if (node instanceof DocumentType && this.getDoctype() != null) {
            throw new DOMException(3, "Only one DOCTYPE element allowed");
        }
        return super.insertChildAt(node, n);
    }

    @Override
    public void normalizeDocument() {
        Element element = this.getDocumentElement();
        if (element == null) {
            return;
        }
        ((DOMConfigurationImpl)this.getDomConfig()).normalize(element);
    }

    @Override
    public Node renameNode(Node node, String string, String string2) {
        if (node.getOwnerDocument() == this) {
            DocumentImpl.setNameNS((NodeImpl)node, string, string2);
            DocumentImpl.notifyUserDataHandlers((short)4, node, null);
            return node;
        }
        throw new DOMException(4, null);
    }

    @Override
    public void setDocumentURI(String string) {
        this.documentUri = string;
    }

    @Override
    public void setStrictErrorChecking(boolean bl) {
        this.strictErrorChecking = bl;
    }

    @Override
    public void setXmlStandalone(boolean bl) {
        this.xmlStandalone = bl;
    }

    @Override
    public void setXmlVersion(String string) {
        this.xmlVersion = string;
    }
}

