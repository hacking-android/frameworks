/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.widget.-$
 *  com.android.internal.widget.-$$Lambda
 *  com.android.internal.widget.-$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10
 */
package com.android.internal.widget;

import com.android.internal.widget.-$;
import com.android.internal.widget.MessagingMessage;
import java.util.function.Consumer;

public final class _$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10
implements Consumer {
    public static final /* synthetic */ -$.Lambda.DKD2sNhLnyRFoBkFvfwKyxoEx10 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10();
    }

    private /* synthetic */ _$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10() {
    }

    public final void accept(Object object) {
        ((MessagingMessage)object).removeMessage();
    }
}

