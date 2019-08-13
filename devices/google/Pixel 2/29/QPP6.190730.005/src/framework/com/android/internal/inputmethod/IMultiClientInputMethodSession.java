/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import com.android.internal.view.IInputContext;

public interface IMultiClientInputMethodSession
extends IInterface {
    public void hideSoftInput(int var1, ResultReceiver var2) throws RemoteException;

    public void showSoftInput(int var1, ResultReceiver var2) throws RemoteException;

    public void startInputOrWindowGainedFocus(IInputContext var1, int var2, EditorInfo var3, int var4, int var5, int var6) throws RemoteException;

    public static class Default
    implements IMultiClientInputMethodSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void hideSoftInput(int n, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void showSoftInput(int n, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void startInputOrWindowGainedFocus(IInputContext iInputContext, int n, EditorInfo editorInfo, int n2, int n3, int n4) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMultiClientInputMethodSession {
        private static final String DESCRIPTOR = "com.android.internal.inputmethod.IMultiClientInputMethodSession";
        static final int TRANSACTION_hideSoftInput = 3;
        static final int TRANSACTION_showSoftInput = 2;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMultiClientInputMethodSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMultiClientInputMethodSession) {
                return (IMultiClientInputMethodSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMultiClientInputMethodSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "hideSoftInput";
                }
                return "showSoftInput";
            }
            return "startInputOrWindowGainedFocus";
        }

        public static boolean setDefaultImpl(IMultiClientInputMethodSession iMultiClientInputMethodSession) {
            if (Proxy.sDefaultImpl == null && iMultiClientInputMethodSession != null) {
                Proxy.sDefaultImpl = iMultiClientInputMethodSession;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                        }
                        ((Parcel)object2).writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = ((Parcel)object).readInt();
                    object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                    this.hideSoftInput(n, (ResultReceiver)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                this.showSoftInput(n, (ResultReceiver)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            IInputContext iInputContext = IInputContext.Stub.asInterface(((Parcel)object).readStrongBinder());
            n = ((Parcel)object).readInt();
            object2 = ((Parcel)object).readInt() != 0 ? EditorInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.startInputOrWindowGainedFocus(iInputContext, n, (EditorInfo)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements IMultiClientInputMethodSession {
            public static IMultiClientInputMethodSession sDefaultImpl;
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
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideSoftInput(n, resultReceiver);
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
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public void startInputOrWindowGainedFocus(IInputContext iInputContext, int n, EditorInfo editorInfo, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iInputContext != null ? iInputContext.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        try {
                            parcel.writeInt(n);
                            if (editorInfo != null) {
                                parcel.writeInt(1);
                                editorInfo.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startInputOrWindowGainedFocus(iInputContext, n, editorInfo, n2, n3, n4);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }
        }

    }

}

