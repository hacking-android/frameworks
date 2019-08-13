/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.IAccessibilityServiceConnection;
import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public interface IAccessibilityServiceClient
extends IInterface {
    public void clearAccessibilityCache() throws RemoteException;

    public void init(IAccessibilityServiceConnection var1, int var2, IBinder var3) throws RemoteException;

    public void onAccessibilityButtonAvailabilityChanged(boolean var1) throws RemoteException;

    public void onAccessibilityButtonClicked() throws RemoteException;

    public void onAccessibilityEvent(AccessibilityEvent var1, boolean var2) throws RemoteException;

    public void onFingerprintCapturingGesturesChanged(boolean var1) throws RemoteException;

    public void onFingerprintGesture(int var1) throws RemoteException;

    public void onGesture(int var1) throws RemoteException;

    public void onInterrupt() throws RemoteException;

    public void onKeyEvent(KeyEvent var1, int var2) throws RemoteException;

    public void onMagnificationChanged(int var1, Region var2, float var3, float var4, float var5) throws RemoteException;

    public void onPerformGestureResult(int var1, boolean var2) throws RemoteException;

    public void onSoftKeyboardShowModeChanged(int var1) throws RemoteException;

    public static class Default
    implements IAccessibilityServiceClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearAccessibilityCache() throws RemoteException {
        }

        @Override
        public void init(IAccessibilityServiceConnection iAccessibilityServiceConnection, int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void onAccessibilityButtonAvailabilityChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onAccessibilityButtonClicked() throws RemoteException {
        }

        @Override
        public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean bl) throws RemoteException {
        }

        @Override
        public void onFingerprintCapturingGesturesChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onFingerprintGesture(int n) throws RemoteException {
        }

        @Override
        public void onGesture(int n) throws RemoteException {
        }

        @Override
        public void onInterrupt() throws RemoteException {
        }

        @Override
        public void onKeyEvent(KeyEvent keyEvent, int n) throws RemoteException {
        }

        @Override
        public void onMagnificationChanged(int n, Region region, float f, float f2, float f3) throws RemoteException {
        }

        @Override
        public void onPerformGestureResult(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onSoftKeyboardShowModeChanged(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccessibilityServiceClient {
        private static final String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceClient";
        static final int TRANSACTION_clearAccessibilityCache = 5;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_onAccessibilityButtonAvailabilityChanged = 13;
        static final int TRANSACTION_onAccessibilityButtonClicked = 12;
        static final int TRANSACTION_onAccessibilityEvent = 2;
        static final int TRANSACTION_onFingerprintCapturingGesturesChanged = 10;
        static final int TRANSACTION_onFingerprintGesture = 11;
        static final int TRANSACTION_onGesture = 4;
        static final int TRANSACTION_onInterrupt = 3;
        static final int TRANSACTION_onKeyEvent = 6;
        static final int TRANSACTION_onMagnificationChanged = 7;
        static final int TRANSACTION_onPerformGestureResult = 9;
        static final int TRANSACTION_onSoftKeyboardShowModeChanged = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityServiceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccessibilityServiceClient) {
                return (IAccessibilityServiceClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccessibilityServiceClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 13: {
                    return "onAccessibilityButtonAvailabilityChanged";
                }
                case 12: {
                    return "onAccessibilityButtonClicked";
                }
                case 11: {
                    return "onFingerprintGesture";
                }
                case 10: {
                    return "onFingerprintCapturingGesturesChanged";
                }
                case 9: {
                    return "onPerformGestureResult";
                }
                case 8: {
                    return "onSoftKeyboardShowModeChanged";
                }
                case 7: {
                    return "onMagnificationChanged";
                }
                case 6: {
                    return "onKeyEvent";
                }
                case 5: {
                    return "clearAccessibilityCache";
                }
                case 4: {
                    return "onGesture";
                }
                case 3: {
                    return "onInterrupt";
                }
                case 2: {
                    return "onAccessibilityEvent";
                }
                case 1: 
            }
            return "init";
        }

        public static boolean setDefaultImpl(IAccessibilityServiceClient iAccessibilityServiceClient) {
            if (Proxy.sDefaultImpl == null && iAccessibilityServiceClient != null) {
                Proxy.sDefaultImpl = iAccessibilityServiceClient;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 13: {
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bl4 = true;
                        }
                        this.onAccessibilityButtonAvailabilityChanged(bl4);
                        return true;
                    }
                    case 12: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAccessibilityButtonClicked();
                        return true;
                    }
                    case 11: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onFingerprintGesture(parcel.readInt());
                        return true;
                    }
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        bl4 = bl;
                        if (parcel.readInt() != 0) {
                            bl4 = true;
                        }
                        this.onFingerprintCapturingGesturesChanged(bl4);
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        bl4 = bl2;
                        if (parcel.readInt() != 0) {
                            bl4 = true;
                        }
                        this.onPerformGestureResult(n, bl4);
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSoftKeyboardShowModeChanged(parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        object = parcel.readInt() != 0 ? Region.CREATOR.createFromParcel(parcel) : null;
                        this.onMagnificationChanged(n, (Region)object, parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(parcel) : null;
                        this.onKeyEvent((KeyEvent)object, parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.clearAccessibilityCache();
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onGesture(parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onInterrupt();
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? AccessibilityEvent.CREATOR.createFromParcel(parcel) : null;
                        bl4 = bl3;
                        if (parcel.readInt() != 0) {
                            bl4 = true;
                        }
                        this.onAccessibilityEvent((AccessibilityEvent)object, bl4);
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.init(IAccessibilityServiceConnection.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readStrongBinder());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAccessibilityServiceClient {
            public static IAccessibilityServiceClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearAccessibilityCache() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearAccessibilityCache();
                        return;
                    }
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
            public void init(IAccessibilityServiceConnection iAccessibilityServiceConnection, int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iAccessibilityServiceConnection != null ? iAccessibilityServiceConnection.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().init(iAccessibilityServiceConnection, n, iBinder);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAccessibilityButtonAvailabilityChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAccessibilityButtonAvailabilityChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAccessibilityButtonClicked() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAccessibilityButtonClicked();
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
            public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (accessibilityEvent != null) {
                        parcel.writeInt(1);
                        accessibilityEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onAccessibilityEvent(accessibilityEvent, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFingerprintCapturingGesturesChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFingerprintCapturingGesturesChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFingerprintGesture(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFingerprintGesture(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGesture(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGesture(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onInterrupt() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInterrupt();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onKeyEvent(KeyEvent keyEvent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onKeyEvent(keyEvent, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMagnificationChanged(int n, Region region, float f, float f2, float f3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeFloat(f);
                    parcel.writeFloat(f2);
                    parcel.writeFloat(f3);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMagnificationChanged(n, region, f, f2, f3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPerformGestureResult(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPerformGestureResult(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSoftKeyboardShowModeChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSoftKeyboardShowModeChanged(n);
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

