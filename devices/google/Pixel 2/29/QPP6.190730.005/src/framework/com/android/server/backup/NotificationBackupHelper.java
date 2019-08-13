/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.INotificationManager;
import android.app.backup.BlobBackupHelper;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

public class NotificationBackupHelper
extends BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final boolean DEBUG = Log.isLoggable("NotifBackupHelper", 3);
    static final String KEY_NOTIFICATIONS = "notifications";
    static final String TAG = "NotifBackupHelper";
    private final int mUserId;

    public NotificationBackupHelper(int n) {
        super(1, KEY_NOTIFICATIONS);
        this.mUserId = n;
    }

    @Override
    protected void applyRestoredPayload(String string2, byte[] arrby) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got restore of ");
            stringBuilder.append(string2);
            Slog.v(TAG, stringBuilder.toString());
        }
        if (KEY_NOTIFICATIONS.equals(string2)) {
            try {
                INotificationManager.Stub.asInterface(ServiceManager.getService("notification")).applyRestore(arrby, this.mUserId);
            }
            catch (Exception exception) {
                Slog.e(TAG, "Couldn't communicate with notification manager");
            }
        }
    }

    @Override
    protected byte[] getBackupPayload(String string2) {
        byte[] arrby = null;
        if (KEY_NOTIFICATIONS.equals(string2)) {
            try {
                arrby = INotificationManager.Stub.asInterface(ServiceManager.getService("notification")).getBackupPayload(this.mUserId);
            }
            catch (Exception exception) {
                Slog.e(TAG, "Couldn't communicate with notification manager");
                arrby = null;
            }
        }
        return arrby;
    }
}

