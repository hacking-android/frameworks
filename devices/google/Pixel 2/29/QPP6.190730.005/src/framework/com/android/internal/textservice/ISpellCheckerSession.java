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
import android.view.textservice.TextInfo;

public interface ISpellCheckerSession
extends IInterface {
    public void onCancel() throws RemoteException;

    public void onClose() throws RemoteException;

    public void onGetSentenceSuggestionsMultiple(TextInfo[] var1, int var2) throws RemoteException;

    public void onGetSuggestionsMultiple(TextInfo[] var1, int var2, boolean var3) throws RemoteException;

    public static class Default
    implements ISpellCheckerSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCancel() throws RemoteException {
        }

        @Override
        public void onClose() throws RemoteException {
        }

        @Override
        public void onGetSentenceSuggestionsMultiple(TextInfo[] arrtextInfo, int n) throws RemoteException {
        }

        @Override
        public void onGetSuggestionsMultiple(TextInfo[] arrtextInfo, int n, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISpellCheckerSession {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSession";
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onClose = 4;
        static final int TRANSACTION_onGetSentenceSuggestionsMultiple = 2;
        static final int TRANSACTION_onGetSuggestionsMultiple = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISpellCheckerSession) {
                return (ISpellCheckerSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISpellCheckerSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onClose";
                    }
                    return "onCancel";
                }
                return "onGetSentenceSuggestionsMultiple";
            }
            return "onGetSuggestionsMultiple";
        }

        public static boolean setDefaultImpl(ISpellCheckerSession iSpellCheckerSession) {
            if (Proxy.sDefaultImpl == null && iSpellCheckerSession != null) {
                Proxy.sDefaultImpl = iSpellCheckerSession;
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
        public boolean onTransact(int n, Parcel parcel, Parcel arrtextInfo, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, (Parcel)arrtextInfo, n2);
                            }
                            arrtextInfo.writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onClose();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onCancel();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onGetSentenceSuggestionsMultiple(parcel.createTypedArray(TextInfo.CREATOR), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            arrtextInfo = parcel.createTypedArray(TextInfo.CREATOR);
            n = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.onGetSuggestionsMultiple(arrtextInfo, n, bl);
            return true;
        }

        private static class Proxy
        implements ISpellCheckerSession {
            public static ISpellCheckerSession sDefaultImpl;
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
            public void onCancel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCancel();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onClose() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClose();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGetSentenceSuggestionsMultiple(TextInfo[] arrtextInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrtextInfo, 0);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSentenceSuggestionsMultiple(arrtextInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGetSuggestionsMultiple(TextInfo[] arrtextInfo, int n, boolean bl) throws RemoteException {
                Parcel parcel;
                int n2;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    n2 = 0;
                    parcel.writeTypedArray((Parcelable[])arrtextInfo, 0);
                    parcel.writeInt(n);
                    if (!bl) break block6;
                    n2 = 1;
                }
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSuggestionsMultiple(arrtextInfo, n, bl);
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

