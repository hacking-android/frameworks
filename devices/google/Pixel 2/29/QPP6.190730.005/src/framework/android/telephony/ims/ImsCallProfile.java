/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.VideoProfile;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsStreamMediaProfile;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class ImsCallProfile
implements Parcelable {
    public static final int CALL_RESTRICT_CAUSE_DISABLED = 2;
    public static final int CALL_RESTRICT_CAUSE_HD = 3;
    public static final int CALL_RESTRICT_CAUSE_NONE = 0;
    public static final int CALL_RESTRICT_CAUSE_RAT = 1;
    public static final int CALL_TYPE_VIDEO_N_VOICE = 3;
    public static final int CALL_TYPE_VOICE = 2;
    public static final int CALL_TYPE_VOICE_N_VIDEO = 1;
    public static final int CALL_TYPE_VS = 8;
    public static final int CALL_TYPE_VS_RX = 10;
    public static final int CALL_TYPE_VS_TX = 9;
    public static final int CALL_TYPE_VT = 4;
    public static final int CALL_TYPE_VT_NODIR = 7;
    public static final int CALL_TYPE_VT_RX = 6;
    public static final int CALL_TYPE_VT_TX = 5;
    public static final Parcelable.Creator<ImsCallProfile> CREATOR = new Parcelable.Creator<ImsCallProfile>(){

        @Override
        public ImsCallProfile createFromParcel(Parcel parcel) {
            return new ImsCallProfile(parcel);
        }

        public ImsCallProfile[] newArray(int n) {
            return new ImsCallProfile[n];
        }
    };
    public static final int DIALSTRING_NORMAL = 0;
    public static final int DIALSTRING_SS_CONF = 1;
    public static final int DIALSTRING_USSD = 2;
    public static final String EXTRA_ADDITIONAL_CALL_INFO = "AdditionalCallInfo";
    public static final String EXTRA_ADDITIONAL_SIP_INVITE_FIELDS = "android.telephony.ims.extra.ADDITIONAL_SIP_INVITE_FIELDS";
    public static final String EXTRA_CALL_MODE_CHANGEABLE = "call_mode_changeable";
    public static final String EXTRA_CALL_RAT_TYPE = "CallRadioTech";
    public static final String EXTRA_CALL_RAT_TYPE_ALT = "callRadioTech";
    public static final String EXTRA_CHILD_NUMBER = "ChildNum";
    public static final String EXTRA_CNA = "cna";
    public static final String EXTRA_CNAP = "cnap";
    public static final String EXTRA_CODEC = "Codec";
    public static final String EXTRA_CONFERENCE = "conference";
    public static final String EXTRA_CONFERENCE_AVAIL = "conference_avail";
    public static final String EXTRA_DIALSTRING = "dialstring";
    public static final String EXTRA_DISPLAY_TEXT = "DisplayText";
    public static final String EXTRA_EMERGENCY_CALL = "e_call";
    public static final String EXTRA_IS_CALL_PULL = "CallPull";
    public static final String EXTRA_OEM_EXTRAS = "OemCallExtras";
    public static final String EXTRA_OI = "oi";
    public static final String EXTRA_OIR = "oir";
    public static final String EXTRA_REMOTE_URI = "remote_uri";
    public static final String EXTRA_USSD = "ussd";
    public static final String EXTRA_VMS = "vms";
    public static final int OIR_DEFAULT = 0;
    public static final int OIR_PRESENTATION_NOT_RESTRICTED = 2;
    public static final int OIR_PRESENTATION_PAYPHONE = 4;
    public static final int OIR_PRESENTATION_RESTRICTED = 1;
    public static final int OIR_PRESENTATION_UNKNOWN = 3;
    public static final int SERVICE_TYPE_EMERGENCY = 2;
    public static final int SERVICE_TYPE_NONE = 0;
    public static final int SERVICE_TYPE_NORMAL = 1;
    private static final String TAG = "ImsCallProfile";
    @UnsupportedAppUsage
    public Bundle mCallExtras;
    @UnsupportedAppUsage
    public int mCallType;
    private int mEmergencyCallRouting = 0;
    private boolean mEmergencyCallTesting = false;
    private int mEmergencyServiceCategories = 0;
    private List<String> mEmergencyUrns = new ArrayList<String>();
    private boolean mHasKnownUserIntentEmergency = false;
    @UnsupportedAppUsage
    public ImsStreamMediaProfile mMediaProfile;
    @UnsupportedAppUsage
    public int mRestrictCause = 0;
    public int mServiceType;

    public ImsCallProfile() {
        this.mServiceType = 1;
        this.mCallType = 1;
        this.mCallExtras = new Bundle();
        this.mMediaProfile = new ImsStreamMediaProfile();
    }

    public ImsCallProfile(int n, int n2) {
        this.mServiceType = n;
        this.mCallType = n2;
        this.mCallExtras = new Bundle();
        this.mMediaProfile = new ImsStreamMediaProfile();
    }

    public ImsCallProfile(int n, int n2, Bundle bundle, ImsStreamMediaProfile imsStreamMediaProfile) {
        this.mServiceType = n;
        this.mCallType = n2;
        this.mCallExtras = bundle;
        this.mMediaProfile = imsStreamMediaProfile;
    }

    public ImsCallProfile(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static int OIRToPresentation(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return 3;
                    }
                    return 4;
                }
                return 3;
            }
            return 1;
        }
        return 2;
    }

    public static int getCallTypeFromVideoState(int n) {
        boolean bl = ImsCallProfile.isVideoStateSet(n, 1);
        boolean bl2 = ImsCallProfile.isVideoStateSet(n, 2);
        if (ImsCallProfile.isVideoStateSet(n, 4)) {
            return 7;
        }
        if (bl && !bl2) {
            return 5;
        }
        if (!bl && bl2) {
            return 6;
        }
        if (bl && bl2) {
            return 4;
        }
        return 2;
    }

    public static int getVideoStateFromCallType(int n) {
        n = n != 2 ? (n != 4 ? (n != 5 ? (n != 6 ? 0 : 2) : 1) : 3) : 0;
        return n;
    }

    public static int getVideoStateFromImsCallProfile(ImsCallProfile imsCallProfile) {
        int n = ImsCallProfile.getVideoStateFromCallType(imsCallProfile.mCallType);
        n = imsCallProfile.isVideoPaused() && !VideoProfile.isAudioOnly(n) ? (n |= 4) : (n &= -5);
        return n;
    }

    private static boolean isVideoStateSet(int n, int n2) {
        boolean bl = (n & n2) == n2;
        return bl;
    }

    private Bundle maybeCleanseExtras(Bundle object) {
        Bundle bundle;
        int n;
        if (object == null) {
            return null;
        }
        int n2 = ((BaseBundle)object).size();
        if (n2 != (n = (bundle = ((Bundle)object).filterValues()).size())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("maybeCleanseExtras: ");
            ((StringBuilder)object).append(n2 - n);
            ((StringBuilder)object).append(" extra values were removed - only primitive types and system parcelables are permitted.");
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        return bundle;
    }

    @UnsupportedAppUsage
    public static int presentationToOIR(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return 0;
                    }
                    return 4;
                }
                return 3;
            }
            return 1;
        }
        return 2;
    }

    public static int presentationToOir(int n) {
        return ImsCallProfile.presentationToOIR(n);
    }

    private void readFromParcel(Parcel parcel) {
        this.mServiceType = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mCallExtras = parcel.readBundle();
        this.mMediaProfile = (ImsStreamMediaProfile)parcel.readParcelable(ImsStreamMediaProfile.class.getClassLoader());
        this.mEmergencyServiceCategories = parcel.readInt();
        this.mEmergencyUrns = parcel.createStringArrayList();
        this.mEmergencyCallRouting = parcel.readInt();
        this.mEmergencyCallTesting = parcel.readBoolean();
        this.mHasKnownUserIntentEmergency = parcel.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCallExtra(String string2) {
        return this.getCallExtra(string2, "");
    }

    public String getCallExtra(String string2, String string3) {
        Bundle bundle = this.mCallExtras;
        if (bundle == null) {
            return string3;
        }
        return bundle.getString(string2, string3);
    }

    public boolean getCallExtraBoolean(String string2) {
        return this.getCallExtraBoolean(string2, false);
    }

    public boolean getCallExtraBoolean(String string2, boolean bl) {
        Bundle bundle = this.mCallExtras;
        if (bundle == null) {
            return bl;
        }
        return bundle.getBoolean(string2, bl);
    }

    public int getCallExtraInt(String string2) {
        return this.getCallExtraInt(string2, -1);
    }

    public int getCallExtraInt(String string2, int n) {
        Bundle bundle = this.mCallExtras;
        if (bundle == null) {
            return n;
        }
        return bundle.getInt(string2, n);
    }

    public Bundle getCallExtras() {
        return this.mCallExtras;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    public int getEmergencyServiceCategories() {
        return this.mEmergencyServiceCategories;
    }

    public List<String> getEmergencyUrns() {
        return this.mEmergencyUrns;
    }

    public ImsStreamMediaProfile getMediaProfile() {
        return this.mMediaProfile;
    }

    public int getRestrictCause() {
        return this.mRestrictCause;
    }

    public int getServiceType() {
        return this.mServiceType;
    }

    public boolean hasKnownUserIntentEmergency() {
        return this.mHasKnownUserIntentEmergency;
    }

    public boolean isEmergencyCallTesting() {
        return this.mEmergencyCallTesting;
    }

    public boolean isVideoCall() {
        return VideoProfile.isVideo(ImsCallProfile.getVideoStateFromCallType(this.mCallType));
    }

    public boolean isVideoPaused() {
        boolean bl = this.mMediaProfile.mVideoDirection == 0;
        return bl;
    }

    public void setCallExtra(String string2, String string3) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putString(string2, string3);
        }
    }

    public void setCallExtraBoolean(String string2, boolean bl) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putBoolean(string2, bl);
        }
    }

    public void setCallExtraInt(String string2, int n) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putInt(string2, n);
        }
    }

    public void setCallRestrictCause(int n) {
        this.mRestrictCause = n;
    }

    public void setEmergencyCallInfo(EmergencyNumber emergencyNumber, boolean bl) {
        this.setEmergencyServiceCategories(emergencyNumber.getEmergencyServiceCategoryBitmaskInternalDial());
        this.setEmergencyUrns(emergencyNumber.getEmergencyUrns());
        this.setEmergencyCallRouting(emergencyNumber.getEmergencyCallRouting());
        boolean bl2 = emergencyNumber.getEmergencyNumberSourceBitmask() == 32;
        this.setEmergencyCallTesting(bl2);
        this.setHasKnownUserIntentEmergency(bl);
    }

    @VisibleForTesting
    public void setEmergencyCallRouting(int n) {
        this.mEmergencyCallRouting = n;
    }

    @VisibleForTesting
    public void setEmergencyCallTesting(boolean bl) {
        this.mEmergencyCallTesting = bl;
    }

    @VisibleForTesting
    public void setEmergencyServiceCategories(int n) {
        this.mEmergencyServiceCategories = n;
    }

    @VisibleForTesting
    public void setEmergencyUrns(List<String> list) {
        this.mEmergencyUrns = list;
    }

    @VisibleForTesting
    public void setHasKnownUserIntentEmergency(boolean bl) {
        this.mHasKnownUserIntentEmergency = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ serviceType=");
        stringBuilder.append(this.mServiceType);
        stringBuilder.append(", callType=");
        stringBuilder.append(this.mCallType);
        stringBuilder.append(", restrictCause=");
        stringBuilder.append(this.mRestrictCause);
        stringBuilder.append(", mediaProfile=");
        stringBuilder.append(this.mMediaProfile.toString());
        stringBuilder.append(", emergencyServiceCategories=");
        stringBuilder.append(this.mEmergencyServiceCategories);
        stringBuilder.append(", emergencyUrns=");
        stringBuilder.append(this.mEmergencyUrns);
        stringBuilder.append(", emergencyCallRouting=");
        stringBuilder.append(this.mEmergencyCallRouting);
        stringBuilder.append(", emergencyCallTesting=");
        stringBuilder.append(this.mEmergencyCallTesting);
        stringBuilder.append(", hasKnownUserIntentEmergency=");
        stringBuilder.append(this.mHasKnownUserIntentEmergency);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public void updateCallExtras(ImsCallProfile imsCallProfile) {
        this.mCallExtras.clear();
        this.mCallExtras = (Bundle)imsCallProfile.mCallExtras.clone();
    }

    public void updateCallType(ImsCallProfile imsCallProfile) {
        this.mCallType = imsCallProfile.mCallType;
    }

    public void updateMediaProfile(ImsCallProfile imsCallProfile) {
        this.mMediaProfile = imsCallProfile.mMediaProfile;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Bundle bundle = this.maybeCleanseExtras(this.mCallExtras);
        parcel.writeInt(this.mServiceType);
        parcel.writeInt(this.mCallType);
        parcel.writeBundle(bundle);
        parcel.writeParcelable(this.mMediaProfile, 0);
        parcel.writeInt(this.mEmergencyServiceCategories);
        parcel.writeStringList(this.mEmergencyUrns);
        parcel.writeInt(this.mEmergencyCallRouting);
        parcel.writeBoolean(this.mEmergencyCallTesting);
        parcel.writeBoolean(this.mHasKnownUserIntentEmergency);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CallRestrictCause {
    }

}

