/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlSerializer
 */
package android.app.admin;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

public final class SystemUpdateInfo
implements Parcelable {
    private static final String ATTR_ORIGINAL_BUILD = "original-build";
    private static final String ATTR_RECEIVED_TIME = "received-time";
    private static final String ATTR_SECURITY_PATCH_STATE = "security-patch-state";
    public static final Parcelable.Creator<SystemUpdateInfo> CREATOR = new Parcelable.Creator<SystemUpdateInfo>(){

        @Override
        public SystemUpdateInfo createFromParcel(Parcel parcel) {
            return new SystemUpdateInfo(parcel);
        }

        public SystemUpdateInfo[] newArray(int n) {
            return new SystemUpdateInfo[n];
        }
    };
    public static final int SECURITY_PATCH_STATE_FALSE = 1;
    public static final int SECURITY_PATCH_STATE_TRUE = 2;
    public static final int SECURITY_PATCH_STATE_UNKNOWN = 0;
    private final long mReceivedTime;
    private final int mSecurityPatchState;

    private SystemUpdateInfo(long l, int n) {
        this.mReceivedTime = l;
        this.mSecurityPatchState = n;
    }

    private SystemUpdateInfo(Parcel parcel) {
        this.mReceivedTime = parcel.readLong();
        this.mSecurityPatchState = parcel.readInt();
    }

    public static SystemUpdateInfo of(long l) {
        SystemUpdateInfo systemUpdateInfo = l == -1L ? null : new SystemUpdateInfo(l, 0);
        return systemUpdateInfo;
    }

    public static SystemUpdateInfo of(long l, boolean bl) {
        SystemUpdateInfo systemUpdateInfo;
        if (l == -1L) {
            systemUpdateInfo = null;
        } else {
            int n = bl ? 2 : 1;
            systemUpdateInfo = new SystemUpdateInfo(l, n);
        }
        return systemUpdateInfo;
    }

    public static SystemUpdateInfo readFromXml(XmlPullParser xmlPullParser) {
        String string2 = xmlPullParser.getAttributeValue(null, ATTR_ORIGINAL_BUILD);
        if (!Build.FINGERPRINT.equals(string2)) {
            return null;
        }
        long l = Long.parseLong(xmlPullParser.getAttributeValue(null, ATTR_RECEIVED_TIME));
        return new SystemUpdateInfo(l, Integer.parseInt(xmlPullParser.getAttributeValue(null, ATTR_SECURITY_PATCH_STATE)));
    }

    private static String securityPatchStateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    return "true";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unrecognized security patch state: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return "false";
        }
        return "unknown";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (SystemUpdateInfo)object;
            if (this.mReceivedTime != ((SystemUpdateInfo)object).mReceivedTime || this.mSecurityPatchState != ((SystemUpdateInfo)object).mSecurityPatchState) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public long getReceivedTime() {
        return this.mReceivedTime;
    }

    public int getSecurityPatchState() {
        return this.mSecurityPatchState;
    }

    public int hashCode() {
        return Objects.hash(this.mReceivedTime, this.mSecurityPatchState);
    }

    public String toString() {
        return String.format("SystemUpdateInfo (receivedTime = %d, securityPatchState = %s)", this.mReceivedTime, SystemUpdateInfo.securityPatchStateToString(this.mSecurityPatchState));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.getReceivedTime());
        parcel.writeInt(this.getSecurityPatchState());
    }

    public void writeToXml(XmlSerializer xmlSerializer, String string2) throws IOException {
        xmlSerializer.startTag(null, string2);
        xmlSerializer.attribute(null, ATTR_RECEIVED_TIME, String.valueOf(this.mReceivedTime));
        xmlSerializer.attribute(null, ATTR_SECURITY_PATCH_STATE, String.valueOf(this.mSecurityPatchState));
        xmlSerializer.attribute(null, ATTR_ORIGINAL_BUILD, Build.FINGERPRINT);
        xmlSerializer.endTag(null, string2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SecurityPatchState {
    }

}

