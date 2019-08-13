/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayAdjustments;
import android.view.DisplayCutout;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public final class DisplayInfo
implements Parcelable {
    public static final Parcelable.Creator<DisplayInfo> CREATOR = new Parcelable.Creator<DisplayInfo>(){

        @Override
        public DisplayInfo createFromParcel(Parcel parcel) {
            return new DisplayInfo(parcel);
        }

        public DisplayInfo[] newArray(int n) {
            return new DisplayInfo[n];
        }
    };
    public DisplayAddress address;
    public int appHeight;
    public long appVsyncOffsetNanos;
    public int appWidth;
    public int colorMode;
    public int defaultModeId;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public DisplayCutout displayCutout;
    public int displayId;
    public int flags;
    public Display.HdrCapabilities hdrCapabilities;
    public int largestNominalAppHeight;
    public int largestNominalAppWidth;
    public int layerStack;
    public int logicalDensityDpi;
    @UnsupportedAppUsage
    public int logicalHeight;
    @UnsupportedAppUsage
    public int logicalWidth;
    public int modeId;
    public String name;
    public int overscanBottom;
    public int overscanLeft;
    public int overscanRight;
    public int overscanTop;
    public String ownerPackageName;
    public int ownerUid;
    public float physicalXDpi;
    public float physicalYDpi;
    public long presentationDeadlineNanos;
    public int removeMode = 0;
    @UnsupportedAppUsage
    public int rotation;
    public int smallestNominalAppHeight;
    public int smallestNominalAppWidth;
    public int state;
    public int[] supportedColorModes = new int[]{0};
    public Display.Mode[] supportedModes = Display.Mode.EMPTY_ARRAY;
    public int type;
    public String uniqueId;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769467L)
    public DisplayInfo() {
    }

    private DisplayInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public DisplayInfo(DisplayInfo displayInfo) {
        this.copyFrom(displayInfo);
    }

    private Display.Mode findMode(int n) {
        Object object;
        for (int i = 0; i < ((Display.Mode[])(object = this.supportedModes)).length; ++i) {
            if (object[i].getModeId() != n) continue;
            return this.supportedModes[i];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to locate mode ");
        ((StringBuilder)object).append(n);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private static String flagsToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n & 2) != 0) {
            stringBuilder.append(", FLAG_SECURE");
        }
        if ((n & 1) != 0) {
            stringBuilder.append(", FLAG_SUPPORTS_PROTECTED_BUFFERS");
        }
        if ((n & 4) != 0) {
            stringBuilder.append(", FLAG_PRIVATE");
        }
        if ((n & 8) != 0) {
            stringBuilder.append(", FLAG_PRESENTATION");
        }
        if ((1073741824 & n) != 0) {
            stringBuilder.append(", FLAG_SCALING_DISABLED");
        }
        if ((n & 16) != 0) {
            stringBuilder.append(", FLAG_ROUND");
        }
        return stringBuilder.toString();
    }

    private void getMetricsWithSize(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration parcelable, int n, int n2) {
        float f;
        int n3;
        displayMetrics.noncompatDensityDpi = n3 = this.logicalDensityDpi;
        displayMetrics.densityDpi = n3;
        displayMetrics.noncompatDensity = f = (float)n3 * 0.00625f;
        displayMetrics.noncompatScaledDensity = f = (displayMetrics.density = f);
        displayMetrics.scaledDensity = f;
        displayMetrics.noncompatXdpi = f = this.physicalXDpi;
        displayMetrics.xdpi = f;
        displayMetrics.noncompatYdpi = f = this.physicalYDpi;
        displayMetrics.ydpi = f;
        if ((parcelable = parcelable != null ? ((Configuration)parcelable).windowConfiguration.getAppBounds() : null) != null) {
            n = ((Rect)parcelable).width();
        }
        if (parcelable != null) {
            n2 = ((Rect)parcelable).height();
        }
        displayMetrics.widthPixels = n;
        displayMetrics.noncompatWidthPixels = n;
        displayMetrics.heightPixels = n2;
        displayMetrics.noncompatHeightPixels = n2;
        if (!compatibilityInfo.equals(CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
            compatibilityInfo.applyToDisplayMetrics(displayMetrics);
        }
    }

    public void copyFrom(DisplayInfo displayInfo) {
        this.layerStack = displayInfo.layerStack;
        this.flags = displayInfo.flags;
        this.type = displayInfo.type;
        this.displayId = displayInfo.displayId;
        this.address = displayInfo.address;
        this.name = displayInfo.name;
        this.uniqueId = displayInfo.uniqueId;
        this.appWidth = displayInfo.appWidth;
        this.appHeight = displayInfo.appHeight;
        this.smallestNominalAppWidth = displayInfo.smallestNominalAppWidth;
        this.smallestNominalAppHeight = displayInfo.smallestNominalAppHeight;
        this.largestNominalAppWidth = displayInfo.largestNominalAppWidth;
        this.largestNominalAppHeight = displayInfo.largestNominalAppHeight;
        this.logicalWidth = displayInfo.logicalWidth;
        this.logicalHeight = displayInfo.logicalHeight;
        this.overscanLeft = displayInfo.overscanLeft;
        this.overscanTop = displayInfo.overscanTop;
        this.overscanRight = displayInfo.overscanRight;
        this.overscanBottom = displayInfo.overscanBottom;
        this.displayCutout = displayInfo.displayCutout;
        this.rotation = displayInfo.rotation;
        this.modeId = displayInfo.modeId;
        this.defaultModeId = displayInfo.defaultModeId;
        Object[] arrobject = displayInfo.supportedModes;
        this.supportedModes = Arrays.copyOf(arrobject, arrobject.length);
        this.colorMode = displayInfo.colorMode;
        arrobject = displayInfo.supportedColorModes;
        this.supportedColorModes = Arrays.copyOf((int[])arrobject, arrobject.length);
        this.hdrCapabilities = displayInfo.hdrCapabilities;
        this.logicalDensityDpi = displayInfo.logicalDensityDpi;
        this.physicalXDpi = displayInfo.physicalXDpi;
        this.physicalYDpi = displayInfo.physicalYDpi;
        this.appVsyncOffsetNanos = displayInfo.appVsyncOffsetNanos;
        this.presentationDeadlineNanos = displayInfo.presentationDeadlineNanos;
        this.state = displayInfo.state;
        this.ownerUid = displayInfo.ownerUid;
        this.ownerPackageName = displayInfo.ownerPackageName;
        this.removeMode = displayInfo.removeMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(DisplayInfo displayInfo) {
        boolean bl = displayInfo != null && this.layerStack == displayInfo.layerStack && this.flags == displayInfo.flags && this.type == displayInfo.type && this.displayId == displayInfo.displayId && Objects.equals(this.address, displayInfo.address) && Objects.equals(this.uniqueId, displayInfo.uniqueId) && this.appWidth == displayInfo.appWidth && this.appHeight == displayInfo.appHeight && this.smallestNominalAppWidth == displayInfo.smallestNominalAppWidth && this.smallestNominalAppHeight == displayInfo.smallestNominalAppHeight && this.largestNominalAppWidth == displayInfo.largestNominalAppWidth && this.largestNominalAppHeight == displayInfo.largestNominalAppHeight && this.logicalWidth == displayInfo.logicalWidth && this.logicalHeight == displayInfo.logicalHeight && this.overscanLeft == displayInfo.overscanLeft && this.overscanTop == displayInfo.overscanTop && this.overscanRight == displayInfo.overscanRight && this.overscanBottom == displayInfo.overscanBottom && Objects.equals(this.displayCutout, displayInfo.displayCutout) && this.rotation == displayInfo.rotation && this.modeId == displayInfo.modeId && this.defaultModeId == displayInfo.defaultModeId && this.colorMode == displayInfo.colorMode && Arrays.equals(this.supportedColorModes, displayInfo.supportedColorModes) && Objects.equals(this.hdrCapabilities, displayInfo.hdrCapabilities) && this.logicalDensityDpi == displayInfo.logicalDensityDpi && this.physicalXDpi == displayInfo.physicalXDpi && this.physicalYDpi == displayInfo.physicalYDpi && this.appVsyncOffsetNanos == displayInfo.appVsyncOffsetNanos && this.presentationDeadlineNanos == displayInfo.presentationDeadlineNanos && this.state == displayInfo.state && this.ownerUid == displayInfo.ownerUid && Objects.equals(this.ownerPackageName, displayInfo.ownerPackageName) && this.removeMode == displayInfo.removeMode;
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DisplayInfo && this.equals((DisplayInfo)object);
        return bl;
    }

    public int findDefaultModeByRefreshRate(float f) {
        Display.Mode[] arrmode = this.supportedModes;
        Display.Mode mode = this.getDefaultMode();
        for (int i = 0; i < arrmode.length; ++i) {
            if (!arrmode[i].matches(mode.getPhysicalWidth(), mode.getPhysicalHeight(), f)) continue;
            return arrmode[i].getModeId();
        }
        return 0;
    }

    public void getAppMetrics(DisplayMetrics displayMetrics) {
        this.getAppMetrics(displayMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
    }

    public void getAppMetrics(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration configuration) {
        this.getMetricsWithSize(displayMetrics, compatibilityInfo, configuration, this.appWidth, this.appHeight);
    }

    public void getAppMetrics(DisplayMetrics displayMetrics, DisplayAdjustments displayAdjustments) {
        this.getMetricsWithSize(displayMetrics, displayAdjustments.getCompatibilityInfo(), displayAdjustments.getConfiguration(), this.appWidth, this.appHeight);
    }

    public Display.Mode getDefaultMode() {
        return this.findMode(this.defaultModeId);
    }

    public float[] getDefaultRefreshRates() {
        int n;
        Object[] arrobject = this.supportedModes;
        Object object = new ArraySet();
        Display.Mode mode = this.getDefaultMode();
        for (n = 0; n < arrobject.length; ++n) {
            Display.Mode mode2 = arrobject[n];
            if (mode2.getPhysicalWidth() != mode.getPhysicalWidth() || mode2.getPhysicalHeight() != mode.getPhysicalHeight()) continue;
            ((ArraySet)object).add(Float.valueOf(mode2.getRefreshRate()));
        }
        arrobject = new float[((ArraySet)object).size()];
        n = 0;
        object = ((ArraySet)object).iterator();
        while (object.hasNext()) {
            arrobject[n] = (Display.Mode)((Float)object.next()).floatValue();
            ++n;
        }
        return arrobject;
    }

    public void getLogicalMetrics(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration configuration) {
        this.getMetricsWithSize(displayMetrics, compatibilityInfo, configuration, this.logicalWidth, this.logicalHeight);
    }

    public Display.Mode getMode() {
        return this.findMode(this.modeId);
    }

    public int getNaturalHeight() {
        int n = this.rotation;
        n = n != 0 && n != 2 ? this.logicalWidth : this.logicalHeight;
        return n;
    }

    public int getNaturalWidth() {
        int n = this.rotation;
        n = n != 0 && n != 2 ? this.logicalHeight : this.logicalWidth;
        return n;
    }

    public boolean hasAccess(int n) {
        return Display.hasAccess(n, this.flags, this.ownerUid, this.displayId);
    }

    public int hashCode() {
        return 0;
    }

    public boolean isHdr() {
        Object object = this.hdrCapabilities;
        object = object != null ? object.getSupportedHdrTypes() : null;
        boolean bl = object != null && ((int[])object).length > 0;
        return bl;
    }

    public boolean isWideColorGamut() {
        for (int n : this.supportedColorModes) {
            if (n != 6 && n <= 7) {
                continue;
            }
            return true;
        }
        return false;
    }

    public void readFromParcel(Parcel parcel) {
        int n;
        this.layerStack = parcel.readInt();
        this.flags = parcel.readInt();
        this.type = parcel.readInt();
        this.displayId = parcel.readInt();
        this.address = (DisplayAddress)parcel.readParcelable(null);
        this.name = parcel.readString();
        this.appWidth = parcel.readInt();
        this.appHeight = parcel.readInt();
        this.smallestNominalAppWidth = parcel.readInt();
        this.smallestNominalAppHeight = parcel.readInt();
        this.largestNominalAppWidth = parcel.readInt();
        this.largestNominalAppHeight = parcel.readInt();
        this.logicalWidth = parcel.readInt();
        this.logicalHeight = parcel.readInt();
        this.overscanLeft = parcel.readInt();
        this.overscanTop = parcel.readInt();
        this.overscanRight = parcel.readInt();
        this.overscanBottom = parcel.readInt();
        this.displayCutout = DisplayCutout.ParcelableWrapper.readCutoutFromParcel(parcel);
        this.rotation = parcel.readInt();
        this.modeId = parcel.readInt();
        this.defaultModeId = parcel.readInt();
        int n2 = parcel.readInt();
        this.supportedModes = new Display.Mode[n2];
        for (n = 0; n < n2; ++n) {
            this.supportedModes[n] = Display.Mode.CREATOR.createFromParcel(parcel);
        }
        this.colorMode = parcel.readInt();
        n2 = parcel.readInt();
        this.supportedColorModes = new int[n2];
        for (n = 0; n < n2; ++n) {
            this.supportedColorModes[n] = parcel.readInt();
        }
        this.hdrCapabilities = (Display.HdrCapabilities)parcel.readParcelable(null);
        this.logicalDensityDpi = parcel.readInt();
        this.physicalXDpi = parcel.readFloat();
        this.physicalYDpi = parcel.readFloat();
        this.appVsyncOffsetNanos = parcel.readLong();
        this.presentationDeadlineNanos = parcel.readLong();
        this.state = parcel.readInt();
        this.ownerUid = parcel.readInt();
        this.ownerPackageName = parcel.readString();
        this.uniqueId = parcel.readString();
        this.removeMode = parcel.readInt();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DisplayInfo{\"");
        stringBuilder.append(this.name);
        stringBuilder.append(", displayId ");
        stringBuilder.append(this.displayId);
        stringBuilder.append("\", uniqueId \"");
        stringBuilder.append(this.uniqueId);
        stringBuilder.append("\", app ");
        stringBuilder.append(this.appWidth);
        stringBuilder.append(" x ");
        stringBuilder.append(this.appHeight);
        stringBuilder.append(", real ");
        stringBuilder.append(this.logicalWidth);
        stringBuilder.append(" x ");
        stringBuilder.append(this.logicalHeight);
        if (this.overscanLeft != 0 || this.overscanTop != 0 || this.overscanRight != 0 || this.overscanBottom != 0) {
            stringBuilder.append(", overscan (");
            stringBuilder.append(this.overscanLeft);
            stringBuilder.append(",");
            stringBuilder.append(this.overscanTop);
            stringBuilder.append(",");
            stringBuilder.append(this.overscanRight);
            stringBuilder.append(",");
            stringBuilder.append(this.overscanBottom);
            stringBuilder.append(")");
        }
        stringBuilder.append(", largest app ");
        stringBuilder.append(this.largestNominalAppWidth);
        stringBuilder.append(" x ");
        stringBuilder.append(this.largestNominalAppHeight);
        stringBuilder.append(", smallest app ");
        stringBuilder.append(this.smallestNominalAppWidth);
        stringBuilder.append(" x ");
        stringBuilder.append(this.smallestNominalAppHeight);
        stringBuilder.append(", mode ");
        stringBuilder.append(this.modeId);
        stringBuilder.append(", defaultMode ");
        stringBuilder.append(this.defaultModeId);
        stringBuilder.append(", modes ");
        stringBuilder.append(Arrays.toString(this.supportedModes));
        stringBuilder.append(", colorMode ");
        stringBuilder.append(this.colorMode);
        stringBuilder.append(", supportedColorModes ");
        stringBuilder.append(Arrays.toString(this.supportedColorModes));
        stringBuilder.append(", hdrCapabilities ");
        stringBuilder.append(this.hdrCapabilities);
        stringBuilder.append(", rotation ");
        stringBuilder.append(this.rotation);
        stringBuilder.append(", density ");
        stringBuilder.append(this.logicalDensityDpi);
        stringBuilder.append(" (");
        stringBuilder.append(this.physicalXDpi);
        stringBuilder.append(" x ");
        stringBuilder.append(this.physicalYDpi);
        stringBuilder.append(") dpi, layerStack ");
        stringBuilder.append(this.layerStack);
        stringBuilder.append(", appVsyncOff ");
        stringBuilder.append(this.appVsyncOffsetNanos);
        stringBuilder.append(", presDeadline ");
        stringBuilder.append(this.presentationDeadlineNanos);
        stringBuilder.append(", type ");
        stringBuilder.append(Display.typeToString(this.type));
        if (this.address != null) {
            stringBuilder.append(", address ");
            stringBuilder.append(this.address);
        }
        stringBuilder.append(", state ");
        stringBuilder.append(Display.stateToString(this.state));
        if (this.ownerUid != 0 || this.ownerPackageName != null) {
            stringBuilder.append(", owner ");
            stringBuilder.append(this.ownerPackageName);
            stringBuilder.append(" (uid ");
            stringBuilder.append(this.ownerUid);
            stringBuilder.append(")");
        }
        stringBuilder.append(DisplayInfo.flagsToString(this.flags));
        stringBuilder.append(", removeMode ");
        stringBuilder.append(this.removeMode);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Object[] arrobject;
        int n2;
        parcel.writeInt(this.layerStack);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.type);
        parcel.writeInt(this.displayId);
        parcel.writeParcelable(this.address, n);
        parcel.writeString(this.name);
        parcel.writeInt(this.appWidth);
        parcel.writeInt(this.appHeight);
        parcel.writeInt(this.smallestNominalAppWidth);
        parcel.writeInt(this.smallestNominalAppHeight);
        parcel.writeInt(this.largestNominalAppWidth);
        parcel.writeInt(this.largestNominalAppHeight);
        parcel.writeInt(this.logicalWidth);
        parcel.writeInt(this.logicalHeight);
        parcel.writeInt(this.overscanLeft);
        parcel.writeInt(this.overscanTop);
        parcel.writeInt(this.overscanRight);
        parcel.writeInt(this.overscanBottom);
        DisplayCutout.ParcelableWrapper.writeCutoutToParcel(this.displayCutout, parcel, n);
        parcel.writeInt(this.rotation);
        parcel.writeInt(this.modeId);
        parcel.writeInt(this.defaultModeId);
        parcel.writeInt(this.supportedModes.length);
        for (n2 = 0; n2 < (arrobject = this.supportedModes).length; ++n2) {
            arrobject[n2].writeToParcel(parcel, n);
        }
        parcel.writeInt(this.colorMode);
        parcel.writeInt(this.supportedColorModes.length);
        for (n2 = 0; n2 < (arrobject = this.supportedColorModes).length; ++n2) {
            parcel.writeInt((int)arrobject[n2]);
        }
        parcel.writeParcelable(this.hdrCapabilities, n);
        parcel.writeInt(this.logicalDensityDpi);
        parcel.writeFloat(this.physicalXDpi);
        parcel.writeFloat(this.physicalYDpi);
        parcel.writeLong(this.appVsyncOffsetNanos);
        parcel.writeLong(this.presentationDeadlineNanos);
        parcel.writeInt(this.state);
        parcel.writeInt(this.ownerUid);
        parcel.writeString(this.ownerPackageName);
        parcel.writeString(this.uniqueId);
        parcel.writeInt(this.removeMode);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, this.logicalWidth);
        protoOutputStream.write(1120986464258L, this.logicalHeight);
        protoOutputStream.write(1120986464259L, this.appWidth);
        protoOutputStream.write(1120986464260L, this.appHeight);
        protoOutputStream.write(1138166333445L, this.name);
        protoOutputStream.end(l);
    }

}

