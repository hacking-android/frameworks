/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.Platform;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public final class GCMParameters
extends AlgorithmParametersSpi {
    private static final int DEFAULT_TLEN = 96;
    private byte[] iv;
    private int tLen;

    public GCMParameters() {
    }

    GCMParameters(int n, byte[] arrby) {
        this.tLen = n;
        this.iv = arrby;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] engineGetEncoded() throws IOException {
        Throwable throwable2222;
        long l;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = l2;
        long l5 = l3;
        long l6 = l3;
        l4 = l = NativeCrypto.asn1_write_init();
        l5 = l3;
        l2 = l;
        l6 = l3;
        l3 = NativeCrypto.asn1_write_sequence(l);
        l4 = l;
        l5 = l3;
        l2 = l;
        l6 = l3;
        NativeCrypto.asn1_write_octetstring(l3, this.iv);
        l4 = l;
        l5 = l3;
        l2 = l;
        l6 = l3;
        if (this.tLen != 96) {
            l4 = l;
            l5 = l3;
            l2 = l;
            l6 = l3;
            NativeCrypto.asn1_write_uint64(l3, this.tLen / 8);
        }
        l4 = l;
        l5 = l3;
        l2 = l;
        l6 = l3;
        byte[] arrby = NativeCrypto.asn1_write_finish(l);
        NativeCrypto.asn1_write_free(l3);
        NativeCrypto.asn1_write_free(l);
        return arrby;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            l4 = l2;
            l5 = l6;
            {
                NativeCrypto.asn1_write_cleanup(l2);
                l4 = l2;
                l5 = l6;
                throw iOException;
            }
        }
        NativeCrypto.asn1_write_free(l5);
        NativeCrypto.asn1_write_free(l4);
        throw throwable2222;
    }

    @Override
    protected byte[] engineGetEncoded(String string) throws IOException {
        if (string != null && !string.equals("ASN.1")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported format: ");
            stringBuilder.append(string);
            throw new IOException(stringBuilder.toString());
        }
        return this.engineGetEncoded();
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> class_) throws InvalidParameterSpecException {
        if (class_ != null && class_.getName().equals("javax.crypto.spec.GCMParameterSpec")) {
            return (T)((AlgorithmParameterSpec)class_.cast(Platform.toGCMParameterSpec(this.tLen, this.iv)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported class: ");
        stringBuilder.append(class_);
        throw new InvalidParameterSpecException(stringBuilder.toString());
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec object) throws InvalidParameterSpecException {
        if ((object = Platform.fromGCMParameterSpec((AlgorithmParameterSpec)object)) != null) {
            this.tLen = ((GCMParameters)object).tLen;
            this.iv = ((GCMParameters)object).iv;
            return;
        }
        throw new InvalidParameterSpecException("Only GCMParameterSpec is supported");
    }

    @Override
    protected void engineInit(byte[] object) throws IOException {
        long l;
        long l2;
        long l3;
        long l4;
        block14 : {
            int n;
            block13 : {
                l3 = 0L;
                l = l4 = 0L;
                try {
                    l3 = l2 = NativeCrypto.asn1_read_init(object);
                    l = l4;
                }
                catch (Throwable throwable) {
                    NativeCrypto.asn1_read_free(l);
                    NativeCrypto.asn1_read_free(l3);
                    throw throwable;
                }
                l4 = NativeCrypto.asn1_read_sequence(l2);
                l3 = l2;
                l = l4;
                object = NativeCrypto.asn1_read_octetstring(l4);
                n = 96;
                l3 = l2;
                l = l4;
                if (NativeCrypto.asn1_read_is_empty(l4)) break block13;
                l3 = l2;
                l = l4;
                n = (int)NativeCrypto.asn1_read_uint64(l4) * 8;
            }
            l3 = l2;
            l = l4;
            if (!NativeCrypto.asn1_read_is_empty(l4)) break block14;
            l3 = l2;
            l = l4;
            if (!NativeCrypto.asn1_read_is_empty(l2)) break block14;
            l3 = l2;
            l = l4;
            this.iv = object;
            l3 = l2;
            l = l4;
            this.tLen = n;
            NativeCrypto.asn1_read_free(l4);
            NativeCrypto.asn1_read_free(l2);
            return;
        }
        l3 = l2;
        l = l4;
        l3 = l2;
        l = l4;
        object = new IOException("Error reading ASN.1 encoding");
        l3 = l2;
        l = l4;
        throw object;
    }

    @Override
    protected void engineInit(byte[] object, String string) throws IOException {
        if (string != null && !string.equals("ASN.1")) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported format: ");
            ((StringBuilder)object).append(string);
            throw new IOException(((StringBuilder)object).toString());
        }
        this.engineInit((byte[])object);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt GCM AlgorithmParameters";
    }

    byte[] getIV() {
        return this.iv;
    }

    int getTLen() {
        return this.tLen;
    }
}

