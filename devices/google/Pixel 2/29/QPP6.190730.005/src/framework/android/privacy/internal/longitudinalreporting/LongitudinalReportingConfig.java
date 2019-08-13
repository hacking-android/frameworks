/*
 * Decompiled with CFR 0.145.
 */
package android.privacy.internal.longitudinalreporting;

import android.privacy.DifferentialPrivacyConfig;
import android.privacy.internal.rappor.RapporConfig;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;

public class LongitudinalReportingConfig
implements DifferentialPrivacyConfig {
    private static final String ALGORITHM_NAME = "LongitudinalReporting";
    private final String mEncoderId;
    private final RapporConfig mIRRConfig;
    private final double mProbabilityF;
    private final double mProbabilityP;
    private final double mProbabilityQ;

    public LongitudinalReportingConfig(String string2, double d, double d2, double d3) {
        boolean bl = false;
        boolean bl2 = d >= 0.0 && d <= 1.0;
        Preconditions.checkArgument(bl2, "probabilityF must be in range [0.0, 1.0]");
        this.mProbabilityF = d;
        bl2 = d2 >= 0.0 && d2 <= 1.0;
        Preconditions.checkArgument(bl2, "probabilityP must be in range [0.0, 1.0]");
        this.mProbabilityP = d2;
        bl2 = bl;
        if (d3 >= 0.0) {
            bl2 = bl;
            if (d3 <= 1.0) {
                bl2 = true;
            }
        }
        Preconditions.checkArgument(bl2, "probabilityQ must be in range [0.0, 1.0]");
        this.mProbabilityQ = d3;
        Preconditions.checkArgument(TextUtils.isEmpty(string2) ^ true, "encoderId cannot be empty");
        this.mEncoderId = string2;
        this.mIRRConfig = new RapporConfig(string2, 1, 0.0, d, 1.0 - d, 1, 1);
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    String getEncoderId() {
        return this.mEncoderId;
    }

    RapporConfig getIRRConfig() {
        return this.mIRRConfig;
    }

    double getProbabilityP() {
        return this.mProbabilityP;
    }

    double getProbabilityQ() {
        return this.mProbabilityQ;
    }

    public String toString() {
        return String.format("EncoderId: %s, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f", this.mEncoderId, this.mProbabilityF, this.mProbabilityP, this.mProbabilityQ);
    }
}

