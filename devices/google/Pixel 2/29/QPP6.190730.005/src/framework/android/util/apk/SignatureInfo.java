/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import java.nio.ByteBuffer;

class SignatureInfo {
    public final long apkSigningBlockOffset;
    public final long centralDirOffset;
    public final ByteBuffer eocd;
    public final long eocdOffset;
    public final ByteBuffer signatureBlock;

    SignatureInfo(ByteBuffer byteBuffer, long l, long l2, long l3, ByteBuffer byteBuffer2) {
        this.signatureBlock = byteBuffer;
        this.apkSigningBlockOffset = l;
        this.centralDirOffset = l2;
        this.eocdOffset = l3;
        this.eocd = byteBuffer2;
    }
}

