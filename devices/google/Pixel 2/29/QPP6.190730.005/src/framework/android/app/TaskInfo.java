/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

public class TaskInfo {
    private static final String TAG = "TaskInfo";
    public ComponentName baseActivity;
    public Intent baseIntent;
    @UnsupportedAppUsage
    public final Configuration configuration = new Configuration();
    public int displayId;
    public boolean isRunning;
    @UnsupportedAppUsage
    public long lastActiveTime;
    public int numActivities;
    public ComponentName origActivity;
    public ComponentName realActivity;
    @UnsupportedAppUsage
    public int resizeMode;
    @UnsupportedAppUsage
    public int stackId;
    @UnsupportedAppUsage
    public boolean supportsSplitScreenMultiWindow;
    public ActivityManager.TaskDescription taskDescription;
    public int taskId;
    public ComponentName topActivity;
    @UnsupportedAppUsage
    public int userId;

    TaskInfo() {
    }

    private TaskInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public ActivityManager.TaskSnapshot getTaskSnapshot(boolean bl) {
        try {
            ActivityManager.TaskSnapshot taskSnapshot = ActivityManager.getService().getTaskSnapshot(this.taskId, bl);
            return taskSnapshot;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to get task snapshot, taskId=");
            stringBuilder.append(this.taskId);
            Log.e(TAG, stringBuilder.toString(), remoteException);
            return null;
        }
    }

    void readFromParcel(Parcel parcel) {
        this.userId = parcel.readInt();
        this.stackId = parcel.readInt();
        this.taskId = parcel.readInt();
        this.displayId = parcel.readInt();
        this.isRunning = parcel.readBoolean();
        int n = parcel.readInt();
        Object var3_3 = null;
        Parcelable parcelable = n != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
        this.baseIntent = parcelable;
        this.baseActivity = ComponentName.readFromParcel(parcel);
        this.topActivity = ComponentName.readFromParcel(parcel);
        this.origActivity = ComponentName.readFromParcel(parcel);
        this.realActivity = ComponentName.readFromParcel(parcel);
        this.numActivities = parcel.readInt();
        this.lastActiveTime = parcel.readLong();
        parcelable = parcel.readInt() != 0 ? ActivityManager.TaskDescription.CREATOR.createFromParcel(parcel) : var3_3;
        this.taskDescription = parcelable;
        this.supportsSplitScreenMultiWindow = parcel.readBoolean();
        this.resizeMode = parcel.readInt();
        this.configuration.readFromParcel(parcel);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TaskInfo{userId=");
        stringBuilder.append(this.userId);
        stringBuilder.append(" stackId=");
        stringBuilder.append(this.stackId);
        stringBuilder.append(" taskId=");
        stringBuilder.append(this.taskId);
        stringBuilder.append(" displayId=");
        stringBuilder.append(this.displayId);
        stringBuilder.append(" isRunning=");
        stringBuilder.append(this.isRunning);
        stringBuilder.append(" baseIntent=");
        stringBuilder.append(this.baseIntent);
        stringBuilder.append(" baseActivity=");
        stringBuilder.append(this.baseActivity);
        stringBuilder.append(" topActivity=");
        stringBuilder.append(this.topActivity);
        stringBuilder.append(" origActivity=");
        stringBuilder.append(this.origActivity);
        stringBuilder.append(" realActivity=");
        stringBuilder.append(this.realActivity);
        stringBuilder.append(" numActivities=");
        stringBuilder.append(this.numActivities);
        stringBuilder.append(" lastActiveTime=");
        stringBuilder.append(this.lastActiveTime);
        stringBuilder.append(" supportsSplitScreenMultiWindow=");
        stringBuilder.append(this.supportsSplitScreenMultiWindow);
        stringBuilder.append(" resizeMode=");
        stringBuilder.append(this.resizeMode);
        return stringBuilder.toString();
    }

    void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.userId);
        parcel.writeInt(this.stackId);
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.displayId);
        parcel.writeBoolean(this.isRunning);
        if (this.baseIntent != null) {
            parcel.writeInt(1);
            this.baseIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        ComponentName.writeToParcel(this.baseActivity, parcel);
        ComponentName.writeToParcel(this.topActivity, parcel);
        ComponentName.writeToParcel(this.origActivity, parcel);
        ComponentName.writeToParcel(this.realActivity, parcel);
        parcel.writeInt(this.numActivities);
        parcel.writeLong(this.lastActiveTime);
        if (this.taskDescription != null) {
            parcel.writeInt(1);
            this.taskDescription.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.supportsSplitScreenMultiWindow);
        parcel.writeInt(this.resizeMode);
        this.configuration.writeToParcel(parcel, n);
    }
}

