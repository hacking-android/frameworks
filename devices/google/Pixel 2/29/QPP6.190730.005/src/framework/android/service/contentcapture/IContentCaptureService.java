/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.contentcapture.ActivityEvent;
import android.service.contentcapture.SnapshotData;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.DataRemovalRequest;
import com.android.internal.os.IResultReceiver;

public interface IContentCaptureService
extends IInterface {
    public void onActivityEvent(ActivityEvent var1) throws RemoteException;

    public void onActivitySnapshot(int var1, SnapshotData var2) throws RemoteException;

    public void onConnected(IBinder var1, boolean var2, boolean var3) throws RemoteException;

    public void onDataRemovalRequest(DataRemovalRequest var1) throws RemoteException;

    public void onDisconnected() throws RemoteException;

    public void onSessionFinished(int var1) throws RemoteException;

    public void onSessionStarted(ContentCaptureContext var1, int var2, int var3, IResultReceiver var4, int var5) throws RemoteException;

    public static class Default
    implements IContentCaptureService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActivityEvent(ActivityEvent activityEvent) throws RemoteException {
        }

        @Override
        public void onActivitySnapshot(int n, SnapshotData snapshotData) throws RemoteException {
        }

        @Override
        public void onConnected(IBinder iBinder, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) throws RemoteException {
        }

        @Override
        public void onDisconnected() throws RemoteException {
        }

        @Override
        public void onSessionFinished(int n) throws RemoteException {
        }

        @Override
        public void onSessionStarted(ContentCaptureContext contentCaptureContext, int n, int n2, IResultReceiver iResultReceiver, int n3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentCaptureService {
        private static final String DESCRIPTOR = "android.service.contentcapture.IContentCaptureService";
        static final int TRANSACTION_onActivityEvent = 7;
        static final int TRANSACTION_onActivitySnapshot = 5;
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDataRemovalRequest = 6;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onSessionFinished = 4;
        static final int TRANSACTION_onSessionStarted = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentCaptureService) {
                return (IContentCaptureService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentCaptureService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onActivityEvent";
                }
                case 6: {
                    return "onDataRemovalRequest";
                }
                case 5: {
                    return "onActivitySnapshot";
                }
                case 4: {
                    return "onSessionFinished";
                }
                case 3: {
                    return "onSessionStarted";
                }
                case 2: {
                    return "onDisconnected";
                }
                case 1: 
            }
            return "onConnected";
        }

        public static boolean setDefaultImpl(IContentCaptureService iContentCaptureService) {
            if (Proxy.sDefaultImpl == null && iContentCaptureService != null) {
                Proxy.sDefaultImpl = iContentCaptureService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ActivityEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onActivityEvent((ActivityEvent)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? DataRemovalRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onDataRemovalRequest((DataRemovalRequest)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? SnapshotData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onActivitySnapshot(n, (SnapshotData)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSessionFinished(((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ContentCaptureContext.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSessionStarted((ContentCaptureContext)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDisconnected();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readStrongBinder();
                n = ((Parcel)object).readInt();
                boolean bl = false;
                boolean bl2 = n != 0;
                if (((Parcel)object).readInt() != 0) {
                    bl = true;
                }
                this.onConnected((IBinder)object2, bl2, bl);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IContentCaptureService {
            public static IContentCaptureService sDefaultImpl;
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
            public void onActivityEvent(ActivityEvent activityEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (activityEvent != null) {
                        parcel.writeInt(1);
                        activityEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityEvent(activityEvent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivitySnapshot(int n, SnapshotData snapshotData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (snapshotData != null) {
                        parcel.writeInt(1);
                        snapshotData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivitySnapshot(n, snapshotData);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnected(IBinder iBinder, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                int n;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnected(iBinder, bl, bl2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dataRemovalRequest != null) {
                        parcel.writeInt(1);
                        dataRemovalRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataRemovalRequest(dataRemovalRequest);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDisconnected() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDisconnected();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionFinished(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionFinished(n);
                        return;
                    }
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
            public void onSessionStarted(ContentCaptureContext contentCaptureContext, int n, int n2, IResultReceiver iResultReceiver, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (contentCaptureContext != null) {
                        parcel.writeInt(1);
                        contentCaptureContext.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n3);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSessionStarted(contentCaptureContext, n, n2, iResultReceiver, n3);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

