/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.InputChannel;
import java.util.ArrayList;
import java.util.List;

public interface ITvInputClient
extends IInterface {
    public void onChannelRetuned(Uri var1, int var2) throws RemoteException;

    public void onContentAllowed(int var1) throws RemoteException;

    public void onContentBlocked(String var1, int var2) throws RemoteException;

    public void onError(int var1, int var2) throws RemoteException;

    public void onLayoutSurface(int var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public void onRecordingStopped(Uri var1, int var2) throws RemoteException;

    public void onSessionCreated(String var1, IBinder var2, InputChannel var3, int var4) throws RemoteException;

    public void onSessionEvent(String var1, Bundle var2, int var3) throws RemoteException;

    public void onSessionReleased(int var1) throws RemoteException;

    public void onTimeShiftCurrentPositionChanged(long var1, int var3) throws RemoteException;

    public void onTimeShiftStartPositionChanged(long var1, int var3) throws RemoteException;

    public void onTimeShiftStatusChanged(int var1, int var2) throws RemoteException;

    public void onTrackSelected(int var1, String var2, int var3) throws RemoteException;

    public void onTracksChanged(List<TvTrackInfo> var1, int var2) throws RemoteException;

    public void onTuned(int var1, Uri var2) throws RemoteException;

    public void onVideoAvailable(int var1) throws RemoteException;

    public void onVideoUnavailable(int var1, int var2) throws RemoteException;

    public static class Default
    implements ITvInputClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onChannelRetuned(Uri uri, int n) throws RemoteException {
        }

        @Override
        public void onContentAllowed(int n) throws RemoteException {
        }

        @Override
        public void onContentBlocked(String string2, int n) throws RemoteException {
        }

        @Override
        public void onError(int n, int n2) throws RemoteException {
        }

        @Override
        public void onLayoutSurface(int n, int n2, int n3, int n4, int n5) throws RemoteException {
        }

        @Override
        public void onRecordingStopped(Uri uri, int n) throws RemoteException {
        }

        @Override
        public void onSessionCreated(String string2, IBinder iBinder, InputChannel inputChannel, int n) throws RemoteException {
        }

        @Override
        public void onSessionEvent(String string2, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public void onSessionReleased(int n) throws RemoteException {
        }

        @Override
        public void onTimeShiftCurrentPositionChanged(long l, int n) throws RemoteException {
        }

        @Override
        public void onTimeShiftStartPositionChanged(long l, int n) throws RemoteException {
        }

        @Override
        public void onTimeShiftStatusChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onTrackSelected(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void onTracksChanged(List<TvTrackInfo> list, int n) throws RemoteException {
        }

        @Override
        public void onTuned(int n, Uri uri) throws RemoteException {
        }

        @Override
        public void onVideoAvailable(int n) throws RemoteException {
        }

        @Override
        public void onVideoUnavailable(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputClient {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputClient";
        static final int TRANSACTION_onChannelRetuned = 4;
        static final int TRANSACTION_onContentAllowed = 9;
        static final int TRANSACTION_onContentBlocked = 10;
        static final int TRANSACTION_onError = 17;
        static final int TRANSACTION_onLayoutSurface = 11;
        static final int TRANSACTION_onRecordingStopped = 16;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 3;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 14;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 13;
        static final int TRANSACTION_onTimeShiftStatusChanged = 12;
        static final int TRANSACTION_onTrackSelected = 6;
        static final int TRANSACTION_onTracksChanged = 5;
        static final int TRANSACTION_onTuned = 15;
        static final int TRANSACTION_onVideoAvailable = 7;
        static final int TRANSACTION_onVideoUnavailable = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputClient) {
                return (ITvInputClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 17: {
                    return "onError";
                }
                case 16: {
                    return "onRecordingStopped";
                }
                case 15: {
                    return "onTuned";
                }
                case 14: {
                    return "onTimeShiftCurrentPositionChanged";
                }
                case 13: {
                    return "onTimeShiftStartPositionChanged";
                }
                case 12: {
                    return "onTimeShiftStatusChanged";
                }
                case 11: {
                    return "onLayoutSurface";
                }
                case 10: {
                    return "onContentBlocked";
                }
                case 9: {
                    return "onContentAllowed";
                }
                case 8: {
                    return "onVideoUnavailable";
                }
                case 7: {
                    return "onVideoAvailable";
                }
                case 6: {
                    return "onTrackSelected";
                }
                case 5: {
                    return "onTracksChanged";
                }
                case 4: {
                    return "onChannelRetuned";
                }
                case 3: {
                    return "onSessionEvent";
                }
                case 2: {
                    return "onSessionReleased";
                }
                case 1: 
            }
            return "onSessionCreated";
        }

        public static boolean setDefaultImpl(ITvInputClient iTvInputClient) {
            if (Proxy.sDefaultImpl == null && iTvInputClient != null) {
                Proxy.sDefaultImpl = iTvInputClient;
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
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onError(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRecordingStopped((Uri)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTuned(n, (Uri)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTimeShiftCurrentPositionChanged(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTimeShiftStartPositionChanged(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTimeShiftStatusChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onLayoutSurface(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onContentBlocked(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onContentAllowed(((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onVideoUnavailable(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onVideoAvailable(((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTrackSelected(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTracksChanged(((Parcel)object).createTypedArrayList(TvTrackInfo.CREATOR), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onChannelRetuned((Uri)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSessionEvent(string2, (Bundle)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSessionReleased(((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string3 = ((Parcel)object).readString();
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                object2 = ((Parcel)object).readInt() != 0 ? InputChannel.CREATOR.createFromParcel((Parcel)object) : null;
                this.onSessionCreated(string3, iBinder, (InputChannel)object2, ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITvInputClient {
            public static ITvInputClient sDefaultImpl;
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
            public void onChannelRetuned(Uri uri, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onChannelRetuned(uri, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onContentAllowed(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onContentAllowed(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onContentBlocked(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onContentBlocked(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLayoutSurface(int n, int n2, int n3, int n4, int n5) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    parcel.writeInt(n5);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutSurface(n, n2, n3, n4, n5);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRecordingStopped(Uri uri, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecordingStopped(uri, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionCreated(String string2, IBinder iBinder, InputChannel inputChannel, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (inputChannel != null) {
                        parcel.writeInt(1);
                        inputChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionCreated(string2, iBinder, inputChannel, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionEvent(String string2, Bundle bundle, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionEvent(string2, bundle, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionReleased(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionReleased(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeShiftCurrentPositionChanged(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeShiftCurrentPositionChanged(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeShiftStartPositionChanged(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeShiftStartPositionChanged(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeShiftStatusChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeShiftStatusChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTrackSelected(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrackSelected(n, string2, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTracksChanged(List<TvTrackInfo> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTracksChanged(list, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTuned(int n, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTuned(n, uri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVideoAvailable(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVideoAvailable(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVideoUnavailable(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVideoUnavailable(n, n2);
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

