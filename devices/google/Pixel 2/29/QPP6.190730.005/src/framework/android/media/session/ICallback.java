/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.content.ComponentName;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.KeyEvent;

public interface ICallback
extends IInterface {
    public void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName var1) throws RemoteException;

    public void onAddressedPlayerChangedToMediaSession(MediaSession.Token var1) throws RemoteException;

    public void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent var1, ComponentName var2) throws RemoteException;

    public void onMediaKeyEventDispatchedToMediaSession(KeyEvent var1, MediaSession.Token var2) throws RemoteException;

    public static class Default
    implements ICallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void onAddressedPlayerChangedToMediaSession(MediaSession.Token token) throws RemoteException {
        }

        @Override
        public void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent keyEvent, ComponentName componentName) throws RemoteException {
        }

        @Override
        public void onMediaKeyEventDispatchedToMediaSession(KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICallback {
        private static final String DESCRIPTOR = "android.media.session.ICallback";
        static final int TRANSACTION_onAddressedPlayerChangedToMediaButtonReceiver = 4;
        static final int TRANSACTION_onAddressedPlayerChangedToMediaSession = 3;
        static final int TRANSACTION_onMediaKeyEventDispatchedToMediaButtonReceiver = 2;
        static final int TRANSACTION_onMediaKeyEventDispatchedToMediaSession = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICallback) {
                return (ICallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onAddressedPlayerChangedToMediaButtonReceiver";
                    }
                    return "onAddressedPlayerChangedToMediaSession";
                }
                return "onMediaKeyEventDispatchedToMediaButtonReceiver";
            }
            return "onMediaKeyEventDispatchedToMediaSession";
        }

        public static boolean setDefaultImpl(ICallback iCallback) {
            if (Proxy.sDefaultImpl == null && iCallback != null) {
                Proxy.sDefaultImpl = iCallback;
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
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                            }
                            ((Parcel)object2).writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onAddressedPlayerChangedToMediaButtonReceiver((ComponentName)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onAddressedPlayerChangedToMediaSession((MediaSession.Token)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                this.onMediaKeyEventDispatchedToMediaButtonReceiver((KeyEvent)object2, (ComponentName)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
            object = ((Parcel)object).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)object) : null;
            this.onMediaKeyEventDispatchedToMediaSession((KeyEvent)object2, (MediaSession.Token)object);
            return true;
        }

        private static class Proxy
        implements ICallback {
            public static ICallback sDefaultImpl;
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
            public void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAddressedPlayerChangedToMediaButtonReceiver(componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAddressedPlayerChangedToMediaSession(MediaSession.Token token) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAddressedPlayerChangedToMediaSession(token);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent keyEvent, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaKeyEventDispatchedToMediaButtonReceiver(keyEvent, componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMediaKeyEventDispatchedToMediaSession(KeyEvent keyEvent, MediaSession.Token token) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaKeyEventDispatchedToMediaSession(keyEvent, token);
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

