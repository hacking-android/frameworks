/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 */
package android.media;

import android.media.IMediaController2;
import android.media.MediaController2;
import android.media.Session2Command;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import java.util.Objects;

public final class Controller2Link
implements Parcelable {
    public static final Parcelable.Creator<Controller2Link> CREATOR;
    private static final boolean DEBUG;
    private static final String TAG = "Controller2Link";
    private final MediaController2 mController;
    private final IMediaController2 mIController;

    static {
        DEBUG = MediaController2.DEBUG;
        CREATOR = new Parcelable.Creator<Controller2Link>(){

            public Controller2Link createFromParcel(Parcel parcel) {
                return new Controller2Link(parcel);
            }

            public Controller2Link[] newArray(int n) {
                return new Controller2Link[n];
            }
        };
    }

    public Controller2Link(MediaController2 mediaController2) {
        this.mController = mediaController2;
        this.mIController = new Controller2Stub();
    }

    Controller2Link(Parcel parcel) {
        this.mController = null;
        this.mIController = IMediaController2.Stub.asInterface(parcel.readStrongBinder());
    }

    public void cancelSessionCommand(int n) {
        try {
            this.mIController.cancelSessionCommand(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Controller2Link)) {
            return false;
        }
        object = (Controller2Link)object;
        return Objects.equals((Object)this.mIController.asBinder(), (Object)((Controller2Link)object).mIController.asBinder());
    }

    public int hashCode() {
        return this.mIController.asBinder().hashCode();
    }

    public void notifyConnected(int n, Bundle bundle) {
        try {
            this.mIController.notifyConnected(n, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void notifyDisconnected(int n) {
        try {
            this.mIController.notifyDisconnected(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void notifyPlaybackActiveChanged(int n, boolean bl) {
        try {
            this.mIController.notifyPlaybackActiveChanged(n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void onCancelCommand(int n) {
        this.mController.onCancelCommand(n);
    }

    public void onConnected(int n, Bundle bundle) {
        if (bundle == null) {
            this.onDisconnected(n);
            return;
        }
        this.mController.onConnected(n, bundle);
    }

    public void onDisconnected(int n) {
        this.mController.onDisconnected(n);
    }

    public void onPlaybackActiveChanged(int n, boolean bl) {
        this.mController.onPlaybackActiveChanged(n, bl);
    }

    public void onSessionCommand(int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
        this.mController.onSessionCommand(n, session2Command, bundle, resultReceiver);
    }

    public void sendSessionCommand(int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
        try {
            this.mIController.sendSessionCommand(n, session2Command, bundle, resultReceiver);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mIController.asBinder());
    }

    private class Controller2Stub
    extends IMediaController2.Stub {
        private Controller2Stub() {
        }

        @Override
        public void cancelSessionCommand(int n) {
            long l = Binder.clearCallingIdentity();
            try {
                Controller2Link.this.onCancelCommand(n);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        @Override
        public void notifyConnected(int n, Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            try {
                Controller2Link.this.onConnected(n, bundle);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        @Override
        public void notifyDisconnected(int n) {
            long l = Binder.clearCallingIdentity();
            try {
                Controller2Link.this.onDisconnected(n);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        @Override
        public void notifyPlaybackActiveChanged(int n, boolean bl) {
            long l = Binder.clearCallingIdentity();
            try {
                Controller2Link.this.onPlaybackActiveChanged(n, bl);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        @Override
        public void sendSessionCommand(int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
            long l = Binder.clearCallingIdentity();
            try {
                Controller2Link.this.onSessionCommand(n, session2Command, bundle, resultReceiver);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
    }

}

