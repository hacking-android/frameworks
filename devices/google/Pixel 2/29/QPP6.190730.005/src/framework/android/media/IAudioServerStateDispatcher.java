/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAudioServerStateDispatcher
extends IInterface {
    public void dispatchAudioServerStateChange(boolean var1) throws RemoteException;

    public static class Default
    implements IAudioServerStateDispatcher {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchAudioServerStateChange(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAudioServerStateDispatcher {
        private static final String DESCRIPTOR = "android.media.IAudioServerStateDispatcher";
        static final int TRANSACTION_dispatchAudioServerStateChange = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAudioServerStateDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAudioServerStateDispatcher) {
                return (IAudioServerStateDispatcher)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAudioServerStateDispatcher getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "dispatchAudioServerStateChange";
        }

        public static boolean setDefaultImpl(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
            if (Proxy.sDefaultImpl == null && iAudioServerStateDispatcher != null) {
                Proxy.sDefaultImpl = iAudioServerStateDispatcher;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean bl = parcel.readInt() != 0;
            this.dispatchAudioServerStateChange(bl);
            return true;
        }

        private static class Proxy
        implements IAudioServerStateDispatcher {
            public static IAudioServerStateDispatcher sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchAudioServerStateChange(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchAudioServerStateChange(bl);
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

