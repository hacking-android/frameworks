/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import android.telephony.mbms.IStreamingServiceCallback;
import android.telephony.mbms.MbmsStreamingSessionCallback;
import android.telephony.mbms.StreamingServiceCallback;
import android.telephony.mbms.StreamingServiceInfo;
import android.telephony.mbms.vendor.IMbmsStreamingService;
import java.util.List;

@SystemApi
public class MbmsStreamingServiceBase
extends IMbmsStreamingService.Stub {
    @SystemApi
    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    @Override
    public void dispose(int n) throws RemoteException {
    }

    @Override
    public Uri getPlaybackUri(int n, String string2) throws RemoteException {
        return null;
    }

    @Override
    public final int initialize(final IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, final int n) throws RemoteException {
        if (iMbmsStreamingSessionCallback != null) {
            final int n2 = Binder.getCallingUid();
            int n3 = this.initialize(new MbmsStreamingSessionCallback(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void onError(int n3, String object) {
                    if (n3 != -1) {
                        try {
                            iMbmsStreamingSessionCallback.onError(n3, (String)object);
                            return;
                        }
                        catch (RemoteException remoteException) {}
                    } else {
                        object = new IllegalArgumentException("Middleware cannot send an unknown error.");
                        throw object;
                    }
                    MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                }

                @Override
                public void onMiddlewareReady() {
                    try {
                        iMbmsStreamingSessionCallback.onMiddlewareReady();
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }

                @Override
                public void onStreamingServicesUpdated(List<StreamingServiceInfo> list) {
                    try {
                        iMbmsStreamingSessionCallback.onStreamingServicesUpdated(list);
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }
            }, n);
            if (n3 == 0) {
                iMbmsStreamingSessionCallback.asBinder().linkToDeath(new IBinder.DeathRecipient(){

                    @Override
                    public void binderDied() {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }, 0);
            }
            return n3;
        }
        throw new NullPointerException("Callback must not be null");
    }

    public int initialize(MbmsStreamingSessionCallback mbmsStreamingSessionCallback, int n) throws RemoteException {
        return 0;
    }

    public void onAppCallbackDied(int n, int n2) {
    }

    @SystemApi
    @Override
    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        return super.onTransact(n, parcel, parcel2, n2);
    }

    @Override
    public int requestUpdateStreamingServices(int n, List<String> list) throws RemoteException {
        return 0;
    }

    @Override
    public int startStreaming(final int n, String string2, final IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException {
        if (iStreamingServiceCallback != null) {
            final int n2 = Binder.getCallingUid();
            int n3 = this.startStreaming(n, string2, new StreamingServiceCallback(){

                @Override
                public void onBroadcastSignalStrengthUpdated(int n3) {
                    try {
                        iStreamingServiceCallback.onBroadcastSignalStrengthUpdated(n3);
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void onError(int n3, String object) {
                    if (n3 != -1) {
                        try {
                            iStreamingServiceCallback.onError(n3, (String)object);
                            return;
                        }
                        catch (RemoteException remoteException) {}
                    } else {
                        object = new IllegalArgumentException("Middleware cannot send an unknown error.");
                        throw object;
                    }
                    MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                }

                @Override
                public void onMediaDescriptionUpdated() {
                    try {
                        iStreamingServiceCallback.onMediaDescriptionUpdated();
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }

                @Override
                public void onStreamMethodUpdated(int n3) {
                    try {
                        iStreamingServiceCallback.onStreamMethodUpdated(n3);
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }

                @Override
                public void onStreamStateUpdated(int n3, int n22) {
                    try {
                        iStreamingServiceCallback.onStreamStateUpdated(n3, n22);
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }
            });
            if (n3 == 0) {
                iStreamingServiceCallback.asBinder().linkToDeath(new IBinder.DeathRecipient(){

                    @Override
                    public void binderDied() {
                        MbmsStreamingServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }, 0);
            }
            return n3;
        }
        throw new NullPointerException("Callback must not be null");
    }

    public int startStreaming(int n, String string2, StreamingServiceCallback streamingServiceCallback) throws RemoteException {
        return 0;
    }

    @Override
    public void stopStreaming(int n, String string2) throws RemoteException {
    }

}

