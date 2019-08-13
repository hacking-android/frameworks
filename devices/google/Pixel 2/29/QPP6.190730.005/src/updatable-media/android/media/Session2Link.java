/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.util.Log
 */
package android.media;

import android.media.Controller2Link;
import android.media.IMediaSession2;
import android.media.MediaSession2;
import android.media.Session2Command;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import java.util.Objects;

public final class Session2Link
implements Parcelable {
    public static final Parcelable.Creator<Session2Link> CREATOR;
    private static final boolean DEBUG;
    private static final String TAG = "Session2Link";
    private final IMediaSession2 mISession;
    private final MediaSession2 mSession;

    static {
        DEBUG = MediaSession2.DEBUG;
        CREATOR = new Parcelable.Creator<Session2Link>(){

            public Session2Link createFromParcel(Parcel parcel) {
                return new Session2Link(parcel);
            }

            public Session2Link[] newArray(int n) {
                return new Session2Link[n];
            }
        };
    }

    public Session2Link(MediaSession2 mediaSession2) {
        this.mSession = mediaSession2;
        this.mISession = new Session2Stub();
    }

    Session2Link(Parcel parcel) {
        this.mSession = null;
        this.mISession = IMediaSession2.Stub.asInterface(parcel.readStrongBinder());
    }

    public void cancelSessionCommand(Controller2Link controller2Link, int n) {
        try {
            this.mISession.cancelSessionCommand(controller2Link, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void connect(Controller2Link controller2Link, int n, Bundle bundle) {
        try {
            this.mISession.connect(controller2Link, n, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void disconnect(Controller2Link controller2Link, int n) {
        try {
            this.mISession.disconnect(controller2Link, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof Session2Link)) {
            return false;
        }
        object = (Session2Link)object;
        return Objects.equals((Object)this.mISession.asBinder(), (Object)((Session2Link)object).mISession.asBinder());
    }

    public int hashCode() {
        return this.mISession.asBinder().hashCode();
    }

    public void linkToDeath(IBinder.DeathRecipient deathRecipient, int n) {
        block3 : {
            IMediaSession2 iMediaSession2 = this.mISession;
            if (iMediaSession2 != null) {
                try {
                    iMediaSession2.asBinder().linkToDeath(deathRecipient, n);
                }
                catch (RemoteException remoteException) {
                    if (!DEBUG) break block3;
                    Log.d((String)TAG, (String)"Session died too early.", (Throwable)remoteException);
                }
            }
        }
    }

    public void onCancelCommand(Controller2Link controller2Link, int n) {
        this.mSession.onCancelCommand(controller2Link, n);
    }

    public void onConnect(Controller2Link controller2Link, int n, int n2, int n3, Bundle bundle) {
        this.mSession.onConnect(controller2Link, n, n2, n3, bundle);
    }

    public void onDisconnect(Controller2Link controller2Link, int n) {
        this.mSession.onDisconnect(controller2Link, n);
    }

    public void onSessionCommand(Controller2Link controller2Link, int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
        this.mSession.onSessionCommand(controller2Link, n, session2Command, bundle, resultReceiver);
    }

    public void sendSessionCommand(Controller2Link controller2Link, int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
        try {
            this.mISession.sendSessionCommand(controller2Link, n, session2Command, bundle, resultReceiver);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int n) {
        IMediaSession2 iMediaSession2 = this.mISession;
        if (iMediaSession2 != null) {
            return iMediaSession2.asBinder().unlinkToDeath(deathRecipient, n);
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mISession.asBinder());
    }

    private class Session2Stub
    extends IMediaSession2.Stub {
        private Session2Stub() {
        }

        @Override
        public void cancelSessionCommand(Controller2Link controller2Link, int n) {
            if (controller2Link == null) {
                return;
            }
            long l = Binder.clearCallingIdentity();
            try {
                Session2Link.this.onCancelCommand(controller2Link, n);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        @Override
        public void connect(Controller2Link controller2Link, int n, Bundle bundle) {
            if (controller2Link != null && bundle != null) {
                int n2 = Binder.getCallingPid();
                int n3 = Binder.getCallingUid();
                long l = Binder.clearCallingIdentity();
                try {
                    Session2Link.this.onConnect(controller2Link, n2, n3, n, bundle);
                    return;
                }
                finally {
                    Binder.restoreCallingIdentity((long)l);
                }
            }
        }

        @Override
        public void disconnect(Controller2Link controller2Link, int n) {
            if (controller2Link == null) {
                return;
            }
            long l = Binder.clearCallingIdentity();
            try {
                Session2Link.this.onDisconnect(controller2Link, n);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        @Override
        public void sendSessionCommand(Controller2Link controller2Link, int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
            if (controller2Link == null) {
                return;
            }
            long l = Binder.clearCallingIdentity();
            try {
                Session2Link.this.onSessionCommand(controller2Link, n, session2Command, bundle, resultReceiver);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
    }

}

