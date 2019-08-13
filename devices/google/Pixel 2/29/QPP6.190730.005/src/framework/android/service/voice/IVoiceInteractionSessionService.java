/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IVoiceInteractionSessionService
extends IInterface {
    public void newSession(IBinder var1, Bundle var2, int var3) throws RemoteException;

    public static class Default
    implements IVoiceInteractionSessionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void newSession(IBinder iBinder, Bundle bundle, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractionSessionService {
        private static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionSessionService";
        static final int TRANSACTION_newSession = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSessionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractionSessionService) {
                return (IVoiceInteractionSessionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractionSessionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "newSession";
        }

        public static boolean setDefaultImpl(IVoiceInteractionSessionService iVoiceInteractionSessionService) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractionSessionService != null) {
                Proxy.sDefaultImpl = iVoiceInteractionSessionService;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            IBinder iBinder = parcel.readStrongBinder();
            object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
            this.newSession(iBinder, (Bundle)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IVoiceInteractionSessionService {
            public static IVoiceInteractionSessionService sDefaultImpl;
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
            public void newSession(IBinder iBinder, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().newSession(iBinder, bundle, n);
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

