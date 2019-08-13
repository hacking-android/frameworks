/*
 * Decompiled with CFR 0.145.
 */
package android.service.settings.suggestions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.settings.suggestions.Suggestion;
import java.util.ArrayList;
import java.util.List;

public interface ISuggestionService
extends IInterface {
    public void dismissSuggestion(Suggestion var1) throws RemoteException;

    public List<Suggestion> getSuggestions() throws RemoteException;

    public void launchSuggestion(Suggestion var1) throws RemoteException;

    public static class Default
    implements ISuggestionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dismissSuggestion(Suggestion suggestion) throws RemoteException {
        }

        @Override
        public List<Suggestion> getSuggestions() throws RemoteException {
            return null;
        }

        @Override
        public void launchSuggestion(Suggestion suggestion) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISuggestionService {
        private static final String DESCRIPTOR = "android.service.settings.suggestions.ISuggestionService";
        static final int TRANSACTION_dismissSuggestion = 3;
        static final int TRANSACTION_getSuggestions = 2;
        static final int TRANSACTION_launchSuggestion = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISuggestionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISuggestionService) {
                return (ISuggestionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISuggestionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return null;
                    }
                    return "launchSuggestion";
                }
                return "dismissSuggestion";
            }
            return "getSuggestions";
        }

        public static boolean setDefaultImpl(ISuggestionService iSuggestionService) {
            if (Proxy.sDefaultImpl == null && iSuggestionService != null) {
                Proxy.sDefaultImpl = iSuggestionService;
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
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? Suggestion.CREATOR.createFromParcel((Parcel)object) : null;
                    this.launchSuggestion((Suggestion)object);
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? Suggestion.CREATOR.createFromParcel((Parcel)object) : null;
                this.dismissSuggestion((Suggestion)object);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getSuggestions();
            parcel.writeNoException();
            parcel.writeTypedList(object);
            return true;
        }

        private static class Proxy
        implements ISuggestionService {
            public static ISuggestionService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dismissSuggestion(Suggestion suggestion) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (suggestion != null) {
                        parcel.writeInt(1);
                        suggestion.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissSuggestion(suggestion);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public List<Suggestion> getSuggestions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<Suggestion> list = Stub.getDefaultImpl().getSuggestions();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<Suggestion> arrayList = parcel2.createTypedArrayList(Suggestion.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void launchSuggestion(Suggestion suggestion) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (suggestion != null) {
                        parcel.writeInt(1);
                        suggestion.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().launchSuggestion(suggestion);
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

