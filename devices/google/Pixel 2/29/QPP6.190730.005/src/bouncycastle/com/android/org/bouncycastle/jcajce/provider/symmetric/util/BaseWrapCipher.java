/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.Wrapper;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class BaseWrapCipher
extends CipherSpi
implements PBE {
    private Class[] availableSpecs = new Class[]{PBEParameterSpec.class, IvParameterSpec.class};
    protected AlgorithmParameters engineParams = null;
    private boolean forWrapping;
    private final JcaJceHelper helper = new DefaultJcaJceHelper();
    private byte[] iv;
    private int ivSize;
    protected int pbeHash = 1;
    protected int pbeIvSize;
    protected int pbeKeySize;
    protected int pbeType = 2;
    protected Wrapper wrapEngine = null;
    private ErasableOutputStream wrapStream = null;

    protected BaseWrapCipher() {
    }

    protected BaseWrapCipher(Wrapper wrapper) {
        this(wrapper, 0);
    }

    protected BaseWrapCipher(Wrapper wrapper, int n) {
        this.wrapEngine = wrapper;
        this.ivSize = n;
    }

    protected final AlgorithmParameters createParametersInstance(String string) throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.helper.createAlgorithmParameters(string);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected int engineDoFinal(byte[] object, int n, int n2, byte[] object2, int n3) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        int n4;
        void var3_11;
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream == null) throw new IllegalStateException("not supported in a wrapping mode");
        erasableOutputStream.write((byte[])object, n4, (int)var3_11);
        try {
            void var5_15;
            void var1_5;
            void var4_12;
            boolean bl = this.forWrapping;
            if (bl) {
                try {
                    byte[] arrby = this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                }
                catch (Exception exception) {
                    IllegalBlockSizeException illegalBlockSizeException = new IllegalBlockSizeException(exception.getMessage());
                    throw illegalBlockSizeException;
                }
            } else {
                byte[] arrby = this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
            }
            if (((void)var1_5).length + var5_15 <= ((void)var4_12).length) {
                System.arraycopy((byte[])var1_5, (int)0, (byte[])var4_12, (int)var5_15, (int)((void)var1_5).length);
                n4 = ((void)var1_5).length;
                return n4;
            }
            ShortBufferException shortBufferException = new ShortBufferException("output buffer too short for input.");
            throw shortBufferException;
            catch (InvalidCipherTextException invalidCipherTextException) {
                BadPaddingException badPaddingException = new BadPaddingException(invalidCipherTextException.getMessage());
                throw badPaddingException;
            }
        }
        finally {
            this.wrapStream.erase();
        }
    }

    @Override
    protected byte[] engineDoFinal(byte[] object, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        Object object2 = this.wrapStream;
        if (object2 != null) {
            block10 : {
                ((ByteArrayOutputStream)object2).write((byte[])object, n, n2);
                boolean bl = this.forWrapping;
                if (!bl) break block10;
                object = this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                return object;
            }
            try {
                object = this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                return object;
            }
            catch (InvalidCipherTextException invalidCipherTextException) {
                object2 = new BadPaddingException(invalidCipherTextException.getMessage());
                throw object2;
            }
            finally {
                this.wrapStream.erase();
            }
        }
        throw new IllegalStateException("not supported in a wrapping mode");
    }

    @Override
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override
    protected byte[] engineGetIV() {
        return Arrays.clone(this.iv);
    }

    @Override
    protected int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return -1;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.iv != null) {
            Object object;
            Object object2 = object = this.wrapEngine.getAlgorithmName();
            if (((String)object).indexOf(47) >= 0) {
                object2 = ((String)object).substring(0, ((String)object).indexOf(47));
            }
            try {
                this.engineParams = this.createParametersInstance((String)object2);
                object2 = this.engineParams;
                object = new IvParameterSpec(this.iv);
                ((AlgorithmParameters)object2).init((AlgorithmParameterSpec)object);
            }
            catch (Exception exception) {
                throw new RuntimeException(exception.toString());
            }
        }
        return this.engineParams;
    }

    @Override
    protected void engineInit(int n, Key serializable, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec algorithmParameterSpec = null;
        AlgorithmParameterSpec algorithmParameterSpec2 = null;
        if (algorithmParameters != null) {
            int n2 = 0;
            do {
                Class[] arrclass = this.availableSpecs;
                algorithmParameterSpec = algorithmParameterSpec2;
                if (n2 == arrclass.length) break;
                try {
                    algorithmParameterSpec = (AlgorithmParameterSpec)algorithmParameters.getParameterSpec(arrclass[n2]);
                }
                catch (Exception exception) {
                    ++n2;
                    continue;
                }
                break;
            } while (true);
            if (algorithmParameterSpec == null) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("can't handle parameter ");
                ((StringBuilder)serializable).append(algorithmParameters.toString());
                throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString());
            }
        }
        this.engineParams = algorithmParameters;
        this.engineInit(n, (Key)serializable, algorithmParameterSpec, secureRandom);
    }

    @Override
    protected void engineInit(int n, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            this.engineInit(n, key, (AlgorithmParameterSpec)null, secureRandom);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyOrParametersException(invalidAlgorithmParameterException.getMessage(), invalidAlgorithmParameterException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(int var1_1, Key var2_2, AlgorithmParameterSpec var3_4, SecureRandom var4_5) throws InvalidKeyException, InvalidAlgorithmParameterException {
        block13 : {
            block14 : {
                block15 : {
                    block11 : {
                        block12 : {
                            if (var2_2 instanceof BCPBEKey) {
                                var2_2 = (BCPBEKey)var2_2;
                                if (var3_4 instanceof PBEParameterSpec) {
                                    var5_6 /* !! */  = PBE.Util.makePBEParameters((BCPBEKey)var2_2, (AlgorithmParameterSpec)var3_4, this.wrapEngine.getAlgorithmName());
                                } else {
                                    if (var2_2.getParam() == null) throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                                    var5_6 /* !! */  = var2_2.getParam();
                                }
                            } else {
                                var5_6 /* !! */  = new KeyParameter(var2_2.getEncoded());
                            }
                            var2_2 = var5_6 /* !! */ ;
                            if (var3_4 instanceof IvParameterSpec) {
                                this.iv = ((IvParameterSpec)var3_4).getIV();
                                var2_2 = new ParametersWithIV(var5_6 /* !! */ , this.iv);
                            }
                            var3_4 = var2_2;
                            if (!(var2_2 instanceof KeyParameter)) break block11;
                            var3_4 = var2_2;
                            if (this.ivSize == 0) break block11;
                            if (var1_1 == 3) break block12;
                            var3_4 = var2_2;
                            if (var1_1 != 1) break block11;
                        }
                        this.iv = new byte[this.ivSize];
                        var4_5.nextBytes(this.iv);
                        var3_4 = new ParametersWithIV((CipherParameters)var2_2, this.iv);
                    }
                    var2_2 = var3_4;
                    if (var4_5 != null) {
                        var2_2 = new ParametersWithRandom((CipherParameters)var3_4, var4_5);
                    }
                    if (var1_1 == 1) break block13;
                    if (var1_1 == 2) break block14;
                    if (var1_1 == 3) break block15;
                    if (var1_1 != 4) ** GOTO lbl38
                    try {
                        this.wrapEngine.init(false, (CipherParameters)var2_2);
                        this.wrapStream = null;
                        this.forWrapping = false;
                        return;
lbl38: // 1 sources:
                        var2_2 = new InvalidParameterException("Unknown mode parameter passed to init.");
                        throw var2_2;
                    }
                    catch (Exception var2_3) {
                        throw new InvalidKeyOrParametersException(var2_3.getMessage(), var2_3);
                    }
                }
                this.wrapEngine.init(true, (CipherParameters)var2_2);
                this.wrapStream = null;
                this.forWrapping = true;
                return;
            }
            this.wrapEngine.init(false, (CipherParameters)var2_2);
            this.wrapStream = var2_2 = new ErasableOutputStream();
            this.forWrapping = false;
            return;
        }
        this.wrapEngine.init(true, (CipherParameters)var2_2);
        this.wrapStream = var2_2 = new ErasableOutputStream();
        this.forWrapping = true;
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
    protected Key engineUnwrap(byte[] object, String object2, int n) throws InvalidKeyException, NoSuchAlgorithmException {
        block13 : {
            object = this.wrapEngine == null ? this.engineDoFinal((byte[])object, 0, ((Object)object).length) : this.wrapEngine.unwrap((byte[])object, 0, ((Object)object).length);
            if (n == 3) {
                return new SecretKeySpec((byte[])object, (String)object2);
            }
            if (!((String)object2).equals("") || n != 2) break block13;
            try {
                object = PrivateKeyInfo.getInstance(object);
                object2 = BouncyCastleProvider.getPrivateKey((PrivateKeyInfo)object);
                if (object2 != null) {
                    return object2;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("algorithm ");
                ((StringBuilder)object2).append(((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm());
                ((StringBuilder)object2).append(" not supported");
                InvalidKeyException invalidKeyException = new InvalidKeyException(((StringBuilder)object2).toString());
                throw invalidKeyException;
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
        catch (InvalidKeySpecException invalidKeySpecException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key type ");
            ((StringBuilder)object).append(invalidKeySpecException.getMessage());
            throw new InvalidKeyException(((StringBuilder)object).toString());
        }
        catch (NoSuchProviderException noSuchProviderException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key type ");
            ((StringBuilder)object).append(noSuchProviderException.getMessage());
            throw new InvalidKeyException(((StringBuilder)object).toString());
        }
        ((StringBuilder)object).append(n);
        throw new InvalidKeyException(((StringBuilder)object).toString());
        catch (IllegalBlockSizeException illegalBlockSizeException) {
            throw new InvalidKeyException(illegalBlockSizeException.getMessage());
        }
        catch (BadPaddingException badPaddingException) {
            throw new InvalidKeyException(badPaddingException.getMessage());
        }
        catch (InvalidCipherTextException invalidCipherTextException) {
            throw new InvalidKeyException(invalidCipherTextException.getMessage());
        }
    }

    @Override
    protected int engineUpdate(byte[] arrby, int n, int n2, byte[] object, int n3) throws ShortBufferException {
        object = this.wrapStream;
        if (object != null) {
            ((ByteArrayOutputStream)object).write(arrby, n, n2);
            return 0;
        }
        throw new IllegalStateException("not supported in a wrapping mode");
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream != null) {
            erasableOutputStream.write(arrby, n, n2);
            return null;
        }
        throw new IllegalStateException("not supported in a wrapping mode");
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

    protected static class InvalidKeyOrParametersException
    extends InvalidKeyException {
        private final Throwable cause;

        InvalidKeyOrParametersException(String string, Throwable throwable) {
            super(string);
            this.cause = throwable;
        }

        @Override
        public Throwable getCause() {
            return this.cause;
        }
    }

}

