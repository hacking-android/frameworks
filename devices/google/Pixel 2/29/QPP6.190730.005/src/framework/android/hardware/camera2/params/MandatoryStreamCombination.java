/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class MandatoryStreamCombination {
    private static final String TAG = "MandatoryStreamCombination";
    private static StreamCombinationTemplate[] sBurstCombinations;
    private static StreamCombinationTemplate[] sFullCombinations;
    private static StreamCombinationTemplate[] sFullPrivateReprocCombinations;
    private static StreamCombinationTemplate[] sFullYUVReprocCombinations;
    private static StreamCombinationTemplate[] sLegacyCombinations;
    private static StreamCombinationTemplate[] sLevel3Combinations;
    private static StreamCombinationTemplate[] sLevel3PrivateReprocCombinations;
    private static StreamCombinationTemplate[] sLevel3YUVReprocCombinations;
    private static StreamCombinationTemplate[] sLimitedCombinations;
    private static StreamCombinationTemplate[] sLimitedPrivateReprocCombinations;
    private static StreamCombinationTemplate[] sLimitedYUVReprocCombinations;
    private static StreamCombinationTemplate[] sRAWPrivateReprocCombinations;
    private static StreamCombinationTemplate[] sRAWYUVReprocCombinations;
    private static StreamCombinationTemplate[] sRawCombinations;
    private final String mDescription;
    private final boolean mIsReprocessable;
    private final ArrayList<MandatoryStreamInformation> mStreamsInformation = new ArrayList();

    static {
        sLegacyCombinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Simple preview, GPU video processing, or no-preview video recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "No-viewfinder still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "In-application video/image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Standard still imaging"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "In-app processing plus still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Standard recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "Preview plus in-app processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Still capture plus in-app processing")};
        sLimitedCombinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD)}, "High-resolution video recording with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "High-resolution in-app video processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "Two-input in-app video processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD), new StreamTemplate(256, SizeThreshold.RECORD)}, "High-resolution recording with video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD), new StreamTemplate(256, SizeThreshold.RECORD)}, "High-resolution in-app processing with video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Two-input in-app processing with still capture")};
        sBurstCombinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Maximum-resolution GPU processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution in-app processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input in-app processsing")};
        sFullCombinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM), new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Maximum-resolution GPU processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution in-app processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input in-app processsing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Video recording with maximum-size video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.VGA), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Standard video recording plus maximum-resolution in-app processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.VGA), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Preview plus two-input maximum-resolution in-app processing")};
        sRawCombinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "No-preview DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Standard DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app processing plus DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Video recording with DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Preview with in-app processing and DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Two-input in-app processing plus DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Still capture with simultaneous JPEG and DNG"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app processing with simultaneous JPEG and DNG")};
        sLevel3Combinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(35, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with dynamic selection of output format"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with dynamic selection of output format")};
        Object object = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        Object object2 = ReprocessType.PRIVATE;
        object = new StreamCombinationTemplate(new StreamTemplate[]{object}, "No-viewfinder still image reprocessing", (ReprocessType)((Object)object2));
        object2 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        Object object3 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        Object object4 = ReprocessType.PRIVATE;
        object2 = new StreamCombinationTemplate(new StreamTemplate[]{object2, object3}, "ZSL(Zero-Shutter-Lag) still imaging", (ReprocessType)((Object)object4));
        object3 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        Object object5 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object4 = ReprocessType.PRIVATE;
        object4 = new StreamCombinationTemplate(new StreamTemplate[]{object3, object5}, "ZSL still and in-app processing imaging", (ReprocessType)((Object)object4));
        Object object6 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        Object object7 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.PRIVATE;
        sLimitedPrivateReprocCombinations = new StreamCombinationTemplate[]{object, object2, object4, new StreamCombinationTemplate(new StreamTemplate[]{object6, object7, object3}, "ZSL in-app processing with still capture", (ReprocessType)((Object)object5))};
        object = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object2 = ReprocessType.YUV;
        object = new StreamCombinationTemplate(new StreamTemplate[]{object}, "No-viewfinder still image reprocessing", (ReprocessType)((Object)object2));
        object4 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object2 = ReprocessType.YUV;
        object2 = new StreamCombinationTemplate(new StreamTemplate[]{object4, object3}, "ZSL(Zero-Shutter-Lag) still imaging", (ReprocessType)((Object)object2));
        object3 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object5 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object4 = ReprocessType.YUV;
        object5 = new StreamCombinationTemplate(new StreamTemplate[]{object3, object5}, "ZSL still and in-app processing imaging", (ReprocessType)((Object)object4));
        object3 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object6 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object7 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object4 = ReprocessType.YUV;
        sLimitedYUVReprocCombinations = new StreamCombinationTemplate[]{object, object2, object5, new StreamCombinationTemplate(new StreamTemplate[]{object3, object6, object7}, "ZSL in-app processing with still capture", (ReprocessType)((Object)object4))};
        object4 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object2 = new StreamTemplate(35, SizeThreshold.RECORD);
        object = ReprocessType.PRIVATE;
        object = new StreamCombinationTemplate(new StreamTemplate[]{object4, object2}, "High-resolution ZSL in-app video processing with regular preview", (ReprocessType)((Object)object));
        object3 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object2 = new StreamTemplate(35, SizeThreshold.MAXIMUM);
        object4 = ReprocessType.PRIVATE;
        object2 = new StreamCombinationTemplate(new StreamTemplate[]{object3, object2}, "Maximum-resolution ZSL in-app processing with regular preview", (ReprocessType)((Object)object4));
        object4 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(35, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.PRIVATE;
        object3 = new StreamCombinationTemplate(new StreamTemplate[]{object4, object3}, "Maximum-resolution two-input ZSL in-app processing", (ReprocessType)((Object)object5));
        object7 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object6 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.PRIVATE;
        sFullPrivateReprocCombinations = new StreamCombinationTemplate[]{object, object2, object3, new StreamCombinationTemplate(new StreamTemplate[]{object7, object4, object6}, "ZSL still capture and in-app processing", (ReprocessType)((Object)object5))};
        object = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object2 = ReprocessType.YUV;
        object = new StreamCombinationTemplate(new StreamTemplate[]{object}, "Maximum-resolution multi-frame image fusion in-app processing with regular preview", (ReprocessType)((Object)object2));
        object2 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object4 = ReprocessType.YUV;
        object2 = new StreamCombinationTemplate(new StreamTemplate[]{object2}, "Maximum-resolution multi-frame image fusion two-input in-app processing", (ReprocessType)((Object)object4));
        object3 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(35, SizeThreshold.RECORD);
        object5 = ReprocessType.YUV;
        object3 = new StreamCombinationTemplate(new StreamTemplate[]{object3, object4}, "High-resolution ZSL in-app video processing with regular preview", (ReprocessType)((Object)object5));
        object6 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object5 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object7 = ReprocessType.YUV;
        sFullYUVReprocCombinations = new StreamCombinationTemplate[]{object, object2, object3, new StreamCombinationTemplate(new StreamTemplate[]{object6, object5, object4}, "ZSL still capture and in-app processing", (ReprocessType)((Object)object7))};
        object2 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object = ReprocessType.PRIVATE;
        object = new StreamCombinationTemplate(new StreamTemplate[]{object2, object4}, "Mutually exclusive ZSL in-app processing and DNG capture", (ReprocessType)((Object)object));
        object2 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.PRIVATE;
        object2 = new StreamCombinationTemplate(new StreamTemplate[]{object2, object4, object3}, "Mutually exclusive ZSL in-app processing and preview with DNG capture", (ReprocessType)((Object)object5));
        object6 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object5 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object4 = ReprocessType.PRIVATE;
        object4 = new StreamCombinationTemplate(new StreamTemplate[]{object6, object3, object5}, "Mutually exclusive ZSL two-input in-app processing and DNG capture", (ReprocessType)((Object)object4));
        object5 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object7 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object6 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object3 = ReprocessType.PRIVATE;
        object5 = new StreamCombinationTemplate(new StreamTemplate[]{object5, object7, object6}, "Mutually exclusive ZSL still capture and preview with DNG capture", (ReprocessType)((Object)object3));
        object7 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        Object object8 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object6 = ReprocessType.PRIVATE;
        sRAWPrivateReprocCombinations = new StreamCombinationTemplate[]{object, object2, object4, object5, new StreamCombinationTemplate(new StreamTemplate[]{object7, object3, object8}, "Mutually exclusive ZSL in-app processing with still capture and DNG capture", (ReprocessType)((Object)object6))};
        object4 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object2 = ReprocessType.YUV;
        object = new StreamCombinationTemplate(new StreamTemplate[]{object4, object}, "Mutually exclusive ZSL in-app processing and DNG capture", (ReprocessType)((Object)object2));
        object3 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object2 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.YUV;
        object2 = new StreamCombinationTemplate(new StreamTemplate[]{object3, object4, object2}, "Mutually exclusive ZSL in-app processing and preview with DNG capture", (ReprocessType)((Object)object5));
        object4 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object6 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object3 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.YUV;
        object4 = new StreamCombinationTemplate(new StreamTemplate[]{object4, object6, object3}, "Mutually exclusive ZSL two-input in-app processing and DNG capture", (ReprocessType)((Object)object5));
        object3 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object7 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object6 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.YUV;
        object8 = new StreamCombinationTemplate(new StreamTemplate[]{object3, object7, object6}, "Mutually exclusive ZSL still capture and preview with DNG capture", (ReprocessType)((Object)object5));
        object3 = new StreamTemplate(35, SizeThreshold.PREVIEW);
        object6 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object7 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.YUV;
        sRAWYUVReprocCombinations = new StreamCombinationTemplate[]{object, object2, object4, object8, new StreamCombinationTemplate(new StreamTemplate[]{object3, object6, object7}, "Mutually exclusive ZSL in-app processing with still capture and DNG capture", (ReprocessType)((Object)object5))};
        object3 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object4 = new StreamTemplate(34, SizeThreshold.VGA);
        object = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object2 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object5 = ReprocessType.PRIVATE;
        sLevel3PrivateReprocCombinations = new StreamCombinationTemplate[]{new StreamCombinationTemplate(new StreamTemplate[]{object3, object4, object, object2}, "In-app viewfinder analysis with ZSL, RAW, and JPEG reprocessing output", (ReprocessType)((Object)object5))};
        object4 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object = new StreamTemplate(34, SizeThreshold.VGA);
        object2 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object3 = ReprocessType.YUV;
        object4 = new StreamCombinationTemplate(new StreamTemplate[]{object4, object, object2}, "In-app viewfinder analysis with ZSL and RAW", (ReprocessType)((Object)object3));
        object5 = new StreamTemplate(34, SizeThreshold.PREVIEW);
        object = new StreamTemplate(34, SizeThreshold.VGA);
        object2 = new StreamTemplate(32, SizeThreshold.MAXIMUM);
        object6 = new StreamTemplate(256, SizeThreshold.MAXIMUM);
        object3 = ReprocessType.YUV;
        sLevel3YUVReprocCombinations = new StreamCombinationTemplate[]{object4, new StreamCombinationTemplate(new StreamTemplate[]{object5, object, object2, object6}, "In-app viewfinder analysis with ZSL, RAW, and JPEG reprocessing output", (ReprocessType)((Object)object3))};
    }

    public MandatoryStreamCombination(List<MandatoryStreamInformation> list, String string2, boolean bl) {
        if (!list.isEmpty()) {
            this.mStreamsInformation.addAll(list);
            this.mDescription = string2;
            this.mIsReprocessable = bl;
            return;
        }
        throw new IllegalArgumentException("Empty stream information");
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof MandatoryStreamCombination) {
            object = (MandatoryStreamCombination)object;
            if (this.mDescription == ((MandatoryStreamCombination)object).mDescription && this.mIsReprocessable == ((MandatoryStreamCombination)object).mIsReprocessable && this.mStreamsInformation.size() == ((MandatoryStreamCombination)object).mStreamsInformation.size()) {
                return this.mStreamsInformation.equals(((MandatoryStreamCombination)object).mStreamsInformation);
            }
            return false;
        }
        return false;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public List<MandatoryStreamInformation> getStreamsInformation() {
        return Collections.unmodifiableList(this.mStreamsInformation);
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{Boolean.hashCode(this.mIsReprocessable), this.mDescription.hashCode(), this.mStreamsInformation.hashCode()});
    }

    public boolean isReprocessable() {
        return this.mIsReprocessable;
    }

    public static final class Builder {
        private final Size kPreviewSizeBound = new Size(1920, 1088);
        private int mCameraId;
        private List<Integer> mCapabilities;
        private Size mDisplaySize;
        private int mHwLevel;
        private boolean mIsHiddenPhysicalCamera;
        private StreamConfigurationMap mStreamConfigMap;

        public Builder(int n, int n2, Size size, List<Integer> list, StreamConfigurationMap streamConfigurationMap) {
            this.mCameraId = n;
            this.mDisplaySize = size;
            this.mCapabilities = list;
            this.mStreamConfigMap = streamConfigurationMap;
            this.mHwLevel = n2;
            this.mIsHiddenPhysicalCamera = CameraManager.isHiddenPhysicalCamera(Integer.toString(this.mCameraId));
        }

        private static int compareSizes(int n, int n2, int n3, int n4) {
            long l = (long)n * (long)n2;
            long l2 = (long)n3 * (long)n4;
            long l3 = l;
            long l4 = l2;
            if (l == l2) {
                l3 = n;
                l4 = n3;
            }
            n = l3 < l4 ? -1 : (l3 > l4 ? 1 : 0);
            return n;
        }

        private HashMap<Pair<SizeThreshold, Integer>, List<Size>> enumerateAvailableSizes() {
            int[] arrn;
            int[] arrn2 = arrn = new int[3];
            arrn2[0] = 34;
            arrn2[1] = 35;
            arrn2[2] = 256;
            int n = 0;
            new Size(0, 0);
            new Size(0, 0);
            Size size = new Size(640, 480);
            Size size2 = !this.isExternalCamera() && !this.mIsHiddenPhysicalCamera ? this.getMaxRecordingSize() : this.getMaxCameraRecordingSize();
            if (size2 == null) {
                Log.e(MandatoryStreamCombination.TAG, "Failed to find maximum recording size!");
                return null;
            }
            HashMap<Integer, Size[]> hashMap = new HashMap<Integer, Size[]>();
            for (int n2 : arrn) {
                hashMap.put(new Integer(n2), this.mStreamConfigMap.getOutputSizes(n2));
            }
            Size[] arrsize = Builder.getSizesWithinBound((Size[])hashMap.get(new Integer(34)), this.kPreviewSizeBound);
            if (arrsize != null && !arrsize.isEmpty()) {
                Size size3 = this.getMaxPreviewSize(Builder.getAscendingOrderSizes(arrsize, false));
                HashMap<Pair<SizeThreshold, Integer>, List<Size>> hashMap2 = new HashMap<Pair<SizeThreshold, Integer>, List<Size>>();
                int n3 = arrn.length;
                for (int i = n; i < n3; ++i) {
                    Integer n4 = new Integer(arrn[i]);
                    arrsize = (Size[])hashMap.get(n4);
                    hashMap2.put(new Pair<SizeThreshold, Integer>(SizeThreshold.VGA, n4), Builder.getSizesWithinBound(arrsize, size));
                    hashMap2.put(new Pair<SizeThreshold, Integer>(SizeThreshold.PREVIEW, n4), Builder.getSizesWithinBound(arrsize, size3));
                    hashMap2.put(new Pair<SizeThreshold, Integer>(SizeThreshold.RECORD, n4), Builder.getSizesWithinBound(arrsize, size2));
                    hashMap2.put(new Pair<SizeThreshold, Integer>(SizeThreshold.MAXIMUM, n4), Arrays.asList(arrsize));
                }
                return hashMap2;
            }
            Log.e(MandatoryStreamCombination.TAG, "No preview sizes within preview size bound!");
            return null;
        }

        private List<MandatoryStreamCombination> generateAvailableCombinations(ArrayList<StreamCombinationTemplate> object) {
            if (((ArrayList)object).isEmpty()) {
                Log.e(MandatoryStreamCombination.TAG, "No available stream templates!");
                return null;
            }
            HashMap<Pair<SizeThreshold, Integer>, List<Size>> hashMap = this.enumerateAvailableSizes();
            if (hashMap == null) {
                Log.e(MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            Object object2 = this.mStreamConfigMap.getOutputSizes(32);
            ArrayList<Size> arrayList = new ArrayList<Size>();
            if (object2 != null) {
                arrayList.ensureCapacity(((Size[])object2).length);
                arrayList.addAll(Arrays.asList(object2));
            }
            object2 = new Size(0, 0);
            if (this.isPrivateReprocessingSupported()) {
                object2 = Builder.getMaxSize(this.mStreamConfigMap.getInputSizes(34));
            }
            Size size = new Size(0, 0);
            if (this.isYUVReprocessingSupported()) {
                size = Builder.getMaxSize(this.mStreamConfigMap.getInputSizes(35));
            }
            ArrayList<Object> arrayList2 = new ArrayList<Object>();
            arrayList2.ensureCapacity(((ArrayList)object).size());
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                Object object3;
                StreamCombinationTemplate streamCombinationTemplate = (StreamCombinationTemplate)object.next();
                ArrayList<MandatoryStreamInformation> arrayList3 = new ArrayList<MandatoryStreamInformation>();
                arrayList3.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                boolean bl = streamCombinationTemplate.mReprocessType != ReprocessType.NONE;
                if (bl) {
                    int n;
                    object3 = new ArrayList();
                    if (streamCombinationTemplate.mReprocessType == ReprocessType.PRIVATE) {
                        ((ArrayList)object3).add(object2);
                        n = 34;
                    } else {
                        ((ArrayList)object3).add(size);
                        n = 35;
                    }
                    arrayList3.add(new MandatoryStreamInformation((List<Size>)object3, n, true));
                    arrayList3.add(new MandatoryStreamInformation((List<Size>)object3, n));
                }
                for (StreamTemplate streamTemplate : streamCombinationTemplate.mStreamTemplates) {
                    List<Size> list = streamTemplate.mFormat == 32 ? arrayList : hashMap.get(new Pair<SizeThreshold, Integer>(streamTemplate.mSizeThreshold, new Integer(streamTemplate.mFormat)));
                    try {
                        list = new MandatoryStreamInformation(list, streamTemplate.mFormat);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("No available sizes found for format: ");
                        stringBuilder.append(streamTemplate.mFormat);
                        stringBuilder.append(" size threshold: ");
                        stringBuilder.append((Object)streamTemplate.mSizeThreshold);
                        stringBuilder.append(" combination: ");
                        stringBuilder.append(streamCombinationTemplate.mDescription);
                        Log.e(MandatoryStreamCombination.TAG, stringBuilder.toString());
                        return null;
                    }
                    arrayList3.add((MandatoryStreamInformation)((Object)list));
                }
                try {
                    object3 = new MandatoryStreamCombination(arrayList3, streamCombinationTemplate.mDescription, bl);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No stream information for mandatory combination: ");
                    stringBuilder.append(streamCombinationTemplate.mDescription);
                    Log.e(MandatoryStreamCombination.TAG, stringBuilder.toString());
                    return null;
                }
                arrayList2.add(object3);
            }
            return Collections.unmodifiableList(arrayList2);
        }

        private static List<Size> getAscendingOrderSizes(List<Size> list, boolean bl) {
            SizeComparator sizeComparator = new SizeComparator();
            ArrayList<Size> arrayList = new ArrayList<Size>();
            arrayList.addAll(list);
            Collections.sort(arrayList, sizeComparator);
            if (!bl) {
                Collections.reverse(arrayList);
            }
            return arrayList;
        }

        private Size getMaxCameraRecordingSize() {
            Object object = new Size(1920, 1080);
            Size[] object22 = this.mStreamConfigMap.getOutputSizes(MediaRecorder.class);
            ArrayList<Size> arrayList = new ArrayList<Size>();
            for (Size size : object22) {
                if (size.getWidth() > ((Size)object).getWidth() || size.getHeight() > ((Size)object).getHeight()) continue;
                arrayList.add(size);
            }
            for (Size size : Builder.getAscendingOrderSizes(arrayList, false)) {
                if (!((double)this.mStreamConfigMap.getOutputMinFrameDuration(MediaRecorder.class, size) > 3.3222591362126246E7)) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("External camera ");
                ((StringBuilder)object).append(this.mCameraId);
                ((StringBuilder)object).append(" has max video size:");
                ((StringBuilder)object).append(size);
                Log.i(MandatoryStreamCombination.TAG, ((StringBuilder)object).toString());
                return size;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Camera ");
            stringBuilder.append(this.mCameraId);
            stringBuilder.append(" does not support any 30fps video output");
            Log.w(MandatoryStreamCombination.TAG, stringBuilder.toString());
            return object;
        }

        private Size getMaxPreviewSize(List<Size> object) {
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    Size size = (Size)object.next();
                    if (this.mDisplaySize.getWidth() < size.getWidth() || this.mDisplaySize.getWidth() < size.getHeight()) continue;
                    return size;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Camera ");
            ((StringBuilder)object).append(this.mCameraId);
            ((StringBuilder)object).append(" maximum preview size search failed with display size ");
            ((StringBuilder)object).append(this.mDisplaySize);
            Log.w(MandatoryStreamCombination.TAG, ((StringBuilder)object).toString());
            return this.kPreviewSizeBound;
        }

        private Size getMaxRecordingSize() {
            int n = this.mCameraId;
            int n2 = 8;
            if (!CamcorderProfile.hasProfile(n, 8)) {
                n2 = CamcorderProfile.hasProfile(this.mCameraId, 6) ? 6 : (CamcorderProfile.hasProfile(this.mCameraId, 5) ? 5 : (CamcorderProfile.hasProfile(this.mCameraId, 4) ? 4 : (CamcorderProfile.hasProfile(this.mCameraId, 7) ? 7 : (CamcorderProfile.hasProfile(this.mCameraId, 3) ? 3 : (CamcorderProfile.hasProfile(this.mCameraId, 2) ? 2 : -1)))));
            }
            if (n2 < 0) {
                return null;
            }
            CamcorderProfile camcorderProfile = CamcorderProfile.get(this.mCameraId, n2);
            return new Size(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        }

        public static Size getMaxSize(Size ... arrsize) {
            if (arrsize != null && arrsize.length != 0) {
                Size size = arrsize[0];
                for (Size size2 : arrsize) {
                    Size size3 = size;
                    if (size2.getWidth() * size2.getHeight() > size.getWidth() * size.getHeight()) {
                        size3 = size2;
                    }
                    size = size3;
                }
                return size;
            }
            throw new IllegalArgumentException("sizes was empty");
        }

        private static List<Size> getSizesWithinBound(Size[] arrsize, Size size) {
            ArrayList<Size> arrayList = new ArrayList<Size>();
            for (Size size2 : arrsize) {
                if (size2.getWidth() > size.getWidth() || size2.getHeight() > size.getHeight()) continue;
                arrayList.add(size2);
            }
            return arrayList;
        }

        private boolean isCapabilitySupported(int n) {
            return this.mCapabilities.contains(n);
        }

        private boolean isColorOutputSupported() {
            return this.isCapabilitySupported(0);
        }

        private boolean isExternalCamera() {
            boolean bl = this.mHwLevel == 4;
            return bl;
        }

        private boolean isHardwareLevelAtLeast(int n) {
            int[] arrn;
            int[] arrn2 = arrn = new int[5];
            arrn2[0] = 2;
            arrn2[1] = 4;
            arrn2[2] = 0;
            arrn2[3] = 1;
            arrn2[4] = 3;
            if (n == this.mHwLevel) {
                return true;
            }
            for (int n2 : arrn) {
                if (n2 == n) {
                    return true;
                }
                if (n2 != this.mHwLevel) continue;
                return false;
            }
            return false;
        }

        private boolean isHardwareLevelAtLeastFull() {
            return this.isHardwareLevelAtLeast(1);
        }

        private boolean isHardwareLevelAtLeastLegacy() {
            return this.isHardwareLevelAtLeast(2);
        }

        private boolean isHardwareLevelAtLeastLevel3() {
            return this.isHardwareLevelAtLeast(3);
        }

        private boolean isHardwareLevelAtLeastLimited() {
            return this.isHardwareLevelAtLeast(0);
        }

        private boolean isPrivateReprocessingSupported() {
            return this.isCapabilitySupported(4);
        }

        private boolean isYUVReprocessingSupported() {
            return this.isCapabilitySupported(7);
        }

        public List<MandatoryStreamCombination> getAvailableMandatoryStreamCombinations() {
            if (!this.isColorOutputSupported()) {
                Log.v(MandatoryStreamCombination.TAG, "Device is not backward compatible!");
                return null;
            }
            if (this.mCameraId < 0 && !this.isExternalCamera()) {
                Log.i(MandatoryStreamCombination.TAG, "Invalid camera id");
                return null;
            }
            ArrayList<StreamCombinationTemplate> arrayList = new ArrayList<StreamCombinationTemplate>();
            if (this.isHardwareLevelAtLeastLegacy()) {
                arrayList.addAll(Arrays.asList(sLegacyCombinations));
            }
            if (this.isHardwareLevelAtLeastLimited() || this.isExternalCamera()) {
                arrayList.addAll(Arrays.asList(sLimitedCombinations));
                if (this.isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sLimitedPrivateReprocCombinations));
                }
                if (this.isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sLimitedYUVReprocCombinations));
                }
            }
            if (this.isCapabilitySupported(6)) {
                arrayList.addAll(Arrays.asList(sBurstCombinations));
            }
            if (this.isHardwareLevelAtLeastFull()) {
                arrayList.addAll(Arrays.asList(sFullCombinations));
                if (this.isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sFullPrivateReprocCombinations));
                }
                if (this.isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sFullYUVReprocCombinations));
                }
            }
            if (this.isCapabilitySupported(3)) {
                arrayList.addAll(Arrays.asList(sRawCombinations));
                if (this.isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sRAWPrivateReprocCombinations));
                }
                if (this.isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sRAWYUVReprocCombinations));
                }
            }
            if (this.isHardwareLevelAtLeastLevel3()) {
                arrayList.addAll(Arrays.asList(sLevel3Combinations));
                if (this.isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sLevel3PrivateReprocCombinations));
                }
                if (this.isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(sLevel3YUVReprocCombinations));
                }
            }
            return this.generateAvailableCombinations(arrayList);
        }

        public static class SizeComparator
        implements Comparator<Size> {
            @Override
            public int compare(Size size, Size size2) {
                return Builder.compareSizes(size.getWidth(), size.getHeight(), size2.getWidth(), size2.getHeight());
            }
        }

    }

    public static final class MandatoryStreamInformation {
        private final ArrayList<Size> mAvailableSizes = new ArrayList();
        private final int mFormat;
        private final boolean mIsInput;

        public MandatoryStreamInformation(List<Size> list, int n) {
            this(list, n, false);
        }

        public MandatoryStreamInformation(List<Size> list, int n, boolean bl) {
            if (!list.isEmpty()) {
                this.mAvailableSizes.addAll(list);
                this.mFormat = StreamConfigurationMap.checkArgumentFormat(n);
                this.mIsInput = bl;
                return;
            }
            throw new IllegalArgumentException("No available sizes");
        }

        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (object instanceof MandatoryStreamInformation) {
                object = (MandatoryStreamInformation)object;
                if (this.mFormat == ((MandatoryStreamInformation)object).mFormat && this.mIsInput == ((MandatoryStreamInformation)object).mIsInput && this.mAvailableSizes.size() == ((MandatoryStreamInformation)object).mAvailableSizes.size()) {
                    return this.mAvailableSizes.equals(((MandatoryStreamInformation)object).mAvailableSizes);
                }
                return false;
            }
            return false;
        }

        public List<Size> getAvailableSizes() {
            return Collections.unmodifiableList(this.mAvailableSizes);
        }

        public int getFormat() {
            return this.mFormat;
        }

        public int hashCode() {
            return HashCodeHelpers.hashCode(new int[]{this.mFormat, Boolean.hashCode(this.mIsInput), this.mAvailableSizes.hashCode()});
        }

        public boolean isInput() {
            return this.mIsInput;
        }
    }

    private static enum ReprocessType {
        NONE,
        PRIVATE,
        YUV;
        
    }

    private static enum SizeThreshold {
        VGA,
        PREVIEW,
        RECORD,
        MAXIMUM;
        
    }

    private static final class StreamCombinationTemplate {
        public String mDescription;
        public ReprocessType mReprocessType;
        public StreamTemplate[] mStreamTemplates;

        public StreamCombinationTemplate(StreamTemplate[] arrstreamTemplate, String string2) {
            this(arrstreamTemplate, string2, ReprocessType.NONE);
        }

        public StreamCombinationTemplate(StreamTemplate[] arrstreamTemplate, String string2, ReprocessType reprocessType) {
            this.mStreamTemplates = arrstreamTemplate;
            this.mReprocessType = reprocessType;
            this.mDescription = string2;
        }
    }

    private static final class StreamTemplate {
        public int mFormat;
        public boolean mIsInput;
        public SizeThreshold mSizeThreshold;

        public StreamTemplate(int n, SizeThreshold sizeThreshold) {
            this(n, sizeThreshold, false);
        }

        public StreamTemplate(int n, SizeThreshold sizeThreshold, boolean bl) {
            this.mFormat = n;
            this.mSizeThreshold = sizeThreshold;
            this.mIsInput = bl;
        }
    }

}

