/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.NamespaceAlias;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XMLNSDecl;
import org.apache.xalan.templates.XSLTVisitable;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.UnImplNode;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.WhitespaceStrippingElementMatcher;
import org.apache.xpath.XPathContext;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.NamespaceSupport;

public class ElemTemplateElement
extends UnImplNode
implements PrefixResolver,
Serializable,
ExpressionNode,
WhitespaceStrippingElementMatcher,
XSLTVisitable {
    static final long serialVersionUID = 4440018597841834447L;
    private transient Node m_DOMBackPointer;
    private int m_columnNumber;
    private List m_declaredPrefixes;
    private boolean m_defaultSpace = true;
    protected int m_docOrderNumber = -1;
    private int m_endColumnNumber;
    private int m_endLineNumber;
    ElemTemplateElement m_firstChild;
    private boolean m_hasTextLitOnly = false;
    protected boolean m_hasVariableDecl = false;
    private int m_lineNumber;
    ElemTemplateElement m_nextSibling;
    protected ElemTemplateElement m_parentNode;
    private List m_prefixTable;

    private boolean excludeResultNSDecl(String string, String string2) throws TransformerException {
        if (string2 != null) {
            if (!string2.equals("http://www.w3.org/1999/XSL/Transform") && !this.getStylesheet().containsExtensionElementURI(string2)) {
                if (this.containsExcludeResultPrefix(string, string2)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    protected boolean accept(XSLTVisitor xSLTVisitor) {
        return xSLTVisitor.visitInstruction(this);
    }

    void addOrReplaceDecls(XMLNSDecl xMLNSDecl) {
        for (int i = this.m_prefixTable.size() - 1; i >= 0; --i) {
            if (!((XMLNSDecl)this.m_prefixTable.get(i)).getPrefix().equals(xMLNSDecl.getPrefix())) continue;
            return;
        }
        this.m_prefixTable.add(xMLNSDecl);
    }

    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        if (elemTemplateElement == null) {
            this.error("ER_NULL_CHILD", null);
        }
        if (this.m_firstChild == null) {
            this.m_firstChild = elemTemplateElement;
        } else {
            this.getLastChildElem().m_nextSibling = elemTemplateElement;
        }
        elemTemplateElement.setParentElem(this);
        return elemTemplateElement;
    }

    @Override
    public Node appendChild(Node node) throws DOMException {
        if (node == null) {
            this.error("ER_NULL_CHILD", null);
        }
        ElemTemplateElement elemTemplateElement = (ElemTemplateElement)node;
        if (this.m_firstChild == null) {
            this.m_firstChild = elemTemplateElement;
        } else {
            ((ElemTemplateElement)this.getLastChild()).m_nextSibling = elemTemplateElement;
        }
        elemTemplateElement.m_parentNode = this;
        return node;
    }

    protected void callChildVisitors(XSLTVisitor xSLTVisitor) {
        this.callChildVisitors(xSLTVisitor, true);
    }

    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        while (elemTemplateElement != null) {
            elemTemplateElement.callVisitors(xSLTVisitor);
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
    }

    @Override
    public void callVisitors(XSLTVisitor xSLTVisitor) {
        if (this.accept(xSLTVisitor)) {
            this.callChildVisitors(xSLTVisitor);
        }
    }

    public boolean canAcceptVariables() {
        return true;
    }

    @Override
    public boolean canStripWhiteSpace() {
        StylesheetRoot stylesheetRoot = this.getStylesheetRoot();
        boolean bl = stylesheetRoot != null ? stylesheetRoot.canStripWhiteSpace() : false;
        return bl;
    }

    public int compareTo(Object object) throws ClassCastException {
        object = (ElemTemplateElement)object;
        int n = ((ElemTemplateElement)object).getStylesheetComposed().getImportCountComposed();
        int n2 = this.getStylesheetComposed().getImportCountComposed();
        if (n2 < n) {
            return -1;
        }
        if (n2 > n) {
            return 1;
        }
        return this.getUid() - ((ElemTemplateElement)object).getUid();
    }

    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        this.resolvePrefixTables();
        ElemTemplateElement elemTemplateElement = this.getFirstChildElem();
        boolean bl = elemTemplateElement != null && elemTemplateElement.getXSLToken() == 78 && elemTemplateElement.getNextSiblingElem() == null;
        this.m_hasTextLitOnly = bl;
        stylesheetRoot.getComposeState().pushStackMark();
    }

    public boolean containsExcludeResultPrefix(String string, String string2) {
        ElemTemplateElement elemTemplateElement = this.getParentElem();
        if (elemTemplateElement != null) {
            return elemTemplateElement.containsExcludeResultPrefix(string, string2);
        }
        return false;
    }

    public void endCompose(StylesheetRoot stylesheetRoot) throws TransformerException {
        stylesheetRoot.getComposeState().popStackMark();
    }

    @Override
    public void error(String string) {
        this.error(string, null);
    }

    @Override
    public void error(String string, Object[] arrobject) {
        throw new RuntimeException(XSLMessages.createMessage("ER_ELEMTEMPLATEELEM_ERR", new Object[]{XSLMessages.createMessage(string, arrobject)}));
    }

    public void execute(TransformerImpl transformerImpl) throws TransformerException {
    }

    void executeNSDecls(TransformerImpl transformerImpl) throws TransformerException {
        this.executeNSDecls(transformerImpl, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void executeNSDecls(TransformerImpl object, String string) throws TransformerException {
        try {
            if (this.m_prefixTable != null) {
                object = ((TransformerImpl)object).getResultTreeHandler();
                for (int i = this.m_prefixTable.size() - 1; i >= 0; --i) {
                    XMLNSDecl xMLNSDecl = (XMLNSDecl)this.m_prefixTable.get(i);
                    if (xMLNSDecl.getIsExcluded() || string != null && xMLNSDecl.getPrefix().equals(string)) continue;
                    object.startPrefixMapping(xMLNSDecl.getPrefix(), xMLNSDecl.getURI(), true);
                }
            }
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    @Override
    public void exprAddChild(ExpressionNode expressionNode, int n) {
        this.appendChild((ElemTemplateElement)expressionNode);
    }

    @Override
    public ExpressionNode exprGetChild(int n) {
        return (ExpressionNode)((Object)this.item(n));
    }

    @Override
    public int exprGetNumChildren() {
        return this.getLength();
    }

    @Override
    public ExpressionNode exprGetParent() {
        return this.getParentElem();
    }

    @Override
    public void exprSetParent(ExpressionNode expressionNode) {
        this.setParentElem((ElemTemplateElement)expressionNode);
    }

    @Override
    public String getBaseIdentifier() {
        return this.getSystemId();
    }

    @Override
    public NodeList getChildNodes() {
        return this;
    }

    @Override
    public int getColumnNumber() {
        return this.m_columnNumber;
    }

    public Node getDOMBackPointer() {
        return this.m_DOMBackPointer;
    }

    public List getDeclaredPrefixes() {
        return this.m_declaredPrefixes;
    }

    public int getEndColumnNumber() {
        return this.m_endColumnNumber;
    }

    public int getEndLineNumber() {
        return this.m_endLineNumber;
    }

    @Override
    public Node getFirstChild() {
        return this.m_firstChild;
    }

    public ElemTemplateElement getFirstChildElem() {
        return this.m_firstChild;
    }

    @Override
    public Node getLastChild() {
        ElemTemplateElement elemTemplateElement = null;
        ElemTemplateElement elemTemplateElement2 = this.m_firstChild;
        while (elemTemplateElement2 != null) {
            elemTemplateElement = elemTemplateElement2;
            elemTemplateElement2 = elemTemplateElement2.m_nextSibling;
        }
        return elemTemplateElement;
    }

    public ElemTemplateElement getLastChildElem() {
        ElemTemplateElement elemTemplateElement = null;
        ElemTemplateElement elemTemplateElement2 = this.m_firstChild;
        while (elemTemplateElement2 != null) {
            elemTemplateElement = elemTemplateElement2;
            elemTemplateElement2 = elemTemplateElement2.m_nextSibling;
        }
        return elemTemplateElement;
    }

    @Override
    public int getLength() {
        int n = 0;
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        while (elemTemplateElement != null) {
            ++n;
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
        return n;
    }

    @Override
    public int getLineNumber() {
        return this.m_lineNumber;
    }

    @Override
    public String getLocalName() {
        return this.getNodeName();
    }

    @Override
    public String getNamespaceForPrefix(String object) {
        List list = this.m_declaredPrefixes;
        Object object2 = object;
        if (list != null) {
            int n = list.size();
            Object object3 = object;
            if (((String)object).equals("#default")) {
                object3 = "";
            }
            int n2 = 0;
            do {
                object2 = object3;
                if (n2 >= n) break;
                object = (XMLNSDecl)list.get(n2);
                if (((String)object3).equals(((XMLNSDecl)object).getPrefix())) {
                    return ((XMLNSDecl)object).getURI();
                }
                ++n2;
            } while (true);
        }
        if ((object = this.m_parentNode) != null) {
            return ((ElemTemplateElement)object).getNamespaceForPrefix((String)object2);
        }
        if ("xml".equals(object2)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        return null;
    }

    @Override
    public String getNamespaceForPrefix(String string, Node node) {
        this.error("ER_CANT_RESOLVE_NSPREFIX", null);
        return null;
    }

    @Override
    public Node getNextSibling() {
        return this.m_nextSibling;
    }

    public ElemTemplateElement getNextSiblingElem() {
        return this.m_nextSibling;
    }

    @Override
    public String getNodeName() {
        return "Unknown XSLT Element";
    }

    @Override
    public short getNodeType() {
        return 1;
    }

    @Override
    public Document getOwnerDocument() {
        return this.getStylesheet();
    }

    public ElemTemplate getOwnerXSLTemplate() {
        ElemTemplateElement elemTemplateElement = this;
        int n = elemTemplateElement.getXSLToken();
        while (elemTemplateElement != null && n != 19) {
            ElemTemplateElement elemTemplateElement2;
            elemTemplateElement = elemTemplateElement2 = elemTemplateElement.getParentElem();
            if (elemTemplateElement2 == null) continue;
            n = elemTemplateElement2.getXSLToken();
            elemTemplateElement = elemTemplateElement2;
        }
        return (ElemTemplate)elemTemplateElement;
    }

    public ElemTemplateElement getParentElem() {
        return this.m_parentNode;
    }

    @Override
    public Node getParentNode() {
        return this.m_parentNode;
    }

    public ElemTemplateElement getParentNodeElem() {
        return this.m_parentNode;
    }

    List getPrefixTable() {
        return this.m_prefixTable;
    }

    @Override
    public Node getPreviousSibling() {
        Node node = this.getParentNode();
        Node node2 = null;
        if (node != null) {
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node == this) {
                    return node2;
                }
                node2 = node;
            }
        }
        return null;
    }

    public ElemTemplateElement getPreviousSiblingElem() {
        ElemTemplateElement elemTemplateElement = this.getParentNodeElem();
        ElemTemplateElement elemTemplateElement2 = null;
        if (elemTemplateElement != null) {
            for (elemTemplateElement = elemTemplateElement.getFirstChildElem(); elemTemplateElement != null; elemTemplateElement = elemTemplateElement.getNextSiblingElem()) {
                if (elemTemplateElement == this) {
                    return elemTemplateElement2;
                }
                elemTemplateElement2 = elemTemplateElement;
            }
        }
        return null;
    }

    @Override
    public String getPublicId() {
        Object object = this.m_parentNode;
        object = object != null ? ((ElemTemplateElement)object).getPublicId() : null;
        return object;
    }

    public Stylesheet getStylesheet() {
        ElemTemplateElement elemTemplateElement = this.m_parentNode;
        elemTemplateElement = elemTemplateElement == null ? null : elemTemplateElement.getStylesheet();
        return elemTemplateElement;
    }

    public StylesheetComposed getStylesheetComposed() {
        return this.m_parentNode.getStylesheetComposed();
    }

    public StylesheetRoot getStylesheetRoot() {
        return this.m_parentNode.getStylesheetRoot();
    }

    @Override
    public String getSystemId() {
        Object object = this.getStylesheet();
        object = object == null ? null : ((Stylesheet)object).getHref();
        return object;
    }

    @Override
    public String getTagName() {
        return this.getNodeName();
    }

    public int getUid() {
        return this.m_docOrderNumber;
    }

    public int getXSLToken() {
        return -1;
    }

    public boolean getXmlSpace() {
        return this.m_defaultSpace;
    }

    @Override
    public boolean handlesNullPrefixes() {
        return false;
    }

    @Override
    public boolean hasChildNodes() {
        boolean bl = this.m_firstChild != null;
        return bl;
    }

    public boolean hasTextLitOnly() {
        return this.m_hasTextLitOnly;
    }

    public boolean hasVariableDecl() {
        return this.m_hasVariableDecl;
    }

    @Override
    public Node insertBefore(Node node, Node node2) throws DOMException {
        if (node2 == null) {
            this.appendChild(node);
            return node;
        }
        if (node == node2) {
            return node;
        }
        Node node3 = this.m_firstChild;
        Node node4 = null;
        boolean bl = false;
        while (node3 != null) {
            if (node == node3) {
                if (node4 != null) {
                    ((ElemTemplateElement)node4).m_nextSibling = (ElemTemplateElement)node3.getNextSibling();
                } else {
                    this.m_firstChild = (ElemTemplateElement)node3.getNextSibling();
                }
                node3 = node3.getNextSibling();
                continue;
            }
            if (node2 == node3) {
                if (node4 != null) {
                    node4.m_nextSibling = (ElemTemplateElement)node;
                } else {
                    this.m_firstChild = (ElemTemplateElement)node;
                }
                ((ElemTemplateElement)node).m_nextSibling = (ElemTemplateElement)node2;
                ((ElemTemplateElement)node).setParentElem(this);
                node4 = node;
                node3 = node3.getNextSibling();
                bl = true;
                continue;
            }
            node4 = node3;
            node3 = node3.getNextSibling();
        }
        if (bl) {
            return node;
        }
        throw new DOMException(8, "refChild was not found in insertBefore method!");
    }

    public boolean isCompiledTemplate() {
        return false;
    }

    @Override
    public Node item(int n) {
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        for (int i = 0; i < n && elemTemplateElement != null; ++i) {
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
        return elemTemplateElement;
    }

    boolean needToCheckExclude() {
        return false;
    }

    public void recompose(StylesheetRoot stylesheetRoot) throws TransformerException {
    }

    public ElemTemplateElement removeChild(ElemTemplateElement elemTemplateElement) {
        if (elemTemplateElement != null && elemTemplateElement.m_parentNode == this) {
            if (elemTemplateElement == this.m_firstChild) {
                this.m_firstChild = elemTemplateElement.m_nextSibling;
            } else {
                elemTemplateElement.getPreviousSiblingElem().m_nextSibling = elemTemplateElement.m_nextSibling;
            }
            elemTemplateElement.m_parentNode = null;
            elemTemplateElement.m_nextSibling = null;
            return elemTemplateElement;
        }
        return null;
    }

    public ElemTemplateElement replaceChild(ElemTemplateElement elemTemplateElement, ElemTemplateElement elemTemplateElement2) {
        if (elemTemplateElement2 != null && elemTemplateElement2.getParentElem() == this) {
            ElemTemplateElement elemTemplateElement3 = elemTemplateElement2.getPreviousSiblingElem();
            if (elemTemplateElement3 != null) {
                elemTemplateElement3.m_nextSibling = elemTemplateElement;
            }
            if (this.m_firstChild == elemTemplateElement2) {
                this.m_firstChild = elemTemplateElement;
            }
            elemTemplateElement.m_parentNode = this;
            elemTemplateElement2.m_parentNode = null;
            elemTemplateElement.m_nextSibling = elemTemplateElement2.m_nextSibling;
            elemTemplateElement2.m_nextSibling = null;
            return elemTemplateElement;
        }
        return null;
    }

    @Override
    public Node replaceChild(Node node, Node node2) throws DOMException {
        if (node2 != null && node2.getParentNode() == this) {
            node = (ElemTemplateElement)node;
            ElemTemplateElement elemTemplateElement = (ElemTemplateElement)((ElemTemplateElement)(node2 = (ElemTemplateElement)node2)).getPreviousSibling();
            if (elemTemplateElement != null) {
                elemTemplateElement.m_nextSibling = node;
            }
            if (this.m_firstChild == node2) {
                this.m_firstChild = node;
            }
            ((ElemTemplateElement)node).m_parentNode = this;
            ((ElemTemplateElement)node2).m_parentNode = null;
            ((ElemTemplateElement)node).m_nextSibling = ((ElemTemplateElement)node2).m_nextSibling;
            ((ElemTemplateElement)node2).m_nextSibling = null;
            return node;
        }
        return null;
    }

    public void resolvePrefixTables() throws TransformerException {
        Object object;
        int n;
        int n2;
        Object object2;
        boolean bl;
        Object object3;
        this.setPrefixTable(null);
        if (this.m_declaredPrefixes != null) {
            object2 = this.getStylesheetRoot();
            n2 = this.m_declaredPrefixes.size();
            for (n = 0; n < n2; ++n) {
                object = (XMLNSDecl)this.m_declaredPrefixes.get(n);
                String string = ((XMLNSDecl)object).getPrefix();
                object = object3 = ((XMLNSDecl)object).getURI();
                if (object3 == null) {
                    object = "";
                }
                bl = this.excludeResultNSDecl(string, (String)object);
                if (this.m_prefixTable == null) {
                    this.setPrefixTable(new ArrayList());
                }
                object = (object3 = ((StylesheetRoot)object2).getNamespaceAliasComposed((String)object)) != null ? new XMLNSDecl(((NamespaceAlias)object3).getStylesheetPrefix(), ((NamespaceAlias)object3).getResultNamespace(), bl) : new XMLNSDecl(string, (String)object, bl);
                this.m_prefixTable.add(object);
            }
        }
        if ((object = this.getParentNodeElem()) != null) {
            object2 = ((ElemTemplateElement)object).m_prefixTable;
            if (this.m_prefixTable == null && !this.needToCheckExclude()) {
                this.setPrefixTable(((ElemTemplateElement)object).m_prefixTable);
            } else {
                n2 = object2.size();
                for (n = 0; n < n2; ++n) {
                    object3 = (XMLNSDecl)object2.get(n);
                    bl = this.excludeResultNSDecl(((XMLNSDecl)object3).getPrefix(), ((XMLNSDecl)object3).getURI());
                    object = object3;
                    if (bl != ((XMLNSDecl)object3).getIsExcluded()) {
                        object = new XMLNSDecl(((XMLNSDecl)object3).getPrefix(), ((XMLNSDecl)object3).getURI(), bl);
                    }
                    this.addOrReplaceDecls((XMLNSDecl)object);
                }
            }
        } else if (this.m_prefixTable == null) {
            this.setPrefixTable(new ArrayList());
        }
    }

    public void runtimeInit(TransformerImpl transformerImpl) throws TransformerException {
    }

    public void setDOMBackPointer(Node node) {
        this.m_DOMBackPointer = node;
    }

    public void setEndLocaterInfo(SourceLocator sourceLocator) {
        this.m_endLineNumber = sourceLocator.getLineNumber();
        this.m_endColumnNumber = sourceLocator.getColumnNumber();
    }

    public void setLocaterInfo(SourceLocator sourceLocator) {
        this.m_lineNumber = sourceLocator.getLineNumber();
        this.m_columnNumber = sourceLocator.getColumnNumber();
    }

    public void setParentElem(ElemTemplateElement elemTemplateElement) {
        this.m_parentNode = elemTemplateElement;
    }

    void setPrefixTable(List list) {
        this.m_prefixTable = list;
    }

    public void setPrefixes(NamespaceSupport namespaceSupport) throws TransformerException {
        this.setPrefixes(namespaceSupport, false);
    }

    public void setPrefixes(NamespaceSupport namespaceSupport, boolean bl) throws TransformerException {
        Enumeration enumeration = namespaceSupport.getDeclaredPrefixes();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            if (this.m_declaredPrefixes == null) {
                this.m_declaredPrefixes = new ArrayList();
            }
            Object object = namespaceSupport.getURI(string);
            if (bl && ((String)object).equals("http://www.w3.org/1999/XSL/Transform")) continue;
            object = new XMLNSDecl(string, (String)object, false);
            this.m_declaredPrefixes.add(object);
        }
    }

    public void setUid(int n) {
        this.m_docOrderNumber = n;
    }

    public void setXmlSpace(int n) {
        boolean bl = 2 == n;
        this.m_defaultSpace = bl;
    }

    @Override
    public boolean shouldStripWhiteSpace(XPathContext xPathContext, Element element) throws TransformerException {
        StylesheetRoot stylesheetRoot = this.getStylesheetRoot();
        boolean bl = stylesheetRoot != null ? stylesheetRoot.shouldStripWhiteSpace(xPathContext, element) : false;
        return bl;
    }

    void unexecuteNSDecls(TransformerImpl transformerImpl) throws TransformerException {
        this.unexecuteNSDecls(transformerImpl, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unexecuteNSDecls(TransformerImpl object, String string) throws TransformerException {
        try {
            if (this.m_prefixTable != null) {
                SerializationHandler serializationHandler = ((TransformerImpl)object).getResultTreeHandler();
                int n = this.m_prefixTable.size();
                for (int i = 0; i < n; ++i) {
                    object = (XMLNSDecl)this.m_prefixTable.get(i);
                    if (((XMLNSDecl)object).getIsExcluded() || string != null && ((XMLNSDecl)object).getPrefix().equals(string)) continue;
                    serializationHandler.endPrefixMapping(((XMLNSDecl)object).getPrefix());
                }
            }
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }
}

