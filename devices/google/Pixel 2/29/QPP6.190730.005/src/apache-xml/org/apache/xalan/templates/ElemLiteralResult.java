/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemUse;
import org.apache.xalan.templates.NamespaceAlias;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.StringVector;
import org.apache.xpath.XPathContext;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.SAXException;

public class ElemLiteralResult
extends ElemUse {
    private static final String EMPTYSTRING = "";
    static final long serialVersionUID = -8703409074421657260L;
    private boolean isLiteralResultAsStylesheet = false;
    private StringVector m_ExtensionElementURIs;
    private List m_avts = null;
    private StringVector m_excludeResultPrefixes;
    private String m_localName;
    private String m_namespace;
    private String m_rawName;
    private String m_version;
    private List m_xslAttr = null;

    private boolean excludeResultNSDecl(String string, String string2) throws TransformerException {
        if (this.m_excludeResultPrefixes != null) {
            return this.containsExcludeResultPrefix(string, string2);
        }
        return false;
    }

    @Override
    protected boolean accept(XSLTVisitor xSLTVisitor) {
        return xSLTVisitor.visitLiteralResultElement(this);
    }

    public void addLiteralResultAttribute(String string) {
        if (this.m_xslAttr == null) {
            this.m_xslAttr = new ArrayList();
        }
        this.m_xslAttr.add(string);
    }

    public void addLiteralResultAttribute(AVT aVT) {
        if (this.m_avts == null) {
            this.m_avts = new ArrayList();
        }
        this.m_avts.add(aVT);
    }

    @Override
    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        List list;
        if (bl && (list = this.m_avts) != null) {
            for (int i = list.size() - 1; i >= 0; --i) {
                ((AVT)this.m_avts.get(i)).callVisitors(xSLTVisitor);
            }
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot object) throws TransformerException {
        super.compose((StylesheetRoot)object);
        StylesheetRoot.ComposeState composeState = ((StylesheetRoot)object).getComposeState();
        Vector vector = composeState.getVariableNames();
        object = this.m_avts;
        if (object != null) {
            for (int i = object.size() - 1; i >= 0; --i) {
                ((AVT)this.m_avts.get(i)).fixupVariables(vector, composeState.getGlobalsSize());
            }
        }
    }

    @Override
    public boolean containsExcludeResultPrefix(String object, String string) {
        if (string != null && (this.m_excludeResultPrefixes != null || this.m_ExtensionElementURIs != null)) {
            String string2 = object;
            if (((String)object).length() == 0) {
                string2 = "#default";
            }
            if (this.m_excludeResultPrefixes != null) {
                for (int i = 0; i < this.m_excludeResultPrefixes.size(); ++i) {
                    if (!string.equals(this.getNamespaceForPrefix(this.m_excludeResultPrefixes.elementAt(i)))) continue;
                    return true;
                }
            }
            if ((object = this.m_ExtensionElementURIs) != null && ((StringVector)object).contains(string)) {
                return true;
            }
            return super.containsExcludeResultPrefix(string2, string);
        }
        return super.containsExcludeResultPrefix((String)object, string);
    }

    public boolean containsExtensionElementURI(String string) {
        StringVector stringVector = this.m_ExtensionElementURIs;
        if (stringVector == null) {
            return false;
        }
        return stringVector.contains(string);
    }

    public Iterator enumerateLiteralResultAttributes() {
        Object object = this.m_avts;
        object = object == null ? null : object.iterator();
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        SerializationHandler serializationHandler;
        TransformerException transformerException;
        block14 : {
            block15 : {
                block13 : {
                    serializationHandler = transformerImpl.getSerializationHandler();
                    serializationHandler.startPrefixMapping(this.getPrefix(), this.getNamespace());
                    this.executeNSDecls(transformerImpl);
                    serializationHandler.startElement(this.getNamespace(), this.getLocalName(), this.getRawName());
                    transformerException = null;
                    super.execute(transformerImpl);
                    if (this.m_avts == null) break block13;
                    for (int i = this.m_avts.size() - 1; i >= 0; --i) {
                        AVT aVT = (AVT)this.m_avts.get(i);
                        Object object = transformerImpl.getXPathContext();
                        int n = ((XPathContext)object).getCurrentNode();
                        if ((object = aVT.evaluate((XPathContext)object, n, this)) == null) continue;
                        serializationHandler.addAttribute(aVT.getURI(), aVT.getName(), aVT.getRawName(), "CDATA", (String)object, false);
                    }
                }
                try {
                    transformerImpl.executeChildTemplates((ElemTemplateElement)this, true);
                    break block14;
                }
                catch (SAXException sAXException) {
                    break block15;
                }
                catch (TransformerException transformerException2) {
                    break block14;
                }
                catch (SAXException sAXException) {
                    // empty catch block
                }
            }
            transformerException = new TransformerException(transformerException);
            break block14;
            catch (TransformerException transformerException3) {
                // empty catch block
            }
        }
        try {
            serializationHandler.endElement(this.getNamespace(), this.getLocalName(), this.getRawName());
            if (transformerException != null) throw transformerException;
        }
        catch (SAXException sAXException) {
            if (transformerException == null) throw new TransformerException(sAXException);
            throw transformerException;
        }
        this.unexecuteNSDecls(transformerImpl);
        try {
            serializationHandler.endPrefixMapping(this.getPrefix());
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    @Override
    public String getAttribute(String object) {
        if ((object = this.getLiteralResultAttribute((String)object)) != null) {
            return ((AVT)object).getSimpleString();
        }
        return EMPTYSTRING;
    }

    @Override
    public String getAttributeNS(String object, String string) {
        if ((object = this.getLiteralResultAttributeNS((String)object, string)) != null) {
            return ((AVT)object).getSimpleString();
        }
        return EMPTYSTRING;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new LiteralElementAttributes();
    }

    public String getExtensionElementPrefix(int n) throws ArrayIndexOutOfBoundsException {
        StringVector stringVector = this.m_ExtensionElementURIs;
        if (stringVector != null) {
            return stringVector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getExtensionElementPrefixCount() {
        StringVector stringVector = this.m_ExtensionElementURIs;
        int n = stringVector != null ? stringVector.size() : 0;
        return n;
    }

    public boolean getIsLiteralResultAsStylesheet() {
        return this.isLiteralResultAsStylesheet;
    }

    public AVT getLiteralResultAttribute(String string) {
        block3 : {
            Object object = this.m_avts;
            if (object == null) break block3;
            for (int i = object.size() - 1; i >= 0; --i) {
                AVT aVT;
                block5 : {
                    block4 : {
                        aVT = (AVT)this.m_avts.get(i);
                        object = aVT.getURI();
                        if (object == null || ((String)object).equals(EMPTYSTRING)) break block4;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object);
                        stringBuilder.append(":");
                        stringBuilder.append(aVT.getName());
                        if (stringBuilder.toString().equals(string)) break block5;
                    }
                    if (object != null && !((String)object).equals(EMPTYSTRING) || !aVT.getRawName().equals(string)) continue;
                }
                return aVT;
            }
        }
        return null;
    }

    public AVT getLiteralResultAttributeNS(String string, String string2) {
        Object object = this.m_avts;
        if (object != null) {
            for (int i = object.size() - 1; i >= 0; --i) {
                object = (AVT)this.m_avts.get(i);
                if (!((AVT)object).getName().equals(string2) || !((AVT)object).getURI().equals(string)) continue;
                return object;
            }
        }
        return null;
    }

    @Override
    public String getLocalName() {
        return this.m_localName;
    }

    public String getNamespace() {
        return this.m_namespace;
    }

    @Override
    public String getNodeName() {
        return this.m_rawName;
    }

    @Override
    public String getPrefix() {
        int n = this.m_rawName.length() - this.m_localName.length() - 1;
        String string = n > 0 ? this.m_rawName.substring(0, n) : EMPTYSTRING;
        return string;
    }

    public String getRawName() {
        return this.m_rawName;
    }

    public String getVersion() {
        return this.m_version;
    }

    @Override
    public int getXSLToken() {
        return 77;
    }

    @Override
    boolean needToCheckExclude() {
        if (this.m_excludeResultPrefixes == null && this.getPrefixTable() == null && this.m_ExtensionElementURIs == null) {
            return false;
        }
        if (this.getPrefixTable() == null) {
            this.setPrefixTable(new ArrayList());
        }
        return true;
    }

    @Override
    public void resolvePrefixTables() throws TransformerException {
        CharSequence charSequence;
        super.resolvePrefixTables();
        StylesheetRoot stylesheetRoot = this.getStylesheetRoot();
        Object object = this.m_namespace;
        if (object != null && ((String)object).length() > 0 && (object = stylesheetRoot.getNamespaceAliasComposed(this.m_namespace)) != null) {
            this.m_namespace = ((NamespaceAlias)object).getResultNamespace();
            if ((object = ((NamespaceAlias)object).getStylesheetPrefix()) != null && ((String)object).length() > 0) {
                charSequence = new StringBuilder();
                charSequence.append((String)object);
                charSequence.append(":");
                charSequence.append(this.m_localName);
                this.m_rawName = charSequence.toString();
            } else {
                this.m_rawName = this.m_localName;
            }
        }
        if ((object = this.m_avts) != null) {
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                AVT aVT = (AVT)this.m_avts.get(i);
                object = aVT.getURI();
                if (object == null || ((String)object).length() <= 0 || (object = stylesheetRoot.getNamespaceAliasComposed(this.m_namespace)) == null) continue;
                String string = ((NamespaceAlias)object).getResultNamespace();
                String string2 = ((NamespaceAlias)object).getStylesheetPrefix();
                charSequence = aVT.getName();
                object = charSequence;
                if (string2 != null) {
                    object = charSequence;
                    if (string2.length() > 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append(":");
                        ((StringBuilder)object).append((String)charSequence);
                        object = ((StringBuilder)object).toString();
                    }
                }
                aVT.setURI(string);
                aVT.setRawName((String)object);
            }
        }
    }

    public void setExcludeResultPrefixes(StringVector stringVector) {
        this.m_excludeResultPrefixes = stringVector;
    }

    public void setExtensionElementPrefixes(StringVector stringVector) {
        this.m_ExtensionElementURIs = stringVector;
    }

    public void setIsLiteralResultAsStylesheet(boolean bl) {
        this.isLiteralResultAsStylesheet = bl;
    }

    public void setLocalName(String string) {
        this.m_localName = string;
    }

    public void setNamespace(String string) {
        String string2 = string;
        if (string == null) {
            string2 = EMPTYSTRING;
        }
        this.m_namespace = string2;
    }

    public void setRawName(String string) {
        this.m_rawName = string;
    }

    public void setVersion(String string) {
        this.m_version = string;
    }

    public void setXmlSpace(AVT object) {
        this.addLiteralResultAttribute((AVT)object);
        object = ((AVT)object).getSimpleString();
        if (((String)object).equals("default")) {
            super.setXmlSpace(2);
        } else if (((String)object).equals("preserve")) {
            super.setXmlSpace(1);
        }
    }

    public void throwDOMException(short s, String string) {
        throw new DOMException(s, XSLMessages.createMessage(string, null));
    }

    public class Attribute
    implements Attr {
        private AVT m_attribute;
        private Element m_owner = null;

        public Attribute(AVT aVT, Element element) {
            this.m_attribute = aVT;
            this.m_owner = element;
        }

        @Override
        public Node appendChild(Node node) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public Node cloneNode(boolean bl) {
            return new Attribute(this.m_attribute, this.m_owner);
        }

        @Override
        public short compareDocumentPosition(Node node) throws DOMException {
            return 0;
        }

        @Override
        public NamedNodeMap getAttributes() {
            return null;
        }

        @Override
        public String getBaseURI() {
            return null;
        }

        @Override
        public NodeList getChildNodes() {
            return new NodeList(){

                @Override
                public int getLength() {
                    return 0;
                }

                @Override
                public Node item(int n) {
                    return null;
                }
            };
        }

        @Override
        public Object getFeature(String object, String string) {
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
            return this.m_attribute.getName();
        }

        @Override
        public String getName() {
            return this.m_attribute.getName();
        }

        @Override
        public String getNamespaceURI() {
            String string;
            block0 : {
                string = this.m_attribute.getURI();
                if (!string.equals(ElemLiteralResult.EMPTYSTRING)) break block0;
                string = null;
            }
            return string;
        }

        @Override
        public Node getNextSibling() {
            return null;
        }

        @Override
        public String getNodeName() {
            String string = this.m_attribute.getURI();
            String string2 = this.getLocalName();
            if (!string.equals(ElemLiteralResult.EMPTYSTRING)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(":");
                stringBuilder.append(string2);
                string2 = stringBuilder.toString();
            }
            return string2;
        }

        @Override
        public short getNodeType() {
            return 2;
        }

        @Override
        public String getNodeValue() throws DOMException {
            return this.m_attribute.getSimpleString();
        }

        @Override
        public Document getOwnerDocument() {
            return this.m_owner.getOwnerDocument();
        }

        @Override
        public Element getOwnerElement() {
            return this.m_owner;
        }

        @Override
        public Node getParentNode() {
            return this.m_owner;
        }

        @Override
        public String getPrefix() {
            String string = this.m_attribute.getURI();
            String string2 = this.m_attribute.getRawName();
            string2 = string.equals(ElemLiteralResult.EMPTYSTRING) ? null : string2.substring(0, string2.indexOf(":"));
            return string2;
        }

        @Override
        public Node getPreviousSibling() {
            return null;
        }

        @Override
        public TypeInfo getSchemaTypeInfo() {
            return null;
        }

        @Override
        public boolean getSpecified() {
            return true;
        }

        @Override
        public String getTextContent() throws DOMException {
            return this.getNodeValue();
        }

        @Override
        public Object getUserData(String string) {
            return this.getOwnerDocument().getUserData(string);
        }

        @Override
        public String getValue() {
            return this.m_attribute.getSimpleString();
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
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public boolean isDefaultNamespace(String string) {
            return false;
        }

        @Override
        public boolean isEqualNode(Node node) {
            boolean bl = node == this;
            return bl;
        }

        @Override
        public boolean isId() {
            return false;
        }

        @Override
        public boolean isSameNode(Node node) {
            boolean bl = this == node;
            return bl;
        }

        @Override
        public boolean isSupported(String string, String string2) {
            return false;
        }

        @Override
        public String lookupNamespaceURI(String string) {
            return null;
        }

        @Override
        public String lookupPrefix(String string) {
            return null;
        }

        @Override
        public void normalize() {
        }

        @Override
        public Node removeChild(Node node) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public Node replaceChild(Node node, Node node2) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public void setNodeValue(String string) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
        }

        @Override
        public void setPrefix(String string) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
        }

        @Override
        public void setTextContent(String string) throws DOMException {
            this.setNodeValue(string);
        }

        @Override
        public Object setUserData(String string, Object object, UserDataHandler userDataHandler) {
            return this.getOwnerDocument().setUserData(string, object, userDataHandler);
        }

        @Override
        public void setValue(String string) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
        }

    }

    public class LiteralElementAttributes
    implements NamedNodeMap {
        private int m_count = -1;

        @Override
        public int getLength() {
            if (this.m_count == -1) {
                this.m_count = ElemLiteralResult.this.m_avts != null ? ElemLiteralResult.this.m_avts.size() : 0;
            }
            return this.m_count;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public Node getNamedItem(String var1_1) {
            if (this.getLength() == 0) {
                return null;
            }
            var2_2 = null;
            var3_3 = var1_1;
            var4_4 = var1_1.indexOf(":");
            if (-1 != var4_4) {
                var2_2 = var1_1.substring(0, var4_4);
                var3_3 = var1_1.substring(var4_4 + 1);
            }
            var5_5 = null;
            var6_6 = ElemLiteralResult.access$000(ElemLiteralResult.this).iterator();
            do lbl-1000: // 3 sources:
            {
                var1_1 = var5_5;
                if (var6_6.hasNext() == false) return var1_1;
                var1_1 = (AVT)var6_6.next();
                if (!var3_3.equals(var1_1.getName())) ** GOTO lbl-1000
                var7_7 = var1_1.getURI();
            } while ((var2_2 != null || var7_7 != null) && (var2_2 == null || !var2_2.equals(var7_7)));
            var2_2 = ElemLiteralResult.this;
            return (ElemLiteralResult)var2_2.new Attribute((AVT)var1_1, (Element)var2_2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public Node getNamedItemNS(String var1_1, String var2_2) {
            if (this.getLength() == 0) {
                return null;
            }
            var3_3 = null;
            var4_4 = ElemLiteralResult.access$000(ElemLiteralResult.this).iterator();
            do lbl-1000: // 3 sources:
            {
                var5_5 = var3_3;
                if (var4_4.hasNext() == false) return var5_5;
                var5_5 = (AVT)var4_4.next();
                if (!var2_2.equals(var5_5.getName())) ** GOTO lbl-1000
                var6_6 = var5_5.getURI();
            } while ((var1_1 != null || var6_6 != null) && (var1_1 == null || !var1_1.equals(var6_6)));
            var1_1 = ElemLiteralResult.this;
            return (ElemLiteralResult)var1_1.new Attribute((AVT)var5_5, (Element)var1_1);
        }

        @Override
        public Node item(int n) {
            if (this.getLength() != 0 && n < ElemLiteralResult.this.m_avts.size()) {
                ElemLiteralResult elemLiteralResult = ElemLiteralResult.this;
                return elemLiteralResult.new Attribute((AVT)elemLiteralResult.m_avts.get(n), ElemLiteralResult.this);
            }
            return null;
        }

        @Override
        public Node removeNamedItem(String string) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public Node removeNamedItemNS(String string, String string2) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public Node setNamedItem(Node node) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }

        @Override
        public Node setNamedItemNS(Node node) throws DOMException {
            ElemLiteralResult.this.throwDOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
            return null;
        }
    }

}

