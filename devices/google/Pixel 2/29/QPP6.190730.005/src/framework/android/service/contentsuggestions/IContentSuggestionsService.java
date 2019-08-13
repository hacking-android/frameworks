/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.graphics.GraphicBuffer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IContentSuggestionsService
extends IInterface {
    public void classifyContentSelections(ClassificationsRequest var1, IClassificationsCallback var2) throws RemoteException;

    public void notifyInteraction(String var1, Bundle var2) throws RemoteException;

    public void provideContextImage(int var1, GraphicBuffer var2, int var3, Bundle var4) throws RemoteException;

    public void suggestContentSelections(SelectionsRequest var1, ISelectionsCallback var2) throws RemoteException;

    public static class Default
    implements IContentSuggestionsService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
        }

        @Override
        public void notifyInteraction(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void provideContextImage(int n, GraphicBuffer graphicBuffer, int n2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentSuggestionsService {
        private static final String DESCRIPTOR = "android.service.contentsuggestions.IContentSuggestionsService";
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentSuggestionsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentSuggestionsService) {
                return (IContentSuggestionsService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentSuggestionsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "notifyInteraction";
                    }
                    return "classifyContentSelections";
                }
                return "suggestContentSelections";
            }
            return "provideContextImage";
        }

        public static boolean setDefaultImpl(IContentSuggestionsService iContentSuggestionsService) {
            if (Proxy.sDefaultImpl == null && iContentSuggestionsService != null) {
                Proxy.sDefaultImpl = iContentSuggestionsService;
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
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                            }
                            ((Parcel)object2).writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyInteraction((String)object2, (Bundle)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object2 = ((Parcel)object).readInt() != 0 ? ClassificationsRequest.CREATOR.createFromParcel((Parcel)object) : null;
                    this.classifyContentSelections((ClassificationsRequest)object2, IClassificationsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? SelectionsRequest.CREATOR.createFromParcel((Parcel)object) : null;
                this.suggestContentSelections((SelectionsRequest)object2, ISelectionsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object2 = ((Parcel)object).readInt() != 0 ? GraphicBuffer.CREATOR.createFromParcel((Parcel)object) : null;
            n2 = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.provideContextImage(n, (GraphicBuffer)object2, n2, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IContentSuggestionsService {
            public static IContentSuggestionsService sDefaultImpl;
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
            public void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    Stub.getDefaultImpl().classifyContentSelections(classificationsRequest, iClassificationsCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void notifyInteraction(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyInteraction(string2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void provideContextImage(int n, GraphicBuffer graphicBuffer, int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (graphicBuffer != null) {
                        parcel.writeInt(1);
                        graphicBuffer.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().provideContextImage(n, graphicBuffer, n2, bundle);
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
            public void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    Stub.getDefaultImpl().suggestContentSelections(selectionsRequest, iSelectionsCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

