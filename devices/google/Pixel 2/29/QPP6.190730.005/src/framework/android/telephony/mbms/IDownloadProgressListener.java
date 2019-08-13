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

public interface IDownloadProgressListener
extends IInterface {
    public void onProgressUpdated(DownloadRequest var1, FileInfo var2, int var3, int var4, int var5, int var6) throws RemoteException;

    public static class Default
    implements IDownloadProgressListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onProgressUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int n, int n2, int n3, int n4) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDownloadProgressListener {
        private static final String DESCRIPTOR = "android.telephony.mbms.IDownloadProgressListener";
        static final int TRANSACTION_onProgressUpdated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadProgressListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDownloadProgressListener) {
                return (IDownloadProgressListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDownloadProgressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onProgressUpdated";
        }

        public static boolean setDefaultImpl(IDownloadProgressListener iDownloadProgressListener) {
            if (Proxy.sDefaultImpl == null && iDownloadProgressListener != null) {
                Proxy.sDefaultImpl = iDownloadProgressListener;
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
            this.onProgressUpdated(downloadRequest, fileInfo, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IDownloadProgressListener {
            public static IDownloadProgressListener sDefaultImpl;
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onProgressUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (downloadRequest != null) {
                            parcel2.writeInt(1);
                            downloadRequest.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (fileInfo != null) {
                            parcel2.writeInt(1);
                            fileInfo.writeToParcel(parcel2, 0);
                            break block15;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n4);
                        if (!this.mRemote.transact(1, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onProgressUpdated(downloadRequest, fileInfo, n, n2, n3, n4);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }
        }

    }

}

