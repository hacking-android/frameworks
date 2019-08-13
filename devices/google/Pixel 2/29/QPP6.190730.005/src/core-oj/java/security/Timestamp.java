/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.List;

public final class Timestamp
implements Serializable {
    private static final long serialVersionUID = -5502683707821851294L;
    private transient int myhash = -1;
    private CertPath signerCertPath;
    private Date timestamp;

    public Timestamp(Date date, CertPath certPath) {
        if (date != null && certPath != null) {
            this.timestamp = new Date(date.getTime());
            this.signerCertPath = certPath;
            return;
        }
        throw new NullPointerException();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.myhash = -1;
        this.timestamp = new Date(this.timestamp.getTime());
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof Timestamp) {
            if (this == (object = (Timestamp)object)) {
                return true;
            }
            if (this.timestamp.equals(((Timestamp)object).getTimestamp()) && this.signerCertPath.equals(((Timestamp)object).getSignerCertPath())) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public CertPath getSignerCertPath() {
        return this.signerCertPath;
    }

    public Date getTimestamp() {
        return new Date(this.timestamp.getTime());
    }

    public int hashCode() {
        if (this.myhash == -1) {
            this.myhash = this.timestamp.hashCode() + this.signerCertPath.hashCode();
        }
        return this.myhash;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("(");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("timestamp: ");
        stringBuilder.append(this.timestamp);
        stringBuffer.append(stringBuilder.toString());
        List<? extends Certificate> list = this.signerCertPath.getCertificates();
        if (!list.isEmpty()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("TSA: ");
            stringBuilder.append(list.get(0));
            stringBuffer.append(stringBuilder.toString());
        } else {
            stringBuffer.append("TSA: <empty>");
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
}

