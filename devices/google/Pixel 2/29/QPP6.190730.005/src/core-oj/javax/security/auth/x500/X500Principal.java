/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth.x500;

import java.io.IOException;
import java.io.InputStream;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ResourcesMgr;
import sun.security.x509.X500Name;

public final class X500Principal
implements Principal,
Serializable {
    public static final String CANONICAL = "CANONICAL";
    public static final String RFC1779 = "RFC1779";
    public static final String RFC2253 = "RFC2253";
    private static final long serialVersionUID = -500463348111345721L;
    private transient X500Name thisX500Name;

    public X500Principal(InputStream object) {
        if (object != null) {
            try {
                X500Name x500Name;
                if (((InputStream)object).markSupported()) {
                    ((InputStream)object).mark(((InputStream)object).available() + 1);
                }
                DerValue derValue = new DerValue((InputStream)object);
                this.thisX500Name = x500Name = new X500Name(derValue.data);
                return;
            }
            catch (Exception exception) {
                if (((InputStream)object).markSupported()) {
                    try {
                        ((InputStream)object).reset();
                    }
                    catch (IOException iOException) {
                        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("improperly specified input stream and unable to reset input stream");
                        illegalArgumentException.initCause(exception);
                        throw illegalArgumentException;
                    }
                }
                object = new IllegalArgumentException("improperly specified input stream");
                ((Throwable)object).initCause(exception);
                throw object;
            }
        }
        throw new NullPointerException("provided null input stream");
    }

    public X500Principal(String string) {
        this(string, Collections.emptyMap());
    }

    public X500Principal(String object, Map<String, String> map) {
        if (object != null) {
            if (map != null) {
                try {
                    X500Name x500Name;
                    this.thisX500Name = x500Name = new X500Name((String)object, map);
                    return;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("improperly specified input name: ");
                    stringBuilder.append((String)object);
                    object = new IllegalArgumentException(stringBuilder.toString());
                    ((Throwable)object).initCause(exception);
                    throw object;
                }
            }
            throw new NullPointerException(ResourcesMgr.getString("provided.null.keyword.map"));
        }
        throw new NullPointerException(ResourcesMgr.getString("provided.null.name"));
    }

    X500Principal(X500Name x500Name) {
        this.thisX500Name = x500Name;
    }

    public X500Principal(byte[] object) {
        try {
            X500Name x500Name;
            this.thisX500Name = x500Name = new X500Name((byte[])object);
            return;
        }
        catch (Exception exception) {
            object = new IllegalArgumentException("improperly specified input name");
            ((Throwable)object).initCause(exception);
            throw object;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, NotActiveException, ClassNotFoundException {
        this.thisX500Name = new X500Name((byte[])objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.thisX500Name.getEncodedInternal());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof X500Principal)) {
            return false;
        }
        object = (X500Principal)object;
        return this.thisX500Name.equals(((X500Principal)object).thisX500Name);
    }

    public byte[] getEncoded() {
        try {
            byte[] arrby = this.thisX500Name.getEncoded();
            return arrby;
        }
        catch (IOException iOException) {
            throw new RuntimeException("unable to get encoding", iOException);
        }
    }

    @Override
    public String getName() {
        return this.getName(RFC2253);
    }

    public String getName(String string) {
        if (string != null) {
            if (string.equalsIgnoreCase(RFC1779)) {
                return this.thisX500Name.getRFC1779Name();
            }
            if (string.equalsIgnoreCase(RFC2253)) {
                return this.thisX500Name.getRFC2253Name();
            }
            if (string.equalsIgnoreCase(CANONICAL)) {
                return this.thisX500Name.getRFC2253CanonicalName();
            }
        }
        throw new IllegalArgumentException("invalid format specified");
    }

    public String getName(String string, Map<String, String> map) {
        if (map != null) {
            if (string != null) {
                if (string.equalsIgnoreCase(RFC1779)) {
                    return this.thisX500Name.getRFC1779Name(map);
                }
                if (string.equalsIgnoreCase(RFC2253)) {
                    return this.thisX500Name.getRFC2253Name(map);
                }
            }
            throw new IllegalArgumentException("invalid format specified");
        }
        throw new NullPointerException(ResourcesMgr.getString("provided.null.OID.map"));
    }

    @Override
    public int hashCode() {
        return this.thisX500Name.hashCode();
    }

    @Override
    public String toString() {
        return this.thisX500Name.toString();
    }
}

