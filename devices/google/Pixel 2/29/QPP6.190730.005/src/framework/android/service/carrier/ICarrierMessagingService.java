/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.carrier.ICarrierMessagingCallback;
import android.service.carrier.MessagePdu;
import java.util.ArrayList;
import java.util.List;

public interface ICarrierMessagingService
extends IInterface {
    public void downloadMms(Uri var1, int var2, Uri var3, ICarrierMessagingCallback var4) throws RemoteException;

    public void filterSms(MessagePdu var1, String var2, int var3, int var4, ICarrierMessagingCallback var5) throws RemoteException;

    public void sendDataSms(byte[] var1, int var2, String var3, int var4, int var5, ICarrierMessagingCallback var6) throws RemoteException;

    public void sendMms(Uri var1, int var2, Uri var3, ICarrierMessagingCallback var4) throws RemoteException;

    public void sendMultipartTextSms(List<String> var1, int var2, String var3, int var4, ICarrierMessagingCallback var5) throws RemoteException;

    public void sendTextSms(String var1, int var2, String var3, int var4, ICarrierMessagingCallback var5) throws RemoteException;

    public static class Default
    implements ICarrierMessagingService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void downloadMms(Uri uri, int n, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override
        public void filterSms(MessagePdu messagePdu, String string2, int n, int n2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override
        public void sendDataSms(byte[] arrby, int n, String string2, int n2, int n3, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override
        public void sendMms(Uri uri, int n, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override
        public void sendMultipartTextSms(List<String> list, int n, String string2, int n2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override
        public void sendTextSms(String string2, int n, String string3, int n2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICarrierMessagingService {
        private static final String DESCRIPTOR = "android.service.carrier.ICarrierMessagingService";
        static final int TRANSACTION_downloadMms = 6;
        static final int TRANSACTION_filterSms = 1;
        static final int TRANSACTION_sendDataSms = 3;
        static final int TRANSACTION_sendMms = 5;
        static final int TRANSACTION_sendMultipartTextSms = 4;
        static final int TRANSACTION_sendTextSms = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierMessagingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICarrierMessagingService) {
                return (ICarrierMessagingService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICarrierMessagingService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "downloadMms";
                }
                case 5: {
                    return "sendMms";
                }
                case 4: {
                    return "sendMultipartTextSms";
                }
                case 3: {
                    return "sendDataSms";
                }
                case 2: {
                    return "sendTextSms";
                }
                case 1: 
            }
            return "filterSms";
        }

        public static boolean setDefaultImpl(ICarrierMessagingService iCarrierMessagingService) {
            if (Proxy.sDefaultImpl == null && iCarrierMessagingService != null) {
                Proxy.sDefaultImpl = iCarrierMessagingService;
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
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                        n = parcel.readInt();
                        Uri uri = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                        this.downloadMms((Uri)object, n, uri, ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                        n = parcel.readInt();
                        Uri uri = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                        this.sendMms((Uri)object, n, uri, ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendMultipartTextSms(parcel.createStringArrayList(), parcel.readInt(), parcel.readString(), parcel.readInt(), ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendDataSms(parcel.createByteArray(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendTextSms(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? MessagePdu.CREATOR.createFromParcel(parcel) : null;
                this.filterSms((MessagePdu)object, parcel.readString(), parcel.readInt(), parcel.readInt(), ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ICarrierMessagingService {
            public static ICarrierMessagingService sDefaultImpl;
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
            public void downloadMms(Uri uri, int n, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (uri2 != null) {
                        parcel.writeInt(1);
                        uri2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iCarrierMessagingCallback != null ? iCarrierMessagingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().downloadMms(uri, n, uri2, iCarrierMessagingCallback);
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
            public void filterSms(MessagePdu messagePdu, String string2, int n, int n2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (messagePdu != null) {
                        parcel.writeInt(1);
                        messagePdu.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iCarrierMessagingCallback != null ? iCarrierMessagingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().filterSms(messagePdu, string2, n, n2, iCarrierMessagingCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
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
            public void sendDataSms(byte[] arrby, int n, String string2, int n2, int n3, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel parcel;
                void var1_9;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n3);
                        IBinder iBinder = iCarrierMessagingCallback != null ? iCarrierMessagingCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendDataSms(arrby, n, string2, n2, n3, iCarrierMessagingCallback);
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
                throw var1_9;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void sendMms(Uri uri, int n, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (uri2 != null) {
                        parcel.writeInt(1);
                        uri2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iCarrierMessagingCallback != null ? iCarrierMessagingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().sendMms(uri, n, uri2, iCarrierMessagingCallback);
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
            public void sendMultipartTextSms(List<String> list, int n, String string2, int n2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    IBinder iBinder = iCarrierMessagingCallback != null ? iCarrierMessagingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().sendMultipartTextSms(list, n, string2, n2, iCarrierMessagingCallback);
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
            public void sendTextSms(String string2, int n, String string3, int n2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    IBinder iBinder = iCarrierMessagingCallback != null ? iCarrierMessagingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().sendTextSms(string2, n, string3, n2, iCarrierMessagingCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

