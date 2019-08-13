/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.os;

import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class PersistableBundle
extends BaseBundle
implements Cloneable,
Parcelable,
XmlUtils.WriteMapCallback {
    public static final Parcelable.Creator<PersistableBundle> CREATOR;
    public static final PersistableBundle EMPTY;
    private static final String TAG_PERSISTABLEMAP = "pbundle_as_map";

    static {
        EMPTY = new PersistableBundle();
        PersistableBundle.EMPTY.mMap = ArrayMap.EMPTY;
        CREATOR = new Parcelable.Creator<PersistableBundle>(){

            @Override
            public PersistableBundle createFromParcel(Parcel parcel) {
                return parcel.readPersistableBundle();
            }

            public PersistableBundle[] newArray(int n) {
                return new PersistableBundle[n];
            }
        };
    }

    public PersistableBundle() {
        this.mFlags = 1;
    }

    public PersistableBundle(int n) {
        super(n);
        this.mFlags = 1;
    }

    public PersistableBundle(Bundle bundle) {
        this(bundle.getMap());
    }

    PersistableBundle(Parcel parcel, int n) {
        super(parcel, n);
        this.mFlags = 1;
    }

    public PersistableBundle(PersistableBundle persistableBundle) {
        super(persistableBundle);
        this.mFlags = persistableBundle.mFlags;
    }

    private PersistableBundle(ArrayMap<String, Object> object) {
        this.mFlags = 1;
        this.putAll((ArrayMap)object);
        int n = this.mMap.size();
        for (int i = 0; i < n; ++i) {
            Object v = this.mMap.valueAt(i);
            if (v instanceof ArrayMap) {
                this.mMap.setValueAt(i, new PersistableBundle((ArrayMap)v));
                continue;
            }
            if (v instanceof Bundle) {
                this.mMap.setValueAt(i, new PersistableBundle((Bundle)v));
                continue;
            }
            if (PersistableBundle.isValidType(v)) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad value in PersistableBundle key=");
            ((StringBuilder)object).append((String)this.mMap.keyAt(i));
            ((StringBuilder)object).append(" value=");
            ((StringBuilder)object).append(v);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    PersistableBundle(boolean bl) {
        super(bl);
    }

    public static PersistableBundle forPair(String string2, String string3) {
        PersistableBundle persistableBundle = new PersistableBundle(1);
        persistableBundle.putString(string2, string3);
        return persistableBundle;
    }

    public static boolean isValidType(Object object) {
        boolean bl = object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof String || object instanceof int[] || object instanceof long[] || object instanceof double[] || object instanceof String[] || object instanceof PersistableBundle || object == null || object instanceof Boolean || object instanceof boolean[];
        return bl;
    }

    public static PersistableBundle restoreFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        int n;
        int n2 = xmlPullParser.getDepth();
        String string2 = xmlPullParser.getName();
        String[] arrstring = new String[1];
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() < n2)) {
            if (n != 2) continue;
            return new PersistableBundle(XmlUtils.readThisArrayMapXml(xmlPullParser, string2, arrstring, new MyReadMapCallback()));
        }
        return EMPTY;
    }

    public Object clone() {
        return new PersistableBundle(this);
    }

    public PersistableBundle deepCopy() {
        PersistableBundle persistableBundle = new PersistableBundle(false);
        persistableBundle.copyInternal(this, true);
        return persistableBundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PersistableBundle getPersistableBundle(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            PersistableBundle persistableBundle = (PersistableBundle)v;
            return persistableBundle;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "Bundle", classCastException);
            return null;
        }
    }

    public void putPersistableBundle(String string2, PersistableBundle persistableBundle) {
        this.unparcel();
        this.mMap.put(string2, persistableBundle);
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IOException, XmlPullParserException {
        this.unparcel();
        XmlUtils.writeMapXml((Map)this.mMap, xmlSerializer, this);
    }

    public String toShortString() {
        synchronized (this) {
            block5 : {
                block6 : {
                    if (this.mParcelledData == null) break block5;
                    if (!this.isEmptyParcel()) break block6;
                    return "EMPTY_PARCEL";
                }
                CharSequence charSequence = new StringBuilder();
                charSequence.append("mParcelledData.dataSize=");
                charSequence.append(this.mParcelledData.dataSize());
                charSequence = charSequence.toString();
                return charSequence;
            }
            String string2 = this.mMap.toString();
            return string2;
        }
    }

    public String toString() {
        synchronized (this) {
            block5 : {
                block6 : {
                    if (this.mParcelledData == null) break block5;
                    if (!this.isEmptyParcel()) break block6;
                    return "PersistableBundle[EMPTY_PARCEL]";
                }
                CharSequence charSequence = new StringBuilder();
                charSequence.append("PersistableBundle[mParcelledData.dataSize=");
                charSequence.append(this.mParcelledData.dataSize());
                charSequence.append("]");
                charSequence = charSequence.toString();
                return charSequence;
            }
            CharSequence charSequence = new StringBuilder();
            charSequence.append("PersistableBundle[");
            charSequence.append(this.mMap.toString());
            charSequence.append("]");
            charSequence = charSequence.toString();
            return charSequence;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        boolean bl = parcel.pushAllowFds(false);
        try {
            this.writeToParcelInner(parcel, n);
            return;
        }
        finally {
            parcel.restoreAllowFds(bl);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        if (this.mParcelledData != null) {
            if (this.isEmptyParcel()) {
                protoOutputStream.write(1120986464257L, 0);
            } else {
                protoOutputStream.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            protoOutputStream.write(1138166333442L, this.mMap.toString());
        }
        protoOutputStream.end(l);
    }

    @Override
    public void writeUnknownObject(Object object, String charSequence, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (object instanceof PersistableBundle) {
            xmlSerializer.startTag(null, TAG_PERSISTABLEMAP);
            xmlSerializer.attribute(null, "name", (String)charSequence);
            ((PersistableBundle)object).saveToXml(xmlSerializer);
            xmlSerializer.endTag(null, TAG_PERSISTABLEMAP);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unknown Object o=");
        ((StringBuilder)charSequence).append(object);
        throw new XmlPullParserException(((StringBuilder)charSequence).toString());
    }

    static class MyReadMapCallback
    implements XmlUtils.ReadMapCallback {
        MyReadMapCallback() {
        }

        @Override
        public Object readThisUnknownObjectXml(XmlPullParser object, String string2) throws XmlPullParserException, IOException {
            if (PersistableBundle.TAG_PERSISTABLEMAP.equals(string2)) {
                return PersistableBundle.restoreFromXml((XmlPullParser)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown tag=");
            ((StringBuilder)object).append(string2);
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
    }

}

