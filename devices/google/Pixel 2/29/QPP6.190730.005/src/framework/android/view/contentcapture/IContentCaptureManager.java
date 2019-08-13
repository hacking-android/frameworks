/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.contentcapture.DataRemovalRequest;
import com.android.internal.os.IResultReceiver;

public interface IContentCaptureManager
extends IInterface {
    public void finishSession(int var1) throws RemoteException;

    public void getContentCaptureConditions(String var1, IResultReceiver var2) throws RemoteException;

    public void getServiceComponentName(IResultReceiver var1) throws RemoteException;

    public void getServiceSettingsActivity(IResultReceiver var1) throws RemoteException;

    public void isContentCaptureFeatureEnabled(IResultReceiver var1) throws RemoteException;

    public void removeData(DataRemovalRequest var1) throws RemoteException;

    public void startSession(IBinder var1, ComponentName var2, int var3, int var4, IResultReceiver var5) throws RemoteException;

    public static class Default
    implements IContentCaptureManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void finishSession(int n) throws RemoteException {
        }

        @Override
        public void getContentCaptureConditions(String string2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException {
        }

        @Override
        public void startSession(IBinder iBinder, ComponentName componentName, int n, int n2, IResultReceiver iResultReceiver) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentCaptureManager {
        private static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureManager";
        static final int TRANSACTION_finishSession = 2;
        static final int TRANSACTION_getContentCaptureConditions = 7;
        static final int TRANSACTION_getServiceComponentName = 3;
        static final int TRANSACTION_getServiceSettingsActivity = 6;
        static final int TRANSACTION_isContentCaptureFeatureEnabled = 5;
        static final int TRANSACTION_removeData = 4;
        static final int TRANSACTION_startSession = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentCaptureManager) {
                return (IContentCaptureManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentCaptureManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "getContentCaptureConditions";
                }
                case 6: {
                    return "getServiceSettingsActivity";
                }
                case 5: {
                    return "isContentCaptureFeatureEnabled";
                }
                case 4: {
                    return "removeData";
                }
                case 3: {
                    return "getServiceComponentName";
                }
                case 2: {
                    return "finishSession";
                }
                case 1: 
            }
            return "startSession";
        }

        public static boolean setDefaultImpl(IContentCaptureManager iContentCaptureManager) {
            if (Proxy.sDefaultImpl == null && iContentCaptureManager != null) {
                Proxy.sDefaultImpl = iContentCaptureManager;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getContentCaptureConditions(((Parcel)object).readString(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getServiceSettingsActivity(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.isContentCaptureFeatureEnabled(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? DataRemovalRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeData((DataRemovalRequest)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getServiceComponentName(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishSession(((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                object2 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                this.startSession(iBinder, (ComponentName)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IContentCaptureManager {
            public static IContentCaptureManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void finishSession(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishSession(n);
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
            public void getContentCaptureConditions(String string2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getContentCaptureConditions(string2, iResultReceiver);
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
            public void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getServiceComponentName(iResultReceiver);
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
            public void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getServiceSettingsActivity(iResultReceiver);
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
            public void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().isContentCaptureFeatureEnabled(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dataRemovalRequest != null) {
                        parcel.writeInt(1);
                        dataRemovalRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeData(dataRemovalRequest);
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
            public void startSession(IBinder iBinder, ComponentName componentName, int n, int n2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder2 = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startSession(iBinder, componentName, n, n2, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

