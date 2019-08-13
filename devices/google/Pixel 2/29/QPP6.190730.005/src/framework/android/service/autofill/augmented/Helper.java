/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.content.ComponentName;
import android.metrics.LogMaker;
import android.util.SparseArray;
import com.android.internal.logging.MetricsLogger;
import java.io.PrintStream;

public final class Helper {
    private static final MetricsLogger sMetricsLogger = new MetricsLogger();

    private Helper() {
        throw new UnsupportedOperationException("contains only static methods");
    }

    public static void logResponse(int n, String charSequence, ComponentName object, int n2, long l) {
        LogMaker logMaker = new LogMaker(1724).setType(n).setComponentName((ComponentName)object).addTaggedData(1456, n2).addTaggedData(908, charSequence).addTaggedData(1145, l);
        object = System.out;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("LOGGGO: ");
        ((StringBuilder)charSequence).append(logMaker.getEntries());
        ((PrintStream)object).println(((StringBuilder)charSequence).toString());
        sMetricsLogger.write(logMaker);
    }
}

