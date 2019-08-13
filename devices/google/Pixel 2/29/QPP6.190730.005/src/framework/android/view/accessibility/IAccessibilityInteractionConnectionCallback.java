/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.List;

public interface IAccessibilityInteractionConnectionCallback
extends IInterface {
    @UnsupportedAppUsage
    public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setPerformAccessibilityActionResult(boolean var1, int var2) throws RemoteException;

    public static class Default
    implements IAccessibilityInteractionConnectionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int n) throws RemoteException {
        }

        @Override
        public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int n) throws RemoteException {
        }

        @Override
        public void setPerformAccessibilityActionResult(boolean bl, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccessibilityInteractionConnectionCallback {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnectionCallback";
        static final int TRANSACTION_setFindAccessibilityNodeInfoResult = 1;
        static final int TRANSACTION_setFindAccessibilityNodeInfosResult = 2;
        static final int TRANSACTION_setPerformAccessibilityActionResult = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccessibilityInteractionConnectionCallback) {
                return (IAccessibilityInteractionConnectionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccessibilityInteractionConnectionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "setPerformAccessibilityActionResult";
                }
                return "setFindAccessibilityNodeInfosResult";
            }
            return "setFindAccessibilityNodeInfoResult";
        }

        public static boolean setDefaultImpl(IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
            if (Proxy.sDefaultImpl == null && iAccessibilityInteractionConnectionCallback != null) {
                Proxy.sDefaultImpl = iAccessibilityInteractionConnectionCallback;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, (Parcel)object, n2);
                        }
                        ((Parcel)object).writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean bl = parcel.readInt() != 0;
                    this.setPerformAccessibilityActionResult(bl, parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.setFindAccessibilityNodeInfosResult(parcel.createTypedArrayList(AccessibilityNodeInfo.CREATOR), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? AccessibilityNodeInfo.CREATOR.createFromParcel(parcel) : null;
            this.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IAccessibilityInteractionConnectionCallback {
            public static IAccessibilityInteractionConnectionCallback sDefaultImpl;
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
            public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessibilityNodeInfo != null) {
                        parcel.writeInt(1);
                        accessibilityNodeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFindAccessibilityNodeInfoResult(accessibilityNodeInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFindAccessibilityNodeInfosResult(list, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPerformAccessibilityActionResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPerformAccessibilityActionResult(bl, n);
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

