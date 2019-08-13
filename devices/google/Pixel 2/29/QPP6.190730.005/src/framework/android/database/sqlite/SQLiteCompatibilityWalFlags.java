/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteGlobal;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.KeyValueListParser;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;

public class SQLiteCompatibilityWalFlags {
    private static final String TAG = "SQLiteCompatibilityWalFlags";
    private static volatile boolean sCallingGlobalSettings;
    private static volatile boolean sInitialized;
    private static volatile boolean sLegacyCompatibilityWalEnabled;
    private static volatile long sTruncateSize;
    private static volatile String sWALSyncMode;

    static {
        sTruncateSize = -1L;
    }

    private SQLiteCompatibilityWalFlags() {
    }

    @VisibleForTesting
    public static long getTruncateSize() {
        SQLiteCompatibilityWalFlags.initIfNeeded();
        return sTruncateSize;
    }

    @VisibleForTesting
    public static String getWALSyncMode() {
        SQLiteCompatibilityWalFlags.initIfNeeded();
        if (sLegacyCompatibilityWalEnabled) {
            return sWALSyncMode;
        }
        throw new IllegalStateException("isLegacyCompatibilityWalEnabled() == false");
    }

    @VisibleForTesting
    public static void init(String charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            sInitialized = true;
            return;
        }
        Object object = new KeyValueListParser(',');
        try {
            ((KeyValueListParser)object).setString((String)charSequence);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Setting has invalid format: ");
            ((StringBuilder)object).append((String)charSequence);
            Log.e(TAG, ((StringBuilder)object).toString(), illegalArgumentException);
            sInitialized = true;
            return;
        }
        sLegacyCompatibilityWalEnabled = ((KeyValueListParser)object).getBoolean("legacy_compatibility_wal_enabled", false);
        sWALSyncMode = ((KeyValueListParser)object).getString("wal_syncmode", SQLiteGlobal.getWALSyncMode());
        sTruncateSize = ((KeyValueListParser)object).getInt("truncate_size", -1);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Read compatibility WAL flags: legacy_compatibility_wal_enabled=");
        ((StringBuilder)charSequence).append(sLegacyCompatibilityWalEnabled);
        ((StringBuilder)charSequence).append(", wal_syncmode=");
        ((StringBuilder)charSequence).append(sWALSyncMode);
        Log.i(TAG, ((StringBuilder)charSequence).toString());
        sInitialized = true;
    }

    private static void initIfNeeded() {
        if (!sInitialized && !sCallingGlobalSettings) {
            Object object = ActivityThread.currentActivityThread();
            object = object == null ? null : ((ActivityThread)object).getApplication();
            Object var1_2 = null;
            if (object == null) {
                Log.w(TAG, "Cannot read global setting sqlite_compatibility_wal_flags - Application state not available");
                object = var1_2;
            } else {
                sCallingGlobalSettings = true;
                object = Settings.Global.getString(((ContextWrapper)object).getContentResolver(), "sqlite_compatibility_wal_flags");
            }
            SQLiteCompatibilityWalFlags.init((String)object);
            return;
            finally {
                sCallingGlobalSettings = false;
            }
        }
    }

    @VisibleForTesting
    public static boolean isLegacyCompatibilityWalEnabled() {
        SQLiteCompatibilityWalFlags.initIfNeeded();
        return sLegacyCompatibilityWalEnabled;
    }

    @VisibleForTesting
    public static void reset() {
        sInitialized = false;
        sLegacyCompatibilityWalEnabled = false;
        sWALSyncMode = null;
    }
}

