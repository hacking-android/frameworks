/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClipDescription
implements Parcelable {
    public static final Parcelable.Creator<ClipDescription> CREATOR = new Parcelable.Creator<ClipDescription>(){

        @Override
        public ClipDescription createFromParcel(Parcel parcel) {
            return new ClipDescription(parcel);
        }

        public ClipDescription[] newArray(int n) {
            return new ClipDescription[n];
        }
    };
    public static final String EXTRA_TARGET_COMPONENT_NAME = "android.content.extra.TARGET_COMPONENT_NAME";
    public static final String EXTRA_USER_SERIAL_NUMBER = "android.content.extra.USER_SERIAL_NUMBER";
    public static final String MIMETYPE_TEXT_HTML = "text/html";
    public static final String MIMETYPE_TEXT_INTENT = "text/vnd.android.intent";
    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static final String MIMETYPE_TEXT_URILIST = "text/uri-list";
    private PersistableBundle mExtras;
    final CharSequence mLabel;
    private final ArrayList<String> mMimeTypes;
    private long mTimeStamp;

    public ClipDescription(ClipDescription clipDescription) {
        this.mLabel = clipDescription.mLabel;
        this.mMimeTypes = new ArrayList<String>(clipDescription.mMimeTypes);
        this.mTimeStamp = clipDescription.mTimeStamp;
    }

    ClipDescription(Parcel parcel) {
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mMimeTypes = parcel.createStringArrayList();
        this.mExtras = parcel.readPersistableBundle();
        this.mTimeStamp = parcel.readLong();
    }

    public ClipDescription(CharSequence charSequence, String[] arrstring) {
        if (arrstring != null) {
            this.mLabel = charSequence;
            this.mMimeTypes = new ArrayList<String>(Arrays.asList(arrstring));
            return;
        }
        throw new NullPointerException("mimeTypes is null");
    }

    public static boolean compareMimeTypes(String string2, String string3) {
        int n = string3.length();
        if (n == 3 && string3.equals("*/*")) {
            return true;
        }
        int n2 = string3.indexOf(47);
        return n2 > 0 && (n == n2 + 2 && string3.charAt(n2 + 1) == '*' ? string3.regionMatches(0, string2, 0, n2 + 1) : string3.equals(string2));
    }

    void addMimeTypes(String[] arrstring) {
        for (int i = 0; i != arrstring.length; ++i) {
            String string2 = arrstring[i];
            if (this.mMimeTypes.contains(string2)) continue;
            this.mMimeTypes.add(string2);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String[] filterMimeTypes(String arrstring) {
        ArrayList<String> arrayList = null;
        int n = this.mMimeTypes.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<String> arrayList2 = arrayList;
            if (ClipDescription.compareMimeTypes(this.mMimeTypes.get(i), (String)arrstring)) {
                arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<String>();
                }
                arrayList2.add(this.mMimeTypes.get(i));
            }
            arrayList = arrayList2;
        }
        if (arrayList == null) {
            return null;
        }
        arrstring = new String[arrayList.size()];
        arrayList.toArray(arrstring);
        return arrstring;
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public String getMimeType(int n) {
        return this.mMimeTypes.get(n);
    }

    public int getMimeTypeCount() {
        return this.mMimeTypes.size();
    }

    public long getTimestamp() {
        return this.mTimeStamp;
    }

    public boolean hasMimeType(String string2) {
        int n = this.mMimeTypes.size();
        for (int i = 0; i < n; ++i) {
            if (!ClipDescription.compareMimeTypes(this.mMimeTypes.get(i), string2)) continue;
            return true;
        }
        return false;
    }

    public void setExtras(PersistableBundle persistableBundle) {
        this.mExtras = new PersistableBundle(persistableBundle);
    }

    public void setTimestamp(long l) {
        this.mTimeStamp = l;
    }

    public boolean toShortString(StringBuilder stringBuilder) {
        boolean bl;
        boolean bl2 = this.toShortStringTypesOnly(stringBuilder);
        boolean bl3 = true;
        boolean bl4 = bl = bl2 ^ true;
        if (this.mLabel != null) {
            if (!bl) {
                stringBuilder.append(' ');
            }
            bl4 = false;
            stringBuilder.append('\"');
            stringBuilder.append(this.mLabel);
            stringBuilder.append('\"');
        }
        bl = bl4;
        if (this.mExtras != null) {
            if (!bl4) {
                stringBuilder.append(' ');
            }
            bl = false;
            stringBuilder.append(this.mExtras.toString());
        }
        bl4 = bl;
        if (this.mTimeStamp > 0L) {
            if (!bl) {
                stringBuilder.append(' ');
            }
            bl4 = false;
            stringBuilder.append('<');
            stringBuilder.append(TimeUtils.logTimeOfDay(this.mTimeStamp));
            stringBuilder.append('>');
        }
        if (bl4) {
            bl3 = false;
        }
        return bl3;
    }

    public boolean toShortStringTypesOnly(StringBuilder stringBuilder) {
        boolean bl = true;
        int n = this.mMimeTypes.size();
        for (int i = 0; i < n; ++i) {
            if (!bl) {
                stringBuilder.append(' ');
            }
            bl = false;
            stringBuilder.append(this.mMimeTypes.get(i));
        }
        boolean bl2 = !bl;
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("ClipDescription { ");
        this.toShortString(stringBuilder);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public void validate() {
        Serializable serializable = this.mMimeTypes;
        if (serializable != null) {
            int n = ((ArrayList)serializable).size();
            if (n > 0) {
                for (int i = 0; i < n; ++i) {
                    if (this.mMimeTypes.get(i) != null) {
                        continue;
                    }
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("mime type at ");
                    ((StringBuilder)serializable).append(i);
                    ((StringBuilder)serializable).append(" is null");
                    throw new NullPointerException(((StringBuilder)serializable).toString());
                }
                return;
            }
            throw new IllegalArgumentException("must have at least 1 mime type");
        }
        throw new NullPointerException("null mime types");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        TextUtils.writeToParcel(this.mLabel, parcel, n);
        parcel.writeStringList(this.mMimeTypes);
        parcel.writePersistableBundle(this.mExtras);
        parcel.writeLong(this.mTimeStamp);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        long l2 = protoOutputStream.start(l);
        int n = this.mMimeTypes.size();
        for (int i = 0; i < n; ++i) {
            protoOutputStream.write(2237677961217L, this.mMimeTypes.get(i));
        }
        Object object = this.mLabel;
        if (object != null) {
            protoOutputStream.write(1138166333442L, object.toString());
        }
        if ((object = this.mExtras) != null) {
            ((PersistableBundle)object).writeToProto(protoOutputStream, 1146756268035L);
        }
        if ((l = this.mTimeStamp) > 0L) {
            protoOutputStream.write(1112396529668L, l);
        }
        protoOutputStream.end(l2);
    }

}

