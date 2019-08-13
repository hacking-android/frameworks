/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IPinnedStackController;

public interface IPinnedStackListener
extends IInterface {
    public void onActionsChanged(ParceledListSlice var1) throws RemoteException;

    public void onImeVisibilityChanged(boolean var1, int var2) throws RemoteException;

    public void onListenerRegistered(IPinnedStackController var1) throws RemoteException;

    public void onMinimizedStateChanged(boolean var1) throws RemoteException;

    public void onMovementBoundsChanged(Rect var1, Rect var2, Rect var3, boolean var4, boolean var5, int var6) throws RemoteException;

    public void onShelfVisibilityChanged(boolean var1, int var2) throws RemoteException;

    public static class Default
    implements IPinnedStackListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActionsChanged(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void onImeVisibilityChanged(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void onListenerRegistered(IPinnedStackController iPinnedStackController) throws RemoteException {
        }

        @Override
        public void onMinimizedStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onMovementBoundsChanged(Rect rect, Rect rect2, Rect rect3, boolean bl, boolean bl2, int n) throws RemoteException {
        }

        @Override
        public void onShelfVisibilityChanged(boolean bl, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPinnedStackListener {
        private static final String DESCRIPTOR = "android.view.IPinnedStackListener";
        static final int TRANSACTION_onActionsChanged = 6;
        static final int TRANSACTION_onImeVisibilityChanged = 3;
        static final int TRANSACTION_onListenerRegistered = 1;
        static final int TRANSACTION_onMinimizedStateChanged = 5;
        static final int TRANSACTION_onMovementBoundsChanged = 2;
        static final int TRANSACTION_onShelfVisibilityChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPinnedStackListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPinnedStackListener) {
                return (IPinnedStackListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPinnedStackListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "onActionsChanged";
                }
                case 5: {
                    return "onMinimizedStateChanged";
                }
                case 4: {
                    return "onShelfVisibilityChanged";
                }
                case 3: {
                    return "onImeVisibilityChanged";
                }
                case 2: {
                    return "onMovementBoundsChanged";
                }
                case 1: 
            }
            return "onListenerRegistered";
        }

        public static boolean setDefaultImpl(IPinnedStackListener iPinnedStackListener) {
            if (Proxy.sDefaultImpl == null && iPinnedStackListener != null) {
                Proxy.sDefaultImpl = iPinnedStackListener;
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
        public boolean onTransact(int n, Parcel parceledListSlice, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)parceledListSlice), (Parcel)object, n2);
                    }
                    case 6: {
                        ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                        parceledListSlice = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
                        this.onActionsChanged(parceledListSlice);
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                        if (((Parcel)((Object)parceledListSlice)).readInt() != 0) {
                            bl3 = true;
                        }
                        this.onMinimizedStateChanged(bl3);
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                        bl3 = bl;
                        if (((Parcel)((Object)parceledListSlice)).readInt() != 0) {
                            bl3 = true;
                        }
                        this.onShelfVisibilityChanged(bl3, ((Parcel)((Object)parceledListSlice)).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                        bl3 = bl2;
                        if (((Parcel)((Object)parceledListSlice)).readInt() != 0) {
                            bl3 = true;
                        }
                        this.onImeVisibilityChanged(bl3, ((Parcel)((Object)parceledListSlice)).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
                        Rect rect = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
                        Rect rect2 = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
                        bl3 = ((Parcel)((Object)parceledListSlice)).readInt() != 0;
                        bl = ((Parcel)((Object)parceledListSlice)).readInt() != 0;
                        this.onMovementBoundsChanged((Rect)object, rect, rect2, bl3, bl, ((Parcel)((Object)parceledListSlice)).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                this.onListenerRegistered(IPinnedStackController.Stub.asInterface(((Parcel)((Object)parceledListSlice)).readStrongBinder()));
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPinnedStackListener {
            public static IPinnedStackListener sDefaultImpl;
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
            public void onActionsChanged(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActionsChanged(parceledListSlice);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onImeVisibilityChanged(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onImeVisibilityChanged(bl, n);
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
            public void onListenerRegistered(IPinnedStackController iPinnedStackController) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPinnedStackController != null ? iPinnedStackController.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onListenerRegistered(iPinnedStackController);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMinimizedStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMinimizedStateChanged(bl);
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
            public void onMovementBoundsChanged(Rect rect, Rect rect2, Rect rect3, boolean bl, boolean bl2, int n) throws RemoteException {
                Parcel parcel;
                void var1_5;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect2 != null) {
                        parcel.writeInt(1);
                        rect2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect3 != null) {
                        parcel.writeInt(1);
                        rect3.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n3 = bl ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = n2;
                    if (bl2) {
                        n3 = 1;
                    }
                    parcel.writeInt(n3);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onMovementBoundsChanged(rect, rect2, rect3, bl, bl2, n);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public void onShelfVisibilityChanged(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShelfVisibilityChanged(bl, n);
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

