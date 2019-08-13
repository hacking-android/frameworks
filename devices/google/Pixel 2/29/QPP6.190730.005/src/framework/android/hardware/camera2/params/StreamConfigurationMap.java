/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.renderscript.Allocation;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public final class StreamConfigurationMap {
    private static final long DURATION_20FPS_NS = 50000000L;
    private static final int DURATION_MIN_FRAME = 0;
    private static final int DURATION_STALL = 1;
    private static final int HAL_DATASPACE_DEPTH = 4096;
    private static final int HAL_DATASPACE_DYNAMIC_DEPTH = 4098;
    private static final int HAL_DATASPACE_HEIF = 4099;
    private static final int HAL_DATASPACE_RANGE_SHIFT = 27;
    private static final int HAL_DATASPACE_STANDARD_SHIFT = 16;
    private static final int HAL_DATASPACE_TRANSFER_SHIFT = 22;
    private static final int HAL_DATASPACE_UNKNOWN = 0;
    private static final int HAL_DATASPACE_V0_JFIF = 146931712;
    private static final int HAL_PIXEL_FORMAT_BLOB = 33;
    private static final int HAL_PIXEL_FORMAT_IMPLEMENTATION_DEFINED = 34;
    private static final int HAL_PIXEL_FORMAT_RAW10 = 37;
    private static final int HAL_PIXEL_FORMAT_RAW12 = 38;
    private static final int HAL_PIXEL_FORMAT_RAW16 = 32;
    private static final int HAL_PIXEL_FORMAT_RAW_OPAQUE = 36;
    private static final int HAL_PIXEL_FORMAT_Y16 = 540422489;
    private static final int HAL_PIXEL_FORMAT_YCbCr_420_888 = 35;
    private static final String TAG = "StreamConfigurationMap";
    private final SparseIntArray mAllOutputFormats = new SparseIntArray();
    private final StreamConfiguration[] mConfigurations;
    private final StreamConfiguration[] mDepthConfigurations;
    private final StreamConfigurationDuration[] mDepthMinFrameDurations;
    private final SparseIntArray mDepthOutputFormats = new SparseIntArray();
    private final StreamConfigurationDuration[] mDepthStallDurations;
    private final StreamConfiguration[] mDynamicDepthConfigurations;
    private final StreamConfigurationDuration[] mDynamicDepthMinFrameDurations;
    private final SparseIntArray mDynamicDepthOutputFormats = new SparseIntArray();
    private final StreamConfigurationDuration[] mDynamicDepthStallDurations;
    private final StreamConfiguration[] mHeicConfigurations;
    private final StreamConfigurationDuration[] mHeicMinFrameDurations;
    private final SparseIntArray mHeicOutputFormats = new SparseIntArray();
    private final StreamConfigurationDuration[] mHeicStallDurations;
    private final SparseIntArray mHighResOutputFormats = new SparseIntArray();
    private final HighSpeedVideoConfiguration[] mHighSpeedVideoConfigurations;
    private final HashMap<Range<Integer>, Integer> mHighSpeedVideoFpsRangeMap = new HashMap();
    private final HashMap<Size, Integer> mHighSpeedVideoSizeMap = new HashMap();
    private final SparseIntArray mInputFormats = new SparseIntArray();
    private final ReprocessFormatsMap mInputOutputFormatsMap;
    private final boolean mListHighResolution;
    private final StreamConfigurationDuration[] mMinFrameDurations;
    private final SparseIntArray mOutputFormats = new SparseIntArray();
    private final StreamConfigurationDuration[] mStallDurations;

    public StreamConfigurationMap(StreamConfiguration[] arrstreamConfiguration, StreamConfigurationDuration[] arrstreamConfigurationDuration, StreamConfigurationDuration[] arrstreamConfigurationDuration2, StreamConfiguration[] arrstreamConfiguration2, StreamConfigurationDuration[] arrstreamConfigurationDuration3, StreamConfigurationDuration[] arrstreamConfigurationDuration4, StreamConfiguration[] arrstreamConfiguration3, StreamConfigurationDuration[] arrstreamConfigurationDuration5, StreamConfigurationDuration[] arrstreamConfigurationDuration6, StreamConfiguration[] arrstreamConfiguration4, StreamConfigurationDuration[] arrstreamConfigurationDuration7, StreamConfigurationDuration[] arrstreamConfigurationDuration8, HighSpeedVideoConfiguration[] arrhighSpeedVideoConfiguration, ReprocessFormatsMap reprocessFormatsMap, boolean bl) {
        this(arrstreamConfiguration, arrstreamConfigurationDuration, arrstreamConfigurationDuration2, arrstreamConfiguration2, arrstreamConfigurationDuration3, arrstreamConfigurationDuration4, arrstreamConfiguration3, arrstreamConfigurationDuration5, arrstreamConfigurationDuration6, arrstreamConfiguration4, arrstreamConfigurationDuration7, arrstreamConfigurationDuration8, arrhighSpeedVideoConfiguration, reprocessFormatsMap, bl, true);
    }

    /*
     * WARNING - void declaration
     */
    public StreamConfigurationMap(StreamConfiguration[] arrobject, StreamConfigurationDuration[] object5, StreamConfigurationDuration[] object22, StreamConfiguration[] object3, StreamConfigurationDuration[] object4, StreamConfigurationDuration[] arrstreamConfigurationDuration, StreamConfiguration[] arrstreamConfiguration, StreamConfigurationDuration[] arrstreamConfigurationDuration2, StreamConfigurationDuration[] arrstreamConfigurationDuration3, StreamConfiguration[] arrstreamConfiguration2, StreamConfigurationDuration[] arrstreamConfigurationDuration4, StreamConfigurationDuration[] arrstreamConfigurationDuration5, HighSpeedVideoConfiguration[] arrhighSpeedVideoConfiguration, ReprocessFormatsMap reprocessFormatsMap, boolean bl, boolean bl2) {
        Object object;
        void var7_36;
        void var10_39;
        void var16_45;
        Object object2;
        void var14_43;
        void var15_44;
        void var13_42;
        if (arrobject == null && object == null && var10_39 == null) {
            throw new NullPointerException("At least one of color/depth/heic configurations must not be null");
        }
        if (arrobject == null) {
            this.mConfigurations = new StreamConfiguration[0];
            this.mMinFrameDurations = new StreamConfigurationDuration[0];
            this.mStallDurations = new StreamConfigurationDuration[0];
        } else {
            void var3_23;
            this.mConfigurations = (StreamConfiguration[])Preconditions.checkArrayElementsNotNull(arrobject, "configurations");
            this.mMinFrameDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(object5, "minFrameDurations");
            this.mStallDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(var3_23, "stallDurations");
        }
        this.mListHighResolution = var15_44;
        if (object == null) {
            this.mDepthConfigurations = new StreamConfiguration[0];
            this.mDepthMinFrameDurations = new StreamConfigurationDuration[0];
            this.mDepthStallDurations = new StreamConfigurationDuration[0];
        } else {
            void var6_35;
            this.mDepthConfigurations = (StreamConfiguration[])Preconditions.checkArrayElementsNotNull(object, "depthConfigurations");
            this.mDepthMinFrameDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(object2, "depthMinFrameDurations");
            this.mDepthStallDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(var6_35, "depthStallDurations");
        }
        if (var7_36 == null) {
            this.mDynamicDepthConfigurations = new StreamConfiguration[0];
            this.mDynamicDepthMinFrameDurations = new StreamConfigurationDuration[0];
            this.mDynamicDepthStallDurations = new StreamConfigurationDuration[0];
        } else {
            void var9_38;
            void var8_37;
            this.mDynamicDepthConfigurations = (StreamConfiguration[])Preconditions.checkArrayElementsNotNull(var7_36, "dynamicDepthConfigurations");
            this.mDynamicDepthMinFrameDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(var8_37, "dynamicDepthMinFrameDurations");
            this.mDynamicDepthStallDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(var9_38, "dynamicDepthStallDurations");
        }
        if (var10_39 == null) {
            this.mHeicConfigurations = new StreamConfiguration[0];
            this.mHeicMinFrameDurations = new StreamConfigurationDuration[0];
            this.mHeicStallDurations = new StreamConfigurationDuration[0];
        } else {
            void var11_40;
            void var12_41;
            this.mHeicConfigurations = (StreamConfiguration[])Preconditions.checkArrayElementsNotNull(var10_39, "heicConfigurations");
            this.mHeicMinFrameDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(var11_40, "heicMinFrameDurations");
            this.mHeicStallDurations = (StreamConfigurationDuration[])Preconditions.checkArrayElementsNotNull(var12_41, "heicStallDurations");
        }
        this.mHighSpeedVideoConfigurations = var13_42 == null ? new HighSpeedVideoConfiguration[0] : (HighSpeedVideoConfiguration[])Preconditions.checkArrayElementsNotNull(var13_42, "highSpeedVideoConfigurations");
        StreamConfiguration[] arrstreamConfiguration3 = this.mConfigurations;
        int n = arrstreamConfiguration3.length;
        for (int i = 0; i < n; ++i) {
            void var2_11;
            object = arrstreamConfiguration3[i];
            int n2 = ((StreamConfiguration)object).getFormat();
            if (((StreamConfiguration)object).isOutput()) {
                long l;
                SparseIntArray sparseIntArray = this.mAllOutputFormats;
                sparseIntArray.put(n2, sparseIntArray.get(n2) + 1);
                long l2 = l = 0L;
                if (this.mListHighResolution) {
                    StreamConfigurationDuration[] arrstreamConfigurationDuration6 = this.mMinFrameDurations;
                    int n3 = arrstreamConfigurationDuration6.length;
                    int n4 = 0;
                    do {
                        l2 = l;
                        if (n4 >= n3) break;
                        object2 = arrstreamConfigurationDuration6[n4];
                        if (((StreamConfigurationDuration)object2).getFormat() == n2 && ((StreamConfigurationDuration)object2).getWidth() == ((StreamConfiguration)object).getSize().getWidth() && ((StreamConfigurationDuration)object2).getHeight() == ((StreamConfiguration)object).getSize().getHeight()) {
                            l2 = ((StreamConfigurationDuration)object2).getDuration();
                            break;
                        }
                        ++n4;
                    } while (true);
                }
                if (l2 <= 50000000L) {
                    SparseIntArray sparseIntArray2 = this.mOutputFormats;
                } else {
                    SparseIntArray sparseIntArray3 = this.mHighResOutputFormats;
                }
            } else {
                SparseIntArray sparseIntArray = this.mInputFormats;
            }
            var2_11.put(n2, var2_11.get(n2) + 1);
        }
        for (StreamConfiguration streamConfiguration : this.mDepthConfigurations) {
            if (!streamConfiguration.isOutput()) continue;
            this.mDepthOutputFormats.put(streamConfiguration.getFormat(), this.mDepthOutputFormats.get(streamConfiguration.getFormat()) + 1);
        }
        for (StreamConfiguration streamConfiguration : this.mDynamicDepthConfigurations) {
            if (!streamConfiguration.isOutput()) continue;
            this.mDynamicDepthOutputFormats.put(streamConfiguration.getFormat(), this.mDynamicDepthOutputFormats.get(streamConfiguration.getFormat()) + 1);
        }
        for (StreamConfiguration streamConfiguration : this.mHeicConfigurations) {
            if (!streamConfiguration.isOutput()) continue;
            this.mHeicOutputFormats.put(streamConfiguration.getFormat(), this.mHeicOutputFormats.get(streamConfiguration.getFormat()) + 1);
        }
        if (arrobject != null && var16_45 != false && this.mOutputFormats.indexOfKey(34) < 0) {
            throw new AssertionError((Object)"At least one stream configuration for IMPLEMENTATION_DEFINED must exist");
        }
        for (HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
            void var2_22;
            void var2_19;
            Integer n5;
            object2 = highSpeedVideoConfiguration.getSize();
            object = highSpeedVideoConfiguration.getFpsRange();
            Integer n6 = n5 = this.mHighSpeedVideoSizeMap.get(object2);
            if (n5 == null) {
                Integer n7 = 0;
            }
            this.mHighSpeedVideoSizeMap.put((Size)object2, var2_19.intValue() + 1);
            Integer n8 = this.mHighSpeedVideoFpsRangeMap.get(object);
            if (n8 == null) {
                Integer n9 = 0;
            }
            this.mHighSpeedVideoFpsRangeMap.put((Range<Integer>)object, var2_22.intValue() + 1);
        }
        this.mInputOutputFormatsMap = var14_43;
    }

    private void appendHighResOutputsString(StringBuilder stringBuilder) {
        stringBuilder.append("HighResolutionOutputs(");
        for (int n : this.getOutputFormats()) {
            Size[] arrsize = this.getHighResolutionOutputSizes(n);
            if (arrsize == null) continue;
            for (Size size : arrsize) {
                long l = this.getOutputMinFrameDuration(n, size);
                long l2 = this.getOutputStallDuration(n, size);
                stringBuilder.append(String.format("[w:%d, h:%d, format:%s(%d), min_duration:%d, stall:%d], ", size.getWidth(), size.getHeight(), this.formatToString(n), n, l, l2));
            }
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(")");
    }

    private void appendHighSpeedVideoConfigurationsString(StringBuilder stringBuilder) {
        stringBuilder.append("HighSpeedVideoConfigurations(");
        for (Size size : this.getHighSpeedVideoSizes()) {
            for (Range<Integer> range : this.getHighSpeedVideoFpsRangesFor(size)) {
                stringBuilder.append(String.format("[w:%d, h:%d, min_fps:%d, max_fps:%d], ", size.getWidth(), size.getHeight(), range.getLower(), range.getUpper()));
            }
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(")");
    }

    private void appendInputsString(StringBuilder stringBuilder) {
        stringBuilder.append("Inputs(");
        for (int n : this.getInputFormats()) {
            for (Size size : this.getInputSizes(n)) {
                stringBuilder.append(String.format("[w:%d, h:%d, format:%s(%d)], ", size.getWidth(), size.getHeight(), this.formatToString(n), n));
            }
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(")");
    }

    private void appendOutputsString(StringBuilder stringBuilder) {
        stringBuilder.append("Outputs(");
        for (int n : this.getOutputFormats()) {
            for (Size size : this.getOutputSizes(n)) {
                long l = this.getOutputMinFrameDuration(n, size);
                long l2 = this.getOutputStallDuration(n, size);
                stringBuilder.append(String.format("[w:%d, h:%d, format:%s(%d), min_duration:%d, stall:%d], ", size.getWidth(), size.getHeight(), this.formatToString(n), n, l, l2));
            }
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(")");
    }

    private void appendValidOutputFormatsForInputString(StringBuilder stringBuilder) {
        stringBuilder.append("ValidOutputFormatsForInput(");
        for (int n : this.getInputFormats()) {
            stringBuilder.append(String.format("[in:%s(%d), out:", this.formatToString(n), n));
            int[] arrn = this.getValidOutputFormatsForInput(n);
            for (n = 0; n < arrn.length; ++n) {
                stringBuilder.append(String.format("%s(%d)", this.formatToString(arrn[n]), arrn[n]));
                if (n >= arrn.length - 1) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append("], ");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(")");
    }

    private static <T> boolean arrayContains(T[] arrT, T t) {
        if (arrT == null) {
            return false;
        }
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            if (!Objects.equals(arrT[i], t)) continue;
            return true;
        }
        return false;
    }

    static int checkArgumentFormat(int n) {
        if (!ImageFormat.isPublicFormat(n) && !PixelFormat.isPublicFormat(n)) {
            throw new IllegalArgumentException(String.format("format 0x%x was not defined in either ImageFormat or PixelFormat", n));
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int checkArgumentFormatInternal(int n) {
        if (n == 33 || n == 34 || n == 36) return n;
        if (n != 256) {
            if (n == 540422489) return n;
            if (n != 1212500294) {
                return StreamConfigurationMap.checkArgumentFormat(n);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("An unknown internal format: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int checkArgumentFormatSupported(int n, boolean bl) {
        StreamConfigurationMap.checkArgumentFormat(n);
        int n2 = StreamConfigurationMap.imageFormatToInternal(n);
        int n3 = StreamConfigurationMap.imageFormatToDataspace(n);
        if (bl ? (n3 == 4096 ? this.mDepthOutputFormats.indexOfKey(n2) >= 0 : (n3 == 4098 ? this.mDynamicDepthOutputFormats.indexOfKey(n2) >= 0 : (n3 == 4099 ? this.mHeicOutputFormats.indexOfKey(n2) >= 0 : this.mAllOutputFormats.indexOfKey(n2) >= 0))) : this.mInputFormats.indexOfKey(n2) >= 0) {
            return n;
        }
        throw new IllegalArgumentException(String.format("format %x is not supported by this stream configuration map", n));
    }

    public static int depthFormatToPublic(int n) {
        if (n != 256) {
            if (n != 540422489) {
                switch (n) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown DATASPACE_DEPTH format ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    case 34: {
                        throw new IllegalArgumentException("IMPLEMENTATION_DEFINED must not leak to public API");
                    }
                    case 33: {
                        return 257;
                    }
                    case 32: 
                }
                return 4098;
            }
            return 1144402265;
        }
        throw new IllegalArgumentException("ImageFormat.JPEG is an unknown internal format");
    }

    private String formatToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 16) {
                            if (n != 17) {
                                if (n != 256) {
                                    if (n != 257) {
                                        switch (n) {
                                            default: {
                                                switch (n) {
                                                    default: {
                                                        return "UNKNOWN";
                                                    }
                                                    case 37: {
                                                        return "RAW10";
                                                    }
                                                    case 36: {
                                                        return "RAW_PRIVATE";
                                                    }
                                                    case 35: {
                                                        return "YUV_420_888";
                                                    }
                                                    case 34: 
                                                }
                                                return "PRIVATE";
                                            }
                                            case 1768253795: {
                                                return "DEPTH_JPEG";
                                            }
                                            case 1212500294: {
                                                return "HEIC";
                                            }
                                            case 1144402265: {
                                                return "DEPTH16";
                                            }
                                            case 842094169: {
                                                return "YV12";
                                            }
                                            case 540422489: {
                                                return "Y16";
                                            }
                                            case 538982489: {
                                                return "Y8";
                                            }
                                            case 4098: {
                                                return "RAW_DEPTH";
                                            }
                                            case 32: {
                                                return "RAW_SENSOR";
                                            }
                                            case 20: 
                                        }
                                        return "YUY2";
                                    }
                                    return "DEPTH_POINT_CLOUD";
                                }
                                return "JPEG";
                            }
                            return "NV21";
                        }
                        return "NV16";
                    }
                    return "RGB_565";
                }
                return "RGB_888";
            }
            return "RGBX_8888";
        }
        return "RGBA_8888";
    }

    private StreamConfigurationDuration[] getDurations(int n, int n2) {
        if (n != 0) {
            if (n == 1) {
                StreamConfigurationDuration[] arrstreamConfigurationDuration = n2 == 4096 ? this.mDepthStallDurations : (n2 == 4098 ? this.mDynamicDepthStallDurations : (n2 == 4099 ? this.mHeicStallDurations : this.mStallDurations));
                return arrstreamConfigurationDuration;
            }
            throw new IllegalArgumentException("duration was invalid");
        }
        StreamConfigurationDuration[] arrstreamConfigurationDuration = n2 == 4096 ? this.mDepthMinFrameDurations : (n2 == 4098 ? this.mDynamicDepthMinFrameDurations : (n2 == 4099 ? this.mHeicMinFrameDurations : this.mMinFrameDurations));
        return arrstreamConfigurationDuration;
    }

    private SparseIntArray getFormatsMap(boolean bl) {
        SparseIntArray sparseIntArray = bl ? this.mAllOutputFormats : this.mInputFormats;
        return sparseIntArray;
    }

    private long getInternalFormatDuration(int n, int n2, Size size, int n3) {
        if (this.isSupportedInternalConfiguration(n, n2, size)) {
            for (StreamConfigurationDuration streamConfigurationDuration : this.getDurations(n3, n2)) {
                if (streamConfigurationDuration.getFormat() != n || streamConfigurationDuration.getWidth() != size.getWidth() || streamConfigurationDuration.getHeight() != size.getHeight()) continue;
                return streamConfigurationDuration.getDuration();
            }
            return 0L;
        }
        throw new IllegalArgumentException("size was not supported");
    }

    private Size[] getInternalFormatSizes(int n, int n2, boolean bl, boolean bl2) {
        block10 : {
            int n3;
            int n4;
            Object object;
            block14 : {
                block13 : {
                    Size[] arrsize;
                    block11 : {
                        block12 : {
                            StreamConfigurationDuration[] arrstreamConfigurationDuration = this;
                            int n5 = n;
                            int n6 = 4096;
                            if (n2 == 4096 && bl2) {
                                return new Size[0];
                            }
                            object = !bl ? arrstreamConfigurationDuration.mInputFormats : (n2 == 4096 ? arrstreamConfigurationDuration.mDepthOutputFormats : (n2 == 4098 ? arrstreamConfigurationDuration.mDynamicDepthOutputFormats : (n2 == 4099 ? arrstreamConfigurationDuration.mHeicOutputFormats : (bl2 ? arrstreamConfigurationDuration.mHighResOutputFormats : arrstreamConfigurationDuration.mOutputFormats))));
                            n4 = ((SparseIntArray)object).get(n5);
                            if ((!bl || n2 == 4096 || n2 == 4098 || n2 == 4099) && n4 == 0 || bl && n2 != 4096 && n2 != 4098 && n2 != 4099 && arrstreamConfigurationDuration.mAllOutputFormats.get(n5) == 0) break block10;
                            arrsize = new Size[n4];
                            object = n2 == 4096 ? arrstreamConfigurationDuration.mDepthConfigurations : (n2 == 4098 ? arrstreamConfigurationDuration.mDynamicDepthConfigurations : (n2 == 4099 ? arrstreamConfigurationDuration.mHeicConfigurations : arrstreamConfigurationDuration.mConfigurations));
                            arrstreamConfigurationDuration = n2 == 4096 ? arrstreamConfigurationDuration.mDepthMinFrameDurations : (n2 == 4098 ? arrstreamConfigurationDuration.mDynamicDepthMinFrameDurations : (n2 == 4099 ? arrstreamConfigurationDuration.mHeicMinFrameDurations : arrstreamConfigurationDuration.mMinFrameDurations));
                            int n7 = ((StreamConfiguration[])object).length;
                            n3 = 0;
                            for (n5 = 0; n5 < n7; ++n5) {
                                StreamConfiguration streamConfiguration = object[n5];
                                int n8 = streamConfiguration.getFormat();
                                if (n8 != n || streamConfiguration.isOutput() != bl) continue;
                                if (bl && this.mListHighResolution) {
                                    long l;
                                    long l2 = 0L;
                                    n6 = 0;
                                    do {
                                        l = l2;
                                        if (n6 >= arrstreamConfigurationDuration.length) break;
                                        StreamConfigurationDuration streamConfigurationDuration = arrstreamConfigurationDuration[n6];
                                        if (streamConfigurationDuration.getFormat() == n8 && streamConfigurationDuration.getWidth() == streamConfiguration.getSize().getWidth() && streamConfigurationDuration.getHeight() == streamConfiguration.getSize().getHeight()) {
                                            l = streamConfigurationDuration.getDuration();
                                            break;
                                        }
                                        ++n6;
                                    } while (true);
                                    n6 = n8 = 4096;
                                    if (n2 != 4096) {
                                        boolean bl3 = l > 50000000L;
                                        n6 = n8;
                                        if (bl2 != bl3) {
                                            n6 = n8;
                                            continue;
                                        }
                                    }
                                }
                                arrsize[n3] = streamConfiguration.getSize();
                                ++n3;
                            }
                            if (n3 == n4 || n2 != 4098 && n2 != 4099) break block11;
                            if (n3 > n4) break block12;
                            object = n3 <= 0 ? new Size[]{} : Arrays.copyOf(arrsize, n3);
                            break block13;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Too many dynamic depth sizes (expected ");
                        ((StringBuilder)object).append(n4);
                        ((StringBuilder)object).append(", actual ");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(")");
                        throw new AssertionError((Object)((StringBuilder)object).toString());
                    }
                    if (n3 != n4) break block14;
                    object = arrsize;
                }
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Too few sizes (expected ");
            ((StringBuilder)object).append(n4);
            ((StringBuilder)object).append(", actual ");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(")");
            throw new AssertionError((Object)((StringBuilder)object).toString());
        }
        return null;
    }

    private int getPublicFormatCount(boolean bl) {
        int n;
        int n2 = n = this.getFormatsMap(bl).size();
        if (bl) {
            n2 = n + this.mDepthOutputFormats.size() + this.mDynamicDepthOutputFormats.size() + this.mHeicOutputFormats.size();
        }
        return n2;
    }

    private Size[] getPublicFormatSizes(int n, boolean bl, boolean bl2) {
        try {
            this.checkArgumentFormatSupported(n, bl);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        return this.getInternalFormatSizes(StreamConfigurationMap.imageFormatToInternal(n), StreamConfigurationMap.imageFormatToDataspace(n), bl, bl2);
    }

    private int[] getPublicFormats(boolean bl) {
        int[] arrn = new int[this.getPublicFormatCount(bl)];
        int n = 0;
        Object object = this.getFormatsMap(bl);
        int n2 = 0;
        while (n2 < ((SparseIntArray)object).size()) {
            arrn[n] = StreamConfigurationMap.imageFormatToPublic(((SparseIntArray)object).keyAt(n2));
            ++n2;
            ++n;
        }
        n2 = n;
        if (bl) {
            n2 = 0;
            while (n2 < this.mDepthOutputFormats.size()) {
                arrn[n] = StreamConfigurationMap.depthFormatToPublic(this.mDepthOutputFormats.keyAt(n2));
                ++n2;
                ++n;
            }
            int n3 = n;
            if (this.mDynamicDepthOutputFormats.size() > 0) {
                arrn[n] = 1768253795;
                n3 = n + 1;
            }
            n2 = n3;
            if (this.mHeicOutputFormats.size() > 0) {
                arrn[n3] = 1212500294;
                n2 = n3 + 1;
            }
        }
        if (arrn.length == n2) {
            return arrn;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Too few formats ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(", expected ");
        ((StringBuilder)object).append(arrn.length);
        throw new AssertionError((Object)((StringBuilder)object).toString());
    }

    static int imageFormatToDataspace(int n) {
        if (n != 256) {
            if (n != 257 && n != 4098 && n != 1144402265) {
                if (n != 1212500294) {
                    if (n != 1768253795) {
                        return 0;
                    }
                    return 4098;
                }
                return 4099;
            }
            return 4096;
        }
        return 146931712;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int imageFormatToInternal(int n) {
        if (n == 256 || n == 257) return 33;
        if (n == 4098) return 32;
        if (n == 1144402265) return 540422489;
        if (n == 1212500294 || n == 1768253795) return 33;
        return n;
    }

    public static int[] imageFormatToInternal(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = StreamConfigurationMap.imageFormatToInternal(arrn[i]);
        }
        return arrn;
    }

    public static int imageFormatToPublic(int n) {
        if (n != 33) {
            if (n != 256) {
                return n;
            }
            throw new IllegalArgumentException("ImageFormat.JPEG is an unknown internal format");
        }
        return 256;
    }

    static int[] imageFormatToPublic(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = StreamConfigurationMap.imageFormatToPublic(arrn[i]);
        }
        return arrn;
    }

    public static <T> boolean isOutputSupportedFor(Class<T> class_) {
        Preconditions.checkNotNull(class_, "klass must not be null");
        if (class_ == ImageReader.class) {
            return true;
        }
        if (class_ == MediaRecorder.class) {
            return true;
        }
        if (class_ == MediaCodec.class) {
            return true;
        }
        if (class_ == Allocation.class) {
            return true;
        }
        if (class_ == SurfaceHolder.class) {
            return true;
        }
        return class_ == SurfaceTexture.class;
    }

    private boolean isSupportedInternalConfiguration(int n, int n2, Size size) {
        StreamConfiguration[] arrstreamConfiguration = n2 == 4096 ? this.mDepthConfigurations : (n2 == 4098 ? this.mDynamicDepthConfigurations : (n2 == 4099 ? this.mHeicConfigurations : this.mConfigurations));
        for (n2 = 0; n2 < arrstreamConfiguration.length; ++n2) {
            if (arrstreamConfiguration[n2].getFormat() != n || !arrstreamConfiguration[n2].getSize().equals(size)) continue;
            return true;
        }
        return false;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof StreamConfigurationMap) {
            object = (StreamConfigurationMap)object;
            if (Arrays.equals(this.mConfigurations, ((StreamConfigurationMap)object).mConfigurations) && Arrays.equals(this.mMinFrameDurations, ((StreamConfigurationMap)object).mMinFrameDurations) && Arrays.equals(this.mStallDurations, ((StreamConfigurationMap)object).mStallDurations) && Arrays.equals(this.mDepthConfigurations, ((StreamConfigurationMap)object).mDepthConfigurations) && Arrays.equals(this.mDepthMinFrameDurations, ((StreamConfigurationMap)object).mDepthMinFrameDurations) && Arrays.equals(this.mDepthStallDurations, ((StreamConfigurationMap)object).mDepthStallDurations) && Arrays.equals(this.mDynamicDepthConfigurations, ((StreamConfigurationMap)object).mDynamicDepthConfigurations) && Arrays.equals(this.mDynamicDepthMinFrameDurations, ((StreamConfigurationMap)object).mDynamicDepthMinFrameDurations) && Arrays.equals(this.mDynamicDepthStallDurations, ((StreamConfigurationMap)object).mDynamicDepthStallDurations) && Arrays.equals(this.mHeicConfigurations, ((StreamConfigurationMap)object).mHeicConfigurations) && Arrays.equals(this.mHeicMinFrameDurations, ((StreamConfigurationMap)object).mHeicMinFrameDurations) && Arrays.equals(this.mHeicStallDurations, ((StreamConfigurationMap)object).mHeicStallDurations) && Arrays.equals(this.mHighSpeedVideoConfigurations, ((StreamConfigurationMap)object).mHighSpeedVideoConfigurations)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public Size[] getHighResolutionOutputSizes(int n) {
        if (!this.mListHighResolution) {
            return null;
        }
        return this.getPublicFormatSizes(n, true, true);
    }

    public Range<Integer>[] getHighSpeedVideoFpsRanges() {
        Set<Range<Integer>> set = this.mHighSpeedVideoFpsRangeMap.keySet();
        return set.toArray(new Range[set.size()]);
    }

    public Range<Integer>[] getHighSpeedVideoFpsRangesFor(Size size) {
        Range[] arrrange = this.mHighSpeedVideoSizeMap.get(size);
        if (arrrange != null && arrrange.intValue() != 0) {
            arrrange = new Range[arrrange.intValue()];
            int n = 0;
            for (HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
                int n2 = n;
                if (size.equals(highSpeedVideoConfiguration.getSize())) {
                    arrrange[n] = highSpeedVideoConfiguration.getFpsRange();
                    n2 = n + 1;
                }
                n = n2;
            }
            return arrrange;
        }
        throw new IllegalArgumentException(String.format("Size %s does not support high speed video recording", size));
    }

    public Size[] getHighSpeedVideoSizes() {
        Set<Size> set = this.mHighSpeedVideoSizeMap.keySet();
        return set.toArray(new Size[set.size()]);
    }

    public Size[] getHighSpeedVideoSizesFor(Range<Integer> range) {
        HighSpeedVideoConfiguration[] arrhighSpeedVideoConfiguration = this.mHighSpeedVideoFpsRangeMap.get(range);
        if (arrhighSpeedVideoConfiguration != null && arrhighSpeedVideoConfiguration.intValue() != 0) {
            Size[] arrsize = new Size[arrhighSpeedVideoConfiguration.intValue()];
            int n = 0;
            for (HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
                int n2 = n;
                if (range.equals(highSpeedVideoConfiguration.getFpsRange())) {
                    arrsize[n] = highSpeedVideoConfiguration.getSize();
                    n2 = n + 1;
                }
                n = n2;
            }
            return arrsize;
        }
        throw new IllegalArgumentException(String.format("FpsRange %s does not support high speed video recording", range));
    }

    public int[] getInputFormats() {
        return this.getPublicFormats(false);
    }

    public Size[] getInputSizes(int n) {
        return this.getPublicFormatSizes(n, false, false);
    }

    public int[] getOutputFormats() {
        return this.getPublicFormats(true);
    }

    public long getOutputMinFrameDuration(int n, Size size) {
        Preconditions.checkNotNull(size, "size must not be null");
        this.checkArgumentFormatSupported(n, true);
        return this.getInternalFormatDuration(StreamConfigurationMap.imageFormatToInternal(n), StreamConfigurationMap.imageFormatToDataspace(n), size, 0);
    }

    public <T> long getOutputMinFrameDuration(Class<T> class_, Size size) {
        if (StreamConfigurationMap.isOutputSupportedFor(class_)) {
            return this.getInternalFormatDuration(34, 0, size, 0);
        }
        throw new IllegalArgumentException("klass was not supported");
    }

    public Size[] getOutputSizes(int n) {
        return this.getPublicFormatSizes(n, true, false);
    }

    public <T> Size[] getOutputSizes(Class<T> class_) {
        if (!StreamConfigurationMap.isOutputSupportedFor(class_)) {
            return null;
        }
        return this.getInternalFormatSizes(34, 0, true, false);
    }

    public long getOutputStallDuration(int n, Size size) {
        this.checkArgumentFormatSupported(n, true);
        return this.getInternalFormatDuration(StreamConfigurationMap.imageFormatToInternal(n), StreamConfigurationMap.imageFormatToDataspace(n), size, 1);
    }

    public <T> long getOutputStallDuration(Class<T> class_, Size size) {
        if (StreamConfigurationMap.isOutputSupportedFor(class_)) {
            return this.getInternalFormatDuration(34, 0, size, 1);
        }
        throw new IllegalArgumentException("klass was not supported");
    }

    public int[] getValidOutputFormatsForInput(int n) {
        int[] arrn = this.mInputOutputFormatsMap;
        if (arrn == null) {
            return new int[0];
        }
        arrn = arrn.getOutputs(n);
        if (this.mHeicOutputFormats.size() > 0) {
            int[] arrn2 = Arrays.copyOf(arrn, arrn.length + 1);
            arrn2[arrn.length] = 1212500294;
            return arrn2;
        }
        return arrn;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mConfigurations, this.mMinFrameDurations, this.mStallDurations, this.mDepthConfigurations, this.mDepthMinFrameDurations, this.mDepthStallDurations, this.mDynamicDepthConfigurations, this.mDynamicDepthMinFrameDurations, this.mDynamicDepthStallDurations, this.mHeicConfigurations, this.mHeicMinFrameDurations, this.mHeicStallDurations, this.mHighSpeedVideoConfigurations);
    }

    public boolean isOutputSupportedFor(int n) {
        StreamConfigurationMap.checkArgumentFormat(n);
        int n2 = StreamConfigurationMap.imageFormatToInternal(n);
        n = StreamConfigurationMap.imageFormatToDataspace(n);
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (n == 4096) {
            if (this.mDepthOutputFormats.indexOfKey(n2) >= 0) {
                bl4 = true;
            }
            return bl4;
        }
        if (n == 4098) {
            bl4 = bl;
            if (this.mDynamicDepthOutputFormats.indexOfKey(n2) >= 0) {
                bl4 = true;
            }
            return bl4;
        }
        if (n == 4099) {
            bl4 = bl2;
            if (this.mHeicOutputFormats.indexOfKey(n2) >= 0) {
                bl4 = true;
            }
            return bl4;
        }
        bl4 = bl3;
        if (this.getFormatsMap(true).indexOfKey(n2) >= 0) {
            bl4 = true;
        }
        return bl4;
    }

    public boolean isOutputSupportedFor(Size size, int n) {
        int n2 = StreamConfigurationMap.imageFormatToInternal(n);
        for (StreamConfiguration streamConfiguration : (n = StreamConfigurationMap.imageFormatToDataspace(n)) == 4096 ? this.mDepthConfigurations : (n == 4098 ? this.mDynamicDepthConfigurations : (n == 4099 ? this.mHeicConfigurations : this.mConfigurations))) {
            if (streamConfiguration.getFormat() != n2 || !streamConfiguration.isOutput() || !streamConfiguration.getSize().equals(size)) continue;
            return true;
        }
        return false;
    }

    public boolean isOutputSupportedFor(Surface arrstreamConfiguration) {
        Preconditions.checkNotNull(arrstreamConfiguration, "surface must not be null");
        Size size = SurfaceUtils.getSurfaceSize((Surface)arrstreamConfiguration);
        int n = SurfaceUtils.getSurfaceFormat((Surface)arrstreamConfiguration);
        int n2 = SurfaceUtils.getSurfaceDataspace((Surface)arrstreamConfiguration);
        boolean bl = SurfaceUtils.isFlexibleConsumer((Surface)arrstreamConfiguration);
        for (StreamConfiguration streamConfiguration : n2 == 4096 ? this.mDepthConfigurations : (n2 == 4098 ? this.mDynamicDepthConfigurations : (n2 == 4099 ? this.mHeicConfigurations : this.mConfigurations))) {
            if (streamConfiguration.getFormat() != n || !streamConfiguration.isOutput()) continue;
            if (streamConfiguration.getSize().equals(size)) {
                return true;
            }
            if (!bl || streamConfiguration.getSize().getWidth() > 1920) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StreamConfiguration(");
        this.appendOutputsString(stringBuilder);
        stringBuilder.append(", ");
        this.appendHighResOutputsString(stringBuilder);
        stringBuilder.append(", ");
        this.appendInputsString(stringBuilder);
        stringBuilder.append(", ");
        this.appendValidOutputFormatsForInputString(stringBuilder);
        stringBuilder.append(", ");
        this.appendHighSpeedVideoConfigurationsString(stringBuilder);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

