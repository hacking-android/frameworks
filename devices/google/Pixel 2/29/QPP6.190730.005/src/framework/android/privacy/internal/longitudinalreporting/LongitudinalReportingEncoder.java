/*
 * Decompiled with CFR 0.145.
 */
package android.privacy.internal.longitudinalreporting;

import android.privacy.DifferentialPrivacyConfig;
import android.privacy.DifferentialPrivacyEncoder;
import android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig;
import android.privacy.internal.rappor.RapporConfig;
import android.privacy.internal.rappor.RapporEncoder;
import com.android.internal.annotations.VisibleForTesting;

public class LongitudinalReportingEncoder
implements DifferentialPrivacyEncoder {
    private static final boolean DEBUG = false;
    private static final String PRR1_ENCODER_ID = "prr1_encoder_id";
    private static final String PRR2_ENCODER_ID = "prr2_encoder_id";
    private static final String TAG = "LongitudinalEncoder";
    private final LongitudinalReportingConfig mConfig;
    private final Boolean mFakeValue;
    private final RapporEncoder mIRREncoder;
    private final boolean mIsSecure;

    private LongitudinalReportingEncoder(LongitudinalReportingConfig object, boolean bl, byte[] arrby) {
        this.mConfig = object;
        this.mIsSecure = bl;
        double d = ((LongitudinalReportingConfig)object).getProbabilityP();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((LongitudinalReportingConfig)object).getEncoderId());
        stringBuilder.append(PRR1_ENCODER_ID);
        if (LongitudinalReportingEncoder.getLongTermRandomizedResult(d, bl, arrby, stringBuilder.toString())) {
            d = ((LongitudinalReportingConfig)object).getProbabilityQ();
            stringBuilder = new StringBuilder();
            stringBuilder.append(((LongitudinalReportingConfig)object).getEncoderId());
            stringBuilder.append(PRR2_ENCODER_ID);
            this.mFakeValue = LongitudinalReportingEncoder.getLongTermRandomizedResult(d, bl, arrby, stringBuilder.toString());
        } else {
            this.mFakeValue = null;
        }
        object = ((LongitudinalReportingConfig)object).getIRRConfig();
        object = bl ? RapporEncoder.createEncoder((RapporConfig)object, arrby) : RapporEncoder.createInsecureEncoderForTest((RapporConfig)object);
        this.mIRREncoder = object;
    }

    public static LongitudinalReportingEncoder createEncoder(LongitudinalReportingConfig longitudinalReportingConfig, byte[] arrby) {
        return new LongitudinalReportingEncoder(longitudinalReportingConfig, true, arrby);
    }

    @VisibleForTesting
    public static LongitudinalReportingEncoder createInsecureEncoderForTest(LongitudinalReportingConfig longitudinalReportingConfig) {
        return new LongitudinalReportingEncoder(longitudinalReportingConfig, false, null);
    }

    @VisibleForTesting
    public static boolean getLongTermRandomizedResult(double d, boolean bl, byte[] object, String object2) {
        double d2 = d < 0.5 ? d * 2.0 : (1.0 - d) * 2.0;
        boolean bl2 = true;
        boolean bl3 = !(d < 0.5);
        object2 = new RapporConfig((String)object2, 1, d2, 0.0, 1.0, 1, 1);
        object = bl ? RapporEncoder.createEncoder((RapporConfig)object2, (byte[])object) : RapporEncoder.createInsecureEncoderForTest((RapporConfig)object2);
        bl = ((RapporEncoder)object).encodeBoolean(bl3)[0] > 0 ? bl2 : false;
        return bl;
    }

    @Override
    public byte[] encodeBits(byte[] arrby) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] encodeBoolean(boolean bl) {
        Boolean bl2 = this.mFakeValue;
        if (bl2 != null) {
            bl = bl2;
        }
        return this.mIRREncoder.encodeBoolean(bl);
    }

    @Override
    public byte[] encodeString(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LongitudinalReportingConfig getConfig() {
        return this.mConfig;
    }

    @Override
    public boolean isInsecureEncoderForTest() {
        return this.mIsSecure ^ true;
    }
}

