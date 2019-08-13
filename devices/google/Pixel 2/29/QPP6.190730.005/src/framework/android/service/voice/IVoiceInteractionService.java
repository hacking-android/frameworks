/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.IVoiceActionCheckCallback;
import java.util.ArrayList;
import java.util.List;

public interface IVoiceInteractionService
extends IInterface {
    public void getActiveServiceSupportedActions(List<String> var1, IVoiceActionCheckCallback var2) throws RemoteException;

    public void launchVoiceAssistFromKeyguard() throws RemoteException;

    public void ready() throws RemoteException;

    public void shutdown() throws RemoteException;

    public void soundModelsChanged() throws RemoteException;

    public static class Default
    implements IVoiceInteractionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
        }

        @Override
        public void launchVoiceAssistFromKeyguard() throws RemoteException {
        }

        @Override
        public void ready() throws RemoteException {
        }

        @Override
        public void shutdown() throws RemoteException {
        }

        @Override
        public void soundModelsChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractionService {
        private static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionService";
        static final int TRANSACTION_getActiveServiceSupportedActions = 5;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 4;
        static final int TRANSACTION_ready = 1;
        static final int TRANSACTION_shutdown = 3;
        static final int TRANSACTION_soundModelsChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractionService) {
                return (IVoiceInteractionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "getActiveServiceSupportedActions";
                        }
                        return "launchVoiceAssistFromKeyguard";
                    }
                    return "shutdown";
                }
                return "soundModelsChanged";
            }
            return "ready";
        }

        public static boolean setDefaultImpl(IVoiceInteractionService iVoiceInteractionService) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractionService != null) {
                Proxy.sDefaultImpl = iVoiceInteractionService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.getActiveServiceSupportedActions(parcel.createStringArrayList(), IVoiceActionCheckCallback.Stub.asInterface(parcel.readStrongBinder()));
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.launchVoiceAssistFromKeyguard();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.shutdown();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.soundModelsChanged();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.ready();
            return true;
        }

        private static class Proxy
        implements IVoiceInteractionService {
            public static IVoiceInteractionService sDefaultImpl;
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
            public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    IBinder iBinder = iVoiceActionCheckCallback != null ? iVoiceActionCheckCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getActiveServiceSupportedActions(list, iVoiceActionCheckCallback);
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
            public void launchVoiceAssistFromKeyguard() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().launchVoiceAssistFromKeyguard();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void ready() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().ready();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void shutdown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void soundModelsChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().soundModelsChanged();
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

