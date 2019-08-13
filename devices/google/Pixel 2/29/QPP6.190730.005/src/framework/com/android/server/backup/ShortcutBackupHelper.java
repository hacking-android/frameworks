/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.content.pm.IShortcutService;
import android.os.ServiceManager;
import android.util.Slog;

public class ShortcutBackupHelper
extends BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final String KEY_USER_FILE = "shortcutuser.xml";
    private static final String TAG = "ShortcutBackupAgent";

    public ShortcutBackupHelper() {
        super(1, KEY_USER_FILE);
    }

    private IShortcutService getShortcutService() {
        return IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut"));
    }

    @Override
    protected void applyRestoredPayload(String string2, byte[] object) {
        int n = string2.hashCode() == -792920646 && string2.equals(KEY_USER_FILE) ? 0 : -1;
        if (n != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown key: ");
            ((StringBuilder)object).append(string2);
            Slog.w(TAG, ((StringBuilder)object).toString());
        } else {
            try {
                this.getShortcutService().applyRestore((byte[])object, 0);
            }
            catch (Exception exception) {
                Slog.wtf(TAG, "Restore failed", exception);
            }
        }
    }

    @Override
    protected byte[] getBackupPayload(String arrby) {
        int n = arrby.hashCode() == -792920646 && arrby.equals(KEY_USER_FILE) ? 0 : -1;
        if (n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown key: ");
            stringBuilder.append((String)arrby);
            Slog.w(TAG, stringBuilder.toString());
        } else {
            try {
                arrby = this.getShortcutService().getBackupPayload(0);
                return arrby;
            }
            catch (Exception exception) {
                Slog.wtf(TAG, "Backup failed", exception);
            }
        }
        return null;
    }
}

