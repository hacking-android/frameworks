/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.InstantAppResolveInfo;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;

public final class AuxiliaryResolveInfo {
    public final Intent failureIntent;
    public final List<AuxiliaryFilter> filters;
    public final ComponentName installFailureActivity;
    public final boolean needsPhaseTwo;
    public final String token;

    public AuxiliaryResolveInfo(ComponentName componentName, Intent intent, List<AuxiliaryFilter> list) {
        this.installFailureActivity = componentName;
        this.filters = list;
        this.token = null;
        this.needsPhaseTwo = false;
        this.failureIntent = intent;
    }

    public AuxiliaryResolveInfo(ComponentName componentName, String string2, long l, String string3) {
        this(componentName, null, Collections.singletonList(new AuxiliaryFilter(string2, l, string3)));
    }

    public AuxiliaryResolveInfo(String string2, boolean bl, Intent intent, List<AuxiliaryFilter> list) {
        this.token = string2;
        this.needsPhaseTwo = bl;
        this.failureIntent = intent;
        this.filters = list;
        this.installFailureActivity = null;
    }

    public static final class AuxiliaryFilter
    extends IntentFilter {
        public final Bundle extras;
        public final String packageName;
        public final InstantAppResolveInfo resolveInfo;
        public final String splitName;
        public final long versionCode;

        public AuxiliaryFilter(IntentFilter intentFilter, InstantAppResolveInfo instantAppResolveInfo, String string2, Bundle bundle) {
            super(intentFilter);
            this.resolveInfo = instantAppResolveInfo;
            this.packageName = instantAppResolveInfo.getPackageName();
            this.versionCode = instantAppResolveInfo.getLongVersionCode();
            this.splitName = string2;
            this.extras = bundle;
        }

        public AuxiliaryFilter(InstantAppResolveInfo instantAppResolveInfo, String string2, Bundle bundle) {
            this.resolveInfo = instantAppResolveInfo;
            this.packageName = instantAppResolveInfo.getPackageName();
            this.versionCode = instantAppResolveInfo.getLongVersionCode();
            this.splitName = string2;
            this.extras = bundle;
        }

        public AuxiliaryFilter(String string2, long l, String string3) {
            this.resolveInfo = null;
            this.packageName = string2;
            this.versionCode = l;
            this.splitName = string3;
            this.extras = null;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AuxiliaryFilter{packageName='");
            stringBuilder.append(this.packageName);
            stringBuilder.append('\'');
            stringBuilder.append(", versionCode=");
            stringBuilder.append(this.versionCode);
            stringBuilder.append(", splitName='");
            stringBuilder.append(this.splitName);
            stringBuilder.append('\'');
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

