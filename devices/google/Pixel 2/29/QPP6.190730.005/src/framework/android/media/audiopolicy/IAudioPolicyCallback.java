/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.media.AudioFocusInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IAudioPolicyCallback
extends IInterface {
    public void notifyAudioFocusAbandon(AudioFocusInfo var1) throws RemoteException;

    public void notifyAudioFocusGrant(AudioFocusInfo var1, int var2) throws RemoteException;

    public void notifyAudioFocusLoss(AudioFocusInfo var1, boolean var2) throws RemoteException;

    public void notifyAudioFocusRequest(AudioFocusInfo var1, int var2) throws RemoteException;

    public void notifyMixStateUpdate(String var1, int var2) throws RemoteException;

    public void notifyVolumeAdjust(int var1) throws RemoteException;

    public static class Default
    implements IAudioPolicyCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException {
        }

        @Override
        public void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int n) throws RemoteException {
        }

        @Override
        public void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean bl) throws RemoteException {
        }

        @Override
        public void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int n) throws RemoteException {
        }

        @Override
        public void notifyMixStateUpdate(String string2, int n) throws RemoteException {
        }

        @Override
        public void notifyVolumeAdjust(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAudioPolicyCallback {
        private static final String DESCRIPTOR = "android.media.audiopolicy.IAudioPolicyCallback";
        static final int TRANSACTION_notifyAudioFocusAbandon = 4;
        static final int TRANSACTION_notifyAudioFocusGrant = 1;
        static final int TRANSACTION_notifyAudioFocusLoss = 2;
        static final int TRANSACTION_notifyAudioFocusRequest = 3;
        static final int TRANSACTION_notifyMixStateUpdate = 5;
        static final int TRANSACTION_notifyVolumeAdjust = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAudioPolicyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAudioPolicyCallback) {
                return (IAudioPolicyCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAudioPolicyCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "notifyVolumeAdjust";
                }
                case 5: {
                    return "notifyMixStateUpdate";
                }
                case 4: {
                    return "notifyAudioFocusAbandon";
                }
                case 3: {
                    return "notifyAudioFocusRequest";
                }
                case 2: {
                    return "notifyAudioFocusLoss";
                }
                case 1: 
            }
            return "notifyAudioFocusGrant";
        }

        public static boolean setDefaultImpl(IAudioPolicyCallback iAudioPolicyCallback) {
            if (Proxy.sDefaultImpl == null && iAudioPolicyCallback != null) {
                Proxy.sDefaultImpl = iAudioPolicyCallback;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyVolumeAdjust(((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyMixStateUpdate(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyAudioFocusAbandon((AudioFocusInfo)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyAudioFocusRequest((AudioFocusInfo)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.notifyAudioFocusLoss((AudioFocusInfo)object2, bl);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.notifyAudioFocusGrant((AudioFocusInfo)object2, ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAudioPolicyCallback {
            public static IAudioPolicyCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioFocusInfo != null) {
                        parcel.writeInt(1);
                        audioFocusInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusAbandon(audioFocusInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioFocusInfo != null) {
                        parcel.writeInt(1);
                        audioFocusInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusGrant(audioFocusInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (audioFocusInfo != null) {
                        parcel.writeInt(1);
                        audioFocusInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().notifyAudioFocusLoss(audioFocusInfo, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioFocusInfo != null) {
                        parcel.writeInt(1);
                        audioFocusInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAudioFocusRequest(audioFocusInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyMixStateUpdate(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyMixStateUpdate(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyVolumeAdjust(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyVolumeAdjust(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

