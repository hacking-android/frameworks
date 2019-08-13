/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMediaScannerListener
extends IInterface {
    public void scanCompleted(String var1, Uri var2) throws RemoteException;

    public static class Default
    implements IMediaScannerListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void scanCompleted(String string2, Uri uri) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaScannerListener {
        private static final String DESCRIPTOR = "android.media.IMediaScannerListener";
        static final int TRANSACTION_scanCompleted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaScannerListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaScannerListener) {
                return (IMediaScannerListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaScannerListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "scanCompleted";
        }

        public static boolean setDefaultImpl(IMediaScannerListener iMediaScannerListener) {
            if (Proxy.sDefaultImpl == null && iMediaScannerListener != null) {
                Proxy.sDefaultImpl = iMediaScannerListener;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                }
                ((Parcel)object2).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readString();
            object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
            this.scanCompleted((String)object2, (Uri)object);
            return true;
        }

        private static class Proxy
        implements IMediaScannerListener {
            public static IMediaScannerListener sDefaultImpl;
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
            public void scanCompleted(String string2, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scanCompleted(string2, uri);
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

