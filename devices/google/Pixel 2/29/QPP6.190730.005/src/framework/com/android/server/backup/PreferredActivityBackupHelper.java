/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.AppGlobals;
import android.app.backup.BlobBackupHelper;
import android.content.pm.IPackageManager;
import android.util.Slog;

public class PreferredActivityBackupHelper
extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_DEFAULT_APPS = "default-apps";
    private static final String KEY_INTENT_VERIFICATION = "intent-verification";
    private static final String KEY_PREFERRED = "preferred-activity";
    private static final int STATE_VERSION = 3;
    private static final String TAG = "PreferredBackup";

    public PreferredActivityBackupHelper() {
        super(3, KEY_PREFERRED, KEY_DEFAULT_APPS, KEY_INTENT_VERIFICATION);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void applyRestoredPayload(String string2, byte[] object) {
        IPackageManager iPackageManager = AppGlobals.getPackageManager();
        int n = -1;
        try {
            int n2 = string2.hashCode();
            if (n2 != -696985986) {
                if (n2 != -429170260) {
                    if (n2 == 1336142555 && string2.equals(KEY_PREFERRED)) {
                        n = 0;
                    }
                } else if (string2.equals(KEY_INTENT_VERIFICATION)) {
                    n = 2;
                }
            } else if (string2.equals(KEY_DEFAULT_APPS)) {
                n = 1;
            }
            if (n == 0) {
                iPackageManager.restorePreferredActivities((byte[])object, 0);
                return;
            }
            if (n == 1) {
                iPackageManager.restoreDefaultApps((byte[])object, 0);
                return;
            }
            if (n != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected restore key ");
                ((StringBuilder)object).append(string2);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            iPackageManager.restoreIntentFilterVerification((byte[])object, 0);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to restore key ");
            stringBuilder.append(string2);
            Slog.w(TAG, stringBuilder.toString());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] getBackupPayload(String string2) {
        Object object;
        int n;
        block10 : {
            block8 : {
                block9 : {
                    object = AppGlobals.getPackageManager();
                    n = -1;
                    int n2 = string2.hashCode();
                    if (n2 == -696985986) break block8;
                    if (n2 == -429170260) break block9;
                    if (n2 != 1336142555) break block10;
                    if (string2.equals(KEY_PREFERRED)) {
                        n = 0;
                    }
                    break block10;
                }
                if (string2.equals(KEY_INTENT_VERIFICATION)) {
                    n = 2;
                }
                break block10;
            }
            if (!string2.equals(KEY_DEFAULT_APPS)) break block10;
            n = 1;
        }
        if (n == 0) return object.getPreferredActivityBackup(0);
        if (n == 1) return object.getDefaultAppsBackup(0);
        if (n == 2) return object.getIntentFilterVerificationBackup(0);
        try {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected backup key ");
            ((StringBuilder)object).append(string2);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return null;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to store payload ");
            stringBuilder.append(string2);
            Slog.e(TAG, stringBuilder.toString());
        }
        return null;
    }
}

