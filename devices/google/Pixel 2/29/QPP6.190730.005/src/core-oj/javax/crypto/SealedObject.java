/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.extObjectInputStream;

public class SealedObject
implements Serializable {
    static final long serialVersionUID = 4482838265551344752L;
    protected byte[] encodedParams;
    private byte[] encryptedContent;
    private String paramsAlg;
    private String sealAlg;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SealedObject(Serializable arrby, Cipher cipher) throws IOException, IllegalBlockSizeException {
        ObjectOutputStream objectOutputStream;
        block6 : {
            this.encryptedContent = null;
            this.sealAlg = null;
            this.paramsAlg = null;
            this.encodedParams = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(arrby);
            objectOutputStream.flush();
            arrby = byteArrayOutputStream.toByteArray();
            this.encryptedContent = cipher.doFinal(arrby);
            break block6;
            catch (BadPaddingException badPaddingException) {
                // empty catch block
            }
        }
        if (cipher.getParameters() != null) {
            this.encodedParams = cipher.getParameters().getEncoded();
            this.paramsAlg = cipher.getParameters().getAlgorithm();
        }
        this.sealAlg = cipher.getAlgorithm();
        return;
        finally {
            objectOutputStream.close();
        }
    }

    protected SealedObject(SealedObject arrby) {
        this.encryptedContent = null;
        this.sealAlg = null;
        this.paramsAlg = null;
        this.encodedParams = null;
        this.encryptedContent = (byte[])arrby.encryptedContent.clone();
        this.sealAlg = arrby.sealAlg;
        this.paramsAlg = arrby.paramsAlg;
        arrby = arrby.encodedParams;
        this.encodedParams = arrby != null ? (byte[])arrby.clone() : null;
    }

    private void readObject(ObjectInputStream arrby) throws IOException, ClassNotFoundException {
        arrby.defaultReadObject();
        arrby = this.encryptedContent;
        if (arrby != null) {
            this.encryptedContent = (byte[])arrby.clone();
        }
        if ((arrby = this.encodedParams) != null) {
            this.encodedParams = (byte[])arrby.clone();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Object unseal(Key var1_1, String var2_8) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        block13 : {
            block12 : {
                block10 : {
                    var3_10 = null;
                    if (this.encodedParams == null) break block10;
                    if (var2_8 == null) ** GOTO lbl7
                    try {
                        block11 : {
                            var3_10 = AlgorithmParameters.getInstance(this.paramsAlg, (String)var2_8);
                            break block11;
lbl7: // 1 sources:
                            var3_10 = AlgorithmParameters.getInstance(this.paramsAlg);
                        }
                        var3_10.init(this.encodedParams);
                    }
                    catch (NoSuchProviderException var1_2) {
                        if (var2_8 != null) throw new NoSuchProviderException(var1_2.getMessage());
                        var1_3 = new StringBuilder();
                        var1_3.append(this.paramsAlg);
                        var1_3.append(" not found");
                        throw new NoSuchAlgorithmException(var1_3.toString());
                    }
                }
                if (var2_8 == null) ** GOTO lbl25
                var4_11 = Cipher.getInstance(this.sealAlg, (String)var2_8);
                var2_8 = var4_11;
                break block12;
lbl25: // 1 sources:
                var4_12 = Cipher.getInstance(this.sealAlg);
                var2_8 = var4_12;
            }
            if (var3_10 == null) ** GOTO lbl32
            var2_8.init(2, (Key)var1_1, var3_10);
            break block13;
lbl32: // 1 sources:
            var2_8.init(2, (Key)var1_1);
        }
        var1_1 = new extObjectInputStream(new ByteArrayInputStream(var2_8.doFinal(this.encryptedContent)));
        try {
            var2_8 = var1_1.readObject();
            return var2_8;
        }
        finally {
            var1_1.close();
        }
        catch (InvalidAlgorithmParameterException var1_4) {
            throw new RuntimeException(var1_4.getMessage());
        }
        catch (NoSuchProviderException var1_5) {
            if (var2_8 != null) throw new NoSuchProviderException(var1_5.getMessage());
            var1_6 = new StringBuilder();
            var1_6.append(this.sealAlg);
            var1_6.append(" not found");
            throw new NoSuchAlgorithmException(var1_6.toString());
        }
        catch (NoSuchPaddingException var1_7) {
            throw new NoSuchAlgorithmException("Padding that was used in sealing operation not available");
        }
    }

    public final String getAlgorithm() {
        return this.sealAlg;
    }

    public final Object getObject(Key object) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException {
        if (object != null) {
            try {
                object = this.unseal((Key)object, null);
                return object;
            }
            catch (BadPaddingException badPaddingException) {
                throw new InvalidKeyException(badPaddingException.getMessage());
            }
            catch (IllegalBlockSizeException illegalBlockSizeException) {
                throw new InvalidKeyException(illegalBlockSizeException.getMessage());
            }
            catch (NoSuchProviderException noSuchProviderException) {
                throw new NoSuchAlgorithmException("algorithm not found");
            }
        }
        throw new NullPointerException("key is null");
    }

    public final Object getObject(Key object, String string) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        if (object != null) {
            if (string != null && string.length() != 0) {
                try {
                    object = this.unseal((Key)object, string);
                    return object;
                }
                catch (BadPaddingException | IllegalBlockSizeException generalSecurityException) {
                    throw new InvalidKeyException(generalSecurityException.getMessage());
                }
            }
            throw new IllegalArgumentException("missing provider");
        }
        throw new NullPointerException("key is null");
    }

    public final Object getObject(Cipher object) throws IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException {
        object = new extObjectInputStream(new ByteArrayInputStream(((Cipher)object).doFinal(this.encryptedContent)));
        try {
            Object object2 = object.readObject();
            return object2;
        }
        finally {
            object.close();
        }
    }
}

