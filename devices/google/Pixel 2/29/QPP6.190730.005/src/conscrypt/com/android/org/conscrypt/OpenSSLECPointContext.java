/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import java.math.BigInteger;
import java.security.spec.ECPoint;

final class OpenSSLECPointContext {
    private final OpenSSLECGroupContext group;
    private final NativeRef.EC_POINT pointCtx;

    OpenSSLECPointContext(OpenSSLECGroupContext openSSLECGroupContext, NativeRef.EC_POINT eC_POINT) {
        this.group = openSSLECGroupContext;
        this.pointCtx = eC_POINT;
    }

    static OpenSSLECPointContext getInstance(OpenSSLECGroupContext openSSLECGroupContext, ECPoint eCPoint) {
        OpenSSLECPointContext openSSLECPointContext = new OpenSSLECPointContext(openSSLECGroupContext, new NativeRef.EC_POINT(NativeCrypto.EC_POINT_new(openSSLECGroupContext.getNativeRef())));
        NativeCrypto.EC_POINT_set_affine_coordinates(openSSLECGroupContext.getNativeRef(), openSSLECPointContext.getNativeRef(), eCPoint.getAffineX().toByteArray(), eCPoint.getAffineY().toByteArray());
        return openSSLECPointContext;
    }

    public boolean equals(Object object) {
        throw new IllegalArgumentException("OpenSSLECPointContext.equals is not defined.");
    }

    ECPoint getECPoint() {
        byte[][] arrby = NativeCrypto.EC_POINT_get_affine_coordinates(this.group.getNativeRef(), this.pointCtx);
        return new ECPoint(new BigInteger(arrby[0]), new BigInteger(arrby[1]));
    }

    NativeRef.EC_POINT getNativeRef() {
        return this.pointCtx;
    }

    public int hashCode() {
        return super.hashCode();
    }
}

