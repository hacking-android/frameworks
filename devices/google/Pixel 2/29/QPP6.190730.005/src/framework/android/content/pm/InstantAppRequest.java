/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.Intent;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.InstantAppResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

public final class InstantAppRequest {
    public final String callingPackage;
    public final InstantAppResolveInfo.InstantAppDigest digest;
    public final Intent origIntent;
    public final boolean resolveForStart;
    public final String resolvedType;
    public final AuxiliaryResolveInfo responseObj;
    public final int userId;
    public final Bundle verificationBundle;

    public InstantAppRequest(AuxiliaryResolveInfo auxiliaryResolveInfo, Intent intent, String string2, String string3, int n, Bundle bundle, boolean bl) {
        this.responseObj = auxiliaryResolveInfo;
        this.origIntent = intent;
        this.resolvedType = string2;
        this.callingPackage = string3;
        this.userId = n;
        this.verificationBundle = bundle;
        this.resolveForStart = bl;
        this.digest = intent.getData() != null && !TextUtils.isEmpty(intent.getData().getHost()) ? new InstantAppResolveInfo.InstantAppDigest(intent.getData().getHost(), 5) : InstantAppResolveInfo.InstantAppDigest.UNDEFINED;
    }
}

