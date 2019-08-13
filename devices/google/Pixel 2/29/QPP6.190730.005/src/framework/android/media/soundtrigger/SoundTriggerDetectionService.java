/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.soundtrigger.-$
 *  android.media.soundtrigger.-$$Lambda
 *  android.media.soundtrigger.-$$Lambda$ISQYIYPBRBIOLBUJy7rrJW-SiJg
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService$1
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw
 *  android.media.soundtrigger.-$$Lambda$SoundTriggerDetectionService$1$pKR4r0FzOzoVczcnvLQIZNjkZZw
 *  android.media.soundtrigger.-$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ
 *  android.media.soundtrigger.-$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY
 */
package android.media.soundtrigger;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.-$;
import android.media.soundtrigger.ISoundTriggerDetectionService;
import android.media.soundtrigger.ISoundTriggerDetectionServiceClient;
import android.media.soundtrigger._$$Lambda$ISQYIYPBRBIOLBUJy7rrJW_SiJg;
import android.media.soundtrigger._$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw;
import android.media.soundtrigger._$$Lambda$SoundTriggerDetectionService$1$pKR4r0FzOzoVczcnvLQIZNjkZZw;
import android.media.soundtrigger._$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ;
import android.media.soundtrigger._$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.UUID;

@SystemApi
public abstract class SoundTriggerDetectionService
extends Service {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = SoundTriggerDetectionService.class.getSimpleName();
    @GuardedBy(value={"mLock"})
    private final ArrayMap<UUID, ISoundTriggerDetectionServiceClient> mClients = new ArrayMap();
    private Handler mHandler;
    private final Object mLock = new Object();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeClient(UUID uUID, Bundle bundle) {
        Object object = this.mLock;
        synchronized (object) {
            this.mClients.remove(uUID);
        }
        this.onDisconnected(uUID, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setClient(UUID uUID, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) {
        Object object = this.mLock;
        synchronized (object) {
            this.mClients.put(uUID, iSoundTriggerDetectionServiceClient);
        }
        this.onConnected(uUID, bundle);
    }

    @Override
    protected final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new ISoundTriggerDetectionService.Stub(){
            private final Object mBinderLock = new Object();
            @GuardedBy(value={"mBinderLock"})
            public final ArrayMap<UUID, Bundle> mParams = new ArrayMap();

            static /* synthetic */ void lambda$removeClient$1(Object object, UUID uUID, Bundle bundle) {
                ((SoundTriggerDetectionService)object).removeClient(uUID, bundle);
            }

            static /* synthetic */ void lambda$setClient$0(Object object, UUID uUID, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) {
                ((SoundTriggerDetectionService)object).setClient(uUID, bundle, iSoundTriggerDetectionServiceClient);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onError(ParcelUuid object, int n, int n2) {
                Bundle bundle;
                UUID uUID = ((ParcelUuid)object).getUuid();
                object = this.mBinderLock;
                synchronized (object) {
                    bundle = this.mParams.get(uUID);
                }
                SoundTriggerDetectionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$oNgT3sYhSGVWlnU92bECo_ULGeY.INSTANCE, SoundTriggerDetectionService.this, uUID, bundle, n, n2));
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onGenericRecognitionEvent(ParcelUuid object, int n, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
                Bundle bundle;
                UUID uUID = ((ParcelUuid)object).getUuid();
                object = this.mBinderLock;
                synchronized (object) {
                    bundle = this.mParams.get(uUID);
                }
                SoundTriggerDetectionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ISQYIYPBRBIOLBUJy7rrJW_SiJg.INSTANCE, SoundTriggerDetectionService.this, uUID, bundle, n, genericRecognitionEvent));
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onStopOperation(ParcelUuid object, int n) {
                Bundle bundle;
                UUID uUID = ((ParcelUuid)object).getUuid();
                object = this.mBinderLock;
                synchronized (object) {
                    bundle = this.mParams.get(uUID);
                }
                SoundTriggerDetectionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$bPGNpvkCtpPW14oaI3pxn1e6JtQ.INSTANCE, SoundTriggerDetectionService.this, uUID, bundle, n));
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeClient(ParcelUuid object) {
                Bundle bundle;
                UUID uUID = ((ParcelUuid)object).getUuid();
                object = this.mBinderLock;
                synchronized (object) {
                    bundle = this.mParams.remove(uUID);
                }
                SoundTriggerDetectionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$SoundTriggerDetectionService$1$pKR4r0FzOzoVczcnvLQIZNjkZZw.INSTANCE, SoundTriggerDetectionService.this, uUID, bundle));
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setClient(ParcelUuid object, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) {
                UUID uUID = ((ParcelUuid)object).getUuid();
                object = this.mBinderLock;
                synchronized (object) {
                    this.mParams.put(uUID, bundle);
                }
                SoundTriggerDetectionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$SoundTriggerDetectionService$1$LlOo7TiZplZCgGhS07DqYHocFcw.INSTANCE, SoundTriggerDetectionService.this, uUID, bundle, iSoundTriggerDetectionServiceClient));
            }
        };
    }

    public void onConnected(UUID uUID, Bundle bundle) {
    }

    public void onDisconnected(UUID uUID, Bundle bundle) {
    }

    public void onError(UUID uUID, Bundle bundle, int n, int n2) {
        this.operationFinished(uUID, n);
    }

    public void onGenericRecognitionEvent(UUID uUID, Bundle bundle, int n, SoundTrigger.RecognitionEvent recognitionEvent) {
        this.operationFinished(uUID, n);
    }

    public abstract void onStopOperation(UUID var1, Bundle var2, int var3);

    @Override
    public boolean onUnbind(Intent intent) {
        this.mClients.clear();
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void operationFinished(UUID uUID, int n) {
        Object object;
        Object object2;
        block7 : {
            object2 = this.mLock;
            // MONITORENTER : object2
            object = this.mClients.get(uUID);
            if (object != null) break block7;
            String string2 = LOG_TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("operationFinished called, but no client for ");
            ((StringBuilder)object).append(uUID);
            ((StringBuilder)object).append(". Was this called after onDisconnected?");
            Log.w(string2, ((StringBuilder)object).toString());
            // MONITOREXIT : object2
            return;
        }
        // MONITOREXIT : object2
        try {
            object.onOpFinished(n);
            return;
        }
        catch (RemoteException remoteException) {
            String string3 = LOG_TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("operationFinished, remote exception for client ");
            ((StringBuilder)object2).append(uUID);
            Log.e(string3, ((StringBuilder)object2).toString(), remoteException);
        }
    }

}

