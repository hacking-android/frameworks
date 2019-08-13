/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.Announcement;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IAnnouncementListener
extends IInterface {
    public void onListUpdated(List<Announcement> var1) throws RemoteException;

    public static class Default
    implements IAnnouncementListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onListUpdated(List<Announcement> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAnnouncementListener {
        private static final String DESCRIPTOR = "android.hardware.radio.IAnnouncementListener";
        static final int TRANSACTION_onListUpdated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAnnouncementListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAnnouncementListener) {
                return (IAnnouncementListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAnnouncementListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onListUpdated";
        }

        public static boolean setDefaultImpl(IAnnouncementListener iAnnouncementListener) {
            if (Proxy.sDefaultImpl == null && iAnnouncementListener != null) {
                Proxy.sDefaultImpl = iAnnouncementListener;
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
            this.onListUpdated(parcel.createTypedArrayList(Announcement.CREATOR));
            return true;
        }

        private static class Proxy
        implements IAnnouncementListener {
            public static IAnnouncementListener sDefaultImpl;
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
            public void onListUpdated(List<Announcement> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onListUpdated(list);
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

