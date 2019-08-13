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
import android.os.ResultReceiver;
import android.view.InputChannel;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethodSession;
import com.android.internal.view.IInputSessionCallback;

public interface IInputMethod
extends IInterface {
    public void bindInput(InputBinding var1) throws RemoteException;

    public void changeInputMethodSubtype(InputMethodSubtype var1) throws RemoteException;

    public void createSession(InputChannel var1, IInputSessionCallback var2) throws RemoteException;

    public void hideSoftInput(int var1, ResultReceiver var2) throws RemoteException;

    public void initializeInternal(IBinder var1, int var2, IInputMethodPrivilegedOperations var3) throws RemoteException;

    public void revokeSession(IInputMethodSession var1) throws RemoteException;

    public void setSessionEnabled(IInputMethodSession var1, boolean var2) throws RemoteException;

    public void showSoftInput(int var1, ResultReceiver var2) throws RemoteException;

    public void startInput(IBinder var1, IInputContext var2, int var3, EditorInfo var4, boolean var5, boolean var6) throws RemoteException;

    public void unbindInput() throws RemoteException;

    public static class Default
    implements IInputMethod {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void bindInput(InputBinding inputBinding) throws RemoteException {
        }

        @Override
        public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException {
        }

        @Override
        public void createSession(InputChannel inputChannel, IInputSessionCallback iInputSessionCallback) throws RemoteException {
        }

        @Override
        public void hideSoftInput(int n, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void initializeInternal(IBinder iBinder, int n, IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) throws RemoteException {
        }

        @Override
        public void revokeSession(IInputMethodSession iInputMethodSession) throws RemoteException {
        }

        @Override
        public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean bl) throws RemoteException {
        }

        @Override
        public void showSoftInput(int n, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void startInput(IBinder iBinder, IInputContext iInputContext, int n, EditorInfo editorInfo, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void unbindInput() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputMethod {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethod";
        static final int TRANSACTION_bindInput = 2;
        static final int TRANSACTION_changeInputMethodSubtype = 10;
        static final int TRANSACTION_createSession = 5;
        static final int TRANSACTION_hideSoftInput = 9;
        static final int TRANSACTION_initializeInternal = 1;
        static final int TRANSACTION_revokeSession = 7;
        static final int TRANSACTION_setSessionEnabled = 6;
        static final int TRANSACTION_showSoftInput = 8;
        static final int TRANSACTION_startInput = 4;
        static final int TRANSACTION_unbindInput = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethod asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputMethod) {
                return (IInputMethod)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputMethod getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "changeInputMethodSubtype";
                }
                case 9: {
                    return "hideSoftInput";
                }
                case 8: {
                    return "showSoftInput";
                }
                case 7: {
                    return "revokeSession";
                }
                case 6: {
                    return "setSessionEnabled";
                }
                case 5: {
                    return "createSession";
                }
                case 4: {
                    return "startInput";
                }
                case 3: {
                    return "unbindInput";
                }
                case 2: {
                    return "bindInput";
                }
                case 1: 
            }
            return "initializeInternal";
        }

        public static boolean setDefaultImpl(IInputMethod iInputMethod) {
            if (Proxy.sDefaultImpl == null && iInputMethod != null) {
                Proxy.sDefaultImpl = iInputMethod;
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
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? InputMethodSubtype.CREATOR.createFromParcel((Parcel)object) : null;
                        this.changeInputMethodSubtype((InputMethodSubtype)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        this.hideSoftInput(n, (ResultReceiver)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        this.showSoftInput(n, (ResultReceiver)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeSession(IInputMethodSession.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IInputMethodSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setSessionEnabled((IInputMethodSession)object2, bl);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? InputChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createSession((InputChannel)object2, IInputSessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        IInputContext iInputContext = IInputContext.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? EditorInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        bl = ((Parcel)object).readInt() != 0;
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        this.startInput(iBinder, iInputContext, n, (EditorInfo)object2, bl, bl2);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unbindInput();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? InputBinding.CREATOR.createFromParcel((Parcel)object) : null;
                        this.bindInput((InputBinding)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.initializeInternal(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), IInputMethodPrivilegedOperations.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputMethod {
            public static IInputMethod sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void bindInput(InputBinding inputBinding) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputBinding != null) {
                        parcel.writeInt(1);
                        inputBinding.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().bindInput(inputBinding);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputMethodSubtype != null) {
                        parcel.writeInt(1);
                        inputMethodSubtype.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().changeInputMethodSubtype(inputMethodSubtype);
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
            public void createSession(InputChannel inputChannel, IInputSessionCallback iInputSessionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputChannel != null) {
                        parcel.writeInt(1);
                        inputChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iInputSessionCallback != null ? iInputSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().createSession(inputChannel, iInputSessionCallback);
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
            public void hideSoftInput(int n, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideSoftInput(n, resultReceiver);
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
            public void initializeInternal(IBinder iBinder, int n, IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    IBinder iBinder2 = iInputMethodPrivilegedOperations != null ? iInputMethodPrivilegedOperations.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().initializeInternal(iBinder, n, iInputMethodPrivilegedOperations);
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
            public void revokeSession(IInputMethodSession iInputMethodSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodSession != null ? iInputMethodSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().revokeSession(iInputMethodSession);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean bl) throws RemoteException {
                IBinder iBinder;
                Parcel parcel;
                block7 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iInputMethodSession == null) break block7;
                    iBinder = iInputMethodSession.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSessionEnabled(iInputMethodSession, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showSoftInput(int n, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showSoftInput(n, resultReceiver);
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
            public void startInput(IBinder iBinder, IInputContext iInputContext, int n, EditorInfo editorInfo, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                void var1_6;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeStrongBinder(iBinder);
                        IBinder iBinder2 = iInputContext != null ? iInputContext.asBinder() : null;
                        parcel.writeStrongBinder(iBinder2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n);
                        int n2 = 0;
                        if (editorInfo != null) {
                            parcel.writeInt(1);
                            editorInfo.writeToParcel(parcel, 0);
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
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startInput(iBinder, iInputContext, n, editorInfo, bl, bl2);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_6;
            }

            @Override
            public void unbindInput() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbindInput();
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

