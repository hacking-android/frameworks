/*
 * Decompiled with CFR 0.145.
 */
package android.service.chooser;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.chooser.ChooserTarget;
import android.service.chooser.IChooserTargetResult;
import android.service.chooser.IChooserTargetService;
import java.util.List;

public abstract class ChooserTargetService
extends Service {
    public static final String BIND_PERMISSION = "android.permission.BIND_CHOOSER_TARGET_SERVICE";
    private static final boolean DEBUG = false;
    public static final String META_DATA_NAME = "android.service.chooser.chooser_target_service";
    public static final String SERVICE_INTERFACE = "android.service.chooser.ChooserTargetService";
    private final String TAG;
    private IChooserTargetServiceWrapper mWrapper;

    public ChooserTargetService() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ChooserTargetService.class.getSimpleName());
        stringBuilder.append('[');
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(']');
        this.TAG = stringBuilder.toString();
        this.mWrapper = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (this.mWrapper == null) {
            this.mWrapper = new IChooserTargetServiceWrapper();
        }
        return this.mWrapper;
    }

    public abstract List<ChooserTarget> onGetChooserTargets(ComponentName var1, IntentFilter var2);

    private class IChooserTargetServiceWrapper
    extends IChooserTargetService.Stub {
        private IChooserTargetServiceWrapper() {
        }

        @Override
        public void getChooserTargets(ComponentName object, IntentFilter intentFilter, IChooserTargetResult iChooserTargetResult) throws RemoteException {
            long l = IChooserTargetServiceWrapper.clearCallingIdentity();
            try {
                object = ChooserTargetService.this.onGetChooserTargets((ComponentName)object, intentFilter);
            }
            catch (Throwable throwable) {
                IChooserTargetServiceWrapper.restoreCallingIdentity(l);
                iChooserTargetResult.sendResult(null);
                throw throwable;
            }
            IChooserTargetServiceWrapper.restoreCallingIdentity(l);
            iChooserTargetResult.sendResult((List<ChooserTarget>)object);
        }
    }

}

