/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;

public interface ISpellCheckerSessionListener
extends IInterface {
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] var1) throws RemoteException;

    public void onGetSuggestions(SuggestionsInfo[] var1) throws RemoteException;

    public static class Default
    implements ISpellCheckerSessionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] arrsentenceSuggestionsInfo) throws RemoteException {
        }

        @Override
        public void onGetSuggestions(SuggestionsInfo[] arrsuggestionsInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISpellCheckerSessionListener {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSessionListener";
        static final int TRANSACTION_onGetSentenceSuggestions = 2;
        static final int TRANSACTION_onGetSuggestions = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISpellCheckerSessionListener) {
                return (ISpellCheckerSessionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISpellCheckerSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onGetSentenceSuggestions";
            }
            return "onGetSuggestions";
        }

        public static boolean setDefaultImpl(ISpellCheckerSessionListener iSpellCheckerSessionListener) {
            if (Proxy.sDefaultImpl == null && iSpellCheckerSessionListener != null) {
                Proxy.sDefaultImpl = iSpellCheckerSessionListener;
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
                this.onGetSentenceSuggestions(parcel.createTypedArray(SentenceSuggestionsInfo.CREATOR));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onGetSuggestions(parcel.createTypedArray(SuggestionsInfo.CREATOR));
            return true;
        }

        private static class Proxy
        implements ISpellCheckerSessionListener {
            public static ISpellCheckerSessionListener sDefaultImpl;
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
            public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] arrsentenceSuggestionsInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrsentenceSuggestionsInfo, 0);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSentenceSuggestions(arrsentenceSuggestionsInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGetSuggestions(SuggestionsInfo[] arrsuggestionsInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrsuggestionsInfo, 0);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSuggestions(arrsuggestionsInfo);
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

