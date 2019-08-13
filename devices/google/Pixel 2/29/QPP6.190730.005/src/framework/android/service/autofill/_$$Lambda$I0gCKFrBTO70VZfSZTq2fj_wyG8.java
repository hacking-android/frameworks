/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.-$
 *  android.service.autofill.-$$Lambda
 *  android.service.autofill.-$$Lambda$I0gCKFrBTO70VZfSZTq2fj-wyG8
 */
package android.service.autofill;

import android.os.CancellationSignal;
import android.service.autofill.-$;
import android.service.autofill.AutofillService;
import android.service.autofill.FillCallback;
import android.service.autofill.FillRequest;
import com.android.internal.util.function.QuadConsumer;

public final class _$$Lambda$I0gCKFrBTO70VZfSZTq2fj_wyG8
implements QuadConsumer {
    public static final /* synthetic */ -$.Lambda.I0gCKFrBTO70VZfSZTq2fj-wyG8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$I0gCKFrBTO70VZfSZTq2fj_wyG8();
    }

    private /* synthetic */ _$$Lambda$I0gCKFrBTO70VZfSZTq2fj_wyG8() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4) {
        ((AutofillService)object).onFillRequest((FillRequest)object2, (CancellationSignal)object3, (FillCallback)object4);
    }
}

