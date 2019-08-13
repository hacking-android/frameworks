/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.Certificate;
import java.security.IdentityScope;
import java.security.KeyManagementException;
import java.security.Principal;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Vector;

@Deprecated
public abstract class Identity
implements Principal,
Serializable {
    private static final long serialVersionUID = 3609922007826600659L;
    Vector<Certificate> certificates;
    String info = "No further information available.";
    private String name;
    private PublicKey publicKey;
    IdentityScope scope;

    protected Identity() {
        this("restoring...");
    }

    public Identity(String string) {
        this.name = string;
    }

    public Identity(String string, IdentityScope identityScope) throws KeyManagementException {
        this(string);
        if (identityScope != null) {
            identityScope.addIdentity(this);
        }
        this.scope = identityScope;
    }

    private static void check(String string) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(string);
        }
    }

    private boolean keyEquals(PublicKey publicKey, PublicKey publicKey2) {
        String string = publicKey.getFormat();
        String string2 = publicKey2.getFormat();
        boolean bl = true;
        boolean bl2 = string == null;
        if (string2 != null) {
            bl = false;
        }
        if (bl ^ bl2) {
            return false;
        }
        if (string != null && string2 != null && !string.equalsIgnoreCase(string2)) {
            return false;
        }
        return Arrays.equals(publicKey.getEncoded(), publicKey2.getEncoded());
    }

    public void addCertificate(Certificate certificate) throws KeyManagementException {
        PublicKey publicKey;
        Identity.check("addIdentityCertificate");
        if (this.certificates == null) {
            this.certificates = new Vector();
        }
        if ((publicKey = this.publicKey) != null) {
            if (!this.keyEquals(publicKey, certificate.getPublicKey())) {
                throw new KeyManagementException("public key different from cert public key");
            }
        } else {
            this.publicKey = certificate.getPublicKey();
        }
        this.certificates.addElement(certificate);
    }

    public Certificate[] certificates() {
        Object[] arrobject = this.certificates;
        if (arrobject == null) {
            return new Certificate[0];
        }
        arrobject = new Certificate[arrobject.size()];
        this.certificates.copyInto(arrobject);
        return arrobject;
    }

    @Override
    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Identity) {
            object = (Identity)object;
            if (this.fullName().equals(((Identity)object).fullName())) {
                return true;
            }
            return this.identityEquals((Identity)object);
        }
        return false;
    }

    String fullName() {
        String string = this.name;
        CharSequence charSequence = string;
        if (this.scope != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(".");
            ((StringBuilder)charSequence).append(this.scope.getName());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public String getInfo() {
        return this.info;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public final IdentityScope getScope() {
        return this.scope;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    protected boolean identityEquals(Identity serializable) {
        boolean bl;
        if (!this.name.equalsIgnoreCase(serializable.name)) {
            return false;
        }
        boolean bl2 = this.publicKey == null;
        if (bl2 ^ (bl = serializable.publicKey == null)) {
            return false;
        }
        PublicKey publicKey = this.publicKey;
        return publicKey == null || (serializable = serializable.publicKey) == null || publicKey.equals(serializable);
    }

    String printCertificates() {
        if (this.certificates == null) {
            return "\tno certificates";
        }
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("");
        ((StringBuilder)charSequence).append("\tcertificates: \n");
        charSequence = ((StringBuilder)charSequence).toString();
        int n = 1;
        for (Certificate certificate : this.certificates) {
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append("\tcertificate ");
            charSequence2.append(n);
            charSequence2.append("\tfor  : ");
            charSequence2.append(certificate.getPrincipal());
            charSequence2.append("\n");
            charSequence2 = charSequence2.toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("\t\t\tfrom : ");
            ((StringBuilder)charSequence).append(certificate.getGuarantor());
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
            ++n;
        }
        return charSequence;
    }

    String printKeys() {
        String string = this.publicKey != null ? "\tpublic key initialized" : "\tno public key";
        return string;
    }

    public void removeCertificate(Certificate certificate) throws KeyManagementException {
        Identity.check("removeIdentityCertificate");
        Vector<Certificate> vector = this.certificates;
        if (vector != null) {
            if (certificate != null && vector.contains(certificate)) {
                this.certificates.removeElement(certificate);
            } else {
                throw new KeyManagementException();
            }
        }
    }

    public void setInfo(String string) {
        Identity.check("setIdentityInfo");
        this.info = string;
    }

    public void setPublicKey(PublicKey publicKey) throws KeyManagementException {
        Identity.check("setIdentityPublicKey");
        this.publicKey = publicKey;
        this.certificates = new Vector();
    }

    @Override
    public String toString() {
        Identity.check("printIdentity");
        String string = this.name;
        CharSequence charSequence = string;
        if (this.scope != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("[");
            ((StringBuilder)charSequence).append(this.scope.getName());
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public String toString(boolean bl) {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = this.toString();
        if (bl) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.printKeys());
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("\n");
            ((StringBuilder)charSequence).append(this.printCertificates());
            charSequence2 = ((StringBuilder)charSequence).toString();
            if (this.info != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("\n\t");
                ((StringBuilder)charSequence).append(this.info);
                charSequence2 = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("\n\tno additional information available.");
                charSequence2 = ((StringBuilder)charSequence).toString();
            }
        }
        return charSequence2;
    }
}

