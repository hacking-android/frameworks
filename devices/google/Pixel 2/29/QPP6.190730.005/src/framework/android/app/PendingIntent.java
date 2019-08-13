/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AndroidException;
import android.util.ArraySet;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.IResultReceiver;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PendingIntent
implements Parcelable {
    public static final Parcelable.Creator<PendingIntent> CREATOR;
    public static final int FLAG_CANCEL_CURRENT = 268435456;
    public static final int FLAG_IMMUTABLE = 67108864;
    public static final int FLAG_NO_CREATE = 536870912;
    public static final int FLAG_ONE_SHOT = 1073741824;
    public static final int FLAG_UPDATE_CURRENT = 134217728;
    private static final ThreadLocal<OnMarshaledListener> sOnMarshaledListener;
    private ArraySet<CancelListener> mCancelListeners;
    private IResultReceiver mCancelReceiver;
    private final IIntentSender mTarget;
    private IBinder mWhitelistToken;

    static {
        sOnMarshaledListener = new ThreadLocal();
        CREATOR = new Parcelable.Creator<PendingIntent>(){

            @Override
            public PendingIntent createFromParcel(Parcel object) {
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                object = iBinder != null ? new PendingIntent(iBinder, ((Parcel)object).getClassCookie(PendingIntent.class)) : null;
                return object;
            }

            public PendingIntent[] newArray(int n) {
                return new PendingIntent[n];
            }
        };
    }

    PendingIntent(IIntentSender iIntentSender) {
        this.mTarget = iIntentSender;
    }

    PendingIntent(IBinder iBinder, Object object) {
        this.mTarget = IIntentSender.Stub.asInterface(iBinder);
        if (object != null) {
            this.mWhitelistToken = (IBinder)object;
        }
    }

    private static PendingIntent buildServicePendingIntent(Context object, int n, Intent object2, int n2, int n3) {
        block7 : {
            Object var6_7;
            IActivityManager iActivityManager;
            String string2;
            String[] arrstring;
            block6 : {
                block5 : {
                    string2 = ((Context)object).getPackageName();
                    var6_7 = null;
                    arrstring = object2 != null ? ((Intent)object2).resolveTypeIfNeeded(((Context)object).getContentResolver()) : null;
                    try {
                        ((Intent)object2).prepareToLeaveProcess((Context)object);
                        iActivityManager = ActivityManager.getService();
                        if (arrstring == null) break block5;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    arrstring = new String[]{arrstring};
                    break block6;
                }
                arrstring = null;
            }
            int n4 = ((Context)object).getUserId();
            object2 = iActivityManager.getIntentSender(n3, string2, null, null, n, new Intent[]{object2}, arrstring, n2, null, n4);
            object = var6_7;
            if (object2 == null) break block7;
            object = new PendingIntent((IIntentSender)object2);
        }
        return object;
    }

    public static PendingIntent getActivities(Context context, int n, Intent[] arrintent, int n2) {
        return PendingIntent.getActivities(context, n, arrintent, n2, null);
    }

    public static PendingIntent getActivities(Context object, int n, Intent[] arrintent, int n2, Bundle bundle) {
        block5 : {
            block4 : {
                String string2 = ((Context)object).getPackageName();
                String[] arrstring = new String[arrintent.length];
                for (int i = 0; i < arrintent.length; ++i) {
                    arrintent[i].migrateExtraStreamToClipData();
                    arrintent[i].prepareToLeaveProcess((Context)object);
                    arrstring[i] = arrintent[i].resolveTypeIfNeeded(((Context)object).getContentResolver());
                }
                try {
                    object = ActivityManager.getService().getIntentSender(2, string2, null, null, n, arrintent, arrstring, n2, bundle, ((Context)object).getUserId());
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                object = new PendingIntent((IIntentSender)object);
                break block5;
            }
            object = null;
        }
        return object;
    }

    public static PendingIntent getActivitiesAsUser(Context object, int n, Intent[] arrintent, int n2, Bundle bundle, UserHandle userHandle) {
        block5 : {
            block4 : {
                String string2 = ((Context)object).getPackageName();
                String[] arrstring = new String[arrintent.length];
                for (int i = 0; i < arrintent.length; ++i) {
                    arrintent[i].migrateExtraStreamToClipData();
                    arrintent[i].prepareToLeaveProcess((Context)object);
                    arrstring[i] = arrintent[i].resolveTypeIfNeeded(((Context)object).getContentResolver());
                }
                try {
                    object = ActivityManager.getService().getIntentSender(2, string2, null, null, n, arrintent, arrstring, n2, bundle, userHandle.getIdentifier());
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                object = new PendingIntent((IIntentSender)object);
                break block5;
            }
            object = null;
        }
        return object;
    }

    public static PendingIntent getActivity(Context context, int n, Intent intent, int n2) {
        return PendingIntent.getActivity(context, n, intent, n2, null);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static PendingIntent getActivity(Context object, int n, Intent object2, int n2, Bundle bundle) {
        void var0_3;
        String string2 = ((Context)object).getPackageName();
        Object var6_9 = null;
        Object arrstring = object2 != null ? ((Intent)object2).resolveTypeIfNeeded(((Context)object).getContentResolver()) : null;
        ((Intent)object2).migrateExtraStreamToClipData();
        try {
            ((Intent)object2).prepareToLeaveProcess((Context)object);
            IActivityManager iActivityManager = ActivityManager.getService();
            arrstring = arrstring != null ? new String[]{arrstring} : null;
            int n3 = ((Context)object).getUserId();
            object2 = iActivityManager.getIntentSender(2, string2, null, null, n, new Intent[]{object2}, (String[])arrstring, n2, bundle, n3);
            object = var6_9;
            if (object2 == null) return object;
            return new PendingIntent((IIntentSender)object2);
        }
        catch (RemoteException remoteException) {
            throw var0_3.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var0_3.rethrowFromSystemServer();
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static PendingIntent getActivityAsUser(Context object, int n, Intent object2, int n2, Bundle bundle, UserHandle userHandle) {
        void var0_3;
        String string2 = ((Context)object).getPackageName();
        Object var7_10 = null;
        String string3 = object2 != null ? ((Intent)object2).resolveTypeIfNeeded(((Context)object).getContentResolver()) : null;
        ((Intent)object2).migrateExtraStreamToClipData();
        try {
            ((Intent)object2).prepareToLeaveProcess((Context)object);
            IActivityManager iActivityManager = ActivityManager.getService();
            object = string3 != null ? new String[]{string3} : null;
            int n3 = userHandle.getIdentifier();
            object2 = iActivityManager.getIntentSender(2, string2, null, null, n, new Intent[]{object2}, (String[])object, n2, bundle, n3);
            object = var7_10;
            if (object2 == null) return object;
            return new PendingIntent((IIntentSender)object2);
        }
        catch (RemoteException remoteException) {
            throw var0_3.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var0_3.rethrowFromSystemServer();
    }

    public static PendingIntent getBroadcast(Context context, int n, Intent intent, int n2) {
        return PendingIntent.getBroadcastAsUser(context, n, intent, n2, context.getUser());
    }

    @UnsupportedAppUsage
    public static PendingIntent getBroadcastAsUser(Context object, int n, Intent object2, int n2, UserHandle userHandle) {
        block7 : {
            Object var6_7;
            IActivityManager iActivityManager;
            String string2;
            block6 : {
                block5 : {
                    string2 = object.getPackageName();
                    var6_7 = null;
                    String string3 = object2 != null ? ((Intent)object2).resolveTypeIfNeeded(object.getContentResolver()) : null;
                    try {
                        ((Intent)object2).prepareToLeaveProcess((Context)object);
                        iActivityManager = ActivityManager.getService();
                        if (string3 == null) break block5;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    object = new String[]{string3};
                    break block6;
                }
                object = null;
            }
            int n3 = userHandle.getIdentifier();
            object2 = iActivityManager.getIntentSender(1, string2, null, null, n, new Intent[]{object2}, (String[])object, n2, null, n3);
            object = var6_7;
            if (object2 == null) break block7;
            object = new PendingIntent((IIntentSender)object2);
        }
        return object;
    }

    public static PendingIntent getForegroundService(Context context, int n, Intent intent, int n2) {
        return PendingIntent.buildServicePendingIntent(context, n, intent, n2, 5);
    }

    public static PendingIntent getService(Context context, int n, Intent intent, int n2) {
        return PendingIntent.buildServicePendingIntent(context, n, intent, n2, 4);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyCancelListeners() {
        ArraySet<CancelListener> arraySet;
        synchronized (this) {
            arraySet = new ArraySet<CancelListener>(this.mCancelListeners);
        }
        int n = arraySet.size();
        int n2 = 0;
        while (n2 < n) {
            arraySet.valueAt(n2).onCancelled(this);
            ++n2;
        }
        return;
    }

    public static PendingIntent readPendingIntentOrNullFromParcel(Parcel object) {
        IBinder iBinder = ((Parcel)object).readStrongBinder();
        object = iBinder != null ? new PendingIntent(iBinder, ((Parcel)object).getClassCookie(PendingIntent.class)) : null;
        return object;
    }

    @UnsupportedAppUsage
    public static void setOnMarshaledListener(OnMarshaledListener onMarshaledListener) {
        sOnMarshaledListener.set(onMarshaledListener);
    }

    public static void writePendingIntentOrNullToParcel(PendingIntent pendingIntent, Parcel parcel) {
        Object object = pendingIntent != null ? pendingIntent.mTarget.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
        if (pendingIntent != null && (object = sOnMarshaledListener.get()) != null) {
            object.onMarshaled(pendingIntent, parcel, 0);
        }
    }

    public void cancel() {
        try {
            ActivityManager.getService().cancelIntentSender(this.mTarget);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof PendingIntent) {
            return this.mTarget.asBinder().equals(((PendingIntent)object).mTarget.asBinder());
        }
        return false;
    }

    public String getCreatorPackage() {
        try {
            String string2 = ActivityManager.getService().getPackageForIntentSender(this.mTarget);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getCreatorUid() {
        try {
            int n = ActivityManager.getService().getUidForIntentSender(this.mTarget);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UserHandle getCreatorUserHandle() {
        UserHandle userHandle;
        block4 : {
            block3 : {
                int n;
                try {
                    n = ActivityManager.getService().getUidForIntentSender(this.mTarget);
                    if (n <= 0) break block3;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                userHandle = new UserHandle(UserHandle.getUserId(n));
                break block4;
            }
            userHandle = null;
        }
        return userHandle;
    }

    @UnsupportedAppUsage
    public Intent getIntent() {
        try {
            Intent intent = ActivityManager.getService().getIntentForIntentSender(this.mTarget);
            return intent;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public IntentSender getIntentSender() {
        return new IntentSender(this.mTarget, this.mWhitelistToken);
    }

    @UnsupportedAppUsage
    public String getTag(String string2) {
        try {
            string2 = ActivityManager.getService().getTagForIntentSender(this.mTarget, string2);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

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
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public IBinder getWhitelistToken() {
        return this.mWhitelistToken;
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    @UnsupportedAppUsage
    public boolean isActivity() {
        try {
            boolean bl = ActivityManager.getService().isIntentSenderAnActivity(this.mTarget);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isBroadcast() {
        try {
            boolean bl = ActivityManager.getService().isIntentSenderABroadcast(this.mTarget);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isForegroundService() {
        try {
            boolean bl = ActivityManager.getService().isIntentSenderAForegroundService(this.mTarget);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isTargetedToPackage() {
        try {
            boolean bl = ActivityManager.getService().isIntentSenderTargetedToPackage(this.mTarget);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCancelListener(CancelListener cancelListener) {
        synchronized (this) {
            IResultReceiver.Stub stub;
            if (this.mCancelReceiver == null) {
                stub = new IResultReceiver.Stub(){

                    @Override
                    public void send(int n, Bundle bundle) throws RemoteException {
                        PendingIntent.this.notifyCancelListeners();
                    }
                };
                this.mCancelReceiver = stub;
            }
            if (this.mCancelListeners == null) {
                stub = new ArraySet();
                this.mCancelListeners = stub;
            }
            boolean bl = this.mCancelListeners.isEmpty();
            this.mCancelListeners.add(cancelListener);
            if (bl) {
                try {
                    ActivityManager.getService().registerIntentSenderCancelListener(this.mTarget, this.mCancelReceiver);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    public void send() throws CanceledException {
        this.send(null, 0, null, null, null, null, null);
    }

    public void send(int n) throws CanceledException {
        this.send(null, n, null, null, null, null, null);
    }

    public void send(int n, OnFinished onFinished, Handler handler) throws CanceledException {
        this.send(null, n, null, onFinished, handler, null, null);
    }

    public void send(Context context, int n, Intent intent) throws CanceledException {
        this.send(context, n, intent, null, null, null, null);
    }

    public void send(Context context, int n, Intent intent, OnFinished onFinished, Handler handler) throws CanceledException {
        this.send(context, n, intent, onFinished, handler, null, null);
    }

    public void send(Context context, int n, Intent intent, OnFinished onFinished, Handler handler, String string2) throws CanceledException {
        this.send(context, n, intent, onFinished, handler, string2, null);
    }

    public void send(Context context, int n, Intent intent, OnFinished onFinished, Handler handler, String string2, Bundle bundle) throws CanceledException {
        if (this.sendAndReturnResult(context, n, intent, onFinished, handler, string2, bundle) >= 0) {
            return;
        }
        throw new CanceledException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int sendAndReturnResult(Context var1_1, int var2_5, Intent var3_6, OnFinished var4_7, Handler var5_8, String var6_9, Bundle var7_10) throws CanceledException {
        if (var3_6 != null) {
            try {
                var1_1 = var3_6.resolveTypeIfNeeded(var1_1.getContentResolver());
            }
            catch (RemoteException var1_2) {
                throw new CanceledException((Exception)var1_4);
            }
        } else {
            var1_1 = null;
        }
        var8_11 = ActivityManager.getService();
        var9_12 = this.mTarget;
        var10_13 = this.mWhitelistToken;
        if (var4_7 == null) ** GOTO lbl16
        try {
            var11_14 = new FinishedDispatcher(this, (OnFinished)var4_7, var5_8);
            var4_7 = var11_14;
            return var8_11.sendIntentSender(var9_12, var10_13, var2_5, var3_6, (String)var1_1, (IIntentReceiver)var4_7, var6_9, var7_10);
lbl16: // 1 sources:
            var4_7 = null;
            return var8_11.sendIntentSender(var9_12, var10_13, var2_5, var3_6, (String)var1_1, (IIntentReceiver)var4_7, var6_9, var7_10);
        }
        catch (RemoteException var1_3) {
            // empty catch block
        }
        throw new CanceledException((Exception)var1_4);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("PendingIntent{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(": ");
        Object object = this.mTarget;
        object = object != null ? object.asBinder() : null;
        stringBuilder.append(object);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCancelListener(CancelListener cancelListener) {
        synchronized (this) {
            if (this.mCancelListeners == null) {
                return;
            }
            boolean bl = this.mCancelListeners.isEmpty();
            this.mCancelListeners.remove(cancelListener);
            boolean bl2 = this.mCancelListeners.isEmpty();
            if (bl2 && !bl) {
                try {
                    ActivityManager.getService().unregisterIntentSenderCancelListener(this.mTarget, this.mCancelReceiver);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
        OnMarshaledListener onMarshaledListener = sOnMarshaledListener.get();
        if (onMarshaledListener != null) {
            onMarshaledListener.onMarshaled(this, parcel, n);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        IIntentSender iIntentSender = this.mTarget;
        if (iIntentSender != null) {
            protoOutputStream.write(1138166333441L, iIntentSender.asBinder().toString());
        }
        protoOutputStream.end(l);
    }

    public static interface CancelListener {
        public void onCancelled(PendingIntent var1);
    }

    public static class CanceledException
    extends AndroidException {
        public CanceledException() {
        }

        public CanceledException(Exception exception) {
            super(exception);
        }

        public CanceledException(String string2) {
            super(string2);
        }
    }

    private static class FinishedDispatcher
    extends IIntentReceiver.Stub
    implements Runnable {
        private static Handler sDefaultSystemHandler;
        private final Handler mHandler;
        private Intent mIntent;
        private final PendingIntent mPendingIntent;
        private int mResultCode;
        private String mResultData;
        private Bundle mResultExtras;
        private final OnFinished mWho;

        FinishedDispatcher(PendingIntent pendingIntent, OnFinished onFinished, Handler handler) {
            this.mPendingIntent = pendingIntent;
            this.mWho = onFinished;
            if (handler == null && ActivityThread.isSystem()) {
                if (sDefaultSystemHandler == null) {
                    sDefaultSystemHandler = new Handler(Looper.getMainLooper());
                }
                this.mHandler = sDefaultSystemHandler;
            } else {
                this.mHandler = handler;
            }
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
            this.mWho.onSendFinished(this.mPendingIntent, this.mIntent, this.mResultCode, this.mResultData, this.mResultExtras);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Flags {
    }

    public static interface OnFinished {
        public void onSendFinished(PendingIntent var1, Intent var2, int var3, String var4, Bundle var5);
    }

    public static interface OnMarshaledListener {
        public void onMarshaled(PendingIntent var1, Parcel var2, int var3);
    }

}

