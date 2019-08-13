/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.VerifiedSCT;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CTVerificationResult {
    private final ArrayList<VerifiedSCT> invalidSCTs = new ArrayList();
    private final ArrayList<VerifiedSCT> validSCTs = new ArrayList();

    public void add(VerifiedSCT verifiedSCT) {
        if (verifiedSCT.status == VerifiedSCT.Status.VALID) {
            this.validSCTs.add(verifiedSCT);
        } else {
            this.invalidSCTs.add(verifiedSCT);
        }
    }

    public List<VerifiedSCT> getInvalidSCTs() {
        return Collections.unmodifiableList(this.invalidSCTs);
    }

    public List<VerifiedSCT> getValidSCTs() {
        return Collections.unmodifiableList(this.validSCTs);
    }
}

