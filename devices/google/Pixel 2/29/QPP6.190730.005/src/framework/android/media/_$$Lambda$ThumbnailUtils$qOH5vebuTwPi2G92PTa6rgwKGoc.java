/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$ThumbnailUtils
 *  android.media.-$$Lambda$ThumbnailUtils$qOH5vebuTwPi2G92PTa6rgwKGoc
 */
package android.media;

import android.media.-$;
import android.media.ThumbnailUtils;
import java.io.File;
import java.util.function.ToIntFunction;

public final class _$$Lambda$ThumbnailUtils$qOH5vebuTwPi2G92PTa6rgwKGoc
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.ThumbnailUtils.qOH5vebuTwPi2G92PTa6rgwKGoc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ThumbnailUtils$qOH5vebuTwPi2G92PTa6rgwKGoc();
    }

    private /* synthetic */ _$$Lambda$ThumbnailUtils$qOH5vebuTwPi2G92PTa6rgwKGoc() {
    }

    public final int applyAsInt(Object object) {
        return ThumbnailUtils.lambda$createAudioThumbnail$1((File)object);
    }
}

