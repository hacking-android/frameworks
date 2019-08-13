/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.List;

public abstract class CertPath
implements Serializable {
    private static final long serialVersionUID = 6068470306649138683L;
    private String type;

    protected CertPath(String string) {
        this.type = string;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CertPath)) {
            return false;
        }
        if (!((CertPath)(object = (CertPath)object)).getType().equals(this.type)) {
            return false;
        }
        return this.getCertificates().equals(((CertPath)object).getCertificates());
    }

    public abstract List<? extends Certificate> getCertificates();

    public abstract byte[] getEncoded() throws CertificateEncodingException;

    public abstract byte[] getEncoded(String var1) throws CertificateEncodingException;

    public abstract Iterator<String> getEncodings();

    public String getType() {
        return this.type;
    }

    public int hashCode() {
        return this.type.hashCode() * 31 + this.getCertificates().hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<? extends Certificate> iterator = this.getCertificates().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(this.type);
        stringBuilder.append(" Cert Path: length = ");
        stringBuilder.append(this.getCertificates().size());
        stringBuilder.append(".\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("[\n");
        int n = 1;
        while (iterator.hasNext()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("=========================================================Certificate ");
            stringBuilder.append(n);
            stringBuilder.append(" start.\n");
            stringBuffer.append(stringBuilder.toString());
            stringBuffer.append(iterator.next().toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("\n=========================================================Certificate ");
            stringBuilder.append(n);
            stringBuilder.append(" end.\n\n\n");
            stringBuffer.append(stringBuilder.toString());
            ++n;
        }
        stringBuffer.append("\n]");
        return stringBuffer.toString();
    }

    protected Object writeReplace() throws ObjectStreamException {
        try {
            CertPathRep certPathRep = new CertPathRep(this.type, this.getEncoded());
            return certPathRep;
        }
        catch (CertificateException certificateException) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("java.security.cert.CertPath: ");
            ((StringBuilder)serializable).append(this.type);
            serializable = new NotSerializableException(((StringBuilder)serializable).toString());
            ((Throwable)serializable).initCause(certificateException);
            throw serializable;
        }
    }

    protected static class CertPathRep
    implements Serializable {
        private static final long serialVersionUID = 3015633072427920915L;
        private byte[] data;
        private String type;

        protected CertPathRep(String string, byte[] arrby) {
            this.type = string;
            this.data = arrby;
        }

        protected Object readResolve() throws ObjectStreamException {
            try {
                Object object = CertificateFactory.getInstance(this.type);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.data);
                object = ((CertificateFactory)object).generateCertPath(byteArrayInputStream);
                return object;
            }
            catch (CertificateException certificateException) {
                Serializable serializable = new StringBuilder();
                ((StringBuilder)serializable).append("java.security.cert.CertPath: ");
                ((StringBuilder)serializable).append(this.type);
                serializable = new NotSerializableException(((StringBuilder)serializable).toString());
                ((Throwable)serializable).initCause(certificateException);
                throw serializable;
            }
        }
    }

}

