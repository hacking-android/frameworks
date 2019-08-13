/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.IOException;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class EncryptedPrivateKeyInfo {
    private AlgorithmId algid;
    private byte[] encoded = null;
    private byte[] encryptedData;

    public EncryptedPrivateKeyInfo(String string, byte[] arrby) throws NoSuchAlgorithmException {
        if (string != null) {
            this.algid = AlgorithmId.get(string);
            if (arrby != null) {
                if (arrby.length != 0) {
                    this.encryptedData = (byte[])arrby.clone();
                    this.encoded = null;
                    return;
                }
                throw new IllegalArgumentException("the encryptedData parameter must not be empty");
            }
            throw new NullPointerException("the encryptedData parameter must be non-null");
        }
        throw new NullPointerException("the algName parameter must be non-null");
    }

    public EncryptedPrivateKeyInfo(AlgorithmParameters algorithmParameters, byte[] arrby) throws NoSuchAlgorithmException {
        if (algorithmParameters != null) {
            this.algid = AlgorithmId.get(algorithmParameters);
            if (arrby != null) {
                if (arrby.length != 0) {
                    this.encryptedData = (byte[])arrby.clone();
                    this.encoded = null;
                    return;
                }
                throw new IllegalArgumentException("the encryptedData parameter must not be empty");
            }
            throw new NullPointerException("encryptedData must be non-null");
        }
        throw new NullPointerException("algParams must be non-null");
    }

    public EncryptedPrivateKeyInfo(byte[] object) throws IOException {
        if (object != null) {
            this.encoded = (byte[])object.clone();
            object = new DerValue(this.encoded);
            Object object2 = new DerValue[]{object.data.getDerValue(), object.data.getDerValue()};
            if (object.data.available() == 0) {
                this.algid = AlgorithmId.parse(object2[0]);
                if (((DerValue)object2[0]).data.available() == 0) {
                    this.encryptedData = ((DerValue)object2[1]).getOctetString();
                    if (((DerValue)object2[1]).data.available() == 0) {
                        return;
                    }
                    throw new IOException("encryptedData field overrun");
                }
                throw new IOException("encryptionAlgorithm field overrun");
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("overrun, bytes = ");
            ((StringBuilder)object2).append(object.data.available());
            throw new IOException(((StringBuilder)object2).toString());
        }
        throw new NullPointerException("the encoded parameter must be non-null");
    }

    private static void checkPKCS8Encoding(byte[] arrobject) throws IOException {
        int n = (arrobject = new DerInputStream((byte[])arrobject).getSequence(3)).length;
        if (n != 3) {
            if (n == 4) {
                EncryptedPrivateKeyInfo.checkTag((DerValue)arrobject[3], (byte)-128, "attributes");
            } else {
                throw new IOException("invalid key encoding");
            }
        }
        EncryptedPrivateKeyInfo.checkTag((DerValue)arrobject[0], (byte)2, "version");
        DerInputStream derInputStream = arrobject[1].toDerInputStream();
        derInputStream.getOID();
        if (derInputStream.available() != 0) {
            derInputStream.getDerValue();
        }
        EncryptedPrivateKeyInfo.checkTag((DerValue)arrobject[2], (byte)4, "privateKey");
    }

    private static void checkTag(DerValue object, byte by, String string) throws IOException {
        if (((DerValue)object).getTag() == by) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("invalid key encoding - wrong tag for ");
        ((StringBuilder)object).append(string);
        throw new IOException(((StringBuilder)object).toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private PKCS8EncodedKeySpec getKeySpecImpl(Key var1_1, Provider var2_4) throws NoSuchAlgorithmException, InvalidKeyException {
        if (var2_4 != null) ** GOTO lbl5
        try {
            block3 : {
                var2_4 = Cipher.getInstance(this.algid.getName());
                break block3;
lbl5: // 1 sources:
                var2_4 = Cipher.getInstance(this.algid.getName(), (Provider)var2_4);
            }
            var2_4.init(2, (Key)var1_1, this.algid.getParameters());
            var1_1 = var2_4.doFinal(this.encryptedData);
            EncryptedPrivateKeyInfo.checkPKCS8Encoding(var1_1);
            return new PKCS8EncodedKeySpec(var1_1);
        }
        catch (IOException | GeneralSecurityException var1_2) {
            throw new InvalidKeyException("Cannot retrieve the PKCS8EncodedKeySpec", var1_2);
        }
        catch (NoSuchAlgorithmException var1_3) {
            throw var1_3;
        }
    }

    public String getAlgName() {
        return this.algid.getName();
    }

    public AlgorithmParameters getAlgParameters() {
        return this.algid.getParameters();
    }

    public byte[] getEncoded() throws IOException {
        if (this.encoded == null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            this.algid.encode(derOutputStream2);
            derOutputStream2.putOctetString(this.encryptedData);
            derOutputStream.write((byte)48, derOutputStream2);
            this.encoded = derOutputStream.toByteArray();
        }
        return (byte[])this.encoded.clone();
    }

    public byte[] getEncryptedData() {
        return (byte[])this.encryptedData.clone();
    }

    public PKCS8EncodedKeySpec getKeySpec(Key key) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key != null) {
            return this.getKeySpecImpl(key, null);
        }
        throw new NullPointerException("decryptKey is null");
    }

    public PKCS8EncodedKeySpec getKeySpec(Key serializable, String string) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
        if (serializable != null) {
            if (string != null) {
                Provider provider = Security.getProvider(string);
                if (provider != null) {
                    return this.getKeySpecImpl((Key)serializable, provider);
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("provider ");
                ((StringBuilder)serializable).append(string);
                ((StringBuilder)serializable).append(" not found");
                throw new NoSuchProviderException(((StringBuilder)serializable).toString());
            }
            throw new NullPointerException("provider is null");
        }
        throw new NullPointerException("decryptKey is null");
    }

    public PKCS8EncodedKeySpec getKeySpec(Key key, Provider provider) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key != null) {
            if (provider != null) {
                return this.getKeySpecImpl(key, provider);
            }
            throw new NullPointerException("provider is null");
        }
        throw new NullPointerException("decryptKey is null");
    }

    public PKCS8EncodedKeySpec getKeySpec(Cipher arrby) throws InvalidKeySpecException {
        try {
            arrby = arrby.doFinal(this.encryptedData);
            EncryptedPrivateKeyInfo.checkPKCS8Encoding(arrby);
        }
        catch (IOException | IllegalStateException | GeneralSecurityException exception) {
            throw new InvalidKeySpecException("Cannot retrieve the PKCS8EncodedKeySpec", exception);
        }
        return new PKCS8EncodedKeySpec(arrby);
    }
}

