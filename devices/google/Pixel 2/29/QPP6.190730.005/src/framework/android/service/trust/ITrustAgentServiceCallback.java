/*
 * Decompiled with CFR 0.145.
 */
package android.service.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ITrustAgentServiceCallback
extends IInterface {
    public void addEscrowToken(byte[] var1, int var2) throws RemoteException;

    public void grantTrust(CharSequence var1, long var2, int var4) throws RemoteException;

    public void isEscrowTokenActive(long var1, int var3) throws RemoteException;

    public void onConfigureCompleted(boolean var1, IBinder var2) throws RemoteException;

    public void removeEscrowToken(long var1, int var3) throws RemoteException;

    public void revokeTrust() throws RemoteException;

    public void setManagingTrust(boolean var1) throws RemoteException;

    public void showKeyguardErrorMessage(CharSequence var1) throws RemoteException;

    public void unlockUserWithToken(long var1, byte[] var3, int var4) throws RemoteException;

    public static class Default
    implements ITrustAgentServiceCallback {
        @Override
        public void addEscrowToken(byte[] arrby, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void grantTrust(CharSequence charSequence, long l, int n) throws RemoteException {
        }

        @Override
        public void isEscrowTokenActive(long l, int n) throws RemoteException {
        }

        @Override
        public void onConfigureCompleted(boolean bl, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void removeEscrowToken(long l, int n) throws RemoteException {
        }

        @Override
        public void revokeTrust() throws RemoteException {
        }

        @Override
        public void setManagingTrust(boolean bl) throws RemoteException {
        }

        @Override
        public void showKeyguardErrorMessage(CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void unlockUserWithToken(long l, byte[] arrby, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITrustAgentServiceCallback {
        private static final String DESCRIPTOR = "android.service.trust.ITrustAgentServiceCallback";
        static final int TRANSACTION_addEscrowToken = 5;
        static final int TRANSACTION_grantTrust = 1;
        static final int TRANSACTION_isEscrowTokenActive = 6;
        static final int TRANSACTION_onConfigureCompleted = 4;
        static final int TRANSACTION_removeEscrowToken = 7;
        static final int TRANSACTION_revokeTrust = 2;
        static final int TRANSACTION_setManagingTrust = 3;
        static final int TRANSACTION_showKeyguardErrorMessage = 9;
        static final int TRANSACTION_unlockUserWithToken = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITrustAgentServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITrustAgentServiceCallback) {
                return (ITrustAgentServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITrustAgentServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "showKeyguardErrorMessage";
                }
                case 8: {
                    return "unlockUserWithToken";
                }
                case 7: {
                    return "removeEscrowToken";
                }
                case 6: {
                    return "isEscrowTokenActive";
                }
                case 5: {
                    return "addEscrowToken";
                }
                case 4: {
                    return "onConfigureCompleted";
                }
                case 3: {
                    return "setManagingTrust";
                }
                case 2: {
                    return "revokeTrust";
                }
                case 1: 
            }
            return "grantTrust";
        }

        public static boolean setDefaultImpl(ITrustAgentServiceCallback iTrustAgentServiceCallback) {
            if (Proxy.sDefaultImpl == null && iTrustAgentServiceCallback != null) {
                Proxy.sDefaultImpl = iTrustAgentServiceCallback;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.showKeyguardErrorMessage((CharSequence)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unlockUserWithToken(((Parcel)object).readLong(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeEscrowToken(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.isEscrowTokenActive(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addEscrowToken(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onConfigureCompleted(bl2, ((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setManagingTrust(bl2);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeTrust();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                this.grantTrust((CharSequence)object2, ((Parcel)object).readLong(), ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITrustAgentServiceCallback {
            public static ITrustAgentServiceCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addEscrowToken(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addEscrowToken(arrby, n);
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
            public void grantTrust(CharSequence charSequence, long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantTrust(charSequence, l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void isEscrowTokenActive(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isEscrowTokenActive(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConfigureCompleted(boolean bl, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfigureCompleted(bl, iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeEscrowToken(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeEscrowToken(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void revokeTrust() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeTrust();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setManagingTrust(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setManagingTrust(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showKeyguardErrorMessage(CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showKeyguardErrorMessage(charSequence);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unlockUserWithToken(long l, byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unlockUserWithToken(l, arrby, n);
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

