/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import java.util.ArrayList;
import java.util.Arrays;

class ZygoteArguments {
    boolean mAbiListQuery;
    String[] mApiBlacklistExemptions;
    String mAppDataDir;
    boolean mCapabilitiesSpecified;
    long mEffectiveCapabilities;
    int mGid = 0;
    boolean mGidSpecified;
    int[] mGids;
    int mHiddenApiAccessLogSampleRate = -1;
    int mHiddenApiAccessStatslogSampleRate = -1;
    String mInstructionSet;
    String mInvokeWith;
    int mMountExternal = 0;
    String mNiceName;
    String mPackageName;
    long mPermittedCapabilities;
    boolean mPidQuery;
    String mPreloadApp;
    boolean mPreloadDefault;
    String mPreloadPackage;
    String mPreloadPackageCacheKey;
    String mPreloadPackageLibFileName;
    String mPreloadPackageLibs;
    ArrayList<int[]> mRLimits;
    String[] mRemainingArgs;
    int mRuntimeFlags;
    String mSeInfo;
    boolean mSeInfoSpecified;
    boolean mStartChildZygote;
    int mTargetSdkVersion;
    boolean mTargetSdkVersionSpecified;
    int mUid = 0;
    boolean mUidSpecified;
    boolean mUsapPoolEnabled;
    boolean mUsapPoolStatusSpecified = false;

