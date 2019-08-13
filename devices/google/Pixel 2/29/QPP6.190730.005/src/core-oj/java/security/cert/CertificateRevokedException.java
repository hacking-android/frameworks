/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.CRLReason;
import java.security.cert.CertificateException;
import java.security.cert.Extension;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.InvalidityDateExtension;

public class CertificateRevokedException
extends CertificateException {
    private static final long serialVersionUID = 7839996631571608627L;
    private final X500Principal authority;
    private transient Map<String, Extension> extensions;
    private final CRLReason reason;
    private Date revocationDate;

    public CertificateRevokedException(Date date, CRLReason cRLReason, X500Principal x500Principal, Map<String, Extension> map) {
        if (date != null && cRLReason != null && x500Principal != null && map != null) {
            this.revocationDate = new Date(date.getTime());
            this.reason = cRLReason;
            this.authority = x500Principal;
            this.extensions = Collections.checkedMap(new HashMap(), String.class, Extension.class);
            this.extensions.putAll(map);
            return;
        }
        throw new NullPointerException();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.revocationDate = new Date(this.revocationDate.getTime());
        int n = objectInputStream.readInt();
        this.extensions = n == 0 ? Collections.emptyMap() : new HashMap<String, Extension>(n);
        for (int i = 0; i < n; ++i) {
            String string = (String)objectInputStream.readObject();
            boolean bl = objectInputStream.readBoolean();
            Object object = new byte[objectInputStream.readInt()];
            objectInputStream.readFully((byte[])object);
            object = sun.security.x509.Extension.newExtension(new ObjectIdentifier(string), bl, object);
            this.extensions.put(string, (Extension)object);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.extensions.size());
        Iterator<Map.Entry<String, Extension>> iterator = this.extensions.entrySet().iterator();
        while (iterator.hasNext()) {
            byte[] arrby = iterator.next().getValue();
            objectOutputStream.writeObject(arrby.getId());
            objectOutputStream.writeBoolean(arrby.isCritical());
            arrby = arrby.getValue();
            objectOutputStream.writeInt(arrby.length);
            objectOutputStream.write(arrby);
        }
    }

    public X500Principal getAuthorityName() {
        return this.authority;
    }

    public Map<String, Extension> getExtensions() {
        return Collections.unmodifiableMap(this.extensions);
    }

    public Date getInvalidityDate() {
        Object object = this.getExtensions().get("2.5.29.24");
        if (object == null) {
            return null;
        }
        try {
            object = new Date(((Date)InvalidityDateExtension.toImpl((Extension)object).get("DATE")).getTime());
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Certificate has been revoked, reason: ");
        stringBuilder.append((Object)this.reason);
        stringBuilder.append(", revocation date: ");
        stringBuilder.append(this.revocationDate);
        stringBuilder.append(", authority: ");
        stringBuilder.append(this.authority);
        stringBuilder.append(", extension OIDs: ");
        stringBuilder.append(this.extensions.keySet());
        return stringBuilder.toString();
    }

    public Date getRevocationDate() {
        return (Date)this.revocationDate.clone();
    }

    public CRLReason getRevocationReason() {
        return this.reason;
    }
}

