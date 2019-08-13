/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.IStatusBarNotificationHolder;
import android.service.notification.NotificationRankingUpdate;
import android.service.notification.NotificationStats;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface INotificationListener
extends IInterface {
    public void onActionClicked(String var1, Notification.Action var2, int var3) throws RemoteException;

    public void onAllowedAdjustmentsChanged() throws RemoteException;

    public void onInterruptionFilterChanged(int var1) throws RemoteException;

    public void onListenerConnected(NotificationRankingUpdate var1) throws RemoteException;

    public void onListenerHintsChanged(int var1) throws RemoteException;

    public void onNotificationChannelGroupModification(String var1, UserHandle var2, NotificationChannelGroup var3, int var4) throws RemoteException;

    public void onNotificationChannelModification(String var1, UserHandle var2, NotificationChannel var3, int var4) throws RemoteException;

    public void onNotificationDirectReply(String var1) throws RemoteException;

    public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder var1, NotificationChannel var2) throws RemoteException;

    public void onNotificationExpansionChanged(String var1, boolean var2, boolean var3) throws RemoteException;

    public void onNotificationPosted(IStatusBarNotificationHolder var1, NotificationRankingUpdate var2) throws RemoteException;

    public void onNotificationRankingUpdate(NotificationRankingUpdate var1) throws RemoteException;

    public void onNotificationRemoved(IStatusBarNotificationHolder var1, NotificationRankingUpdate var2, NotificationStats var3, int var4) throws RemoteException;

    public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder var1, String var2) throws RemoteException;

    public void onNotificationsSeen(List<String> var1) throws RemoteException;

    public void onStatusBarIconsBehaviorChanged(boolean var1) throws RemoteException;

    public void onSuggestedReplySent(String var1, CharSequence var2, int var3) throws RemoteException;

    public static class Default
    implements INotificationListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActionClicked(String string2, Notification.Action action, int n) throws RemoteException {
        }

        @Override
        public void onAllowedAdjustmentsChanged() throws RemoteException {
        }

        @Override
        public void onInterruptionFilterChanged(int n) throws RemoteException {
        }

        @Override
        public void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override
        public void onListenerHintsChanged(int n) throws RemoteException {
        }

        @Override
        public void onNotificationChannelGroupModification(String string2, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int n) throws RemoteException {
        }

        @Override
        public void onNotificationChannelModification(String string2, UserHandle userHandle, NotificationChannel notificationChannel, int n) throws RemoteException {
        }

        @Override
        public void onNotificationDirectReply(String string2) throws RemoteException {
        }

        @Override
        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel) throws RemoteException {
        }

        @Override
        public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override
        public void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        @Override
        public void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int n) throws RemoteException {
        }

        @Override
        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String string2) throws RemoteException {
        }

        @Override
        public void onNotificationsSeen(List<String> list) throws RemoteException {
        }

        @Override
        public void onStatusBarIconsBehaviorChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onSuggestedReplySent(String string2, CharSequence charSequence, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INotificationListener {
        private static final String DESCRIPTOR = "android.service.notification.INotificationListener";
        static final int TRANSACTION_onActionClicked = 16;
        static final int TRANSACTION_onAllowedAdjustmentsChanged = 17;
        static final int TRANSACTION_onInterruptionFilterChanged = 7;
        static final int TRANSACTION_onListenerConnected = 1;
        static final int TRANSACTION_onListenerHintsChanged = 6;
        static final int TRANSACTION_onNotificationChannelGroupModification = 9;
        static final int TRANSACTION_onNotificationChannelModification = 8;
        static final int TRANSACTION_onNotificationDirectReply = 14;
        static final int TRANSACTION_onNotificationEnqueuedWithChannel = 10;
        static final int TRANSACTION_onNotificationExpansionChanged = 13;
        static final int TRANSACTION_onNotificationPosted = 2;
        static final int TRANSACTION_onNotificationRankingUpdate = 5;
        static final int TRANSACTION_onNotificationRemoved = 4;
        static final int TRANSACTION_onNotificationSnoozedUntilContext = 11;
        static final int TRANSACTION_onNotificationsSeen = 12;
        static final int TRANSACTION_onStatusBarIconsBehaviorChanged = 3;
        static final int TRANSACTION_onSuggestedReplySent = 15;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INotificationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INotificationListener) {
                return (INotificationListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INotificationListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 17: {
                    return "onAllowedAdjustmentsChanged";
                }
                case 16: {
                    return "onActionClicked";
                }
                case 15: {
                    return "onSuggestedReplySent";
                }
                case 14: {
                    return "onNotificationDirectReply";
                }
                case 13: {
                    return "onNotificationExpansionChanged";
                }
                case 12: {
                    return "onNotificationsSeen";
                }
                case 11: {
                    return "onNotificationSnoozedUntilContext";
                }
                case 10: {
                    return "onNotificationEnqueuedWithChannel";
                }
                case 9: {
                    return "onNotificationChannelGroupModification";
                }
                case 8: {
                    return "onNotificationChannelModification";
                }
                case 7: {
                    return "onInterruptionFilterChanged";
                }
                case 6: {
                    return "onListenerHintsChanged";
                }
                case 5: {
                    return "onNotificationRankingUpdate";
                }
                case 4: {
                    return "onNotificationRemoved";
                }
                case 3: {
                    return "onStatusBarIconsBehaviorChanged";
                }
                case 2: {
                    return "onNotificationPosted";
                }
                case 1: 
            }
            return "onListenerConnected";
        }

        public static boolean setDefaultImpl(INotificationListener iNotificationListener) {
            if (Proxy.sDefaultImpl == null && iNotificationListener != null) {
                Proxy.sDefaultImpl = iNotificationListener;
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
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onAllowedAdjustmentsChanged();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Notification.Action.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onActionClicked(string2, (Notification.Action)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSuggestedReplySent(string3, (CharSequence)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationDirectReply(((Parcel)object).readString());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onNotificationExpansionChanged((String)object2, bl, bl2);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationsSeen(((Parcel)object).createStringArrayList());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationSnoozedUntilContext(IStatusBarNotificationHolder.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IStatusBarNotificationHolder.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? NotificationChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationEnqueuedWithChannel((IStatusBarNotificationHolder)object2, (NotificationChannel)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        NotificationChannelGroup notificationChannelGroup = ((Parcel)object).readInt() != 0 ? NotificationChannelGroup.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationChannelGroupModification(string4, (UserHandle)object2, notificationChannelGroup, ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        NotificationChannel notificationChannel = ((Parcel)object).readInt() != 0 ? NotificationChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationChannelModification(string5, (UserHandle)object2, notificationChannel, ((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onInterruptionFilterChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onListenerHintsChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? NotificationRankingUpdate.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationRankingUpdate((NotificationRankingUpdate)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IStatusBarNotificationHolder iStatusBarNotificationHolder = IStatusBarNotificationHolder.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? NotificationRankingUpdate.CREATOR.createFromParcel((Parcel)object) : null;
                        NotificationStats notificationStats = ((Parcel)object).readInt() != 0 ? NotificationStats.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationRemoved(iStatusBarNotificationHolder, (NotificationRankingUpdate)object2, notificationStats, ((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.onStatusBarIconsBehaviorChanged(bl);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IStatusBarNotificationHolder.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? NotificationRankingUpdate.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationPosted((IStatusBarNotificationHolder)object2, (NotificationRankingUpdate)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? NotificationRankingUpdate.CREATOR.createFromParcel((Parcel)object) : null;
                this.onListenerConnected((NotificationRankingUpdate)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INotificationListener {
            public static INotificationListener sDefaultImpl;
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
            public void onActionClicked(String string2, Notification.Action action, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (action != null) {
                        parcel.writeInt(1);
                        action.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActionClicked(string2, action, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAllowedAdjustmentsChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAllowedAdjustmentsChanged();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onInterruptionFilterChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInterruptionFilterChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (notificationRankingUpdate != null) {
                        parcel.writeInt(1);
                        notificationRankingUpdate.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onListenerConnected(notificationRankingUpdate);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onListenerHintsChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onListenerHintsChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationChannelGroupModification(String string2, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (notificationChannelGroup != null) {
                        parcel.writeInt(1);
                        notificationChannelGroup.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationChannelGroupModification(string2, userHandle, notificationChannelGroup, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationChannelModification(String string2, UserHandle userHandle, NotificationChannel notificationChannel, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (notificationChannel != null) {
                        parcel.writeInt(1);
                        notificationChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationChannelModification(string2, userHandle, notificationChannel, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationDirectReply(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationDirectReply(string2);
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
            public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStatusBarNotificationHolder != null ? iStatusBarNotificationHolder.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (notificationChannel != null) {
                        parcel.writeInt(1);
                        notificationChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onNotificationEnqueuedWithChannel(iStatusBarNotificationHolder, notificationChannel);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                int n;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationExpansionChanged(string2, bl, bl2);
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
            public void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStatusBarNotificationHolder != null ? iStatusBarNotificationHolder.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (notificationRankingUpdate != null) {
                        parcel.writeInt(1);
                        notificationRankingUpdate.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onNotificationPosted(iStatusBarNotificationHolder, notificationRankingUpdate);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (notificationRankingUpdate != null) {
                        parcel.writeInt(1);
                        notificationRankingUpdate.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationRankingUpdate(notificationRankingUpdate);
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
            public void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStatusBarNotificationHolder != null ? iStatusBarNotificationHolder.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (notificationRankingUpdate != null) {
                        parcel.writeInt(1);
                        notificationRankingUpdate.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (notificationStats != null) {
                        parcel.writeInt(1);
                        notificationStats.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onNotificationRemoved(iStatusBarNotificationHolder, notificationRankingUpdate, notificationStats, n);
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
            public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStatusBarNotificationHolder != null ? iStatusBarNotificationHolder.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onNotificationSnoozedUntilContext(iStatusBarNotificationHolder, string2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationsSeen(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationsSeen(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStatusBarIconsBehaviorChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusBarIconsBehaviorChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSuggestedReplySent(String string2, CharSequence charSequence, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuggestedReplySent(string2, charSequence, n);
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

