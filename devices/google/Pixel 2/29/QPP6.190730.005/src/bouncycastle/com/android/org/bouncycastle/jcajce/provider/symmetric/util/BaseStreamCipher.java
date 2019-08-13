/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.StreamCipher;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.jcajce.PKCS12Key;
import com.android.org.bouncycastle.jcajce.PKCS12KeyWithParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

public class BaseStreamCipher
extends BaseWrapCipher
implements PBE {
    private Class[] availableSpecs = new Class[]{IvParameterSpec.class, PBEParameterSpec.class};
    private StreamCipher cipher;
    private int digest;
    private int ivLength = 0;
    private ParametersWithIV ivParam;
    private int keySizeInBits;
    private String pbeAlgorithm = null;
    private PBEParameterSpec pbeSpec = null;

    protected BaseStreamCipher(StreamCipher streamCipher, int n) {
        this(streamCipher, n, -1, -1);
    }

    protected BaseStreamCipher(StreamCipher streamCipher, int n, int n2, int n3) {
        this.cipher = streamCipher;
        this.ivLength = n;
        this.keySizeInBits = n2;
        this.digest = n3;
    }

    @Override
    protected int engineDoFinal(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException {
        if (n3 + n2 <= arrby2.length) {
            if (n2 != 0) {
                this.cipher.processBytes(arrby, n, n2, arrby2, n3);
            }
            this.cipher.reset();
            return n2;
        }
        throw new ShortBufferException("output buffer too short for input.");
    }

    @Override
    protected byte[] engineDoFinal(byte[] arrby, int n, int n2) {
        if (n2 != 0) {
            arrby = this.engineUpdate(arrby, n, n2);
            this.cipher.reset();
            return arrby;
        }
        this.cipher.reset();
        return new byte[0];
    }

    @Override
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override
    protected byte[] engineGetIV() {
        Object object = this.ivParam;
        object = object != null ? object.getIV() : null;
        return object;
    }

    @Override
    protected int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return n;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    AlgorithmParameters algorithmParameters = this.createParametersInstance(this.pbeAlgorithm);
                    algorithmParameters.init(this.pbeSpec);
                    return algorithmParameters;
                }
                catch (Exception exception) {
                    return null;
                }
            }
            if (this.ivParam != null) {
                Object object;
                Object object2 = object = this.cipher.getAlgorithmName();
                if (((String)object).indexOf(47) >= 0) {
                    object2 = ((String)object).substring(0, ((String)object).indexOf(47));
                }
                if (((String)object2).startsWith("ChaCha7539")) {
                    object = "ChaCha7539";
                } else if (((String)object2).startsWith("Grain")) {
                    object = "Grainv1";
                } else {
                    object = object2;
                    if (((String)object2).startsWith("HC")) {
                        int n = ((String)object2).indexOf(45);
                        object = new StringBuilder();
                        ((StringBuilder)object).append(((String)object2).substring(0, n));
                        ((StringBuilder)object).append(((String)object2).substring(n + 1));
                        object = ((StringBuilder)object).toString();
                    }
                }
                try {
                    this.engineParams = this.createParametersInstance((String)object);
                    object = this.engineParams;
                    object2 = new IvParameterSpec(this.ivParam.getIV());
                    ((AlgorithmParameters)object).init((AlgorithmParameterSpec)object2);
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
                try {
                    var5_5 = algorithmParameters.getParameterSpec(arrclass[n2]);
                }
                catch (Exception exception) {
                    ++n2;
                    continue;
                }
                break;
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
    protected void engineInit(int var1_1, Key var2_2, AlgorithmParameterSpec var3_4, SecureRandom var4_5) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.engineParams = null;
        if (!(var2_2 instanceof SecretKey)) {
            var3_4 = new StringBuilder();
            var3_4.append("Key for algorithm ");
            var3_4.append(var2_2.getAlgorithm());
            var3_4.append(" not suitable for symmetric enryption.");
            throw new InvalidKeyException(var3_4.toString());
        }
        if (var2_2 instanceof PKCS12Key) {
            var2_2 = (PKCS12Key)var2_2;
            this.pbeSpec = (PBEParameterSpec)var3_4;
            if (var2_2 instanceof PKCS12KeyWithParameters && this.pbeSpec == null) {
                this.pbeSpec = new PBEParameterSpec(((PKCS12KeyWithParameters)var2_2).getSalt(), ((PKCS12KeyWithParameters)var2_2).getIterationCount());
            }
            var2_2 = PBE.Util.makePBEParameters(var2_2.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
        } else if (var2_2 instanceof BCPBEKey) {
            var5_6 = (BCPBEKey)var2_2;
            this.pbeAlgorithm = var5_6.getOID() != null ? var5_6.getOID().getId() : var5_6.getAlgorithm();
            if (var5_6.getParam() != null) {
                var2_2 = var5_6.getParam();
                this.pbeSpec = new PBEParameterSpec(var5_6.getSalt(), var5_6.getIterationCount());
            } else {
                if (var3_4 instanceof PBEParameterSpec == false) throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                var2_2 = PBE.Util.makePBEParameters(var5_6, (AlgorithmParameterSpec)var3_4, this.cipher.getAlgorithmName());
                this.pbeSpec = (PBEParameterSpec)var3_4;
            }
            if (var5_6.getIvSize() != 0) {
                this.ivParam = (ParametersWithIV)var2_2;
            }
        } else if (var3_4 == null) {
            if (this.digest > 0) throw new InvalidKeyException("Algorithm requires a PBE key");
            var2_2 = new KeyParameter(var2_2.getEncoded());
        } else {
            if (var3_4 instanceof IvParameterSpec == false) throw new InvalidAlgorithmParameterException("unknown parameter type.");
            var2_2 = new ParametersWithIV(new KeyParameter(var2_2.getEncoded()), ((IvParameterSpec)var3_4).getIV());
            this.ivParam = (ParametersWithIV)var2_2;
        }
        var3_4 = var2_2;
        if (this.ivLength != 0) {
            var3_4 = var2_2;
            if (!(var2_2 instanceof ParametersWithIV)) {
                var3_4 = var4_5;
                if (var4_5 == null) {
                    var3_4 = CryptoServicesRegistrar.getSecureRandom();
                }
                if (var1_1 != 1) {
                    if (var1_1 != 3) throw new InvalidAlgorithmParameterException("no IV set when one expected");
                }
                var4_5 = new byte[this.ivLength];
                var3_4.nextBytes(var4_5);
                var3_4 = new ParametersWithIV((CipherParameters)var2_2, var4_5);
                this.ivParam = (ParametersWithIV)var3_4;
            }
        }
        if (var1_1 != 1) {
            if (var1_1 != 2) {
                if (var1_1 != 3) {
                    if (var1_1 != 4) {
                        try {
                            var2_2 = new StringBuilder();
                            var2_2.append("unknown opmode ");
                            var2_2.append(var1_1);
                            var2_2.append(" passed");
                            var3_4 = new InvalidParameterException(var2_2.toString());
                            throw var3_4;
                        }
                        catch (Exception var2_3) {
                            throw new InvalidKeyException(var2_3.getMessage());
                        }
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000: // 3 sources:
            {
                this.cipher.init(false, (CipherParameters)var3_4);
                return;
            }
        }
        this.cipher.init(true, (CipherParameters)var3_4);
    }

    @Override
    protected void engineSetMode(String string) throws NoSuchAlgorithmException {
        if (!string.equalsIgnoreCase("ECB") && !string.equals("NONE")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("can't support mode ");
            stringBuilder.append(string);
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
    }

    @Override
    protected void engineSetPadding(String string) throws NoSuchPaddingException {
        if (string.equalsIgnoreCase("NoPadding")) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Padding ");
        stringBuilder.append(string);
        stringBuilder.append(" unknown.");
        throw new NoSuchPaddingException(stringBuilder.toString());
    }

    @Override
    protected int engineUpdate(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException {
        if (n3 + n2 <= arrby2.length) {
            try {
                this.cipher.processBytes(arrby, n, n2, arrby2, n3);
                return n2;
            }
            catch (DataLengthException dataLengthException) {
                throw new IllegalStateException(dataLengthException.getMessage());
            }
        }
        throw new ShortBufferException("output buffer too short for input.");
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n2];
        this.cipher.processBytes(arrby, n, n2, arrby2, 0);
        return arrby2;
    }
}

