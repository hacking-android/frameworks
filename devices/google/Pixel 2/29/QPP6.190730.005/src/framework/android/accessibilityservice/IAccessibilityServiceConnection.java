/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ParceledListSlice;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import java.util.ArrayList;
import java.util.List;

public interface IAccessibilityServiceConnection
extends IInterface {
    public void disableSelf() throws RemoteException;

    public String[] findAccessibilityNodeInfoByAccessibilityId(int var1, long var2, int var4, IAccessibilityInteractionConnectionCallback var5, int var6, long var7, Bundle var9) throws RemoteException;

    public String[] findAccessibilityNodeInfosByText(int var1, long var2, String var4, int var5, IAccessibilityInteractionConnectionCallback var6, long var7) throws RemoteException;

    public String[] findAccessibilityNodeInfosByViewId(int var1, long var2, String var4, int var5, IAccessibilityInteractionConnectionCallback var6, long var7) throws RemoteException;

    public String[] findFocus(int var1, long var2, int var4, int var5, IAccessibilityInteractionConnectionCallback var6, long var7) throws RemoteException;

    public String[] focusSearch(int var1, long var2, int var4, int var5, IAccessibilityInteractionConnectionCallback var6, long var7) throws RemoteException;

    public float getMagnificationCenterX(int var1) throws RemoteException;

    public float getMagnificationCenterY(int var1) throws RemoteException;

    public Region getMagnificationRegion(int var1) throws RemoteException;

    public float getMagnificationScale(int var1) throws RemoteException;

    public AccessibilityServiceInfo getServiceInfo() throws RemoteException;

    public int getSoftKeyboardShowMode() throws RemoteException;

    public AccessibilityWindowInfo getWindow(int var1) throws RemoteException;

    public List<AccessibilityWindowInfo> getWindows() throws RemoteException;

    public boolean isAccessibilityButtonAvailable() throws RemoteException;

    public boolean isFingerprintGestureDetectionAvailable() throws RemoteException;

    public boolean performAccessibilityAction(int var1, long var2, int var4, Bundle var5, int var6, IAccessibilityInteractionConnectionCallback var7, long var8) throws RemoteException;

    public boolean performGlobalAction(int var1) throws RemoteException;

    public boolean resetMagnification(int var1, boolean var2) throws RemoteException;

    public void sendGesture(int var1, ParceledListSlice var2) throws RemoteException;

    public void setMagnificationCallbackEnabled(int var1, boolean var2) throws RemoteException;

    public boolean setMagnificationScaleAndCenter(int var1, float var2, float var3, float var4, boolean var5) throws RemoteException;

    public void setOnKeyEventResult(boolean var1, int var2) throws RemoteException;

    public void setServiceInfo(AccessibilityServiceInfo var1) throws RemoteException;

    public void setSoftKeyboardCallbackEnabled(boolean var1) throws RemoteException;

    public boolean setSoftKeyboardShowMode(int var1) throws RemoteException;

    public static class Default
    implements IAccessibilityServiceConnection {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disableSelf() throws RemoteException {
        }

        @Override
        public String[] findAccessibilityNodeInfoByAccessibilityId(int n, long l, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, long l2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public String[] findAccessibilityNodeInfosByText(int n, long l, String string2, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
            return null;
        }

        @Override
        public String[] findAccessibilityNodeInfosByViewId(int n, long l, String string2, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
            return null;
        }

        @Override
        public String[] findFocus(int n, long l, int n2, int n3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
            return null;
        }

        @Override
        public String[] focusSearch(int n, long l, int n2, int n3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
            return null;
        }

        @Override
        public float getMagnificationCenterX(int n) throws RemoteException {
            return 0.0f;
        }

        @Override
        public float getMagnificationCenterY(int n) throws RemoteException {
            return 0.0f;
        }

        @Override
        public Region getMagnificationRegion(int n) throws RemoteException {
            return null;
        }

        @Override
        public float getMagnificationScale(int n) throws RemoteException {
            return 0.0f;
        }

        @Override
        public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
            return null;
        }

        @Override
        public int getSoftKeyboardShowMode() throws RemoteException {
            return 0;
        }

        @Override
        public AccessibilityWindowInfo getWindow(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<AccessibilityWindowInfo> getWindows() throws RemoteException {
            return null;
        }

        @Override
        public boolean isAccessibilityButtonAvailable() throws RemoteException {
            return false;
        }

        @Override
        public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
            return false;
        }

        @Override
        public boolean performAccessibilityAction(int n, long l, int n2, Bundle bundle, int n3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
            return false;
        }

        @Override
        public boolean performGlobalAction(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean resetMagnification(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void sendGesture(int n, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void setMagnificationCallbackEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setMagnificationScaleAndCenter(int n, float f, float f2, float f3, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setOnKeyEventResult(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
        }

        @Override
        public void setSoftKeyboardCallbackEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public boolean setSoftKeyboardShowMode(int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccessibilityServiceConnection {
        private static final String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceConnection";
        static final int TRANSACTION_disableSelf = 12;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 2;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 4;
        static final int TRANSACTION_findFocus = 5;
        static final int TRANSACTION_focusSearch = 6;
        static final int TRANSACTION_getMagnificationCenterX = 15;
        static final int TRANSACTION_getMagnificationCenterY = 16;
        static final int TRANSACTION_getMagnificationRegion = 17;
        static final int TRANSACTION_getMagnificationScale = 14;
        static final int TRANSACTION_getServiceInfo = 10;
        static final int TRANSACTION_getSoftKeyboardShowMode = 22;
        static final int TRANSACTION_getWindow = 8;
        static final int TRANSACTION_getWindows = 9;
        static final int TRANSACTION_isAccessibilityButtonAvailable = 24;
        static final int TRANSACTION_isFingerprintGestureDetectionAvailable = 26;
        static final int TRANSACTION_performAccessibilityAction = 7;
        static final int TRANSACTION_performGlobalAction = 11;
        static final int TRANSACTION_resetMagnification = 18;
        static final int TRANSACTION_sendGesture = 25;
        static final int TRANSACTION_setMagnificationCallbackEnabled = 20;
        static final int TRANSACTION_setMagnificationScaleAndCenter = 19;
        static final int TRANSACTION_setOnKeyEventResult = 13;
        static final int TRANSACTION_setServiceInfo = 1;
        static final int TRANSACTION_setSoftKeyboardCallbackEnabled = 23;
        static final int TRANSACTION_setSoftKeyboardShowMode = 21;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityServiceConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccessibilityServiceConnection) {
                return (IAccessibilityServiceConnection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccessibilityServiceConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 26: {
                    return "isFingerprintGestureDetectionAvailable";
                }
                case 25: {
                    return "sendGesture";
                }
                case 24: {
                    return "isAccessibilityButtonAvailable";
                }
                case 23: {
                    return "setSoftKeyboardCallbackEnabled";
                }
                case 22: {
                    return "getSoftKeyboardShowMode";
                }
                case 21: {
                    return "setSoftKeyboardShowMode";
                }
                case 20: {
                    return "setMagnificationCallbackEnabled";
                }
                case 19: {
                    return "setMagnificationScaleAndCenter";
                }
                case 18: {
                    return "resetMagnification";
                }
                case 17: {
                    return "getMagnificationRegion";
                }
                case 16: {
                    return "getMagnificationCenterY";
                }
                case 15: {
                    return "getMagnificationCenterX";
                }
                case 14: {
                    return "getMagnificationScale";
                }
                case 13: {
                    return "setOnKeyEventResult";
                }
                case 12: {
                    return "disableSelf";
                }
                case 11: {
                    return "performGlobalAction";
                }
                case 10: {
                    return "getServiceInfo";
                }
                case 9: {
                    return "getWindows";
                }
                case 8: {
                    return "getWindow";
                }
                case 7: {
                    return "performAccessibilityAction";
                }
                case 6: {
                    return "focusSearch";
                }
                case 5: {
                    return "findFocus";
                }
                case 4: {
                    return "findAccessibilityNodeInfosByViewId";
                }
                case 3: {
                    return "findAccessibilityNodeInfosByText";
                }
                case 2: {
                    return "findAccessibilityNodeInfoByAccessibilityId";
                }
                case 1: 
            }
            return "setServiceInfo";
        }

        public static boolean setDefaultImpl(IAccessibilityServiceConnection iAccessibilityServiceConnection) {
            if (Proxy.sDefaultImpl == null && iAccessibilityServiceConnection != null) {
                Proxy.sDefaultImpl = iAccessibilityServiceConnection;
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
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 26: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isFingerprintGestureDetectionAvailable() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        object.enforceInterface(DESCRIPTOR);
                        n = object.readInt();
                        object = object.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendGesture(n, (ParceledListSlice)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isAccessibilityButtonAvailable() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        object.enforceInterface(DESCRIPTOR);
                        if (object.readInt() != 0) {
                            bl4 = true;
                        }
                        this.setSoftKeyboardCallbackEnabled(bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.getSoftKeyboardShowMode();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.setSoftKeyboardShowMode(object.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        object.enforceInterface(DESCRIPTOR);
                        n = object.readInt();
                        bl4 = bl;
                        if (object.readInt() != 0) {
                            bl4 = true;
                        }
                        this.setMagnificationCallbackEnabled(n, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        object.enforceInterface(DESCRIPTOR);
                        n = object.readInt();
                        float f = object.readFloat();
                        float f2 = object.readFloat();
                        float f3 = object.readFloat();
                        bl4 = object.readInt() != 0;
                        n = this.setMagnificationScaleAndCenter(n, f, f2, f3, bl4) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        object.enforceInterface(DESCRIPTOR);
                        n = object.readInt();
                        bl4 = bl2;
                        if (object.readInt() != 0) {
                            bl4 = true;
                        }
                        n = this.resetMagnification(n, bl4) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getMagnificationRegion(object.readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 16: {
                        object.enforceInterface(DESCRIPTOR);
                        float f = this.getMagnificationCenterY(object.readInt());
                        parcel.writeNoException();
                        parcel.writeFloat(f);
                        return true;
                    }
                    case 15: {
                        object.enforceInterface(DESCRIPTOR);
                        float f = this.getMagnificationCenterX(object.readInt());
                        parcel.writeNoException();
                        parcel.writeFloat(f);
                        return true;
                    }
                    case 14: {
                        object.enforceInterface(DESCRIPTOR);
                        float f = this.getMagnificationScale(object.readInt());
                        parcel.writeNoException();
                        parcel.writeFloat(f);
                        return true;
                    }
                    case 13: {
                        object.enforceInterface(DESCRIPTOR);
                        bl4 = bl3;
                        if (object.readInt() != 0) {
                            bl4 = true;
                        }
                        this.setOnKeyEventResult(bl4, object.readInt());
                        return true;
                    }
                    case 12: {
                        object.enforceInterface(DESCRIPTOR);
                        this.disableSelf();
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.performGlobalAction(object.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getServiceInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getWindows();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 8: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getWindow(object.readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        n2 = object.readInt();
                        long l = object.readLong();
                        n = object.readInt();
                        Bundle bundle = object.readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.performAccessibilityAction(n2, l, n, bundle, object.readInt(), IAccessibilityInteractionConnectionCallback.Stub.asInterface(object.readStrongBinder()), object.readLong()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.focusSearch(object.readInt(), object.readLong(), object.readInt(), object.readInt(), IAccessibilityInteractionConnectionCallback.Stub.asInterface(object.readStrongBinder()), object.readLong());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.findFocus(object.readInt(), object.readLong(), object.readInt(), object.readInt(), IAccessibilityInteractionConnectionCallback.Stub.asInterface(object.readStrongBinder()), object.readLong());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.findAccessibilityNodeInfosByViewId(object.readInt(), object.readLong(), object.readString(), object.readInt(), IAccessibilityInteractionConnectionCallback.Stub.asInterface(object.readStrongBinder()), object.readLong());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.findAccessibilityNodeInfosByText(object.readInt(), object.readLong(), object.readString(), object.readInt(), IAccessibilityInteractionConnectionCallback.Stub.asInterface(object.readStrongBinder()), object.readLong());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        int n3 = object.readInt();
                        long l = object.readLong();
                        n = object.readInt();
                        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = IAccessibilityInteractionConnectionCallback.Stub.asInterface(object.readStrongBinder());
                        n2 = object.readInt();
                        long l2 = object.readLong();
                        object = object.readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.findAccessibilityNodeInfoByAccessibilityId(n3, l, n, iAccessibilityInteractionConnectionCallback, n2, l2, (Bundle)object);
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object = object.readInt() != 0 ? AccessibilityServiceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.setServiceInfo((AccessibilityServiceInfo)object);
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAccessibilityServiceConnection {
            public static IAccessibilityServiceConnection sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disableSelf() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableSelf();
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String[] findAccessibilityNodeInfoByAccessibilityId(int n, long l, int n2, IAccessibilityInteractionConnectionCallback arrstring, int n3, long l2, Bundle bundle) throws RemoteException {
                void var5_8;
                Parcel parcel2;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = arrstring != null ? arrstring.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeInt(n3);
                        parcel.writeLong(l2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrstring = Stub.getDefaultImpl().findAccessibilityNodeInfoByAccessibilityId(n, l, n2, (IAccessibilityInteractionConnectionCallback)arrstring, n3, l2, bundle);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrstring;
                        }
                        parcel2.readException();
                        arrstring = parcel2.createStringArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrstring;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var5_8;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String[] findAccessibilityNodeInfosByText(int n, long l, String arrstring, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
                Parcel parcel2;
                void var4_8;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeString((String)arrstring);
                        parcel.writeInt(n2);
                        IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l2);
                        if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrstring = Stub.getDefaultImpl().findAccessibilityNodeInfosByText(n, l, (String)arrstring, n2, iAccessibilityInteractionConnectionCallback, l2);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrstring;
                        }
                        parcel2.readException();
                        arrstring = parcel2.createStringArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrstring;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var4_8;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String[] findAccessibilityNodeInfosByViewId(int n, long l, String arrstring, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
                Parcel parcel2;
                void var4_8;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeString((String)arrstring);
                        parcel.writeInt(n2);
                        IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l2);
                        if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrstring = Stub.getDefaultImpl().findAccessibilityNodeInfosByViewId(n, l, (String)arrstring, n2, iAccessibilityInteractionConnectionCallback, l2);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrstring;
                        }
                        parcel2.readException();
                        arrstring = parcel2.createStringArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrstring;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var4_8;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String[] findFocus(int n, long l, int n2, int n3, IAccessibilityInteractionConnectionCallback arrstring, long l2) throws RemoteException {
                Parcel parcel2;
                void var6_10;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        IBinder iBinder = arrstring != null ? arrstring.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l2);
                        if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrstring = Stub.getDefaultImpl().findFocus(n, l, n2, n3, (IAccessibilityInteractionConnectionCallback)arrstring, l2);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrstring;
                        }
                        parcel2.readException();
                        arrstring = parcel2.createStringArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrstring;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var6_10;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String[] focusSearch(int n, long l, int n2, int n3, IAccessibilityInteractionConnectionCallback arrstring, long l2) throws RemoteException {
                Parcel parcel2;
                void var6_10;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        IBinder iBinder = arrstring != null ? arrstring.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l2);
                        if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrstring = Stub.getDefaultImpl().focusSearch(n, l, n2, n3, (IAccessibilityInteractionConnectionCallback)arrstring, l2);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrstring;
                        }
                        parcel2.readException();
                        arrstring = parcel2.createStringArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrstring;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var6_10;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public float getMagnificationCenterX(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float f = Stub.getDefaultImpl().getMagnificationCenterX(n);
                        return f;
                    }
                    parcel2.readException();
                    float f = parcel2.readFloat();
                    return f;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float getMagnificationCenterY(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float f = Stub.getDefaultImpl().getMagnificationCenterY(n);
                        return f;
                    }
                    parcel2.readException();
                    float f = parcel2.readFloat();
                    return f;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Region getMagnificationRegion(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(17, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Region region = Stub.getDefaultImpl().getMagnificationRegion(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return region;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Region region = parcel2.readInt() != 0 ? Region.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return region;
            }

            @Override
            public float getMagnificationScale(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float f = Stub.getDefaultImpl().getMagnificationScale(n);
                        return f;
                    }
                    parcel2.readException();
                    float f = parcel2.readFloat();
                    return f;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        AccessibilityServiceInfo accessibilityServiceInfo = Stub.getDefaultImpl().getServiceInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return accessibilityServiceInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                AccessibilityServiceInfo accessibilityServiceInfo = parcel.readInt() != 0 ? AccessibilityServiceInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return accessibilityServiceInfo;
            }

            @Override
            public int getSoftKeyboardShowMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getSoftKeyboardShowMode();
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
            public AccessibilityWindowInfo getWindow(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        AccessibilityWindowInfo accessibilityWindowInfo = Stub.getDefaultImpl().getWindow(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return accessibilityWindowInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                AccessibilityWindowInfo accessibilityWindowInfo = parcel2.readInt() != 0 ? AccessibilityWindowInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return accessibilityWindowInfo;
            }

            @Override
            public List<AccessibilityWindowInfo> getWindows() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AccessibilityWindowInfo> list = Stub.getDefaultImpl().getWindows();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<AccessibilityWindowInfo> arrayList = parcel2.createTypedArrayList(AccessibilityWindowInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isAccessibilityButtonAvailable() throws RemoteException {
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
                    if (iBinder.transact(24, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAccessibilityButtonAvailable();
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

            @Override
            public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
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
                    if (iBinder.transact(26, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isFingerprintGestureDetectionAvailable();
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean performAccessibilityAction(int n, long l, int n2, Bundle bundle, int n3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long l2) throws RemoteException {
                void var5_8;
                Parcel parcel2;
                Parcel parcel;
                block11 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                        boolean bl = true;
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n3);
                        IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l2);
                        if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().performAccessibilityAction(n, l, n2, bundle, n3, iAccessibilityInteractionConnectionCallback, l2);
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
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var5_8;
            }

            @Override
            public boolean performGlobalAction(int n) throws RemoteException {
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
                    if (iBinder.transact(11, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().performGlobalAction(n);
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
            public boolean resetMagnification(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(18, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().resetMagnification(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void sendGesture(int n, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendGesture(n, parceledListSlice);
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
            public void setMagnificationCallbackEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMagnificationCallbackEnabled(n, bl);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean setMagnificationScaleAndCenter(int n, float f, float f2, float f3, boolean bl) throws RemoteException {
                void var10_16;
                Parcel parcel2;
                Parcel parcel;
                block14 : {
                    boolean bl2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeFloat(f);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeFloat(f2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeFloat(f3);
                        bl2 = true;
                        int n2 = bl ? 1 : 0;
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(19, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().setMagnificationScaleAndCenter(n, f, f2, f3, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        bl = n != 0 ? bl2 : false;
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var10_16;
            }

            @Override
            public void setOnKeyEventResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnKeyEventResult(bl, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accessibilityServiceInfo != null) {
                        parcel.writeInt(1);
                        accessibilityServiceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setServiceInfo(accessibilityServiceInfo);
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
            public void setSoftKeyboardCallbackEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSoftKeyboardCallbackEnabled(bl);
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
            public boolean setSoftKeyboardShowMode(int n) throws RemoteException {
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
                    if (iBinder.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setSoftKeyboardShowMode(n);
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
        }

    }

}

