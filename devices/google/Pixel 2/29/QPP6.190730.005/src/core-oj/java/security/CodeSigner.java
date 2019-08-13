/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.util.List;

public final class CodeSigner
implements Serializable {
    private static final long serialVersionUID = 6819288105193937581L;
    private transient int myhash = -1;
    private CertPath signerCertPath;
    private Timestamp timestamp;

    public CodeSigner(CertPath certPath, Timestamp timestamp) {
        if (certPath != null) {
            this.signerCertPath = certPath;
            this.timestamp = timestamp;
            return;
        }
        throw new NullPointerException();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.myhash = -1;
    }

    public boolean equals(Object object) {
        if (object != null && object instanceof CodeSigner) {
            if (this == (object = (CodeSigner)object)) {
                return true;
            }
            Timestamp timestamp = ((CodeSigner)object).getTimestamp();
            Timestamp timestamp2 = this.timestamp;
            if (timestamp2 == null ? timestamp != null : timestamp == null || !timestamp2.equals(timestamp)) {
                return false;
            }
            return this.signerCertPath.equals(((CodeSigner)object).getSignerCertPath());
        }
        return false;
    }

    public CertPath getSignerCertPath() {
        return this.signerCertPath;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            this.myhash = this.timestamp == null ? this.signerCertPath.hashCode() : this.signerCertPath.hashCode() + this.timestamp.hashCode();
        }
        return this.myhash;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("(");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Signer: ");
        stringBuilder.append(this.signerCertPath.getCertificates().get(0));
        stringBuffer.append(stringBuilder.toString());
        if (this.timestamp != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("timestamp: ");
            stringBuilder.append(this.timestamp);
            stringBuffer.append(stringBuilder.toString());
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
}

