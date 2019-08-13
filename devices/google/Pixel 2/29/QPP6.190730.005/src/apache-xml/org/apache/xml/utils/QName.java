/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Serializable;
import java.util.Stack;
import java.util.StringTokenizer;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.NameSpace;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.XML11Char;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class QName
implements Serializable {
    public static final String S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace";
    static final long serialVersionUID = 467434581652829920L;
    protected String _localName;
    protected String _namespaceURI;
    protected String _prefix;
    private int m_hashCode;

    public QName() {
    }

    public QName(String string) {
        this(string, false);
    }

    public QName(String string, String string2) {
        this(string, string2, false);
    }

    public QName(String string, String string2, String string3) {
        this(string, string2, string3, false);
    }

    public QName(String string, String string2, String string3, boolean bl) {
        if (string3 != null) {
            if (bl) {
                if (XML11Char.isXML11ValidNCName(string3)) {
                    if (string2 != null && !XML11Char.isXML11ValidNCName(string2)) {
                        throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_PREFIX_INVALID", null));
                    }
                } else {
                    throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
                }
            }
            this._namespaceURI = string;
            this._prefix = string2;
            this._localName = string3;
            this.m_hashCode = this.toString().hashCode();
            return;
        }
        throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
    }

    public QName(String string, String string2, boolean bl) {
        if (string2 != null) {
            if (bl && !XML11Char.isXML11ValidNCName(string2)) {
                throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
            }
            this._namespaceURI = string;
            this._localName = string2;
            this.m_hashCode = this.toString().hashCode();
            return;
        }
        throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
    }

    public QName(String string, Stack stack) {
        this(string, stack, false);
    }

    public QName(String string, Stack stack, boolean bl) {
        String string2 = null;
        NameSpace nameSpace = null;
        String string3 = null;
        int n = string.indexOf(58);
        if (n > 0) {
            String string4 = string.substring(0, n);
            if (string4.equals("xml")) {
                string2 = S_XMLNAMESPACEURI;
            } else {
                if (string4.equals("xmlns")) {
                    return;
                }
                int n2 = stack.size() - 1;
                string3 = nameSpace;
                do {
                    int n3;
                    string2 = string3;
                    if (n2 < 0) break;
                    nameSpace = (NameSpace)stack.elementAt(n2);
                    do {
                        string2 = string3;
                        n3 = n2;
                        if (nameSpace == null) break;
                        if (nameSpace.m_prefix != null && string4.equals(nameSpace.m_prefix)) {
                            string2 = nameSpace.m_uri;
                            n3 = -1;
                            break;
                        }
                        nameSpace = nameSpace.m_next;
                    } while (true);
                    n2 = n3 - 1;
                    string3 = string2;
                } while (true);
            }
            if (string2 != null) {
                string3 = string4;
            } else {
                throw new RuntimeException(XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[]{string4}));
            }
        }
        if (n >= 0) {
            string = string.substring(n + 1);
        }
        this._localName = string;
        if (bl && ((string = this._localName) == null || !XML11Char.isXML11ValidNCName(string))) {
            throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
        }
        this._namespaceURI = string2;
        this._prefix = string3;
        this.m_hashCode = this.toString().hashCode();
    }

    public QName(String string, PrefixResolver prefixResolver) {
        this(string, prefixResolver, false);
    }

    public QName(String string, PrefixResolver object, boolean bl) {
        block7 : {
            block6 : {
                int n;
                String string2;
                block4 : {
                    block5 : {
                        string2 = null;
                        this._namespaceURI = null;
                        n = string.indexOf(58);
                        if (n <= 0) break block4;
                        string2 = string.substring(0, n);
                        this._namespaceURI = string2.equals("xml") ? S_XMLNAMESPACEURI : object.getNamespaceForPrefix(string2);
                        if (this._namespaceURI == null) break block5;
                        this._localName = string.substring(n + 1);
                        string = string2;
                        break block6;
                    }
                    throw new RuntimeException(XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[]{string2}));
                }
                if (n == 0) break block7;
                this._localName = string;
                string = string2;
            }
            if (bl && ((object = this._localName) == null || !XML11Char.isXML11ValidNCName((String)object))) {
                throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
            }
            this.m_hashCode = this.toString().hashCode();
            this._prefix = string;
            return;
        }
        throw new RuntimeException(XMLMessages.createXMLMessage("ER_NAME_CANT_START_WITH_COLON", null));
    }

    public QName(String string, Element element, PrefixResolver prefixResolver) {
        this(string, element, prefixResolver, false);
    }

    public QName(String string, Element element, PrefixResolver prefixResolver, boolean bl) {
        this._namespaceURI = null;
        int n = string.indexOf(58);
        if (n > 0 && element != null) {
            String string2;
            this._prefix = string2 = string.substring(0, n);
            if (string2.equals("xml")) {
                this._namespaceURI = S_XMLNAMESPACEURI;
            } else {
                if (string2.equals("xmlns")) {
                    return;
                }
                this._namespaceURI = prefixResolver.getNamespaceForPrefix(string2, element);
            }
            if (this._namespaceURI == null) {
                throw new RuntimeException(XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[]{string2}));
            }
        }
        if (n >= 0) {
            string = string.substring(n + 1);
        }
        this._localName = string;
        if (bl && ((string = this._localName) == null || !XML11Char.isXML11ValidNCName(string))) {
            throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
        }
        this.m_hashCode = this.toString().hashCode();
    }

    public QName(String string, boolean bl) {
        if (string != null) {
            if (bl && !XML11Char.isXML11ValidNCName(string)) {
                throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
            }
            this._namespaceURI = null;
            this._localName = string;
            this.m_hashCode = this.toString().hashCode();
            return;
        }
        throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
    }

    public static String getLocalPart(String string) {
        int n = string.indexOf(58);
        if (n >= 0) {
            string = string.substring(n + 1);
        }
        return string;
    }

    public static String getPrefixFromXMLNSDecl(String string) {
        int n = string.indexOf(58);
        string = n >= 0 ? string.substring(n + 1) : "";
        return string;
    }

    public static String getPrefixPart(String string) {
        int n = string.indexOf(58);
        string = n >= 0 ? string.substring(0, n) : "";
        return string;
    }

    public static QName getQNameFromString(String object) {
        object = new StringTokenizer((String)object, "{}", false);
        String string = ((StringTokenizer)object).nextToken();
        object = ((StringTokenizer)object).hasMoreTokens() ? ((StringTokenizer)object).nextToken() : null;
        object = object == null ? new QName(null, string) : new QName(string, (String)object);
        return object;
    }

    public static boolean isXMLNSDecl(String string) {
        boolean bl = string.startsWith("xmlns") && (string.equals("xmlns") || string.startsWith("xmlns:"));
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof QName) {
            QName qName = (QName)object;
            String string = this.getNamespaceURI();
            object = qName.getNamespaceURI();
            if (!this.getLocalName().equals(qName.getLocalName()) || !(string != null && object != null ? string.equals(object) : string == null && object == null)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public boolean equals(String string, String string2) {
        String string3 = this.getNamespaceURI();
        boolean bl = this.getLocalName().equals(string2) && (string3 != null && string != null ? string3.equals(string) : string3 == null && string == null);
        return bl;
    }

    public String getLocalName() {
        return this._localName;
    }

    public String getLocalPart() {
        return this.getLocalName();
    }

    public String getNamespace() {
        return this.getNamespaceURI();
    }

    public String getNamespaceURI() {
        return this._namespaceURI;
    }

    public String getPrefix() {
        return this._prefix;
    }

    public int hashCode() {
        return this.m_hashCode;
    }

    public String toNamespacedString() {
        CharSequence charSequence;
        if (this._namespaceURI != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("{");
            ((StringBuilder)charSequence).append(this._namespaceURI);
            ((StringBuilder)charSequence).append("}");
            ((StringBuilder)charSequence).append(this._localName);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this._localName;
        }
        return charSequence;
    }

    public String toString() {
        CharSequence charSequence;
        if (this._prefix != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this._prefix);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(this._localName);
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (this._namespaceURI != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("{");
            ((StringBuilder)charSequence).append(this._namespaceURI);
            ((StringBuilder)charSequence).append("}");
            ((StringBuilder)charSequence).append(this._localName);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this._localName;
        }
        return charSequence;
    }
}

