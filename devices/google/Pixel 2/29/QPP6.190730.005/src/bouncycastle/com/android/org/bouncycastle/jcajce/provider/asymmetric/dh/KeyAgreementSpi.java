/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.crypto.BasicAgreement;
import com.android.org.bouncycastle.crypto.DerivationFunction;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import com.android.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import com.android.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyAgreementSpi
extends BaseAgreementSpi {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private BigInteger g;
    private final BasicAgreement mqvAgreement;
    private BigInteger p;
    private byte[] result;
    private BigInteger x;

    public KeyAgreementSpi() {
        this("Diffie-Hellman", null);
    }

    public KeyAgreementSpi(String string, BasicAgreement basicAgreement, DerivationFunction derivationFunction) {
        super(string, derivationFunction);
        this.mqvAgreement = basicAgreement;
    }

    public KeyAgreementSpi(String string, DerivationFunction derivationFunction) {
        super(string, derivationFunction);
        this.mqvAgreement = null;
    }

    private DHPrivateKeyParameters generatePrivateKeyParameter(PrivateKey object) throws InvalidKeyException {
        if (object instanceof DHPrivateKey) {
            if (object instanceof BCDHPrivateKey) {
                return ((BCDHPrivateKey)object).engineGetKeyParameters();
            }
            DHPrivateKey dHPrivateKey = (DHPrivateKey)object;
            object = dHPrivateKey.getParams();
            return new DHPrivateKeyParameters(dHPrivateKey.getX(), new DHParameters(((DHParameterSpec)object).getP(), ((DHParameterSpec)object).getG(), null, ((DHParameterSpec)object).getL()));
        }
        throw new InvalidKeyException("private key not a DHPrivateKey");
    }

    private DHPublicKeyParameters generatePublicKeyParameter(PublicKey object) throws InvalidKeyException {
        if (object instanceof DHPublicKey) {
            if (object instanceof BCDHPublicKey) {
                return ((BCDHPublicKey)object).engineGetKeyParameters();
            }
            DHPublicKey dHPublicKey = (DHPublicKey)object;
            object = dHPublicKey.getParams();
            if (object instanceof DHDomainParameterSpec) {
                return new DHPublicKeyParameters(dHPublicKey.getY(), ((DHDomainParameterSpec)object).getDomainParameters());
            }
            return new DHPublicKeyParameters(dHPublicKey.getY(), new DHParameters(((DHParameterSpec)object).getP(), ((DHParameterSpec)object).getG(), null, ((DHParameterSpec)object).getL()));
        }
        throw new InvalidKeyException("public key not a DHPublicKey");
    }

    protected byte[] bigIntToBytes(BigInteger arrby) {
        int n = (this.p.bitLength() + 7) / 8;
        if ((arrby = arrby.toByteArray()).length == n) {
            return arrby;
        }
        if (arrby[0] == 0 && arrby.length == n + 1) {
            byte[] arrby2 = new byte[arrby.length - 1];
            System.arraycopy((byte[])arrby, (int)1, (byte[])arrby2, (int)0, (int)arrby2.length);
            return arrby2;
        }
        byte[] arrby3 = new byte[n];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby3, (int)(arrby3.length - arrby.length), (int)arrby.length);
        return arrby3;
    }

    @Override
    protected byte[] calcSecret() {
        return this.result;
    }

    @Override
    protected Key engineDoPhase(Key serializable, boolean bl) throws InvalidKeyException, IllegalStateException {
        if (this.x != null) {
            if (serializable instanceof DHPublicKey) {
                DHPublicKey dHPublicKey = (DHPublicKey)serializable;
                if (dHPublicKey.getParams().getG().equals(this.g) && dHPublicKey.getParams().getP().equals(this.p)) {
                    if ((serializable = ((DHPublicKey)serializable).getY()) != null && ((BigInteger)serializable).compareTo(TWO) >= 0 && ((BigInteger)serializable).compareTo(this.p.subtract(ONE)) < 0) {
                        if (((BigInteger)(serializable = ((BigInteger)serializable).modPow(this.x, this.p))).compareTo(ONE) != 0) {
                            this.result = this.bigIntToBytes((BigInteger)serializable);
                            if (bl) {
                                return null;
                            }
                            return new BCDHPublicKey((BigInteger)serializable, dHPublicKey.getParams());
                        }
                        throw new InvalidKeyException("Shared key can't be 1");
                    }
                    throw new InvalidKeyException("Invalid DH PublicKey");
                }
                throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
            }
            throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    @Override
    protected int engineGenerateSecret(byte[] arrby, int n) throws IllegalStateException, ShortBufferException {
        if (this.x != null) {
            return super.engineGenerateSecret(arrby, n);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    @Override
    protected SecretKey engineGenerateSecret(String string) throws NoSuchAlgorithmException {
        if (this.x != null) {
            if (string.equals("TlsPremasterSecret")) {
                return new SecretKeySpec(KeyAgreementSpi.trimZeroes(this.result), string);
            }
            return super.engineGenerateSecret(string);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    @Override
    protected byte[] engineGenerateSecret() throws IllegalStateException {
        if (this.x != null) {
            return super.engineGenerateSecret();
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    @Override
    protected void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (key instanceof DHPrivateKey) {
            key = (DHPrivateKey)key;
            this.p = key.getParams().getP();
            this.g = key.getParams().getG();
            this.x = key.getX();
            this.result = this.bigIntToBytes(this.x);
            return;
        }
        throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!(key instanceof DHPrivateKey)) throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
        key = (DHPrivateKey)key;
        if (algorithmParameterSpec != null) {
            if (algorithmParameterSpec instanceof DHParameterSpec) {
                algorithmParameterSpec = (DHParameterSpec)algorithmParameterSpec;
                this.p = ((DHParameterSpec)algorithmParameterSpec).getP();
                this.g = ((DHParameterSpec)algorithmParameterSpec).getG();
                this.ukmParameters = null;
            } else {
                if (!(algorithmParameterSpec instanceof UserKeyingMaterialSpec)) throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
                if (this.kdf == null) throw new InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
                this.p = key.getParams().getP();
                this.g = key.getParams().getG();
                this.ukmParameters = ((UserKeyingMaterialSpec)algorithmParameterSpec).getUserKeyingMaterial();
            }
        } else {
            this.p = key.getParams().getP();
            this.g = key.getParams().getG();
        }
        this.x = key.getX();
        this.result = this.bigIntToBytes(this.x);
    }
}

