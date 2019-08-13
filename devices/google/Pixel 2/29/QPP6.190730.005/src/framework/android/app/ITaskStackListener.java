/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITaskStackListener
extends IInterface {
    public static final int FORCED_RESIZEABLE_REASON_SECONDARY_DISPLAY = 2;
    public static final int FORCED_RESIZEABLE_REASON_SPLIT_SCREEN = 1;

    public void onActivityDismissingDockedStack() throws RemoteException;

    public void onActivityForcedResizable(String var1, int var2, int var3) throws RemoteException;

    public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo var1, int var2) throws RemoteException;

    public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo var1, int var2) throws RemoteException;

    public void onActivityPinned(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onActivityRequestedOrientationChanged(int var1, int var2) throws RemoteException;

    public void onActivityUnpinned() throws RemoteException;

    public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo var1) throws RemoteException;

    public void onPinnedActivityRestartAttempt(boolean var1) throws RemoteException;

    public void onPinnedStackAnimationEnded() throws RemoteException;

    public void onPinnedStackAnimationStarted() throws RemoteException;

    public void onSizeCompatModeActivityChanged(int var1, IBinder var2) throws RemoteException;

    public void onTaskCreated(int var1, ComponentName var2) throws RemoteException;

    public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo var1) throws RemoteException;

    public void onTaskDisplayChanged(int var1, int var2) throws RemoteException;

    public void onTaskMovedToFront(ActivityManager.RunningTaskInfo var1) throws RemoteException;

    public void onTaskProfileLocked(int var1, int var2) throws RemoteException;

    public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo var1) throws RemoteException;

    public void onTaskRemoved(int var1) throws RemoteException;

    public void onTaskSnapshotChanged(int var1, ActivityManager.TaskSnapshot var2) throws RemoteException;

    public void onTaskStackChanged() throws RemoteException;

    public static class Default
    implements ITaskStackListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActivityDismissingDockedStack() throws RemoteException {
        }

        @Override
        public void onActivityForcedResizable(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int n) throws RemoteException {
        }

        @Override
        public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int n) throws RemoteException {
        }

        @Override
        public void onActivityPinned(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onActivityRequestedOrientationChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onActivityUnpinned() throws RemoteException {
        }

        @Override
        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override
        public void onPinnedActivityRestartAttempt(boolean bl) throws RemoteException {
        }

        @Override
        public void onPinnedStackAnimationEnded() throws RemoteException {
        }

        @Override
        public void onPinnedStackAnimationStarted() throws RemoteException {
        }

        @Override
        public void onSizeCompatModeActivityChanged(int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void onTaskCreated(int n, ComponentName componentName) throws RemoteException {
        }

        @Override
        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override
        public void onTaskDisplayChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override
        public void onTaskProfileLocked(int n, int n2) throws RemoteException {
        }

        @Override
        public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override
        public void onTaskRemoved(int n) throws RemoteException {
        }

        @Override
        public void onTaskSnapshotChanged(int n, ActivityManager.TaskSnapshot taskSnapshot) throws RemoteException {
        }

        @Override
        public void onTaskStackChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITaskStackListener {
        private static final String DESCRIPTOR = "android.app.ITaskStackListener";
        static final int TRANSACTION_onActivityDismissingDockedStack = 8;
        static final int TRANSACTION_onActivityForcedResizable = 7;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayFailed = 9;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayRerouted = 10;
        static final int TRANSACTION_onActivityPinned = 2;
        static final int TRANSACTION_onActivityRequestedOrientationChanged = 15;
        static final int TRANSACTION_onActivityUnpinned = 3;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 20;
        static final int TRANSACTION_onPinnedActivityRestartAttempt = 4;
        static final int TRANSACTION_onPinnedStackAnimationEnded = 6;
        static final int TRANSACTION_onPinnedStackAnimationStarted = 5;
        static final int TRANSACTION_onSizeCompatModeActivityChanged = 19;
        static final int TRANSACTION_onTaskCreated = 11;
        static final int TRANSACTION_onTaskDescriptionChanged = 14;
        static final int TRANSACTION_onTaskDisplayChanged = 21;
        static final int TRANSACTION_onTaskMovedToFront = 13;
        static final int TRANSACTION_onTaskProfileLocked = 17;
        static final int TRANSACTION_onTaskRemovalStarted = 16;
        static final int TRANSACTION_onTaskRemoved = 12;
        static final int TRANSACTION_onTaskSnapshotChanged = 18;
        static final int TRANSACTION_onTaskStackChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITaskStackListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITaskStackListener) {
                return (ITaskStackListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITaskStackListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 21: {
                    return "onTaskDisplayChanged";
                }
                case 20: {
                    return "onBackPressedOnTaskRoot";
                }
                case 19: {
                    return "onSizeCompatModeActivityChanged";
                }
                case 18: {
                    return "onTaskSnapshotChanged";
                }
                case 17: {
                    return "onTaskProfileLocked";
                }
                case 16: {
                    return "onTaskRemovalStarted";
                }
                case 15: {
                    return "onActivityRequestedOrientationChanged";
                }
                case 14: {
                    return "onTaskDescriptionChanged";
                }
                case 13: {
                    return "onTaskMovedToFront";
                }
                case 12: {
                    return "onTaskRemoved";
                }
                case 11: {
                    return "onTaskCreated";
                }
                case 10: {
                    return "onActivityLaunchOnSecondaryDisplayRerouted";
                }
                case 9: {
                    return "onActivityLaunchOnSecondaryDisplayFailed";
                }
                case 8: {
                    return "onActivityDismissingDockedStack";
                }
                case 7: {
                    return "onActivityForcedResizable";
                }
                case 6: {
                    return "onPinnedStackAnimationEnded";
                }
                case 5: {
                    return "onPinnedStackAnimationStarted";
                }
                case 4: {
                    return "onPinnedActivityRestartAttempt";
                }
                case 3: {
                    return "onActivityUnpinned";
                }
                case 2: {
                    return "onActivityPinned";
                }
                case 1: 
            }
            return "onTaskStackChanged";
        }

        public static boolean setDefaultImpl(ITaskStackListener iTaskStackListener) {
            if (Proxy.sDefaultImpl == null && iTaskStackListener != null) {
                Proxy.sDefaultImpl = iTaskStackListener;
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
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTaskDisplayChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ActivityManager.RunningTaskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onBackPressedOnTaskRoot((ActivityManager.RunningTaskInfo)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSizeCompatModeActivityChanged(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ActivityManager.TaskSnapshot.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTaskSnapshotChanged(n, (ActivityManager.TaskSnapshot)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTaskProfileLocked(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ActivityManager.RunningTaskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTaskRemovalStarted((ActivityManager.RunningTaskInfo)object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onActivityRequestedOrientationChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ActivityManager.RunningTaskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTaskDescriptionChanged((ActivityManager.RunningTaskInfo)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ActivityManager.RunningTaskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTaskMovedToFront((ActivityManager.RunningTaskInfo)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onTaskRemoved(((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTaskCreated(n, (ComponentName)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ActivityManager.RunningTaskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onActivityLaunchOnSecondaryDisplayRerouted((ActivityManager.RunningTaskInfo)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ActivityManager.RunningTaskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onActivityLaunchOnSecondaryDisplayFailed((ActivityManager.RunningTaskInfo)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onActivityDismissingDockedStack();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onActivityForcedResizable(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPinnedStackAnimationEnded();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPinnedStackAnimationStarted();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.onPinnedActivityRestartAttempt(bl);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onActivityUnpinned();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onActivityPinned(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onTaskStackChanged();
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITaskStackListener {
            public static ITaskStackListener sDefaultImpl;
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
            public void onActivityDismissingDockedStack() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityDismissingDockedStack();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivityForcedResizable(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityForcedResizable(string2, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (runningTaskInfo != null) {
                        parcel.writeInt(1);
                        runningTaskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityLaunchOnSecondaryDisplayFailed(runningTaskInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (runningTaskInfo != null) {
                        parcel.writeInt(1);
                        runningTaskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityLaunchOnSecondaryDisplayRerouted(runningTaskInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivityPinned(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityPinned(string2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivityRequestedOrientationChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityRequestedOrientationChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onActivityUnpinned() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityUnpinned();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (runningTaskInfo != null) {
                        parcel.writeInt(1);
                        runningTaskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBackPressedOnTaskRoot(runningTaskInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPinnedActivityRestartAttempt(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPinnedActivityRestartAttempt(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPinnedStackAnimationEnded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPinnedStackAnimationEnded();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPinnedStackAnimationStarted() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPinnedStackAnimationStarted();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSizeCompatModeActivityChanged(int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSizeCompatModeActivityChanged(n, iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskCreated(int n, ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskCreated(n, componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (runningTaskInfo != null) {
                        parcel.writeInt(1);
                        runningTaskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskDescriptionChanged(runningTaskInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskDisplayChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskDisplayChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (runningTaskInfo != null) {
                        parcel.writeInt(1);
                        runningTaskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskMovedToFront(runningTaskInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskProfileLocked(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskProfileLocked(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (runningTaskInfo != null) {
                        parcel.writeInt(1);
                        runningTaskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskRemovalStarted(runningTaskInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskRemoved(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskRemoved(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskSnapshotChanged(int n, ActivityManager.TaskSnapshot taskSnapshot) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (taskSnapshot != null) {
                        parcel.writeInt(1);
                        taskSnapshot.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskSnapshotChanged(n, taskSnapshot);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTaskStackChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskStackChanged();
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

