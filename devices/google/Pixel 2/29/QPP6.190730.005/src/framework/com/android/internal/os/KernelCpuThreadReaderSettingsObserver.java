/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Range;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.KernelCpuThreadReader;
import com.android.internal.os.KernelCpuThreadReaderDiff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KernelCpuThreadReaderSettingsObserver
extends ContentObserver {
    private static final String COLLECTED_UIDS_DEFAULT = "0-0;1000-1000";
    private static final String COLLECTED_UIDS_SETTINGS_KEY = "collected_uids";
    private static final int MINIMUM_TOTAL_CPU_USAGE_MILLIS_DEFAULT = 10000;
    private static final String MINIMUM_TOTAL_CPU_USAGE_MILLIS_SETTINGS_KEY = "minimum_total_cpu_usage_millis";
    private static final int NUM_BUCKETS_DEFAULT = 8;
    private static final String NUM_BUCKETS_SETTINGS_KEY = "num_buckets";
    private static final String TAG = "KernelCpuThreadReaderSettingsObserver";
    private final Context mContext;
    private final KernelCpuThreadReader mKernelCpuThreadReader;
    private final KernelCpuThreadReaderDiff mKernelCpuThreadReaderDiff;

    private KernelCpuThreadReaderSettingsObserver(Context context) {
        super(BackgroundThread.getHandler());
        this.mContext = context;
        this.mKernelCpuThreadReader = KernelCpuThreadReader.create(8, UidPredicate.fromString(COLLECTED_UIDS_DEFAULT));
        this.mKernelCpuThreadReaderDiff = new KernelCpuThreadReaderDiff(this.mKernelCpuThreadReader, 10000);
    }

    public static KernelCpuThreadReaderDiff getSettingsModifiedReader(Context context) {
        KernelCpuThreadReaderSettingsObserver kernelCpuThreadReaderSettingsObserver = new KernelCpuThreadReaderSettingsObserver(context);
        Uri uri = Settings.Global.getUriFor("kernel_cpu_thread_reader");
        context.getContentResolver().registerContentObserver(uri, false, kernelCpuThreadReaderSettingsObserver, 0);
        return kernelCpuThreadReaderSettingsObserver.mKernelCpuThreadReaderDiff;
    }

    private void updateReader() {
        UidPredicate uidPredicate;
        if (this.mKernelCpuThreadReader == null) {
            return;
        }
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(Settings.Global.getString(this.mContext.getContentResolver(), "kernel_cpu_thread_reader"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Slog.e(TAG, "Bad settings", illegalArgumentException);
            return;
        }
        try {
            uidPredicate = UidPredicate.fromString(keyValueListParser.getString(COLLECTED_UIDS_SETTINGS_KEY, COLLECTED_UIDS_DEFAULT));
        }
        catch (NumberFormatException numberFormatException) {
            Slog.w(TAG, "Failed to get UID predicate", numberFormatException);
            return;
        }
        this.mKernelCpuThreadReader.setNumBuckets(keyValueListParser.getInt(NUM_BUCKETS_SETTINGS_KEY, 8));
        this.mKernelCpuThreadReader.setUidPredicate(uidPredicate);
        this.mKernelCpuThreadReaderDiff.setMinimumTotalCpuUsageMillis(keyValueListParser.getInt(MINIMUM_TOTAL_CPU_USAGE_MILLIS_SETTINGS_KEY, 10000));
    }

    @Override
    public void onChange(boolean bl, Uri uri, int n) {
        this.updateReader();
    }

    @VisibleForTesting
    public static class UidPredicate
    implements Predicate<Integer> {
        private static final Pattern UID_RANGE_PATTERN = Pattern.compile("([0-9]+)-([0-9]+)");
        private static final String UID_SPECIFIER_DELIMITER = ";";
        private final List<Range<Integer>> mAcceptedUidRanges;

        private UidPredicate(List<Range<Integer>> list) {
            this.mAcceptedUidRanges = list;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public static UidPredicate fromString(String string22) throws NumberFormatException {
            Serializable serializable = new ArrayList<Range<Integer>>();
            for (String string22 : string22.split(UID_SPECIFIER_DELIMITER)) {
                Matcher matcher = UID_RANGE_PATTERN.matcher(string22);
                if (matcher.matches()) {
                    serializable.add(Range.create(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
                    continue;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Failed to recognize as number range: ");
                ((StringBuilder)serializable).append(string22);
                throw new NumberFormatException(((StringBuilder)serializable).toString());
            }
            return new UidPredicate((List<Range<Integer>>)((Object)serializable));
        }

        @Override
        public boolean test(Integer n) {
            for (int i = 0; i < this.mAcceptedUidRanges.size(); ++i) {
                if (!this.mAcceptedUidRanges.get(i).contains(n)) continue;
                return true;
            }
            return false;
        }
    }

}

