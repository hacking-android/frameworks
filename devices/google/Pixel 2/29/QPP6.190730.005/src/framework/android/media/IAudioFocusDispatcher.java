/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAudioFocusDispatcher
extends IInterface {
    @UnsupportedAppUsage
    public void dispatchAudioFocusChange(int var1, String var2) throws RemoteException;

    public void dispatchFocusResultFromExtPolicy(int var1, String var2) throws RemoteException;

    public static class Default
    implements IAudioFocusDispatcher {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchAudioFocusChange(int n, String string2) throws RemoteException {
        }

        @Override
        public void dispatchFocusResultFromExtPolicy(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAudioFocusDispatcher {
        private static final String DESCRIPTOR = "android.media.IAudioFocusDispatcher";
        static final int TRANSACTION_dispatchAudioFocusChange = 1;
        static final int TRANSACTION_dispatchFocusResultFromExtPolicy = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAudioFocusDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAudioFocusDispatcher) {
                return (IAudioFocusDispatcher)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAudioFocusDispatcher getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "dispatchFocusResultFromExtPolicy";
            }
            return "dispatchAudioFocusChange";
        }

        public static boolean setDefaultImpl(IAudioFocusDispatcher iAudioFocusDispatcher) {
            if (Proxy.sDefaultImpl == null && iAudioFocusDispatcher != null) {
                Proxy.sDefaultImpl = iAudioFocusDispatcher;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.dispatchFocusResultFromExtPolicy(parcel.readInt(), parcel.readString());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.dispatchAudioFocusChange(parcel.readInt(), parcel.readString());
            return true;
        }

        private static class Proxy
        implements IAudioFocusDispatcher {
            public static IAudioFocusDispatcher sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchAudioFocusChange(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchAudioFocusChange(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchFocusResultFromExtPolicy(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchFocusResultFromExtPolicy(n, string2);
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

