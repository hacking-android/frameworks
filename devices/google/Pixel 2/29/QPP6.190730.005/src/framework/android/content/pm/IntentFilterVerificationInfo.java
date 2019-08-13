/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

@SystemApi
public final class IntentFilterVerificationInfo
implements Parcelable {
    private static final String ATTR_DOMAIN_NAME = "name";
    private static final String ATTR_PACKAGE_NAME = "packageName";
    private static final String ATTR_STATUS = "status";
    public static final Parcelable.Creator<IntentFilterVerificationInfo> CREATOR;
    private static final String TAG;
    private static final String TAG_DOMAIN = "domain";
    private ArraySet<String> mDomains = new ArraySet();
    private int mMainStatus;
    private String mPackageName;

    static {
        TAG = IntentFilterVerificationInfo.class.getName();
        CREATOR = new Parcelable.Creator<IntentFilterVerificationInfo>(){

            @Override
            public IntentFilterVerificationInfo createFromParcel(Parcel parcel) {
                return new IntentFilterVerificationInfo(parcel);
            }

            public IntentFilterVerificationInfo[] newArray(int n) {
                return new IntentFilterVerificationInfo[n];
            }
        };
    }

    public IntentFilterVerificationInfo() {
        this.mPackageName = null;
        this.mMainStatus = 0;
    }

    public IntentFilterVerificationInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public IntentFilterVerificationInfo(String string2, ArraySet<String> arraySet) {
        this.mPackageName = string2;
        this.mDomains = arraySet;
        this.mMainStatus = 0;
    }

    public IntentFilterVerificationInfo(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        this.readFromXml(xmlPullParser);
    }

    public static String getStatusStringFromValue(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = (int)(l >> 32);
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        stringBuilder.append("undefined");
                    } else {
                        stringBuilder.append("always-ask");
                    }
                } else {
                    stringBuilder.append("never");
                }
            } else {
                stringBuilder.append("always : ");
                stringBuilder.append(Long.toHexString(-1L & l));
            }
        } else {
            stringBuilder.append("ask");
        }
        return stringBuilder.toString();
    }

    private void readFromParcel(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mMainStatus = parcel.readInt();
        ArrayList<String> arrayList = new ArrayList<String>();
        parcel.readStringList(arrayList);
        this.mDomains.addAll(arrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Set<String> getDomains() {
        return this.mDomains;
    }

    public String getDomainsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : this.mDomains) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    int getIntFromXml(XmlPullParser object, String string2, int n) {
        CharSequence charSequence = object.getAttributeValue(null, string2);
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Missing element under ");
            ((StringBuilder)charSequence).append(TAG);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" at ");
            ((StringBuilder)charSequence).append(object.getPositionDescription());
            object = ((StringBuilder)charSequence).toString();
            Log.w(TAG, (String)object);
            return n;
        }
        return Integer.parseInt((String)charSequence);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getStatus() {
        return this.mMainStatus;
    }

    public String getStatusString() {
        return IntentFilterVerificationInfo.getStatusStringFromValue((long)this.mMainStatus << 32);
    }

    String getStringFromXml(XmlPullParser object, String string2, String string3) {
        CharSequence charSequence = object.getAttributeValue(null, string2);
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Missing element under ");
            ((StringBuilder)charSequence).append(TAG);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" at ");
            ((StringBuilder)charSequence).append(object.getPositionDescription());
            object = ((StringBuilder)charSequence).toString();
            Log.w(TAG, (String)object);
            return string3;
        }
        return charSequence;
    }

    public void readFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String string2;
        int n;
        StringBuilder stringBuilder;
        this.mPackageName = this.getStringFromXml(xmlPullParser, ATTR_PACKAGE_NAME, null);
        if (this.mPackageName == null) {
            Log.e(TAG, "Package name cannot be null!");
        }
        if ((n = this.getIntFromXml(xmlPullParser, ATTR_STATUS, -1)) == -1) {
            string2 = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown status value: ");
            stringBuilder.append(n);
            Log.e(string2, stringBuilder.toString());
        }
        this.mMainStatus = n;
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            if (n == 3 || n == 4) continue;
            string2 = xmlPullParser.getName();
            if (string2.equals(TAG_DOMAIN)) {
                string2 = this.getStringFromXml(xmlPullParser, ATTR_DOMAIN_NAME, null);
                if (!TextUtils.isEmpty(string2)) {
                    this.mDomains.add(string2);
                }
            } else {
                String string3 = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown tag parsing IntentFilter: ");
                stringBuilder.append(string2);
                Log.w(string3, stringBuilder.toString());
            }
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
    }

    public void setDomains(ArraySet<String> arraySet) {
        this.mDomains = arraySet;
    }

    public void setStatus(int n) {
        if (n >= 0 && n <= 3) {
            this.mMainStatus = n;
        } else {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to set a non supported status: ");
            stringBuilder.append(n);
            Log.w(string2, stringBuilder.toString());
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mMainStatus);
        parcel.writeStringList(new ArrayList<String>(this.mDomains));
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
        xmlSerializer.attribute(null, ATTR_STATUS, String.valueOf(this.mMainStatus));
        for (String string2 : this.mDomains) {
            xmlSerializer.startTag(null, TAG_DOMAIN);
            xmlSerializer.attribute(null, ATTR_DOMAIN_NAME, string2);
            xmlSerializer.endTag(null, TAG_DOMAIN);
        }
    }

}

