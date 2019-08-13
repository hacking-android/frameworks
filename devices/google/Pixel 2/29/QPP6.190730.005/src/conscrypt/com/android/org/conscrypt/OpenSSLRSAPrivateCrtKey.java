/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;

final class OpenSSLRSAPrivateCrtKey
extends OpenSSLRSAPrivateKey
implements RSAPrivateCrtKey {
    private static final long serialVersionUID = 3785291944868707197L;
    private BigInteger crtCoefficient;
    private BigInteger primeExponentP;
    private BigInteger primeExponentQ;
    private BigInteger primeP;
    private BigInteger primeQ;
    private BigInteger publicExponent;

    OpenSSLRSAPrivateCrtKey(OpenSSLKey openSSLKey) {
        super(openSSLKey);
    }

    OpenSSLRSAPrivateCrtKey(OpenSSLKey openSSLKey, byte[][] arrby) {
        super(openSSLKey, arrby);
    }

    OpenSSLRSAPrivateCrtKey(RSAPrivateCrtKeySpec rSAPrivateCrtKeySpec) throws InvalidKeySpecException {
        super(OpenSSLRSAPrivateCrtKey.init(rSAPrivateCrtKeySpec));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static OpenSSLKey getInstance(RSAPrivateCrtKey object) throws InvalidKeyException {
        if (object.getFormat() == null) {
            return OpenSSLRSAPrivateCrtKey.wrapPlatformKey((RSAPrivateKey)object);
        }
        byte[] arrby = object.getModulus();
        byte[] arrby2 = object.getPrivateExponent();
        if (arrby == null) {
            throw new InvalidKeyException("modulus == null");
        }
        if (arrby2 == null) {
            throw new InvalidKeyException("privateExponent == null");
        }
        try {
            void var0_3;
            BigInteger bigInteger = object.getPublicExponent();
            Object object2 = object.getPrimeP();
            Object object3 = object.getPrimeQ();
            Object object4 = object.getPrimeExponentP();
            Object object5 = object.getPrimeExponentQ();
            BigInteger bigInteger2 = object.getCrtCoefficient();
            byte[] arrby3 = arrby.toByteArray();
            arrby = null;
            if (bigInteger == null) {
                Object var0_1 = null;
            } else {
                byte[] arrby4 = bigInteger.toByteArray();
            }
            arrby2 = arrby2.toByteArray();
            object2 = object2 == null ? null : object2.toByteArray();
            object3 = object3 == null ? null : object3.toByteArray();
            object4 = object4 == null ? null : object4.toByteArray();
            object5 = object5 == null ? null : object5.toByteArray();
            if (bigInteger2 != null) {
                arrby = bigInteger2.toByteArray();
            }
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(arrby3, (byte[])var0_3, arrby2, object2, object3, object4, object5, arrby));
        }
        catch (Exception exception) {
            throw new InvalidKeyException(exception);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static OpenSSLKey init(RSAPrivateCrtKeySpec object) throws InvalidKeySpecException {
        byte[] arrby = ((RSAPrivateKeySpec)object).getModulus();
        byte[] arrby2 = ((RSAPrivateKeySpec)object).getPrivateExponent();
        if (arrby == null) {
            throw new InvalidKeySpecException("modulus == null");
        }
        if (arrby2 == null) {
            throw new InvalidKeySpecException("privateExponent == null");
        }
        try {
            void var0_3;
            BigInteger bigInteger = ((RSAPrivateCrtKeySpec)object).getPublicExponent();
            Object object2 = ((RSAPrivateCrtKeySpec)object).getPrimeP();
            Object object3 = ((RSAPrivateCrtKeySpec)object).getPrimeQ();
            Object object4 = ((RSAPrivateCrtKeySpec)object).getPrimeExponentP();
            Object object5 = ((RSAPrivateCrtKeySpec)object).getPrimeExponentQ();
            BigInteger bigInteger2 = ((RSAPrivateCrtKeySpec)object).getCrtCoefficient();
            byte[] arrby3 = arrby.toByteArray();
            arrby = null;
            if (bigInteger == null) {
                Object var0_1 = null;
            } else {
                byte[] arrby4 = bigInteger.toByteArray();
            }
            arrby2 = arrby2.toByteArray();
            object2 = object2 == null ? null : object2.toByteArray();
            object3 = object3 == null ? null : object3.toByteArray();
            object4 = object4 == null ? null : object4.toByteArray();
            object5 = object5 == null ? null : object5.toByteArray();
            if (bigInteger2 != null) {
                arrby = bigInteger2.toByteArray();
            }
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(arrby3, (byte[])var0_3, arrby2, object2, object3, object4, object5, arrby));
        }
        catch (Exception exception) {
            throw new InvalidKeySpecException(exception);
        }
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object.defaultReadObject();
        byte[] arrby = this.modulus.toByteArray();
        object = this.publicExponent;
        byte[] arrby2 = null;
        object = object == null ? null : object.toByteArray();
        byte[] arrby3 = this.privateExponent.toByteArray();
        Object object2 = this.primeP;
        object2 = object2 == null ? null : object2.toByteArray();
        Object object3 = this.primeQ;
        object3 = object3 == null ? null : object3.toByteArray();
        Object object4 = this.primeExponentP;
        object4 = object4 == null ? null : object4.toByteArray();
        Object object5 = this.primeExponentQ;
        object5 = object5 == null ? null : object5.toByteArray();
        BigInteger bigInteger = this.crtCoefficient;
        if (bigInteger != null) {
            arrby2 = bigInteger.toByteArray();
        }
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(arrby, object, arrby3, object2, object3, object4, object5, arrby2));
        this.fetchedParams = true;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.ensureReadParams();
        objectOutputStream.defaultWriteObject();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        boolean bl2 = true;
        if (object == this) {
            return true;
        }
        if (object instanceof OpenSSLRSAPrivateKey) {
            object = (OpenSSLRSAPrivateKey)object;
            return this.getOpenSSLKey().equals(((OpenSSLRSAPrivateKey)object).getOpenSSLKey());
        }
        if (object instanceof RSAPrivateCrtKey) {
            this.ensureReadParams();
            object = (RSAPrivateCrtKey)object;
            if (!(this.getModulus().equals(object.getModulus()) && this.publicExponent.equals(object.getPublicExponent()) && this.getPrivateExponent().equals(object.getPrivateExponent()) && this.primeP.equals(object.getPrimeP()) && this.primeQ.equals(object.getPrimeQ()) && this.primeExponentP.equals(object.getPrimeExponentP()) && this.primeExponentQ.equals(object.getPrimeExponentQ()) && this.crtCoefficient.equals(object.getCrtCoefficient()))) {
                bl2 = false;
            }
            return bl2;
        }
        if (object instanceof RSAPrivateKey) {
            this.ensureReadParams();
            object = (RSAPrivateKey)object;
            bl2 = this.getModulus().equals(object.getModulus()) && this.getPrivateExponent().equals(object.getPrivateExponent()) ? bl : false;
            return bl2;
        }
        return false;
    }

    @Override
    public BigInteger getCrtCoefficient() {
        this.ensureReadParams();
        return this.crtCoefficient;
    }

    @Override
    public BigInteger getPrimeExponentP() {
        this.ensureReadParams();
        return this.primeExponentP;
    }

    @Override
    public BigInteger getPrimeExponentQ() {
        this.ensureReadParams();
        return this.primeExponentQ;
    }

    @Override
    public BigInteger getPrimeP() {
        this.ensureReadParams();
        return this.primeP;
    }

    @Override
    public BigInteger getPrimeQ() {
        this.ensureReadParams();
        return this.primeQ;
    }

    @Override
    public BigInteger getPublicExponent() {
        this.ensureReadParams();
        return this.publicExponent;
    }

    @Override
    public final int hashCode() {
        int n = super.hashCode();
        BigInteger bigInteger = this.publicExponent;
        int n2 = n;
        if (bigInteger != null) {
            n2 = n ^ bigInteger.hashCode();
        }
        return n2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void readParams(byte[][] arrby) {
        synchronized (this) {
            BigInteger bigInteger;
            super.readParams(arrby);
            if (arrby[1] != null) {
                this.publicExponent = bigInteger = new BigInteger(arrby[1]);
            }
            if (arrby[3] != null) {
                this.primeP = bigInteger = new BigInteger(arrby[3]);
            }
            if (arrby[4] != null) {
                this.primeQ = bigInteger = new BigInteger(arrby[4]);
            }
            if (arrby[5] != null) {
                this.primeExponentP = bigInteger = new BigInteger(arrby[5]);
            }
            if (arrby[6] != null) {
                this.primeExponentQ = bigInteger = new BigInteger(arrby[6]);
            }
            if (arrby[7] != null) {
                this.crtCoefficient = bigInteger = new BigInteger(arrby[7]);
            }
            return;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("OpenSSLRSAPrivateCrtKey{");
        this.ensureReadParams();
        stringBuilder.append("modulus=");
        stringBuilder.append(this.getModulus().toString(16));
        if (this.publicExponent != null) {
            stringBuilder.append(',');
            stringBuilder.append("publicExponent=");
            stringBuilder.append(this.publicExponent.toString(16));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

