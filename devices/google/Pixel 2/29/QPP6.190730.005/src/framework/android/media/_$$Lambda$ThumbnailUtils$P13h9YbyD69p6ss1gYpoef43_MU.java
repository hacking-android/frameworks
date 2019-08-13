/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$ThumbnailUtils
 *  android.media.-$$Lambda$ThumbnailUtils$P13h9YbyD69p6ss1gYpoef43_MU
 */
package android.media;

import android.media.-$;
import android.media.ThumbnailUtils;
import java.io.File;
import java.io.FilenameFilter;

public final class _$$Lambda$ThumbnailUtils$P13h9YbyD69p6ss1gYpoef43_MU
implements FilenameFilter {
    public static final /* synthetic */ -$.Lambda.ThumbnailUtils.P13h9YbyD69p6ss1gYpoef43_MU INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ThumbnailUtils$P13h9YbyD69p6ss1gYpoef43_MU();
    }

    private /* synthetic */ _$$Lambda$ThumbnailUtils$P13h9YbyD69p6ss1gYpoef43_MU() {
    }

    @Override
    public final boolean accept(File file, String string2) {
        return ThumbnailUtils.lambda$createAudioThumbnail$0(file, string2);
    }
}