    ZygoteArguments(String[] arrstring) throws IllegalArgumentException {
        this.parseArgs(arrstring);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseArgs(String[] object) throws IllegalArgumentException {
        Object object2;
        int n;
        int n2;
        int n3 = 0;
        boolean bl = false;
        boolean bl2 = true;
        block6 : do {
            int n4 = ((Object)object).length;
            n2 = 0;
            n = ++n3;
            if (n3 >= n4) break;
            object2 = object[n3];
            if (((String)object2).equals("--")) {
                n = n3 + 1;
                break;
            }
            if (((String)object2).startsWith("--setuid=")) {
                if (this.mUidSpecified) throw new IllegalArgumentException("Duplicate arg specified");
                this.mUidSpecified = true;
                this.mUid = Integer.parseInt(((String)object2).substring(((String)object2).indexOf(61) + 1));
                continue;
            }
            if (((String)object2).startsWith("--setgid=")) {
                if (this.mGidSpecified) throw new IllegalArgumentException("Duplicate arg specified");
                this.mGidSpecified = true;
                this.mGid = Integer.parseInt(((String)object2).substring(((String)object2).indexOf(61) + 1));
                continue;
            }
            if (((String)object2).startsWith("--target-sdk-version=")) {
                if (this.mTargetSdkVersionSpecified) throw new IllegalArgumentException("Duplicate target-sdk-version specified");
                this.mTargetSdkVersionSpecified = true;
                this.mTargetSdkVersion = Integer.parseInt(((String)object2).substring(((String)object2).indexOf(61) + 1));
                continue;
            }
            if (((String)object2).equals("--runtime-args")) {
                bl = true;
                continue;
            }
            if (((String)object2).startsWith("--runtime-flags=")) {
                this.mRuntimeFlags = Integer.parseInt(((String)object2).substring(((String)object2).indexOf(61) + 1));
                continue;
            }
            if (((String)object2).startsWith("--seinfo=")) {
                if (this.mSeInfoSpecified) throw new IllegalArgumentException("Duplicate arg specified");
                this.mSeInfoSpecified = true;
                this.mSeInfo = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                continue;
            }
            if (((String)object2).startsWith("--capabilities=")) {
                if (this.mCapabilitiesSpecified) throw new IllegalArgumentException("Duplicate arg specified");
                this.mCapabilitiesSpecified = true;
                if (((String[])(object2 = ((String)object2).substring(((String)object2).indexOf(61) + 1).split(",", 2))).length == 1) {
                    this.mPermittedCapabilities = this.mEffectiveCapabilities = Long.decode((String)object2[0]).longValue();
                    continue;
                }
                this.mPermittedCapabilities = Long.decode((String)object2[0]);
                this.mEffectiveCapabilities = Long.decode((String)object2[1]);
                continue;
            }
            if (((String)object2).startsWith("--rlimit=")) {
                String[] arrstring = ((String)object2).substring(((String)object2).indexOf(61) + 1).split(",");
                if (arrstring.length != 3) throw new IllegalArgumentException("--rlimit= should have 3 comma-delimited ints");
                object2 = new int[arrstring.length];
                for (n = 0; n < arrstring.length; ++n) {
                    object2[n] = Integer.parseInt(arrstring[n]);
                }
                if (this.mRLimits == null) {
                    this.mRLimits = new ArrayList();
                }
                this.mRLimits.add((int[])object2);
                continue;
            }
            if (((String)object2).startsWith("--setgroups=")) {
                if (this.mGids != null) throw new IllegalArgumentException("Duplicate arg specified");
                object2 = ((String)object2).substring(((String)object2).indexOf(61) + 1).split(",");
                this.mGids = new int[((String[])object2).length];
                n = ((Object[])object2).length - 1;
                do {
                    if (n < 0) continue block6;
                    this.mGids[n] = Integer.parseInt((String)object2[n]);
                    --n;
                } while (true);
            }
            if (((String)object2).equals("--invoke-with")) {
                if (this.mInvokeWith != null) throw new IllegalArgumentException("Duplicate arg specified");
                ++n3;
                try {
                    this.mInvokeWith = object[n3];
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new IllegalArgumentException("--invoke-with requires argument");
                }
            }
            if (((String)object2).startsWith("--nice-name=")) {
                if (this.mNiceName != null) throw new IllegalArgumentException("Duplicate arg specified");
                this.mNiceName = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                continue;
            }
            if (((String)object2).equals("--mount-external-default")) {
                this.mMountExternal = 1;
                continue;
            }
            if (((String)object2).equals("--mount-external-read")) {
                this.mMountExternal = 2;
                continue;
            }
            if (((String)object2).equals("--mount-external-write")) {
                this.mMountExternal = 3;
                continue;
            }
            if (((String)object2).equals("--mount-external-full")) {
                this.mMountExternal = 6;
                continue;
            }
            if (((String)object2).equals("--mount-external-installer")) {
                this.mMountExternal = 5;
                continue;
            }
            if (((String)object2).equals("--mount-external-legacy")) {
                this.mMountExternal = 4;
                continue;
            }
            if (((String)object2).equals("--query-abi-list")) {
                this.mAbiListQuery = true;
                continue;
            }
            if (((String)object2).equals("--get-pid")) {
                this.mPidQuery = true;
                continue;
            }
            if (((String)object2).startsWith("--instruction-set=")) {
                this.mInstructionSet = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                continue;
            }
            if (((String)object2).startsWith("--app-data-dir=")) {
                this.mAppDataDir = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                continue;
            }
            if (((String)object2).equals("--preload-app")) {
                this.mPreloadApp = object[++n3];
                continue;
            }
            if (((String)object2).equals("--preload-package")) {
                this.mPreloadPackage = object[++n3];
                this.mPreloadPackageLibs = object[++n3];
                this.mPreloadPackageLibFileName = object[++n3];
                this.mPreloadPackageCacheKey = object[++n3];
                continue;
            }
            if (((String)object2).equals("--preload-default")) {
                this.mPreloadDefault = true;
                bl2 = false;
                continue;
            }
            if (((String)object2).equals("--start-child-zygote")) {
                this.mStartChildZygote = true;
                continue;
            }
            if (((String)object2).equals("--set-api-blacklist-exemptions")) {
                this.mApiBlacklistExemptions = (String[])Arrays.copyOfRange(object, n3 + 1, ((Object)object).length);
                n3 = ((Object)object).length;
                bl2 = false;
                continue;
            }
            if (((String)object2).startsWith("--hidden-api-log-sampling-rate=")) {
                object2 = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                try {
                    this.mHiddenApiAccessLogSampleRate = Integer.parseInt((String)object2);
                    bl2 = false;
                }
                catch (NumberFormatException numberFormatException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid log sampling rate: ");
                    ((StringBuilder)object).append((String)object2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString(), numberFormatException);
                }
            }
            if (((String)object2).startsWith("--hidden-api-statslog-sampling-rate=")) {
                object2 = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                try {
                    this.mHiddenApiAccessStatslogSampleRate = Integer.parseInt((String)object2);
                    bl2 = false;
                }
                catch (NumberFormatException numberFormatException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid statslog sampling rate: ");
                    ((StringBuilder)object).append((String)object2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString(), numberFormatException);
                }
            }
            if (((String)object2).startsWith("--package-name=")) {
                if (this.mPackageName != null) throw new IllegalArgumentException("Duplicate arg specified");
                this.mPackageName = ((String)object2).substring(((String)object2).indexOf(61) + 1);
                continue;
            }
            n = n3;
            if (!((String)object2).startsWith("--usap-pool-enabled=")) break;
            this.mUsapPoolStatusSpecified = true;
            this.mUsapPoolEnabled = Boolean.parseBoolean(((String)object2).substring(((String)object2).indexOf(61) + 1));
            bl2 = false;
        } while (true);
        if (!this.mAbiListQuery && !this.mPidQuery) {
            if (this.mPreloadPackage != null) {
                if (((Object)object).length - n > 0) throw new IllegalArgumentException("Unexpected arguments after --preload-package.");
            } else if (this.mPreloadApp != null) {
                if (((Object)object).length - n > 0) throw new IllegalArgumentException("Unexpected arguments after --preload-app.");
            } else if (bl2) {
                if (!bl) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unexpected argument : ");
                    ((StringBuilder)object2).append((String)object[n]);
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
                this.mRemainingArgs = new String[((Object)object).length - n];
                object2 = this.mRemainingArgs;
                System.arraycopy(object, n, object2, 0, ((String[])object2).length);
            }
        } else if (((Object)object).length - n > 0) throw new IllegalArgumentException("Unexpected arguments after --query-abi-list.");
        if (!this.mStartChildZygote) return;
        bl = false;
        object = this.mRemainingArgs;
        n = ((Object)object).length;
        n3 = n2;
        do {
            bl2 = bl;
            if (n3 >= n) break;
            if (((String)object[n3]).startsWith("--zygote-socket=")) {
                bl2 = true;
                break;
            }
            ++n3;
        } while (true);
        if (!bl2) throw new IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");
    }
}

