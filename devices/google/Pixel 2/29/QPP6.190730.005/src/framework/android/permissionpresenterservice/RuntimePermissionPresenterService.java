/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.permissionpresenterservice.-$
 *  android.permissionpresenterservice.-$$Lambda
 *  android.permissionpresenterservice.-$$Lambda$RuntimePermissionPresenterService
 *  android.permissionpresenterservice.-$$Lambda$RuntimePermissionPresenterService$1
 *  android.permissionpresenterservice.-$$Lambda$RuntimePermissionPresenterService$1$hIxcH5_fyEVhEY0Z-wjDuhvJriA
 */
package android.permissionpresenterservice;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.permission.IRuntimePermissionPresenter;
import android.content.pm.permission.RuntimePermissionPresentationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.permissionpresenterservice.-$;
import android.permissionpresenterservice._$$Lambda$RuntimePermissionPresenterService$1$hIxcH5_fyEVhEY0Z_wjDuhvJriA;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.List;

@SystemApi
@Deprecated
public abstract class RuntimePermissionPresenterService
extends Service {
    private static final String KEY_RESULT = "android.content.pm.permission.RuntimePermissionPresenter.key.result";
    public static final String SERVICE_INTERFACE = "android.permissionpresenterservice.RuntimePermissionPresenterService";
    private Handler mHandler;

    private void getAppPermissions(String object, RemoteCallback remoteCallback) {
        if ((object = this.onGetAppPermissions((String)object)) != null && !object.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putParcelableList(KEY_RESULT, (List<? extends Parcelable>)object);
            remoteCallback.sendResult(bundle);
        } else {
            remoteCallback.sendResult(null);
        }
    }

    @Override
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new IRuntimePermissionPresenter.Stub(){

            static /* synthetic */ void lambda$getAppPermissions$0(Object object, String string2, RemoteCallback remoteCallback) {
                ((RuntimePermissionPresenterService)object).getAppPermissions(string2, remoteCallback);
            }

            @Override
            public void getAppPermissions(String string2, RemoteCallback remoteCallback) {
                Preconditions.checkNotNull(string2, "packageName");
                Preconditions.checkNotNull(remoteCallback, "callback");
                RuntimePermissionPresenterService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$RuntimePermissionPresenterService$1$hIxcH5_fyEVhEY0Z_wjDuhvJriA.INSTANCE, RuntimePermissionPresenterService.this, string2, remoteCallback));
            }
        };
    }

    public abstract List<RuntimePermissionPresentationInfo> onGetAppPermissions(String var1);

}

