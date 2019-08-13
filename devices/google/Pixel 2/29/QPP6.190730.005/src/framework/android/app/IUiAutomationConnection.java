/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.InputEvent;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;

public interface IUiAutomationConnection
extends IInterface {
    public void adoptShellPermissionIdentity(int var1, String[] var2) throws RemoteException;

    public void clearWindowAnimationFrameStats() throws RemoteException;

    public boolean clearWindowContentFrameStats(int var1) throws RemoteException;

    public void connect(IAccessibilityServiceClient var1, int var2) throws RemoteException;

    public void disconnect() throws RemoteException;

    public void dropShellPermissionIdentity() throws RemoteException;

    public void executeShellCommand(String var1, ParcelFileDescriptor var2, ParcelFileDescriptor var3) throws RemoteException;

    public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException;

    public WindowContentFrameStats getWindowContentFrameStats(int var1) throws RemoteException;

    public void grantRuntimePermission(String var1, String var2, int var3) throws RemoteException;

    public boolean injectInputEvent(InputEvent var1, boolean var2) throws RemoteException;

    public void revokeRuntimePermission(String var1, String var2, int var3) throws RemoteException;

    public boolean setRotation(int var1) throws RemoteException;

    public void shutdown() throws RemoteException;

    public void syncInputTransactions() throws RemoteException;

    public Bitmap takeScreenshot(Rect var1, int var2) throws RemoteException;

    public static class Default
    implements IUiAutomationConnection {
        @Override
        public void adoptShellPermissionIdentity(int n, String[] arrstring) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearWindowAnimationFrameStats() throws RemoteException {
        }

        @Override
        public boolean clearWindowContentFrameStats(int n) throws RemoteException {
            return false;
        }

        @Override
        public void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int n) throws RemoteException {
        }

        @Override
        public void disconnect() throws RemoteException {
        }

        @Override
        public void dropShellPermissionIdentity() throws RemoteException {
        }

        @Override
        public void executeShellCommand(String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
        }

        @Override
        public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException {
            return null;
        }

        @Override
        public WindowContentFrameStats getWindowContentFrameStats(int n) throws RemoteException {
            return null;
        }

        @Override
        public void grantRuntimePermission(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public boolean injectInputEvent(InputEvent inputEvent, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void revokeRuntimePermission(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public boolean setRotation(int n) throws RemoteException {
            return false;
        }

        @Override
        public void shutdown() throws RemoteException {
        }

        @Override
        public void syncInputTransactions() throws RemoteException {
        }

        @Override
        public Bitmap takeScreenshot(Rect rect, int n) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUiAutomationConnection {
        private static final String DESCRIPTOR = "android.app.IUiAutomationConnection";
        static final int TRANSACTION_adoptShellPermissionIdentity = 14;
        static final int TRANSACTION_clearWindowAnimationFrameStats = 9;
        static final int TRANSACTION_clearWindowContentFrameStats = 7;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_dropShellPermissionIdentity = 15;
        static final int TRANSACTION_executeShellCommand = 11;
        static final int TRANSACTION_getWindowAnimationFrameStats = 10;
        static final int TRANSACTION_getWindowContentFrameStats = 8;
        static final int TRANSACTION_grantRuntimePermission = 12;
        static final int TRANSACTION_injectInputEvent = 3;
        static final int TRANSACTION_revokeRuntimePermission = 13;
        static final int TRANSACTION_setRotation = 5;
        static final int TRANSACTION_shutdown = 16;
        static final int TRANSACTION_syncInputTransactions = 4;
        static final int TRANSACTION_takeScreenshot = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUiAutomationConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUiAutomationConnection) {
                return (IUiAutomationConnection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUiAutomationConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "shutdown";
                }
                case 15: {
                    return "dropShellPermissionIdentity";
                }
                case 14: {
                    return "adoptShellPermissionIdentity";
                }
                case 13: {
                    return "revokeRuntimePermission";
                }
                case 12: {
                    return "grantRuntimePermission";
                }
                case 11: {
                    return "executeShellCommand";
                }
                case 10: {
                    return "getWindowAnimationFrameStats";
                }
                case 9: {
                    return "clearWindowAnimationFrameStats";
                }
                case 8: {
                    return "getWindowContentFrameStats";
                }
                case 7: {
                    return "clearWindowContentFrameStats";
                }
                case 6: {
                    return "takeScreenshot";
                }
                case 5: {
                    return "setRotation";
                }
                case 4: {
                    return "syncInputTransactions";
                }
                case 3: {
                    return "injectInputEvent";
                }
                case 2: {
                    return "disconnect";
                }
                case 1: 
            }
            return "connect";
        }

        public static boolean setDefaultImpl(IUiAutomationConnection iUiAutomationConnection) {
            if (Proxy.sDefaultImpl == null && iUiAutomationConnection != null) {
                Proxy.sDefaultImpl = iUiAutomationConnection;
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
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.shutdown();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dropShellPermissionIdentity();
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.adoptShellPermissionIdentity(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeRuntimePermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.grantRuntimePermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.executeShellCommand(string2, parcelFileDescriptor, (ParcelFileDescriptor)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWindowAnimationFrameStats();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WindowAnimationFrameStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearWindowAnimationFrameStats();
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWindowContentFrameStats(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WindowContentFrameStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.clearWindowContentFrameStats(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.takeScreenshot(rect, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bitmap)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setRotation(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.syncInputTransactions();
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        InputEvent inputEvent = ((Parcel)object).readInt() != 0 ? InputEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        n = this.injectInputEvent(inputEvent, bl) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disconnect();
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.connect(IAccessibilityServiceClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUiAutomationConnection {
            public static IUiAutomationConnection sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void adoptShellPermissionIdentity(int n, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adoptShellPermissionIdentity(n, arrstring);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearWindowAnimationFrameStats() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearWindowAnimationFrameStats();
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
            public boolean clearWindowContentFrameStats(int n) throws RemoteException {
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
                    if (iBinder.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().clearWindowContentFrameStats(n);
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
            public void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccessibilityServiceClient != null ? iAccessibilityServiceClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connect(iAccessibilityServiceClient, n);
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
            public void disconnect() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect();
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
            public void dropShellPermissionIdentity() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dropShellPermissionIdentity();
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
            public void executeShellCommand(String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor2 != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().executeShellCommand(string2, parcelFileDescriptor, parcelFileDescriptor2);
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
            public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        WindowAnimationFrameStats windowAnimationFrameStats = Stub.getDefaultImpl().getWindowAnimationFrameStats();
                        parcel.recycle();
                        parcel2.recycle();
                        return windowAnimationFrameStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                WindowAnimationFrameStats windowAnimationFrameStats = parcel.readInt() != 0 ? WindowAnimationFrameStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return windowAnimationFrameStats;
            }

            @Override
            public WindowContentFrameStats getWindowContentFrameStats(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        WindowContentFrameStats windowContentFrameStats = Stub.getDefaultImpl().getWindowContentFrameStats(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return windowContentFrameStats;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                WindowContentFrameStats windowContentFrameStats = parcel2.readInt() != 0 ? WindowContentFrameStats.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return windowContentFrameStats;
            }

            @Override
            public void grantRuntimePermission(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantRuntimePermission(string2, string3, n);
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
            public boolean injectInputEvent(InputEvent inputEvent, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (inputEvent != null) {
                        parcel.writeInt(1);
                        inputEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().injectInputEvent(inputEvent, bl);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    bl = n != 0 ? bl2 : false;
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

            @Override
            public void revokeRuntimePermission(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeRuntimePermission(string2, string3, n);
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
            public boolean setRotation(int n) throws RemoteException {
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
                    if (iBinder.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setRotation(n);
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

            @Override
            public void shutdown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void syncInputTransactions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().syncInputTransactions();
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
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Bitmap takeScreenshot(Rect parcelable, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Rect)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Bitmap bitmap = Stub.getDefaultImpl().takeScreenshot((Rect)parcelable, (int)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return bitmap;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Bitmap bitmap = Bitmap.CREATOR.createFromParcel(parcel2);
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
        }

    }

}

