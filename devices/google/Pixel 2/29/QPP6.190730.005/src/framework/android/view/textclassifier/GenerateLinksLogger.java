/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$GenerateLinksLogger
 *  android.view.textclassifier.-$$Lambda$GenerateLinksLogger$vmbT_h7MLlbrIm0lJJwA-eHQhXk
 */
package android.view.textclassifier;

import android.metrics.LogMaker;
import android.util.ArrayMap;
import android.view.textclassifier.-$;
import android.view.textclassifier.Log;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier._$$Lambda$GenerateLinksLogger$vmbT_h7MLlbrIm0lJJwA_eHQhXk;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.Preconditions;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class GenerateLinksLogger {
    private static final String LOG_TAG = "GenerateLinksLogger";
    private static final String ZERO = "0";
    private final MetricsLogger mMetricsLogger;
    private final Random mRng;
    private final int mSampleRate;

    public GenerateLinksLogger(int n) {
        this.mSampleRate = n;
        this.mRng = new Random(System.nanoTime());
        this.mMetricsLogger = new MetricsLogger();
    }

    @VisibleForTesting
    public GenerateLinksLogger(int n, MetricsLogger metricsLogger) {
        this.mSampleRate = n;
        this.mRng = new Random(System.nanoTime());
        this.mMetricsLogger = metricsLogger;
    }

    private static void debugLog(LogMaker logMaker) {
        if (!Log.ENABLE_FULL_LOGGING) {
            return;
        }
        String string2 = Objects.toString(logMaker.getTaggedData(1319), "");
        String string3 = Objects.toString(logMaker.getTaggedData(1318), "ANY_ENTITY");
        int n = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1316), ZERO));
        int n2 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1317), ZERO));
        int n3 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1315), ZERO));
        int n4 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1314), ZERO));
        Log.v(LOG_TAG, String.format(Locale.US, "%s:%s %d links (%d/%d chars) %dms %s", string2, string3, n, n2, n3, n4, logMaker.getPackageName()));
    }

    static /* synthetic */ LinkifyStats lambda$logGenerateLinks$0(String string2) {
        return new LinkifyStats();
    }

    private boolean shouldLog() {
        int n = this.mSampleRate;
        boolean bl = true;
        if (n <= 1) {
            return true;
        }
        if (this.mRng.nextInt(n) != 0) {
            bl = false;
        }
        return bl;
    }

    private void writeStats(String object, String string2, String string3, LinkifyStats linkifyStats, CharSequence charSequence, long l) {
        object = new LogMaker(1313).setPackageName(string2).addTaggedData(1319, object).addTaggedData(1316, linkifyStats.mNumLinks).addTaggedData(1317, linkifyStats.mNumLinksTextLength).addTaggedData(1315, charSequence.length()).addTaggedData(1314, l);
        if (string3 != null) {
            ((LogMaker)object).addTaggedData(1318, string3);
        }
        this.mMetricsLogger.write((LogMaker)object);
        GenerateLinksLogger.debugLog((LogMaker)object);
    }

    public void logGenerateLinks(CharSequence charSequence, TextLinks object, String string2, long l) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(string2);
        if (!this.shouldLog()) {
            return;
        }
        LinkifyStats object22 = new LinkifyStats();
        ArrayMap arrayMap = new ArrayMap();
        for (TextLinks.TextLink textLink : ((TextLinks)object).getLinks()) {
            if (textLink.getEntityCount() == 0 || (object = textLink.getEntity(0)) == null || "other".equals(object) || "".equals(object)) continue;
            object22.countLink(textLink);
            ((LinkifyStats)arrayMap.computeIfAbsent(object, _$$Lambda$GenerateLinksLogger$vmbT_h7MLlbrIm0lJJwA_eHQhXk.INSTANCE)).countLink(textLink);
        }
        object = UUID.randomUUID().toString();
        this.writeStats((String)object, string2, null, object22, charSequence, l);
        for (Map.Entry entry : arrayMap.entrySet()) {
            this.writeStats((String)object, string2, (String)entry.getKey(), (LinkifyStats)entry.getValue(), charSequence, l);
        }
    }

    private static final class LinkifyStats {
        int mNumLinks;
        int mNumLinksTextLength;

        private LinkifyStats() {
        }

        void countLink(TextLinks.TextLink textLink) {
            ++this.mNumLinks;
            this.mNumLinksTextLength += textLink.getEnd() - textLink.getStart();
        }
    }

}

