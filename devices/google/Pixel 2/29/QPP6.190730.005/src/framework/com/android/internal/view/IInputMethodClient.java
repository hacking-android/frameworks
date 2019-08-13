/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;
import com.android.internal.view.InputBindResult;

public interface IInputMethodClient
extends IInterface {
    public void applyImeVisibility(boolean var1) throws RemoteException;

    public void onBindMethod(InputBindResult var1) throws RemoteException;

    public void onUnbindMethod(int var1, int var2) throws RemoteException;

    public void reportFullscreenMode(boolean var1) throws RemoteException;

    public void reportPreRendered(EditorInfo var1) throws RemoteException;

    public void setActive(boolean var1, boolean var2) throws RemoteException;

    public void updateActivityViewToScreenMatrix(int var1, float[] var2) throws RemoteException;

    public static class Default
    implements IInputMethodClient {
        @Override
        public void applyImeVisibility(boolean bl) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onBindMethod(InputBindResult inputBindResult) throws RemoteException {
        }

        @Override
        public void onUnbindMethod(int n, int n2) throws RemoteException {
        }

        @Override
        public void reportFullscreenMode(boolean bl) throws RemoteException {
        }

        @Override
        public void reportPreRendered(EditorInfo editorInfo) throws RemoteException {
        }

        @Override
        public void setActive(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void updateActivityViewToScreenMatrix(int n, float[] arrf) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputMethodClient {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethodClient";
        static final int TRANSACTION_applyImeVisibility = 6;
        static final int TRANSACTION_onBindMethod = 1;
        static final int TRANSACTION_onUnbindMethod = 2;
        static final int TRANSACTION_reportFullscreenMode = 4;
        static final int TRANSACTION_reportPreRendered = 5;
        static final int TRANSACTION_setActive = 3;
        static final int TRANSACTION_updateActivityViewToScreenMatrix = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputMethodClient) {
                return (IInputMethodClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputMethodClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "updateActivityViewToScreenMatrix";
                }
                case 6: {
                    return "applyImeVisibility";
                }
                case 5: {
                    return "reportPreRendered";
                }
                case 4: {
                    return "reportFullscreenMode";
                }
                case 3: {
                    return "setActive";
                }
                case 2: {
                    return "onUnbindMethod";
                }
                case 1: 
            }
            return "onBindMethod";
        }

        public static boolean setDefaultImpl(IInputMethodClient iInputMethodClient) {
            if (Proxy.sDefaultImpl == null && iInputMethodClient != null) {
                Proxy.sDefaultImpl = iInputMethodClient;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateActivityViewToScreenMatrix(((Parcel)object).readInt(), ((Parcel)object).createFloatArray());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.applyImeVisibility(bl3);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? EditorInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.reportPreRendered((EditorInfo)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl3 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.reportFullscreenMode(bl3);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl3 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setActive(bl3, bl2);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onUnbindMethod(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? InputBindResult.CREATOR.createFromParcel((Parcel)object) : null;
                this.onBindMethod((InputBindResult)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputMethodClient {
            public static IInputMethodClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void applyImeVisibility(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyImeVisibility(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onBindMethod(InputBindResult inputBindResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputBindResult != null) {
                        parcel.writeInt(1);
                        inputBindResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBindMethod(inputBindResult);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUnbindMethod(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUnbindMethod(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void reportFullscreenMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportFullscreenMode(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void reportPreRendered(EditorInfo editorInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (editorInfo != null) {
                        parcel.writeInt(1);
                        editorInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportPreRendered(editorInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setActive(boolean bl, boolean bl2) throws RemoteException {
                int n;
                Parcel parcel;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActive(bl, bl2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateActivityViewToScreenMatrix(int n, float[] arrf) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeFloatArray(arrf);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateActivityViewToScreenMatrix(n, arrf);
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

