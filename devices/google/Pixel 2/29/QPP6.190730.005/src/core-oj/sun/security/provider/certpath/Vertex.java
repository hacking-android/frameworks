/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Debug;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.X509CertImpl;

public class Vertex {
    private static final Debug debug = Debug.getInstance("certpath");
    private X509Certificate cert;
    private int index;
    private Throwable throwable;

    Vertex(X509Certificate x509Certificate) {
        this.cert = x509Certificate;
        this.index = -1;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String certToString() {
        int n;
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        Object object = X509CertImpl.toImpl(this.cert);
        stringBuilder.append("Issuer:     ");
        stringBuilder.append(((X509CertImpl)object).getIssuerX500Principal());
        stringBuilder.append("\n");
        stringBuilder.append("Subject:    ");
        stringBuilder.append(((X509CertImpl)object).getSubjectX500Principal());
        stringBuilder.append("\n");
        stringBuilder.append("SerialNum:  ");
        stringBuilder.append(((X509CertImpl)object).getSerialNumber().toString(16));
        stringBuilder.append("\n");
        stringBuilder.append("Expires:    ");
        stringBuilder.append(((X509CertImpl)object).getNotAfter().toString());
        stringBuilder.append("\n");
        Object object2 = ((X509CertImpl)object).getIssuerUniqueID();
        int n3 = 0;
        if (object2 != null) {
            stringBuilder.append("IssuerUID:  ");
            n = ((boolean[])object2).length;
            for (n2 = 0; n2 < n; ++n2) {
                stringBuilder.append((int)object2[n2]);
            }
            stringBuilder.append("\n");
        }
        if ((object2 = ((X509CertImpl)object).getSubjectUniqueID()) != null) {
            stringBuilder.append("SubjectUID: ");
            n = ((boolean[])object2).length;
            for (n2 = n3; n2 < n; ++n2) {
                stringBuilder.append((int)object2[n2]);
            }
            stringBuilder.append("\n");
        }
        try {
            object2 = ((X509CertImpl)object).getSubjectKeyIdentifierExtension();
            if (object2 != null) {
                object2 = ((SubjectKeyIdentifierExtension)object2).get("key_id");
                stringBuilder.append("SubjKeyID:  ");
                stringBuilder.append(((KeyIdentifier)object2).toString());
            }
            if ((object = ((X509CertImpl)object).getAuthorityKeyIdentifierExtension()) == null) return stringBuilder.toString();
            object = (KeyIdentifier)((AuthorityKeyIdentifierExtension)object).get("key_id");
            stringBuilder.append("AuthKeyID:  ");
            stringBuilder.append(((KeyIdentifier)object).toString());
            return stringBuilder.toString();
        }
        catch (IOException iOException) {
            object2 = debug;
            if (object2 == null) return stringBuilder.toString();
            ((Debug)object2).println("Vertex.certToString() unexpected exception");
            iOException.printStackTrace();
        }
        return stringBuilder.toString();
        catch (CertificateException certificateException) {
            Debug debug = Vertex.debug;
            if (debug == null) return stringBuilder.toString();
            debug.println("Vertex.certToString() unexpected exception");
            certificateException.printStackTrace();
            return stringBuilder.toString();
        }
    }

    public X509Certificate getCertificate() {
        return this.cert;
    }

    public int getIndex() {
        return this.index;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public String indexToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:      ");
        stringBuilder.append(this.index);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String moreToString() {
        StringBuilder stringBuilder = new StringBuilder("Last cert?  ");
        String string = this.index == -1 ? "Yes" : "No";
        stringBuilder.append(string);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    void setIndex(int n) {
        this.index = n;
    }

    void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String throwableToString() {
        StringBuilder stringBuilder = new StringBuilder("Exception:  ");
        Throwable throwable = this.throwable;
        if (throwable != null) {
            stringBuilder.append(throwable.toString());
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.certToString());
        stringBuilder.append(this.throwableToString());
        stringBuilder.append(this.indexToString());
        return stringBuilder.toString();
    }
}

