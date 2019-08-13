/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.app.PendingIntent;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.ContextHubMessage;
import android.hardware.location.IContextHubCallback;
import android.hardware.location.IContextHubClient;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.IContextHubTransactionCallback;
import android.hardware.location.NanoApp;
import android.hardware.location.NanoAppBinary;
import android.hardware.location.NanoAppFilter;
import android.hardware.location.NanoAppInstanceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IContextHubService
extends IInterface {
    public IContextHubClient createClient(int var1, IContextHubClientCallback var2) throws RemoteException;

    public IContextHubClient createPendingIntentClient(int var1, PendingIntent var2, long var3) throws RemoteException;

    public void disableNanoApp(int var1, IContextHubTransactionCallback var2, long var3) throws RemoteException;

    public void enableNanoApp(int var1, IContextHubTransactionCallback var2, long var3) throws RemoteException;

    public int[] findNanoAppOnHub(int var1, NanoAppFilter var2) throws RemoteException;

    public int[] getContextHubHandles() throws RemoteException;

    public ContextHubInfo getContextHubInfo(int var1) throws RemoteException;

    public List<ContextHubInfo> getContextHubs() throws RemoteException;

    public NanoAppInstanceInfo getNanoAppInstanceInfo(int var1) throws RemoteException;

    public int loadNanoApp(int var1, NanoApp var2) throws RemoteException;

    public void loadNanoAppOnHub(int var1, IContextHubTransactionCallback var2, NanoAppBinary var3) throws RemoteException;

    public void queryNanoApps(int var1, IContextHubTransactionCallback var2) throws RemoteException;

    public int registerCallback(IContextHubCallback var1) throws RemoteException;

    public int sendMessage(int var1, int var2, ContextHubMessage var3) throws RemoteException;

    public int unloadNanoApp(int var1) throws RemoteException;

    public void unloadNanoAppFromHub(int var1, IContextHubTransactionCallback var2, long var3) throws RemoteException;

    public static class Default
    implements IContextHubService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public IContextHubClient createClient(int n, IContextHubClientCallback iContextHubClientCallback) throws RemoteException {
            return null;
        }

        @Override
        public IContextHubClient createPendingIntentClient(int n, PendingIntent pendingIntent, long l) throws RemoteException {
            return null;
        }

        @Override
        public void disableNanoApp(int n, IContextHubTransactionCallback iContextHubTransactionCallback, long l) throws RemoteException {
        }

        @Override
        public void enableNanoApp(int n, IContextHubTransactionCallback iContextHubTransactionCallback, long l) throws RemoteException {
        }

        @Override
        public int[] findNanoAppOnHub(int n, NanoAppFilter nanoAppFilter) throws RemoteException {
            return null;
        }

        @Override
        public int[] getContextHubHandles() throws RemoteException {
            return null;
        }

        @Override
        public ContextHubInfo getContextHubInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<ContextHubInfo> getContextHubs() throws RemoteException {
            return null;
        }

        @Override
        public NanoAppInstanceInfo getNanoAppInstanceInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public int loadNanoApp(int n, NanoApp nanoApp) throws RemoteException {
            return 0;
        }

        @Override
        public void loadNanoAppOnHub(int n, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException {
        }

        @Override
        public void queryNanoApps(int n, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
        }

        @Override
        public int registerCallback(IContextHubCallback iContextHubCallback) throws RemoteException {
            return 0;
        }

        @Override
        public int sendMessage(int n, int n2, ContextHubMessage contextHubMessage) throws RemoteException {
            return 0;
        }

        @Override
        public int unloadNanoApp(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void unloadNanoAppFromHub(int n, IContextHubTransactionCallback iContextHubTransactionCallback, long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContextHubService {
        private static final String DESCRIPTOR = "android.hardware.location.IContextHubService";
        static final int TRANSACTION_createClient = 9;
        static final int TRANSACTION_createPendingIntentClient = 10;
        static final int TRANSACTION_disableNanoApp = 15;
        static final int TRANSACTION_enableNanoApp = 14;
        static final int TRANSACTION_findNanoAppOnHub = 7;
        static final int TRANSACTION_getContextHubHandles = 2;
        static final int TRANSACTION_getContextHubInfo = 3;
        static final int TRANSACTION_getContextHubs = 11;
        static final int TRANSACTION_getNanoAppInstanceInfo = 6;
        static final int TRANSACTION_loadNanoApp = 4;
        static final int TRANSACTION_loadNanoAppOnHub = 12;
        static final int TRANSACTION_queryNanoApps = 16;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendMessage = 8;
        static final int TRANSACTION_unloadNanoApp = 5;
        static final int TRANSACTION_unloadNanoAppFromHub = 13;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContextHubService) {
                return (IContextHubService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContextHubService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "queryNanoApps";
                }
                case 15: {
                    return "disableNanoApp";
                }
                case 14: {
                    return "enableNanoApp";
                }
                case 13: {
                    return "unloadNanoAppFromHub";
                }
                case 12: {
                    return "loadNanoAppOnHub";
                }
                case 11: {
                    return "getContextHubs";
                }
                case 10: {
                    return "createPendingIntentClient";
                }
                case 9: {
                    return "createClient";
                }
                case 8: {
                    return "sendMessage";
                }
                case 7: {
                    return "findNanoAppOnHub";
                }
                case 6: {
                    return "getNanoAppInstanceInfo";
                }
                case 5: {
                    return "unloadNanoApp";
                }
                case 4: {
                    return "loadNanoApp";
                }
                case 3: {
                    return "getContextHubInfo";
                }
                case 2: {
                    return "getContextHubHandles";
                }
                case 1: 
            }
            return "registerCallback";
        }

        public static boolean setDefaultImpl(IContextHubService iContextHubService) {
            if (Proxy.sDefaultImpl == null && iContextHubService != null) {
                Proxy.sDefaultImpl = iContextHubService;
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
                Object object2 = null;
                IContextHubClient iContextHubClient = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.queryNanoApps(((Parcel)object).readInt(), IContextHubTransactionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableNanoApp(((Parcel)object).readInt(), IContextHubTransactionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableNanoApp(((Parcel)object).readInt(), IContextHubTransactionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unloadNanoAppFromHub(((Parcel)object).readInt(), IContextHubTransactionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = IContextHubTransactionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? NanoAppBinary.CREATOR.createFromParcel((Parcel)object) : null;
                        this.loadNanoAppOnHub(n, (IContextHubTransactionCallback)object2, (NanoAppBinary)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getContextHubs();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = this.createPendingIntentClient(n, (PendingIntent)object2, ((Parcel)object).readLong());
                        parcel.writeNoException();
                        object = iContextHubClient;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iContextHubClient = this.createClient(((Parcel)object).readInt(), IContextHubClientCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = object2;
                        if (iContextHubClient != null) {
                            object = iContextHubClient.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ContextHubMessage.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.sendMessage(n2, n, (ContextHubMessage)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? NanoAppFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.findNanoAppOnHub(n, (NanoAppFilter)object);
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNanoAppInstanceInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NanoAppInstanceInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.unloadNanoApp(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? NanoApp.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.loadNanoApp(n, (NanoApp)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getContextHubInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ContextHubInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getContextHubHandles();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.registerCallback(IContextHubCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IContextHubService {
            public static IContextHubService sDefaultImpl;
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
            public IContextHubClient createClient(int n, IContextHubClientCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IContextHubClient iContextHubClient = Stub.getDefaultImpl().createClient(n, (IContextHubClientCallback)iInterface);
                        return iContextHubClient;
                    }
                    parcel2.readException();
                    IContextHubClient iContextHubClient = IContextHubClient.Stub.asInterface(parcel2.readStrongBinder());
                    return iContextHubClient;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IContextHubClient createPendingIntentClient(int n, PendingIntent object, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((PendingIntent)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createPendingIntentClient(n, (PendingIntent)object, l);
                        return object;
                    }
                    parcel2.readException();
                    object = IContextHubClient.Stub.asInterface(parcel2.readStrongBinder());
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
            public void disableNanoApp(int n, IContextHubTransactionCallback iContextHubTransactionCallback, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iContextHubTransactionCallback != null ? iContextHubTransactionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableNanoApp(n, iContextHubTransactionCallback, l);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void enableNanoApp(int n, IContextHubTransactionCallback iContextHubTransactionCallback, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iContextHubTransactionCallback != null ? iContextHubTransactionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableNanoApp(n, iContextHubTransactionCallback, l);
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
            public int[] findNanoAppOnHub(int n, NanoAppFilter arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (arrn != null) {
                        parcel.writeInt(1);
                        arrn.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().findNanoAppOnHub(n, (NanoAppFilter)arrn);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getContextHubHandles() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getContextHubHandles();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ContextHubInfo getContextHubInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ContextHubInfo contextHubInfo = Stub.getDefaultImpl().getContextHubInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return contextHubInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ContextHubInfo contextHubInfo = parcel2.readInt() != 0 ? ContextHubInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return contextHubInfo;
            }

            @Override
            public List<ContextHubInfo> getContextHubs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ContextHubInfo> list = Stub.getDefaultImpl().getContextHubs();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ContextHubInfo> arrayList = parcel2.createTypedArrayList(ContextHubInfo.CREATOR);
                    return arrayList;
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
            public NanoAppInstanceInfo getNanoAppInstanceInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        NanoAppInstanceInfo nanoAppInstanceInfo = Stub.getDefaultImpl().getNanoAppInstanceInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return nanoAppInstanceInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                NanoAppInstanceInfo nanoAppInstanceInfo = parcel2.readInt() != 0 ? NanoAppInstanceInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return nanoAppInstanceInfo;
            }

            @Override
            public int loadNanoApp(int n, NanoApp nanoApp) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (nanoApp != null) {
                        parcel.writeInt(1);
                        nanoApp.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().loadNanoApp(n, nanoApp);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
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
            public void loadNanoAppOnHub(int n, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iContextHubTransactionCallback != null ? iContextHubTransactionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (nanoAppBinary != null) {
                        parcel.writeInt(1);
                        nanoAppBinary.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().loadNanoAppOnHub(n, iContextHubTransactionCallback, nanoAppBinary);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void queryNanoApps(int n, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iContextHubTransactionCallback != null ? iContextHubTransactionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().queryNanoApps(n, iContextHubTransactionCallback);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int registerCallback(IContextHubCallback iContextHubCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iContextHubCallback != null ? iContextHubCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().registerCallback(iContextHubCallback);
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
            public int sendMessage(int n, int n2, ContextHubMessage contextHubMessage) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (contextHubMessage != null) {
                        parcel.writeInt(1);
                        contextHubMessage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().sendMessage(n, n2, contextHubMessage);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int unloadNanoApp(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().unloadNanoApp(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
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
            public void unloadNanoAppFromHub(int n, IContextHubTransactionCallback iContextHubTransactionCallback, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iContextHubTransactionCallback != null ? iContextHubTransactionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unloadNanoAppFromHub(n, iContextHubTransactionCallback, l);
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

