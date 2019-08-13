/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.app.PendingIntent;
import android.companion.AssociationRequest;
import android.companion.IFindDeviceCallback;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ICompanionDeviceManager
extends IInterface {
    public void associate(AssociationRequest var1, IFindDeviceCallback var2, String var3) throws RemoteException;

    public void disassociate(String var1, String var2) throws RemoteException;

    public List<String> getAssociations(String var1, int var2) throws RemoteException;

    public boolean hasNotificationAccess(ComponentName var1) throws RemoteException;

    public PendingIntent requestNotificationAccess(ComponentName var1) throws RemoteException;

    public void stopScan(AssociationRequest var1, IFindDeviceCallback var2, String var3) throws RemoteException;

    public static class Default
    implements ICompanionDeviceManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void associate(AssociationRequest associationRequest, IFindDeviceCallback iFindDeviceCallback, String string2) throws RemoteException {
        }

        @Override
        public void disassociate(String string2, String string3) throws RemoteException {
        }

        @Override
        public List<String> getAssociations(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasNotificationAccess(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public PendingIntent requestNotificationAccess(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public void stopScan(AssociationRequest associationRequest, IFindDeviceCallback iFindDeviceCallback, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICompanionDeviceManager {
        private static final String DESCRIPTOR = "android.companion.ICompanionDeviceManager";
        static final int TRANSACTION_associate = 1;
        static final int TRANSACTION_disassociate = 4;
        static final int TRANSACTION_getAssociations = 3;
        static final int TRANSACTION_hasNotificationAccess = 5;
        static final int TRANSACTION_requestNotificationAccess = 6;
        static final int TRANSACTION_stopScan = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICompanionDeviceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICompanionDeviceManager) {
                return (ICompanionDeviceManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICompanionDeviceManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "requestNotificationAccess";
                }
                case 5: {
                    return "hasNotificationAccess";
                }
                case 4: {
                    return "disassociate";
                }
                case 3: {
                    return "getAssociations";
                }
                case 2: {
                    return "stopScan";
                }
                case 1: 
            }
            return "associate";
        }

        public static boolean setDefaultImpl(ICompanionDeviceManager iCompanionDeviceManager) {
            if (Proxy.sDefaultImpl == null && iCompanionDeviceManager != null) {
                Proxy.sDefaultImpl = iCompanionDeviceManager;
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
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.requestNotificationAccess((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PendingIntent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hasNotificationAccess((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disassociate(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAssociations(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AssociationRequest associationRequest = ((Parcel)object).readInt() != 0 ? AssociationRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopScan(associationRequest, IFindDeviceCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                AssociationRequest associationRequest = ((Parcel)object).readInt() != 0 ? AssociationRequest.CREATOR.createFromParcel((Parcel)object) : null;
                this.associate(associationRequest, IFindDeviceCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ICompanionDeviceManager {
            public static ICompanionDeviceManager sDefaultImpl;
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
            public void associate(AssociationRequest associationRequest, IFindDeviceCallback iFindDeviceCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (associationRequest != null) {
                        parcel.writeInt(1);
                        associationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iFindDeviceCallback != null ? iFindDeviceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().associate(associationRequest, iFindDeviceCallback, string2);
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

            @Override
            public void disassociate(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disassociate(string2, string3);
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

            @Override
            public List<String> getAssociations(String list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)list));
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getAssociations((String)((Object)list), n);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createStringArrayList();
                    return list;
                }
                finally {
                    parcel2.recycle();
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
            public boolean hasNotificationAccess(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasNotificationAccess(componentName);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PendingIntent requestNotificationAccess(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        PendingIntent pendingIntent = Stub.getDefaultImpl().requestNotificationAccess((ComponentName)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return pendingIntent;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        PendingIntent pendingIntent = PendingIntent.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void stopScan(AssociationRequest associationRequest, IFindDeviceCallback iFindDeviceCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (associationRequest != null) {
                        parcel.writeInt(1);
                        associationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iFindDeviceCallback != null ? iFindDeviceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopScan(associationRequest, iFindDeviceCallback, string2);
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

