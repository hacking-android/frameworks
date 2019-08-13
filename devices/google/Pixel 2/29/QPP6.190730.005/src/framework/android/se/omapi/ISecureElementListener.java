/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISecureElementListener
extends IInterface {

    public static class Default
    implements ISecureElementListener {
        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISecureElementListener {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementListener";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISecureElementListener) {
                return (ISecureElementListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISecureElementListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            return null;
        }

        public static boolean setDefaultImpl(ISecureElementListener iSecureElementListener) {
            if (Proxy.sDefaultImpl == null && iSecureElementListener != null) {
                Proxy.sDefaultImpl = iSecureElementListener;
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
            if (n != 1598968902) {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISecureElementListener {
            public static ISecureElementListener sDefaultImpl;
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
        }

    }

}

