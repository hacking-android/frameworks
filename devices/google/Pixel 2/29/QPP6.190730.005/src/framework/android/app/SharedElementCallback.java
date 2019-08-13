/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.TransitionUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import java.util.List;
import java.util.Map;

public abstract class SharedElementCallback {
    private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final String BUNDLE_SNAPSHOT_COLOR_SPACE = "sharedElement:snapshot:colorSpace";
    private static final String BUNDLE_SNAPSHOT_GRAPHIC_BUFFER = "sharedElement:snapshot:graphicBuffer";
    private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    static final SharedElementCallback NULL_CALLBACK = new SharedElementCallback(){};
    private Matrix mTempMatrix;

    public Parcelable onCaptureSharedElementSnapshot(View object, Matrix arrf, RectF object2) {
        Object object3;
        if (object instanceof ImageView) {
            object3 = (ImageView)object;
            Drawable drawable2 = ((ImageView)object3).getDrawable();
            Object object4 = ((View)object3).getBackground();
            if (drawable2 != null && (object4 == null || ((Drawable)object4).getAlpha() == 0) && (object4 = TransitionUtils.createDrawableBitmap(drawable2, (View)object3)) != null) {
                object = new Bundle();
                if (((Bitmap)object4).getConfig() != Bitmap.Config.HARDWARE) {
                    ((Bundle)object).putParcelable(BUNDLE_SNAPSHOT_BITMAP, (Parcelable)object4);
                } else {
                    ((Bundle)object).putParcelable(BUNDLE_SNAPSHOT_GRAPHIC_BUFFER, ((Bitmap)object4).createGraphicBufferHandle());
                    arrf = ((Bitmap)object4).getColorSpace();
                    if (arrf != null) {
                        ((BaseBundle)object).putInt(BUNDLE_SNAPSHOT_COLOR_SPACE, arrf.getId());
                    }
                }
                ((BaseBundle)object).putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, ((ImageView)object3).getScaleType().toString());
                if (((ImageView)object3).getScaleType() == ImageView.ScaleType.MATRIX) {
                    object2 = ((ImageView)object3).getImageMatrix();
                    arrf = new float[9];
                    ((Matrix)object2).getValues(arrf);
                    ((Bundle)object).putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, arrf);
                }
                return object;
            }
        }
        if ((object3 = this.mTempMatrix) == null) {
            this.mTempMatrix = new Matrix((Matrix)arrf);
        } else {
            ((Matrix)object3).set((Matrix)arrf);
        }
        arrf = (ViewGroup)((View)object).getParent();
        return TransitionUtils.createViewBitmap((View)object, this.mTempMatrix, (RectF)object2, (ViewGroup)arrf);
    }

    public View onCreateSnapshotView(Context object, Parcelable arrf) {
        Object object2;
        block5 : {
            block4 : {
                object2 = null;
                if (!(arrf instanceof Bundle)) break block4;
                Bundle bundle = (Bundle)arrf;
                Object object3 = (GraphicBuffer)bundle.getParcelable(BUNDLE_SNAPSHOT_GRAPHIC_BUFFER);
                object2 = (Bitmap)bundle.getParcelable(BUNDLE_SNAPSHOT_BITMAP);
                if (object3 == null && object2 == null) {
                    return null;
                }
                arrf = object2;
                if (object2 == null) {
                    object2 = null;
                    int n = bundle.getInt(BUNDLE_SNAPSHOT_COLOR_SPACE, 0);
                    arrf = object2;
                    if (n >= 0) {
                        arrf = object2;
                        if (n < ColorSpace.Named.values().length) {
                            arrf = ColorSpace.get(ColorSpace.Named.values()[n]);
                        }
                    }
                    arrf = Bitmap.wrapHardwareBuffer(HardwareBuffer.createFromGraphicBuffer((GraphicBuffer)object3), (ColorSpace)arrf);
                }
                object = object3 = new ImageView((Context)object);
                ((ImageView)object3).setImageBitmap((Bitmap)arrf);
                ((ImageView)object3).setScaleType(ImageView.ScaleType.valueOf(bundle.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
                object2 = object;
                if (((ImageView)object3).getScaleType() != ImageView.ScaleType.MATRIX) break block5;
                arrf = bundle.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
                object2 = new Matrix();
                ((Matrix)object2).setValues(arrf);
                ((ImageView)object3).setImageMatrix((Matrix)object2);
                object2 = object;
                break block5;
            }
            if (!(arrf instanceof Bitmap)) break block5;
            arrf = (Bitmap)arrf;
            object2 = new View((Context)object);
            ((View)object2).setBackground(new BitmapDrawable(((Context)object).getResources(), (Bitmap)arrf));
        }
        return object2;
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
    }

    public void onRejectSharedElements(List<View> list) {
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener.onSharedElementsReady();
    }

    public static interface OnSharedElementsReadyListener {
        public void onSharedElementsReady();
    }

}

