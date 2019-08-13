/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.namespace;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class QName
implements Serializable {
    private static final long compatibilitySerialVersionUID = 4418622981026545151L;
    private static final long defaultSerialVersionUID = -9120448754896609940L;
    private static final long serialVersionUID;
    private final String localPart;
    private final String namespaceURI;
    private String prefix;
    private transient String qNameAsString;

    static {
        long l = !"1.0".equals(System.getProperty("org.apache.xml.namespace.QName.useCompatibleSerialVersionUID")) ? -9120448754896609940L : 4418622981026545151L;
        serialVersionUID = l;
    }

    public QName(String string) {
        this("", string, "");
    }

    public QName(String string, String string2) {
        this(string, string2, "");
    }

    public QName(String string, String string2, String string3) {
        this.namespaceURI = string == null ? "" : string;
        if (string2 != null) {
            this.localPart = string2;
            if (string3 != null) {
                this.prefix = string3;
                return;
            }
            throw new IllegalArgumentException("prefix cannot be \"null\" when creating a QName");
        }
        throw new IllegalArgumentException("local part cannot be \"null\" when creating a QName");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.prefix == null) {
            this.prefix = "";
        }
    }

    public static QName valueOf(String string) {
        if (string != null) {
            if (string.length() == 0) {
                return new QName("", string, "");
            }
            if (string.charAt(0) != '{') {
                return new QName("", string, "");
            }
            if (!string.startsWith("{}")) {
                int n = string.indexOf(125);
                if (n != -1) {
                    return new QName(string.substring(1, n), string.substring(n + 1), "");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("cannot create QName from \"");
                stringBuilder.append(string);
                stringBuilder.append("\", missing closing \"}\"");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Namespace URI .equals(XMLConstants.NULL_NS_URI), .equals(\"\"), only the local part, \"");
            stringBuilder.append(string.substring("".length() + 2));
            stringBuilder.append("\", should be provided.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new IllegalArgumentException("cannot create QName from \"null\" or \"\" String");
    }

    public final boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof QName) {
            object = (QName)object;
            if (!this.localPart.equals(((QName)object).localPart) || !this.namespaceURI.equals(((QName)object).namespaceURI)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getLocalPart() {
        return this.localPart;
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public final int hashCode() {
        return this.namespaceURI.hashCode() ^ this.localPart.hashCode();
    }

    public String toString() {
        String string = this.qNameAsString;
        CharSequence charSequence = string;
        if (string == null) {
            int n = this.namespaceURI.length();
            if (n == 0) {
                charSequence = this.localPart;
            } else {
                charSequence = new StringBuilder(this.localPart.length() + n + 2);
                ((StringBuilder)charSequence).append('{');
                ((StringBuilder)charSequence).append(this.namespaceURI);
                ((StringBuilder)charSequence).append('}');
                ((StringBuilder)charSequence).append(this.localPart);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            this.qNameAsString = charSequence;
        }
        return charSequence;
    }
}

