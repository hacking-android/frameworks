/*
 * Decompiled with CFR 0.145.
 */
package android.privacy.internal.rappor;

import android.privacy.DifferentialPrivacyConfig;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;

public class RapporConfig
implements DifferentialPrivacyConfig {
    private static final String ALGORITHM_NAME = "Rappor";
    final String mEncoderId;
    final int mNumBits;
    final int mNumBloomHashes;
    final int mNumCohorts;
    final double mProbabilityF;
    final double mProbabilityP;
    final double mProbabilityQ;

    public RapporConfig(String string2, int n, double d, double d2, double d3, int n2, int n3) {
        Preconditions.checkArgument(TextUtils.isEmpty(string2) ^ true, "encoderId cannot be empty");
        this.mEncoderId = string2;
        boolean bl = false;
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2, "numBits needs to be > 0");
        this.mNumBits = n;
        bl2 = d >= 0.0 && d <= 1.0;
        Preconditions.checkArgument(bl2, "probabilityF must be in range [0.0, 1.0]");
        this.mProbabilityF = d;
        bl2 = d2 >= 0.0 && d2 <= 1.0;
        Preconditions.checkArgument(bl2, "probabilityP must be in range [0.0, 1.0]");
        this.mProbabilityP = d2;
        bl2 = d3 >= 0.0 && d3 <= 1.0;
        Preconditions.checkArgument(bl2, "probabilityQ must be in range [0.0, 1.0]");
        this.mProbabilityQ = d3;
        bl2 = n2 > 0;
        Preconditions.checkArgument(bl2, "numCohorts needs to be > 0");
        this.mNumCohorts = n2;
        bl2 = bl;
        if (n3 > 0) {
            bl2 = true;
        }
        Preconditions.checkArgument(bl2, "numBloomHashes needs to be > 0");
        this.mNumBloomHashes = n3;
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    public String toString() {
        return String.format("EncoderId: %s, NumBits: %d, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f, NumCohorts: %d, NumBloomHashes: %d", this.mEncoderId, this.mNumBits, this.mProbabilityF, this.mProbabilityP, this.mProbabilityQ, this.mNumCohorts, this.mNumBloomHashes);
    }
}

