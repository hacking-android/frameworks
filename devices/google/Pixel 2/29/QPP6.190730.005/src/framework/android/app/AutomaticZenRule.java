/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.ZenPolicy;
import java.util.Objects;

public final class AutomaticZenRule
implements Parcelable {
    public static final Parcelable.Creator<AutomaticZenRule> CREATOR = new Parcelable.Creator<AutomaticZenRule>(){

        @Override
        public AutomaticZenRule createFromParcel(Parcel parcel) {
            return new AutomaticZenRule(parcel);
        }

        public AutomaticZenRule[] newArray(int n) {
            return new AutomaticZenRule[n];
        }
    };
    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    private Uri conditionId;
    private ComponentName configurationActivity;
    private long creationTime;
    private boolean enabled;
    private int interruptionFilter;
    private boolean mModified;
    private ZenPolicy mZenPolicy;
    private String name;
    private ComponentName owner;

    public AutomaticZenRule(Parcel parcel) {
        boolean bl = false;
        this.enabled = false;
        this.mModified = false;
        boolean bl2 = parcel.readInt() == 1;
        this.enabled = bl2;
        if (parcel.readInt() == 1) {
            this.name = parcel.readString();
        }
        this.interruptionFilter = parcel.readInt();
        this.conditionId = (Uri)parcel.readParcelable(null);
        this.owner = (ComponentName)parcel.readParcelable(null);
        this.configurationActivity = (ComponentName)parcel.readParcelable(null);
        this.creationTime = parcel.readLong();
        this.mZenPolicy = (ZenPolicy)parcel.readParcelable(null);
        bl2 = bl;
        if (parcel.readInt() == 1) {
            bl2 = true;
        }
        this.mModified = bl2;
    }

    public AutomaticZenRule(String string2, ComponentName componentName, ComponentName componentName2, Uri uri, ZenPolicy zenPolicy, int n, boolean bl) {
        this.enabled = false;
        this.mModified = false;
        this.name = string2;
        this.owner = componentName;
        this.configurationActivity = componentName2;
        this.conditionId = uri;
        this.interruptionFilter = n;
        this.enabled = bl;
        this.mZenPolicy = zenPolicy;
    }

    public AutomaticZenRule(String string2, ComponentName componentName, ComponentName componentName2, Uri uri, ZenPolicy zenPolicy, int n, boolean bl, long l) {
        this(string2, componentName, componentName2, uri, zenPolicy, n, bl);
        this.creationTime = l;
    }

    @Deprecated
    public AutomaticZenRule(String string2, ComponentName componentName, Uri uri, int n, boolean bl) {
        this(string2, componentName, null, uri, null, n, bl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof AutomaticZenRule)) {
            return false;
        }
        boolean bl = true;
        if (object == this) {
            return true;
        }
        object = (AutomaticZenRule)object;
        if (!(((AutomaticZenRule)object).enabled == this.enabled && ((AutomaticZenRule)object).mModified == this.mModified && Objects.equals(((AutomaticZenRule)object).name, this.name) && ((AutomaticZenRule)object).interruptionFilter == this.interruptionFilter && Objects.equals(((AutomaticZenRule)object).conditionId, this.conditionId) && Objects.equals(((AutomaticZenRule)object).owner, this.owner) && Objects.equals(((AutomaticZenRule)object).mZenPolicy, this.mZenPolicy) && Objects.equals(((AutomaticZenRule)object).configurationActivity, this.configurationActivity) && ((AutomaticZenRule)object).creationTime == this.creationTime)) {
            bl = false;
        }
        return bl;
    }

    public Uri getConditionId() {
        return this.conditionId;
    }

    public ComponentName getConfigurationActivity() {
        return this.configurationActivity;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public int getInterruptionFilter() {
        return this.interruptionFilter;
    }

    public String getName() {
        return this.name;
    }

    public ComponentName getOwner() {
        return this.owner;
    }

    public ZenPolicy getZenPolicy() {
        ZenPolicy zenPolicy = this.mZenPolicy;
        zenPolicy = zenPolicy == null ? null : zenPolicy.copy();
        return zenPolicy;
    }

    public int hashCode() {
        return Objects.hash(this.enabled, this.name, this.interruptionFilter, this.conditionId, this.owner, this.configurationActivity, this.mZenPolicy, this.mModified, this.creationTime);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isModified() {
        return this.mModified;
    }

    public void setConditionId(Uri uri) {
        this.conditionId = uri;
    }

    public void setConfigurationActivity(ComponentName componentName) {
        this.configurationActivity = componentName;
    }

    public void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    public void setInterruptionFilter(int n) {
        this.interruptionFilter = n;
    }

    public void setModified(boolean bl) {
        this.mModified = bl;
    }

    public void setName(String string2) {
        this.name = string2;
    }

    public void setZenPolicy(ZenPolicy zenPolicy) {
        zenPolicy = zenPolicy == null ? null : zenPolicy.copy();
        this.mZenPolicy = zenPolicy;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(AutomaticZenRule.class.getSimpleName());
        stringBuilder.append('[');
        stringBuilder.append("enabled=");
        stringBuilder.append(this.enabled);
        stringBuilder.append(",name=");
        stringBuilder.append(this.name);
        stringBuilder.append(",interruptionFilter=");
        stringBuilder.append(this.interruptionFilter);
        stringBuilder.append(",conditionId=");
        stringBuilder.append(this.conditionId);
        stringBuilder.append(",owner=");
        stringBuilder.append(this.owner);
        stringBuilder.append(",configActivity=");
        stringBuilder.append(this.configurationActivity);
        stringBuilder.append(",creationTime=");
        stringBuilder.append(this.creationTime);
        stringBuilder.append(",mZenPolicy=");
        stringBuilder.append(this.mZenPolicy);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.enabled);
        if (this.name != null) {
            parcel.writeInt(1);
            parcel.writeString(this.name);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.interruptionFilter);
        parcel.writeParcelable(this.conditionId, 0);
        parcel.writeParcelable(this.owner, 0);
        parcel.writeParcelable(this.configurationActivity, 0);
        parcel.writeLong(this.creationTime);
        parcel.writeParcelable(this.mZenPolicy, 0);
        parcel.writeInt((int)this.mModified);
    }

}

