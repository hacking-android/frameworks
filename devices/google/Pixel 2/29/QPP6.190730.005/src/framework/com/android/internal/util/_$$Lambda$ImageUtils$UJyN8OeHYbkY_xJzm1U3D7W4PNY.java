/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ContentProviderClient;
import android.net.Uri;
import android.os.Bundle;
import com.android.internal.util.ImageUtils;
import java.util.concurrent.Callable;

public final class _$$Lambda$ImageUtils$UJyN8OeHYbkY_xJzm1U3D7W4PNY
implements Callable {
    private final /* synthetic */ ContentProviderClient f$0;
    private final /* synthetic */ Uri f$1;
    private final /* synthetic */ Bundle f$2;

    public /* synthetic */ _$$Lambda$ImageUtils$UJyN8OeHYbkY_xJzm1U3D7W4PNY(ContentProviderClient contentProviderClient, Uri uri, Bundle bundle) {
        this.f$0 = contentProviderClient;
        this.f$1 = uri;
        this.f$2 = bundle;
    }

    public final Object call() {
        return ImageUtils.lambda$loadThumbnail$0(this.f$0, this.f$1, this.f$2);
    }
}

