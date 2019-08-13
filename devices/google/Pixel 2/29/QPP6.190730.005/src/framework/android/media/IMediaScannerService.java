/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.IMediaScannerListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaScannerService
extends IInterface {
    @UnsupportedAppUsage
    public void requestScanFile(String var1, String var2, IMediaScannerListener var3) throws RemoteException;

    @UnsupportedAppUsage
    public void scanFile(String var1, String var2) throws RemoteException;

    public static class Default
    implements IMediaScannerService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void requestScanFile(String string2, String string3, IMediaScannerListener iMediaScannerListener) throws RemoteException {
        }

        @Override
        public void scanFile(String string2, String string3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaScannerService {
        private static final String DESCRIPTOR = "android.media.IMediaScannerService";
        static final int TRANSACTION_requestScanFile = 1;
        static final int TRANSACTION_scanFile = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaScannerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaScannerService) {
                return (IMediaScannerService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaScannerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "scanFile";
            }
            return "requestScanFile";
        }

        public static boolean setDefaultImpl(IMediaScannerService iMediaScannerService) {
            if (Proxy.sDefaultImpl == null && iMediaScannerService != null) {
                Proxy.sDefaultImpl = iMediaScannerService;
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
                this.scanFile(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.requestScanFile(parcel.readString(), parcel.readString(), IMediaScannerListener.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IMediaScannerService {
            public static IMediaScannerService sDefaultImpl;
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestScanFile(String string2, String string3, IMediaScannerListener iMediaScannerListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iMediaScannerListener != null ? iMediaScannerListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestScanFile(string2, string3, iMediaScannerListener);
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

            @Override
            public void scanFile(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scanFile(string2, string3);
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

