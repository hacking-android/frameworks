/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLECPointContext;
import com.android.org.conscrypt.Platform;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

final class OpenSSLECGroupContext {
    private final NativeRef.EC_GROUP groupCtx;

    OpenSSLECGroupContext(NativeRef.EC_GROUP eC_GROUP) {
        this.groupCtx = eC_GROUP;
    }

    static OpenSSLECGroupContext getCurveByName(String string) {
        long l;
        String string2 = string;
        if ("secp256r1".equals(string)) {
            string2 = "prime256v1";
        }
        if ((l = NativeCrypto.EC_GROUP_new_by_curve_name(string2)) == 0L) {
            return null;
        }
        return new OpenSSLECGroupContext(new NativeRef.EC_GROUP(l));
    }

    static OpenSSLECGroupContext getInstance(ECParameterSpec object) throws InvalidAlgorithmParameterException {
        Object object2 = Platform.getCurveName((ECParameterSpec)object);
        if (object2 != null) {
            return OpenSSLECGroupContext.getCurveByName((String)object2);
        }
        Object object3 = ((ECParameterSpec)object).getCurve();
        Object object4 = ((EllipticCurve)object3).getField();
        if (object4 instanceof ECFieldFp) {
            block15 : {
                object4 = ((ECFieldFp)object4).getP();
                Object object5 = ((ECParameterSpec)object).getGenerator();
                BigInteger bigInteger = ((EllipticCurve)object3).getB();
                BigInteger bigInteger2 = ((ECPoint)object5).getAffineX();
                object5 = ((ECPoint)object5).getAffineY();
                int n = ((BigInteger)object4).bitLength();
                if (n != 224) {
                    if (n != 256) {
                        if (n != 384) {
                            if (n == 521 && ((BigInteger)object4).toString(16).equals("1ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff") && bigInteger.toString(16).equals("51953eb9618e1c9a1f929a21a0b68540eea2da725b99b315f3b8b489918ef109e156193951ec7e937b1652c0bd3bb1bf073573df883d2c34f1ef451fd46b503f00") && bigInteger2.toString(16).equals("c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66") && ((BigInteger)object5).toString(16).equals("11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650")) {
                                object2 = "secp521r1";
                            }
                        } else if (((BigInteger)object4).toString(16).equals("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffff0000000000000000ffffffff") && bigInteger.toString(16).equals("b3312fa7e23ee7e4988e056be3f82d19181d9c6efe8141120314088f5013875ac656398d8a2ed19d2a85c8edd3ec2aef") && bigInteger2.toString(16).equals("aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7") && ((BigInteger)object5).toString(16).equals("3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f")) {
                            object2 = "secp384r1";
                        }
                    } else if (((BigInteger)object4).toString(16).equals("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff") && bigInteger.toString(16).equals("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b") && bigInteger2.toString(16).equals("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296") && ((BigInteger)object5).toString(16).equals("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5")) {
                        object2 = "prime256v1";
                    }
                } else if (((BigInteger)object4).toString(16).equals("ffffffffffffffffffffffffffffffff000000000000000000000001") && bigInteger.toString(16).equals("b4050a850c04b3abf54132565044b0b7d7bfd8ba270b39432355ffb4") && bigInteger2.toString(16).equals("b70e0cbd6bb4bf7f321390b94a03c1d356c21122343280d6115c1d21") && ((BigInteger)object5).toString(16).equals("bd376388b5f723fb4c22dfe6cd4375a05a07476444d5819985007e34")) {
                    object2 = "secp224r1";
                }
                if (object2 != null) {
                    return OpenSSLECGroupContext.getCurveByName((String)object2);
                }
                object3 = ((EllipticCurve)object3).getA();
                object2 = ((ECParameterSpec)object).getOrder();
                n = ((ECParameterSpec)object).getCofactor();
                try {
                    long l = NativeCrypto.EC_GROUP_new_arbitrary(((BigInteger)object4).toByteArray(), ((BigInteger)object3).toByteArray(), bigInteger.toByteArray(), bigInteger2.toByteArray(), ((BigInteger)object5).toByteArray(), ((BigInteger)object2).toByteArray(), n);
                    if (l == 0L) break block15;
                    return new OpenSSLECGroupContext(new NativeRef.EC_GROUP(l));
                }
                catch (Throwable throwable) {
                    throw new InvalidAlgorithmParameterException("EC_GROUP_new_arbitrary failed", throwable);
                }
            }
            throw new InvalidAlgorithmParameterException("EC_GROUP_new_arbitrary returned NULL");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unhandled field class ");
        ((StringBuilder)object).append(object4.getClass().getName());
        throw new InvalidParameterException(((StringBuilder)object).toString());
    }

    public boolean equals(Object object) {
        throw new IllegalArgumentException("OpenSSLECGroupContext.equals is not defined");
    }

    ECParameterSpec getECParameterSpec() {
        String string = NativeCrypto.EC_GROUP_get_curve_name(this.groupCtx);
        Object object = NativeCrypto.EC_GROUP_get_curve(this.groupCtx);
        BigInteger bigInteger = new BigInteger(object[0]);
        Object object2 = new BigInteger(object[1]);
        object = new BigInteger(object[2]);
        object2 = new ECParameterSpec(new EllipticCurve(new ECFieldFp(bigInteger), (BigInteger)object2, (BigInteger)object), new OpenSSLECPointContext(this, new NativeRef.EC_POINT(NativeCrypto.EC_GROUP_get_generator(this.groupCtx))).getECPoint(), new BigInteger(NativeCrypto.EC_GROUP_get_order(this.groupCtx)), new BigInteger(NativeCrypto.EC_GROUP_get_cofactor(this.groupCtx)).intValue());
        Platform.setCurveName((ECParameterSpec)object2, string);
        return object2;
    }

    NativeRef.EC_GROUP getNativeRef() {
        return this.groupCtx;
    }

    public int hashCode() {
        return super.hashCode();
    }
}

