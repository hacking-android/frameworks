/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;

public class IvParameters
extends AlgorithmParametersSpi {
    private byte[] iv;

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
        long l;
        Throwable throwable2222;
        long l2;
        long l3 = l2 = 0L;
        l3 = l = NativeCrypto.asn1_write_init();
        l2 = l;
        NativeCrypto.asn1_write_octetstring(l, this.iv);
        l3 = l;
        l2 = l;
        byte[] arrby = NativeCrypto.asn1_write_finish(l);
        NativeCrypto.asn1_write_free(l);
        return arrby;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            l3 = l2;
            {
                NativeCrypto.asn1_write_cleanup(l2);
                l3 = l2;
                throw iOException;
            }
        }
        NativeCrypto.asn1_write_free(l3);
        throw throwable2222;
    }

    @Override
    protected byte[] engineGetEncoded(String string) throws IOException {
        if (string != null && !string.equals("ASN.1")) {
            if (string.equals("RAW")) {
                return (byte[])this.iv.clone();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported format: ");
            stringBuilder.append(string);
            throw new IOException(stringBuilder.toString());
        }
        return this.engineGetEncoded();
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> class_) throws InvalidParameterSpecException {
        if (class_ == IvParameterSpec.class) {
            return (T)new IvParameterSpec(this.iv);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Incompatible AlgorithmParametersSpec class: ");
        stringBuilder.append(class_);
        throw new InvalidParameterSpecException(stringBuilder.toString());
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            this.iv = (byte[])((IvParameterSpec)algorithmParameterSpec).getIV().clone();
            return;
        }
        throw new InvalidParameterSpecException("Only IvParameterSpec is supported");
    }

    @Override
    protected void engineInit(byte[] object) throws IOException {
        long l;
        long l2;
        block8 : {
            l = 0L;
            try {
                l = l2 = NativeCrypto.asn1_read_init(object);
            }
            catch (Throwable throwable) {
                NativeCrypto.asn1_read_free(l);
                throw throwable;
            }
            object = NativeCrypto.asn1_read_octetstring(l2);
            l = l2;
            if (!NativeCrypto.asn1_read_is_empty(l2)) break block8;
            l = l2;
            this.iv = object;
            NativeCrypto.asn1_read_free(l2);
            return;
        }
        l = l2;
        l = l2;
        object = new IOException("Error reading ASN.1 encoding");
        l = l2;
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void engineInit(byte[] object, String string) throws IOException {
        if (string != null && !string.equals("ASN.1")) {
            if (string.equals("RAW")) {
                this.iv = (byte[])object.clone();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported format: ");
            ((StringBuilder)object).append(string);
            throw new IOException(((StringBuilder)object).toString());
        }
        this.engineInit((byte[])object);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt IV AlgorithmParameters";
    }

    public static class AES
    extends IvParameters {
    }

    public static class ChaCha20
    extends IvParameters {
    }

    public static class DESEDE
    extends IvParameters {
    }

}

