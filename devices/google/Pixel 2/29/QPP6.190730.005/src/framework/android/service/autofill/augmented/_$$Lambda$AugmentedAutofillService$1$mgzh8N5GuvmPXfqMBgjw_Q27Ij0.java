/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.augmented.-$
 *  android.service.autofill.augmented.-$$Lambda
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw-Q27Ij0
 */
package android.service.autofill.augmented;

import android.content.ComponentName;
import android.os.IBinder;
import android.service.autofill.augmented.-$;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.service.autofill.augmented.IFillCallback;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.function.NonaConsumer;

public final class _$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw_Q27Ij0
implements NonaConsumer {
    public static final /* synthetic */ -$.Lambda.AugmentedAutofillService.1.mgzh8N5GuvmPXfqMBgjw-Q27Ij0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw_Q27Ij0();
    }

    private /* synthetic */ _$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw_Q27Ij0() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        AugmentedAutofillService.1.lambda$onFillRequest$2((AugmentedAutofillService)object, (Integer)object2, (IBinder)object3, (Integer)object4, (ComponentName)object5, (AutofillId)object6, (AutofillValue)object7, (Long)object8, (IFillCallback)object9);
    }
}

