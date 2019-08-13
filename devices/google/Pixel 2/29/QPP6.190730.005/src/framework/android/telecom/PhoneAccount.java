/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Log;
import android.telecom.PhoneAccountHandle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class PhoneAccount
implements Parcelable {
    public static final int CAPABILITY_CALL_PROVIDER = 2;
    public static final int CAPABILITY_CALL_SUBJECT = 64;
    public static final int CAPABILITY_CONNECTION_MANAGER = 1;
    public static final int CAPABILITY_EMERGENCY_CALLS_ONLY = 128;
    public static final int CAPABILITY_EMERGENCY_PREFERRED = 8192;
    public static final int CAPABILITY_EMERGENCY_VIDEO_CALLING = 512;
    @SystemApi
    public static final int CAPABILITY_MULTI_USER = 32;
    public static final int CAPABILITY_PLACE_EMERGENCY_CALLS = 16;
    public static final int CAPABILITY_RTT = 4096;
    public static final int CAPABILITY_SELF_MANAGED = 2048;
    public static final int CAPABILITY_SIM_SUBSCRIPTION = 4;
    public static final int CAPABILITY_SUPPORTS_VIDEO_CALLING = 1024;
    public static final int CAPABILITY_VIDEO_CALLING = 8;
    public static final int CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE = 256;
    public static final Parcelable.Creator<PhoneAccount> CREATOR = new Parcelable.Creator<PhoneAccount>(){

        @Override
        public PhoneAccount createFromParcel(Parcel parcel) {
            return new PhoneAccount(parcel);
        }

        public PhoneAccount[] newArray(int n) {
            return new PhoneAccount[n];
        }
    };
    public static final String EXTRA_ALWAYS_USE_VOIP_AUDIO_MODE = "android.telecom.extra.ALWAYS_USE_VOIP_AUDIO_MODE";
    public static final String EXTRA_CALL_SUBJECT_CHARACTER_ENCODING = "android.telecom.extra.CALL_SUBJECT_CHARACTER_ENCODING";
    public static final String EXTRA_CALL_SUBJECT_MAX_LENGTH = "android.telecom.extra.CALL_SUBJECT_MAX_LENGTH";
    public static final String EXTRA_LOG_SELF_MANAGED_CALLS = "android.telecom.extra.LOG_SELF_MANAGED_CALLS";
    public static final String EXTRA_PLAY_CALL_RECORDING_TONE = "android.telecom.extra.PLAY_CALL_RECORDING_TONE";
    public static final String EXTRA_SKIP_CALL_FILTERING = "android.telecom.extra.SKIP_CALL_FILTERING";
    public static final String EXTRA_SORT_ORDER = "android.telecom.extra.SORT_ORDER";
    public static final String EXTRA_SUPPORTS_HANDOVER_FROM = "android.telecom.extra.SUPPORTS_HANDOVER_FROM";
    public static final String EXTRA_SUPPORTS_HANDOVER_TO = "android.telecom.extra.SUPPORTS_HANDOVER_TO";
    public static final String EXTRA_SUPPORTS_VIDEO_CALLING_FALLBACK = "android.telecom.extra.SUPPORTS_VIDEO_CALLING_FALLBACK";
    public static final int NO_HIGHLIGHT_COLOR = 0;
    public static final int NO_ICON_TINT = 0;
    public static final int NO_RESOURCE_ID = -1;
    public static final String SCHEME_SIP = "sip";
    public static final String SCHEME_TEL = "tel";
    public static final String SCHEME_VOICEMAIL = "voicemail";
    private final PhoneAccountHandle mAccountHandle;
    private final Uri mAddress;
    private final int mCapabilities;
    private final Bundle mExtras;
    private String mGroupId;
    private final int mHighlightColor;
    private final Icon mIcon;
    private boolean mIsEnabled;
    private final CharSequence mLabel;
    private final CharSequence mShortDescription;
    private final Uri mSubscriptionAddress;
    private final int mSupportedAudioRoutes;
    private final List<String> mSupportedUriSchemes;

    private PhoneAccount(Parcel parcel) {
        this.mAccountHandle = parcel.readInt() > 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel) : null;
        this.mAddress = parcel.readInt() > 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
        this.mSubscriptionAddress = parcel.readInt() > 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
        this.mCapabilities = parcel.readInt();
        this.mHighlightColor = parcel.readInt();
        this.mLabel = parcel.readCharSequence();
        this.mShortDescription = parcel.readCharSequence();
        this.mSupportedUriSchemes = Collections.unmodifiableList(parcel.createStringArrayList());
        this.mIcon = parcel.readInt() > 0 ? Icon.CREATOR.createFromParcel(parcel) : null;
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        this.mIsEnabled = bl;
        this.mExtras = parcel.readBundle();
        this.mGroupId = parcel.readString();
        this.mSupportedAudioRoutes = parcel.readInt();
    }

    private PhoneAccount(PhoneAccountHandle phoneAccountHandle, Uri uri, Uri uri2, int n, Icon icon, int n2, CharSequence charSequence, CharSequence charSequence2, List<String> list, Bundle bundle, int n3, boolean bl, String string2) {
        this.mAccountHandle = phoneAccountHandle;
        this.mAddress = uri;
        this.mSubscriptionAddress = uri2;
        this.mCapabilities = n;
        this.mIcon = icon;
        this.mHighlightColor = n2;
        this.mLabel = charSequence;
        this.mShortDescription = charSequence2;
        this.mSupportedUriSchemes = Collections.unmodifiableList(list);
        this.mExtras = bundle;
        this.mSupportedAudioRoutes = n3;
        this.mIsEnabled = bl;
        this.mGroupId = string2;
    }

    private static boolean areBundlesEqual(Bundle bundle, Bundle bundle2) {
        boolean bl = true;
        if (bundle != null && bundle2 != null) {
            if (bundle.size() != bundle2.size()) {
                return false;
            }
            for (String string2 : bundle.keySet()) {
                if (string2 == null || Objects.equals(bundle.get(string2), bundle2.get(string2))) continue;
                return false;
            }
            return true;
        }
        if (bundle != bundle2) {
            bl = false;
        }
        return bl;
    }

    private String audioRoutesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.hasAudioRoutes(2)) {
            stringBuilder.append("B");
        }
        if (this.hasAudioRoutes(1)) {
            stringBuilder.append("E");
        }
        if (this.hasAudioRoutes(8)) {
            stringBuilder.append("S");
        }
        if (this.hasAudioRoutes(4)) {
            stringBuilder.append("W");
        }
        return stringBuilder.toString();
    }

    public static Builder builder(PhoneAccountHandle phoneAccountHandle, CharSequence charSequence) {
        return new Builder(phoneAccountHandle, charSequence);
    }

    public String capabilitiesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.hasCapabilities(2048)) {
            stringBuilder.append("SelfManaged ");
        }
        if (this.hasCapabilities(1024)) {
            stringBuilder.append("SuppVideo ");
        }
        if (this.hasCapabilities(8)) {
            stringBuilder.append("Video ");
        }
        if (this.hasCapabilities(256)) {
            stringBuilder.append("Presence ");
        }
        if (this.hasCapabilities(2)) {
            stringBuilder.append("CallProvider ");
        }
        if (this.hasCapabilities(64)) {
            stringBuilder.append("CallSubject ");
        }
        if (this.hasCapabilities(1)) {
            stringBuilder.append("ConnectionMgr ");
        }
        if (this.hasCapabilities(128)) {
            stringBuilder.append("EmergOnly ");
        }
        if (this.hasCapabilities(32)) {
            stringBuilder.append("MultiUser ");
        }
        if (this.hasCapabilities(16)) {
            stringBuilder.append("PlaceEmerg ");
        }
        if (this.hasCapabilities(8192)) {
            stringBuilder.append("EmerPrefer ");
        }
        if (this.hasCapabilities(512)) {
            stringBuilder.append("EmergVideo ");
        }
        if (this.hasCapabilities(4)) {
            stringBuilder.append("SimSub ");
        }
        if (this.hasCapabilities(4096)) {
            stringBuilder.append("Rtt");
        }
        return stringBuilder.toString();
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
            object = (PhoneAccount)object;
            if (!(this.mCapabilities == ((PhoneAccount)object).mCapabilities && this.mHighlightColor == ((PhoneAccount)object).mHighlightColor && this.mSupportedAudioRoutes == ((PhoneAccount)object).mSupportedAudioRoutes && this.mIsEnabled == ((PhoneAccount)object).mIsEnabled && Objects.equals(this.mAccountHandle, ((PhoneAccount)object).mAccountHandle) && Objects.equals(this.mAddress, ((PhoneAccount)object).mAddress) && Objects.equals(this.mSubscriptionAddress, ((PhoneAccount)object).mSubscriptionAddress) && Objects.equals(this.mLabel, ((PhoneAccount)object).mLabel) && Objects.equals(this.mShortDescription, ((PhoneAccount)object).mShortDescription) && Objects.equals(this.mSupportedUriSchemes, ((PhoneAccount)object).mSupportedUriSchemes) && PhoneAccount.areBundlesEqual(this.mExtras, ((PhoneAccount)object).mExtras) && Objects.equals(this.mGroupId, ((PhoneAccount)object).mGroupId))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getGroupId() {
        return this.mGroupId;
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public CharSequence getShortDescription() {
        return this.mShortDescription;
    }

    public Uri getSubscriptionAddress() {
        return this.mSubscriptionAddress;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public List<String> getSupportedUriSchemes() {
        return this.mSupportedUriSchemes;
    }

    public boolean hasAudioRoutes(int n) {
        boolean bl = (this.mSupportedAudioRoutes & n) == n;
        return bl;
    }

    public boolean hasCapabilities(int n) {
        boolean bl = (this.mCapabilities & n) == n;
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.mAccountHandle, this.mAddress, this.mSubscriptionAddress, this.mCapabilities, this.mHighlightColor, this.mLabel, this.mShortDescription, this.mSupportedUriSchemes, this.mSupportedAudioRoutes, this.mExtras, this.mIsEnabled, this.mGroupId);
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isSelfManaged() {
        boolean bl = (this.mCapabilities & 2048) == 2048;
        return bl;
    }

    public void setIsEnabled(boolean bl) {
        this.mIsEnabled = bl;
    }

    public boolean supportsUriScheme(String string2) {
        Object object = this.mSupportedUriSchemes;
        if (object != null && string2 != null) {
            Iterator<String> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (object == null || !((String)object).equals(string2)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String toString() {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[[");
        char c2 = this.mIsEnabled ? (c = 'X') : (c = ' ');
        stringBuilder.append(c2);
        stringBuilder.append("] PhoneAccount: ");
        stringBuilder.append(this.mAccountHandle);
        stringBuilder.append(" Capabilities: ");
        stringBuilder.append(this.capabilitiesToString());
        stringBuilder.append(" Audio Routes: ");
        stringBuilder.append(this.audioRoutesToString());
        stringBuilder = stringBuilder.append(" Schemes: ");
        Iterator<String> iterator = this.mSupportedUriSchemes.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            stringBuilder.append(" ");
        }
        stringBuilder.append(" Extras: ");
        stringBuilder.append(this.mExtras);
        stringBuilder.append(" GroupId: ");
        stringBuilder.append(Log.pii(this.mGroupId));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mAccountHandle == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mAccountHandle.writeToParcel(parcel, n);
        }
        if (this.mAddress == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mAddress.writeToParcel(parcel, n);
        }
        if (this.mSubscriptionAddress == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mSubscriptionAddress.writeToParcel(parcel, n);
        }
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mHighlightColor);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeCharSequence(this.mShortDescription);
        parcel.writeStringList(this.mSupportedUriSchemes);
        if (this.mIcon == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, n);
        }
        parcel.writeByte((byte)(this.mIsEnabled ? 1 : 0));
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mGroupId);
        parcel.writeInt(this.mSupportedAudioRoutes);
    }

    public static class Builder {
        private PhoneAccountHandle mAccountHandle;
        private Uri mAddress;
        private int mCapabilities;
        private Bundle mExtras;
        private String mGroupId = "";
        private int mHighlightColor = 0;
        private Icon mIcon;
        private boolean mIsEnabled = false;
        private CharSequence mLabel;
        private CharSequence mShortDescription;
        private Uri mSubscriptionAddress;
        private int mSupportedAudioRoutes = 15;
        private List<String> mSupportedUriSchemes = new ArrayList<String>();

        public Builder(PhoneAccount phoneAccount) {
            this.mAccountHandle = phoneAccount.getAccountHandle();
            this.mAddress = phoneAccount.getAddress();
            this.mSubscriptionAddress = phoneAccount.getSubscriptionAddress();
            this.mCapabilities = phoneAccount.getCapabilities();
            this.mHighlightColor = phoneAccount.getHighlightColor();
            this.mLabel = phoneAccount.getLabel();
            this.mShortDescription = phoneAccount.getShortDescription();
            this.mSupportedUriSchemes.addAll(phoneAccount.getSupportedUriSchemes());
            this.mIcon = phoneAccount.getIcon();
            this.mIsEnabled = phoneAccount.isEnabled();
            this.mExtras = phoneAccount.getExtras();
            this.mGroupId = phoneAccount.getGroupId();
            this.mSupportedAudioRoutes = phoneAccount.getSupportedAudioRoutes();
        }

        public Builder(PhoneAccountHandle phoneAccountHandle, CharSequence charSequence) {
            this.mAccountHandle = phoneAccountHandle;
            this.mLabel = charSequence;
        }

        public Builder addSupportedUriScheme(String string2) {
            if (!TextUtils.isEmpty(string2) && !this.mSupportedUriSchemes.contains(string2)) {
                this.mSupportedUriSchemes.add(string2);
            }
            return this;
        }

        public PhoneAccount build() {
            if (this.mSupportedUriSchemes.isEmpty()) {
                this.addSupportedUriScheme(PhoneAccount.SCHEME_TEL);
            }
            return new PhoneAccount(this.mAccountHandle, this.mAddress, this.mSubscriptionAddress, this.mCapabilities, this.mIcon, this.mHighlightColor, this.mLabel, this.mShortDescription, this.mSupportedUriSchemes, this.mExtras, this.mSupportedAudioRoutes, this.mIsEnabled, this.mGroupId);
        }

        public Builder setAddress(Uri uri) {
            this.mAddress = uri;
            return this;
        }

        public Builder setCapabilities(int n) {
            this.mCapabilities = n;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setGroupId(String string2) {
            this.mGroupId = string2 != null ? string2 : "";
            return this;
        }

        public Builder setHighlightColor(int n) {
            this.mHighlightColor = n;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setIsEnabled(boolean bl) {
            this.mIsEnabled = bl;
            return this;
        }

        public Builder setLabel(CharSequence charSequence) {
            this.mLabel = charSequence;
            return this;
        }

        public Builder setShortDescription(CharSequence charSequence) {
            this.mShortDescription = charSequence;
            return this;
        }

        public Builder setSubscriptionAddress(Uri uri) {
            this.mSubscriptionAddress = uri;
            return this;
        }

        public Builder setSupportedAudioRoutes(int n) {
            this.mSupportedAudioRoutes = n;
            return this;
        }

        public Builder setSupportedUriSchemes(List<String> object) {
            this.mSupportedUriSchemes.clear();
            if (object != null && !object.isEmpty()) {
                object = object.iterator();
                while (object.hasNext()) {
                    this.addSupportedUriScheme((String)object.next());
                }
            }
            return this;
        }
    }

}

