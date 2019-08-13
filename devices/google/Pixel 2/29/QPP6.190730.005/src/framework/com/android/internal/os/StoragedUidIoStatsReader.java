/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StoragedUidIoStatsReader {
    private static final String TAG = StoragedUidIoStatsReader.class.getSimpleName();
    private static String sUidIoFile = "/proc/uid_io/stats";

    public StoragedUidIoStatsReader() {
    }

    @VisibleForTesting
    public StoragedUidIoStatsReader(String string2) {
        sUidIoFile = string2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void readAbsolute(Callback object) {
        int n;
        Throwable throwable522;
        block16 : {
            CharSequence charSequence;
            n = StrictMode.allowThreadDiskReadsMask();
            Object object2 = new File(sUidIoFile);
            object2 = Files.newBufferedReader(((File)object2).toPath());
            while ((charSequence = ((BufferedReader)object2).readLine()) != null) {
                Object object3 = TextUtils.split((String)charSequence, " ");
                if (((String[])object3).length != 11) {
                    String string2 = TAG;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Malformed entry in ");
                    ((StringBuilder)object3).append(sUidIoFile);
                    ((StringBuilder)object3).append(": ");
                    ((StringBuilder)object3).append((String)charSequence);
                    Slog.e(string2, ((StringBuilder)object3).toString());
                    continue;
                }
                charSequence = object3[0];
                try {
                    object.onUidStorageStats(Integer.parseInt((String)object3[0], 10), Long.parseLong((String)object3[1], 10), Long.parseLong((String)object3[2], 10), Long.parseLong((String)object3[3], 10), Long.parseLong((String)object3[4], 10), Long.parseLong((String)object3[5], 10), Long.parseLong((String)object3[6], 10), Long.parseLong((String)object3[7], 10), Long.parseLong((String)object3[8], 10), Long.parseLong((String)object3[9], 10), Long.parseLong((String)object3[10], 10));
                }
                catch (NumberFormatException numberFormatException) {
                    object3 = TAG;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Could not parse entry in ");
                    ((StringBuilder)charSequence).append(sUidIoFile);
                    ((StringBuilder)charSequence).append(": ");
                    ((StringBuilder)charSequence).append(numberFormatException.getMessage());
                    Slog.e((String)object3, ((StringBuilder)charSequence).toString());
                }
            }
            ((BufferedReader)object2).close();
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    if (object2 == null) throw throwable3;
                    try {
                        ((BufferedReader)object2).close();
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        try {
                            throwable2.addSuppressed(throwable4);
                            throw throwable3;
                        }
                        catch (Throwable throwable522) {
                            break block16;
                        }
                        catch (IOException iOException) {
                            charSequence = TAG;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Failed to read ");
                            ((StringBuilder)object).append(sUidIoFile);
                            ((StringBuilder)object).append(": ");
                            ((StringBuilder)object).append(iOException.getMessage());
                            Slog.e((String)charSequence, ((StringBuilder)object).toString());
                        }
                    }
                }
            }
            StrictMode.setThreadPolicyMask(n);
            return;
        }
        StrictMode.setThreadPolicyMask(n);
        throw throwable522;
    }

    public static interface Callback {
        public void onUidStorageStats(int var1, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20);
    }

}

