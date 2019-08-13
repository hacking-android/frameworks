/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.print.-$
 *  android.print.-$$Lambda
 *  android.print.-$$Lambda$PrinterCapabilitiesInfo
 *  android.print.-$$Lambda$PrinterCapabilitiesInfo$2mJhwjGC7Dgi0vwDsnG83V2s6sE
 *  android.print.-$$Lambda$PrinterCapabilitiesInfo$Builder
 *  android.print.-$$Lambda$PrinterCapabilitiesInfo$Builder$dbsSt8pZfd6hqZ6hGCnpzhPK6Uk
 *  android.print.-$$Lambda$PrinterCapabilitiesInfo$Builder$gsgXbNHGWpWENdPzemgHcCY8HnE
 *  android.print.-$$Lambda$PrinterCapabilitiesInfo$TL1SYHyXTbqj2Nseol9bDJQOn3U
 */
package android.print;

import android.os.Parcel;
import android.os.Parcelable;
import android.print.-$;
import android.print.PrintAttributes;
import android.print.PrinterId;
import android.print._$$Lambda$PrinterCapabilitiesInfo$2mJhwjGC7Dgi0vwDsnG83V2s6sE;
import android.print._$$Lambda$PrinterCapabilitiesInfo$Builder$dbsSt8pZfd6hqZ6hGCnpzhPK6Uk;
import android.print._$$Lambda$PrinterCapabilitiesInfo$Builder$gsgXbNHGWpWENdPzemgHcCY8HnE;
import android.print._$$Lambda$PrinterCapabilitiesInfo$TL1SYHyXTbqj2Nseol9bDJQOn3U;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;

