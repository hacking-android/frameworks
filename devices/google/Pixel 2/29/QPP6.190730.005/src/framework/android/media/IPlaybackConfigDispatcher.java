/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioPlaybackConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IPlaybackConfigDispatcher
extends IInterface {
    public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> var1, boolean var2) throws RemoteException;

    public static class Default
    implements IPlaybackConfigDispatcher {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> list, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPlaybackConfigDispatcher {
        private static final String DESCRIPTOR = "android.media.IPlaybackConfigDispatcher";
        static final int TRANSACTION_dispatchPlaybackConfigChange = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPlaybackConfigDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPlaybackConfigDispatcher) {
                return (IPlaybackConfigDispatcher)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPlaybackConfigDispatcher getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "dispatchPlaybackConfigChange";
        }

        public static boolean setDefaultImpl(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
            if (Proxy.sDefaultImpl == null && iPlaybackConfigDispatcher != null) {
                Proxy.sDefaultImpl = iPlaybackConfigDispatcher;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
            boolean bl = parcel.readInt() != 0;
            this.dispatchPlaybackConfigChange((List<AudioPlaybackConfiguration>)object, bl);
            return true;
        }

        private static class Proxy
        implements IPlaybackConfigDispatcher {
            public static IPlaybackConfigDispatcher sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> list, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeTypedList(list);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPlaybackConfigChange(list, bl);
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

