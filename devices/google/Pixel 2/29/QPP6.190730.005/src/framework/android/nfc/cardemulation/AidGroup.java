/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.nfc.cardemulation;

import android.annotation.UnsupportedAppUsage;
import android.nfc.cardemulation.CardEmulation;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class AidGroup
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<AidGroup> CREATOR = new Parcelable.Creator<AidGroup>(){

        @Override
        public AidGroup createFromParcel(Parcel parcel) {
            String string2 = parcel.readString();
            int n = parcel.readInt();
            ArrayList<String> arrayList = new ArrayList<String>();
            if (n > 0) {
                parcel.readStringList(arrayList);
            }
            return new AidGroup(arrayList, string2);
        }

        public AidGroup[] newArray(int n) {
            return new AidGroup[n];
        }
    };
    public static final int MAX_NUM_AIDS = 256;
    static final String TAG = "AidGroup";
    @UnsupportedAppUsage
    final List<String> aids;
    @UnsupportedAppUsage
    final String category;
    @UnsupportedAppUsage
    final String description;

    @UnsupportedAppUsage
    AidGroup(String string2, String string3) {
        this.aids = new ArrayList<String>();
        this.category = string2;
        this.description = string3;
    }

    public AidGroup(List<String> iterator, String string2) {
        if (iterator != null && iterator.size() != 0) {
            if (iterator.size() <= 256) {
                Iterator<String> iterator2 = iterator.iterator();
                while (iterator2.hasNext()) {
                    String string3 = iterator2.next();
                    if (CardEmulation.isValidAid(string3)) continue;
                    iterator = new StringBuilder();
                    ((StringBuilder)((Object)iterator)).append("AID ");
                    ((StringBuilder)((Object)iterator)).append(string3);
                    ((StringBuilder)((Object)iterator)).append(" is not a valid AID.");
                    throw new IllegalArgumentException(((StringBuilder)((Object)iterator)).toString());
                }
                this.category = AidGroup.isValidCategory(string2) ? string2 : "other";
                this.aids = new ArrayList<String>(iterator.size());
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    string2 = (String)iterator.next();
                    this.aids.add(string2.toUpperCase());
                }
                this.description = null;
                return;
            }
            throw new IllegalArgumentException("Too many AIDs in AID group.");
        }
        throw new IllegalArgumentException("No AIDS in AID group.");
    }

    @UnsupportedAppUsage
    public static AidGroup createFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        Object object;
        String string2 = null;
        ArrayList<String> arrayList = new ArrayList<String>();
        String string3 = null;
        boolean bl = false;
        int n = xmlPullParser.getEventType();
        int n2 = xmlPullParser.getDepth();
        do {
            boolean bl2;
            object = string3;
            if (n == 1) break;
            object = string3;
            if (xmlPullParser.getDepth() < n2) break;
            String string4 = xmlPullParser.getName();
            if (n == 2) {
                if (string4.equals("aid")) {
                    if (bl) {
                        object = xmlPullParser.getAttributeValue(null, "value");
                        if (object != null) {
                            arrayList.add(((String)object).toUpperCase());
                        }
                        object = string2;
                        bl2 = bl;
                    } else {
                        Log.d(TAG, "Ignoring <aid> tag while not in group");
                        object = string2;
                        bl2 = bl;
                    }
                } else if (string4.equals("aid-group")) {
                    object = xmlPullParser.getAttributeValue(null, "category");
                    if (object == null) {
                        Log.e(TAG, "<aid-group> tag without valid category");
                        return null;
                    }
                    bl2 = true;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Ignoring unexpected tag: ");
                    ((StringBuilder)object).append(string4);
                    Log.d(TAG, ((StringBuilder)object).toString());
                    object = string2;
                    bl2 = bl;
                }
            } else {
                object = string2;
                bl2 = bl;
                if (n == 3) {
                    object = string2;
                    bl2 = bl;
                    if (string4.equals("aid-group")) {
                        object = string2;
                        bl2 = bl;
                        if (bl) {
                            object = string2;
                            bl2 = bl;
                            if (arrayList.size() > 0) {
                                object = new AidGroup(arrayList, string2);
                                break;
                            }
                        }
                    }
                }
            }
            n = xmlPullParser.next();
            string2 = object;
            bl = bl2;
        } while (true);
        return object;
    }

    static boolean isValidCategory(String string2) {
        boolean bl = "payment".equals(string2) || "other".equals(string2);
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public List<String> getAids() {
        return this.aids;
    }

    @UnsupportedAppUsage
    public String getCategory() {
        return this.category;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Category: ");
        stringBuilder.append(this.category);
        stringBuilder.append(", AIDs:");
        stringBuilder = new StringBuilder(stringBuilder.toString());
        Iterator<String> iterator = this.aids.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public void writeAsXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, "aid-group");
        xmlSerializer.attribute(null, "category", this.category);
        for (String string2 : this.aids) {
            xmlSerializer.startTag(null, "aid");
            xmlSerializer.attribute(null, "value", string2);
            xmlSerializer.endTag(null, "aid");
        }
        xmlSerializer.endTag(null, "aid-group");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.category);
        parcel.writeInt(this.aids.size());
        if (this.aids.size() > 0) {
            parcel.writeStringList(this.aids);
        }
    }

}