public final class PrinterCapabilitiesInfo
implements Parcelable {
    public static final Parcelable.Creator<PrinterCapabilitiesInfo> CREATOR;
    private static final PrintAttributes.Margins DEFAULT_MARGINS;
    public static final int DEFAULT_UNDEFINED = -1;
    private static final int PROPERTY_COLOR_MODE = 2;
    private static final int PROPERTY_COUNT = 4;
    private static final int PROPERTY_DUPLEX_MODE = 3;
    private static final int PROPERTY_MEDIA_SIZE = 0;
    private static final int PROPERTY_RESOLUTION = 1;
    private int mColorModes;
    private final int[] mDefaults = new int[4];
    private int mDuplexModes;
    private List<PrintAttributes.MediaSize> mMediaSizes;
    private PrintAttributes.Margins mMinMargins = DEFAULT_MARGINS;
    private List<PrintAttributes.Resolution> mResolutions;

    static {
        DEFAULT_MARGINS = new PrintAttributes.Margins(0, 0, 0, 0);
        CREATOR = new Parcelable.Creator<PrinterCapabilitiesInfo>(){

            @Override
            public PrinterCapabilitiesInfo createFromParcel(Parcel parcel) {
                return new PrinterCapabilitiesInfo(parcel);
            }

            public PrinterCapabilitiesInfo[] newArray(int n) {
                return new PrinterCapabilitiesInfo[n];
            }
        };
    }

    public PrinterCapabilitiesInfo() {
        Arrays.fill(this.mDefaults, -1);
    }

    private PrinterCapabilitiesInfo(Parcel arrn) {
        this.mMinMargins = Preconditions.checkNotNull(this.readMargins((Parcel)arrn));
        this.readMediaSizes((Parcel)arrn);
        this.readResolutions((Parcel)arrn);
        this.mColorModes = arrn.readInt();
        PrinterCapabilitiesInfo.enforceValidMask(this.mColorModes, (IntConsumer)_$$Lambda$PrinterCapabilitiesInfo$2mJhwjGC7Dgi0vwDsnG83V2s6sE.INSTANCE);
        this.mDuplexModes = arrn.readInt();
        PrinterCapabilitiesInfo.enforceValidMask(this.mDuplexModes, (IntConsumer)_$$Lambda$PrinterCapabilitiesInfo$TL1SYHyXTbqj2Nseol9bDJQOn3U.INSTANCE);
        this.readDefaults((Parcel)arrn);
        int n = this.mMediaSizes.size();
        arrn = this.mDefaults;
        boolean bl = false;
        boolean bl2 = n > arrn[0];
        Preconditions.checkArgument(bl2);
        bl2 = bl;
        if (this.mResolutions.size() > this.mDefaults[1]) {
            bl2 = true;
        }
        Preconditions.checkArgument(bl2);
    }

    public PrinterCapabilitiesInfo(PrinterCapabilitiesInfo printerCapabilitiesInfo) {
        this.copyFrom(printerCapabilitiesInfo);
    }

    private String colorModesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n = this.mColorModes;
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n2;
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(PrintAttributes.colorModeToString(n2));
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private String duplexModesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n = this.mDuplexModes;
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n2;
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(PrintAttributes.duplexModeToString(n2));
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static void enforceValidMask(int n, IntConsumer intConsumer) {
        while (n > 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n2;
            intConsumer.accept(n2);
        }
    }

    static /* synthetic */ void lambda$new$0(int n) {
        PrintAttributes.enforceValidColorMode(n);
    }

    static /* synthetic */ void lambda$new$1(int n) {
        PrintAttributes.enforceValidDuplexMode(n);
    }

    private void readDefaults(Parcel parcel) {
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            this.mDefaults[i] = parcel.readInt();
        }
    }

    private PrintAttributes.Margins readMargins(Parcel object) {
        object = ((Parcel)object).readInt() == 1 ? PrintAttributes.Margins.createFromParcel((Parcel)object) : null;
        return object;
    }

    private void readMediaSizes(Parcel parcel) {
        int n = parcel.readInt();
        if (n > 0 && this.mMediaSizes == null) {
            this.mMediaSizes = new ArrayList<PrintAttributes.MediaSize>();
        }
        for (int i = 0; i < n; ++i) {
            this.mMediaSizes.add(PrintAttributes.MediaSize.createFromParcel(parcel));
        }
    }

    private void readResolutions(Parcel parcel) {
        int n = parcel.readInt();
        if (n > 0 && this.mResolutions == null) {
            this.mResolutions = new ArrayList<PrintAttributes.Resolution>();
        }
        for (int i = 0; i < n; ++i) {
            this.mResolutions.add(PrintAttributes.Resolution.createFromParcel(parcel));
        }
    }

    private void writeDefaults(Parcel parcel) {
        int n = this.mDefaults.length;
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            parcel.writeInt(this.mDefaults[i]);
        }
    }

    private void writeMargins(PrintAttributes.Margins margins, Parcel parcel) {
        if (margins == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            margins.writeToParcel(parcel);
        }
    }

    private void writeMediaSizes(Parcel parcel) {
        List<PrintAttributes.MediaSize> list = this.mMediaSizes;
        if (list == null) {
            parcel.writeInt(0);
            return;
        }
        int n = list.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.mMediaSizes.get(i).writeToParcel(parcel);
        }
    }

    private void writeResolutions(Parcel parcel) {
        List<PrintAttributes.Resolution> list = this.mResolutions;
        if (list == null) {
            parcel.writeInt(0);
            return;
        }
        int n = list.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.mResolutions.get(i).writeToParcel(parcel);
        }
    }

    public void copyFrom(PrinterCapabilitiesInfo printerCapabilitiesInfo) {
        List<Object> list;
        if (this == printerCapabilitiesInfo) {
            return;
        }
        this.mMinMargins = printerCapabilitiesInfo.mMinMargins;
        List<Object> list2 = printerCapabilitiesInfo.mMediaSizes;
        if (list2 != null) {
            list = this.mMediaSizes;
            if (list != null) {
                list.clear();
                this.mMediaSizes.addAll(printerCapabilitiesInfo.mMediaSizes);
            } else {
                this.mMediaSizes = new ArrayList<PrintAttributes.MediaSize>(list2);
            }
        } else {
            this.mMediaSizes = null;
        }
        list2 = printerCapabilitiesInfo.mResolutions;
        if (list2 != null) {
            list = this.mResolutions;
            if (list != null) {
                list.clear();
                this.mResolutions.addAll(printerCapabilitiesInfo.mResolutions);
            } else {
                this.mResolutions = new ArrayList<PrintAttributes.MediaSize>(list2);
            }
        } else {
            this.mResolutions = null;
        }
        this.mColorModes = printerCapabilitiesInfo.mColorModes;
        this.mDuplexModes = printerCapabilitiesInfo.mDuplexModes;
        for (int this.mDefaults[var5_5] : printerCapabilitiesInfo.mDefaults) {
        }
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
        object = (PrinterCapabilitiesInfo)object;
        List<Object> list = this.mMinMargins;
        if (list == null ? ((PrinterCapabilitiesInfo)object).mMinMargins != null : !((PrintAttributes.Margins)((Object)list)).equals(((PrinterCapabilitiesInfo)object).mMinMargins)) {
            return false;
        }
        list = this.mMediaSizes;
        if (list == null ? ((PrinterCapabilitiesInfo)object).mMediaSizes != null : !list.equals(((PrinterCapabilitiesInfo)object).mMediaSizes)) {
            return false;
        }
        list = this.mResolutions;
        if (list == null ? ((PrinterCapabilitiesInfo)object).mResolutions != null : !list.equals(((PrinterCapabilitiesInfo)object).mResolutions)) {
            return false;
        }
        if (this.mColorModes != ((PrinterCapabilitiesInfo)object).mColorModes) {
            return false;
        }
        if (this.mDuplexModes != ((PrinterCapabilitiesInfo)object).mDuplexModes) {
            return false;
        }
        return Arrays.equals(this.mDefaults, ((PrinterCapabilitiesInfo)object).mDefaults);
    }

    public int getColorModes() {
        return this.mColorModes;
    }

    public PrintAttributes getDefaults() {
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMinMargins(this.mMinMargins);
        int n = this.mDefaults[0];
        if (n >= 0) {
            builder.setMediaSize(this.mMediaSizes.get(n));
        }
        if ((n = this.mDefaults[1]) >= 0) {
            builder.setResolution(this.mResolutions.get(n));
        }
        if ((n = this.mDefaults[2]) > 0) {
            builder.setColorMode(n);
        }
        if ((n = this.mDefaults[3]) > 0) {
            builder.setDuplexMode(n);
        }
        return builder.build();
    }

    public int getDuplexModes() {
        return this.mDuplexModes;
    }

    public List<PrintAttributes.MediaSize> getMediaSizes() {
        return Collections.unmodifiableList(this.mMediaSizes);
    }

    public PrintAttributes.Margins getMinMargins() {
        return this.mMinMargins;
    }

    public List<PrintAttributes.Resolution> getResolutions() {
        return Collections.unmodifiableList(this.mResolutions);
    }

    public int hashCode() {
        List<PrintAttributes.Resolution> list = this.mMinMargins;
        int n = 0;
        int n2 = list == null ? 0 : ((PrintAttributes.Margins)((Object)list)).hashCode();
        list = this.mMediaSizes;
        int n3 = list == null ? 0 : list.hashCode();
        list = this.mResolutions;
        if (list != null) {
            n = list.hashCode();
        }
        return (((((1 * 31 + n2) * 31 + n3) * 31 + n) * 31 + this.mColorModes) * 31 + this.mDuplexModes) * 31 + Arrays.hashCode(this.mDefaults);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrinterInfo{");
        stringBuilder.append("minMargins=");
        stringBuilder.append(this.mMinMargins);
        stringBuilder.append(", mediaSizes=");
        stringBuilder.append(this.mMediaSizes);
        stringBuilder.append(", resolutions=");
        stringBuilder.append(this.mResolutions);
        stringBuilder.append(", colorModes=");
        stringBuilder.append(this.colorModesToString());
        stringBuilder.append(", duplexModes=");
        stringBuilder.append(this.duplexModesToString());
        stringBuilder.append("\"}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeMargins(this.mMinMargins, parcel);
        this.writeMediaSizes(parcel);
        this.writeResolutions(parcel);
        parcel.writeInt(this.mColorModes);
        parcel.writeInt(this.mDuplexModes);
        this.writeDefaults(parcel);
    }

    public static final class Builder {
        private final PrinterCapabilitiesInfo mPrototype;

        public Builder(PrinterId printerId) {
            if (printerId != null) {
                this.mPrototype = new PrinterCapabilitiesInfo();
                return;
            }
            throw new IllegalArgumentException("printerId cannot be null.");
        }

        static /* synthetic */ void lambda$setColorModes$0(int n) {
            PrintAttributes.enforceValidColorMode(n);
        }

        static /* synthetic */ void lambda$setDuplexModes$1(int n) {
            PrintAttributes.enforceValidDuplexMode(n);
        }

        private void throwIfDefaultAlreadySpecified(int n) {
            if (this.mPrototype.mDefaults[n] == -1) {
                return;
            }
            throw new IllegalArgumentException("Default already specified.");
        }

        public Builder addMediaSize(PrintAttributes.MediaSize mediaSize, boolean bl) {
            if (this.mPrototype.mMediaSizes == null) {
                this.mPrototype.mMediaSizes = new ArrayList();
            }
            int n = this.mPrototype.mMediaSizes.size();
            this.mPrototype.mMediaSizes.add(mediaSize);
            if (bl) {
                this.throwIfDefaultAlreadySpecified(0);
                PrinterCapabilitiesInfo.access$100((PrinterCapabilitiesInfo)this.mPrototype)[0] = n;
            }
            return this;
        }

        public Builder addResolution(PrintAttributes.Resolution resolution, boolean bl) {
            if (this.mPrototype.mResolutions == null) {
                this.mPrototype.mResolutions = new ArrayList();
            }
            int n = this.mPrototype.mResolutions.size();
            this.mPrototype.mResolutions.add(resolution);
            if (bl) {
                this.throwIfDefaultAlreadySpecified(1);
                PrinterCapabilitiesInfo.access$100((PrinterCapabilitiesInfo)this.mPrototype)[1] = n;
            }
            return this;
        }

        public PrinterCapabilitiesInfo build() {
            if (this.mPrototype.mMediaSizes != null && !this.mPrototype.mMediaSizes.isEmpty()) {
                if (this.mPrototype.mDefaults[0] != -1) {
                    if (this.mPrototype.mResolutions != null && !this.mPrototype.mResolutions.isEmpty()) {
                        if (this.mPrototype.mDefaults[1] != -1) {
                            if (this.mPrototype.mColorModes != 0) {
                                if (this.mPrototype.mDefaults[2] != -1) {
                                    if (this.mPrototype.mDuplexModes == 0) {
                                        this.setDuplexModes(1, 1);
                                    }
                                    if (this.mPrototype.mMinMargins != null) {
                                        return this.mPrototype;
                                    }
                                    throw new IllegalArgumentException("margins cannot be null");
                                }
                                throw new IllegalStateException("No default color mode specified.");
                            }
                            throw new IllegalStateException("No color mode specified.");
                        }
                        throw new IllegalStateException("No default resolution specified.");
                    }
                    throw new IllegalStateException("No resolution specified.");
                }
                throw new IllegalStateException("No default media size specified.");
            }
            throw new IllegalStateException("No media size specified.");
        }

        public Builder setColorModes(int n, int n2) {
            PrinterCapabilitiesInfo.enforceValidMask(n, (IntConsumer)_$$Lambda$PrinterCapabilitiesInfo$Builder$dbsSt8pZfd6hqZ6hGCnpzhPK6Uk.INSTANCE);
            PrintAttributes.enforceValidColorMode(n2);
            this.mPrototype.mColorModes = n;
            PrinterCapabilitiesInfo.access$100((PrinterCapabilitiesInfo)this.mPrototype)[2] = n2;
            return this;
        }

        public Builder setDuplexModes(int n, int n2) {
            PrinterCapabilitiesInfo.enforceValidMask(n, (IntConsumer)_$$Lambda$PrinterCapabilitiesInfo$Builder$gsgXbNHGWpWENdPzemgHcCY8HnE.INSTANCE);
            PrintAttributes.enforceValidDuplexMode(n2);
            this.mPrototype.mDuplexModes = n;
            PrinterCapabilitiesInfo.access$100((PrinterCapabilitiesInfo)this.mPrototype)[3] = n2;
            return this;
        }

        public Builder setMinMargins(PrintAttributes.Margins margins) {
            if (margins != null) {
                this.mPrototype.mMinMargins = margins;
                return this;
            }
            throw new IllegalArgumentException("margins cannot be null");
        }
    }

}

