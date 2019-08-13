/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.infra.-$
 *  com.android.internal.infra.-$$Lambda
 *  com.android.internal.infra.-$$Lambda$7-CJJfrUZBVuXZyYFEWBNh8Mky8
 */
package com.android.internal.infra;

import com.android.internal.infra.-$;
import com.android.internal.infra.AbstractRemoteService;
import java.util.function.BiConsumer;

public final class _$$Lambda$7_CJJfrUZBVuXZyYFEWBNh8Mky8
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.7-CJJfrUZBVuXZyYFEWBNh8Mky8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$7_CJJfrUZBVuXZyYFEWBNh8Mky8();
    }

    private /* synthetic */ _$$Lambda$7_CJJfrUZBVuXZyYFEWBNh8Mky8() {
    }

    public final void accept(Object object, Object object2) {
        ((AbstractRemoteService)object).handlePendingRequest((AbstractRemoteService.BasePendingRequest)object2);
    }
}

