/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$ActivityThread
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread$uR_ee-5oPoxu4U_by7wU55jwtdU
 */
package android.app;

import android.app.-$;
import android.app.ActivityThread;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.RemoteCallback;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.util.function.QuintConsumer;

public final class _$$Lambda$ActivityThread$ApplicationThread$uR_ee_5oPoxu4U_by7wU55jwtdU
implements QuintConsumer {
    public static final /* synthetic */ -$.Lambda.ActivityThread.ApplicationThread.uR_ee-5oPoxu4U_by7wU55jwtdU INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ActivityThread$ApplicationThread$uR_ee_5oPoxu4U_by7wU55jwtdU();
    }

    private /* synthetic */ _$$Lambda$ActivityThread$ApplicationThread$uR_ee_5oPoxu4U_by7wU55jwtdU() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4, Object object5) {
        ActivityThread.ApplicationThread.lambda$requestDirectActions$1((ActivityThread)object, (IBinder)object2, (IVoiceInteractor)object3, (CancellationSignal)object4, (RemoteCallback)object5);
    }
}

