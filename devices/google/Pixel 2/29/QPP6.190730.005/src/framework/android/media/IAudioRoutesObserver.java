/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioRoutesInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IAudioRoutesObserver
extends IInterface {
    public void dispatchAudioRoutesChanged(AudioRoutesInfo var1) throws RemoteException;

    public static class Default
    implements IAudioRoutesObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchAudioRoutesChanged(AudioRoutesInfo audioRoutesInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAudioRoutesObserver {
        private static final String DESCRIPTOR = "android.media.IAudioRoutesObserver";
        static final int TRANSACTION_dispatchAudioRoutesChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAudioRoutesObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAudioRoutesObserver) {
                return (IAudioRoutesObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAudioRoutesObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "dispatchAudioRoutesChanged";
        }

        public static boolean setDefaultImpl(IAudioRoutesObserver iAudioRoutesObserver) {
            if (Proxy.sDefaultImpl == null && iAudioRoutesObserver != null) {
                Proxy.sDefaultImpl = iAudioRoutesObserver;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? AudioRoutesInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.dispatchAudioRoutesChanged((AudioRoutesInfo)object);
            return true;
        }

        private static class Proxy
        implements IAudioRoutesObserver {
            public static IAudioRoutesObserver sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchAudioRoutesChanged(AudioRoutesInfo audioRoutesInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioRoutesInfo != null) {
                        parcel.writeInt(1);
                        audioRoutesInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchAudioRoutesChanged(audioRoutesInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

