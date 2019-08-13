/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.os.IResultReceiver;

public interface IContentSuggestionsManager
extends IInterface {
    public void classifyContentSelections(int var1, ClassificationsRequest var2, IClassificationsCallback var3) throws RemoteException;

    public void isEnabled(int var1, IResultReceiver var2) throws RemoteException;

    public void notifyInteraction(int var1, String var2, Bundle var3) throws RemoteException;

    public void provideContextImage(int var1, int var2, Bundle var3) throws RemoteException;

    public void suggestContentSelections(int var1, SelectionsRequest var2, ISelectionsCallback var3) throws RemoteException;

    public static class Default
    implements IContentSuggestionsManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void classifyContentSelections(int n, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
        }

        @Override
        public void isEnabled(int n, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void notifyInteraction(int n, String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void provideContextImage(int n, int n2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void suggestContentSelections(int n, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentSuggestionsManager {
        private static final String DESCRIPTOR = "android.app.contentsuggestions.IContentSuggestionsManager";
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_isEnabled = 5;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentSuggestionsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentSuggestionsManager) {
                return (IContentSuggestionsManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentSuggestionsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "isEnabled";
                        }
                        return "notifyInteraction";
                    }
                    return "classifyContentSelections";
                }
                return "suggestContentSelections";
            }
            return "provideContextImage";
        }

        public static boolean setDefaultImpl(IContentSuggestionsManager iContentSuggestionsManager) {
            if (Proxy.sDefaultImpl == null && iContentSuggestionsManager != null) {
                Proxy.sDefaultImpl = iContentSuggestionsManager;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                                }
                                ((Parcel)object2).writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            this.isEnabled(((Parcel)object).readInt(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyInteraction(n, (String)object2, (Bundle)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = ((Parcel)object).readInt();
                    object2 = ((Parcel)object).readInt() != 0 ? ClassificationsRequest.CREATOR.createFromParcel((Parcel)object) : null;
                    this.classifyContentSelections(n, (ClassificationsRequest)object2, IClassificationsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object2 = ((Parcel)object).readInt() != 0 ? SelectionsRequest.CREATOR.createFromParcel((Parcel)object) : null;
                this.suggestContentSelections(n, (SelectionsRequest)object2, ISelectionsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            n2 = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.provideContextImage(n, n2, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IContentSuggestionsManager {
            public static IContentSuggestionsManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void classifyContentSelections(int n, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (classificationsRequest != null) {
                        parcel.writeInt(1);
                        classificationsRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iClassificationsCallback != null ? iClassificationsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().classifyContentSelections(n, classificationsRequest, iClassificationsCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
            public void isEnabled(int n, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().isEnabled(n, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyInteraction(int n, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyInteraction(n, string2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void provideContextImage(int n, int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().provideContextImage(n, n2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void suggestContentSelections(int n, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (selectionsRequest != null) {
                        parcel.writeInt(1);
                        selectionsRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iSelectionsCallback != null ? iSelectionsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().suggestContentSelections(n, selectionsRequest, iSelectionsCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

