/*
 * Decompiled with CFR 0.145.
 */
package android.service.sms;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IFinancialSmsService
extends IInterface {
    public void getSmsMessages(RemoteCallback var1, Bundle var2) throws RemoteException;

    public static class Default
    implements IFinancialSmsService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getSmsMessages(RemoteCallback remoteCallback, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFinancialSmsService {
        private static final String DESCRIPTOR = "android.service.sms.IFinancialSmsService";
        static final int TRANSACTION_getSmsMessages = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFinancialSmsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFinancialSmsService) {
                return (IFinancialSmsService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFinancialSmsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getSmsMessages";
        }

        public static boolean setDefaultImpl(IFinancialSmsService iFinancialSmsService) {
            if (Proxy.sDefaultImpl == null && iFinancialSmsService != null) {
                Proxy.sDefaultImpl = iFinancialSmsService;
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
            object2 = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.getSmsMessages((RemoteCallback)object2, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IFinancialSmsService {
            public static IFinancialSmsService sDefaultImpl;
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
            public void getSmsMessages(RemoteCallback remoteCallback, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getSmsMessages(remoteCallback, bundle);
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

