/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.ParcelableCall;
import com.android.internal.telecom.ICallScreeningAdapter;

public interface ICallScreeningService
extends IInterface {
    public void screenCall(ICallScreeningAdapter var1, ParcelableCall var2) throws RemoteException;

    public static class Default
    implements ICallScreeningService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICallScreeningService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ICallScreeningService";
        static final int TRANSACTION_screenCall = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICallScreeningService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICallScreeningService) {
                return (ICallScreeningService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICallScreeningService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "screenCall";
        }

        public static boolean setDefaultImpl(ICallScreeningService iCallScreeningService) {
            if (Proxy.sDefaultImpl == null && iCallScreeningService != null) {
                Proxy.sDefaultImpl = iCallScreeningService;
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
            object2 = ICallScreeningAdapter.Stub.asInterface(((Parcel)object).readStrongBinder());
            object = ((Parcel)object).readInt() != 0 ? ParcelableCall.CREATOR.createFromParcel((Parcel)object) : null;
            this.screenCall((ICallScreeningAdapter)object2, (ParcelableCall)object);
            return true;
        }

        private static class Proxy
        implements ICallScreeningService {
            public static ICallScreeningService sDefaultImpl;
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
            public void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCallScreeningAdapter != null ? iCallScreeningAdapter.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (parcelableCall != null) {
                        parcel.writeInt(1);
                        parcelableCall.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().screenCall(iCallScreeningAdapter, parcelableCall);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

