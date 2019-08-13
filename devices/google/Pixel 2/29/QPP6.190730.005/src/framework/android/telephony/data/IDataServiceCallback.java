/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.data.DataCallResponse;
import java.util.ArrayList;
import java.util.List;

public interface IDataServiceCallback
extends IInterface {
    public void onDataCallListChanged(List<DataCallResponse> var1) throws RemoteException;

    public void onDeactivateDataCallComplete(int var1) throws RemoteException;

    public void onRequestDataCallListComplete(int var1, List<DataCallResponse> var2) throws RemoteException;

    public void onSetDataProfileComplete(int var1) throws RemoteException;

    public void onSetInitialAttachApnComplete(int var1) throws RemoteException;

    public void onSetupDataCallComplete(int var1, DataCallResponse var2) throws RemoteException;

    public static class Default
    implements IDataServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDataCallListChanged(List<DataCallResponse> list) throws RemoteException {
        }

        @Override
        public void onDeactivateDataCallComplete(int n) throws RemoteException {
        }

        @Override
        public void onRequestDataCallListComplete(int n, List<DataCallResponse> list) throws RemoteException {
        }

        @Override
        public void onSetDataProfileComplete(int n) throws RemoteException {
        }

        @Override
        public void onSetInitialAttachApnComplete(int n) throws RemoteException {
        }

        @Override
        public void onSetupDataCallComplete(int n, DataCallResponse dataCallResponse) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDataServiceCallback {
        private static final String DESCRIPTOR = "android.telephony.data.IDataServiceCallback";
        static final int TRANSACTION_onDataCallListChanged = 6;
        static final int TRANSACTION_onDeactivateDataCallComplete = 2;
        static final int TRANSACTION_onRequestDataCallListComplete = 5;
        static final int TRANSACTION_onSetDataProfileComplete = 4;
        static final int TRANSACTION_onSetInitialAttachApnComplete = 3;
        static final int TRANSACTION_onSetupDataCallComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDataServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDataServiceCallback) {
                return (IDataServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDataServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "onDataCallListChanged";
                }
                case 5: {
                    return "onRequestDataCallListComplete";
                }
                case 4: {
                    return "onSetDataProfileComplete";
                }
                case 3: {
                    return "onSetInitialAttachApnComplete";
                }
                case 2: {
                    return "onDeactivateDataCallComplete";
                }
                case 1: 
            }
            return "onSetupDataCallComplete";
        }

        public static boolean setDefaultImpl(IDataServiceCallback iDataServiceCallback) {
            if (Proxy.sDefaultImpl == null && iDataServiceCallback != null) {
                Proxy.sDefaultImpl = iDataServiceCallback;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDataCallListChanged(((Parcel)object).createTypedArrayList(DataCallResponse.CREATOR));
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRequestDataCallListComplete(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(DataCallResponse.CREATOR));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSetDataProfileComplete(((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSetInitialAttachApnComplete(((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDeactivateDataCallComplete(((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? DataCallResponse.CREATOR.createFromParcel((Parcel)object) : null;
                this.onSetupDataCallComplete(n, (DataCallResponse)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IDataServiceCallback {
            public static IDataServiceCallback sDefaultImpl;
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
            public void onDataCallListChanged(List<DataCallResponse> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataCallListChanged(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeactivateDataCallComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeactivateDataCallComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRequestDataCallListComplete(int n, List<DataCallResponse> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRequestDataCallListComplete(n, list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSetDataProfileComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetDataProfileComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSetInitialAttachApnComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetInitialAttachApnComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSetupDataCallComplete(int n, DataCallResponse dataCallResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (dataCallResponse != null) {
                        parcel.writeInt(1);
                        dataCallResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetupDataCallComplete(n, dataCallResponse);
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

