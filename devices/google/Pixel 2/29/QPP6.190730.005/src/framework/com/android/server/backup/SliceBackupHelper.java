/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.app.slice.ISliceManager;
import android.content.Context;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

public class SliceBackupHelper
extends BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final boolean DEBUG = Log.isLoggable("SliceBackupHelper", 3);
    static final String KEY_SLICES = "slices";
    static final String TAG = "SliceBackupHelper";

    public SliceBackupHelper(Context context) {
        super(1, KEY_SLICES);
    }

    @Override
    protected void applyRestoredPayload(String string2, byte[] arrby) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got restore of ");
            stringBuilder.append(string2);
            Slog.v(TAG, stringBuilder.toString());
        }
        if (KEY_SLICES.equals(string2)) {
            try {
                ISliceManager.Stub.asInterface(ServiceManager.getService("slice")).applyRestore(arrby, 0);
            }
            catch (Exception exception) {
                Slog.e(TAG, "Couldn't communicate with slice manager");
            }
        }
    }

    @Override
    protected byte[] getBackupPayload(String string2) {
        byte[] arrby = null;
        if (KEY_SLICES.equals(string2)) {
            try {
                arrby = ISliceManager.Stub.asInterface(ServiceManager.getService("slice")).getBackupPayload(0);
            }
            catch (Exception exception) {
                Slog.e(TAG, "Couldn't communicate with slice manager");
                arrby = null;
            }
        }
        return arrby;
    }
}

