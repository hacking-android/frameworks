/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;

public interface IAccessibilityInteractionConnection
extends IInterface {
    public void clearAccessibilityFocus() throws RemoteException;

    public void findAccessibilityNodeInfoByAccessibilityId(long var1, Region var3, int var4, IAccessibilityInteractionConnectionCallback var5, int var6, int var7, long var8, MagnificationSpec var10, Bundle var11) throws RemoteException;

    public void findAccessibilityNodeInfosByText(long var1, String var3, Region var4, int var5, IAccessibilityInteractionConnectionCallback var6, int var7, int var8, long var9, MagnificationSpec var11) throws RemoteException;

    public void findAccessibilityNodeInfosByViewId(long var1, String var3, Region var4, int var5, IAccessibilityInteractionConnectionCallback var6, int var7, int var8, long var9, MagnificationSpec var11) throws RemoteException;

    public void findFocus(long var1, int var3, Region var4, int var5, IAccessibilityInteractionConnectionCallback var6, int var7, int var8, long var9, MagnificationSpec var11) throws RemoteException;

    public void focusSearch(long var1, int var3, Region var4, int var5, IAccessibilityInteractionConnectionCallback var6, int var7, int var8, long var9, MagnificationSpec var11) throws RemoteException;

    public void notifyOutsideTouch() throws RemoteException;

    public void performAccessibilityAction(long var1, int var3, Bundle var4, int var5, IAccessibilityInteractionConnectionCallback var6, int var7, int var8, long var9) throws RemoteException;

    public static class Default
    implements IAccessibilityInteractionConnection {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearAccessibilityFocus() throws RemoteException {
        }

        @Override
        public void findAccessibilityNodeInfoByAccessibilityId(long l, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec, Bundle bundle) throws RemoteException {
        }

        @Override
        public void findAccessibilityNodeInfosByText(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
        }

        @Override
        public void findAccessibilityNodeInfosByViewId(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
        }

        @Override
        public void findFocus(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
        }

        @Override
        public void focusSearch(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
        }

        @Override
        public void notifyOutsideTouch() throws RemoteException {
        }

        @Override
        public void performAccessibilityAction(long l, int n, Bundle bundle, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccessibilityInteractionConnection {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnection";
        static final int TRANSACTION_clearAccessibilityFocus = 7;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 1;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 2;
        static final int TRANSACTION_findFocus = 4;
        static final int TRANSACTION_focusSearch = 5;
        static final int TRANSACTION_notifyOutsideTouch = 8;
        static final int TRANSACTION_performAccessibilityAction = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccessibilityInteractionConnection) {
                return (IAccessibilityInteractionConnection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccessibilityInteractionConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "notifyOutsideTouch";
                }
                case 7: {
                    return "clearAccessibilityFocus";
                }
                case 6: {
                    return "performAccessibilityAction";
                }
                case 5: {
                    return "focusSearch";
                }
                case 4: {
                    return "findFocus";
                }
                case 3: {
                    return "findAccessibilityNodeInfosByText";
                }
                case 2: {
                    return "findAccessibilityNodeInfosByViewId";
                }
                case 1: 
            }
            return "findAccessibilityNodeInfoByAccessibilityId";
        }

        public static boolean setDefaultImpl(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
            if (Proxy.sDefaultImpl == null && iAccessibilityInteractionConnection != null) {
                Proxy.sDefaultImpl = iAccessibilityInteractionConnection;
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
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyOutsideTouch();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearAccessibilityFocus();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.performAccessibilityAction(l, n, (Bundle)object2, ((Parcel)object).readInt(), IAccessibilityInteractionConnectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        n2 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = IAccessibilityInteractionConnectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        int n3 = ((Parcel)object).readInt();
                        int n4 = ((Parcel)object).readInt();
                        long l2 = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? MagnificationSpec.CREATOR.createFromParcel((Parcel)object) : null;
                        this.focusSearch(l, n2, (Region)object2, n, iAccessibilityInteractionConnectionCallback, n3, n4, l2, (MagnificationSpec)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        n2 = ((Parcel)object).readInt();
                        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = IAccessibilityInteractionConnectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        int n5 = ((Parcel)object).readInt();
                        int n6 = ((Parcel)object).readInt();
                        long l3 = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? MagnificationSpec.CREATOR.createFromParcel((Parcel)object) : null;
                        this.findFocus(l, n, (Region)object2, n2, iAccessibilityInteractionConnectionCallback, n5, n6, l3, (MagnificationSpec)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = IAccessibilityInteractionConnectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n2 = ((Parcel)object).readInt();
                        int n7 = ((Parcel)object).readInt();
                        long l4 = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? MagnificationSpec.CREATOR.createFromParcel((Parcel)object) : null;
                        this.findAccessibilityNodeInfosByText(l, string2, (Region)object2, n, iAccessibilityInteractionConnectionCallback, n2, n7, l4, (MagnificationSpec)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        String string3 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                        int n8 = ((Parcel)object).readInt();
                        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = IAccessibilityInteractionConnectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        long l5 = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? MagnificationSpec.CREATOR.createFromParcel((Parcel)object) : null;
                        this.findAccessibilityNodeInfosByViewId(l, string3, (Region)object2, n8, iAccessibilityInteractionConnectionCallback, n, n2, l5, (MagnificationSpec)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                long l = ((Parcel)object).readLong();
                object2 = ((Parcel)object).readInt() != 0 ? Region.CREATOR.createFromParcel((Parcel)object) : null;
                int n9 = ((Parcel)object).readInt();
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = IAccessibilityInteractionConnectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                n = ((Parcel)object).readInt();
                n2 = ((Parcel)object).readInt();
                long l6 = ((Parcel)object).readLong();
                MagnificationSpec magnificationSpec = ((Parcel)object).readInt() != 0 ? MagnificationSpec.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.findAccessibilityNodeInfoByAccessibilityId(l, (Region)object2, n9, iAccessibilityInteractionConnectionCallback, n, n2, l6, magnificationSpec, (Bundle)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAccessibilityInteractionConnection {
            public static IAccessibilityInteractionConnection sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearAccessibilityFocus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearAccessibilityFocus();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void findAccessibilityNodeInfoByAccessibilityId(long var1_1, Region var3_2, int var4_7, IAccessibilityInteractionConnectionCallback var5_8, int var6_9, int var7_10, long var8_11, MagnificationSpec var10_12, Bundle var11_13) throws RemoteException {
                block18 : {
                    block19 : {
                        block20 : {
                            block17 : {
                                block16 : {
                                    block15 : {
                                        block14 : {
                                            block13 : {
                                                var12_14 = Parcel.obtain();
                                                var12_14.writeInterfaceToken("android.view.accessibility.IAccessibilityInteractionConnection");
                                                var12_14.writeLong(var1_1);
                                                if (var3_2 == null) break block13;
                                                try {
                                                    var12_14.writeInt(1);
                                                    var3_2.writeToParcel(var12_14, 0);
                                                    ** GOTO lbl15
                                                }
                                                catch (Throwable var3_3) {
                                                    break block18;
                                                }
                                            }
                                            var12_14.writeInt(0);
lbl15: // 2 sources:
                                            var12_14.writeInt(var4_7);
                                            if (var5_8 == null) break block14;
                                            var13_15 = var5_8.asBinder();
                                            break block15;
                                        }
                                        var13_15 = null;
                                    }
                                    var12_14.writeStrongBinder((IBinder)var13_15);
                                    var12_14.writeInt(var6_9);
                                    var12_14.writeInt(var7_10);
                                    var12_14.writeLong(var8_11);
                                    if (var10_12 == null) break block16;
                                    var12_14.writeInt(1);
                                    var10_12.writeToParcel(var12_14, 0);
                                    break block17;
                                }
                                var12_14.writeInt(0);
                            }
                            if (var11_13 == null) break block20;
                            var12_14.writeInt(1);
                            var11_13.writeToParcel(var12_14, 0);
                            ** GOTO lbl45
                        }
                        var12_14.writeInt(0);
lbl45: // 2 sources:
                        if (this.mRemote.transact(1, var12_14, null, 1) || Stub.getDefaultImpl() == null) break block19;
                        var13_15 = Stub.getDefaultImpl();
                        try {
                            var13_15.findAccessibilityNodeInfoByAccessibilityId(var1_1, var3_2, var4_7, var5_8, var6_9, var7_10, var8_11, var10_12, var11_13);
                            var12_14.recycle();
                            return;
                        }
                        catch (Throwable var3_4) {}
                        break block18;
                    }
                    var12_14.recycle();
                    return;
                    catch (Throwable var3_5) {
                        // empty catch block
                    }
                }
                var12_14.recycle();
                throw var3_6;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void findAccessibilityNodeInfosByText(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeLong(l2);
                    if (magnificationSpec != null) {
                        parcel.writeInt(1);
                        magnificationSpec.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().findAccessibilityNodeInfosByText(l, string2, region, n, iAccessibilityInteractionConnectionCallback, n2, n3, l2, magnificationSpec);
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
            public void findAccessibilityNodeInfosByViewId(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeLong(l2);
                    if (magnificationSpec != null) {
                        parcel.writeInt(1);
                        magnificationSpec.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().findAccessibilityNodeInfosByViewId(l, string2, region, n, iAccessibilityInteractionConnectionCallback, n2, n3, l2, magnificationSpec);
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
            public void findFocus(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    parcel.writeLong(l2);
                    if (magnificationSpec != null) {
                        parcel.writeInt(1);
                        magnificationSpec.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().findFocus(l, n, region, n2, iAccessibilityInteractionConnectionCallback, n3, n4, l2, magnificationSpec);
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
            public void focusSearch(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (region != null) {
                        parcel.writeInt(1);
                        region.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    parcel.writeLong(l2);
                    if (magnificationSpec != null) {
                        parcel.writeInt(1);
                        magnificationSpec.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().focusSearch(l, n, region, n2, iAccessibilityInteractionConnectionCallback, n3, n4, l2, magnificationSpec);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void notifyOutsideTouch() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyOutsideTouch();
                        return;
                    }
                    return;
                }
                finally {
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
            public void performAccessibilityAction(long l, int n, Bundle bundle, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2) throws RemoteException {
                Parcel parcel;
                void var4_6;
                block7 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeLong(l);
                        parcel.writeInt(n);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n2);
                        IBinder iBinder = iAccessibilityInteractionConnectionCallback != null ? iAccessibilityInteractionConnectionCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        parcel.writeLong(l2);
                        if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().performAccessibilityAction(l, n, bundle, n2, iAccessibilityInteractionConnectionCallback, n3, n4, l2);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block7;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var4_6;
            }
        }

    }

}

