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
import android.telecom.PhoneAccountSuggestion;
import java.util.ArrayList;
import java.util.List;

public interface IPhoneAccountSuggestionCallback
extends IInterface {
    public void suggestPhoneAccounts(String var1, List<PhoneAccountSuggestion> var2) throws RemoteException;

    public static class Default
    implements IPhoneAccountSuggestionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void suggestPhoneAccounts(String string2, List<PhoneAccountSuggestion> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPhoneAccountSuggestionCallback {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IPhoneAccountSuggestionCallback";
        static final int TRANSACTION_suggestPhoneAccounts = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPhoneAccountSuggestionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPhoneAccountSuggestionCallback) {
                return (IPhoneAccountSuggestionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPhoneAccountSuggestionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "suggestPhoneAccounts";
        }

        public static boolean setDefaultImpl(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback) {
            if (Proxy.sDefaultImpl == null && iPhoneAccountSuggestionCallback != null) {
                Proxy.sDefaultImpl = iPhoneAccountSuggestionCallback;
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
            this.suggestPhoneAccounts(parcel.readString(), parcel.createTypedArrayList(PhoneAccountSuggestion.CREATOR));
            return true;
        }

        private static class Proxy
        implements IPhoneAccountSuggestionCallback {
            public static IPhoneAccountSuggestionCallback sDefaultImpl;
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
            public void suggestPhoneAccounts(String string2, List<PhoneAccountSuggestion> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().suggestPhoneAccounts(string2, list);
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

