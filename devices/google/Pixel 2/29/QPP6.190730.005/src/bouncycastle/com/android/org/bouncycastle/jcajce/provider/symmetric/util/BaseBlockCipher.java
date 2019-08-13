/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.cms.GCMParameters;
import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.BufferedBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.modes.AEADBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CCMBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CFBBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CTSBlockCipher;
import com.android.org.bouncycastle.crypto.modes.GCMBlockCipher;
import com.android.org.bouncycastle.crypto.modes.OFBBlockCipher;
import com.android.org.bouncycastle.crypto.modes.SICBlockCipher;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import com.android.org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import com.android.org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import com.android.org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import com.android.org.bouncycastle.crypto.paddings.TBCPadding;
import com.android.org.bouncycastle.crypto.paddings.X923Padding;
import com.android.org.bouncycastle.crypto.paddings.ZeroBytePadding;
import com.android.org.bouncycastle.crypto.params.AEADParameters;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.jcajce.PKCS12Key;
import com.android.org.bouncycastle.jcajce.PKCS12KeyWithParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.org.bouncycastle.jcajce.spec.AEADParameterSpec;
import com.android.org.bouncycastle.util.Strings;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class BaseBlockCipher
extends BaseWrapCipher
implements PBE {
    private static final Class gcmSpecClass = ClassUtil.loadClass(BaseBlockCipher.class, "javax.crypto.spec.GCMParameterSpec");
    private AEADParameters aeadParams;
    private Class[] availableSpecs = new Class[]{gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class};
    private BlockCipher baseEngine;
    private GenericBlockCipher cipher;
    private int digest;
    private BlockCipherProvider engineProvider;
    private boolean fixedIv = true;
    private int ivLength = 0;
    private ParametersWithIV ivParam;
    private int keySizeInBits;
    private String modeName = null;
    private boolean padded;
    private String pbeAlgorithm = null;
    private PBEParameterSpec pbeSpec = null;
    private int scheme = -1;

    protected BaseBlockCipher(BlockCipher blockCipher) {
        this.baseEngine = blockCipher;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, int n) {
        this(blockCipher, true, n);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, int n, int n2, int n3, int n4) {
        this.baseEngine = blockCipher;
        this.scheme = n;
        this.digest = n2;
        this.keySizeInBits = n3;
        this.ivLength = n4;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, boolean bl, int n) {
        this.baseEngine = blockCipher;
        this.fixedIv = bl;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
        this.ivLength = n / 8;
    }

    protected BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, int n) {
        this(bufferedBlockCipher, true, n);
    }

    protected BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, boolean bl, int n) {
        this.baseEngine = bufferedBlockCipher.getUnderlyingCipher();
        this.cipher = new BufferedGenericBlockCipher(bufferedBlockCipher);
        this.fixedIv = bl;
        this.ivLength = n / 8;
    }

    protected BaseBlockCipher(AEADBlockCipher aEADBlockCipher) {
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.ivLength = this.baseEngine.getBlockSize();
        this.cipher = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(AEADBlockCipher aEADBlockCipher, boolean bl, int n) {
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.fixedIv = bl;
        this.ivLength = n;
        this.cipher = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(BlockCipherProvider blockCipherProvider) {
        this.baseEngine = blockCipherProvider.get();
        this.engineProvider = blockCipherProvider;
        this.cipher = new BufferedGenericBlockCipher(blockCipherProvider.get());
    }

    private CipherParameters adjustParameters(AlgorithmParameterSpec algorithmParameterSpec, CipherParameters cipherParameters) {
        CipherParameters cipherParameters2;
        if (cipherParameters instanceof ParametersWithIV) {
            CipherParameters cipherParameters3 = ((ParametersWithIV)cipherParameters).getParameters();
            cipherParameters2 = cipherParameters;
            if (algorithmParameterSpec instanceof IvParameterSpec) {
                this.ivParam = new ParametersWithIV(cipherParameters3, ((IvParameterSpec)algorithmParameterSpec).getIV());
                cipherParameters2 = this.ivParam;
            }
        } else {
            cipherParameters2 = cipherParameters;
            if (algorithmParameterSpec instanceof IvParameterSpec) {
                this.ivParam = new ParametersWithIV(cipherParameters, ((IvParameterSpec)algorithmParameterSpec).getIV());
                cipherParameters2 = this.ivParam;
            }
        }
        return cipherParameters2;
    }

    private boolean isAEADModeName(String string) {
        boolean bl = "CCM".equals(string) || "GCM".equals(string);
        return bl;
    }

    private boolean isBCPBEKeyWithoutIV(Key key) {
        boolean bl = key instanceof BCPBEKey && !(((BCPBEKey)key).getParam() instanceof ParametersWithIV);
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected int engineDoFinal(byte[] var1_1, int var2_4, int var3_5, byte[] var4_6, int var5_7) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        var6_8 = 0;
        if (this.engineGetOutputSize(var3_5) + var5_7 > var4_6.length) throw new ShortBufferException("output buffer too short for input.");
        if (var3_5 == 0) ** GOTO lbl6
        try {
            var6_8 = this.cipher.processBytes(var1_1, var2_4, var3_5, var4_6, var5_7);
lbl6: // 2 sources:
            var2_4 = this.cipher.doFinal(var4_6, var5_7 + var6_8);
            return var2_4 + var6_8;
        }
        catch (DataLengthException var1_2) {
            throw new IllegalBlockSizeException(var1_2.getMessage());
        }
        catch (OutputLengthException var1_3) {
            throw new IllegalBlockSizeException(var1_3.getMessage());
        }
    }

    @Override
    protected byte[] engineDoFinal(byte[] arrby, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        int n3 = 0;
        byte[] arrby2 = new byte[this.engineGetOutputSize(n2)];
        if (n2 != 0) {
            n3 = this.cipher.processBytes(arrby, n, n2, arrby2, 0);
        }
        try {
            n = this.cipher.doFinal(arrby2, n3);
            n = n3 + n;
        }
        catch (DataLengthException dataLengthException) {
            throw new IllegalBlockSizeException(dataLengthException.getMessage());
        }
        if (n == arrby2.length) {
            return arrby2;
        }
        arrby = new byte[n];
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)n);
        return arrby;
    }

    @Override
    protected int engineGetBlockSize() {
        return this.baseEngine.getBlockSize();
    }

    @Override
    protected byte[] engineGetIV() {
        CipherParameters cipherParameters = this.aeadParams;
        if (cipherParameters != null) {
            return cipherParameters.getNonce();
        }
        cipherParameters = this.ivParam;
        cipherParameters = cipherParameters != null ? ((ParametersWithIV)cipherParameters).getIV() : null;
        return cipherParameters;
    }

    @Override
    protected int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return this.cipher.getOutputSize(n);
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    this.engineParams = this.createParametersInstance(this.pbeAlgorithm);
                    this.engineParams.init(this.pbeSpec);
                }
                catch (Exception exception) {
                    return null;
                }
            }
            if (this.aeadParams != null) {
                try {
                    AlgorithmParameters algorithmParameters = this.engineParams = this.createParametersInstance("GCM");
                    GCMParameters gCMParameters = new GCMParameters(this.aeadParams.getNonce(), this.aeadParams.getMacSize() / 8);
                    algorithmParameters.init(gCMParameters.getEncoded());
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception.toString());
                }
            }
            if (this.ivParam != null) {
                Object object;
                Object object2 = object = this.cipher.getUnderlyingCipher().getAlgorithmName();
                if (((String)object).indexOf(47) >= 0) {
                    object2 = ((String)object).substring(0, ((String)object).indexOf(47));
                }
                try {
                    this.engineParams = this.createParametersInstance((String)object2);
                    object2 = this.engineParams;
                    object = new IvParameterSpec(this.ivParam.getIV());
                    ((AlgorithmParameters)object2).init((AlgorithmParameterSpec)object);
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception.toString());
                }
            }
        }
        return this.engineParams;
    }

    @Override
    protected void engineInit(int n, Key serializable, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        Object var5_5 = null;
        Object var6_7 = null;
        if (algorithmParameters != null) {
            int n2 = 0;
            do {
                Class[] arrclass = this.availableSpecs;
                var5_5 = var6_7;
                if (n2 == arrclass.length) break;
                if (arrclass[n2] != null) {
                    try {
                        var5_5 = algorithmParameters.getParameterSpec(arrclass[n2]);
                        break;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                ++n2;
            } while (true);
            if (var5_5 == null) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("can't handle parameter ");
                ((StringBuilder)serializable).append(algorithmParameters.toString());
                throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString());
            }
        }
        this.engineInit(n, (Key)serializable, var5_5, secureRandom);
        this.engineParams = algorithmParameters;
    }

    @Override
    protected void engineInit(int n, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            this.engineInit(n, key, (AlgorithmParameterSpec)null, secureRandom);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException(invalidAlgorithmParameterException.getMessage());
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
    protected void engineInit(int var1_1, Key var2_2, AlgorithmParameterSpec var3_6, SecureRandom var4_7) throws InvalidKeyException, InvalidAlgorithmParameterException {
        block57 : {
            block55 : {
                block56 : {
                    block54 : {
                        block53 : {
                            var5_8 /* !! */  = null;
                            this.pbeSpec = null;
                            this.pbeAlgorithm = null;
                            this.engineParams = null;
                            this.aeadParams = null;
                            if (!(var2_2 instanceof SecretKey)) {
                                var4_7 = new StringBuilder();
                                var4_7.append("Key for algorithm ");
                                var3_6 = var5_8 /* !! */ ;
                                if (var2_2 != null) {
                                    var3_6 = var2_2.getAlgorithm();
                                }
                                var4_7.append((String)var3_6);
                                var4_7.append(" not suitable for symmetric enryption.");
                                throw new InvalidKeyException(var4_7.toString());
                            }
                            if (var3_6 == null) {
                                if (this.baseEngine.getAlgorithmName().startsWith("RC5-64") != false) throw new InvalidAlgorithmParameterException("RC5 requires an RC5ParametersSpec to be passed in.");
                            }
                            if (this.scheme != 2 && !(var2_2 instanceof PKCS12Key) || this.isBCPBEKeyWithoutIV((Key)var2_2)) break block53;
                            try {
                                var5_8 /* !! */  = (SecretKey)var2_2;
                            }
                            catch (Exception var2_3) {
                                throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
                            }
                            if (var3_6 instanceof PBEParameterSpec) {
                                this.pbeSpec = (PBEParameterSpec)var3_6;
                            }
                            if (var5_8 /* !! */  instanceof PBEKey && this.pbeSpec == null) {
                                var6_9 = (PBEKey)var5_8 /* !! */ ;
                                if (var6_9.getSalt() == null) throw new InvalidAlgorithmParameterException("PBEKey requires parameters to specify salt");
                                this.pbeSpec = new PBEParameterSpec(var6_9.getSalt(), var6_9.getIterationCount());
                            }
                            if (this.pbeSpec == null) {
                                if (var5_8 /* !! */  instanceof PBEKey == false) throw new InvalidKeyException("Algorithm requires a PBE key");
                            }
                            if (var2_2 instanceof BCPBEKey) {
                                var5_8 /* !! */  = ((BCPBEKey)var2_2).getParam();
                                if (!(var5_8 /* !! */  instanceof ParametersWithIV)) {
                                    if (var5_8 /* !! */  != null) throw new InvalidKeyException("Algorithm requires a PBE key suitable for PKCS12");
                                    throw new AssertionError((Object)"Unreachable code");
                                }
                            } else {
                                var5_8 /* !! */  = PBE.Util.makePBEParameters(var5_8 /* !! */ .getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
                            }
                            if (var5_8 /* !! */  instanceof ParametersWithIV) {
                                this.ivParam = (ParametersWithIV)var5_8 /* !! */ ;
                            }
                            break block54;
                        }
                        if (var2_2 instanceof BCPBEKey) {
                            var6_9 = (BCPBEKey)var2_2;
                            this.pbeAlgorithm = var6_9.getOID() != null ? var6_9.getOID().getId() : var6_9.getAlgorithm();
                            if (var6_9.getParam() != null) {
                                var5_8 /* !! */  = this.adjustParameters((AlgorithmParameterSpec)var3_6, var6_9.getParam());
                            } else {
                                if (var3_6 instanceof PBEParameterSpec == false) throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                                this.pbeSpec = (PBEParameterSpec)var3_6;
                                var5_8 /* !! */  = var6_9;
                                if (this.pbeSpec.getSalt().length != 0) {
                                    var5_8 /* !! */  = var6_9;
                                    if (this.pbeSpec.getIterationCount() > 0) {
                                        var5_8 /* !! */  = new BCPBEKey(var6_9.getAlgorithm(), var6_9.getOID(), var6_9.getType(), var6_9.getDigest(), var6_9.getKeySize(), var6_9.getIvSize(), new PBEKeySpec(var6_9.getPassword(), this.pbeSpec.getSalt(), this.pbeSpec.getIterationCount(), var6_9.getKeySize()), null);
                                    }
                                }
                                var5_8 /* !! */  = PBE.Util.makePBEParameters((BCPBEKey)var5_8 /* !! */ , (AlgorithmParameterSpec)var3_6, this.cipher.getUnderlyingCipher().getAlgorithmName());
                            }
                            if (var5_8 /* !! */  instanceof ParametersWithIV) {
                                this.ivParam = (ParametersWithIV)var5_8 /* !! */ ;
                            }
                        } else if (var2_2 instanceof PBEKey) {
                            var5_8 /* !! */  = (PBEKey)var2_2;
                            this.pbeSpec = (PBEParameterSpec)var3_6;
                            if (var5_8 /* !! */  instanceof PKCS12KeyWithParameters && this.pbeSpec == null) {
                                this.pbeSpec = new PBEParameterSpec(var5_8 /* !! */ .getSalt(), var5_8 /* !! */ .getIterationCount());
                            }
                            if ((var5_8 /* !! */  = PBE.Util.makePBEParameters(var5_8 /* !! */ .getEncoded(), this.scheme, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName())) instanceof ParametersWithIV) {
                                this.ivParam = (ParametersWithIV)var5_8 /* !! */ ;
                            }
                        } else {
                            var7_10 = this.scheme;
                            if (var7_10 == 0) throw new InvalidKeyException("Algorithm requires a PBE key");
                            if (var7_10 == 4) throw new InvalidKeyException("Algorithm requires a PBE key");
                            if (var7_10 == 1) throw new InvalidKeyException("Algorithm requires a PBE key");
                            if (var7_10 == 5) throw new InvalidKeyException("Algorithm requires a PBE key");
                            var5_8 /* !! */  = new KeyParameter(var2_2.getEncoded());
                        }
                    }
                    if (var3_6 instanceof AEADParameterSpec) {
                        if (!this.isAEADModeName(this.modeName)) {
                            if (this.cipher instanceof AEADGenericBlockCipher == false) throw new InvalidAlgorithmParameterException("AEADParameterSpec can only be used with AEAD modes.");
                        }
                        var6_9 = (AEADParameterSpec)var3_6;
                        var3_6 = var5_8 /* !! */  instanceof ParametersWithIV != false ? (KeyParameter)((ParametersWithIV)var5_8 /* !! */ ).getParameters() : (KeyParameter)var5_8 /* !! */ ;
                        this.aeadParams = var6_9 = new AEADParameters((KeyParameter)var3_6, var6_9.getMacSizeInBits(), var6_9.getNonce(), var6_9.getAssociatedData());
                    } else if (var3_6 instanceof IvParameterSpec) {
                        if (this.ivLength != 0) {
                            if ((var3_6 = (IvParameterSpec)var3_6).getIV().length != this.ivLength && !(this.cipher instanceof AEADGenericBlockCipher) && this.fixedIv) {
                                var2_2 = new StringBuilder();
                                var2_2.append("IV must be ");
                                var2_2.append(this.ivLength);
                                var2_2.append(" bytes long.");
                                throw new InvalidAlgorithmParameterException(var2_2.toString());
                            }
                            var6_9 = var5_8 /* !! */  instanceof ParametersWithIV != false ? new ParametersWithIV(((ParametersWithIV)var5_8 /* !! */ ).getParameters(), var3_6.getIV()) : new ParametersWithIV((CipherParameters)var5_8 /* !! */ , var3_6.getIV());
                            this.ivParam = (ParametersWithIV)var6_9;
                        } else {
                            var3_6 = this.modeName;
                            var6_9 = var5_8 /* !! */ ;
                            if (var3_6 != null) {
                                if (var3_6.equals("ECB") != false) throw new InvalidAlgorithmParameterException("ECB mode does not use an IV");
                                var6_9 = var5_8 /* !! */ ;
                            }
                        }
                    } else {
                        var6_9 = BaseBlockCipher.gcmSpecClass;
                        if (var6_9 != null && var6_9.isInstance(var3_6)) {
                            if (!this.isAEADModeName(this.modeName)) {
                                if (this.cipher instanceof AEADGenericBlockCipher == false) throw new InvalidAlgorithmParameterException("GCMParameterSpec can only be used with AEAD modes.");
                            }
                            try {
                                var8_11 = BaseBlockCipher.gcmSpecClass.getDeclaredMethod("getTLen", new Class[0]);
                                var9_12 = BaseBlockCipher.gcmSpecClass.getDeclaredMethod("getIV", new Class[0]);
                                var5_8 /* !! */  = var5_8 /* !! */  instanceof ParametersWithIV != false ? (KeyParameter)((ParametersWithIV)var5_8 /* !! */ ).getParameters() : (KeyParameter)var5_8 /* !! */ ;
                                this.aeadParams = var6_9 = new AEADParameters((KeyParameter)var5_8 /* !! */ , (Integer)var8_11.invoke(var3_6, new Object[0]), (byte[])var9_12.invoke(var3_6, new Object[0]));
                            }
                            catch (Exception var2_4) {
                                throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
                            }
                        } else {
                            var6_9 = var5_8 /* !! */ ;
                            if (var3_6 != null) {
                                if (var3_6 instanceof PBEParameterSpec == false) throw new InvalidAlgorithmParameterException("unknown parameter type.");
                                var6_9 = var5_8 /* !! */ ;
                            }
                        }
                    }
                    var3_6 = var6_9;
                    if (this.ivLength != 0) {
                        var3_6 = var6_9;
                        if (!(var6_9 instanceof ParametersWithIV)) {
                            var3_6 = var6_9;
                            if (!(var6_9 instanceof AEADParameters)) {
                                var5_8 /* !! */  = var4_7;
                                var3_6 = var5_8 /* !! */ ;
                                if (var5_8 /* !! */  == null) {
                                    var3_6 = CryptoServicesRegistrar.getSecureRandom();
                                }
                                if (var1_1 != 1 && var1_1 != 3) {
                                    var3_6 = var6_9;
                                    if (this.cipher.getUnderlyingCipher().getAlgorithmName().indexOf("PGPCFB") < 0) {
                                        if (this.isBCPBEKeyWithoutIV((Key)var2_2) == false) throw new InvalidAlgorithmParameterException("no IV set when one expected");
                                        System.err.println(" ******** DEPRECATED FUNCTIONALITY ********");
                                        System.err.println(" * You have initialized a cipher with a PBE key with no IV and");
                                        System.err.println(" * have not provided an IV in the AlgorithmParameterSpec.  This");
                                        System.err.println(" * configuration is deprecated.  The cipher will be initialized");
                                        System.err.println(" * with an all-zero IV, but in a future release this call will");
                                        System.err.println(" * throw an exception.");
                                        new InvalidAlgorithmParameterException("No IV set when using PBE key").printStackTrace(System.err);
                                        var3_6 = new ParametersWithIV((CipherParameters)var6_9, new byte[this.ivLength]);
                                        this.ivParam = (ParametersWithIV)var3_6;
                                    }
                                } else {
                                    var5_8 /* !! */  = new byte[this.ivLength];
                                    if (!this.isBCPBEKeyWithoutIV((Key)var2_2)) {
                                        var3_6.nextBytes(var5_8 /* !! */ );
                                    } else {
                                        System.err.println(" ******** DEPRECATED FUNCTIONALITY ********");
                                        System.err.println(" * You have initialized a cipher with a PBE key with no IV and");
                                        System.err.println(" * have not provided an IV in the AlgorithmParameterSpec.  This");
                                        System.err.println(" * configuration is deprecated.  The cipher will be initialized");
                                        System.err.println(" * with an all-zero IV, but in a future release this call will");
                                        System.err.println(" * throw an exception.");
                                        new InvalidAlgorithmParameterException("No IV set when using PBE key").printStackTrace(System.err);
                                    }
                                    var3_6 = new ParametersWithIV((CipherParameters)var6_9, var5_8 /* !! */ );
                                    this.ivParam = (ParametersWithIV)var3_6;
                                }
                            }
                        }
                    }
                    var2_2 = var3_6;
                    if (var4_7 != null) {
                        var2_2 = var3_6;
                        if (this.padded) {
                            var2_2 = new ParametersWithRandom((CipherParameters)var3_6, (SecureRandom)var4_7);
                        }
                    }
                    if (var1_1 == 1) break block55;
                    if (var1_1 == 2) break block56;
                    if (var1_1 != 3) {
                        if (var1_1 != 4) {
                            try {
                                var2_2 = new StringBuilder();
                                var2_2.append("unknown opmode ");
                                var2_2.append(var1_1);
                                var2_2.append(" passed");
                                var3_6 = new InvalidParameterException(var2_2.toString());
                                throw var3_6;
                            }
                            catch (Exception var2_5) {
                                throw new BaseWrapCipher.InvalidKeyOrParametersException(var2_5.getMessage(), var2_5);
                            }
                        } else {
                            ** GOTO lbl178
                        }
                    }
                    break block55;
                }
                this.cipher.init(false, (CipherParameters)var2_2);
                break block57;
            }
            this.cipher.init(true, (CipherParameters)var2_2);
        }
        if (this.cipher instanceof AEADGenericBlockCipher == false) return;
        if (this.aeadParams != null) return;
        var3_6 = AEADGenericBlockCipher.access$000((AEADGenericBlockCipher)this.cipher);
        this.aeadParams = var2_2 = new AEADParameters((KeyParameter)this.ivParam.getParameters(), var3_6.getMac().length * 8, this.ivParam.getIV());
    }

    @Override
    protected void engineSetMode(String object) throws NoSuchAlgorithmException {
        block15 : {
            block8 : {
                block14 : {
                    block13 : {
                        block12 : {
                            block11 : {
                                block10 : {
                                    block9 : {
                                        block7 : {
                                            this.modeName = Strings.toUpperCase((String)object);
                                            if (!this.modeName.equals("ECB")) break block7;
                                            this.ivLength = 0;
                                            this.cipher = new BufferedGenericBlockCipher(this.baseEngine);
                                            break block8;
                                        }
                                        if (!this.modeName.equals("CBC")) break block9;
                                        this.ivLength = this.baseEngine.getBlockSize();
                                        this.cipher = new BufferedGenericBlockCipher(new CBCBlockCipher(this.baseEngine));
                                        break block8;
                                    }
                                    if (!this.modeName.startsWith("OFB")) break block10;
                                    this.ivLength = this.baseEngine.getBlockSize();
                                    if (this.modeName.length() != 3) {
                                        int n = Integer.parseInt(this.modeName.substring(3));
                                        this.cipher = new BufferedGenericBlockCipher(new OFBBlockCipher(this.baseEngine, n));
                                    } else {
                                        object = this.baseEngine;
                                        this.cipher = new BufferedGenericBlockCipher(new OFBBlockCipher((BlockCipher)object, object.getBlockSize() * 8));
                                    }
                                    break block8;
                                }
                                if (!this.modeName.startsWith("CFB")) break block11;
                                this.ivLength = this.baseEngine.getBlockSize();
                                if (this.modeName.length() != 3) {
                                    int n = Integer.parseInt(this.modeName.substring(3));
                                    this.cipher = new BufferedGenericBlockCipher(new CFBBlockCipher(this.baseEngine, n));
                                } else {
                                    object = this.baseEngine;
                                    this.cipher = new BufferedGenericBlockCipher(new CFBBlockCipher((BlockCipher)object, object.getBlockSize() * 8));
                                }
                                break block8;
                            }
                            if (!this.modeName.startsWith("CTR")) break block12;
                            this.ivLength = this.baseEngine.getBlockSize();
                            this.fixedIv = false;
                            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
                            break block8;
                        }
                        if (!this.modeName.startsWith("CTS")) break block13;
                        this.ivLength = this.baseEngine.getBlockSize();
                        this.cipher = new BufferedGenericBlockCipher(new CTSBlockCipher(new CBCBlockCipher(this.baseEngine)));
                        break block8;
                    }
                    if (!this.modeName.startsWith("CCM")) break block14;
                    this.ivLength = 12;
                    this.cipher = new AEADGenericBlockCipher(new CCMBlockCipher(this.baseEngine));
                    break block8;
                }
                if (!this.modeName.startsWith("GCM")) break block15;
                this.ivLength = this.baseEngine.getBlockSize();
                this.cipher = new AEADGenericBlockCipher(new GCMBlockCipher(this.baseEngine));
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("can't support mode ");
        stringBuilder.append((String)object);
        throw new NoSuchAlgorithmException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void engineSetPadding(String string) throws NoSuchPaddingException {
        CharSequence charSequence = Strings.toUpperCase(string);
        if (((String)charSequence).equals("NOPADDING")) {
            if (!this.cipher.wrapOnNoPadding()) return;
            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.cipher.getUnderlyingCipher()));
            return;
        }
        if (!(((String)charSequence).equals("WITHCTS") || ((String)charSequence).equals("CTSPADDING") || ((String)charSequence).equals("CS3PADDING"))) {
            this.padded = true;
            if (this.isAEADModeName(this.modeName)) throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
            if (!((String)charSequence).equals("PKCS5PADDING") && !((String)charSequence).equals("PKCS7PADDING")) {
                if (((String)charSequence).equals("ZEROBYTEPADDING")) {
                    this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ZeroBytePadding());
                    return;
                }
                if (!((String)charSequence).equals("ISO10126PADDING") && !((String)charSequence).equals("ISO10126-2PADDING")) {
                    if (!((String)charSequence).equals("X9.23PADDING") && !((String)charSequence).equals("X923PADDING")) {
                        if (!((String)charSequence).equals("ISO7816-4PADDING") && !((String)charSequence).equals("ISO9797-1PADDING")) {
                            if (((String)charSequence).equals("TBCPADDING")) {
                                this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new TBCPadding());
                                return;
                            }
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Padding ");
                            ((StringBuilder)charSequence).append(string);
                            ((StringBuilder)charSequence).append(" unknown.");
                            throw new NoSuchPaddingException(((StringBuilder)charSequence).toString());
                        }
                        this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO7816d4Padding());
                        return;
                    }
                    this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new X923Padding());
                    return;
                }
                this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO10126d2Padding());
                return;
            }
            this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher());
            return;
        }
        this.cipher = new BufferedGenericBlockCipher(new CTSBlockCipher(this.cipher.getUnderlyingCipher()));
    }

    @Override
    protected int engineUpdate(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException {
        if (this.cipher.getUpdateOutputSize(n2) + n3 <= arrby2.length) {
            try {
                n = this.cipher.processBytes(arrby, n, n2, arrby2, n3);
                return n;
            }
            catch (DataLengthException dataLengthException) {
                throw new IllegalStateException(dataLengthException.toString());
            }
        }
        throw new ShortBufferException("output buffer too short for input.");
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        int n3 = this.cipher.getUpdateOutputSize(n2);
        if (n3 > 0) {
            byte[] arrby2 = new byte[n3];
            if ((n = this.cipher.processBytes(arrby, n, n2, arrby2, 0)) == 0) {
                return null;
            }
            if (n != arrby2.length) {
                arrby = new byte[n];
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)n);
                return arrby;
            }
            return arrby2;
        }
        this.cipher.processBytes(arrby, n, n2, null, 0);
        return null;
    }

    @Override
    protected void engineUpdateAAD(ByteBuffer byteBuffer) {
        int n = byteBuffer.arrayOffset();
        int n2 = byteBuffer.position();
        int n3 = byteBuffer.limit();
        int n4 = byteBuffer.position();
        this.engineUpdateAAD(byteBuffer.array(), n + n2, n3 - n4);
    }

    @Override
    protected void engineUpdateAAD(byte[] arrby, int n, int n2) {
        this.cipher.updateAAD(arrby, n, n2);
    }

    private static class AEADGenericBlockCipher
    implements GenericBlockCipher {
        private static final Constructor aeadBadTagConstructor;
        private AEADBlockCipher cipher;

        static {
            Class class_ = ClassUtil.loadClass(BaseBlockCipher.class, "javax.crypto.AEADBadTagException");
            aeadBadTagConstructor = class_ != null ? AEADGenericBlockCipher.findExceptionConstructor(class_) : null;
        }

        AEADGenericBlockCipher(AEADBlockCipher aEADBlockCipher) {
            this.cipher = aEADBlockCipher;
        }

        static /* synthetic */ AEADBlockCipher access$000(AEADGenericBlockCipher aEADGenericBlockCipher) {
            return aEADGenericBlockCipher.cipher;
        }

        private static Constructor findExceptionConstructor(Class genericDeclaration) {
            try {
                genericDeclaration = genericDeclaration.getConstructor(String.class);
                return genericDeclaration;
            }
            catch (Exception exception) {
                return null;
            }
        }

        @Override
        public int doFinal(byte[] object, int n) throws IllegalStateException, BadPaddingException {
            try {
                n = this.cipher.doFinal((byte[])object, n);
                return n;
            }
            catch (InvalidCipherTextException invalidCipherTextException) {
                Object object2 = aeadBadTagConstructor;
                if (object2 != null) {
                    object = null;
                    try {
                        object2 = (BadPaddingException)((Constructor)object2).newInstance(invalidCipherTextException.getMessage());
                        object = object2;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (object != null) {
                        throw object;
                    }
                }
                throw new BadPaddingException(invalidCipherTextException.getMessage());
            }
        }

        @Override
        public String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        @Override
        public int getOutputSize(int n) {
            return this.cipher.getOutputSize(n);
        }

        @Override
        public BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        @Override
        public int getUpdateOutputSize(int n) {
            return this.cipher.getUpdateOutputSize(n);
        }

        @Override
        public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
            this.cipher.init(bl, cipherParameters);
        }

        @Override
        public int processByte(byte by, byte[] arrby, int n) throws DataLengthException {
            return this.cipher.processByte(by, arrby, n);
        }

        @Override
        public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException {
            return this.cipher.processBytes(arrby, n, n2, arrby2, n3);
        }

        @Override
        public void updateAAD(byte[] arrby, int n, int n2) {
            this.cipher.processAADBytes(arrby, n, n2);
        }

        @Override
        public boolean wrapOnNoPadding() {
            return false;
        }
    }

    private static class BufferedGenericBlockCipher
    implements GenericBlockCipher {
        private BufferedBlockCipher cipher;

        BufferedGenericBlockCipher(BlockCipher blockCipher) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher);
        }

        BufferedGenericBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher, blockCipherPadding);
        }

        BufferedGenericBlockCipher(BufferedBlockCipher bufferedBlockCipher) {
            this.cipher = bufferedBlockCipher;
        }

        @Override
        public int doFinal(byte[] arrby, int n) throws IllegalStateException, BadPaddingException {
            try {
                n = this.cipher.doFinal(arrby, n);
                return n;
            }
            catch (InvalidCipherTextException invalidCipherTextException) {
                throw new BadPaddingException(invalidCipherTextException.getMessage());
            }
        }

        @Override
        public String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        @Override
        public int getOutputSize(int n) {
            return this.cipher.getOutputSize(n);
        }

        @Override
        public BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        @Override
        public int getUpdateOutputSize(int n) {
            return this.cipher.getUpdateOutputSize(n);
        }

        @Override
        public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
            this.cipher.init(bl, cipherParameters);
        }

        @Override
        public int processByte(byte by, byte[] arrby, int n) throws DataLengthException {
            return this.cipher.processByte(by, arrby, n);
        }

        @Override
        public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException {
            return this.cipher.processBytes(arrby, n, n2, arrby2, n3);
        }

        @Override
        public void updateAAD(byte[] arrby, int n, int n2) {
            throw new UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        @Override
        public boolean wrapOnNoPadding() {
            boolean bl = !(this.cipher instanceof CTSBlockCipher);
            return bl;
        }
    }

    private static interface GenericBlockCipher {
        public int doFinal(byte[] var1, int var2) throws IllegalStateException, BadPaddingException;

        public String getAlgorithmName();

        public int getOutputSize(int var1);

        public BlockCipher getUnderlyingCipher();

        public int getUpdateOutputSize(int var1);

        public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

        public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException;

        public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

        public void updateAAD(byte[] var1, int var2, int var3);

        public boolean wrapOnNoPadding();
    }

}

