/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.content.ContentCaptureOptions;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IContentCaptureDirectManager
extends IInterface {
    public void sendEvents(ParceledListSlice var1, int var2, ContentCaptureOptions var3) throws RemoteException;

    public static class Default
    implements IContentCaptureDirectManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void sendEvents(ParceledListSlice parceledListSlice, int n, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentCaptureDirectManager {
        private static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureDirectManager";
        static final int TRANSACTION_sendEvents = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureDirectManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentCaptureDirectManager) {
                return (IContentCaptureDirectManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentCaptureDirectManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "sendEvents";
        }

        public static boolean setDefaultImpl(IContentCaptureDirectManager iContentCaptureDirectManager) {
            if (Proxy.sDefaultImpl == null && iContentCaptureDirectManager != null) {
                Proxy.sDefaultImpl = iContentCaptureDirectManager;
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
        public boolean onTransact(int n, Parcel object, Parcel parceledListSlice, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)((Object)parceledListSlice), n2);
                }
                ((Parcel)((Object)parceledListSlice)).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            parceledListSlice = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? ContentCaptureOptions.CREATOR.createFromParcel((Parcel)object) : null;
            this.sendEvents(parceledListSlice, n, (ContentCaptureOptions)object);
            return true;
        }

        private static class Proxy
        implements IContentCaptureDirectManager {
            public static IContentCaptureDirectManager sDefaultImpl;
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
            public void sendEvents(ParceledListSlice parceledListSlice, int n, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (contentCaptureOptions != null) {
                        parcel.writeInt(1);
                        contentCaptureOptions.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendEvents(parceledListSlice, n, contentCaptureOptions);
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

