/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.service.carrier.ICarrierMessagingService
 *  android.service.carrier.ICarrierMessagingService$Stub
 *  com.android.internal.util.Preconditions
 */
package android.telephony;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.service.carrier.ICarrierMessagingService;
import com.android.internal.util.Preconditions;

public abstract class CarrierMessagingServiceManager {
    private volatile CarrierMessagingServiceConnection mCarrierMessagingServiceConnection;

    public boolean bindToCarrierMessagingService(Context context, String string) {
        boolean bl = this.mCarrierMessagingServiceConnection == null;
        Preconditions.checkState((boolean)bl);
        Intent intent = new Intent("android.service.carrier.CarrierMessagingService");
        intent.setPackage(string);
        this.mCarrierMessagingServiceConnection = new CarrierMessagingServiceConnection();
        return context.bindService(intent, (ServiceConnection)this.mCarrierMessagingServiceConnection, 1);
    }

    public void disposeConnection(Context context) {
        Preconditions.checkNotNull((Object)this.mCarrierMessagingServiceConnection);
        context.unbindService((ServiceConnection)this.mCarrierMessagingServiceConnection);
        this.mCarrierMessagingServiceConnection = null;
    }

    protected abstract void onServiceReady(ICarrierMessagingService var1);

    private final class CarrierMessagingServiceConnection
    implements ServiceConnection {
        private CarrierMessagingServiceConnection() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CarrierMessagingServiceManager.this.onServiceReady(ICarrierMessagingService.Stub.asInterface((IBinder)iBinder));
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

}

