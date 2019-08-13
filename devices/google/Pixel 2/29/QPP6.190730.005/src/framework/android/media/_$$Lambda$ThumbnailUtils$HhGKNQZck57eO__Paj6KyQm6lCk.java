/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.ThumbnailUtils;
import java.io.File;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public final class _$$Lambda$ThumbnailUtils$HhGKNQZck57eO__Paj6KyQm6lCk
implements Comparator {
    private final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ _$$Lambda$ThumbnailUtils$HhGKNQZck57eO__Paj6KyQm6lCk(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    public final int compare(Object object, Object object2) {
        return ThumbnailUtils.lambda$createAudioThumbnail$2(this.f$0, (File)object, (File)object2);
    }
}

