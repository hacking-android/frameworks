/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AndroidException;

public class IntentSender
implements Parcelable {
    public static final Parcelable.Creator<IntentSender> CREATOR = new Parcelable.Creator<IntentSender>(){

        @Override
        public IntentSender createFromParcel(Parcel object) {
            object = (object = ((Parcel)object).readStrongBinder()) != null ? new IntentSender((IBinder)object) : null;
            return object;
        }

        public IntentSender[] newArray(int n) {
            return new IntentSender[n];
        }
    };
    @UnsupportedAppUsage
    private final IIntentSender mTarget;
    IBinder mWhitelistToken;

    @UnsupportedAppUsage
    public IntentSender(IIntentSender iIntentSender) {
        this.mTarget = iIntentSender;
    }

    public IntentSender(IIntentSender iIntentSender, IBinder iBinder) {
        this.mTarget = iIntentSender;
        this.mWhitelistToken = iBinder;
    }

    public IntentSender(IBinder iBinder) {
        this.mTarget = IIntentSender.Stub.asInterface(iBinder);
    }

    public static IntentSender readIntentSenderOrNullFromParcel(Parcel object) {
        object = (object = ((Parcel)object).readStrongBinder()) != null ? new IntentSender((IBinder)object) : null;
        return object;
    }

    public static void writeIntentSenderOrNullToParcel(IntentSender object, Parcel parcel) {
        object = object != null ? ((IntentSender)object).mTarget.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof IntentSender) {
            return this.mTarget.asBinder().equals(((IntentSender)object).mTarget.asBinder());
        }
        return false;
    }

    public String getCreatorPackage() {
        try {
            String string2 = ActivityManager.getService().getPackageForIntentSender(this.mTarget);
            return string2;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public int getCreatorUid() {
        try {
            int n = ActivityManager.getService().getUidForIntentSender(this.mTarget);
            return n;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public UserHandle getCreatorUserHandle() {
        UserHandle userHandle;
        block3 : {
            int n;
            userHandle = null;
            try {
                n = ActivityManager.getService().getUidForIntentSender(this.mTarget);
                if (n <= 0) break block3;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            userHandle = new UserHandle(UserHandle.getUserId(n));
        }
        return userHandle;
    }

    @UnsupportedAppUsage
    public IIntentSender getTarget() {
        return this.mTarget;
    }

    @Deprecated
    public String getTargetPackage() {
        try {
            String string2 = ActivityManager.getService().getPackageForIntentSender(this.mTarget);
            return string2;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public IBinder getWhitelistToken() {
        return this.mWhitelistToken;
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    public void sendIntent(Context context, int n, Intent intent, OnFinished onFinished, Handler handler) throws SendIntentException {
        this.sendIntent(context, n, intent, onFinished, handler, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void sendIntent(Context var1_1, int var2_4, Intent var3_5, OnFinished var4_6, Handler var5_7, String var6_8) throws SendIntentException {
        if (var3_5 != null) {
            try {
                var1_1 = var3_5.resolveTypeIfNeeded(var1_1.getContentResolver());
            }
            catch (RemoteException var1_2) {
                throw new SendIntentException();
            }
        } else {
            var1_1 = null;
        }
        var7_9 = ActivityManager.getService();
        var8_10 = this.mTarget;
        var9_11 = this.mWhitelistToken;
        if (var4_6 == null) ** GOTO lbl16
        try {
            block7 : {
                var10_12 = new FinishedDispatcher(this, (OnFinished)var4_6, var5_7);
                var4_6 = var10_12;
                break block7;
lbl16: // 1 sources:
                var4_6 = null;
            }
            if (var7_9.sendIntentSender(var8_10, var9_11, var2_4, var3_5, (String)var1_1, (IIntentReceiver)var4_6, var6_8, null) >= 0) {
                return;
            }
            var1_1 = new SendIntentException();
            throw var1_1;
        }
        catch (RemoteException var1_3) {
            // empty catch block
        }
        throw new SendIntentException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("IntentSender{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(": ");
        Object object = this.mTarget;
        object = object != null ? object.asBinder() : null;
        stringBuilder.append(object);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
    }

    private static class FinishedDispatcher
    extends IIntentReceiver.Stub
    implements Runnable {
        private final Handler mHandler;
        private Intent mIntent;
        private final IntentSender mIntentSender;
        private int mResultCode;
        private String mResultData;
        private Bundle mResultExtras;
        private final OnFinished mWho;

        FinishedDispatcher(IntentSender intentSender, OnFinished onFinished, Handler handler) {
            this.mIntentSender = intentSender;
            this.mWho = onFinished;
            this.mHandler = handler;
        }

        @Override
        public void performReceive(Intent object, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2) {
            this.mIntent = object;
            this.mResultCode = n;
            this.mResultData = string2;
            this.mResultExtras = bundle;
            object = this.mHandler;
            if (object == null) {
                this.run();
            } else {
                ((Handler)object).post(this);
            }
        }

        @Override
        public void run() {
            this.mWho.onSendFinished(this.mIntentSender, this.mIntent, this.mResultCode, this.mResultData, this.mResultExtras);
        }
    }

    public static interface OnFinished {
        public void onSendFinished(IntentSender var1, Intent var2, int var3, String var4, Bundle var5);
    }

    public static class SendIntentException
    extends AndroidException {
        public SendIntentException() {
        }

        public SendIntentException(Exception exception) {
            super(exception);
        }

        public SendIntentException(String string2) {
            super(string2);
        }
    }

}

