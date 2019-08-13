/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2;

import android.annotation.SystemApi;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.net.wifi.WifiSsid;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@SystemApi
public final class OsuProvider
implements Parcelable {
    public static final Parcelable.Creator<OsuProvider> CREATOR = new Parcelable.Creator<OsuProvider>(){

        @Override
        public OsuProvider createFromParcel(Parcel parcel) {
            WifiSsid wifiSsid = (WifiSsid)parcel.readParcelable(null);
            String string2 = parcel.readString();
            Uri uri = (Uri)parcel.readParcelable(null);
            String string3 = parcel.readString();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            parcel.readList(arrayList, null);
            Icon icon = (Icon)parcel.readParcelable(null);
            return new OsuProvider(wifiSsid, (HashMap)parcel.readBundle().getSerializable("friendlyNameMap"), string2, uri, string3, arrayList, icon);
        }

        public OsuProvider[] newArray(int n) {
            return new OsuProvider[n];
        }
    };
    public static final int METHOD_OMA_DM = 0;
    public static final int METHOD_SOAP_XML_SPP = 1;
    private final Map<String, String> mFriendlyNames;
    private final Icon mIcon;
    private final List<Integer> mMethodList;
    private final String mNetworkAccessIdentifier;
    private WifiSsid mOsuSsid;
    private final Uri mServerUri;
    private final String mServiceDescription;

    public OsuProvider(WifiSsid wifiSsid, Map<String, String> map, String string2, Uri uri, String string3, List<Integer> list, Icon icon) {
        this.mOsuSsid = wifiSsid;
        this.mFriendlyNames = map;
        this.mServiceDescription = string2;
        this.mServerUri = uri;
        this.mNetworkAccessIdentifier = string3;
        this.mMethodList = list == null ? new ArrayList<Integer>() : new ArrayList<Integer>(list);
        this.mIcon = icon;
    }

    public OsuProvider(OsuProvider osuProvider) {
        if (osuProvider == null) {
            this.mOsuSsid = null;
            this.mFriendlyNames = null;
            this.mServiceDescription = null;
            this.mServerUri = null;
            this.mNetworkAccessIdentifier = null;
            this.mMethodList = new ArrayList<Integer>();
            this.mIcon = null;
            return;
        }
        this.mOsuSsid = osuProvider.mOsuSsid;
        this.mFriendlyNames = osuProvider.mFriendlyNames;
        this.mServiceDescription = osuProvider.mServiceDescription;
        this.mServerUri = osuProvider.mServerUri;
        this.mNetworkAccessIdentifier = osuProvider.mNetworkAccessIdentifier;
        List<Integer> list = osuProvider.mMethodList;
        this.mMethodList = list == null ? new ArrayList<Integer>() : new ArrayList<Integer>(list);
        this.mIcon = osuProvider.mIcon;
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
        if (!(object instanceof OsuProvider)) {
            return false;
        }
        object = (OsuProvider)object;
        Object object2 = this.mOsuSsid;
        if ((object2 == null ? ((OsuProvider)object).mOsuSsid == null : ((WifiSsid)object2).equals(((OsuProvider)object).mOsuSsid)) && this.mFriendlyNames == null) {
            if (((OsuProvider)object).mFriendlyNames != null) {
                bl = false;
            }
        } else if (!(this.mFriendlyNames.equals(((OsuProvider)object).mFriendlyNames) && TextUtils.equals(this.mServiceDescription, ((OsuProvider)object).mServiceDescription) && ((object2 = this.mServerUri) == null ? ((OsuProvider)object).mServerUri == null : ((Uri)object2).equals(((OsuProvider)object).mServerUri)) && TextUtils.equals(this.mNetworkAccessIdentifier, ((OsuProvider)object).mNetworkAccessIdentifier) && ((object2 = this.mMethodList) == null ? ((OsuProvider)object).mMethodList == null : object2.equals(((OsuProvider)object).mMethodList)) && ((object2 = this.mIcon) == null ? ((OsuProvider)object).mIcon == null : ((Icon)object2).sameAs(((OsuProvider)object).mIcon)))) {
            bl = false;
        }
        return bl;
    }

    public String getFriendlyName() {
        Object object = this.mFriendlyNames;
        if (object != null && !object.isEmpty()) {
            object = Locale.getDefault().getLanguage();
            if ((object = this.mFriendlyNames.get(object)) != null) {
                return object;
            }
            object = this.mFriendlyNames.get("en");
            if (object != null) {
                return object;
            }
            object = this.mFriendlyNames;
            return object.get(object.keySet().stream().findFirst().get());
        }
        return null;
    }

    public Map<String, String> getFriendlyNameList() {
        return this.mFriendlyNames;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public List<Integer> getMethodList() {
        return this.mMethodList;
    }

    public String getNetworkAccessIdentifier() {
        return this.mNetworkAccessIdentifier;
    }

    public WifiSsid getOsuSsid() {
        return this.mOsuSsid;
    }

    public Uri getServerUri() {
        return this.mServerUri;
    }

    public String getServiceDescription() {
        return this.mServiceDescription;
    }

    public int hashCode() {
        return Objects.hash(this.mOsuSsid, this.mServiceDescription, this.mFriendlyNames, this.mServerUri, this.mNetworkAccessIdentifier, this.mMethodList);
    }

    public void setOsuSsid(WifiSsid wifiSsid) {
        this.mOsuSsid = wifiSsid;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OsuProvider{mOsuSsid=");
        stringBuilder.append(this.mOsuSsid);
        stringBuilder.append(" mFriendlyNames=");
        stringBuilder.append(this.mFriendlyNames);
        stringBuilder.append(" mServiceDescription=");
        stringBuilder.append(this.mServiceDescription);
        stringBuilder.append(" mServerUri=");
        stringBuilder.append(this.mServerUri);
        stringBuilder.append(" mNetworkAccessIdentifier=");
        stringBuilder.append(this.mNetworkAccessIdentifier);
        stringBuilder.append(" mMethodList=");
        stringBuilder.append(this.mMethodList);
        stringBuilder.append(" mIcon=");
        stringBuilder.append(this.mIcon);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mOsuSsid, n);
        parcel.writeString(this.mServiceDescription);
        parcel.writeParcelable(this.mServerUri, n);
        parcel.writeString(this.mNetworkAccessIdentifier);
        parcel.writeList(this.mMethodList);
        parcel.writeParcelable(this.mIcon, n);
        Bundle bundle = new Bundle();
        bundle.putSerializable("friendlyNameMap", (HashMap)this.mFriendlyNames);
        parcel.writeBundle(bundle);
    }

}

