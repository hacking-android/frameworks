/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.CTVerificationResult;
import java.security.cert.X509Certificate;

public interface CTPolicy {
    public boolean doesResultConformToPolicy(CTVerificationResult var1, String var2, X509Certificate[] var3);
}

