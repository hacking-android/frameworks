/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OAEPParameters;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

public class PSSParameters
extends AlgorithmParametersSpi {
    private PSSParameterSpec spec = PSSParameterSpec.DEFAULT;

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
            var13_7 = this.spec.getSaltLength();
            if (var13_7 == 20) ** GOTO lbl55
            var14_8 = 0L;
            var14_8 = var16_9 = NativeCrypto.asn1_write_tag(var3_2, 2);
            NativeCrypto.asn1_write_uint64(var16_9, this.spec.getSaltLength());
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            {
                catch (Throwable var18_10) {
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    NativeCrypto.asn1_write_flush(var3_2);
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    NativeCrypto.asn1_write_free(var14_8);
                    var5_3 = var11_6;
                    var7_4 = var3_2;
                    var1_1 = var11_6;
                    var9_5 = var3_2;
                    throw var18_10;
                }
            }
            NativeCrypto.asn1_write_flush(var3_2);
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            NativeCrypto.asn1_write_free(var16_9);
lbl55: // 2 sources:
            var5_3 = var11_6;
            var7_4 = var3_2;
            var1_1 = var11_6;
            var9_5 = var3_2;
            var18_11 = NativeCrypto.asn1_write_finish(var11_6);
            NativeCrypto.asn1_write_free(var3_2);
            NativeCrypto.asn1_write_free(var11_6);
            return var18_11;
            {
                catch (Throwable var18_12) {
                    break block7;
                }
                catch (IOException var18_13) {}
                var5_3 = var1_1;
                var7_4 = var9_5;
                {
                    NativeCrypto.asn1_write_cleanup(var1_1);
                    var5_3 = var1_1;
                    var7_4 = var9_5;
                    throw var18_13;
                }
            }
        }
        NativeCrypto.asn1_write_free(var7_4);
        NativeCrypto.asn1_write_free(var5_3);
        throw var18_12;
    }

    @Override
    protected byte[] engineGetEncoded(String string) throws IOException {
        if (string != null && !string.equals("ASN.1") && !string.equals("X.509")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported format: ");
            stringBuilder.append(string);
            throw new IOException(stringBuilder.toString());
        }
        return this.engineGetEncoded();
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> class_) throws InvalidParameterSpecException {
        if (class_ != null && class_ == PSSParameterSpec.class) {
            return (T)this.spec;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported class: ");
        stringBuilder.append(class_);
        throw new InvalidParameterSpecException(stringBuilder.toString());
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof PSSParameterSpec) {
            this.spec = (PSSParameterSpec)algorithmParameterSpec;
            return;
        }
        throw new InvalidParameterSpecException("Only PSSParameterSpec is supported");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(byte[] var1_1) throws IOException {
        block13 : {
            block12 : {
                var2_7 = 0L;
                var6_9 = var4_8 = 0L;
                var2_7 = var8_10 = NativeCrypto.asn1_read_init(var1_1 /* !! */ );
                var6_9 = var4_8;
                var4_8 = NativeCrypto.asn1_read_sequence(var8_10);
                var10_11 = 20;
                var2_7 = var8_10;
                var6_9 = var4_8;
                var11_12 = OAEPParameters.readHash(var4_8);
                var2_7 = var8_10;
                var6_9 = var4_8;
                var1_1 /* !! */  = OAEPParameters.readMgfHash(var4_8);
                var2_7 = var8_10;
                var6_9 = var4_8;
                var12_13 = NativeCrypto.asn1_read_next_tag_is(var4_8, 2);
                if (!var12_13) ** GOTO lbl35
                var13_14 = 0L;
                var13_14 = var15_15 = NativeCrypto.asn1_read_tagged(var4_8);
                var2_7 = NativeCrypto.asn1_read_uint64(var15_15);
                {
                    catch (Throwable var1_2) {
                        var2_7 = var8_10;
                        var6_9 = var4_8;
                        NativeCrypto.asn1_read_free(var13_14);
                        var2_7 = var8_10;
                        var6_9 = var4_8;
                        throw var1_2;
                    }
                }
                var10_11 = (int)var2_7;
                var2_7 = var8_10;
                var6_9 = var4_8;
                NativeCrypto.asn1_read_free(var15_15);
lbl35: // 2 sources:
                var2_7 = var8_10;
                var6_9 = var4_8;
                var12_13 = NativeCrypto.asn1_read_next_tag_is(var4_8, 3);
                if (!var12_13) ** GOTO lbl65
                var13_14 = 0L;
                var13_14 = var15_15 = NativeCrypto.asn1_read_tagged(var4_8);
                var2_7 = NativeCrypto.asn1_read_uint64(var15_15);
                {
                    catch (Throwable var1_3) {
                        var2_7 = var8_10;
                        var6_9 = var4_8;
                        NativeCrypto.asn1_read_free(var13_14);
                        var2_7 = var8_10;
                        var6_9 = var4_8;
                        throw var1_3;
                    }
                }
                var13_14 = (int)var2_7;
                var2_7 = var8_10;
                var6_9 = var4_8;
                NativeCrypto.asn1_read_free(var15_15);
                if (var13_14 != 1L) {
                    var2_7 = var8_10;
                    var6_9 = var4_8;
                    var2_7 = var8_10;
                    var6_9 = var4_8;
                    var1_1 /* !! */  = new IOException("Error reading ASN.1 encoding");
                    var2_7 = var8_10;
                    var6_9 = var4_8;
                    throw var1_1 /* !! */ ;
                }
lbl65: // 3 sources:
                var2_7 = var8_10;
                var6_9 = var4_8;
                if (!NativeCrypto.asn1_read_is_empty(var4_8)) break block12;
                var2_7 = var8_10;
                var6_9 = var4_8;
                if (!NativeCrypto.asn1_read_is_empty(var8_10)) break block12;
                var2_7 = var8_10;
                var6_9 = var4_8;
                var2_7 = var8_10;
                var6_9 = var4_8;
                var2_7 = var8_10;
                var6_9 = var4_8;
                var18_17 = new MGF1ParameterSpec((String)var1_1 /* !! */ );
                var2_7 = var8_10;
                var6_9 = var4_8;
                var17_16 = new PSSParameterSpec(var11_12, "MGF1", var18_17, var10_11, 1);
                this.spec = var17_16;
                NativeCrypto.asn1_read_free(var4_8);
                NativeCrypto.asn1_read_free(var8_10);
                return;
            }
            try {
                var1_1 /* !! */  = new IOException("Error reading ASN.1 encoding");
                throw var1_1 /* !! */ ;
            }
            catch (Throwable var1_4) {
                var2_7 = var8_10;
                var6_9 = var4_8;
            }
            break block13;
            catch (Throwable var1_5) {
                // empty catch block
            }
        }
        NativeCrypto.asn1_read_free(var6_9);
        NativeCrypto.asn1_read_free(var2_7);
        throw var1_6;
    }

    @Override
    protected void engineInit(byte[] object, String string) throws IOException {
        if (string != null && !string.equals("ASN.1") && !string.equals("X.509")) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported format: ");
            ((StringBuilder)object).append(string);
            throw new IOException(((StringBuilder)object).toString());
        }
        this.engineInit((byte[])object);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt PSS AlgorithmParameters";
    }
}

