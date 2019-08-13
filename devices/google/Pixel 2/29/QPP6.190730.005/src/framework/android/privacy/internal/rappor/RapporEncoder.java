/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.android.rappor.Encoder
 */
package android.privacy.internal.rappor;

import android.privacy.DifferentialPrivacyConfig;
import android.privacy.DifferentialPrivacyEncoder;
import android.privacy.internal.rappor.RapporConfig;
import com.google.android.rappor.Encoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RapporEncoder
implements DifferentialPrivacyEncoder {
    private static final byte[] INSECURE_SECRET = new byte[]{-41, 104, -103, -109, -108, 19, 83, 84, -2, -48, 126, 84, -2, -48, 126, 84, -41, 104, -103, -109, -108, 19, 83, 84, -2, -48, 126, 84, -2, -48, 126, 84, -41, 104, -103, -109, -108, 19, 83, 84, -2, -48, 126, 84, -2, -48, 126, 84};
    private static final SecureRandom sSecureRandom = new SecureRandom();
    private final RapporConfig mConfig;
    private final Encoder mEncoder;
    private final boolean mIsSecure;

    private RapporEncoder(RapporConfig rapporConfig, boolean bl, byte[] arrby) {
        Random random;
        this.mConfig = rapporConfig;
        this.mIsSecure = bl;
        if (bl) {
            random = sSecureRandom;
        } else {
            random = new Random(this.getInsecureSeed(rapporConfig.mEncoderId));
            arrby = INSECURE_SECRET;
        }
        this.mEncoder = new Encoder(random, null, null, arrby, rapporConfig.mEncoderId, rapporConfig.mNumBits, rapporConfig.mProbabilityF, rapporConfig.mProbabilityP, rapporConfig.mProbabilityQ, rapporConfig.mNumCohorts, rapporConfig.mNumBloomHashes);
    }

    public static RapporEncoder createEncoder(RapporConfig rapporConfig, byte[] arrby) {
        return new RapporEncoder(rapporConfig, true, arrby);
    }

    public static RapporEncoder createInsecureEncoderForTest(RapporConfig rapporConfig) {
        return new RapporEncoder(rapporConfig, false, null);
    }

    private long getInsecureSeed(String string2) {
        try {
            long l = ByteBuffer.wrap(MessageDigest.getInstance("SHA-256").digest(string2.getBytes(StandardCharsets.UTF_8))).getLong();
            return l;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError((Object)"Unable generate insecure seed");
        }
    }

    @Override
    public byte[] encodeBits(byte[] arrby) {
        return this.mEncoder.encodeBits(arrby);
    }

    @Override
    public byte[] encodeBoolean(boolean bl) {
        return this.mEncoder.encodeBoolean(bl);
    }

    @Override
    public byte[] encodeString(String string2) {
        return this.mEncoder.encodeString(string2);
    }

    @Override
    public RapporConfig getConfig() {
        return this.mConfig;
    }

    @Override
    public boolean isInsecureEncoderForTest() {
        return this.mIsSecure ^ true;
    }
}

