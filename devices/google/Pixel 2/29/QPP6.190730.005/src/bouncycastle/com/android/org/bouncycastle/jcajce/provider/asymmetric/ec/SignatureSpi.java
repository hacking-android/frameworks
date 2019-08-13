/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DSAExt;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.digests.NullDigest;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.crypto.signers.DSAEncoding;
import com.android.org.bouncycastle.crypto.signers.ECDSASigner;
import com.android.org.bouncycastle.crypto.signers.StandardDSAEncoding;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.DSABase;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class SignatureSpi
extends DSABase {
    SignatureSpi(Digest digest, DSAExt dSAExt, DSAEncoding dSAEncoding) {
        super(digest, dSAExt, dSAEncoding);
    }

    @Override
    protected void engineInitSign(PrivateKey object) throws InvalidKeyException {
        object = ECUtil.generatePrivateKeyParameter((PrivateKey)object);
        this.digest.reset();
        if (this.appRandom != null) {
            this.signer.init(true, new ParametersWithRandom((CipherParameters)object, this.appRandom));
        } else {
            this.signer.init(true, (CipherParameters)object);
        }
    }

    @Override
    protected void engineInitVerify(PublicKey object) throws InvalidKeyException {
        object = ECUtils.generatePublicKeyParameter((PublicKey)object);
        this.digest.reset();
        this.signer.init(false, (CipherParameters)object);
    }

    public static class ecDSA
    extends SignatureSpi {
        public ecDSA() {
            super(AndroidDigestFactory.getSHA1(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA224
    extends SignatureSpi {
        public ecDSA224() {
            super(AndroidDigestFactory.getSHA224(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA256
    extends SignatureSpi {
        public ecDSA256() {
            super(AndroidDigestFactory.getSHA256(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA384
    extends SignatureSpi {
        public ecDSA384() {
            super(AndroidDigestFactory.getSHA384(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA512
    extends SignatureSpi {
        public ecDSA512() {
            super(AndroidDigestFactory.getSHA512(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSAnone
    extends SignatureSpi {
        public ecDSAnone() {
            super(new NullDigest(), new ECDSASigner(), StandardDSAEncoding.INSTANCE);
        }
    }

}

