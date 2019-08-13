/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.annotation.UnsupportedAppUsage;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SurfaceUtils {
    public static void checkConstrainedHighSpeedSurfaces(Collection<Surface> object, Range<Integer> list, StreamConfigurationMap object2) {
        block7 : {
            Object[] arrobject;
            block10 : {
                boolean bl;
                block9 : {
                    block8 : {
                        if (object == null || object.size() == 0 || object.size() > 2) break block7;
                        if (list != null) break block8;
                        list = Arrays.asList(((StreamConfigurationMap)object2).getHighSpeedVideoSizes());
                        break block9;
                    }
                    arrobject = ((StreamConfigurationMap)object2).getHighSpeedVideoFpsRanges();
                    if (!Arrays.asList(arrobject).contains(list)) break block10;
                    list = Arrays.asList(((StreamConfigurationMap)object2).getHighSpeedVideoSizesFor((Range<Integer>)((Object)list)));
                }
                Iterator<Surface> iterator = object.iterator();
                while (iterator.hasNext()) {
                    arrobject = iterator.next();
                    SurfaceUtils.checkHighSpeedSurfaceFormat(arrobject);
                    object2 = SurfaceUtils.getSurfaceSize(arrobject);
                    if (list.contains(object2)) {
                        if (!SurfaceUtils.isSurfaceForPreview(arrobject) && !SurfaceUtils.isSurfaceForHwVideoEncoder((Surface)arrobject)) {
                            throw new IllegalArgumentException("This output surface is neither preview nor hardware video encoding surface");
                        }
                        if (!SurfaceUtils.isSurfaceForPreview((Surface)arrobject) || !SurfaceUtils.isSurfaceForHwVideoEncoder((Surface)arrobject)) continue;
                        throw new IllegalArgumentException("This output surface can not be both preview and hardware video encoding surface");
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Surface size ");
                    ((StringBuilder)object).append(((Size)object2).toString());
                    ((StringBuilder)object).append(" is not part of the high speed supported size list ");
                    ((StringBuilder)object).append(Arrays.toString(list.toArray()));
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                if (object.size() == 2 && (bl = SurfaceUtils.isSurfaceForPreview((Surface)(object = object.iterator()).next())) == SurfaceUtils.isSurfaceForPreview((Surface)object.next())) {
                    throw new IllegalArgumentException("The 2 output surfaces must have different type");
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Fps range ");
            ((StringBuilder)object).append(((Range)((Object)list)).toString());
            ((StringBuilder)object).append(" in the request is not a supported high speed fps range ");
            ((StringBuilder)object).append(Arrays.toString(arrobject));
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Output target surface list must not be null and the size must be 1 or 2");
    }

    private static void checkHighSpeedSurfaceFormat(Surface object) {
        int n = SurfaceUtils.getSurfaceFormat((Surface)object);
        if (n == 34) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Surface format(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") is not for preview or hardware video encoding!");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static int getSurfaceDataspace(Surface surface) {
        try {
            int n = LegacyCameraDevice.detectSurfaceDataspace(surface);
            return n;
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            throw new IllegalArgumentException("Surface was abandoned", bufferQueueAbandonedException);
        }
    }

    public static int getSurfaceFormat(Surface surface) {
        try {
            int n = LegacyCameraDevice.detectSurfaceType(surface);
            return n;
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            throw new IllegalArgumentException("Surface was abandoned", bufferQueueAbandonedException);
        }
    }

    public static long getSurfaceId(Surface surface) {
        try {
            long l = LegacyCameraDevice.getSurfaceId(surface);
            return l;
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            return 0L;
        }
    }

    @UnsupportedAppUsage
    public static Size getSurfaceSize(Surface object) {
        try {
            object = LegacyCameraDevice.getSurfaceSize((Surface)object);
            return object;
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            throw new IllegalArgumentException("Surface was abandoned", bufferQueueAbandonedException);
        }
    }

    public static boolean isFlexibleConsumer(Surface surface) {
        return LegacyCameraDevice.isFlexibleConsumer(surface);
    }

    public static boolean isSurfaceForHwVideoEncoder(Surface surface) {
        return LegacyCameraDevice.isVideoEncoderConsumer(surface);
    }

    public static boolean isSurfaceForPreview(Surface surface) {
        return LegacyCameraDevice.isPreviewConsumer(surface);
    }
}

