/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.concurrent.-$
 *  java.util.concurrent.-$$Lambda
 *  java.util.concurrent.-$$Lambda$ConcurrentSkipListMap
 *  java.util.concurrent.-$$Lambda$ConcurrentSkipListMap$EntrySpliterator
 *  java.util.concurrent.-$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o
 */
package java.util.concurrent;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.-$;
import java.util.concurrent.ConcurrentSkipListMap;

public final class _$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o
implements Comparator,
Serializable {
    public static final /* synthetic */ -$.Lambda.ConcurrentSkipListMap.EntrySpliterator.y0KdhWWpZC4eKUM6bCtPBgl2u2o INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o();
    }

    private /* synthetic */ _$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o() {
    }

    public final int compare(Object object, Object object2) {
        return ConcurrentSkipListMap.EntrySpliterator.lambda$getComparator$d5a01062$1((Map.Entry)object, (Map.Entry)object2);
    }
}

