/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.encodings.OAEPEncoding;
import com.android.org.bouncycastle.crypto.encodings.PKCS1Encoding;
import com.android.org.bouncycastle.crypto.engines.RSABlindedEngine;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import com.android.org.bouncycastle.jcajce.provider.util.DigestFactory;
import com.android.org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.util.Strings;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class CipherSpi
extends BaseCipherSpi {
    private BaseCipherSpi.ErasableOutputStream bOut = new BaseCipherSpi.ErasableOutputStream();
    private AsymmetricBlockCipher cipher;
    private AlgorithmParameters engineParams;
    private final JcaJceHelper helper = new DefaultJcaJceHelper();
    private AlgorithmParameterSpec paramSpec;
    private boolean privateKeyOnly = false;
    private boolean publicKeyOnly = false;

    public CipherSpi(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.cipher = asymmetricBlockCipher;
    }

    public CipherSpi(OAEPParameterSpec oAEPParameterSpec) {
        try {
            this.initFromSpec(oAEPParameterSpec);
            return;
        }
        catch (NoSuchPaddingException noSuchPaddingException) {
            throw new IllegalArgumentException(noSuchPaddingException.getMessage());
        }
    }

    public CipherSpi(boolean bl, boolean bl2, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.publicKeyOnly = bl;
        this.privateKeyOnly = bl2;
        this.cipher = asymmetricBlockCipher;
    }

    /*
     * Exception decompiling
     */
    private byte[] getOutput() throws BadPaddingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void initFromSpec(OAEPParameterSpec object) throws NoSuchPaddingException {
        MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec)((OAEPParameterSpec)object).getMGFParameters();
        Digest digest = DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
        if (digest != null) {
            this.cipher = new OAEPEncoding(new RSABlindedEngine(), digest, ((PSource.PSpecified)((OAEPParameterSpec)object).getPSource()).getValue());
            this.paramSpec = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("no match on OAEP constructor for digest algorithm: ");
        ((StringBuilder)object).append(mGF1ParameterSpec.getDigestAlgorithm());
        throw new NoSuchPaddingException(((StringBuilder)object).toString());
    }

    @Override
    protected int engineDoFinal(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        block7 : {
            block10 : {
                block9 : {
                    block8 : {
                        if (this.engineGetOutputSize(n2) + n3 > arrby2.length) break block7;
                        if (arrby != null) {
                            this.bOut.write(arrby, n, n2);
                        }
                        if (!(this.cipher instanceof RSABlindedEngine)) break block8;
                        if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
                        }
                        break block9;
                    }
                    if (this.bOut.size() > this.cipher.getInputBlockSize()) break block10;
                }
                arrby = this.getOutput();
                for (n = 0; n != arrby.length; ++n) {
                    arrby2[n3 + n] = arrby[n];
                }
                return arrby.length;
            }
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        throw new ShortBufferException("output buffer too short for input.");
    }

    @Override
    protected byte[] engineDoFinal(byte[] arrby, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        block8 : {
            block7 : {
                block6 : {
                    if (arrby != null) {
                        this.bOut.write(arrby, n, n2);
                    }
                    if (!(this.cipher instanceof RSABlindedEngine)) break block6;
                    if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
                    }
                    break block7;
                }
                if (this.bOut.size() > this.cipher.getInputBlockSize()) break block8;
            }
            return this.getOutput();
        }
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }

    @Override
    protected int engineGetBlockSize() {
        try {
            int n = this.cipher.getInputBlockSize();
            return n;
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalStateException("RSA Cipher not initialised");
        }
    }

    @Override
    protected int engineGetKeySize(Key key) {
        if (key instanceof RSAPrivateKey) {
            return ((RSAPrivateKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPublicKey) {
            return ((RSAPublicKey)key).getModulus().bitLength();
        }
        throw new IllegalArgumentException("not an RSA key!");
    }

    @Override
    protected int engineGetOutputSize(int n) {
        try {
            n = this.cipher.getOutputBlockSize();
            return n;
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalStateException("RSA Cipher not initialised");
        }
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.paramSpec != null) {
            try {
                this.engineParams = this.helper.createAlgorithmParameters("OAEP");
                this.engineParams.init(this.paramSpec);
            }
            catch (Exception exception) {
                throw new RuntimeException(exception.toString());
            }
        }
        return this.engineParams;
    }

    @Override
    protected void engineInit(int n, Key serializable, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        OAEPParameterSpec oAEPParameterSpec = null;
        if (algorithmParameters != null) {
            try {
                oAEPParameterSpec = algorithmParameters.getParameterSpec(OAEPParameterSpec.class);
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("cannot recognise parameters: ");
                ((StringBuilder)serializable).append(invalidParameterSpecException.toString());
                throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString(), invalidParameterSpecException);
            }
        }
        this.engineParams = algorithmParameters;
        this.engineInit(n, (Key)serializable, oAEPParameterSpec, secureRandom);
    }

    @Override
    protected void engineInit(int n, Key key, SecureRandom serializable) throws InvalidKeyException {
        try {
            this.engineInit(n, key, (AlgorithmParameterSpec)null, (SecureRandom)serializable);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Eeeek! ");
            ((StringBuilder)serializable).append(invalidAlgorithmParameterException.toString());
            throw new InvalidKeyException(((StringBuilder)serializable).toString(), invalidAlgorithmParameterException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(int var1_1, Key var2_2, AlgorithmParameterSpec var3_3, SecureRandom var4_4) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (var3_3 != null && !(var3_3 instanceof OAEPParameterSpec)) {
            var2_2 = new StringBuilder();
            var2_2.append("unknown parameter type: ");
            var2_2.append(var3_3.getClass().getName());
            throw new InvalidAlgorithmParameterException(var2_2.toString());
        }
        if (var2_2 instanceof RSAPublicKey) {
            if (this.privateKeyOnly) {
                if (var1_1 == 1) throw new InvalidKeyException("mode 1 requires RSAPrivateKey");
            }
            var2_2 = RSAUtil.generatePublicKeyParameter((RSAPublicKey)var2_2);
        } else {
            if (var2_2 instanceof RSAPrivateKey == false) throw new InvalidKeyException("unknown key type passed to RSA");
            if (this.publicKeyOnly) {
                if (var1_1 == 1) throw new InvalidKeyException("mode 2 requires RSAPublicKey");
            }
            var2_2 = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var2_2);
        }
        if (var3_3 != null) {
            var5_5 = (OAEPParameterSpec)var3_3;
            this.paramSpec = var3_3;
            if (!var5_5.getMGFAlgorithm().equalsIgnoreCase("MGF1")) {
                if (var5_5.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId()) == false) throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
            }
            if (var5_5.getMGFParameters() instanceof MGF1ParameterSpec == false) throw new InvalidAlgorithmParameterException("unkown MGF parameters");
            var6_6 = DigestFactory.getDigest(var5_5.getDigestAlgorithm());
            if (var6_6 == null) {
                var2_2 = new StringBuilder();
                var2_2.append("no match on digest algorithm: ");
                var2_2.append(var5_5.getDigestAlgorithm());
                throw new InvalidAlgorithmParameterException(var2_2.toString());
            }
            var3_3 = (MGF1ParameterSpec)var5_5.getMGFParameters();
            var7_7 = DigestFactory.getDigest(var3_3.getDigestAlgorithm());
            if (var7_7 == null) {
                var2_2 = new StringBuilder();
                var2_2.append("no match on MGF digest algorithm: ");
                var2_2.append(var3_3.getDigestAlgorithm());
                throw new InvalidAlgorithmParameterException(var2_2.toString());
            }
            this.cipher = new OAEPEncoding(new RSABlindedEngine(), var6_6, var7_7, ((PSource.PSpecified)var5_5.getPSource()).getValue());
        }
        var3_3 = var2_2;
        if (!(this.cipher instanceof RSABlindedEngine)) {
            var3_3 = var4_4 != null ? new ParametersWithRandom((CipherParameters)var2_2, var4_4) : new ParametersWithRandom((CipherParameters)var2_2, CryptoServicesRegistrar.getSecureRandom());
        }
        this.bOut.reset();
        if (var1_1 != 1) {
            if (var1_1 != 2) {
                if (var1_1 != 3) {
                    if (var1_1 != 4) {
                        var2_2 = new StringBuilder();
                        var2_2.append("unknown opmode ");
                        var2_2.append(var1_1);
                        var2_2.append(" passed to RSA");
                        throw new InvalidParameterException(var2_2.toString());
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000: // 3 sources:
            {
                this.cipher.init(false, (CipherParameters)var3_3);
                return;
            }
        }
        this.cipher.init(true, (CipherParameters)var3_3);
    }

    @Override
    protected void engineSetMode(String string) throws NoSuchAlgorithmException {
        CharSequence charSequence = Strings.toUpperCase(string);
        if (!((String)charSequence).equals("NONE") && !((String)charSequence).equals("ECB")) {
            if (((String)charSequence).equals("1")) {
                this.privateKeyOnly = true;
                this.publicKeyOnly = false;
                return;
            }
            if (((String)charSequence).equals("2")) {
                this.privateKeyOnly = false;
                this.publicKeyOnly = true;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("can't support mode ");
            ((StringBuilder)charSequence).append(string);
            throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString());
        }
    }

    @Override
    protected void engineSetPadding(String string) throws NoSuchPaddingException {
        CharSequence charSequence = Strings.toUpperCase(string);
        if (((String)charSequence).equals("NOPADDING")) {
            this.cipher = new RSABlindedEngine();
        } else if (((String)charSequence).equals("PKCS1PADDING")) {
            this.cipher = new PKCS1Encoding(new RSABlindedEngine());
        } else if (((String)charSequence).equals("OAEPWITHMD5ANDMGF1PADDING")) {
            this.initFromSpec(new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSource.PSpecified.DEFAULT));
        } else if (((String)charSequence).equals("OAEPPADDING")) {
            this.initFromSpec(OAEPParameterSpec.DEFAULT);
        } else if (!((String)charSequence).equals("OAEPWITHSHA1ANDMGF1PADDING") && !((String)charSequence).equals("OAEPWITHSHA-1ANDMGF1PADDING")) {
            if (!((String)charSequence).equals("OAEPWITHSHA224ANDMGF1PADDING") && !((String)charSequence).equals("OAEPWITHSHA-224ANDMGF1PADDING")) {
                if (!((String)charSequence).equals("OAEPWITHSHA256ANDMGF1PADDING") && !((String)charSequence).equals("OAEPWITHSHA-256ANDMGF1PADDING")) {
                    if (!((String)charSequence).equals("OAEPWITHSHA384ANDMGF1PADDING") && !((String)charSequence).equals("OAEPWITHSHA-384ANDMGF1PADDING")) {
                        if (!((String)charSequence).equals("OAEPWITHSHA512ANDMGF1PADDING") && !((String)charSequence).equals("OAEPWITHSHA-512ANDMGF1PADDING")) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(string);
                            ((StringBuilder)charSequence).append(" unavailable with RSA.");
                            throw new NoSuchPaddingException(((StringBuilder)charSequence).toString());
                        }
                        this.initFromSpec(new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT));
                    } else {
                        this.initFromSpec(new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSource.PSpecified.DEFAULT));
                    }
                } else {
                    this.initFromSpec(new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
                }
            } else {
                this.initFromSpec(new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSource.PSpecified.DEFAULT));
            }
        } else {
            this.initFromSpec(OAEPParameterSpec.DEFAULT);
        }
    }

    @Override
    protected int engineUpdate(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        block7 : {
            block6 : {
                block5 : {
                    this.bOut.write(arrby, n, n2);
                    if (!(this.cipher instanceof RSABlindedEngine)) break block5;
                    if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
                    }
                    break block6;
                }
                if (this.bOut.size() > this.cipher.getInputBlockSize()) break block7;
            }
            return 0;
        }
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        block7 : {
            block6 : {
                block5 : {
                    this.bOut.write(arrby, n, n2);
                    if (!(this.cipher instanceof RSABlindedEngine)) break block5;
                    if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
                    }
                    break block6;
                }
                if (this.bOut.size() > this.cipher.getInputBlockSize()) break block7;
            }
            return null;
        }
        throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
    }

    public static class NoPadding
    extends CipherSpi {
        public NoPadding() {
            super(new RSABlindedEngine());
        }
    }

}

