/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.utils.ParamsUtils;
import android.hardware.camera2.utils.SizeAreaComparator;
import android.os.Parcelable;
import android.util.Log;
import android.util.Size;
import android.util.SizeF;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ParameterUtils {
    private static final double ASPECT_RATIO_TOLERANCE = 0.05000000074505806;
    public static final Camera.Area CAMERA_AREA_DEFAULT;
    private static final boolean DEBUG = false;
    public static final Rect NORMALIZED_RECTANGLE_DEFAULT;
    public static final int NORMALIZED_RECTANGLE_MAX = 1000;
    public static final int NORMALIZED_RECTANGLE_MIN = -1000;
    public static final Rect RECTANGLE_EMPTY;
    private static final String TAG = "ParameterUtils";
    private static final int ZOOM_RATIO_MULTIPLIER = 100;

    static {
        NORMALIZED_RECTANGLE_DEFAULT = new Rect(-1000, -1000, 1000, 1000);
        CAMERA_AREA_DEFAULT = new Camera.Area(new Rect(NORMALIZED_RECTANGLE_DEFAULT), 1);
        RECTANGLE_EMPTY = new Rect(0, 0, 0, 0);
    }

    private ParameterUtils() {
        throw new AssertionError();
    }

    public static boolean containsSize(List<Camera.Size> object, int n, int n2) {
        Preconditions.checkNotNull(object, "sizeList must not be null");
        object = object.iterator();
        while (object.hasNext()) {
            Camera.Size size = (Camera.Size)object.next();
            if (size.height != n2 || size.width != n) continue;
            return true;
        }
        return false;
    }

    public static WeightedRectangle convertCameraAreaToActiveArrayRectangle(Rect rect, ZoomData zoomData, Camera.Area area) {
        return ParameterUtils.convertCameraAreaToActiveArrayRectangle(rect, zoomData, area, true);
    }

    private static WeightedRectangle convertCameraAreaToActiveArrayRectangle(Rect object, ZoomData object2, Camera.Area area, boolean bl) {
        object = ((ZoomData)object2).previewCrop;
        object2 = ((ZoomData)object2).reportedCrop;
        float f = (float)((Rect)object).width() * 1.0f / 2000.0f;
        float f2 = (float)((Rect)object).height() * 1.0f / 2000.0f;
        Matrix matrix = new Matrix();
        matrix.setTranslate(1000.0f, 1000.0f);
        matrix.postScale(f, f2);
        matrix.postTranslate(((Rect)object).left, ((Rect)object).top);
        if (!bl) {
            object = object2;
        }
        object2 = ParamsUtils.mapRect(matrix, area.rect);
        if (!((Rect)object2).intersect((Rect)object)) {
            ((Rect)object2).set(RECTANGLE_EMPTY);
        }
        if (area.weight < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("convertCameraAreaToMeteringRectangle - rectangle ");
            ((StringBuilder)object).append(ParameterUtils.stringFromArea(area));
            ((StringBuilder)object).append(" has too small weight, clip to 0");
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        return new WeightedRectangle((Rect)object2, area.weight);
    }

    private static Point convertCameraPointToActiveArrayPoint(Rect object, ZoomData zoomData, Point object2, boolean bl) {
        object2 = new Camera.Area(new Rect(((Point)object2).x, ((Point)object2).y, ((Point)object2).x, ((Point)object2).y), 1);
        object = ParameterUtils.convertCameraAreaToActiveArrayRectangle((Rect)object, zoomData, (Camera.Area)object2, bl);
        return new Point(object.rect.left, object.rect.top);
    }

    public static Face convertFaceFromLegacy(Camera.Face object, Rect parcelable, ZoomData zoomData) {
        Preconditions.checkNotNull(object, "face must not be null");
        Object object2 = new Camera.Area(((Camera.Face)object).rect, 1);
        object2 = ParameterUtils.convertCameraAreaToActiveArrayRectangle(parcelable, zoomData, (Camera.Area)object2);
        Point point = ((Camera.Face)object).leftEye;
        Point point2 = ((Camera.Face)object).rightEye;
        Point point3 = ((Camera.Face)object).mouth;
        if (point != null && point2 != null && point3 != null && point.x != -2000 && point.y != -2000 && point2.x != -2000 && point2.y != -2000 && point3.x != -2000 && point3.y != -2000) {
            point = ParameterUtils.convertCameraPointToActiveArrayPoint(parcelable, zoomData, point, true);
            point3 = ParameterUtils.convertCameraPointToActiveArrayPoint(parcelable, zoomData, point, true);
            parcelable = ParameterUtils.convertCameraPointToActiveArrayPoint(parcelable, zoomData, point, true);
            object = ((WeightedRectangle)object2).toFace(((Camera.Face)object).id, point, point3, (Point)parcelable);
        } else {
            object = ((WeightedRectangle)object2).toFace();
        }
        return object;
    }

    public static MeteringData convertMeteringRectangleToLegacy(Rect rect, MeteringRectangle meteringRectangle, ZoomData zoomData) {
        Rect rect2 = zoomData.previewCrop;
        float f = 2000.0f / (float)rect2.width();
        float f2 = 2000.0f / (float)rect2.height();
        Object object = new Matrix();
        ((Matrix)object).setTranslate(-rect2.left, -rect2.top);
        ((Matrix)object).postScale(f, f2);
        ((Matrix)object).postTranslate(-1000.0f, -1000.0f);
        Rect rect3 = ParamsUtils.mapRect((Matrix)object, meteringRectangle.getRect());
        object = new Rect(rect3);
        if (!((Rect)object).intersect(NORMALIZED_RECTANGLE_DEFAULT)) {
            Log.w(TAG, "convertMeteringRectangleToLegacy - metering rectangle too small, no metering will be done");
            ((Rect)object).set(RECTANGLE_EMPTY);
            object = new Camera.Area(RECTANGLE_EMPTY, 0);
        } else {
            object = new Camera.Area((Rect)object, meteringRectangle.getMeteringWeight());
        }
        Rect rect4 = meteringRectangle.getRect();
        if (!rect4.intersect(rect2)) {
            rect4.set(RECTANGLE_EMPTY);
        }
        return new MeteringData((Camera.Area)object, rect4, ParameterUtils.convertCameraAreaToActiveArrayRectangle((Rect)rect, (ZoomData)zoomData, (Camera.Area)new Camera.Area((Rect)rect3, (int)meteringRectangle.getMeteringWeight()), (boolean)false).rect);
    }

    public static ZoomData convertScalerCropRegion(Rect rect, Rect rect2, Size size, Camera.Parameters parameters) {
        Rect rect3 = new Rect(0, 0, rect.width(), rect.height());
        rect = rect2 == null ? rect3 : rect2;
        rect2 = new Rect();
        Rect rect4 = new Rect();
        return new ZoomData(ParameterUtils.getClosestAvailableZoomCrop(parameters, rect3, size, rect, rect2, rect4), rect4, rect2);
    }

    public static Size convertSize(Camera.Size size) {
        Preconditions.checkNotNull(size, "size must not be null");
        return new Size(size.width, size.height);
    }

    public static List<Size> convertSizeList(List<Camera.Size> object) {
        Preconditions.checkNotNull(object, "sizeList must not be null");
        ArrayList<Size> arrayList = new ArrayList<Size>(object.size());
        Iterator<Camera.Size> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            arrayList.add(new Size(((Camera.Size)object).width, ((Camera.Size)object).height));
        }
        return arrayList;
    }

    public static Size[] convertSizeListToArray(List<Camera.Size> object) {
        Preconditions.checkNotNull(object, "sizeList must not be null");
        Size[] arrsize = new Size[object.size()];
        int n = 0;
        Iterator<Camera.Size> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            arrsize[n] = new Size(((Camera.Size)object).width, ((Camera.Size)object).height);
            ++n;
        }
        return arrsize;
    }

    private static List<Rect> getAvailableCropRectangles(Camera.Parameters object, Rect rect, Size object2) {
        Preconditions.checkNotNull(object, "params must not be null");
        Preconditions.checkNotNull(rect, "activeArray must not be null");
        Preconditions.checkNotNull(object2, "streamSize must not be null");
        Rect rect2 = ParameterUtils.getPreviewCropRectangleUnzoomed(rect, (Size)object2);
        if (!((Camera.Parameters)object).isZoomSupported()) {
            return new ArrayList<Rect>(Arrays.asList(rect2));
        }
        ArrayList<Rect> arrayList = new ArrayList<Rect>(((Camera.Parameters)object).getMaxZoom() + 1);
        object2 = new Matrix();
        RectF rectF = new RectF();
        object = ((Camera.Parameters)object).getZoomRatios().iterator();
        while (object.hasNext()) {
            float f = 100.0f / (float)((Integer)object.next()).intValue();
            ParamsUtils.convertRectF(rect2, rectF);
            ((Matrix)object2).setScale(f, f, rect.exactCenterX(), rect.exactCenterY());
            ((Matrix)object2).mapRect(rectF);
            arrayList.add(ParamsUtils.createRect(rectF));
        }
        return arrayList;
    }

    public static List<Rect> getAvailablePreviewZoomCropRectangles(Camera.Parameters parameters, Rect rect, Size size) {
        Preconditions.checkNotNull(parameters, "params must not be null");
        Preconditions.checkNotNull(rect, "activeArray must not be null");
        Preconditions.checkNotNull(size, "previewSize must not be null");
        return ParameterUtils.getAvailableCropRectangles(parameters, rect, size);
    }

    public static List<Rect> getAvailableZoomCropRectangles(Camera.Parameters parameters, Rect rect) {
        Preconditions.checkNotNull(parameters, "params must not be null");
        Preconditions.checkNotNull(rect, "activeArray must not be null");
        return ParameterUtils.getAvailableCropRectangles(parameters, rect, ParamsUtils.createSize(rect));
    }

    public static int getClosestAvailableZoomCrop(Camera.Parameters object, Rect rect, Size object2, Rect rect2, Rect rect3, Rect rect4) {
        Preconditions.checkNotNull(object, "params must not be null");
        Preconditions.checkNotNull(rect, "activeArray must not be null");
        Preconditions.checkNotNull(object2, "streamSize must not be null");
        Preconditions.checkNotNull(rect3, "reportedCropRegion must not be null");
        Preconditions.checkNotNull(rect4, "previewCropRegion must not be null");
        rect2 = new Rect(rect2);
        if (!rect2.intersect(rect)) {
            Log.w(TAG, "getClosestAvailableZoomCrop - Crop region out of range; setting to active array size");
            rect2.set(rect);
        }
        Rect rect5 = ParameterUtils.getPreviewCropRectangleUnzoomed(rect, (Size)object2);
        Rect rect6 = ParameterUtils.shrinkToSameAspectRatioCentered(rect5, rect2);
        rect5 = null;
        Object var8_8 = null;
        int n = -1;
        List<Rect> list = ParameterUtils.getAvailableZoomCropRectangles((Camera.Parameters)object, rect);
        List<Rect> list2 = ParameterUtils.getAvailablePreviewZoomCropRectangles((Camera.Parameters)object, rect, (Size)object2);
        if (list.size() == list2.size()) {
            int n2 = 0;
            object2 = var8_8;
            rect = rect5;
            object = rect2;
            while (n2 < list.size()) {
                rect5 = list2.get(n2);
                rect2 = list.get(n2);
                boolean bl = n == -1 ? true : rect5.width() >= rect6.width() && rect5.height() >= rect6.height();
                if (!bl) break;
                object2 = rect5;
                rect = rect2;
                n = n2++;
            }
            if (n != -1) {
                rect3.set(rect);
                rect4.set((Rect)object2);
                return n;
            }
            throw new AssertionError((Object)"Should've found at least one valid zoom index");
        }
        throw new AssertionError((Object)"available reported/preview crop region size mismatch");
    }

    public static Size getLargestSupportedJpegSizeByArea(Camera.Parameters parameters) {
        Preconditions.checkNotNull(parameters, "params must not be null");
        return SizeAreaComparator.findLargestByArea(ParameterUtils.convertSizeList(parameters.getSupportedPictureSizes()));
    }

    public static float getMaxZoomRatio(Camera.Parameters object) {
        if (!((Camera.Parameters)object).isZoomSupported()) {
            return 1.0f;
        }
        object = ((Camera.Parameters)object).getZoomRatios();
        return (float)((Integer)object.get(object.size() - 1)).intValue() * 1.0f / 100.0f;
    }

    private static Rect getPreviewCropRectangleUnzoomed(Rect rect, Size object) {
        if (((Size)object).getWidth() <= rect.width()) {
            if (((Size)object).getHeight() <= rect.height()) {
                float f;
                float f2 = (float)rect.width() * 1.0f / (float)rect.height();
                float f3 = (float)((Size)object).getWidth() * 1.0f / (float)((Size)object).getHeight();
                if ((double)Math.abs(f3 - f2) < 0.05000000074505806) {
                    f2 = rect.height();
                    f = rect.width();
                } else if (f3 < f2) {
                    f2 = rect.height();
                    f = f2 * f3;
                } else {
                    f = rect.width();
                    f2 = f / f3;
                }
                Matrix matrix = new Matrix();
                object = new RectF(0.0f, 0.0f, f, f2);
                matrix.setTranslate(rect.exactCenterX(), rect.exactCenterY());
                matrix.postTranslate(-((RectF)object).centerX(), -((RectF)object).centerY());
                matrix.mapRect((RectF)object);
                return ParamsUtils.createRect((RectF)object);
            }
            throw new IllegalArgumentException("previewSize must not be taller than activeArray");
        }
        throw new IllegalArgumentException("previewSize must not be wider than activeArray");
    }

    private static SizeF getZoomRatio(Size size, Size size2) {
        Preconditions.checkNotNull(size, "activeArraySize must not be null");
        Preconditions.checkNotNull(size2, "cropSize must not be null");
        Preconditions.checkArgumentPositive(size2.getWidth(), "cropSize.width must be positive");
        Preconditions.checkArgumentPositive(size2.getHeight(), "cropSize.height must be positive");
        return new SizeF((float)size.getWidth() * 1.0f / (float)size2.getWidth(), (float)size.getHeight() * 1.0f / (float)size2.getHeight());
    }

    private static Rect shrinkToSameAspectRatioCentered(Rect rect, Rect rect2) {
        float f;
        float f2 = (float)rect.width() * 1.0f / (float)rect.height();
        float f3 = (float)rect2.width() * 1.0f / (float)rect2.height();
        if (f3 < f2) {
            f2 = rect.height();
            f = f2 * f3;
        } else {
            f = rect.width();
            f2 = f / f3;
        }
        Matrix matrix = new Matrix();
        RectF rectF = new RectF(rect2);
        matrix.setScale(f / (float)rect.width(), f2 / (float)rect.height(), rect2.exactCenterX(), rect2.exactCenterY());
        matrix.mapRect(rectF);
        return ParamsUtils.createRect(rectF);
    }

    public static String stringFromArea(Camera.Area area) {
        if (area == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Rect rect = area.rect;
        stringBuilder.setLength(0);
        stringBuilder.append("([");
        stringBuilder.append(rect.left);
        stringBuilder.append(',');
        stringBuilder.append(rect.top);
        stringBuilder.append("][");
        stringBuilder.append(rect.right);
        stringBuilder.append(',');
        stringBuilder.append(rect.bottom);
        stringBuilder.append(']');
        stringBuilder.append(',');
        stringBuilder.append(area.weight);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public static String stringFromAreaList(List<Camera.Area> list) {
        StringBuilder stringBuilder = new StringBuilder();
        if (list == null) {
            return null;
        }
        int n = 0;
        for (Camera.Area area : list) {
            if (area == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(ParameterUtils.stringFromArea(area));
            }
            if (n != list.size() - 1) {
                stringBuilder.append(", ");
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    public static class MeteringData {
        public final Camera.Area meteringArea;
        public final Rect previewMetering;
        public final Rect reportedMetering;

        public MeteringData(Camera.Area area, Rect rect, Rect rect2) {
            this.meteringArea = area;
            this.previewMetering = rect;
            this.reportedMetering = rect2;
        }
    }

    public static class WeightedRectangle {
        public final Rect rect;
        public final int weight;

        public WeightedRectangle(Rect rect, int n) {
            this.rect = Preconditions.checkNotNull(rect, "rect must not be null");
            this.weight = n;
        }

        private static int clip(int n, int n2, int n3, Rect rect, String string2) {
            if (n < n2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("toMetering - Rectangle ");
                stringBuilder.append(rect);
                stringBuilder.append(" ");
                stringBuilder.append(string2);
                stringBuilder.append(" too small, clip to ");
                stringBuilder.append(n2);
                Log.w(ParameterUtils.TAG, stringBuilder.toString());
            } else {
                n2 = n;
                if (n > n3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("toMetering - Rectangle ");
                    stringBuilder.append(rect);
                    stringBuilder.append(" ");
                    stringBuilder.append(string2);
                    stringBuilder.append(" too small, clip to ");
                    stringBuilder.append(n3);
                    Log.w(ParameterUtils.TAG, stringBuilder.toString());
                    n2 = n3;
                }
            }
            return n2;
        }

        private static int clipLower(int n, int n2, Rect rect, String string2) {
            return WeightedRectangle.clip(n, n2, Integer.MAX_VALUE, rect, string2);
        }

        public Face toFace() {
            int n = WeightedRectangle.clip(this.weight, 1, 100, this.rect, "score");
            return new Face(this.rect, n);
        }

        public Face toFace(int n, Point point, Point point2, Point point3) {
            n = WeightedRectangle.clipLower(n, 0, this.rect, "id");
            int n2 = WeightedRectangle.clip(this.weight, 1, 100, this.rect, "score");
            return new Face(this.rect, n2, n, point, point2, point3);
        }

        public MeteringRectangle toMetering() {
            int n = WeightedRectangle.clip(this.weight, 0, 1000, this.rect, "weight");
            return new MeteringRectangle(WeightedRectangle.clipLower(this.rect.left, 0, this.rect, "left"), WeightedRectangle.clipLower(this.rect.top, 0, this.rect, "top"), WeightedRectangle.clipLower(this.rect.width(), 0, this.rect, "width"), WeightedRectangle.clipLower(this.rect.height(), 0, this.rect, "height"), n);
        }
    }

    public static class ZoomData {
        public final Rect previewCrop;
        public final Rect reportedCrop;
        public final int zoomIndex;

        public ZoomData(int n, Rect rect, Rect rect2) {
            this.zoomIndex = n;
            this.previewCrop = rect;
            this.reportedCrop = rect2;
        }
    }

}

