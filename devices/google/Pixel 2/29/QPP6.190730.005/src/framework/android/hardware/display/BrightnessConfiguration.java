/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.hardware.display;

import android.annotation.SystemApi;
import android.hardware.display.BrightnessCorrection;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

@SystemApi
public final class BrightnessConfiguration
implements Parcelable {
    private static final String ATTR_CATEGORY = "category";
    private static final String ATTR_DESCRIPTION = "description";
    private static final String ATTR_LUX = "lux";
    private static final String ATTR_NITS = "nits";
    private static final String ATTR_PACKAGE_NAME = "package-name";
    public static final Parcelable.Creator<BrightnessConfiguration> CREATOR = new Parcelable.Creator<BrightnessConfiguration>(){

        @Override
        public BrightnessConfiguration createFromParcel(Parcel parcel) {
            int n;
            Builder builder = new Builder(parcel.createFloatArray(), parcel.createFloatArray());
            int n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                builder.addCorrectionByPackageName(parcel.readString(), BrightnessCorrection.CREATOR.createFromParcel(parcel));
            }
            n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                builder.addCorrectionByCategory(parcel.readInt(), BrightnessCorrection.CREATOR.createFromParcel(parcel));
            }
            builder.setDescription(parcel.readString());
            return builder.build();
        }

        public BrightnessConfiguration[] newArray(int n) {
            return new BrightnessConfiguration[n];
        }
    };
    private static final String TAG_BRIGHTNESS_CORRECTION = "brightness-correction";
    private static final String TAG_BRIGHTNESS_CORRECTIONS = "brightness-corrections";
    private static final String TAG_BRIGHTNESS_CURVE = "brightness-curve";
    private static final String TAG_BRIGHTNESS_POINT = "brightness-point";
    private final Map<Integer, BrightnessCorrection> mCorrectionsByCategory;
    private final Map<String, BrightnessCorrection> mCorrectionsByPackageName;
    private final String mDescription;
    private final float[] mLux;
    private final float[] mNits;

    private BrightnessConfiguration(float[] arrf, float[] arrf2, Map<String, BrightnessCorrection> map, Map<Integer, BrightnessCorrection> map2, String string2) {
        this.mLux = arrf;
        this.mNits = arrf2;
        this.mCorrectionsByPackageName = map;
        this.mCorrectionsByCategory = map2;
        this.mDescription = string2;
    }

    private static float loadFloatFromXml(XmlPullParser object, String string2) {
        object = object.getAttributeValue(null, string2);
        try {
            float f = Float.parseFloat((String)object);
            return f;
        }
        catch (NullPointerException | NumberFormatException runtimeException) {
            return Float.NaN;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static BrightnessConfiguration loadFromXml(XmlPullParser object) throws IOException, XmlPullParserException {
        int n;
        void runtimeException;
        Object object32 = null;
        ArrayList<Float> arrayList = new ArrayList<Float>();
        ArrayList<Float> arrayList2 = new ArrayList<Float>();
        HashMap<String, BrightnessCorrection> hashMap = new HashMap<String, BrightnessCorrection>();
        HashMap<Integer, BrightnessCorrection> hashMap2 = new HashMap<Integer, BrightnessCorrection>();
        int n2 = object.getDepth();
        while (XmlUtils.nextElementWithin((XmlPullParser)object, n2)) {
            void var7_18;
            void var7_19 = runtimeException;
            if (TAG_BRIGHTNESS_CURVE.equals(object.getName())) {
                String string2 = object.getAttributeValue(null, ATTR_DESCRIPTION);
                n = object.getDepth();
                do {
                    String string3 = string2;
                    if (!XmlUtils.nextElementWithin((XmlPullParser)object, n)) break;
                    if (!TAG_BRIGHTNESS_POINT.equals(object.getName())) continue;
                    float f = BrightnessConfiguration.loadFloatFromXml((XmlPullParser)object, ATTR_LUX);
                    float f2 = BrightnessConfiguration.loadFloatFromXml((XmlPullParser)object, ATTR_NITS);
                    arrayList.add(Float.valueOf(f));
                    arrayList2.add(Float.valueOf(f2));
                } while (true);
            }
            void var1_5 = var7_18;
            if (!TAG_BRIGHTNESS_CORRECTIONS.equals(object.getName())) continue;
            n = object.getDepth();
            while (XmlUtils.nextElementWithin((XmlPullParser)object, n)) {
                if (!TAG_BRIGHTNESS_CORRECTION.equals(object.getName())) continue;
                String string4 = object.getAttributeValue(null, ATTR_PACKAGE_NAME);
                String string5 = object.getAttributeValue(null, ATTR_CATEGORY);
                BrightnessCorrection brightnessCorrection = BrightnessCorrection.loadFromXml((XmlPullParser)object);
                if (string4 != null) {
                    hashMap.put(string4, brightnessCorrection);
                    continue;
                }
                if (string5 == null) continue;
                try {
                    hashMap2.put(Integer.parseInt(string5), brightnessCorrection);
                }
                catch (NullPointerException | NumberFormatException runtimeException2) {}
            }
            void var1_9 = var7_18;
        }
        n = arrayList.size();
        object = new float[n];
        float[] arrf = new float[n];
        for (n2 = 0; n2 < n; ++n2) {
            object[n2] = ((Float)arrayList.get(n2)).floatValue();
            arrf[n2] = ((Float)arrayList2.get(n2)).floatValue();
        }
        object = new Builder((float[])object, arrf);
        ((Builder)object).setDescription((String)runtimeException);
        for (Map.Entry entry : hashMap.entrySet()) {
            ((Builder)object).addCorrectionByPackageName((String)entry.getKey(), (BrightnessCorrection)entry.getValue());
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            ((Builder)object).addCorrectionByCategory((Integer)entry.getKey(), (BrightnessCorrection)entry.getValue());
        }
        return ((Builder)object).build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof BrightnessConfiguration)) {
            return false;
        }
        object = (BrightnessConfiguration)object;
        if (!(Arrays.equals(this.mLux, ((BrightnessConfiguration)object).mLux) && Arrays.equals(this.mNits, ((BrightnessConfiguration)object).mNits) && this.mCorrectionsByPackageName.equals(((BrightnessConfiguration)object).mCorrectionsByPackageName) && this.mCorrectionsByCategory.equals(((BrightnessConfiguration)object).mCorrectionsByCategory) && Objects.equals(this.mDescription, ((BrightnessConfiguration)object).mDescription))) {
            bl = false;
        }
        return bl;
    }

    public BrightnessCorrection getCorrectionByCategory(int n) {
        return this.mCorrectionsByCategory.get(n);
    }

    public BrightnessCorrection getCorrectionByPackageName(String string2) {
        return this.mCorrectionsByPackageName.get(string2);
    }

    public Pair<float[], float[]> getCurve() {
        float[] arrf = this.mLux;
        float[] arrf2 = Arrays.copyOf(arrf, arrf.length);
        arrf = this.mNits;
        return Pair.create(arrf2, Arrays.copyOf(arrf, arrf.length));
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int hashCode() {
        int n = (((1 * 31 + Arrays.hashCode(this.mLux)) * 31 + Arrays.hashCode(this.mNits)) * 31 + this.mCorrectionsByPackageName.hashCode()) * 31 + this.mCorrectionsByCategory.hashCode();
        String string2 = this.mDescription;
        int n2 = n;
        if (string2 != null) {
            n2 = n * 31 + string2.hashCode();
        }
        return n2;
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
        int n;
        xmlSerializer.startTag(null, TAG_BRIGHTNESS_CURVE);
        Iterator<Map.Entry<Integer, BrightnessCorrection>> iterator = this.mDescription;
        if (iterator != null) {
            xmlSerializer.attribute(null, ATTR_DESCRIPTION, (String)((Object)iterator));
        }
        for (n = 0; n < this.mLux.length; ++n) {
            xmlSerializer.startTag(null, TAG_BRIGHTNESS_POINT);
            xmlSerializer.attribute(null, ATTR_LUX, Float.toString(this.mLux[n]));
            xmlSerializer.attribute(null, ATTR_NITS, Float.toString(this.mNits[n]));
            xmlSerializer.endTag(null, TAG_BRIGHTNESS_POINT);
        }
        xmlSerializer.endTag(null, TAG_BRIGHTNESS_CURVE);
        xmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTIONS);
        for (Map.Entry entry : this.mCorrectionsByPackageName.entrySet()) {
            String string2 = (String)entry.getKey();
            BrightnessCorrection object = (BrightnessCorrection)entry.getValue();
            xmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTION);
            xmlSerializer.attribute(null, ATTR_PACKAGE_NAME, string2);
            object.saveToXml(xmlSerializer);
            xmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTION);
        }
        for (Map.Entry<Integer, BrightnessCorrection> entry : this.mCorrectionsByCategory.entrySet()) {
            n = entry.getKey();
            BrightnessCorrection brightnessCorrection = entry.getValue();
            xmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTION);
            xmlSerializer.attribute(null, ATTR_CATEGORY, Integer.toString(n));
            brightnessCorrection.saveToXml(xmlSerializer);
            xmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTION);
        }
        xmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTIONS);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("BrightnessConfiguration{[");
        int n = this.mLux.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("(");
            stringBuilder.append(this.mLux[i]);
            stringBuilder.append(", ");
            stringBuilder.append(this.mNits[i]);
            stringBuilder.append(")");
        }
        stringBuilder.append("], {");
        for (Map.Entry<String, BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("'");
            stringBuilder2.append(entry.getKey());
            stringBuilder2.append("': ");
            stringBuilder2.append(entry.getValue());
            stringBuilder2.append(", ");
            stringBuilder.append(stringBuilder2.toString());
        }
        for (Map.Entry entry : this.mCorrectionsByCategory.entrySet()) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(entry.getKey());
            stringBuilder3.append(": ");
            stringBuilder3.append(entry.getValue());
            stringBuilder3.append(", ");
            stringBuilder.append(stringBuilder3.toString());
        }
        stringBuilder.append("}, '");
        String string2 = this.mDescription;
        if (string2 != null) {
            stringBuilder.append(string2);
        }
        stringBuilder.append("'}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloatArray(this.mLux);
        parcel.writeFloatArray(this.mNits);
        parcel.writeInt(this.mCorrectionsByPackageName.size());
        for (Map.Entry<String, BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            String string2 = entry.getKey();
            BrightnessCorrection object = entry.getValue();
            parcel.writeString(string2);
            object.writeToParcel(parcel, n);
        }
        parcel.writeInt(this.mCorrectionsByCategory.size());
        for (Map.Entry<Object, BrightnessCorrection> entry : this.mCorrectionsByCategory.entrySet()) {
            int n2 = (Integer)entry.getKey();
            BrightnessCorrection brightnessCorrection = entry.getValue();
            parcel.writeInt(n2);
            brightnessCorrection.writeToParcel(parcel, n);
        }
        parcel.writeString(this.mDescription);
    }

    public static class Builder {
        private static final int MAX_CORRECTIONS_BY_CATEGORY = 20;
        private static final int MAX_CORRECTIONS_BY_PACKAGE_NAME = 20;
        private Map<Integer, BrightnessCorrection> mCorrectionsByCategory;
        private Map<String, BrightnessCorrection> mCorrectionsByPackageName;
        private float[] mCurveLux;
        private float[] mCurveNits;
        private String mDescription;

        public Builder(float[] arrf, float[] arrf2) {
            Preconditions.checkNotNull(arrf);
            Preconditions.checkNotNull(arrf2);
            if (arrf.length != 0 && arrf2.length != 0) {
                if (arrf.length == arrf2.length) {
                    if (arrf[0] == 0.0f) {
                        Preconditions.checkArrayElementsInRange(arrf, 0.0f, Float.MAX_VALUE, BrightnessConfiguration.ATTR_LUX);
                        Preconditions.checkArrayElementsInRange(arrf2, 0.0f, Float.MAX_VALUE, BrightnessConfiguration.ATTR_NITS);
                        Builder.checkMonotonic(arrf, true, BrightnessConfiguration.ATTR_LUX);
                        Builder.checkMonotonic(arrf2, false, BrightnessConfiguration.ATTR_NITS);
                        this.mCurveLux = arrf;
                        this.mCurveNits = arrf2;
                        this.mCorrectionsByPackageName = new HashMap<String, BrightnessCorrection>();
                        this.mCorrectionsByCategory = new HashMap<Integer, BrightnessCorrection>();
                        return;
                    }
                    throw new IllegalArgumentException("Initial control point must be for 0 lux");
                }
                throw new IllegalArgumentException("Lux and nits arrays must be the same length");
            }
            throw new IllegalArgumentException("Lux and nits arrays must not be empty");
        }

        private static void checkMonotonic(float[] object, boolean bl, String string2) {
            if (((float[])object).length <= 1) {
                return;
            }
            float f = object[0];
            for (int i = 1; i < ((float[])object).length; ++i) {
                if (!(f > object[i] || f == object[i] && bl)) {
                    f = object[i];
                    continue;
                }
                object = bl ? "strictly increasing" : "monotonic";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" values must be ");
                stringBuilder.append((String)object);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        public Builder addCorrectionByCategory(int n, BrightnessCorrection brightnessCorrection) {
            Objects.requireNonNull(brightnessCorrection, "correction must not be null");
            if (this.mCorrectionsByCategory.size() < this.getMaxCorrectionsByCategory()) {
                this.mCorrectionsByCategory.put(n, brightnessCorrection);
                return this;
            }
            throw new IllegalArgumentException("Too many corrections by category");
        }

        public Builder addCorrectionByPackageName(String string2, BrightnessCorrection brightnessCorrection) {
            Objects.requireNonNull(string2, "packageName must not be null");
            Objects.requireNonNull(brightnessCorrection, "correction must not be null");
            if (this.mCorrectionsByPackageName.size() < this.getMaxCorrectionsByPackageName()) {
                this.mCorrectionsByPackageName.put(string2, brightnessCorrection);
                return this;
            }
            throw new IllegalArgumentException("Too many corrections by package name");
        }

        public BrightnessConfiguration build() {
            float[] arrf;
            float[] arrf2 = this.mCurveLux;
            if (arrf2 != null && (arrf = this.mCurveNits) != null) {
                return new BrightnessConfiguration(arrf2, arrf, this.mCorrectionsByPackageName, this.mCorrectionsByCategory, this.mDescription);
            }
            throw new IllegalStateException("A curve must be set!");
        }

        public int getMaxCorrectionsByCategory() {
            return 20;
        }

        public int getMaxCorrectionsByPackageName() {
            return 20;
        }

        public Builder setDescription(String string2) {
            this.mDescription = string2;
            return this;
        }
    }

}

