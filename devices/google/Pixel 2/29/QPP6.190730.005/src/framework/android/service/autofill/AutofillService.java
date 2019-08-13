/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.-$
 *  android.service.autofill.-$$Lambda
 *  android.service.autofill.-$$Lambda$I0gCKFrBTO70VZfSZTq2fj-wyG8
 *  android.service.autofill.-$$Lambda$KrOZIsyY-3lh3prHWFldsWopHBw
 *  android.service.autofill.-$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc
 *  android.service.autofill.-$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ
 */
package android.service.autofill;

import android.app.Service;
import android.content.Intent;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.autofill.-$;
import android.service.autofill.FillCallback;
import android.service.autofill.FillEventHistory;
import android.service.autofill.FillRequest;
import android.service.autofill.IAutoFillService;
import android.service.autofill.IFillCallback;
import android.service.autofill.ISaveCallback;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import android.service.autofill._$$Lambda$I0gCKFrBTO70VZfSZTq2fj_wyG8;
import android.service.autofill._$$Lambda$KrOZIsyY_3lh3prHWFldsWopHBw;
import android.service.autofill._$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc;
import android.service.autofill._$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ;
import android.util.Log;
import android.view.autofill.AutofillManager;
import com.android.internal.util.function.pooled.PooledLambda;

public abstract class AutofillService
extends Service {
    public static final String SERVICE_INTERFACE = "android.service.autofill.AutofillService";
    public static final String SERVICE_META_DATA = "android.autofill";
    private static final String TAG = "AutofillService";
    private Handler mHandler;
    private final IAutoFillService mInterface = new IAutoFillService.Stub(){

        @Override
        public void onConnectedStateChanged(boolean bl) {
            Handler handler = AutofillService.this.mHandler;
            Object object = bl ? _$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc.INSTANCE : _$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ.INSTANCE;
            handler.sendMessage(PooledLambda.obtainMessage(object, AutofillService.this));
        }

        @Override
        public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) {
            ICancellationSignal iCancellationSignal = CancellationSignal.createTransport();
            try {
                iFillCallback.onCancellable(iCancellationSignal);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$I0gCKFrBTO70VZfSZTq2fj_wyG8.INSTANCE, AutofillService.this, fillRequest, CancellationSignal.fromTransport(iCancellationSignal), new FillCallback(iFillCallback, fillRequest.getId())));
        }

        @Override
        public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) {
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$KrOZIsyY_3lh3prHWFldsWopHBw.INSTANCE, AutofillService.this, saveRequest, new SaveCallback(iSaveCallback)));
        }
    };

    public final FillEventHistory getFillEventHistory() {
        AutofillManager autofillManager = this.getSystemService(AutofillManager.class);
        if (autofillManager == null) {
            return null;
        }
        return autofillManager.getFillEventHistory();
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tried to bind to wrong intent (should be android.service.autofill.AutofillService: ");
        stringBuilder.append(intent);
        Log.w(TAG, stringBuilder.toString());
        return null;
    }

    public void onConnected() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    public void onDisconnected() {
    }

    public abstract void onFillRequest(FillRequest var1, CancellationSignal var2, FillCallback var3);

    public abstract void onSaveRequest(SaveRequest var1, SaveCallback var2);

}

