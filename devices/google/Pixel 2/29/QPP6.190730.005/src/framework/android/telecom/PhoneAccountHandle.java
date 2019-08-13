/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.telecom.Log;
import java.util.Objects;

public final class PhoneAccountHandle
implements Parcelable {
    public static final Parcelable.Creator<PhoneAccountHandle> CREATOR = new Parcelable.Creator<PhoneAccountHandle>(){

        @Override
        public PhoneAccountHandle createFromParcel(Parcel parcel) {
            return new PhoneAccountHandle(parcel);
        }

        public PhoneAccountHandle[] newArray(int n) {
            return new PhoneAccountHandle[n];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    private final ComponentName mComponentName;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final String mId;
    private final UserHandle mUserHandle;

    public PhoneAccountHandle(ComponentName componentName, String string2) {
        this(componentName, string2, Process.myUserHandle());
    }

    public PhoneAccountHandle(ComponentName componentName, String string2, UserHandle userHandle) {
        this.checkParameters(componentName, userHandle);
        this.mComponentName = componentName;
        this.mId = string2;
        this.mUserHandle = userHandle;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private PhoneAccountHandle(Parcel parcel) {
        this(ComponentName.CREATOR.createFromParcel(parcel), parcel.readString(), UserHandle.CREATOR.createFromParcel(parcel));
    }

    public static boolean areFromSamePackage(PhoneAccountHandle object, PhoneAccountHandle phoneAccountHandle) {
        String string2 = null;
        object = object != null ? ((PhoneAccountHandle)object).getComponentName().getPackageName() : null;
        if (phoneAccountHandle != null) {
            string2 = phoneAccountHandle.getComponentName().getPackageName();
        }
        return Objects.equals(object, string2);
    }

    private void checkParameters(ComponentName componentName, UserHandle userHandle) {
        if (componentName == null) {
            android.util.Log.w("PhoneAccountHandle", new Exception("PhoneAccountHandle has been created with null ComponentName!"));
        }
        if (userHandle == null) {
            android.util.Log.w("PhoneAccountHandle", new Exception("PhoneAccountHandle has been created with null UserHandle!"));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object != null && object instanceof PhoneAccountHandle && Objects.equals(((PhoneAccountHandle)object).getComponentName(), this.getComponentName()) && Objects.equals(((PhoneAccountHandle)object).getId(), this.getId()) && Objects.equals(((PhoneAccountHandle)object).getUserHandle(), this.getUserHandle());
        return bl;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getId() {
        return this.mId;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public int hashCode() {
        return Objects.hash(this.mComponentName, this.mId, this.mUserHandle);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mComponentName);
        stringBuilder.append(", ");
        stringBuilder.append(Log.pii(this.mId));
        stringBuilder.append(", ");
        stringBuilder.append(this.mUserHandle);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mComponentName.writeToParcel(parcel, n);
        parcel.writeString(this.mId);
        this.mUserHandle.writeToParcel(parcel, n);
    }

}

