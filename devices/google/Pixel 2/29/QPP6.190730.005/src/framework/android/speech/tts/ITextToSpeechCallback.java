/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITextToSpeechCallback
extends IInterface {
    public void onAudioAvailable(String var1, byte[] var2) throws RemoteException;

    public void onBeginSynthesis(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onError(String var1, int var2) throws RemoteException;

    public void onRangeStart(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onStart(String var1) throws RemoteException;

    public void onStop(String var1, boolean var2) throws RemoteException;

    public void onSuccess(String var1) throws RemoteException;

    public static class Default
    implements ITextToSpeechCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAudioAvailable(String string2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onBeginSynthesis(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onError(String string2, int n) throws RemoteException {
        }

        @Override
        public void onRangeStart(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onStart(String string2) throws RemoteException {
        }

        @Override
        public void onStop(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void onSuccess(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITextToSpeechCallback {
        private static final String DESCRIPTOR = "android.speech.tts.ITextToSpeechCallback";
        static final int TRANSACTION_onAudioAvailable = 6;
        static final int TRANSACTION_onBeginSynthesis = 5;
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onRangeStart = 7;
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITextToSpeechCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITextToSpeechCallback) {
                return (ITextToSpeechCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITextToSpeechCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onRangeStart";
                }
                case 6: {
                    return "onAudioAvailable";
                }
                case 5: {
                    return "onBeginSynthesis";
                }
                case 4: {
                    return "onError";
                }
                case 3: {
                    return "onStop";
                }
                case 2: {
                    return "onSuccess";
                }
                case 1: 
            }
            return "onStart";
        }

        public static boolean setDefaultImpl(ITextToSpeechCallback iTextToSpeechCallback) {
            if (Proxy.sDefaultImpl == null && iTextToSpeechCallback != null) {
                Proxy.sDefaultImpl = iTextToSpeechCallback;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRangeStart(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAudioAvailable(parcel.readString(), parcel.createByteArray());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onBeginSynthesis(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onError(parcel.readString(), parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readString();
                        boolean bl = parcel.readInt() != 0;
                        this.onStop((String)object, bl);
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSuccess(parcel.readString());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onStart(parcel.readString());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITextToSpeechCallback {
            public static ITextToSpeechCallback sDefaultImpl;
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
            public void onAudioAvailable(String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAudioAvailable(string2, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBeginSynthesis(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBeginSynthesis(string2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRangeStart(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRangeStart(string2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStart(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStart(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStop(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStop(string2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSuccess(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(string2);
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

