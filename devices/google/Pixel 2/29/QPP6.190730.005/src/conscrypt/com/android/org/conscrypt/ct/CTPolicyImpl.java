/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.CTLogInfo;
import com.android.org.conscrypt.ct.CTLogStore;
import com.android.org.conscrypt.ct.CTPolicy;
import com.android.org.conscrypt.ct.CTVerificationResult;
import com.android.org.conscrypt.ct.SignedCertificateTimestamp;
import com.android.org.conscrypt.ct.VerifiedSCT;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class CTPolicyImpl
implements CTPolicy {
    private final CTLogStore logStore;
    private final int minimumLogCount;

    public CTPolicyImpl(CTLogStore cTLogStore, int n) {
        this.logStore = cTLogStore;
        this.minimumLogCount = n;
    }

    @Override
    public boolean doesResultConformToPolicy(CTVerificationResult object, String object2, X509Certificate[] object32) {
        object2 = new HashSet<CTLogInfo>();
        for (VerifiedSCT verifiedSCT : ((CTVerificationResult)((Object)object)).getValidSCTs()) {
            CTLogInfo cTLogInfo = this.logStore.getKnownLog(verifiedSCT.sct.getLogID());
            if (cTLogInfo == null) continue;
            object2.add(cTLogInfo);
        }
        boolean bl = object2.size() >= this.minimumLogCount;
        return bl;
    }
}

