/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.content.ComponentName;
import android.nfc.cardemulation.NfcFServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface INfcFCardEmulation
extends IInterface {
    public boolean disableNfcFForegroundService() throws RemoteException;

    public boolean enableNfcFForegroundService(ComponentName var1) throws RemoteException;

    public int getMaxNumOfRegisterableSystemCodes() throws RemoteException;

    public List<NfcFServiceInfo> getNfcFServices(int var1) throws RemoteException;

    public String getNfcid2ForService(int var1, ComponentName var2) throws RemoteException;

    public String getSystemCodeForService(int var1, ComponentName var2) throws RemoteException;

    public boolean registerSystemCodeForService(int var1, ComponentName var2, String var3) throws RemoteException;

    public boolean removeSystemCodeForService(int var1, ComponentName var2) throws RemoteException;

    public boolean setNfcid2ForService(int var1, ComponentName var2, String var3) throws RemoteException;

    public static class Default
    implements INfcFCardEmulation {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean disableNfcFForegroundService() throws RemoteException {
            return false;
        }

        @Override
        public boolean enableNfcFForegroundService(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public int getMaxNumOfRegisterableSystemCodes() throws RemoteException {
            return 0;
        }

        @Override
        public List<NfcFServiceInfo> getNfcFServices(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getNfcid2ForService(int n, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public String getSystemCodeForService(int n, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public boolean registerSystemCodeForService(int n, ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeSystemCodeForService(int n, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean setNfcid2ForService(int n, ComponentName componentName, String string2) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INfcFCardEmulation {
        private static final String DESCRIPTOR = "android.nfc.INfcFCardEmulation";
        static final int TRANSACTION_disableNfcFForegroundService = 7;
        static final int TRANSACTION_enableNfcFForegroundService = 6;
        static final int TRANSACTION_getMaxNumOfRegisterableSystemCodes = 9;
        static final int TRANSACTION_getNfcFServices = 8;
        static final int TRANSACTION_getNfcid2ForService = 4;
        static final int TRANSACTION_getSystemCodeForService = 1;
        static final int TRANSACTION_registerSystemCodeForService = 2;
        static final int TRANSACTION_removeSystemCodeForService = 3;
        static final int TRANSACTION_setNfcid2ForService = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INfcFCardEmulation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INfcFCardEmulation) {
                return (INfcFCardEmulation)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INfcFCardEmulation getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "getMaxNumOfRegisterableSystemCodes";
                }
                case 8: {
                    return "getNfcFServices";
                }
                case 7: {
                    return "disableNfcFForegroundService";
                }
                case 6: {
                    return "enableNfcFForegroundService";
                }
                case 5: {
                    return "setNfcid2ForService";
                }
                case 4: {
                    return "getNfcid2ForService";
                }
                case 3: {
                    return "removeSystemCodeForService";
                }
                case 2: {
                    return "registerSystemCodeForService";
                }
                case 1: 
            }
            return "getSystemCodeForService";
        }

        public static boolean setDefaultImpl(INfcFCardEmulation iNfcFCardEmulation) {
            if (Proxy.sDefaultImpl == null && iNfcFCardEmulation != null) {
                Proxy.sDefaultImpl = iNfcFCardEmulation;
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
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMaxNumOfRegisterableSystemCodes();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNfcFServices(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disableNfcFForegroundService() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.enableNfcFForegroundService((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setNfcid2ForService(n, componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getNfcid2ForService(n, (ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeSystemCodeForService(n, (ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.registerSystemCodeForService(n, componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.getSystemCodeForService(n, (ComponentName)object);
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INfcFCardEmulation {
            public static INfcFCardEmulation sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean disableNfcFForegroundService() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableNfcFForegroundService();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean enableNfcFForegroundService(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().enableNfcFForegroundService(componentName);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getMaxNumOfRegisterableSystemCodes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMaxNumOfRegisterableSystemCodes();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<NfcFServiceInfo> getNfcFServices(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<NfcFServiceInfo> list = Stub.getDefaultImpl().getNfcFServices(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<NfcFServiceInfo> arrayList = parcel2.createTypedArrayList(NfcFServiceInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getNfcid2ForService(int n, ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getNfcid2ForService(n, (ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSystemCodeForService(int n, ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getSystemCodeForService(n, (ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean registerSystemCodeForService(int n, ComponentName componentName, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().registerSystemCodeForService(n, componentName, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean removeSystemCodeForService(int n, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeSystemCodeForService(n, componentName);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setNfcid2ForService(int n, ComponentName componentName, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setNfcid2ForService(n, componentName, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
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
        }

    }

}

