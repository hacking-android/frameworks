/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.VoiceInteractor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractorRequest;

public interface IVoiceInteractorCallback
extends IInterface {
    public void deliverAbortVoiceResult(IVoiceInteractorRequest var1, Bundle var2) throws RemoteException;

    public void deliverCancel(IVoiceInteractorRequest var1) throws RemoteException;

    public void deliverCommandResult(IVoiceInteractorRequest var1, boolean var2, Bundle var3) throws RemoteException;

    public void deliverCompleteVoiceResult(IVoiceInteractorRequest var1, Bundle var2) throws RemoteException;

    public void deliverConfirmationResult(IVoiceInteractorRequest var1, boolean var2, Bundle var3) throws RemoteException;

    public void deliverPickOptionResult(IVoiceInteractorRequest var1, boolean var2, VoiceInteractor.PickOptionRequest.Option[] var3, Bundle var4) throws RemoteException;

    public void destroy() throws RemoteException;

    public static class Default
    implements IVoiceInteractorCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
        }

        @Override
        public void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) throws RemoteException {
        }

        @Override
        public void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, Bundle bundle) throws RemoteException {
        }

        @Override
        public void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
        }

        @Override
        public void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, Bundle bundle) throws RemoteException {
        }

        @Override
        public void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) throws RemoteException {
        }

        @Override
        public void destroy() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractorCallback {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractorCallback";
        static final int TRANSACTION_deliverAbortVoiceResult = 4;
        static final int TRANSACTION_deliverCancel = 6;
        static final int TRANSACTION_deliverCommandResult = 5;
        static final int TRANSACTION_deliverCompleteVoiceResult = 3;
        static final int TRANSACTION_deliverConfirmationResult = 1;
        static final int TRANSACTION_deliverPickOptionResult = 2;
        static final int TRANSACTION_destroy = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractorCallback) {
                return (IVoiceInteractorCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractorCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "destroy";
                }
                case 6: {
                    return "deliverCancel";
                }
                case 5: {
                    return "deliverCommandResult";
                }
                case 4: {
                    return "deliverAbortVoiceResult";
                }
                case 3: {
                    return "deliverCompleteVoiceResult";
                }
                case 2: {
                    return "deliverPickOptionResult";
                }
                case 1: 
            }
            return "deliverConfirmationResult";
        }

        public static boolean setDefaultImpl(IVoiceInteractorCallback iVoiceInteractorCallback) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractorCallback != null) {
                Proxy.sDefaultImpl = iVoiceInteractorCallback;
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
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroy();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deliverCancel(IVoiceInteractorRequest.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IVoiceInteractorRequest.Stub.asInterface(((Parcel)object).readStrongBinder());
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deliverCommandResult((IVoiceInteractorRequest)object2, bl3, (Bundle)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IVoiceInteractorRequest.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deliverAbortVoiceResult((IVoiceInteractorRequest)object2, (Bundle)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IVoiceInteractorRequest.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deliverCompleteVoiceResult((IVoiceInteractorRequest)object2, (Bundle)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IVoiceInteractorRequest.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl3 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        VoiceInteractor.PickOptionRequest.Option[] arroption = ((Parcel)object).createTypedArray(VoiceInteractor.PickOptionRequest.Option.CREATOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deliverPickOptionResult((IVoiceInteractorRequest)object2, bl3, arroption, (Bundle)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = IVoiceInteractorRequest.Stub.asInterface(((Parcel)object).readStrongBinder());
                bl3 = bl2;
                if (((Parcel)object).readInt() != 0) {
                    bl3 = true;
                }
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.deliverConfirmationResult((IVoiceInteractorRequest)object2, bl3, (Bundle)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IVoiceInteractorCallback {
            public static IVoiceInteractorCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVoiceInteractorRequest != null ? iVoiceInteractorRequest.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().deliverAbortVoiceResult(iVoiceInteractorRequest, bundle);
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
            public void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVoiceInteractorRequest != null ? iVoiceInteractorRequest.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().deliverCancel(iVoiceInteractorRequest);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, Bundle bundle) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iVoiceInteractorRequest == null) break block10;
                    iBinder = iVoiceInteractorRequest.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deliverCommandResult(iVoiceInteractorRequest, bl, bundle);
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
            public void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVoiceInteractorRequest != null ? iVoiceInteractorRequest.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().deliverCompleteVoiceResult(iVoiceInteractorRequest, bundle);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, Bundle bundle) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iVoiceInteractorRequest == null) break block10;
                    iBinder = iVoiceInteractorRequest.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deliverConfirmationResult(iVoiceInteractorRequest, bl, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) throws RemoteException {
                IBinder iBinder;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iVoiceInteractorRequest == null) break block10;
                    iBinder = iVoiceInteractorRequest.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arroption, 0);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deliverPickOptionResult(iVoiceInteractorRequest, bl, arroption, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void destroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroy();
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
        }

    }

}

