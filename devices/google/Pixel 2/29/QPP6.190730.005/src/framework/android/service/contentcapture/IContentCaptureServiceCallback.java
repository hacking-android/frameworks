/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentcapture;

import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.contentcapture.FlushMetrics;
import android.view.contentcapture.ContentCaptureCondition;
import java.util.ArrayList;
import java.util.List;

public interface IContentCaptureServiceCallback
extends IInterface {
    public void disableSelf() throws RemoteException;

    public void setContentCaptureConditions(String var1, List<ContentCaptureCondition> var2) throws RemoteException;

    public void setContentCaptureWhitelist(List<String> var1, List<ComponentName> var2) throws RemoteException;

    public void writeSessionFlush(int var1, ComponentName var2, FlushMetrics var3, ContentCaptureOptions var4, int var5) throws RemoteException;

    public static class Default
    implements IContentCaptureServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disableSelf() throws RemoteException {
        }

        @Override
        public void setContentCaptureConditions(String string2, List<ContentCaptureCondition> list) throws RemoteException {
        }

        @Override
        public void setContentCaptureWhitelist(List<String> list, List<ComponentName> list2) throws RemoteException {
        }

        @Override
        public void writeSessionFlush(int n, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentCaptureServiceCallback {
        private static final String DESCRIPTOR = "android.service.contentcapture.IContentCaptureServiceCallback";
        static final int TRANSACTION_disableSelf = 3;
        static final int TRANSACTION_setContentCaptureConditions = 2;
        static final int TRANSACTION_setContentCaptureWhitelist = 1;
        static final int TRANSACTION_writeSessionFlush = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentCaptureServiceCallback) {
                return (IContentCaptureServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentCaptureServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "writeSessionFlush";
                    }
                    return "disableSelf";
                }
                return "setContentCaptureConditions";
            }
            return "setContentCaptureWhitelist";
        }

        public static boolean setDefaultImpl(IContentCaptureServiceCallback iContentCaptureServiceCallback) {
            if (Proxy.sDefaultImpl == null && iContentCaptureServiceCallback != null) {
                Proxy.sDefaultImpl = iContentCaptureServiceCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, (Parcel)object, n2);
                            }
                            ((Parcel)object).writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        object = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                        FlushMetrics flushMetrics = parcel.readInt() != 0 ? FlushMetrics.CREATOR.createFromParcel(parcel) : null;
                        ContentCaptureOptions contentCaptureOptions = parcel.readInt() != 0 ? ContentCaptureOptions.CREATOR.createFromParcel(parcel) : null;
                        this.writeSessionFlush(n, (ComponentName)object, flushMetrics, contentCaptureOptions, parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.disableSelf();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.setContentCaptureConditions(parcel.readString(), parcel.createTypedArrayList(ContentCaptureCondition.CREATOR));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.setContentCaptureWhitelist(parcel.createStringArrayList(), parcel.createTypedArrayList(ComponentName.CREATOR));
            return true;
        }

        private static class Proxy
        implements IContentCaptureServiceCallback {
            public static IContentCaptureServiceCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disableSelf() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableSelf();
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

            @Override
            public void setContentCaptureConditions(String string2, List<ContentCaptureCondition> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setContentCaptureConditions(string2, list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setContentCaptureWhitelist(List<String> list, List<ComponentName> list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    parcel.writeTypedList(list2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setContentCaptureWhitelist(list, list2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void writeSessionFlush(int n, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (flushMetrics != null) {
                        parcel.writeInt(1);
                        flushMetrics.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (contentCaptureOptions != null) {
                        parcel.writeInt(1);
                        contentCaptureOptions.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().writeSessionFlush(n, componentName, flushMetrics, contentCaptureOptions, n2);
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

