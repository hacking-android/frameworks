/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.media.projection.MediaProjectionInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMediaProjectionWatcherCallback
extends IInterface {
    public void onStart(MediaProjectionInfo var1) throws RemoteException;

    public void onStop(MediaProjectionInfo var1) throws RemoteException;

    public static class Default
    implements IMediaProjectionWatcherCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStart(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
        }

        @Override
        public void onStop(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaProjectionWatcherCallback {
        private static final String DESCRIPTOR = "android.media.projection.IMediaProjectionWatcherCallback";
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjectionWatcherCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaProjectionWatcherCallback) {
                return (IMediaProjectionWatcherCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaProjectionWatcherCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onStop";
            }
            return "onStart";
        }

        public static boolean setDefaultImpl(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            if (Proxy.sDefaultImpl == null && iMediaProjectionWatcherCallback != null) {
                Proxy.sDefaultImpl = iMediaProjectionWatcherCallback;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? MediaProjectionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.onStop((MediaProjectionInfo)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? MediaProjectionInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onStart((MediaProjectionInfo)object);
            return true;
        }

        private static class Proxy
        implements IMediaProjectionWatcherCallback {
            public static IMediaProjectionWatcherCallback sDefaultImpl;
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
            public void onStart(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mediaProjectionInfo != null) {
                        parcel.writeInt(1);
                        mediaProjectionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStart(mediaProjectionInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStop(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mediaProjectionInfo != null) {
                        parcel.writeInt(1);
                        mediaProjectionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStop(mediaProjectionInfo);
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

