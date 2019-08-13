/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IWindow;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityManagerClient;
import java.util.ArrayList;
import java.util.List;

public interface IAccessibilityManager
extends IInterface {
    public int addAccessibilityInteractionConnection(IWindow var1, IAccessibilityInteractionConnection var2, String var3, int var4) throws RemoteException;

    public long addClient(IAccessibilityManagerClient var1, int var2) throws RemoteException;

    public String getAccessibilityShortcutService() throws RemoteException;

    public int getAccessibilityWindowId(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int var1, int var2) throws RemoteException;

    public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int var1) throws RemoteException;

    public long getRecommendedTimeoutMillis() throws RemoteException;

    public IBinder getWindowToken(int var1, int var2) throws RemoteException;

    public void interrupt(int var1) throws RemoteException;

    public void notifyAccessibilityButtonClicked(int var1) throws RemoteException;

    public void notifyAccessibilityButtonVisibilityChanged(boolean var1) throws RemoteException;

    public void performAccessibilityShortcut() throws RemoteException;

    public void registerUiTestAutomationService(IBinder var1, IAccessibilityServiceClient var2, AccessibilityServiceInfo var3, int var4) throws RemoteException;

    public void removeAccessibilityInteractionConnection(IWindow var1) throws RemoteException;

    public void sendAccessibilityEvent(AccessibilityEvent var1, int var2) throws RemoteException;

    public boolean sendFingerprintGesture(int var1) throws RemoteException;

    public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection var1) throws RemoteException;

    public void temporaryEnableAccessibilityStateUntilKeyguardRemoved(ComponentName var1, boolean var2) throws RemoteException;

    public void unregisterUiTestAutomationService(IAccessibilityServiceClient var1) throws RemoteException;

    public static class Default
    implements IAccessibilityManager {
        @Override
        public int addAccessibilityInteractionConnection(IWindow iWindow, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getAccessibilityShortcutService() throws RemoteException {
            return null;
        }

        @Override
        public int getAccessibilityWindowId(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int n) throws RemoteException {
            return null;
        }

        @Override
        public long getRecommendedTimeoutMillis() throws RemoteException {
            return 0L;
        }

        @Override
        public IBinder getWindowToken(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void interrupt(int n) throws RemoteException {
        }

        @Override
        public void notifyAccessibilityButtonClicked(int n) throws RemoteException {
        }

        @Override
        public void notifyAccessibilityButtonVisibilityChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void performAccessibilityShortcut() throws RemoteException {
        }

        @Override
        public void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int n) throws RemoteException {
        }

        @Override
        public void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException {
        }

        @Override
        public void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int n) throws RemoteException {
        }

        @Override
        public boolean sendFingerprintGesture(int n) throws RemoteException {
            return false;
        }

        @Override
        public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException {
        }

        @Override
        public void temporaryEnableAccessibilityStateUntilKeyguardRemoved(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccessibilityManager {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityManager";
        static final int TRANSACTION_addAccessibilityInteractionConnection = 6;
        static final int TRANSACTION_addClient = 3;
        static final int TRANSACTION_getAccessibilityShortcutService = 16;
        static final int TRANSACTION_getAccessibilityWindowId = 18;
        static final int TRANSACTION_getEnabledAccessibilityServiceList = 5;
        static final int TRANSACTION_getInstalledAccessibilityServiceList = 4;
        static final int TRANSACTION_getRecommendedTimeoutMillis = 19;
        static final int TRANSACTION_getWindowToken = 12;
        static final int TRANSACTION_interrupt = 1;
        static final int TRANSACTION_notifyAccessibilityButtonClicked = 13;
        static final int TRANSACTION_notifyAccessibilityButtonVisibilityChanged = 14;
        static final int TRANSACTION_performAccessibilityShortcut = 15;
        static final int TRANSACTION_registerUiTestAutomationService = 9;
        static final int TRANSACTION_removeAccessibilityInteractionConnection = 7;
        static final int TRANSACTION_sendAccessibilityEvent = 2;
        static final int TRANSACTION_sendFingerprintGesture = 17;
        static final int TRANSACTION_setPictureInPictureActionReplacingConnection = 8;
        static final int TRANSACTION_temporaryEnableAccessibilityStateUntilKeyguardRemoved = 11;
        static final int TRANSACTION_unregisterUiTestAutomationService = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccessibilityManager) {
                return (IAccessibilityManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccessibilityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 19: {
                    return "getRecommendedTimeoutMillis";
                }
                case 18: {
                    return "getAccessibilityWindowId";
                }
                case 17: {
                    return "sendFingerprintGesture";
                }
                case 16: {
                    return "getAccessibilityShortcutService";
                }
                case 15: {
                    return "performAccessibilityShortcut";
                }
                case 14: {
                    return "notifyAccessibilityButtonVisibilityChanged";
                }
                case 13: {
                    return "notifyAccessibilityButtonClicked";
                }
                case 12: {
                    return "getWindowToken";
                }
                case 11: {
                    return "temporaryEnableAccessibilityStateUntilKeyguardRemoved";
                }
                case 10: {
                    return "unregisterUiTestAutomationService";
                }
                case 9: {
                    return "registerUiTestAutomationService";
                }
                case 8: {
                    return "setPictureInPictureActionReplacingConnection";
                }
                case 7: {
                    return "removeAccessibilityInteractionConnection";
                }
                case 6: {
                    return "addAccessibilityInteractionConnection";
                }
                case 5: {
                    return "getEnabledAccessibilityServiceList";
                }
                case 4: {
                    return "getInstalledAccessibilityServiceList";
                }
                case 3: {
                    return "addClient";
                }
                case 2: {
                    return "sendAccessibilityEvent";
                }
                case 1: 
            }
            return "interrupt";
        }

        public static boolean setDefaultImpl(IAccessibilityManager iAccessibilityManager) {
            if (Proxy.sDefaultImpl == null && iAccessibilityManager != null) {
                Proxy.sDefaultImpl = iAccessibilityManager;
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
        public boolean onTransact(int n, Parcel list, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)list), (Parcel)object, n2);
                    }
                    case 19: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        long l = this.getRecommendedTimeoutMillis();
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeLong(l);
                        return true;
                    }
                    case 18: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.getAccessibilityWindowId(((Parcel)((Object)list)).readStrongBinder());
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.sendFingerprintGesture(((Parcel)((Object)list)).readInt()) ? 1 : 0;
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getAccessibilityShortcutService();
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeString((String)((Object)list));
                        return true;
                    }
                    case 15: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        this.performAccessibilityShortcut();
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        if (((Parcel)((Object)list)).readInt() != 0) {
                            bl2 = true;
                        }
                        this.notifyAccessibilityButtonVisibilityChanged(bl2);
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        this.notifyAccessibilityButtonClicked(((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getWindowToken(((Parcel)((Object)list)).readInt(), ((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeStrongBinder((IBinder)((Object)list));
                        return true;
                    }
                    case 11: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)((Object)list)).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)((Object)list)) : null;
                        bl2 = bl;
                        if (((Parcel)((Object)list)).readInt() != 0) {
                            bl2 = true;
                        }
                        this.temporaryEnableAccessibilityStateUntilKeyguardRemoved(componentName, bl2);
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        this.unregisterUiTestAutomationService(IAccessibilityServiceClient.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()));
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)((Object)list)).readStrongBinder();
                        IAccessibilityServiceClient iAccessibilityServiceClient = IAccessibilityServiceClient.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder());
                        AccessibilityServiceInfo accessibilityServiceInfo = ((Parcel)((Object)list)).readInt() != 0 ? AccessibilityServiceInfo.CREATOR.createFromParcel((Parcel)((Object)list)) : null;
                        this.registerUiTestAutomationService(iBinder, iAccessibilityServiceClient, accessibilityServiceInfo, ((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        this.setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()));
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        this.removeAccessibilityInteractionConnection(IWindow.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()));
                        ((Parcel)object).writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        n = this.addAccessibilityInteractionConnection(IWindow.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()), IAccessibilityInteractionConnection.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()), ((Parcel)((Object)list)).readString(), ((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getEnabledAccessibilityServiceList(((Parcel)((Object)list)).readInt(), ((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeTypedList(list);
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        list = this.getInstalledAccessibilityServiceList(((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeTypedList(list);
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        long l = this.addClient(IAccessibilityManagerClient.Stub.asInterface(((Parcel)((Object)list)).readStrongBinder()), ((Parcel)((Object)list)).readInt());
                        ((Parcel)object).writeNoException();
                        ((Parcel)object).writeLong(l);
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)list)).readInt() != 0 ? AccessibilityEvent.CREATOR.createFromParcel((Parcel)((Object)list)) : null;
                        this.sendAccessibilityEvent((AccessibilityEvent)object, ((Parcel)((Object)list)).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)list)).enforceInterface(DESCRIPTOR);
                this.interrupt(((Parcel)((Object)list)).readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAccessibilityManager {
            public static IAccessibilityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int addAccessibilityInteractionConnection(IWindow iWindow, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var7_8 = null;
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var7_8;
                    if (iAccessibilityInteractionConnection != null) {
                        iBinder = iAccessibilityInteractionConnection.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().addAccessibilityInteractionConnection(iWindow, iAccessibilityInteractionConnection, string2, n);
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
            public long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccessibilityManagerClient != null ? iAccessibilityManagerClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().addClient(iAccessibilityManagerClient, n);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public String getAccessibilityShortcutService() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getAccessibilityShortcutService();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getAccessibilityWindowId(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getAccessibilityWindowId(iBinder);
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
            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AccessibilityServiceInfo> list = Stub.getDefaultImpl().getEnabledAccessibilityServiceList(n, n2);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AccessibilityServiceInfo> arrayList = parcel2.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AccessibilityServiceInfo> list = Stub.getDefaultImpl().getInstalledAccessibilityServiceList(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AccessibilityServiceInfo> arrayList = parcel2.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
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
            public long getRecommendedTimeoutMillis() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getRecommendedTimeoutMillis();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder getWindowToken(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IBinder iBinder = Stub.getDefaultImpl().getWindowToken(n, n2);
                        return iBinder;
                    }
                    parcel2.readException();
                    IBinder iBinder = parcel2.readStrongBinder();
                    return iBinder;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void interrupt(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interrupt(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyAccessibilityButtonClicked(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAccessibilityButtonClicked(n);
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
            public void notifyAccessibilityButtonVisibilityChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyAccessibilityButtonVisibilityChanged(bl);
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
            public void performAccessibilityShortcut() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performAccessibilityShortcut();
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
            public void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iAccessibilityServiceClient != null ? iAccessibilityServiceClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (accessibilityServiceInfo != null) {
                        parcel.writeInt(1);
                        accessibilityServiceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUiTestAutomationService(iBinder, iAccessibilityServiceClient, accessibilityServiceInfo, n);
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
            public void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWindow != null ? iWindow.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAccessibilityInteractionConnection(iWindow);
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
            public void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessibilityEvent != null) {
                        parcel.writeInt(1);
                        accessibilityEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendAccessibilityEvent(accessibilityEvent, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean sendFingerprintGesture(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().sendFingerprintGesture(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccessibilityInteractionConnection != null ? iAccessibilityInteractionConnection.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPictureInPictureActionReplacingConnection(iAccessibilityInteractionConnection);
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
            public void temporaryEnableAccessibilityStateUntilKeyguardRemoved(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().temporaryEnableAccessibilityStateUntilKeyguardRemoved(componentName, bl);
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
            public void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccessibilityServiceClient != null ? iAccessibilityServiceClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUiTestAutomationService(iAccessibilityServiceClient);
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

