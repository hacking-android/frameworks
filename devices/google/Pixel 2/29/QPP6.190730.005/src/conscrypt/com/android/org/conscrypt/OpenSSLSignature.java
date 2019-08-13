/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.EvpMdRef;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

public class OpenSSLSignature
extends SignatureSpi {
    private NativeRef.EVP_MD_CTX ctx;
    private final EngineType engineType;
    private final long evpMdRef;
    private long evpPkeyCtx;
    private OpenSSLKey key;
    private boolean signing;
    private final byte[] singleByte = new byte[1];

    private OpenSSLSignature(long l, EngineType engineType) {
        this.engineType = engineType;
        this.evpMdRef = l;
    }

    private void checkEngineType(OpenSSLKey object) throws InvalidKeyException {
        block8 : {
            block7 : {
                int n;
                block5 : {
                    block6 : {
                        n = NativeCrypto.EVP_PKEY_type(((OpenSSLKey)object).getNativeRef());
                        int n2 = 1.$SwitchMap$com$android$org$conscrypt$OpenSSLSignature$EngineType[this.engineType.ordinal()];
                        if (n2 == 1) break block5;
                        if (n2 != 2) break block6;
                        if (n != 408) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Signature initialized as ");
                            ((StringBuilder)object).append((Object)this.engineType);
                            ((StringBuilder)object).append(" (not EC)");
                            throw new InvalidKeyException(((StringBuilder)object).toString());
                        }
                        break block7;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Key must be of type ");
                    ((StringBuilder)object).append((Object)this.engineType);
                    throw new InvalidKeyException(((StringBuilder)object).toString());
                }
                if (n != 6) break block8;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Signature initialized as ");
        ((StringBuilder)object).append((Object)this.engineType);
        ((StringBuilder)object).append(" (not RSA)");
        throw new InvalidKeyException(((StringBuilder)object).toString());
    }

    private void initInternal(OpenSSLKey openSSLKey, boolean bl) throws InvalidKeyException {
        this.checkEngineType(openSSLKey);
        this.key = openSSLKey;
        this.signing = bl;
        try {
            this.resetContext();
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException(invalidAlgorithmParameterException);
        }
    }

    private void resetContext() throws InvalidAlgorithmParameterException {
        NativeRef.EVP_MD_CTX eVP_MD_CTX = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
        this.evpPkeyCtx = this.signing ? NativeCrypto.EVP_DigestSignInit(eVP_MD_CTX, this.evpMdRef, this.key.getNativeRef()) : NativeCrypto.EVP_DigestVerifyInit(eVP_MD_CTX, this.evpMdRef, this.key.getNativeRef());
        this.configureEVP_PKEY_CTX(this.evpPkeyCtx);
        this.ctx = eVP_MD_CTX;
    }

    protected void configureEVP_PKEY_CTX(long l) throws InvalidAlgorithmParameterException {
    }

    @Deprecated
    @Override
    protected Object engineGetParameter(String string) throws InvalidParameterException {
        return null;
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        this.initInternal(OpenSSLKey.fromPrivateKey(privateKey), true);
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.initInternal(OpenSSLKey.fromPublicKey(publicKey), false);
    }

    @Deprecated
    @Override
    protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
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
    protected byte[] engineSign() throws SignatureException {
        Throwable throwable2222;
        Object object = this.ctx;
        object = NativeCrypto.EVP_DigestSignFinal((NativeRef.EVP_MD_CTX)object);
        try {
            this.resetContext();
            return object;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new AssertionError((Object)"Reset of context failed after it was successful once");
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                object = new SignatureException(exception);
                throw object;
            }
        }
        try {
            this.resetContext();
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new AssertionError((Object)"Reset of context failed after it was successful once");
        }
        throw throwable2222;
    }

    @Override
    protected void engineUpdate(byte by) {
        byte[] arrby = this.singleByte;
        arrby[0] = by;
        this.engineUpdate(arrby, 0, 1);
    }

    @Override
    protected void engineUpdate(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return;
        }
        if (!byteBuffer.isDirect()) {
            super.engineUpdate(byteBuffer);
            return;
        }
        long l = NativeCrypto.getDirectBufferAddress(byteBuffer);
        if (l == 0L) {
            super.engineUpdate(byteBuffer);
            return;
        }
        int n = byteBuffer.position();
        if (n >= 0) {
            l = (long)n + l;
            int n2 = byteBuffer.remaining();
            if (n2 >= 0) {
                NativeRef.EVP_MD_CTX eVP_MD_CTX = this.ctx;
                if (this.signing) {
                    NativeCrypto.EVP_DigestSignUpdateDirect(eVP_MD_CTX, l, n2);
                } else {
                    NativeCrypto.EVP_DigestVerifyUpdateDirect(eVP_MD_CTX, l, n2);
                }
                byteBuffer.position(n + n2);
                return;
            }
            throw new RuntimeException("Negative remaining amount");
        }
        throw new RuntimeException("Negative position");
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        NativeRef.EVP_MD_CTX eVP_MD_CTX = this.ctx;
        if (this.signing) {
            NativeCrypto.EVP_DigestSignUpdate(eVP_MD_CTX, arrby, n, n2);
        } else {
            NativeCrypto.EVP_DigestVerifyUpdate(eVP_MD_CTX, arrby, n, n2);
        }
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
    protected boolean engineVerify(byte[] arrby) throws SignatureException {
        Throwable throwable2222;
        Object object = this.ctx;
        boolean bl = NativeCrypto.EVP_DigestVerifyFinal((NativeRef.EVP_MD_CTX)object, arrby, 0, arrby.length);
        try {
            this.resetContext();
            return bl;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new AssertionError((Object)"Reset of context failed after it was successful once");
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                object = new SignatureException(exception);
                throw object;
            }
        }
        try {
            this.resetContext();
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new AssertionError((Object)"Reset of context failed after it was successful once");
        }
        throw throwable2222;
    }

    protected final long getEVP_PKEY_CTX() {
        return this.evpPkeyCtx;
    }

    private static enum EngineType {
        RSA,
        EC;
        
    }

    public static final class MD5RSA
    extends RSAPKCS1Padding {
        public MD5RSA() {
            super(EvpMdRef.MD5.EVP_MD);
        }
    }

    static abstract class RSAPKCS1Padding
    extends OpenSSLSignature {
        RSAPKCS1Padding(long l) {
            super(l, EngineType.RSA);
        }

        @Override
        protected final void configureEVP_PKEY_CTX(long l) throws InvalidAlgorithmParameterException {
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(l, 1);
        }
    }

    static abstract class RSAPSSPadding
    extends OpenSSLSignature {
        private static final int TRAILER_FIELD_BC_ID = 1;
        private final String contentDigestAlgorithm;
        private String mgf1DigestAlgorithm;
        private long mgf1EvpMdRef;
        private int saltSizeBytes;

        RSAPSSPadding(long l, String string, int n) {
            super(l, EngineType.RSA);
            this.contentDigestAlgorithm = string;
            this.mgf1DigestAlgorithm = string;
            this.mgf1EvpMdRef = l;
            this.saltSizeBytes = n;
        }

        @Override
        protected final void configureEVP_PKEY_CTX(long l) throws InvalidAlgorithmParameterException {
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(l, 6);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_mgf1_md(l, this.mgf1EvpMdRef);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_pss_saltlen(l, this.saltSizeBytes);
        }

        @Override
        protected final AlgorithmParameters engineGetParameters() {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PSS");
                String string = this.contentDigestAlgorithm;
                MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec(this.mgf1DigestAlgorithm);
                PSSParameterSpec pSSParameterSpec = new PSSParameterSpec(string, "MGF1", mGF1ParameterSpec, this.saltSizeBytes, 1);
                algorithmParameters.init(pSSParameterSpec);
                return algorithmParameters;
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                throw new ProviderException("Failed to create PSS AlgorithmParameters", invalidParameterSpecException);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new ProviderException("Failed to create PSS AlgorithmParameters", noSuchAlgorithmException);
            }
        }

        @Override
        protected final void engineSetParameter(AlgorithmParameterSpec object) throws InvalidAlgorithmParameterException {
            if (object instanceof PSSParameterSpec) {
                Object object2 = EvpMdRef.getJcaDigestAlgorithmStandardName(((PSSParameterSpec)(object = (PSSParameterSpec)object)).getDigestAlgorithm());
                if (object2 != null) {
                    if (this.contentDigestAlgorithm.equalsIgnoreCase((String)object2)) {
                        object2 = ((PSSParameterSpec)object).getMGFAlgorithm();
                        if (!"MGF1".equalsIgnoreCase((String)object2) && !"1.2.840.113549.1.1.8".equals(object2)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unsupported MGF algorithm: ");
                            ((StringBuilder)object).append((String)object2);
                            ((StringBuilder)object).append(". Only ");
                            ((StringBuilder)object).append("MGF1");
                            ((StringBuilder)object).append(" supported");
                            throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                        }
                        object2 = ((PSSParameterSpec)object).getMGFParameters();
                        if (object2 instanceof MGF1ParameterSpec) {
                            Object object3 = (MGF1ParameterSpec)((PSSParameterSpec)object).getMGFParameters();
                            object2 = EvpMdRef.getJcaDigestAlgorithmStandardName(((MGF1ParameterSpec)object3).getDigestAlgorithm());
                            if (object2 != null) {
                                int n;
                                block9 : {
                                    int n2;
                                    block10 : {
                                        long l;
                                        try {
                                            l = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName((String)object2);
                                            n = ((PSSParameterSpec)object).getSaltLength();
                                            if (n < 0) break block9;
                                            n2 = ((PSSParameterSpec)object).getTrailerField();
                                            if (n2 != 1) break block10;
                                            this.mgf1DigestAlgorithm = object2;
                                            this.mgf1EvpMdRef = l;
                                            this.saltSizeBytes = n;
                                        }
                                        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                                            object3 = new StringBuilder();
                                            ((StringBuilder)object3).append("Failed to obtain EVP_MD for ");
                                            ((StringBuilder)object3).append((String)object2);
                                            throw new ProviderException(((StringBuilder)object3).toString(), noSuchAlgorithmException);
                                        }
                                        l = this.getEVP_PKEY_CTX();
                                        if (l != 0L) {
                                            this.configureEVP_PKEY_CTX(l);
                                        }
                                        return;
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Unsupported trailer field: ");
                                    ((StringBuilder)object).append(n2);
                                    ((StringBuilder)object).append(". Only ");
                                    ((StringBuilder)object).append(1);
                                    ((StringBuilder)object).append(" supported");
                                    throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Salt length must be non-negative: ");
                                ((StringBuilder)object).append(n);
                                throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unsupported MGF1 digest algorithm: ");
                            ((StringBuilder)object).append(((MGF1ParameterSpec)object3).getDigestAlgorithm());
                            throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unsupported MGF parameters: ");
                        ((StringBuilder)object).append(object2);
                        ((StringBuilder)object).append(". Only ");
                        ((StringBuilder)object).append(MGF1ParameterSpec.class.getName());
                        ((StringBuilder)object).append(" supported");
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    }
                    throw new InvalidAlgorithmParameterException("Changing content digest algorithm not supported");
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unsupported content digest algorithm: ");
                ((StringBuilder)object2).append(((PSSParameterSpec)object).getDigestAlgorithm());
                throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported parameter: ");
            stringBuilder.append(object);
            stringBuilder.append(". Only ");
            stringBuilder.append(PSSParameterSpec.class.getName());
            stringBuilder.append(" supported");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }
    }

    public static final class SHA1ECDSA
    extends OpenSSLSignature {
        public SHA1ECDSA() {
            super(EvpMdRef.SHA1.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA1RSA
    extends RSAPKCS1Padding {
        public SHA1RSA() {
            super(EvpMdRef.SHA1.EVP_MD);
        }
    }

    public static final class SHA1RSAPSS
    extends RSAPSSPadding {
        public SHA1RSAPSS() {
            super(EvpMdRef.SHA1.EVP_MD, "SHA-1", EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    public static final class SHA224ECDSA
    extends OpenSSLSignature {
        public SHA224ECDSA() {
            super(EvpMdRef.SHA224.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA224RSA
    extends RSAPKCS1Padding {
        public SHA224RSA() {
            super(EvpMdRef.SHA224.EVP_MD);
        }
    }

    public static final class SHA224RSAPSS
    extends RSAPSSPadding {
        public SHA224RSAPSS() {
            super(EvpMdRef.SHA224.EVP_MD, "SHA-224", EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class SHA256ECDSA
    extends OpenSSLSignature {
        public SHA256ECDSA() {
            super(EvpMdRef.SHA256.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA256RSA
    extends RSAPKCS1Padding {
        public SHA256RSA() {
            super(EvpMdRef.SHA256.EVP_MD);
        }
    }

    public static final class SHA256RSAPSS
    extends RSAPSSPadding {
        public SHA256RSAPSS() {
            super(EvpMdRef.SHA256.EVP_MD, "SHA-256", EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class SHA384ECDSA
    extends OpenSSLSignature {
        public SHA384ECDSA() {
            super(EvpMdRef.SHA384.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA384RSA
    extends RSAPKCS1Padding {
        public SHA384RSA() {
            super(EvpMdRef.SHA384.EVP_MD);
        }
    }

    public static final class SHA384RSAPSS
    extends RSAPSSPadding {
        public SHA384RSAPSS() {
            super(EvpMdRef.SHA384.EVP_MD, "SHA-384", EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class SHA512ECDSA
    extends OpenSSLSignature {
        public SHA512ECDSA() {
            super(EvpMdRef.SHA512.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA512RSA
    extends RSAPKCS1Padding {
        public SHA512RSA() {
            super(EvpMdRef.SHA512.EVP_MD);
        }
    }

    public static final class SHA512RSAPSS
    extends RSAPSSPadding {
        public SHA512RSAPSS() {
            super(EvpMdRef.SHA512.EVP_MD, "SHA-512", EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

}

