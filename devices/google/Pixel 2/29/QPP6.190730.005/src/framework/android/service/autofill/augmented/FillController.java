/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.service.autofill.augmented.FillWindow;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.Preconditions;
import java.util.List;

@SystemApi
public final class FillController {
    private static final String TAG = FillController.class.getSimpleName();
    private final AugmentedAutofillService.AutofillProxy mProxy;

    FillController(AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public void autofill(List<Pair<AutofillId, AutofillValue>> object) {
        block4 : {
            Preconditions.checkNotNull(object);
            if (AugmentedAutofillService.sDebug) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("autofill() with ");
                stringBuilder.append(object.size());
                stringBuilder.append(" values");
                Log.d(string2, stringBuilder.toString());
            }
            this.mProxy.autofill((List<Pair<AutofillId, AutofillValue>>)object);
            object = this.mProxy.getFillWindow();
            if (object == null) break block4;
            try {
                ((FillWindow)object).destroy();
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowAsRuntimeException();
            }
        }
    }
}

