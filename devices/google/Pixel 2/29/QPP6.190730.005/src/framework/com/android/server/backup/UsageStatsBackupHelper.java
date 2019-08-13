/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.app.usage.UsageStatsManagerInternal;
import android.content.Context;
import com.android.server.LocalServices;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UsageStatsBackupHelper
extends BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final boolean DEBUG = false;
    static final String KEY_USAGE_STATS = "usage_stats";
    static final String TAG = "UsgStatsBackupHelper";

    public UsageStatsBackupHelper(Context context) {
        super(1, KEY_USAGE_STATS);
    }

    @Override
    protected void applyRestoredPayload(String string2, byte[] arrby) {
        if (KEY_USAGE_STATS.equals(string2)) {
            UsageStatsManagerInternal usageStatsManagerInternal = LocalServices.getService(UsageStatsManagerInternal.class);
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(arrby));
            try {
                int n = dataInputStream.readInt();
                arrby = new byte[arrby.length - 4];
                dataInputStream.read(arrby, 0, arrby.length);
                usageStatsManagerInternal.applyRestoredPayload(n, string2, arrby);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    @Override
    protected byte[] getBackupPayload(String string2) {
        if (KEY_USAGE_STATS.equals(string2)) {
            UsageStatsManagerInternal usageStatsManagerInternal = LocalServices.getService(UsageStatsManagerInternal.class);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeInt(0);
                dataOutputStream.write(usageStatsManagerInternal.getBackupPayload(0, string2));
            }
            catch (IOException iOException) {
                byteArrayOutputStream.reset();
            }
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }
}

