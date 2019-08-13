/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Map;

public final class PrintAttributes
implements Parcelable {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final Parcelable.Creator<PrintAttributes> CREATOR = new Parcelable.Creator<PrintAttributes>(){

        @Override
        public PrintAttributes createFromParcel(Parcel parcel) {
            return new PrintAttributes(parcel);
        }

        public PrintAttributes[] newArray(int n) {
            return new PrintAttributes[n];
        }
    };
    public static final int DUPLEX_MODE_LONG_EDGE = 2;
    public static final int DUPLEX_MODE_NONE = 1;
    public static final int DUPLEX_MODE_SHORT_EDGE = 4;
    private static final int VALID_COLOR_MODES = 3;
    private static final int VALID_DUPLEX_MODES = 7;
    private int mColorMode;
    private int mDuplexMode;
    private MediaSize mMediaSize;
    private Margins mMinMargins;
    private Resolution mResolution;

    PrintAttributes() {
    }

    private PrintAttributes(Parcel parcel) {
        int n = parcel.readInt();
        Object var3_3 = null;
        Object object = n == 1 ? MediaSize.createFromParcel(parcel) : null;
        this.mMediaSize = object;
        object = parcel.readInt() == 1 ? Resolution.createFromParcel(parcel) : null;
        this.mResolution = object;
        object = var3_3;
        if (parcel.readInt() == 1) {
            object = Margins.createFromParcel(parcel);
        }
        this.mMinMargins = object;
        this.mColorMode = parcel.readInt();
        n = this.mColorMode;
        if (n != 0) {
            PrintAttributes.enforceValidColorMode(n);
        }
        if ((n = (this.mDuplexMode = parcel.readInt())) != 0) {
            PrintAttributes.enforceValidDuplexMode(n);
        }
    }

    static String colorModeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                return "COLOR_MODE_UNKNOWN";
            }
            return "COLOR_MODE_COLOR";
        }
        return "COLOR_MODE_MONOCHROME";
    }

    static String duplexModeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    return "DUPLEX_MODE_UNKNOWN";
                }
                return "DUPLEX_MODE_SHORT_EDGE";
            }
            return "DUPLEX_MODE_LONG_EDGE";
        }
        return "DUPLEX_MODE_NONE";
    }

    static void enforceValidColorMode(int n) {
        if ((n & 3) != 0 && Integer.bitCount(n) == 1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid color mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static void enforceValidDuplexMode(int n) {
        if ((n & 7) != 0 && Integer.bitCount(n) == 1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid duplex mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public PrintAttributes asLandscape() {
        if (!this.isPortrait()) {
            return this;
        }
        PrintAttributes printAttributes = new PrintAttributes();
        printAttributes.setMediaSize(this.getMediaSize().asLandscape());
        Resolution resolution = this.getResolution();
        printAttributes.setResolution(new Resolution(resolution.getId(), resolution.getLabel(), resolution.getVerticalDpi(), resolution.getHorizontalDpi()));
        printAttributes.setMinMargins(this.getMinMargins());
        printAttributes.setColorMode(this.getColorMode());
        printAttributes.setDuplexMode(this.getDuplexMode());
        return printAttributes;
    }

    public PrintAttributes asPortrait() {
        if (this.isPortrait()) {
            return this;
        }
        PrintAttributes printAttributes = new PrintAttributes();
        printAttributes.setMediaSize(this.getMediaSize().asPortrait());
        Resolution resolution = this.getResolution();
        printAttributes.setResolution(new Resolution(resolution.getId(), resolution.getLabel(), resolution.getVerticalDpi(), resolution.getHorizontalDpi()));
        printAttributes.setMinMargins(this.getMinMargins());
        printAttributes.setColorMode(this.getColorMode());
        printAttributes.setDuplexMode(this.getDuplexMode());
        return printAttributes;
    }

    public void clear() {
        this.mMediaSize = null;
        this.mResolution = null;
        this.mMinMargins = null;
        this.mColorMode = 0;
        this.mDuplexMode = 0;
    }

    public void copyFrom(PrintAttributes printAttributes) {
        this.mMediaSize = printAttributes.mMediaSize;
        this.mResolution = printAttributes.mResolution;
        this.mMinMargins = printAttributes.mMinMargins;
        this.mColorMode = printAttributes.mColorMode;
        this.mDuplexMode = printAttributes.mDuplexMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PrintAttributes)object;
        if (this.mColorMode != ((PrintAttributes)object).mColorMode) {
            return false;
        }
        if (this.mDuplexMode != ((PrintAttributes)object).mDuplexMode) {
            return false;
        }
        Object object2 = this.mMinMargins;
        if (object2 == null ? ((PrintAttributes)object).mMinMargins != null : !((Margins)object2).equals(((PrintAttributes)object).mMinMargins)) {
            return false;
        }
        object2 = this.mMediaSize;
        if (object2 == null ? ((PrintAttributes)object).mMediaSize != null : !((MediaSize)object2).equals(((PrintAttributes)object).mMediaSize)) {
            return false;
        }
        object2 = this.mResolution;
        return !(object2 == null ? ((PrintAttributes)object).mResolution != null : !((Resolution)object2).equals(((PrintAttributes)object).mResolution));
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public int getDuplexMode() {
        return this.mDuplexMode;
    }

    public MediaSize getMediaSize() {
        return this.mMediaSize;
    }

    public Margins getMinMargins() {
        return this.mMinMargins;
    }

    public Resolution getResolution() {
        return this.mResolution;
    }

    public int hashCode() {
        int n = this.mColorMode;
        int n2 = this.mDuplexMode;
        Object object = this.mMinMargins;
        int n3 = 0;
        int n4 = object == null ? 0 : ((Margins)object).hashCode();
        object = this.mMediaSize;
        int n5 = object == null ? 0 : ((MediaSize)object).hashCode();
        object = this.mResolution;
        if (object != null) {
            n3 = ((Resolution)object).hashCode();
        }
        return ((((1 * 31 + n) * 31 + n2) * 31 + n4) * 31 + n5) * 31 + n3;
    }

    public boolean isPortrait() {
        return this.mMediaSize.isPortrait();
    }

    public void setColorMode(int n) {
        PrintAttributes.enforceValidColorMode(n);
        this.mColorMode = n;
    }

    public void setDuplexMode(int n) {
        PrintAttributes.enforceValidDuplexMode(n);
        this.mDuplexMode = n;
    }

    public void setMediaSize(MediaSize mediaSize) {
        this.mMediaSize = mediaSize;
    }

    public void setMinMargins(Margins margins) {
        this.mMinMargins = margins;
    }

    public void setResolution(Resolution resolution) {
        this.mResolution = resolution;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrintAttributes{");
        stringBuilder.append("mediaSize: ");
        stringBuilder.append(this.mMediaSize);
        if (this.mMediaSize != null) {
            stringBuilder.append(", orientation: ");
            String string2 = this.mMediaSize.isPortrait() ? "portrait" : "landscape";
            stringBuilder.append(string2);
        } else {
            stringBuilder.append(", orientation: ");
            stringBuilder.append("null");
        }
        stringBuilder.append(", resolution: ");
        stringBuilder.append(this.mResolution);
        stringBuilder.append(", minMargins: ");
        stringBuilder.append(this.mMinMargins);
        stringBuilder.append(", colorMode: ");
        stringBuilder.append(PrintAttributes.colorModeToString(this.mColorMode));
        stringBuilder.append(", duplexMode: ");
        stringBuilder.append(PrintAttributes.duplexModeToString(this.mDuplexMode));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mMediaSize != null) {
            parcel.writeInt(1);
            this.mMediaSize.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        if (this.mResolution != null) {
            parcel.writeInt(1);
            this.mResolution.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        if (this.mMinMargins != null) {
            parcel.writeInt(1);
            this.mMinMargins.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mColorMode);
        parcel.writeInt(this.mDuplexMode);
    }

    public static final class Builder {
        private final PrintAttributes mAttributes = new PrintAttributes();

        public PrintAttributes build() {
            return this.mAttributes;
        }

        public Builder setColorMode(int n) {
            this.mAttributes.setColorMode(n);
            return this;
        }

        public Builder setDuplexMode(int n) {
            this.mAttributes.setDuplexMode(n);
            return this;
        }

        public Builder setMediaSize(MediaSize mediaSize) {
            this.mAttributes.setMediaSize(mediaSize);
            return this;
        }

        public Builder setMinMargins(Margins margins) {
            this.mAttributes.setMinMargins(margins);
            return this;
        }

        public Builder setResolution(Resolution resolution) {
            this.mAttributes.setResolution(resolution);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ColorMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface DuplexMode {
    }

    public static final class Margins {
        public static final Margins NO_MARGINS = new Margins(0, 0, 0, 0);
        private final int mBottomMils;
        private final int mLeftMils;
        private final int mRightMils;
        private final int mTopMils;

        public Margins(int n, int n2, int n3, int n4) {
            this.mTopMils = n2;
            this.mLeftMils = n;
            this.mRightMils = n3;
            this.mBottomMils = n4;
        }

        static Margins createFromParcel(Parcel parcel) {
            return new Margins(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (Margins)object;
            if (this.mBottomMils != ((Margins)object).mBottomMils) {
                return false;
            }
            if (this.mLeftMils != ((Margins)object).mLeftMils) {
                return false;
            }
            if (this.mRightMils != ((Margins)object).mRightMils) {
                return false;
            }
            return this.mTopMils == ((Margins)object).mTopMils;
        }

        public int getBottomMils() {
            return this.mBottomMils;
        }

        public int getLeftMils() {
            return this.mLeftMils;
        }

        public int getRightMils() {
            return this.mRightMils;
        }

        public int getTopMils() {
            return this.mTopMils;
        }

        public int hashCode() {
            return (((1 * 31 + this.mBottomMils) * 31 + this.mLeftMils) * 31 + this.mRightMils) * 31 + this.mTopMils;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Margins{");
            stringBuilder.append("leftMils: ");
            stringBuilder.append(this.mLeftMils);
            stringBuilder.append(", topMils: ");
            stringBuilder.append(this.mTopMils);
            stringBuilder.append(", rightMils: ");
            stringBuilder.append(this.mRightMils);
            stringBuilder.append(", bottomMils: ");
            stringBuilder.append(this.mBottomMils);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.mLeftMils);
            parcel.writeInt(this.mTopMils);
            parcel.writeInt(this.mRightMils);
            parcel.writeInt(this.mBottomMils);
        }
    }

    public static final class MediaSize {
        public static final MediaSize ISO_A0;
        public static final MediaSize ISO_A1;
        public static final MediaSize ISO_A10;
        public static final MediaSize ISO_A2;
        public static final MediaSize ISO_A3;
        public static final MediaSize ISO_A4;
        public static final MediaSize ISO_A5;
        public static final MediaSize ISO_A6;
        public static final MediaSize ISO_A7;
        public static final MediaSize ISO_A8;
        public static final MediaSize ISO_A9;
        public static final MediaSize ISO_B0;
        public static final MediaSize ISO_B1;
        public static final MediaSize ISO_B10;
        public static final MediaSize ISO_B2;
        public static final MediaSize ISO_B3;
        public static final MediaSize ISO_B4;
        public static final MediaSize ISO_B5;
        public static final MediaSize ISO_B6;
        public static final MediaSize ISO_B7;
        public static final MediaSize ISO_B8;
        public static final MediaSize ISO_B9;
        public static final MediaSize ISO_C0;
        public static final MediaSize ISO_C1;
        public static final MediaSize ISO_C10;
        public static final MediaSize ISO_C2;
        public static final MediaSize ISO_C3;
        public static final MediaSize ISO_C4;
        public static final MediaSize ISO_C5;
        public static final MediaSize ISO_C6;
        public static final MediaSize ISO_C7;
        public static final MediaSize ISO_C8;
        public static final MediaSize ISO_C9;
        public static final MediaSize JIS_B0;
        public static final MediaSize JIS_B1;
        public static final MediaSize JIS_B10;
        public static final MediaSize JIS_B2;
        public static final MediaSize JIS_B3;
        public static final MediaSize JIS_B4;
        public static final MediaSize JIS_B5;
        public static final MediaSize JIS_B6;
        public static final MediaSize JIS_B7;
        public static final MediaSize JIS_B8;
        public static final MediaSize JIS_B9;
        public static final MediaSize JIS_EXEC;
        public static final MediaSize JPN_CHOU2;
        public static final MediaSize JPN_CHOU3;
        public static final MediaSize JPN_CHOU4;
        public static final MediaSize JPN_HAGAKI;
        public static final MediaSize JPN_KAHU;
        public static final MediaSize JPN_KAKU2;
        public static final MediaSize JPN_OUFUKU;
        public static final MediaSize JPN_YOU4;
        private static final String LOG_TAG = "MediaSize";
        public static final MediaSize NA_FOOLSCAP;
        public static final MediaSize NA_GOVT_LETTER;
        public static final MediaSize NA_INDEX_3X5;
        public static final MediaSize NA_INDEX_4X6;
        public static final MediaSize NA_INDEX_5X8;
        public static final MediaSize NA_JUNIOR_LEGAL;
        public static final MediaSize NA_LEDGER;
        public static final MediaSize NA_LEGAL;
        public static final MediaSize NA_LETTER;
        public static final MediaSize NA_MONARCH;
        public static final MediaSize NA_QUARTO;
        public static final MediaSize NA_TABLOID;
        public static final MediaSize OM_DAI_PA_KAI;
        public static final MediaSize OM_JUURO_KU_KAI;
        public static final MediaSize OM_PA_KAI;
        public static final MediaSize PRC_1;
        public static final MediaSize PRC_10;
        public static final MediaSize PRC_16K;
        public static final MediaSize PRC_2;
        public static final MediaSize PRC_3;
        public static final MediaSize PRC_4;
        public static final MediaSize PRC_5;
        public static final MediaSize PRC_6;
        public static final MediaSize PRC_7;
        public static final MediaSize PRC_8;
        public static final MediaSize PRC_9;
        public static final MediaSize ROC_16K;
        public static final MediaSize ROC_8K;
        public static final MediaSize UNKNOWN_LANDSCAPE;
        public static final MediaSize UNKNOWN_PORTRAIT;
        private static final Map<String, MediaSize> sIdToMediaSizeMap;
        private final int mHeightMils;
        private final String mId;
        public final String mLabel;
        public final int mLabelResId;
        public final String mPackageName;
        private final int mWidthMils;

        static {
            sIdToMediaSizeMap = new ArrayMap<String, MediaSize>();
            UNKNOWN_PORTRAIT = new MediaSize("UNKNOWN_PORTRAIT", "android", 17040400, 1, Integer.MAX_VALUE);
            UNKNOWN_LANDSCAPE = new MediaSize("UNKNOWN_LANDSCAPE", "android", 17040399, Integer.MAX_VALUE, 1);
            ISO_A0 = new MediaSize("ISO_A0", "android", 17040334, 33110, 46810);
            ISO_A1 = new MediaSize("ISO_A1", "android", 17040335, 23390, 33110);
            ISO_A2 = new MediaSize("ISO_A2", "android", 17040337, 16540, 23390);
            ISO_A3 = new MediaSize("ISO_A3", "android", 17040338, 11690, 16540);
            ISO_A4 = new MediaSize("ISO_A4", "android", 17040339, 8270, 11690);
            ISO_A5 = new MediaSize("ISO_A5", "android", 17040340, 5830, 8270);
            ISO_A6 = new MediaSize("ISO_A6", "android", 17040341, 4130, 5830);
            ISO_A7 = new MediaSize("ISO_A7", "android", 17040342, 2910, 4130);
            ISO_A8 = new MediaSize("ISO_A8", "android", 17040343, 2050, 2910);
            ISO_A9 = new MediaSize("ISO_A9", "android", 17040344, 1460, 2050);
            ISO_A10 = new MediaSize("ISO_A10", "android", 17040336, 1020, 1460);
            ISO_B0 = new MediaSize("ISO_B0", "android", 17040345, 39370, 55670);
            ISO_B1 = new MediaSize("ISO_B1", "android", 17040346, 27830, 39370);
            ISO_B2 = new MediaSize("ISO_B2", "android", 17040348, 19690, 27830);
            ISO_B3 = new MediaSize("ISO_B3", "android", 17040349, 13900, 19690);
            ISO_B4 = new MediaSize("ISO_B4", "android", 17040350, 9840, 13900);
            ISO_B5 = new MediaSize("ISO_B5", "android", 17040351, 6930, 9840);
            ISO_B6 = new MediaSize("ISO_B6", "android", 17040352, 4920, 6930);
            ISO_B7 = new MediaSize("ISO_B7", "android", 17040353, 3460, 4920);
            ISO_B8 = new MediaSize("ISO_B8", "android", 17040354, 2440, 3460);
            ISO_B9 = new MediaSize("ISO_B9", "android", 17040355, 1730, 2440);
            ISO_B10 = new MediaSize("ISO_B10", "android", 17040347, 1220, 1730);
            ISO_C0 = new MediaSize("ISO_C0", "android", 17040356, 36100, 51060);
            ISO_C1 = new MediaSize("ISO_C1", "android", 17040357, 25510, 36100);
            ISO_C2 = new MediaSize("ISO_C2", "android", 17040359, 18030, 25510);
            ISO_C3 = new MediaSize("ISO_C3", "android", 17040360, 12760, 18030);
            ISO_C4 = new MediaSize("ISO_C4", "android", 17040361, 9020, 12760);
            ISO_C5 = new MediaSize("ISO_C5", "android", 17040362, 6380, 9020);
            ISO_C6 = new MediaSize("ISO_C6", "android", 17040363, 4490, 6380);
            ISO_C7 = new MediaSize("ISO_C7", "android", 17040364, 3190, 4490);
            ISO_C8 = new MediaSize("ISO_C8", "android", 17040365, 2240, 3190);
            ISO_C9 = new MediaSize("ISO_C9", "android", 17040366, 1570, 2240);
            ISO_C10 = new MediaSize("ISO_C10", "android", 17040358, 1100, 1570);
            NA_LETTER = new MediaSize("NA_LETTER", "android", 17040395, 8500, 11000);
            NA_GOVT_LETTER = new MediaSize("NA_GOVT_LETTER", "android", 17040388, 8000, 10500);
            NA_LEGAL = new MediaSize("NA_LEGAL", "android", 17040394, 8500, 14000);
            NA_JUNIOR_LEGAL = new MediaSize("NA_JUNIOR_LEGAL", "android", 17040392, 8000, 5000);
            NA_LEDGER = new MediaSize("NA_LEDGER", "android", 17040393, 17000, 11000);
            NA_TABLOID = new MediaSize("NA_TABLOID", "android", 17040398, 11000, 17000);
            NA_INDEX_3X5 = new MediaSize("NA_INDEX_3X5", "android", 17040389, 3000, 5000);
            NA_INDEX_4X6 = new MediaSize("NA_INDEX_4X6", "android", 17040390, 4000, 6000);
            NA_INDEX_5X8 = new MediaSize("NA_INDEX_5X8", "android", 17040391, 5000, 8000);
            NA_MONARCH = new MediaSize("NA_MONARCH", "android", 17040396, 7250, 10500);
            NA_QUARTO = new MediaSize("NA_QUARTO", "android", 17040397, 8000, 10000);
            NA_FOOLSCAP = new MediaSize("NA_FOOLSCAP", "android", 17040387, 8000, 13000);
            ROC_8K = new MediaSize("ROC_8K", "android", 17040333, 10629, 15354);
            ROC_16K = new MediaSize("ROC_16K", "android", 17040332, 7677, 10629);
            PRC_1 = new MediaSize("PRC_1", "android", 17040321, 4015, 6496);
            PRC_2 = new MediaSize("PRC_2", "android", 17040324, 4015, 6929);
            PRC_3 = new MediaSize("PRC_3", "android", 17040325, 4921, 6929);
            PRC_4 = new MediaSize("PRC_4", "android", 17040326, 4330, 8189);
            PRC_5 = new MediaSize("PRC_5", "android", 17040327, 4330, 8661);
            PRC_6 = new MediaSize("PRC_6", "android", 17040328, 4724, 12599);
            PRC_7 = new MediaSize("PRC_7", "android", 17040329, 6299, 9055);
            PRC_8 = new MediaSize("PRC_8", "android", 17040330, 4724, 12165);
            PRC_9 = new MediaSize("PRC_9", "android", 17040331, 9016, 12756);
            PRC_10 = new MediaSize("PRC_10", "android", 17040322, 12756, 18032);
            PRC_16K = new MediaSize("PRC_16K", "android", 17040323, 5749, 8465);
            OM_PA_KAI = new MediaSize("OM_PA_KAI", "android", 17040320, 10512, 15315);
            OM_DAI_PA_KAI = new MediaSize("OM_DAI_PA_KAI", "android", 17040318, 10827, 15551);
            OM_JUURO_KU_KAI = new MediaSize("OM_JUURO_KU_KAI", "android", 17040319, 7796, 10827);
            JIS_B10 = new MediaSize("JIS_B10", "android", 17040373, 1259, 1772);
            JIS_B9 = new MediaSize("JIS_B9", "android", 17040381, 1772, 2520);
            JIS_B8 = new MediaSize("JIS_B8", "android", 17040380, 2520, 3583);
            JIS_B7 = new MediaSize("JIS_B7", "android", 17040379, 3583, 5049);
            JIS_B6 = new MediaSize("JIS_B6", "android", 17040378, 5049, 7165);
            JIS_B5 = new MediaSize("JIS_B5", "android", 17040377, 7165, 10118);
            JIS_B4 = new MediaSize("JIS_B4", "android", 17040376, 10118, 14331);
            JIS_B3 = new MediaSize("JIS_B3", "android", 17040375, 14331, 20276);
            JIS_B2 = new MediaSize("JIS_B2", "android", 17040374, 20276, 28661);
            JIS_B1 = new MediaSize("JIS_B1", "android", 17040372, 28661, 40551);
            JIS_B0 = new MediaSize("JIS_B0", "android", 17040371, 40551, 57323);
            JIS_EXEC = new MediaSize("JIS_EXEC", "android", 17040382, 8504, 12992);
            JPN_CHOU4 = new MediaSize("JPN_CHOU4", "android", 17040369, 3543, 8071);
            JPN_CHOU3 = new MediaSize("JPN_CHOU3", "android", 17040368, 4724, 9252);
            JPN_CHOU2 = new MediaSize("JPN_CHOU2", "android", 17040367, 4374, 5748);
            JPN_HAGAKI = new MediaSize("JPN_HAGAKI", "android", 17040370, 3937, 5827);
            JPN_OUFUKU = new MediaSize("JPN_OUFUKU", "android", 17040385, 5827, 7874);
            JPN_KAHU = new MediaSize("JPN_KAHU", "android", 17040383, 9449, 12681);
            JPN_KAKU2 = new MediaSize("JPN_KAKU2", "android", 17040384, 9449, 13071);
            JPN_YOU4 = new MediaSize("JPN_YOU4", "android", 17040386, 4134, 9252);
        }

        public MediaSize(String string2, String string3, int n, int n2) {
            this(string2, string3, null, n, n2, 0);
        }

        public MediaSize(String string2, String string3, int n, int n2, int n3) {
            this(string2, null, string3, n2, n3, n);
            sIdToMediaSizeMap.put(this.mId, this);
        }

        public MediaSize(String string2, String string3, String string4, int n, int n2, int n3) {
            this.mPackageName = string4;
            this.mId = Preconditions.checkStringNotEmpty(string2, "id cannot be empty.");
            this.mLabelResId = n3;
            this.mWidthMils = Preconditions.checkArgumentPositive(n, "widthMils cannot be less than or equal to zero.");
            this.mHeightMils = Preconditions.checkArgumentPositive(n2, "heightMils cannot be less than or equal to zero.");
            this.mLabel = string3;
            boolean bl = TextUtils.isEmpty(string3);
            boolean bl2 = true;
            n = !TextUtils.isEmpty(string4) && n3 != 0 ? 1 : 0;
            if ((bl ^ true) == n) {
                bl2 = false;
            }
            Preconditions.checkArgument(bl2, "label cannot be empty.");
        }

        static MediaSize createFromParcel(Parcel parcel) {
            return new MediaSize(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public static ArraySet<MediaSize> getAllPredefinedSizes() {
            ArraySet<MediaSize> arraySet = new ArraySet<MediaSize>(sIdToMediaSizeMap.values());
            arraySet.remove(UNKNOWN_PORTRAIT);
            arraySet.remove(UNKNOWN_LANDSCAPE);
            return arraySet;
        }

        public static MediaSize getStandardMediaSizeById(String string2) {
            return sIdToMediaSizeMap.get(string2);
        }

        public MediaSize asLandscape() {
            if (!this.isPortrait()) {
                return this;
            }
            return new MediaSize(this.mId, this.mLabel, this.mPackageName, Math.max(this.mWidthMils, this.mHeightMils), Math.min(this.mWidthMils, this.mHeightMils), this.mLabelResId);
        }

        public MediaSize asPortrait() {
            if (this.isPortrait()) {
                return this;
            }
            return new MediaSize(this.mId, this.mLabel, this.mPackageName, Math.min(this.mWidthMils, this.mHeightMils), Math.max(this.mWidthMils, this.mHeightMils), this.mLabelResId);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (MediaSize)object;
            if (this.mWidthMils != ((MediaSize)object).mWidthMils) {
                return false;
            }
            return this.mHeightMils == ((MediaSize)object).mHeightMils;
        }

        public int getHeightMils() {
            return this.mHeightMils;
        }

        public String getId() {
            return this.mId;
        }

        public String getLabel(PackageManager object) {
            if (!TextUtils.isEmpty(this.mPackageName) && this.mLabelResId > 0) {
                try {
                    object = ((PackageManager)object).getResourcesForApplication(this.mPackageName).getString(this.mLabelResId);
                    return object;
                }
                catch (PackageManager.NameNotFoundException | Resources.NotFoundException exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not load resouce");
                    stringBuilder.append(this.mLabelResId);
                    stringBuilder.append(" from package ");
                    stringBuilder.append(this.mPackageName);
                    Log.w(LOG_TAG, stringBuilder.toString());
                }
            }
            return this.mLabel;
        }

        public int getWidthMils() {
            return this.mWidthMils;
        }

        public int hashCode() {
            return (1 * 31 + this.mWidthMils) * 31 + this.mHeightMils;
        }

        public boolean isPortrait() {
            boolean bl = this.mHeightMils >= this.mWidthMils;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaSize{");
            stringBuilder.append("id: ");
            stringBuilder.append(this.mId);
            stringBuilder.append(", label: ");
            stringBuilder.append(this.mLabel);
            stringBuilder.append(", packageName: ");
            stringBuilder.append(this.mPackageName);
            stringBuilder.append(", heightMils: ");
            stringBuilder.append(this.mHeightMils);
            stringBuilder.append(", widthMils: ");
            stringBuilder.append(this.mWidthMils);
            stringBuilder.append(", labelResId: ");
            stringBuilder.append(this.mLabelResId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mId);
            parcel.writeString(this.mLabel);
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mWidthMils);
            parcel.writeInt(this.mHeightMils);
            parcel.writeInt(this.mLabelResId);
        }
    }

    public static final class Resolution {
        private final int mHorizontalDpi;
        private final String mId;
        private final String mLabel;
        private final int mVerticalDpi;

        public Resolution(String string2, String string3, int n, int n2) {
            if (!TextUtils.isEmpty(string2)) {
                if (!TextUtils.isEmpty(string3)) {
                    if (n > 0) {
                        if (n2 > 0) {
                            this.mId = string2;
                            this.mLabel = string3;
                            this.mHorizontalDpi = n;
                            this.mVerticalDpi = n2;
                            return;
                        }
                        throw new IllegalArgumentException("verticalDpi cannot be less than or equal to zero.");
                    }
                    throw new IllegalArgumentException("horizontalDpi cannot be less than or equal to zero.");
                }
                throw new IllegalArgumentException("label cannot be empty.");
            }
            throw new IllegalArgumentException("id cannot be empty.");
        }

        static Resolution createFromParcel(Parcel parcel) {
            return new Resolution(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (Resolution)object;
            if (this.mHorizontalDpi != ((Resolution)object).mHorizontalDpi) {
                return false;
            }
            return this.mVerticalDpi == ((Resolution)object).mVerticalDpi;
        }

        public int getHorizontalDpi() {
            return this.mHorizontalDpi;
        }

        public String getId() {
            return this.mId;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public int getVerticalDpi() {
            return this.mVerticalDpi;
        }

        public int hashCode() {
            return (1 * 31 + this.mHorizontalDpi) * 31 + this.mVerticalDpi;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resolution{");
            stringBuilder.append("id: ");
            stringBuilder.append(this.mId);
            stringBuilder.append(", label: ");
            stringBuilder.append(this.mLabel);
            stringBuilder.append(", horizontalDpi: ");
            stringBuilder.append(this.mHorizontalDpi);
            stringBuilder.append(", verticalDpi: ");
            stringBuilder.append(this.mVerticalDpi);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mId);
            parcel.writeString(this.mLabel);
            parcel.writeInt(this.mHorizontalDpi);
            parcel.writeInt(this.mVerticalDpi);
        }
    }

}

