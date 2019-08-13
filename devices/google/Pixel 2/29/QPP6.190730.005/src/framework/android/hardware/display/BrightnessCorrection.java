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
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

@SystemApi
public final class BrightnessCorrection
implements Parcelable {
    public static final Parcelable.Creator<BrightnessCorrection> CREATOR = new Parcelable.Creator<BrightnessCorrection>(){

        @Override
        public BrightnessCorrection createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 1) {
                return null;
            }
            return ScaleAndTranslateLog.readFromParcel(parcel);
        }

        public BrightnessCorrection[] newArray(int n) {
            return new BrightnessCorrection[n];
        }
    };
    private static final int SCALE_AND_TRANSLATE_LOG = 1;
    private static final String TAG_SCALE_AND_TRANSLATE_LOG = "scale-and-translate-log";
    private BrightnessCorrectionImplementation mImplementation;

    private BrightnessCorrection(BrightnessCorrectionImplementation brightnessCorrectionImplementation) {
        this.mImplementation = brightnessCorrectionImplementation;
    }

    public static BrightnessCorrection createScaleAndTranslateLog(float f, float f2) {
        return new BrightnessCorrection(new ScaleAndTranslateLog(f, f2));
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

    public static BrightnessCorrection loadFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        int n = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, n)) {
            if (!TAG_SCALE_AND_TRANSLATE_LOG.equals(xmlPullParser.getName())) continue;
            return ScaleAndTranslateLog.loadFromXml(xmlPullParser);
        }
        return null;
    }

    public float apply(float f) {
        return this.mImplementation.apply(f);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof BrightnessCorrection)) {
            return false;
        }
        return ((BrightnessCorrection)object).mImplementation.equals(this.mImplementation);
    }

    public int hashCode() {
        return this.mImplementation.hashCode();
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
        this.mImplementation.saveToXml(xmlSerializer);
    }

    public String toString() {
        return this.mImplementation.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mImplementation.writeToParcel(parcel);
    }

    private static interface BrightnessCorrectionImplementation {
        public float apply(float var1);

        public void saveToXml(XmlSerializer var1) throws IOException;

        public String toString();

        public void writeToParcel(Parcel var1);
    }

    private static class ScaleAndTranslateLog
    implements BrightnessCorrectionImplementation {
        private static final String ATTR_SCALE = "scale";
        private static final String ATTR_TRANSLATE = "translate";
        private static final float MAX_SCALE = 2.0f;
        private static final float MAX_TRANSLATE = 0.7f;
        private static final float MIN_SCALE = 0.5f;
        private static final float MIN_TRANSLATE = -0.6f;
        private final float mScale;
        private final float mTranslate;

        ScaleAndTranslateLog(float f, float f2) {
            if (!Float.isNaN(f) && !Float.isNaN(f2)) {
                this.mScale = MathUtils.constrain(f, 0.5f, 2.0f);
                this.mTranslate = MathUtils.constrain(f2, -0.6f, 0.7f);
                return;
            }
            throw new IllegalArgumentException("scale and translate must be numbers");
        }

        static BrightnessCorrection loadFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
            return BrightnessCorrection.createScaleAndTranslateLog(BrightnessCorrection.loadFloatFromXml(xmlPullParser, ATTR_SCALE), BrightnessCorrection.loadFloatFromXml(xmlPullParser, ATTR_TRANSLATE));
        }

        static BrightnessCorrection readFromParcel(Parcel parcel) {
            return BrightnessCorrection.createScaleAndTranslateLog(parcel.readFloat(), parcel.readFloat());
        }

        @Override
        public float apply(float f) {
            return MathUtils.exp(this.mScale * MathUtils.log(f) + this.mTranslate);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof ScaleAndTranslateLog)) {
                return false;
            }
            object = (ScaleAndTranslateLog)object;
            if (((ScaleAndTranslateLog)object).mScale != this.mScale || ((ScaleAndTranslateLog)object).mTranslate != this.mTranslate) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return (1 * 31 + Float.hashCode(this.mScale)) * 31 + Float.hashCode(this.mTranslate);
        }

        @Override
        public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(null, BrightnessCorrection.TAG_SCALE_AND_TRANSLATE_LOG);
            xmlSerializer.attribute(null, ATTR_SCALE, Float.toString(this.mScale));
            xmlSerializer.attribute(null, ATTR_TRANSLATE, Float.toString(this.mTranslate));
            xmlSerializer.endTag(null, BrightnessCorrection.TAG_SCALE_AND_TRANSLATE_LOG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ScaleAndTranslateLog(");
            stringBuilder.append(this.mScale);
            stringBuilder.append(", ");
            stringBuilder.append(this.mTranslate);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(1);
            parcel.writeFloat(this.mScale);
            parcel.writeFloat(this.mTranslate);
        }
    }

}

