/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;
import javax.crypto.spec.SecretKeySpec;

public class KeyRep
implements Serializable {
    private static final String PKCS8 = "PKCS#8";
    private static final String RAW = "RAW";
    private static final String X509 = "X.509";
    private static final long serialVersionUID = -4757683898830641853L;
    private String algorithm;
    private byte[] encoded;
    private String format;
    private Type type;

    public KeyRep(Type type, String string, String string2, byte[] arrby) {
        if (type != null && string != null && string2 != null && arrby != null) {
            this.type = type;
            this.algorithm = string;
            this.format = string2.toUpperCase(Locale.ENGLISH);
            this.encoded = (byte[])arrby.clone();
            return;
        }
        throw new NullPointerException("invalid null input(s)");
    }

    protected Object readResolve() throws ObjectStreamException {
        try {
            if (this.type == Type.SECRET && RAW.equals(this.format)) {
                return new SecretKeySpec(this.encoded, this.algorithm);
            }
            if (this.type == Type.PUBLIC && X509.equals(this.format)) {
                KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.encoded);
                return keyFactory.generatePublic(x509EncodedKeySpec);
            }
            if (this.type == Type.PRIVATE && PKCS8.equals(this.format)) {
                KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
                PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(this.encoded);
                return keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unrecognized type/format combination: ");
            stringBuilder.append((Object)this.type);
            stringBuilder.append("/");
            stringBuilder.append(this.format);
            NotSerializableException notSerializableException = new NotSerializableException(stringBuilder.toString());
            throw notSerializableException;
        }
        catch (Exception exception) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("java.security.Key: [");
            ((StringBuilder)serializable).append((Object)this.type);
            ((StringBuilder)serializable).append("] [");
            ((StringBuilder)serializable).append(this.algorithm);
            ((StringBuilder)serializable).append("] [");
            ((StringBuilder)serializable).append(this.format);
            ((StringBuilder)serializable).append("]");
            serializable = new NotSerializableException(((StringBuilder)serializable).toString());
            ((Throwable)serializable).initCause(exception);
            throw serializable;
        }
        catch (NotSerializableException notSerializableException) {
            throw notSerializableException;
        }
    }

    public static enum Type {
        SECRET,
        PUBLIC,
        PRIVATE;
        
    }

}

