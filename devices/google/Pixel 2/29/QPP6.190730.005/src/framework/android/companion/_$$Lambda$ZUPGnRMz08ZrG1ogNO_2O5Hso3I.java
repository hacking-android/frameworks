/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.companion.-$
 *  android.companion.-$$Lambda
 *  android.companion.-$$Lambda$ZUPGnRMz08ZrG1ogNO-2O5Hso3I
 */
package android.companion;

import android.companion.-$;
import android.companion.CompanionDeviceManager;
import java.util.function.BiConsumer;

public final class _$$Lambda$ZUPGnRMz08ZrG1ogNO_2O5Hso3I
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.ZUPGnRMz08ZrG1ogNO-2O5Hso3I INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ZUPGnRMz08ZrG1ogNO_2O5Hso3I();
    }

    private /* synthetic */ _$$Lambda$ZUPGnRMz08ZrG1ogNO_2O5Hso3I() {
    }

    public final void accept(Object object, Object object2) {
        ((CompanionDeviceManager.Callback)object).onFailure((CharSequence)object2);
    }
}

