/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$ActivityThread
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc
 */
package android.app;

import android.app.-$;
import android.app.ActivityThread;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.RemoteCallback;
import com.android.internal.util.function.HexConsumer;

public final class _$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc
implements HexConsumer {
    public static final /* synthetic */ -$.Lambda.ActivityThread.ApplicationThread.nBC_BR7B9W6ftKAxur3BC53SJYc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc();
    }

    private /* synthetic */ _$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        ActivityThread.ApplicationThread.lambda$performDirectAction$2((ActivityThread)object, (IBinder)object2, (String)object3, (Bundle)object4, (CancellationSignal)object5, (RemoteCallback)object6);
    }
}

