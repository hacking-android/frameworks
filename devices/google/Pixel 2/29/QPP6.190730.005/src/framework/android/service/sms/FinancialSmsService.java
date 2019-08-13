/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.sms.-$
 *  android.service.sms.-$$Lambda
 *  android.service.sms.-$$Lambda$FinancialSmsService
 *  android.service.sms.-$$Lambda$FinancialSmsService$FinancialSmsServiceWrapper
 *  android.service.sms.-$$Lambda$FinancialSmsService$FinancialSmsServiceWrapper$XFtzKfY0m01I8W-d6pG7NlmdfiQ
 */
package android.service.sms;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.sms.-$;
import android.service.sms.IFinancialSmsService;
import android.service.sms._$$Lambda$FinancialSmsService$FinancialSmsServiceWrapper$XFtzKfY0m01I8W_d6pG7NlmdfiQ;
import com.android.internal.util.function.pooled.PooledLambda;

@SystemApi
public abstract class FinancialSmsService
extends Service {
    public static final String ACTION_FINANCIAL_SERVICE_INTENT = "android.service.sms.action.FINANCIAL_SERVICE_INTENT";
    public static final String EXTRA_SMS_MSGS = "sms_messages";
    private static final String TAG = "FinancialSmsService";
    private final Handler mHandler = new Handler(Looper.getMainLooper(), null, true);
    private FinancialSmsServiceWrapper mWrapper;

    private void getSmsMessages(RemoteCallback remoteCallback, Bundle parcelable) {
        Bundle bundle = new Bundle();
        if ((parcelable = this.onGetSmsMessages((Bundle)parcelable)) != null) {
            bundle.putParcelable(EXTRA_SMS_MSGS, parcelable);
        }
        remoteCallback.sendResult(bundle);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new FinancialSmsServiceWrapper();
    }

    @SystemApi
    public abstract CursorWindow onGetSmsMessages(Bundle var1);

    private final class FinancialSmsServiceWrapper
    extends IFinancialSmsService.Stub {
        private FinancialSmsServiceWrapper() {
        }

        static /* synthetic */ void lambda$getSmsMessages$0(Object object, RemoteCallback remoteCallback, Bundle bundle) {
            ((FinancialSmsService)object).getSmsMessages(remoteCallback, bundle);
        }

        @Override
        public void getSmsMessages(RemoteCallback remoteCallback, Bundle bundle) throws RemoteException {
            FinancialSmsService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$FinancialSmsService$FinancialSmsServiceWrapper$XFtzKfY0m01I8W_d6pG7NlmdfiQ.INSTANCE, FinancialSmsService.this, remoteCallback, bundle));
        }
    }

}

