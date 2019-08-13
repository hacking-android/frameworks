/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.-$
 *  android.service.autofill.-$$Lambda
 *  android.service.autofill.-$$Lambda$KrOZIsyY-3lh3prHWFldsWopHBw
 */
package android.service.autofill;

import android.service.autofill.-$;
import android.service.autofill.AutofillService;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$KrOZIsyY_3lh3prHWFldsWopHBw
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.KrOZIsyY-3lh3prHWFldsWopHBw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$KrOZIsyY_3lh3prHWFldsWopHBw();
    }

    private /* synthetic */ _$$Lambda$KrOZIsyY_3lh3prHWFldsWopHBw() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((AutofillService)object).onSaveRequest((SaveRequest)object2, (SaveCallback)object3);
    }
}

