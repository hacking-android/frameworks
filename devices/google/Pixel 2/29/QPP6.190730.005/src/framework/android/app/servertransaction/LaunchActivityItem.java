/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.ProfilerInfo;
import android.app.ResultInfo;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Trace;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LaunchActivityItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<LaunchActivityItem> CREATOR = new Parcelable.Creator<LaunchActivityItem>(){

        @Override
        public LaunchActivityItem createFromParcel(Parcel parcel) {
            return new LaunchActivityItem(parcel);
        }

        public LaunchActivityItem[] newArray(int n) {
            return new LaunchActivityItem[n];
        }
    };
    private IBinder mAssistToken;
    private CompatibilityInfo mCompatInfo;
    private Configuration mCurConfig;
    private int mIdent;
    @UnsupportedAppUsage
    private ActivityInfo mInfo;
    @UnsupportedAppUsage
    private Intent mIntent;
    private boolean mIsForward;
    private Configuration mOverrideConfig;
    private List<ReferrerIntent> mPendingNewIntents;
    private List<ResultInfo> mPendingResults;
    private PersistableBundle mPersistentState;
    private int mProcState;
    private ProfilerInfo mProfilerInfo;
    private String mReferrer;
    private Bundle mState;
    private IVoiceInteractor mVoiceInteractor;

    private LaunchActivityItem() {
    }

    private LaunchActivityItem(Parcel parcel) {
        LaunchActivityItem.setValues(this, parcel.readTypedObject(Intent.CREATOR), parcel.readInt(), parcel.readTypedObject(ActivityInfo.CREATOR), parcel.readTypedObject(Configuration.CREATOR), parcel.readTypedObject(Configuration.CREATOR), parcel.readTypedObject(CompatibilityInfo.CREATOR), parcel.readString(), IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readBundle(this.getClass().getClassLoader()), parcel.readPersistableBundle(this.getClass().getClassLoader()), parcel.createTypedArrayList(ResultInfo.CREATOR), parcel.createTypedArrayList(ReferrerIntent.CREATOR), parcel.readBoolean(), parcel.readTypedObject(ProfilerInfo.CREATOR), parcel.readStrongBinder());
    }

    private boolean activityInfoEqual(ActivityInfo activityInfo) {
        ActivityInfo activityInfo2 = this.mInfo;
        boolean bl = true;
        boolean bl2 = true;
        if (activityInfo2 == null) {
            if (activityInfo != null) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = activityInfo != null && activityInfo2.flags == activityInfo.flags && this.mInfo.maxAspectRatio == activityInfo.maxAspectRatio && Objects.equals(this.mInfo.launchToken, activityInfo.launchToken) && Objects.equals(this.mInfo.getComponentName(), activityInfo.getComponentName()) ? bl : false;
        return bl2;
    }

    private static boolean areBundlesEqual(BaseBundle baseBundle, BaseBundle baseBundle2) {
        boolean bl = true;
        if (baseBundle != null && baseBundle2 != null) {
            if (baseBundle.size() != baseBundle2.size()) {
                return false;
            }
            for (String string2 : baseBundle.keySet()) {
                if (string2 == null || Objects.equals(baseBundle.get(string2), baseBundle2.get(string2))) continue;
                return false;
            }
            return true;
        }
        if (baseBundle != baseBundle2) {
            bl = false;
        }
        return bl;
    }

    public static LaunchActivityItem obtain(Intent intent, int n, ActivityInfo activityInfo, Configuration configuration, Configuration configuration2, CompatibilityInfo compatibilityInfo, String string2, IVoiceInteractor iVoiceInteractor, int n2, Bundle bundle, PersistableBundle persistableBundle, List<ResultInfo> list, List<ReferrerIntent> list2, boolean bl, ProfilerInfo profilerInfo, IBinder iBinder) {
        LaunchActivityItem launchActivityItem;
        LaunchActivityItem launchActivityItem2 = launchActivityItem = ObjectPool.obtain(LaunchActivityItem.class);
        if (launchActivityItem == null) {
            launchActivityItem2 = new LaunchActivityItem();
        }
        LaunchActivityItem.setValues(launchActivityItem2, intent, n, activityInfo, configuration, configuration2, compatibilityInfo, string2, iVoiceInteractor, n2, bundle, persistableBundle, list, list2, bl, profilerInfo, iBinder);
        return launchActivityItem2;
    }

    private static void setValues(LaunchActivityItem launchActivityItem, Intent intent, int n, ActivityInfo activityInfo, Configuration configuration, Configuration configuration2, CompatibilityInfo compatibilityInfo, String string2, IVoiceInteractor iVoiceInteractor, int n2, Bundle bundle, PersistableBundle persistableBundle, List<ResultInfo> list, List<ReferrerIntent> list2, boolean bl, ProfilerInfo profilerInfo, IBinder iBinder) {
        launchActivityItem.mIntent = intent;
        launchActivityItem.mIdent = n;
        launchActivityItem.mInfo = activityInfo;
        launchActivityItem.mCurConfig = configuration;
        launchActivityItem.mOverrideConfig = configuration2;
        launchActivityItem.mCompatInfo = compatibilityInfo;
        launchActivityItem.mReferrer = string2;
        launchActivityItem.mVoiceInteractor = iVoiceInteractor;
        launchActivityItem.mProcState = n2;
        launchActivityItem.mState = bundle;
        launchActivityItem.mPersistentState = persistableBundle;
        launchActivityItem.mPendingResults = list;
        launchActivityItem.mPendingNewIntents = list2;
        launchActivityItem.mIsForward = bl;
        launchActivityItem.mProfilerInfo = profilerInfo;
        launchActivityItem.mAssistToken = iBinder;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Intent intent;
            object = (LaunchActivityItem)object;
            boolean bl2 = this.mIntent == null && ((LaunchActivityItem)object).mIntent == null || (intent = this.mIntent) != null && intent.filterEquals(((LaunchActivityItem)object).mIntent);
            if (!(bl2 && this.mIdent == ((LaunchActivityItem)object).mIdent && this.activityInfoEqual(((LaunchActivityItem)object).mInfo) && Objects.equals(this.mCurConfig, ((LaunchActivityItem)object).mCurConfig) && Objects.equals(this.mOverrideConfig, ((LaunchActivityItem)object).mOverrideConfig) && Objects.equals(this.mCompatInfo, ((LaunchActivityItem)object).mCompatInfo) && Objects.equals(this.mReferrer, ((LaunchActivityItem)object).mReferrer) && this.mProcState == ((LaunchActivityItem)object).mProcState && LaunchActivityItem.areBundlesEqual(this.mState, ((LaunchActivityItem)object).mState) && LaunchActivityItem.areBundlesEqual(this.mPersistentState, ((LaunchActivityItem)object).mPersistentState) && Objects.equals(this.mPendingResults, ((LaunchActivityItem)object).mPendingResults) && Objects.equals(this.mPendingNewIntents, ((LaunchActivityItem)object).mPendingNewIntents) && this.mIsForward == ((LaunchActivityItem)object).mIsForward && Objects.equals(this.mProfilerInfo, ((LaunchActivityItem)object).mProfilerInfo) && Objects.equals(this.mAssistToken, ((LaunchActivityItem)object).mAssistToken))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityStart");
        clientTransactionHandler.handleLaunchActivity(new ActivityThread.ActivityClientRecord(iBinder, this.mIntent, this.mIdent, this.mInfo, this.mOverrideConfig, this.mCompatInfo, this.mReferrer, this.mVoiceInteractor, this.mState, this.mPersistentState, this.mPendingResults, this.mPendingNewIntents, this.mIsForward, this.mProfilerInfo, clientTransactionHandler, this.mAssistToken), pendingTransactionActions, null);
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        int n = this.mIntent.filterHashCode();
        int n2 = this.mIdent;
        int n3 = Objects.hashCode(this.mCurConfig);
        int n4 = Objects.hashCode(this.mOverrideConfig);
        int n5 = Objects.hashCode(this.mCompatInfo);
        int n6 = Objects.hashCode(this.mReferrer);
        int n7 = Objects.hashCode(this.mProcState);
        BaseBundle baseBundle = this.mState;
        int n8 = 0;
        int n9 = baseBundle != null ? baseBundle.size() : 0;
        baseBundle = this.mPersistentState;
        if (baseBundle != null) {
            n8 = baseBundle.size();
        }
        return (((((((((((((17 * 31 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n9) * 31 + n8) * 31 + Objects.hashCode(this.mPendingResults)) * 31 + Objects.hashCode(this.mPendingNewIntents)) * 31 + this.mIsForward) * 31 + Objects.hashCode(this.mProfilerInfo)) * 31 + Objects.hashCode(this.mAssistToken);
    }

    @Override
    public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.countLaunchingActivities(-1);
    }

    @Override
    public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
        clientTransactionHandler.countLaunchingActivities(1);
        clientTransactionHandler.updateProcessState(this.mProcState, false);
        clientTransactionHandler.updatePendingConfiguration(this.mCurConfig);
    }

    @Override
    public void recycle() {
        LaunchActivityItem.setValues(this, null, 0, null, null, null, null, null, null, 0, null, null, null, null, false, null, null);
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LaunchActivityItem{intent=");
        stringBuilder.append(this.mIntent);
        stringBuilder.append(",ident=");
        stringBuilder.append(this.mIdent);
        stringBuilder.append(",info=");
        stringBuilder.append(this.mInfo);
        stringBuilder.append(",curConfig=");
        stringBuilder.append(this.mCurConfig);
        stringBuilder.append(",overrideConfig=");
        stringBuilder.append(this.mOverrideConfig);
        stringBuilder.append(",referrer=");
        stringBuilder.append(this.mReferrer);
        stringBuilder.append(",procState=");
        stringBuilder.append(this.mProcState);
        stringBuilder.append(",state=");
        stringBuilder.append(this.mState);
        stringBuilder.append(",persistentState=");
        stringBuilder.append(this.mPersistentState);
        stringBuilder.append(",pendingResults=");
        stringBuilder.append(this.mPendingResults);
        stringBuilder.append(",pendingNewIntents=");
        stringBuilder.append(this.mPendingNewIntents);
        stringBuilder.append(",profilerInfo=");
        stringBuilder.append(this.mProfilerInfo);
        stringBuilder.append(" assistToken=");
        stringBuilder.append(this.mAssistToken);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.mIntent, n);
        parcel.writeInt(this.mIdent);
        parcel.writeTypedObject(this.mInfo, n);
        parcel.writeTypedObject(this.mCurConfig, n);
        parcel.writeTypedObject(this.mOverrideConfig, n);
        parcel.writeTypedObject(this.mCompatInfo, n);
        parcel.writeString(this.mReferrer);
        parcel.writeStrongInterface(this.mVoiceInteractor);
        parcel.writeInt(this.mProcState);
        parcel.writeBundle(this.mState);
        parcel.writePersistableBundle(this.mPersistentState);
        parcel.writeTypedList(this.mPendingResults, n);
        parcel.writeTypedList(this.mPendingNewIntents, n);
        parcel.writeBoolean(this.mIsForward);
        parcel.writeTypedObject(this.mProfilerInfo, n);
        parcel.writeStrongBinder(this.mAssistToken);
    }

}

