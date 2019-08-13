/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.ArraySet;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public final class RecommendedStreamConfigurationMap {
    public static final int MAX_USECASE_COUNT = 32;
    private static final String TAG = "RecommendedStreamConfigurationMap";
    public static final int USECASE_LOW_LATENCY_SNAPSHOT = 6;
    public static final int USECASE_PREVIEW = 0;
    public static final int USECASE_RAW = 5;
    public static final int USECASE_RECORD = 1;
    public static final int USECASE_SNAPSHOT = 3;
    public static final int USECASE_VENDOR_START = 24;
    public static final int USECASE_VIDEO_SNAPSHOT = 2;
    public static final int USECASE_ZSL = 4;
    private StreamConfigurationMap mRecommendedMap;
    private boolean mSupportsPrivate;
    private int mUsecase;

    public RecommendedStreamConfigurationMap(StreamConfigurationMap streamConfigurationMap, int n, boolean bl) {
        this.mRecommendedMap = streamConfigurationMap;
        this.mUsecase = n;
        this.mSupportsPrivate = bl;
    }

    private Set<Integer> getUnmodifiableIntegerSet(int[] arrn) {
        if (arrn != null && arrn.length > 0) {
            ArraySet<Integer> arraySet = new ArraySet<Integer>();
            arraySet.ensureCapacity(arrn.length);
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                arraySet.add(arrn[i]);
            }
            return Collections.unmodifiableSet(arraySet);
        }
        return null;
    }

    private Set<Range<Integer>> getUnmodifiableRangeSet(Range<Integer>[] arrrange) {
        if (arrrange != null && arrrange.length > 0) {
            ArraySet<Range<Integer>> arraySet = new ArraySet<Range<Integer>>();
            arraySet.addAll(Arrays.asList(arrrange));
            return Collections.unmodifiableSet(arraySet);
        }
        return null;
    }

    private Set<Size> getUnmodifiableSizeSet(Size[] arrsize) {
        if (arrsize != null && arrsize.length > 0) {
            ArraySet<Size> arraySet = new ArraySet<Size>();
            arraySet.addAll(Arrays.asList(arrsize));
            return Collections.unmodifiableSet(arraySet);
        }
        return null;
    }

    public Set<Size> getHighResolutionOutputSizes(int n) {
        return this.getUnmodifiableSizeSet(this.mRecommendedMap.getHighResolutionOutputSizes(n));
    }

    public Set<Range<Integer>> getHighSpeedVideoFpsRanges() {
        return this.getUnmodifiableRangeSet(this.mRecommendedMap.getHighSpeedVideoFpsRanges());
    }

    public Set<Range<Integer>> getHighSpeedVideoFpsRangesFor(Size size) {
        return this.getUnmodifiableRangeSet(this.mRecommendedMap.getHighSpeedVideoFpsRangesFor(size));
    }

    public Set<Size> getHighSpeedVideoSizes() {
        return this.getUnmodifiableSizeSet(this.mRecommendedMap.getHighSpeedVideoSizes());
    }

    public Set<Size> getHighSpeedVideoSizesFor(Range<Integer> range) {
        return this.getUnmodifiableSizeSet(this.mRecommendedMap.getHighSpeedVideoSizesFor(range));
    }

    public Set<Integer> getInputFormats() {
        return this.getUnmodifiableIntegerSet(this.mRecommendedMap.getInputFormats());
    }

    public Set<Size> getInputSizes(int n) {
        return this.getUnmodifiableSizeSet(this.mRecommendedMap.getInputSizes(n));
    }

    public Set<Integer> getOutputFormats() {
        return this.getUnmodifiableIntegerSet(this.mRecommendedMap.getOutputFormats());
    }

    public long getOutputMinFrameDuration(int n, Size size) {
        return this.mRecommendedMap.getOutputMinFrameDuration(n, size);
    }

    public <T> long getOutputMinFrameDuration(Class<T> class_, Size size) {
        if (this.mSupportsPrivate) {
            return this.mRecommendedMap.getOutputMinFrameDuration(class_, size);
        }
        return 0L;
    }

    public Set<Size> getOutputSizes(int n) {
        return this.getUnmodifiableSizeSet(this.mRecommendedMap.getOutputSizes(n));
    }

    public <T> Set<Size> getOutputSizes(Class<T> class_) {
        if (this.mSupportsPrivate) {
            return this.getUnmodifiableSizeSet(this.mRecommendedMap.getOutputSizes(class_));
        }
        return null;
    }

    public long getOutputStallDuration(int n, Size size) {
        return this.mRecommendedMap.getOutputStallDuration(n, size);
    }

    public <T> long getOutputStallDuration(Class<T> class_, Size size) {
        if (this.mSupportsPrivate) {
            return this.mRecommendedMap.getOutputStallDuration(class_, size);
        }
        return 0L;
    }

    public int getRecommendedUseCase() {
        return this.mUsecase;
    }

    public Set<Integer> getValidOutputFormatsForInput(int n) {
        return this.getUnmodifiableIntegerSet(this.mRecommendedMap.getValidOutputFormatsForInput(n));
    }

    public boolean isOutputSupportedFor(int n) {
        return this.mRecommendedMap.isOutputSupportedFor(n);
    }

    public boolean isOutputSupportedFor(Surface surface) {
        return this.mRecommendedMap.isOutputSupportedFor(surface);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RecommendedUsecase {
    }

}

