/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.StrictMode;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.LongSparseLongArray;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class KernelMemoryBandwidthStats {
    private static final boolean DEBUG = false;
    private static final String TAG = "KernelMemoryBandwidthStats";
    private static final String mSysfsFile = "/sys/kernel/memory_state_time/show_stat";
    protected final LongSparseLongArray mBandwidthEntries = new LongSparseLongArray();
    private boolean mStatsDoNotExist = false;

    public LongSparseLongArray getBandwidthEntries() {
        return this.mBandwidthEntries;
    }

    @VisibleForTesting
    public void parseStats(BufferedReader bufferedReader) throws IOException {
        Object object;
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
        this.mBandwidthEntries.clear();
        while ((object = bufferedReader.readLine()) != null) {
            simpleStringSplitter.setString((String)object);
            simpleStringSplitter.next();
            int n = 0;
            do {
                int n2;
                if ((n2 = this.mBandwidthEntries.indexOfKey(n)) >= 0) {
                    object = this.mBandwidthEntries;
                    ((LongSparseLongArray)object).put(n, ((LongSparseLongArray)object).valueAt(n2) + Long.parseLong((String)simpleStringSplitter.next()) / 1000000L);
                } else {
                    this.mBandwidthEntries.put(n, Long.parseLong((String)simpleStringSplitter.next()) / 1000000L);
                }
                ++n;
            } while (simpleStringSplitter.hasNext());
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void updateStats() {
        Throwable throwable5222;
        Object object;
        block14 : {
            long l;
            block15 : {
                if (this.mStatsDoNotExist) {
                    return;
                }
                l = SystemClock.uptimeMillis();
                object = StrictMode.allowThreadDiskReads();
                Object object2 = new FileReader(mSysfsFile);
                BufferedReader bufferedReader = new BufferedReader((Reader)object2);
                this.parseStats(bufferedReader);
                bufferedReader.close();
                catch (Throwable throwable2) {
                    try {
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            bufferedReader.close();
                            throw throwable3;
                        }
                        catch (Throwable throwable4) {
                            try {
                                throwable2.addSuppressed(throwable4);
                                throw throwable3;
                            }
                            catch (Throwable throwable5222) {
                                break block14;
                            }
                            catch (IOException iOException) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Failed to read memory bandwidth: ");
                                ((StringBuilder)object2).append(iOException.getMessage());
                                Slog.e(TAG, ((StringBuilder)object2).toString());
                                this.mBandwidthEntries.clear();
                                break block15;
                            }
                            catch (FileNotFoundException fileNotFoundException) {
                                Slog.w(TAG, "No kernel memory bandwidth stats available");
                                this.mBandwidthEntries.clear();
                                this.mStatsDoNotExist = true;
                            }
                        }
                    }
                }
            }
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)object);
            l = SystemClock.uptimeMillis() - l;
            if (l <= 100L) return;
            object = new StringBuilder();
            ((StringBuilder)object).append("Reading memory bandwidth file took ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append("ms");
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)object);
        throw throwable5222;
    }
}

