/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.media.tv.ITvInputSession;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ITvInputSessionCallback
extends IInterface {
    public void onChannelRetuned(Uri var1) throws RemoteException;

    public void onContentAllowed() throws RemoteException;

    public void onContentBlocked(String var1) throws RemoteException;

    public void onError(int var1) throws RemoteException;

    public void onLayoutSurface(int var1, int var2, int var3, int var4) throws RemoteException;

    public void onRecordingStopped(Uri var1) throws RemoteException;

    public void onSessionCreated(ITvInputSession var1, IBinder var2) throws RemoteException;

    public void onSessionEvent(String var1, Bundle var2) throws RemoteException;

    public void onTimeShiftCurrentPositionChanged(long var1) throws RemoteException;

    public void onTimeShiftStartPositionChanged(long var1) throws RemoteException;

    public void onTimeShiftStatusChanged(int var1) throws RemoteException;

    public void onTrackSelected(int var1, String var2) throws RemoteException;

    public void onTracksChanged(List<TvTrackInfo> var1) throws RemoteException;

    public void onTuned(Uri var1) throws RemoteException;

    public void onVideoAvailable() throws RemoteException;

    public void onVideoUnavailable(int var1) throws RemoteException;

    public static class Default
    implements ITvInputSessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onChannelRetuned(Uri uri) throws RemoteException {
        }

        @Override
        public void onContentAllowed() throws RemoteException {
        }

        @Override
        public void onContentBlocked(String string2) throws RemoteException {
        }

        @Override
        public void onError(int n) throws RemoteException {
        }

        @Override
        public void onLayoutSurface(int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void onRecordingStopped(Uri uri) throws RemoteException {
        }

        @Override
        public void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void onSessionEvent(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onTimeShiftCurrentPositionChanged(long l) throws RemoteException {
        }

        @Override
        public void onTimeShiftStartPositionChanged(long l) throws RemoteException {
        }

        @Override
        public void onTimeShiftStatusChanged(int n) throws RemoteException {
        }

        @Override
        public void onTrackSelected(int n, String string2) throws RemoteException {
        }

        @Override
        public void onTracksChanged(List<TvTrackInfo> list) throws RemoteException {
        }

        @Override
        public void onTuned(Uri uri) throws RemoteException {
        }

        @Override
        public void onVideoAvailable() throws RemoteException {
        }

        @Override
        public void onVideoUnavailable(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputSessionCallback {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputSessionCallback";
        static final int TRANSACTION_onChannelRetuned = 3;
        static final int TRANSACTION_onContentAllowed = 8;
        static final int TRANSACTION_onContentBlocked = 9;
        static final int TRANSACTION_onError = 16;
        static final int TRANSACTION_onLayoutSurface = 10;
        static final int TRANSACTION_onRecordingStopped = 15;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 2;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 13;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 12;
        static final int TRANSACTION_onTimeShiftStatusChanged = 11;
        static final int TRANSACTION_onTrackSelected = 5;
        static final int TRANSACTION_onTracksChanged = 4;
        static final int TRANSACTION_onTuned = 14;
        static final int TRANSACTION_onVideoAvailable = 6;
        static final int TRANSACTION_onVideoUnavailable = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputSessionCallback) {
                return (ITvInputSessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputSessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "onError";
                }
                case 15: {
                    return "onRecordingStopped";
                }
                case 14: {
                    return "onTuned";
                }
                case 13: {
                    return "onTimeShiftCurrentPositionChanged";
                }
                case 12: {
                    return "onTimeShiftStartPositionChanged";
                }
                case 11: {
                    return "onTimeShiftStatusChanged";
                }
                case 10: {
                    return "onLayoutSurface";
                }
                case 9: {
                    return "onContentBlocked";
                }
                case 8: {
                    return "onContentAllowed";
                }
                case 7: {
                    return "onVideoUnavailable";
                }
                case 6: {
                    return "onVideoAvailable";
                }
                case 5: {
                    return "onTrackSelected";
                }
                case 4: {
                    return "onTracksChanged";
                }
                case 3: {
                    return "onChannelRetuned";
                }
                case 2: {
                    return "onSessionEvent";
                }
                case 1: 
            }
            return "onSessionCreated";
        }

        public static boolean setDefaultImpl(ITvInputSessionCallback iTvInputSessionCallback) {
            if (Proxy.sDefaultImpl == null && iTvInputSessionCallback != null) {
                Proxy.sDefaultImpl = iTvInputSessionCallback;
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
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onError(((Parcel)object).readInt());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRecordingStopped((Uri)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTuned((Uri)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTimeShiftCurrentPositionChanged(((Parcel)object).readLong());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTimeShiftStartPositionChanged(((Parcel)object).readLong());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTimeShiftStatusChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onLayoutSurface(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onContentBlocked(((Parcel)object).readString());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onContentAllowed();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onVideoUnavailable(((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onVideoAvailable();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTrackSelected(((Parcel)object).readInt(), ((Parcel)object).readString());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTracksChanged(((Parcel)object).createTypedArrayList(TvTrackInfo.CREATOR));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onChannelRetuned((Uri)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSessionEvent((String)object2, (Bundle)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onSessionCreated(ITvInputSession.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readStrongBinder());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITvInputSessionCallback {
            public static ITvInputSessionCallback sDefaultImpl;
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
            public void onChannelRetuned(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onChannelRetuned(uri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onContentAllowed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onContentAllowed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onContentBlocked(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onContentBlocked(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLayoutSurface(int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutSurface(n, n2, n3, n4);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRecordingStopped(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecordingStopped(uri);
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
            public void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iTvInputSession != null ? iTvInputSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSessionCreated(iTvInputSession, iBinder);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionEvent(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionEvent(string2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeShiftCurrentPositionChanged(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeShiftCurrentPositionChanged(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeShiftStartPositionChanged(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeShiftStartPositionChanged(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeShiftStatusChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeShiftStatusChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTrackSelected(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrackSelected(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTracksChanged(List<TvTrackInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTracksChanged(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTuned(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTuned(uri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVideoAvailable() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVideoAvailable();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVideoUnavailable(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVideoUnavailable(n);
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

