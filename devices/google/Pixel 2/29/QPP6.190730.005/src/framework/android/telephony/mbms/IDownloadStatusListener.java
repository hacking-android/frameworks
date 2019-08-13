/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;

public interface IDownloadStatusListener
extends IInterface {
    public void onStatusUpdated(DownloadRequest var1, FileInfo var2, int var3) throws RemoteException;

    public static class Default
    implements IDownloadStatusListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDownloadStatusListener {
        private static final String DESCRIPTOR = "android.telephony.mbms.IDownloadStatusListener";
        static final int TRANSACTION_onStatusUpdated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDownloadStatusListener) {
                return (IDownloadStatusListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDownloadStatusListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStatusUpdated";
        }

        public static boolean setDefaultImpl(IDownloadStatusListener iDownloadStatusListener) {
            if (Proxy.sDefaultImpl == null && iDownloadStatusListener != null) {
                Proxy.sDefaultImpl = iDownloadStatusListener;
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
            DownloadRequest downloadRequest = parcel.readInt() != 0 ? DownloadRequest.CREATOR.createFromParcel(parcel) : null;
            FileInfo fileInfo = parcel.readInt() != 0 ? FileInfo.CREATOR.createFromParcel(parcel) : null;
            this.onStatusUpdated(downloadRequest, fileInfo, parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IDownloadStatusListener {
            public static IDownloadStatusListener sDefaultImpl;
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
            public void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadRequest != null) {
                        parcel.writeInt(1);
                        downloadRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (fileInfo != null) {
                        parcel.writeInt(1);
                        fileInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusUpdated(downloadRequest, fileInfo, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

