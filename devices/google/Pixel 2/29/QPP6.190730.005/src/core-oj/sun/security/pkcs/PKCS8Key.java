/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;

public class PKCS8Key
implements PrivateKey {
    private static final long serialVersionUID = -3836890099307167124L;
    public static final BigInteger version = BigInteger.ZERO;
    protected AlgorithmId algid;
    protected byte[] encodedKey;
    protected byte[] key;

    public PKCS8Key() {
    }

    private PKCS8Key(AlgorithmId algorithmId, byte[] arrby) throws InvalidKeyException {
        this.algid = algorithmId;
        this.key = arrby;
        this.encode();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static PrivateKey buildPKCS8Key(AlgorithmId algorithmId, byte[] arrby) throws IOException, InvalidKeyException {
        block14 : {
            object3 = new DerOutputStream();
            PKCS8Key.encode((DerOutputStream)object3, algorithmId, arrby);
            object3 = new PKCS8EncodedKeySpec(object3.toByteArray());
            try {
                return KeyFactory.getInstance(algorithmId.getName()).generatePrivate((KeySpec)object3);
            }
            catch (InvalidKeySpecException invalidKeySpecException) {
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
            serializable = "";
            object = serializable;
            try {
                object2 = Security.getProvider("SUN");
                if (object2 == null) ** GOTO lbl62
                object = serializable;
                object = serializable;
                charSequence /* !! */  = new StringBuilder();
                object = serializable;
                charSequence /* !! */ .append("PrivateKey.PKCS#8.");
                object = serializable;
                charSequence /* !! */ .append(algorithmId.getName());
                object = serializable;
                charSequence /* !! */  = object2.getProperty(charSequence /* !! */ .toString());
                if (charSequence /* !! */  == null) ** GOTO lbl57
                serializable = null;
                object = charSequence /* !! */ ;
                try {
                    object2 = Class.forName((String)charSequence /* !! */ );
                    serializable = object2;
                    ** GOTO lbl41
                }
                catch (ClassNotFoundException classNotFoundException) {
                    object = charSequence /* !! */ ;
                    try {
                        object2 = ClassLoader.getSystemClassLoader();
                        if (object2 != null) {
                            object = charSequence /* !! */ ;
                            serializable = object2.loadClass((String)charSequence /* !! */ );
                        }
lbl41: // 4 sources:
                        object2 = null;
                        if (serializable != null) {
                            object = charSequence /* !! */ ;
                            object2 = serializable.newInstance();
                        }
                        object = charSequence /* !! */ ;
                        if (object2 instanceof PKCS8Key) {
                            object = charSequence /* !! */ ;
                            serializable = (PKCS8Key)object2;
                            object = charSequence /* !! */ ;
                            serializable.algid = algorithmId;
                            object = charSequence /* !! */ ;
                            serializable.key = arrby;
                            object = charSequence /* !! */ ;
                            serializable.parseKeyBits();
                            return serializable;
                        }
                        break block14;
lbl57: // 1 sources:
                        object = charSequence /* !! */ ;
                        object = charSequence /* !! */ ;
                        serializable = new InstantiationException();
                        object = charSequence /* !! */ ;
                        throw serializable;
lbl62: // 1 sources:
                        object = serializable;
                        object = serializable;
                        instantiationException = new InstantiationException();
                        object = serializable;
                        throw instantiationException;
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        // empty catch block
                    }
                }
            }
            catch (IllegalAccessException illegalAccessException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(" [internal error]");
                throw new IOException(stringBuilder.toString());
            }
            catch (InstantiationException instantiationException2) {}
        }
        serializable = new PKCS8Key();
        serializable.algid = algorithmId;
        serializable.key = arrby;
        return serializable;
    }

    static void encode(DerOutputStream derOutputStream, AlgorithmId algorithmId, byte[] arrby) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putInteger(version);
        algorithmId.encode(derOutputStream2);
        derOutputStream2.putOctetString(arrby);
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public static PKCS8Key parse(DerValue object) throws IOException {
        if ((object = PKCS8Key.parseKey((DerValue)object)) instanceof PKCS8Key) {
            return (PKCS8Key)object;
        }
        throw new IOException("Provider did not return PKCS8Key");
    }

    public static PrivateKey parseKey(DerValue object) throws IOException {
        if (((DerValue)object).tag == 48) {
            Serializable serializable = ((DerValue)object).data.getBigInteger();
            if (version.equals(serializable)) {
                serializable = AlgorithmId.parse(((DerValue)object).data.getDerValue());
                try {
                    serializable = PKCS8Key.buildPKCS8Key((AlgorithmId)serializable, ((DerValue)object).data.getOctetString());
                    if (((DerValue)object).data.available() == 0) {
                        return serializable;
                    }
                    throw new IOException("excess private key");
                }
                catch (InvalidKeyException invalidKeyException) {
                    throw new IOException("corrupt private key");
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("version mismatch: (supported: ");
            ((StringBuilder)object).append(Debug.toHexString(version));
            ((StringBuilder)object).append(", parsed: ");
            ((StringBuilder)object).append(Debug.toHexString(serializable));
            throw new IOException(((StringBuilder)object).toString());
        }
        throw new IOException("corrupt private key");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.decode(objectInputStream);
            return;
        }
        catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("deserialized key is invalid: ");
            stringBuilder.append(invalidKeyException.getMessage());
            throw new IOException(stringBuilder.toString());
        }
    }

    public void decode(InputStream object) throws InvalidKeyException {
        try {
            Object object2 = new DerValue((InputStream)object);
            if (((DerValue)object2).tag == 48) {
                object = ((DerValue)object2).data.getBigInteger();
                if (((BigInteger)object).equals(version)) {
                    this.algid = AlgorithmId.parse(((DerValue)object2).data.getDerValue());
                    this.key = ((DerValue)object2).data.getOctetString();
                    this.parseKeyBits();
                    ((DerValue)object2).data.available();
                    return;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("version mismatch: (supported: ");
                ((StringBuilder)object2).append(Debug.toHexString(version));
                ((StringBuilder)object2).append(", parsed: ");
                ((StringBuilder)object2).append(Debug.toHexString((BigInteger)object));
                IOException iOException = new IOException(((StringBuilder)object2).toString());
                throw iOException;
            }
            object = new InvalidKeyException("invalid key format");
            throw object;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IOException : ");
            stringBuilder.append(iOException.getMessage());
            throw new InvalidKeyException(stringBuilder.toString());
        }
    }

    public void decode(byte[] arrby) throws InvalidKeyException {
        this.decode(new ByteArrayInputStream(arrby));
    }

    public final void encode(DerOutputStream derOutputStream) throws IOException {
        PKCS8Key.encode(derOutputStream, this.algid, this.key);
    }

    public byte[] encode() throws InvalidKeyException {
        if (this.encodedKey == null) {
            try {
                DerOutputStream derOutputStream = new DerOutputStream();
                this.encode(derOutputStream);
                this.encodedKey = derOutputStream.toByteArray();
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IOException : ");
                stringBuilder.append(iOException.getMessage());
                throw new InvalidKeyException(stringBuilder.toString());
            }
        }
        return (byte[])this.encodedKey.clone();
    }

    public boolean equals(Object arrby) {
        if (this == arrby) {
            return true;
        }
        if (arrby instanceof Key) {
            byte[] arrby2 = this.encodedKey != null ? this.encodedKey : this.getEncoded();
            if (arrby2.length != (arrby = ((Key)arrby).getEncoded()).length) {
                return false;
            }
            for (int i = 0; i < arrby2.length; ++i) {
                if (arrby2[i] == arrby[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getAlgorithm() {
        return this.algid.getName();
    }

    public AlgorithmId getAlgorithmId() {
        return this.algid;
    }

    @Override
    public byte[] getEncoded() {
        synchronized (this) {
            byte[] arrby = null;
            try {
                byte[] arrby2;
                arrby = arrby2 = this.encode();
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            catch (InvalidKeyException invalidKeyException) {
                // empty catch block
            }
            return arrby;
        }
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    public int hashCode() {
        int n = 0;
        byte[] arrby = this.getEncoded();
        for (int i = 1; i < arrby.length; ++i) {
            n += arrby[i] * i;
        }
        return n;
    }

    protected void parseKeyBits() throws IOException, InvalidKeyException {
        this.encode();
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new KeyRep(KeyRep.Type.PRIVATE, this.getAlgorithm(), this.getFormat(), this.getEncoded());
    }
}

