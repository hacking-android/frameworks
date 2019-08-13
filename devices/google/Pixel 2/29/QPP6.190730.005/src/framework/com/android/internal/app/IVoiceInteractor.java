/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.VoiceInteractor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractorCallback;
import com.android.internal.app.IVoiceInteractorRequest;

public interface IVoiceInteractor
extends IInterface {
    public void notifyDirectActionsChanged(int var1, IBinder var2) throws RemoteException;

    public void setKillCallback(ICancellationSignal var1) throws RemoteException;

    public IVoiceInteractorRequest startAbortVoice(String var1, IVoiceInteractorCallback var2, VoiceInteractor.Prompt var3, Bundle var4) throws RemoteException;

    public IVoiceInteractorRequest startCommand(String var1, IVoiceInteractorCallback var2, String var3, Bundle var4) throws RemoteException;

    public IVoiceInteractorRequest startCompleteVoice(String var1, IVoiceInteractorCallback var2, VoiceInteractor.Prompt var3, Bundle var4) throws RemoteException;

    public IVoiceInteractorRequest startConfirmation(String var1, IVoiceInteractorCallback var2, VoiceInteractor.Prompt var3, Bundle var4) throws RemoteException;

    public IVoiceInteractorRequest startPickOption(String var1, IVoiceInteractorCallback var2, VoiceInteractor.Prompt var3, VoiceInteractor.PickOptionRequest.Option[] var4, Bundle var5) throws RemoteException;

    public boolean[] supportsCommands(String var1, String[] var2) throws RemoteException;

    public static class Default
    implements IVoiceInteractor {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyDirectActionsChanged(int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void setKillCallback(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override
        public IVoiceInteractorRequest startAbortVoice(String string2, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public IVoiceInteractorRequest startCommand(String string2, IVoiceInteractorCallback iVoiceInteractorCallback, String string3, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public IVoiceInteractorRequest startCompleteVoice(String string2, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public IVoiceInteractorRequest startConfirmation(String string2, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public IVoiceInteractorRequest startPickOption(String string2, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public boolean[] supportsCommands(String string2, String[] arrstring) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractor {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractor";
        static final int TRANSACTION_notifyDirectActionsChanged = 7;
        static final int TRANSACTION_setKillCallback = 8;
        static final int TRANSACTION_startAbortVoice = 4;
        static final int TRANSACTION_startCommand = 5;
        static final int TRANSACTION_startCompleteVoice = 3;
        static final int TRANSACTION_startConfirmation = 1;
        static final int TRANSACTION_startPickOption = 2;
        static final int TRANSACTION_supportsCommands = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractor) {
                return (IVoiceInteractor)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractor getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "setKillCallback";
                }
                case 7: {
                    return "notifyDirectActionsChanged";
                }
                case 6: {
                    return "supportsCommands";
                }
                case 5: {
                    return "startCommand";
                }
                case 4: {
                    return "startAbortVoice";
                }
                case 3: {
                    return "startCompleteVoice";
                }
                case 2: {
                    return "startPickOption";
                }
                case 1: 
            }
            return "startConfirmation";
        }

        public static boolean setDefaultImpl(IVoiceInteractor iVoiceInteractor) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractor != null) {
                Proxy.sDefaultImpl = iVoiceInteractor;
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
                Object object2 = null;
                Object object3 = null;
                Object object4 = null;
                VoiceInteractor.PickOptionRequest.Option[] arroption = null;
                Object object5 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setKillCallback(ICancellationSignal.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDirectActionsChanged(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.supportsCommands(((Parcel)object).readString(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        parcel.writeBooleanArray((boolean[])object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readString();
                        object4 = IVoiceInteractorCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object4 = this.startCommand((String)object3, (IVoiceInteractorCallback)object4, (String)object2, (Bundle)object);
                        parcel.writeNoException();
                        object = object5;
                        if (object4 != null) {
                            object = object4.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readString();
                        object4 = IVoiceInteractorCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object5 = ((Parcel)object).readInt() != 0 ? VoiceInteractor.Prompt.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object5 = this.startAbortVoice((String)object3, (IVoiceInteractorCallback)object4, (VoiceInteractor.Prompt)object5, (Bundle)object);
                        parcel.writeNoException();
                        object = object2;
                        if (object5 != null) {
                            object = object5.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object4 = IVoiceInteractorCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object5 = ((Parcel)object).readInt() != 0 ? VoiceInteractor.Prompt.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object5 = this.startCompleteVoice((String)object2, (IVoiceInteractorCallback)object4, (VoiceInteractor.Prompt)object5, (Bundle)object);
                        parcel.writeNoException();
                        object = object3;
                        if (object5 != null) {
                            object = object5.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object3 = IVoiceInteractorCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object5 = ((Parcel)object).readInt() != 0 ? VoiceInteractor.Prompt.CREATOR.createFromParcel((Parcel)object) : null;
                        arroption = ((Parcel)object).createTypedArray(VoiceInteractor.PickOptionRequest.Option.CREATOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object5 = this.startPickOption((String)object2, (IVoiceInteractorCallback)object3, (VoiceInteractor.Prompt)object5, arroption, (Bundle)object);
                        parcel.writeNoException();
                        object = object4;
                        if (object5 != null) {
                            object = object5.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object4 = ((Parcel)object).readString();
                object2 = IVoiceInteractorCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                object5 = ((Parcel)object).readInt() != 0 ? VoiceInteractor.Prompt.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                object5 = this.startConfirmation((String)object4, (IVoiceInteractorCallback)object2, (VoiceInteractor.Prompt)object5, (Bundle)object);
                parcel.writeNoException();
                object = arroption;
                if (object5 != null) {
                    object = object5.asBinder();
                }
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IVoiceInteractor {
            public static IVoiceInteractor sDefaultImpl;
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
            public void notifyDirectActionsChanged(int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDirectActionsChanged(n, iBinder);
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
            public void setKillCallback(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setKillCallback(iCancellationSignal);
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
            public IVoiceInteractorRequest startAbortVoice(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    IBinder iBinder = iVoiceInteractorCallback != null ? iVoiceInteractorCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (prompt != null) {
                        parcel.writeInt(1);
                        prompt.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().startAbortVoice((String)object, iVoiceInteractorCallback, prompt, bundle);
                        return object;
                    }
                    parcel2.readException();
                    object = IVoiceInteractorRequest.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public IVoiceInteractorRequest startCommand(String object, IVoiceInteractorCallback iVoiceInteractorCallback, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    IBinder iBinder = iVoiceInteractorCallback != null ? iVoiceInteractorCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().startCommand((String)object, iVoiceInteractorCallback, string2, bundle);
                        return object;
                    }
                    parcel2.readException();
                    object = IVoiceInteractorRequest.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public IVoiceInteractorRequest startCompleteVoice(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    IBinder iBinder = iVoiceInteractorCallback != null ? iVoiceInteractorCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (prompt != null) {
                        parcel.writeInt(1);
                        prompt.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().startCompleteVoice((String)object, iVoiceInteractorCallback, prompt, bundle);
                        return object;
                    }
                    parcel2.readException();
                    object = IVoiceInteractorRequest.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public IVoiceInteractorRequest startConfirmation(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    IBinder iBinder = iVoiceInteractorCallback != null ? iVoiceInteractorCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (prompt != null) {
                        parcel.writeInt(1);
                        prompt.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().startConfirmation((String)object, iVoiceInteractorCallback, prompt, bundle);
                        return object;
                    }
                    parcel2.readException();
                    object = IVoiceInteractorRequest.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public IVoiceInteractorRequest startPickOption(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    IBinder iBinder = iVoiceInteractorCallback != null ? iVoiceInteractorCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (prompt != null) {
                        parcel.writeInt(1);
                        prompt.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedArray((Parcelable[])arroption, 0);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().startPickOption((String)object, iVoiceInteractorCallback, prompt, arroption, bundle);
                        return object;
                    }
                    parcel2.readException();
                    object = IVoiceInteractorRequest.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean[] supportsCommands(String arrbl, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrbl);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrbl = Stub.getDefaultImpl().supportsCommands((String)arrbl, arrstring);
                        return arrbl;
                    }
                    parcel2.readException();
                    arrbl = parcel2.createBooleanArray();
                    return arrbl;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

