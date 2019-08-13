/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.annotation.UnsupportedAppUsage;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.ArrayList;

public class StatusBarNotification
implements Parcelable {
    public static final Parcelable.Creator<StatusBarNotification> CREATOR = new Parcelable.Creator<StatusBarNotification>(){

        @Override
        public StatusBarNotification createFromParcel(Parcel parcel) {
            return new StatusBarNotification(parcel);
        }

        public StatusBarNotification[] newArray(int n) {
            return new StatusBarNotification[n];
        }
    };
    static final int MAX_LOG_TAG_LENGTH = 36;
    private String groupKey;
    @UnsupportedAppUsage
    private final int id;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int initialPid;
    private final String key;
    private Context mContext;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final Notification notification;
    private final String opPkg;
    private String overrideGroupKey;
    @UnsupportedAppUsage
    private final String pkg;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final long postTime;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final String tag;
    @UnsupportedAppUsage
    private final int uid;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final UserHandle user;

    public StatusBarNotification(Parcel parcel) {
        this.pkg = parcel.readString();
        this.opPkg = parcel.readString();
        this.id = parcel.readInt();
        this.tag = parcel.readInt() != 0 ? parcel.readString() : null;
        this.uid = parcel.readInt();
        this.initialPid = parcel.readInt();
        this.notification = new Notification(parcel);
        this.user = UserHandle.readFromParcel(parcel);
        this.postTime = parcel.readLong();
        this.overrideGroupKey = parcel.readInt() != 0 ? parcel.readString() : null;
        this.key = this.key();
        this.groupKey = this.groupKey();
    }

    @Deprecated
    public StatusBarNotification(String string2, String string3, int n, String string4, int n2, int n3, int n4, Notification notification, UserHandle userHandle, long l) {
        if (string2 != null) {
            if (notification != null) {
                this.pkg = string2;
                this.opPkg = string3;
                this.id = n;
                this.tag = string4;
                this.uid = n2;
                this.initialPid = n3;
                this.notification = notification;
                this.user = userHandle;
                this.postTime = l;
                this.key = this.key();
                this.groupKey = this.groupKey();
                return;
            }
            throw new NullPointerException();
        }
        throw new NullPointerException();
    }

    public StatusBarNotification(String string2, String string3, int n, String string4, int n2, int n3, Notification notification, UserHandle userHandle, String string5, long l) {
        if (string2 != null) {
            if (notification != null) {
                this.pkg = string2;
                this.opPkg = string3;
                this.id = n;
                this.tag = string4;
                this.uid = n2;
                this.initialPid = n3;
                this.notification = notification;
                this.user = userHandle;
                this.postTime = l;
                this.overrideGroupKey = string5;
                this.key = this.key();
                this.groupKey = this.groupKey();
                return;
            }
            throw new NullPointerException();
        }
        throw new NullPointerException();
    }

    private String getChannelIdLogTag() {
        if (this.notification.getChannelId() == null) {
            return null;
        }
        return this.shortenTag(this.notification.getChannelId());
    }

    private String getGroupLogTag() {
        return this.shortenTag(this.getGroup());
    }

    private String groupKey() {
        if (this.overrideGroupKey != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.user.getIdentifier());
            stringBuilder.append("|");
            stringBuilder.append(this.pkg);
            stringBuilder.append("|g:");
            stringBuilder.append(this.overrideGroupKey);
            return stringBuilder.toString();
        }
        CharSequence charSequence = this.getNotification().getGroup();
        CharSequence charSequence2 = this.getNotification().getSortKey();
        if (charSequence == null && charSequence2 == null) {
            return this.key;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(this.user.getIdentifier());
        ((StringBuilder)charSequence2).append("|");
        ((StringBuilder)charSequence2).append(this.pkg);
        ((StringBuilder)charSequence2).append("|");
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("c:");
            ((StringBuilder)charSequence).append(this.notification.getChannelId());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("g:");
            stringBuilder.append((String)charSequence);
            charSequence = stringBuilder.toString();
        }
        ((StringBuilder)charSequence2).append((String)charSequence);
        return ((StringBuilder)charSequence2).toString();
    }

    private String key() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.user.getIdentifier());
        ((StringBuilder)charSequence).append("|");
        ((StringBuilder)charSequence).append(this.pkg);
        ((StringBuilder)charSequence).append("|");
        ((StringBuilder)charSequence).append(this.id);
        ((StringBuilder)charSequence).append("|");
        ((StringBuilder)charSequence).append(this.tag);
        ((StringBuilder)charSequence).append("|");
        ((StringBuilder)charSequence).append(this.uid);
        String string2 = ((StringBuilder)charSequence).toString();
        charSequence = string2;
        if (this.overrideGroupKey != null) {
            charSequence = string2;
            if (this.getNotification().isGroupSummary()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("|");
                ((StringBuilder)charSequence).append(this.overrideGroupKey);
                charSequence = ((StringBuilder)charSequence).toString();
            }
        }
        return charSequence;
    }

    private String shortenTag(String string2) {
        if (string2 != null && string2.length() > 36) {
            String string3 = Integer.toHexString(string2.hashCode());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2.substring(0, 36 - string3.length() - 1));
            stringBuilder.append("-");
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }
        return string2;
    }

    public void clearPackageContext() {
        this.mContext = null;
    }

    public StatusBarNotification clone() {
        return new StatusBarNotification(this.pkg, this.opPkg, this.id, this.tag, this.uid, this.initialPid, this.notification.clone(), this.user, this.overrideGroupKey, this.postTime);
    }

    public StatusBarNotification cloneLight() {
        Notification notification = new Notification();
        this.notification.cloneInto(notification, false);
        return new StatusBarNotification(this.pkg, this.opPkg, this.id, this.tag, this.uid, this.initialPid, notification, this.user, this.overrideGroupKey, this.postTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getGroup() {
        String string2 = this.overrideGroupKey;
        if (string2 != null) {
            return string2;
        }
        return this.getNotification().getGroup();
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public int getId() {
        return this.id;
    }

    @UnsupportedAppUsage
    public int getInitialPid() {
        return this.initialPid;
    }

    public String getKey() {
        return this.key;
    }

    public LogMaker getLogMaker() {
        LogMaker logMaker = new LogMaker(0).setPackageName(this.getPackageName()).addTaggedData(796, this.getId()).addTaggedData(797, this.getTag()).addTaggedData(857, this.getChannelIdLogTag()).addTaggedData(946, this.getGroupLogTag()).addTaggedData(947, (int)this.getNotification().isGroupSummary()).addTaggedData(1641, this.getNotification().category);
        if (this.getNotification().extras != null) {
            Object object = this.getNotification().extras.getString("android.template");
            if (object != null && !((String)object).isEmpty()) {
                logMaker.addTaggedData(1745, ((String)object).hashCode());
            }
            if ((object = this.getNotification().extras.getParcelableArrayList("android.people.list")) != null && !((ArrayList)object).isEmpty()) {
                logMaker.addTaggedData(1744, ((ArrayList)object).size());
            }
        }
        return logMaker;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public String getOpPkg() {
        return this.opPkg;
    }

    public String getOverrideGroupKey() {
        return this.overrideGroupKey;
    }

    @UnsupportedAppUsage
    public Context getPackageContext(Context context) {
        if (this.mContext == null) {
            try {
                this.mContext = context.createApplicationContext(context.getPackageManager().getApplicationInfoAsUser(this.pkg, 8192, this.getUserId()), 4);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                this.mContext = null;
            }
        }
        if (this.mContext == null) {
            this.mContext = context;
        }
        return this.mContext;
    }

    public String getPackageName() {
        return this.pkg;
    }

    public long getPostTime() {
        return this.postTime;
    }

    public String getTag() {
        return this.tag;
    }

    public int getUid() {
        return this.uid;
    }

    public UserHandle getUser() {
        return this.user;
    }

    @Deprecated
    public int getUserId() {
        return this.user.getIdentifier();
    }

    public boolean isAppGroup() {
        return this.getNotification().getGroup() != null || this.getNotification().getSortKey() != null;
        {
        }
    }

    public boolean isClearable() {
        boolean bl = (this.notification.flags & 2) == 0 && (this.notification.flags & 32) == 0;
        return bl;
    }

    public boolean isGroup() {
        return this.overrideGroupKey != null || this.isAppGroup();
        {
        }
    }

    public boolean isOngoing() {
        boolean bl = (this.notification.flags & 2) != 0;
        return bl;
    }

    public void setOverrideGroupKey(String string2) {
        this.overrideGroupKey = string2;
        this.groupKey = this.groupKey();
    }

    public String toString() {
        return String.format("StatusBarNotification(pkg=%s user=%s id=%d tag=%s key=%s: %s)", this.pkg, this.user, this.id, this.tag, this.key, this.notification);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.pkg);
        parcel.writeString(this.opPkg);
        parcel.writeInt(this.id);
        if (this.tag != null) {
            parcel.writeInt(1);
            parcel.writeString(this.tag);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.uid);
        parcel.writeInt(this.initialPid);
        this.notification.writeToParcel(parcel, n);
        this.user.writeToParcel(parcel, n);
        parcel.writeLong(this.postTime);
        if (this.overrideGroupKey != null) {
            parcel.writeInt(1);
            parcel.writeString(this.overrideGroupKey);
        } else {
            parcel.writeInt(0);
        }
    }

}

