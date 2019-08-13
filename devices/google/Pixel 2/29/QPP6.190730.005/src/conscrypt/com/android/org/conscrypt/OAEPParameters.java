/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class OAEPParameters
extends AlgorithmParametersSpi {
    private static final String MGF1_OID = "1.2.840.113549.1.1.8";
    private static final Map<String, String> NAME_TO_OID;
    private static final Map<String, String> OID_TO_NAME;
    private static final String PSPECIFIED_OID = "1.2.840.113549.1.1.9";
    private OAEPParameterSpec spec = OAEPParameterSpec.DEFAULT;

    static {
        OID_TO_NAME = new HashMap<String, String>();
        NAME_TO_OID = new HashMap<String, String>();
        OID_TO_NAME.put("1.3.14.3.2.26", "SHA-1");
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.4", "SHA-224");
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.1", "SHA-256");
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.2", "SHA-384");
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.3", "SHA-512");
        for (Map.Entry<String, String> entry : OID_TO_NAME.entrySet()) {
            NAME_TO_OID.put(entry.getValue(), entry.getKey());
        }
    }

    private static String getHashName(long l) throws IOException {
        long l2;
        Object object;
        block12 : {
            block11 : {
                l2 = 0L;
                try {
                    l2 = l = NativeCrypto.asn1_read_sequence(l);
                }
                catch (Throwable throwable) {
                    NativeCrypto.asn1_read_free(l2);
                    throw throwable;
                }
                object = NativeCrypto.asn1_read_oid(l);
                l2 = l;
                if (NativeCrypto.asn1_read_is_empty(l)) break block11;
                l2 = l;
                NativeCrypto.asn1_read_null(l);
            }
            l2 = l;
            if (!NativeCrypto.asn1_read_is_empty(l)) break block12;
            l2 = l;
            if (!OID_TO_NAME.containsKey(object)) break block12;
            l2 = l;
            object = OID_TO_NAME.get(object);
            NativeCrypto.asn1_read_free(l);
            return object;
        }
        l2 = l;
        l2 = l;
        object = new IOException("Error reading ASN.1 encoding");
        l2 = l;
        throw object;
    }

    static String readHash(long l) throws IOException {
        if (NativeCrypto.asn1_read_next_tag_is(l, 0)) {
            long l2 = 0L;
            try {
                l2 = l = NativeCrypto.asn1_read_tagged(l);
            }
            catch (Throwable throwable) {
                NativeCrypto.asn1_read_free(l2);
                throw throwable;
            }
            String string = OAEPParameters.getHashName(l);
            NativeCrypto.asn1_read_free(l);
            return string;
        }
        return "SHA-1";
    }

    static String readMgfHash(long l) throws IOException {
        if (NativeCrypto.asn1_read_next_tag_is(l, 1)) {
            long l2;
            long l3;
            long l4;
            Object object;
            block13 : {
                block14 : {
                    l2 = 0L;
                    l3 = l4 = 0L;
                    try {
                        l2 = l = NativeCrypto.asn1_read_tagged(l);
                        l3 = l4;
                    }
                    catch (Throwable throwable) {
                        NativeCrypto.asn1_read_free(l3);
                        NativeCrypto.asn1_read_free(l2);
                        throw throwable;
                    }
                    l4 = NativeCrypto.asn1_read_sequence(l);
                    l2 = l;
                    l3 = l4;
                    boolean bl = NativeCrypto.asn1_read_oid(l4).equals(MGF1_OID);
                    if (!bl) break block13;
                    l2 = l;
                    l3 = l4;
                    object = OAEPParameters.getHashName(l4);
                    l2 = l;
                    l3 = l4;
                    bl = NativeCrypto.asn1_read_is_empty(l4);
                    if (!bl) break block14;
                    NativeCrypto.asn1_read_free(l4);
                    NativeCrypto.asn1_read_free(l);
                    return object;
                }
                l2 = l;
                l3 = l4;
                l2 = l;
                l3 = l4;
                object = new IOException("Error reading ASN.1 encoding");
                l2 = l;
                l3 = l4;
                throw object;
            }
            l2 = l;
            l3 = l4;
            l2 = l;
            l3 = l4;
            object = new IOException("Error reading ASN.1 encoding");
            l2 = l;
            l3 = l4;
            throw object;
        }
        return "SHA-1";
    }

    private static long writeAlgorithmIdentifier(long l, String string) throws IOException {
        long l2 = 0L;
        try {
            l2 = l = NativeCrypto.asn1_write_sequence(l);
        }
        catch (IOException iOException) {
            NativeCrypto.asn1_write_free(l2);
            throw iOException;
        }
        NativeCrypto.asn1_write_oid(l, string);
        return l;
    }

    static void writeHashAndMgfHash(long l, String string, MGF1ParameterSpec mGF1ParameterSpec) throws IOException {
        long l2;
        long l3;
        long l4;
        long l5;
        if (!string.equals("SHA-1")) {
            l4 = 0L;
            l3 = l2 = 0L;
            try {
                l4 = l5 = NativeCrypto.asn1_write_tag(l, 0);
                l3 = l2;
            }
            catch (Throwable throwable) {
                NativeCrypto.asn1_write_flush(l);
                NativeCrypto.asn1_write_free(l3);
                NativeCrypto.asn1_write_free(l4);
                throw throwable;
            }
            l2 = OAEPParameters.writeAlgorithmIdentifier(l5, NAME_TO_OID.get(string));
            l4 = l5;
            l3 = l2;
            NativeCrypto.asn1_write_null(l2);
            NativeCrypto.asn1_write_flush(l);
            NativeCrypto.asn1_write_free(l2);
            NativeCrypto.asn1_write_free(l5);
        }
        if (!mGF1ParameterSpec.getDigestAlgorithm().equals("SHA-1")) {
            l4 = 0L;
            long l6 = 0L;
            long l7 = 0L;
            l2 = l6;
            l3 = l7;
            try {
                l4 = l5 = NativeCrypto.asn1_write_tag(l, 1);
                l2 = l6;
                l3 = l7;
            }
            catch (Throwable throwable) {
                NativeCrypto.asn1_write_flush(l);
                NativeCrypto.asn1_write_free(l3);
                NativeCrypto.asn1_write_free(l2);
                NativeCrypto.asn1_write_free(l4);
                throw throwable;
            }
            l6 = OAEPParameters.writeAlgorithmIdentifier(l5, MGF1_OID);
            l4 = l5;
            l2 = l6;
            l3 = l7;
            l7 = OAEPParameters.writeAlgorithmIdentifier(l6, NAME_TO_OID.get(mGF1ParameterSpec.getDigestAlgorithm()));
            l4 = l5;
            l2 = l6;
            l3 = l7;
            NativeCrypto.asn1_write_null(l7);
            NativeCrypto.asn1_write_flush(l);
            NativeCrypto.asn1_write_free(l7);
            NativeCrypto.asn1_write_free(l6);
            NativeCrypto.asn1_write_free(l5);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] engineGetEncoded() throws IOException {
        block7 : {
            var1_1 = 0L;
            var3_2 = 0L;
            var5_3 = var1_1;
            var7_4 = var3_2;
            var9_5 = var3_2;
            var5_3 = var11_6 = NativeCrypto.asn1_write_init();
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            var3_2 = NativeCrypto.asn1_write_sequence(var11_6);
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            OAEPParameters.writeHashAndMgfHash(var3_2, this.spec.getDigestAlgorithm(), (MGF1ParameterSpec)this.spec.getMGFParameters());
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            var13_7 = (byte[])this.spec.getPSource();
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            var14_11 = var13_7.getValue().length;
            if (var14_11 == 0) ** GOTO lbl75
            var15_12 = 0L;
            var17_13 = var7_4 = 0L;
            var15_12 = var19_14 = NativeCrypto.asn1_write_tag(var3_2, 2);
            var17_13 = var7_4;
            var21_15 = OAEPParameters.writeAlgorithmIdentifier(var19_14, "1.2.840.113549.1.1.9");
            var15_12 = var19_14;
            var17_13 = var21_15;
            NativeCrypto.asn1_write_octetstring(var21_15, var13_7.getValue());
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            {
                catch (Throwable var13_8) {
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    NativeCrypto.asn1_write_flush(var3_2);
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    NativeCrypto.asn1_write_free(var17_13);
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    NativeCrypto.asn1_write_free(var15_12);
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    throw var13_8;
                }
            }
            NativeCrypto.asn1_write_flush(var3_2);
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            NativeCrypto.asn1_write_free(var21_15);
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            NativeCrypto.asn1_write_free(var19_14);
lbl75: // 2 sources:
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            var13_7 = NativeCrypto.asn1_write_finish(var11_6);
            NativeCrypto.asn1_write_free(var3_2);
            NativeCrypto.asn1_write_free(var11_6);
            return var13_7;
            {
                catch (Throwable var13_9) {
                    break block7;
                }
                catch (IOException var13_10) {}
                var5_3 = var1_1;
                var7_4 = var9_5;
                {
                    NativeCrypto.asn1_write_cleanup(var1_1);
                    var5_3 = var1_1;
                    var7_4 = var9_5;
                    throw var13_10;
                }
            }
        }
        NativeCrypto.asn1_write_free(var7_4);
        NativeCrypto.asn1_write_free(var5_3);
        throw var13_9;
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
        if (class_ != null && class_ == OAEPParameterSpec.class) {
            return (T)this.spec;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported class: ");
        stringBuilder.append(class_);
        throw new InvalidParameterSpecException(stringBuilder.toString());
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof OAEPParameterSpec) {
            this.spec = (OAEPParameterSpec)algorithmParameterSpec;
            return;
        }
        throw new InvalidParameterSpecException("Only OAEPParameterSpec is supported");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(byte[] object) throws IOException {
        block12 : {
            block10 : {
                l2 = 0L;
                l = l3 = 0L;
                l2 = l4 = NativeCrypto.asn1_read_init((byte[])object);
                l = l3;
                l3 = NativeCrypto.asn1_read_sequence(l4);
                l2 = l4;
                l = l3;
                object = PSource.PSpecified.DEFAULT;
                l2 = l4;
                l = l3;
                string2 = OAEPParameters.readHash(l3);
                l2 = l4;
                l = l3;
                string = OAEPParameters.readMgfHash(l3);
                l2 = l4;
                l = l3;
                bl = NativeCrypto.asn1_read_next_tag_is(l3, 2);
                if (!bl) ** GOTO lbl75
                l6 = 0L;
                l8 = l2 = 0L;
                l6 = l5 = NativeCrypto.asn1_read_tagged(l3);
                l8 = l2;
                l7 = NativeCrypto.asn1_read_sequence(l5);
                l6 = l5;
                l8 = l7;
                if (!NativeCrypto.asn1_read_oid(l7).equals("1.2.840.113549.1.1.9")) ** GOTO lbl56
                l6 = l5;
                l8 = l7;
                l6 = l5;
                l8 = l7;
                object = new PSource.PSpecified(NativeCrypto.asn1_read_octetstring(l7));
                l6 = l5;
                l8 = l7;
                bl = NativeCrypto.asn1_read_is_empty(l7);
                if (!bl) break block10;
                l2 = l4;
                l = l3;
                NativeCrypto.asn1_read_free(l7);
                l2 = l4;
                l = l3;
                NativeCrypto.asn1_read_free(l5);
                ** GOTO lbl75
            }
            l6 = l5;
            l8 = l7;
            try {
                l6 = l5;
                l8 = l7;
                object = new IOException("Error reading ASN.1 encoding");
                l6 = l5;
                l8 = l7;
                throw object;
lbl56: // 1 sources:
                l6 = l5;
                l8 = l7;
                l6 = l5;
                l8 = l7;
                object = new IOException("Error reading ASN.1 encoding");
                l6 = l5;
                l8 = l7;
                throw object;
            }
            catch (Throwable throwable) {
                block11 : {
                    l2 = l4;
                    l = l3;
                    NativeCrypto.asn1_read_free(l8);
                    l2 = l4;
                    l = l3;
                    NativeCrypto.asn1_read_free(l6);
                    l2 = l4;
                    l = l3;
                    throw throwable;
lbl75: // 2 sources:
                    l2 = l4;
                    l = l3;
                    if (!NativeCrypto.asn1_read_is_empty(l3)) break block11;
                    l2 = l4;
                    l = l3;
                    if (!NativeCrypto.asn1_read_is_empty(l4)) break block11;
                    l2 = l4;
                    l = l3;
                    l2 = l4;
                    l = l3;
                    l2 = l4;
                    l = l3;
                    mGF1ParameterSpec = new MGF1ParameterSpec(string);
                    l2 = l4;
                    l = l3;
                    oAEPParameterSpec = new OAEPParameterSpec(string2, "MGF1", mGF1ParameterSpec, (PSource)object);
                    try {
                        this.spec = oAEPParameterSpec;
                    }
                    catch (Throwable throwable) {
                        l2 = l4;
                        l = l3;
                        break block12;
                    }
                    NativeCrypto.asn1_read_free(l3);
                    NativeCrypto.asn1_read_free(l4);
                    return;
                }
                object = new IOException("Error reading ASN.1 encoding");
                throw object;
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        NativeCrypto.asn1_read_free(l);
        NativeCrypto.asn1_read_free(l2);
        throw var1_5;
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
        return "Conscrypt OAEP AlgorithmParameters";
    }
}

