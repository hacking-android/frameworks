/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.Wrapper;
import com.android.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class BaseCipherSpi
extends CipherSpi {
    private Class[] availableSpecs = new Class[]{IvParameterSpec.class, PBEParameterSpec.class};
    protected AlgorithmParameters engineParams = null;
    private final JcaJceHelper helper = new BCJcaJceHelper();
    private byte[] iv;
    private int ivSize;
    protected Wrapper wrapEngine = null;

    protected BaseCipherSpi() {
    }

    protected final AlgorithmParameters createParametersInstance(String string) throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.helper.createAlgorithmParameters(string);
    }

    @Override
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override
    protected byte[] engineGetIV() {
        return null;
    }

    @Override
    protected int engineGetKeySize(Key key) {
        return key.getEncoded().length;
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return -1;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override
    protected void engineSetMode(String string) throws NoSuchAlgorithmException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("can't support mode ");
        stringBuilder.append(string);
        throw new NoSuchAlgorithmException(stringBuilder.toString());
    }

    @Override
    protected void engineSetPadding(String string) throws NoSuchPaddingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Padding ");
        stringBuilder.append(string);
        stringBuilder.append(" unknown.");
        throw new NoSuchPaddingException(stringBuilder.toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected Key engineUnwrap(byte[] object, String object2, int n) throws InvalidKeyException {
        block14 : {
            object = this.wrapEngine == null ? this.engineDoFinal((byte[])object, 0, ((Object)object).length) : this.wrapEngine.unwrap((byte[])object, 0, ((Object)object).length);
            if (n == 3) {
                return new SecretKeySpec((byte[])object, (String)object2);
            }
            if (!((String)object2).equals("") || n != 2) break block14;
            try {
                object = PrivateKeyInfo.getInstance(object);
                object2 = BouncyCastleProvider.getPrivateKey((PrivateKeyInfo)object);
                if (object2 != null) {
                    return object2;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("algorithm ");
                stringBuilder.append(((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm());
                stringBuilder.append(" not supported");
                object2 = new InvalidKeyException(stringBuilder.toString());
                throw object2;
            }
            catch (Exception exception) {
                throw new InvalidKeyException("Invalid key encoding.");
            }
        }
        try {
            object2 = this.helper.createKeyFactory((String)object2);
            if (n == 1) {
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
                return ((KeyFactory)object2).generatePublic(x509EncodedKeySpec);
            }
            if (n == 2) {
                PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec((byte[])object);
                return ((KeyFactory)object2).generatePrivate(pKCS8EncodedKeySpec);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key type ");
        }
        catch (NoSuchProviderException noSuchProviderException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key type ");
            ((StringBuilder)object).append(noSuchProviderException.getMessage());
            throw new InvalidKeyException(((StringBuilder)object).toString());
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key type ");
            ((StringBuilder)object).append(invalidKeySpecException.getMessage());
            throw new InvalidKeyException(((StringBuilder)object).toString());
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key type ");
            ((StringBuilder)object).append(noSuchAlgorithmException.getMessage());
            throw new InvalidKeyException(((StringBuilder)object).toString());
        }
        ((StringBuilder)object).append(n);
        throw new InvalidKeyException(((StringBuilder)object).toString());
        catch (IllegalBlockSizeException illegalBlockSizeException) {
            throw new InvalidKeyException(illegalBlockSizeException.getMessage());
        }
        catch (BadPaddingException badPaddingException) {
            throw new InvalidKeyException("unable to unwrap"){

                @Override
                public Throwable getCause() {
                    synchronized (this) {
                        BadPaddingException badPaddingException2 = badPaddingException;
                        return badPaddingException2;
                    }
                }
            };
        }
        catch (InvalidCipherTextException invalidCipherTextException) {
            throw new InvalidKeyException(invalidCipherTextException.getMessage());
        }
    }

    @Override
    protected byte[] engineWrap(Key arrby) throws IllegalBlockSizeException, InvalidKeyException {
        if ((arrby = arrby.getEncoded()) != null) {
            try {
                if (this.wrapEngine == null) {
                    return this.engineDoFinal(arrby, 0, arrby.length);
                }
                arrby = this.wrapEngine.wrap(arrby, 0, arrby.length);
                return arrby;
            }
            catch (BadPaddingException badPaddingException) {
                throw new IllegalBlockSizeException(badPaddingException.getMessage());
            }
        }
        throw new InvalidKeyException("Cannot wrap key, null encoding.");
    }

    protected static final class ErasableOutputStream
    extends ByteArrayOutputStream {
        public void erase() {
            Arrays.fill(this.buf, (byte)0);
            this.reset();
        }

        public byte[] getBuf() {
            return this.buf;
        }
    }

}

