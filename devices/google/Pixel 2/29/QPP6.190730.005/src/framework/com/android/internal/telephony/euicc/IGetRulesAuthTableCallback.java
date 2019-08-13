/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.euicc.EuiccRulesAuthTable;

public interface IGetRulesAuthTableCallback
extends IInterface {
    public void onComplete(int var1, EuiccRulesAuthTable var2) throws RemoteException;

    public static class Default
    implements IGetRulesAuthTableCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(int n, EuiccRulesAuthTable euiccRulesAuthTable) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGetRulesAuthTableCallback {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetRulesAuthTableCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGetRulesAuthTableCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGetRulesAuthTableCallback) {
                return (IGetRulesAuthTableCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGetRulesAuthTableCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IGetRulesAuthTableCallback iGetRulesAuthTableCallback) {
            if (Proxy.sDefaultImpl == null && iGetRulesAuthTableCallback != null) {
                Proxy.sDefaultImpl = iGetRulesAuthTableCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? EuiccRulesAuthTable.CREATOR.createFromParcel((Parcel)object) : null;
            this.onComplete(n, (EuiccRulesAuthTable)object);
            return true;
        }

        private static class Proxy
        implements IGetRulesAuthTableCallback {
            public static IGetRulesAuthTableCallback sDefaultImpl;
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
            public void onComplete(int n, EuiccRulesAuthTable euiccRulesAuthTable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (euiccRulesAuthTable != null) {
                        parcel.writeInt(1);
                        euiccRulesAuthTable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(n, euiccRulesAuthTable);
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

