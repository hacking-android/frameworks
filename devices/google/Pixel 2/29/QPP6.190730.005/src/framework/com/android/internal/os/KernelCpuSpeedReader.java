/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.Os
 *  android.system.OsConstants
 */
package com.android.internal.os;

import android.os.StrictMode;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class KernelCpuSpeedReader {
    private static final String TAG = "KernelCpuSpeedReader";
    private final long[] mDeltaSpeedTimesMs;
    private final long mJiffyMillis;
    private final long[] mLastSpeedTimesMs;
    private final int mNumSpeedSteps;
    private final String mProcFile;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public KernelCpuSpeedReader(int n, int n2) {
        this.mProcFile = String.format("/sys/devices/system/cpu/cpu%d/cpufreq/stats/time_in_state", n);
        this.mNumSpeedSteps = n2;
        this.mLastSpeedTimesMs = new long[n2];
        this.mDeltaSpeedTimesMs = new long[n2];
        this.mJiffyMillis = 1000L / Os.sysconf((int)OsConstants._SC_CLK_TCK);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long[] readAbsolute() {
        block11 : {
            threadPolicy = StrictMode.allowThreadDiskReads();
            arrl = new long[this.mNumSpeedSteps];
            object = new FileReader(this.mProcFile);
            bufferedReader = new BufferedReader((Reader)object);
            try {
                object = new TextUtils.SimpleStringSplitter(' ');
                ** GOTO lbl17
            }
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    KernelCpuSpeedReader.$closeResource(throwable2, bufferedReader);
                    throw throwable3;
lbl17: // 2 sources:
                    for (i = 0; i < this.mNumSpeedSteps && (string2 = bufferedReader.readLine()) != null; ++i) {
                        object.setString(string2);
                        object.next();
                        arrl[i] = Long.parseLong((String)object.next()) * this.mJiffyMillis;
                    }
                    try {
                        KernelCpuSpeedReader.$closeResource(null, bufferedReader);
                    }
                    catch (Throwable throwable4) {
                        break block11;
                    }
                    catch (IOException iOException) {
                        object = new StringBuilder();
                        object.append("Failed to read cpu-freq: ");
                        object.append(iOException.getMessage());
                        Slog.e("KernelCpuSpeedReader", object.toString());
                        Arrays.fill(arrl, 0L);
                    }
                }
            }
            StrictMode.setThreadPolicy(threadPolicy);
            return arrl;
        }
        StrictMode.setThreadPolicy(threadPolicy);
        throw throwable4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long[] readDelta() {
        block11 : {
            threadPolicy = StrictMode.allowThreadDiskReads();
            object = new FileReader(this.mProcFile);
            object2 = new BufferedReader((Reader)object);
            try {
                simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
                ** GOTO lbl16
            }
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    KernelCpuSpeedReader.$closeResource(throwable2, (AutoCloseable)object2);
                    throw throwable3;
lbl16: // 2 sources:
                    for (i = 0; i < this.mLastSpeedTimesMs.length && (object = object2.readLine()) != null; ++i) {
                        simpleStringSplitter.setString((String)object);
                        simpleStringSplitter.next();
                        l = Long.parseLong((String)simpleStringSplitter.next()) * this.mJiffyMillis;
                        this.mDeltaSpeedTimesMs[i] = l < this.mLastSpeedTimesMs[i] ? l : l - this.mLastSpeedTimesMs[i];
                        this.mLastSpeedTimesMs[i] = l;
                    }
                    try {
                        KernelCpuSpeedReader.$closeResource(null, (AutoCloseable)object2);
                    }
                    catch (Throwable throwable4) {
                        break block11;
                    }
                    catch (IOException iOException) {
                        object2 = new StringBuilder();
                        object2.append("Failed to read cpu-freq: ");
                        object2.append(iOException.getMessage());
                        Slog.e("KernelCpuSpeedReader", object2.toString());
                        Arrays.fill(this.mDeltaSpeedTimesMs, 0L);
                    }
                }
            }
            StrictMode.setThreadPolicy(threadPolicy);
            return this.mDeltaSpeedTimesMs;
        }
        StrictMode.setThreadPolicy(threadPolicy);
        throw throwable4;
    }
}

